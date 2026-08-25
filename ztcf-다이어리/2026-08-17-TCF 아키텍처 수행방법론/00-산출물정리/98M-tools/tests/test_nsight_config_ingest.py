import json
import sys
from pathlib import Path
sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'tools'))

from nsight_config_ingest import (
    parse_apache,
    parse_server_xml,
    parse_setenv,
    parse_spring,
    resolve_placeholders,
    validate_evidence_manifest,
)


def test_parse_apache_routes_and_balancer_members():
    text = '''
ServerName nh.marketing.com:443
Listen 80
Listen 443 https
ProxyTimeout 10
<Proxy "balancer://nsight_tomcat_cluster">
BalancerMember "http://10.10.20.11:8080" route=tc01 retry=0
BalancerMember "http://10.10.20.12:8080" route=tc02 retry=0
</Proxy>
ProxyPass "/portal/" "balancer://nsight_tomcat_cluster/portal/" timeout=10
'''
    r = parse_apache(text)
    assert r['server_name'] == 'nh.marketing.com:443'
    assert r['listen'] == ['80', '443 https']
    assert r['proxy_timeout'] == 10
    assert r['balancer_members'][0]['host'] == '10.10.20.11'
    assert r['balancer_members'][0]['port'] == 8080
    assert r['balancer_members'][0]['route'] == 'tc01'
    assert r['proxy_pass'][0]['path'] == '/portal/'
    assert r['proxy_pass'][0]['target'].startswith('balancer://')


def test_parse_tomcat_server_xml():
    text = '''<?xml version="1.0"?>
<Server port="${shutdown.port}" shutdown="SHUTDOWN">
<Service name="Catalina">
<Connector port="${http.port}" maxThreads="800" minSpareThreads="150" acceptCount="500" />
<Engine name="Catalina" jvmRoute="${jvmRoute}">
<Cluster><Manager className="org.apache.catalina.ha.session.DeltaManager" /></Cluster>
<Host name="localhost" appBase="webapps" />
</Engine>
</Service>
</Server>'''
    r = parse_server_xml(text)
    assert r['shutdown_port'] == '${shutdown.port}'
    assert r['connectors'][0]['port'] == '${http.port}'
    assert r['connectors'][0]['maxThreads'] == 800
    assert r['jvm_route'] == '${jvmRoute}'
    assert r['session_manager'] == 'DeltaManager'
    assert r['app_base'] == 'webapps'


def test_parse_setenv_and_jvm_options():
    text = '''
export JAVA_HOME=/usr/lib/jvm/java-21
export CATALINA_BASE=/app/tomcat-nsight
export JVM_ROUTE=${JVM_ROUTE:-tc01}
export HTTP_PORT=${HTTP_PORT:-19000}
export CATALINA_OPTS="$CATALINA_OPTS -Xms24g -Xmx24g -Xss512k"
export CATALINA_OPTS="$CATALINA_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
'''
    r = parse_setenv(text)
    assert r['env']['JAVA_HOME'] == '/usr/lib/jvm/java-21'
    assert r['env']['CATALINA_BASE'] == '/app/tomcat-nsight'
    assert r['defaults']['JVM_ROUTE'] == 'tc01'
    assert r['defaults']['HTTP_PORT'] == '19000'
    assert r['jvm']['xms'] == '24g'
    assert r['jvm']['xmx'] == '24g'
    assert r['jvm']['gc'] == 'G1GC'
    assert r['jvm']['max_gc_pause_ms'] == 200


def test_resolve_placeholders_with_setenv_defaults():
    obj = {'port': '${http.port}', 'route': '${jvmRoute}', 'other': 'x'}
    env = {'http.port': '19000', 'jvmRoute': 'tc01'}
    assert resolve_placeholders(obj, env) == {'port': '19000', 'route': 'tc01', 'other': 'x'}


def test_parse_spring_redacts_secrets_and_extracts_hikari():
    text = '''
spring:
  datasource:
    rdw:
      jdbc-url: jdbc:oracle:thin:@//rdw-vip:1521/RDW
      username: ${RDW_USER}
      password: ${RDW_PASSWORD}
      hikari:
        maximum-pool-size: 150
        connection-timeout: 3000
nsight:
  session:
    timeout-minutes: 90
  timeout:
    db-query-seconds: 3
    transaction-seconds: 5
'''
    r = parse_spring(text, suffix='.yml')
    assert r['datasources']['rdw']['jdbc_url'].endswith('/RDW')
    assert r['datasources']['rdw']['username'] == '${RDW_USER}'
    assert r['datasources']['rdw']['password'] == '<REDACTED>'
    assert r['datasources']['rdw']['hikari']['maximum_pool_size'] == 150
    assert r['session']['timeout_minutes'] == 90
    assert r['timeout']['db_query_seconds'] == 3


def test_manifest_requires_production_provenance():
    manifest = {
        'environment': 'PROD',
        'hostname': 'sbmpcolows01',
        'source_path': '/app/tomcat-a/conf/server.xml',
        'sha256': 'a' * 64,
        'captured_at': '2026-08-19T08:00:00+09:00',
        'evidence_class': 'PRODUCTION_RUNTIME',
    }
    ok = validate_evidence_manifest(manifest)
    assert ok['accepted'] is True
    assert ok['missing'] == []


def test_manifest_rejects_reference_without_hostname():
    manifest = {
        'environment': 'PROD',
        'hostname': 'UNKNOWN',
        'source_path': 'repo/reference/server.xml',
        'sha256': 'b' * 64,
        'captured_at': '2026-08-19T08:00:00+09:00',
        'evidence_class': 'REFERENCE_CONFIG',
    }
    r = validate_evidence_manifest(manifest)
    assert r['accepted'] is False
    assert 'hostname' in r['missing_or_invalid']
    assert 'evidence_class' in r['missing_or_invalid']


def test_ingest_zip_configs_excludes_generated_and_keeps_tomcat_bin(tmp_path):
    import zipfile
    from nsight_config_ingest import ingest_zip_configs
    zp = tmp_path / 'x.zip'
    prefix = 'root/znsight-config-info/nsight_env_config_one/'
    with zipfile.ZipFile(zp, 'w') as z:
        z.writestr(prefix + '.git/HEAD', 'ref: refs/heads/main\n')
        z.writestr(prefix + '.git/refs/heads/main', '1234567890abcdef1234567890abcdef12345678\n')
        z.writestr(prefix + 'sample/01-apache/httpd.conf', 'Listen 443\n')
        z.writestr(prefix + 'sample/02-tomcat/bin/setenv.sh', 'export CATALINA_BASE=/a\n')
        z.writestr(prefix + 'sample/02-tomcat/conf/server.xml', '<Server port="8005"><Service><Connector port="8080"/></Service></Server>')
        z.writestr(prefix + 'sample/03-spring/src/main/resources/application-prod.yml', 'spring: {}\n')
        z.writestr(prefix + 'sample/03-spring/target/classes/application-prod.yml', 'spring: {}\n')
    rows = ingest_zip_configs(zp)
    paths = [r['source_path'] for r in rows]
    assert any(p.endswith('/02-tomcat/bin/setenv.sh') for p in paths)
    assert not any('/target/' in p for p in paths)
    assert len(rows) == 4
    assert all(r['repo_branch'] == 'main' for r in rows)
    assert all(r['repo_commit'].startswith('1234567') for r in rows)
    prod = next(r for r in rows if r['source_path'].endswith('application-prod.yml'))
    assert prod['environment'] == 'PROD'
    assert prod['environment_source'] == 'INFERRED_FROM_FILENAME'
    assert prod['hostname'] == 'UNKNOWN'
    assert prod['evidence_acceptance']['accepted'] is False


def test_ingest_evidence_dir_accepts_hash_bound_production_bundle(tmp_path):
    import hashlib, json
    from nsight_config_ingest import ingest_evidence_dir
    cfg = tmp_path / 'tomcat' / 'jvm01' / 'conf' / 'server.xml'
    cfg.parent.mkdir(parents=True)
    body = b'<Server port="8005"><Service><Connector port="19000" maxThreads="800"/></Service></Server>'
    cfg.write_bytes(body)
    manifest = {
        'schema_version': '1.0',
        'environment': 'PROD',
        'hostname': 'sbmpcolows01',
        'captured_at': '2026-08-19T08:30:00+09:00',
        'evidence_class': 'PRODUCTION_RUNTIME',
        'files': [
            {'path': 'tomcat/jvm01/conf/server.xml', 'sha256': hashlib.sha256(body).hexdigest(), 'instance_id': 'jvm01'}
        ]
    }
    (tmp_path/'evidence-manifest.json').write_text(json.dumps(manifest), encoding='utf-8')
    result = ingest_evidence_dir(tmp_path)
    assert result['bundle_acceptance']['accepted'] is True
    assert result['files'][0]['hash_match'] is True
    assert result['files'][0]['evidence_acceptance']['accepted'] is True
    assert result['files'][0]['parsed']['connectors'][0]['port'] == '19000'


def test_ingest_evidence_dir_rejects_hash_mismatch(tmp_path):
    import json
    from nsight_config_ingest import ingest_evidence_dir
    cfg = tmp_path/'apache'/'httpd.conf'
    cfg.parent.mkdir(parents=True)
    cfg.write_text('Listen 443\n', encoding='utf-8')
    manifest = {
        'schema_version': '1.0', 'environment':'PROD','hostname':'sbmpcolowb01',
        'captured_at':'2026-08-19T08:30:00+09:00','evidence_class':'PRODUCTION_RUNTIME',
        'files':[{'path':'apache/httpd.conf','sha256':'0'*64}]
    }
    (tmp_path/'evidence-manifest.json').write_text(json.dumps(manifest), encoding='utf-8')
    result=ingest_evidence_dir(tmp_path)
    assert result['bundle_acceptance']['accepted'] is False
    assert result['files'][0]['hash_match'] is False
    assert 'hash_mismatch' in result['files'][0]['rejection_reasons']


def test_cli_scan_dir_writes_json(tmp_path):
    import hashlib, json, subprocess, sys
    cfg=tmp_path/'apache'/'httpd.conf'; cfg.parent.mkdir(parents=True); cfg.write_text('Listen 443\n',encoding='utf-8')
    h=hashlib.sha256(cfg.read_bytes()).hexdigest()
    manifest={'schema_version':'1.0','environment':'PROD','hostname':'sbmpcolowb01','captured_at':'2026-08-19T08:30:00+09:00','evidence_class':'PRODUCTION_RUNTIME','files':[{'path':'apache/httpd.conf','sha256':h}]}
    (tmp_path/'evidence-manifest.json').write_text(json.dumps(manifest),encoding='utf-8')
    out=tmp_path/'out.json'
    script=Path(__file__).resolve().parents[1]/'tools'/'nsight_config_ingest.py'
    p=subprocess.run([sys.executable,str(script),'scan-dir',str(tmp_path),'--json',str(out)],capture_output=True,text=True)
    assert p.returncode == 0
    data=json.loads(out.read_text(encoding='utf-8'))
    assert data['bundle_acceptance']['accepted'] is True
