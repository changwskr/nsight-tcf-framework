from __future__ import annotations

from datetime import datetime
from pathlib import Path
from typing import Any

from .common import now_iso, sha256_file, write_json


def create_approval(artifact: Path, approval_id: str, approver: str, role: str, decision: str, comment: str, expiration: str | None, out: Path) -> dict[str, Any]:
    if not artifact.exists() or not artifact.is_file():
        raise FileNotFoundError(str(artifact))
    data = {
        "approvalId": approval_id,
        "artifact": str(artifact.resolve()),
        "artifactId": artifact.stem,
        "artifactHash": sha256_file(artifact),
        "approver": approver,
        "role": role,
        "decision": decision.upper(),
        "approvedAt": now_iso(),
        "comment": comment,
        "expiration": expiration,
    }
    write_json(out, data)
    return data


def validate_approval(path: Path) -> dict[str, Any]:
    import json
    data = json.loads(path.read_text(encoding="utf-8"))
    artifact = Path(data["artifact"])
    hash_match = artifact.exists() and artifact.is_file() and sha256_file(artifact) == data.get("artifactHash")
    not_expired = True
    if data.get("expiration"):
        try:
            exp = datetime.fromisoformat(data["expiration"])
            now = datetime.now(exp.tzinfo) if exp.tzinfo else datetime.now()
            not_expired = now <= exp
        except Exception:
            not_expired = False
    decision_ok = data.get("decision") in {"APPROVED", "CONDITIONAL_APPROVAL"}
    return {
        "approvalId": data.get("approvalId"),
        "valid": bool(hash_match and not_expired and decision_ok),
        "hashMatch": hash_match,
        "notExpired": not_expired,
        "decisionAccepted": decision_ok,
        "role": data.get("role"),
    }


def add_required_approval(register_path: Path, approval_id: str, artifact: Path, role: str, reason: str) -> dict[str, Any]:
    import json
    if not artifact.exists() or not artifact.is_file():
        raise FileNotFoundError(str(artifact))
    data = {"required": []}
    if register_path.exists():
        data = json.loads(register_path.read_text(encoding="utf-8"))
    item = {
        "approvalId": approval_id,
        "artifact": str(artifact.resolve()),
        "artifactHash": sha256_file(artifact),
        "requiredRole": role,
        "reason": reason,
    }
    data["required"] = [x for x in data.get("required", []) if x.get("approvalId") != approval_id] + [item]
    write_json(register_path, data)
    return item


def validate_approval_register(register_path: Path, approvals_dir: Path, min_required: int = 1) -> dict[str, Any]:
    import json
    if not register_path.exists():
        return {"valid": False, "requiredCount": 0, "validCount": 0, "items": [], "reason": "required approval register missing"}
    data = json.loads(register_path.read_text(encoding="utf-8"))
    required = data.get("required", [])
    items = []
    for req in required:
        ap = approvals_dir / f"{req['approvalId']}.json"
        if not ap.exists():
            items.append({"approvalId": req["approvalId"], "valid": False, "reason": "approval object missing"})
            continue
        v = validate_approval(ap)
        approval_data = json.loads(ap.read_text(encoding="utf-8"))
        request_hash_match = approval_data.get("artifactHash") == req.get("artifactHash")
        role_match = approval_data.get("role") == req.get("requiredRole")
        v["requestHashMatch"] = request_hash_match
        v["roleMatch"] = role_match
        v["valid"] = bool(v["valid"] and request_hash_match and role_match)
        items.append(v)
    valid_count = sum(1 for x in items if x.get("valid"))
    overall = len(required) >= min_required and valid_count == len(required)
    return {"valid": overall, "requiredCount": len(required), "validCount": valid_count, "items": items}
