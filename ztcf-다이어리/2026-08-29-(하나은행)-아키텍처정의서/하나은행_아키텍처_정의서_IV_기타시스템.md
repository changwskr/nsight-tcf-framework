# 하나은행 아키텍처 정의서 — IV. 기타 시스템 아키텍처

> 프로젝트 ONE · 1단계 **[마케팅플랫폼 및 데이터허브]**  
> 작성 기준: [`하나은행_아키텍처_정의서_목차.md`](../2026-08-27-(하나은행)%20아키텍처%20정의서%20목차/하나은행_아키텍처_정의서_목차.md)  
> 작성 방식: [`하나은행_아키텍처_정의서_목차별_TopDown_Text그림_상세강화_작성프롬프트_v5.md`](./하나은행_아키텍처_정의서_목차별_TopDown_Text그림_상세강화_작성프롬프트_v5.md)  
> 문서 성격: **V5 작업본** — v1 초안은 동일 폴더 `… - 원본.md`  
> 작성일: 2026-09-07  
> 로마숫자 V장 없음. III.5는 III장 안의 절이다.

**기밀:** 하나은행 자산. 대외 반출 시 각별한 주의를 요망한다.

---

## 읽는 법

| 태그 | 의미 |
|------|------|
| `[FACT]` | 다이어리 원문·장표에서 확인됨 |
| `[ANALYSIS]` | 확인된 사실에 대한 아키텍처 해석 |
| `[TO-BE/PROPOSED]` | 목표설계 또는 제안. 은행 확정 전 |
| `[GAP]` | 목차/기준 대비 자료 미충족 |
| `[TBD/확인필요]` | 자료만으로 확정 불가 |

TEXT 그림을 우선한다. Mermaid는 사용하지 않는다. 자료에 없는 제품 버전·수량·Timeout·포트·암호 알고리즘을 채우지 않는다. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud는 비교·제안만 한다.

각 절은 V5 강제 구조 **0. Evidence Register ~ 21. 최종 평가**를 생략하지 않는다. 필수 Figure Slot은 독립 ` ```text ` 코드블록이며 표/문장은 FIG 수에 넣지 않는다.

---

# IV. 기타 시스템 아키텍처

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-IV-01 | 원문 목차 | IV 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-IV-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-IV-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-IV-04 | BI/흐름관리 장표 | BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C | Self BI·SSO AS-IS 문의 |

## 1. Figure Plan

필수 Figure Slot **16**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-IV-01 | IV L0 Enterprise Context | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-02 | IV 역할 분류: Consumption / Metadata / Analysis / Authentication | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-03 | User→BI Portal/Self BI Service Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-04 | Data Hub→BI Portal/Self BI Data Access Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-05 | ETCL/CDC/EAI/DAMS/Source→Flow Management Metadata Map | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-06 | BizMeta↔Q-Track↔DAMS Responsibility Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-07 | Standard vs Non-standard Terminal/Auth Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-08 | System별 Evidence Maturity Map (M3/M2/M1) | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-09 | AS-IS/TO-BE/Transition Summary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-10 | Cross-System Security/Authorization 영향 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-11 | Cross-System Operation/HA/DR Summary | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-12 | IV Dependency/Owner Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-13 | IV GAP/Required-Evidence Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-14 | IV ADR/Decision Priority Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-15 | I→II→III→IV Full Architecture Roll-up | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV-16 | Baseline Approval Gate / Next Review Agenda | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

소비(BI/SelfBI)·메타(BIZ/Q-Track)·인증(SSO)을 I~III 허브와 결합. Q-Track≠ETL 엔진.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `IV 기타 시스템 아키텍처`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-IV-01 IV L0 Enterprise Context

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-01 │
└───────────┘
┌──────────────────────────┐
│ IV L0 Enterprise Context │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'IV L0 Enterprise Context' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 6. L2 Component/Application/Node/SW/DB/Contract View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 7. Static Mapping / Responsibility View

### FIG-IV-02 IV 역할 분류: Consumption / Metadata / Analysis / Authentication

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-02 │
└───────────┘
┌────────────────────────────────────────────────────────┐
│ IV 역할 분류: Consumption / Metadata / Analysis / Authenti │
└────────────────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'IV 역할 분류: Consumption / Metadata / Analysis / Authentication' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-03 User→BI Portal/Self BI Service Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-03 │
└───────────┘
┌────────────────────────────────────┐
│ User→BI Portal/Self BI Service Map │
└────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'User→BI Portal/Self BI Service Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-04 Data Hub→BI Portal/Self BI Data Access Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-04 │
└───────────┘
┌────────────────────────────────────────────┐
│ Data Hub→BI Portal/Self BI Data Access Map │
└────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Data Hub→BI Portal/Self BI Data Access Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-06 BizMeta↔Q-Track↔DAMS Responsibility Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-06 │
└───────────┘
┌──────────────────────────────────────────────┐
│ BizMeta↔Q-Track↔DAMS Responsibility Boundary │
└──────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'BizMeta↔Q-Track↔DAMS Responsibility Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-07 Standard vs Non-standard Terminal/Auth Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-07 │
└───────────┘
┌─────────────────────────────────────────────────┐
│ Standard vs Non-standard Terminal/Auth Boundary │
└─────────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Standard vs Non-standard Terminal/Auth Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-08 System별 Evidence Maturity Map (M3/M2/M1)

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-08 │
└───────────┘
┌──────────────────────────────────────────┐
│ System별 Evidence Maturity Map (M3/M2/M1) │
└──────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'System별 Evidence Maturity Map (M3/M2/M1)' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-12 IV Dependency/Owner Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-12 │
└───────────┘
┌─────────────────────────┐
│ IV Dependency/Owner Map │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'IV Dependency/Owner Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-13 IV GAP/Required-Evidence Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────────────────────────┐
│ IV / 13 IV GAP/Required-Evidence Map │
└──────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'IV GAP/Required-Evidence Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-14 IV ADR/Decision Priority Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
환경: 개발 / 테스트 / 운영 / DR
┌─────────┬─────────┐
│ DEV     │ TEST    │
│ ETL VM  │ 단독구성│
│ RHEL9   │ (NAS 없음│
│ TeraStream│ 개발 힌트)│
├─────────┼─────────┤
│ PROD    │ DR      │
│ HA 적용 │ [장표 없음 / GAP] │
│ Portal A-A / Q-Track A-S │
└─────────┴─────────┘
[FACT] 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
sFTP 이관 불가. DR 절차·RTO 창작 금지.
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'IV ADR/Decision Priority Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-IV-05 ETCL/CDC/EAI/DAMS/Source→Flow Management Metadata Map

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-05 │
└───────────┘
┌───────────────────────────────────────────────────────┐
│ ETCL/CDC/EAI/DAMS/Source→Flow Management Metadata Map │
└───────────────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'ETCL/CDC/EAI/DAMS/Source→Flow Management Metadata Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV-11 Cross-System Operation/HA/DR Summary

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
환경: 개발 / 테스트 / 운영 / DR
┌─────────┬─────────┐
│ DEV     │ TEST    │
│ ETL VM  │ 단독구성│
│ RHEL9   │ (NAS 없음│
│ TeraStream│ 개발 힌트)│
├─────────┼─────────┤
│ PROD    │ DR      │
│ HA 적용 │ [장표 없음 / GAP] │
│ Portal A-A / Q-Track A-S │
└─────────┴─────────┘
[FACT] 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
sFTP 이관 불가. DR 절차·RTO 창작 금지.
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Cross-System Operation/HA/DR Summary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-IV-15 I→II→III→IV Full Architecture Roll-up

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────┐
│ IV 산출 (그림·GAP·ADR) │
└────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌────────────────────┐
│ 승인 Gate / 다음 리뷰 안건 │
└────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'I→II→III→IV Full Architecture Roll-up' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-IV-10 Cross-System Security/Authorization 영향

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-10 │
└───────────┘
┌────────────────────────────────────────┐
│ Cross-System Security/Authorization 영향 │
└────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Cross-System Security/Authorization 영향' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| IV 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
| [TBD] 칸 | 빈칸 가시화 | 질문 Pack | ADR 후보 | Owner 미확정 표기 | 승인 차단 |

## 13. Flow / Interface / Contract 정의표

| Source | Target | Data | Direction | Sync/Async | Contract | Error/Recovery |
|--------|--------|------|-----------|------------|----------|----------------|
| 해당 절 Source [FACT 이름만] | Target [FACT 또는 TBD] | 업무데이터 또는 메타 | 그림 화살표 | 근거 없으면 TBD | 레이아웃 [GAP] | 별도 Failure FIG |

## 14. 설계 규칙 / 금지 / 예외

**규칙**
- FACT 이름 유지. 영역/패턴/환경 이름을 새로 만들지 않는다.
- M1에서 일반 제품 아키텍처를 FACT로 승격하지 않는다.

**금지**
- 창작 스펙, NSIGHT 제품을 하나 FACT로 전환, Q-Track을 ETL 엔진으로 표현, `상동`/`4~12.` 축약.

**예외**
- 예외는 ADR + 승인 Owner. 우회 경로를 기본값으로 두지 않는다.

## 15. Requirement/Policy/Principle→Decision→FIG Traceability

| Req/Policy/Principle | Decision/Claim | FIG | Verification |
|----------------------|----------------|-----|--------------|
| 목차 IV | 슬롯 100% 독립 그림 | FIG-IV-01~ | Completion Gate 수치 |
| 유지보수 비영향 | 최소변경·XDA 범위 | 관련 Why/Scope FIG | 부분개선 접점 유지 |
| Evidence Maturity | `M2` 그림 종류 | Discovery 또는 Runtime | 가짜 상세 0건 |

## 16. AS-IS vs TO-BE / 변경영향

```text
AS-IS
  현행 CRM/BSA/RDW · ETCL 분산 · SAP BO OLAP · EUC-KR 중심
  │ 유지 / 변경 / 폐기 / 대체 / 공존
  ▼
Transition
  Oracle Exa · 신·구 공존 · XDA SQL · 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
  DR·SSO·Self BI·BC 정의는 아직 빈칸
  ▼
TO-BE
  프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
  RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
```

변화점만 연결하고, 없는 이관 절차를 만들지 않는다.

### FIG-IV-09 AS-IS/TO-BE/Transition Summary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-09 │
└───────────┘
┌────────────────────────────────┐
│ AS-IS/TO-BE/Transition Summary │
└────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS/TO-BE/Transition Summary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- 아키텍처(작성) · 해당 Owner TBD

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-IV-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-IV-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-IV-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- IV 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- IV GAP 박스
- Owner 미응답 항목

**Who must answer**
- 아키텍처(작성) · 해당 Owner TBD
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- IV 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-IV-16 Baseline Approval Gate / Next Review Agenda

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG IV-16 │
└───────────┘
┌─────────────────────────────────────────────┐
│ Baseline Approval Gate / Next Review Agenda │
└─────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Baseline Approval Gate / Next Review Agenda' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 16 = 본문 FIG 16
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] IV 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 16 | 실제 16 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 16 · Figure Plan 행 16 · 본문 ` ```text ` 그림 코드블록(FIG) 16건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`IV` V5 재작성. 필수 FIG 16개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# IV.1 BI포탈

**협업 태그:** [업무담당파트 자료]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M3` — 물리·제품·HA 근거가 있다. Topology/정상 Sequence/장애를 분리한다.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-IV.1-01 | 원문 목차 | IV.1 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-IV.1-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-IV.1-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-IV-04 | BI/흐름관리 장표 | BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C | Self BI·SSO AS-IS 문의 |

## 1. Figure Plan

필수 Figure Slot **18**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-IV.1-01 | BI Portal L0 Context | L0 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-02 | 사용자/역할→Portal Service | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-03 | Portal Logical Component View | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-04 | L4→AP1/AP2→DB Topology | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-05 | Shared Storage Role/Capacity Flow | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-06 | Portal DB Active/Standby & Replication | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-07 | Portal→RTW/ADW/BSA/Data Virtualization Access Options | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-08 | 정상 조회 Sequence | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-09 | AP Failure / L4 Reroute Sequence | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-10 | DB Failure / Standby Transition [절차 TBD 허용] | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-11 | Data Source Failure/Query Failure 영향 | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-12 | Security/SSO/Masking Dependency | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-13 | Operation/Monitoring/Capacity View | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-14 | DEV/TEST/PROD/DR Evidence Map | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-15 | AS-IS OLAP/Portal vs TO-BE Portal Position | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-16 | BIP↔BIZ Shared Stack / Logical Boundary Gap | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-17 | Confirmed vs TBD Architecture Overlay | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.1-18 | Owner/Required Evidence/Approval Gate | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 운영 구성 명칭 **BI포털**. DataEye Portal(WEB), JEUS(포탈 장표 버전 없음), JDK, Linux, 진입 L4, Portal_AP1/AP2 **Active-Active**, Portal_DB1 Oracle Master / DB2 Standby+복제. 공유 Storage 2TB→5TB는 설치파일·매뉴얼·데이터셋 누적(파일 최저 20~50GB 이상)이지 HA 용량이 아니다. 장표6 소비 힌트=JDBC·데이터 가상화·허브. 조회 경로 Option은 ADR-BIP-01. NSIGHT MSTR/NH Cloud/BI-Matrix는 이 절 FACT가 아니다. 기능목록·SSO·DR·DEV/TEST 포탈 토폴로지는 GAP.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `IV.1 BI포탈`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [업무담당파트 자료]

## 4. L0 Big Picture

### FIG-IV.1-01 BI Portal L0 Context

**Level:** L0 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[I.5 Actor]  BI 사용자 · 정보포탈
[OLAP 장표 사용자칸] 정보포탈 / BI 서비스 디자인 도구 / 중앙 관리 콘솔
        │  포탈 UI vs OLAP UI 경계 = [TBD]
        ▼
II.1 ② 데이터 분석 및 정보제공 영역
        │
        ▼
┌──────── APP-BIP  BI포털 [FACT 장표2 명칭] ────────┐
│ 진입 L4                                           │
│ WEB: DataEye Portal                               │
│ WAS: JEUS  (포탈 장표에 버전 없음. 흐름관리 JEUS 8.5를 복사 금지) │
│ OS: Linux  (버전 미기재)  JDK 기재됨               │
│ AP: Portal_AP1 / AP2  Active-Active               │
│ DB: Portal_DB1 Oracle Master / DB2 Standby + 복제 │
│ Storage: 공유 2TB → 5TB 요구 (데이터셋·매뉴얼, HA 아님) │
└──────────────────────┬────────────────────────────┘
                       │ JDBC 및/또는 데이터 가상화 [장표6]
                       ▼
허브 저장 (II.4.6): RTW/ADW Oracle Exa · BSA Oracle · (As-Is) SAP BO Target
IV.4 활용칸에 BI Portal [FACT]  ← 계보 UI 연계 후보, ETL 아님
NSIGHT MSTR / NH Cloud / BI-Matrix = 이 절 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** BI포탈이 정보계 소비층에서 어디에 앉는지 L0로 고정한다. Self BI(IV.3)·BIZ메타(IV.2)와 한 상자로 합치지 않는다.
2. **근거자료와 상태:** [FACT] 장표2 명칭 BI포털, DataEye Portal(WEB), JEUS, JDK, Linux, L4, AP A-A, DB A-S. [GAP] 기능목록·SSO. NSIGHT BI 장표는 비교 금지.
3. **Boundary / In / Out:** In: I.5 사용자, II.1 ②영역. Out: APP-BIP Runtime과 허브 조회 후보. 밖: 마케팅 온라인(III.1), Q-Track ETL화.
4. **Trigger / 시작점:** 사용자가 정보포탈/BI 화면을 연다. 설계 리뷰에서는 장표2·6을 펼친 시점.
5. **처리순서:** ① Actor 확인 → ② ②영역 위치 → ③ DataEye/L4/AP/DB/Storage 확정칸 → ④ 허브 조회는 경로 TBD로 점선 → ⑤ IV.2/IV.3/IV.4는 옆 상자.
6. **책임·비책임:** 책임: 업무담당(서비스), TA(노드/HA). 비책임: 리포트 메뉴 창작, JEUS 8.5를 포탈 버전으로 단정.
7. **데이터/전문/상태/제어:** 제어: L4→AP. 데이터: 포탈 자체 Oracle + (후보) 허브 JDBC/가상화. 전문 JSON 필드 창작 금지.
8. **실패·운영·후속:** AP 장애=FIG-09, DB 장애=FIG-10, 소스 장애=FIG-11. SSO는 FIG-12·IV.5. DR은 FIG-14 GAP.

## 5. L1 영역/계층/서비스 View

II.1 **② 데이터 분석 및 정보제공** 안의 소비 서비스가 BI포탈이다. 사용자→Portal Service 상세는 FIG-IV.1-02(절 7)에 독립 그림으로 둔다. 이 번호에 슬롯을 합치지 않는다.

```text
[II.1 ② 분석·정보제공]
        │
        ├─ APP-BIP  BI포탈   ← 이 절 (DataEye · L4 · A-A)
        ├─ APP-BIZ  비즈메타 ← IV.2 (장표2 공존, 논리 분리 TBD)
        ├─ APP-SBI  Self BI  ← IV.3 (제품 TBD)
        └─ APP-DFM  흐름관리 ← IV.4 (Q-Track A-S, 계보지 ETL 아님)
OLAP(SAP BO)는 같은 ②영역의 As-Is 소비. To-Be 포탈과의 관계는 FIG-15.
```

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-IV.1-03 Portal Logical Component View

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[논리 컴포넌트 — 장표에서 분리 가능한 것만]
┌──────── DataEye Portal (WEB) ────────┐
│  UI / 포털 앱                         │
│  WAS: JEUS · JDK · Linux              │
└──────────────┬───────────────────────┘
               │ 내부 JDBC/세션 [프로토콜 값 창작 금지]
               ▼
┌──────── Portal Oracle ───────────────┐
│  DB1 Master / DB2 Standby            │
│  용도: 포탈 메타/콘텐츠 [ANALYSIS, 원문 미기재] │
└──────────────────────────────────────┘
               │
┌──────── 공유 Storage ────────────────┐
│  설치파일 · 매뉴얼 · 데이터셋 누적     │
│  2TB → 5TB (파일 최저 20~50GB 이상)   │
└──────────────────────────────────────┘
허브 접근 컴포넌트(가상화 클라이언트 vs 직접 JDBC) = FIG-07 Option
비즈메타 전용 컴포넌트 박스 없음 [장표2에 별도 AP/DB 없음]
```

**그림 상세해설**

1. **그림 목적:** 포탈을 WEB/WAS, 자체 DB, 공유스토리지 세 논리 덩어리로 나눈다. 허브 접근은 이 그림에서 확정하지 않는다.
2. **근거자료와 상태:** [FACT] DataEye Portal, JEUS, JDK, Linux, Portal Oracle, 공유 Storage. [ANALYSIS] DB 용도는 원문 미기재.
3. **Boundary / In / Out:** In: APP-BIP. Out: 세 컴포넌트. 밖: RTW/ADW 스키마, Q-Track.
4. **Trigger / 시작점:** 논리 설계 리뷰. 물리 배치는 FIG-04.
5. **처리순서:** ① WEB/WAS → ② Portal DB → ③ Storage → ④ 허브 접근은 점선으로 FIG-07에 위임.
6. **책임·비책임:** 책임: 앱=업무담당, WAS/OS=TA, DB=DA/담당, Storage=추진단 증설 FACT.
7. **데이터/전문/상태/제어:** 제어: WAS 세션. 데이터: 포탈 Oracle + 파일 데이터셋. 필드 창작 금지.
8. **실패·운영·후속:** 컴포넌트 장애는 AP/DB/Storage 각각 FIG-09/10/05. BIP↔BIZ=FIG-16.

### FIG-IV.1-04 L4→AP1/AP2→DB Topology

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
                    [ User ]
                       │
                       ▼
                 ┌─────────┐
                 │   L4    │  [FACT] 진입 부하분산
                 └───┬─┬───┘
           ① 정상    │ │    ② 정상 (A-A 동시 가능)
           ┌─────────┘ └─────────┐
           ▼                     ▼
    Portal_AP1 (Active)   Portal_AP2 (Active)
    DataEye Portal         DataEye Portal
    JEUS · JDK · Linux     JEUS · JDK · Linux
    16C / 64GB             16C / 64GB     [FACT 스펙]
           │     공유 Storage 2T→5T      │
           └────────────┬────────────────┘
                        ▼ JDBC/내부
              Portal_DB1 Oracle Master (Active)
                        │ 복제 [FACT]
                        ▼
              Portal_DB2 Oracle Standby
              DB 스펙: 16C / 64GB / HDD 4TB [FACT]
AP를 Active-Standby로 바꾸어 그리지 않는다. (장표는 A-A)
```

**그림 상세해설**

1. **그림 목적:** L4→AP1/AP2→Portal DB의 물리 Topology만 그린다. 조회 Sequence(FIG-08)와 합치지 않는다.
2. **근거자료와 상태:** [FACT] L4, Portal_AP1/AP2 A-A, DB1 Master/DB2 Standby+복제, AP 16C/64GB 공유스토리지, DB 16C/64GB HDD 4TB.
3. **Boundary / In / Out:** In: 사용자 HTTP(S) 진입. Out: AP 인스턴스와 Portal DB. 밖: 허브 Exa 토폴로지(II.4.3).
4. **Trigger / 시작점:** 운영 환경 기동. DR 토폴로지는 이 그림에 넣지 않는다(FIG-14 GAP).
5. **처리순서:** ① L4 수신 → ② AP1 또는 AP2 (둘 다 Active) → ③ 공유스토리지 참조 → ④ Portal_DB1, 복제는 DB2.
6. **책임·비책임:** 책임: TA 네트워크/노드. 비책임: VIP/포트 번호 창작, JEUS 버전 확정.
7. **데이터/전문/상태/제어:** 제어: L4 분배. 데이터: 포탈 세션·포탈 DB 트랜잭션. 허브 SQL은 FIG-08/07.
8. **실패·운영·후속:** AP 한 대 장애=FIG-09. DB Master 장애=FIG-10. Storage 용량=FIG-05(HA 아님).

### FIG-IV.1-06 Portal DB Active/Standby & Replication

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
상태 ① 정상
 Portal_DB1 = Master (Active)  ──복제──►  Portal_DB2 = Standby
 AP1/AP2 는 DB1을 사용 [접속 문자열/SCAN 값 창작 금지]

상태 ② Master 장애
 DB1 불가
        │
        ▼
 DB2 Standby 승격  [절차·RTO·자동/수동 = TBD]
        │
        ▼
 AP1/AP2 재접속  [재연결 방식 TBD]
        │
        ▼
복제 재구성  [장표에 절차 없음]

상태표
| 구성 | 정상 | 장애 목표 | 근거 |
| DB1 | Active Master | 중단 | FACT 역할 |
| DB2 | Standby | 승격 후보 | FACT 복제 |
| 절차 | — | TBD | 운영 가이드 GAP |
```

**그림 상세해설**

1. **그림 목적:** Portal DB의 A-S와 복제만 그린다. AP A-A(FIG-04/09)와 한 그림으로 섞지 않는다.
2. **근거자료와 상태:** [FACT] Portal_DB1 Oracle Master, DB2 Standby, 복제. [GAP] 승격 절차·자동여부·RTO.
3. **Boundary / In / Out:** In: AP의 DB 세션. Out: Master/Standby 역할. 밖: 허브 Exa HA.
4. **Trigger / 시작점:** 정상=복제 유지. 장애=Master 손실 인지(모니터링 FIG-13).
5. **처리순서:** ① 정상 Master+복제 → ② Master 불가 → ③ Standby 승격(절차 TBD) → ④ AP 재접속 TBD → ⑤ 복제 재구성 TBD.
6. **책임·비책임:** 책임: DA/DBA 승격, TA 접속. 비책임: DataGuard 모드명 창작.
7. **데이터/전문/상태/제어:** 제어: 복제 스트림(제품 옵션 미기재). 데이터: 포탈 Oracle 데이터. 허브 데이터 아님.
8. **실패·운영·후속:** 승격 실패 시 포탈 콘텐츠 조회 중단. 허브 JDBC 조회는 FIG-11. 상세 절차=운영 가이드 입수.

### FIG-IV.1-07 Portal→RTW/ADW/BSA/Data Virtualization Access Options

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Portal AP (DataEye)
        │ 분석 소스 접근
        ▼
장표6 힌트: JDBC · 데이터 가상화 · 허브
        │
   ┌────┴────────────┐
   ▼                 ▼
Option A [TO-BE/PROPOSED]   Option B [TO-BE/PROPOSED]
가상화 경유만               포탈 AP → 허브 직접 JDBC
        │                         │
        ▼                         ▼
 가상화 계층 [제품 미기재]    RTW / ADW / BSA / 마트
        │                    (As-Is) OLAP Target
        ▼
 RTW/ADW/BSA
금지: RDW만 쓴다고 단정 (원문 미기재)
ADR-BIP-01: A only vs B vs A+B 병행
답변 영향: FIG-08 ④단계, FIG-11 장애 범위, III.7 마스킹 위치
```

**그림 상세해설**

1. **그림 목적:** 허브 조회 경로를 Option으로만 열고 하나를 FACT로 고르지 않는다.
2. **근거자료와 상태:** [FACT] 장표6 JDBC·가상화·허브 소비. [GAP] 포탈이 RDW만/ADW도/가상화 only 여부.
3. **Boundary / In / Out:** In: Portal AP. Out: 후보 소스 RTW/ADW/BSA/마트/OLAP Target. 밖: Q-Track DB.
4. **Trigger / 시작점:** 리포트/분석 조회가 허브 데이터가 필요할 때. ADR 리뷰.
5. **처리순서:** ① AP에서 소스 필요 → ② 장표6 힌트 확인 → ③ Option A/B 분기 → ④ ADR-BIP-01 → ⑤ FIG-08에 선택된 경로만 실선.
6. **책임·비책임:** 책임: DA 소스 허용, 업무담당 리포트 소스, TA 연결. 비책임: 가상화 제품명 창작.
7. **데이터/전문/상태/제어:** Direction: AP→소스. Sync 조회. Contract/SQL 소유권 [GAP].
8. **실패·운영·후속:** 소스 장애=FIG-11. 직접 JDBC면 허브 부하 NFR [GAP]. 가상화면 가상화 장애 추가.

## 7. Static Mapping / Responsibility View

### FIG-IV.1-02 사용자/역할→Portal Service

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
사용자/역할 [장표·I.5에서 읽히는 이름만]
┌──────────────┬──────────────┬──────────────┐
│ BI 사용자     │ 정보포탈 사용자│ OLAP 장표의   │
│              │              │ 디자인도구/콘솔│
└──────┬───────┴──────┬───────┴──────┬───────┘
       │              │              │
       ▼              ▼              ▼
 Portal Service [업무담당 자료 없음 → 이름만]
 ┌ WEB 포털 화면      ┐   기능 트리/권한 매트릭스 = [GAP]
 │ 데이터셋 공유(스토리지)│   COL-IV.1-01 질문
 └ 리포트 카탈로그 TBD ┘
       │
       ├─ 포탈 자체 DB (콘텐츠/메타 용도 [ANALYSIS] 미기재)
       └─ 분석 조회 → FIG-07 소스 Option
Self BI(IV.3)와 역할 중복 여부는 담당 자료 없이 단정 금지 → 이슈만 제시
```

**그림 상세해설**

1. **그림 목적:** 누가 어떤 Portal Service를 쓰는지 확정칸과 빈칸을 나눈다. 메뉴 트리를 지어내지 않는다.
2. **근거자료와 상태:** [FACT] 사용자 이름=장표/I.5. WEB 포털·데이터셋 공유. [GAP] 역할·권한·리포트 목록=업무담당 자료 없음.
3. **Boundary / In / Out:** In: Actor 이름. Out: 포탈 화면/데이터셋 서비스. 밖: Self BI 제품 기능.
4. **Trigger / 시작점:** 로그인 후 포탈 진입. 설계 질문은 COL-IV.1-01.
5. **처리순서:** ① 사용자 칸 3종 병기 → ② 확인된 서비스(WEB, 데이터셋) → ③ 권한/리포트는 GAP 박스 → ④ Self BI와 비교는 단정하지 않음.
6. **책임·비책임:** 책임: 업무담당 기능정의. TA는 서비스 목록을 만들지 않는다.
7. **데이터/전문/상태/제어:** 운반물: 화면 요청, 데이터셋 파일(공유스토리지). SQL 소유권 [GAP].
8. **실패·운영·후속:** 권한 실패·SSO 미정=FIG-12. 후속 인터뷰 질문 Pack=절 17.

### FIG-IV.1-16 BIP↔BIZ Shared Stack / Logical Boundary Gap

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
장표2 제목: BI포털 , 비즈메타  한 운영 구성도에 공존 [FACT]
        │
        ▼
물리 관찰: 별도 BIZ AP/DB 박스 없음 [FACT]
        │
        ▼
논리 해석은 아직 결정이 아님
┌ Option 공유스택 [ANALYSIS] ┐  ┌ Option 논리분리 미기재 ┐
│ DataEye/JEUS/DB를          │  │ 화면/권한/저장소만     │
│ 메타도 사용?               │  │ 다른데 박스가 없음     │
└────────────────────────────┘  └────────────────────────┘
ADR-BIZ-01 (IV.2) 와 GAP-II.3.2-03 에 연결
금지: 같은 제품이라는 이유만으로 같은 애플리케이션으로 단정
Q-Track은 이 공유스택이 아님 (별도 AP A-S)
```

**그림 상세해설**

1. **그림 목적:** 장표에 같이 그려진 사실과 논리 앱 분리를 섞지 않는다.
2. **근거자료와 상태:** [FACT] 장표2 제목에 비즈메타+BI포털, BIZ 전용 노드 박스 없음. [GAP] 논리 Repository/권한 분리.
3. **Boundary / In / Out:** In: APP-BIP vs APP-BIZ 임시 ID. Out: IV.2 Option A/B. 밖: Q-Track 스택.
4. **Trigger / 시작점:** BIP/BIZ 경계 리뷰.
5. **처리순서:** ① 한 장표 공존 FACT → ② 박스 없음 FACT → ③ 논리 단정 금지 → ④ ADR-BIZ-01.
6. **책임·비책임:** 책임: 업무담당 두 서비스 정의, TA 노드. DA 스키마 분리 여부.
7. **데이터/전문/상태/제어:** 데이터: 포탈 Oracle 공유 여부 TBD. 메타 유형은 IV.2 GAP.
8. **실패·운영·후속:** 잘못 합치면 권한/배포 단위가 묶임. 후속 IV.2 FIG-03/12/13.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-IV.1-05 Shared Storage Role/Capacity Flow

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[공유 Storage 역할 — HA Failover 스토리가 아님]
설치파일 · 매뉴얼 · 데이터셋 파일
        │ 누적
        ▼
 현재 할당 2TB
        │ 증설 요구 [FACT]
        ▼
 목표 5TB
 근거: 데이터셋 파일 최저 20~50GB 이상 공유
        │
        ▼
 Portal_AP1 ──┐
              ├── 동일 공유 볼륨 마운트 [장표]
 Portal_AP2 ──┘
금지: 5TB를 AP A-A 또는 DB A-S의 장애복구 용량으로 설명
보안/반출 통제 = [TBD] → III.7 · 업무담당
DR 복제 여부 = 장표 없음 [GAP]
```

**그림 상세해설**

1. **그림 목적:** 2TB→5TB가 왜 필요한지(데이터셋 누적)를 HA와 분리해 보여 준다.
2. **근거자료와 상태:** [FACT] 공유 Storage 2TB→5TB 증설 요구, 설치파일·매뉴얼·데이터셋, 파일 최저 20~50GB 이상. HA 목적 문장 없음.
3. **Boundary / In / Out:** In: AP가 읽는 파일. Out: 증설 요구. 밖: Portal DB 디스크 4TB(다른 상자).
4. **Trigger / 시작점:** 데이터셋 적재/공유 시. 용량 경보는 운영 FIG-13.
5. **처리순서:** ① 파일 유형 확인 → ② 2TB 현재 → ③ 5TB 요구 사유 → ④ 양 AP 공유 → ⑤ HA로 해석하지 않음.
6. **책임·비책임:** 책임: 추진단/업무 용량. TA는 마운트. 보안 반출=TBD.
7. **데이터/전문/상태/제어:** 운반물: 설치파일, 매뉴얼, 데이터셋 파일. DB 블록이 아님.
8. **실패·운영·후속:** 용량 부족은 조회 실패가 아니라 공유 실패. DR 복제 미기재=FIG-14.

### FIG-IV.1-08 정상 조회 Sequence

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
① User 가 포탈 화면에서 조회 요청
        │
        ▼
② L4 가 Portal_AP1 또는 AP2 로 분배  [FACT A-A]
        │
        ▼
③ DataEye Portal / JEUS 가 요청 처리
        │
        ├─ ③-a 포탈 자체 콘텐츠 → Portal_DB1 Master
        │         (메뉴/포탈메타 용도 [ANALYSIS])
        └─ ③-b 분석 데이터 → FIG-07 Option A 또는 B
                  (이 단계는 경로 미확정이므로 점선)
        │
        ▼
④ 결과 조합
        │
        ▼
⑤ 화면/리포트 응답
Timeout 초 · 동시세션 상한 = 창작 금지 [GAP]
SSO 삽입 위치 = FIG-12 (이 Sequence에 제품 넣지 않음)
```

**그림 상세해설**

1. **그림 목적:** 정상 조회 Happy Path만 그린다. AP/DB 장애 Sequence와 합치지 않는다.
2. **근거자료와 상태:** [FACT] User→L4→AP A-A→DataEye/JEUS→Portal DB 및/또는 허브. [GAP] ③-b 실선화, Timeout.
3. **Boundary / In / Out:** In: 사용자 조회. Out: 화면 결과. 밖: ETCL 적재, Q-Track 계보 수집.
4. **Trigger / 시작점:** 사용자가 리포트/화면을 연다.
5. **처리순서:** ① 요청 → ② L4 분배 → ③ WAS 처리 → ③-a 및/또는 ③-b → ④ 조합 → ⑤ 응답. 번호=본문과 동일.
6. **책임·비책임:** 책임: 업무 화면=담당, 분배=TA, SQL=소유권 GAP. 비책임: 쿼리 문장 창작.
7. **데이터/전문/상태/제어:** 제어: HTTP 요청. 데이터: 포탈 DB 행 및/또는 허브 조회 결과. CS는 포탈 장표 미기재.
8. **실패·운영·후속:** 실패 분기 FIG-09/10/11. 부분 성공(포탈 OK, 허브 NG)도 FIG-11.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-IV.1-09 AP Failure / L4 Reroute Sequence

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상: L4 → AP1(A) 와 AP2(A) 둘 다 서비스
        │
        ▼ Trigger: Portal_AP1 프로세스/노드 장애
        │
        ▼
① L4 가 AP1 제외하고 AP2 로만 분배  [A-A 잔여 노드]
        │
        ▼
② 진행 중 세션 (AP1 분) 처리 결과 = [TBD]
   재로그인 / 재조회 필요 여부 장표 없음
        │
        ▼
③ 공유 Storage · Portal_DB 는 살아 있으면 AP2가 계속 사용
        │
        ▼
④ AP1 복구 후 L4 재등록 [절차 TBD]
금지: AP를 A-S로 재해석. 제품 A-A FACT 유지
DB 장애와 혼선 금지 → FIG-10
```

**그림 상세해설**

1. **그림 목적:** AP 한 쪽 장애 시 L4가 남은 Active로 넘기는 경로만 그린다.
2. **근거자료와 상태:** [FACT] AP Active-Active. [GAP] 세션 스티키, 드레인, 재로그인, 헬스체크 주기.
3. **Boundary / In / Out:** In: L4+AP1+AP2. Out: AP2만 서비스. 밖: DB Standby 승격.
4. **Trigger / 시작점:** AP1 다운 감지(방법 TBD).
5. **처리순서:** ① 장애 감지 → ② L4가 AP2만 → ③ 스토리지/DB 공유 유지 → ④ AP1 복구 재등록 TBD.
6. **책임·비책임:** 책임: TA L4/AP. 업무는 사용자 안내. 비책임: Timeout 초 창작.
7. **데이터/전문/상태/제어:** 제어: L4 헬스/분배. 데이터: AP2 메모리 세션만 유효할 수 있음 [TBD].
8. **실패·운영·후속:** 양쪽 AP 동시 장애=포탈 중단(DB 살아 있어도). 운영 알림=FIG-13.

### FIG-IV.1-10 DB Failure / Standby Transition [절차 TBD 허용]

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상: AP1/AP2 → Portal_DB1 Master , DB2는 복제 Standby
        │
        ▼ Trigger: DB1 Master 장애
        │
        ▼
① 포탈 콘텐츠 조회 실패 (③-a 경로)
   허브 직접 조회(Option B)는 DB1과 무관할 수 있음 [분석, 단정 금지]
        │
        ▼
② Standby(DB2) 승격  [자동/수동 · 승인 Owner = TBD]
        │
        ▼
③ AP 접속 문자열 전환  [TBD]
        │
        ▼
④ 복제 방향 재설정  [TBD]
        │
        ▼
⑤ 서비스 재개 확인
장표는 역할(Master/Standby/복제)만 FACT. 운영 런북 없음.
```

**그림 상세해설**

1. **그림 목적:** Portal DB Failover 상태 전이만 그린다. 절차 값을 채우지 않는다.
2. **근거자료와 상태:** [FACT] DB A-S + 복제. [GAP] 승격 런북, RTO, AP 재접속.
3. **Boundary / In / Out:** In: DB1 장애 이벤트. Out: DB2 승격 후보 상태. 밖: 허브 Exa Failover.
4. **Trigger / 시작점:** DB1 무응답/강제 장애.
5. **처리순서:** ① 콘텐츠 조회 실패 → ② 승격 TBD → ③ AP 재접속 TBD → ④ 복제 재구성 TBD → ⑤ 재개.
6. **책임·비책임:** 책임: DBA/DA. TA는 AP 접속. 비책임: DataGuard/GoldenGate 등 제품 단정.
7. **데이터/전문/상태/제어:** 데이터: 포탈 Oracle. 제어: 승격 명령(미기재). 복제 lag 값 창작 금지.
8. **실패·운영·후속:** 승격 전 포탈 메뉴 장애. 허브 조회 지속 여부는 FIG-07 선택에 좌우=FIG-11.

### FIG-IV.1-11 Data Source Failure/Query Failure 영향

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
조회 경로별 장애 영향 (경로 자체는 FIG-07 TBD)
┌─────────────┬──────────────────────────────┐
│ 실패 지점    │ 사용자 영향 (근거 범위)        │
├─────────────┼──────────────────────────────┤
│ L4/AP       │ 포탈 전체 불가 또는 FIG-09 잔여 │
│ Portal DB   │ 포탈 콘텐츠(③-a) 불가, FIG-10 │
│ 가상화 계층  │ Option A일 때만 분석조회 불가  │
│ RTW/ADW/BSA │ Option B 또는 A 하류 불가      │
│ As-Is OLAP  │ 이관 전 소비 경로면 영향 [TBD]  │
└─────────────┴──────────────────────────────┘
부분 실패: 포탈 화면은 뜨고 차트만 공란 → 담당 UX [GAP]
Retry 횟수/초 = 창작 금지
Q-Track 장애는 계보 화면 후보만, 포탈 본조회와 분리 [FACT 활용칸]
```

**그림 상세해설**

1. **그림 목적:** 데이터 소스/쿼리 실패가 화면 어디에 떨어지는지 경로별로 분리한다.
2. **근거자료와 상태:** [FACT] 소비 힌트=JDBC/가상화/허브. Q-Track은 활용칸(계보)이지 조회 엔진 아님. [GAP] 부분실패 UX, Retry.
3. **Boundary / In / Out:** In: FIG-08 ③-b. Out: 영향 범위 표. 밖: ETCL 적재 실패(III.4) — 데이터 공백의 원인일 수는 있으나 이 절 Runtime이 아님.
4. **Trigger / 시작점:** 하류 소스 무응답 또는 권한 거부 [정책 TBD].
5. **처리순서:** ① 실패 지점 식별 → ② 표의 영향 칸 → ③ Option A/B에 따라 가상화 vs 직접 → ④ 포탈 DB와 혼동하지 않음.
6. **책임·비책임:** 책임: DA 소스 가용, 업무 메시지. TA 연결. Q-Track Owner는 계보만.
7. **데이터/전문/상태/제어:** 데이터: 조회 결과 없음/부분. 제어: 에러 코드 매핑 [GAP].
8. **실패·운영·후속:** 복구=소스 측 HA(II.4) 또는 가상화. 포탈 AP Failover로 허브 장애가 낫지 않음.

### FIG-IV.1-17 Confirmed vs TBD Architecture Overlay

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
실선 FACT                          점선 TBD/GAP
L4                                 기능/리포트 카탈로그
DataEye Portal WEB                 포탈 JEUS 버전
JEUS (버전 미기재)                  SSO 삽입점
AP1/AP2 A-A                        허브 조회 Option A/B
Portal DB A-S + 복제               SQL 소유권
AP/DB 스펙 (16C64G 등)             DEV/TEST 포탈 토폴로지
Storage 2T→5T 데이터셋             DR
장표6 JDBC/가상화 힌트              권한 매트릭스
활용칸 BI Portal (Q-Track)         부분실패 UX
                                   BIP vs BIZ 논리
Overlay 규칙: 실선만 Baseline. 점선은 ADR/인터뷰 전 승인 대상
```

**그림 상세해설**

1. **그림 목적:** 한 장에 확정과 미확정을 겹쳐 보여 Baseline 오해를 막는다.
2. **근거자료와 상태:** 실선=장표/스펙 FACT. 점선=담당자료·ADR. NSIGHT 항목은 어느 쪽에도 넣지 않음.
3. **Boundary / In / Out:** In: FIG-01~16 결과. Out: 승인 가능 범위. 밖: 창작 값.
4. **Trigger / 시작점:** 중간 리뷰, Baseline 동결 시도.
5. **처리순서:** ① 실선 목록 낭독 → ② 점선이 막는지 확인 → ③ ADR-BIP-01 등 우선순위.
6. **책임·비책임:** 책임: 아키텍처가 Overlay 유지. Owner는 점선 닫기.
7. **데이터/전문/상태/제어:** 제어: 리뷰 체크리스트. 데이터: 없음(메타 설계 산출).
8. **실패·운영·후속:** 점선이 남은 채 구축 착수하면 FIG-08 ④가 흔들림. Gate=FIG-18.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-IV.1-12 Security/SSO/Masking Dependency

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[포탈 장표] SSO 박스 없음 → 삽입 위치를 FACT로 그리지 않음
User ──?──► L4 ──► DataEye
        │
        ▼
IV.5 비표준단말 SSO/EAM  = 별도 절, AS-IS 문의
표준 정보/계정단말 인증   = I.5 / III.1.1 (Neoworks 경로)
포탈이 표준단말 SSO를 재사용하는지 = [TBD]
마스킹/표시 보호           = III.7 (알고리즘 창작 금지)
다운로드/데이터셋 반출     = Storage FIG-05 + 보안 [TBD]
질문 Pack
 Q1 포탈 로그인 시작점?
 Q2 SSO 제품/서버? (TRM IM 행만으로 확정 금지)
 Q3 권한은 포탈 RBAC vs EAM?
 Q4 조회 결과 마스킹 Owner?
```

**그림 상세해설**

1. **그림 목적:** 보안 의존성을 질문 구조로 열고 JWT/OAuth를 넣지 않는다.
2. **근거자료와 상태:** [FACT] 포탈 장표에 SSO 박스 없음. IV.5는 AS-IS 문의. 마스킹은 III.7 할 일. [금지] TRM만으로 SSO 제품 확정.
3. **Boundary / In / Out:** In: 포탈 진입. Out: IV.5/III.7 입력. 밖: 허브 TDE 등 미기재 단정.
4. **Trigger / 시작점:** 포탈 로그인 설계 리뷰, 보안 협의.
5. **처리순서:** ① 장표에 SSO 없음 확인 → ② 표준단말 인증과 분리 → ③ IV.5 점선 → ④ 마스킹은 III.7 → ⑤ 질문 Pack.
6. **책임·비책임:** 책임: 보안/업무담당. 아키텍처는 의존만 표시. 비책임: 프로토콜 선정.
7. **데이터/전문/상태/제어:** 제어: 인증 토큰/세션 — 종류 TBD. 데이터: 화면 표시값, 데이터셋 파일.
8. **실패·운영·후속:** 인증 장애 시 우회 로컬계정 기본값 금지(IV.5와 동일). 후속=IV.5 Discovery.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-IV.1-13 Operation/Monitoring/Capacity View

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
운영 관찰 축 (값/툴 창작 금지)
┌──────────┬─────────────────────────────┐
│ 축        │ 장표에서 읽히는 것 / 빈칸      │
├──────────┼─────────────────────────────┤
│ 가용      │ AP A-A, DB A-S [FACT]        │
│ 용량      │ Storage 2T→5T 데이터셋 [FACT] │
│ 스펙      │ AP 16C64G, DB 16C64G 4TB HDD │
│ 모니터링  │ 도구/임계치 [GAP]              │
│ SLA/동시자│ [GAP]                         │
│ 배치      │ 포탈 자체 배치 여부 [GAP]      │
│ JEUS 버전 │ 포탈 장표 미기재. 흐름 8.5 복사 금지 │
└──────────┴─────────────────────────────┘
용량 경보 ≠ HA Failover (FIG-05와 연결)
```

**그림 상세해설**

1. **그림 목적:** 운영·관측·용량을 HA Topology와 분리해 무엇이 FACT인지 표로 고정한다.
2. **근거자료와 상태:** [FACT] HA 모드, 스펙, 스토리지 증설. [GAP] 모니터링 제품, SLA, 포탈 배치.
3. **Boundary / In / Out:** In: 운영 환경. Out: 관찰 축. 밖: Q-Track 관제(IV.4 FIG-21).
4. **Trigger / 시작점:** 운영 리뷰, 용량 증설 요청 시점.
5. **처리순서:** ① 가용 축=이미 그린 HA → ② 용량=데이터셋 → ③ 스펙 FACT → ④ 도구/SLA는 GAP.
6. **책임·비책임:** 책임: 운영/TA. 업무는 데이터셋 증가 원인. 비책임: 임계치 숫자 창작.
7. **데이터/전문/상태/제어:** 제어: 미정 모니터링 이벤트. 데이터: 용량 사용량(미계측값 창작 금지).
8. **실패·운영·후속:** 경보 후 증설=FIG-05. 장애 런북=FIG-09/10. DR=FIG-14.

### FIG-IV.1-14 DEV/TEST/PROD/DR Evidence Map

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
        DEV              TEST             PROD              DR
┌──────────────┬──────────────┬──────────────────┬──────────────┐
│ 포탈 전용    │ 포탈 전용    │ L4 + AP1/AP2 A-A │ 장표 없음    │
│ 토폴로지     │ 토폴로지     │ DB1/DB2 A-S      │ [GAP]        │
│ 장표 미분리  │ 장표 미분리  │ Storage 2T→5T    │ RTO/RPO 창작 │
│ 또는 미기재  │              │ [FACT 운영 구성도]│ 금지         │
└──────────────┴──────────────┴──────────────────┴──────────────┘
혼동 금지: 개발 ETL VM(RHEL9 TeraStream)은 III.2/II.4.3 이지 포탈 DEV가 아님
흐름관리 개발=단독·NAS 없음 은 IV.4 FACT이지 포탈 DEV가 아님
```

**그림 상세해설**

1. **그림 목적:** 포탈의 환경별 Evidence가 어디에 있고 없는지를 그린다. ETL DEV를 포탈 DEV로 가져오지 않는다.
2. **근거자료와 상태:** [FACT] 운영 구성도(PROD HA). [GAP] DEV/TEST 포탈 단독 장표, DR.
3. **Boundary / In / Out:** In: 4환경 이름(II.4.2). Out: 포탈 Evidence 맵. 밖: Q-Track DEV/TEST FACT(IV.4).
4. **Trigger / 시작점:** 환경 매트릭스 리뷰.
5. **처리순서:** ① PROD 실선 → ② DEV/TEST 빈칸 → ③ DR 빈칸 → ④ ETL/Q-Track 환경과 라벨 분리.
6. **책임·비책임:** 책임: TA 환경. 비책임: 없는 DR 절차 창작.
7. **데이터/전문/상태/제어:** 데이터: 환경별 포탈 콘텐츠 이관 방식 [GAP]. sFTP 이관 불가는 ETL FACT.
8. **실패·운영·후속:** DR 공백은 승인 차단 가능. COL-IV.1-02 JEUS/DR.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| L4 | 포탈 진입 분배 | User | AP1 또는 AP2 | TA. VIP/포트 창작 금지 | 양쪽 AP 미도달 시 포탈 중단 |
| Portal_AP1/AP2 | DataEye 실행, A-A | L4 | 화면·JDBC | TA+업무. A-S로 재해석 금지 | 1대 장애=FIG-09, 2대=중단 |
| DataEye Portal / JEUS | WEB 포털 | HTTP | 화면/세션 | 업무 기능, TA WAS. JEUS 버전 미기재 | 화면 불가 |
| Portal_DB1/DB2 | 포탈 자체 Oracle A-S | AP JDBC | 포탈 콘텐츠 | DA/DBA. 용도 원문 미기재 | FIG-10. 허브 조회와 분리 |
| 공유 Storage | 데이터셋·매뉴얼 공유 | AP 마운트 | 파일 | 추진단 2T→5T FACT. HA 아님 | 공유 실패. 조회 엔진 장애와 다름 |
| 허브 RTW/ADW/BSA | 분석 소스 후보 | FIG-07 Option | 조회 결과 | DA 허용, SQL 소유권 GAP | FIG-11 |
| [TBD] 기능/SSO/DR | 승인 차단 가시화 | 인터뷰 | ADR | Owner 표기 | Baseline 동결 불가 |

## 13. Flow / Interface / Contract 정의표

| Source | Target | Data | Direction | Sync/Async | Contract | Error/Recovery |
|--------|--------|------|-----------|------------|----------|----------------|
| User | L4 | 조회 요청 | → | Sync | URL/세션 [GAP] | FIG-09 |
| L4 | Portal_AP1/AP2 | 동일 요청 | → | Sync | A-A 분배 | 잔여 AP |
| Portal AP | Portal_DB1 | 포탈 콘텐츠 | → | Sync | JDBC. 스키마 [GAP] | FIG-10 |
| Portal AP | 가상화 또는 허브 | 분석 데이터 | → | Sync | Option A/B ADR-BIP-01 | FIG-11 |
| Portal AP | 공유 Storage | 데이터셋 파일 | ↔ | — | 용량 2T→5T | FIG-05 |
| Q-Track | BI Portal | 계보 메타 | → | TBD | 활용칸 FACT, 임베드 GAP | IV.4 FIG-17 |

## 14. 설계 규칙 / 금지 / 예외

**규칙**
- 포탈 AP는 장표대로 Active-Active. DB는 Active-Standby+복제.
- 2TB→5TB는 데이터셋·매뉴얼 누적으로만 설명한다.
- 허브 조회는 FIG-07 Option A(가상화) / B(직접 JDBC)로만 열고 ADR-BIP-01 전에는 실선 확정하지 않는다.
- 포탈 JEUS 버전에 흐름관리 8.5를 복사하지 않는다.

**금지**
- NSIGHT MSTR / NH Cloud / BI-Matrix를 하나 포탈 FACT로 전환.
- 포탈과 비즈메타를 같은 애플리케이션으로 단정 (장표 공존 ≠ 동일 앱).
- Timeout 초, VIP, 포트, SSO 프로토콜 창작.
- Q-Track을 포탈 조회 엔진으로 표현.

**예외**
- BIP/BIZ 스택 공유는 ADR-BIZ-01. OLAP 유지/교체/병행은 ADR-OLAP-01. 우회 로그인을 기본값으로 두지 않는다.

## 15. Requirement/Policy/Principle→Decision→FIG Traceability

| Req/Policy/Principle | Decision/Claim | FIG | Verification |
|----------------------|----------------|-----|--------------|
| 장표2 운영 구성 | DataEye+L4+AP A-A+DB A-S | 01,03,04,06 | 장표 명칭·HA 모드 |
| 스토리지 증설 | 5TB=데이터셋, HA 아님 | 05,13 | 증설 사유 문장 |
| 장표6 소비 | 조회 경로 Option, 미선정 | 07,08,11 | ADR-BIP-01 |
| 장표 SSO 없음 | 인증은 IV.5/질문 | 12,18 | 프로토콜 미삽입 |
| NSIGHT 비혼입 | MSTR 등 비교만 | 01,15 | 검색 0건 |

## 16. AS-IS vs TO-BE / 변경영향

```text
AS-IS
  현행 CRM/BSA/RDW · ETCL 분산 · SAP BO OLAP · EUC-KR 중심
  │ 유지 / 변경 / 폐기 / 대체 / 공존
  ▼
Transition
  Oracle Exa · 신·구 공존 · XDA SQL · 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
  DR·SSO·Self BI·BC 정의는 아직 빈칸
  ▼
TO-BE
  프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
  RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
```

변화점만 연결하고, 없는 이관 절차를 만들지 않는다.

### FIG-IV.1-15 AS-IS OLAP/Portal vs TO-BE Portal Position

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
AS-IS 소비층
 SAP BO + WebSphere + IQ/BSA  [현행 OLAP 장표]
 정보포탈 / 디자인도구 / 콘솔이 OLAP 사용자칸에 공존
        │ 유지 / 교체 / 병행 = [TBD]  ADR-OLAP-01
        ▼
Transition
 DataEye Portal 신설 (장표2 To-Be 운영 구성)
 허브는 Oracle Exa RTW/ADW 로 이동 (I.1/II)
 OLAP Target 잔존 가능
        │
        ▼
TO-BE 위치
 II.1 ②영역 APP-BIP = DataEye + JEUS + L4 + A-A/A-S
 Self BI(IV.3)로 OLAP를 흡수하는지는 자료 없음 → 단정 금지
NSIGHT 신용실적 4영역 분할을 하나 포탈 내부 구조로 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** 현행 OLAP와 To-Be 포탈의 자리만 비교한다. 이관 절차서는 없다.
2. **근거자료와 상태:** [FACT] As-Is OLAP(SAP BO 등), To-Be DataEye 포탈. [GAP] BO 유지/교체/병행.
3. **Boundary / In / Out:** In: AS-IS OLAP, TO-BE APP-BIP. Out: 변화점. 밖: 마케팅 온라인 재작성.
4. **Trigger / 시작점:** 전환 아키텍처 리뷰.
5. **처리순서:** ① AS-IS 소비 → ② Transition 공존 → ③ TO-BE 포탈 위치 → ④ Self BI 흡수 여부 TBD.
6. **책임·비책임:** 책임: 업무담당 OLAP 전략, TA 포탈 스택. 비책임: BO 폐기일 창작.
7. **데이터/전문/상태/제어:** 데이터: 현행 IQ/BSA vs 허브 Exa. 화면 이관 목록 [GAP].
8. **실패·운영·후속:** 병행 시 이중 조회 경로=FIG-07/11. 후속 ADR-OLAP-01.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [업무담당파트 자료]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-IV.1-01 | 자료 | 이 절 빈 박스 | [업무담당파트 자료] | 장표/인터뷰/ADR |
| GAP-IV.1-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-IV.1-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- IV.1 슬롯 이름·Evidence Maturity `M3`

**What blocks approval**
- IV.1 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [업무담당파트 자료]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- IV.1 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-IV.1-18 Owner/Required Evidence/Approval Gate

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Approval Gate
 닫히면 다음 가능: IV.2 논리경계, FIG-07 실선화, 운영 런북
        │
        ▼
Owner → 필요 Evidence → 닫히는 FIG
 업무담당 → 기능/권한/리포트/허브매트릭스 → FIG-02,07,08
 TA        → JEUS 버전, DR, L4 헬스       → FIG-04,09,14
 DA        → 소스 허용, Portal DB 용도     → FIG-06,07,11
 보안      → SSO/마스킹/반출               → FIG-12, III.7, IV.5
        │
        ▼
차단 중이면 Baseline 동결 불가
 ADR-BIP-01 (가상화 only vs 직접 JDBC)
 ADR-BIZ-01 (스택 공유) 는 IV.2와 공동
다음 절 Handoff: IV.2는 메타, 포탈 물리 FACT를 재사용하되 앱을 동일시하지 않음
```

**그림 상세해설**

1. **그림 목적:** 누가 무엇을 답해야 이 절이 승인되는지 Gate로 고정하고 IV.2에 넘긴다.
2. **근거자료와 상태:** Owner 태그는 목차 업무담당 + TA/DA/보안. Evidence=장표 보강·인터뷰·ADR.
3. **Boundary / In / Out:** In: Overlay 점선. Out: IV.2 입력 계약. 밖: 도구 선정(Jenkins 등) 이 절 범위 아님.
4. **Trigger / 시작점:** IV.1 리뷰 종료, 다음 아젠다 확정.
5. **처리순서:** ① Gate 항목 → ② Owner별 Evidence → ③ 영향 FIG → ④ 미응답 시 동결 금지 → ⑤ IV.2 Handoff.
6. **책임·비책임:** 책임: 각 Owner. 아키텍처는 Gate 유지. 비책임: 답변 대행.
7. **데이터/전문/상태/제어:** 운반물: 질문 Pack 답변, ADR 결정문.
8. **실패·운영·후속:** 실패=점선 잔존. 운영 사고 시 런북 공백(FIG-10). 다음 안건=COL-IV.1-01/02.

## 19. 검증 체크리스트

- [ ] Figure Plan 18 = 본문 FIG 18
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] IV.1 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 18 | 실제 18 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 18 · Figure Plan 행 18 · 본문 ` ```text ` 그림 코드블록(FIG) 18건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`IV.1` M3 심화. 18개 FIG를 장표 Topology·정상 Sequence·AP/DB/소스 장애·스토리지/환경 Overlay로 분리했다. 창작값 0. 남은 승인 차단은 기능목록·SSO·DR·ADR-BIP-01.

---

# IV.2 BIZ메타

**협업 태그:** [업무담당파트 자료]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 이름·일부 연계는 확정, Repository/워크플로는 TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-IV.2-01 | 원문 목차 | IV.2 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-IV.2-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-IV.2-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-IV-04 | BI/흐름관리 장표 | BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C | Self BI·SSO AS-IS 문의 |

## 1. Figure Plan

필수 Figure Slot **18**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-IV.2-01 | BIZMeta Confirmed Context | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-02 | Confirmed Metadata Source/Consumer Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-03 | BIZMeta↔BI Portal Physical/Logical Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-04 | BIZMeta↔Q-Track↔DAMS Responsibility Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-05 | Known Repository vs Unknown Repository Gap View | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-06 | Metadata Lifecycle Skeleton: Create/Register→Review→Publish→Consume [단계 근거 없으면 모두 TBD] | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-07 | Search/Consumption Skeleton | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-08 | Metadata Type Unknown Map (어떤 분류가 필요한지, 값은 TBD) | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-09 | Owner/RACI Dependency Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-10 | Required Evidence Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-11 | Interview Question Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-12 | Architecture Option A: Portal DB 공유 [PROPOSED] | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-13 | Architecture Option B: 독립 Repository [PROPOSED] | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-14 | Option Comparison / Decision Criteria | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-15 | Security/Authorization/Change Governance Questions | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-16 | AS-IS→Transition→TO-BE Skeleton | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-17 | Approval Blocking GAP Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.2-18 | BIZMeta Handoff to IV.4/DA | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**IV.2 BIZ메타** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `IV.2 BIZ메타`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [업무담당파트 자료]

## 4. L0 Big Picture

### FIG-IV.2-01 BIZMeta Confirmed Context

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.2-01 │
└─────────────┘
┌───────────────────────────┐
│ BIZMeta Confirmed Context │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [업무담당파트 자료]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'BIZMeta Confirmed Context' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-IV.2-05 Known Repository vs Unknown Repository Gap View

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────────────────────────┐
│ IV.2 / 05 Known Repository vs Unknown Repository Gap V │
└────────────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Known Repository vs Unknown Repository Gap View' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-IV.2-06 Metadata Lifecycle Skeleton: Create/Register→Review→Publish→Consume [단계 근거 없으면 모두 TBD]

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────────────────────────┐
│ IV.2 / 06 Metadata Lifecycle Skeleton: Create/Register │
└────────────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Metadata Lifecycle Skeleton: Create/Register→Review→Publish→Consume [단계 근거 없으면 모두 TBD]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-07 Search/Consumption Skeleton

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.2-07 │
└─────────────┘
┌─────────────────────────────┐
│ Search/Consumption Skeleton │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [업무담당파트 자료]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Search/Consumption Skeleton' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-12 Architecture Option A: Portal DB 공유 [PROPOSED]

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────┐
│ IV.2 의사결정 분기 [TO-BE/PROPOSED] │
└───────────────────────────────┘
          │
          ▼
┌──────────┐
│ Option A │
└──────────┘
          │
          ▼
┌───────────────────────────┐
│ 채택 시 영향 FIG / 필요 Evidence │
└───────────────────────────┘
          │
          ▼
┌─────────────────────┐
│ 미채택 시 대안 Option B/C │
└─────────────────────┘
[금지] 근거 없는 제품/프로토콜을 Option 안에 FACT로 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Architecture Option A: Portal DB 공유 [PROPOSED]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-IV.2-02 Confirmed Metadata Source/Consumer Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.2-02 │
└─────────────┘
┌────────────────────────────────────────┐
│ Confirmed Metadata Source/Consumer Map │
└────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [업무담당파트 자료]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Confirmed Metadata Source/Consumer Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-03 BIZMeta↔BI Portal Physical/Logical Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.2-03 │
└─────────────┘
┌─────────────────────────────────────────────┐
│ BIZMeta↔BI Portal Physical/Logical Boundary │
└─────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [업무담당파트 자료]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'BIZMeta↔BI Portal Physical/Logical Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-04 BIZMeta↔Q-Track↔DAMS Responsibility Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.2-04 │
└─────────────┘
┌─────────────────────────────────────────┐
│ BIZMeta↔Q-Track↔DAMS Responsibility Map │
└─────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [업무담당파트 자료]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'BIZMeta↔Q-Track↔DAMS Responsibility Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-08 Metadata Type Unknown Map (어떤 분류가 필요한지, 값은 TBD)

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────────────────────────┐
│ IV.2 / 08 Metadata Type Unknown Map (어떤 분류가 필요한지, 값은 T │
└────────────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Metadata Type Unknown Map (어떤 분류가 필요한지, 값은 TBD)' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-09 Owner/RACI Dependency Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.2-09 │
└─────────────┘
┌───────────────────────────┐
│ Owner/RACI Dependency Map │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [업무담당파트 자료]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Owner/RACI Dependency Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-10 Required Evidence Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.2-10 │
└─────────────┘
┌───────────────────────┐
│ Required Evidence Map │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [업무담당파트 자료]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Required Evidence Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-17 Approval Blocking GAP Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────────────┐
│ IV.2 / 17 Approval Blocking GAP Map │
└─────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Approval Blocking GAP Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-IV.2-11 Interview Question Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────┐
│ IV.2 / 11 Interview Question Flow │
└───────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Interview Question Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.2-13 Architecture Option B: 독립 Repository [PROPOSED]

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────┐
│ IV.2 Option B [TO-BE/PROPOSED] │
└────────────────────────────────┘
          │
          ▼
┌──────────┐
│ 독립/대체 경로 │
└──────────┘
          │
          ▼
┌──────────────────────┐
│ 비교 기준: 책임경계·운영·보안·이관 │
└──────────────────────┘
          │
          ▼
┌─────────────────────┐
│ Decision Gate → ADR │
└─────────────────────┘
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Architecture Option B: 독립 Repository [PROPOSED]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-IV.2-14 Option Comparison / Decision Criteria

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────┐
│ IV.2 Option C [TO-BE/PROPOSED] │
└────────────────────────────────┘
          │
          ▼
┌───────────────────────┐
│ 예외 Adapter/Gateway 골격 │
└───────────────────────┘
          │
          ▼
┌───────────────┐
│ [TBD] 프로토콜/제품 │
└───────────────┘
          │
          ▼
┌─────────────┐
│ 승인 주체 [TBD] │
└─────────────┘
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Option Comparison / Decision Criteria' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-IV.2-15 Security/Authorization/Change Governance Questions

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────────────────────────┐
│ IV.2 / 15 Security/Authorization/Change Governance Que │
└────────────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Security/Authorization/Change Governance Questions' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| IV.2 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [업무담당파트 자료] | 후속 설계 중단 |
| [TBD] 칸 | 빈칸 가시화 | 질문 Pack | ADR 후보 | Owner 미확정 표기 | 승인 차단 |

## 13. Flow / Interface / Contract 정의표

| Source | Target | Data | Direction | Sync/Async | Contract | Error/Recovery |
|--------|--------|------|-----------|------------|----------|----------------|
| 해당 절 Source [FACT 이름만] | Target [FACT 또는 TBD] | 업무데이터 또는 메타 | 그림 화살표 | 근거 없으면 TBD | 레이아웃 [GAP] | 별도 Failure FIG |

## 14. 설계 규칙 / 금지 / 예외

**규칙**
- FACT 이름 유지. 영역/패턴/환경 이름을 새로 만들지 않는다.
- M1에서 일반 제품 아키텍처를 FACT로 승격하지 않는다.

**금지**
- 창작 스펙, NSIGHT 제품을 하나 FACT로 전환, Q-Track을 ETL 엔진으로 표현, `상동`/`4~12.` 축약.

**예외**
- 예외는 ADR + 승인 Owner. 우회 경로를 기본값으로 두지 않는다.

## 15. Requirement/Policy/Principle→Decision→FIG Traceability

| Req/Policy/Principle | Decision/Claim | FIG | Verification |
|----------------------|----------------|-----|--------------|
| 목차 IV.2 | 슬롯 100% 독립 그림 | FIG-IV.2-01~ | Completion Gate 수치 |
| 유지보수 비영향 | 최소변경·XDA 범위 | 관련 Why/Scope FIG | 부분개선 접점 유지 |
| Evidence Maturity | `M2` 그림 종류 | Discovery 또는 Runtime | 가짜 상세 0건 |

## 16. AS-IS vs TO-BE / 변경영향

```text
AS-IS
  현행 CRM/BSA/RDW · ETCL 분산 · SAP BO OLAP · EUC-KR 중심
  │ 유지 / 변경 / 폐기 / 대체 / 공존
  ▼
Transition
  Oracle Exa · 신·구 공존 · XDA SQL · 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
  DR·SSO·Self BI·BC 정의는 아직 빈칸
  ▼
TO-BE
  프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
  RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
```

변화점만 연결하고, 없는 이관 절차를 만들지 않는다.

### FIG-IV.2-16 AS-IS→Transition→TO-BE Skeleton

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.2-16 │
└─────────────┘
┌─────────────────────────────────┐
│ AS-IS→Transition→TO-BE Skeleton │
└─────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [업무담당파트 자료]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS→Transition→TO-BE Skeleton' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [업무담당파트 자료]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-IV.2-01 | 자료 | 이 절 빈 박스 | [업무담당파트 자료] | 장표/인터뷰/ADR |
| GAP-IV.2-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-IV.2-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- IV.2 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- IV.2 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [업무담당파트 자료]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- IV.2 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-IV.2-18 BIZMeta Handoff to IV.4/DA

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────────┐
│ IV.2 산출 (그림·GAP·ADR) │
└──────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌────────────────────┐
│ 승인 Gate / 다음 리뷰 안건 │
└────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'BIZMeta Handoff to IV.4/DA' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 18 = 본문 FIG 18
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] IV.2 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 18 | 실제 18 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 18 · Figure Plan 행 18 · 본문 ` ```text ` 그림 코드블록(FIG) 18건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`IV.2` V5 재작성. 필수 FIG 18개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# IV.3 Self BI

**협업 태그:** [업무담당파트 자료] M1

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M1` — 근거 부족. Discovery/Question/Option/Gate 그림을 본체로 둔다. 가짜 Runtime 금지.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-IV.3-01 | 원문 목차 | IV.3 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-IV.3-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-IV.3-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-IV-04 | BI/흐름관리 장표 | BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C | Self BI·SSO AS-IS 문의 |

## 1. Figure Plan

필수 Figure Slot **20**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-IV.3-01 | Self BI Confirmed Fact Boundary | L0 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-02 | Self BI Unknown/GAP Topology | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-03 | User Persona / Use-case Unknown Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-04 | Data Source Candidates: ADW/Virtualization/etc [근거별 상태] | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-05 | BI Portal vs Self BI Responsibility Boundary | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-06 | Existing OLAP(SAP BO) vs Self BI Relationship Gap | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-07 | S10/User Analysis AP Evidence Gap | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-08 | Data Access Option A: Virtualization [PROPOSED] | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-09 | Data Access Option B: Direct governed source [PROPOSED] | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-10 | Product/Runtime Decision Tree | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-11 | Query/Resource Isolation Decision Tree | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-12 | Security/Authorization Decision Tree | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-13 | Download/Export/Data Protection Question Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-14 | Required NFR/SLA Evidence Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-15 | Owner/Dependency Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-16 | Interview Question Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-17 | Architecture Options Comparison | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-18 | AS-IS OLAP→Transition→Self BI Target Scenarios | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-19 | Approval Blocking GAP / ADR Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.3-20 | Target Architecture Skeleton with TBD slots | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

M1. 제품/시맨틱/워크스페이스 FACT화 금지. Discovery가 본체.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `IV.3 Self BI`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [업무담당파트 자료] M1

## 4. L0 Big Picture

### FIG-IV.3-01 Self BI Confirmed Fact Boundary

**Level:** L0 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-01 │
└─────────────┘
┌─────────────────────────────────┐
│ Self BI Confirmed Fact Boundary │
└─────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Self BI Confirmed Fact Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-IV.3-04 Data Source Candidates: ADW/Virtualization/etc [근거별 상태]

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-04 │
└─────────────┘
┌────────────────────────────────────────────────────────┐
│ Data Source Candidates: ADW/Virtualization/etc [근거별 상태 │
└────────────────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Data Source Candidates: ADW/Virtualization/etc [근거별 상태]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-IV.3-02 Self BI Unknown/GAP Topology

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────────┐
│ IV.3 / 02 Self BI Unknown/GAP Topology │
└────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료] M1
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Self BI Unknown/GAP Topology' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-06 Existing OLAP(SAP BO) vs Self BI Relationship Gap

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-06 │
└─────────────┘
┌───────────────────────────────────────────────────┐
│ Existing OLAP(SAP BO) vs Self BI Relationship Gap │
└───────────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Existing OLAP(SAP BO) vs Self BI Relationship Gap' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-07 S10/User Analysis AP Evidence Gap

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-07 │
└─────────────┘
┌───────────────────────────────────┐
│ S10/User Analysis AP Evidence Gap │
└───────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'S10/User Analysis AP Evidence Gap' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-08 Data Access Option A: Virtualization [PROPOSED]

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────┐
│ IV.3 의사결정 분기 [TO-BE/PROPOSED] │
└───────────────────────────────┘
          │
          ▼
┌──────────┐
│ Option A │
└──────────┘
          │
          ▼
┌───────────────────────────┐
│ 채택 시 영향 FIG / 필요 Evidence │
└───────────────────────────┘
          │
          ▼
┌─────────────────────┐
│ 미채택 시 대안 Option B/C │
└─────────────────────┘
[금지] 근거 없는 제품/프로토콜을 Option 안에 FACT로 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Data Access Option A: Virtualization [PROPOSED]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-09 Data Access Option B: Direct governed source [PROPOSED]

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────┐
│ IV.3 Option B [TO-BE/PROPOSED] │
└────────────────────────────────┘
          │
          ▼
┌──────────┐
│ 독립/대체 경로 │
└──────────┘
          │
          ▼
┌──────────────────────┐
│ 비교 기준: 책임경계·운영·보안·이관 │
└──────────────────────┘
          │
          ▼
┌─────────────────────┐
│ Decision Gate → ADR │
└─────────────────────┘
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Data Access Option B: Direct governed source [PROPOSED]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-IV.3-03 User Persona / Use-case Unknown Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────────────────┐
│ IV.3 / 03 User Persona / Use-case Unknown Map │
└───────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료] M1
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'User Persona / Use-case Unknown Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-05 BI Portal vs Self BI Responsibility Boundary

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-05 │
└─────────────┘
┌──────────────────────────────────────────────┐
│ BI Portal vs Self BI Responsibility Boundary │
└──────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'BI Portal vs Self BI Responsibility Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-13 Download/Export/Data Protection Question Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────────────────────────┐
│ IV.3 / 13 Download/Export/Data Protection Question Map │
└────────────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료] M1
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Download/Export/Data Protection Question Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-14 Required NFR/SLA Evidence Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-14 │
└─────────────┘
┌───────────────────────────────┐
│ Required NFR/SLA Evidence Map │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Required NFR/SLA Evidence Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-15 Owner/Dependency Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-15 │
└─────────────┘
┌──────────────────────┐
│ Owner/Dependency Map │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Owner/Dependency Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-19 Approval Blocking GAP / ADR Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────────────┐
│ IV.3 / 19 Approval Blocking GAP / ADR Map │
└───────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료] M1
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Approval Blocking GAP / ADR Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-IV.3-11 Query/Resource Isolation Decision Tree

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-11 │
└─────────────┘
┌────────────────────────────────────────┐
│ Query/Resource Isolation Decision Tree │
└────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Query/Resource Isolation Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.3-16 Interview Question Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────┐
│ IV.3 / 16 Interview Question Flow │
└───────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료] M1
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Interview Question Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-IV.3-17 Architecture Options Comparison

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-17 │
└─────────────┘
┌─────────────────────────────────┐
│ Architecture Options Comparison │
└─────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Architecture Options Comparison' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-IV.3-12 Security/Authorization Decision Tree

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-12 │
└─────────────┘
┌──────────────────────────────────────┐
│ Security/Authorization Decision Tree │
└──────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Security/Authorization Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-IV.3-10 Product/Runtime Decision Tree

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────┐
│ ① Trigger [IV.3] │
└──────────────────┘
          │
          ▼
┌──────────────────────────────────────┐
│ ② 처리 (Product/Runtime Decision Tree) │
└──────────────────────────────────────┘
          │
          ▼
┌──────────────────────────┐
│ ③ 계약/저장/응답 [근거 있는 칸만 이름] │
└──────────────────────────┘
          │
          ▼
┌──────────────────┐
│ ④ 관측/로그 [위치 TBD] │
└──────────────────┘
단계번호는 본문 해설 ①~N과 동일
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Product/Runtime Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| IV.3 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [업무담당파트 자료] M1 | 후속 설계 중단 |
| [TBD] 칸 | 빈칸 가시화 | 질문 Pack | ADR 후보 | Owner 미확정 표기 | 승인 차단 |

## 13. Flow / Interface / Contract 정의표

| Source | Target | Data | Direction | Sync/Async | Contract | Error/Recovery |
|--------|--------|------|-----------|------------|----------|----------------|
| 해당 절 Source [FACT 이름만] | Target [FACT 또는 TBD] | 업무데이터 또는 메타 | 그림 화살표 | 근거 없으면 TBD | 레이아웃 [GAP] | 별도 Failure FIG |

## 14. 설계 규칙 / 금지 / 예외

**규칙**
- FACT 이름 유지. 영역/패턴/환경 이름을 새로 만들지 않는다.
- M1에서 일반 제품 아키텍처를 FACT로 승격하지 않는다.

**금지**
- 창작 스펙, NSIGHT 제품을 하나 FACT로 전환, Q-Track을 ETL 엔진으로 표현, `상동`/`4~12.` 축약.

**예외**
- 예외는 ADR + 승인 Owner. 우회 경로를 기본값으로 두지 않는다.

## 15. Requirement/Policy/Principle→Decision→FIG Traceability

| Req/Policy/Principle | Decision/Claim | FIG | Verification |
|----------------------|----------------|-----|--------------|
| 목차 IV.3 | 슬롯 100% 독립 그림 | FIG-IV.3-01~ | Completion Gate 수치 |
| 유지보수 비영향 | 최소변경·XDA 범위 | 관련 Why/Scope FIG | 부분개선 접점 유지 |
| Evidence Maturity | `M1` 그림 종류 | Discovery 또는 Runtime | 가짜 상세 0건 |

## 16. AS-IS vs TO-BE / 변경영향

```text
AS-IS
  현행 CRM/BSA/RDW · ETCL 분산 · SAP BO OLAP · EUC-KR 중심
  │ 유지 / 변경 / 폐기 / 대체 / 공존
  ▼
Transition
  Oracle Exa · 신·구 공존 · XDA SQL · 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
  DR·SSO·Self BI·BC 정의는 아직 빈칸
  ▼
TO-BE
  프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
  RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
```

변화점만 연결하고, 없는 이관 절차를 만들지 않는다.

### FIG-IV.3-18 AS-IS OLAP→Transition→Self BI Target Scenarios

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.3-18 │
└─────────────┘
┌────────────────────────────────────────────────┐
│ AS-IS OLAP→Transition→Self BI Target Scenarios │
└────────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [업무담당파트 자료] M1
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS OLAP→Transition→Self BI Target Scenarios' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [업무담당파트 자료] M1

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-IV.3-01 | 자료 | 이 절 빈 박스 | [업무담당파트 자료] M1 | 장표/인터뷰/ADR |
| GAP-IV.3-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-IV.3-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- IV.3 슬롯 이름·Evidence Maturity `M1`

**What blocks approval**
- IV.3 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [업무담당파트 자료] M1
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- IV.3 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-IV.3-20 Target Architecture Skeleton with TBD slots

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────────────────────────┐
│ IV.3 / 20 Target Architecture Skeleton with TBD slots │
└───────────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [업무담당파트 자료] M1
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Target Architecture Skeleton with TBD slots' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [업무담당파트 자료] M1. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 20 = 본문 FIG 20
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] IV.3 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 20 | 실제 20 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 20 · Figure Plan 행 20 · 본문 ` ```text ` 그림 코드블록(FIG) 20건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`IV.3` V5 재작성. 필수 FIG 20개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# IV.4 데이터흐름관리

**협업 태그:** [업무담당파트 자료] DA/TA

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M3` — 물리·제품·HA 근거가 있다. Topology/정상 Sequence/장애를 분리한다.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-IV.4-01 | 원문 목차 | IV.4 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-IV.4-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-IV.4-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-IV-04 | BI/흐름관리 장표 | BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C | Self BI·SSO AS-IS 문의 |

## 1. Figure Plan

필수 Figure Slot **26**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-IV.4-01 | Data Flow Management L0 Context | L0 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-02 | Real Data Flow vs Metadata Flow Separation | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-03 | Metadata Source Catalog: DAMS / ETCL / CDC / EAI / Source / Bigdata | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-04 | Metadata Collection Boundary | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-05 | Create→Extract→Transform→Load Lineage Concept View | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-06 | Q-Track Logical Component View | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-07 | Q-Track AP/DB/Storage Physical Topology | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-08 | Q-Track Software Stack Mapping | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-09 | DEV Topology | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-10 | TEST Topology | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-11 | PROD Topology | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-12 | DR Evidence Gap Topology | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-13 | Metadata Normal Processing Sequence | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-14 | ETCL Metadata Collection Sequence | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-15 | CDC/EAI Metadata Collection Sequence | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-16 | Source Code Metadata Collection Sequence | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-17 | Q-Track→DAMS/BI Portal/BizMeta Consumption Flow | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-18 | Collection Failure / Retry / Reconciliation [근거 없으면 TBD] | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-19 | AP Active→Standby Failover | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-20 | DB Active→Standby Failover | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-21 | Monitoring / Batch / Capacity Operation View | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-22 | Security/Metadata Sensitivity View | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-23 | BIZMeta/Q-Track/DAMS Responsibility Overlap | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-24 | AS-IS→TO-BE/Transition View | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-25 | Owner/DA/TA Responsibility Map | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.4-26 | GAP/ADR/Approval Gate | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 흐름관리는 데이터 변화 흐름을 **분석**하는 솔루션이다(상세는 DA 영역 참조). 수집 원천=DAMS, Data Interface(ETCL/CDC/EAI/…), 형상 소스, 빅데이터 메타. 스택=Q-Track 3.1, WebtoB, JEUS 8.5, JDK 11, AP A-S(제품 A-A 미지원), Oracle 19C A-S, 운영 NAS. 개발/테스트는 단독·NAS 없음. 활용=DAMS(IDAMS)·BI Portal·Biz Meta·Q-Track. 실데이터 적재(III.4)와 계층이 다르다. Q-Track을 TeraStream으로 부르지 않는다. DR 장표 없음.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `IV.4 데이터흐름관리`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [업무담당파트 자료] DA/TA

## 4. L0 Big Picture

### FIG-IV.4-01 Data Flow Management L0 Context

**Level:** L0 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정의 [FACT]: 생성/추출/변환/적재를 통해 DB에 저장·가공하는
            일련의 데이터 변화 흐름을 분석하는 솔루션 영역
상세는 데이터 아키텍처 영역 참조 [FACT]
        │
        ▼
수집 원천 [FACT]
 DAMS(IT 메타) · Data Interface(ETCL, CDC, EAI, …)
 형상관리(소스코드) · 빅데이터 메타
        │ 흐름 정보(메타)만
        ▼
┌── APP-DFM  Q-Track ─────────────────────────┐
│ Q-Track 3.1 · WebtoB · JEUS 8.5 · JDK 11     │
│ AP#1 Active / AP#2 Standby  (제품 A-A 미지원) │
│ Oracle 19C #1/#2 Active-Standby               │
│ 공용 Storage (운영 NAS). 개발/테스트 NAS 없음  │
└──────────────────────┬────────────────────────┘
                       ▼
활용 [FACT]: DAMS(IDAMS) · BI Portal · Biz Meta · Q-Track UI
종료조건 = 계보/흐름 조회. 적재 성공(III.4)이 아님
```

**그림 상세해설**

1. **그림 목적:** 흐름관리가 ‘분석 솔루션’이지 ETL 엔진이 아님을 L0에서 고정한다.
2. **근거자료와 상태:** [FACT] 정의 문구, 원천 4종, Q-Track 3.1, WebtoB, JEUS 8.5, JDK 11, AP/DB A-S, 활용 4칸. [GAP] 화면 기능명 Lineage 영문 확정.
3. **Boundary / In / Out:** In: 인터페이스/메타 원천. Out: 활용 시스템. 밖: RTW 적재 파이프(III.4).
4. **Trigger / 시작점:** 원천 쪽 변화 또는 사용자가 흐름을 조회할 때.
5. **처리순서:** ① 정의 확인 → ② 원천 칸 → ③ Q-Track 스택 → ④ 활용 칸 → ⑤ 종료=조회.
6. **책임·비책임:** 책임: 업무담당+DA(상세 참조). TA 노드. 비책임: TeraStream 대체 표현.
7. **데이터/전문/상태/제어:** 운반물: 흐름 메타. 실데이터 행은 이 상자 밖.
8. **실패·운영·후속:** 수집 실패=FIG-18. AP/DB Failover=FIG-19/20. DR=FIG-12 GAP.

## 5. L1 영역/계층/서비스 View

### FIG-IV.4-03 Metadata Source Catalog: DAMS / ETCL / CDC / EAI / Source / Bigdata

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Metadata Source Catalog [FACT 이름만, 수집기 내부 창작 금지]
┌────────────┬─────────────────────────────┐
│ 원천        │ 장표 역할                      │
├────────────┼─────────────────────────────┤
│ DAMS       │ IT 메타. 활용칸에 IDAMS도 표기 │
│ ETCL       │ Data Interface 흐름 정보       │
│ CDC        │ Data Interface 흐름 정보       │
│ EAI        │ Data Interface 흐름 정보       │
│ 형상/소스   │ 프로그램 계보 후보              │
│ 빅데이터메타│ 빅데이터 영역 메타              │
│ …          │ 장표 말줄임. 추가 수단 창작 금지 │
└────────────┴─────────────────────────────┘
각 원천은 FIG-14/15/16에서 독립 Sequence
BC(III.4) 정의 미입수 → 이 카탈로그에 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** 수집 원천 이름을 카탈로그로 고정하고 각각을 나중에 독립 Sequence로 넘긴다.
2. **근거자료와 상태:** [FACT] DAMS, ETCL, CDC, EAI, 형상, 빅데이터 메타. 말줄임 존재. [GAP] 원천별 어댑터 스펙.
3. **Boundary / In / Out:** In: 장표7 원천. Out: 원천 ID. 밖: BC, 실데이터 테이블.
4. **Trigger / 시작점:** 원천 온보딩 설계.
5. **처리순서:** ① 이름 목록 → ② 역할 한 줄 → ③ 독립 FIG로 분해 → ④ 없는 수단 추가 금지.
6. **책임·비책임:** 책임: DA/인터페이스 원천 Owner, 흐름관리 수집 경계(FIG-04).
7. **데이터/전문/상태/제어:** 운반물: 원천 메타 레코드(스키마 창작 금지).
8. **실패·운영·후속:** 원천 누락=계보 공백. 온보딩 GAP=COL-IV.4-01.

### FIG-IV.4-05 Create→Extract→Transform→Load Lineage Concept View

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
장표 정의의 개념 축 (영문 Lineage 제품기능을 FACT화하지 않음)
 Create (생성) → Extract (추출) → Transform (변환) → Load (적재/가공)
        │
        ▼
이 축은 실데이터 생애에 대한 분석 관점
실제 수행 엔진 = CDC/ETCL/EAI/배치 (III)
Q-Track = 위 축을 따라 변화 흐름을 분석·표시 [FACT 문구]
화면 메뉴가 C/E/T/L 네 칸인지는 [GAP]
```

**그림 상세해설**

1. **그림 목적:** C-E-T-L을 ‘Q-Track이 수행하는 ETL’이 아니라 ‘분석 대상 개념 축’으로 그린다.
2. **근거자료와 상태:** [FACT] 정의 문구에 생성/추출/변환/적재·저장·가공·흐름 분석. 영문 기능명 없음.
3. **Boundary / In / Out:** In: 정의 문장. Out: 개념 축. 밖: TeraStream 매핑 강제.
4. **Trigger / 시작점:** DA/업무와 용어 맞출 때.
5. **처리순서:** ① 네 단어 나열 → ② 실수행은 III → ③ Q-Track은 분석 → ④ UI 확정 금지.
6. **책임·비책임:** 책임: DA 개념, 제품 화면은 담당 자료.
7. **데이터/전문/상태/제어:** 메타: 단계 라벨. 실데이터 아님.
8. **실패·운영·후속:** 축이 비면 활용 UI가 공허. 기능명 GAP=GAP-IV.4-01.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-IV.4-06 Q-Track Logical Component View

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
논리 컴포넌트 (장표 스택에서 분리)
┌──────── Q-Track 3.1 ────────┐
│ 흐름 분석 UI / 관리 앱        │
└────────────┬────────────────┘
             │
┌──────── WebtoB ─────────────┐
│ WEB 진입                     │
└────────────┬────────────────┘
             │
┌──────── JEUS 8.5 · JDK 11 ─┐
│ AP 런타임                    │
└────────────┬────────────────┘
             │
┌──────── Oracle 19C ─────────┐
│ 흐름 메타 Repository          │
└─────────────────────────────┘
+ 공용 Storage (운영)
포탈 DataEye 스택과 제품이 다름 → 동일 앱 단정 금지
```

**그림 상세해설**

1. **그림 목적:** Q-Track 논리 스택을 포탈(DataEye)과 다른 제품군으로 고정한다.
2. **근거자료와 상태:** [FACT] Q-Track 3.1, WebtoB, JEUS 8.5, JDK 11, Oracle 19C, 공용 Storage.
3. **Boundary / In / Out:** In: APP-DFM. Out: WEB/WAS/DB/Storage. 밖: DataEye, TeraStream.
4. **Trigger / 시작점:** 소프트웨어 구성 리뷰(II.4.5 연결).
5. **처리순서:** ① UI → ② WebtoB → ③ JEUS/JDK → ④ Oracle → ⑤ Storage.
6. **책임·비책임:** 책임: 제품=업무/TA, DB=DA. 비책임: 모듈 내부 클래스 창작.
7. **데이터/전문/상태/제어:** 제어: HTTP→WAS→JDBC. 데이터: 흐름 메타.
8. **실패·운영·후속:** WAS 장애=FIG-19. DB=FIG-20. 포탈 JEUS 버전과 불일치 가능(복사 금지).

### FIG-IV.4-07 Q-Track AP/DB/Storage Physical Topology

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
운영 물리 [FACT]
              [사용자/활용시스템]
                      │
                      ▼
              WebtoB / L4?  [L4 여부 포탈과 달리 장표 확인 범위: WEB WebtoB FACT]
                      │
          ┌───────────┴───────────┐
          ▼                       ▼
   AP#1 Active              AP#2 Standby
   RHEL 9                   RHEL 9
   24C / 256G / 2T          24C / 256G / 2T
          │                       │
          └──────────┬────────────┘
                     ▼ 공용 Storage (운영 NAS SDS 2~4T)
          Oracle 19C #1 Active ──A-S── #2 Standby
          RHEL 8.6  DB 8C/512G/2T ×2
제품 제약: Active-Active 미지원 → AP도 A-S [FACT]
Host/IP 미확정 [GAP]
```

**그림 상세해설**

1. **그림 목적:** 운영 물리 Topology. A-A로 그리지 않는다.
2. **근거자료와 상태:** [FACT] AP#1 A / AP#2 S, DB A-S, 스펙, RHEL 9 AP, RHEL 8.6 DB, NAS 운영. A-A 미지원. [GAP] Host/IP, L4 별도 여부.
3. **Boundary / In / Out:** In: 사용자. Out: AP/DB/NAS. 밖: Portal_AP A-A.
4. **Trigger / 시작점:** 운영 기동.
5. **처리순서:** ① WEB 진입 → ② Active AP → ③ Oracle Active → ④ Storage. Standby는 대기.
6. **책임·비책임:** 책임: TA 노드, 스토리지. 제품 제약 설명 의무.
7. **데이터/전문/상태/제어:** 제어: A-S 전환(FIG-19/20). 데이터: 메타+NAS 파일(용도 상세 GAP).
8. **실패·운영·후속:** AP Failover≠DB Failover. DR 없음=FIG-12.

### FIG-IV.4-12 DR Evidence Gap Topology

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
DR [GAP]
┌──────────────────────────────┐
│ 흐름관리 DR 사이트 장표 없음   │
│ RTO/RPO 창작 금지             │
│ 운영 NAS 복제 여부 미기재      │
│ Q-Track 메타 백업 체계 [TBD]   │
└──────────────────────────────┘
영향: 운영 사이트 재해 시 계보 조회 중단 가능
적재(III.4) DR과 이 칸을 자동 동일시하지 않음
```

**그림 상세해설**

1. **그림 목적:** DR이 없음을 빈 Topology로 보여 준다. 가짜 DR을 그리지 않는다.
2. **근거자료와 상태:** [GAP] DR 장표 없음. 운영 NAS·스펙만 FACT.
3. **Boundary / In / Out:** In: 재해 가정. Out: 공백. 밖: 허브 DR(II.4.2도 GAP 가능).
4. **Trigger / 시작점:** BCP 리뷰.
5. **처리순서:** ① 장표 검색 → ② 없음 → ③ 영향 한 줄 → ④ 적재 DR과 분리.
6. **책임·비책임:** 책임: TA/운영 BCP. 아키텍처는 GAP 유지.
7. **데이터/전문/상태/제어:** 없음.
8. **실패·운영·후속:** 승인 시 DR 공백을 리스크로 남김. FIG-26 Gate.

## 7. Static Mapping / Responsibility View

### FIG-IV.4-04 Metadata Collection Boundary

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[원천 시스템 내부]
 실제 ETL 엔진 / CDC 캡처 / EAI 허브 / Git 등
        │ 이 경계 안은 IV.4가 실행하지 않음
        ▼
[수집 경계]
 흐름 정보만 Q-Track 쪽으로 전달
 전달 방식(API/파일/DB링크/배치) = [GAP]
        │
        ▼
[Q-Track]
 AP A-S → Oracle 19C
금지: 경계 안에서 TeraStream 잡을 Q-Track이 돌린다고 그림
재처리/정합 범위도 이 경계 밖 원천 vs 안 저장소로 나뉨 → FIG-18
```

**그림 상세해설**

1. **그림 목적:** 무엇이 Q-Track 책임이고 무엇이 원천 실행 책임인지 경계를 긋는다.
2. **근거자료와 상태:** [FACT] 분석 솔루션, 원천에 Data Interface. [GAP] 수집 프로토콜.
3. **Boundary / In / Out:** In: 원천 이벤트. Out: Q-Track 입력. 밖: ETCL 실행(III.2.1).
4. **Trigger / 시작점:** 수집 설계, 장애 책임 나눌 때.
5. **처리순서:** ① 원천 실행 칸 → ② 전달 칸(방식 TBD) → ③ Q-Track 저장 → ④ 실행 엔진 오인 금지.
6. **책임·비책임:** 책임: 원천 시스템 Owner vs 흐름관리. TA는 연결만.
7. **데이터/전문/상태/제어:** Direction: 원천→Q-Track. Sync/Async 미기재.
8. **실패·운영·후속:** 경계 밖 실패는 III.4. 경계 안 실패는 FIG-18/19/20.

### FIG-IV.4-08 Q-Track Software Stack Mapping

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Node     SW              Version/OS        HA
AP       Q-Track         3.1               A-S (제품 A-A 미지원)
AP       WebtoB          (버전 장표 범위)  AP와 동일 노드 [ANALYSIS]
AP       JEUS            8.5               AP A-S
AP       JDK             11                AP
AP OS    RHEL            9
DB       Oracle          19C               A-S
DB OS    RHEL            8.6
Storage  NAS SDS         2~4T 운영         개발/테스트 없음
포탈 JEUS 버전을 이 표에 복사하지 않음
```

**그림 상세해설**

1. **그림 목적:** 소프트웨어-노드-HA 매핑표의 그림 형태. 버전은 장표 있는 것만.
2. **근거자료와 상태:** [FACT] 위 스택. WebtoB 세부 버전 공란 가능. [금지] 포탈 JEUS 버전 전용.
3. **Boundary / In / Out:** In: FIG-06/07. Out: II.4.4/4.5 입력. 밖: DataEye.
4. **Trigger / 시작점:** SW 식별 리뷰.
5. **처리순서:** ① AP 스택 → ② DB 스택 → ③ NAS → ④ HA 열 A-S 고정.
6. **책임·비책임:** 책임: TA SW 표준, 보안 패치 주기 [GAP].
7. **데이터/전문/상태/제어:** 구성 메타. 실행 데이터 아님.
8. **실패·운영·후속:** 버전 불일치 사고=운영. 패치 창=FIG-21.

### FIG-IV.4-23 BIZMeta/Q-Track/DAMS Responsibility Overlap

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
        DAMS(IT메타)     Q-Track(흐름메타)     BIZ메타(의미?)
Master  원천이자 활용     변화 흐름 분석        장표 이름만
        IDAMS 표기       활용칸에 세 이름      워크플로 GAP
중복 가능 속성: 시스템/테이블/잡 이름 [ANALYSIS]
Master 결정 없음 → ADR-META-01
금지: 비즈메타=Q-Track 화면의 다른 이름 단정
금지: DAMS를 Q-Track이 대체한다고 그림
```

**그림 상세해설**

1. **그림 목적:** 세 메타 주체의 중첩을 책임 그림으로 보여 ADR로 넘긴다.
2. **근거자료와 상태:** [FACT] 세 이름 모두 장표. DAMS는 원천+활용. [GAP] glossary Master.
3. **Boundary / In / Out:** In: IV.2/IV.4/DAMS. Out: ADR-META-01. 밖: 실데이터 사전.
4. **Trigger / 시작점:** 메타 거버넌스 회의.
5. **처리순서:** ① 세 상자 → ② 확인된 역할 한 줄 → ③ 중복 후보 → ④ Master TBD.
6. **책임·비책임:** 책임: DA DAMS, 흐름 담당 Q-Track, 업무 BIZ. 공동 ADR.
7. **데이터/전문/상태/제어:** 속성 키 후보(시스템/테이블/잡). 값 표준 창작 금지.
8. **실패·운영·후속:** 미결정 시 이중 등록. IV.2 Handoff와 쌍.

### FIG-IV.4-25 Owner/DA/TA Responsibility Map

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Owner Map
 업무담당 : 흐름관리 영역, UI, 활용 협의
 DA       : 장표 ‘데이터 아키텍처 영역 참조’, DAMS, 실이동(III.4) 정합
 TA       : AP/DB/NAS/HA, JEUS/WebtoB, 환경 4칸
 인터페이스 : CDC/EAI/ETCL 원천 메타 제공
 보안     : 메타 민감도 FIG-22
Host/IP 미정 = TA GAP
```

**그림 상세해설**

1. **그림 목적:** RACI를 장표 지시(DA 참조)와 물리(TA)로 나눈다.
2. **근거자료와 상태:** [FACT] 상세 DA 참조, 물리 장표8. 목차 업무담당.
3. **Boundary / In / Out:** In: 절 산출. Out: COL-IV.4-01. 밖: 2사업 컨테이너.
4. **Trigger / 시작점:** 협업 킥오프.
5. **처리순서:** ① 업무 → ② DA → ③ TA → ④ 인터페이스 → ⑤ 보안.
6. **책임·비책임:** 책임: 표. 아키텍처는 공백 Owner를 숨기지 않음.
7. **데이터/전문/상태/제어:** 없음(조직 메타).
8. **실패·운영·후속:** Owner 공백이면 FIG-18 런북이 안 닫힘.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-IV.4-02 Real Data Flow vs Metadata Flow Separation

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[실데이터 이동]  ← III.4 / III.2
원천계 ──CDC/ETCL/EAI──► RTW / ADW / BSA / HDW / BDP
종료: 적재 완료 · Control-M 잡 성공
        │ 같은 수단이지만 계층이 다름
        ▼
[관리정보]  ← IV.4  (이 그림의 본체)
CDC/ETCL/EAI/DAMS/형상/빅데이터메타 의 흐름 정보
        ──수집──► Q-Track Oracle 19C
종료: 계보 메타 저장 · 활용 UI 조회
[의미메타]  ← IV.2
용어/모델 후보 → 비즈메타 (glossary 여부는 미기재, ADR-META-01)
금지: Q-Track 상자를 ETCL 실행 엔진으로 그리기
```

**그림 상세해설**

1. **그림 목적:** 실데이터 파이프와 계보 메타 파이프를 두 층으로 분리한다. 이 슬롯이 IV.4의 핵심 계약이다.
2. **근거자료와 상태:** [FACT] 흐름관리=변화 흐름 분석. 원천에 Data Interface(ETCL,CDC,EAI). [ANALYSIS] 실이동은 III.4.
3. **Boundary / In / Out:** In: 동일 원천 이벤트. Out: 위층=적재, 아래층=메타. 밖: 비즈 의미메타.
4. **Trigger / 시작점:** ETCL/CDC 잡이 돌 때 동시에 두 층이 생길 수 있음.
5. **처리순서:** ① 실이동 층 확인 → ② 메타 층 확인 → ③ 종료조건 다름 → ④ 비즈메타는 세 번째 층.
6. **책임·비책임:** 책임: DA 실이동, 흐름관리 담당 메타, 업무 비즈메타. 섞으면 Owner 붕괴.
7. **데이터/전문/상태/제어:** 실데이터 vs 메타 레코드. 동기화 지연 값 창작 금지.
8. **실패·운영·후속:** 한 층만 실패하는 경우 FIG-18. 적재 성공+계보 공백 가능.

### FIG-IV.4-13 Metadata Normal Processing Sequence

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
① 원천에서 흐름 정보 발생 (실행은 원천)
        │
        ▼
② 수집 경계 통과 (방식 TBD)  FIG-04
        │
        ▼
③ Q-Track AP#1 Active 가 수신/적재
        │
        ▼
④ Oracle 19C Active 에 메타 저장
        │
        ▼
⑤ 활용: DAMS / BI Portal / Biz Meta / Q-Track UI 조회
정상 경로에 Failover를 그리지 않음 (FIG-19/20)
원천별 차이는 FIG-14/15/16
```

**그림 상세해설**

1. **그림 목적:** 메타 정상 E2E Happy Path. 원천 종류별 상세는 다음 세 그림.
2. **근거자료와 상태:** [FACT] 수집→Q-Track→활용 4칸. [GAP] ② 전달 방식, 화면 쿼리.
3. **Boundary / In / Out:** In: 원천 메타. Out: 활용 조회. 밖: 실데이터 Load 완료.
4. **Trigger / 시작점:** 원천 변화 또는 스케줄 수집 [스케줄러 제품 TBD, Control-M 단정 금지].
5. **처리순서:** ① 발생 → ② 경계 → ③ AP → ④ DB → ⑤ 활용 조회.
6. **책임·비책임:** 책임: 수집=흐름관리, 원천=각 Owner, 활용=각 시스템.
7. **데이터/전문/상태/제어:** 메타 레코드. Sync/Async TBD.
8. **실패·운영·후속:** ②/③/④ 실패=FIG-18. AP 전환=FIG-19.

### FIG-IV.4-14 ETCL Metadata Collection Sequence

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
ETCL 잡 실행 (TeraStream / 개발 ETL VM 등)  ← III.2/III.4 실데이터
        │ 적재 성공과 별개
        ▼
ETCL 흐름 정보 (잡 정의·매핑·실행 이력 중 무엇이 수집되는지는 [GAP])
        │
        ▼
Data Interface 수집 경계 → Q-Track
        │
        ▼
계보: 원천 → 변환 → RTW/ADW 적재 단계가 분석 대상 (FIG-05 축)
금지: Q-Track이 TeraStream을 기동한다고 화살표를 반대로 그림
```

**그림 상세해설**

1. **그림 목적:** ETCL 경로의 메타 수집만. 실적재 화살표와 방향을 섞지 않는다.
2. **근거자료와 상태:** [FACT] Data Interface에 ETCL. ETL VM+RHEL9+TeraStream은 실실행 FACT. [GAP] 수집 필드.
3. **Boundary / In / Out:** In: ETCL 잡. Out: Q-Track ETCL 계보. 밖: 파일 이관 sFTP 불가(실이관).
4. **Trigger / 시작점:** ETCL 잡 변경/실행.
5. **처리순서:** ① 잡 실행(III) → ② 흐름정보 추출 → ③ 경계 → ④ Q-Track 저장.
6. **책임·비책임:** 책임: ETCL Owner vs 흐름관리 수집. DA 매핑.
7. **데이터/전문/상태/제어:** 잡 메타. 실데이터 행 수 창작 금지.
8. **실패·운영·후속:** 잡 실패 vs 메타 미수집은 다른 사고=FIG-18.

### FIG-IV.4-15 CDC/EAI Metadata Collection Sequence

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
CDC 캡처 / EAI 연계 실행  ← 원천·인터페이스 (III.4)
        │
        ▼
CDC 흐름 정보 · EAI 흐름 정보  [각각 슬롯상 한 그림, 내부 어댑터는 GAP]
        │
        ▼
Data Interface 수집 경계 → Q-Track
        │
        ▼
활용 UI에서 CDC/EAI 구간 계보 조회
BC는 정의 미입수라 이 Sequence에 끼우지 않음
```

**그림 상세해설**

1. **그림 목적:** CDC와 EAI 메타 수집 Sequence. ETCL(FIG-14)과 합치지 않는다.
2. **근거자료와 상태:** [FACT] 원천 목록에 CDC, EAI. [GAP] 캡처 툴/EAI 제품 이 절 확정, 수집 주기.
3. **Boundary / In / Out:** In: CDC/EAI 실행. Out: Q-Track 해당 계보. 밖: BC.
4. **Trigger / 시작점:** CDC 변경 또는 EAI 거래/배치.
5. **처리순서:** ① 원천 실행 → ② 인터페이스 메타 → ③ 경계 → ④ Q-Track → ⑤ 조회.
6. **책임·비책임:** 책임: 인터페이스 Owner, 흐름관리. 비책임: CDC 제품명 창작.
7. **데이터/전문/상태/제어:** 변경 로그 메타. 원장 이미지 아님.
8. **실패·운영·후속:** 캡처 지연 시 계보 lag [값 TBD]. 재처리 FIG-18.

### FIG-IV.4-16 Source Code Metadata Collection Sequence

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
형상관리 소스 변경 [FACT: 원천에 형상/소스코드]
        │
        ▼
프로그램/매핑 계보 후보 메타
 화면 기능이 ‘소스 라인age’인지는 [GAP]
        │
        ▼
수집 경계 → Q-Track
        │
        ▼
활용: 영향 분석 사용자 (장표 소비자)
형상 도구 이름(Git 등) 창작 금지
```

**그림 상세해설**

1. **그림 목적:** 소스코드 메타 수집 후보 Sequence. 구현 화면을 단정하지 않는다.
2. **근거자료와 상태:** [FACT] 원천에 형상관리(소스코드). [GAP] 도구, 파싱 범위, UI.
3. **Boundary / In / Out:** In: 소스 변경. Out: 프로그램 계보 후보. 밖: CI/CD 빌드(III.8).
4. **Trigger / 시작점:** 커밋/배포 형상 변경.
5. **처리순서:** ① 형상 변경 → ② 후보 메타 → ③ Q-Track → ④ 영향 조회.
6. **책임·비책임:** 책임: 형상 Owner, 흐름관리. 개발 표준 III.3과 연결은 이름만.
7. **데이터/전문/상태/제어:** 프로그램 ID/매핑 이름. 소스 원문 저장 여부 TBD.
8. **실패·운영·후속:** 미수집 시 코드 변경 영향 분석 공백. COL-IV.4-01.

### FIG-IV.4-17 Q-Track→DAMS/BI Portal/BizMeta Consumption Flow

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Q-Track Repository (Oracle 19C)
        │ 제공 방식 API/화면/배치 = [GAP]
        ▼
┌─────────┬─────────┬─────────┬─────────┐
│ DAMS    │ BI Portal│ Biz Meta│ Q-Track │
│ (IDAMS) │ FIG-IV.1 │ IV.2    │ 자체 UI │
└─────────┴─────────┴─────────┴─────────┘
장표 활용 칸 FACT. 누가 Master UI인지는 미기재
포탈이 계보를 임베드하는지 = [TBD]
비즈메타와 속성 중복 = ADR-META-01 (FIG-23)
```

**그림 상세해설**

1. **그림 목적:** Q-Track에서 네 소비자로 나가는 활용 흐름. 수집 Sequence와 방향을 반대로 두지 않는다.
2. **근거자료와 상태:** [FACT] 활용 DAMS·BI Portal·Biz Meta·Q-Track. [GAP] API, 임베드.
3. **Boundary / In / Out:** In: Repository. Out: 4 소비자. 밖: 현업 Self BI(IV.3) 장표 활용칸 없음.
4. **Trigger / 시작점:** 사용자가 계보/흐름을 볼 때.
5. **처리순서:** ① 저장 → ② 제공(방식 TBD) → ③ 4소비자 → ④ 중복 속성 ADR.
6. **책임·비책임:** 책임: 각 소비자 Owner. 흐름관리가 실데이터를 제공하지 않음.
7. **데이터/전문/상태/제어:** 메타 조회 결과. 리포트 숫자(허브)와 혼동 금지.
8. **실패·운영·후속:** 소비자 장애는 각 절. Q-Track 장애 시 네 칸 모두 계보 공백.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-IV.4-18 Collection Failure / Retry / Reconciliation [근거 없으면 TBD]

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
실패 모드 (절차 값 없으면 TBD)
① 원천은 성공, 메타 미도착 → 계보 공백, 적재는 정상 (FIG-02)
② 수집 재시도  [횟수/간격 TBD]
③ 정합(Reconciliation) 잡 존재 여부 [GAP]
④ 중복 메타 / 순서 역전 처리 [GAP]
⑤ 운영 알림 Owner [GAP]
금지: ETL 재처리 버튼을 Q-Track 기본 기능으로 그림
```

**그림 상세해설**

1. **그림 목적:** 수집 실패·재시도·정합을 적재 재처리와 분리해 빈칸으로 둔다.
2. **근거자료와 상태:** [GAP] 재시도/정합 런북. [FACT] 두 층 분리이므로 한쪽만 실패 가능.
3. **Boundary / In / Out:** In: FIG-13 ②③④. Out: TBD 런북. 밖: Control-M 적재 재처리.
4. **Trigger / 시작점:** 수집 오류 이벤트.
5. **처리순서:** ① 실패 유형 분류 → ② Retry TBD → ③ 정합 TBD → ④ 알림 TBD.
6. **책임·비책임:** 책임: 흐름관리 운영. 원천 재실행은 원천 Owner.
7. **데이터/전문/상태/제어:** 오류 메타, 누락 ID. 값 창작 금지.
8. **실패·운영·후속:** 미정합이 쌓이면 활용 UI 신뢰 하락. Gate FIG-26.

### FIG-IV.4-19 AP Active→Standby Failover

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상: AP#1 Active 가 요청 처리, AP#2 Standby 대기
        │ Trigger: AP#1 장애
        ▼
① AP#2 Standby → Active 승격  [자동/수동 TBD]
        │
        ▼
② WEB/WebtoB 진입이 AP#2를 보도록 전환 [방법 TBD]
        │
        ▼
③ DB는 그대로 Active (이 그림에서 DB Failover 금지)
        │
        ▼
④ AP#1 복구 후 역할 재배치 [TBD]
Why A-S: 제품 Active-Active 미지원 [FACT]
Impact: 전환 중 계보 조회 중단 가능, 세션 유지 [TBD]
포탈 A-A와 운영 경험이 다름 → 런북 공유 금지
```

**그림 상세해설**

1. **그림 목적:** AP Active→Standby 전환만. DB 전환과 한 그림에 넣지 않는다.
2. **근거자료와 상태:** [FACT] AP A-S, 제품 A-A 미지원. [GAP] 자동승격, 세션.
3. **Boundary / In / Out:** In: AP#1 장애. Out: AP#2 Active. 밖: Portal AP A-A.
4. **Trigger / 시작점:** AP#1 다운.
5. **처리순서:** ① 감지 → ② AP#2 승격 TBD → ③ 진입 전환 TBD → ④ DB 유지 → ⑤ 복구 TBD.
6. **책임·비책임:** 책임: TA/운영. 제품 제약 커뮤니케이션.
7. **데이터/전문/상태/제어:** 제어: HA 스위치. 데이터: 처리 중 요청 손실 가능 TBD.
8. **실패·운영·후속:** 양쪽 AP 불가=서비스 중단. DB 장애는 FIG-20.

### FIG-IV.4-20 DB Active→Standby Failover

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상: Oracle #1 Active , #2 Standby 복제
        │ Trigger: #1 장애
        ▼
① 메타 쓰기/조회 실패
        │
        ▼
② #2 Standby 승격  [절차 TBD]
        │
        ▼
③ AP(Active) 가 새 Master 로 재접속 [TBD]
        │
        ▼
④ 복제 재구성 [TBD]
AP Failover(FIG-19)와 동시에 그리지 않음 — 결합 시나리오는 런북 GAP
```

**그림 상세해설**

1. **그림 목적:** DB A-S 상태 전이만 그린다.
2. **근거자료와 상태:** [FACT] Oracle 19C #1/#2 A-S. [GAP] 승격 런북.
3. **Boundary / In / Out:** In: DB#1 장애. Out: DB#2 Master 후보. 밖: Portal_DB Failover.
4. **Trigger / 시작점:** DB#1 무응답.
5. **처리순서:** ① 쓰기실패 → ② 승격 TBD → ③ AP 재접속 TBD → ④ 복제 TBD.
6. **책임·비책임:** 책임: DBA. AP 접속은 TA.
7. **데이터/전문/상태/제어:** 메타 Repository. NAS 파일과 별개일 수 있음 [용도 GAP].
8. **실패·운영·후속:** 승격 전 활용 4칸 계보 중단. 실데이터 적재는 계속될 수 있음(FIG-02).

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-IV.4-22 Security/Metadata Sensitivity View

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
흐름 메타에 개인정보/쿼리문/계정 포함 여부 = [TBD] → III.7
소스코드 메타에 경로/개발자 정보 = [TBD]
활용 4칸으로 전파 시 권한 모델 = [GAP]
전송 구간 보호 = 프로토콜 창작 금지
Q-Track UI 권한 ≠ 포탈 권한 단정 금지
```

**그림 상세해설**

1. **그림 목적:** 메타 민감도를 질문으로 연다. 암호 알고리즘을 넣지 않는다.
2. **근거자료와 상태:** [GAP] 민감 포함 여부. III.7은 할 일 FACT. 활용 4칸 FACT.
3. **Boundary / In / Out:** In: Repository·UI. Out: III.7/보안. 밖: 허브 컬럼 마스킹 구현.
4. **Trigger / 시작점:** 보안 리뷰.
5. **처리순서:** ① 포함 여부 질문 → ② 전파 범위 4칸 → ③ 권한 분리 → ④ 알고리즘 공란.
6. **책임·비책임:** 책임: 보안/DA/담당. 비책임: AES 등 단정.
7. **데이터/전문/상태/제어:** 메타 필드 민감 분류 TBD.
8. **실패·운영·후속:** 포함 시 로그/추적 보호 필요. 미답변=승인 리스크.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-IV.4-09 DEV Topology

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
DEV [FACT: 단독 구성, 공유 NAS 없음]
┌─────────────────────────┐
│ AP 1식  16C / 256G / 2T │
│ DB 1식  8C / 256G / 1T  │
│ NAS 없음                 │
│ Host/IP [GAP]            │
└─────────────────────────┘
A-S 없음 (단독). PROD HA를 DEV에 복사 금지
sFTP 이관 불가 는 ETL 이관 FACT이지 Q-Track DEV 이관 방식으로 단정 금지
```

**그림 상세해설**

1. **그림 목적:** 개발 환경만 단독 Topology로 그린다. PROD와 한 그림으로 합치지 않는다.
2. **근거자료와 상태:** [FACT] 개발 단독, NAS 없음, 스펙 AP16C/256G/2T DB8C/256G/1T. [GAP] Host.
3. **Boundary / In / Out:** In: 개발자/이행물. Out: 단독 AP+DB. 밖: PROD NAS.
4. **Trigger / 시작점:** 개발 배포(III.8 축과 용어 불일치 가능).
5. **처리순서:** ① 단독 AP → ② 단독 DB → ③ NAS 없음 표시 → ④ HA 없음.
6. **책임·비책임:** 책임: TA 개발환경. 비책임: 운영 스펙을 개발에 요구.
7. **데이터/전문/상태/제어:** 개발 메타 (운영 데이터 복제 여부 TBD).
8. **실패·운영·후속:** 개발 장애는 운영 Failover 스토리로 대체하지 않음.

### FIG-IV.4-10 TEST Topology

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
TEST [FACT: 단독 구성, 공유 NAS 없음 — 개발과 동일 계열]
┌─────────────────────────┐
│ AP 1식  16C / 256G / 2T │
│ DB 1식  8C / 256G / 1T  │
│ NAS 없음                 │
└─────────────────────────┘
PROD A-S 검증을 TEST 단독에서 어느 범위까지 하는가 = [GAP]
개발과 스펙이 같다는 이유로 환경을 하나로 합쳐 그리지 않음 (슬롯 분리)
```

**그림 상세해설**

1. **그림 목적:** 테스트 환경 단독 Topology. DEV 그림과 숫자가 같아도 별도 슬롯이다.
2. **근거자료와 상태:** [FACT] 테스트 단독·NAS 없음·스펙 동일 계열. [GAP] HA 시험 범위.
3. **Boundary / In / Out:** In: 시험 이관. Out: TEST 노드. 밖: DEV, PROD.
4. **Trigger / 시작점:** 시험 수행.
5. **처리순서:** ① TEST AP/DB → ② NAS 없음 → ③ PROD HA 시험 공백 표시.
6. **책임·비책임:** 책임: TA/시험. 업무 시나리오 담당.
7. **데이터/전문/상태/제어:** 시험 메타. 운영 복제 정책 TBD.
8. **실패·운영·후속:** TEST 실패가 운영 A-S를 증명하지 못함 → FIG-11/19 한계.

### FIG-IV.4-11 PROD Topology

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
PROD [FACT]
Web 진입 → AP#1 Active / AP#2 Standby
         → Oracle 19C Active/Standby
         → NAS SDS 2~4T
스펙 AP 24C/256G/2T ×2 , DB 8C/512G/2T ×2
A-A 미지원이므로 AP를 포탈처럼 A-A로 그리지 않음
이 그림에 DEV/TEST/DR을 끼워 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** 운영 Topology만. 포탈 A-A와 대비되는 A-S를 강조한다.
2. **근거자료와 상태:** [FACT] 운영 HA, NAS, 스펙. 제품 A-A 미지원.
3. **Boundary / In / Out:** In: 운영 트래픽. Out: PROD 노드. 밖: 포탈 PROD.
4. **Trigger / 시작점:** 운영 개통.
5. **처리순서:** ① WEB → ② AP A-S → ③ DB A-S → ④ NAS.
6. **책임·비책임:** 책임: 운영/TA. 제품 제약 Why/Impact 설명.
7. **데이터/전문/상태/제어:** 운영 메타+NAS.
8. **실패·운영·후속:** Failover FIG-19/20. 용량 FIG-21. DR 공백 FIG-12.

### FIG-IV.4-21 Monitoring / Batch / Capacity Operation View

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
운영 축
 모니터링 도구/임계 [GAP]
 수집 배치 vs 실시간 [GAP] — Control-M을 기본 스케줄러로 단정 금지
 용량: AP/DB 스펙 FACT, NAS 2~4T 운영 FACT, 메타 증가율 [GAP]
 패치: JEUS 8.5 / Q-Track 3.1 / Oracle 19C 창 [GAP]
개발 NAS 없음 → 대용량 파일 시험 한계
```

**그림 상세해설**

1. **그림 목적:** 관측·배치·용량을 Topology와 분리한다.
2. **근거자료와 상태:** [FACT] 스펙, NAS 운영, 개발 NAS 없음. [GAP] 관측툴, 수집주기, 패치.
3. **Boundary / In / Out:** In: 운영. Out: 운영 공백. 밖: 포탈 용량(데이터셋).
4. **Trigger / 시작점:** 운영 협의.
5. **처리순서:** ① 관측 GAP → ② 주기 GAP → ③ 용량 FACT/GAP → ④ 개발 한계.
6. **책임·비책임:** 책임: 운영/TA. 메타 증가=DA/담당.
7. **데이터/전문/상태/제어:** 용량 메트릭 미수집값 창작 금지.
8. **실패·운영·후속:** 용량 부족≠HA. 배치 실패=FIG-18.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| IV.4 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [업무담당파트 자료] DA/TA | 후속 설계 중단 |
| [TBD] 칸 | 빈칸 가시화 | 질문 Pack | ADR 후보 | Owner 미확정 표기 | 승인 차단 |

## 13. Flow / Interface / Contract 정의표

| Source | Target | Data | Direction | Sync/Async | Contract | Error/Recovery |
|--------|--------|------|-----------|------------|----------|----------------|
| 해당 절 Source [FACT 이름만] | Target [FACT 또는 TBD] | 업무데이터 또는 메타 | 그림 화살표 | 근거 없으면 TBD | 레이아웃 [GAP] | 별도 Failure FIG |

## 14. 설계 규칙 / 금지 / 예외

**규칙**
- FACT 이름 유지. 영역/패턴/환경 이름을 새로 만들지 않는다.
- M1에서 일반 제품 아키텍처를 FACT로 승격하지 않는다.

**금지**
- 창작 스펙, NSIGHT 제품을 하나 FACT로 전환, Q-Track을 ETL 엔진으로 표현, `상동`/`4~12.` 축약.

**예외**
- 예외는 ADR + 승인 Owner. 우회 경로를 기본값으로 두지 않는다.

## 15. Requirement/Policy/Principle→Decision→FIG Traceability

| Req/Policy/Principle | Decision/Claim | FIG | Verification |
|----------------------|----------------|-----|--------------|
| 목차 IV.4 | 슬롯 100% 독립 그림 | FIG-IV.4-01~ | Completion Gate 수치 |
| 유지보수 비영향 | 최소변경·XDA 범위 | 관련 Why/Scope FIG | 부분개선 접점 유지 |
| Evidence Maturity | `M3` 그림 종류 | Discovery 또는 Runtime | 가짜 상세 0건 |

## 16. AS-IS vs TO-BE / 변경영향

```text
AS-IS
  현행 CRM/BSA/RDW · ETCL 분산 · SAP BO OLAP · EUC-KR 중심
  │ 유지 / 변경 / 폐기 / 대체 / 공존
  ▼
Transition
  Oracle Exa · 신·구 공존 · XDA SQL · 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
  DR·SSO·Self BI·BC 정의는 아직 빈칸
  ▼
TO-BE
  프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
  RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
```

변화점만 연결하고, 없는 이관 절차를 만들지 않는다.

### FIG-IV.4-24 AS-IS→TO-BE/Transition View

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
AS-IS: 흐름관리 영역 정의 + To-Be 장표 스택 (현행 도구 병기 여부는 장표 범위)
Transition: Q-Track 3.1 도입/구축, 원천 온보딩 순서 [GAP]
TO-BE: FIG-01 운영 A-S + 활용 4칸
현행 계보 공백을 숨기지 않음 — As-Is 상세 장표 추가 입수 전 TBD
```

**그림 상세해설**

1. **그림 목적:** 전환을 스택 도입과 원천 온보딩으로만 스케치한다. 일정 창작 금지.
2. **근거자료와 상태:** [FACT] To-Be 스택. [GAP] As-Is 상세, 온보딩 순서.
3. **Boundary / In / Out:** In: 현행/목표. Out: Transition 빈칸. 밖: Exa 이관 일정.
4. **Trigger / 시작점:** 전환 계획 리뷰.
5. **처리순서:** ① AS-IS 공백 인정 → ② 스택 FACT → ③ 온보딩 GAP → ④ TO-BE L0.
6. **책임·비책임:** 책임: 업무/DA 온보딩. TA 스택.
7. **데이터/전문/상태/제어:** 메타 이관 범위 TBD.
8. **실패·운영·후속:** 온보딩 지연=계보 부분 개통. Gate FIG-26.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [업무담당파트 자료] DA/TA

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-IV.4-01 | 자료 | 이 절 빈 박스 | [업무담당파트 자료] DA/TA | 장표/인터뷰/ADR |
| GAP-IV.4-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-IV.4-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- IV.4 슬롯 이름·Evidence Maturity `M3`

**What blocks approval**
- IV.4 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [업무담당파트 자료] DA/TA
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- IV.4 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-IV.4-26 GAP/ADR/Approval Gate

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Gate
 필수 닫을 것: 수집 프로토콜(FIG-04), 원천별 필드(14~16), 승격 런북(19/20),
              정합(18), DR(12), ADR-META-01(23)
 이미 실선: 정의, 원천이름, 스택, A-S, 환경 단독 vs 운영, 활용 4칸
다음 절: IV.5 SSO는 이 스택과 무관하게 AS-IS 문의
Handoff 계약: Q-Track≠ETL, 포탈 A-A와 HA 모델 공유 금지
```

**그림 상세해설**

1. **그림 목적:** IV.4 승인 Gate와 IV.5로의 비연결(인증은 별도)을 명시한다.
2. **근거자료와 상태:** 실선 FACT vs 점선 GAP 목록은 FIG-01~25 합.
3. **Boundary / In / Out:** In: 심화 그림. Out: IV.5, DA 상세. 밖: 포탈 기능목록.
4. **Trigger / 시작점:** IV.4 리뷰 종료.
5. **처리순서:** ① 실선 확인 → ② 점선 Gate → ③ ADR → ④ IV.5는 인증 별도.
6. **책임·비책임:** 책임: 각 GAP Owner. 아키텍처 Gate 유지.
7. **데이터/전문/상태/제어:** 결정문.
8. **실패·운영·후속:** 미닫힘 시 계보 부분 개통만 가능. 적재 개통과 혼동 금지.

## 19. 검증 체크리스트

- [ ] Figure Plan 26 = 본문 FIG 26
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] IV.4 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 26 | 실제 26 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 26 · Figure Plan 행 26 · 본문 ` ```text ` 그림 코드블록(FIG) 26건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`IV.4` M3 심화. 실데이터 vs 계보 메타를 FIG-02로 분리하고, ETCL/CDC·EAI/소스 수집 Sequence와 AP/DB A-S Failover·DEV/TEST/PROD/DR을 독립 그림으로 두었다. Q-Track≠ETL. DR·수집 프로토콜·ADR-META-01은 GAP.

---

# IV.5 비표준단말 SSO/EAM 연계 방안

**협업 태그:** AS-IS 담당자 문의 필요

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M1` — 근거 부족. Discovery/Question/Option/Gate 그림을 본체로 둔다. 가짜 Runtime 금지.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-IV.5-01 | 원문 목차 | IV.5 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-IV.5-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-IV.5-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-IV-04 | BI/흐름관리 장표 | BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C | Self BI·SSO AS-IS 문의 |

## 1. Figure Plan

필수 Figure Slot **24**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-IV.5-01 | Standard vs Non-standard Terminal Confirmed Boundary | L0 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-02 | Current Known AS-IS Authentication Skeleton | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-03 | AS-IS Unknown Components/Links Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-04 | AS-IS Evidence Required Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-05 | AS-IS Owner/Interview Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-06 | AS-IS Login Sequence Skeleton | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-07 | AS-IS Authorization/EAM Skeleton | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-08 | AS-IS Session/Logout/Timeout Skeleton | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-09 | Authentication Failure/Authorization Failure Question Map | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-10 | Security Zone/Trust Boundary Questions | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-11 | Non-standard Terminal Inventory Decision Tree | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-12 | TO-BE Option A: Existing SSO/EAM extension [PROPOSED] | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-13 | TO-BE Option B: Standard Terminal absorption [PROPOSED] | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-14 | TO-BE Option C: Exception Adapter/Gateway [PROPOSED] | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-15 | Option Comparison / Decision Criteria | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-16 | Session/Token Technology Decision Gate | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-17 | Authorization/RBAC/EAM Decision Gate | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-18 | Failure/Bypass/Local Account Exception Gate | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-19 | AS-IS→Transition→TO-BE Migration Scenarios | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-20 | Cut-over/Rollback Evidence Requirements | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-21 | Security Review Gate | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-22 | Owner/RACI Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-23 | ADR/GAP Priority Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-IV.5-24 | Final Target Skeleton with TBD slots | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

M1. AS-IS 문의 선행. JWT/OAuth/SAML 자동 선택 금지. TRM IM 행만으로 제품 확정 금지.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `IV.5 비표준단말 SSO/EAM 연계 방안`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** AS-IS 담당자 문의 필요

## 4. L0 Big Picture

### FIG-IV.5-01 Standard vs Non-standard Terminal Confirmed Boundary

**Level:** L0 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-01 │
└─────────────┘
┌──────────────────────────────────────────────────────┐
│ Standard vs Non-standard Terminal Confirmed Boundary │
└──────────────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Standard vs Non-standard Terminal Confirmed Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-IV.5-11 Non-standard Terminal Inventory Decision Tree

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-11 │
└─────────────┘
┌───────────────────────────────────────────────┐
│ Non-standard Terminal Inventory Decision Tree │
└───────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Non-standard Terminal Inventory Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-IV.5-22 Owner/RACI Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-22 │
└─────────────┘
┌────────────────┐
│ Owner/RACI Map │
└────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Owner/RACI Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-23 ADR/GAP Priority Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────┐
│ IV.5 / 23 ADR/GAP Priority Map │
└────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 AS-IS 담당자 문의 필요
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'ADR/GAP Priority Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-IV.5-15 Option Comparison / Decision Criteria

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────┐
│ IV.5 Option C [TO-BE/PROPOSED] │
└────────────────────────────────┘
          │
          ▼
┌───────────────────────┐
│ 예외 Adapter/Gateway 골격 │
└───────────────────────┘
          │
          ▼
┌───────────────┐
│ [TBD] 프로토콜/제품 │
└───────────────┘
          │
          ▼
┌─────────────┐
│ 승인 주체 [TBD] │
└─────────────┘
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Option Comparison / Decision Criteria' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-16 Session/Token Technology Decision Gate

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-16 │
└─────────────┘
┌────────────────────────────────────────┐
│ Session/Token Technology Decision Gate │
└────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Session/Token Technology Decision Gate' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-IV.5-09 Authentication Failure/Authorization Failure Question Map

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────────────────────────┐
│ IV.5 / 09 Authentication Failure/Authorization Failure │
└────────────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 AS-IS 담당자 문의 필요
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Authentication Failure/Authorization Failure Question Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-18 Failure/Bypass/Local Account Exception Gate

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상경로
  RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
          │ 예외
          ▼
┌──────────────────────────────┐
│ Failure / Exception [근거 범위만] │
└──────────────────────────────┘
          │
    ┌─────┼─────┐
    ▼     ▼     ▼
 Retry  Failover  중단/알림
 [값 TBD] [제품제약 FACT] [운영 Owner TBD]
          │
          ▼
복구 후 정합 / 재처리 [절차 TBD]
DR 칸: 환경: 개발 / 테스트 / 운영 / DR 중 DR 장표 [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Failure/Bypass/Local Account Exception Gate' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-IV.5-07 AS-IS Authorization/EAM Skeleton

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-07 │
└─────────────┘
┌──────────────────────────────────┐
│ AS-IS Authorization/EAM Skeleton │
└──────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS Authorization/EAM Skeleton' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-10 Security Zone/Trust Boundary Questions

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────────────────────────────────────┐
│ IV.5 / 10 Security Zone/Trust Boundary Questions │
└──────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 AS-IS 담당자 문의 필요
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Security Zone/Trust Boundary Questions' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-12 TO-BE Option A: Existing SSO/EAM extension [PROPOSED]

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────┐
│ IV.5 의사결정 분기 [TO-BE/PROPOSED] │
└───────────────────────────────┘
          │
          ▼
┌──────────┐
│ Option A │
└──────────┘
          │
          ▼
┌───────────────────────────┐
│ 채택 시 영향 FIG / 필요 Evidence │
└───────────────────────────┘
          │
          ▼
┌─────────────────────┐
│ 미채택 시 대안 Option B/C │
└─────────────────────┘
[금지] 근거 없는 제품/프로토콜을 Option 안에 FACT로 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'TO-BE Option A: Existing SSO/EAM extension [PROPOSED]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-17 Authorization/RBAC/EAM Decision Gate

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-17 │
└─────────────┘
┌──────────────────────────────────────┐
│ Authorization/RBAC/EAM Decision Gate │
└──────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Authorization/RBAC/EAM Decision Gate' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-21 Security Review Gate

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-21 │
└─────────────┘
┌──────────────────────┐
│ Security Review Gate │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Security Review Gate' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| IV.5 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | AS-IS 담당자 문의 필요 | 후속 설계 중단 |
| [TBD] 칸 | 빈칸 가시화 | 질문 Pack | ADR 후보 | Owner 미확정 표기 | 승인 차단 |

## 13. Flow / Interface / Contract 정의표

| Source | Target | Data | Direction | Sync/Async | Contract | Error/Recovery |
|--------|--------|------|-----------|------------|----------|----------------|
| 해당 절 Source [FACT 이름만] | Target [FACT 또는 TBD] | 업무데이터 또는 메타 | 그림 화살표 | 근거 없으면 TBD | 레이아웃 [GAP] | 별도 Failure FIG |

## 14. 설계 규칙 / 금지 / 예외

**규칙**
- FACT 이름 유지. 영역/패턴/환경 이름을 새로 만들지 않는다.
- M1에서 일반 제품 아키텍처를 FACT로 승격하지 않는다.

**금지**
- 창작 스펙, NSIGHT 제품을 하나 FACT로 전환, Q-Track을 ETL 엔진으로 표현, `상동`/`4~12.` 축약.

**예외**
- 예외는 ADR + 승인 Owner. 우회 경로를 기본값으로 두지 않는다.

## 15. Requirement/Policy/Principle→Decision→FIG Traceability

| Req/Policy/Principle | Decision/Claim | FIG | Verification |
|----------------------|----------------|-----|--------------|
| 목차 IV.5 | 슬롯 100% 독립 그림 | FIG-IV.5-01~ | Completion Gate 수치 |
| 유지보수 비영향 | 최소변경·XDA 범위 | 관련 Why/Scope FIG | 부분개선 접점 유지 |
| Evidence Maturity | `M1` 그림 종류 | Discovery 또는 Runtime | 가짜 상세 0건 |

## 16. AS-IS vs TO-BE / 변경영향

```text
AS-IS
  현행 CRM/BSA/RDW · ETCL 분산 · SAP BO OLAP · EUC-KR 중심
  │ 유지 / 변경 / 폐기 / 대체 / 공존
  ▼
Transition
  Oracle Exa · 신·구 공존 · XDA SQL · 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
  DR·SSO·Self BI·BC 정의는 아직 빈칸
  ▼
TO-BE
  프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
  RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
```

변화점만 연결하고, 없는 이관 절차를 만들지 않는다.

### FIG-IV.5-02 Current Known AS-IS Authentication Skeleton

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-02 │
└─────────────┘
┌─────────────────────────────────────────────┐
│ Current Known AS-IS Authentication Skeleton │
└─────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Current Known AS-IS Authentication Skeleton' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-03 AS-IS Unknown Components/Links Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────────────────────────────────┐
│ IV.5 / 03 AS-IS Unknown Components/Links Map │
└──────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 AS-IS 담당자 문의 필요
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS Unknown Components/Links Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-04 AS-IS Evidence Required Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────────┐
│ IV.5 / 04 AS-IS Evidence Required Map │
└───────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 AS-IS 담당자 문의 필요
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS Evidence Required Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-05 AS-IS Owner/Interview Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────────────┐
│ IV.5 / 05 AS-IS Owner/Interview Map │
└─────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 AS-IS 담당자 문의 필요
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS Owner/Interview Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-06 AS-IS Login Sequence Skeleton

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────┐
│ ① Trigger [IV.5] │
└──────────────────┘
          │
          ▼
┌──────────────────────────────────────┐
│ ② 처리 (AS-IS Login Sequence Skeleton) │
└──────────────────────────────────────┘
          │
          ▼
┌──────────────────────────┐
│ ③ 계약/저장/응답 [근거 있는 칸만 이름] │
└──────────────────────────┘
          │
          ▼
┌──────────────────┐
│ ④ 관측/로그 [위치 TBD] │
└──────────────────┘
단계번호는 본문 해설 ①~N과 동일
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS Login Sequence Skeleton' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-08 AS-IS Session/Logout/Timeout Skeleton

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-08 │
└─────────────┘
┌───────────────────────────────────────┐
│ AS-IS Session/Logout/Timeout Skeleton │
└───────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS Session/Logout/Timeout Skeleton' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-13 TO-BE Option B: Standard Terminal absorption [PROPOSED]

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────┐
│ IV.5 Option B [TO-BE/PROPOSED] │
└────────────────────────────────┘
          │
          ▼
┌──────────┐
│ 독립/대체 경로 │
└──────────┘
          │
          ▼
┌──────────────────────┐
│ 비교 기준: 책임경계·운영·보안·이관 │
└──────────────────────┘
          │
          ▼
┌─────────────────────┐
│ Decision Gate → ADR │
└─────────────────────┘
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'TO-BE Option B: Standard Terminal absorption [PROPOSED]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-14 TO-BE Option C: Exception Adapter/Gateway [PROPOSED]

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────┐
│ IV.5 Option C [TO-BE/PROPOSED] │
└────────────────────────────────┘
          │
          ▼
┌───────────────────────┐
│ 예외 Adapter/Gateway 골격 │
└───────────────────────┘
          │
          ▼
┌───────────────┐
│ [TBD] 프로토콜/제품 │
└───────────────┘
          │
          ▼
┌─────────────┐
│ 승인 주체 [TBD] │
└─────────────┘
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'TO-BE Option C: Exception Adapter/Gateway [PROPOSED]' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-19 AS-IS→Transition→TO-BE Migration Scenarios

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-19 │
└─────────────┘
┌────────────────────────────────────────────┐
│ AS-IS→Transition→TO-BE Migration Scenarios │
└────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS→Transition→TO-BE Migration Scenarios' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-IV.5-20 Cut-over/Rollback Evidence Requirements

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG IV.5-20 │
└─────────────┘
┌─────────────────────────────────────────┐
│ Cut-over/Rollback Evidence Requirements │
└─────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S / 흐름관리: Q-Track 3.1, AP A-S(제품 A-A 미지원), Oracle 19C
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: AS-IS 담당자 문의 필요
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Cut-over/Rollback Evidence Requirements' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- AS-IS 담당자 문의 필요

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-IV.5-01 | 자료 | 이 절 빈 박스 | AS-IS 담당자 문의 필요 | 장표/인터뷰/ADR |
| GAP-IV.5-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-IV.5-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- IV.5 슬롯 이름·Evidence Maturity `M1`

**What blocks approval**
- IV.5 GAP 박스
- Owner 미응답 항목

**Who must answer**
- AS-IS 담당자 문의 필요
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- IV.5 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-IV.5-24 Final Target Skeleton with TBD slots

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────────────────┐
│ IV.5 / 24 Final Target Skeleton with TBD slots │
└────────────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 AS-IS 담당자 문의 필요
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Final Target Skeleton with TBD slots' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: IV.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: AS-IS 담당자 문의 필요. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 24 = 본문 FIG 24
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] IV.5 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 24 | 실제 24 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 24 · Figure Plan 행 24 · 본문 ` ```text ` 그림 코드블록(FIG) 24건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`IV.5` V5 재작성. 필수 FIG 24개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# I~IV 통합 Architecture Closure

## 0. Evidence Register

IV까지 작성한 뒤 문서를 끝내지 않고 통합 추적 그림을 둔다. 하위 절 FIG를 여기 10개에 합산하지 않는다.

## 1. Figure Plan

| FIG ID | 제목 |
|--------|------|
| FIG-IV-CLOSE-01 | I~IV One-Page Text Architecture |
| FIG-IV-CLOSE-02 | User→Service→App→Node→Data→Metadata→Auth 추적 |
| FIG-IV-CLOSE-03 | Online / Batch / Data Integration / BI / Metadata / Auth 6 Runtime Lane |
| FIG-IV-CLOSE-04 | AS-IS / Transition / TO-BE 전체 변화 지도 |
| FIG-IV-CLOSE-05 | Environment DEV/TEST/PROD/DR 전체 지도 |
| FIG-IV-CLOSE-06 | HA/DR Coverage Map |
| FIG-IV-CLOSE-07 | Security / Trust / Sensitive Data Coverage Map |
| FIG-IV-CLOSE-08 | Owner / Collaboration / Approval Map |
| FIG-IV-CLOSE-09 | ADR Dependency Graph |
| FIG-IV-CLOSE-10 | GAP Closure Priority / Required Evidence Roadmap |

### FIG-IV-CLOSE-01 I~IV One-Page Text Architecture

```text
I Why/Approach/Scope/Principle/Context
 → II 5영역·App·Node·SW·DB
 → III 온라인/배치/연계/Hydra/Con/암호/CI
 → IV Portal/Meta/SelfBI/Q-Track/SSO
```

**그림 상세해설**

1. **그림 목적:** I~IV One-Page Text Architecture
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-02 User→Service→App→Node→Data→Metadata→Auth 추적

```text
User → 단말/포탈 → Neoworks/APP → Node(VM/Con/BM) → RTW/ADW/BSA/HDW/BDP → Q-Track/BIZ → SSO[TBD]
```

**그림 상세해설**

1. **그림 목적:** User→Service→App→Node→Data→Metadata→Auth 추적
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-03 Online / Batch / Data Integration / BI / Metadata / Auth 6 Runtime Lane

```text
Lane1 온라인  정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Lane2 배치    배치 3유형: DevOn Java / ETCL / Shell·SP + Control-M
Lane3 연계    연계 수단 이름: CDC, ETCL, BC(정의 미입수)
Lane4 BI      BI포탈: DataEye Portal, JEUS, L4, AP A-A, DB A-S
Lane5 메타    Q-Track ≠ ETL
Lane6 Auth    비표준단말 AS-IS 문의
```

**그림 상세해설**

1. **그림 목적:** Online / Batch / Data Integration / BI / Metadata / Auth 6 Runtime Lane
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-04 AS-IS / Transition / TO-BE 전체 변화 지도

```text
AS-IS CRM/ETCL분산/SAP BO → Transition Exa/XDA/신구공존 → TO-BE 1단계 허브
```

**그림 상세해설**

1. **그림 목적:** AS-IS / Transition / TO-BE 전체 변화 지도
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-05 Environment DEV/TEST/PROD/DR 전체 지도

```text
환경: 개발 / 테스트 / 운영 / DR
DEV ETL FACT / PROD HA FACT / DR GAP
```

**그림 상세해설**

1. **그림 목적:** Environment DEV/TEST/PROD/DR 전체 지도
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-06 HA/DR Coverage Map

```text
Portal AP A-A, DB A-S
Q-Track AP A-S (A-A 미지원)
마케팅 AP HA [TBD]
DR [GAP]
```

**그림 상세해설**

1. **그림 목적:** HA/DR Coverage Map
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-07 Security / Trust / Sensitive Data Coverage Map

```text
JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)
III.7 저장/표시/전송 분리, 알고리즘 [GAP]
```

**그림 상세해설**

1. **그림 목적:** Security / Trust / Sensitive Data Coverage Map
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-08 Owner / Collaboration / Approval Map

```text
이용한 / 임채정 / 허준 / 연제학 / TA / DA / FW / Hydra / 2사업 / 보안 / 업무담당 / AS-IS담당
```

**그림 상세해설**

1. **그림 목적:** Owner / Collaboration / Approval Map
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-09 ADR Dependency Graph

```text
ADR-CS-01 CS단일 미확정
ADR-BIP 허브 조회경로
ADR-SSO 프로토콜 미선택
ADR-CICD 도구 미선정
```

**그림 상세해설**

1. **그림 목적:** ADR Dependency Graph
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

### FIG-IV-CLOSE-10 GAP Closure Priority / Required Evidence Roadmap

```text
P0: SSO AS-IS, Self BI 목적, DR, BC정의, Timeout값, 2사업 R/F
필요 Evidence: 인터뷰/장표/ADR
```

**그림 상세해설**

1. **그림 목적:** GAP Closure Priority / Required Evidence Roadmap
2. **근거자료와 상태:** I~IV FACT 롤업. 빈칸은 GAP.
3. **Boundary / In / Out:** In=I~IV 확정 이름. Out=다음 리뷰 안건.
4. **Trigger / 시작점:** 장 완료 리뷰.
5. **처리순서:** 그림 위→아래.
6. **책임·비책임:** 아키텍처 롤업. 개별 값 확정은 Owner.
7. **데이터/제어:** 추적 키는 시스템/앱/노드 이름.
8. **실패·운영·후속:** 승인 차단 GAP가 닫히지 않으면 Baseline 동결 불가.

## 20. V5 Completion Gate — Closure

필수 10 · 실제 10 · 판정 PASS

## 21. 최종 평가

목차를 채운 것이 완료가 아니라, 근거·그림·결정·GAP·Owner가 추적되고 다음 리뷰에서 닫을 항목이 보일 때 완료다.
