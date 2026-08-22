import json
import sys
from pathlib import Path
import pytest

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'tools'))

from nsight_runtime_evidence import (
    RUN_CATALOG,
    validate_run_manifest,
    evaluate_bundle,
    generate_templates,
)


def base_manifest(run_id='RUN-P600'):
    return {
        'schema_version': '1.0',
        'run_id': run_id,
        'timestamp': '2026-08-19T09:10:00+09:00',
        'environment': 'PERF',
        'evidence_class': 'PRODUCTION_RUNTIME',
        'synthetic': False,
        'git_commit': '0123456789abcdef0123456789abcdef01234567',
        'artifact_version': 'nsight-1.0.0-rc1',
        'config_version': 'cfg-20260819-01',
        'service_id': 'MG.TEST.001',
        'guid': '11111111-2222-3333-4444-555555555555',
        'hostname': 'sbmpcolows01',
        'tomcat_jvm_instance': 'mg-jvm-01',
        'db_target': 'RDW',
    }


def test_manifest_accepts_complete_known_run():
    r = validate_run_manifest(base_manifest())
    assert r['accepted'] is True
    assert r['errors'] == []


def test_manifest_rejects_missing_identity_and_unknown_run():
    m = base_manifest('RUN-UNKNOWN')
    m['hostname'] = 'UNKNOWN'
    m.pop('guid')
    r = validate_run_manifest(m)
    assert r['accepted'] is False
    assert 'unknown_run_id' in r['errors']
    assert 'guid' in r['missing_or_invalid']
    assert 'hostname' in r['missing_or_invalid']


def test_synthetic_evidence_can_never_be_runtime_approved(tmp_path):
    b = tmp_path / 'RUN-P600'
    (b / 'metrics').mkdir(parents=True)
    (b / 'logs').mkdir()
    m = base_manifest()
    m['synthetic'] = True
    (b / 'run-manifest.json').write_text(json.dumps(m), encoding='utf-8')
    (b / 'metrics' / 'summary.json').write_text(json.dumps({
        'tps': 600, 'p95_seconds': 2.1, 'error_rate_pct': 0.0, 'timeout_rate_pct': 0.0,
        'cpu_pct': 50, 'busy_thread_pct': 40, 'hikari_active_pct': 50
    }), encoding='utf-8')
    (b / 'logs' / 'run.log').write_text('synthetic', encoding='utf-8')
    r = evaluate_bundle(b)
    assert r['status'] == 'SYNTHETIC_ONLY'
    assert r['runtime_approved'] is False


def test_p600_passes_when_required_metrics_and_thresholds_are_met(tmp_path):
    b = tmp_path / 'RUN-P600'
    (b / 'metrics').mkdir(parents=True)
    (b / 'logs').mkdir()
    (b / 'run-manifest.json').write_text(json.dumps(base_manifest()), encoding='utf-8')
    (b / 'metrics' / 'summary.json').write_text(json.dumps({
        'tps': 600, 'p95_seconds': 2.5, 'error_rate_pct': 0.1, 'timeout_rate_pct': 0.0,
        'cpu_pct': 65, 'busy_thread_pct': 60, 'hikari_active_pct': 70
    }), encoding='utf-8')
    (b / 'logs' / 'run.log').write_text('ok', encoding='utf-8')
    r = evaluate_bundle(b)
    assert r['status'] == 'PASS'
    assert r['runtime_approved'] is True


def test_p600_fails_when_p95_exceeds_target(tmp_path):
    b = tmp_path / 'RUN-P600'
    (b / 'metrics').mkdir(parents=True)
    (b / 'logs').mkdir()
    (b / 'run-manifest.json').write_text(json.dumps(base_manifest()), encoding='utf-8')
    (b / 'metrics' / 'summary.json').write_text(json.dumps({
        'tps': 600, 'p95_seconds': 3.4, 'error_rate_pct': 0.1, 'timeout_rate_pct': 0.0,
        'cpu_pct': 65, 'busy_thread_pct': 60, 'hikari_active_pct': 70
    }), encoding='utf-8')
    (b / 'logs' / 'run.log').write_text('slow', encoding='utf-8')
    r = evaluate_bundle(b)
    assert r['status'] == 'FAIL'
    assert 'p95_seconds>3.0' in r['failures']


def test_timeout_requires_rollback_no_late_commit_connection_return_and_context_cleanup(tmp_path):
    b = tmp_path / 'RUN-TIMEOUT'
    for d in ['db','logs','metrics']:
        (b / d).mkdir(parents=True, exist_ok=True)
    m = base_manifest('RUN-TIMEOUT')
    (b / 'run-manifest.json').write_text(json.dumps(m), encoding='utf-8')
    (b / 'db' / 'before-after.json').write_text(json.dumps({'rolled_back': True, 'late_commit_count': 0}), encoding='utf-8')
    (b / 'logs' / 'transaction.json').write_text(json.dumps({'tx_result':'ROLLBACK','client_result':'TIMEOUT'}), encoding='utf-8')
    (b / 'metrics' / 'pool.json').write_text(json.dumps({'active_before': 3, 'active_after': 3, 'pending_after': 0}), encoding='utf-8')
    (b / 'metrics' / 'thread.json').write_text(json.dumps({'worker_returned': True, 'context_leak_count': 0}), encoding='utf-8')
    r = evaluate_bundle(b)
    assert r['status'] == 'PASS'
    assert r['runtime_approved'] is True


def test_timeout_fails_on_late_commit_or_context_leak(tmp_path):
    b = tmp_path / 'RUN-TIMEOUT'
    for d in ['db','logs','metrics']:
        (b / d).mkdir(parents=True, exist_ok=True)
    (b / 'run-manifest.json').write_text(json.dumps(base_manifest('RUN-TIMEOUT')), encoding='utf-8')
    (b / 'db' / 'before-after.json').write_text(json.dumps({'rolled_back': False, 'late_commit_count': 1}), encoding='utf-8')
    (b / 'logs' / 'transaction.json').write_text(json.dumps({'tx_result':'COMMIT','client_result':'TIMEOUT'}), encoding='utf-8')
    (b / 'metrics' / 'pool.json').write_text(json.dumps({'active_before': 3, 'active_after': 4, 'pending_after': 1}), encoding='utf-8')
    (b / 'metrics' / 'thread.json').write_text(json.dumps({'worker_returned': False, 'context_leak_count': 1}), encoding='utf-8')
    r = evaluate_bundle(b)
    assert r['status'] == 'FAIL'
    assert 'late_commit_count!=0' in r['failures']
    assert 'context_leak_count!=0' in r['failures']


def test_missing_bundle_evidence_is_incomplete_not_pass(tmp_path):
    b = tmp_path / 'RUN-N1'
    b.mkdir()
    (b / 'run-manifest.json').write_text(json.dumps(base_manifest('RUN-N1')), encoding='utf-8')
    r = evaluate_bundle(b)
    assert r['status'] == 'INCOMPLETE'
    assert r['runtime_approved'] is False
    assert r['missing_evidence']


def test_catalog_contains_all_mandatory_runs():
    required = {
        'RUN-TIMEOUT','RUN-P600','RUN-P1200','RUN-S1800','RUN-HIKARI','RUN-SLOWSQL',
        'RUN-N1','RUN-SESSION','RUN-CF','RUN-TRACE','RUN-ROLLING','RUN-JWT-ROTATE'
    }
    assert required == set(RUN_CATALOG)


def test_generate_templates_creates_all_run_directories(tmp_path):
    generate_templates(tmp_path)
    for run_id in RUN_CATALOG:
        assert (tmp_path / run_id / 'run-manifest.json').exists()
        assert (tmp_path / run_id / 'result.md').exists()
        assert (tmp_path / run_id / 'approval.md').exists()


def test_local_or_reference_evidence_is_not_runtime_approved(tmp_path):
    b = tmp_path / 'RUN-P600'
    (b / 'metrics').mkdir(parents=True)
    (b / 'logs').mkdir()
    m = base_manifest()
    m['evidence_class'] = 'REFERENCE_CONFIG'
    (b / 'run-manifest.json').write_text(json.dumps(m), encoding='utf-8')
    (b / 'metrics' / 'summary.json').write_text(json.dumps({
        'tps': 600, 'p95_seconds': 2.0, 'error_rate_pct': 0, 'timeout_rate_pct': 0,
        'cpu_pct': 40, 'busy_thread_pct': 30, 'hikari_active_pct': 30
    }), encoding='utf-8')
    (b / 'logs' / 'run.log').write_text('ok', encoding='utf-8')
    r = evaluate_bundle(b)
    assert r['status'] == 'REFERENCE_ONLY'
    assert r['runtime_approved'] is False
