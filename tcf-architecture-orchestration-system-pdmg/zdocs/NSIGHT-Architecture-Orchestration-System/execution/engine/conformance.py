from __future__ import annotations

from pathlib import Path
from typing import Any

from .common import now_iso, write_json, write_text


def _violations_for_rule(rule: dict[str, Any], target_scan: dict[str, Any]) -> list[dict[str, Any]]:
    findings = []
    evaluator = rule.get("evaluator")
    for p in target_scan.get("projects", []):
        if evaluator == "technology_equal":
            field = rule["field"]
            actual = p["technology"].get(field)
            expected = rule.get("expected")
            if str(actual) != str(expected):
                findings.append({"project": p["name"], "expected": expected, "actual": actual})
        elif evaluator == "duplicate_serviceid_zero":
            if p.get("duplicateServiceIds"):
                findings.append({"project": p["name"], "actual": p["duplicateServiceIds"]})
        elif evaluator == "duplicate_sqlid_zero":
            if p.get("duplicateSqlIds"):
                findings.append({"project": p["name"], "actual": p["duplicateSqlIds"]})
        elif evaluator == "handler_no_dao_import":
            for j in p.get("java", []):
                if j.get("isHandler") and any(".dao." in x.lower() or x.lower().endswith(".dao") for x in j.get("imports", [])):
                    findings.append({"project": p["name"], "class": j["class"], "path": j["path"]})
        elif evaluator == "private_key_boundary":
            allowed = set(rule.get("allowedProjects", []))
            if p["name"] in allowed:
                continue
            for j in p.get("java", []):
                if j.get("usesPrivateKey"):
                    findings.append({"project": p["name"], "class": j["class"], "path": j["path"]})
    return findings


def compare(reference: dict[str, Any], target_scan: dict[str, Any]) -> dict[str, Any]:
    results = []
    for rule in reference.get("rules", []):
        violations = _violations_for_rule(rule, target_scan)
        results.append({
            "ruleId": rule["ruleId"],
            "title": rule["title"],
            "referenceRuleStatus": rule.get("status", "CANDIDATE"),
            "severity": rule.get("severity", "MEDIUM"),
            "classification": "GAP" if violations else "MATCH",
            "violations": violations,
            "evaluator": rule.get("evaluator"),
        })
    return {
        "generatedAt": now_iso(),
        "referencePromotionStatus": reference.get("promotionStatus", "DRAFT"),
        "targetRepository": target_scan.get("repositoryRoot"),
        "results": results,
        "summary": {
            "MATCH": sum(1 for x in results if x["classification"] == "MATCH"),
            "GAP": sum(1 for x in results if x["classification"] == "GAP"),
            "CRITICAL_GAP": sum(1 for x in results if x["classification"] == "GAP" and x["severity"] == "CRITICAL"),
        },
    }


def write_conformance_outputs(result: dict[str, Any], out: Path) -> None:
    write_json(out / "conformance-result.json", result)
    drift = []
    for r in result["results"]:
        if r["classification"] == "GAP":
            drift.append({
                "driftId": f"CONF-{r['ruleId']}",
                "type": "REFERENCE_TARGET",
                "expected": r["title"],
                "actual": r["violations"],
                "severity": r["severity"],
                "status": "CANDIDATE",
                "sourceEvidence": "conformance-result.json",
                "runtimeEvidence": "UNKNOWN",
            })
    write_json(out / "DRIFT-REGISTER.json", drift)
    md = [
        "# Target Conformance Report",
        "",
        f"- Reference promotion status: `{result['referencePromotionStatus']}`",
        f"- Target: `{result['targetRepository']}`",
        f"- MATCH: **{result['summary']['MATCH']}**",
        f"- GAP: **{result['summary']['GAP']}**",
        f"- CRITICAL GAP: **{result['summary']['CRITICAL_GAP']}**",
        "",
        "| Rule | Severity | Classification | Violation Count |",
        "|---|---|---|---:|",
    ]
    for r in result["results"]:
        md.append(f"| {r['ruleId']} | {r['severity']} | {r['classification']} | {len(r['violations'])} |")
    write_text(out / "CONFORMANCE-REPORT.md", "\n".join(md))
