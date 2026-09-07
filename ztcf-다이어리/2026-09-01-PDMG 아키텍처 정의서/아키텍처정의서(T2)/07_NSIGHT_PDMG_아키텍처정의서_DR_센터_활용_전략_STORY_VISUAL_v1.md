# NSIGHT PDMG 아키텍처 정의서
# 제7장. DR 센터 활용 전략
## AP 가용성과 DB 정합성을 분리하는 HA/DR 전략
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **6장에서 배치한 Physical Node가 장애와 센터 재해를 어떻게 견디고 복구되는지 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제6장에서 넘어온 질문
     ↓
6장에서 배치한 Physical Node가 장애와 센터 재해를 어떻게 견디고 복구되는지 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
AP 가용성과 DB 정합성을 분리하는 HA/DR 전략
```

이 장의 핵심 원칙은 다음과 같다.

- Local HA와 Center DR을 구분한다.
- AP 가용성 전략과 DB 정합성 전략을 같은 것으로 취급하지 않는다.
- RTO/RPO는 Business Criticality로 결정한다.
- Backup 성공만으로 DR PASS를 선언하지 않는다.

---

# 1. HA와 DR은 같은 것이 아니다

## FIG-07-01. HA와 DR은 같은 것이 아니다

```text
Local HA
= Node / Process Failure 대응

DR
= Center / Site Disaster 대응
```

---

# 2. Main Center의 Local HA

## FIG-07-02. Main Center의 Local HA

```text
GSLB / L4
 ↓
WEB Pair / N+1
 ↓
WAS Active-Active / N+1
 ↓
DB Local HA
 ↓
Residual Capacity
```

---

# 3. 센터 장애 시 DR Flow

## FIG-07-03. 센터 장애 시 DR Flow

```text
Main Center Failure
 ↓
Detect
 ↓
Isolate
 ↓
Traffic Reroute
 ↓
DR WEB/WAS
 ↓
DR DB
 ↓
Business Validation
```

---

# 4. DR에서 Application만 복제하면 부족하다

## FIG-07-04. DR에서 Application만 복제하면 부족하다

```text
DR 준비대상
├─ Artifact
├─ Config
├─ Secret / Key
├─ DB
├─ Interface
├─ Scheduler/Batch
├─ Monitoring
└─ Runbook
```

---

# 5. Key/Secret도 DR 데이터다

## FIG-07-05. Key/Secret도 DR 데이터다

```text
Main JWT Key / Secret
       ↓
Versioned / Managed
       ↓
DR Sync
       ↓
Same Trust
```

---

# 6. DB 정합성은 AP 가용성과 별도 문제다

## FIG-07-06. DB 정합성은 AP 가용성과 별도 문제다

```text
AP Active-Active
= request availability

DB Active-Active
= data write consistency problem

둘은 같은 결정이 아니다
```

---

# 7. RTO/RPO가 DR Tier를 결정한다

## FIG-07-07. RTO/RPO가 DR Tier를 결정한다

```text
Business Criticality
 ↓
RTO / RPO
 ↓
DR Tier
 ↓
Warm / Hot / Other
 ↓
Test Evidence
```

---

# 8. Backup과 DR을 구분한다

## FIG-07-08. Backup과 DR을 구분한다

```text
Backup
= Data Copy

Restore
= Data Recovery

DR
= Service Recovery

Business Validation
= Real Recovery PASS
```

---

# 9. Failover만큼 Failback도 중요하다

## FIG-07-09. Failover만큼 Failback도 중요하다

```text
Main → DR
 Failover
    ↓
Business on DR
    ↓
Main Recovery
    ↓
Data/Config Re-sync
    ↓
Failback
```

---

# 10. DR Test는 전체 경로를 검증한다

## FIG-07-10. DR Test는 전체 경로를 검증한다

```text
Network
 ↓
WEB/WAS
 ↓
Artifact/Config/Key
 ↓
DB
 ↓
Interface
 ↓
Business Transaction
 ↓
Evidence
```

---

# 11. DR의 최종 목적

## FIG-07-11. DR의 최종 목적

```text
"센터가 살아있는가?"
        X

"업무가
정합성을 유지하며
복구되는가?"
        O
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-07-12. Normal Pattern

```text
Detect
 ↓
Isolate
 ↓
Reroute
 ↓
Recover App / Config / Key / Data
 ↓
Business Validation
 ↓
Failback
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-07-13. Forbidden Pattern

```text
Backup Success = DR PASS          X
DB 양방향 Active-Active 무검증       X
RTO/RPO 임의 숫자 확정               X
Failover만 테스트, Failback 미검증    X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — DR 전략

## FIG-07-14. 주안과 대안

```text
[주안]
AP Active-Active/N+1 + DB 정합성 우선 DR

        VS

[대안]
DB 양방향 Active-Active
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | AP Active-Active/N+1 + DB 정합성 우선 DR | DB 양방향 Active-Active |
| 장점 | • 정합성 위험 통제<br>• 운영복잡도 관리<br>• Current 구조와 자연스러운 연결 | • RTO 단축 가능성 |
| 단점 | • 전환절차/동기화 자동화 필요 | • Split-brain/충돌 위험<br>• 검증/운영비용 증가 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-07-15. GAP Map

```text
Current PDMG
│
├─ RTO/RPO 최종 값 OPEN
├─ DR Artifact/Config/Key 동기화 Evidence 미완료
├─ DB HA/DR 상세 Inventory OPEN
└─ Failback/Business Validation Evidence 미완료
```

- `[GAP/OPEN]` RTO/RPO 최종 값 OPEN
- `[GAP/OPEN]` DR Artifact/Config/Key 동기화 Evidence 미완료
- `[GAP/OPEN]` DB HA/DR 상세 Inventory OPEN
- `[GAP/OPEN]` Failback/Business Validation Evidence 미완료

---

# 16. 제7장 Architecture 판정

## FIG-07-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
OPEN / CONDITIONAL

Runtime Evidence
      ↓
LOW-MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Local HA | CONDITIONAL | N+1 Failure Test 필요 |
| DR Topology | PASS/REFERENCE | Main→DR 구조 정의 |
| RTO/RPO | OPEN | Business 승인 필요 |
| DB DR | CONDITIONAL | 제품/복제 상세 필요 |
| Drill | OPEN | Failover/Failback Evidence 필요 |

---

# 17. 원천 정의서 Trace

## FIG-07-17. Source Trace

```text
Story Chapter
   ↓
PDMG 00~18 Source
   ↓
Current Fact / Target Reference
   ↓
Architecture Rule / GAP
```

| 원천 | 용도 |
|---|---|
| 05 Physical | Story/Drill-down/Current-Target 근거 |
| 16 Capacity/HA/DR | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-07-18. 7장 → 8

```text
제7장
DR 센터 활용 전략
      ↓
"그렇다면 정상상태에서 거래를 안전하게 움직이는 공통 실행규칙은 무엇인가?"
      ↓
제8장
메커니즘
```

---

# 19. 제7장 최종 결론

## FIG-07-19. Final Story

```text
DR 센터 활용 전략
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
PASS
```

제7장의 결론은 **AP 가용성과 DB 정합성을 분리하는 HA/DR 전략**라는 한 문장으로 정리된다.
