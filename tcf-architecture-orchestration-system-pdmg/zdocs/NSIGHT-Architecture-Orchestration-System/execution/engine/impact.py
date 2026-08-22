from __future__ import annotations

from pathlib import Path
from typing import Any

from .common import now_iso, read_json, write_json


def analyze_changed_files(source_dir: Path, changed_files: list[str]) -> dict[str, Any]:
    source_inventory = read_json(source_dir / "source-inventory.json", []) or []
    traces = read_json(source_dir / "traceability.json", []) or []
    service_index = read_json(source_dir / "serviceid-index.json", []) or []

    changed_norm = [x.replace('\\', '/') for x in changed_files]
    changed_classes = set()
    changed_projects = set()
    for row in source_inventory:
        path = str(row.get("path", "")).replace('\\', '/')
        if any(path == c or path.endswith(c) or c.endswith(path) for c in changed_norm):
            changed_classes.add(row.get("class"))
            if path:
                changed_projects.add(path.split('/')[0])

    affected = set()
    evidence = []
    for tr in traces:
        classes = {x.get("class") for x in tr.get("components", [])}
        hit = sorted(x for x in changed_classes if x in classes)
        if hit:
            sid = tr.get("serviceId")
            if sid:
                affected.add(sid)
                evidence.append({"serviceId": sid, "changedClasses": hit, "project": tr.get("project")})

    # Conservative fallback: when changed file belongs to a scanned project but the static graph cannot
    # connect it to a ServiceId, mark every ServiceId in that project as potentially affected.
    fallback = False
    if not affected and changed_projects:
        fallback = True
        for row in service_index:
            if row.get("project") in changed_projects and row.get("serviceId"):
                affected.add(row["serviceId"])

    if changed_files and not affected:
        strategy = "BROAD"
    elif fallback:
        strategy = "BROAD"
    elif affected:
        strategy = "INCREMENTAL"
    else:
        strategy = "NO_CHANGE"

    return {
        "generatedAt": now_iso(),
        "changedFiles": changed_files,
        "changedClasses": sorted(x for x in changed_classes if x),
        "affectedServiceIds": sorted(affected),
        "traceEvidence": evidence,
        "fallbackBroadByProject": fallback,
        "validationStrategy": strategy,
    }


def write_impact(data: dict[str, Any], out: Path) -> None:
    write_json(out, data)
