from pathlib import Path
import json
import sys

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))

from nsight_runbook_validate import validate_catalog, evaluate_go_nogo, build_execution_plan


def sample_catalog():
    return {
        'schema_version': '1.0',
        'runs': {
            'RUN-TIMEOUT': {
                'purpose': 'timeout safety',
                'target': {},
                'preconditions': ['approved test service'],
                'required_evidence': ['db/before-after.json','logs/transaction.json','metrics/pool.json','metrics/thread.json'],
                'commands': {'preflight':'echo preflight','execute':'echo execute','evaluate':'echo evaluate'},
                'hard_gates': ['rolled_back == true','late_commit_count == 0'],
                'human_gates': ['change approval'],
            },
            'RUN-P600': {
                'purpose': '600 tps',
                'target': {'tps':600,'p95_seconds':3.0},
                'preconditions': ['approved load environment'],
                'required_evidence': ['metrics/summary.json','logs/run.log'],
                'commands': {'preflight':'echo preflight','execute':'echo execute','evaluate':'echo evaluate'},
                'hard_gates': ['tps >= 600','p95_seconds <= 3.0'],
                'human_gates': ['error/timeout threshold approval'],
            },
            'RUN-P1200': {
                'purpose': '1200 tps',
                'target': {'tps':1200,'p95_seconds':3.0},
                'preconditions': ['RUN-P600 accepted'],
                'required_evidence': ['metrics/summary.json','logs/run.log'],
                'commands': {'preflight':'echo preflight','execute':'echo execute','evaluate':'echo evaluate'},
                'hard_gates': ['tps >= 1200','p95_seconds <= 3.0'],
                'human_gates': ['error/timeout threshold approval'],
            },
        },
    }


def test_catalog_requires_exact_first_batch():
    out = validate_catalog(sample_catalog())
    assert out['accepted'] is True
    assert out['run_count'] == 3


def test_catalog_rejects_missing_evidence():
    c = sample_catalog()
    c['runs']['RUN-P600']['required_evidence'] = []
    out = validate_catalog(c)
    assert out['accepted'] is False
    assert 'RUN-P600:required_evidence' in out['errors']


def test_execution_plan_orders_timeout_then_p600_then_p1200():
    assert build_execution_plan(sample_catalog()) == ['RUN-TIMEOUT','RUN-P600','RUN-P1200']


def test_timeout_pass_requires_rollback_and_cleanup():
    facts = {
        'rolled_back': True, 'late_commit_count': 0, 'tx_result': 'ROLLBACK',
        'client_result':'TIMEOUT', 'pending_after':0, 'active_before':2,
        'active_after':2, 'worker_returned':True, 'context_leak_count':0,
        'human_approvals_complete': True,
    }
    out = evaluate_go_nogo('RUN-TIMEOUT', facts)
    assert out['decision'] == 'GO_CANDIDATE'


def test_timeout_late_commit_is_no_go():
    facts = {
        'rolled_back': True, 'late_commit_count': 1, 'tx_result': 'ROLLBACK',
        'client_result':'TIMEOUT', 'pending_after':0, 'active_before':2,
        'active_after':2, 'worker_returned':True, 'context_leak_count':0,
        'human_approvals_complete': True,
    }
    out = evaluate_go_nogo('RUN-TIMEOUT', facts)
    assert out['decision'] == 'NO_GO'
    assert 'late_commit_count!=0' in out['failures']


def test_p600_hard_gate_and_open_human_thresholds():
    facts = {
        'tps': 615, 'p95_seconds': 2.7, 'error_rate_pct': 0.1,
        'timeout_rate_pct':0.01,'cpu_pct':65,'busy_thread_pct':60,
        'hikari_active_pct':65,'human_approvals_complete':False,
    }
    out = evaluate_go_nogo('RUN-P600', facts)
    assert out['decision'] == 'CONDITIONAL_REVIEW'
    assert not out['failures']


def test_p1200_p95_breach_is_no_go():
    facts = {
        'tps': 1220, 'p95_seconds': 3.2, 'error_rate_pct': 0.1,
        'timeout_rate_pct':0.01,'cpu_pct':65,'busy_thread_pct':60,
        'hikari_active_pct':65,'human_approvals_complete':True,
    }
    out = evaluate_go_nogo('RUN-P1200', facts)
    assert out['decision'] == 'NO_GO'
    assert 'p95_seconds>3.0' in out['failures']


def test_p1200_below_tps_is_no_go():
    facts = {
        'tps': 1190, 'p95_seconds': 2.5, 'error_rate_pct': 0.1,
        'timeout_rate_pct':0.01,'cpu_pct':65,'busy_thread_pct':60,
        'hikari_active_pct':65,'human_approvals_complete':True,
    }
    out = evaluate_go_nogo('RUN-P1200', facts)
    assert out['decision'] == 'NO_GO'
    assert 'tps<1200' in out['failures']

def test_null_template_values_are_incomplete_not_exception():
    facts = {
        'tps': None, 'p95_seconds': None, 'error_rate_pct': None,
        'timeout_rate_pct':None,'cpu_pct':None,'busy_thread_pct':None,
        'hikari_active_pct':None,'human_approvals_complete':False,
    }
    out = evaluate_go_nogo('RUN-P600', facts)
    assert out['decision'] == 'INCOMPLETE'
    assert 'tps' in out['missing']
