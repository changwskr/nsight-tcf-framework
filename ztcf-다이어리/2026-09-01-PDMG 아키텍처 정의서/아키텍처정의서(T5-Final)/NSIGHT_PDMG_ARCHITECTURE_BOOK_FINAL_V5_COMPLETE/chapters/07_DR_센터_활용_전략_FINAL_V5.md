# NSIGHT PDMG 아키텍처 정의서
# 제7장. DR 센터 활용 전략
## Story: “완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다”


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

DR 그림은 센터가 하나 더 있다는 사실을 보여주기 위한 것이 아닙니다. 장애를 감지하고 격리한 뒤 Traffic, Application, Config, Key, Data를 DR에서 다시 연결하고 실제 업무를 검증한 후 Failback하는 전체 복구 Chain을 보여줍니다.


---

# 1. HA와 DR 구분

## FIG-07-02. HA와 DR 구분

```text
Local HA
= Node/Process Failure

Center DR
= Site Disaster
```

Local HA와 DR은 같은 문제가 아닙니다.

Node Failure를 N+1로 견디는 것과 Center 전체를 전환하는 것은 범위와 운영절차가 다릅니다.

두 문제를 분리해야 Test Scenario도 분리됩니다.


---

# 2. Main Local HA

## FIG-07-03. Main Local HA

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


---

# 3. 센터 장애 전환

## FIG-07-04. 센터 장애 전환

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


---

# 4. Application/Config/Key 복구

## FIG-07-05. Application/Config/Key 복구

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


---

# 5. DB Consistency

## FIG-07-06. DB Consistency

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


---

# 6. RTO/RPO

## FIG-07-07. RTO/RPO

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


---

# 7. Failover와 Failback

## FIG-07-08. Failover와 Failback

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


---

# 8. DR PASS의 의미

## FIG-07-09. DR PASS의 의미

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

## FIG-07-10. Normal Pattern

```text
Detect→Isolate→Reroute→Recover→Validate→Failback
```

정상패턴은 Local HA로 일반 장애를 흡수하고, Center 장애 시 Detect→Isolate→Reroute→Recover→Validate→Failback을 수행하는 것입니다.

## FIG-07-11. Forbidden Pattern

```text
Backup=DR / Failback 미검증
```

Backup 성공을 DR PASS로 간주하거나 Failback을 검증하지 않은 채 DR 완료로 판단하는 것을 금지합니다.

---

# Architecture Decision

## FIG-07-12. 주안과 대안

```text
[주안]
AP Active-Active/N+1 + DB 정합성 우선 DR

        VS

[대안]
DB 양방향 Active-Active
```

Application은 Stateless Active-Active/N+1을 지향하되 DB는 정합성을 우선한 별도 DR Decision으로 관리합니다.

DB 양방향 Write Active-Active는 빠른 전환의 장점보다 충돌·순서·정합성 운영위험이 크므로 명시적 PoC와 승인 없이는 채택하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
DR 센터 활용 전략 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Physical/HA/DR 정의
- `[SOURCE]` Security Key/Config 동기화 요구

### Config Evidence

- `[CONFIG]` DR Config/Secret/Key/Endpoint 동기화 필요

### Runtime / Deployment Evidence

- `[RUNTIME]` Detect→Isolate→Reroute→Recover→Business Validate→Failback

### Architecture Decision

- `[DECISION]` Application Active-Active와 DB Write Active-Active 분리
- `[DECISION]` DB 정합성 우선

### Related ADR

- `ADR-030 Stateless Active-Active+N+1`
- `ADR-031 Warm/Hot DR`
- `ADR-032 DB HA/DR`
- `ADR-038 Restore Drill`

### Current GAP / OPEN

- `[GAP/OPEN]` RTO/RPO
- `[GAP/OPEN]` DB DR 상세
- `[GAP/OPEN]` Config/Key Sync Evidence
- `[GAP/OPEN]` Failback Evidence

## 이 장의 판정

```text
Architecture Definition
        ↓
PASS

Current PDMG Conformance
        ↓
OPEN / CONDITIONAL

Runtime Evidence Coverage
        ↓
LOW-MEDIUM

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `RTO/RPO 승인`
- `Restore Test`
- `Failover/Failback Business Validation`
- `Key/Config DR Sync`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-07-16. 다음 장 Handoff

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

DR까지 정리하면 공간과 복구전략은 준비됩니다. 이제 정상상태에서 거래가 매번 같은 방식으로 움직이도록 만드는 공통 실행규칙이 필요합니다. 그 규칙이 8장의 Mechanism입니다.
