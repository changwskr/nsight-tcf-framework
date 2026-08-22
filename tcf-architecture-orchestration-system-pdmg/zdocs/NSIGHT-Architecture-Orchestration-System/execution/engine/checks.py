from __future__ import annotations

from pathlib import Path
from typing import Any

from .common import read_json, now_iso, write_json, write_text


def architecture_check(source_baseline_path: Path) -> dict[str, Any]:
    scan = read_json(source_baseline_path, {})
    violations = []
    for p in scan.get("projects", []):
        for sid, owners in p.get("duplicateServiceIds", {}).items():
            violations.append({"ruleId":"PDMG-R-SERVICEID-UNIQUE","project":p["name"],"serviceId":sid,"owners":owners})
        for sqlid in p.get("duplicateSqlIds", []):
            violations.append({"ruleId":"PDMG-R-MAPPER-SQLID-UNIQUE","project":p["name"],"sqlId":sqlid})
        for j in p.get("java", []):
            if j.get("isHandler") and any(".dao." in x.lower() or x.lower().endswith(".dao") for x in j.get("imports", [])):
                violations.append({"ruleId":"PDMG-R-HANDLER-NO-DAO","project":p["name"],"class":j["class"],"path":j["path"]})
    return {
        "label":"architecture",
        "executedAt":now_iso(),
        "exitCode":0 if not violations else 1,
        "result":"PASS" if not violations else "FAIL",
        "measuredValue":len(violations),
        "threshold":0,
        "violations":violations,
    }


def security_check(source_baseline_path: Path, allowed_private_key_projects: list[str]) -> dict[str, Any]:
    scan = read_json(source_baseline_path, {})
    allowed = set(allowed_private_key_projects)
    violations = []
    for p in scan.get("projects", []):
        if p["name"] in allowed:
            continue
        for j in p.get("java", []):
            if j.get("usesPrivateKey"):
                violations.append({"ruleId":"PDMG-R-JWT-PRIVATE-KEY-BOUNDARY","project":p["name"],"class":j["class"],"path":j["path"]})
    return {
        "label":"security",
        "executedAt":now_iso(),
        "exitCode":0 if not violations else 1,
        "result":"PASS" if not violations else "FAIL",
        "measuredValue":len(violations),
        "threshold":0,
        "allowedPrivateKeyProjects":sorted(allowed),
        "violations":violations,
    }


def write_check(data: dict[str, Any], out: Path) -> None:
    write_json(out, data)
    lines=[
        f"# Static Check — {data['label']}", "",
        f"- Result: **{data['result']}**", f"- Measured: `{data['measuredValue']}`", f"- Threshold: `{data['threshold']}`", "",
        "## Violations", ""
    ]
    if data.get("violations"):
        for v in data["violations"]:
            lines.append(f"- `{v.get('ruleId')}` — `{v.get('project','')}` — `{v.get('class') or v.get('serviceId') or v.get('sqlId')}`")
    else:
        lines.append("- NONE")
    write_text(out.with_suffix('.md'), "\n".join(lines))
