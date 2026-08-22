#!/usr/bin/env python3
"""NSIGHT runtime evidence bundle validator/evaluator.

This tool does not execute load/failover tests itself. It validates the identity,
minimum evidence set, and machine-readable pass/fail facts for completed runs.
Synthetic/reference evidence is never promoted to runtime-approved status.
"""
from __future__ import annotations

import argparse
import json
import re
from pathlib import Path
from typing import Any, Dict, List

RUN_CATALOG: Dict[str, Dict[str, Any]] = {
    'RUN-TIMEOUT': {
        'purpose': 'Timeout rollback / late commit / pool & context cleanup',
        'required': ['db/before-after.json','logs/transaction.json','metrics/pool.json','metrics/thread.json'],
    },
    'RUN-P600': {
        'purpose': 'General peak 600 TPS',
        'target_tps': 600,
        'required': ['metrics/summary.json','logs/run.log'],
    },
    'RUN-P1200': {
        'purpose': 'Design peak 1,200 TPS',
        'target_tps': 1200,
        'required': ['metrics/summary.json','logs/run.log'],
    },
    'RUN-S1800': {
        'purpose': 'Stress 1,800 TPS and saturation characterization',
        'target_tps': 1800,
        'required': ['metrics/summary.json','logs/run.log','db/integrity.json'],
    },
    'RUN-HIKARI': {
        'purpose': 'Hikari pool pressure and DB session ceiling',
        'required': ['metrics/pool.json','metrics/db-session.json'],
    },
    'RUN-SLOWSQL': {
        'purpose': 'Slow SQL query timeout < TX timeout and connection return',
        'required': ['metrics/slow-sql.json','logs/transaction.json','metrics/pool.json'],
    },
    'RUN-N1': {
        'purpose': 'AP N-1 at design peak',
        'required': ['metrics/summary.json','logs/routing.log','logs/failover.log'],
    },
    'RUN-SESSION': {
        'purpose': 'Session failover/re-authentication policy',
        'required': ['logs/session.log','logs/l4.log'],
    },
    'RUN-CF': {
        'purpose': 'Center failover/failback RTO/RPO',
        'required': ['logs/failover.log','metrics/rto-rpo.json'],
    },
    'RUN-TRACE': {
        'purpose': 'GUID+ServiceId end-to-end traceability',
        'required': ['logs/e2e-trace.json'],
    },
    'RUN-ROLLING': {
        'purpose': 'Rolling deployment residual capacity / health',
        'required': ['logs/deploy.log','logs/health.log','metrics/summary.json'],
    },
    'RUN-JWT-ROTATE': {
        'purpose': 'JWT active/previous kid grace, multi-node/restart verification',
        'required': ['logs/jwt-rotation.json','logs/jwks.json','logs/key-audit.log'],
    },
}

REQUIRED_IDENTITY = [
    'schema_version','run_id','timestamp','environment','evidence_class','synthetic',
    'git_commit','artifact_version','config_version','service_id','guid','hostname',
    'tomcat_jvm_instance','db_target',
]

HASH40 = re.compile(r'^[0-9a-fA-F]{40}$')
GUID = re.compile(r'^[0-9a-fA-F-]{36}$')


def _unknown(v: Any) -> bool:
    return v is None or str(v).strip() in {'', 'UNKNOWN', 'N/A?'}


def validate_run_manifest(m: Dict[str, Any]) -> Dict[str, Any]:
    missing: List[str] = []
    errors: List[str] = []
    for k in REQUIRED_IDENTITY:
        if k not in m or _unknown(m.get(k)):
            missing.append(k)
    if m.get('run_id') not in RUN_CATALOG:
        errors.append('unknown_run_id')
    if m.get('git_commit') and not HASH40.match(str(m.get('git_commit'))):
        errors.append('invalid_git_commit')
    if m.get('guid') and not GUID.match(str(m.get('guid'))):
        errors.append('invalid_guid')
    if m.get('hostname') == 'UNKNOWN':
        if 'hostname' not in missing:
            missing.append('hostname')
    accepted = not missing and not errors
    return {
        'accepted': accepted,
        'missing_or_invalid': sorted(set(missing)),
        'errors': errors,
    }


def _read_json(path: Path) -> Dict[str, Any]:
    with path.open(encoding='utf-8') as f:
        obj = json.load(f)
    if not isinstance(obj, dict):
        raise ValueError(f'JSON object required: {path}')
    return obj


def _evidence_mode(manifest: Dict[str, Any]) -> str:
    if manifest.get('synthetic') is True:
        return 'SYNTHETIC_ONLY'
    if manifest.get('evidence_class') != 'PRODUCTION_RUNTIME':
        return 'REFERENCE_ONLY'
    return 'RUNTIME'


def _evaluate_capacity(summary: Dict[str, Any], target_tps: int) -> List[str]:
    failures: List[str] = []
    try:
        if float(summary.get('tps', -1)) < target_tps:
            failures.append(f'tps<{target_tps}')
    except Exception:
        failures.append('invalid_tps')
    # p95 <= 3s is an approved NFR baseline. Error/timeout percentage limits are
    # intentionally not hard-coded because their approval threshold remains OPEN.
    try:
        if float(summary.get('p95_seconds', 999)) > 3.0:
            failures.append('p95_seconds>3.0')
    except Exception:
        failures.append('invalid_p95_seconds')
    required_metrics = [
        'error_rate_pct','timeout_rate_pct','cpu_pct','busy_thread_pct','hikari_active_pct'
    ]
    for k in required_metrics:
        if k not in summary:
            failures.append(f'missing_metric:{k}')
    return failures


def _evaluate_timeout(bundle: Path) -> List[str]:
    failures: List[str] = []
    db = _read_json(bundle / 'db/before-after.json')
    tx = _read_json(bundle / 'logs/transaction.json')
    pool = _read_json(bundle / 'metrics/pool.json')
    thread = _read_json(bundle / 'metrics/thread.json')
    if db.get('rolled_back') is not True:
        failures.append('rolled_back!=true')
    if db.get('late_commit_count') != 0:
        failures.append('late_commit_count!=0')
    if tx.get('tx_result') != 'ROLLBACK':
        failures.append('tx_result!=ROLLBACK')
    if tx.get('client_result') != 'TIMEOUT':
        failures.append('client_result!=TIMEOUT')
    if pool.get('pending_after') not in (0, 0.0):
        failures.append('pending_after!=0')
    if pool.get('active_after') != pool.get('active_before'):
        failures.append('pool_active_not_restored')
    if thread.get('worker_returned') is not True:
        failures.append('worker_returned!=true')
    if thread.get('context_leak_count') != 0:
        failures.append('context_leak_count!=0')
    return failures


def _evaluate_special(bundle: Path, run_id: str) -> List[str]:
    failures: List[str] = []
    if run_id == 'RUN-TIMEOUT':
        return _evaluate_timeout(bundle)
    if run_id in {'RUN-P600','RUN-P1200'}:
        return _evaluate_capacity(_read_json(bundle / 'metrics/summary.json'), RUN_CATALOG[run_id]['target_tps'])
    if run_id == 'RUN-S1800':
        summary = _read_json(bundle / 'metrics/summary.json')
        # Stress is for saturation characterization, not an implicit 3s SLA pass.
        if float(summary.get('tps', -1)) < 1800:
            failures.append('tps<1800')
        integrity = _read_json(bundle / 'db/integrity.json')
        if integrity.get('data_consistent') is not True:
            failures.append('data_consistent!=true')
    elif run_id == 'RUN-SLOWSQL':
        slow = _read_json(bundle / 'metrics/slow-sql.json')
        if slow.get('query_timeout_lt_tx_timeout') is not True:
            failures.append('query_timeout_lt_tx_timeout!=true')
        if slow.get('connection_returned') is not True:
            failures.append('connection_returned!=true')
    elif run_id == 'RUN-TRACE':
        trace = _read_json(bundle / 'logs/e2e-trace.json')
        for k in ['guid','service_id','apache','tomcat_jvm','tcf','sql_or_external']:
            if not trace.get(k): failures.append(f'trace_missing:{k}')
    elif run_id == 'RUN-JWT-ROTATE':
        rot = _read_json(bundle / 'logs/jwt-rotation.json')
        if rot.get('old_kid_valid_during_grace') is not True:
            failures.append('old_kid_grace_failed')
        if rot.get('new_kid_valid') is not True:
            failures.append('new_kid_failed')
        if rot.get('restart_validation_ok') is not True:
            failures.append('restart_validation_failed')
    return failures


def evaluate_bundle(bundle: Path | str) -> Dict[str, Any]:
    bundle = Path(bundle)
    manifest_path = bundle / 'run-manifest.json'
    if not manifest_path.exists():
        return {'status':'INCOMPLETE','runtime_approved':False,'missing_evidence':['run-manifest.json'],'failures':[]}
    try:
        manifest = _read_json(manifest_path)
    except Exception as e:
        return {'status':'INVALID','runtime_approved':False,'missing_evidence':[],'failures':[f'manifest_parse:{e}']}
    validation = validate_run_manifest(manifest)
    if not validation['accepted']:
        return {
            'status':'INVALID','runtime_approved':False,'missing_evidence':[],
            'failures': validation['errors'] + [f'identity:{x}' for x in validation['missing_or_invalid']],
            'manifest_validation': validation,
        }
    run_id = manifest['run_id']
    required = RUN_CATALOG[run_id]['required']
    missing = [x for x in required if not (bundle / x).exists()]
    if missing:
        return {
            'status':'INCOMPLETE','runtime_approved':False,'missing_evidence':missing,'failures':[],
            'manifest_validation': validation,
        }
    mode = _evidence_mode(manifest)
    # We still evaluate machine facts for synthetic/reference bundles, but they can never
    # produce runtime approval.
    try:
        failures = _evaluate_special(bundle, run_id)
    except Exception as e:
        return {'status':'INVALID','runtime_approved':False,'missing_evidence':[], 'failures':[f'evidence_parse:{e}']}
    if mode != 'RUNTIME':
        return {'status':mode,'runtime_approved':False,'missing_evidence':[], 'failures':failures}
    if failures:
        return {'status':'FAIL','runtime_approved':False,'missing_evidence':[], 'failures':failures}
    return {'status':'PASS','runtime_approved':True,'missing_evidence':[], 'failures':[]}


def generate_templates(root: Path | str) -> None:
    root = Path(root)
    root.mkdir(parents=True, exist_ok=True)
    for run_id, spec in RUN_CATALOG.items():
        d = root / run_id
        for sub in ['config-snapshot','metrics','logs','db','screenshots']:
            (d / sub).mkdir(parents=True, exist_ok=True)
        m = {
            'schema_version':'1.0','run_id':run_id,'timestamp':'UNKNOWN','environment':'UNKNOWN',
            'evidence_class':'PRODUCTION_RUNTIME','synthetic':False,'git_commit':'UNKNOWN',
            'artifact_version':'UNKNOWN','config_version':'UNKNOWN','service_id':'UNKNOWN',
            'guid':'UNKNOWN','hostname':'UNKNOWN','tomcat_jvm_instance':'UNKNOWN','db_target':'UNKNOWN',
            'purpose':spec['purpose'],
        }
        (d / 'run-manifest.json').write_text(json.dumps(m, ensure_ascii=False, indent=2)+'\n', encoding='utf-8')
        (d / 'result.md').write_text(f'# {run_id} Result\n\nStatus: OPEN\n\n', encoding='utf-8')
        (d / 'approval.md').write_text(f'# {run_id} Approval\n\nHuman approval: PENDING\n\n', encoding='utf-8')
        (d / 'REQUIRED-EVIDENCE.txt').write_text('\n'.join(spec['required'])+'\n', encoding='utf-8')


def _cli() -> int:
    ap = argparse.ArgumentParser()
    sp = ap.add_subparsers(dest='cmd', required=True)
    p = sp.add_parser('validate-manifest'); p.add_argument('manifest')
    p = sp.add_parser('evaluate-bundle'); p.add_argument('bundle')
    p = sp.add_parser('generate-templates'); p.add_argument('root')
    args = ap.parse_args()
    if args.cmd == 'validate-manifest':
        out = validate_run_manifest(_read_json(Path(args.manifest)))
    elif args.cmd == 'evaluate-bundle':
        out = evaluate_bundle(Path(args.bundle))
    else:
        generate_templates(Path(args.root)); out = {'status':'OK','runs':len(RUN_CATALOG)}
    print(json.dumps(out, ensure_ascii=False, indent=2))
    return 0


if __name__ == '__main__':
    raise SystemExit(_cli())
