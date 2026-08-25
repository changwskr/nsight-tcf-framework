import json
from pathlib import Path

from nsight_regate import evaluate_regate, validate_adr_register


def base_state():
    return {
        "adrs": [
            {"id": "ADR-SEC-001", "status": "PROPOSED", "priority": "P0", "requires_human_approval": True, "requires_runtime": True},
            {"id": "ADR-TX-001", "status": "APPROVED", "priority": "P0", "requires_human_approval": True, "requires_runtime": False},
        ],
        "runtime_runs": [
            {"id": "RUN-JWT-ROTATE", "status": "OPEN", "evidence_class": None, "runtime_approved": False},
            {"id": "RUN-TIMEOUT", "status": "PASS", "evidence_class": "PRODUCTION_RUNTIME", "runtime_approved": True},
        ],
        "p0_items": [
            {"id": "P0-SEC-001", "status": "IMPLEMENTED_CANDIDATE_BUILD_BLOCKED"},
            {"id": "P0-TX-001", "status": "CLOSED_STATIC"},
        ],
    }


def test_hold_when_p0_adr_not_approved():
    result = evaluate_regate(base_state())
    assert result["g80"] == "HOLD"
    assert "ADR-SEC-001" in result["blocking_adrs"]


def test_hold_when_required_runtime_open_even_if_adr_approved():
    state = base_state()
    state["adrs"][0]["status"] = "APPROVED"
    result = evaluate_regate(state)
    assert result["g80"] == "HOLD"
    assert "RUN-JWT-ROTATE" in result["blocking_runtime_runs"]


def test_runtime_only_counts_when_production_and_runtime_approved():
    state = base_state()
    state["adrs"][0]["status"] = "APPROVED"
    state["runtime_runs"][0].update({"status": "PASS", "evidence_class": "SYNTHETIC", "runtime_approved": True})
    result = evaluate_regate(state)
    assert result["g80"] == "HOLD"
    assert "RUN-JWT-ROTATE" in result["blocking_runtime_runs"]


def test_conditional_pass_when_all_hard_blockers_closed_but_non_p0_open():
    state = base_state()
    state["adrs"][0]["status"] = "APPROVED"
    state["runtime_runs"][0].update({"status": "PASS", "evidence_class": "PRODUCTION_RUNTIME", "runtime_approved": True})
    state["p0_items"][0]["status"] = "CLOSED_RUNTIME"
    state["non_p0_open"] = ["P1-OBS-001"]
    result = evaluate_regate(state)
    assert result["g80"] == "CONDITIONAL_PASS"
    assert result["hg90"] == "WAIT_HUMAN_SIGNOFF"


def test_pass_candidate_when_all_blockers_closed_and_no_non_p0_open():
    state = base_state()
    state["adrs"][0]["status"] = "APPROVED"
    state["runtime_runs"][0].update({"status": "PASS", "evidence_class": "PRODUCTION_RUNTIME", "runtime_approved": True})
    state["p0_items"][0]["status"] = "CLOSED_RUNTIME"
    state["non_p0_open"] = []
    result = evaluate_regate(state)
    assert result["g80"] == "PASS_CANDIDATE"
    assert result["hg90"] == "WAIT_HUMAN_SIGNOFF"


def test_adr_register_rejects_accepted_without_approver_or_date():
    issues = validate_adr_register([{"id": "ADR-X", "status": "APPROVED", "approver": None, "decision_date": None}])
    assert any("approver" in x for x in issues)
    assert any("decision_date" in x for x in issues)


def test_adr_register_rejects_invalid_status():
    issues = validate_adr_register([{"id": "ADR-X", "status": "DONE"}])
    assert any("invalid status" in x for x in issues)


def test_cli_writes_json_result(tmp_path):
    import subprocess, sys
    state = base_state()
    p = tmp_path / "state.json"
    p.write_text(json.dumps(state), encoding="utf-8")
    out = tmp_path / "result.json"
    cp = subprocess.run([sys.executable, str(Path(__file__).parent / "nsight_regate.py"), "--state", str(p), "--output", str(out)], capture_output=True, text=True)
    assert cp.returncode == 0
    data = json.loads(out.read_text(encoding="utf-8"))
    assert data["g80"] == "HOLD"
