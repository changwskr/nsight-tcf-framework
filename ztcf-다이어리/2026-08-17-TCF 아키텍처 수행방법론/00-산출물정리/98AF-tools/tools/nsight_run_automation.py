#!/usr/bin/env python3
"""NSIGHT Wave 3B runtime execution preparation/ingestion utilities.

Safety rules:
- Does not invent runtime evidence.
- Operator hooks are DRY_RUN unless execute=True.
- PROD hooks require an approval token.
- Prepared bundles require concrete runtime identity; UNKNOWN is rejected.
"""
from __future__ import annotations

import argparse
import csv
import json
import math
import os
from pathlib import Path
import subprocess
from datetime import datetime, timezone
from typing import Any, Dict, Iterable, List

RUN_SEQUENCE = [
    'RUN-TIMEOUT','RUN-P600','RUN-P1200','RUN-S1800','RUN-HIKARI','RUN-SLOWSQL',
    'RUN-N1','RUN-SESSION','RUN-CF','RUN-TRACE','RUN-ROLLING','RUN-JWT-ROTATE'
]

IDENTITY_FIELDS = [
    'environment','evidence_class','synthetic','git_commit','artifact_version',
    'config_version','service_id','guid','hostname','tomcat_jvm_instance','db_target'
]


def build_run_plan() -> List[str]:
    return list(RUN_SEQUENCE)


def _is_unknown(value: Any) -> bool:
    if value is None:
        return True
    if isinstance(value, bool):
        return False
    return str(value).strip() in {'', 'UNKNOWN', 'N/A', 'N/A?'}


def _validate_identity(identity: Dict[str, Any]) -> None:
    missing = [k for k in IDENTITY_FIELDS if k not in identity or _is_unknown(identity[k])]
    if missing:
        raise ValueError('runtime identity missing/unknown: ' + ', '.join(missing))
    if identity.get('evidence_class') == 'PRODUCTION_RUNTIME' and identity.get('synthetic') is True:
        raise ValueError('PRODUCTION_RUNTIME cannot be synthetic')


def prepare_bundle(root: Path | str, run_id: str, identity: Dict[str, Any]) -> Path:
    if run_id not in RUN_SEQUENCE:
        raise ValueError(f'unknown run_id: {run_id}')
    _validate_identity(identity)
    root = Path(root)
    bundle = root / run_id
    for sub in ['config-snapshot', 'metrics', 'logs', 'db', 'screenshots']:
        (bundle / sub).mkdir(parents=True, exist_ok=True)
    manifest = {
        'schema_version': '1.0',
        'run_id': run_id,
        'timestamp': datetime.now(timezone.utc).isoformat(),
        **identity,
    }
    (bundle / 'run-manifest.json').write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2) + '\n', encoding='utf-8'
    )
    (bundle / 'result.md').write_text(f'# {run_id} Result\n\nStatus: OPEN\n', encoding='utf-8')
    (bundle / 'approval.md').write_text(f'# {run_id} Approval\n\nHuman approval: PENDING\n', encoding='utf-8')
    return bundle


def _nearest_rank(values: List[float], percentile: float) -> float:
    if not values:
        return 0.0
    ordered = sorted(values)
    rank = max(1, math.ceil(percentile * len(ordered)))
    return ordered[rank - 1]


def parse_jmeter_jtl(path: Path | str) -> Dict[str, Any]:
    path = Path(path)
    rows: List[Dict[str, str]] = []
    with path.open(newline='', encoding='utf-8-sig') as f:
        reader = csv.DictReader(f)
        rows = list(reader)
    if not rows:
        raise ValueError('empty JMeter JTL')
    elapsed_ms = [float(r['elapsed']) for r in rows]
    timestamps_ms = [float(r['timeStamp']) for r in rows]
    successes = [str(r.get('success', '')).lower() == 'true' for r in rows]
    n = len(rows)
    success_count = sum(successes)
    duration_sec = max((max(timestamps_ms) - min(timestamps_ms)) / 1000.0, 1.0)
    timeout_count = 0
    for row, success in zip(rows, successes):
        msg = (row.get('failureMessage') or '').lower()
        code = (row.get('responseCode') or '').lower()
        if ('timeout' in msg) or code in {'408', '504'}:
            timeout_count += 1
    return {
        'sample_count': n,
        'success_count': success_count,
        'failure_count': n - success_count,
        'tps': round(n / duration_sec, 6),
        'p95_seconds': round(_nearest_rank(elapsed_ms, 0.95) / 1000.0, 6),
        'p99_seconds': round(_nearest_rank(elapsed_ms, 0.99) / 1000.0, 6),
        'error_rate_pct': round((n - success_count) * 100.0 / n, 6),
        'timeout_rate_pct': round(timeout_count * 100.0 / n, 6),
        'duration_seconds': round(duration_sec, 6),
    }


def ingest_jmeter(bundle: Path | str, jtl: Path | str, resource_metrics: Dict[str, Any]) -> Dict[str, Any]:
    bundle = Path(bundle)
    summary = parse_jmeter_jtl(jtl)
    for required in ['cpu_pct', 'busy_thread_pct', 'hikari_active_pct', 'timeout_rate_pct']:
        if required not in resource_metrics:
            raise ValueError(f'missing resource metric: {required}')
    summary.update(resource_metrics)
    (bundle / 'metrics').mkdir(parents=True, exist_ok=True)
    (bundle / 'logs').mkdir(parents=True, exist_ok=True)
    (bundle / 'metrics/summary.json').write_text(
        json.dumps(summary, ensure_ascii=False, indent=2) + '\n', encoding='utf-8'
    )
    (bundle / 'logs/run.log').write_text(
        f'jmeter_jtl={Path(jtl).resolve()}\nsample_count={summary["sample_count"]}\n', encoding='utf-8'
    )
    return summary


def production_execution_allowed(environment: str, approval_token: str | None) -> bool:
    env_upper = environment.strip().upper()
    if env_upper not in {'PROD', 'PRODUCTION'}:
        return True
    return bool(approval_token and approval_token.startswith('APPROVED:') and len(approval_token) > len('APPROVED:'))


def render_jmeter_jmx() -> str:
    """Return a stock-JMeter, property-driven HTTP/JSON load plan."""
    return r'''<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2" properties="5.0" jmeter="5.6.3">
  <hashTree>
    <TestPlan guiclass="TestPlanGui" testclass="TestPlan" testname="NSIGHT Service Load Plan">
      <boolProp name="TestPlan.functional_mode">false</boolProp>
      <boolProp name="TestPlan.tearDown_on_shutdown">true</boolProp>
      <elementProp name="TestPlan.user_defined_variables" elementType="Arguments">
        <collectionProp name="Arguments.arguments">
          <elementProp name="TARGET_TPS" elementType="Argument"><stringProp name="Argument.name">TARGET_TPS</stringProp><stringProp name="Argument.value">${__P(TARGET_TPS,600)}</stringProp></elementProp>
          <elementProp name="BASE_URL" elementType="Argument"><stringProp name="Argument.name">BASE_URL</stringProp><stringProp name="Argument.value">${__P(BASE_URL,http://127.0.0.1:8080)}</stringProp></elementProp>
        </collectionProp>
      </elementProp>
    </TestPlan>
    <hashTree>
      <ThreadGroup guiclass="ThreadGroupGui" testclass="ThreadGroup" testname="NSIGHT Load">
        <stringProp name="ThreadGroup.num_threads">${__P(THREADS,500)}</stringProp>
        <stringProp name="ThreadGroup.ramp_time">${__P(RAMP_SEC,30)}</stringProp>
        <boolProp name="ThreadGroup.scheduler">true</boolProp>
        <stringProp name="ThreadGroup.duration">${__P(DURATION_SEC,300)}</stringProp>
        <elementProp name="ThreadGroup.main_controller" elementType="LoopController">
          <boolProp name="LoopController.continue_forever">true</boolProp>
          <stringProp name="LoopController.loops">-1</stringProp>
        </elementProp>
      </ThreadGroup>
      <hashTree>
        <ConstantThroughputTimer guiclass="TestBeanGUI" testclass="ConstantThroughputTimer" testname="Target TPS">
          <doubleProp><name>throughput</name><value>${__groovy((props.get('TARGET_TPS') ?: '600').toDouble() * 60.0)}</value><savedValue>0.0</savedValue></doubleProp>
          <intProp name="calcMode">1</intProp>
        </ConstantThroughputTimer>
        <hashTree/>
        <HTTPSamplerProxy guiclass="HttpTestSampleGui" testclass="HTTPSamplerProxy" testname="NSIGHT Service Request">
          <stringProp name="HTTPSampler.domain">${__groovy(new URI(props.get('BASE_URL') ?: 'http://127.0.0.1:8080').host)}</stringProp>
          <stringProp name="HTTPSampler.port">${__groovy(new URI(props.get('BASE_URL') ?: 'http://127.0.0.1:8080').port == -1 ? '' : new URI(props.get('BASE_URL') ?: 'http://127.0.0.1:8080').port)}</stringProp>
          <stringProp name="HTTPSampler.protocol">${__groovy(new URI(props.get('BASE_URL') ?: 'http://127.0.0.1:8080').scheme)}</stringProp>
          <stringProp name="HTTPSampler.path">${__P(PATH,/api/service)}</stringProp>
          <stringProp name="HTTPSampler.method">POST</stringProp>
          <boolProp name="HTTPSampler.postBodyRaw">true</boolProp>
          <elementProp name="HTTPsampler.Arguments" elementType="Arguments">
            <collectionProp name="Arguments.arguments">
              <elementProp name="" elementType="HTTPArgument">
                <boolProp name="HTTPArgument.always_encode">false</boolProp>
                <stringProp name="Argument.value">${__FileToString(${__P(REQUEST_BODY_FILE,request.json)},UTF-8,)}</stringProp>
                <stringProp name="Argument.metadata">=</stringProp>
              </elementProp>
            </collectionProp>
          </elementProp>
        </HTTPSamplerProxy>
        <hashTree>
          <HeaderManager guiclass="HeaderPanel" testclass="HeaderManager" testname="Headers">
            <collectionProp name="HeaderManager.headers">
              <elementProp name="Content-Type" elementType="Header"><stringProp name="Header.name">Content-Type</stringProp><stringProp name="Header.value">application/json</stringProp></elementProp>
              <elementProp name="ServiceId" elementType="Header"><stringProp name="Header.name">ServiceId</stringProp><stringProp name="Header.value">${__P(SERVICE_ID,MG.TEST.sample)}</stringProp></elementProp>
              <elementProp name="Authorization" elementType="Header"><stringProp name="Header.name">Authorization</stringProp><stringProp name="Header.value">Bearer ${__P(AUTH_BEARER,)}</stringProp></elementProp>
              <elementProp name="X-NSIGHT-GUID" elementType="Header"><stringProp name="Header.name">X-NSIGHT-GUID</stringProp><stringProp name="Header.value">${__UUID()}</stringProp></elementProp>
            </collectionProp>
          </HeaderManager>
          <hashTree/>
        </hashTree>
      </hashTree>
    </hashTree>
  </hashTree>
</jmeterTestPlan>
'''

def run_operator_hook(command: str, environment: str, execute: bool = False, approval_token: str | None = None) -> Dict[str, Any]:
    if not command.strip():
        raise ValueError('operator command is empty')
    env_upper = environment.strip().upper()
    if not execute:
        return {'status': 'DRY_RUN', 'environment': env_upper, 'command': command, 'returncode': None}
    if not production_execution_allowed(env_upper, approval_token):
        raise PermissionError('production operator hook requires approval token in form APPROVED:<change-id>')
    proc = subprocess.run(command, shell=True, text=True, capture_output=True)
    if proc.returncode != 0:
        raise RuntimeError(f'operator hook failed rc={proc.returncode}: {proc.stderr.strip()}')
    return {
        'status': 'EXECUTED', 'environment': env_upper, 'command': command,
        'returncode': proc.returncode, 'stdout': proc.stdout, 'stderr': proc.stderr,
    }


def _read_json(path: Path) -> Dict[str, Any]:
    obj = json.loads(path.read_text(encoding='utf-8'))
    if not isinstance(obj, dict):
        raise ValueError(f'JSON object required: {path}')
    return obj


def _cli() -> int:
    ap = argparse.ArgumentParser()
    sp = ap.add_subparsers(dest='cmd', required=True)
    p = sp.add_parser('prepare-bundle')
    p.add_argument('--root', required=True); p.add_argument('--run-id', required=True); p.add_argument('--identity', required=True)
    p = sp.add_parser('ingest-jmeter')
    p.add_argument('--bundle', required=True); p.add_argument('--jtl', required=True); p.add_argument('--resource-metrics', required=True)
    p = sp.add_parser('operator-hook')
    p.add_argument('--command', required=True); p.add_argument('--environment', required=True); p.add_argument('--execute', action='store_true'); p.add_argument('--approval-token')
    p = sp.add_parser('plan')
    args = ap.parse_args()
    if args.cmd == 'prepare-bundle':
        bundle = prepare_bundle(args.root, args.run_id, _read_json(Path(args.identity)))
        out = {'status': 'READY', 'bundle': str(bundle.resolve())}
    elif args.cmd == 'ingest-jmeter':
        out = ingest_jmeter(args.bundle, args.jtl, _read_json(Path(args.resource_metrics)))
    elif args.cmd == 'operator-hook':
        out = run_operator_hook(args.command, args.environment, args.execute, args.approval_token)
    else:
        out = {'status': 'OK', 'runs': build_run_plan()}
    print(json.dumps(out, ensure_ascii=False, indent=2))
    return 0


if __name__ == '__main__':
    raise SystemExit(_cli())
