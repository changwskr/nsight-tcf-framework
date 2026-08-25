from __future__ import annotations

VALID_ADR_STATUSES = {"DRAFT", "PROPOSED", "APPROVED", "REJECTED", "SUPERSEDED"}
CLOSED_P0_STATUSES = {"CLOSED_STATIC", "CLOSED_RUNTIME", "CLOSED_APPROVED"}


def validate_adr_register(adrs):
    issues = []
    seen = set()
    for adr in adrs:
        aid = adr.get("id")
        if not aid:
            issues.append("ADR missing id")
            continue
        if aid in seen:
            issues.append(f"{aid}: duplicate id")
        seen.add(aid)
        status = adr.get("status")
        if status not in VALID_ADR_STATUSES:
            issues.append(f"{aid}: invalid status {status!r}")
        if status == "APPROVED":
            if not adr.get("approver"):
                issues.append(f"{aid}: APPROVED requires approver")
            if not adr.get("decision_date"):
                issues.append(f"{aid}: APPROVED requires decision_date")
    return issues


def _runtime_is_approved(run):
    return (
        run.get("status") == "PASS"
        and run.get("evidence_class") == "PRODUCTION_RUNTIME"
        and run.get("runtime_approved") is True
    )


def evaluate_regate(state):
    adrs = state.get("adrs", [])
    runtime_runs = state.get("runtime_runs", [])
    p0_items = state.get("p0_items", [])

    blocking_adrs = sorted(
        adr.get("id") for adr in adrs
        if adr.get("priority") == "P0"
        and adr.get("requires_human_approval", True)
        and adr.get("status") != "APPROVED"
    )
    blocking_runtime_runs = sorted(
        run.get("id") for run in runtime_runs if not _runtime_is_approved(run)
    )
    blocking_p0_items = sorted(
        item.get("id") for item in p0_items
        if item.get("status") not in CLOSED_P0_STATUSES
    )

    hard_blockers = blocking_adrs + blocking_runtime_runs + blocking_p0_items
    non_p0_open = list(state.get("non_p0_open", []))

    if hard_blockers:
        g80 = "HOLD"
        hg90 = "HOLD"
    elif non_p0_open:
        g80 = "CONDITIONAL_PASS"
        hg90 = "WAIT_HUMAN_SIGNOFF"
    else:
        g80 = "PASS_CANDIDATE"
        hg90 = "WAIT_HUMAN_SIGNOFF"

    return {
        "g80": g80,
        "hg90": hg90,
        "blocking_adrs": blocking_adrs,
        "blocking_runtime_runs": blocking_runtime_runs,
        "blocking_p0_items": blocking_p0_items,
        "non_p0_open": non_p0_open,
        "hard_blocker_count": len(hard_blockers),
    }


def _main():
    import argparse, json
    from pathlib import Path
    parser = argparse.ArgumentParser(description="NSIGHT G80/HG90 re-gate evaluator")
    parser.add_argument("--state", required=True)
    parser.add_argument("--output", required=False)
    args = parser.parse_args()
    state = json.loads(Path(args.state).read_text(encoding="utf-8"))
    result = evaluate_regate(state)
    payload = json.dumps(result, ensure_ascii=False, indent=2)
    if args.output:
        Path(args.output).write_text(payload + "\n", encoding="utf-8")
    else:
        print(payload)


if __name__ == "__main__":
    _main()
