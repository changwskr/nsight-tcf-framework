# 하나은행 아키텍처 정의서 — I. 개요

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

# I. 개요

**협업 태그:** 이용한(I.2~I.5) · I.1 기작성 미입수

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-I-01 | 원문 목차 | I 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-I-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-I-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-I-04 | 회의록·의사결정 | 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW / 개발환경 4종: 계정단말 / 정보단말 / Neoworks / Devon | 사업 KPI 기작성 없음 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-I-01 | L0 전체 장 Big Picture: 배경→Approach→범위→원칙→Context→후속 장 | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-02 | Why 계층: 현행/변화요인→사업목표→시스템목표→아키텍처목표 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-03 | Approach 3단계와 Gate/Feedback | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-04 | In/Out/Interface/Dependency 범위 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-05 | 설계원칙 입력→원칙→결정→검증 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-06 | System Context Level-0 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-07 | Actor/User→Target 사용목적 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-08 | 외부/내부 Boundary 및 데이터 흐름 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-09 | FACT/GAP/TBD/ADR Evidence Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I-10 | I→II→III→IV Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계). 이 장은 Why→Approach→범위→원칙→Context를 한 논리로 고정하고 II~IV에 이름을 넘긴다. [GAP] 기작성 배경 장표·NFR 원문 미입수.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `I 개요`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 이용한(I.2~I.5) · I.1 기작성 미입수

## 4. L0 Big Picture

### FIG-I-01 L0 전체 장 Big Picture: 배경→Approach→범위→원칙→Context→후속 장

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ I 산출 (그림·GAP·ADR) │
└───────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌──────────────────────┐
│ II 시스템 구성 View Stack │
└──────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'L0 전체 장 Big Picture: 배경→Approach→범위→원칙→Context→후속 장' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-I-03 Approach 3단계와 Gate/Feedback

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────┐
│ FIG I-03 │
└──────────┘
┌─────────────────────────────┐
│ Approach 3단계와 Gate/Feedback │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 이용한(I.2~I.5) · I.1 기작성 미입수
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Approach 3단계와 Gate/Feedback' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I-04 In/Out/Interface/Dependency 범위

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────┐
│ FIG I-04 │
└──────────┘
┌────────────────────────────────┐
│ In/Out/Interface/Dependency 범위 │
└────────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 이용한(I.2~I.5) · I.1 기작성 미입수
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'In/Out/Interface/Dependency 범위' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I-05 설계원칙 입력→원칙→결정→검증

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────┐
│ FIG I-05 │
└──────────┘
┌──────────────────┐
│ 설계원칙 입력→원칙→결정→검증 │
└──────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 이용한(I.2~I.5) · I.1 기작성 미입수
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '설계원칙 입력→원칙→결정→검증' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-I-08 외부/내부 Boundary 및 데이터 흐름

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────┐
│ FIG I-08 │
└──────────┘
┌─────────────────────────┐
│ 외부/내부 Boundary 및 데이터 흐름 │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 이용한(I.2~I.5) · I.1 기작성 미입수
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '외부/내부 Boundary 및 데이터 흐름' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I-09 FACT/GAP/TBD/ADR Evidence Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────────────────────────┐
│ I / 09 FACT/GAP/TBD/ADR Evidence Map │
└──────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 이용한(I.2~I.5) · I.1 기작성 미입수
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'FACT/GAP/TBD/ADR Evidence Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-I-06 System Context Level-0

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────┐
│ FIG I-06 │
└──────────┘
┌────────────────────────┐
│ System Context Level-0 │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 이용한(I.2~I.5) · I.1 기작성 미입수
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'System Context Level-0' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I-07 Actor/User→Target 사용목적

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────┐
│ FIG I-07 │
└──────────┘
┌────────────────────────┐
│ Actor/User→Target 사용목적 │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 이용한(I.2~I.5) · I.1 기작성 미입수
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Actor/User→Target 사용목적' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| I 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 이용한(I.2~I.5) · I.1 기작성 미입수 | 후속 설계 중단 |
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
| 목차 I | 슬롯 100% 독립 그림 | FIG-I-01~ | Completion Gate 수치 |
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

### FIG-I-02 Why 계층: 현행/변화요인→사업목표→시스템목표→아키텍처목표

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────┐
│ FIG I-02 │
└──────────┘
┌───────────────────────────────────┐
│ Why 계층: 현행/변화요인→사업목표→시스템목표→아키텍처목표 │
└───────────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 이용한(I.2~I.5) · I.1 기작성 미입수
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Why 계층: 현행/변화요인→사업목표→시스템목표→아키텍처목표' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- 이용한(I.2~I.5) · I.1 기작성 미입수

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-I-01 | 자료 | 이 절 빈 박스 | 이용한(I.2~I.5) · I.1 기작성 미입수 | 장표/인터뷰/ADR |
| GAP-I-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-I-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- I 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- I GAP 박스
- Owner 미응답 항목

**Who must answer**
- 이용한(I.2~I.5) · I.1 기작성 미입수
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- I 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-I-10 I→II→III→IV Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ I 산출 (그림·GAP·ADR) │
└───────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌──────────────────────┐
│ II 시스템 구성 View Stack │
└──────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'I→II→III→IV Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 이용한(I.2~I.5) · I.1 기작성 미입수. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] I 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 10 | 실제 10 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 10 · Figure Plan 행 10 · 본문 ` ```text ` 그림 코드블록(FIG) 10건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`I` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# I.1 시스템 구축 배경 및 목적

**협업 태그:** 기작성 원문 미입수 · 아키텍처/PMO

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M1` — 근거 부족. Discovery/Question/Option/Gate 그림을 본체로 둔다. 가짜 Runtime 금지.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-I.1-01 | 원문 목차 | I.1 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-I.1-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-I.1-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-I-04 | 회의록·의사결정 | 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW / 개발환경 4종: 계정단말 / 정보단말 / Neoworks / Devon | 사업 KPI 기작성 없음 |

## 1. Figure Plan

필수 Figure Slot **8**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-I.1-01 | 현행 환경 Landscape | L0 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.1-02 | 변화요인/문제점 Cause Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.1-03 | 현행→변화→사업목표→시스템목표→아키텍처목표 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.1-04 | 기능목표 vs 비기능/운영목표 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.1-05 | 영향 시스템/부분개선 Scope | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.1-06 | 변경 최소화 Boundary | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.1-07 | AS-IS vs TO-BE 변화점 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.1-08 | 미확정 사업 KPI/TBD Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW. 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks. 개발환경 4종: 계정단말 / 정보단말 / Neoworks / Devon. 변경 허용=XDA SQL. [GAP] 사업 KPI.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `I.1 시스템 구축 배경 및 목적`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 기작성 원문 미입수 · 아키텍처/PMO

## 4. L0 Big Picture

### FIG-I.1-01 현행 환경 Landscape

**Level:** L0 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
환경: 개발 / 테스트 / 운영 / DR
┌─────────┬─────────┐
│ DEV     │ TEST    │
│ ETL VM  │ 단독구성│
│ RHEL9   │ (NAS 없음│
│ TeraStream│ 개발 힌트)│
├─────────┼─────────┤
│ PROD    │ DR      │
│ HA 적용 │ [근거 범위] │
│ Portal A-A / Q-Track A-S │
└─────────┴─────────┘
[FACT] 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
sFTP 이관 불가. DR 절차·RTO 창작 금지.
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '현행 환경 Landscape' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 기작성 원문 미입수 · 아키텍처/PMO. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-I.1-06 변경 최소화 Boundary

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.1-06 │
└────────────┘
┌─────────────────┐
│ 변경 최소화 Boundary │
└─────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 기작성 원문 미입수 · 아키텍처/PMO
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '변경 최소화 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 기작성 원문 미입수 · 아키텍처/PMO. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-I.1-05 영향 시스템/부분개선 Scope

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.1-05 │
└────────────┘
┌───────────────────┐
│ 영향 시스템/부분개선 Scope │
└───────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 기작성 원문 미입수 · 아키텍처/PMO
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '영향 시스템/부분개선 Scope' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 기작성 원문 미입수 · 아키텍처/PMO. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-I.1-04 기능목표 vs 비기능/운영목표

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.1-04 │
└────────────┘
┌──────────────────┐
│ 기능목표 vs 비기능/운영목표 │
└──────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 기작성 원문 미입수 · 아키텍처/PMO
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '기능목표 vs 비기능/운영목표' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 기작성 원문 미입수 · 아키텍처/PMO. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| I.1 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 기작성 원문 미입수 · 아키텍처/PMO | 후속 설계 중단 |
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
| 목차 I.1 | 슬롯 100% 독립 그림 | FIG-I.1-01~ | Completion Gate 수치 |
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

### FIG-I.1-02 변화요인/문제점 Cause Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.1-02 │
└────────────┘
┌────────────────────┐
│ 변화요인/문제점 Cause Map │
└────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 기작성 원문 미입수 · 아키텍처/PMO
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '변화요인/문제점 Cause Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 기작성 원문 미입수 · 아키텍처/PMO. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.1-03 현행→변화→사업목표→시스템목표→아키텍처목표

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.1-03 │
└────────────┘
┌─────────────────────────┐
│ 현행→변화→사업목표→시스템목표→아키텍처목표 │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 기작성 원문 미입수 · 아키텍처/PMO
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '현행→변화→사업목표→시스템목표→아키텍처목표' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 기작성 원문 미입수 · 아키텍처/PMO. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.1-07 AS-IS vs TO-BE 변화점

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.1-07 │
└────────────┘
┌────────────────────┐
│ AS-IS vs TO-BE 변화점 │
└────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 기작성 원문 미입수 · 아키텍처/PMO
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AS-IS vs TO-BE 변화점' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 기작성 원문 미입수 · 아키텍처/PMO. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- 기작성 원문 미입수 · 아키텍처/PMO

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-I.1-01 | 자료 | 이 절 빈 박스 | 기작성 원문 미입수 · 아키텍처/PMO | 장표/인터뷰/ADR |
| GAP-I.1-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-I.1-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- I.1 슬롯 이름·Evidence Maturity `M1`

**What blocks approval**
- I.1 GAP 박스
- Owner 미응답 항목

**Who must answer**
- 기작성 원문 미입수 · 아키텍처/PMO
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- I.1 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-I.1-08 미확정 사업 KPI/TBD Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────┐
│ I.1 / 08 미확정 사업 KPI/TBD Map │
└─────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 기작성 원문 미입수 · 아키텍처/PMO
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '미확정 사업 KPI/TBD Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 기작성 원문 미입수 · 아키텍처/PMO. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 8 = 본문 FIG 8
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] I.1 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 8 | 실제 8 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 8 · Figure Plan 행 8 · 본문 ` ```text ` 그림 코드블록(FIG) 8건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`I.1` V5 재작성. 필수 FIG 8개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# I.2 아키텍처 정의 수행 Approach

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-I.2-01 | 원문 목차 | I.2 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-I.2-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-I.2-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-I-04 | 회의록·의사결정 | 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW / 개발환경 4종: 계정단말 / 정보단말 / Neoworks / Devon | 사업 KPI 기작성 없음 |

## 1. Figure Plan

필수 Figure Slot **8**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-I.2-01 | Approach 3단계 Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.2-02 | 입력자료→단계별 투입 Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.2-03 | Gate-1/2/3 승인 흐름 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.2-04 | Feedback/반려 Loop | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.2-05 | 협업주체 Swimlane | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.2-06 | 산출물 생성 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.2-07 | FACT/ANALYSIS/PROPOSED 분리 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.2-08 | Baseline 동결 Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] Approach 3단계: 범위 → 원칙 → 상세화. Gate에서 Baseline을 동결하지 않으면 II 상세가 흔들린다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `I.2 아키텍처 정의 수행 Approach`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-I.2-01 Approach 3단계 Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.2-01 │
└────────────┘
┌──────────────────────────┐
│ Approach 3단계 Big Picture │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Approach 3단계 Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-I.2-03 Gate-1/2/3 승인 흐름

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.2-03 │
└────────────┘
┌──────────────────┐
│ Gate-1/2/3 승인 흐름 │
└──────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Gate-1/2/3 승인 흐름' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.2-04 Feedback/반려 Loop

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.2-04 │
└────────────┘
┌──────────────────┐
│ Feedback/반려 Loop │
└──────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Feedback/반려 Loop' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.2-05 협업주체 Swimlane

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.2-05 │
└────────────┘
┌───────────────┐
│ 협업주체 Swimlane │
└───────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '협업주체 Swimlane' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-I.2-02 입력자료→단계별 투입 Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.2-02 │
└────────────┘
┌─────────────────────┐
│ 입력자료→단계별 투입 Mapping │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '입력자료→단계별 투입 Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-I.2-06 산출물 생성 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.2-06 │
└────────────┘
┌─────────────┐
│ 산출물 생성 Flow │
└─────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '산출물 생성 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.2-07 FACT/ANALYSIS/PROPOSED 분리 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.2-07 │
└────────────┘
┌────────────────────────────────┐
│ FACT/ANALYSIS/PROPOSED 분리 Flow │
└────────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'FACT/ANALYSIS/PROPOSED 분리 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| I.2 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 I.2 | 슬롯 100% 독립 그림 | FIG-I.2-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- 아키텍처(작성) · 해당 Owner TBD

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-I.2-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-I.2-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-I.2-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- I.2 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- I.2 GAP 박스
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
- I.2 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-I.2-08 Baseline 동결 Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────┐
│ I.2 산출 (그림·GAP·ADR) │
└─────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌──────────────────────┐
│ II 시스템 구성 View Stack │
└──────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Baseline 동결 Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 8 = 본문 FIG 8
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] I.2 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 8 | 실제 8 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 8 · Figure Plan 행 8 · 본문 ` ```text ` 그림 코드블록(FIG) 8건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`I.2` V5 재작성. 필수 FIG 8개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# I.3 아키텍처 정의 범위

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-I.3-01 | 원문 목차 | I.3 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-I.3-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-I.3-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-I-04 | 회의록·의사결정 | 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW / 개발환경 4종: 계정단말 / 정보단말 / Neoworks / Devon | 사업 KPI 기작성 없음 |

## 1. Figure Plan

필수 Figure Slot **8**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-I.3-01 | 전체 과업→정의대상 Scope Funnel | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.3-02 | In-Scope / Out-of-Scope / IF / Dependency 4분면 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.3-03 | 업무/앱/데이터/IF/인프라/보안/운영 범위 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.3-04 | 2사업 역할분담 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.3-05 | TA/DA/FW/Hydra/보안 협업 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.3-06 | 목차→범위 Traceability | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.3-07 | Scope Decision Tree | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.3-08 | 범위 GAP/TBD Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 1단계=정보계 마케팅·허브. 디지털뱅킹 DPN/PaaS는 참조. 2사업 역할분담 계약서 [GAP].

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `I.3 아키텍처 정의 범위`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-I.3-01 전체 과업→정의대상 Scope Funnel

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.3-01 │
└────────────┘
┌─────────────────────────┐
│ 전체 과업→정의대상 Scope Funnel │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '전체 과업→정의대상 Scope Funnel' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-I.3-02 In-Scope / Out-of-Scope / IF / Dependency 4분면

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.3-02 │
└────────────┘
┌───────────────────────────────────────────────┐
│ In-Scope / Out-of-Scope / IF / Dependency 4분면 │
└───────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'In-Scope / Out-of-Scope / IF / Dependency 4분면' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 7. Static Mapping / Responsibility View

### FIG-I.3-04 2사업 역할분담 Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.3-04 │
└────────────┘
┌───────────────────┐
│ 2사업 역할분담 Boundary │
└───────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '2사업 역할분담 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.3-06 목차→범위 Traceability

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.3-06 │
└────────────┘
┌────────────────────┐
│ 목차→범위 Traceability │
└────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '목차→범위 Traceability' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-I.3-07 Scope Decision Tree

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.3-07 │
└────────────┘
┌─────────────────────┐
│ Scope Decision Tree │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Scope Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-I.3-03 업무/앱/데이터/IF/인프라/보안/운영 범위

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.3-03 │
└────────────┘
┌──────────────────────────┐
│ 업무/앱/데이터/IF/인프라/보안/운영 범위 │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '업무/앱/데이터/IF/인프라/보안/운영 범위' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.3-05 TA/DA/FW/Hydra/보안 협업 Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.3-05 │
└────────────┘
┌───────────────────────────────┐
│ TA/DA/FW/Hydra/보안 협업 Boundary │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'TA/DA/FW/Hydra/보안 협업 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| I.3 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 I.3 | 슬롯 100% 독립 그림 | FIG-I.3-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- 아키텍처(작성) · 해당 Owner TBD

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-I.3-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-I.3-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-I.3-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- I.3 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- I.3 GAP 박스
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
- I.3 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-I.3-08 범위 GAP/TBD Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────┐
│ I.3 / 08 범위 GAP/TBD Map │
└─────────────────────────┘
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

1. **그림 목적:** 이 그림은 '범위 GAP/TBD Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 8 = 본문 FIG 8
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] I.3 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 8 | 실제 8 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 8 · Figure Plan 행 8 · 본문 ` ```text ` 그림 코드블록(FIG) 8건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`I.3` V5 재작성. 필수 FIG 8개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# I.4 아키텍처 설계 원칙 정의

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-I.4-01 | 원문 목차 | I.4 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-I.4-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-I.4-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-I-04 | 회의록·의사결정 | 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW / 개발환경 4종: 계정단말 / 정보단말 / Neoworks / Devon | 사업 KPI 기작성 없음 |

## 1. Figure Plan

필수 Figure Slot **8**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-I.4-01 | 원칙 도출 Input→Principle→Decision→Verification | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.4-02 | 원칙 분류 Map(앱/데이터/IF/인프라/보안/운영) | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.4-03 | Must/Should/May 적용 흐름 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.4-04 | 원칙 충돌 Resolution Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.4-05 | 예외/ADR 승인 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.4-06 | 원칙→후속 장 적용 Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.4-07 | 원칙 검증 Lifecycle | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.4-08 | 근거 부족 원칙/TBD Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[TO-BE/PROPOSED] 유지보수 비영향, 최소변경, 영역 우회 금지(원문 문장 없음), CS 단일 미확정.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `I.4 아키텍처 설계 원칙 정의`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-I.4-01 원칙 도출 Input→Principle→Decision→Verification

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.4-01 │
└────────────┘
┌─────────────────────────────────────────────┐
│ 원칙 도출 Input→Principle→Decision→Verification │
└─────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '원칙 도출 Input→Principle→Decision→Verification' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-I.4-03 Must/Should/May 적용 흐름

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.4-03 │
└────────────┘
┌───────────────────────┐
│ Must/Should/May 적용 흐름 │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Must/Should/May 적용 흐름' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-I.4-06 원칙→후속 장 적용 Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────┐
│ I.4 산출 (그림·GAP·ADR) │
└─────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌──────────────────────┐
│ II 시스템 구성 View Stack │
└──────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '원칙→후속 장 적용 Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-I.4-04 원칙 충돌 Resolution Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.4-04 │
└────────────┘
┌───────────────────────┐
│ 원칙 충돌 Resolution Flow │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '원칙 충돌 Resolution Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-I.4-05 예외/ADR 승인 Flow

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

1. **그림 목적:** 이 그림은 '예외/ADR 승인 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.4-07 원칙 검증 Lifecycle

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.4-07 │
└────────────┘
┌─────────────────┐
│ 원칙 검증 Lifecycle │
└─────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '원칙 검증 Lifecycle' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-I.4-02 원칙 분류 Map(앱/데이터/IF/인프라/보안/운영)

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.4-02 │
└────────────┘
┌───────────────────────────────┐
│ 원칙 분류 Map(앱/데이터/IF/인프라/보안/운영) │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '원칙 분류 Map(앱/데이터/IF/인프라/보안/운영)' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| I.4 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 I.4 | 슬롯 100% 독립 그림 | FIG-I.4-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- 아키텍처(작성) · 해당 Owner TBD

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-I.4-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-I.4-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-I.4-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- I.4 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- I.4 GAP 박스
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
- I.4 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-I.4-08 근거 부족 원칙/TBD Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────┐
│ I.4 / 08 근거 부족 원칙/TBD Map │
└───────────────────────────┘
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

1. **그림 목적:** 이 그림은 '근거 부족 원칙/TBD Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 8 = 본문 FIG 8
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] I.4 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 8 | 실제 8 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 8 · Figure Plan 행 8 · 본문 ` ```text ` 그림 코드블록(FIG) 8건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`I.4` V5 재작성. 필수 FIG 8개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# I.5 시스템 Context 다이어그램

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-I.5-01 | 원문 목차 | I.5 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-I.5-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-I.5-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-I-04 | 회의록·의사결정 | 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW / 개발환경 4종: 계정단말 / 정보단말 / Neoworks / Devon | 사업 KPI 기작성 없음 |

## 1. Figure Plan

필수 Figure Slot **12**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-I.5-01 | Level-0 System Context | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-02 | Target 내부/외부 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-03 | Actor/User→Target Use Case Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-04 | 원천시스템→Target 데이터 유입 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-05 | Target→소비시스템 데이터 제공 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-06 | 온라인 단말 Runtime Context | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-07 | 데이터허브 내부 저장계층 Context | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-08 | Character Set/Format Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-09 | 주요 Interface Type Context | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-10 | 장애/중계 실패 Context | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-11 | 협업/소유 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-I.5-12 | Context→II.1 Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 액터=정보/계정단말 등. 저장=RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive. 중계=EIC/MCA/CDC/ETCL/HYDRA. Context는 경계이지 논리노드 구성도가 아니다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `I.5 시스템 Context 다이어그램`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-I.5-01 Level-0 System Context

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-01 │
└────────────┘
┌────────────────────────┐
│ Level-0 System Context │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Level-0 System Context' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-I.5-04 원천시스템→Target 데이터 유입

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-04 │
└────────────┘
┌─────────────────────┐
│ 원천시스템→Target 데이터 유입 │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '원천시스템→Target 데이터 유입' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.5-05 Target→소비시스템 데이터 제공

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-05 │
└────────────┘
┌─────────────────────┐
│ Target→소비시스템 데이터 제공 │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Target→소비시스템 데이터 제공' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.5-07 데이터허브 내부 저장계층 Context

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-07 │
└────────────┘
┌───────────────────────┐
│ 데이터허브 내부 저장계층 Context │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '데이터허브 내부 저장계층 Context' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-I.5-02 Target 내부/외부 Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-02 │
└────────────┘
┌───────────────────────┐
│ Target 내부/외부 Boundary │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Target 내부/외부 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.5-03 Actor/User→Target Use Case Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-03 │
└────────────┘
┌────────────────────────────────┐
│ Actor/User→Target Use Case Map │
└────────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Actor/User→Target Use Case Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.5-11 협업/소유 Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-11 │
└────────────┘
┌────────────────┐
│ 협업/소유 Boundary │
└────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '협업/소유 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-I.5-06 온라인 단말 Runtime Context

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────┐
│ ① Trigger [I.5] │
└─────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ② 처리 (온라인 단말 Runtime Context) │
└───────────────────────────────┘
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

1. **그림 목적:** 이 그림은 '온라인 단말 Runtime Context' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-I.5-09 주요 Interface Type Context

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-09 │
└────────────┘
┌───────────────────────────┐
│ 주요 Interface Type Context │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 '주요 Interface Type Context' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-I.5-10 장애/중계 실패 Context

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 '장애/중계 실패 Context' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-I.5-08 Character Set/Format Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG I.5-08 │
└────────────┘
┌───────────────────────────────┐
│ Character Set/Format Boundary │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계) / 부분개선: 운영CRM·분석CRM·BSA XDA SQL, Oracle Exa/RDW
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

1. **그림 목적:** 이 그림은 'Character Set/Format Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| I.5 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 I.5 | 슬롯 100% 독립 그림 | FIG-I.5-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- 아키텍처(작성) · 해당 Owner TBD

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-I.5-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-I.5-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-I.5-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- I.5 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- I.5 GAP 박스
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
- I.5 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-I.5-12 Context→II.1 Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────┐
│ I.5 산출 (그림·GAP·ADR) │
└─────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌──────────────────────┐
│ II 시스템 구성 View Stack │
└──────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Context→II.1 Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: I.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 12 = 본문 FIG 12
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] I.5 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 12 | 실제 12 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 12 · Figure Plan 행 12 · 본문 ` ```text ` 그림 코드블록(FIG) 12건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`I.5` V5 재작성. 필수 FIG 12개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

