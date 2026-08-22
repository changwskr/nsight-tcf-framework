# 22. NSIGHT HA / DR Architecture

## 1. 목적

NSIGHT HA/DR은 단순히 서버를 두 대 두는 것이 아니라 **장애 단위를 식별하고, 탐지→격리→우회→복구→Failback→증적**까지 정의하는 서비스 연속성 아키텍처다.

## 2. Availability Baseline

전략 자료에서 확인되는 방향:

- AP 레벨 Active-Active
- 도메인별 기능 서버 독립 분리
- RDW/ADW 자원 분리
- GSLB/L4 기반 센터/노드 우회
- 운영/DR 서버 Pair 존재
- 농협 표준 RTO 30분 이내 기준이 전략 문서에 제시됨

RPO는 대상 데이터/시스템별 실제 기준 Evidence가 부족하므로 **UNKNOWN**으로 유지한다.

## 3. Failure Domain

| 장애 단위 | 탐지 | 우회/복구 |
|---|---|---|
| Apache Process | L4/Process Health | Peer WEB |
| WEB VM | L4 Health | Peer WEB |
| Tomcat JVM | Health/JMX/APM | Peer JVM |
| WAS VM | L4/Apache Health | Peer WAS |
| Application | Service Health | Peer Application JVM |
| Thread Exhaustion | Busy/Pending | Pool 제외/증설/원인 제거 |
| Hikari Exhaustion | Pool Pending | SQL/DB Session 조치 |
| DB Node | DB HA | RAC/DG/DB 정책 |
| External System | Timeout/CB | Fail-fast/대체/보상 |
| Center | GSLB/운영 판단 | DR Center |

## 4. 운영 노드 HA

```text
                GSLB
                  ↓
                 L4
         ┌────────┴────────┐
         ▼                 ▼
     WEB/APACHE01      WEB/APACHE02
         │  \             /  │
         │   \           /   │
         ▼    ▼         ▼    ▼
      WAS01               WAS02
   ┌──────────┐        ┌──────────┐
   │ JVM A    │        │ JVM A    │ ← App A HA
   │ JVM B    │        │ JVM B    │ ← App B HA
   └──────────┘        └──────────┘
```

운영은 WEB↔WAS 고정 1:1이 아니라 Peer JVM으로 우회 가능한 Cross Routing을 기준으로 한다. 실제 `httpd.conf`/L4 설정은 아직 Evidence 확보가 필요하다.

## 5. Session HA

현재 가장 Source-supported한 Working Candidate는 **센터 내부 DeltaManager + L4 Sticky** 구조다.

```text
GSLB → Center L4
          ↓ Sticky
     Tomcat JVM A
          ↔ DeltaManager
     Tomcat JVM B
```

Reference/Working Rule:

- 센터 내부 AP Cluster에 한정
- 동일 Application Cluster별 분리
- Sticky Session 적용
- `<distributable/>`
- Serializable Session Object
- `jvmRoute`
- 세션 객체 최소화
- 센터 장애 시 재로그인 후보정책

그러나 Spring Session JDBC 등 대안도 프로젝트에서 논의되었으므로 **최종 Session Strategy는 ADR 대상**이다.

## 6. Center DR

대표 Pair 모델:

```text
PROD #01/#02                  DR #51/#52
WEB 01/02      ───────────→   WEB 51/52
WAS 01/02      ───────────→   WAS 51/52
RDW 01/02      ───────────→   RDW 51/52
```

실제 Pair 전수 Catalog와 RTO/RPO는 아직 미완료다.

## 7. Center Failure Capacity

G60 Working Capacity:

```text
16Core Working = 855 TPS/VM
Operational 80% = 684 TPS/VM
```

센터에 2 VM이 남는 경우:

```text
684 × 2 = 1,368 TPS
```

따라서 Peak 1,200 TPS는 산술상 수용하지만, **센터 장애 후 추가 1 VM 장애까지 보장하는 것은 아니다.**

따라서 topology 후보:

| 후보 | 장점 | 위험 |
|---|---|---|
| 2+2 | 자원 효율 | 센터장애 후 추가 노드장애 취약 |
| 3+3 | N+1 여유 | 비용/DB Session 증가 |
| 8Core Scale-Out | 장애단위 축소 | 운영노드 수 증가 |

최종안은 Performance + DR Test + DB Session 상한을 함께 검증하여 ADR로 결정한다.

## 8. Failover / Failback 표준절차

```text
Detect
 ↓
Impact Scope 확인
 ↓
Node/Center 격리
 ↓
Traffic Reroute
 ↓
Session/Data Consistency 확인
 ↓
Residual Capacity 확인
 ↓
Business Validation
 ↓
Service Restore 선언
 ↓
Root Cause / Repair
 ↓
Failback Plan
 ↓
Controlled Failback
 ↓
Post Validation / Evidence
```

자동 Failover와 Human Decision이 필요한 Center DR을 구분한다.

## 9. RTO / RPO Matrix

| 서비스 | RTO | RPO | 상태 |
|---|---|---|---|
| Online AP | 전략상 30분 이내 기준 존재 | UNKNOWN | OPEN |
| RDW | 확인 필요 | 확인 필요 | OPEN |
| ADW | 확인 필요 | 확인 필요 | OPEN |
| Batch/ETL | 확인 필요 | 확인 필요 | OPEN |
| OM/Control | 확인 필요 | 정책데이터 기준 필요 | OPEN |

## 10. DR 구축 일정 의존성

DR은 HW/SW 도입 후 실제 Failover/Failback 시험까지 완료되어야 Architecture Ready로 본다.

```text
DR Infra / DB / Storage
   ↓
Middleware
   ↓
Application Deploy
   ↓
Data Sync
   ↓
Routing / GSLB / L4
   ↓
Failover Test
   ↓
Failback Test
   ↓
RTO/RPO Evidence
```

## 11. 주요 GAP

| GAP ID | 내용 | 우선순위 |
|---|---|---:|
| HA-G01 | 전체 운영↔DR Pair Catalog 미완성 | P0 |
| HA-G02 | 시스템별 RTO/RPO 미확정 | P0 |
| HA-G03 | 2+2 vs 3+3 Capacity ADR 미결정 | P0 |
| HA-G04 | Session Strategy 및 Center Failure 시 정책 미결정 | P0 |
| HA-G05 | 실제 L4/GSLB/Apache Routing Evidence 부재 | P0 |
| HA-G06 | N-1/Center Failure 실측 Evidence 없음 | P0 |
| HA-G07 | Failback Runbook/데이터 정합성 절차 미완성 | P0 |

## 12. Gate

현재 판정: **CONDITIONAL PASS**

PASS 조건:

1. 전수 HA/DR Pair Catalog
2. RTO/RPO 승인
3. Session ADR
4. Center Failure/N-1 Test
5. GSLB/L4/Apache 실제 설정 증적
6. Failover/Failback Runbook + Evidence
