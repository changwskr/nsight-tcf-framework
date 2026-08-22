# 25. NSIGHT HW/SW Dependency Architecture

## 1. 목적

HW/SW 도입 일정은 단순 PM 일정이 아니라 **아키텍처 검증 가능 시점을 결정하는 Critical Dependency Chain**으로 관리한다.

## 2. Dependency Chain

```text
IaaS / Appliance / Storage
  ↓
OS / Network / Security
  ↓
Apache / Tomcat / DB / Middleware
  ↓
Framework / SSO / Gateway
  ↓
Application
  ↓
Data Migration / CDC / ETL
  ↓
Performance Test
  ↓
HA / DR Test
  ↓
Cutover
```

## 3. 확인된 환경별 흐름

개발·선도개발은 GitLab/GitRunner, Framework 관리, Nexus, MP WEB/WAS, RDW 등을 중심으로 Reference 검증환경을 제공한다.

운영은 MP/RD/AD/DG/BL/IM 영역으로 확장한다.

DR은 Exadata, Storage, Oracle DBMS, WebTopSuite, CUNI 등의 구축 완료 후 Failover/Failback 검증이 가능하다.

## 4. DR 일정 의존성

자료상 DR 주요 도입은 단계적으로 진행되며, 최종적으로 구성점검과 Failover/Failback 검증이 뒤따른다.

따라서 아래 상태를 구분한다.

```text
HW INSTALLED
≠ MIDDLEWARE READY
≠ APPLICATION READY
≠ DATA SYNC READY
≠ DR TESTED
≠ ARCHITECTURE PASS
```

## 5. Architecture Blocking Rules

| Dependency | 미완료 시 영향 |
|---|---|
| DR DB/Storage | RPO/RTO Test 불가 |
| GSLB/L4 | Center Failover Test 불가 |
| Apache/Tomcat Config | JVM/Route HA 검증 불가 |
| APM/Monitoring | Runtime Evidence 수집 불가 |
| CI/CD | 반복 배포/롤백 검증 불가 |
| Migration Tool/Stage | Cutover Rehearsal 불가 |
| SSO/JWT Key | Security End-to-End Test 불가 |

## 6. Gate

G70에서는 일정 자체를 PASS/FAIL하지 않고, **Critical Architecture Test를 막는 미도입/미구성 요소를 Blocking Dependency로 등록**한다.

현재 판정: **CONDITIONAL PASS**
