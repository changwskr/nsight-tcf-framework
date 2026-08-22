import json
from pathlib import Path
import sys
sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'tools'))
from nsight_second_batch_validate import validate_catalog, expected_run_ids


def sample_catalog():
    runs = {}
    for rid in expected_run_ids():
        runs[rid] = {
            'purpose': 'x',
            'prerequisites': ['runtime_identity'],
            'required_evidence': ['logs/run.log'],
            'machine_gates': [],
            'human_gates': ['approval'],
            'production_status': 'OPEN'
        }
    runs['RUN-SESSION']['human_gates'].append('Session ADR approved')
    runs['RUN-CF']['human_gates'].append('RTO/RPO approved')
    runs['RUN-JWT-ROTATE']['prerequisites'].append('Key Provider deployed')
    runs['RUN-TRACE']['required_evidence'].extend(['logs/GUID-trace.json','logs/ServiceId-trace.json'])
    return {'schema_version': '1.0', 'runs': runs}


def test_expected_run_set_is_exact():
    assert expected_run_ids() == [
        'RUN-S1800','RUN-HIKARI','RUN-SLOWSQL','RUN-N1','RUN-SESSION',
        'RUN-CF','RUN-TRACE','RUN-ROLLING','RUN-JWT-ROTATE'
    ]


def test_valid_catalog_passes():
    issues = validate_catalog(sample_catalog())
    assert issues == []


def test_missing_run_fails():
    c = sample_catalog(); del c['runs']['RUN-CF']
    assert any('RUN-CF' in x for x in validate_catalog(c))


def test_production_result_cannot_be_preapproved():
    c = sample_catalog(); c['runs']['RUN-N1']['production_status'] = 'PASS'
    assert any('pre-approved' in x for x in validate_catalog(c))


def test_each_run_requires_evidence():
    c = sample_catalog(); c['runs']['RUN-TRACE']['required_evidence'] = []
    assert any('required_evidence' in x for x in validate_catalog(c))


def test_session_requires_adr_human_gate():
    c = sample_catalog(); c['runs']['RUN-SESSION']['human_gates'] = ['approval']
    assert any('Session ADR' in x for x in validate_catalog(c))


def test_center_failure_requires_rto_rpo_human_gate():
    c = sample_catalog(); c['runs']['RUN-CF']['human_gates'] = ['approval']
    issues = validate_catalog(c)
    assert any('RTO/RPO' in x for x in issues)


def test_jwt_rotate_requires_key_provider_prerequisite():
    c = sample_catalog(); c['runs']['RUN-JWT-ROTATE']['prerequisites'] = ['runtime_identity']
    assert any('Key Provider' in x for x in validate_catalog(c))


def test_trace_requires_guid_serviceid_evidence():
    c = sample_catalog(); c['runs']['RUN-TRACE']['required_evidence'] = ['logs/run.log']
    assert any('GUID+ServiceId' in x for x in validate_catalog(c))
