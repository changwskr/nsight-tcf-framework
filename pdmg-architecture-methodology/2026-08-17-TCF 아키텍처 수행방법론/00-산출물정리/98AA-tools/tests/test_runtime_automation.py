import csv
import json
import os
from pathlib import Path
import subprocess
import sys
import pytest

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'tools'))
import nsight_run_automation as nra


def identity(**overrides):
    base = {
        'environment': 'PERF',
        'evidence_class': 'PRODUCTION_RUNTIME',
        'synthetic': False,
        'git_commit': 'a' * 40,
        'artifact_version': '1.0.0',
        'config_version': 'cfg-001',
        'service_id': 'MG.TEST.timeout',
        'guid': '12345678-1234-1234-1234-1234567890ab',
        'hostname': 'sbmpcolows01',
        'tomcat_jvm_instance': 'MG-01',
        'db_target': 'RDW',
    }
    base.update(overrides)
    return base


def test_prepare_bundle_creates_runtime_identity_and_directories(tmp_path):
    bundle = nra.prepare_bundle(tmp_path, 'RUN-P600', identity())
    manifest = json.loads((bundle / 'run-manifest.json').read_text())
    assert manifest['run_id'] == 'RUN-P600'
    assert manifest['hostname'] == 'sbmpcolows01'
    assert manifest['synthetic'] is False
    for name in ['config-snapshot', 'metrics', 'logs', 'db', 'screenshots']:
        assert (bundle / name).is_dir()


def test_prepare_bundle_rejects_unknown_runtime_identity(tmp_path):
    with pytest.raises(ValueError, match='hostname'):
        nra.prepare_bundle(tmp_path, 'RUN-P600', identity(hostname='UNKNOWN'))


def write_jtl(path: Path):
    rows = [
        {'timeStamp':'1000','elapsed':'100','label':'svc','responseCode':'200','success':'true','failureMessage':'','Latency':'80','Connect':'10'},
        {'timeStamp':'1100','elapsed':'200','label':'svc','responseCode':'200','success':'true','failureMessage':'','Latency':'150','Connect':'10'},
        {'timeStamp':'1200','elapsed':'3000','label':'svc','responseCode':'500','success':'false','failureMessage':'boom','Latency':'2900','Connect':'10'},
        {'timeStamp':'2000','elapsed':'400','label':'svc','responseCode':'200','success':'true','failureMessage':'','Latency':'350','Connect':'10'},
    ]
    with path.open('w', newline='', encoding='utf-8') as f:
        w = csv.DictWriter(f, fieldnames=rows[0].keys())
        w.writeheader(); w.writerows(rows)


def test_parse_jmeter_jtl_computes_required_summary_metrics(tmp_path):
    jtl = tmp_path / 'result.jtl'
    write_jtl(jtl)
    summary = nra.parse_jmeter_jtl(jtl)
    assert summary['sample_count'] == 4
    assert summary['success_count'] == 3
    assert summary['error_rate_pct'] == pytest.approx(25.0)
    assert summary['p95_seconds'] == pytest.approx(3.0)
    assert summary['tps'] == pytest.approx(4.0)


def test_ingest_jmeter_writes_validator_compatible_summary(tmp_path):
    bundle = nra.prepare_bundle(tmp_path, 'RUN-P600', identity())
    jtl = tmp_path / 'result.jtl'
    write_jtl(jtl)
    nra.ingest_jmeter(bundle, jtl, resource_metrics={
        'cpu_pct': 50.0,
        'busy_thread_pct': 40.0,
        'hikari_active_pct': 30.0,
        'timeout_rate_pct': 0.0,
    })
    summary = json.loads((bundle / 'metrics/summary.json').read_text())
    assert summary['cpu_pct'] == 50.0
    assert summary['busy_thread_pct'] == 40.0
    assert summary['hikari_active_pct'] == 30.0
    assert summary['timeout_rate_pct'] == 0.0
    assert (bundle / 'logs/run.log').exists()


def test_operator_hook_dry_run_never_executes(tmp_path):
    marker = tmp_path / 'executed.txt'
    result = nra.run_operator_hook(
        command=f"printf executed > {marker}",
        environment='PERF',
        execute=False,
        approval_token=None,
    )
    assert result['status'] == 'DRY_RUN'
    assert not marker.exists()


def test_operator_hook_prod_requires_approval_token(tmp_path):
    marker = tmp_path / 'executed.txt'
    with pytest.raises(PermissionError, match='approval'):
        nra.run_operator_hook(
            command=f"printf executed > {marker}",
            environment='PROD',
            execute=True,
            approval_token=None,
        )
    assert not marker.exists()


def test_operator_hook_perf_executes_when_explicit(tmp_path):
    marker = tmp_path / 'executed.txt'
    result = nra.run_operator_hook(
        command=f"printf executed > {marker}",
        environment='PERF',
        execute=True,
        approval_token=None,
    )
    assert result['status'] == 'EXECUTED'
    assert marker.read_text() == 'executed'


def test_build_run_plan_uses_canonical_sequence():
    assert nra.build_run_plan() == [
        'RUN-TIMEOUT','RUN-P600','RUN-P1200','RUN-S1800','RUN-HIKARI','RUN-SLOWSQL',
        'RUN-N1','RUN-SESSION','RUN-CF','RUN-TRACE','RUN-ROLLING','RUN-JWT-ROTATE'
    ]


def test_cli_prepare_bundle(tmp_path):
    cfg = tmp_path / 'identity.json'
    cfg.write_text(json.dumps(identity()), encoding='utf-8')
    script = Path(__file__).resolve().parents[1] / 'tools' / 'nsight_run_automation.py'
    proc = subprocess.run([
        sys.executable, str(script), 'prepare-bundle',
        '--root', str(tmp_path / 'out'), '--run-id', 'RUN-TRACE', '--identity', str(cfg)
    ], capture_output=True, text=True)
    assert proc.returncode == 0, proc.stderr
    out = json.loads(proc.stdout)
    assert out['status'] == 'READY'
    assert Path(out['bundle']).exists()


def test_render_jmeter_jmx_is_stock_jmeter_and_property_driven():
    xml = nra.render_jmeter_jmx()
    assert 'ConstantThroughputTimer' in xml
    assert '${__P(TARGET_TPS,600)}' in xml
    assert '${__P(BASE_URL,http://127.0.0.1:8080)}' in xml
    assert '${__P(SERVICE_ID,MG.TEST.sample)}' in xml
    assert 'Authorization' in xml


def test_runtime_guard_refuses_prod_execute_without_literal_token():
    assert nra.production_execution_allowed('PERF', None) is True
    assert nra.production_execution_allowed('PROD', None) is False
    assert nra.production_execution_allowed('PROD', 'APPROVED:CHG-123') is True
    assert nra.production_execution_allowed('PROD', 'yes') is False
