# NSIGHT PDMG 아키텍처 정의서
# 제7장. DR 센터 활용 전략
## Story: “완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 작성 버전: `REWRITE V2 — Story Quality / Figure-to-Explanation Consistency 보완`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference

---

# 0. Opening Script

Physical 구조를 만들었으면 다음 질문은 장애와 재해입니다.

DR의 목표는 '센터가 하나 더 있다'가 아닙니다. **Main이 사용할 수 없을 때 Application, Config, Key, Data, Interface를 DR에서 다시 연결하고 Business Transaction이 정상적으로 수행되는 것**이 목표입니다.

## FIG-07-01. 장 전체 Architecture

```text
Main Center
 ↓ failure
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
 ↓
Failback
```

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-07-02. Drill-down Route

```text
L0 전체 Story
 ↓
L1 책임 / Boundary
 ↓
L2 Logical / Application / Platform
 ↓
L3 Component / Contract
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Deployment / Evidence
```

---

# 1. HA와 DR 구분

## FIG-07-03. HA와 DR 구분

```text
Local HA
= Node/Process Failure

Center DR
= Site Disaster
```

Local HA와 DR은 같은 문제가 아닙니다.

Node Failure를 N+1로 견디는 것과 Center 전체를 전환하는 것은 범위와 운영절차가 다릅니다.

두 문제를 분리해야 Test Scenario도 분리됩니다.

여기까지가 `HA와 DR 구분`의 역할입니다. 이제 이 구조를 더 내려가 **Main Local HA**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. Main Local HA

## FIG-07-04. Main Local HA

```text
GSLB/L4
 ↓
WEB N+1
 ↓
WAS Active-Active/N+1
 ↓
DB Local HA
```

정상시에는 Local HA가 우선 방어선입니다.

노드 하나가 죽어도 잔존 노드가 부하를 감당해야 하므로 단순 Active-Active가 아니라 N+1 Capacity를 검증해야 합니다.

이 결과가 DR 규모 산정에도 영향을 줍니다.

여기까지가 `Main Local HA`의 역할입니다. 이제 이 구조를 더 내려가 **센터 장애 전환**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. 센터 장애 전환

## FIG-07-05. 센터 장애 전환

```text
Main Failure
 ↓
Detect
 ↓
Isolate
 ↓
Route Switch
 ↓
DR Runtime
 ↓
Business Check
```

DR에서 가장 중요한 것은 자동전환 여부보다 **전환조건과 검증**입니다.

잘못된 감지로 전환되면 더 큰 장애가 될 수 있으므로 Detect/Isolate가 먼저입니다.

그 다음 Traffic과 Application/Data가 같은 시점에 일관되게 전환되어야 합니다.

여기까지가 `센터 장애 전환`의 역할입니다. 이제 이 구조를 더 내려가 **Application/Config/Key 복구**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. Application/Config/Key 복구

## FIG-07-06. Application/Config/Key 복구

```text
Artifact
Config
Secret / JWT Key
Interface Config
Scheduler
Monitoring
 ↓
DR Ready
```

DR은 WAR 파일만 복제한다고 준비되는 것이 아닙니다.

Environment Config, Secret, JWT Key, Interface Endpoint, Monitoring까지 같은 Baseline을 가져야 합니다.

특히 Key 불일치는 인증장애로 직결되므로 DR 데이터로 취급해야 합니다.

여기까지가 `Application/Config/Key 복구`의 역할입니다. 이제 이 구조를 더 내려가 **DB Consistency**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. DB Consistency

## FIG-07-07. DB Consistency

```text
Application Active-Active
        ≠
DB Write Active-Active

DB
 ↓
Replication / Recovery
 ↓
Consistency Check
```

Application 가용성과 DB 쓰기 정합성은 다른 결정입니다.

DB 양방향 Active-Active는 충돌/순서/정합성 문제가 있으므로 단순히 RTO를 줄인다는 이유로 채택할 수 없습니다.

금융계에서는 검증되지 않은 Write Active-Active보다 정합성을 우선합니다.

여기까지가 `DB Consistency`의 역할입니다. 이제 이 구조를 더 내려가 **RTO/RPO**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. RTO/RPO

## FIG-07-08. RTO/RPO

```text
Business Criticality
 ↓
RTO / RPO
 ↓
DR Tier
 ↓
Technology / Procedure
 ↓
Test Evidence
```

RTO/RPO는 Architecture가 임의로 정하는 숫자가 아닙니다.

업무 중요도와 허용손실을 기준으로 결정하고, 그 값을 만족시키기 위해 Technology와 Operation Procedure를 선택해야 합니다.

현재 정확한 값은 OPEN으로 유지합니다.

여기까지가 `RTO/RPO`의 역할입니다. 이제 이 구조를 더 내려가 **Failover와 Failback**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Failover와 Failback

## FIG-07-09. Failover와 Failback

```text
Main → DR
 Failover
    ↓
Operate on DR
    ↓
Main Recover
    ↓
Re-sync
    ↓
Failback
```

많은 DR 설계가 Failover까지만 생각합니다. 하지만 실제 운영에서는 원센터 복구 후 Failback이 더 어렵습니다.

Data/Config가 다시 동기화되고, Business Cutover 시점이 명확해야 합니다.

Failback까지 Test해야 DR 설계가 닫힙니다.

여기까지가 `Failover와 Failback`의 역할입니다. 이제 이 구조를 더 내려가 **DR PASS의 의미**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. DR PASS의 의미

## FIG-07-10. DR PASS의 의미

```text
Backup Success
   ≠
DR PASS

DR PASS
= Network
+ App/Config/Key
+ Data
+ Interface
+ Monitoring
+ Business Validation
```

DR PASS는 Backup Job 성공이 아닙니다.

사용자가 실제 업무를 수행하고 결과가 정합하게 저장되는지까지 확인해야 합니다.

따라서 최종 Evidence는 Business Validation입니다.

---

# 정상패턴과 금지패턴

## FIG-07-11. Normal Pattern

```text
Detect→Isolate→Reroute→Recover→Validate→Failback
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-07-12. Forbidden Pattern

```text
Backup=DR / Failback 미검증
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-07-13. 주안과 대안

```text
[주안]
AP Active-Active/N+1 + DB 정합성 우선 DR

        VS

[대안]
DB 양방향 Active-Active
```

이번 장의 주안은 **AP Active-Active/N+1 + DB 정합성 우선 DR**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **DB 양방향 Active-Active**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-07-14. Current GAP

```text
Current
│
├─ rto/rpo
├─ dr config/key sync
├─ db dr detail
└─ failback evidence
```

- `[GAP/OPEN]` rto/rpo
- `[GAP/OPEN]` dr config/key sync
- `[GAP/OPEN]` db dr detail
- `[GAP/OPEN]` failback evidence

## FIG-07-15. Architecture Assessment

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

Architecture PASS
 ≠
Implementation PASS
```

Architecture가 잘 정의되었다는 것과 현재 구현이 그 정의를 지킨다는 것은 별도 판단입니다. 이 문서는 둘을 분리해 평가하며, `[OPEN]`과 `[UNKNOWN]`을 임의로 채우지 않습니다.

---

# Evidence Card

## FIG-07-16. Evidence Chain

```text
Architecture Rule
 ↓
Source / Config
 ↓
Build / Artifact
 ↓
Deployment
 ↓
ServiceId / GUID
 ↓
Metric / Log / Trace / Test
 ↓
Runtime Evidence
 ↓
PASS / GAP / ADR
```

본문에서는 Story와 Architecture 설명을 우선하고, Evidence는 이 카드에서 정리합니다. 앞으로 자동화 단계에서는 이 Chain을 Manifest/Registry로 기계적으로 생성하는 것이 목표입니다.

---

# Chapter Closing Script

## FIG-07-17. 다음 장 Handoff

```text
DR 센터 활용 전략
 ↓
완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다
 ↓
남은 질문
"시스템은 서버가 아니라 표준과 실행규칙으로 움직인다"
 ↓
메커니즘
```

여기까지가 **DR 센터 활용 전략**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **시스템은 서버가 아니라 표준과 실행규칙으로 움직인다**. 그 질문이 다음 단계인 **메커니즘**의 출발점입니다.
