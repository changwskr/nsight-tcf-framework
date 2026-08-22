# 98M. P0 Closure Wave 2C — Production Config Evidence Ingestion

> 판정: **INGESTION READY / PRODUCTION EVIDENCE NOT YET ACCEPTED**  
> Gate: `G80 HOLD / HG90 HOLD` 유지

## 1. 목적

Wave 2B에서 남은 `71 Server → Apache → Tomcat JVM → WAR → ServiceId → DB/DR`의 UNKNOWN을 실제 운영 설정으로 치환하기 위한 수집·파싱·검증 계층을 만든다.

```text
Production Host
   ↓
Evidence Bundle
   ↓
Manifest + SHA-256
   ↓
Config Parser
   ↓
Evidence Validator
   ↓
Normalized Configuration Inventory
   ↓
71 Server Production Mapping
   ↓
G80 Re-Gate
```

## 2. Production Evidence Acceptance Contract

운영설정은 파일명이나 `prod` 문자열만으로 Production Evidence가 되지 않는다.

필수 식별자는 다음과 같다.

```text
Environment
+ Hostname
+ Source Path
+ SHA-256
+ Captured Timestamp
+ Evidence Class = PRODUCTION_RUNTIME
```

하나라도 없으면 `REFERENCE/CANDIDATE`로 유지한다.

## 3. 지원 Config

| 종류 | 추출 항목 |
|---|---|
| Apache `httpd.conf/conf.d` | ServerName, Listen, Timeout, ProxyTimeout, Include, BalancerMember, ProxyPass |
| Tomcat `server.xml` | Shutdown/Connector Port, maxThreads, minSpareThreads, acceptCount, jvmRoute, Cluster/Session Manager, appBase |
| Tomcat `setenv.sh` | JAVA_HOME, CATALINA_HOME/BASE, JVM_ROUTE/HTTP_PORT defaults, Xms/Xmx/Xss, GC |
| Spring `application*.yml/properties` | Datasource logical URL, Hikari, Session, Timeout; secret-like keys redacted |

## 4. 현재 Source Snapshot 자동 스캔

`nsight-tcf-framework (2).zip`의 `znsight-config-info`를 Canonical Candidate 기준으로 스캔했다. `build/`, `target/` 생성복제는 제외하되 Tomcat의 실제 `bin/setenv.sh`는 포함했다.

| 결과 | 값 |
|---|---:|
| Config 후보 | **122** |
| Apache | **40** |
| Tomcat `server.xml` | **8** |
| Tomcat `setenv.sh` | **8** |
| Spring Config | **66** |
| Parser PASS | **122 / 122** |
| Unique SHA-256 | **102** |
| Exact Duplicate Group | **16** |
| Production Accepted | **0 / 122** |

Production Accepted가 0인 이유는 Parser 실패가 아니라 **운영 Hostname/캡처시점/운영 Source Provenance가 파일에 결합되어 있지 않기 때문**이다.

## 5. Config Repository Provenance

| Config Source | Branch | Commit | 파일수 | 판정 |
|---|---|---|---:|---|
| `nsight_env_config_one` | `main` | `4aed7fbdcd97f30e65ef6cbb72a975644e3803eb` | 92 | REFERENCE/CANDIDATE |
| `nsight_env_config` | `master` | `caf4db8952fb360a196b8752ed2a726f2eddbc12` | 9 | REFERENCE/CANDIDATE |
| `nsight_system_manual` | UNKNOWN | UNKNOWN | 21 | MANUAL/REFERENCE |

Git Commit은 Config 파일 버전 추적에는 유용하지만 **어느 운영 Host에 실제 적용되었는지**를 증명하지 않는다.

## 6. Reference Config Variant / Conflict 발견

현재 Config Repository에는 단일 운영값이 아니라 여러 설계 Style과 산정안이 함께 존재한다.

| 항목 | 발견값 | 판정 |
|---|---|---|
| Apache `ProxyTimeout` | 10 / 30 sec | REFERENCE VARIANT |
| Tomcat Connector | 8080 / `${http.port}` | REFERENCE VARIANT |
| Tomcat `maxThreads` | 500 / 1600 / 3200 | **CONFLICT/VARIANT** |
| Session Manager | DeltaManager 존재 / Spring Session JDBC Manual 존재 | **STRATEGY VARIANT** |
| JAVA_HOME | Java 17 경로 존재 | Current Java 21 Source Baseline과 분리 필요 |
| JVM Xmx | 12g / 48g / 64g / 192g | **CAPACITY VARIANT** |
| RDW Hikari max | 8 / 32 / 50 / 200 등 | **CAPACITY VARIANT** |
| Session | `60m` / absolute `480m` 등 | **POLICY VARIANT** |
| DB Query / TX Timeout | 3s / 5s 조합 반복 | Working Reference |

이 값들은 실제 Host Evidence가 들어오기 전에는 `Current Production`으로 승격하지 않는다.

## 7. 운영 증적 Bundle 구조

```text
PROD-sbmpcolows01/
├─ evidence-manifest.json
├─ apache/
│  ├─ httpd.conf
│  └─ conf.d/
├─ tomcat/
│  ├─ JVM01/
│  │  ├─ conf/server.xml
│  │  └─ bin/setenv.sh
│  └─ JVM02/...
├─ app/
│  └─ <application>/application-prod.yml
└─ runtime/
   ├─ ps-ef.txt
   ├─ catalina-base.txt
   └─ webapps.txt
```

WEB Host는 Apache 중심, WAS Host는 Tomcat JVM별 Bundle로 만든다.

## 8. Evidence가 들어오면 자동으로 닫는 관계

```text
httpd.conf
  → Listener / Proxy / Balancer
  → WAS Target

server.xml + setenv.sh
  → Hostname / JVM / CATALINA_BASE / Connector / Thread

webapps/deploy manifest
  → JVM / WAR / Context

application-prod.yml
  → WAR / Hikari / Datasource / Session / Timeout

OM/Service Catalog
  → WAR / ServiceId
```

## 9. Security Rule

- 비밀번호, Token, Private Key를 보고서/JSON으로 출력하지 않는다.
- Parser는 secret-like key를 `<REDACTED>` 처리한다.
- Private Key 원문을 Evidence Package에 넣지 않는다.
- 설정 원문에 실제 Secret이 포함되어 있으면 보안통제 하에서 내부 검증하고, 외부 전달본은 별도 Sanitization 절차를 따른다.

## 10. Wave 2C 판정

**`P0-PHY-001 = INGESTION_READY / BLOCKED_PRODUCTION_EVIDENCE`**

- 71 Server Mapping: 존재
- Production Config Schema: 생성
- Parser/Validator: 생성
- Reference Config 122개: 파싱 완료
- 실제 운영 Hostname-bound Config: 미수집
- Production Accepted Evidence: 0개

따라서 G80/HG90 HOLD는 유지한다.
