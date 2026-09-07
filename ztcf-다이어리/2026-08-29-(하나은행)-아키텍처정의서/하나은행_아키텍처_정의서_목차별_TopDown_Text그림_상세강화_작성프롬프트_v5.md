# 하나은행 아키텍처 정의서 목차별 작성 프롬프트
## V5 — Evidence-Maturity Adaptive · Diagram-Driven · Review-Ready Top-down 버전

> 기준문서: `하나은행_아키텍처_정의서_목차.md`  
> 역검증 입력: `I. 개요`, `II. 시스템 구성`, `III. 마케팅플랫폼 및 데이터허브 아키텍처` 실제 생성본  
> 목적: 각 목차를 **그림이 본체이고 설명·표가 그림을 증명하는 아키텍처 정의서**로 작성하기 위한 독립 실행형 프롬프트 모음  
> 핵심: **L0→L1→L2→L3 Top-down, Evidence-First, 독립 Figure Slot, 정상/예외/운영 분리, Source/Target/책임/계약/Traceability, GAP/TBD 시각화**

---

# 0. V5에서 다시 바꾼 이유

실제 `III. 마케팅플랫폼 및 데이터허브 아키텍처` 생성 결과를 역검증하면 다음 문제가 남았다.

1. 상위 묶음 절 `III.1`, `III.2`가 다시 `상세는 하위 절` 형태로 축약되었다.
2. `III.1.7 거래패턴`의 8개 패턴이 각각 독립된 상세 그림으로 충분히 풀리지 않았다.
3. `III.1.5 실행 제어`는 Timeout·중복·로그레벨·선후처리라는 서로 다른 Runtime 메커니즘이 상위 그림에 압축되었다.
4. `III.4`는 CDC/ETCL/BC를 개별 아키텍처와 장애·재처리 관점으로 분리하지 못했다.
5. `III.5 Hydra`, `III.6 컨테이너`, `III.7 암복호`, `III.8 CI/CD`는 목차 중요도에 비해 상위 그림 1~2개 중심으로 끝났다.
6. 입력자료가 부족한 경우 `[TBD]` 표시는 잘 했지만, **알 수 없는 경계·소유·계약을 그림으로 보여 주는 Evidence/GAP View**가 부족했다.
7. 표가 상세관계를 설명하면서 Text 그림을 대신하는 경우가 있었다.

V5는 더 많은 지시를 무작정 추가하는 방식이 아니라, **각 절을 작은 View Bundle로 완결시키는 실행 계약**으로 재구성한다.

---


## 0.1 IV 실제 생성본 역검증으로 추가된 문제

`IV. 기타 시스템 아키텍처` 실제 생성본까지 역검증한 결과, V5에서는 다음 문제를 추가로 교정한다.

1. **Evidence가 풍부한 시스템과 부족한 시스템을 동일한 Figure Slot으로 처리**하면 품질이 낮아진다.
   - BI포탈·데이터흐름관리처럼 AP/DB/HA/제품/스펙 근거가 있는 절은 실제 Topology·Sequence·Failover·Operation 그림을 더 깊게 내려야 한다.
   - Self BI·SSO/EAM처럼 이름·목차만 있고 설계자료가 부족한 절은 억지 Runtime을 그리기보다 Discovery·Question·Option·Decision Gate를 그려야 한다.
2. `[TBD]`가 많을 때 같은 형태의 빈 그림이 반복될 수 있다. **TBD 자체를 구조화한 Gap/Discovery Architecture**가 필요하다.
3. IV 시스템들은 I~III와 달리 소비·거버넌스·인증이라는 서로 다른 역할을 가지므로, 하나의 공통 템플릿만으로는 책임 경계가 흐려진다.
4. BI포탈과 BIZ메타처럼 **물리 스택 공유 가능성**이 있는 경우 `공유/분리 옵션`, `책임 중첩`, `데이터 저장소 소유`를 별도 그림으로 보여야 한다.
5. Self BI처럼 To-Be 제품/Runtime이 미확정인 경우 `현재 알고 있는 것`보다 **무엇을 확인해야 아키텍처가 닫히는가**가 핵심 산출물이다.
6. SSO/EAM처럼 AS-IS 담당자 문의가 목차에 명시된 경우, 설계서가 단순 GAP 선언으로 끝나지 않고 **AS-IS Discovery Pack → Target Decision Pack**을 제공해야 한다.
7. 장 단위 Roll-up에서 I~IV 전체의 `사용자→서비스→데이터→메타→인증→운영` 관계와 ADR/GAP 우선순위를 한 번 더 통합해야 한다.

V5는 이를 위해 **Evidence Maturity Adaptive 방식**과 **Discovery/Decision View Bundle**을 추가한다.

# 1. V5 최우선 실행 계약

아래 규칙은 모든 개별 프롬프트보다 우선한다.

## 1.1 Evidence-First

문서에 없는 메커니즘을 일반적인 아키텍처 지식으로 채우지 않는다.

```text
입력자료에서 확인
      │
      ├─ 확인됨 ─────► [FACT]
      │
      ├─ 사실로부터 해석 가능 ─► [ANALYSIS]
      │
      ├─ 목표/권고안 ──────────► [TO-BE/PROPOSED]
      │
      └─ 근거 없음 ────────────► [TBD/GAP]
                                      │
                                      └─ 그림에서 빈 박스/경계로 표시
```

다른 은행, NSIGHT, TCF, 일반 베스트프랙티스 자료는 **비교·제안 근거**로만 사용한다. 하나은행 FACT로 승격하지 않는다.

## 1.2 Diagram-First

각 절은 먼저 `0. Evidence Register`와 `1. Figure Plan`을 만든 다음 그림을 작성한다.  
표·문장·불릿은 필수 그림을 대체할 수 없다.

## 1.3 L0→L1→L2→L3

```text
L0  왜 / 전체 Context
 ↓
L1  영역 / 서비스 / 계층
 ↓
L2  Component / Application / Node / SW / DB / Contract
 ↓
L3  Runtime Sequence / Data Flow / State / Failure / Recovery
```

동적 주제는 L3가 필수다. 정적 표준/명명 주제는 L3를 `Decision / Validation / Governance Flow`로 대체한다.

## 1.4 View Bundle

동적인 메커니즘 하나를 설명할 때 최소 다음 5개 관점을 분리한다.

```text
[Static Structure]
       ↓
[Happy Path Sequence]
       ↓
[Failure / Exception]
       ↓
[Operation / Observability / Recovery]
       ↓
[Responsibility / Contract / Decision]
```

한 그림에 5개를 몰아넣지 않는다.

## 1.5 항목 수만큼 독립 그림

목차가 `8개 거래패턴`, `CDC/ETCL/BC`, `저장/표시/전송`, `VM/Container`처럼 복수 항목을 명시하면 **각 항목마다 독립 그림을 만든다.**

예:

```text
거래패턴 8종
  ├─ Pattern-01 그림
  ├─ Pattern-02 그림
  ├─ ...
  └─ Pattern-08 그림
      +
  전체 Catalog / Decision / 공통 NFR 그림
```

## 1.6 상위 묶음 절도 축약 금지

`III.1 온라인어플리케이션`, `III.2 배치어플리케이션`, `II.4 논리노드 구성` 같은 상위 절은 하위 절이 존재하더라도 자체적으로 다음을 작성한다.

- 전체 Big Picture
- 하위 절 간 관계
- 공통 Runtime
- 공통 장애/운영
- 공통 책임/협업
- 하위 절 Handoff

`상세는 하위 절 참조`, `4~12.`, `9~12.`, `상동`으로 끝내지 않는다.

## 1.7 GAP도 그림으로 작성

자료가 없으면 그림을 생략하지 않는다.

```text
[확정 Source]
     │
     ▼
[확정 Component]
     │
     ├────────► [TBD Contract]
     │                 │
     │                 └─ 필요한 자료: XXXXX
     ▼
[GAP Target / Owner]
```

## 1.8 그림별 상세해설

각 필수 그림 아래 최소 다음 8개를 설명한다.

1. 그림 목적
2. 근거자료와 상태 `[FACT]/[ANALYSIS]/[PROPOSED]/[TBD]`
3. Boundary / In / Out
4. Trigger 또는 시작점
5. 번호를 붙인 처리순서
6. 구성요소별 책임·비책임
7. 화살표가 운반하는 데이터/전문/상태/제어
8. 실패·운영 의미와 다음 FIG/절/ADR/GAP 연결

## 1.9 표-그림 상호검증

관계형 표를 작성하면 동일 관계를 보여주는 Text 그림이 반드시 하나 이상 있어야 한다.

```text
Application | Node | SW | DB
```

표만 두지 말고:

```text
[Application]
     │ deployed-on
     ▼
[Logical Node]
     │ runtime
     ▼
[Software]
     │ JDBC
     ▼
[Database]
```

## 1.10 Completion Gate

절 마지막에 반드시 아래를 수치로 확인한다.

| 검사 | PASS 기준 |
|---|---|
| Figure Plan 필수 수 | 실제 FIG 수와 동일 |
| L0/L1/L2 | 모두 존재 |
| 동적 절 L3 | 정상 + 실패/복구 존재 |
| 복수 항목 | 항목별 독립 FIG 존재 |
| 관계표 | 대응 그림 존재 |
| GAP/TBD | 필요한 경우 그림에도 표현 |
| 협업태그 | 누락 없음 |
| 창작값 | 0건 |
| 축약표현 | 0건 |
| Handoff | 다음 절 연결 FIG 존재 |

하나라도 FAIL이면 최종평가 전에 누락 산출물을 먼저 작성한다.

---


## 1.11 Evidence Maturity Gate — 절마다 먼저 판정

Figure Plan을 만들기 전에 해당 절의 근거 성숙도를 아래 세 등급 중 하나로 판정한다.

```text
                    Evidence Maturity
                           │
          ┌────────────────┼────────────────┐
          ▼                ▼                ▼
       M3 RICH          M2 PARTIAL        M1 SPARSE
   구조+Runtime+       일부 구조/제품      목차/명칭/
   Data+HA 근거        은 있으나 핵심       담당자 표시만
   다수 존재           계약·기능 미확정     존재
          │                │                │
          ▼                ▼                ▼
 Topology/Sequence   Known+TBD Overlay   Discovery/Question/
 Failover/Operation  Option/Gap Map      Option/Decision Gate
```

### M3 — 근거 풍부형
다음 중 5개 이상이 독립 근거로 존재하면 M3 후보로 본다.

- 시스템 Context/구성도
- 제품/솔루션명
- AP/DB/노드
- HA 구조
- 데이터 소스/타깃
- Runtime/인터페이스
- 환경(DEV/TEST/PROD/DR)
- 운영/스펙
- 업무 기능/사용자

**필수:** 실제 Topology, 정상 Sequence, 장애/Failover, Data Access, Operation/Monitoring, AS-IS/TO-BE.

### M2 — 부분확정형
일부 물리/논리 근거는 있으나 핵심 역할·Owner·계약이 빠진 경우.

**필수:** Confirmed View + TBD Overlay + 책임중첩 Map + Option 비교 + 결정 필요사항 + 질문목록.

### M1 — 근거부족형
목차에 시스템명/작성방향만 있고 설계자료가 거의 없는 경우.

**금지:** 일반적인 제품/계층/프로토콜/세션/Runtime을 사실처럼 채우기.

**필수:**

```text
Confirmed Facts
      ↓
Unknown Boundary
      ↓
Required Evidence
      ↓
Interview Questions
      ↓
Architecture Options [PROPOSED]
      ↓
Decision Criteria / Gate
      ↓
Target Figure [TBD skeleton]
```

M1의 상세성은 **가짜 구현 상세**가 아니라 **의사결정을 완료하기 위한 구조적 상세성**으로 만든다.

## 1.12 Figure Substance Gate — 작은 그림 남발 금지

필수 FIG 수를 맞추기 위해 의미 없는 2~3줄 그림을 반복하지 않는다.

각 일반 FIG는 가능한 범위에서 다음을 만족해야 한다.

- 최소 3개 이상의 의미 있는 Node/State/Decision 요소
- 최소 2개 이상의 관계/흐름
- 시작점과 종료점 또는 In/Out Boundary
- 다른 FIG와 중복되지 않는 질문에 답함
- 그림 안에서 `[FACT]`, `[TBD]`, `[DEP]`, `[OUT]`의 경계가 식별 가능

단, `Decision Tree`, `State Machine`, `Gap Map`, `Responsibility Map`은 시스템 노드 수 대신 **3개 이상의 결정/상태/책임 축**을 만족하면 된다.

## 1.13 Discovery / Decision View Bundle — M1/M2 전용

자료가 부족한 절은 다음 7종을 우선 사용한다.

```text
D1 Confirmed Fact Boundary
D2 Unknown / GAP Topology
D3 Dependency / Owner Map
D4 Required Evidence Map
D5 Interview Question Flow
D6 Architecture Option Map
D7 Decision Criteria / Approval Gate
```

질문 목록은 단순 불릿이 아니라 다음 형태로 Traceability를 갖춘다.

| Question ID | 확인 질문 | 왜 필요한가 | 기대 Evidence | Owner | 미확정 시 영향 | 연결 FIG/ADR |
|---|---|---|---|---|---|---|

## 1.14 Cross-Chapter Integration Gate

각 장과 주요 절은 반드시 선행·후행을 그림으로 연결한다.

```text
I. Why / Boundary / Principle
        ↓
II. Service / App / Node / SW / DB
        ↓
III. Runtime / Batch / IF / Security / CI-CD
        ↓
IV. Consumption / Metadata / Self BI / SSO
        ↓
Architecture Baseline / ADR / GAP Closure
```

IV에서는 특히 다음 관계를 별도로 검증한다.

```text
[Users]
   │
   ├─ BI Portal ───────┐
   ├─ Self BI ─────────┼─► [RTW/ADW/BSA/Data Virtualization]
   └─ BizMeta ─────────┘

[ETCL/CDC/EAI/Source Code/DAMS]
   └─► Data Flow Management ──► BI Portal / BizMeta / DAMS

[Non-standard Terminal]
   └─► SSO/EAM [TBD] ──► Application
```

## 1.15 AS-IS → Transition → TO-BE 세 단계

AS-IS와 TO-BE가 모두 관련되면 단순 비교표로 끝내지 않는다.

```text
AS-IS
  │ 유지 / 변경 / 폐기 / 대체 / 공존
  ▼
Transition Architecture
  │ 데이터이관 / 병행운영 / 호환 / Cut-over / Rollback [근거 범위]
  ▼
TO-BE
```

Transition 근거가 없으면 `[TBD Transition]`으로 표시하고 필요한 결정/자료를 연결한다.

## 1.16 Claim-to-Evidence Traceability

중요한 결론마다 최소 하나의 Evidence ID를 부여한다.

```text
EV-01 장표/회의록/정책
   │
   ▼
CL-01 Claim
   │
   ├─► FIG-xx
   ├─► Decision/Rule
   └─► Verification
```

제품명 하나가 장표에 존재한다고 해서 해당 제품의 역할·버전·HA·To-Be 채택까지 자동 확장하지 않는다.

## 1.17 Review-Ready 산출물 강제

각 절 마지막에는 설계 검토자가 바로 사용할 수 있도록 다음을 추가한다.

1. **What is decided** — 확정 3~10개
2. **What blocks approval** — 승인 차단 GAP
3. **Who must answer** — Owner/협업조직
4. **What evidence is required** — 필요한 원문/장표/설정/인터뷰
5. **What changes if the answer changes** — 영향 FIG/ADR/절
6. **Next review agenda** — 다음 검토회의 안건

# 2. 공통 Text 그림 문법

```text
┌──────── Boundary / Zone / Layer ────────┐
│ [Component A]                           │
│       │ Request / Data / Trigger        │
│       ▼                                 │
│ [Component B] ──Response/Event──► [C]   │
└─────────────────────────────────────────┘

→  단방향 호출/데이터 이동
←  역방향 응답/반환
↔  양방향 또는 명시적 쌍방
-x→ 금지/차단/실패
...► 비동기/지연/후행 의미가 근거자료에 있을 때만 사용

[F] FACT
[A] ANALYSIS
[P] TO-BE/PROPOSED
[T] TBD/GAP
```

실제 그림에서 태그가 너무 복잡하면 그림 제목 아래 `근거상태:`로 분리해도 된다.

---

# 3. 모든 개별 프롬프트의 강제 출력 목차

각 절은 아래 번호를 생략하지 않는다.

```text
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가
```

해당 관점이 현재 절의 직접 범위가 아니어도 번호를 없애지 않는다. 대신 `현재 절 영향 → 후속 절 → 필요한 입력자료`를 그림으로 표시한다.

---

# 4. 개별 목차별 독립 실행 프롬프트

아래 각 코드블록은 **그 자체로 복사해서 실행**할 수 있다.

## I. 개요
**작성 목적:** 아키텍처 정의서 전체의 Why, 수행방식, 범위, 원칙, 시스템 경계를 한 장의 논리로 연결한다.

**핵심 입력자료:** 목차 I.1~I.5, 기작성 배경자료, 요구사항/NFR, 정책/규정, Context 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
I. 개요

[작성 목적]
아키텍처 정의서 전체의 Why, 수행방식, 범위, 원칙, 시스템 경계를 한 장의 논리로 연결한다.

[우선 확인할 입력]
목차 I.1~I.5, 기작성 배경자료, 요구사항/NFR, 정책/규정, Context 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. L0 전체 장 Big Picture: 배경→Approach→범위→원칙→Context→후속 장
2. Why 계층: 현행/변화요인→사업목표→시스템목표→아키텍처목표
3. Approach 3단계와 Gate/Feedback
4. In/Out/Interface/Dependency 범위
5. 설계원칙 입력→원칙→결정→검증
6. System Context Level-0
7. Actor/User→Target 사용목적
8. 외부/내부 Boundary 및 데이터 흐름
9. FACT/GAP/TBD/ADR Evidence Map
10. I→II→III→IV Handoff

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## I.1 시스템 구축 배경 및 목적
**작성 목적:** 현행환경과 변화요인을 분리하고 사업목표·시스템 구축목표·아키텍처 목표를 Top-down으로 정의한다.

**핵심 입력자료:** 기작성 배경자료, 회의록/ADR, 현행 구성도, 변화요인 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
I.1 시스템 구축 배경 및 목적

[작성 목적]
현행환경과 변화요인을 분리하고 사업목표·시스템 구축목표·아키텍처 목표를 Top-down으로 정의한다.

[우선 확인할 입력]
기작성 배경자료, 회의록/ADR, 현행 구성도, 변화요인 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 현행 환경 Landscape
2. 변화요인/문제점 Cause Map
3. 현행→변화→사업목표→시스템목표→아키텍처목표
4. 기능목표 vs 비기능/운영목표
5. 영향 시스템/부분개선 Scope
6. 변경 최소화 Boundary
7. AS-IS vs TO-BE 변화점
8. 미확정 사업 KPI/TBD Map

- 필수 슬롯 수: 8개. 실제 FIG 코드블록도 최소 8개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=8, 실제 필수 FIG>=8인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## I.2 아키텍처 정의 수행 Approach
**작성 목적:** 범위선정→설계원칙→상세화의 선후관계, 입력물, Gate, 반복조건, 협업주체를 정의한다.

**핵심 입력자료:** 목차 I.2, 수행방법론, 요구사항/NFR, 정책/규정, 협업표시

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
I.2 아키텍처 정의 수행 Approach

[작성 목적]
범위선정→설계원칙→상세화의 선후관계, 입력물, Gate, 반복조건, 협업주체를 정의한다.

[우선 확인할 입력]
목차 I.2, 수행방법론, 요구사항/NFR, 정책/규정, 협업표시

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Approach 3단계 Big Picture
2. 입력자료→단계별 투입 Mapping
3. Gate-1/2/3 승인 흐름
4. Feedback/반려 Loop
5. 협업주체 Swimlane
6. 산출물 생성 Flow
7. FACT/ANALYSIS/PROPOSED 분리 Flow
8. Baseline 동결 Handoff

- 필수 슬롯 수: 8개. 실제 FIG 코드블록도 최소 8개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=8, 실제 필수 FIG>=8인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## I.3 아키텍처 정의 범위
**작성 목적:** 전체 과업에서 아키텍처 정의 대상과 비대상, 연계범위, 2사업/TA/DA/FW 등 의존영역을 명확히 분리한다.

**핵심 입력자료:** 목차 I.3, 사업 R&R, 계약/과업범위, 시스템 목록, 협업표시

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
I.3 아키텍처 정의 범위

[작성 목적]
전체 과업에서 아키텍처 정의 대상과 비대상, 연계범위, 2사업/TA/DA/FW 등 의존영역을 명확히 분리한다.

[우선 확인할 입력]
목차 I.3, 사업 R&R, 계약/과업범위, 시스템 목록, 협업표시

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 전체 과업→정의대상 Scope Funnel
2. In-Scope / Out-of-Scope / IF / Dependency 4분면
3. 업무/앱/데이터/IF/인프라/보안/운영 범위
4. 2사업 역할분담 Boundary
5. TA/DA/FW/Hydra/보안 협업 Boundary
6. 목차→범위 Traceability
7. Scope Decision Tree
8. 범위 GAP/TBD Map

- 필수 슬롯 수: 8개. 실제 FIG 코드블록도 최소 8개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=8, 실제 필수 FIG>=8인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## I.4 아키텍처 설계 원칙 정의
**작성 목적:** 요구사항·은행정책·운영기준·법규를 근거로 설계원칙을 만들고 우선순위·예외·검증방법을 정의한다.

**핵심 입력자료:** 목차 I.4, 기능/비기능요구, 은행정책, 규정/법규, 현행 제약

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
I.4 아키텍처 설계 원칙 정의

[작성 목적]
요구사항·은행정책·운영기준·법규를 근거로 설계원칙을 만들고 우선순위·예외·검증방법을 정의한다.

[우선 확인할 입력]
목차 I.4, 기능/비기능요구, 은행정책, 규정/법규, 현행 제약

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 원칙 도출 Input→Principle→Decision→Verification
2. 원칙 분류 Map(앱/데이터/IF/인프라/보안/운영)
3. Must/Should/May 적용 흐름
4. 원칙 충돌 Resolution Flow
5. 예외/ADR 승인 Flow
6. 원칙→후속 장 적용 Mapping
7. 원칙 검증 Lifecycle
8. 근거 부족 원칙/TBD Map

- 필수 슬롯 수: 8개. 실제 FIG 코드블록도 최소 8개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=8, 실제 필수 FIG>=8인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## I.5 시스템 Context 다이어그램
**작성 목적:** Target Boundary, 사용자, 외부/인접시스템, 주고받는 데이터 종류, 주요 연계 방향을 한눈에 합의한다.

**핵심 입력자료:** 목차 I.5, Context 자료, 시스템목록, 인터페이스/데이터흐름, 사용자/채널 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
I.5 시스템 Context 다이어그램

[작성 목적]
Target Boundary, 사용자, 외부/인접시스템, 주고받는 데이터 종류, 주요 연계 방향을 한눈에 합의한다.

[우선 확인할 입력]
목차 I.5, Context 자료, 시스템목록, 인터페이스/데이터흐름, 사용자/채널 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Level-0 System Context
2. Target 내부/외부 Boundary
3. Actor/User→Target Use Case Map
4. 원천시스템→Target 데이터 유입
5. Target→소비시스템 데이터 제공
6. 온라인 단말 Runtime Context
7. 데이터허브 내부 저장계층 Context
8. Character Set/Format Boundary
9. 주요 Interface Type Context
10. 장애/중계 실패 Context
11. 협업/소유 Boundary
12. Context→II.1 Handoff

- 필수 슬롯 수: 12개. 실제 FIG 코드블록도 최소 12개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=12, 실제 필수 FIG>=12인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II. 시스템 구성
**작성 목적:** 서비스/업무 View를 Application→Logical Node→Software→Database→Target IT View로 단계적으로 내린다.

**핵심 입력자료:** 목차 II, I.5 Context, 앱맵, 서버/솔루션 인벤토리, TA/DA 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II. 시스템 구성

[작성 목적]
서비스/업무 View를 Application→Logical Node→Software→Database→Target IT View로 단계적으로 내린다.

[우선 확인할 입력]
목차 II, I.5 Context, 앱맵, 서버/솔루션 인벤토리, TA/DA 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. II 전체 View Stack
2. 5개 시스템 영역 Big Picture
3. 서비스/업무 View
4. Application View
5. Logical Node View
6. Software/Data View
7. Application→Node→SW→DB 수직 Traceability
8. 환경 DEV/TEST/PROD/DR
9. Target IT 통합 View
10. II→III Runtime Handoff

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.1 시스템 영역 구분
**작성 목적:** 정보전달, 데이터 분석 및 정보제공, 데이터 저장소, 데이터 수집 및 연계, 인프라의 5영역을 역할·입출력·의존관계로 정의한다.

**핵심 입력자료:** 목차 II.1, I.5 Context, 시스템/솔루션 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.1 시스템 영역 구분

[작성 목적]
정보전달, 데이터 분석 및 정보제공, 데이터 저장소, 데이터 수집 및 연계, 인프라의 5영역을 역할·입출력·의존관계로 정의한다.

[우선 확인할 입력]
목차 II.1, I.5 Context, 시스템/솔루션 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 5영역 전체 Layer Map
2. 정보전달 영역 Drill-down
3. 분석·정보제공 영역 Drill-down
4. 데이터 저장소 영역 Drill-down
5. 수집·연계 영역 Drill-down
6. 인프라 영역 Drill-down
7. 영역 간 정상 데이터 흐름
8. 영역 간 금지/예외/TBD Boundary
9. 영역 Owner/Dependency Map

- 필수 슬롯 수: 9개. 실제 FIG 코드블록도 최소 9개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=9, 실제 필수 FIG>=9인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.2 목표 업무시스템 구성도
**작성 목적:** 기술제품이 아니라 서비스 관점으로 1단계 목표 업무시스템, 사용자, 데이터서비스, 인접업무를 구성한다.

**핵심 입력자료:** 목차 II.2, 업무시스템 구성도, I.5 Context, 요구사항

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.2 목표 업무시스템 구성도

[작성 목적]
기술제품이 아니라 서비스 관점으로 1단계 목표 업무시스템, 사용자, 데이터서비스, 인접업무를 구성한다.

[우선 확인할 입력]
목차 II.2, 업무시스템 구성도, I.5 Context, 요구사항

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 서비스 관점 전체 업무시스템
2. 사용자/채널→업무서비스
3. 마케팅 서비스 Drill-down
4. 데이터허브/데이터서비스 Drill-down
5. BI/메타/Self BI/흐름관리 서비스
6. 부분개선 시스템 Scope
7. 인접 코어/채널 Boundary
8. 온라인/배치/실시간 대표 서비스 흐름
9. AS-IS vs TO-BE 업무서비스

- 필수 슬롯 수: 9개. 실제 FIG 코드블록도 최소 9개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=9, 실제 필수 FIG>=9인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.3 어플리케이션 구성
**작성 목적:** 업무서비스를 은행 IT 관리체계의 Application/Lv2/Lv3 구조로 추적 가능하게 만든다.

**핵심 입력자료:** 목차 II.3, TO-BE 앱맵, 시스템코드/서비스그룹 정책

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.3 어플리케이션 구성

[작성 목적]
업무서비스를 은행 IT 관리체계의 Application/Lv2/Lv3 구조로 추적 가능하게 만든다.

[우선 확인할 입력]
목차 II.3, TO-BE 앱맵, 시스템코드/서비스그룹 정책

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Domain→Application→Service Group→Lv3 Tree
2. 1단계 Application Overlay
3. 신규/변경/유지 Application Map
4. Application Dependency Map
5. Application→Data/IF 관계
6. Application→Logical Node Handoff
7. 공식 코드 vs 임시 식별자 Gap Map
8. II.3.1→II.3.2 연결

- 필수 슬롯 수: 8개. 실제 FIG 코드블록도 최소 8개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=8, 실제 필수 FIG>=8인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.3.1 어플리케이션 맵
**작성 목적:** 은행 TO-BE 앱맵을 보존하면서 1단계 신규/변경/유지 애플리케이션의 위치와 관리 식별체계를 정의한다.

**핵심 입력자료:** 목차 II.3.1, 공식 AA맵, 코드정책, 프로젝트 대상 시스템

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.3.1 어플리케이션 맵

[작성 목적]
은행 TO-BE 앱맵을 보존하면서 1단계 신규/변경/유지 애플리케이션의 위치와 관리 식별체계를 정의한다.

[우선 확인할 입력]
목차 II.3.1, 공식 AA맵, 코드정책, 프로젝트 대상 시스템

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 은행 공식 AA Tree
2. 1단계 Overlay Map
3. 신규/변경/유지 상태 Map
4. System Code↔Service Group 관계
5. Application ID 결정 Flow
6. EAI/형상/모델 관리키 Mapping
7. 공식코드 미입수 Gap Map
8. Application Map→Definition Handoff

- 필수 슬롯 수: 8개. 실제 FIG 코드블록도 최소 8개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=8, 실제 필수 FIG>=8인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.3.2 어플리케이션 정의
**작성 목적:** 식별된 각 애플리케이션의 목적·서비스·사용자·연계·데이터·Owner를 포괄적으로 정의한다.

**핵심 입력자료:** 목차 II.3.2, 앱맵, 업무기능, 인터페이스/데이터 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.3.2 어플리케이션 정의

[작성 목적]
식별된 각 애플리케이션의 목적·서비스·사용자·연계·데이터·Owner를 포괄적으로 정의한다.

[우선 확인할 입력]
목차 II.3.2, 앱맵, 업무기능, 인터페이스/데이터 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Application Portfolio Big Picture
2. 앱별 책임 Boundary
3. Application→Service Map
4. Application→User Map
5. Application→Interface Map
6. Application→Data Map
7. Application Dependency Call Map
8. 중복/중첩 책임 Conflict Map
9. Application→Node Handoff

- 필수 슬롯 수: 9개. 실제 FIG 코드블록도 최소 9개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=9, 실제 필수 FIG>=9인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.4 논리노드 구성
**작성 목적:** 애플리케이션 워크로드를 VM/Container/Bare Metal, 4환경, SW, DB와 연결하는 기술 배치체계를 정의한다.

**핵심 입력자료:** 목차 II.4, PMO 노드매트릭스, 서버/솔루션/DB 인벤토리

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.4 논리노드 구성

[작성 목적]
애플리케이션 워크로드를 VM/Container/Bare Metal, 4환경, SW, DB와 연결하는 기술 배치체계를 정의한다.

[우선 확인할 입력]
목차 II.4, PMO 노드매트릭스, 서버/솔루션/DB 인벤토리

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Workload→Node Type Decision
2. DEV/TEST/PROD/DR Environment Map
3. Application→Logical Node Mapping
4. Logical Node Topology
5. Node→Software Stack
6. Node→Database/Storage
7. HA/Failover
8. DR/TBD Topology
9. Node/SW/DB Traceability
10. II.4→II.5 Handoff

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.4.1 논리노드 식별
**작성 목적:** 워크로드별 논리노드를 식별하고 VM/Container/베어메탈 Baseline과 역할·HA·DR 후보를 매핑한다.

**핵심 입력자료:** 목차 II.4.1, PMO 매트릭스, 서버매핑, 물리장표

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.4.1 논리노드 식별

[작성 목적]
워크로드별 논리노드를 식별하고 VM/Container/베어메탈 Baseline과 역할·HA·DR 후보를 매핑한다.

[우선 확인할 입력]
목차 II.4.1, PMO 매트릭스, 서버매핑, 물리장표

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Logical Node Identification Big Picture
2. Workload→VM/Con/BM Decision Tree
3. 온라인 AP 노드 후보
4. ETL/Batch 노드 후보
5. DW/DB Appliance 노드
6. BI/메타/흐름관리 노드
7. Integration Node
8. Node Ownership/2사업 Boundary
9. 확정 Node vs TBD Node
10. Node→Environment Handoff

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.4.2 노드 구성환경 구분
**작성 목적:** 개발/테스트/운영/DR 환경을 각각 독립 구조로 그리고 환경 간 승격·차이·데이터 이동·보안 제약을 정의한다.

**핵심 입력자료:** 목차 II.4.2, 환경별 장표, 운영기준, DR 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.4.2 노드 구성환경 구분

[작성 목적]
개발/테스트/운영/DR 환경을 각각 독립 구조로 그리고 환경 간 승격·차이·데이터 이동·보안 제약을 정의한다.

[우선 확인할 입력]
목차 II.4.2, 환경별 장표, 운영기준, DR 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 4환경 전체 Lifecycle
2. DEV Topology
3. TEST Topology
4. PROD Topology
5. DR Topology 또는 TBD Boundary
6. DEV→TEST Promotion
7. TEST→PROD Promotion
8. PROD→DR Replication/Failover
9. 환경별 차이 Comparison Diagram
10. 운영→개발 반입/반출 제약

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.4.3 논리노드 구성도
**작성 목적:** 환경별 논리노드 배치, 스펙, 연결, HA/DR을 실제 구성도로 상세화한다.

**핵심 입력자료:** 목차 II.4.3, TA 구성도, 서버스펙, 네트워크/스토리지 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.4.3 논리노드 구성도

[작성 목적]
환경별 논리노드 배치, 스펙, 연결, HA/DR을 실제 구성도로 상세화한다.

[우선 확인할 입력]
목차 II.4.3, TA 구성도, 서버스펙, 네트워크/스토리지 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 4환경 통합 Logical Topology
2. DEV 전체 노드 구성
3. DEV ETL 상세
4. TEST 전체 노드 구성
5. PROD 전체 노드 구성
6. BI Portal HA 상세
7. 데이터흐름관리 HA 상세
8. 마케팅AP/Integration/DB 후보 배치
9. DR 미확정/목표 Topology
10. Application→Node 배치
11. Node→DB/Storage 연결
12. 정상 Runtime 경로
13. Node 장애/Failover
14. 스펙 확정 vs TBD Node

- 필수 슬롯 수: 14개. 실제 FIG 코드블록도 최소 14개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=14, 실제 필수 FIG>=14인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.4.4 적용 소프트웨어 식별
**작성 목적:** 장표에서 확인된 적용 SW와 TRM 후보를 분리하여 카테고리·제품·버전·용도·노드·도입구분을 정의한다.

**핵심 입력자료:** 목차 II.4.4, TRM, 솔루션/서버 인벤토리, 기술장표

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.4.4 적용 소프트웨어 식별

[작성 목적]
장표에서 확인된 적용 SW와 TRM 후보를 분리하여 카테고리·제품·버전·용도·노드·도입구분을 정의한다.

[우선 확인할 입력]
목차 II.4.4, TRM, 솔루션/서버 인벤토리, 기술장표

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Software Category Map
2. FACT SW vs Candidate SW Boundary
3. OS/Web/WAS Stack
4. Framework/Integration Stack
5. Batch/ETL Stack
6. Database/Data Platform Stack
7. CI/CD/Monitoring/Security Candidate Stack
8. SW→Node Mapping
9. SW Selection Decision/Gate

- 필수 슬롯 수: 9개. 실제 FIG 코드블록도 최소 9개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=9, 실제 필수 FIG>=9인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.4.5 소프트웨어 구성도
**작성 목적:** 논리노드에 실제 적용 소프트웨어 스택을 매핑하고 계층·의존·버전/미확정 상태를 시각화한다.

**핵심 입력자료:** 목차 II.4.5, 서버-솔루션 매핑, SW 인벤토리

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.4.5 소프트웨어 구성도

[작성 목적]
논리노드에 실제 적용 소프트웨어 스택을 매핑하고 계층·의존·버전/미확정 상태를 시각화한다.

[우선 확인할 입력]
목차 II.4.5, 서버-솔루션 매핑, SW 인벤토리

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Node×Software 전체 Stack
2. ETL Node Stack
3. 마케팅 AP Stack
4. Portal Stack
5. Flow Management Stack
6. Integration Stack
7. DB Client/Driver Connection Stack
8. Container Stack/TBD
9. FACT Mapping vs Blank/TBD Map

- 필수 슬롯 수: 9개. 실제 FIG 코드블록도 최소 9개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=9, 실제 필수 FIG>=9인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.4.6 데이터베이스 목록
**작성 목적:** 논리 DB/플랫폼, 엔진, 역할, 환경, 애플리케이션, 노드를 매핑한다.

**핵심 입력자료:** 목차 II.4.6, DA DB목록, 스키마/CDC 자료, 물리장표

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.4.6 데이터베이스 목록

[작성 목적]
논리 DB/플랫폼, 엔진, 역할, 환경, 애플리케이션, 노드를 매핑한다.

[우선 확인할 입력]
목차 II.4.6, DA DB목록, 스키마/CDC 자료, 물리장표

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Database Portfolio Big Picture
2. RTW/ADW/BSA/HDW/BDP 저장계층
3. Portal/Flow 운영DB
4. AS-IS DB vs TO-BE DB
5. Application→Database
6. Database→Logical Node
7. Source→CDC/ETL→Target DB
8. DB Ownership/Schema Boundary
9. SID/Schema/CDC GAP Map

- 필수 슬롯 수: 9개. 실제 FIG 코드블록도 최소 9개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=9, 실제 필수 FIG>=9인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## II.5 목표 IT시스템 구성도
**작성 목적:** II.1~II.4를 통합하여 5영역×Application×Node×SW×DB×환경의 목표 IT 구성을 완성한다.

**핵심 입력자료:** 목차 II.5, II.1~II.4 산출, 목표 IT 장표

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
II.5 목표 IT시스템 구성도

[작성 목적]
II.1~II.4를 통합하여 5영역×Application×Node×SW×DB×환경의 목표 IT 구성을 완성한다.

[우선 확인할 입력]
목차 II.5, II.1~II.4 산출, 목표 IT 장표

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 목표 IT 전체 통합 Big Picture
2. 정보전달 영역 기술상세
3. 수집·연계 영역 기술상세
4. 저장소 영역 기술상세
5. 분석·정보제공 영역 기술상세
6. 인프라 영역 기술상세
7. Application→Node→SW→DB
8. DEV Target IT
9. TEST Target IT
10. PROD Target IT
11. DR Target IT/TBD
12. 온라인 Runtime
13. 데이터 적재 Runtime
14. HA/DR/Governance Cross-cutting

- 필수 슬롯 수: 14개. 실제 FIG 코드블록도 최소 14개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=14, 실제 필수 FIG>=14인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III. 마케팅플랫폼 및 데이터허브 아키텍처
**작성 목적:** 온라인·배치·연계·행동데이터·컨테이너·보안·CI/CD를 하나의 실행 아키텍처로 연결한다.

**핵심 입력자료:** 목차 III, I·II 산출, 프레임워크/배치/DA/Hydra/2사업/보안/CM 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III. 마케팅플랫폼 및 데이터허브 아키텍처

[작성 목적]
온라인·배치·연계·행동데이터·컨테이너·보안·CI/CD를 하나의 실행 아키텍처로 연결한다.

[우선 확인할 입력]
목차 III, I·II 산출, 프레임워크/배치/DA/Hydra/2사업/보안/CM 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. III 전체 Runtime Big Picture
2. 온라인 vs 배치 vs 데이터유입 3축
3. 정보단말/계정단말 진입경로
4. Context→Layer→ServiceGroup→Execution Control
5. 거래패턴 Catalog Map
6. 표준전문/Character Set Boundary
7. Batch/Control-M Runtime
8. CDC/ETCL/BC Integration
9. Hydra 행동데이터
10. Container R/F Runtime
11. Security Cross-cutting
12. CI/CD Cross-cutting
13. 정상/장애/운영 Cross-cutting
14. III→IV Handoff

- 필수 슬롯 수: 14개. 실제 FIG 코드블록도 최소 14개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=14, 실제 필수 FIG>=14인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1 온라인어플리케이션
**작성 목적:** 정보단말/계정단말 진입부터 Context, 계층, 서비스그룹, 실행제어, 거래패턴, 표준전문까지 온라인 Runtime 전체를 완결한다.

**핵심 입력자료:** 목차 III.1.1~1.8, FW 자료, Runtime 회의록, 표준전문/CS 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1 온라인어플리케이션

[작성 목적]
정보단말/계정단말 진입부터 Context, 계층, 서비스그룹, 실행제어, 거래패턴, 표준전문까지 온라인 Runtime 전체를 완결한다.

[우선 확인할 입력]
목차 III.1.1~1.8, FW 자료, Runtime 회의록, 표준전문/CS 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 온라인 Runtime Level-0
2. 정보단말 직접 경로
3. 계정단말 EIC/MCA 경로
4. Service Context Lifecycle
5. Application Layer Map
6. Service Group Boundary
7. Execution Control Cross-cut
8. 거래패턴 전체 Map
9. JSON 표준전문 적용구간
10. Character Set 변환경계
11. 온라인 정상 Sequence
12. 온라인 예외/Timeout/중복 Sequence
13. 로그/관측/운영 Flow
14. III.1→III.2/III.4 Handoff

- 필수 슬롯 수: 14개. 실제 FIG 코드블록도 최소 14개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=14, 실제 필수 FIG>=14인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1.1 정보단말 활용
**작성 목적:** 정보단말의 AS-IS/TO-BE 경로, 엔진/EIC/EIMS 영향, UI 주의사항, 정보전달 패턴을 분리하여 정의한다.

**핵심 입력자료:** 목차 III.1.1, 단말/FW 자료, 엔진/EIC/EIMS 자료, UI가이드, Runtime 회의록

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1.1 정보단말 활용

[작성 목적]
정보단말의 AS-IS/TO-BE 경로, 엔진/EIC/EIMS 영향, UI 주의사항, 정보전달 패턴을 분리하여 정의한다.

[우선 확인할 입력]
목차 III.1.1, 단말/FW 자료, 엔진/EIC/EIMS 자료, UI가이드, Runtime 회의록

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 정보단말 전체 Context
2. 정보단말 AS-IS Runtime
3. 정보단말 TO-BE/TBD Runtime
4. 정보단말 vs 계정단말 비교
5. 엔진 업그레이드 영향 Map
6. EIC/EIMS 영향 Boundary
7. UI→서비스 정상 Sequence
8. UI 오류/CS/마스킹 영향
9. 정보전달 패턴 분류 Map
10. 확정/미확정 변경점

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1.2 서비스 Context 레이아웃 정의
**작성 목적:** Service Context의 생성·보강·전달·변경·폐기와 업무로그 연계를 필드 출처와 함께 정의한다.

**핵심 입력자료:** 목차 III.1.2, FW Context/업무로그 레이아웃, 전문 기준자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1.2 서비스 Context 레이아웃 정의

[작성 목적]
Service Context의 생성·보강·전달·변경·폐기와 업무로그 연계를 필드 출처와 함께 정의한다.

[우선 확인할 입력]
목차 III.1.2, FW Context/업무로그 레이아웃, 전문 기준자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Context Lifecycle Big Picture
2. Request→Context 생성
3. Context Field Provenance Map
4. Context→Application Layer 전달
5. Context→Service Group 전달
6. Context→업무로그 Correlation
7. 정상 Context Sequence
8. 오류/Timeout Context 처리
9. 민감정보/마스킹 영향
10. Context SSOT/GAP Map

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1.3 어플리케이션 계층 구조
**작성 목적:** 공식 Neoworks/은행 계층과 역할·호출방향·금지호출·트랜잭션/데이터 접근 경계를 정의한다.

**핵심 입력자료:** 목차 III.1.3, FW 계층자료, 코드정책, 비교자료는 별도 표시

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1.3 어플리케이션 계층 구조

[작성 목적]
공식 Neoworks/은행 계층과 역할·호출방향·금지호출·트랜잭션/데이터 접근 경계를 정의한다.

[우선 확인할 입력]
목차 III.1.3, FW 계층자료, 코드정책, 비교자료는 별도 표시

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Application Layer Big Picture
2. AA Map→Package→Service/Business Task
3. 공식 Layer Responsibility
4. 정상 Call Chain
5. 금지 Call Chain
6. Layer→DTO/Context 전달
7. Layer→Transaction Boundary
8. Layer→DAO/DB
9. 예외 처리/응답 흐름
10. FACT 공식계층 vs 비교 제안계층

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1.4 서비스그룹 간 호출 규칙
**작성 목적:** 서비스그룹 경계, 직접호출 가능여부, DTO 공유, Remote 호출, Timeout/TX/오류 전파 규칙을 결정한다.

**핵심 입력자료:** 목차 III.1.4, FW 호출규칙, 프로젝트 구조, Remote 규약

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1.4 서비스그룹 간 호출 규칙

[작성 목적]
서비스그룹 경계, 직접호출 가능여부, DTO 공유, Remote 호출, Timeout/TX/오류 전파 규칙을 결정한다.

[우선 확인할 입력]
목차 III.1.4, FW 호출규칙, 프로젝트 구조, Remote 규약

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Service Group Boundary Big Picture
2. 동일 그룹 내부 호출
3. 타 그룹 호출 Decision Tree
4. 직접 메소드 호출 허용/금지
5. 공통계약/API 호출
6. DTO 공유/복제/공통모듈
7. WAS Instance 간 Remote 호출
8. Remote 정상 Sequence
9. Remote 장애/Timeout Sequence
10. Transaction Boundary
11. Dependency/Cycle Detection
12. 예외승인/ADR Flow

- 필수 슬롯 수: 12개. 실제 FIG 코드블록도 최소 12개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=12, 실제 필수 FIG>=12인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1.5 어플리케이션 실행 제어
**작성 목적:** 서비스 선·후처리와 Timeout, 로그레벨, 중복거래, 예외/복구를 실제 Runtime 제어 흐름으로 정의한다.

**핵심 입력자료:** 목차 III.1.5, FW 실행제어 자료, 소스/설정, 거래로그/오류코드 정책

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1.5 어플리케이션 실행 제어

[작성 목적]
서비스 선·후처리와 Timeout, 로그레벨, 중복거래, 예외/복구를 실제 Runtime 제어 흐름으로 정의한다.

[우선 확인할 입력]
목차 III.1.5, FW 실행제어 자료, 소스/설정, 거래로그/오류코드 정책

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Execution Control Big Picture
2. Pre→Execute→Post Pipeline
3. 정상 거래 상세 Sequence
4. Timeout 감시 Lifecycle
5. Timeout 전파/차단 Sequence
6. 중복거래 State Machine
7. 중복거래 정상/충돌 Sequence
8. 로그레벨 동적 변경 Flow
9. Validation/Auth/Business Reject 분기
10. Retry 허용/금지 Decision Tree
11. Transaction Boundary
12. Exception→Response Mapping
13. Correlation/거래로그/관측
14. Recovery/Manual 처리

- 필수 슬롯 수: 14개. 실제 FIG 코드블록도 최소 14개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=14, 실제 필수 FIG>=14인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1.6 Lv3 어플리케이션 식별 기준
**작성 목적:** Lv2 아래 Lv3 식별 기준, 명명, 중복검증, Repository/배포단위, 변경Lifecycle을 정의한다.

**핵심 입력자료:** 목차 III.1.6, 공식 Lv3/명명 기준, 코드정책

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1.6 Lv3 어플리케이션 식별 기준

[작성 목적]
Lv2 아래 Lv3 식별 기준, 명명, 중복검증, Repository/배포단위, 변경Lifecycle을 정의한다.

[우선 확인할 입력]
목차 III.1.6, 공식 Lv3/명명 기준, 코드정책

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Lv1→Lv2→Lv3 Tree
2. Service Group→Lv3 분해기준
3. Naming Rule 구조
4. 업무기능→Lv3 ID Decision
5. 좋은/나쁜 식별 예
6. 중복/충돌 검증 Flow
7. Lv3→Repository/배포단위
8. 변경/폐기 Lifecycle

- 필수 슬롯 수: 8개. 실제 FIG 코드블록도 최소 8개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=8, 실제 필수 FIG>=8인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1.7 거래패턴
**작성 목적:** 목차의 8개 거래패턴을 각각 독립된 표준 패턴으로 그리고 공통 NFR/오류/재처리/보안 기준을 연결한다.

**핵심 입력자료:** 목차 III.1.7, 패턴 원본도식, Runtime/배치/IF 기준

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1.7 거래패턴

[작성 목적]
목차의 8개 거래패턴을 각각 독립된 표준 패턴으로 그리고 공통 NFR/오류/재처리/보안 기준을 연결한다.

[우선 확인할 입력]
목차 III.1.7, 패턴 원본도식, Runtime/배치/IF 기준

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 거래패턴 전체 Catalog Map
2. Pattern 선택 Decision Tree
3. 통상거래 독립 Sequence
4. 파일 Upload 독립 Flow
5. 파일 Download 독립 Flow
6. On-demand Batch 독립 Flow
7. 대용량조회 독립 Flow
8. 대량데이터 Online Sync 독립 Flow
9. 대량데이터 Async Batch 독립 Flow
10. 계정단말 Push 독립 Flow
11. 보고서 조회 독립 Flow
12. 내부시스템연계 독립 Flow
13. Pattern 공통 장애/Retry/Idempotency
14. Pattern→NFR/운영/보안 Matrix Diagram

- 필수 슬롯 수: 14개. 실제 FIG 코드블록도 최소 14개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=14, 실제 필수 FIG>=14인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.1.8 표준전문활용
**작성 목적:** JSON 표준전문의 실제 레이아웃, 적용/비적용 구간, 변환·Validation·Error·Version·Correlation을 정의한다.

**핵심 입력자료:** 목차 III.1.8, 표준전문 기준자료, Character Set 기준, FW 자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.1.8 표준전문활용

[작성 목적]
JSON 표준전문의 실제 레이아웃, 적용/비적용 구간, 변환·Validation·Error·Version·Correlation을 정의한다.

[우선 확인할 입력]
목차 III.1.8, 표준전문 기준자료, Character Set 기준, FW 자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 표준전문 적용 전체 Map
2. 실제 Envelope/Field 구조
3. Request 구조
4. Response 구조
5. Error 구조
6. 생성→전송→검증→처리 Flow
7. 적용/비적용 구간 Map
8. JSON/Character Set 변환경계
9. Validation/Error Sequence
10. Version Compatibility
11. Correlation/Logging
12. 비표준전문 예외승인

- 필수 슬롯 수: 12개. 실제 FIG 코드블록도 최소 12개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=12, 실제 필수 FIG>=12인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.2 배치어플리케이션
**작성 목적:** 배치 유형, Trigger, Scheduler, 실행노드, 데이터 적재, 재처리/운영을 온라인과 분리하여 정의한다.

**핵심 입력자료:** 목차 III.2, 배치 표준, Control-M 기준, ETCL/ETL 장표

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.2 배치어플리케이션

[작성 목적]
배치 유형, Trigger, Scheduler, 실행노드, 데이터 적재, 재처리/운영을 온라인과 분리하여 정의한다.

[우선 확인할 입력]
목차 III.2, 배치 표준, Control-M 기준, ETCL/ETL 장표

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Batch Architecture Big Picture
2. Trigger 유형 Map
3. DevOn Java Batch Runtime
4. ETCL Batch Runtime
5. Shell/SP Runtime/TBD
6. Control-M→Program Mapping
7. ETL #1/#2 실행경로
8. 정상 Job Chain
9. 실패/재시작/재처리
10. On-demand Batch 연계
11. Batch Observability
12. III.2→III.4 Data Handoff

- 필수 슬롯 수: 12개. 실제 FIG 코드블록도 최소 12개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=12, 실제 필수 FIG>=12인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.2.1 배치어플리케이션 유형
**작성 목적:** DevOn Java배치, ETCL, Shell/SP 유형별 사용조건·실행구조·입출력·운영책임을 정의한다.

**핵심 입력자료:** 목차 III.2.1, 배치 표준, ETCL 장표

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.2.1 배치어플리케이션 유형

[작성 목적]
DevOn Java배치, ETCL, Shell/SP 유형별 사용조건·실행구조·입출력·운영책임을 정의한다.

[우선 확인할 입력]
목차 III.2.1, 배치 표준, ETCL 장표

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 3종 Batch Type Map
2. DevOn Java Batch Flow
3. ETCL/TeraStream Flow
4. Shell Flow
5. Stored Procedure Flow
6. 유형 선택 Decision Tree
7. 유형별 입력/출력/저장
8. 유형별 오류/재처리
9. 유형별 Node Mapping
10. Batch Type→Control-M Handoff

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.2.2 스케줄러 연동 방안
**작성 목적:** Control-M Job과 배치 프로그램의 매핑, 선후행, 종료코드, 실패/재기동, 온디맨드 호출을 정의한다.

**핵심 입력자료:** 목차 III.2.2, Control-M 표준, 기작성 매핑자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.2.2 스케줄러 연동 방안

[작성 목적]
Control-M Job과 배치 프로그램의 매핑, 선후행, 종료코드, 실패/재기동, 온디맨드 호출을 정의한다.

[우선 확인할 입력]
목차 III.2.2, Control-M 표준, 기작성 매핑자료

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Control-M Integration Big Picture
2. Job→Program Mapping
3. 선행/후행 Job Chain
4. 정상 Scheduler Sequence
5. 실패/재시작 Sequence
6. 조건부/분기 Job Flow
7. On-demand Trigger Flow
8. Job→Node→Data Mapping
9. 종료코드/알림 Flow
10. 운영자 Manual Recovery

- 필수 슬롯 수: 10개. 실제 FIG 코드블록도 최소 10개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=10, 실제 필수 FIG>=10인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.3 어플리케이션 표준화 방안
**작성 목적:** 프로그램 명명, 패키지, 시스템코드/서비스그룹/Lv3, 형상경로, 검증·예외를 정의한다.

**핵심 입력자료:** 목차 III.3, 기작성 명명표준, 코드정책

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.3 어플리케이션 표준화 방안

[작성 목적]
프로그램 명명, 패키지, 시스템코드/서비스그룹/Lv3, 형상경로, 검증·예외를 정의한다.

[우선 확인할 입력]
목차 III.3, 기작성 명명표준, 코드정책

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Naming Standard Big Picture
2. SystemCode→ServiceGroup→Lv3→Program
3. Package Naming 구조
4. Program/Class/Job/SQL ID Mapping
5. Repository/형상 경로 Mapping
6. Naming Decision Tree
7. 중복/위반 Validation
8. 예외승인/변경 Lifecycle
9. FACT 기준 vs 외부 비교기준

- 필수 슬롯 수: 9개. 실제 FIG 코드블록도 최소 9개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=9, 실제 필수 FIG>=9인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.4 시스템 간 연계 방안
**작성 목적:** CDC, ETCL, BC와 온라인/파일 연계의 Source/Target/계약/오류/재처리/관측을 유형별로 분리 정의한다.

**핵심 입력자료:** 목차 III.4, DA 인터페이스 목록, CDC/ETCL/BC 기준, IF 원칙은 제안으로 분리

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.4 시스템 간 연계 방안

[작성 목적]
CDC, ETCL, BC와 온라인/파일 연계의 Source/Target/계약/오류/재처리/관측을 유형별로 분리 정의한다.

[우선 확인할 입력]
목차 III.4, DA 인터페이스 목록, CDC/ETCL/BC 기준, IF 원칙은 제안으로 분리

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Integration Landscape Big Picture
2. Interface Type Selection Decision Tree
3. CDC 독립 Flow
4. CDC 장애/재처리 Flow
5. ETCL 독립 Flow
6. ETCL 장애/재처리 Flow
7. BC 독립 Flow 또는 TBD Boundary
8. 온라인 Service/API/전문 Flow
9. File/MFT/SAM Flow
10. Source→Target Contract Map
11. Sync vs Async Decision
12. Timeout/Retry/DLQ/Manual Recovery
13. Idempotency/Duplicate Prevention
14. Correlation/Trace/Monitoring
15. Interface Contract/Version
16. Direct DB/P2P 예외승인

- 필수 슬롯 수: 16개. 실제 FIG 코드블록도 최소 16개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=16, 실제 필수 FIG>=16인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.5 고객 행동데이터 수집 및 활용 방안
**작성 목적:** 행동이벤트의 생성·수집·처리·저장·활용·보안·운영을 Hydra 역할과 로그 스키마에 근거해 정의한다.

**핵심 입력자료:** 목차 III.5, Hydra 프레임워크 자료, 로그 레이아웃, 데이터흐름

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.5 고객 행동데이터 수집 및 활용 방안

[작성 목적]
행동이벤트의 생성·수집·처리·저장·활용·보안·운영을 Hydra 역할과 로그 스키마에 근거해 정의한다.

[우선 확인할 입력]
목차 III.5, Hydra 프레임워크 자료, 로그 레이아웃, 데이터흐름

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. 행동데이터 E2E Big Picture
2. Channel/Event Source Map
3. Event Log Schema/Field Provenance
4. Hydra/HYDRA-K 역할 Boundary
5. 수집 정상 Sequence
6. 처리/변환 Pipeline
7. 저장 RTW/BDP 등 Target Mapping
8. 마케팅 활용 Flow
9. 오류/유실/재처리
10. PII/마스킹/암호 영향
11. 관측/품질/계보
12. Hydra 협업 GAP Map

- 필수 슬롯 수: 12개. 실제 FIG 코드블록도 최소 12개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=12, 실제 필수 FIG>=12인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.6 컨테이너 실행환경 구축 방안
**작성 목적:** R/F 거래 컨테이너 실행환경의 Runtime, Cluster, 배포, 네트워크, HA, 관측, 2사업 책임경계를 정의한다.

**핵심 입력자료:** 목차 III.6, 2사업 컨테이너 아키텍처, PMO Container Baseline, 서버매핑

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.6 컨테이너 실행환경 구축 방안

[작성 목적]
R/F 거래 컨테이너 실행환경의 Runtime, Cluster, 배포, 네트워크, HA, 관측, 2사업 책임경계를 정의한다.

[우선 확인할 입력]
목차 III.6, 2사업 컨테이너 아키텍처, PMO Container Baseline, 서버매핑

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. R/F Container Big Picture
2. R/F 요청→Container Runtime
3. Cluster/Namespace/Workload 구조
4. Container→Neoworks/DB/Integration 연결
5. Traffic/Ingress/Egress Boundary
6. Pod/Instance 장애/재기동
7. Scale/Resource Boundary
8. Config/Secret/Artifact Flow
9. Observability Flow
10. VM vs Container 비교
11. 2사업 R&R Boundary
12. 미확정 R/F/Cluster Gap Map

- 필수 슬롯 수: 12개. 실제 FIG 코드블록도 최소 12개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=12, 실제 필수 FIG>=12인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.7 데이터 암/복호화 처리 방안
**작성 목적:** 중요데이터를 저장·사용·표시·전송 상태별로 나누고 암복호·마스킹·키/비밀관리·로그보호를 보안팀 기준으로 정의한다.

**핵심 입력자료:** 목차 III.7, 은행 보안정책, 데이터분류, 암호/키관리/마스킹 기준

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.7 데이터 암/복호화 처리 방안

[작성 목적]
중요데이터를 저장·사용·표시·전송 상태별로 나누고 암복호·마스킹·키/비밀관리·로그보호를 보안팀 기준으로 정의한다.

[우선 확인할 입력]
목차 III.7, 은행 보안정책, 데이터분류, 암호/키관리/마스킹 기준

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. Data Protection Big Picture
2. 중요데이터 Classification Flow
3. At-Rest 암호 Flow
4. In-Use 복호/사용 Flow
5. UI Masking Flow
6. In-Transit 보호 Flow
7. Application vs DB vs Channel 책임 Boundary
8. Key/Secret Lifecycle
9. 로그/Trace 민감정보 보호
10. 암호 실패/키오류 처리
11. Character Set vs Encryption 순서
12. 보안 예외/승인 Flow
13. 보안팀 미확정 Gap Map

- 필수 슬롯 수: 13개. 실제 FIG 코드블록도 최소 13개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=13, 실제 필수 FIG>=13인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## III.8 CI/CD 지원 방안
**작성 목적:** VM과 Container를 분리하여 개발→테스트→이행/운영의 빌드·품질·아티팩트·배포·승인·롤백·관측 흐름을 정의한다.

**핵심 입력자료:** 목차 III.8, CI/CD 기준, TRM, 형상정책, 2사업 Container 파이프라인

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface Architect다.

[작성 대상]
III.8 CI/CD 지원 방안

[작성 목적]
VM과 Container를 분리하여 개발→테스트→이행/운영의 빌드·품질·아티팩트·배포·승인·롤백·관측 흐름을 정의한다.

[우선 확인할 입력]
목차 III.8, CI/CD 기준, TRM, 형상정책, 2사업 Container 파이프라인

[V5 실행 계약 — 반드시 준수]
- Evidence Register를 먼저 만들고, 소스가 지지하지 않는 상세는 일반론으로 채우지 마라.
- 첫 본문 산출은 Figure Plan이다. 아래 필수 Figure Slot을 모두 예약하고 같은 ID로 실제 독립 코드블록 그림을 출력하라.
- 표/문장/목록은 그림 수에 포함하지 않는다.
- L0→L1→L2→L3 Top-down을 유지하라. 동적 주제는 정상 Sequence와 실패/복구 Sequence를 반드시 분리한다.
- 복수 항목은 항목별 독립 그림을 만든다. 한 그림으로 묶어 숫자를 줄이지 마라.
- 자료가 없으면 그림을 생략하지 말고 [TBD]/[GAP]/[DEP] 박스로 미확정 경계를 보여라.
- [FACT]/[ANALYSIS]/[TO-BE/PROPOSED]/[GAP]/[TBD]를 명시하라.
- 하나은행 외부 참고자료는 비교/제안으로만 표시하고 하나은행 FACT로 쓰지 마라.
- `상세는 하위 절`, `4~12.`, `9~12.`, `상동`, `표 참조`로 필수 내용을 축약하지 마라.
- 각 그림 아래 목적·근거·Boundary·Trigger·처리순서·책임·데이터/제어·실패/운영/후속연결을 상세히 설명하라.
- 관계표를 만들면 대응 Text 그림을 추가하라.
- 마지막 V5 Completion Gate에서 계획 FIG 수와 실제 FIG 수를 세어 일치시키고, 누락 시 최종평가 전에 보완하라.

[필수 Figure Slots — 각각 독립 그림으로 출력]
1. CI/CD 전체 Big Picture
2. Source→Build→Test→Artifact Lifecycle
3. VM CI Pipeline
4. VM CD/이행 Pipeline
5. Container CI Pipeline
6. Container Image/Registry Flow
7. Container CD/GitOps Flow
8. DEV→TEST Promotion
9. TEST→PROD/이행 Promotion
10. Quality/Security Gate
11. Config/Secret Promotion
12. Rollback/Backout Flow
13. Deployment Observability
14. Approval/R&R Swimlane
15. VM vs Container Tool Boundary
16. CI/CD Tool FACT vs Candidate Map

- 필수 슬롯 수: 16개. 실제 FIG 코드블록도 최소 16개여야 한다.
- 필요하면 상세 그림을 추가할 수 있으나 필수 슬롯을 합치거나 삭제할 수 없다.

[Figure ID 규칙]
- `FIG-<절번호>-01`, `FIG-<절번호>-02` 형식으로 순차 ID를 부여한다.
- Figure Plan의 제목·ID와 실제 그림의 제목·ID를 1:1로 일치시킨다.
- 각 FIG에 Level(L0/L1/L2/L3), Evidence 상태, Source 근거를 함께 표기한다.

[설명 깊이]
- 핵심 결론은 단순 요약이 아니라 이 절에서 무엇을 확정하고 무엇을 남겨두는지 설명한다.
- 각 구성요소는 존재 이유, 입력, 출력, 책임한계, 의존성, 실패영향을 설명한다.
- Runtime은 ①~N 단계로 설명하고 그림 단계번호와 본문 단계번호를 맞춘다.
- AS-IS와 TO-BE가 동시에 있으면 별도 그림으로 분리하고 변화점/영향도를 연결한다.
- 데이터/전문/인터페이스가 있으면 Source, Target, Data, Direction, Sync/Async, Contract, Error/Recovery를 근거 범위에서 적는다.
- 보안/운영이 후속 절 범위이면 생략하지 말고 `현재 절 영향→후속 절→필요자료` 그림을 만든다.

[V5 강제 출력 구조]
0. Evidence Register
1. Figure Plan
2. 핵심 결론
3. 목적 / 범위 / 전제
4. L0 Big Picture
5. L1 영역/계층/서비스 View
6. L2 Component/Application/Node/SW/DB/Contract View
7. Static Mapping / Responsibility View
8. L3 정상 Runtime / Sequence / Data Flow
9. Failure / Exception / Retry / Recovery / HA-DR View
10. Security / Trust / Character Set / Data Protection 영향
11. Operation / Observability / Deployment / Environment 영향
12. 구성요소 책임표
13. Flow / Interface / Contract 정의표
14. 설계 규칙 / 금지 / 예외
15. Requirement/Policy/Principle→Decision→FIG Traceability
16. AS-IS vs TO-BE / 변경영향
17. 확정 / 협의필요 / GAP / TBD / ADR
18. 다음 절 Handoff Text 그림
19. 검증 체크리스트
20. V5 Completion Gate
21. 최종 평가

[Completion Gate 추가검사]
- Figure Plan 필수=16, 실제 필수 FIG>=16인지 숫자로 표시한다.
- 복수 유형/패턴/환경/메커니즘이 있다면 각각 독립 그림이 있는지 체크한다.
- 근거 없는 제품명/버전/수량/Timeout/포트/암호알고리즘/프로토콜을 새로 만들지 않았는지 체크한다.
- 협업 태그와 Owner 미확정이 그림/표에 남아 있는지 체크한다.
- 표가 그림을 대신한 곳이 없는지 체크한다.
- 누락이 있으면 PASS로 쓰지 말고 누락 그림/설명을 먼저 추가한다.

[입력자료]
- 요구사항/비기능요구:
- 현행/목표 구성도:
- 표준/정책/가이드:
- 소스/설정/인벤토리:
- 인터뷰/회의/ADR:
- 관련 업무/TA/DA/FW/보안/2사업 자료:
- 기타:
```

## IV. 기타 시스템 아키텍처
**작성 목적:** BI포탈, BIZ메타, Self BI, 데이터흐름관리, 비표준단말 SSO/EAM을 단순 나열하지 않고, **소비·메타·분석·인증 관점에서 I~III와 결합**하여 전체 정보계 아키텍처의 마지막 Top-down 계층으로 정리한다.

**핵심 입력자료:** 목차 IV, 업무담당파트 자료, BI포탈/OLAP/데이터흐름관리 물리 장표, II 시스템 구성, III 데이터허브/인터페이스, AS-IS SSO/EAM 담당자료

### 독립 실행 프롬프트

```text
너는 은행권 대형 프로젝트의 Enterprise/Application/Data/Technical/Interface/Security/Operation Architect다.

[작성 대상]
IV. 기타 시스템 아키텍처

[작성 목적]
BI포탈, BIZ메타, Self BI, 데이터흐름관리, 비표준단말 SSO/EAM을 I~III와 연결하여 사용자/데이터/메타/인증/운영 관점의 통합 아키텍처로 정의한다.

[V5 핵심 실행]
1) Evidence Register 작성 후 각 하위 시스템의 Evidence Maturity(M3/M2/M1)를 판정한다.
2) 시스템별로 동일한 깊이를 억지로 강제하지 말고, M3는 Runtime/Topology를 깊게, M1은 Discovery/Decision을 깊게 작성한다.
3) IV가 I~III와 어떻게 연결되는지 먼저 그린다.
4) 사용자→서비스→데이터, 데이터이동→흐름메타, 비표준단말→인증의 3개 축을 분리한다.
5) 시스템 간 역할 중첩(BIP/BIZ, BIZ/Q-Track/DAMS, SelfBI/OLAP)을 별도 그림으로 표시한다.
6) 장 마지막에 전체 I~IV Roll-up과 승인 차단 GAP 우선순위를 그린다.

[필수 Figure Slots]
01 IV L0 Enterprise Context
02 IV 역할 분류: Consumption / Metadata / Analysis / Authentication
03 User→BI Portal/Self BI Service Map
04 Data Hub→BI Portal/Self BI Data Access Map
05 ETCL/CDC/EAI/DAMS/Source→Flow Management Metadata Map
06 BizMeta↔Q-Track↔DAMS Responsibility Boundary
07 Standard vs Non-standard Terminal/Auth Boundary
08 System별 Evidence Maturity Map (M3/M2/M1)
09 AS-IS/TO-BE/Transition Summary
10 Cross-System Security/Authorization 영향
11 Cross-System Operation/HA/DR Summary
12 IV Dependency/Owner Map
13 IV GAP/Required-Evidence Map
14 IV ADR/Decision Priority Map
15 I→II→III→IV Full Architecture Roll-up
16 Baseline Approval Gate / Next Review Agenda

- 최소 16개 독립 Text 그림을 실제 출력한다.
- 하위 절 그림을 이 16개에 포함했다고 계산하지 않는다. 장 자체 그림이다.

[강제 출력 구조]
0. Evidence Register + 시스템별 Maturity
1. Figure Plan
2. 핵심 결론
3. IV가 전체 정의서에서 수행하는 역할
4. L0 Enterprise Context
5. 시스템 역할/책임 분해
6. 사용자/서비스 소비 View
7. 데이터/메타 View
8. 인증/보안 View
9. 물리/운영/HA 비교 View
10. AS-IS→Transition→TO-BE
11. 시스템 간 중첩/충돌/공유 자원
12. Owner/Dependency
13. GAP/Required Evidence
14. ADR/Decision Priority
15. I~IV 통합 Traceability
16. Approval Gate
17. 검증 체크리스트
18. V5 Completion Gate
19. 최종 평가

[절대 금지]
- 업무자료가 없는 Self BI/SSO를 일반적인 제품 아키텍처로 채우기
- Q-Track을 ETL 엔진으로 표현
- BI포탈과 BizMeta를 같은 제품이라는 이유만으로 같은 애플리케이션으로 단정
- NSIGHT의 MSTR/BI-Matrix/NH Cloud를 하나은행 FACT로 전환
```

---

## IV.1 BI포탈
**Evidence 기대:** M3 또는 M2. DataEye/JEUS/L4/AP/DB/HA/Storage 근거가 존재하므로 **물리·Runtime·장애·운영까지 깊게 작성**한다.

### 독립 실행 프롬프트

```text
[작성 대상]
IV.1 BI포탈

[목표]
BI포탈의 사용자, Portal Service, L4/AP/DB/Storage, 데이터소스, 정상조회, HA/Failover, 보안/SSO 영향, 운영/용량, AS-IS/TO-BE를 하나의 추적 가능한 아키텍처로 정의한다.

[먼저 판정]
- Evidence Maturity를 M3/M2/M1 중 판정하고 이유를 적는다.
- DataEye Portal, JEUS, L4, AP A-A, DB A-S, 공유 Storage 관련 FACT를 Evidence Register에 분리한다.
- 허브 조회경로, 기능목록, SSO, DR처럼 미확정인 것은 별도 GAP로 둔다.

[필수 Figure Slots]
01 BI Portal L0 Context
02 사용자/역할→Portal Service
03 Portal Logical Component View
04 L4→AP1/AP2→DB Topology
05 Shared Storage Role/Capacity Flow
06 Portal DB Active/Standby & Replication
07 Portal→RTW/ADW/BSA/Data Virtualization Access Options
08 정상 조회 Sequence
09 AP Failure / L4 Reroute Sequence
10 DB Failure / Standby Transition [절차 TBD 허용]
11 Data Source Failure/Query Failure 영향
12 Security/SSO/Masking Dependency
13 Operation/Monitoring/Capacity View
14 DEV/TEST/PROD/DR Evidence Map
15 AS-IS OLAP/Portal vs TO-BE Portal Position
16 BIP↔BIZ Shared Stack / Logical Boundary Gap
17 Confirmed vs TBD Architecture Overlay
18 Owner/Required Evidence/Approval Gate

[그림 상세 강제]
- FIG 04/06/08/09/10은 각각 별도 그림이다.
- Storage 2TB→5TB가 HA 목적이라고 추론하지 말고 데이터셋/파일 누적 근거와 HA를 분리한다.
- Portal이 RTW/ADW/BSA를 직접 JDBC하는지 Data Virtualization만 경유하는지 근거 없으면 FIG 07에서 Option A/B로 분리하고 ADR 후보로 연결한다.
- JEUS 버전이 다른 절에서만 확인되면 Portal 버전으로 복사하지 않는다.

[추가 산출]
- Portal Service→Data Source Matrix
- Component→Node→SW→DB Matrix
- HA State Table
- GAP Question Pack: 기능, 리포트, 권한, SSO, 데이터소스, DR, SLA
- ADR-BIP 후보와 영향 FIG

[완료 조건]
필수 FIG 18개 + 정상/장애/운영 + BIP/BIZ 경계 + Approval Gate가 모두 있어야 PASS.
```

---

## IV.2 BIZ메타
**Evidence 기대:** M2 또는 M1. 이름과 일부 연계 근거는 있지만 논리 Repository·업무 기능·승인 Workflow가 부족하므로 **Confirmed + Discovery + Responsibility Decision** 중심으로 작성한다.

### 독립 실행 프롬프트

```text
[작성 대상]
IV.2 BIZ메타

[목표]
비즈메타의 확정된 위치와 DAMS/BI Portal/Q-Track 관계를 먼저 고정하고, 메타 범위·Repository·등록/승인·검색·API·Owner가 부족한 부분을 구조적인 Discovery/Decision Architecture로 만든다.

[Evidence Maturity]
반드시 M2/M1 여부를 판정한다. 자료 부족 시 일반적인 Metadata Management 기능을 FACT로 채우지 않는다.

[필수 Figure Slots]
01 BIZMeta Confirmed Context
02 Confirmed Metadata Source/Consumer Map
03 BIZMeta↔BI Portal Physical/Logical Boundary
04 BIZMeta↔Q-Track↔DAMS Responsibility Map
05 Known Repository vs Unknown Repository Gap View
06 Metadata Lifecycle Skeleton: Create/Register→Review→Publish→Consume [단계 근거 없으면 모두 TBD]
07 Search/Consumption Skeleton
08 Metadata Type Unknown Map (어떤 분류가 필요한지, 값은 TBD)
09 Owner/RACI Dependency Map
10 Required Evidence Map
11 Interview Question Flow
12 Architecture Option A: Portal DB 공유 [PROPOSED]
13 Architecture Option B: 독립 Repository [PROPOSED]
14 Option Comparison / Decision Criteria
15 Security/Authorization/Change Governance Questions
16 AS-IS→Transition→TO-BE Skeleton
17 Approval Blocking GAP Map
18 BIZMeta Handoff to IV.4/DA

[Discovery Question Pack 필수]
- 관리 메타 유형은 무엇인가?
- 시스템 of record는 어디인가?
- 등록/승인/폐기 Owner는 누구인가?
- Portal DB 공유인지 별도 DB인지?
- Q-Track/DAMS와 중복되는 속성은 무엇이며 Master는 누구인가?
- API/Batch/파일 등 제공방식은 무엇인가?
- 권한/감사/변경이력 기준은 무엇인가?
각 질문에 `왜 필요 / 기대 Evidence / 답변 영향 FIG / ADR`를 붙인다.

[금지]
- 용어사전, 데이터카탈로그, Workflow 등 일반 기능을 근거 없이 FACT로 확정
- 별도 AP/DB를 장표 없이 추가

[완료 조건]
필수 FIG 18개 중 M1의 빈 구현 상세를 억지로 채우지 않고 Discovery/Option/Decision 그림으로 대체했는지 검증한다.
```

---

## IV.3 Self BI
**Evidence 기대:** M1 가능성이 높음. 목차와 `OLAP & Self BI` 행, 사용자분석환경 정도만 있으면 **Target 설계보다 Architecture Discovery와 의사결정 구조가 본체**다.

### 독립 실행 프롬프트

```text
[작성 대상]
IV.3 Self BI

[목표]
Self BI의 사용자·업무목적·데이터접근·도구·권한·부하·운영이 미확정일 때, 가짜 상세 아키텍처가 아니라 Target을 결정하기 위한 구조적 설계 프레임을 만든다.

[Evidence Maturity]
M1/M2 판정. 제품명, Workspace, Semantic Layer, Query Engine을 근거 없이 확정하지 않는다.

[필수 Figure Slots]
01 Self BI Confirmed Fact Boundary
02 Self BI Unknown/GAP Topology
03 User Persona / Use-case Unknown Map
04 Data Source Candidates: ADW/Virtualization/etc [근거별 상태]
05 BI Portal vs Self BI Responsibility Boundary
06 Existing OLAP(SAP BO) vs Self BI Relationship Gap
07 S10/User Analysis AP Evidence Gap
08 Data Access Option A: Virtualization [PROPOSED]
09 Data Access Option B: Direct governed source [PROPOSED]
10 Product/Runtime Decision Tree
11 Query/Resource Isolation Decision Tree
12 Security/Authorization Decision Tree
13 Download/Export/Data Protection Question Map
14 Required NFR/SLA Evidence Map
15 Owner/Dependency Map
16 Interview Question Flow
17 Architecture Options Comparison
18 AS-IS OLAP→Transition→Self BI Target Scenarios
19 Approval Blocking GAP / ADR Map
20 Target Architecture Skeleton with TBD slots

[Question Pack]
업무담당에게 최소 다음을 질문하고, 답이 어떤 FIG를 확정하는지 연결한다.
- Self BI의 사용자와 사용 시나리오?
- BI포탈과 다른 기능은 무엇인가?
- 현행 SAP BO를 유지/교체/병행하는가?
- 접근 가능한 데이터소스와 금지 소스?
- 조회/추출 데이터량과 동시사용자?
- 쿼리 제한/Timeout/자원격리 기준?
- 다운로드/반출/마스킹 기준?
- 개인 Workspace/공유 Workspace 존재 여부?
- 운영 Owner, 비용/라이선스, 장애 대응?

[금지]
- MSTR/BI-Matrix 등 타 프로젝트 제품을 하나은행 제품으로 사용
- 샌드박스/세맨틱 계층/워크스페이스를 근거 없이 사실화

[완료 조건]
20개 FIG. M1인 경우에도 20개 중 최소 10개는 Discovery/Decision/Gap 관점이어야 하며, 같은 TBD 그림 반복은 FAIL.
```

---

## IV.4 데이터흐름관리
**Evidence 기대:** M3. Q-Track, AP/DB, HA, 환경, 입력 메타, 활용 대상, 스펙 근거가 있으므로 **가장 깊은 Runtime/Metadata/HA/Operation 설계**를 작성한다.

### 독립 실행 프롬프트

```text
[작성 대상]
IV.4 데이터흐름관리

[목표]
실데이터 이동과 흐름 메타 수집을 명확히 분리하고, DAMS/Data Interface/Source Code/Bigdata Metadata→Q-Track→BI Portal/BizMeta/DAMS의 E2E와 물리 HA/환경/운영을 정의한다.

[Evidence Maturity]
M3 여부를 확인하고, M3면 물리·Runtime·장애·환경 그림을 생략하지 않는다.

[필수 Figure Slots]
01 Data Flow Management L0 Context
02 Real Data Flow vs Metadata Flow Separation
03 Metadata Source Catalog: DAMS / ETCL / CDC / EAI / Source / Bigdata
04 Metadata Collection Boundary
05 Create→Extract→Transform→Load Lineage Concept View
06 Q-Track Logical Component View
07 Q-Track AP/DB/Storage Physical Topology
08 Q-Track Software Stack Mapping
09 DEV Topology
10 TEST Topology
11 PROD Topology
12 DR Evidence Gap Topology
13 Metadata Normal Processing Sequence
14 ETCL Metadata Collection Sequence
15 CDC/EAI Metadata Collection Sequence
16 Source Code Metadata Collection Sequence
17 Q-Track→DAMS/BI Portal/BizMeta Consumption Flow
18 Collection Failure / Retry / Reconciliation [근거 없으면 TBD]
19 AP Active→Standby Failover
20 DB Active→Standby Failover
21 Monitoring / Batch / Capacity Operation View
22 Security/Metadata Sensitivity View
23 BIZMeta/Q-Track/DAMS Responsibility Overlap
24 AS-IS→TO-BE/Transition View
25 Owner/DA/TA Responsibility Map
26 GAP/ADR/Approval Gate

[그림 강제]
- `실데이터: 원천→CDC/ETCL→RTW/ADW`와 `관리정보: CDC/ETCL 메타→Q-Track`을 반드시 별도 그림으로 그린다.
- Q-Track을 ETL 엔진으로 표현하지 않는다.
- AP A-S와 DB A-S를 각각 상태전이 그림으로 만든다.
- Active-Active 미지원이라는 제품 제약이 근거에 있으면 Why/Impact를 설명한다.
- 개발/테스트 단독 구성과 운영 A-S를 한 표로만 끝내지 말고 각각 별도 Topology로 만든다.

[추가 표]
- Metadata Source→Collector→Repository→Consumer Matrix
- Environment/Node/SW/DB/HA Matrix
- Failure Mode→Impact→Recovery Evidence Matrix
- BIZMeta/Q-Track/DAMS 책임 경계표

[완료 조건]
필수 FIG 26개. Metadata E2E + 물리 + HA + 환경 + 운영 + 책임중첩까지 있어야 PASS.
```

---

## IV.5 비표준단말 SSO/EAM 연계 방안
**Evidence 기대:** M1. 목차가 `AS-IS 담당자 문의 필요`를 명시했으므로 **설계보다 AS-IS Discovery가 선행되는 절**이다.

### 독립 실행 프롬프트

```text
[작성 대상]
IV.5 비표준단말 SSO/EAM 연계 방안

[목표]
비표준단말의 AS-IS 인증/인가 경로를 확인하기 위한 Discovery Pack을 먼저 완성하고, 확인 결과를 토대로 TO-BE SSO/EAM Option과 Migration/Exception을 결정할 수 있는 설계 구조를 만든다.

[Evidence Maturity]
AS-IS 설계서/제품/프로토콜/세션 근거가 없으면 M1이다. JWT/OAuth/SAML 등 일반 기술을 자동 선택하지 않는다.

[필수 Figure Slots]
01 Standard vs Non-standard Terminal Confirmed Boundary
02 Current Known AS-IS Authentication Skeleton
03 AS-IS Unknown Components/Links Map
04 AS-IS Evidence Required Map
05 AS-IS Owner/Interview Map
06 AS-IS Login Sequence Skeleton
07 AS-IS Authorization/EAM Skeleton
08 AS-IS Session/Logout/Timeout Skeleton
09 Authentication Failure/Authorization Failure Question Map
10 Security Zone/Trust Boundary Questions
11 Non-standard Terminal Inventory Decision Tree
12 TO-BE Option A: Existing SSO/EAM extension [PROPOSED]
13 TO-BE Option B: Standard Terminal absorption [PROPOSED]
14 TO-BE Option C: Exception Adapter/Gateway [PROPOSED]
15 Option Comparison / Decision Criteria
16 Session/Token Technology Decision Gate
17 Authorization/RBAC/EAM Decision Gate
18 Failure/Bypass/Local Account Exception Gate
19 AS-IS→Transition→TO-BE Migration Scenarios
20 Cut-over/Rollback Evidence Requirements
21 Security Review Gate
22 Owner/RACI Map
23 ADR/GAP Priority Map
24 Final Target Skeleton with TBD slots

[AS-IS Interview Pack — 필수]
질문은 표로만 끝내지 말고 질문 흐름 그림도 작성한다.

필수 질문 축:
1. 비표준단말의 정확한 목록/Owner/사용자
2. 현재 로그인 시작점과 Credential 종류
3. SSO 제품/서버/연계방식
4. EAM 제품/권한판정 위치
5. 세션 생성/공유/만료/로그아웃
6. 단말→SSO→EAM→업무앱의 실제 호출 순서
7. 장애 시 우회/로컬계정 존재 여부
8. 중요정보/인증정보 저장 위치
9. 감사로그/접속로그/운영모니터링
10. DR/이중화/인증 장애 영향
11. 표준단말로 흡수 가능한 대상
12. 규정/보안 예외 승인 주체

각 질문은 `필요 이유 → 기대 Evidence → 답변에 따라 바뀌는 Target Option → ADR`로 연결한다.

[금지]
- JWT/OAuth/SAML/LDAP 등을 근거 없이 실제 구조에 삽입
- 인벤토리의 IM/Control Minder 행만 보고 SSO/EAM 제품이라고 확정
- 장애 시 인증 우회를 권장하거나 기본값으로 설정

[완료 조건]
필수 FIG 24개. 그중 AS-IS Discovery 관련 최소 10개, Target Option/Decision 관련 최소 8개가 있어야 PASS.
```

---

## IV 장 완료 후 — I~IV 통합 Architecture Closure

IV까지 작성한 뒤 문서를 바로 끝내지 말고 아래 통합 산출물을 추가한다.

```text
[1] I~IV One-Page Text Architecture
[2] User→Service→App→Node→Data→Metadata→Auth 추적 그림
[3] Online / Batch / Data Integration / BI / Metadata / Auth 6개 Runtime Lane
[4] AS-IS / Transition / TO-BE 전체 변화 지도
[5] Environment DEV/TEST/PROD/DR 전체 지도
[6] HA/DR Coverage Map
[7] Security / Trust / Sensitive Data Coverage Map
[8] Owner / Collaboration / Approval Map
[9] ADR Dependency Graph
[10] GAP Closure Priority / Required Evidence Roadmap
```

**최종 원칙:** 목차를 다 채운 것이 완료가 아니라, **근거·그림·결정·GAP·Owner가 서로 추적되고 다음 리뷰에서 무엇을 닫아야 하는지가 보일 때 완료**다.

---

# 5. V5 품질평가 기준

| 영역 | 배점 | 평가 기준 |
|---|---:|---|
| Source Grounding | 12 | FACT/분석/제안/TBD 분리, 외부자료 FACT 전환 없음 |
| Evidence Maturity 적응 | 10 | M3/M2/M1을 판정하고 등급에 맞는 그림 종류 사용 |
| Top-down 구조 | 8 | L0→L1→L2→L3 및 선행/후행 연결 |
| Figure Coverage | 15 | 필수 Slot 100%, 표가 그림을 대체하지 않음 |
| Figure Substance | 8 | 중복/작은 그림 남발 없이 각 FIG가 독립 질문에 답함 |
| Runtime/Failure/Recovery | 8 | 근거가 있는 동적 절은 정상·실패·복구 분리 |
| Static Mapping | 7 | App/Node/SW/DB/Data/Contract 추적 가능 |
| Discovery/Decision Quality | 8 | M1/M2에서 질문→Evidence→Option→Gate가 구조화됨 |
| GAP/TBD 시각화 | 6 | 빈칸이 아니라 경계·Owner·필요자료·영향으로 표현 |
| Security/Operation/HA-DR | 5 | 해당 절 근거 수준에 맞게 반영 |
| Traceability | 5 | Evidence→Claim→FIG→Decision→Verification 연결 |
| Review Readiness | 5 | 승인차단 GAP, Owner, 다음 검토 안건이 명확 |
| Completion Gate | 3 | 실제 FIG 수/누락/창작값/축약표현 수치 검증 |

**90점 미만이면 완료본이 아니다.**

추가 하드게이트:

- 창작값 1건 이상 → FAIL
- 필수 FIG 누락 → FAIL
- M1 절에서 일반 제품/프로토콜을 FACT로 확정 → FAIL
- M3 절에서 정상/장애/운영 그림 중 하나라도 누락 → FAIL
- `상세는 하위 절 참조`, `4~12.` 등 축약 → FAIL
- 중요 GAP에 Owner/필요 Evidence가 없음 → FAIL

---

# 6. V5 핵심 한 문장

> **근거가 충분하면 실제 구조·Runtime·장애·운영까지 깊게 그리고, 근거가 부족하면 억지 설계를 만들지 말고 Confirmed Fact→GAP→Question→Option→Decision Gate를 더 깊게 그린다. 모든 장은 L0→L3와 Evidence→Decision→Verification으로 연결한다.**
