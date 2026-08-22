from __future__ import annotations

from pathlib import Path
from typing import Any

from .common import now_iso, read_json, write_json, write_text


def _load_list(path: Path) -> list[dict[str, Any]]:
    data = read_json(path, [])
    if isinstance(data, dict):
        return list(data.get("items") or data.get("gaps") or data.get("drifts") or [])
    return list(data or [])


def register_gap(path: Path, gap_id: str, severity: str, as_is: str, to_be: str, title: str, adr_required: bool) -> dict[str, Any]:
    items = _load_list(path)
    gap = {
        "gapId": gap_id,
        "title": title,
        "severity": severity.upper(),
        "status": "OPEN",
        "asIs": as_is,
        "toBe": to_be,
        "adrRequired": bool(adr_required),
        "createdAt": now_iso(),
    }
    items = [x for x in items if x.get("gapId") != gap_id] + [gap]
    write_json(path, items)
    return gap


def resolve_item(path: Path, item_id: str, status: str, comment: str) -> dict[str, Any]:
    items = _load_list(path)
    id_keys = ["gapId", "driftId", "id"]
    found = None
    for item in items:
        if any(item.get(k) == item_id for k in id_keys):
            item["status"] = status.upper()
            item["resolutionComment"] = comment
            item["resolvedAt"] = now_iso()
            found = item
            break
    if found is None:
        raise KeyError(item_id)
    write_json(path, items)
    return found


def create_adr(out: Path, adr_id: str, title: str, problem: str, context: str, alternatives: list[str], recommendation: str, impact: str, risk: str) -> dict[str, Any]:
    data = {
        "adrId": adr_id,
        "title": title,
        "status": "PENDING_HUMAN_APPROVAL",
        "problem": problem,
        "context": context,
        "requirements": [],
        "constraints": [],
        "alternatives": alternatives,
        "comparison": "To be completed from evidence before approval",
        "decision": "PENDING",
        "recommendation": recommendation,
        "rationale": "PENDING_HUMAN_APPROVAL",
        "impact": impact,
        "risk": risk,
        "implementation": "After approval, bind to implementation plan and source changes",
        "test": "Must be covered by G40/RG40 evidence",
        "runtimeEvidence": "Must be covered by G50/RG50 when runtime-relevant",
        "migration": "Define before approval when migration is required",
        "deprecationCondition": "Define for replaced architecture",
        "createdAt": now_iso(),
    }
    write_json(out, data)
    md = [
        f"# ADR {adr_id} — {title}", "", "**Status: PENDING HUMAN APPROVAL**", "",
        "## Problem", "", problem, "", "## Context", "", context, "", "## Alternatives", ""
    ]
    md.extend([f"- {x}" for x in alternatives] or ["- NONE PROVIDED"])
    md += ["", "## Recommendation", "", recommendation, "", "## Impact", "", impact, "", "## Risk", "", risk,
           "", "## Decision", "", "PENDING", "", "## Test", "", data["test"], "", "## Runtime Evidence", "", data["runtimeEvidence"],
           "", "## Migration", "", data["migration"], "", "## Deprecation Condition", "", data["deprecationCondition"]]
    write_text(out.with_suffix('.md'), "\n".join(md))
    return data
