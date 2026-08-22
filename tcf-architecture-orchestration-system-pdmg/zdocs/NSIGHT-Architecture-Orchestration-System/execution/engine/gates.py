from __future__ import annotations

import operator
from pathlib import Path
from typing import Any

from .approval import validate_approval, validate_approval_register
from .common import UNKNOWN, now_iso, read_json, write_json, write_text

OPS = {
    "EQ": operator.eq,
    "NE": operator.ne,
    "LTE": operator.le,
    "GTE": operator.ge,
    "LT": operator.lt,
    "GT": operator.gt,
}


def _count_open_critical(path: Path) -> int:
    data = read_json(path, [])
    if isinstance(data, dict):
        data = data.get("items") or data.get("drifts") or data.get("gaps") or []
    total = 0
    for x in data or []:
        sev = str(x.get("severity", "")).upper()
        status = str(x.get("status", "OPEN")).upper()
        if sev == "CRITICAL" and status not in {"RESOLVED", "CLOSED", "ACCEPTED", "SUPERSEDED"}:
            total += 1
    return total


def eval_rule(rule: dict[str, Any], run_dir: Path) -> dict[str, Any]:
    kind = rule["evaluator"]
    evidence = run_dir / rule.get("path", "") if rule.get("path") else None
    measured: Any = None
    threshold: Any = rule.get("threshold")
    op = rule.get("operator", "EQ")
    detail = ""
    if kind == "file_exists":
        measured = bool(evidence and evidence.exists())
        threshold = True
    elif kind == "json_field_equals":
        data = read_json(evidence, {}) if evidence else {}
        current = data
        for part in rule.get("field", "").split("."):
            if not part:
                continue
            current = current.get(part, UNKNOWN) if isinstance(current, dict) else UNKNOWN
        measured = current
        threshold = rule.get("threshold")
    elif kind == "runtime_present":
        data = read_json(evidence, {}) if evidence else {}
        measured = bool(data.get("runtimeEvidencePresent"))
        threshold = True
    elif kind == "artifact_hash_present":
        data = read_json(evidence, {}) if evidence else {}
        measured = data.get("artifactHash", UNKNOWN) not in {None, "", UNKNOWN}
        threshold = True
    elif kind == "critical_open_zero":
        measured = _count_open_critical(evidence) if evidence else 999999
        threshold = 0
    elif kind == "approval_valid":
        if not evidence or not evidence.exists():
            measured = False
        else:
            measured = validate_approval(evidence)["valid"]
        threshold = True
    elif kind == "approval_register_valid":
        approvals_dir = run_dir / rule.get("approvalsDir", "80-APPROVAL")
        summary = validate_approval_register(evidence, approvals_dir, int(rule.get("minRequired", 1))) if evidence else {"valid": False}
        measured = bool(summary.get("valid"))
        threshold = True
        detail = str(summary)
    elif kind == "json_fields_equal":
        left_path = run_dir / rule["leftPath"]
        right_path = run_dir / rule["rightPath"]
        left = read_json(left_path, {})
        right = read_json(right_path, {})
        def pick(data, field):
            cur = data
            for part in field.split('.'):
                cur = cur.get(part, UNKNOWN) if isinstance(cur, dict) else UNKNOWN
            return cur
        lv = pick(left, rule["leftField"])
        rv = pick(right, rule["rightField"])
        measured = (lv == rv and lv not in {UNKNOWN, None, ''})
        threshold = True
        detail = f"left={lv}; right={rv}"
    elif kind == "count_at_least":
        data = read_json(evidence, []) if evidence else []
        if isinstance(data, dict):
            data = data.get(rule.get("listField", "items"), [])
        measured = len(data or [])
        op = "GTE"
    else:
        measured = UNKNOWN
        detail = f"unsupported evaluator: {kind}"
    fn = OPS.get(op, operator.eq)
    try:
        passed = fn(measured, threshold)
    except Exception:
        passed = False
    return {
        "ruleId": rule["ruleId"],
        "evaluator": kind,
        "measuredValue": measured,
        "threshold": threshold,
        "operator": op,
        "result": "PASS" if passed else "FAIL",
        "hard": bool(rule.get("hard", False)),
        "evidence": str(evidence) if evidence else None,
        "detail": detail,
    }


def evaluate_gate(gate: dict[str, Any], run_dir: Path) -> dict[str, Any]:
    results = [eval_rule(r, run_dir) for r in gate.get("rules", [])]
    hard_fail = any(r["hard"] and r["result"] == "FAIL" for r in results)
    any_fail = any(r["result"] == "FAIL" for r in results)
    if hard_fail:
        decision = "HOLD"
    elif any_fail:
        decision = "CONDITIONAL_PASS" if gate.get("allowConditional", True) else "HOLD"
    else:
        decision = "PASS"
    return {
        "gateId": gate["gateId"],
        "evaluatedAt": now_iso(),
        "decision": decision,
        "rules": results,
        "manualOverrideAllowed": False,
    }


def write_gate_result(result: dict[str, Any], out_json: Path) -> None:
    write_json(out_json, result)
    md = [
        f"# Gate Result — {result['gateId']}", "",
        f"**Decision: {result['decision']}**", "",
        "| Rule | Evaluator | Measured | Threshold | Result | Hard |",
        "|---|---|---|---|---|---|",
    ]
    for r in result["rules"]:
        md.append(f"| {r['ruleId']} | {r['evaluator']} | `{r['measuredValue']}` | `{r['threshold']}` | {r['result']} | {r['hard']} |")
    write_text(out_json.with_suffix(".md"), "\n".join(md))
