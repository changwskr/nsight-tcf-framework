import importlib.util, json, sys
from pathlib import Path

TOOL=Path(__file__).parents[1]/'tools'/'nsight_evidence_intake.py'
spec=importlib.util.spec_from_file_location('intake', TOOL)
mod=importlib.util.module_from_spec(spec); sys.modules['intake']=mod
try:
    spec.loader.exec_module(mod)
except FileNotFoundError:
    pass


def test_validate_adr_approval_requires_human_fields():
    bad={'adr_id':'ADR-SEC-001','decision':'APPROVE'}
    out=mod.validate_adr_approval(bad, {'ADR-SEC-001'})
    assert out['accepted'] is False
    assert 'approver' in out['missing']
    assert 'decision_date' in out['missing']


def test_validate_adr_approval_accepts_valid_approval():
    good={'adr_id':'ADR-SEC-001','decision':'APPROVE','approver':'Security Architect','decision_date':'2026-08-19','evidence_ref':'CAB-123'}
    out=mod.validate_adr_approval(good, {'ADR-SEC-001'})
    assert out['accepted'] is True


def test_apply_adr_approval_marks_register_approved():
    adrs=[{'id':'ADR-SEC-001','status':'PROPOSED','approver':None,'decision_date':None,'decision':None}]
    approval={'adr_id':'ADR-SEC-001','decision':'APPROVE','approver':'A','decision_date':'2026-08-19','evidence_ref':'CAB-1'}
    updated=mod.apply_adr_approval(adrs,approval)
    assert updated[0]['status']=='APPROVED'
    assert updated[0]['approver']=='A'
    assert updated[0]['decision']=='APPROVE'


def test_reject_adr_does_not_count_as_approved():
    adrs=[{'id':'ADR-SEC-001','status':'PROPOSED','approver':None,'decision_date':None,'decision':None}]
    approval={'adr_id':'ADR-SEC-001','decision':'REJECT','approver':'A','decision_date':'2026-08-19','evidence_ref':'CAB-1'}
    updated=mod.apply_adr_approval(adrs,approval)
    assert updated[0]['status']=='REJECTED'


def test_runtime_result_requires_production_contract():
    assert mod.runtime_result_is_approved({'status':'PASS','evidence_class':'PRODUCTION_RUNTIME','runtime_approved':True}) is True
    assert mod.runtime_result_is_approved({'status':'PASS','evidence_class':'SYNTHETIC','runtime_approved':True}) is False
    assert mod.runtime_result_is_approved({'status':'PASS','evidence_class':'PRODUCTION_RUNTIME','runtime_approved':False}) is False


def test_build_state_keeps_missing_runs_open():
    adrs=[{'id':'ADR-SEC-001','priority':'P0','requires_human_approval':True,'status':'PROPOSED'}]
    runs=['RUN-TIMEOUT','RUN-P600']
    runtime={'RUN-TIMEOUT':{'status':'PASS','evidence_class':'PRODUCTION_RUNTIME','runtime_approved':True}}
    p0=[{'id':'P0-X','status':'CLOSED_STATIC'}]
    state=mod.build_regate_state(adrs,runs,runtime,p0,[])
    by={x['id']:x for x in state['runtime_runs']}
    assert by['RUN-TIMEOUT']['runtime_approved'] is True
    assert by['RUN-P600']['status']=='OPEN'


def test_evaluate_regate_holds_when_any_hard_blocker():
    state={
      'adrs':[{'id':'ADR-A','priority':'P0','requires_human_approval':True,'status':'APPROVED'}],
      'runtime_runs':[{'id':'RUN-X','status':'OPEN','evidence_class':'NONE','runtime_approved':False}],
      'p0_items':[{'id':'P0-X','status':'CLOSED_STATIC'}],
      'non_p0_open':[]
    }
    r=mod.evaluate_regate(state)
    assert r['g80']=='HOLD'
    assert r['hard_blocker_count']==1


def test_evaluate_regate_conditional_when_only_non_p0_open():
    state={
      'adrs':[{'id':'ADR-A','priority':'P0','requires_human_approval':True,'status':'APPROVED'}],
      'runtime_runs':[{'id':'RUN-X','status':'PASS','evidence_class':'PRODUCTION_RUNTIME','runtime_approved':True}],
      'p0_items':[{'id':'P0-X','status':'CLOSED_APPROVED'}],
      'non_p0_open':['P1-X']
    }
    r=mod.evaluate_regate(state)
    assert r['g80']=='CONDITIONAL_PASS'
    assert r['hg90']=='WAIT_HUMAN_SIGNOFF'


def test_scan_empty_inbox_returns_zero_accepted(tmp_path):
    for d in ['adr-approvals','runtime','production-config','closure']:
        (tmp_path/d).mkdir(parents=True)
    out=mod.scan_inbox(tmp_path, known_adrs={'ADR-SEC-001'})
    assert out['summary']['accepted_adr_approvals']==0
    assert out['summary']['runtime_bundles']==0
    assert out['summary']['production_config_manifests']==0


def test_closure_override_requires_approved_evidence(tmp_path):
    p=tmp_path/'closure.json'
    p.write_text(json.dumps({'item_id':'P0-X','status':'CLOSED_APPROVED','approver':'Ops','decision_date':'2026-08-19','evidence_ref':'EV-1'}),encoding='utf-8')
    out=mod.validate_closure_record(json.loads(p.read_text()), {'P0-X'})
    assert out['accepted'] is True

def test_select_p0_items_accepts_p0_id_when_priority_field_missing():
    items=[{'id':'P0-SEC-001','status':'OPEN'},{'id':'P1-X','priority':'P1','status':'OPEN'}]
    out=mod.select_p0_items(items)
    assert [x['id'] for x in out]==['P0-SEC-001']
