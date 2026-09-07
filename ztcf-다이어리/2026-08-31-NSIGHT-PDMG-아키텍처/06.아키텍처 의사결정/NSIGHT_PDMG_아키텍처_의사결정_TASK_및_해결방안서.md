# NSIGHT / PDMG 아키텍처 의사결정 TASK 및 해결방안서
## Architecture Decision Register + Decision Resolution Guide

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 성격: **프로젝트 아키텍처 의사결정 Master / ADR 후보 / 해결방안 비교서**  
> 기본 비교방식: **주안 1개 + 대안 1개**  
> 상태원칙: 확정 전에는 `[PROPOSED] / [OPEN] / [GAP] / [CONFLICT] / [CANDIDATE]` 유지  

---

# 0. 문서 목적

프로젝트 진행 중 발생하는 기술·아키텍처 의사결정을 개인 경험이나 회의 결론으로만 남기지 않고, **Decision Task → 선택안 비교 → 장단점 → Evidence → 승인 → ADR → Baseline 반영**으로 표준화한다.

```text
Requirement / Issue / GAP
        ↓
Architecture Decision Task
        ↓
주안 vs 대안
        ↓
장점 / 단점 / Risk / Cost / NFR
        ↓
Evidence / PoC / Test
        ↓
Decision / ADR
        ↓
Architecture Baseline
        ↓
Source / Deployment / Runtime Evidence
```

---

# 1. 의사결정 운영원칙

1. **의사결정은 가능한 한 늦게가 아니라 Evidence가 확보되는 즉시 한다.** 단, 근거 없는 조기확정은 피한다.
2. **주안과 대안은 모두 실행 가능한 안이어야 한다.** 형식적인 대안을 두지 않는다.
3. **장점만 적지 않는다.** 비용·운영복잡도·Migration·장애영향까지 적는다.
4. **수치/제품/버전이 미확정이면 `[OPEN]`으로 남긴다.**
5. **PDMG AS-IS는 Reference이지 자동 Target이 아니다.**
6. 최종결정은 ADR과 Architecture Baseline에 반영하고 Runtime Evidence로 재검증한다.

---

# 2. Priority 정의

| Priority | 의미 | 결정시점 | 원칙 |
|---|---|---|---|
| P0 | 오픈/기본구조를 좌우하는 Critical 결정 | 설계·개발 초기에 결정 | 미결정 상태로 상세개발 진행 최소화 |
| P1 | 구조·운영효율을 크게 좌우하는 중요 결정 | 상세설계~통합시험 전 | PoC/부하/운영검토 후 확정 |
| P2 | 최적화/고도화 결정 | 통합/운영 준비 | 오픈 후 단계적 개선 가능 |

---

# 3. 전체 Architecture Decision Map

```text
Architecture Governance
 ├─ Baseline / SSOT
 ├─ Code Registry
 └─ Runtime Evidence

Application
 ├─ TCF / Facade / Rule
 ├─ Message / Error
 └─ Context

Security
 ├─ JWT Algorithm / Key
 ├─ Session
 └─ Identity Binding

Interface
 ├─ Type / P2P
 ├─ Sync / Async
 ├─ Timeout / Retry
 └─ Event / Bulk

Data
 ├─ RDW / ADW
 ├─ CDC
 ├─ Ownership / Lineage
 └─ Analytical Isolation

Technical / Infrastructure / Runtime
 ├─ VM Size / Scale-out
 ├─ WEB/WAS / JVM/WAR
 ├─ Pool Capacity
 ├─ HA / DR
 └─ DB HA/DR

DevOps / Operations
 ├─ CI/CD
 ├─ Artifact / Deployment
 ├─ Observability / OM
 ├─ Config / Secret
 └─ Backup / Change
```

---

# 4. 의사결정 TASK Master List

| ID | 영역 | Priority | 의사결정 TASK | 권고 | 상태 | Gate | Owner |
|---|---|---|---|---|---|---|---|
| ADR-TASK-001 | Architecture Governance | P0 | Architecture Baseline / SSOT 운영체계 | 주안 | [PROPOSED] | G00~G20 | EA / PMO |
| ADR-TASK-002 | Architecture Governance | P0 | Application/Naming/ServiceId Code Registry SSOT | 주안 | [PROPOSED] | G10~G30 | EA / AA |
| ADR-TASK-003 | Architecture Governance | P0 | PDMG AS-IS ↔ NSIGHT Target Mapping | 주안 | [PROPOSED] | G20~G30 | EA |
| ADR-TASK-004 | Application | P0 | Business Core 진입점 표준 | 주안 | [PROPOSED] | G20~G40 | AA |
| ADR-TASK-005 | Application | P0 | TCF 적용 정책 | 주안 | [PROPOSED] | G20~G40 | EA / AA |
| ADR-TASK-006 | Application | P1 | Rule Layer 도입범위 | 주안 | [PROPOSED] | G20~G40 | AA |
| ADR-TASK-007 | Application | P0 | 표준 메시지/헤더 구조 | 주안 | [PROPOSED] | G20~G40 | AA / IA |
| ADR-TASK-008 | Application | P0 | Error Handling / Error Code 표준 | 주안 | [PROPOSED] | G30~G40 | AA / Ops |
| ADR-TASK-009 | Application | P0 | ServiceContext Worker 전달 방식 | 주안 | [GAP] | G30~G50 | AA / Framework |
| ADR-TASK-010 | Security | P0 | JWT 검증 알고리즘 표준 | 주안 | [GAP] | G20~G40 | SA |
| ADR-TASK-011 | Security | P0 | JWT Key Management / Rotation | 주안 | [GAP] | G20~G50 | SA / Ops |
| ADR-TASK-012 | Security | P0 | Session / Token State 전략 | 주안 | [PROPOSED] | G20~G50 | SA / AA |
| ADR-TASK-013 | Security | P0 | Identity Binding / 업무사용자 신뢰체계 | 주안 | [GAP] | G30~G50 | SA |
| ADR-TASK-014 | Interface | P0 | Interface Type Selection 정책 | 주안 | [PROPOSED] | G20~G40 | IA / EA |
| ADR-TASK-015 | Interface | P0 | Cross-System Direct DB / DB-Link 정책 | 주안 | [PROPOSED] | G20~G40 | EA / DA / IA |
| ADR-TASK-016 | Interface | P1 | SYNC / ASYNC 기본정책 | 주안 | [PROPOSED] | G20~G40 | IA / AA |
| ADR-TASK-017 | Interface | P0 | Timeout Budget 표준 | 주안 | [OPEN] | G30~G50 | AA / IA / DBA |
| ADR-TASK-018 | Interface | P0 | Retry / Idempotency 정책 | 주안 | [PROPOSED] | G30~G50 | IA / AA |
| ADR-TASK-019 | Interface | P1 | Event Platform 표준 | 주안 | [PROPOSED] | G20~G40 | TA / IA |
| ADR-TASK-020 | Interface | P1 | 대량/파일 연계 표준 | 주안 | [PROPOSED] | G20~G40 | IA / DA |
| ADR-TASK-021 | Data | P0 | RDW / ADW 역할분리 | 주안 | [BASELINE] | G20~G40 | DA / TA |
| ADR-TASK-022 | Data | P0 | CDC Freshness SLA | 주안 | [CONFLICT] | G20~G50 | DA / TA |
| ADR-TASK-023 | Data | P0 | Data Subject / Ownership SSOT | 주안 | [PROPOSED] | G20~G40 | DA |
| ADR-TASK-024 | Data | P1 | Metadata / Lineage / Data Quality 운영방식 | 주안 | [PROPOSED] | G30~G50 | DA / DG |
| ADR-TASK-025 | Data | P1 | Heavy Analytical Query 격리 | 주안 | [PROPOSED] | G30~G50 | DA / DBA |
| ADR-TASK-026 | Infrastructure | P0 | WAS Compute Sizing / Scale-out 단위 | 주안(Load Test 전제) | [CANDIDATE] | G20~G50 | TA / Infra |
| ADR-TASK-027 | Infrastructure | P0 | WEB/WAS 표준 접속 Topology | 주안 | [BASELINE] | G20~G50 | TA / Infra |
| ADR-TASK-028 | Infrastructure | P0 | JVM / WAR 배치 및 업무그룹 격리 | 주안 | [PROPOSED] | G20~G50 | TA / AA |
| ADR-TASK-029 | Runtime | P0 | Thread / Worker / Hikari Capacity 정책 | 주안 | [OPEN] | G30~G50 | TA / AA / DBA |
| ADR-TASK-030 | Infrastructure | P0 | 주센터 HA Pattern | 주안 | [PROPOSED] | G30~G50 | TA / Infra |
| ADR-TASK-031 | Infrastructure | P0 | DR 운영모델 | 주안(서비스 Criticality별 차등) | [OPEN] | G30~G50 | TA / Ops / PMO |
| ADR-TASK-032 | Data/Infrastructure | P0 | DB HA / DR 구조 | 주안 | [PROPOSED] | G30~G50 | DA / DBA / TA |
| ADR-TASK-033 | DevOps | P1 | CI/CD Orchestration 도구 | 주안(기존 Jenkins 의존도 조사 후) | [OPEN] | G30~G40 | DevOps / TA |
| ADR-TASK-034 | DevOps | P0 | Artifact Promotion / Deployment Trace | 주안 | [PROPOSED] | G30~G50 | DevOps |
| ADR-TASK-035 | Operations | P0 | Observability 표준 | 주안 | [PROPOSED] | G40~G50 | Ops / TA |
| ADR-TASK-036 | Operations | P1 | OM Control Plane 구조 | 주안 | [OPEN] | G30~G50 | Ops / EA |
| ADR-TASK-037 | Security/DevOps | P0 | Configuration / Secret Management | 주안 | [PROPOSED] | G30~G50 | SA / DevOps |
| ADR-TASK-038 | Operations | P0 | Backup / Restore 운영기준 | 주안 | [PROPOSED] | G40~G50 | Ops / DBA |
| ADR-TASK-039 | DevOps/Operations | P0 | Production Change / Release 통제 | 주안 | [PROPOSED] | G40~G50 | DevOps / Ops |
| ADR-TASK-040 | Architecture Governance | P0 | Runtime Evidence 자동수집 / Baseline Release Gate | 주안(중요 Rule부터 단계적) | [PROPOSED] | G50~HG90 | EA / Ops / DevOps |

---

# 5. P0 우선결정 순서

```text
1 Baseline / SSOT
  ↓
2 Code / Naming / ServiceId
  ↓
3 Application Entry / TCF / Message / Error
  ↓
4 Security JWT / Session / Identity
  ↓
5 Interface Type / DB Boundary / Timeout / Retry
  ↓
6 RDW / ADW / CDC
  ↓
7 WAS Sizing / Topology / JVM-WAR / Pool
  ↓
8 HA / DR / DB HA
  ↓
9 Artifact / Config / Observability / Backup
  ↓
10 Runtime Evidence / HG90
```

---

# 6. Architecture 의사결정 해결방안서

# 6.1. ADR-TASK-001 — Architecture Baseline / SSOT 운영체계

**영역:** Architecture Governance  
**Priority:** P0  
**결정 권고시점:** 착수/설계초기 / G00~G20  
**Decision Owner:** EA / PMO  
**참여:** AA,TA,DA,IA,SA,Ops,DevOps  
**현재 상태:** [PROPOSED]

## 의사결정 질문

아키텍처 기준을 어떤 방식으로 단일화하고 변경·승인·증적을 관리할 것인가?

## 현재 현황 / 쟁점

문서·소스·런타임 Evidence가 분산될 경우 동일 개념의 버전/상태가 달라질 수 있다.

## 주안

> **Architecture Model + Registry + Gate(G00~G80/HG90)를 SSOT로 운영하고 문서·소스·Runtime Evidence를 연결한다.**

### 주안 장점

- 의사결정/변경이력 추적 가능;  문서-소스-런타임 Drift 자동검증 가능;  승인 Baseline이 명확하다.

### 주안 단점

- 초기 모델/Registry 설계와 자동화 비용이 크다;  운영 Owner가 필요하다.

## 대안

> **문서 중심 Baseline을 유지하고 PMO/아키텍트가 수동으로 버전·변경을 관리한다.**

### 대안 장점

- 초기 도입이 빠르고 별도 플랫폼 구축 부담이 적다.

### 대안 단점

- 문서와 실제 구현 불일치가 누적되기 쉽고 Runtime Evidence 연결이 약하다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Traceability, 변경빈도, 프로젝트 규모, Runtime 검증 필요성

## 결정 전에 확보해야 할 Evidence

- Architecture Model, Registry, Gate Checklist, Baseline Manifest

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.2. ADR-TASK-002 — Application/Naming/ServiceId Code Registry SSOT

**영역:** Architecture Governance  
**Priority:** P0  
**결정 권고시점:** 설계초기 / G10~G30  
**Decision Owner:** EA / AA  
**참여:** 개발팀, PMO, DevOps  
**현재 상태:** [PROPOSED]

## 의사결정 질문

업무코드·ProgramId·ServiceId·InterfaceId를 어디에서 정식 관리할 것인가?

## 현재 현황 / 쟁점

PDMG Source 패턴과 NSIGHT Application Code 체계가 다르며 UI Catalog와 Backend Registry의 자동정합도 필요하다.

## 주안

> **중앙 Code Registry를 구축하고 Program/ServiceId/InterfaceId/Naming을 CI에서 자동검증한다.**

### 주안 장점

- 중복/오타/Drift 예방;  Source와 Architecture 분류가 연결;  검색/영향분석이 용이하다.

### 주안 단점

- Registry 운영 프로세스와 초기 데이터 정비가 필요하다.

## 대안

> **각 개발팀이 로컬 코드표를 유지하고 통합시점에만 검증한다.**

### 대안 장점

- 팀 자율성이 높고 초기 속도가 빠르다.

### 대안 단점

- 코드충돌과 동일 의미 중복이 발생하기 쉽고 통합비용이 커진다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 코드 충돌 위험, 자동검증 필요, 팀 수, 변경량

## 결정 전에 확보해야 할 Evidence

- Code Registry, CI Rule 결과, UI/Backend Diff

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.3. ADR-TASK-003 — PDMG AS-IS ↔ NSIGHT Target Mapping

**영역:** Architecture Governance  
**Priority:** P0  
**결정 권고시점:** 설계초기 / G20~G30  
**Decision Owner:** EA  
**참여:** AA,TA,SA,Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

PDMG의 mg 계열 코드/구현패턴을 NSIGHT Target에 어떻게 반영할 것인가?

## 현재 현황 / 쟁점

PDMG는 Reference 구현이며 NSIGHT Target과 동일하지 않다. 자동승격 시 잘못된 표준화 위험이 있다.

## 주안

> **명시적 Mapping Registry와 ADR을 통해 PDMG Pattern을 평가한 뒤 Target으로 선택 승격한다.**

### 주안 장점

- AS-IS와 TO-BE 혼동 방지;  근거 기반 재사용;  예외/차이 추적 가능.

### 주안 단점

- Mapping 작업량이 발생하고 초기 설계 속도가 다소 느려질 수 있다.

## 대안

> **PDMG Naming/구현을 NSIGHT 표준으로 일괄 채택한다.**

### 대안 장점

- 개발속도가 빠르고 학습비용이 적다.

### 대안 단점

- PDMG의 기술부채/GAP까지 Target으로 고착될 수 있다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** NFR 적합성, 보안, 운영성, 재사용 가치

## 결정 전에 확보해야 할 Evidence

- Mapping Registry, Conformance Result, ADR

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.4. ADR-TASK-004 — Business Core 진입점 표준

**영역:** Application  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G20~G40  
**Decision Owner:** AA  
**참여:** Framework팀, 업무개발팀  
**현재 상태:** [PROPOSED]

## 의사결정 질문

TCF ON Handler와 TCF OFF Controller가 동일한 Business Core를 어떻게 공유할 것인가?

## 현재 현황 / 쟁점

Current 일부 Direct Controller는 Service를 직접 호출하고 TCF ON은 Handler→Facade 흐름이다.

## 주안

> **Handler와 Controller 모두 공통 Facade를 호출하고 Facade 이하 Business Core를 공유한다.**

### 주안 장점

- ON/OFF 동작 정합;  Transaction/Validation/Logging 정책 적용이 쉬움;  테스트 재사용.

### 주안 단점

- Facade 계층이 추가되어 단순 CRUD에는 다소 구조가 무거울 수 있다.

## 대안

> **Handler는 Facade, Controller는 Service 직접 호출을 허용한다.**

### 대안 장점

- 단순 업무 개발이 빠르고 클래스 수가 적다.

### 대안 단점

- ON/OFF Runtime 차이와 Transaction/정책 Drift가 발생하기 쉽다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** ON/OFF 공존기간, 정책 일관성, 테스트 비용

## 결정 전에 확보해야 할 Evidence

- Call Graph, Architecture Test, Transaction Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.5. ADR-TASK-005 — TCF 적용 정책

**영역:** Application  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G20~G40  
**Decision Owner:** EA / AA  
**참여:** Framework팀, 개발팀, Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

신규 온라인 거래에서 TCF ON/OFF를 어떤 정책으로 운영할 것인가?

## 현재 현황 / 쟁점

TCF ON은 Timeout/Dispatcher/Handler 경로를 제공하지만 OFF는 Business Controller 직접진입이다.

## 주안

> **신규 온라인은 TCF ON을 기본 표준으로 하고 OFF는 승인된 예외로 제한한다.**

### 주안 장점

- 공통 통제·Timeout·ServiceId Routing·Logging을 일관되게 적용 가능.

### 주안 단점

- Framework 의존도가 증가하고 간단한 API도 공통경로 비용을 부담한다.

## 대안

> **TCF ON/OFF를 동등한 공식모드로 지원한다.**

### 대안 장점

- 유연성이 높고 단순 API는 가볍게 개발 가능.

### 대안 단점

- 두 Runtime Path를 장기 유지해야 하며 정책/테스트 비용이 증가한다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 표준통제 필요성, 성능 overhead, 예외 API 비율

## 결정 전에 확보해야 할 Evidence

- Performance Test, Runtime Trace, Exception List

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.6. ADR-TASK-006 — Rule Layer 도입범위

**영역:** Application  
**Priority:** P1  
**결정 권고시점:** 상세설계 / G20~G40  
**Decision Owner:** AA  
**참여:** 업무설계/개발팀  
**현재 상태:** [PROPOSED]

## 의사결정 질문

Business Rule을 별도 Rule Layer로 분리할 것인가?

## 현재 현황 / 쟁점

Current PDMG 대표 Source에서 Rule Layer가 전수 적용된 것은 아니다.

## 주안

> **복잡·재사용·독립검증이 필요한 Business Decision에만 선택적으로 Rule Layer를 적용한다.**

### 주안 장점

- 복잡한 규칙의 재사용/테스트성이 향상되고 Service 비대화를 방지한다.

### 주안 단점

- 단순 로직에도 오남용하면 클래스와 호출계층이 증가한다.

## 대안

> **Rule Layer를 두지 않고 모든 Business Logic을 Service에 둔다.**

### 대안 장점

- 구조가 단순하고 개발자가 이해하기 쉽다.

### 대안 단점

- Service가 비대해지고 재사용/독립테스트가 어려워진다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 규칙 복잡도, 재사용성, 변경빈도

## 결정 전에 확보해야 할 Evidence

- Use Case Complexity, Code Metrics, Testability Review

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.7. ADR-TASK-007 — 표준 메시지/헤더 구조

**영역:** Application  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G20~G40  
**Decision Owner:** AA / IA  
**참여:** Frontend, Backend, Security  
**현재 상태:** [PROPOSED]

## 의사결정 질문

Online Request/Response Envelope와 공통 Header를 어떻게 표준화할 것인가?

## 현재 현황 / 쟁점

PDMG는 hdr_nhnis + dto/result 구조를 사용한다.

## 주안

> **Versioned Standard Envelope(hdr_nhnis + dto/result)를 Enterprise Contract로 정리하고 필수/선택 Header를 고정한다.**

### 주안 장점

- Client/Server 일관성;  Trace/GUID 연계 용이;  공통 Error/Logging 적용 쉬움.

### 주안 단점

- 모든 API에 공통 Header 부담;  외부 REST 스타일과 차이가 있을 수 있다.

## 대안

> **서비스별 독립 Request/Response Schema를 허용한다.**

### 대안 장점

- 각 서비스 최적화가 쉽고 API가 간결하다.

### 대안 단점

- 공통관측/오류/보안 처리가 복잡해지고 계약 표준화가 약하다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 내부 온라인 비중, 외부 API 비중, 공통통제 요구

## 결정 전에 확보해야 할 Evidence

- Contract Spec, Schema Test, Client Compatibility

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.8. ADR-TASK-008 — Error Handling / Error Code 표준

**영역:** Application  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G30~G40  
**Decision Owner:** AA / Ops  
**참여:** Framework, 개발팀, Security  
**현재 상태:** [PROPOSED]

## 의사결정 질문

Framework/Business/Validation/Security/Timeout 오류를 어떤 공통체계로 반환할 것인가?

## 현재 현황 / 쟁점

PDMG에는 일부 Exception Mapping이 있으나 Filter/Generic Exception 경로와 Error Catalog 연결은 보완 필요하다.

## 주안

> **중앙 Error Taxonomy + Stable Error Code + 표준 Error Envelope + Global Handler를 운영한다.**

### 주안 장점

- Client 대응 일관;  Retryability/Severity/Runbook 연결 가능;  모니터링 표준화.

### 주안 단점

- 기존 오류코드 Migration과 Catalog 운영비용이 필요하다.

## 대안

> **업무/모듈별 오류코드와 Handler를 유지한다.**

### 대안 장점

- 기존 개발 영향이 작고 업무별 자유도가 높다.

### 대안 단점

- 동일 의미의 오류가 분산되고 운영/Client 대응이 복잡하다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Client 수, 운영 자동화, 오류코드 중복

## 결정 전에 확보해야 할 Evidence

- Error Catalog, Contract Test, Fault Injection

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.9. ADR-TASK-009 — ServiceContext Worker 전달 방식

**영역:** Application  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G30~G50  
**Decision Owner:** AA / Framework  
**참여:** Ops, Security  
**현재 상태:** [GAP]

## 의사결정 질문

Request Thread의 Context를 Worker Thread에 어떤 방식으로 전달할 것인가?

## 현재 현황 / 쟁점

Current 분석에서는 mutable ServiceContext reference와 servlet request/response 공유 위험이 있다.

## 주안

> **Worker 전용 Immutable Context Snapshot을 생성하여 필요한 식별정보만 전달한다.**

### 주안 장점

- Thread 안전성 향상;  Servlet 객체 누출 방지;  비동기 실행 경계가 명확하다.

### 주안 단점

- Snapshot DTO/Mapping 코드와 유지비용이 증가한다.

## 대안

> **현재처럼 동일 mutable Context reference를 Worker에 전달한다.**

### 대안 장점

- 구현이 단순하고 기존코드 변경이 적다.

### 대안 단점

- 동시성/메모리/Servlet lifecycle 위험이 크다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 비동기/Worker 사용량, Context 크기, Thread safety

## 결정 전에 확보해야 할 Evidence

- Concurrency Test, Heap/Thread Dump, Context Contract

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.10. ADR-TASK-010 — JWT 검증 알고리즘 표준

**영역:** Security  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G20~G40  
**Decision Owner:** SA  
**참여:** AA, Framework, Ops  
**현재 상태:** [GAP]

## 의사결정 질문

Token Issuer와 Business Verifier의 알고리즘/키체계를 무엇으로 통일할 것인가?

## 현재 현황 / 쟁점

PDMG 분석에서 pdmg-jwt RS256 발급과 pdmg-fw HMAC 검증 경로가 충돌한다.

## 주안

> **RS256 + JWKS 기반 비대칭 검증으로 통일한다.**

### 주안 장점

- Private Key를 Issuer에 한정;  다중 Consumer 확장 용이;  키회전/kid 운영에 적합.

### 주안 단점

- JWKS/Key lifecycle 운영이 필요하고 초기 구현복잡도가 높다.

## 대안

> **HMAC shared secret 방식으로 통일한다.**

### 대안 장점

- 구현이 단순하고 성능비용이 낮다.

### 대안 단점

- Verifier가 secret를 공유해야 하며 키유출 영향범위가 크다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Consumer 수, 키격리, 외부확장, 운영성

## 결정 전에 확보해야 할 Evidence

- JWT Integration Test, Key Rotation Test, Security Review

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.11. ADR-TASK-011 — JWT Key Management / Rotation

**영역:** Security  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G20~G50  
**Decision Owner:** SA / Ops  
**참여:** AA, Infra  
**현재 상태:** [GAP]

## 의사결정 질문

JWT Signing Key를 어디에 저장하고 다중 Instance/DR에서 어떻게 일관되게 유지할 것인가?

## 현재 현황 / 쟁점

In-memory/generated key 가능성이 분석되어 restart/multi-instance 시 same kid/different key 위험이 있다.

## 주안

> **Managed Persistent Key Store를 사용하고 versioned key/kid/JWKS/rotation/DR sync를 운영한다.**

### 주안 장점

- 재기동/Scale-out/DR 정합;  안전한 Rotation;  Audit 가능.

### 주안 단점

- Key Store 운영 및 접근통제 체계가 필요하다.

## 대안

> **Instance별 기동 시 Key를 생성하고 JWKS를 해당 Instance에서 제공한다.**

### 대안 장점

- 외부 Key Store 없이 빠르게 구현 가능.

### 대안 단점

- 다중 Instance와 Restart 시 토큰 검증 불안정;  DR 부적합.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Instance 수, Token TTL, DR, 보안정책

## 결정 전에 확보해야 할 Evidence

- Key Inventory, Rotation Drill, Multi-node Token Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.12. ADR-TASK-012 — Session / Token State 전략

**영역:** Security  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G20~G50  
**Decision Owner:** SA / AA  
**참여:** Infra, Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

사용자 상태를 JWT 중심으로 할지 HttpSession 중심으로 할지 결정한다.

## 현재 현황 / 쟁점

JWT access/refresh가 존재하고 세션 정책/복제방식은 여러 후보와 과거값이 혼재한다.

## 주안

> **JWT Access + 서버관리 Refresh/Revoke State를 기본으로 하고 HttpSession은 최소 기능에 한정한다.**

### 주안 장점

- WAS Scale-out 용이;  Session Replication 부담 감소;  API 친화적.

### 주안 단점

- Refresh/Revoke/Key 운영이 필요하고 완전 Stateless는 아니다.

## 대안

> **Tomcat HttpSession을 중심으로 하고 Sticky/Replication/JDBC Session을 사용한다.**

### 대안 장점

- 기존 Web 방식에 익숙하고 서버측 상태통제가 쉽다.

### 대안 단점

- Scale-out/DR 복잡도와 세션복제 비용이 증가한다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** API 비중, SSO, 상태성 요구, DR

## 결정 전에 확보해야 할 Evidence

- Login/Failover Test, Session Loss Test, Revocation Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.13. ADR-TASK-013 — Identity Binding / 업무사용자 신뢰체계

**영역:** Security  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G30~G50  
**Decision Owner:** SA  
**참여:** AA, Frontend  
**현재 상태:** [GAP]

## 의사결정 질문

JWT Principal과 hdr_nhnis의 사용자/조작자 정보를 어떻게 정합시킬 것인가?

## 현재 현황 / 쟁점

JWT subject/ssoId가 request attribute에 존재해도 header operator/userContext와 자동정합되지 않을 수 있다.

## 주안

> **서버가 Trusted Principal을 기준으로 Business User Context를 생성/검증하고 Client Header의 사용자값은 참고값으로만 사용한다.**

### 주안 장점

- Header spoof 방지;  AuthN/AuthZ/Audit 정합성이 높다.

### 주안 단점

- 기존 Client Header 처리와 Mapping 로직 변경이 필요하다.

## 대안

> **Client가 전달한 사용자 Header를 신뢰하고 JWT는 인증여부만 확인한다.**

### 대안 장점

- 기존 변경이 적고 구현이 단순하다.

### 대안 단점

- 사용자 위조/권한오용 위험이 크다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 권한모델, 감사요건, 단말 신뢰수준

## 결정 전에 확보해야 할 Evidence

- Security Test, Principal/Header Mismatch Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.14. ADR-TASK-014 — Interface Type Selection 정책

**영역:** Interface  
**Priority:** P0  
**결정 권고시점:** 논리/상세설계 / G20~G40  
**Decision Owner:** IA / EA  
**참여:** AA,DA,TA,Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

거래/API/Event/CDC/ETL/File을 어떤 기준으로 선택할 것인가?

## 현재 현황 / 쟁점

연계 목적에 따라 여러 mechanism이 필요하며 모든 연계를 REST로 통일하는 것은 부적절하다.

## 주안

> **Purpose-driven 정책: Transaction→API/MCA, Event→Broker, Change→CDC, Bulk→ETL, File→MFT/FOS.**

### 주안 장점

- 업무특성에 맞는 성능/복구;  장애격리;  운영정책 명확.

### 주안 단점

- 여러 플랫폼/기술을 운영해야 하며 역량 요구가 증가한다.

## 대안

> **API-first로 대부분 REST/JSON으로 통일하고 특수경우만 별도방식 사용.**

### 대안 장점

- 개발표준 단순;  기술스택 축소.

### 대안 단점

- 대량/이벤트/CDC에 비효율적이고 장애/재처리 패턴이 약해진다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Latency, Volume, Coupling, Recovery, Fan-out

## 결정 전에 확보해야 할 Evidence

- Interface Catalog, Volume Profile, Failure Scenario

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.15. ADR-TASK-015 — Cross-System Direct DB / DB-Link 정책

**영역:** Interface  
**Priority:** P0  
**결정 권고시점:** 논리/상세설계 / G20~G40  
**Decision Owner:** EA / DA / IA  
**참여:** DBA,AA,Security  
**현재 상태:** [PROPOSED]

## 의사결정 질문

타 시스템 데이터에 Direct JDBC/DML/DB-Link를 허용할 것인가?

## 현재 현황 / 쟁점

Target 원칙은 시스템 경계를 통제하고 Cross-System Direct DML을 제한한다.

## 주안

> **Cross-System DML/DB-Link는 원칙적으로 금지하고 Approved API/Data Contract/CDC/ETL을 사용한다.**

### 주안 장점

- 소유권·변경영향·보안·트랜잭션 경계가 명확하다.

### 주안 단점

- 일부 조회/배치의 개발경로가 길어지고 플랫폼 비용이 증가할 수 있다.

## 대안

> **성능/단순성을 위해 제한된 Direct DB/DB-Link를 허용한다.**

### 대안 장점

- 빠른 조회/배치 구현과 낮은 중간계층 비용.

### 대안 단점

- 강결합, 스키마변경 전파, 권한/감사/장애경계 문제가 커진다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Ownership, Consistency, Performance, Change Risk

## 결정 전에 확보해야 할 Evidence

- Data Ownership, Query Load, Exception ADR

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.16. ADR-TASK-016 — SYNC / ASYNC 기본정책

**영역:** Interface  
**Priority:** P1  
**결정 권고시점:** 논리/상세설계 / G20~G40  
**Decision Owner:** IA / AA  
**참여:** 업무설계,Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

서비스 간 연계를 기본적으로 동기형으로 할지 비동기형으로 할지 결정한다.

## 현재 현황 / 쟁점

동기호출은 cascade failure 위험이 있고 비동기는 retry/replay/idempotency가 필요하다.

## 주안

> **현재 거래완료에 Target 결과가 필수일 때만 SYNC, 그 외는 ASYNC 우선.**

### 주안 장점

- 장애격리/버퍼링/독립확장에 유리;  이벤트기반 처리 강화.

### 주안 단점

- 최종일관성/재처리/운영복잡도가 증가한다.

## 대안

> **기본은 SYNC API, 성능/후처리 필요 시에만 ASYNC.**

### 대안 장점

- 개발/디버깅이 단순하고 즉시결과 모델이 명확하다.

### 대안 단점

- 호출체인 증가 시 장애전파와 Thread 점유가 커진다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 업무완료조건, 일관성, 호출체인, 복구요구

## 결정 전에 확보해야 할 Evidence

- Sequence Diagram, Failure Test, Business Consistency Review

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.17. ADR-TASK-017 — Timeout Budget 표준

**영역:** Interface  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G30~G50  
**Decision Owner:** AA / IA / DBA  
**참여:** Ops,TA  
**현재 상태:** [OPEN]

## 의사결정 질문

Client/Gateway/Server/Worker/DB Timeout을 어떤 계층으로 설계할 것인가?

## 현재 현황 / 쟁점

PDMG Worker 5000ms는 AS-IS이며 Target SLA가 아니다. Query timeout exact value는 미확정.

## 주안

> **DB Query < Worker/TX Deadline < Server/Downstream < Client 순의 계층형 Budget을 서비스별 Registry로 관리한다.**

### 주안 장점

- Late work/자원점유 감소;  원인분석 용이;  상하위 Timeout 역전 방지.

### 주안 단점

- 서비스별 측정/튜닝이 필요하고 운영복잡도가 증가한다.

## 대안

> **모든 Online 거래에 동일 Fixed Timeout(예: 5초)을 적용한다.**

### 대안 장점

- 설정/운영이 단순하다.

### 대안 단점

- 업무특성 무시;  DB/하위 Timeout과 충돌;  False Timeout/Late Commit 위험.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** SLO, DB특성, 외부호출, 업무중요도

## 결정 전에 확보해야 할 Evidence

- Latency Profile, Query Timeout Test, Timeout Matrix

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.18. ADR-TASK-018 — Retry / Idempotency 정책

**영역:** Interface  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G30~G50  
**Decision Owner:** IA / AA  
**참여:** 업무,Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

일시적 오류에 Retry를 허용할 조건과 중복방지 방식을 결정한다.

## 현재 현황 / 쟁점

금융 DML/Validation/Authorization/Business Reject에 Blind Retry는 위험하다.

## 주안

> **Retryable Error 분류 + Backoff + Max Retry + Idempotency Key/Compensation + Final Recovery를 표준화한다.**

### 주안 장점

- 일시적 장애 회복력 향상;  중복거래 위험 통제;  운영절차 명확.

### 주안 단점

- Idempotency 저장소/상태관리와 보상로직이 필요하다.

## 대안

> **통신/Timeout 오류에 공통 N회 Retry를 적용한다.**

### 대안 장점

- 구현이 매우 단순하고 일부 네트워크 장애에 효과적이다.

### 대안 단점

- 중복처리/과부하 증폭/비즈니스 오류 반복 위험이 크다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 멱등성, 거래성, 오류종류, Recovery 비용

## 결정 전에 확보해야 할 Evidence

- Retry Matrix, Duplicate Test, Compensation Scenario

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.19. ADR-TASK-019 — Event Platform 표준

**영역:** Interface  
**Priority:** P1  
**결정 권고시점:** 기술설계 / G20~G40  
**Decision Owner:** TA / IA  
**참여:** DA,Ops,Infra  
**현재 상태:** [PROPOSED]

## 의사결정 질문

실시간 이벤트를 어떤 표준 Broker/운영모델로 구성할 것인가?

## 현재 현황 / 쟁점

Kafka/Event 기반 반응형 처리가 Target 방향이나 정확한 제품/버전/Cluster는 미확정.

## 주안

> **표준 Event Broker(Kafka 계열 후보)를 공통플랫폼으로 구축하고 Topic/Schema/Consumer Group/DLQ를 표준화한다.**

### 주안 장점

- Fan-out/Replay/Scale-out/비동기 처리에 강함;  이벤트표준화 가능.

### 주안 단점

- 플랫폼 운영역량/Schema Governance/용량설계가 필요하다.

## 대안

> **기존 API 호출 + Scheduler/Polling으로 이벤트성 요구를 처리한다.**

### 대안 장점

- 신규 Broker 운영부담이 적다.

### 대안 단점

- 지연/부하/결합도가 높고 Replay/Fan-out이 약하다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 이벤트량, 지연요구, Consumer 수, 운영역량

## 결정 전에 확보해야 할 Evidence

- PoC, Throughput Test, Event Catalog

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.20. ADR-TASK-020 — 대량/파일 연계 표준

**영역:** Interface  
**Priority:** P1  
**결정 권고시점:** 기술설계 / G20~G40  
**Decision Owner:** IA / DA  
**참여:** TA,Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

대량 데이터와 파일을 API로 처리할지 ETL/MFT로 분리할지 결정한다.

## 현재 현황 / 쟁점

대량 Binary/Image를 Online 본문에 포함하는 것은 지양하며 ETL/File 전용경로가 필요하다.

## 주안

> **Bulk는 ETL, File은 MFT/FOS로 분리하고 Online API는 소량 Transaction 중심으로 제한한다.**

### 주안 장점

- 대량전송 안정성/재처리/검증/성능이 좋다.

### 주안 단점

- 전용 플랫폼과 배치/파일 운영절차가 필요하다.

## 대안

> **가능한 범위에서 대량도 HTTP API/Streaming Upload로 통일한다.**

### 대안 장점

- 기술스택 단순;  개발접점 통일.

### 대안 단점

- 대용량 Timeout/재전송/자원점유/운영관리 문제가 커진다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Payload size, Daily volume, Recovery, Business window

## 결정 전에 확보해야 할 Evidence

- Volume Analysis, Transfer Test, Reconciliation Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.21. ADR-TASK-021 — RDW / ADW 역할분리

**영역:** Data  
**Priority:** P0  
**결정 권고시점:** 논리설계 / G20~G40  
**Decision Owner:** DA / TA  
**참여:** DBA,BI,AA  
**현재 상태:** [BASELINE]

## 의사결정 질문

운영/준실시간 조회와 분석/마트를 동일 DW에서 처리할지 분리할지 결정한다.

## 현재 현황 / 쟁점

Target은 RDW=운영·준실시간, ADW=분석·집계·마트 분리를 지향한다.

## 주안

> **RDW와 ADW의 목적/Workload/데이터모델/접근권한을 분리한다.**

### 주안 장점

- Online SLA 보호;  분석부하 격리;  데이터 역할/소유 명확.

### 주안 단점

- 데이터중복/ETL/저장비용과 운영대상이 증가한다.

## 대안

> **통합 DW에서 Resource Manager/Workload Control로 운영·분석을 함께 처리한다.**

### 대안 장점

- 데이터복제/플랫폼 수 감소;  단일 데이터소스.

### 대안 단점

- Heavy Query가 운영성 조회에 영향을 줄 수 있고 확장/장애격리가 약하다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Peak workload, BI query, Freshness, Storage cost

## 결정 전에 확보해야 할 Evidence

- Workload Profile, Performance Test, Cost Analysis

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.22. ADR-TASK-022 — CDC Freshness SLA

**영역:** Data  
**Priority:** P0  
**결정 권고시점:** 상세설계 / G20~G50  
**Decision Owner:** DA / TA  
**참여:** 업무,Ops,DBA  
**현재 상태:** [CONFLICT]

## 의사결정 질문

CDC 지연목표를 단일 숫자로 둘지 업무등급별로 둘지 결정한다.

## 현재 현황 / 쟁점

기존 자료에 30초와 3초 후보가 충돌하며 측정지점 정의도 필요하다.

## 주안

> **Source Commit→Consumer Visible 기준의 측정지점을 고정하고 업무등급별 Tiered Freshness SLA를 정의한다.**

### 주안 장점

- 업무가치/비용에 맞는 SLA;  불필요한 과투자 방지;  측정이 명확하다.

### 주안 단점

- 등급/지표/대시보드가 복잡해지고 운영정책이 늘어난다.

## 대안

> **전 구간 단일 SLA(예: 3초 또는 30초)를 적용한다.**

### 대안 장점

- 계약/운영이 단순하고 이해가 쉽다.

### 대안 단점

- 일부 업무에는 과도하거나 부족한 SLA가 된다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 업무 반응시간, 데이터량, CDC 기술한계, 비용

## 결정 전에 확보해야 할 Evidence

- CDC PoC, Lag Distribution, Business Tier

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.23. ADR-TASK-023 — Data Subject / Ownership SSOT

**영역:** Data  
**Priority:** P0  
**결정 권고시점:** 논리설계 / G20~G40  
**Decision Owner:** DA  
**참여:** 업무Owner,DBA,DG  
**현재 상태:** [PROPOSED]

## 의사결정 질문

Data Subject, Owner, Steward, SOR을 어떤 체계로 확정할 것인가?

## 현재 현황 / 쟁점

RDW/ADW/BI/DG Subject 분류는 있으나 전수 Owner/Steward/Physical Mapping은 추가 필요하다.

## 주안

> **Data Subject Registry에 Business Owner/Steward/SOR/Consumer/Security를 관리한다.**

### 주안 장점

- 소유권·품질·변경승인·보안책임이 명확하다.

### 주안 단점

- 조직 RACI 합의와 지속적인 Metadata 관리가 필요하다.

## 대안

> **Application/DBA가 관리하는 테이블 단위 책임을 그대로 사용한다.**

### 대안 장점

- 기존 운영방식과 유사하고 도입이 빠르다.

### 대안 단점

- 비즈니스 의미/품질/소유권이 기술조직에 종속된다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 데이터공유 정도, 규제/품질, Subject 복잡도

## 결정 전에 확보해야 할 Evidence

- Subject Inventory, RACI, SOR Mapping

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.24. ADR-TASK-024 — Metadata / Lineage / Data Quality 운영방식

**영역:** Data  
**Priority:** P1  
**결정 권고시점:** 상세설계 / G30~G50  
**Decision Owner:** DA / DG  
**참여:** DBA,ETL,BI  
**현재 상태:** [PROPOSED]

## 의사결정 질문

메타데이터·Lineage·품질을 수동문서로 관리할지 자동수집할지 결정한다.

## 현재 현황 / 쟁점

Critical Service/Data의 Source→SQL→Table→ETL→Report Trace가 필요하다.

## 주안

> **자동 Metadata/Lineage 수집 + Critical Data Quality Rule 실행결과를 Governance Repository에 연결한다.**

### 주안 장점

- 영향분석/장애원인/변경검증 자동화;  문서 최신성 향상.

### 주안 단점

- 도구연계/Metadata 정제/운영비용이 크다.

## 대안

> **주요 대상만 수동 문서/Excel로 Lineage와 품질을 관리한다.**

### 대안 장점

- 초기비용이 작고 즉시 시작 가능.

### 대안 단점

- 최신성/정확성 유지가 어렵고 대규모 영향분석에 취약하다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 데이터규모, 변경빈도, 감사요건, 자동화 예산

## 결정 전에 확보해야 할 Evidence

- Metadata Coverage, Lineage Scan, DQ Rule Result

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.25. ADR-TASK-025 — Heavy Analytical Query 격리

**영역:** Data  
**Priority:** P1  
**결정 권고시점:** 상세설계 / G30~G50  
**Decision Owner:** DA / DBA  
**참여:** BI,Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

대용량 분석쿼리를 RDW에서 허용할지 ADW로 강제할지 결정한다.

## 현재 현황 / 쟁점

Online/Operational과 분석 Workload 간 간섭을 줄여야 한다.

## 주안

> **Heavy BI/Ad-hoc Query는 ADW/Analytical Resource로 격리하고 RDW는 운영성 조회에 집중한다.**

### 주안 장점

- Online/준실시간 SLA 보호;  Capacity 예측이 쉬움.

### 주안 단점

- 데이터 Freshness/복제와 사용자 경로가 복잡해질 수 있다.

## 대안

> **RDW에도 Resource Manager/Query Governor를 적용해 Heavy Query를 제한적으로 허용한다.**

### 대안 장점

- 최신데이터 직접분석과 데이터복제 감소.

### 대안 단점

- 잘못된 Query가 Operational 자원을 침해할 위험이 남는다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** BI Freshness, Query Cost, RDW Headroom

## 결정 전에 확보해야 할 Evidence

- Query Profile, Resource Test, User Requirement

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.26. ADR-TASK-026 — WAS Compute Sizing / Scale-out 단위

**영역:** Infrastructure  
**Priority:** P0  
**결정 권고시점:** 물리설계 / G20~G50  
**Decision Owner:** TA / Infra  
**참여:** AA,Ops,PMO  
**현재 상태:** [CANDIDATE]

## 의사결정 질문

32C/256G 대형 VM 소수와 16C/128G 중형 VM 다수 중 어떤 구조를 채택할 것인가?

## 현재 현황 / 쟁점

과거 후보로 32C/256G×4, 16C/128G×8 등 동일 총자원 대안이 존재한다.

## 주안

> **16C/128G급 중형 VM Scale-out을 주안으로 하고 N+1/업무그룹 분리를 적용한다.**

### 주안 장점

- Failure Domain 축소;  배포/Scale-out 유연;  JVM GC/장애 영향범위 감소.

### 주안 단점

- 노드 수/운영대상/라이선스/네트워크 연결이 증가할 수 있다.

## 대안

> **32C/256G급 대형 VM 소수로 구성한다.**

### 대안 장점

- 운영노드가 적고 단순하며 공유자원 활용이 쉽다.

### 대안 단점

- 장애영향이 크고 대형 JVM/GC/자원독점 위험이 증가한다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안(Load Test 전제)**

**최종 판단기준:** License, VM quota, GC, N+1, 운영복잡도

## 결정 전에 확보해야 할 Evidence

- Load/Soak Test, Failure Test, Cost/License

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.27. ADR-TASK-027 — WEB/WAS 표준 접속 Topology

**영역:** Infrastructure  
**Priority:** P0  
**결정 권고시점:** 물리설계 / G20~G50  
**Decision Owner:** TA / Infra  
**참여:** SA,AA,Ops  
**현재 상태:** [BASELINE]

## 의사결정 질문

L4→Apache→Tomcat 구조를 유지할지 L4→Tomcat Direct를 사용할지 결정한다.

## 현재 현황 / 쟁점

Working baseline은 GSLB→L4→Apache→Tomcat→WAR이다.

## 주안

> **L4→Apache WEB→Tomcat WAS를 표준경로로 사용한다.**

### 주안 장점

- WEB/WAS 역할분리;  Static/Proxy/보안/라우팅 정책 중앙화;  WAS 직접노출 감소.

### 주안 단점

- Hop/운영컴포넌트 증가;  Apache 장애/설정 관리 필요.

## 대안

> **L4→Tomcat Direct로 단순화하고 WAS에서 HTTP 서비스를 직접 제공한다.**

### 대안 장점

- 구성 단순;  Hop 감소;  Container-native에 유리할 수 있다.

### 대안 단점

- WEB 역할/보안/라우팅 정책이 WAS에 집중된다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 기존 전사표준, 보안경계, 정적콘텐츠, 운영역량

## 결정 전에 확보해야 할 Evidence

- Network/Security Review, Performance Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.28. ADR-TASK-028 — JVM / WAR 배치 및 업무그룹 격리

**영역:** Infrastructure  
**Priority:** P0  
**결정 권고시점:** 물리설계 / G20~G50  
**Decision Owner:** TA / AA  
**참여:** Ops,DevOps  
**현재 상태:** [PROPOSED]

## 의사결정 질문

다수 WAR를 소수 JVM에 통합할지 업무그룹별 JVM로 분리할지 결정한다.

## 현재 현황 / 쟁점

17 WAR 및 업무그룹 분리 후보가 있으며 JVM 하나의 장애/GC/Thread 독점이 다수 WAR에 영향을 줄 수 있다.

## 주안

> **업무특성/부하/장애영향에 따라 WAR를 2개 이상 업무그룹 JVM으로 격리한다.**

### 주안 장점

- 자원독점/장애격리;  배포영향 축소;  그룹별 튜닝 가능.

### 주안 단점

- JVM 수 증가로 Memory/운영/배포 관리량이 늘어난다.

## 대안

> **다수 WAR를 소수 대형 JVM에 통합한다.**

### 대안 장점

- 메모리공유/운영 단순;  JVM 관리 수 감소.

### 대안 단점

- 한 WAR의 GC/Thread/Leak이 타업무에 영향;  장애범위 확대.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** WAR별 TPS/Heap/배포주기, 공유라이브러리, 장애영향

## 결정 전에 확보해야 할 Evidence

- WAR Resource Profile, Load Test, Deployment Impact

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.29. ADR-TASK-029 — Thread / Worker / Hikari Capacity 정책

**영역:** Runtime  
**Priority:** P0  
**결정 권고시점:** 성능설계 / G30~G50  
**Decision Owner:** TA / AA / DBA  
**참여:** Ops  
**현재 상태:** [OPEN]

## 의사결정 질문

Tomcat Threads, Worker Pool, DB Pool을 어떤 방식으로 산정·제어할 것인가?

## 현재 현황 / 쟁점

과거 후보 maxThreads 1200~1500, Hikari 120~160 등이 있으나 승인값이 아니며 PDMG Worker는 20/100이다.

## 주안

> **End-to-End Capacity Budget(Tomcat→Worker→Hikari→DB)와 Backpressure를 기준으로 Load Test 후 확정한다.**

### 주안 장점

- 연쇄포화 방지;  DB Capacity와 정합;  실제 SLO 기반 튜닝.

### 주안 단점

- 성능시험/관측지표/모델링 비용이 필요하다.

## 대안

> **각 Pool을 크게 설정하여 Queue/거절을 최소화한다.**

### 대안 장점

- 초기에는 요청수용량이 커 보이고 설정이 단순하다.

### 대안 단점

- DB/CPU가 포화되면 대기열과 Timeout이 폭증하고 장애가 확대된다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** TPS, latency, DB sessions, CPU, overload behavior

## 결정 전에 확보해야 할 Evidence

- Load/Stress/Soak Test, Pool Metrics

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.30. ADR-TASK-030 — 주센터 HA Pattern

**영역:** Infrastructure  
**Priority:** P0  
**결정 권고시점:** 가용성설계 / G30~G50  
**Decision Owner:** TA / Infra  
**참여:** AA,DBA,Ops  
**현재 상태:** [PROPOSED]

## 의사결정 질문

주센터 내부 서비스 이중화를 Active-Active로 할지 Active-Standby로 할지 결정한다.

## 현재 현황 / 쟁점

Online/Web/WAS는 수평확장이 가능하며 DB/Integration은 기술별 패턴이 다르다.

## 주안

> **WEB/WAS 등 Stateless 계층은 Active-Active + N+1, Stateful 기술은 제품 특성에 맞는 HA를 적용한다.**

### 주안 장점

- 자원활용률 높음;  장애 시 즉시 잔존노드 처리;  Scale-out과 일치.

### 주안 단점

- 동시Active 정합/세션/Key/DB 연결을 설계해야 한다.

## 대안

> **Active-Standby를 광범위하게 적용한다.**

### 대안 장점

- 상태관리/트래픽 전환 구조가 단순할 수 있다.

### 대안 단점

- 유휴자원이 많고 전환시험/Failover 시간이 필요하다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** Statefulness, license, failover time, residual capacity

## 결정 전에 확보해야 할 Evidence

- Node Failure Test, N+1 Capacity, Session Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.31. ADR-TASK-031 — DR 운영모델

**영역:** Infrastructure  
**Priority:** P0  
**결정 권고시점:** DR설계 / G30~G50  
**Decision Owner:** TA / Ops / PMO  
**참여:** DA,SA,DevOps  
**현재 상태:** [OPEN]

## 의사결정 질문

DR을 Hot/Warm으로 상시준비할지 Cold rebuild 방식으로 할지 결정한다.

## 현재 현황 / 쟁점

DR은 서버뿐 아니라 Artifact/Config/Key/Data/Interface/Monitoring 정합이 필요하다.

## 주안

> **Critical 서비스는 Warm/Hot DR을 구성하고 Artifact/Config/Key를 사전동기화하며 정기 Drill을 수행한다.**

### 주안 장점

- RTO 단축;  복구예측 가능;  실제 전환품질 향상.

### 주안 단점

- DR 인프라/운영비용과 데이터동기화 복잡도가 증가한다.

## 대안

> **Cold DR로 장애 시 환경을 재기동/재배포한다.**

### 대안 장점

- 평상시 비용이 낮다.

### 대안 단점

- RTO 예측이 어렵고 구성/키/배포 Drift 위험이 크다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안(서비스 Criticality별 차등)**

**최종 판단기준:** RTO/RPO, Cost, Regulatory, Criticality

## 결정 전에 확보해야 할 Evidence

- DR Tier, Drill Result, Recovery Time

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.32. ADR-TASK-032 — DB HA / DR 구조

**영역:** Data/Infrastructure  
**Priority:** P0  
**결정 권고시점:** 물리/DR설계 / G30~G50  
**Decision Owner:** DA / DBA / TA  
**참여:** Ops,PMO  
**현재 상태:** [PROPOSED]

## 의사결정 질문

RDW/ADW DB를 어떤 Local HA와 DR 복구방식으로 구성할 것인가?

## 현재 현황 / 쟁점

DB Local HA와 Center DR을 구분해야 하며 정확한 Node 수/제품 버전은 Inventory로 확정 필요하다.

## 주안

> **RDW/ADW를 분리하고 Local Cluster/RAC HA + DR Replication/Recovery를 적용한다.**

### 주안 장점

- Node 장애와 Center 장애를 분리 대응;  데이터 역할/부하격리.

### 주안 단점

- DB License/운영/복제 비용 증가;  복잡한 Failover/Consistency 관리.

## 대안

> **단일/Standby DB + Backup 기반 DR 복구.**

### 대안 장점

- 구축/운영비용이 낮고 단순하다.

### 대안 단점

- RTO/RPO와 가용성이 낮고 장애복구시간이 길다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** RTO/RPO, transaction criticality, license cost, volume

## 결정 전에 확보해야 할 Evidence

- DB Inventory, Failover Test, Restore/DR Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.33. ADR-TASK-033 — CI/CD Orchestration 도구

**영역:** DevOps  
**Priority:** P1  
**결정 권고시점:** 개발초기 / G30~G40  
**Decision Owner:** DevOps / TA  
**참여:** 개발팀,Ops  
**현재 상태:** [OPEN]

## 의사결정 질문

GitLab Runner 중심으로 통합할지 Jenkins 중심으로 운영할지 결정한다.

## 현재 현황 / 쟁점

GitLab/Gradle이 기준이며 Runner/Jenkins 후보가 병존한다.

## 주안

> **GitLab 중심 SCM+CI를 사용하고 Runner에서 Build/Test/Architecture Gate를 실행한다.**

### 주안 장점

- 도구통합/권한/Commit Trace 단순;  Pipeline-as-Code 일원화.

### 주안 단점

- 기존 Jenkins 자산/플러그인 Migration이 필요할 수 있다.

## 대안

> **GitLab SCM + Jenkins CI/CD로 역할을 분리한다.**

### 대안 장점

- 기존 Jenkins 경험/플러그인/운영자산 활용 가능.

### 대안 단점

- 도구간 권한/Trace/운영이 이중화되고 Integration 복잡도가 증가한다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안(기존 Jenkins 의존도 조사 후)**

**최종 판단기준:** 기존 Pipeline, Plugin, 운영역량, 보안/라이선스

## 결정 전에 확보해야 할 Evidence

- Pipeline Inventory, PoC, Migration Cost

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.34. ADR-TASK-034 — Artifact Promotion / Deployment Trace

**영역:** DevOps  
**Priority:** P0  
**결정 권고시점:** 개발/배포 / G30~G50  
**Decision Owner:** DevOps  
**참여:** EA,Ops,Security  
**현재 상태:** [PROPOSED]

## 의사결정 질문

환경별 재빌드할지 동일 Artifact를 Promotion할지 결정한다.

## 현재 현황 / 쟁점

Architecture Trace에는 sourceCommit→buildId→artifactHash→deploymentId 연결이 필요하다.

## 주안

> **한 번 Build한 Immutable Artifact를 Hash 검증 후 DEV→TEST→PROD/DR로 Promotion한다.**

### 주안 장점

- 환경간 동일 Binary 보장;  감사/롤백/재현성 우수.

### 주안 단점

- 환경차이는 외부 Config로 분리해야 하며 Artifact Repository 운영 필요.

## 대안

> **각 환경에서 Source를 다시 Build하여 배포한다.**

### 대안 장점

- 환경별 Build 옵션 적용이 쉽다.

### 대안 단점

- 동일 Release가 다른 Binary가 될 수 있고 재현성/감사가 약하다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 감사, 재현성, 환경차이, 배포도구

## 결정 전에 확보해야 할 Evidence

- Artifact Hash, Deployment Manifest, Promotion Log

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.35. ADR-TASK-035 — Observability 표준

**영역:** Operations  
**Priority:** P0  
**결정 권고시점:** 개발/운영설계 / G40~G50  
**Decision Owner:** Ops / TA  
**참여:** AA,DevOps,SA  
**현재 상태:** [PROPOSED]

## 의사결정 질문

로그 중심으로 운영할지 Metric/Log/Trace 통합 Observability를 구축할지 결정한다.

## 현재 현황 / 쟁점

ServiceId/GUID/Host/JVM/SqlId/DeploymentId를 Runtime Evidence로 연결할 필요가 있다.

## 주안

> **Metric + Structured Log + Distributed Trace를 통합하고 ServiceId/GUID/DeploymentId 공통태그를 사용한다.**

### 주안 장점

- 성능/장애 원인분석과 E2E 추적 강화;  SLO 자동화 가능.

### 주안 단점

- APM/수집비용과 데이터량/표준화 부담이 크다.

## 대안

> **Application/Access Log 중심으로 운영하고 필요 시 수동 DB/APM 조회한다.**

### 대안 장점

- 도입비용이 작고 기존 운영방식 유지.

### 대안 단점

- 복합장애/분산호출 분석이 느리고 Runtime Evidence 자동화가 어렵다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 서비스 수, 장애분석 요구, SLO, 비용

## 결정 전에 확보해야 할 Evidence

- Observability Coverage, Trace Demo, Alert/Runbook

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.36. ADR-TASK-036 — OM Control Plane 구조

**영역:** Operations  
**Priority:** P1  
**결정 권고시점:** 운영설계 / G30~G50  
**Decision Owner:** Ops / EA  
**참여:** TA,AA,DevOps  
**현재 상태:** [OPEN]

## 의사결정 질문

운영관리 기능을 별도 Control Plane으로 둘지 Business Application에 내장할지 결정한다.

## 현재 현황 / 쟁점

pdmg-om 실제 구현범위는 [UNKNOWN]이며 OM은 Runtime Plane과 책임분리가 필요하다.

## 주안

> **OM을 독립 Control Plane으로 정의하고 Inventory/Metric/Config/배포/통제를 모아 운영한다.**

### 주안 장점

- 업무 Runtime과 운영통제 분리;  권한/감사/가시성 향상.

### 주안 단점

- 별도 시스템/권한/HA 운영비용이 필요하다.

## 대안

> **각 Application에 운영 API/화면을 내장한다.**

### 대안 장점

- 개발이 빠르고 시스템 수가 적다.

### 대안 단점

- 권한/기능 중복, 업무코드 오염, 운영표준 불일치 위험.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 운영기능 범위, 보안, 시스템 수, 자동화 요구

## 결정 전에 확보해야 할 Evidence

- OM Scope, Source Inventory, Ops Use Cases

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.37. ADR-TASK-037 — Configuration / Secret Management

**영역:** Security/DevOps  
**Priority:** P0  
**결정 권고시점:** 개발초기 / G30~G50  
**Decision Owner:** SA / DevOps  
**참여:** Ops,AA  
**현재 상태:** [PROPOSED]

## 의사결정 질문

환경설정과 Secret를 Artifact에 포함할지 외부관리할지 결정한다.

## 현재 현황 / 쟁점

환경별 Config/Key/Secret 분리와 DR 정합이 필요하다.

## 주안

> **Config는 환경별 외부화하고 Secret은 전용 Secret Store/Protected Source에서 Runtime Injection한다.**

### 주안 장점

- Artifact 동일성;  Secret 노출 감소;  Rotation/DR 관리 가능.

### 주안 단점

- Secret Store/Config 배포/권한 체계가 필요하다.

## 대안

> **application.yml/패키지 또는 서버 파일에 Secret/Config를 포함한다.**

### 대안 장점

- 구현과 배포가 간단하다.

### 대안 단점

- 소스/Artifact 유출 위험;  Rotation/환경분리/감사가 어렵다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 보안등급, 환경수, Rotation, DR

## 결정 전에 확보해야 할 Evidence

- Secret Inventory, Config Drift Test, Rotation Test

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.38. ADR-TASK-038 — Backup / Restore 운영기준

**영역:** Operations  
**Priority:** P0  
**결정 권고시점:** 운영/DR설계 / G40~G50  
**Decision Owner:** Ops / DBA  
**참여:** DA,TA,PMO  
**현재 상태:** [PROPOSED]

## 의사결정 질문

Backup 성공을 기준으로 할지 Restore/Business Recovery까지 검증할지 결정한다.

## 현재 현황 / 쟁점

Backup 성공 ≠ Restore 성공 ≠ Business Recovery 성공이다.

## 주안

> **Backup 정책과 별도로 주기적 Restore Test + Data Consistency + Application Validation을 운영한다.**

### 주안 장점

- 실제 복구가능성 검증;  DR/감사 대응 향상.

### 주안 단점

- 테스트 환경/시간/운영비용이 필요하다.

## 대안

> **Backup Job 성공률과 Media 보존상태를 운영 KPI로 사용한다.**

### 대안 장점

- 운영이 단순하고 비용이 적다.

### 대안 단점

- 복구불능 상태를 장애 전까지 발견하지 못할 수 있다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 데이터중요도, RTO/RPO, 감사요건

## 결정 전에 확보해야 할 Evidence

- Restore Drill, Consistency Check, Recovery Report

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.39. ADR-TASK-039 — Production Change / Release 통제

**영역:** DevOps/Operations  
**Priority:** P0  
**결정 권고시점:** 배포설계 / G40~G50  
**Decision Owner:** DevOps / Ops  
**참여:** PMO,EA,SA  
**현재 상태:** [PROPOSED]

## 의사결정 질문

운영배포를 자동 Promotion/승인형으로 할지 수동 작업형으로 할지 결정한다.

## 현재 현황 / 쟁점

생산 배포는 Artifact Trace, Change Approval, Rollback, DR Promotion이 함께 관리되어야 한다.

## 주안

> **승인된 Release Manifest 기반 자동/반자동 Promotion + Health Check + Rollback을 표준화한다.**

### 주안 장점

- 실수감소;  변경이력/재현성/감사 향상;  복구시간 단축.

### 주안 단점

- 배포자동화 구축과 운영프로세스 전환 필요.

## 대안

> **운영자가 Change Ticket 기준으로 수동 배포/재기동한다.**

### 대안 장점

- 기존절차 유지;  도구의존이 적다.

### 대안 단점

- 사람 오류/환경차이/Trace 누락/복구지연 위험.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안**

**최종 판단기준:** 배포빈도, 감사, 서비스중요도, 자동화역량

## 결정 전에 확보해야 할 Evidence

- Deployment Pipeline, Rollback Test, Change Audit

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 6.40. ADR-TASK-040 — Runtime Evidence 자동수집 / Baseline Release Gate

**영역:** Architecture Governance  
**Priority:** P0  
**결정 권고시점:** 통합/오픈 / G50~HG90  
**Decision Owner:** EA / Ops / DevOps  
**참여:** 전 아키텍처영역,PMO  
**현재 상태:** [PROPOSED]

## 의사결정 질문

Architecture 완료판정을 문서/테스트 결과로 끝낼지 Runtime Evidence까지 요구할지 결정한다.

## 현재 현황 / 쟁점

Logging 자체는 Runtime Evidence가 아니며 실제 배포/실행/장애복구 증적이 Architecture Baseline과 연결되어야 한다.

## 주안

> **Critical Rule은 Runtime Evidence를 자동수집하여 G50 이후 Gate에서 Baseline Release 조건으로 사용한다.**

### 주안 장점

- 실제 구현/운영이 Architecture와 일치함을 증명;  Drift 조기발견;  감사가능.

### 주안 단점

- Evidence Collector/Storage/Rule 연결 개발비용이 크다.

## 대안

> **테스트 완료와 문서승인을 Architecture 완료조건으로 사용하고 Runtime Evidence는 운영 후 수동확인한다.**

### 대안 장점

- 오픈 전 부담이 낮고 일정이 빠르다.

### 대안 단점

- 운영환경 Drift와 비기능 미충족을 늦게 발견할 수 있다.

## 비교 의사결정

| 비교항목 | 주안 | 대안 |
|---|---|---|
| Architecture 정합 | 높음 | 상대적으로 낮음/조건부 |
| 구현 복잡도 | 중~높음 | 낮음~중 |
| 운영/장애 대응 | 상대적으로 우수 | 단순하지만 제한적 |
| Traceability | 우수 | 수동 보완 필요 |
| Migration 비용 | 존재 | 상대적으로 적음 |

## 권고

**권고안: 주안(중요 Rule부터 단계적)**

**최종 판단기준:** Criticality, 자동화비용, 규제/감사, 운영성

## 결정 전에 확보해야 할 Evidence

- Runtime Evidence Index, Gate Result, Drift Report

## 의사결정 종료조건

```text
주안/대안 비교 완료
   ↓
필요 Evidence 확보
   ↓
Architecture Review
   ↓
ADR 승인
   ↓
Architecture Model / Standard 반영
   ↓
Source / Runtime 검증 Rule 등록
```

---

# 7. 공통 Architecture Decision 평가기준

| 평가축 | 질문 |
|---|---|
| Business Fit | 업무목표/업무완료조건에 맞는가? |
| Architecture Fit | Vision/Big Picture/Logical/Physical/Mechanism/Runtime에 정합하는가? |
| Performance | p95/TPS/Batch Window을 충족할 수 있는가? |
| Availability | Node/Group/Center 장애 시 서비스 지속 가능한가? |
| Scalability | Scale-out/Scale-up 단위가 명확한가? |
| Security | 인증/인가/키/비밀/데이터 보호가 가능한가? |
| Data Integrity | 중복/유실/정합성/복구를 통제하는가? |
| Operability | 모니터링/Alert/Runbook/복구가 가능한가? |
| Deployability | Build/Artifact/배포/롤백이 추적 가능한가? |
| Cost | HW/SW License/운영인력/구축비용이 적정한가? |
| Migration | 현재 소스/데이터/인프라에서 전환 가능성이 높은가? |
| Evidence | PoC/Test/Runtime으로 검증 가능한가? |

---

# 8. Architecture Decision Task 표준 프로세스

```text
[1] Decision Task 등록
    ↓
[2] Fact / Constraint 확보
    ↓
[3] 주안 / 대안 설계
    ↓
[4] 장단점 / Risk / Cost 비교
    ↓
[5] PoC / Test / Sizing / Security Review
    ↓
[6] Architecture Review Board
    ↓
[7] ADR 승인
    ↓
[8] Architecture Model / 표준 반영
    ↓
[9] CI / Conformance Rule 등록
    ↓
[10] Runtime Evidence 재검증
```

---

# 9. ADR 표준 Template

```yaml
adr:
  adrId:
  decisionTaskId:
  title:
  status: PROPOSED | APPROVED | REJECTED | SUPERSEDED
  context:
  constraints:
  mainOption:
    description:
    pros:
    cons:
  alternative:
    description:
    pros:
    cons:
  decision:
  rationale:
  consequences:
  evidence:
  owner:
  approvers:
  effectiveDate:
  reviewDate:
  supersedes:
```

---

# 10. 회의용 Architecture Decision Sheet

| 항목 | 작성내용 |
|---|---|
| Decision Task ID | ADR-TASK-XXX |
| 결정질문 | 무엇을 결정해야 하는가? |
| 결정기한 | 어느 Gate 전에 끝나야 하는가? |
| 현황/Fact | 확인된 Source/Config/Runtime 사실 |
| Constraint | 예산/일정/보안/전사표준/Legacy |
| 주안 | 기본 권고안 |
| 주안 장점 | 3개 이상 |
| 주안 단점 | 2개 이상 |
| 대안 | 실행 가능한 대체안 |
| 대안 장점 | 2개 이상 |
| 대안 단점 | 2개 이상 |
| Evidence | PoC/부하/보안/장애/비용 자료 |
| 권고 | 주안/대안/추가검증 |
| 결정권자 | Owner/Approver |
| ADR | 승인 후 번호 |

---

# 11. 프로젝트 진행 단계별 Decision Gate

```text
착수 / Baseline
  └─ Governance / Code / Ownership

논리설계
  └─ Application / Data / Interface Boundary

물리·기술설계
  └─ Runtime / Infrastructure / HA / DR / Capacity

상세개발
  └─ Message / Error / Timeout / Retry / Security / CI

통합시험
  └─ Performance / Failure / Recovery / Observability

오픈준비
  └─ Deployment / Backup / DR / Runtime Evidence

HG90
  └─ Approved Architecture Baseline
```

---

# 12. 최종 결론

> **아키텍처 의사결정의 핵심은 '좋아 보이는 기술을 선택하는 것'이 아니라, 프로젝트 Constraint와 NFR을 기준으로 주안과 대안을 같은 수준에서 비교하고, Evidence를 통해 결정한 후 그 결과를 Architecture Baseline·Source·Runtime에 끝까지 연결하는 것이다.**

> **본 문서의 P0 항목은 오픈 전에 반드시 의결되어야 하는 후보이며, 특히 JWT 정합성, TCF/Business Core, Timeout/Retry, RDW/ADW, Capacity, HA/DR, Artifact/Runtime Evidence는 미결정 상태가 장기화되지 않도록 Gate Owner를 지정해야 한다.**