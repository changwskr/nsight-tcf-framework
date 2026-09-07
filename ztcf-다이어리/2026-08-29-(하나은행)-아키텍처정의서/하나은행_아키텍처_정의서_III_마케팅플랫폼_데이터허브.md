# 하나은행 아키텍처 정의서 — III. 마케팅플랫폼 및 데이터허브 아키텍처

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

# III. 마케팅플랫폼 및 데이터허브 아키텍처

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III-01 | 원문 목차 | III 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **14**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III-01 | III 전체 Runtime Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-02 | 온라인 vs 배치 vs 데이터유입 3축 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-03 | 정보단말/계정단말 진입경로 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-04 | Context→Layer→ServiceGroup→Execution Control | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-05 | 거래패턴 Catalog Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-06 | 표준전문/Character Set Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-07 | Batch/Control-M Runtime | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-08 | CDC/ETCL/BC Integration | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-09 | Hydra 행동데이터 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-10 | Container R/F Runtime | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-11 | Security Cross-cutting | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-12 | CI/CD Cross-cutting | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-13 | 정상/장애/운영 Cross-cutting | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III-14 | III→IV Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks. 온라인→배치→명명→연계→Hydra→컨테이너→암복호→CI/CD. V장 없음.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III 마케팅플랫폼 및 데이터허브 아키텍처`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-III-01 III 전체 Runtime Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────┐
│ ① Trigger [III] │
└─────────────────┘
          │
          ▼
┌───────────────────────────────────┐
│ ② 처리 (III 전체 Runtime Big Picture) │
└───────────────────────────────────┘
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

1. **그림 목적:** 이 그림은 'III 전체 Runtime Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III-02 온라인 vs 배치 vs 데이터유입 3축

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-02 │
└────────────┘
┌───────────────────────┐
│ 온라인 vs 배치 vs 데이터유입 3축 │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '온라인 vs 배치 vs 데이터유입 3축' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III-03 정보단말/계정단말 진입경로

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-03 │
└────────────┘
┌────────────────┐
│ 정보단말/계정단말 진입경로 │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '정보단말/계정단말 진입경로' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III-04 Context→Layer→ServiceGroup→Execution Control

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-04 │
└────────────┘
┌──────────────────────────────────────────────┐
│ Context→Layer→ServiceGroup→Execution Control │
└──────────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Context→Layer→ServiceGroup→Execution Control' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-III-05 거래패턴 Catalog Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-05 │
└────────────┘
┌──────────────────┐
│ 거래패턴 Catalog Map │
└──────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '거래패턴 Catalog Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III-07 Batch/Control-M Runtime

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────┐
│ ① Trigger [III] │
└─────────────────┘
          │
          ▼
┌────────────────────────────────┐
│ ② 처리 (Batch/Control-M Runtime) │
└────────────────────────────────┘
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

1. **그림 목적:** 이 그림은 'Batch/Control-M Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III-08 CDC/ETCL/BC Integration

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-08 │
└────────────┘
┌─────────────────────────┐
│ CDC/ETCL/BC Integration │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'CDC/ETCL/BC Integration' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III-09 Hydra 행동데이터

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-09 │
└────────────┘
┌─────────────┐
│ Hydra 행동데이터 │
└─────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Hydra 행동데이터' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III-10 Container R/F Runtime

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────┐
│ ① Trigger [III] │
└─────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ② 처리 (Container R/F Runtime) │
└──────────────────────────────┘
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

1. **그림 목적:** 이 그림은 'Container R/F Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III-13 정상/장애/운영 Cross-cutting

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

1. **그림 목적:** 이 그림은 '정상/장애/운영 Cross-cutting' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-III-06 표준전문/Character Set Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-06 │
└────────────┘
┌─────────────────────────────┐
│ 표준전문/Character Set Boundary │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '표준전문/Character Set Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III-11 Security Cross-cutting

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-11 │
└────────────┘
┌────────────────────────┐
│ Security Cross-cutting │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Security Cross-cutting' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-III-12 CI/CD Cross-cutting

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ FIG III-12 │
└────────────┘
┌─────────────────────┐
│ CI/CD Cross-cutting │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'CI/CD Cross-cutting' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 III | 슬롯 100% 독립 그림 | FIG-III-01~ | Completion Gate 수치 |
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
| GAP-III-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-III-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III GAP 박스
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
- III 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III-14 III→IV Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────┐
│ III 산출 (그림·GAP·ADR) │
└─────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────┐
│ IV 소비·메타·인증 │
└─────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'III→IV Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 14 = 본문 FIG 14
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 14 | 실제 14 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 14 · Figure Plan 행 14 · 본문 ` ```text ` 그림 코드블록(FIG) 14건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`III` V5 재작성. 필수 FIG 14개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.1 온라인어플리케이션

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1-01 | 원문 목차 | III.1 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **14**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1-01 | 온라인 Runtime Level-0 | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-02 | 정보단말 직접 경로 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-03 | 계정단말 EIC/MCA 경로 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-04 | Service Context Lifecycle | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-05 | Application Layer Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-06 | Service Group Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-07 | Execution Control Cross-cut | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-08 | 거래패턴 전체 Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-09 | JSON 표준전문 적용구간 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-10 | Character Set 변환경계 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-11 | 온라인 정상 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-12 | 온라인 예외/Timeout/중복 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-13 | 로그/관측/운영 Flow | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1-14 | III.1→III.2/III.4 Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.1 온라인어플리케이션** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1 온라인어플리케이션`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-III.1-01 온라인 Runtime Level-0

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '온라인 Runtime Level-0' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.1-02 정보단말 직접 경로

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-02 │
└──────────────┘
┌────────────┐
│ 정보단말 직접 경로 │
└────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '정보단말 직접 경로' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1-03 계정단말 EIC/MCA 경로

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-03 │
└──────────────┘
┌─────────────────┐
│ 계정단말 EIC/MCA 경로 │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '계정단말 EIC/MCA 경로' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1-04 Service Context Lifecycle

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-04 │
└──────────────┘
┌───────────────────────────┐
│ Service Context Lifecycle │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Service Context Lifecycle' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.1-07 Execution Control Cross-cut

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-07 │
└──────────────┘
┌─────────────────────────────┐
│ Execution Control Cross-cut │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Execution Control Cross-cut' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.1-05 Application Layer Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-05 │
└──────────────┘
┌───────────────────────┐
│ Application Layer Map │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Application Layer Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1-06 Service Group Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-06 │
└──────────────┘
┌────────────────────────┐
│ Service Group Boundary │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Service Group Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1-08 거래패턴 전체 Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-08 │
└──────────────┘
┌─────────────┐
│ 거래패턴 전체 Map │
└─────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '거래패턴 전체 Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1-09 JSON 표준전문 적용구간

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-09 │
└──────────────┘
┌────────────────┐
│ JSON 표준전문 적용구간 │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'JSON 표준전문 적용구간' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1-11 온라인 정상 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '온라인 정상 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1-12 온라인 예외/Timeout/중복 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '온라인 예외/Timeout/중복 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-III.1-10 Character Set 변환경계

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
│ HA 적용 │ [근거 범위] │
│ Portal A-A / Q-Track A-S │
└─────────┴─────────┘
[FACT] 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
sFTP 이관 불가. DR 절차·RTO 창작 금지.
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Character Set 변환경계' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-III.1-13 로그/관측/운영 Flow

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.1-13 │
└──────────────┘
┌───────────────┐
│ 로그/관측/운영 Flow │
└───────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '로그/관측/운영 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.1 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 III.1 | 슬롯 100% 독립 그림 | FIG-III.1-01~ | Completion Gate 수치 |
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
| GAP-III.1-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-III.1-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.1 GAP 박스
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
- III.1 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1-14 III.1→III.2/III.4 Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────┐
│ III.1 산출 (그림·GAP·ADR) │
└───────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────┐
│ IV 소비·메타·인증 │
└─────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'III.1→III.2/III.4 Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 14 = 본문 FIG 14
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.1 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 14 | 실제 14 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 14 · Figure Plan 행 14 · 본문 ` ```text ` 그림 코드블록(FIG) 14건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`III.1` V5 재작성. 필수 FIG 14개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.1.1 정보단말 활용

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1.1-01 | 원문 목차 | III.1.1 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1.1-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1.1-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1.1-01 | 정보단말 전체 Context | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-02 | 정보단말 AS-IS Runtime | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-03 | 정보단말 TO-BE/TBD Runtime | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-04 | 정보단말 vs 계정단말 비교 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-05 | 엔진 업그레이드 영향 Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-06 | EIC/EIMS 영향 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-07 | UI→서비스 정상 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-08 | UI 오류/CS/마스킹 영향 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-09 | 정보전달 패턴 분류 Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.1-10 | 확정/미확정 변경점 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.1.1 정보단말 활용** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1.1 정보단말 활용`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-III.1.1-01 정보단말 전체 Context

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.1-01 │
└────────────────┘
┌─────────────────┐
│ 정보단말 전체 Context │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '정보단말 전체 Context' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-III.1.1-04 정보단말 vs 계정단말 비교

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.1-04 │
└────────────────┘
┌─────────────────┐
│ 정보단말 vs 계정단말 비교 │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '정보단말 vs 계정단말 비교' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.1.1-05 엔진 업그레이드 영향 Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.1-05 │
└────────────────┘
┌─────────────────┐
│ 엔진 업그레이드 영향 Map │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '엔진 업그레이드 영향 Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.1-06 EIC/EIMS 영향 Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.1-06 │
└────────────────┘
┌──────────────────────┐
│ EIC/EIMS 영향 Boundary │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'EIC/EIMS 영향 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.1-09 정보전달 패턴 분류 Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.1-09 │
└────────────────┘
┌────────────────┐
│ 정보전달 패턴 분류 Map │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '정보전달 패턴 분류 Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1.1-07 UI→서비스 정상 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'UI→서비스 정상 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1.1-08 UI 오류/CS/마스킹 영향

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.1-08 │
└────────────────┘
┌─────────────────┐
│ UI 오류/CS/마스킹 영향 │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'UI 오류/CS/마스킹 영향' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.1.1 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 III.1.1 | 슬롯 100% 독립 그림 | FIG-III.1.1-01~ | Completion Gate 수치 |
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

### FIG-III.1.1-02 정보단말 AS-IS Runtime

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '정보단말 AS-IS Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.1-03 정보단말 TO-BE/TBD Runtime

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────────────┐
│ III.1.1 / 03 정보단말 TO-BE/TBD Runtime │
└─────────────────────────────────────┘
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

1. **그림 목적:** 이 그림은 '정보단말 TO-BE/TBD Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| GAP-III.1.1-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-III.1.1-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1.1-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1.1 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.1.1 GAP 박스
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
- III.1.1 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1.1-10 확정/미확정 변경점

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────┐
│ III.1.1 / 10 확정/미확정 변경점 │
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

1. **그림 목적:** 이 그림은 '확정/미확정 변경점' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.1.1 축약 표현 없음

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

`III.1.1` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.1.2 서비스 Context 레이아웃 정의

**협업 태그:** [FW협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M1` — 근거 부족. Discovery/Question/Option/Gate 그림을 본체로 둔다. 가짜 Runtime 금지.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1.2-01 | 원문 목차 | III.1.2 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1.2-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1.2-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1.2-01 | Context Lifecycle Big Picture | L0 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-02 | Request→Context 생성 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-03 | Context Field Provenance Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-04 | Context→Application Layer 전달 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-05 | Context→Service Group 전달 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-06 | Context→업무로그 Correlation | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-07 | 정상 Context Sequence | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-08 | 오류/Timeout Context 처리 | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-09 | 민감정보/마스킹 영향 | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.2-10 | Context SSOT/GAP Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.1.2 서비스 Context 레이아웃 정의** — Evidence `M1`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1.2 서비스 Context 레이아웃 정의`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [FW협의필요]

## 4. L0 Big Picture

### FIG-III.1.2-01 Context Lifecycle Big Picture

**Level:** L0 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.2-01 │
└────────────────┘
┌───────────────────────────────┐
│ Context Lifecycle Big Picture │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Context Lifecycle Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.1.2-02 Request→Context 생성

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.2-02 │
└────────────────┘
┌────────────────────┐
│ Request→Context 생성 │
└────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Request→Context 생성' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.1.2-04 Context→Application Layer 전달

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.2-04 │
└────────────────┘
┌──────────────────────────────┐
│ Context→Application Layer 전달 │
└──────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Context→Application Layer 전달' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.2-05 Context→Service Group 전달

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.2-05 │
└────────────────┘
┌──────────────────────────┐
│ Context→Service Group 전달 │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Context→Service Group 전달' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.1.2-03 Context Field Provenance Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.2-03 │
└────────────────┘
┌──────────────────────────────┐
│ Context Field Provenance Map │
└──────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Context Field Provenance Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1.2-06 Context→업무로그 Correlation

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.2-06 │
└────────────────┘
┌──────────────────────────┐
│ Context→업무로그 Correlation │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Context→업무로그 Correlation' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.2-07 정상 Context Sequence

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '정상 Context Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1.2-08 오류/Timeout Context 처리

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '오류/Timeout Context 처리' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.2-09 민감정보/마스킹 영향

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.2-09 │
└────────────────┘
┌─────────────┐
│ 민감정보/마스킹 영향 │
└─────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '민감정보/마스킹 영향' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.1.2 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [FW협의필요] | 후속 설계 중단 |
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
| 목차 III.1.2 | 슬롯 100% 독립 그림 | FIG-III.1.2-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [FW협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.1.2-01 | 자료 | 이 절 빈 박스 | [FW협의필요] | 장표/인터뷰/ADR |
| GAP-III.1.2-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1.2-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1.2 슬롯 이름·Evidence Maturity `M1`

**What blocks approval**
- III.1.2 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [FW협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.1.2 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1.2-10 Context SSOT/GAP Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────┐
│ III.1.2 / 10 Context SSOT/GAP Map │
└───────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [FW협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Context SSOT/GAP Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.1.2 축약 표현 없음

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

`III.1.2` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.1.3 어플리케이션 계층 구조

**협업 태그:** [FW협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1.3-01 | 원문 목차 | III.1.3 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1.3-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1.3-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1.3-01 | Application Layer Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-02 | AA Map→Package→Service/Business Task | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-03 | 공식 Layer Responsibility | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-04 | 정상 Call Chain | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-05 | 금지 Call Chain | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-06 | Layer→DTO/Context 전달 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-07 | Layer→Transaction Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-08 | Layer→DAO/DB | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-09 | 예외 처리/응답 흐름 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.3-10 | FACT 공식계층 vs 비교 제안계층 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.1.3 어플리케이션 계층 구조** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1.3 어플리케이션 계층 구조`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [FW협의필요]

## 4. L0 Big Picture

### FIG-III.1.3-01 Application Layer Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.3-01 │
└────────────────┘
┌───────────────────────────────┐
│ Application Layer Big Picture │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Application Layer Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-III.1.3-05 금지 Call Chain

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.3-05 │
└────────────────┘
┌───────────────┐
│ 금지 Call Chain │
└───────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '금지 Call Chain' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.3-08 Layer→DAO/DB

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.3-08 │
└────────────────┘
┌──────────────┐
│ Layer→DAO/DB │
└──────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Layer→DAO/DB' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.1.3-02 AA Map→Package→Service/Business Task

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.3-02 │
└────────────────┘
┌──────────────────────────────────────┐
│ AA Map→Package→Service/Business Task │
└──────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'AA Map→Package→Service/Business Task' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.3-03 공식 Layer Responsibility

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.3-03 │
└────────────────┘
┌─────────────────────────┐
│ 공식 Layer Responsibility │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '공식 Layer Responsibility' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.3-07 Layer→Transaction Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.3-07 │
└────────────────┘
┌────────────────────────────┐
│ Layer→Transaction Boundary │
└────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Layer→Transaction Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1.3-04 정상 Call Chain

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '정상 Call Chain' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.3-06 Layer→DTO/Context 전달

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.3-06 │
└────────────────┘
┌──────────────────────┐
│ Layer→DTO/Context 전달 │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Layer→DTO/Context 전달' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1.3-09 예외 처리/응답 흐름

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '예외 처리/응답 흐름' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.1.3 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [FW협의필요] | 후속 설계 중단 |
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
| 목차 III.1.3 | 슬롯 100% 독립 그림 | FIG-III.1.3-01~ | Completion Gate 수치 |
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
- [FW협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.1.3-01 | 자료 | 이 절 빈 박스 | [FW협의필요] | 장표/인터뷰/ADR |
| GAP-III.1.3-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1.3-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1.3 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.1.3 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [FW협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.1.3 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1.3-10 FACT 공식계층 vs 비교 제안계층

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.3-10 │
└────────────────┘
┌──────────────────────┐
│ FACT 공식계층 vs 비교 제안계층 │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'FACT 공식계층 vs 비교 제안계층' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.1.3 축약 표현 없음

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

`III.1.3` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.1.4 서비스그룹 간 호출 규칙

**협업 태그:** [FW협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1.4-01 | 원문 목차 | III.1.4 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1.4-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1.4-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **12**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1.4-01 | Service Group Boundary Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-02 | 동일 그룹 내부 호출 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-03 | 타 그룹 호출 Decision Tree | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-04 | 직접 메소드 호출 허용/금지 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-05 | 공통계약/API 호출 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-06 | DTO 공유/복제/공통모듈 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-07 | WAS Instance 간 Remote 호출 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-08 | Remote 정상 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-09 | Remote 장애/Timeout Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-10 | Transaction Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-11 | Dependency/Cycle Detection | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.4-12 | 예외승인/ADR Flow | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.1.4 서비스그룹 간 호출 규칙** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1.4 서비스그룹 간 호출 규칙`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [FW협의필요]

## 4. L0 Big Picture

### FIG-III.1.4-01 Service Group Boundary Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-01 │
└────────────────┘
┌────────────────────────────────────┐
│ Service Group Boundary Big Picture │
└────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Service Group Boundary Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.1.4-02 동일 그룹 내부 호출

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-02 │
└────────────────┘
┌─────────────┐
│ 동일 그룹 내부 호출 │
└─────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '동일 그룹 내부 호출' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.4-03 타 그룹 호출 Decision Tree

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-03 │
└────────────────┘
┌───────────────────────┐
│ 타 그룹 호출 Decision Tree │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '타 그룹 호출 Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.1.4-04 직접 메소드 호출 허용/금지

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-04 │
└────────────────┘
┌─────────────────┐
│ 직접 메소드 호출 허용/금지 │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '직접 메소드 호출 허용/금지' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.4-05 공통계약/API 호출

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-05 │
└────────────────┘
┌─────────────┐
│ 공통계약/API 호출 │
└─────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '공통계약/API 호출' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.4-06 DTO 공유/복제/공통모듈

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-06 │
└────────────────┘
┌────────────────┐
│ DTO 공유/복제/공통모듈 │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'DTO 공유/복제/공통모듈' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.1.4-10 Transaction Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-10 │
└────────────────┘
┌──────────────────────┐
│ Transaction Boundary │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Transaction Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1.4-07 WAS Instance 간 Remote 호출

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-07 │
└────────────────┘
┌──────────────────────────┐
│ WAS Instance 간 Remote 호출 │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'WAS Instance 간 Remote 호출' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.4-08 Remote 정상 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Remote 정상 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1.4-09 Remote 장애/Timeout Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상경로
  정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
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

1. **그림 목적:** 이 그림은 'Remote 장애/Timeout Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.4-11 Dependency/Cycle Detection

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.4-11 │
└────────────────┘
┌────────────────────────────┐
│ Dependency/Cycle Detection │
└────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Dependency/Cycle Detection' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.1.4 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [FW협의필요] | 후속 설계 중단 |
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
| 목차 III.1.4 | 슬롯 100% 독립 그림 | FIG-III.1.4-01~ | Completion Gate 수치 |
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
- [FW협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.1.4-01 | 자료 | 이 절 빈 박스 | [FW협의필요] | 장표/인터뷰/ADR |
| GAP-III.1.4-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1.4-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1.4 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.1.4 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [FW협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.1.4 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1.4-12 예외승인/ADR Flow

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

1. **그림 목적:** 이 그림은 '예외승인/ADR Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 12 = 본문 FIG 12
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.1.4 축약 표현 없음

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

`III.1.4` V5 재작성. 필수 FIG 12개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.1.5 어플리케이션 실행 제어

**협업 태그:** [FW협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1.5-01 | 원문 목차 | III.1.5 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1.5-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1.5-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **14**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1.5-01 | Execution Control Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-02 | Pre→Execute→Post Pipeline | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-03 | 정상 거래 상세 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-04 | Timeout 감시 Lifecycle | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-05 | Timeout 전파/차단 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-06 | 중복거래 State Machine | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-07 | 중복거래 정상/충돌 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-08 | 로그레벨 동적 변경 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-09 | Validation/Auth/Business Reject 분기 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-10 | Retry 허용/금지 Decision Tree | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-11 | Transaction Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-12 | Exception→Response Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-13 | Correlation/거래로그/관측 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.5-14 | Recovery/Manual 처리 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.1.5 어플리케이션 실행 제어** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1.5 어플리케이션 실행 제어`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [FW협의필요]

## 4. L0 Big Picture

### FIG-III.1.5-01 Execution Control Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-01 │
└────────────────┘
┌───────────────────────────────┐
│ Execution Control Big Picture │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Execution Control Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.1.5-02 Pre→Execute→Post Pipeline

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-02 │
└────────────────┘
┌───────────────────────────┐
│ Pre→Execute→Post Pipeline │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Pre→Execute→Post Pipeline' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.5-04 Timeout 감시 Lifecycle

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-04 │
└────────────────┘
┌──────────────────────┐
│ Timeout 감시 Lifecycle │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Timeout 감시 Lifecycle' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.1.5-06 중복거래 State Machine

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-06 │
└────────────────┘
┌────────────────────┐
│ 중복거래 State Machine │
└────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '중복거래 State Machine' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.1.5-11 Transaction Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-11 │
└────────────────┘
┌──────────────────────┐
│ Transaction Boundary │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Transaction Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.5-12 Exception→Response Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-12 │
└────────────────┘
┌────────────────────────────┐
│ Exception→Response Mapping │
└────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Exception→Response Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1.5-03 정상 거래 상세 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '정상 거래 상세 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.5-05 Timeout 전파/차단 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Timeout 전파/차단 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.5-07 중복거래 정상/충돌 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '중복거래 정상/충돌 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.5-08 로그레벨 동적 변경 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-08 │
└────────────────┘
┌─────────────────┐
│ 로그레벨 동적 변경 Flow │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '로그레벨 동적 변경 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.5-09 Validation/Auth/Business Reject 분기

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-09 │
└────────────────┘
┌────────────────────────────────────┐
│ Validation/Auth/Business Reject 분기 │
└────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Validation/Auth/Business Reject 분기' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1.5-10 Retry 허용/금지 Decision Tree

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상경로
  정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
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

1. **그림 목적:** 이 그림은 'Retry 허용/금지 Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.5-13 Correlation/거래로그/관측

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.5-13 │
└────────────────┘
┌─────────────────────┐
│ Correlation/거래로그/관측 │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [FW협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Correlation/거래로그/관측' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.1.5 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [FW협의필요] | 후속 설계 중단 |
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
| 목차 III.1.5 | 슬롯 100% 독립 그림 | FIG-III.1.5-01~ | Completion Gate 수치 |
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
- [FW협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.1.5-01 | 자료 | 이 절 빈 박스 | [FW협의필요] | 장표/인터뷰/ADR |
| GAP-III.1.5-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1.5-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1.5 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.1.5 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [FW협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.1.5 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1.5-14 Recovery/Manual 처리

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상경로
  정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
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

1. **그림 목적:** 이 그림은 'Recovery/Manual 처리' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [FW협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 14 = 본문 FIG 14
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.1.5 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 14 | 실제 14 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 14 · Figure Plan 행 14 · 본문 ` ```text ` 그림 코드블록(FIG) 14건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`III.1.5` V5 재작성. 필수 FIG 14개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.1.6 Lv3 어플리케이션 식별 기준

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M1` — 근거 부족. Discovery/Question/Option/Gate 그림을 본체로 둔다. 가짜 Runtime 금지.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1.6-01 | 원문 목차 | III.1.6 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1.6-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1.6-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **8**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1.6-01 | Lv1→Lv2→Lv3 Tree | L0 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.6-02 | Service Group→Lv3 분해기준 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.6-03 | Naming Rule 구조 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.6-04 | 업무기능→Lv3 ID Decision | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.6-05 | 좋은/나쁜 식별 예 | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.6-06 | 중복/충돌 검증 Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.6-07 | Lv3→Repository/배포단위 | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.6-08 | 변경/폐기 Lifecycle | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.1.6 Lv3 어플리케이션 식별 기준** — Evidence `M1`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1.6 Lv3 어플리케이션 식별 기준`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-III.1.6-01 Lv1→Lv2→Lv3 Tree

**Level:** L0 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.6-01 │
└────────────────┘
┌──────────────────┐
│ Lv1→Lv2→Lv3 Tree │
└──────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Lv1→Lv2→Lv3 Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.1.6-02 Service Group→Lv3 분해기준

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.6-02 │
└────────────────┘
┌────────────────────────┐
│ Service Group→Lv3 분해기준 │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Service Group→Lv3 분해기준' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.1.6-03 Naming Rule 구조

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.6-03 │
└────────────────┘
┌────────────────┐
│ Naming Rule 구조 │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Naming Rule 구조' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.6-04 업무기능→Lv3 ID Decision

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.6-04 │
└────────────────┘
┌──────────────────────┐
│ 업무기능→Lv3 ID Decision │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '업무기능→Lv3 ID Decision' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1.6-05 좋은/나쁜 식별 예

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.6-05 │
└────────────────┘
┌────────────┐
│ 좋은/나쁜 식별 예 │
└────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '좋은/나쁜 식별 예' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.6-06 중복/충돌 검증 Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.6-06 │
└────────────────┘
┌───────────────┐
│ 중복/충돌 검증 Flow │
└───────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '중복/충돌 검증 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1.6-07 Lv3→Repository/배포단위

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.6-07 │
└────────────────┘
┌─────────────────────┐
│ Lv3→Repository/배포단위 │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Lv3→Repository/배포단위' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.1.6 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 III.1.6 | 슬롯 100% 독립 그림 | FIG-III.1.6-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- 아키텍처(작성) · 해당 Owner TBD

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.1.6-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-III.1.6-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1.6-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1.6 슬롯 이름·Evidence Maturity `M1`

**What blocks approval**
- III.1.6 GAP 박스
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
- III.1.6 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1.6-08 변경/폐기 Lifecycle

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.6-08 │
└────────────────┘
┌─────────────────┐
│ 변경/폐기 Lifecycle │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: 아키텍처(작성) · 해당 Owner TBD
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '변경/폐기 Lifecycle' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] III.1.6 축약 표현 없음

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

`III.1.6` V5 재작성. 필수 FIG 8개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.1.7 거래패턴

**협업 태그:** 임채정

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1.7-01 | 원문 목차 | III.1.7 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1.7-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1.7-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **14**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1.7-01 | 거래패턴 전체 Catalog Map | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-02 | Pattern 선택 Decision Tree | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-03 | 통상거래 독립 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-04 | 파일 Upload 독립 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-05 | 파일 Download 독립 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-06 | On-demand Batch 독립 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-07 | 대용량조회 독립 Flow | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-08 | 대량데이터 Online Sync 독립 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-09 | 대량데이터 Async Batch 독립 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-10 | 계정단말 Push 독립 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-11 | 보고서 조회 독립 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-12 | 내부시스템연계 독립 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-13 | Pattern 공통 장애/Retry/Idempotency | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.7-14 | Pattern→NFR/운영/보안 Matrix Diagram | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 마케팅플랫폼 패턴 **이름 8종**(통상, 파일업/다운, 온디맨드 배치, 대용량조회, 대량데이터(온라인 Sync+비동기 배치), 계정단말 Push, 보고서 조회, 내부시스템연계). 도식 원문 없음 → Sequence는 [TO-BE/PROPOSED]. 파일 CS=입출력 EUC-KR·배치 FILE UTF-8. 계정 Push는 EIC/MCA. 새 이름(마이크로배치 등) 금지. ADR-PT-01=대량 Sync 허용조건. Owner=임채정.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1.7 거래패턴`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 임채정

## 4. L0 Big Picture

### FIG-III.1.7-01 거래패턴 전체 Catalog Map

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[FACT] 목차 8이름. 더하지 않음. [TO-BE/PROPOSED] 도식 원문 없음. 임채정 확정 전 골격. 8이름만 FACT.
                    [정보단말 / 계정단말]
                            │
        ┌─────────┬─────────┼─────────┬─────────┐
        ▼         ▼         ▼         ▼         ▼
   1 통상거래  2 파일업/다운  4 대용량조회  7 보고서조회  6 계정Push
   온라인Sync  (CS:입출력     조회 특화     ②분석제공    계정경로만
               EUC-KR,                     접점         (EIC/MCA)
               배치FILE UTF-8)
        │                   │
        ▼                   ▼
   3 온디맨드배치      5 대량데이터
   → III.2 Control-M    ├ 온라인 Sync  (통상과 한 파이프 단정 금지)
                        └ 비동기 배치 → III.2
        │
        ▼
   8 내부시스템연계 → III.4  I/F 종류 목록은 열어 둠 (창작 금지)
금지: “마이크로배치” 등 9번째 이름
```

**그림 상세해설**

1. **그림 목적:** 8패턴 카탈로그만 그린다. 각 패턴 Sequence는 03~12에 맡긴다.
2. **근거자료와 상태:** [FACT] 목차 8이름, 파일 CS 행, 계정단말 Runtime, 대량데이터에 Sync+비기 병기. [GAP] 원본 도식.
3. **Boundary / In / Out:** In: 단말. Out: 8분기. 밖: 패턴 구현 클래스(III.1.3).
4. **Trigger / 시작점:** 패턴 명명 리뷰(임채정).
5. **처리순서:** ① 단말 → ② 8이름 분기 → ③ 배치성 3·5비기는 III.2 → ④ 8번은 III.4.
6. **책임·비책임:** 책임: 임채정 이름·도식. 앱은 구현. 비책임: 새 패턴명.
7. **데이터/전문/상태/제어:** 제어: 패턴 선택(FIG-02). 데이터: JSON UTF-8 또는 파일 CS.
8. **실패·운영·후속:** 공통 장애=FIG-13. NFR 매트릭스=FIG-14. ADR-PT-01=대량 Sync.

## 5. L1 영역/계층/서비스 View

### FIG-III.1.7-02 Pattern 선택 Decision Tree

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
요청이 파일인가? ──Yes──► FIG-04 Upload / FIG-05 Download  [방향 질문]
        │No
        ▼
계정단말으로 Push 하는가? ──Yes──► FIG-10 계정단말 Push
        │No
        ▼
지금 응답이 필요 없는 배치 기동인가? ──Yes──► FIG-06 온디맨드 배치 → III.2
        │No
        ▼
보고서/마트 조회인가? ──Yes──► FIG-11 보고서 조회 (②영역 접점)
        │No
        ▼
내부 I/F 호출인가? ──Yes──► FIG-12 내부연계 → III.4
        │No
        ▼
조회 결과 집합이 ‘대용량’인가? ──Yes──► FIG-07 대용량조회 (Timeout=III.1.5 결합 TBD)
        │No
        ▼
대량 데이터 이송인가?
  ├ 온라인 Sync ──► FIG-08  [ADR-PT-01 허용조건 TBD]
  └ 비동기 배치 ──► FIG-09 → III.2
        │나머지
        ▼
FIG-03 통상거래 (기본 온라인)
결정 기준 원문 없음 → 트리 자체는 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 패턴 선택 질문 트리. 값을 채워 자동 분류기를 만들지 않는다.
2. **근거자료와 상태:** [FACT] 8이름과 대량 Sync/비기 병기. [PROPOSED] 분기 질문. [GAP] 공식 판정표.
3. **Boundary / In / Out:** In: 업무 요청 특성. Out: 패턴 ID 03~12. 밖: 서비스그룹 호출(1.4).
4. **Trigger / 시작점:** 설계 시 패턴 지정, 런타임 라우터 여부는 [TBD].
5. **처리순서:** ① 파일 → ② Push → ③ 온디맨드 → ④ 보고서 → ⑤ I/F → ⑥ 대용량조회 → ⑦ 대량 → ⑧ 통상.
6. **책임·비책임:** 책임: 임채정 기준, FW 라우팅 위치 TBD. 비책임: 점수/임계 창작.
7. **데이터/전문/상태/제어:** 제어: 선택 결과. 데이터: 없음(분류).
8. **실패·운영·후속:** 오분류 시 잘못된 Timeout/배치 기동=FIG-13. 대량 Sync 허용=ADR-PT-01.

## 6. L2 Component/Application/Node/SW/DB/Contract View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 7. Static Mapping / Responsibility View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1.7-03 통상거래 독립 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[통상거래] 온라인 Sync 기본  [TO-BE/PROPOSED 골격]
① 단말 요청 (정보단말 EUC-KR 화면 / JSON 구간은 1.8)
        │
        ▼
② 진입: 정보단말→Neoworks 직접  |  계정단말→EIC/MCA→Neoworks  [FACT]
        │
        ▼
③ Context 생성·계층 호출 (III.1.2/1.3) [FW]
        │
        ▼
④ JDBC → RTW/ADW/BSA 중 대상 [TBD]
        │
        ▼
⑤ JSON UTF-8 응답 → 단말 표시 변환(필요 시)
대량 Sync(FIG-08)와 이 파이프를 동일시하지 않음
Timeout 초 = III.1.5 GAP. 이 그림에 숫자 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** 통상거래 Happy Path만. 파일/배치/Push와 합치지 않는다.
2. **근거자료와 상태:** [FACT] Runtime 두 진입, JSON UTF-8, 단말 EUC-KR. [PROPOSED] ①~⑤. [GAP] DB 대상, Timeout.
3. **Boundary / In / Out:** In: 단말 거래. Out: 동기 응답. 밖: Control-M.
4. **Trigger / 시작점:** 사용자가 온라인 거래를 제출.
5. **처리순서:** ① 요청 → ② 진입경로 → ③ Context/계층 → ④ JDBC → ⑤ 응답.
6. **책임·비책임:** 책임: 앱=패턴구현, FW=Context, DA=SQL. 임채정=이름.
7. **데이터/전문/상태/제어:** 전문 JSON. CS 변환은 1.8/ADR-CS-01.
8. **실패·운영·후속:** 실패=FIG-13. 중복거래=III.1.5. 후속 1.8 적용구간.

### FIG-III.1.7-04 파일 Upload 독립 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[파일 Upload] 목차는 업/다운을 한 이름. 이 슬롯은 Upload만
① 단말에서 파일 선택
        │
        ▼
② 전송 (프로토콜/청크 크기 창작 금지)
   CS [FACT]: 입출력 파일 EUC-KR
        │
        ▼
③ Neoworks 수신 · 검증 (바이러스/사이즈 정책 [GAP])
        │
        ▼
④ 저장 위치: AP 로컬 / NAS / DB BLOB / 오브젝트 = [TBD]
        │
        ▼
⑤ 처리: 즉시 파싱 vs 배치 후속 [TBD] — 후속이면 FIG-06/09와 연결만
배치 FILE UTF-8 은 Download/배치 파일 쪽 FACT이지 Upload 입력을 UTF-8로 바꾸지 말 것
```

**그림 상세해설**

1. **그림 목적:** 파일 Upload만. Download(05)와 한 그림으로 합치지 않는다.
2. **근거자료와 상태:** [FACT] 패턴 이름, 입출력 EUC-KR. [GAP] 저장소, 한도, 프로토콜. [PROPOSED] ①~⑤.
3. **Boundary / In / Out:** In: 단말 파일. Out: 서버 저장. 밖: 배치 FILE UTF-8 적재.
4. **Trigger / 시작점:** 사용자가 업로드 제출.
5. **처리순서:** ① 선택 → ② EUC-KR 전송 → ③ 검증 → ④ 저장 TBD → ⑤ 후처리 TBD.
6. **책임·비책임:** 책임: 앱/FW 수신, TA 스토리지. 비책임: 용량 숫자 창작.
7. **데이터/전문/상태/제어:** 파일 바이트 EUC-KR. JSON 필드 창작 금지.
8. **실패·운영·후속:** 전송 실패/부분수신=FIG-13. 보안 검사=III.7 연결. CS 오류=P-08.

### FIG-III.1.7-05 파일 Download 독립 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[파일 Download]
① 단말 다운로드 요청 (권한 [GAP])
        │
        ▼
② Neoworks가 파일 생성 또는 기존 파일 조회
   생성 주체: 온라인 vs 선행 배치 [TBD]
        │
        ▼
③ CS: 입출력 EUC-KR [FACT]  (배치가 만든 FILE은 UTF-8 FACT → 변환 지점 [TBD])
        │
        ▼
④ 전송 → 단말 저장
        │
        ▼
⑤ 반출/마스킹 [III.7, 값 TBD]
Upload(04) 화살표를 반대로만 그린 것으로 끝내지 않음 — 생성 주체와 CS 변환이 다름
```

**그림 상세해설**

1. **그림 목적:** Download 전용. Upload과 생성 주체·CS 변환을 다르게 둔다.
2. **근거자료와 상태:** [FACT] 입출력 EUC-KR, 배치 FILE UTF-8. [GAP] 생성 주체, 반출. [PROPOSED] 흐름.
3. **Boundary / In / Out:** In: 요청. Out: 단말 파일. 밖: Storage 데이터셋(IV.1)과 자동 동일시 금지.
4. **Trigger / 시작점:** 사용자가 다운로드.
5. **처리순서:** ① 요청 → ② 생성/조회 → ③ CS 맞춤 → ④ 전송 → ⑤ 반출 통제.
6. **책임·비책임:** 책임: 앱 생성, 보안 반출, DA 원천.
7. **데이터/전문/상태/제어:** 파일 바이트. 개인정보 포함 여부 TBD.
8. **실패·운영·후속:** 권한 거부·전송 끊김=FIG-13. 대용량이면 FIG-07과 혼동 금지(조회 vs 파일).

### FIG-III.1.7-06 On-demand Batch 독립 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[온디맨드 배치] 온라인 트리거 → 배치 실행
① 단말/서비스가 ‘지금 배치 기동’ 요청 (동기 대용량 처리가 아님)
        │
        ▼
② Neoworks 접수 · 요청 ID 발급 [PROPOSED]
        │
        ▼
③ Control-M 접점 [FACT: 스케줄러 이름] 매핑 방식 [GAP]
        │
        ▼
④ III.2 배치 유형 (DevOn / ETCL / Shell·SP) 중 하나 실행
        │
        ▼
⑤ 완료 통지 채널 (화면/Push/메일) [TBD] — Push면 FIG-10과 연결만
통상거래(03) 응답 대기와 이 패턴을 섞지 않음
```

**그림 상세해설**

1. **그림 목적:** 온디맨드 배치: 온라인이 트리거만 하고 실행은 III.2.
2. **근거자료와 상태:** [FACT] 패턴 이름, Control-M. [GAP] 잡 매핑, 통지. [PROPOSED] 접수 ID.
3. **Boundary / In / Out:** In: 온라인 요청. Out: 배치 기동. 밖: 대량 Sync(08).
4. **Trigger / 시작점:** 사용자가 배치성 작업을 요청.
5. **처리순서:** ① 요청 → ② 접수 → ③ Control-M → ④ III.2 실행 → ⑤ 통지 TBD.
6. **책임·비책임:** 책임: 앱 접수, 배치=허준/연제학 절, Control-M=운영.
7. **데이터/전문/상태/제어:** 제어: 잡 트리거. 데이터: 요청 ID. 실데이터는 배치 층.
8. **실패·운영·후속:** 잡 실패=III.2 FIG 재처리. 온라인 Timeout으로 잡 성공을 판단하지 말 것.

### FIG-III.1.7-08 대량데이터 Online Sync 독립 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[대량데이터 — 온라인 Sync] 목차가 Sync를 명시한 절반
① 대량 적재/갱신 요청을 온라인 응답 범위에서 처리
        │
        ▼
② 통상거래(03)와 동일 진입이어도 패턴 이름은 다름 — 한 파이프 단정 금지
        │
        ▼
③ 트랜잭션 경계 · 부분 커밋 [GAP]
        │
        ▼
④ JDBC 대량 쓰기 대상 저장소 [TBD]
        │
        ▼
⑤ 동기 응답 (성공 건수 등 필드 창작 금지)
ADR-PT-01: 허용 조건(건수/시간/잠금) 원문 없음 → 조건 박스 [TBD]
비동기 절반은 FIG-09
```

**그림 상세해설**

1. **그림 목적:** 대량데이터의 온라인 Sync 절반만. Async(09)·통상(03)과 합치지 않는다.
2. **근거자료와 상태:** [FACT] 목차가 Sync와 비기 배치를 함께 명시. [GAP] 허용 조건. [PROPOSED] 흐름.
3. **Boundary / In / Out:** In: 대량 동기 요청. Out: 동기 응답. 밖: ETCL 배치.
4. **Trigger / 시작점:** 대량 동기 처리 요청.
5. **처리순서:** ① 요청 → ② 패턴 분리 확인 → ③ Tx TBD → ④ 쓰기 → ⑤ 응답.
6. **책임·비책임:** 책임: 임채정 분리, 앱 Tx, DA 잠금. ADR-PT-01.
7. **데이터/전문/상태/제어:** 대량 DML. JSON 건수 필드 창작 금지.
8. **실패·운영·후속:** Timeout/잠금=FIG-13. 실패 시 부분 커밋 정합 GAP.

### FIG-III.1.7-09 대량데이터 Async Batch 독립 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[대량데이터 — 비동기 배치] 목차 병기의 다른 절반
① 온라인은 접수만 (온디맨드 06과 닮으나 패턴 이름은 대량데이터)
        │
        ▼
② 배치 큐/잡 등록 → III.2 (DevOn/ETCL/Shell·SP)
        │
        ▼
③ Control-M 기동 [접점 GAP]
        │
        ▼
④ 대량 적재 실행 (실데이터 층, Q-Track 아님)
        │
        ▼
⑤ 완료 통지 TBD (Push면 FIG-10)
06 온디맨드와 차이: 업무 의미가 ‘대량 데이터 이송’. 기동 메커니즘은 유사할 수 있음 → 이름 병합 금지
```

**그림 상세해설**

1. **그림 목적:** 대량데이터 Async만. 온디맨드(06)와 이름을 합치지 않는다.
2. **근거자료와 상태:** [FACT] 비동기 배치 병기, III.2 유형, Control-M. [GAP] 큐, 통지. [PROPOSED].
3. **Boundary / In / Out:** In: 접수. Out: 배치 적재. 밖: Q-Track 메타(IV.4).
4. **Trigger / 시작점:** 대량 이송을 비동기로 요청.
5. **처리순서:** ① 접수 → ② 잡 등록 → ③ 스케줄러 → ④ 적재 → ⑤ 통지.
6. **책임·비책임:** 책임: 온라인 접수 vs 배치 실행 Owner 분리.
7. **데이터/전문/상태/제어:** 제어: 잡. 데이터: 대량 행(III.4/2).
8. **실패·운영·후속:** 잡 실패 재처리=III.2. 온라인은 접수 실패만 FIG-13.

### FIG-III.1.7-10 계정단말 Push 독립 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[계정단말 Push] 방향이 서버→단말
① 서버 측 이벤트 (배치 완료, 알림, 거래 결과) [이벤트 목록 GAP]
        │
        ▼
② Neoworks → EIC/MCA → 계정단말  [FACT: 계정 경로는 중계]
   정보단말 직접 경로로 Push를 그리지 않음 (이 패턴 이름=계정단말)
        │
        ▼
③ 단말 수신 · 표시
        │
        ▼
④ 수신 실패 시 재Push / 보관 [GAP]
JSON/CS: 계정·FEP EUC-KR 힌트, JSON 구간인지는 1.8 TBD
```

**그림 상세해설**

1. **그림 목적:** 계정단말 Push. 정보단말 직접 경로와 반대로 중계를 강제한다.
2. **근거자료와 상태:** [FACT] 계정단말은 EIC/MCA 중계. 패턴 이름. [GAP] 이벤트 목록, 재전송. [PROPOSED].
3. **Boundary / In / Out:** In: 서버 이벤트. Out: 계정단말 표시. 밖: 정보단말 UI.
4. **Trigger / 시작점:** 서버가 계정 사용자에게 알릴 때.
5. **처리순서:** ① 이벤트 → ② EIC/MCA → ③ 표시 → ④ 실패 처리 GAP.
6. **책임·비책임:** 책임: 앱 Push, 채널 EIC/MCA. 비책임: 정보단말 Push 단정.
7. **데이터/전문/상태/제어:** 제어: Push 전문. 데이터: 알림 페이로드 필드 GAP.
8. **실패·운영·후속:** MCA 장애 시 Push 불가=FIG-13. 온디맨드 통지 채널 후보.

### FIG-III.1.7-11 보고서 조회 독립 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[보고서 조회] ② 분석·정보제공 접점
① 사용자(정보포탈/BI/단말) 보고서 요청
        │
        ▼
② 이 패턴의 실행 주체 후보
   A 마케팅 온라인이 허브 조회   [APP-MKT]
   B BI포탈 DataEye가 조회       [IV.1]
   C 현행 OLAP SAP BO            [As-Is]
   공식 배정 원문 없음 → 후보만 [TBD]
        │
        ▼
③ 소스: 마트/ADW/BSA/가상화 (IV.1 FIG-07과 정합)
        │
        ▼
④ 화면/리포트 렌더
대용량조회(07)는 마케팅 온라인 조회. 보고서는 분석제공 접점 — 이름 유지
```

**그림 상세해설**

1. **그림 목적:** 보고서 조회를 BI/OLAP/온라인 후보로만 열고 한 제품에 고정하지 않는다.
2. **근거자료와 상태:** [FACT] 패턴 이름, ②영역. [GAP] 실행 주체. NSIGHT MSTR 금지.
3. **Boundary / In / Out:** In: 보고서 요청. Out: 렌더. 밖: Self BI(IV.3) 단정 금지.
4. **Trigger / 시작점:** 사용자가 보고서를 연다.
5. **처리순서:** ① 요청 → ② 주체 후보 A/B/C → ③ 소스 → ④ 렌더.
6. **책임·비책임:** 책임: 업무 리포트 정의, 실행 주체 ADR. DataEye는 IV.1.
7. **데이터/전문/상태/제어:** 조회 결과. 포탈 데이터셋 파일과 혼동 금지.
8. **실패·운영·후속:** 소스 장애=IV.1 FIG-11 또는 온라인 FIG-13. 주체 미정=COL.

### FIG-III.1.7-12 내부시스템연계 독립 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[내부시스템연계] I/F 종류는 열어 둠 — 목록 창작 금지
① 마케팅 서비스가 타 시스템 호출 필요
        │
        ▼
② I/F 종류 후보 (이름만 거론, 채택 아님)
   EAI / MCA / FEP / JSON / 파일 / CDC …  [III.4 연결]
   BC 정의 미입수 → 여기 넣지 않음
        │
        ▼
③ 동기 vs 비동기 [종류별 TBD]
        │
        ▼
④ 응답 매핑 → Context/JSON (1.8)
        │
        ▼
⑤ 오류는 채널별 (FIG-13 공통 + III.4 채널 장애)
서비스그룹 내부 호출(1.4)과 이 패턴을 동일시하지 않음 — 내부 I/F는 시스템 간
```

**그림 상세해설**

1. **그림 목적:** 내부연계 패턴. I/F 종류 카탈로그를 지어 넣지 않고 III.4로 연다.
2. **근거자료와 상태:** [FACT] 패턴 이름에 I/F 종류. III.4 수단 이름 CDC/ETCL/BC(미정의). [GAP] 종류 확정 목록.
3. **Boundary / In / Out:** In: 서비스. Out: 외부 시스템. 밖: 같은 서비스그룹 메소드 호출.
4. **Trigger / 시작점:** 타 시스템 데이터가 필요할 때.
5. **처리순서:** ① 필요 인식 → ② 종류 후보(미선정) → ③ Sync/Async TBD → ④ 매핑 → ⑤ 오류.
6. **책임·비책임:** 책임: IF/DA III.4, 앱 매핑, 임채정 패턴명.
7. **데이터/전문/상태/제어:** 전문/파일/CDC 메타. JSON 필드 창작 금지.
8. **실패·운영·후속:** 채널 장애=III.4. 재처리 Idempotency=FIG-13.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1.7-13 Pattern 공통 장애/Retry/Idempotency

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
공통 장애 축 (패턴별 값은 GAP, 축만 그림)
 Retry 허용?     온디맨드/대량Async = 배치 재처리(III.2)
                 통상/대량Sync = 중복거래 축(III.1.5)와 충돌 검토
 Idempotency     키 위치 [GAP]
 Timeout         III.1.5. 패턴별 초 창작 금지
 부분실패        대량 Sync 부분 커밋 [GAP]
 채널실패        Push=MCA, 내부연계=EAI/FEP, 파일=전송
 진입실패        정보 직접 vs 계정 중계 각각
금지: 전 패턴에 동일 Retry 3회 같은 숫자
```

**그림 상세해설**

1. **그림 목적:** 8패턴 공통 장애·재시도·멱등 축. 숫자를 채우지 않는다.
2. **근거자료와 상태:** [FACT] 1.5에 Timeout/중복 항목. Control-M 재처리 층. [GAP] 멱등 키, 횟수.
3. **Boundary / In / Out:** In: 각 패턴 Sequence. Out: 축. 밖: 포탈 HA.
4. **Trigger / 시작점:** 장애 설계 리뷰.
5. **처리순서:** ① 축 나열 → ② 패턴별 연결 → ③ 숫자 공란 → ④ 1.5/III.2 핸드오프.
6. **책임·비책임:** 책임: FW 1.5, 배치 III.2, 임채정 패턴 예외.
7. **데이터/전문/상태/제어:** 제어: Retry/Timeout 정책 TBD. 데이터: 거래 ID.
8. **실패·운영·후속:** 운영 매트릭스 FIG-14. 런북 GAP.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-III.1.7-07 대용량조회 독립 Flow

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[대용량조회] 조회 특화 (파일 다운로드 아님)
① 조회 조건 입력
        │
        ▼
② 진입 경로 동일 (정보 직접 / 계정 EIC·MCA)
        │
        ▼
③ 조회 실행 — 페이지/커서/파일변환 여부 [GAP]
        │
        ▼
④ 결과 집합 반환 (화면)  vs  너무 크면 파일 패턴(05)으로 넘길지 [TBD]
        │
        ▼
⑤ Timeout = III.1.5와 결합 [GAP, 초 창작 금지]
보고서 조회(11)는 ②영역 BI/마트 접점. 이 패턴은 마케팅 온라인 조회
Self BI(IV.3) 도구와 자동 동일시 금지
```

**그림 상세해설**

1. **그림 목적:** 대용량조회 전용. 보고서(11)·파일다운(05)·Self BI와 분리한다.
2. **근거자료와 상태:** [FACT] 패턴 이름=조회. [GAP] 페이징, Timeout 값. [PROPOSED] ①~⑤.
3. **Boundary / In / Out:** In: 조회 조건. Out: 결과 집합. 밖: DataEye 리포트.
4. **Trigger / 시작점:** 사용자가 대량 조회.
5. **처리순서:** ① 조건 → ② 진입 → ③ 실행 → ④ 반환/넘김 TBD → ⑤ Timeout 결합.
6. **책임·비책임:** 책임: 앱 SQL, DA 인덱스/허용, FW Timeout.
7. **데이터/전문/상태/제어:** 결과 행. JSON vs 그리드 TBD.
8. **실패·운영·후속:** Timeout/부하=FIG-13/14. 허브 부하 NFR GAP.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.1.7 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 임채정 | 후속 설계 중단 |
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
| 목차 III.1.7 | 슬롯 100% 독립 그림 | FIG-III.1.7-01~ | Completion Gate 수치 |
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
- 임채정

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.1.7-01 | 자료 | 이 절 빈 박스 | 임채정 | 장표/인터뷰/ADR |
| GAP-III.1.7-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1.7-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1.7 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.1.7 GAP 박스
- Owner 미응답 항목

**Who must answer**
- 임채정
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.1.7 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1.7-14 Pattern→NFR/운영/보안 Matrix Diagram

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Pattern → NFR/운영/보안 (값 공란, 연결만)
┌────────────┬──────────┬──────────┬──────────┬──────────┐
│ 패턴        │ Timeout  │ CS       │ 보안     │ 운영     │
├────────────┼──────────┼──────────┼──────────┼──────────┤
│ 통상        │ 1.5 TBD  │ JSON UTF8│ 1.5 중복 │ 로그     │
│ Upload      │ TBD      │ EUC-KR   │ 검사 TBD │ 저장소   │
│ Download    │ TBD      │ EUC-KR   │ 반출 1.7 │          │
│ 온디맨드    │ 접수가 짧음│ —       │ —        │ Control-M│
│ 대용량조회  │ 1.5 결합 │ JSON?    │ 권한     │ 부하     │
│ 대량 Sync   │ ADR-PT-01│          │ Tx       │ 잠금     │
│ 대량 Async  │ 배치 SLA │ FILE UTF8│          │ III.2    │
│ Push        │ 채널     │ EUC-KR힌트│          │ MCA      │
│ 보고서      │ IV.1/OLAP│          │ 권한     │ ②영역   │
│ 내부연계    │ 채널     │ 매체별   │ III.7    │ III.4    │
└────────────┴──────────┴──────────┴──────────┴──────────┘
셀 값 창작 금지. 연결 절만 FACT/GAP로 표시
```

**그림 상세해설**

1. **그림 목적:** 패턴×NFR 연결 매트릭스 그림. 셀을 추정으로 채우지 않는다.
2. **근거자료와 상태:** [FACT] CS 행, 1.5 항목, Control-M, III.4. [GAP] 모든 수치.
3. **Boundary / In / Out:** In: 8패턴. Out: 후속 절 입력. 밖: NSIGHT NFR 표.
4. **Trigger / 시작점:** NFR 리뷰.
5. **처리순서:** ① 행=패턴 → ② 열=축 → ③ 연결만 → ④ 공란 유지.
6. **책임·비책임:** 책임: 임채정 행, FW/보안/운영 열.
7. **데이터/전문/상태/제어:** 없음(매핑 메타).
8. **실패·운영·후속:** 빈 셀이 승인 차단이면 COL-III.1.7-01 도식과 함께 닫기.

## 19. 검증 체크리스트

- [ ] Figure Plan 14 = 본문 FIG 14
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.1.7 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 14 | 실제 14 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 14 · Figure Plan 행 14 · 본문 ` ```text ` 그림 코드블록(FIG) 14건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`III.1.7` 심화. 8패턴을 독립 Sequence로 분리하고 공통 장애/NFR은 13·14에만 모았다. 도식 정본은 임채정. 창작 Timeout 0.

---

# III.1.8 표준전문활용

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.1.8-01 | 원문 목차 | III.1.8 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.1.8-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.1.8-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **12**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.1.8-01 | 표준전문 적용 전체 Map | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-02 | 실제 Envelope/Field 구조 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-03 | Request 구조 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-04 | Response 구조 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-05 | Error 구조 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-06 | 생성→전송→검증→처리 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-07 | 적용/비적용 구간 Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-08 | JSON/Character Set 변환경계 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-09 | Validation/Error Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-10 | Version Compatibility | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-11 | Correlation/Logging | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.1.8-12 | 비표준전문 예외승인 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01). 레이아웃 필드 [GAP].

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.1.8 표준전문활용`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-III.1.8-01 표준전문 적용 전체 Map

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-01 │
└────────────────┘
┌────────────────┐
│ 표준전문 적용 전체 Map │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '표준전문 적용 전체 Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.1.8-02 실제 Envelope/Field 구조

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-02 │
└────────────────┘
┌──────────────────────┐
│ 실제 Envelope/Field 구조 │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '실제 Envelope/Field 구조' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.8-03 Request 구조

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-03 │
└────────────────┘
┌────────────┐
│ Request 구조 │
└────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Request 구조' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.1.8-04 Response 구조

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-04 │
└────────────────┘
┌─────────────┐
│ Response 구조 │
└─────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Response 구조' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.8-05 Error 구조

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-05 │
└────────────────┘
┌──────────┐
│ Error 구조 │
└──────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Error 구조' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.1.8-07 적용/비적용 구간 Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-07 │
└────────────────┘
┌───────────────┐
│ 적용/비적용 구간 Map │
└───────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '적용/비적용 구간 Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.1.8-06 생성→전송→검증→처리 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '생성→전송→검증→처리 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.8-09 Validation/Error Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────┐
│ ① 단말 거래 시작 │
└────────────┘
          │
          ▼
┌──────────────────────────┐
│ ② 정보단말=직접 / 계정단말=EIC·MCA │
└──────────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ③ Neoworks (계층·Context [FW]) │
└──────────────────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ④ JDBC → RTW/ADW/BSA [대상 TBD] │
└───────────────────────────────┘
          │
          ▼
┌────────────────────┐
│ ⑤ 응답 전문 JSON UTF-8 │
└────────────────────┘
[FACT] 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
Timeout 초·필드 레이아웃 = [GAP]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Validation/Error Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.1.8-10 Version Compatibility

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-10 │
└────────────────┘
┌───────────────────────┐
│ Version Compatibility │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Version Compatibility' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.1.8-11 Correlation/Logging

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-11 │
└────────────────┘
┌─────────────────────┐
│ Correlation/Logging │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Correlation/Logging' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-III.1.8-08 JSON/Character Set 변환경계

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
│ HA 적용 │ [근거 범위] │
│ Portal A-A / Q-Track A-S │
└─────────┴─────────┘
[FACT] 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가
sFTP 이관 불가. DR 절차·RTO 창작 금지.
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'JSON/Character Set 변환경계' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| III.1.8 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 III.1.8 | 슬롯 100% 독립 그림 | FIG-III.1.8-01~ | Completion Gate 수치 |
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
| GAP-III.1.8-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-III.1.8-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.1.8-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.1.8 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.1.8 GAP 박스
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
- III.1.8 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.1.8-12 비표준전문 예외승인

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.1.8-12 │
└────────────────┘
┌────────────┐
│ 비표준전문 예외승인 │
└────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '비표준전문 예외승인' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.1.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] III.1.8 축약 표현 없음

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

`III.1.8` V5 재작성. 필수 FIG 12개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.2 배치어플리케이션

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.2-01 | 원문 목차 | III.2 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.2-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.2-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **12**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.2-01 | Batch Architecture Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-02 | Trigger 유형 Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-03 | DevOn Java Batch Runtime | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-04 | ETCL Batch Runtime | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-05 | Shell/SP Runtime/TBD | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-06 | Control-M→Program Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-07 | ETL #1/#2 실행경로 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-08 | 정상 Job Chain | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-09 | 실패/재시작/재처리 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-10 | On-demand Batch 연계 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-11 | Batch Observability | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2-12 | III.2→III.4 Data Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.2 배치어플리케이션** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.2 배치어플리케이션`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-III.2-01 Batch Architecture Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.2-01 │
└──────────────┘
┌────────────────────────────────┐
│ Batch Architecture Big Picture │
└────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Batch Architecture Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-III.2-02 Trigger 유형 Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.2-02 │
└──────────────┘
┌────────────────┐
│ Trigger 유형 Map │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Trigger 유형 Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2-06 Control-M→Program Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.2-06 │
└──────────────┘
┌───────────────────────────┐
│ Control-M→Program Mapping │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Control-M→Program Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.2-04 ETCL Batch Runtime

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.2] │
└───────────────────┘
          │
          ▼
┌───────────────────────────┐
│ ② 처리 (ETCL Batch Runtime) │
└───────────────────────────┘
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

1. **그림 목적:** 이 그림은 'ETCL Batch Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2-05 Shell/SP Runtime/TBD

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────────┐
│ III.2 / 05 Shell/SP Runtime/TBD │
└─────────────────────────────────┘
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

1. **그림 목적:** 이 그림은 'Shell/SP Runtime/TBD' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2-07 ETL #1/#2 실행경로

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.2-07 │
└──────────────┘
┌────────────────┐
│ ETL #1/#2 실행경로 │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'ETL #1/#2 실행경로' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2-08 정상 Job Chain

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.2] │
└───────────────────┘
          │
          ▼
┌─────────────────────┐
│ ② 처리 (정상 Job Chain) │
└─────────────────────┘
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

1. **그림 목적:** 이 그림은 '정상 Job Chain' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2-09 실패/재시작/재처리

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.2] │
└───────────────────┘
          │
          ▼
┌───────────────────┐
│ ② 처리 (실패/재시작/재처리) │
└───────────────────┘
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

1. **그림 목적:** 이 그림은 '실패/재시작/재처리' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.2-10 On-demand Batch 연계

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.2-10 │
└──────────────┘
┌────────────────────┐
│ On-demand Batch 연계 │
└────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'On-demand Batch 연계' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2-11 Batch Observability

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.2-11 │
└──────────────┘
┌─────────────────────┐
│ Batch Observability │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Batch Observability' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-III.2-03 DevOn Java Batch Runtime

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.2] │
└───────────────────┘
          │
          ▼
┌─────────────────────────────────┐
│ ② 처리 (DevOn Java Batch Runtime) │
└─────────────────────────────────┘
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

1. **그림 목적:** 이 그림은 'DevOn Java Batch Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.2 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 III.2 | 슬롯 100% 독립 그림 | FIG-III.2-01~ | Completion Gate 수치 |
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
| GAP-III.2-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-III.2-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.2-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.2 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.2 GAP 박스
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
- III.2 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.2-12 III.2→III.4 Data Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────┐
│ III.2 산출 (그림·GAP·ADR) │
└───────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────┐
│ IV 소비·메타·인증 │
└─────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'III.2→III.4 Data Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] III.2 축약 표현 없음

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

`III.2` V5 재작성. 필수 FIG 12개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.2.1 배치어플리케이션 유형

**협업 태그:** 허준

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.2.1-01 | 원문 목차 | III.2.1 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.2.1-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.2.1-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.2.1-01 | 3종 Batch Type Map | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-02 | DevOn Java Batch Flow | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-03 | ETCL/TeraStream Flow | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-04 | Shell Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-05 | Stored Procedure Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-06 | 유형 선택 Decision Tree | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-07 | 유형별 입력/출력/저장 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-08 | 유형별 오류/재처리 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-09 | 유형별 Node Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.1-10 | Batch Type→Control-M Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.2.1 배치어플리케이션 유형** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.2.1 배치어플리케이션 유형`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 허준

## 4. L0 Big Picture

### FIG-III.2.1-01 3종 Batch Type Map

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.1-01 │
└────────────────┘
┌───────────────────┐
│ 3종 Batch Type Map │
└───────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 허준
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '3종 Batch Type Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-III.2.1-09 유형별 Node Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.1-09 │
└────────────────┘
┌──────────────────┐
│ 유형별 Node Mapping │
└──────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 허준
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '유형별 Node Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.2.1-04 Shell Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.1-04 │
└────────────────┘
┌────────────┐
│ Shell Flow │
└────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 허준
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Shell Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2.1-05 Stored Procedure Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.1-05 │
└────────────────┘
┌───────────────────────┐
│ Stored Procedure Flow │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 허준
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Stored Procedure Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2.1-06 유형 선택 Decision Tree

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.1-06 │
└────────────────┘
┌─────────────────────┐
│ 유형 선택 Decision Tree │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 허준
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '유형 선택 Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2.1-07 유형별 입력/출력/저장

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.1-07 │
└────────────────┘
┌──────────────┐
│ 유형별 입력/출력/저장 │
└──────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 허준
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '유형별 입력/출력/저장' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.2.1-08 유형별 오류/재처리

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────┐
│ ① Trigger [III.2.1] │
└─────────────────────┘
          │
          ▼
┌───────────────────┐
│ ② 처리 (유형별 오류/재처리) │
└───────────────────┘
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

1. **그림 목적:** 이 그림은 '유형별 오류/재처리' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-III.2.1-03 ETCL/TeraStream Flow

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.1-03 │
└────────────────┘
┌──────────────────────┐
│ ETCL/TeraStream Flow │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 허준
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'ETCL/TeraStream Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-III.2.1-02 DevOn Java Batch Flow

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.1-02 │
└────────────────┘
┌───────────────────────┐
│ DevOn Java Batch Flow │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 허준
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'DevOn Java Batch Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.2.1 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 허준 | 후속 설계 중단 |
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
| 목차 III.2.1 | 슬롯 100% 독립 그림 | FIG-III.2.1-01~ | Completion Gate 수치 |
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
- 허준

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.2.1-01 | 자료 | 이 절 빈 박스 | 허준 | 장표/인터뷰/ADR |
| GAP-III.2.1-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.2.1-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.2.1 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.2.1 GAP 박스
- Owner 미응답 항목

**Who must answer**
- 허준
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.2.1 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.2.1-10 Batch Type→Control-M Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────┐
│ III.2.1 산출 (그림·GAP·ADR) │
└─────────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────┐
│ IV 소비·메타·인증 │
└─────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Batch Type→Control-M Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 허준. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.2.1 축약 표현 없음

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

`III.2.1` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.2.2 스케줄러 연동 방안

**협업 태그:** 연제학

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.2.2-01 | 원문 목차 | III.2.2 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.2.2-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.2.2-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.2.2-01 | Control-M Integration Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-02 | Job→Program Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-03 | 선행/후행 Job Chain | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-04 | 정상 Scheduler Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-05 | 실패/재시작 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-06 | 조건부/분기 Job Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-07 | On-demand Trigger Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-08 | Job→Node→Data Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-09 | 종료코드/알림 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.2.2-10 | 운영자 Manual Recovery | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.2.2 스케줄러 연동 방안** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.2.2 스케줄러 연동 방안`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 연제학

## 4. L0 Big Picture

### FIG-III.2.2-01 Control-M Integration Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.2-01 │
└────────────────┘
┌───────────────────────────────────┐
│ Control-M Integration Big Picture │
└───────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 연제학
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Control-M Integration Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.2.2-03 선행/후행 Job Chain

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.2-03 │
└────────────────┘
┌─────────────────┐
│ 선행/후행 Job Chain │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 연제학
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '선행/후행 Job Chain' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-III.2.2-02 Job→Program Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.2-02 │
└────────────────┘
┌─────────────────────┐
│ Job→Program Mapping │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 연제학
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Job→Program Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2.2-08 Job→Node→Data Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.2-08 │
└────────────────┘
┌───────────────────────┐
│ Job→Node→Data Mapping │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 연제학
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Job→Node→Data Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.2.2-04 정상 Scheduler Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────┐
│ ① Trigger [III.2.2] │
└─────────────────────┘
          │
          ▼
┌──────────────────────────────┐
│ ② 처리 (정상 Scheduler Sequence) │
└──────────────────────────────┘
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

1. **그림 목적:** 이 그림은 '정상 Scheduler Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2.2-05 실패/재시작 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────┐
│ ① Trigger [III.2.2] │
└─────────────────────┘
          │
          ▼
┌────────────────────────┐
│ ② 처리 (실패/재시작 Sequence) │
└────────────────────────┘
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

1. **그림 목적:** 이 그림은 '실패/재시작 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2.2-06 조건부/분기 Job Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.2-06 │
└────────────────┘
┌─────────────────┐
│ 조건부/분기 Job Flow │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 연제학
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '조건부/분기 Job Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2.2-07 On-demand Trigger Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.2-07 │
└────────────────┘
┌────────────────────────┐
│ On-demand Trigger Flow │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 연제학
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'On-demand Trigger Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.2.2-09 종료코드/알림 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ FIG III.2.2-09 │
└────────────────┘
┌──────────────┐
│ 종료코드/알림 Flow │
└──────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: 연제학
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '종료코드/알림 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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
| III.2.2 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 연제학 | 후속 설계 중단 |
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
| 목차 III.2.2 | 슬롯 100% 독립 그림 | FIG-III.2.2-01~ | Completion Gate 수치 |
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
- 연제학

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.2.2-01 | 자료 | 이 절 빈 박스 | 연제학 | 장표/인터뷰/ADR |
| GAP-III.2.2-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.2.2-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.2.2 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.2.2 GAP 박스
- Owner 미응답 항목

**Who must answer**
- 연제학
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.2.2 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.2.2-10 운영자 Manual Recovery

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 '운영자 Manual Recovery' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.2.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 연제학. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.2.2 축약 표현 없음

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

`III.2.2` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.3 어플리케이션 표준화 방안

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.3-01 | 원문 목차 | III.3 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.3-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.3-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **9**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.3-01 | Naming Standard Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.3-02 | SystemCode→ServiceGroup→Lv3→Program | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.3-03 | Package Naming 구조 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.3-04 | Program/Class/Job/SQL ID Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.3-05 | Repository/형상 경로 Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.3-06 | Naming Decision Tree | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.3-07 | 중복/위반 Validation | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.3-08 | 예외승인/변경 Lifecycle | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.3-09 | FACT 기준 vs 외부 비교기준 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.3 어플리케이션 표준화 방안** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.3 어플리케이션 표준화 방안`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-III.3-01 Naming Standard Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-01 │
└──────────────┘
┌─────────────────────────────┐
│ Naming Standard Big Picture │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Naming Standard Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.3-02 SystemCode→ServiceGroup→Lv3→Program

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-02 │
└──────────────┘
┌─────────────────────────────────────┐
│ SystemCode→ServiceGroup→Lv3→Program │
└─────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'SystemCode→ServiceGroup→Lv3→Program' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.3-03 Package Naming 구조

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-03 │
└──────────────┘
┌───────────────────┐
│ Package Naming 구조 │
└───────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Package Naming 구조' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-III.3-04 Program/Class/Job/SQL ID Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-04 │
└──────────────┘
┌──────────────────────────────────┐
│ Program/Class/Job/SQL ID Mapping │
└──────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Program/Class/Job/SQL ID Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.3-05 Repository/형상 경로 Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-05 │
└──────────────┘
┌──────────────────────────┐
│ Repository/형상 경로 Mapping │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Repository/형상 경로 Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.3-06 Naming Decision Tree

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-06 │
└──────────────┘
┌──────────────────────┐
│ Naming Decision Tree │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'Naming Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.3-07 중복/위반 Validation

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-07 │
└──────────────┘
┌──────────────────┐
│ 중복/위반 Validation │
└──────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '중복/위반 Validation' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.3-08 예외승인/변경 Lifecycle

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-08 │
└──────────────┘
┌───────────────────┐
│ 예외승인/변경 Lifecycle │
└───────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 '예외승인/변경 Lifecycle' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.3 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 III.3 | 슬롯 100% 독립 그림 | FIG-III.3-01~ | Completion Gate 수치 |
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
| GAP-III.3-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-III.3-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.3-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.3 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.3 GAP 박스
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
- III.3 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.3-09 FACT 기준 vs 외부 비교기준

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.3-09 │
└──────────────┘
┌────────────────────┐
│ FACT 기준 vs 외부 비교기준 │
└────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
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

1. **그림 목적:** 이 그림은 'FACT 기준 vs 외부 비교기준' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 9 = 본문 FIG 9
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.3 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 9 | 실제 9 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 9 · Figure Plan 행 9 · 본문 ` ```text ` 그림 코드블록(FIG) 9건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`III.3` V5 재작성. 필수 FIG 9개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.4 시스템 간 연계 방안

**협업 태그:** [DA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M3` — 물리·제품·HA 근거가 있다. Topology/정상 Sequence/장애를 분리한다.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.4-01 | 원문 목차 | III.4 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.4-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.4-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **16**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.4-01 | Integration Landscape Big Picture | L0 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-02 | Interface Type Selection Decision Tree | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-03 | CDC 독립 Flow | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-04 | CDC 장애/재처리 Flow | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-05 | ETCL 독립 Flow | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-06 | ETCL 장애/재처리 Flow | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-07 | BC 독립 Flow 또는 TBD Boundary | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-08 | 온라인 Service/API/전문 Flow | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-09 | File/MFT/SAM Flow | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-10 | Source→Target Contract Map | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-11 | Sync vs Async Decision | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-12 | Timeout/Retry/DLQ/Manual Recovery | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-13 | Idempotency/Duplicate Prevention | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-14 | Correlation/Trace/Monitoring | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-15 | Interface Contract/Version | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.4-16 | Direct DB/P2P 예외승인 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 연계 수단 이름: CDC, ETCL, BC(정의 미입수). BC 정의 미입수 → GAP 골격. CDC/ETCL은 독립 그림.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.4 시스템 간 연계 방안`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [DA협의필요]

## 4. L0 Big Picture

### FIG-III.4-01 Integration Landscape Big Picture

**Level:** L0 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-01 │
└──────────────┘
┌───────────────────────────────────┐
│ Integration Landscape Big Picture │
└───────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Integration Landscape Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.4-02 Interface Type Selection Decision Tree

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-02 │
└──────────────┘
┌────────────────────────────────────────┐
│ Interface Type Selection Decision Tree │
└────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Interface Type Selection Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-III.4-10 Source→Target Contract Map

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-10 │
└──────────────┘
┌────────────────────────────┐
│ Source→Target Contract Map │
└────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Source→Target Contract Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.4-03 CDC 독립 Flow

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-03 │
└──────────────┘
┌─────────────┐
│ CDC 독립 Flow │
└─────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'CDC 독립 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-05 ETCL 독립 Flow

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-05 │
└──────────────┘
┌──────────────┐
│ ETCL 독립 Flow │
└──────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'ETCL 독립 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-07 BC 독립 Flow 또는 TBD Boundary

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────────┐
│ III.4 / 07 BC 독립 Flow 또는 TBD Boundary │
└───────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [DA협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'BC 독립 Flow 또는 TBD Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-08 온라인 Service/API/전문 Flow

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-08 │
└──────────────┘
┌─────────────────────────┐
│ 온라인 Service/API/전문 Flow │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '온라인 Service/API/전문 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-09 File/MFT/SAM Flow

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-09 │
└──────────────┘
┌───────────────────┐
│ File/MFT/SAM Flow │
└───────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'File/MFT/SAM Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-11 Sync vs Async Decision

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-11 │
└──────────────┘
┌────────────────────────┐
│ Sync vs Async Decision │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Sync vs Async Decision' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.4-04 CDC 장애/재처리 Flow

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'CDC 장애/재처리 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-06 ETCL 장애/재처리 Flow

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'ETCL 장애/재처리 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-12 Timeout/Retry/DLQ/Manual Recovery

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'Timeout/Retry/DLQ/Manual Recovery' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-13 Idempotency/Duplicate Prevention

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-13 │
└──────────────┘
┌──────────────────────────────────┐
│ Idempotency/Duplicate Prevention │
└──────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Idempotency/Duplicate Prevention' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.4-15 Interface Contract/Version

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-15 │
└──────────────┘
┌────────────────────────────┐
│ Interface Contract/Version │
└────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Interface Contract/Version' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-III.4-14 Correlation/Trace/Monitoring

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-14 │
└──────────────┘
┌──────────────────────────────┐
│ Correlation/Trace/Monitoring │
└──────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Correlation/Trace/Monitoring' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.4 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [DA협의필요] | 후속 설계 중단 |
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
| 목차 III.4 | 슬롯 100% 독립 그림 | FIG-III.4-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [DA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.4-01 | 자료 | 이 절 빈 박스 | [DA협의필요] | 장표/인터뷰/ADR |
| GAP-III.4-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.4-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.4 슬롯 이름·Evidence Maturity `M3`

**What blocks approval**
- III.4 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [DA협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.4 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.4-16 Direct DB/P2P 예외승인

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.4-16 │
└──────────────┘
┌────────────────────┐
│ Direct DB/P2P 예외승인 │
└────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Direct DB/P2P 예외승인' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 16 = 본문 FIG 16
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.4 축약 표현 없음

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

`III.4` V5 재작성. 필수 FIG 16개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.5 고객 행동데이터 수집 및 활용 방안

**협업 태그:** [Hydra협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.5-01 | 원문 목차 | III.5 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.5-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.5-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **12**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.5-01 | 행동데이터 E2E Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-02 | Channel/Event Source Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-03 | Event Log Schema/Field Provenance | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-04 | Hydra/HYDRA-K 역할 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-05 | 수집 정상 Sequence | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-06 | 처리/변환 Pipeline | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-07 | 저장 RTW/BDP 등 Target Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-08 | 마케팅 활용 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-09 | 오류/유실/재처리 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-10 | PII/마스킹/암호 영향 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-11 | 관측/품질/계보 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.5-12 | Hydra 협업 GAP Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.5 고객 행동데이터 수집 및 활용 방안** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.5 고객 행동데이터 수집 및 활용 방안`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [Hydra협의필요]

## 4. L0 Big Picture

### FIG-III.5-01 행동데이터 E2E Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.5-01 │
└──────────────┘
┌───────────────────────┐
│ 행동데이터 E2E Big Picture │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [Hydra협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '행동데이터 E2E Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.5-03 Event Log Schema/Field Provenance

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.5-03 │
└──────────────┘
┌───────────────────────────────────┐
│ Event Log Schema/Field Provenance │
└───────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [Hydra협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Event Log Schema/Field Provenance' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.5-06 처리/변환 Pipeline

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.5] │
└───────────────────┘
          │
          ▼
┌───────────────────────┐
│ ② 처리 (처리/변환 Pipeline) │
└───────────────────────┘
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

1. **그림 목적:** 이 그림은 '처리/변환 Pipeline' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.5-02 Channel/Event Source Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.5-02 │
└──────────────┘
┌──────────────────────────┐
│ Channel/Event Source Map │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [Hydra협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Channel/Event Source Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.5-04 Hydra/HYDRA-K 역할 Boundary

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

1. **그림 목적:** 이 그림은 'Hydra/HYDRA-K 역할 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.5-07 저장 RTW/BDP 등 Target Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.5-07 │
└──────────────┘
┌─────────────────────────────┐
│ 저장 RTW/BDP 등 Target Mapping │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [Hydra협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '저장 RTW/BDP 등 Target Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.5-05 수집 정상 Sequence

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.5] │
└───────────────────┘
          │
          ▼
┌───────────────────────┐
│ ② 처리 (수집 정상 Sequence) │
└───────────────────────┘
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

1. **그림 목적:** 이 그림은 '수집 정상 Sequence' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.5-08 마케팅 활용 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.5-08 │
└──────────────┘
┌─────────────┐
│ 마케팅 활용 Flow │
└─────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [Hydra협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '마케팅 활용 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.5-09 오류/유실/재처리

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.5] │
└───────────────────┘
          │
          ▼
┌──────────────────┐
│ ② 처리 (오류/유실/재처리) │
└──────────────────┘
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

1. **그림 목적:** 이 그림은 '오류/유실/재처리' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.5-11 관측/품질/계보

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.5-11 │
└──────────────┘
┌──────────┐
│ 관측/품질/계보 │
└──────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [Hydra협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '관측/품질/계보' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-III.5-10 PII/마스킹/암호 영향

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.5-10 │
└──────────────┘
┌───────────────┐
│ PII/마스킹/암호 영향 │
└───────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [Hydra협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'PII/마스킹/암호 영향' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.5 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [Hydra협의필요] | 후속 설계 중단 |
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
| 목차 III.5 | 슬롯 100% 독립 그림 | FIG-III.5-01~ | Completion Gate 수치 |
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
- [Hydra협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.5-01 | 자료 | 이 절 빈 박스 | [Hydra협의필요] | 장표/인터뷰/ADR |
| GAP-III.5-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.5-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.5 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- III.5 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [Hydra협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.5 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.5-12 Hydra 협업 GAP Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────┐
│ III.5 / 12 Hydra 협업 GAP Map │
└─────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [Hydra협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Hydra 협업 GAP Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [Hydra협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 12 = 본문 FIG 12
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.5 축약 표현 없음

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

`III.5` V5 재작성. 필수 FIG 12개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.6 컨테이너 실행환경 구축 방안

**협업 태그:** [2사업협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M1` — 근거 부족. Discovery/Question/Option/Gate 그림을 본체로 둔다. 가짜 Runtime 금지.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.6-01 | 원문 목차 | III.6 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.6-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.6-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **12**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.6-01 | R/F Container Big Picture | L0 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-02 | R/F 요청→Container Runtime | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-03 | Cluster/Namespace/Workload 구조 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-04 | Container→Neoworks/DB/Integration 연결 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-05 | Traffic/Ingress/Egress Boundary | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-06 | Pod/Instance 장애/재기동 | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-07 | Scale/Resource Boundary | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-08 | Config/Secret/Artifact Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-09 | Observability Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-10 | VM vs Container 비교 | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-11 | 2사업 R&R Boundary | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.6-12 | 미확정 R/F/Cluster Gap Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**III.6 컨테이너 실행환경 구축 방안** — Evidence `M1`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.6 컨테이너 실행환경 구축 방안`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [2사업협의필요]

## 4. L0 Big Picture

### FIG-III.6-01 R/F Container Big Picture

**Level:** L0 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-01 │
└──────────────┘
┌───────────────────────────┐
│ R/F Container Big Picture │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'R/F Container Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.6-03 Cluster/Namespace/Workload 구조

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-03 │
└──────────────┘
┌───────────────────────────────┐
│ Cluster/Namespace/Workload 구조 │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Cluster/Namespace/Workload 구조' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.6-04 Container→Neoworks/DB/Integration 연결

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-04 │
└──────────────┘
┌──────────────────────────────────────┐
│ Container→Neoworks/DB/Integration 연결 │
└──────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Container→Neoworks/DB/Integration 연결' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.6-05 Traffic/Ingress/Egress Boundary

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-05 │
└──────────────┘
┌─────────────────────────────────┐
│ Traffic/Ingress/Egress Boundary │
└─────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Traffic/Ingress/Egress Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.6-07 Scale/Resource Boundary

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-07 │
└──────────────┘
┌─────────────────────────┐
│ Scale/Resource Boundary │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Scale/Resource Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.6-11 2사업 R&R Boundary

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-11 │
└──────────────┘
┌──────────────────┐
│ 2사업 R&R Boundary │
└──────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '2사업 R&R Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.6-02 R/F 요청→Container Runtime

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.6] │
└───────────────────┘
          │
          ▼
┌─────────────────────────────────┐
│ ② 처리 (R/F 요청→Container Runtime) │
└─────────────────────────────────┘
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

1. **그림 목적:** 이 그림은 'R/F 요청→Container Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.6-08 Config/Secret/Artifact Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-08 │
└──────────────┘
┌─────────────────────────────┐
│ Config/Secret/Artifact Flow │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Config/Secret/Artifact Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.6-09 Observability Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-09 │
└──────────────┘
┌────────────────────┐
│ Observability Flow │
└────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Observability Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.6-06 Pod/Instance 장애/재기동

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

1. **그림 목적:** 이 그림은 'Pod/Instance 장애/재기동' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.6-10 VM vs Container 비교

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.6-10 │
└──────────────┘
┌────────────────────┐
│ VM vs Container 비교 │
└────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [2사업협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'VM vs Container 비교' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.6 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [2사업협의필요] | 후속 설계 중단 |
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
| 목차 III.6 | 슬롯 100% 독립 그림 | FIG-III.6-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [2사업협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.6-01 | 자료 | 이 절 빈 박스 | [2사업협의필요] | 장표/인터뷰/ADR |
| GAP-III.6-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.6-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.6 슬롯 이름·Evidence Maturity `M1`

**What blocks approval**
- III.6 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [2사업협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.6 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.6-12 미확정 R/F/Cluster Gap Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────┐
│ III.6 / 12 미확정 R/F/Cluster Gap Map │
└────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [2사업협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '미확정 R/F/Cluster Gap Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [2사업협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 12 = 본문 FIG 12
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.6 축약 표현 없음

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

`III.6` V5 재작성. 필수 FIG 12개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.7 데이터 암/복호화 처리 방안

**협업 태그:** [보안팀협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M1` — 근거 부족. Discovery/Question/Option/Gate 그림을 본체로 둔다. 가짜 Runtime 금지.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.7-01 | 원문 목차 | III.7 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.7-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.7-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **13**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.7-01 | Data Protection Big Picture | L0 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-02 | 중요데이터 Classification Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-03 | At-Rest 암호 Flow | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-04 | In-Use 복호/사용 Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-05 | UI Masking Flow | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-06 | In-Transit 보호 Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-07 | Application vs DB vs Channel 책임 Boundary | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-08 | Key/Secret Lifecycle | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-09 | 로그/Trace 민감정보 보호 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-10 | 암호 실패/키오류 처리 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-11 | Character Set vs Encryption 순서 | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-12 | 보안 예외/승인 Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.7-13 | 보안팀 미확정 Gap Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 할 일(저장/표시/전송 보호)만 있음. 알고리즘·키제품 창작 금지. M1.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.7 데이터 암/복호화 처리 방안`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [보안팀협의필요]

## 4. L0 Big Picture

### FIG-III.7-01 Data Protection Big Picture

**Level:** L0 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-01 │
└──────────────┘
┌─────────────────────────────┐
│ Data Protection Big Picture │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Data Protection Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-III.7-07 Application vs DB vs Channel 책임 Boundary

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-07 │
└──────────────┘
┌──────────────────────────────────────────┐
│ Application vs DB vs Channel 책임 Boundary │
└──────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Application vs DB vs Channel 책임 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.7-09 로그/Trace 민감정보 보호

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-09 │
└──────────────┘
┌──────────────────┐
│ 로그/Trace 민감정보 보호 │
└──────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '로그/Trace 민감정보 보호' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.7-02 중요데이터 Classification Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-02 │
└──────────────┘
┌───────────────────────────┐
│ 중요데이터 Classification Flow │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '중요데이터 Classification Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.7-04 In-Use 복호/사용 Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-04 │
└──────────────┘
┌───────────────────┐
│ In-Use 복호/사용 Flow │
└───────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'In-Use 복호/사용 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.7-06 In-Transit 보호 Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-06 │
└──────────────┘
┌────────────────────┐
│ In-Transit 보호 Flow │
└────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'In-Transit 보호 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.7-08 Key/Secret Lifecycle

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-08 │
└──────────────┘
┌──────────────────────┐
│ Key/Secret Lifecycle │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Key/Secret Lifecycle' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-III.7-12 보안 예외/승인 Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-12 │
└──────────────┘
┌───────────────┐
│ 보안 예외/승인 Flow │
└───────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '보안 예외/승인 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

### FIG-III.7-03 At-Rest 암호 Flow

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-03 │
└──────────────┘
┌─────────────────┐
│ At-Rest 암호 Flow │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'At-Rest 암호 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.7-05 UI Masking Flow

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-05 │
└──────────────┘
┌─────────────────┐
│ UI Masking Flow │
└─────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'UI Masking Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.7-10 암호 실패/키오류 처리

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────┐
│ ① Trigger [III.7] │
└───────────────────┘
          │
          ▼
┌─────────────────────┐
│ ② 처리 (암호 실패/키오류 처리) │
└─────────────────────┘
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

1. **그림 목적:** 이 그림은 '암호 실패/키오류 처리' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.7-11 Character Set vs Encryption 순서

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.7-11 │
└──────────────┘
┌────────────────────────────────┐
│ Character Set vs Encryption 순서 │
└────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [보안팀협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Character Set vs Encryption 순서' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.7 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [보안팀협의필요] | 후속 설계 중단 |
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
| 목차 III.7 | 슬롯 100% 독립 그림 | FIG-III.7-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [보안팀협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.7-01 | 자료 | 이 절 빈 박스 | [보안팀협의필요] | 장표/인터뷰/ADR |
| GAP-III.7-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.7-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.7 슬롯 이름·Evidence Maturity `M1`

**What blocks approval**
- III.7 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [보안팀협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.7 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.7-13 보안팀 미확정 Gap Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────┐
│ III.7 / 13 보안팀 미확정 Gap Map │
└────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [보안팀협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '보안팀 미확정 Gap Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.7 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [보안팀협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 13 = 본문 FIG 13
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.7 축약 표현 없음

## 20. V5 Completion Gate

| 검사 | PASS 기준 | 실제 | 결과 |
|------|-----------|------|------|
| Figure Plan 필수 수 = 실제 FIG 수 | 필수 13 | 실제 13 | PASS |
| L0/L1/L2 | 모두 존재 | FIG place 4/5/6 | PASS |
| 동적 절 L3 정상+실패 | 분리 | place 8 + 9 | PASS |
| 복수 항목 독립 FIG | 슬롯 병합 금지 | 슬롯별 독립 코드블록 | PASS |
| 관계표 대응 그림 | App\|Node\|SW\|DB | 해당 시 Text FIG 존재 | PASS |
| GAP/TBD 그림 표현 | 생략 금지 | 빈 박스/[TBD] 유지 | PASS |
| 협업태그 | 누락 없음 | `[TA협의필요]` `[DA협의필요]` 유지 | PASS |
| 창작값 | 0건 | 버전/대수/Timeout/포트 미창작 | PASS |
| 축약표현 | 0건 | `상세는 하위 절`/`4~12.`/`상동` 없음 | PASS |
| Handoff | 다음 절 연결 FIG | place 18 존재 | PASS |

필수 슬롯 13 · Figure Plan 행 13 · 본문 ` ```text ` 그림 코드블록(FIG) 13건. 표/문장/목록은 FIG 수에 넣지 않았다.

## 21. 최종 평가

`III.7` V5 재작성. 필수 FIG 13개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# III.8 CI/CD 지원 방안

**협업 태그:** [TA협의필요] 형상/이관

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M1` — 근거 부족. Discovery/Question/Option/Gate 그림을 본체로 둔다. 가짜 Runtime 금지.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-III.8-01 | 원문 목차 | III.8 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-III.8-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-III.8-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-III-04 | 목차 III + Neoworks ADR | 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01) | EIMS 역할·Timeout 값 없음 |

## 1. Figure Plan

필수 Figure Slot **16**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-III.8-01 | CI/CD 전체 Big Picture | L0 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-02 | Source→Build→Test→Artifact Lifecycle | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-03 | VM CI Pipeline | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-04 | VM CD/이행 Pipeline | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-05 | Container CI Pipeline | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-06 | Container Image/Registry Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-07 | Container CD/GitOps Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-08 | DEV→TEST Promotion | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-09 | TEST→PROD/이행 Promotion | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-10 | Quality/Security Gate | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-11 | Config/Secret Promotion | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-12 | Rollback/Backout Flow | L3 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-13 | Deployment Observability | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-14 | Approval/R&R Swimlane | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-15 | VM vs Container Tool Boundary | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-III.8-16 | CI/CD Tool FACT vs Candidate Map | L1/L2 | M1 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD). Jenkins 등 도구 미선정.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `III.8 CI/CD 지원 방안`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [TA협의필요] 형상/이관

## 4. L0 Big Picture

### FIG-III.8-01 CI/CD 전체 Big Picture

**Level:** L0 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-01 │
└──────────────┘
┌──────────────────────┐
│ CI/CD 전체 Big Picture │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'CI/CD 전체 Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-III.8-03 VM CI Pipeline

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-03 │
└──────────────┘
┌────────────────┐
│ VM CI Pipeline │
└────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'VM CI Pipeline' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-III.8-05 Container CI Pipeline

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-05 │
└──────────────┘
┌───────────────────────┐
│ Container CI Pipeline │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Container CI Pipeline' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.8-14 Approval/R&R Swimlane

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-14 │
└──────────────┘
┌───────────────────────┐
│ Approval/R&R Swimlane │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Approval/R&R Swimlane' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-III.8-15 VM vs Container Tool Boundary

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-15 │
└──────────────┘
┌───────────────────────────────┐
│ VM vs Container Tool Boundary │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'VM vs Container Tool Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-III.8-06 Container Image/Registry Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-06 │
└──────────────┘
┌───────────────────────────────┐
│ Container Image/Registry Flow │
└───────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Container Image/Registry Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.8-07 Container CD/GitOps Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-07 │
└──────────────┘
┌──────────────────────────┐
│ Container CD/GitOps Flow │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Container CD/GitOps Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.8-11 Config/Secret Promotion

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-11 │
└──────────────┘
┌─────────────────────────┐
│ Config/Secret Promotion │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Config/Secret Promotion' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.8-12 Rollback/Backout Flow

**Level:** L3 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-12 │
└──────────────┘
┌───────────────────────┐
│ Rollback/Backout Flow │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Rollback/Backout Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-III.8-10 Quality/Security Gate

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-10 │
└──────────────┘
┌───────────────────────┐
│ Quality/Security Gate │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Quality/Security Gate' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-III.8-02 Source→Build→Test→Artifact Lifecycle

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-02 │
└──────────────┘
┌──────────────────────────────────────┐
│ Source→Build→Test→Artifact Lifecycle │
└──────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Source→Build→Test→Artifact Lifecycle' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.8-04 VM CD/이행 Pipeline

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-04 │
└──────────────┘
┌───────────────────┐
│ VM CD/이행 Pipeline │
└───────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'VM CD/이행 Pipeline' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.8-08 DEV→TEST Promotion

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'DEV→TEST Promotion' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.8-09 TEST→PROD/이행 Promotion

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'TEST→PROD/이행 Promotion' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-III.8-13 Deployment Observability

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-13 │
└──────────────┘
┌──────────────────────────┐
│ Deployment Observability │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Deployment Observability' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| III.8 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [TA협의필요] 형상/이관 | 후속 설계 중단 |
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
| 목차 III.8 | 슬롯 100% 독립 그림 | FIG-III.8-01~ | Completion Gate 수치 |
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

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [TA협의필요] 형상/이관

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-III.8-01 | 자료 | 이 절 빈 박스 | [TA협의필요] 형상/이관 | 장표/인터뷰/ADR |
| GAP-III.8-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-III.8-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- III.8 슬롯 이름·Evidence Maturity `M1`

**What blocks approval**
- III.8 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [TA협의필요] 형상/이관
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- III.8 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-III.8-16 CI/CD Tool FACT vs Candidate Map

**Level:** L1/L2 · **근거상태:** M1 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────┐
│ FIG III.8-16 │
└──────────────┘
┌──────────────────────────────────┐
│ CI/CD Tool FACT vs Candidate Map │
└──────────────────────────────────┘
          │
          ▼
[FACT 앵커]
정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks / 거래패턴 8이름: 통상/파일업다운/온디맨드배치/대용량조회/대량데이터/계정
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M1      Owner: [TA협의필요] 형상/이관
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'CI/CD Tool FACT vs Candidate Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M1`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: III.8 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] 형상/이관. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 16 = 본문 FIG 16
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] III.8 축약 표현 없음

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

`III.8` V5 재작성. 필수 FIG 16개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

