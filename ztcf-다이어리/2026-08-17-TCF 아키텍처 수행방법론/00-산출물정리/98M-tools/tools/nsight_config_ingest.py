from __future__ import annotations
import hashlib
import re
import xml.etree.ElementTree as ET
from typing import Any
from urllib.parse import urlparse
try:
    import yaml
except Exception:
    yaml = None

SENSITIVE_KEY_RE = re.compile(r'(password|passwd|secret|token|private.?key|credential|client.?secret)', re.I)
PLACEHOLDER_RE = re.compile(r'^\$\{([^}]+)\}$')

def _to_int(v: Any):
    if v is None: return None
    try: return int(str(v))
    except Exception: return v

def _clean_quotes(v: str) -> str:
    v=v.strip()
    if len(v)>=2 and v[0]==v[-1] and v[0] in {'"',"'"}: return v[1:-1]
    return v

def parse_apache(text: str) -> dict[str, Any]:
    r={'server_name':None,'listen':[],'timeout':None,'proxy_timeout':None,'keepalive':None,'keepalive_timeout':None,'balancer_members':[],'proxy_pass':[],'includes':[]}
    for raw in text.splitlines():
        line=raw.strip()
        if not line or line.startswith('#'): continue
        m=re.match(r'^ServerName\s+(.+)$',line,re.I)
        if m: r['server_name']=m.group(1).strip(); continue
        m=re.match(r'^Listen\s+(.+)$',line,re.I)
        if m: r['listen'].append(m.group(1).strip()); continue
        m=re.match(r'^Timeout\s+(\d+)',line,re.I)
        if m: r['timeout']=int(m.group(1)); continue
        m=re.match(r'^ProxyTimeout\s+(\d+)',line,re.I)
        if m: r['proxy_timeout']=int(m.group(1)); continue
        m=re.match(r'^KeepAlive\s+(\w+)',line,re.I)
        if m: r['keepalive']=m.group(1); continue
        m=re.match(r'^KeepAliveTimeout\s+(\d+)',line,re.I)
        if m: r['keepalive_timeout']=int(m.group(1)); continue
        m=re.match(r'^(?:Include|IncludeOptional)\s+(.+)$',line,re.I)
        if m: r['includes'].append(_clean_quotes(m.group(1).strip())); continue
        m=re.match(r'^BalancerMember\s+"?([^"\s]+)"?\s*(.*)$',line,re.I)
        if m:
            target,opts=m.group(1),m.group(2); u=urlparse(target)
            item={'target':target,'scheme':u.scheme or None,'host':u.hostname,'port':u.port,'route':None}
            for k,v in re.findall(r'(\w+)=([^\s]+)',opts):
                kl=k.lower()
                if kl=='route': item['route']=v
                elif kl in {'timeout','retry','connectiontimeout','loadfactor'}: item[kl]=_to_int(v)
                else: item[k]=v
            r['balancer_members'].append(item); continue
        m=re.match(r'^ProxyPass\s+"?([^"\s]+)"?\s+"?([^"\s]+)"?\s*(.*)$',line,re.I)
        if m:
            item={'path':m.group(1),'target':m.group(2)}
            for k,v in re.findall(r'(\w+)=([^\s]+)',m.group(3)): item[k]=_to_int(v) if v.isdigit() else v
            r['proxy_pass'].append(item)
    return r

def parse_server_xml(text: str) -> dict[str, Any]:
    root=ET.fromstring(text)
    r={'shutdown_port':root.attrib.get('port'),'connectors':[],'jvm_route':None,'session_manager':None,'app_base':None,'cluster':False}
    for conn in root.findall('.//Connector'):
        item=dict(conn.attrib)
        for k in ('maxThreads','minSpareThreads','acceptCount','maxConnections','connectionTimeout','keepAliveTimeout','maxKeepAliveRequests'):
            if k in item: item[k]=_to_int(item[k])
        r['connectors'].append(item)
    engine=root.find('.//Engine')
    if engine is not None: r['jvm_route']=engine.attrib.get('jvmRoute')
    host=root.find('.//Host')
    if host is not None: r['app_base']=host.attrib.get('appBase')
    cluster=root.find('.//Cluster')
    if cluster is not None:
        r['cluster']=True; manager=cluster.find('.//Manager')
        if manager is not None:
            cn=manager.attrib.get('className',''); r['session_manager']=cn.rsplit('.',1)[-1] if cn else None
    return r

def _parse_default_expr(v:str):
    m=re.fullmatch(r'\$\{([^}:]+):-([^}]+)\}',v)
    return (m.group(1),m.group(2)) if m else None

def parse_setenv(text:str)->dict[str,Any]:
    env={}; defaults={}; all_opts=[]
    for raw in text.splitlines():
        line=raw.strip()
        if not line or line.startswith('#'): continue
        m=re.match(r'^export\s+([A-Za-z_][A-Za-z0-9_]*)=(.*)$',line)
        if not m: continue
        k,v=m.group(1),_clean_quotes(m.group(2).strip()); de=_parse_default_expr(v)
        if de: defaults[k]=de[1]; env[k]=v
        else: env[k]=v
        if k in {'CATALINA_OPTS','JAVA_OPTS'}: all_opts.append(v)
    opts=' '.join(all_opts)
    def opt(p):
        m=re.search(p,opts); return m.group(1) if m else None
    gc='G1GC' if '-XX:+UseG1GC' in opts else ('ZGC' if '-XX:+UseZGC' in opts else ('ParallelGC' if '-XX:+UseParallelGC' in opts else None))
    return {'env':env,'defaults':defaults,'jvm':{'xms':opt(r'(?:^|\s)-Xms([^\s"]+)'),'xmx':opt(r'(?:^|\s)-Xmx([^\s"]+)'),'xss':opt(r'(?:^|\s)-Xss([^\s"]+)'),'gc':gc,'max_gc_pause_ms':_to_int(opt(r'-XX:MaxGCPauseMillis=(\d+)')),'max_metaspace':opt(r'-XX:MaxMetaspaceSize=([^\s"]+)'),'max_direct_memory':opt(r'-XX:MaxDirectMemorySize=([^\s"]+)')}}

def _redact(obj:Any,key_name:str|None=None):
    if key_name and SENSITIVE_KEY_RE.search(key_name): return '<REDACTED>'
    if isinstance(obj,dict): return {k:_redact(v,str(k)) for k,v in obj.items()}
    if isinstance(obj,list): return [_redact(v) for v in obj]
    return obj

def _get(d:dict,*path):
    cur=d
    for p in path:
        if not isinstance(cur,dict) or p not in cur: return None
        cur=cur[p]
    return cur

def parse_spring(text:str,suffix:str='.yml')->dict[str,Any]:
    if suffix.lower()=='.properties':
        data={}
        for raw in text.splitlines():
            line=raw.strip()
            if not line or line.startswith('#') or '=' not in line: continue
            k,v=line.split('=',1); data[k.strip()]=v.strip()
    else:
        if yaml is None: raise RuntimeError('PyYAML required')
        data=yaml.safe_load(text) or {}
    red=_redact(data)
    r={'datasources':{},'session':{},'timeout':{},'raw_redacted':red}
    ds_root=_get(red,'spring','datasource') if isinstance(red,dict) else None
    if isinstance(ds_root,dict):
        ds_iter={'default':ds_root}.items() if any(k in ds_root for k in ('url','jdbc-url','username','password','hikari')) else ds_root.items()
        for name,ds in ds_iter:
            if not isinstance(ds,dict): continue
            hk=ds.get('hikari') if isinstance(ds.get('hikari'),dict) else {}
            r['datasources'][str(name)]={'jdbc_url':ds.get('jdbc-url',ds.get('url')),'username':ds.get('username'),'password':ds.get('password'),'driver_class_name':ds.get('driver-class-name'),'hikari':{'pool_name':hk.get('pool-name'),'maximum_pool_size':hk.get('maximum-pool-size'),'minimum_idle':hk.get('minimum-idle'),'connection_timeout':hk.get('connection-timeout'),'validation_timeout':hk.get('validation-timeout'),'idle_timeout':hk.get('idle-timeout'),'max_lifetime':hk.get('max-lifetime'),'keepalive_time':hk.get('keepalive-time'),'auto_commit':hk.get('auto-commit')}}
    sess=_get(red,'nsight','session')
    if isinstance(sess,dict):
        for src,dst in [('timeout-minutes','timeout_minutes'),('absolute-timeout-minutes','absolute_timeout_minutes'),('max-session-size-kb','max_session_size_kb')]:
            if src in sess: r['session'][dst]=sess[src]
    sv=_get(red,'server','servlet','session','timeout')
    if sv is not None: r['session']['server_servlet_timeout']=sv
    tmo=_get(red,'nsight','timeout')
    if isinstance(tmo,dict):
        for src,dst in {'db-query-seconds':'db_query_seconds','transaction-seconds':'transaction_seconds','online-seconds':'online_seconds'}.items():
            if src in tmo: r['timeout'][dst]=tmo[src]
    return r

def resolve_placeholders(obj:Any,values:dict[str,Any]):
    if isinstance(obj,dict): return {k:resolve_placeholders(v,values) for k,v in obj.items()}
    if isinstance(obj,list): return [resolve_placeholders(v,values) for v in obj]
    if isinstance(obj,str):
        m=PLACEHOLDER_RE.match(obj)
        if m: return str(values.get(m.group(1),obj))
    return obj

def validate_evidence_manifest(manifest:dict[str,Any])->dict[str,Any]:
    required=['environment','hostname','source_path','sha256','captured_at','evidence_class']
    missing=[k for k in required if k not in manifest or manifest.get(k) in (None,'')]
    invalid=[]
    if str(manifest.get('hostname','')).upper() in {'UNKNOWN','N/A','NONE',''}: invalid.append('hostname')
    if str(manifest.get('environment','')).upper() not in {'PROD','DR','STG','DEV','TEST'}: invalid.append('environment')
    if not re.fullmatch(r'[0-9a-fA-F]{64}',str(manifest.get('sha256',''))): invalid.append('sha256')
    if manifest.get('evidence_class')!='PRODUCTION_RUNTIME': invalid.append('evidence_class')
    if 'T' not in str(manifest.get('captured_at','')): invalid.append('captured_at')
    moi=list(dict.fromkeys(missing+invalid))
    return {'accepted':not moi,'missing':missing,'invalid':invalid,'missing_or_invalid':moi}

def sha256_bytes(b:bytes)->str: return hashlib.sha256(b).hexdigest()


def _classify_config_path(path: str):
    p = path.lower()
    base = p.rsplit('/',1)[-1]
    if base == 'httpd.conf' or ('/conf.d/' in p and base.endswith('.conf')):
        return 'APACHE'
    if base == 'server.xml':
        return 'TOMCAT_SERVER'
    if base == 'setenv.sh':
        return 'TOMCAT_SETENV'
    if re.fullmatch(r'application(?:-[^/]+)?\.(?:yml|yaml|properties)', base):
        return 'SPRING'
    return None


def _infer_environment(path: str):
    b = path.lower().rsplit('/',1)[-1]
    if re.search(r'(?:^|[-_.])(prod|prd)(?:[-_.]|\.)', b): return 'PROD', 'INFERRED_FROM_FILENAME'
    if re.search(r'(?:^|[-_.])stg(?:[-_.]|\.)', b): return 'STG', 'INFERRED_FROM_FILENAME'
    if re.search(r'(?:^|[-_.])dev(?:[-_.]|\.)', b): return 'DEV', 'INFERRED_FROM_FILENAME'
    if re.search(r'(?:^|[-_.])test(?:[-_.]|\.)', b): return 'TEST', 'INFERRED_FROM_FILENAME'
    return 'UNKNOWN', 'UNKNOWN'


def _zip_time_iso(info):
    y,m,d,hh,mm,ss = info.date_time
    return f'{y:04d}-{m:02d}-{d:02d}T{hh:02d}:{mm:02d}:{ss:02d}'


def ingest_zip_configs(zip_path) -> list[dict[str,Any]]:
    import zipfile
    z=zipfile.ZipFile(zip_path)
    names=z.namelist()
    repo_meta=[]
    for n in names:
        if n.endswith('/.git/HEAD'):
            root=n[:-len('/.git/HEAD')]
            head=z.read(n).decode(errors='replace').strip()
            branch='UNKNOWN'; commit='UNKNOWN'
            m=re.match(r'ref:\s+refs/heads/(.+)', head)
            if m:
                branch=m.group(1)
                ref=f'{root}/.git/refs/heads/{branch}'
                if ref in names: commit=z.read(ref).decode(errors='replace').strip()
            elif re.fullmatch(r'[0-9a-fA-F]{40}',head): commit=head
            repo_meta.append((root,branch,commit))
    rows=[]
    for info in z.infolist():
        n=info.filename
        if info.is_dir(): continue
        if '/build/' in n or '/target/' in n:
            continue
        kind=_classify_config_path(n)
        if not kind: continue
        # focus on explicit config-evidence repositories/manuals, not every app resource in the codebase
        if '/znsight-config-info/' not in n:
            continue
        raw=z.read(n)
        text=raw.decode('utf-8',errors='replace')
        branch=commit='UNKNOWN'
        for root,b,c in sorted(repo_meta,key=lambda x:len(x[0]),reverse=True):
            if n.startswith(root+'/'):
                branch,commit=b,c; break
        env,env_src=_infer_environment(n)
        manifest={
            'environment':env,
            'hostname':'UNKNOWN',
            'source_path':n,
            'sha256':sha256_bytes(raw),
            'captured_at':_zip_time_iso(info),
            'evidence_class':'REFERENCE_CONFIG',
        }
        parsed={}
        parse_error=None
        try:
            if kind=='APACHE': parsed=parse_apache(text)
            elif kind=='TOMCAT_SERVER': parsed=parse_server_xml(text)
            elif kind=='TOMCAT_SETENV': parsed=parse_setenv(text)
            elif kind=='SPRING': parsed=parse_spring(text, suffix='.'+n.rsplit('.',1)[-1])
        except Exception as e:
            parse_error=f'{type(e).__name__}: {e}'
        rows.append({
            **manifest,
            'environment_source':env_src,
            'config_kind':kind,
            'repo_branch':branch,
            'repo_commit':commit,
            'size_bytes':len(raw),
            'parse_status':'PASS' if parse_error is None else 'ERROR',
            'parse_error':parse_error,
            'parsed':parsed,
            'evidence_acceptance':validate_evidence_manifest(manifest),
        })
    return rows


def ingest_evidence_dir(root_dir) -> dict[str,Any]:
    import json
    from pathlib import Path
    root=Path(root_dir)
    mp=root/'evidence-manifest.json'
    if not mp.exists():
        return {'bundle_acceptance':{'accepted':False,'reasons':['manifest_missing']},'files':[]}
    manifest=json.loads(mp.read_text(encoding='utf-8'))
    base_manifest={
        'environment':manifest.get('environment'),
        'hostname':manifest.get('hostname'),
        'source_path':'BUNDLE',
        'sha256':'0'*64,
        'captured_at':manifest.get('captured_at'),
        'evidence_class':manifest.get('evidence_class'),
    }
    provenance=validate_evidence_manifest(base_manifest)
    rows=[]; bundle_reasons=[]
    if not provenance['accepted']:
        bundle_reasons.extend('manifest_'+x for x in provenance['missing_or_invalid'])
    for spec in manifest.get('files',[]):
        rel=spec.get('path'); expected=spec.get('sha256')
        f=root/rel if rel else None
        reasons=[]
        if not rel or f is None or not f.exists():
            reasons.append('file_missing')
            rows.append({'source_path':rel,'rejection_reasons':reasons,'hash_match':False,'evidence_acceptance':{'accepted':False},'parse_status':'NOT_RUN','parsed':{}})
            continue
        raw=f.read_bytes(); actual=sha256_bytes(raw); hash_match=(actual.lower()==str(expected).lower())
        if not hash_match: reasons.append('hash_mismatch')
        kind=_classify_config_path(rel)
        if not kind: reasons.append('unsupported_config_kind')
        per_manifest={
            'environment':manifest.get('environment'),
            'hostname':manifest.get('hostname'),
            'source_path':rel,
            'sha256':actual,
            'captured_at':manifest.get('captured_at'),
            'evidence_class':manifest.get('evidence_class'),
        }
        acc=validate_evidence_manifest(per_manifest)
        if not acc['accepted']: reasons.extend('manifest_'+x for x in acc['missing_or_invalid'])
        parsed={}; parse_error=None
        if kind:
            try:
                text=raw.decode('utf-8',errors='replace')
                if kind=='APACHE': parsed=parse_apache(text)
                elif kind=='TOMCAT_SERVER': parsed=parse_server_xml(text)
                elif kind=='TOMCAT_SETENV': parsed=parse_setenv(text)
                elif kind=='SPRING': parsed=parse_spring(text, suffix='.'+rel.rsplit('.',1)[-1])
            except Exception as e:
                parse_error=f'{type(e).__name__}: {e}'; reasons.append('parse_error')
        row={
            **per_manifest,
            'instance_id':spec.get('instance_id'),
            'config_kind':kind,
            'expected_sha256':expected,
            'hash_match':hash_match,
            'parse_status':'PASS' if parse_error is None and kind else 'ERROR',
            'parse_error':parse_error,
            'parsed':parsed,
            'evidence_acceptance':acc,
            'rejection_reasons':list(dict.fromkeys(reasons)),
        }
        rows.append(row)
        if reasons: bundle_reasons.extend(f'{rel}:{x}' for x in reasons)
    return {'manifest':manifest,'bundle_acceptance':{'accepted':not bundle_reasons,'reasons':bundle_reasons},'files':rows}


def _main():
    import argparse, json, csv
    from pathlib import Path
    p=argparse.ArgumentParser(description='NSIGHT production config evidence ingestion/validation tool')
    sub=p.add_subparsers(dest='cmd',required=True)
    pz=sub.add_parser('scan-zip'); pz.add_argument('zip_path'); pz.add_argument('--json',dest='json_out'); pz.add_argument('--csv',dest='csv_out')
    pd=sub.add_parser('scan-dir'); pd.add_argument('root_dir'); pd.add_argument('--json',dest='json_out',required=True)
    args=p.parse_args()
    if args.cmd=='scan-zip':
        result=ingest_zip_configs(args.zip_path)
        if args.json_out:
            Path(args.json_out).write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding='utf-8')
        if args.csv_out:
            fields=['source_path','config_kind','environment','environment_source','hostname','evidence_class','repo_branch','repo_commit','sha256','captured_at','size_bytes','parse_status','parse_error','accepted']
            with open(args.csv_out,'w',newline='',encoding='utf-8-sig') as f:
                w=csv.DictWriter(f,fieldnames=fields); w.writeheader()
                for r in result:
                    w.writerow({**{k:r.get(k) for k in fields if k!='accepted'},'accepted':r['evidence_acceptance']['accepted']})
        print(json.dumps({'files':len(result),'accepted':sum(1 for r in result if r['evidence_acceptance']['accepted']),'parse_errors':sum(1 for r in result if r['parse_status']!='PASS')},ensure_ascii=False))
        return 0
    result=ingest_evidence_dir(args.root_dir)
    Path(args.json_out).write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding='utf-8')
    print(json.dumps({'accepted':result['bundle_acceptance']['accepted'],'files':len(result['files'])},ensure_ascii=False))
    return 0 if result['bundle_acceptance']['accepted'] else 2

if __name__=='__main__':
    raise SystemExit(_main())
