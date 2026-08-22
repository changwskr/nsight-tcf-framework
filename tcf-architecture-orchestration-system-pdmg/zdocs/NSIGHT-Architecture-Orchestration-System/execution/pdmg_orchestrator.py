#!/usr/bin/env python3
from __future__ import annotations

import argparse
import json
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
if str(HERE) not in sys.path:
    sys.path.insert(0, str(HERE))

from engine.approval import create_approval, validate_approval, add_required_approval, validate_approval_register
from engine.common import UNKNOWN, ensure_dir, now_iso, read_json, timestamp_id, write_json, write_text
from engine.conformance import compare, write_conformance_outputs
from engine.checks import architecture_check, security_check, write_check
from engine.evidence import artifact_manifest, import_runtime
from engine.gates import evaluate_gate, write_gate_result
from engine.impact import analyze_changed_files, write_impact
from engine.governance import register_gap, resolve_item, create_adr
from engine.reference import build_reference, write_reference_outputs
from engine.release import release
from engine.runner import run_command
from engine.schema import validate_file
from engine.scanner import scan_repository, write_scan_outputs

ROOT = HERE.parent
CONFIG = HERE / "config"
WORKSPACE = ROOT / "03-WORKSPACE"
RELEASES = ROOT / "10-RELEASES"


def load_reference_projects() -> list[str]:
    return read_json(CONFIG / "reference-projects.json", {})["projects"]


def run_dir(run_id: str) -> Path:
    p = WORKSPACE / "RUNS" / run_id
    if not p.exists():
        raise SystemExit(f"Run not found: {run_id}")
    return p


def create_run(kind: str, mission: str, source: str, target_projects: list[str] | None = None, reference_baseline: str | None = None) -> tuple[str, Path]:
    prefix = "REF" if kind == "reference" else "TGT"
    rid = f"{prefix}-RUN-{timestamp_id()}"
    p = ensure_dir(WORKSPACE / "RUNS" / rid)
    for d in ["00-SOURCE", "10-DOCUMENT", "20-MODEL", "30-CONFORMANCE", "40-TEST", "50-RUNTIME", "60-DRIFT", "70-GAP-ADR", "80-APPROVAL", "90-OUT"]:
        ensure_dir(p / d)
    manifest = {
        "runId": rid,
        "kind": kind,
        "createdAt": now_iso(),
        "mission": mission,
        "sourceRoot": str(Path(source).resolve()),
        "targetProjects": target_projects or [],
        "referenceBaseline": reference_baseline or UNKNOWN,
        "status": "CREATED",
    }
    write_json(p / "RUN-MANIFEST.json", manifest)
    write_text(p / "RUN-MANIFEST.md", f"# Run Manifest\n\n- Run: `{rid}`\n- Kind: `{kind}`\n- Mission: {mission}\n- Source: `{manifest['sourceRoot']}`\n")
    return rid, p


def cmd_init(args):
    ensure_dir(WORKSPACE / "RUNS")
    ensure_dir(RELEASES / "REFERENCE")
    ensure_dir(RELEASES / "TARGET")
    print(json.dumps({"status": "READY", "workspace": str(WORKSPACE), "referenceProjects": load_reference_projects()}, ensure_ascii=False, indent=2))


def cmd_scan_reference(args):
    rid, p = create_run("reference", args.mission, args.repo)
    scan = scan_repository(Path(args.repo), load_reference_projects())
    write_scan_outputs(scan, p / "00-SOURCE")
    # G10 document baseline generated only from scanned facts; unknowns remain explicit.
    write_text(p / "10-DOCUMENT/CURRENT-ARCHITECTURE.md", "\n".join([
        "# PDMG Current Architecture — Source Derived", "",
        "> 이 문서는 Reference Source scan에서 확인된 사실을 정리한 자동 산출물이며 승인된 REFERENCE 표준 자체가 아닙니다.", "",
        f"- Repository: `{scan['repositoryRoot']}`",
        f"- Branch: `{scan['git']['branch']}`",
        f"- Commit: `{scan['git']['commit']}`",
        f"- Reference projects: `{', '.join(load_reference_projects())}`",
        "", "## Status", "", "- Source facts: **AS-IS**", "- Reference promotion: **PENDING**"
    ]))
    ref = build_reference(scan)
    write_reference_outputs(ref, p / "20-MODEL")
    validate_file(p / "20-MODEL/reference-baseline-draft.json", HERE / "schemas/reference-baseline.schema.json", p / "20-MODEL/schema-validation.json")
    write_json(p / "30-CONFORMANCE/reference-rules.json", ref["rules"])
    # Self-conformance is a static evaluator input for RG40; it is not runtime proof.
    self_result = compare(ref, scan)
    write_conformance_outputs(self_result, p / "40-TEST")
    write_json(p / "60-DRIFT/DRIFT-REGISTER.json", ref["internalDrifts"])
    write_json(p / "70-GAP-ADR/GAP-REGISTER.json", [
        {"gapId": d["driftId"], "severity": d["severity"], "status": "OPEN", "source": "reference-internal-drift"}
        for d in ref["internalDrifts"] if d["severity"] in {"CRITICAL", "HIGH"}
    ])
    print(rid)


def cmd_create_target(args):
    rid, _ = create_run("target", args.mission, args.repo, args.projects, args.reference)
    print(rid)


def _load_reference(path_or_id: str) -> dict:
    p = Path(path_or_id)
    if p.is_file():
        return read_json(p, {})
    if p.is_dir():
        for candidate in [p / "reference-baseline-draft.json", p / "BASELINE-RELEASE.json", p / "evidence-package/20-MODEL/reference-baseline-draft.json"]:
            if candidate.exists():
                data = read_json(candidate, {})
                if "rules" not in data and (p / "evidence-package/20-MODEL/reference-baseline-draft.json").exists():
                    data = read_json(p / "evidence-package/20-MODEL/reference-baseline-draft.json", {})
                return data
    release_dir = RELEASES / "REFERENCE" / path_or_id
    candidate = release_dir / "evidence-package/20-MODEL/reference-baseline-draft.json"
    if candidate.exists():
        data = read_json(candidate, {})
        data["promotionStatus"] = "RELEASED"
        return data
    raise SystemExit(f"Reference baseline not found: {path_or_id}")


def cmd_scan_target(args):
    p = run_dir(args.run)
    manifest = read_json(p / "RUN-MANIFEST.json", {})
    projects = manifest.get("targetProjects") or args.projects
    if not projects:
        raise SystemExit("No target projects specified")
    scan = scan_repository(Path(manifest["sourceRoot"]), projects)
    write_scan_outputs(scan, p / "00-SOURCE")
    write_text(p / "10-DOCUMENT/CURRENT-ARCHITECTURE.md", "\n".join([
        "# Target Current Architecture — Source Derived", "",
        "> Target Source scan에서 확인된 사실만 기록합니다. PDMG Reference와의 차이는 Conformance 단계에서 판단합니다.", "",
        f"- Repository: `{scan['repositoryRoot']}`",
        f"- Branch: `{scan['git']['branch']}`",
        f"- Commit: `{scan['git']['commit']}`",
        f"- Target projects: `{', '.join(projects)}`"
    ]))
    write_json(p / "20-MODEL/target-source-model.json", {
        "repositoryRoot": scan["repositoryRoot"],
        "projects": [{
            "name": x["name"], "technology": x["technology"],
            "serviceIdOwners": x["serviceIdOwners"], "relations": x.get("relations", []),
            "serviceTraces": x.get("serviceTraces", []), "tables": x.get("tables", [])
        } for x in scan["projects"]]
    })
    validate_file(p / "20-MODEL/target-source-model.json", HERE / "schemas/target-model.schema.json", p / "20-MODEL/schema-validation.json")
    ref = _load_reference(args.reference or manifest.get("referenceBaseline", ""))
    write_json(p / "30-CONFORMANCE/reference-status.json", {
        "reference": args.reference or manifest.get("referenceBaseline", UNKNOWN),
        "promotionStatus": ref.get("promotionStatus", "DRAFT")
    })
    result = compare(ref, scan)
    write_conformance_outputs(result, p / "30-CONFORMANCE")
    drifts = read_json(p / "30-CONFORMANCE/DRIFT-REGISTER.json", [])
    write_json(p / "60-DRIFT/DRIFT-REGISTER.json", drifts)
    gaps = [
        {"gapId": d["driftId"], "severity": d["severity"], "status": "OPEN", "source": "conformance"}
        for d in drifts if d["severity"] in {"CRITICAL", "HIGH"}
    ]
    write_json(p / "70-GAP-ADR/GAP-REGISTER.json", gaps)
    print(args.run)


def cmd_artifact(args):
    p = run_dir(args.run)
    data = artifact_manifest(Path(args.file), p / "40-TEST/artifact-manifest.json", args.build_id)
    print(json.dumps(data, ensure_ascii=False, indent=2))


def cmd_runtime(args):
    p = run_dir(args.run)
    meta = {
        "architectureBaselineId": args.architecture_baseline_id,
        "architectureModelVersion": args.model_version,
        "sourceCommit": args.source_commit,
        "buildId": args.build_id,
        "artifactHash": args.artifact_hash,
        "deploymentId": args.deployment_id,
        "serviceId": args.service_id,
        "traceId": args.trace_id,
        "guid": args.guid,
    }
    manifest = import_runtime([Path(x) for x in args.files], p / "50-RUNTIME", meta)
    print(json.dumps(manifest, ensure_ascii=False, indent=2))


def cmd_register_gap(args):
    p = run_dir(args.run)
    gap = register_gap(p / "70-GAP-ADR/GAP-REGISTER.json", args.gap_id, args.severity, args.as_is, args.to_be, args.title, args.adr_required)
    print(json.dumps(gap, ensure_ascii=False, indent=2))


def cmd_resolve(args):
    p = run_dir(args.run)
    path = p / ("60-DRIFT/DRIFT-REGISTER.json" if args.kind == "drift" else "70-GAP-ADR/GAP-REGISTER.json")
    item = resolve_item(path, args.id, args.status, args.comment)
    print(json.dumps(item, ensure_ascii=False, indent=2))


def cmd_create_adr(args):
    p = run_dir(args.run)
    out = p / f"70-GAP-ADR/{args.adr_id}.json"
    adr = create_adr(out, args.adr_id, args.title, args.problem, args.context, args.alternative or [], args.recommendation, args.impact, args.risk)
    print(json.dumps(adr, ensure_ascii=False, indent=2))


def cmd_require_approval(args):
    p = run_dir(args.run)
    item = add_required_approval(p / "80-APPROVAL/REQUIRED-APPROVALS.json", args.approval_id, Path(args.artifact), args.role, args.reason)
    print(json.dumps(item, ensure_ascii=False, indent=2))


def cmd_approve(args):
    p = run_dir(args.run)
    out = p / f"80-APPROVAL/{args.approval_id}.json"
    data = create_approval(Path(args.artifact), args.approval_id, args.approver, args.role, args.decision, args.comment, args.expiration, out)
    write_json(p / "80-APPROVAL/APPROVAL.json", data)
    summary = validate_approval_register(p / "80-APPROVAL/REQUIRED-APPROVALS.json", p / "80-APPROVAL", 1)
    write_json(p / "80-APPROVAL/APPROVAL-REGISTER-RESULT.json", summary)
    print(json.dumps({**data, "validation": validate_approval(out), "register": summary}, ensure_ascii=False, indent=2))


def cmd_evaluate(args):
    p = run_dir(args.run)
    manifest = read_json(p / "RUN-MANIFEST.json", {})
    kind = manifest.get("kind")
    config_file = CONFIG / "gates" / ("reference-gates.json" if kind == "reference" else "target-gates.json")
    gates = read_json(config_file, {}).get("gates", {})
    if args.gate not in gates:
        raise SystemExit(f"Unknown gate {args.gate}; available: {', '.join(gates)}")
    result = evaluate_gate(gates[args.gate], p)
    out = p / f"80-APPROVAL/{args.gate}-RESULT.json"
    write_gate_result(result, out)
    print(json.dumps(result, ensure_ascii=False, indent=2))
    if result["decision"] == "HOLD":
        return 2
    return 0


def cmd_release(args):
    p = run_dir(args.run)
    manifest = read_json(p / "RUN-MANIFEST.json", {})
    kind = manifest.get("kind")
    gate_id = "RHG90" if kind == "reference" else "HG90"
    gate_result = p / f"80-APPROVAL/{gate_id}-RESULT.json"
    release_root = RELEASES / ("REFERENCE" if kind == "reference" else "TARGET")
    data = release(p, gate_result, release_root, kind, args.previous)
    print(json.dumps(data, ensure_ascii=False, indent=2))


def cmd_schema_validate(args):
    result = validate_file(Path(args.data), Path(args.schema), Path(args.out) if args.out else None)
    print(json.dumps(result, ensure_ascii=False, indent=2))
    return result["exitCode"]


def cmd_static_check(args):
    p = run_dir(args.run)
    source = p / "00-SOURCE/source-baseline.json"
    if args.type == "architecture":
        data = architecture_check(source)
        out = p / "40-TEST/architecture-result.json"
    else:
        allowed = args.allow_private_key_project or ["pdmg-jwt", "pdmg-fw"]
        data = security_check(source, allowed)
        out = p / "40-TEST/security-result.json"
    write_check(data, out)
    print(json.dumps(data, ensure_ascii=False, indent=2))
    return data["exitCode"]


def cmd_run_check(args):
    p = run_dir(args.run)
    stage_map = {"build": "40-TEST/build-result.json", "test": "40-TEST/test-result.json", "security": "40-TEST/security-result.json", "architecture": "40-TEST/architecture-result.json"}
    out = p / stage_map[args.type]
    command = list(args.command)
    if command and command[0] == "--":
        command = command[1:]
    if not command:
        raise SystemExit("run-check requires a command after --")
    data = run_command(command, Path(args.cwd), out, args.type)
    print(json.dumps(data, ensure_ascii=False, indent=2))
    return data["exitCode"]


def cmd_deployment(args):
    p = run_dir(args.run)
    artifact = read_json(p / "40-TEST/artifact-manifest.json", {})
    data = {
        "recordedAt": now_iso(),
        "deploymentId": args.deployment_id,
        "environment": args.environment,
        "status": "DEPLOYED" if args.status == "DEPLOYED" else args.status,
        "artifactHash": args.artifact_hash or artifact.get("artifactHash", UNKNOWN),
        "endpoint": args.endpoint or UNKNOWN
    }
    write_json(p / "50-RUNTIME/deployment-evidence.json", data)
    print(json.dumps(data, ensure_ascii=False, indent=2))


def cmd_status(args):
    p = run_dir(args.run)
    files = [x.relative_to(p).as_posix() for x in p.rglob("*") if x.is_file()]
    print(json.dumps({"run": args.run, "files": files, "count": len(files)}, ensure_ascii=False, indent=2))


def cmd_impact(args):
    p = run_dir(args.run)
    data = analyze_changed_files(p / "00-SOURCE", args.changed)
    write_impact(data, p / "90-OUT/IMPACT-ANALYSIS.json")
    print(json.dumps(data, ensure_ascii=False, indent=2))


def cmd_validate(args):
    import subprocess
    cp = subprocess.run([sys.executable, str(ROOT / "tools/validate_orchestration.py"), str(ROOT)], text=True)
    return cp.returncode


def parser() -> argparse.ArgumentParser:
    ap = argparse.ArgumentParser(description="PDMG Reference Architecture Orchestration executable harness")
    sp = ap.add_subparsers(dest="cmd", required=True)
    s = sp.add_parser("init"); s.set_defaults(func=cmd_init)
    s = sp.add_parser("scan-reference"); s.add_argument("--repo", required=True); s.add_argument("--mission", default="Build PDMG reference baseline"); s.set_defaults(func=cmd_scan_reference)
    s = sp.add_parser("create-target-run"); s.add_argument("--repo", required=True); s.add_argument("--reference", required=True); s.add_argument("--projects", nargs="+", required=True); s.add_argument("--mission", default="Validate target conformance"); s.set_defaults(func=cmd_create_target)
    s = sp.add_parser("scan-target"); s.add_argument("--run", required=True); s.add_argument("--reference"); s.add_argument("--projects", nargs="+"); s.set_defaults(func=cmd_scan_target)
    s = sp.add_parser("artifact"); s.add_argument("--run", required=True); s.add_argument("--file", required=True); s.add_argument("--build-id", default=UNKNOWN); s.set_defaults(func=cmd_artifact)
    s = sp.add_parser("import-runtime"); s.add_argument("--run", required=True); s.add_argument("--files", nargs="+", required=True); s.add_argument("--architecture-baseline-id", default=UNKNOWN); s.add_argument("--model-version", default=UNKNOWN); s.add_argument("--source-commit", default=UNKNOWN); s.add_argument("--build-id", default=UNKNOWN); s.add_argument("--artifact-hash", default=UNKNOWN); s.add_argument("--deployment-id", default=UNKNOWN); s.add_argument("--service-id", default=UNKNOWN); s.add_argument("--trace-id", default=UNKNOWN); s.add_argument("--guid", default=UNKNOWN); s.set_defaults(func=cmd_runtime)
    s = sp.add_parser("register-gap"); s.add_argument("--run", required=True); s.add_argument("--gap-id", required=True); s.add_argument("--title", required=True); s.add_argument("--severity", choices=["CRITICAL","HIGH","MEDIUM","LOW"], required=True); s.add_argument("--as-is", required=True); s.add_argument("--to-be", required=True); s.add_argument("--adr-required", action="store_true"); s.set_defaults(func=cmd_register_gap)
    s = sp.add_parser("resolve"); s.add_argument("--run", required=True); s.add_argument("--kind", choices=["drift","gap"], required=True); s.add_argument("--id", required=True); s.add_argument("--status", choices=["RESOLVED","CLOSED","ACCEPTED","EXCEPTION"], required=True); s.add_argument("--comment", required=True); s.set_defaults(func=cmd_resolve)
    s = sp.add_parser("create-adr"); s.add_argument("--run", required=True); s.add_argument("--adr-id", required=True); s.add_argument("--title", required=True); s.add_argument("--problem", required=True); s.add_argument("--context", required=True); s.add_argument("--alternative", action="append"); s.add_argument("--recommendation", default="PENDING HUMAN REVIEW"); s.add_argument("--impact", default="UNKNOWN"); s.add_argument("--risk", default="UNKNOWN"); s.set_defaults(func=cmd_create_adr)
    s = sp.add_parser("require-approval"); s.add_argument("--run", required=True); s.add_argument("--artifact", required=True); s.add_argument("--approval-id", required=True); s.add_argument("--role", required=True); s.add_argument("--reason", required=True); s.set_defaults(func=cmd_require_approval)
    s = sp.add_parser("approve"); s.add_argument("--run", required=True); s.add_argument("--artifact", required=True); s.add_argument("--approval-id", required=True); s.add_argument("--approver", required=True); s.add_argument("--role", required=True); s.add_argument("--decision", choices=["APPROVED", "CONDITIONAL_APPROVAL", "REJECTED"], required=True); s.add_argument("--comment", default=""); s.add_argument("--expiration"); s.set_defaults(func=cmd_approve)
    s = sp.add_parser("evaluate"); s.add_argument("--run", required=True); s.add_argument("--gate", required=True); s.set_defaults(func=cmd_evaluate)
    s = sp.add_parser("release"); s.add_argument("--run", required=True); s.add_argument("--previous"); s.set_defaults(func=cmd_release)
    s = sp.add_parser("schema-validate"); s.add_argument("--data", required=True); s.add_argument("--schema", required=True); s.add_argument("--out"); s.set_defaults(func=cmd_schema_validate)
    s = sp.add_parser("static-check"); s.add_argument("--run", required=True); s.add_argument("--type", choices=["architecture","security"], required=True); s.add_argument("--allow-private-key-project", action="append"); s.set_defaults(func=cmd_static_check)
    s = sp.add_parser("run-check"); s.add_argument("--run", required=True); s.add_argument("--type", choices=["build","test","security","architecture"], required=True); s.add_argument("--cwd", required=True); s.add_argument("command", nargs=argparse.REMAINDER); s.set_defaults(func=cmd_run_check)
    s = sp.add_parser("record-deployment"); s.add_argument("--run", required=True); s.add_argument("--deployment-id", required=True); s.add_argument("--environment", required=True); s.add_argument("--status", default="DEPLOYED"); s.add_argument("--artifact-hash"); s.add_argument("--endpoint"); s.set_defaults(func=cmd_deployment)
    s = sp.add_parser("impact"); s.add_argument("--run", required=True); s.add_argument("--changed", nargs="+", required=True); s.set_defaults(func=cmd_impact)
    s = sp.add_parser("status"); s.add_argument("--run", required=True); s.set_defaults(func=cmd_status)
    s = sp.add_parser("validate"); s.set_defaults(func=cmd_validate)
    return ap


def main() -> int:
    args = parser().parse_args()
    rv = args.func(args)
    return int(rv or 0)

if __name__ == "__main__":
    raise SystemExit(main())
