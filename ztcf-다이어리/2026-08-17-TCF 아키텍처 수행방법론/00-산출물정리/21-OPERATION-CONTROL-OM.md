# 21. NSIGHT Operation / Control / OM Architecture

## 1. 목적

NSIGHT 운영 아키텍처는 **운영자가 정책을 관리하는 Control Plane**과 **실제 거래가 정책을 적용받아 실행되는 Runtime Plane**을 분리한다.

PDMG AS-IS에서는 운영 기능이 `pdmg-ui + pdmg-service + pdmg-fw`에 분산되어 있으며, NSIGHT TO-BE는 이를 전체 WAR/Tomcat JVM/ServiceId를 통합 관찰·통제하는 OM Control Plane으로 발전시키는 것을 목표로 한다.

## 2. 전체 구조

```text
                    CONTROL PLANE
┌─────────────────────────────────────────────┐
│ OM / 운영자 UI                              │
│                                             │
│ Service Catalog                             │
│ Transaction Control                         │
│ Timeout Policy                              │
│ User/Role/Menu                              │
│ Config / Common Code / Error Code           │
│ Session / Cache / Batch / Deploy            │
│ Runtime Dashboard / Diagnosis               │
│ Audit / Approval                            │
└─────────────────────┬───────────────────────┘
                      │ Policy / Command
                      ▼
                    RUNTIME PLANE
┌─────────────────────────────────────────────┐
│ Request → TCF → STF → Control Check         │
│                ↓                            │
│          ALLOW / BLOCK                      │
│                ↓                            │
│ Timeout → Dispatcher → Business → DB        │
│                ↓                            │
│ Runtime / Log / Metric / ImageLog           │
└─────────────────────┬───────────────────────┘
                      │ Evidence
                      └──────────→ CONTROL PLANE
```

## 3. 거래통제 책임

관리와 집행을 분리한다.

```text
관리: 운영자 → OM/UI → 관리 ServiceId → Control Repository
집행: Request → STF → Control Service → ALLOW/BLOCK
```

PDMG Reference에서 확인된 통제 단위:

- GLOBAL
- BUSINESS
- SERVICE
- CHANNEL
- BRANCH
- USER
- IP

시간대·단말종류·긴급기간·자동만료 등은 TO-BE 확장 후보로 관리한다.

## 4. Control Plane 보호

운영 복구 기능이 업무 통제로 스스로 차단되지 않도록 별도 Exemption Policy가 필요하다.

Reference 예:

- 거래통제 관리 ServiceId
- Runtime 진단 ServiceId
- 로그인/JWT 관리
- Health Check

이 목록은 Source에서 자동 추출하여 OM Catalog와 일치 여부를 검증해야 한다.

## 5. Fail-Closed 원칙

거래통제 판단 저장소 또는 정책 조회가 실패한 경우 임의 허용하지 않는다.

```text
Control Check 성공 + ALLOW → 실행
Control Check 성공 + BLOCK → 차단
Control Check 실패         → 기본 차단 / 운영 예외정책
```

단, Health/복구용 Control Plane까지 동시에 차단하지 않도록 별도의 Emergency Access Rule이 필요하다.

## 6. 운영 변경 통제

중요 운영정책 변경은 다음 체인을 요구한다.

```text
Operator
  ↓
Authorization
  ↓
Change Reason
  ↓
Approval
  ↓
Effective Time / Expiration
  ↓
Audit Log
  ↓
Runtime Apply
  ↓
Verification
  ↓
Rollback
```

특히 GLOBAL Allow/Block, JWT Key, Timeout, Route, DB Pool, Deploy, Session 정책은 Critical Change로 분류한다.

## 7. OM Runtime Catalog

최소 관리모델:

```text
System
 ↓
Application / WAR
 ↓
ServiceId
 ↓
Tomcat JVM
 ↓
Host
 ↓
Thread / Transaction
 ↓
DB Pool / SQL / Integration
 ↓
Metric / Log / Alert
```

OM이 단일 `pdmg-service` Snapshot에 머물러서는 안 되며 전체 NSIGHT Runtime을 통합해야 한다.

## 8. Monitoring 책임 분리

| 영역 | Owner |
|---|---|
| Infra CPU/MEM/Disk/Network | Infra Monitoring |
| Apache/Tomcat/JVM | Middleware/APM |
| ServiceId/TX/Timeout | TCF/OM |
| SQL/DB Wait | DB/APM |
| Business Error | Application/OM |
| Security/Audit | Security/OM |
| Deploy/Version | CI/CD/OM |

모든 영역은 GUID/ServiceId/Host/JVM을 공통 상관키로 연결한다.

## 9. 운영 Runbook 표준

각 Alert/Control은 반드시 Runbook ID를 가진다.

| Event | Runbook 예 |
|---|---|
| Tomcat JVM Down | JVM 재기동/Pool 제외/Peer 확인 |
| Hikari Exhaustion | Pending/SQL/DB Session 분석 |
| Slow SQL | SQL ID→Plan/AWR/ASH 분석 |
| Transaction Timeout | ServiceId→SQL/External/Thread 분석 |
| JWT Key Error | Issuer/JWKS/Key Version 확인 |
| Center Failure | GSLB/L4 DR 전환 절차 |
| Deployment Failure | 이전 Artifact Rollback |

## 10. 주요 GAP

| GAP ID | 내용 | 우선순위 |
|---|---|---:|
| OM-G01 | 독립 OM Control Plane의 실제 Runtime Scope 미확정 | P0 |
| OM-G02 | 다중 WAR/JVM/Host 통합 Catalog 부재 | P0 |
| OM-G03 | 운영 변경 2인 승인/자동만료/롤백 정책 미완성 | P0 |
| OM-G04 | Slow Transaction/SQL 실제 추적 기능 미완성 | P0 |
| OM-G05 | Control Exemption Catalog 자동검증 부재 | P1 |
| OM-G06 | Alert→Runbook→Evidence 폐쇄루프 미완성 | P0 |

## 11. Gate

현재 판정: **CONDITIONAL PASS**

PASS 조건:

- Control Plane/Runtime Plane Owner 확정
- 전체 Runtime Catalog 생성
- Critical Change Approval/Audit
- Alert/Runbook 연결
- OM↔Runtime Evidence 자동 수집
