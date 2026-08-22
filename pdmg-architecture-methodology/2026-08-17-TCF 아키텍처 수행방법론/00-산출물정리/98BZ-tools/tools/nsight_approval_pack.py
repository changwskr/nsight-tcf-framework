#!/usr/bin/env python3
from __future__ import annotations
import json
from collections import Counter
from pathlib import Path
from typing import Any, Dict

ADR_DECISIONS={'APPROVE','REJECT','DEFER'}
CLOSED_PREFIX='CLOSED'


def board_summary(adrs):
    c=Counter(a.get('readiness') for a in adrs)
    return {
        'total':len(adrs),
        'ready_for_human_decision':c.get('READY_FOR_HUMAN_DECISION',0),
        'needs_owner_input':c.get('NEEDS_OWNER_INPUT',0),
        'needs_owner_input_and_runtime':c.get('NEEDS_OWNER_INPUT_AND_RUNTIME',0),
        'runtime_dependent':c.get('RUNTIME_DEPENDENT',0),
        'approved':sum(1 for a in adrs if a.get('status')=='APPROVED'),
    }


def adr_template(adr):
    return {
        'adr_id':adr['id'],
        'title':adr.get('title'),
        'decision':None,
        'approver':None,
        'decision_date':None,
        'evidence_ref':[],
        'conditions':[],
        'comment':None,
        'approval_state':'DRAFT_NOT_APPROVED',
        'recommended_decision':'APPROVE' if adr.get('readiness')=='READY_FOR_HUMAN_DECISION' else 'DEFER_UNTIL_PREREQUISITES',
        'readiness':adr.get('readiness'),
        'required_approver':adr.get('approver_role'),
        'runtime_dependency':adr.get('runtime',[]),
        'recommendation':adr.get('recommendation'),
        'prerequisites':adr.get('prerequisites',[]),
        'closure':adr.get('closure',[]),
    }


def runtime_template(run_id, run):
    required=run.get('required') or run.get('required_evidence') or []
    return {
        'run_id':run_id,
        'status':'OPEN',
        'evidence_class':'UNSET',
        'runtime_approved':False,
        'synthetic':False,
        'environment':None,
        'hostname':None,
        'git_commit':None,
        'artifact_version':None,
        'config_version':None,
        'started_at':None,
        'ended_at':None,
        'approver':None,
        'approval_date':None,
        'required_evidence':required,
        'evidence_ref':[],
        'purpose':run.get('purpose'),
    }


def p0_template(item):
    return {
        'item_id':item['id'],
        'area':item.get('area'),
        'current_status':item.get('status'),
        'status':None,
        'approver':None,
        'decision_date':None,
        'evidence_ref':[],
        'closure_comment':None,
        'required_next':item.get('requiredNext'),
    }


def submission_index(adrs, runs, items):
    unresolved=[x for x in items if not str(x.get('status','')).startswith(CLOSED_PREFIX)]
    return {
        'counts':{'adr':len(adrs),'runtime':len(runs),'p0_closure':len(unresolved)},
        'adr_ids':[a['id'] for a in adrs],
        'runtime_run_ids':list(runs.keys()),
        'p0_closure_ids':[x['id'] for x in unresolved],
    }


def validate_adr_submission(record):
    issues=[]
    if record.get('decision') not in ADR_DECISIONS:
        issues.append('decision must be APPROVE/REJECT/DEFER')
    if not record.get('approver'):
        issues.append('approver is required')
    if not record.get('decision_date'):
        issues.append('decision_date is required')
    ev=record.get('evidence_ref')
    if not ev:
        issues.append('evidence_ref is required')
    return (not issues,issues)


def write_json(path:Path,obj:Any):
    path.parent.mkdir(parents=True,exist_ok=True)
    path.write_text(json.dumps(obj,ensure_ascii=False,indent=2)+'\n',encoding='utf-8')


def generate(baseline:Path,out:Path):
    reg=json.loads((baseline/'98BI-ADR-APPROVAL-REGISTER.json').read_text(encoding='utf-8'))['adrs']
    runs=json.loads((baseline/'98W-RUNTIME-EVIDENCE-RUN-CATALOG.json').read_text(encoding='utf-8'))['runs']
    items=json.loads((baseline/'96-P0-CLOSURE-MATRIX.json').read_text(encoding='utf-8'))['items']
    unresolved=[x for x in items if not str(x.get('status','')).startswith(CLOSED_PREFIX)]
    for a in reg: write_json(out/'adr-approvals'/f"{a['id']}.json",adr_template(a))
    for rid,r in runs.items(): write_json(out/'runtime'/rid/'evaluation.json',runtime_template(rid,r))
    for x in unresolved: write_json(out/'closure'/f"{x['id']}.json",p0_template(x))
    idx=submission_index(reg,runs,items)
    idx['board_summary']=board_summary(reg)
    write_json(out/'submission-index.json',idx)
    return idx

if __name__=='__main__':
    import argparse
    ap=argparse.ArgumentParser()
    ap.add_argument('--baseline',required=True); ap.add_argument('--out',required=True)
    args=ap.parse_args()
    print(json.dumps(generate(Path(args.baseline),Path(args.out)),ensure_ascii=False,indent=2))
