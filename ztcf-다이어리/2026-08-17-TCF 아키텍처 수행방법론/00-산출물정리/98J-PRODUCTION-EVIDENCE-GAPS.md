# 98J. Production Mapping Evidence Gaps

> 목적: Wave 2B의 UNKNOWN 필드를 운영증적으로 치환하기 위한 수집 계약.

## A. WEB
각 WEB Hostname마다 다음을 수집한다.

```text
hostname
apache_instance_id
httpd_conf_path
include_paths
listen_port
virtualhost
proxy/worker target WAS
health_uri
TLS termination
L4 pool/vip
```

## B. WAS / Tomcat
각 WAS Hostname마다 다음을 수집한다.

```text
hostname
os_account
jvm_id
pid
CATALINA_HOME
CATALINA_BASE
server.xml
connector_port
jvmRoute
setenv.sh
Xms/Xmx
GC
maxThreads
application/war/context
session manager
```

## C. Application / ServiceId

```text
was_hostname
jvm_id
war
context_path
application_code
serviceid
handler
facade
service
version/build_sha
```

Source에 ServiceId가 존재한다는 사실만으로 Production 배포를 증명하지 않는다.

## D. DB / Integration

```text
war/serviceid
datasource_name
jdbc_url_logical_name
rdw/adw/other
hikari_pool
external target
protocol
route/gateway
```

Credential/Secret 원문은 증적에 포함하지 않는다.

## E. HA / DR

```text
ha_group
member_hostname/jvm
load_balancer_pool
health_check
session strategy
dr_target
rto
rpo
failover owner
failback owner
last_test_timestamp
```

## F. Evidence acceptance

Config 파일은 반드시 `Environment + Hostname + Path + Version/Hash + Timestamp`를 함께 등록한다. 파일명만 같은 설정파일은 Production Evidence로 인정하지 않는다.
