#!/usr/bin/env python3
from __future__ import annotations
import argparse, copy, json
from pathlib import Path
from typing import Any, Dict, Iterable

CLOSED_P0_STATUSES={"CLOSED_STATIC","CLOSED_RUNTIME","CLOSED_APPROVED"}
ADR_DECISIONS={"APPROVE","REJECT","DEFER"}


def _load_json(path: Path) -> Dict[str, Any]:
    obj=json.loads(path.read_text(encoding='utf-8'))
    if not isinstance(obj,dict):
        raise ValueError(f'JSON object required: {path}')
    return obj


def validate_adr_approval(record: Dict[str, Any], known_adrs: set[str]) -> Dict[str, Any]:
    missing=[]; errors=[]
    for k in ['adr_id','decision','approver','decision_date','evidence_ref']:
        if not record.get(k): missing.append(k)
    aid=record.get('adr_id')
    if aid and aid not in known_adrs: errors.append('unknown_adr_id')
    if record.get('decision') and record.get('decision') not in ADR_DECISIONS: errors.append('invalid_decision')
    return {'accepted': not missing and not errors, 'missing': missing, 'errors': errors}


def apply_adr_approval(adrs: list[Dict[str,Any]], approval: Dict[str,Any]) -> list[Dict[str,Any]]:
    out=copy.deepcopy(adrs)
    for adr in out:
        if adr.get('id')==approval.get('adr_id'):
            decision=approval.get('decision')
            adr['status']={'APPROVE':'APPROVED','REJECT':'REJECTED','DEFER':'PROPOSED'}[decision]
            adr['approver']=approval.get('approver')
            adr['decision_date']=approval.get('decision_date')
            adr['decision']=decision
            adr['approval_evidence_ref']=approval.get('evidence_ref')
            break
    return out


def validate_closure_record(record: Dict[str,Any], known_items:set[str]) -> Dict[str,Any]:
    missing=[]; errors=[]
    for k in ['item_id','status','approver','decision_date','evidence_ref']:
        if not record.get(k): missing.append(k)
    if record.get('item_id') and record.get('item_id') not in known_items: errors.append('unknown_item_id')
    if record.get('status') not in {'CLOSED_RUNTIME','CLOSED_APPROVED'}: errors.append('invalid_closure_status')
    return {'accepted':not missing and not errors,'missing':missing,'errors':errors}


def runtime_result_is_approved(run: Dict[str,Any]) -> bool:
    return run.get('status')=='PASS' and run.get('evidence_class')=='PRODUCTION_RUNTIME' and run.get('runtime_approved') is True



def select_p0_items(items:list[Dict[str,Any]]) -> list[Dict[str,Any]]:
    return [copy.deepcopy(i) for i in items if i.get("priority")=="P0" or str(i.get("id","")).startswith("P0-")]

def build_regate_state(adrs:list[Dict[str,Any]], mandatory_runs:Iterable[str], runtime_results:Dict[str,Dict[str,Any]], p0_items:list[Dict[str,Any]], non_p0_open:list[str]) -> Dict[str,Any]:
    runs=[]
    for rid in mandatory_runs:
        r={'id':rid,'status':'OPEN','evidence_class':'NONE','runtime_approved':False}
        if rid in runtime_results:
            r.update(runtime_results[rid]); r['id']=rid
        runs.append(r)
    return {'adrs':copy.deepcopy(adrs),'runtime_runs':runs,'p0_items':copy.deepcopy(p0_items),'non_p0_open':list(non_p0_open)}


def evaluate_regate(state:Dict[str,Any]) -> Dict[str,Any]:
    blocking_adrs=sorted(a.get('id') for a in state.get('adrs',[]) if a.get('priority')=='P0' and a.get('requires_human_approval',True) and a.get('status')!='APPROVED')
    blocking_runtime_runs=sorted(r.get('id') for r in state.get('runtime_runs',[]) if not runtime_result_is_approved(r))
    blocking_p0_items=sorted(i.get('id') for i in state.get('p0_items',[]) if i.get('status') not in CLOSED_P0_STATUSES)
    non_p0_open=list(state.get('non_p0_open',[]))
    hard=len(blocking_adrs)+len(blocking_runtime_runs)+len(blocking_p0_items)
    if hard:
        g80='HOLD'; hg90='HOLD'
    elif non_p0_open:
        g80='CONDITIONAL_PASS'; hg90='WAIT_HUMAN_SIGNOFF'
    else:
        g80='PASS_CANDIDATE'; hg90='WAIT_HUMAN_SIGNOFF'
    return {'g80':g80,'hg90':hg90,'blocking_adrs':blocking_adrs,'blocking_runtime_runs':blocking_runtime_runs,'blocking_p0_items':blocking_p0_items,'non_p0_open':non_p0_open,'hard_blocker_count':hard}


def _validate_prod_config_manifest(m:Dict[str,Any]) -> Dict[str,Any]:
    missing=[]; errors=[]
    for k in ['schema_version','environment','hostname','captured_at','evidence_class','files']:
        if not m.get(k): missing.append(k)
    if m.get('evidence_class') and m.get('evidence_class')!='PRODUCTION_RUNTIME': errors.append('evidence_class_not_production_runtime')
    if m.get('environment') and m.get('environment') not in {'PROD','DR'}: errors.append('environment_not_prod_or_dr')
    files=m.get('files') or []
    if files and not isinstance(files,list): errors.append('files_not_array')
    return {'accepted':not missing and not errors,'missing':missing,'errors':errors}


def scan_inbox(root:Path|str, known_adrs:set[str], known_items:set[str]|None=None) -> Dict[str,Any]:
    root=Path(root); known_items=known_items or set()
    adr_records=[]; runtime_results={}; config_records=[]; closure_records=[]
    adr_dir=root/'adr-approvals'
    if adr_dir.exists():
        for p in sorted(adr_dir.glob('*.json')):
            try: rec=_load_json(p); val=validate_adr_approval(rec,known_adrs)
            except Exception as e: rec={}; val={'accepted':False,'missing':[],'errors':[f'parse:{e}']}
            adr_records.append({'path':str(p),'record':rec,'validation':val})
    runtime_dir=root/'runtime'
    runtime_bundle_count=0
    if runtime_dir.exists():
        for d in sorted(x for x in runtime_dir.iterdir() if x.is_dir()):
            if (d/'run-manifest.json').exists() or (d/'evaluation.json').exists(): runtime_bundle_count+=1
            ep=d/'evaluation.json'
            if ep.exists():
                try:
                    r=_load_json(ep); rid=r.get('run_id') or d.name; runtime_results[rid]=r
                except Exception:
                    pass
    cfg_dir=root/'production-config'
    if cfg_dir.exists():
        for p in sorted(cfg_dir.rglob('evidence-manifest.json')):
            try: rec=_load_json(p); val=_validate_prod_config_manifest(rec)
            except Exception as e: rec={}; val={'accepted':False,'missing':[],'errors':[f'parse:{e}']}
            config_records.append({'path':str(p),'record':rec,'validation':val})
    cl_dir=root/'closure'
    if cl_dir.exists():
        for p in sorted(cl_dir.glob('*.json')):
            try: rec=_load_json(p); val=validate_closure_record(rec,known_items)
            except Exception as e: rec={}; val={'accepted':False,'missing':[],'errors':[f'parse:{e}']}
            closure_records.append({'path':str(p),'record':rec,'validation':val})
    return {
        'adr_approvals':adr_records,'runtime_results':runtime_results,'production_config':config_records,'closures':closure_records,
        'summary':{
            'adr_approval_files':len(adr_records),
            'accepted_adr_approvals':sum(1 for x in adr_records if x['validation']['accepted']),
            'runtime_bundles':runtime_bundle_count,
            'runtime_result_files':len(runtime_results),
            'production_config_manifests':len(config_records),
            'accepted_production_config_manifests':sum(1 for x in config_records if x['validation']['accepted']),
            'closure_files':len(closure_records),
            'accepted_closure_records':sum(1 for x in closure_records if x['validation']['accepted']),
        }
    }


def apply_intake(adrs, p0_items, intake):
    a=copy.deepcopy(adrs); p=copy.deepcopy(p0_items)
    for x in intake.get('adr_approvals',[]):
        if x['validation']['accepted']: a=apply_adr_approval(a,x['record'])
    by={i.get('id'):i for i in p}
    for x in intake.get('closures',[]):
        if x['validation']['accepted']:
            r=x['record']; item=by.get(r['item_id'])
            if item:
                item['status']=r['status']; item['closure_approver']=r['approver']; item['closure_date']=r['decision_date']; item['evidence_ref']=r['evidence_ref']
    return a,p


def main():
    ap=argparse.ArgumentParser(description='NSIGHT Wave5 evidence intake and re-gate')
    ap.add_argument('--baseline',required=True); ap.add_argument('--inbox',required=True); ap.add_argument('--out',required=True)
    args=ap.parse_args(); b=Path(args.baseline); o=Path(args.out); o.mkdir(parents=True,exist_ok=True)
    adrreg=_load_json(b/'98BI-ADR-APPROVAL-REGISTER.json'); criteria=_load_json(b/'98BK-REGATE-CRITERIA.json'); matrix=_load_json(b/'96-P0-CLOSURE-MATRIX.json')
    current=_load_json(b/'98BM-REGATE-CURRENT-STATE.json')
    adrs=adrreg['adrs']; p0=select_p0_items(matrix['items'])
    intake=scan_inbox(Path(args.inbox),{a['id'] for a in adrs},{i['id'] for i in p0})
    adrs2,p02=apply_intake(adrs,p0,intake)
    runtime=intake['runtime_results']
    state=build_regate_state(adrs2,criteria['mandatory_runtime_runs'],runtime,p02,current.get('non_p0_open',[]))
    result=evaluate_regate(state)
    (o/'intake-result.json').write_text(json.dumps(intake,ensure_ascii=False,indent=2)+'\n',encoding='utf-8')
    (o/'regate-state.json').write_text(json.dumps(state,ensure_ascii=False,indent=2)+'\n',encoding='utf-8')
    (o/'regate-result.json').write_text(json.dumps(result,ensure_ascii=False,indent=2)+'\n',encoding='utf-8')
    print(json.dumps({'summary':intake['summary'],'regate':result},ensure_ascii=False,indent=2))

if __name__=='__main__': main()
