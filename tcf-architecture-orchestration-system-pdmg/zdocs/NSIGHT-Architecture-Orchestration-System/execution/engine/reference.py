from __future__ import annotations

from collections import Counter, defaultdict
from pathlib import Path
from typing import Any

from .common import now_iso, write_json, write_text


def _consensus(values: list[Any]) -> dict[str, Any]:
    normalized = [str(v) for v in values]
    c = Counter(normalized)
    if not c:
        return {"status": "UNKNOWN", "value": "UNKNOWN", "evidence": []}
    value, count = c.most_common(1)[0]
    return {
        "status": "MATCH" if count == len(normalized) else "VARIANT",
        "value": value,
        "counts": dict(c),
    }


def build_reference(scan: dict[str, Any]) -> dict[str, Any]:
    projects = scan.get("projects", [])
    tech = {
        "java": _consensus([p["technology"]["java"] for p in projects]),
        "springBoot": _consensus([p["technology"]["springBoot"] for p in projects]),
        "gradle": _consensus([p["technology"]["gradle"] for p in projects]),
    }
    drifts = []
    for k, v in tech.items():
        if v["status"] != "MATCH":
            drifts.append({
                "driftId": f"REF-TECH-{k.upper()}",
                "type": "REFERENCE_INTERNAL_VARIANT",
                "subject": k,
                "expected": "consensus",
                "actual": v.get("counts", {}),
                "severity": "HIGH" if k in {"java", "springBoot"} else "MEDIUM",
                "status": "CANDIDATE",
            })
    package_roots = {}
    for p in projects:
        roots = Counter()
        for j in p["java"]:
            pkg = j.get("package", "UNKNOWN")
            if pkg != "UNKNOWN":
                roots[".".join(pkg.split(".")[:3])] += 1
        package_roots[p["name"]] = roots.most_common(1)[0][0] if roots else "UNKNOWN"
    service_dupes = []
    seen: dict[str, list[str]] = defaultdict(list)
    for p in projects:
        for sid in p["serviceIdOwners"]:
            seen[sid].append(p["name"])
        for sid, owners in p.get("duplicateServiceIds", {}).items():
            service_dupes.append({"serviceId": sid, "project": p["name"], "handlers": owners})
    cross_dupes = {sid: owners for sid, owners in seen.items() if len(owners) > 1}
    if cross_dupes:
        drifts.append({
            "driftId": "REF-SERVICEID-CROSS-PROJECT",
            "type": "REFERENCE_INTERNAL_DRIFT",
            "subject": "ServiceId uniqueness",
            "expected": "unique within reference scope unless approved variant",
            "actual": cross_dupes,
            "severity": "HIGH",
            "status": "CANDIDATE",
        })
    rules = [
        {
            "ruleId": "PDMG-R-JAVA-VERSION",
            "category": "BUILD",
            "title": "Java version follows verified PDMG reference",
            "status": "CANDIDATE",
            "evaluator": "technology_equal",
            "field": "java",
            "expected": tech["java"]["value"],
            "severity": "HIGH",
        },
        {
            "ruleId": "PDMG-R-SPRING-BOOT-VERSION",
            "category": "BUILD",
            "title": "Spring Boot version follows verified PDMG reference",
            "status": "CANDIDATE",
            "evaluator": "technology_equal",
            "field": "springBoot",
            "expected": tech["springBoot"]["value"],
            "severity": "HIGH",
        },
        {
            "ruleId": "PDMG-R-SERVICEID-UNIQUE",
            "category": "SERVICE_ID",
            "title": "ServiceId has no duplicate Handler owner in a project",
            "status": "CANDIDATE",
            "evaluator": "duplicate_serviceid_zero",
            "threshold": 0,
            "severity": "HIGH",
        },
        {
            "ruleId": "PDMG-R-MAPPER-SQLID-UNIQUE",
            "category": "DATA",
            "title": "Mapper namespace + SQL ID is unique",
            "status": "CANDIDATE",
            "evaluator": "duplicate_sqlid_zero",
            "threshold": 0,
            "severity": "HIGH",
        },
        {
            "ruleId": "PDMG-R-HANDLER-NO-DAO",
            "category": "LAYER",
            "title": "Handler must not import DAO directly",
            "status": "CANDIDATE",
            "evaluator": "handler_no_dao_import",
            "threshold": 0,
            "severity": "HIGH",
        },
        {
            "ruleId": "PDMG-R-JWT-PRIVATE-KEY-BOUNDARY",
            "category": "SECURITY",
            "title": "Private-key usage outside JWT issuer reference requires review",
            "status": "CANDIDATE",
            "evaluator": "private_key_boundary",
            "allowedProjects": ["pdmg-jwt", "pdmg-fw"],
            "threshold": 0,
            "severity": "CRITICAL",
        },
    ]
    transaction_evidence = []
    for p in projects:
        for j in p["java"]:
            if j["usesTransactionTemplate"] or j["usesTransactional"]:
                transaction_evidence.append({
                    "project": p["name"], "class": j["class"], "path": j["path"],
                    "transactionTemplate": j["usesTransactionTemplate"],
                    "transactional": j["usesTransactional"],
                })
    return {
        "generatedAt": now_iso(),
        "sourceBaseline": {
            "repositoryRoot": scan.get("repositoryRoot"),
            "branch": scan.get("git", {}).get("branch", "UNKNOWN"),
            "commit": scan.get("git", {}).get("commit", "UNKNOWN"),
        },
        "referenceProjects": [p["name"] for p in projects],
        "technologyConsensus": tech,
        "packageRoots": package_roots,
        "transactionEvidence": transaction_evidence,
        "rules": rules,
        "internalDrifts": drifts,
        "rawDuplicateServiceIds": service_dupes,
        "promotionStatus": "DRAFT",
    }


def write_reference_outputs(reference: dict[str, Any], out: Path) -> None:
    write_json(out / "reference-baseline-draft.json", reference)
    write_json(out / "reference-rules.json", reference["rules"])
    write_json(out / "reference-internal-drift.json", reference["internalDrifts"])
    md = [
        "# PDMG Reference Baseline Draft",
        "",
        "> RAW Source에서 자동 추출된 결과입니다. 자동으로 REFERENCE로 승격되지 않습니다.",
        "",
        f"- Promotion status: **{reference['promotionStatus']}**",
        f"- Reference projects: `{', '.join(reference['referenceProjects'])}`",
        "",
        "## Technology Consensus",
        "",
        "| Item | Status | Value |",
        "|---|---|---|",
    ]
    for k, v in reference["technologyConsensus"].items():
        md.append(f"| {k} | {v['status']} | `{v['value']}` |")
    md += ["", "## Candidate Rules", ""]
    for r in reference["rules"]:
        md.append(f"- `{r['ruleId']}` [{r['status']}] {r['title']}")
    md += ["", "## Internal Drift", ""]
    if reference["internalDrifts"]:
        for d in reference["internalDrifts"]:
            md.append(f"- `{d['driftId']}` {d['severity']} — {d['subject']}")
    else:
        md.append("- NONE")
    write_text(out / "PDMG-REFERENCE-BASELINE-DRAFT.md", "\n".join(md))
