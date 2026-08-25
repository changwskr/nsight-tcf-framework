#!/usr/bin/env python3
from __future__ import annotations

import argparse
import json
from pathlib import Path
from typing import Any, Dict, List

FIRST_BATCH = ['RUN-TIMEOUT','RUN-P600','RUN-P1200']
REQUIRED_RUN_FIELDS = ['purpose','preconditions','required_evidence','commands','hard_gates','human_gates']
REQUIRED_COMMANDS = ['preflight','execute','evaluate']


def validate_catalog(catalog: Dict[str, Any]) -> Dict[str, Any]:
    errors: List[str] = []
    if catalog.get('schema_version') != '1.0':
        errors.append('schema_version')
    runs = catalog.get('runs')
    if not isinstance(runs, dict):
        return {'accepted': False, 'run_count': 0, 'errors': ['runs']}
    if list(runs.keys()) != FIRST_BATCH:
        errors.append('run_order_or_membership')
    for run_id in FIRST_BATCH:
        spec = runs.get(run_id)
        if not isinstance(spec, dict):
            errors.append(f'{run_id}:missing')
            continue
        for field in REQUIRED_RUN_FIELDS:
            value = spec.get(field)
            if value is None or value == '' or value == [] or value == {}:
                errors.append(f'{run_id}:{field}')
        commands = spec.get('commands') or {}
        for key in REQUIRED_COMMANDS:
            if not commands.get(key):
                errors.append(f'{run_id}:commands.{key}')
    return {'accepted': not errors, 'run_count': len(runs), 'errors': errors}


def build_execution_plan(catalog: Dict[str, Any]) -> List[str]:
    validation = validate_catalog(catalog)
    if not validation['accepted']:
        raise ValueError('invalid catalog: ' + ', '.join(validation['errors']))
    return list(FIRST_BATCH)


def _missing(facts: Dict[str, Any], keys: List[str]) -> List[str]:
    return [k for k in keys if k not in facts or facts.get(k) is None or (isinstance(facts.get(k), str) and not facts.get(k).strip())]


def evaluate_go_nogo(run_id: str, facts: Dict[str, Any]) -> Dict[str, Any]:
    failures: List[str] = []
    missing: List[str] = []
    warnings: List[str] = []

    if run_id == 'RUN-TIMEOUT':
        required = [
            'rolled_back','late_commit_count','tx_result','client_result','pending_after',
            'active_before','active_after','worker_returned','context_leak_count',
            'human_approvals_complete'
        ]
        missing = _missing(facts, required)
        if not missing:
            if facts['rolled_back'] is not True: failures.append('rolled_back!=true')
            if facts['late_commit_count'] != 0: failures.append('late_commit_count!=0')
            if facts['tx_result'] != 'ROLLBACK': failures.append('tx_result!=ROLLBACK')
            if facts['client_result'] != 'TIMEOUT': failures.append('client_result!=TIMEOUT')
            if facts['pending_after'] not in (0, 0.0): failures.append('pending_after!=0')
            if facts['active_after'] != facts['active_before']: failures.append('pool_active_not_restored')
            if facts['worker_returned'] is not True: failures.append('worker_returned!=true')
            if facts['context_leak_count'] != 0: failures.append('context_leak_count!=0')
    elif run_id in {'RUN-P600','RUN-P1200'}:
        target = 600 if run_id == 'RUN-P600' else 1200
        required = [
            'tps','p95_seconds','error_rate_pct','timeout_rate_pct','cpu_pct',
            'busy_thread_pct','hikari_active_pct','human_approvals_complete'
        ]
        missing = _missing(facts, required)
        if not missing:
            if float(facts['tps']) < target: failures.append(f'tps<{target}')
            if float(facts['p95_seconds']) > 3.0: failures.append('p95_seconds>3.0')
            # Working thresholds are warning-level until G60-C13 and runtime approvals are closed.
            if float(facts['cpu_pct']) > 70.0: warnings.append('cpu_pct>70_working_threshold')
            if float(facts['busy_thread_pct']) > 70.0: warnings.append('busy_thread_pct>70_working_threshold')
            if float(facts['hikari_active_pct']) > 80.0: warnings.append('hikari_active_pct>80_working_threshold')
    else:
        return {'decision':'INVALID','failures':['unsupported_run_id'],'missing':[],'warnings':[]}

    if missing:
        return {'decision':'INCOMPLETE','failures':[],'missing':missing,'warnings':warnings}
    if failures:
        return {'decision':'NO_GO','failures':failures,'missing':[],'warnings':warnings}
    if facts.get('human_approvals_complete') is not True:
        return {'decision':'CONDITIONAL_REVIEW','failures':[],'missing':[],'warnings':warnings}
    return {'decision':'GO_CANDIDATE','failures':[],'missing':[],'warnings':warnings}


def _read_json(path: Path) -> Dict[str, Any]:
    with path.open(encoding='utf-8') as f:
        obj = json.load(f)
    if not isinstance(obj, dict):
        raise ValueError('JSON object required')
    return obj


def main() -> int:
    ap = argparse.ArgumentParser()
    sp = ap.add_subparsers(dest='cmd', required=True)
    p = sp.add_parser('validate-catalog'); p.add_argument('catalog')
    p = sp.add_parser('plan'); p.add_argument('catalog')
    p = sp.add_parser('go-nogo'); p.add_argument('run_id'); p.add_argument('facts')
    args = ap.parse_args()
    if args.cmd == 'validate-catalog':
        out = validate_catalog(_read_json(Path(args.catalog)))
    elif args.cmd == 'plan':
        out = {'plan': build_execution_plan(_read_json(Path(args.catalog)))}
    else:
        out = evaluate_go_nogo(args.run_id, _read_json(Path(args.facts)))
    print(json.dumps(out, ensure_ascii=False, indent=2))
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
