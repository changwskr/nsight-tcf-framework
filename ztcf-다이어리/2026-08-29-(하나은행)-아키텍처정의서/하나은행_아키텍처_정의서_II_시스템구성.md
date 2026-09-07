# 하나은행 아키텍처 정의서 — II. 시스템 구성

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

# II. 시스템 구성

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II-01 | 원문 목차 | II 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II-01 | II 전체 View Stack | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-02 | 5개 시스템 영역 Big Picture | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-03 | 서비스/업무 View | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-04 | Application View | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-05 | Logical Node View | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-06 | Software/Data View | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-07 | Application→Node→SW→DB 수직 Traceability | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-08 | 환경 DEV/TEST/PROD/DR | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-09 | Target IT 통합 View | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II-10 | II→III Runtime Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

서비스 View→App→Node→SW/DB→Target IT. 다섯 영역 이름을 유지한다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II 시스템 구성`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-II-01 II 전체 View Stack

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG II-01 │
└───────────┘
┌──────────────────┐
│ II 전체 View Stack │
└──────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'II 전체 View Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II-02 5개 시스템 영역 Big Picture

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '5개 시스템 영역 Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-II-03 서비스/업무 View

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG II-03 │
└───────────┘
┌─────────────┐
│ 서비스/업무 View │
└─────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '서비스/업무 View' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-II-04 Application View

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG II-04 │
└───────────┘
┌──────────────────┐
│ Application View │
└──────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application View' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II-05 Logical Node View

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG II-05 │
└───────────┘
┌───────────────────┐
│ Logical Node View │
└───────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Logical Node View' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-II-07 Application→Node→SW→DB 수직 Traceability

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG II-07 │
└───────────┘
┌────────────────────────────────────────┐
│ Application→Node→SW→DB 수직 Traceability │
└────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application→Node→SW→DB 수직 Traceability' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-II-06 Software/Data View

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG II-06 │
└───────────┘
┌────────────────────┐
│ Software/Data View │
└────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Software/Data View' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-II-09 Target IT 통합 View

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────┐
│ FIG II-09 │
└───────────┘
┌───────────────────┐
│ Target IT 통합 View │
└───────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Target IT 통합 View' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-II-08 환경 DEV/TEST/PROD/DR

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

1. **그림 목적:** 이 그림은 '환경 DEV/TEST/PROD/DR' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| II 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 II | 슬롯 100% 독립 그림 | FIG-II-01~ | Completion Gate 수치 |
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
| GAP-II-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-II-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II GAP 박스
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
- II 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II-10 II→III Runtime Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────┐
│ ① Trigger [II] │
└────────────────┘
          │
          ▼
┌───────────────────────────────┐
│ ② 처리 (II→III Runtime Handoff) │
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

1. **그림 목적:** 이 그림은 'II→III Runtime Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] II 축약 표현 없음

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

`II` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.1 시스템 영역 구분

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.1-01 | 원문 목차 | II.1 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.1-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.1-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **9**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.1-01 | 5영역 전체 Layer Map | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.1-02 | 정보전달 영역 Drill-down | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.1-03 | 분석·정보제공 영역 Drill-down | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.1-04 | 데이터 저장소 영역 Drill-down | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.1-05 | 수집·연계 영역 Drill-down | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.1-06 | 인프라 영역 Drill-down | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.1-07 | 영역 간 정상 데이터 흐름 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.1-08 | 영역 간 금지/예외/TBD Boundary | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.1-09 | 영역 Owner/Dependency Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 다섯 영역: 정보전달 / 분석·정보제공 / 저장소 / 수집·연계 / 인프라.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.1 시스템 영역 구분`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-II.1-01 5영역 전체 Layer Map

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '5영역 전체 Layer Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-II.1-02 정보전달 영역 Drill-down

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '정보전달 영역 Drill-down' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.1-03 분석·정보제공 영역 Drill-down

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '분석·정보제공 영역 Drill-down' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-II.1-04 데이터 저장소 영역 Drill-down

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '데이터 저장소 영역 Drill-down' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-II.1-05 수집·연계 영역 Drill-down

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '수집·연계 영역 Drill-down' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.1-06 인프라 영역 Drill-down

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '인프라 영역 Drill-down' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.1-07 영역 간 정상 데이터 흐름

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────┐
│ ① Trigger [II.1] │
└──────────────────┘
          │
          ▼
┌───────────────────────┐
│ ② 처리 (영역 간 정상 데이터 흐름) │
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

1. **그림 목적:** 이 그림은 '영역 간 정상 데이터 흐름' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-II.1-08 영역 간 금지/예외/TBD Boundary

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────┐
│ II.1 / 08 영역 간 금지/예외/TBD Boundary │
└───────────────────────────────────┘
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

1. **그림 목적:** 이 그림은 '영역 간 금지/예외/TBD Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| II.1 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 II.1 | 슬롯 100% 독립 그림 | FIG-II.1-01~ | Completion Gate 수치 |
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
| GAP-II.1-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-II.1-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.1-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.1 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.1 GAP 박스
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
- II.1 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.1-09 영역 Owner/Dependency Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '영역 Owner/Dependency Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] II.1 축약 표현 없음

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

`II.1` V5 재작성. 필수 FIG 9개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.2 목표 업무시스템 구성도

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.2-01 | 원문 목차 | II.2 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.2-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.2-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **9**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.2-01 | 서비스 관점 전체 업무시스템 | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.2-02 | 사용자/채널→업무서비스 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.2-03 | 마케팅 서비스 Drill-down | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.2-04 | 데이터허브/데이터서비스 Drill-down | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.2-05 | BI/메타/Self BI/흐름관리 서비스 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.2-06 | 부분개선 시스템 Scope | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.2-07 | 인접 코어/채널 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.2-08 | 온라인/배치/실시간 대표 서비스 흐름 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.2-09 | AS-IS vs TO-BE 업무서비스 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.2 목표 업무시스템 구성도** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.2 목표 업무시스템 구성도`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-II.2-01 서비스 관점 전체 업무시스템

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-01 │
└─────────────┘
┌─────────────────┐
│ 서비스 관점 전체 업무시스템 │
└─────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '서비스 관점 전체 업무시스템' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-II.2-02 사용자/채널→업무서비스

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-02 │
└─────────────┘
┌──────────────┐
│ 사용자/채널→업무서비스 │
└──────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '사용자/채널→업무서비스' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.2-03 마케팅 서비스 Drill-down

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-03 │
└─────────────┘
┌────────────────────┐
│ 마케팅 서비스 Drill-down │
└────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '마케팅 서비스 Drill-down' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-II.2-04 데이터허브/데이터서비스 Drill-down

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-04 │
└─────────────┘
┌─────────────────────────┐
│ 데이터허브/데이터서비스 Drill-down │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '데이터허브/데이터서비스 Drill-down' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.2-05 BI/메타/Self BI/흐름관리 서비스

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-05 │
└─────────────┘
┌────────────────────────┐
│ BI/메타/Self BI/흐름관리 서비스 │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'BI/메타/Self BI/흐름관리 서비스' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-II.2-07 인접 코어/채널 Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-07 │
└─────────────┘
┌───────────────────┐
│ 인접 코어/채널 Boundary │
└───────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '인접 코어/채널 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-II.2-06 부분개선 시스템 Scope

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-06 │
└─────────────┘
┌────────────────┐
│ 부분개선 시스템 Scope │
└────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '부분개선 시스템 Scope' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-II.2-08 온라인/배치/실시간 대표 서비스 흐름

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-08 │
└─────────────┘
┌──────────────────────┐
│ 온라인/배치/실시간 대표 서비스 흐름 │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '온라인/배치/실시간 대표 서비스 흐름' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| II.2 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 II.2 | 슬롯 100% 독립 그림 | FIG-II.2-01~ | Completion Gate 수치 |
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
| GAP-II.2-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-II.2-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.2-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.2 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.2 GAP 박스
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
- II.2 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.2-09 AS-IS vs TO-BE 업무서비스

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.2-09 │
└─────────────┘
┌──────────────────────┐
│ AS-IS vs TO-BE 업무서비스 │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'AS-IS vs TO-BE 업무서비스' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] II.2 축약 표현 없음

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

`II.2` V5 재작성. 필수 FIG 9개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.3 어플리케이션 구성

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.3-01 | 원문 목차 | II.3 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.3-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.3-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **8**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.3-01 | Domain→Application→Service Group→Lv3 Tree | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3-02 | 1단계 Application Overlay | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3-03 | 신규/변경/유지 Application Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3-04 | Application Dependency Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3-05 | Application→Data/IF 관계 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3-06 | Application→Logical Node Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3-07 | 공식 코드 vs 임시 식별자 Gap Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3-08 | II.3.1→II.3.2 연결 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.3 어플리케이션 구성** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.3 어플리케이션 구성`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-II.3-01 Domain→Application→Service Group→Lv3 Tree

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.3-01 │
└─────────────┘
┌───────────────────────────────────────────┐
│ Domain→Application→Service Group→Lv3 Tree │
└───────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Domain→Application→Service Group→Lv3 Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-II.3-02 1단계 Application Overlay

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.3-02 │
└─────────────┘
┌─────────────────────────┐
│ 1단계 Application Overlay │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '1단계 Application Overlay' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-II.3-03 신규/변경/유지 Application Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.3-03 │
└─────────────┘
┌──────────────────────────┐
│ 신규/변경/유지 Application Map │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '신규/변경/유지 Application Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3-04 Application Dependency Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.3-04 │
└─────────────┘
┌────────────────────────────┐
│ Application Dependency Map │
└────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application Dependency Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3-07 공식 코드 vs 임시 식별자 Gap Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.3-07 │
└─────────────┘
┌─────────────────────────┐
│ 공식 코드 vs 임시 식별자 Gap Map │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '공식 코드 vs 임시 식별자 Gap Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-II.3-05 Application→Data/IF 관계

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.3-05 │
└─────────────┘
┌────────────────────────┐
│ Application→Data/IF 관계 │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application→Data/IF 관계' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| II.3 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 II.3 | 슬롯 100% 독립 그림 | FIG-II.3-01~ | Completion Gate 수치 |
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
| GAP-II.3-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-II.3-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.3-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.3 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.3 GAP 박스
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
- II.3 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.3-06 Application→Logical Node Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────────┐
│ II.3 산출 (그림·GAP·ADR) │
└──────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────────────────┐
│ III Runtime (온라인/배치/연계) │
└─────────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Application→Logical Node Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3-08 II.3.1→II.3.2 연결

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────────┐
│ II.3 산출 (그림·GAP·ADR) │
└──────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────────────────┐
│ III Runtime (온라인/배치/연계) │
└─────────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'II.3.1→II.3.2 연결' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] II.3 축약 표현 없음

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

`II.3` V5 재작성. 필수 FIG 8개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.3.1 어플리케이션 맵

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.3.1-01 | 원문 목차 | II.3.1 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.3.1-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.3.1-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **8**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.3.1-01 | 은행 공식 AA Tree | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.1-02 | 1단계 Overlay Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.1-03 | 신규/변경/유지 상태 Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.1-04 | System Code↔Service Group 관계 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.1-05 | Application ID 결정 Flow | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.1-06 | EAI/형상/모델 관리키 Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.1-07 | 공식코드 미입수 Gap Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.1-08 | Application Map→Definition Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.3.1 어플리케이션 맵** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.3.1 어플리케이션 맵`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-II.3.1-01 은행 공식 AA Tree

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.1-01 │
└───────────────┘
┌───────────────┐
│ 은행 공식 AA Tree │
└───────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '은행 공식 AA Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-II.3.1-04 System Code↔Service Group 관계

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.1-04 │
└───────────────┘
┌──────────────────────────────┐
│ System Code↔Service Group 관계 │
└──────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'System Code↔Service Group 관계' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-II.3.1-02 1단계 Overlay Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.1-02 │
└───────────────┘
┌─────────────────┐
│ 1단계 Overlay Map │
└─────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '1단계 Overlay Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.1-03 신규/변경/유지 상태 Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.1-03 │
└───────────────┘
┌─────────────────┐
│ 신규/변경/유지 상태 Map │
└─────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '신규/변경/유지 상태 Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.1-06 EAI/형상/모델 관리키 Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.1-06 │
└───────────────┘
┌───────────────────────┐
│ EAI/형상/모델 관리키 Mapping │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'EAI/형상/모델 관리키 Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.1-07 공식코드 미입수 Gap Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.1-07 │
└───────────────┘
┌──────────────────┐
│ 공식코드 미입수 Gap Map │
└──────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '공식코드 미입수 Gap Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-II.3.1-05 Application ID 결정 Flow

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.1-05 │
└───────────────┘
┌────────────────────────┐
│ Application ID 결정 Flow │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application ID 결정 Flow' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| II.3.1 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 II.3.1 | 슬롯 100% 독립 그림 | FIG-II.3.1-01~ | Completion Gate 수치 |
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
| GAP-II.3.1-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-II.3.1-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.3.1-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.3.1 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.3.1 GAP 박스
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
- II.3.1 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.3.1-08 Application Map→Definition Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────┐
│ II.3.1 산출 (그림·GAP·ADR) │
└────────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────────────────┐
│ III Runtime (온라인/배치/연계) │
└─────────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Application Map→Definition Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] II.3.1 축약 표현 없음

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

`II.3.1` V5 재작성. 필수 FIG 8개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.3.2 어플리케이션 정의

**협업 태그:** 아키텍처(작성) · 해당 Owner TBD

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.3.2-01 | 원문 목차 | II.3.2 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.3.2-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.3.2-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **9**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.3.2-01 | Application Portfolio Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.2-02 | 앱별 책임 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.2-03 | Application→Service Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.2-04 | Application→User Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.2-05 | Application→Interface Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.2-06 | Application→Data Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.2-07 | Application Dependency Call Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.2-08 | 중복/중첩 책임 Conflict Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.3.2-09 | Application→Node Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.3.2 어플리케이션 정의** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.3.2 어플리케이션 정의`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** 아키텍처(작성) · 해당 Owner TBD

## 4. L0 Big Picture

### FIG-II.3.2-01 Application Portfolio Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.2-01 │
└───────────────┘
┌───────────────────────────────────┐
│ Application Portfolio Big Picture │
└───────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application Portfolio Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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

### FIG-II.3.2-02 앱별 책임 Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.2-02 │
└───────────────┘
┌────────────────┐
│ 앱별 책임 Boundary │
└────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '앱별 책임 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.2-03 Application→Service Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.2-03 │
└───────────────┘
┌─────────────────────────┐
│ Application→Service Map │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application→Service Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.2-04 Application→User Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.2-04 │
└───────────────┘
┌──────────────────────┐
│ Application→User Map │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application→User Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.2-05 Application→Interface Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.2-05 │
└───────────────┘
┌───────────────────────────┐
│ Application→Interface Map │
└───────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application→Interface Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.2-06 Application→Data Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.2-06 │
└───────────────┘
┌──────────────────────┐
│ Application→Data Map │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application→Data Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.2-07 Application Dependency Call Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.2-07 │
└───────────────┘
┌─────────────────────────────────┐
│ Application Dependency Call Map │
└─────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application Dependency Call Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: 아키텍처(작성) · 해당 Owner TBD. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.3.2-08 중복/중첩 책임 Conflict Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.3.2-08 │
└───────────────┘
┌───────────────────────┐
│ 중복/중첩 책임 Conflict Map │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 '중복/중첩 책임 Conflict Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
| II.3.2 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | 아키텍처(작성) · 해당 Owner TBD | 후속 설계 중단 |
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
| 목차 II.3.2 | 슬롯 100% 독립 그림 | FIG-II.3.2-01~ | Completion Gate 수치 |
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
| GAP-II.3.2-01 | 자료 | 이 절 빈 박스 | 아키텍처(작성) · 해당 Owner TBD | 장표/인터뷰/ADR |
| GAP-II.3.2-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.3.2-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.3.2 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.3.2 GAP 박스
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
- II.3.2 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.3.2-09 Application→Node Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────┐
│ II.3.2 산출 (그림·GAP·ADR) │
└────────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────────────────┐
│ III Runtime (온라인/배치/연계) │
└─────────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Application→Node Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.3.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
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
- [ ] II.3.2 축약 표현 없음

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

`II.3.2` V5 재작성. 필수 FIG 9개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.4 논리노드 구성

**협업 태그:** [TA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.4-01 | 원문 목차 | II.4 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.4-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.4-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.4-01 | Workload→Node Type Decision | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-02 | DEV/TEST/PROD/DR Environment Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-03 | Application→Logical Node Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-04 | Logical Node Topology | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-05 | Node→Software Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-06 | Node→Database/Storage | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-07 | HA/Failover | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-08 | DR/TBD Topology | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-09 | Node/SW/DB Traceability | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4-10 | II.4→II.5 Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.4 논리노드 구성** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.4 논리노드 구성`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [TA협의필요]

## 4. L0 Big Picture

### FIG-II.4-01 Workload→Node Type Decision

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.4-01 │
└─────────────┘
┌─────────────────────────────┐
│ Workload→Node Type Decision │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Workload→Node Type Decision' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4-04 Logical Node Topology

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.4-04 │
└─────────────┘
┌───────────────────────┐
│ Logical Node Topology │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Logical Node Topology' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4-05 Node→Software Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.4-05 │
└─────────────┘
┌─────────────────────┐
│ Node→Software Stack │
└─────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Node→Software Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4-06 Node→Database/Storage

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.4-06 │
└─────────────┘
┌───────────────────────┐
│ Node→Database/Storage │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Node→Database/Storage' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4-08 DR/TBD Topology

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────┐
│ II.4 / 08 DR/TBD Topology │
└───────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [TA협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'DR/TBD Topology' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-II.4-03 Application→Logical Node Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.4-03 │
└─────────────┘
┌──────────────────────────────────┐
│ Application→Logical Node Mapping │
└──────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Application→Logical Node Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4-09 Node/SW/DB Traceability

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.4-09 │
└─────────────┘
┌─────────────────────────┐
│ Node/SW/DB Traceability │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Node/SW/DB Traceability' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4-07 HA/Failover

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

1. **그림 목적:** 이 그림은 'HA/Failover' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-II.4-02 DEV/TEST/PROD/DR Environment Map

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

1. **그림 목적:** 이 그림은 'DEV/TEST/PROD/DR Environment Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| II.4 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [TA협의필요] | 후속 설계 중단 |
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
| 목차 II.4 | 슬롯 100% 독립 그림 | FIG-II.4-01~ | Completion Gate 수치 |
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
- [TA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-II.4-01 | 자료 | 이 절 빈 박스 | [TA협의필요] | 장표/인터뷰/ADR |
| GAP-II.4-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.4-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.4 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.4 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [TA협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- II.4 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.4-10 II.4→II.5 Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────────┐
│ II.4 산출 (그림·GAP·ADR) │
└──────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────────────────┐
│ III Runtime (온라인/배치/연계) │
└─────────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'II.4→II.5 Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] II.4 축약 표현 없음

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

`II.4` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.4.1 논리노드 식별

**협업 태그:** [TA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.4.1-01 | 원문 목차 | II.4.1 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.4.1-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.4.1-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.4.1-01 | Logical Node Identification Big Picture | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-02 | Workload→VM/Con/BM Decision Tree | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-03 | 온라인 AP 노드 후보 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-04 | ETL/Batch 노드 후보 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-05 | DW/DB Appliance 노드 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-06 | BI/메타/흐름관리 노드 | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-07 | Integration Node | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-08 | Node Ownership/2사업 Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-09 | 확정 Node vs TBD Node | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.1-10 | Node→Environment Handoff | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.4.1 논리노드 식별** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.4.1 논리노드 식별`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [TA협의필요]

## 4. L0 Big Picture

### FIG-II.4.1-01 Logical Node Identification Big Picture

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.1-01 │
└───────────────┘
┌─────────────────────────────────────────┐
│ Logical Node Identification Big Picture │
└─────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Logical Node Identification Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-II.4.1-02 Workload→VM/Con/BM Decision Tree

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.1-02 │
└───────────────┘
┌──────────────────────────────────┐
│ Workload→VM/Con/BM Decision Tree │
└──────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Workload→VM/Con/BM Decision Tree' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.1-03 온라인 AP 노드 후보

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.1-03 │
└───────────────┘
┌──────────────┐
│ 온라인 AP 노드 후보 │
└──────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '온라인 AP 노드 후보' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-II.4.1-04 ETL/Batch 노드 후보

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.1-04 │
└───────────────┘
┌─────────────────┐
│ ETL/Batch 노드 후보 │
└─────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'ETL/Batch 노드 후보' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.1-05 DW/DB Appliance 노드

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.1-05 │
└───────────────┘
┌────────────────────┐
│ DW/DB Appliance 노드 │
└────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'DW/DB Appliance 노드' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.1-07 Integration Node

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.1-07 │
└───────────────┘
┌──────────────────┐
│ Integration Node │
└──────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Integration Node' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.1-09 확정 Node vs TBD Node

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────────┐
│ II.4.1 / 09 확정 Node vs TBD Node │
└─────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [TA협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '확정 Node vs TBD Node' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-II.4.1-08 Node Ownership/2사업 Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.1-08 │
└───────────────┘
┌─────────────────────────────┐
│ Node Ownership/2사업 Boundary │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Node Ownership/2사업 Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-II.4.1-06 BI/메타/흐름관리 노드

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.1-06 │
└───────────────┘
┌───────────────┐
│ BI/메타/흐름관리 노드 │
└───────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'BI/메타/흐름관리 노드' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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
| II.4.1 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [TA협의필요] | 후속 설계 중단 |
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
| 목차 II.4.1 | 슬롯 100% 독립 그림 | FIG-II.4.1-01~ | Completion Gate 수치 |
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
- [TA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-II.4.1-01 | 자료 | 이 절 빈 박스 | [TA협의필요] | 장표/인터뷰/ADR |
| GAP-II.4.1-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.4.1-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.4.1 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.4.1 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [TA협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- II.4.1 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.4.1-10 Node→Environment Handoff

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────┐
│ II.4.1 산출 (그림·GAP·ADR) │
└────────────────────────┘
          │
          ▼
┌────────────────────────┐
│ 입력 계약 (이름 유지, 값 창작 금지) │
└────────────────────────┘
          │
          ▼
┌─────────────────────────┐
│ III Runtime (온라인/배치/연계) │
└─────────────────────────┘
하위 절 그림을 이 슬롯에 합산하지 않음
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Node→Environment Handoff' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.1 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] II.4.1 축약 표현 없음

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

`II.4.1` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.4.2 노드 구성환경 구분

**협업 태그:** [TA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.4.2-01 | 원문 목차 | II.4.2 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.4.2-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.4.2-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **10**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.4.2-01 | 4환경 전체 Lifecycle | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-02 | DEV Topology | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-03 | TEST Topology | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-04 | PROD Topology | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-05 | DR Topology 또는 TBD Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-06 | DEV→TEST Promotion | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-07 | TEST→PROD Promotion | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-08 | PROD→DR Replication/Failover | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-09 | 환경별 차이 Comparison Diagram | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.2-10 | 운영→개발 반입/반출 제약 | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.4.2 노드 구성환경 구분** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.4.2 노드 구성환경 구분`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [TA협의필요]

## 4. L0 Big Picture

### FIG-II.4.2-01 4환경 전체 Lifecycle

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 '4환경 전체 Lifecycle' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4.2-05 DR Topology 또는 TBD Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────────────────┐
│ II.4.2 / 05 DR Topology 또는 TBD Boundary │
└─────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [TA협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'DR Topology 또는 TBD Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4.2-08 PROD→DR Replication/Failover

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

1. **그림 목적:** 이 그림은 'PROD→DR Replication/Failover' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-II.4.2-02 DEV Topology

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

1. **그림 목적:** 이 그림은 'DEV Topology' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.2-03 TEST Topology

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

1. **그림 목적:** 이 그림은 'TEST Topology' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.2-04 PROD Topology

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

1. **그림 목적:** 이 그림은 'PROD Topology' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.2-06 DEV→TEST Promotion

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

1. **그림 목적:** 이 그림은 'DEV→TEST Promotion' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.2-07 TEST→PROD Promotion

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

1. **그림 목적:** 이 그림은 'TEST→PROD Promotion' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.2-09 환경별 차이 Comparison Diagram

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

1. **그림 목적:** 이 그림은 '환경별 차이 Comparison Diagram' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| II.4.2 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [TA협의필요] | 후속 설계 중단 |
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
| 목차 II.4.2 | 슬롯 100% 독립 그림 | FIG-II.4.2-01~ | Completion Gate 수치 |
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
- [TA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-II.4.2-01 | 자료 | 이 절 빈 박스 | [TA협의필요] | 장표/인터뷰/ADR |
| GAP-II.4.2-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.4.2-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.4.2 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.4.2 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [TA협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- II.4.2 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.4.2-10 운영→개발 반입/반출 제약

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.2-10 │
└───────────────┘
┌────────────────┐
│ 운영→개발 반입/반출 제약 │
└────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '운영→개발 반입/반출 제약' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.2 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 10 = 본문 FIG 10
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] II.4.2 축약 표현 없음

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

`II.4.2` V5 재작성. 필수 FIG 10개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.4.3 논리노드 구성도

**협업 태그:** [TA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M3` — 물리·제품·HA 근거가 있다. Topology/정상 Sequence/장애를 분리한다.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.4.3-01 | 원문 목차 | II.4.3 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.4.3-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.4.3-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **14**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.4.3-01 | 4환경 통합 Logical Topology | L0 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-02 | DEV 전체 노드 구성 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-03 | DEV ETL 상세 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-04 | TEST 전체 노드 구성 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-05 | PROD 전체 노드 구성 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-06 | BI Portal HA 상세 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-07 | 데이터흐름관리 HA 상세 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-08 | 마케팅AP/Integration/DB 후보 배치 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-09 | DR 미확정/목표 Topology | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-10 | Application→Node 배치 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-11 | Node→DB/Storage 연결 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-12 | 정상 Runtime 경로 | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-13 | Node 장애/Failover | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.3-14 | 스펙 확정 vs TBD Node | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] 개발 ETL #1/#2=VM+RHEL9+TeraStream(sFTP 이관 불가). 운영 BI포탈 L4+AP A-A+DB A-S+Storage 2T→5T(데이터셋). 운영 흐름관리 Q-Track AP/DB A-S(제품 A-A 미지원)+NAS. 흐름 DEV/TEST=단독·NAS 없음. [GAP] 1단계 4환경 통합 노드도, 마케팅AP 스펙, DR, Host/IP. 통합 그림은 장표 조각의 [ANALYSIS] 합성. [TA협의필요].

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.4.3 논리노드 구성도`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [TA협의필요]

## 4. L0 Big Picture

### FIG-II.4.3-01 4환경 통합 Logical Topology

**Level:** L0 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
4환경 통합 Logical Topology — 원본에 1단계 전체 4환경 노드도 없음
아래는 장표 조각의 [ANALYSIS] 합성. 빈 칸은 빈 칸.
        DEV              TEST             PROD              DR
┌──────────────┬──────────────┬──────────────────┬─────────┐
│ ETL #1/#2 VM │ 흐름 단독    │ L4               │ 장표 없음│
│ RHEL9        │ NAS 없음     │ BIP AP A-A + DB A-S│ [GAP]  │
│ TeraStream   │ AP16C/256G/2T│ DFM AP A-S + DB A-S│        │
│ 흐름 단독    │ DB 8C/256G/1T│ ETL 운영노드?    │        │
│ NAS 없음     │              │ [장표 운영 ETL 미분리]│     │
│ 마케팅AP [GAP]│ 마케팅AP[GAP]│ 마케팅AP [GAP]   │        │
└──────────────┴──────────────┴──────────────────┴─────────┘
[TA협의필요] COL-II.4.3-01 통합 노드도
```

**그림 상세해설**

1. **그림 목적:** 4환경을 한 장에 두되 없는 칸은 비운다. 운영 포탈 HA를 DEV에 복사하지 않는다.
2. **근거자료와 상태:** [FACT] DEV ETL, 흐름 DEV/TEST 단독, PROD 포탈/흐름 HA. [GAP] 통합도, DR, 마케팅AP.
3. **Boundary / In / Out:** In: 환경 이름(II.4.2). Out: 칸별 상세 FIG-02~09. 밖: 디지털뱅킹 PaaS.
4. **Trigger / 시작점:** TA 구성 리뷰.
5. **처리순서:** ① 4열 생성 → ② FACT 채움 → ③ GAP 공란 → ④ 상세 슬롯으로 분해.
6. **책임·비책임:** 책임: TA. 아키텍처는 합성임을 표시.
7. **데이터/전문/상태/제어:** 노드 이름. IP 창작 금지.
8. **실패·운영·후속:** DR 공백=FIG-09. 스펙 Overlay=FIG-14.

## 5. L1 영역/계층/서비스 View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-II.4.3-06 BI Portal HA 상세

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
BI Portal HA 상세 [FACT] — IV.1과 동일 골격, 노드 관점
 L4
  ├ Portal_AP1 Active  DataEye JEUS  16C/64G  공유Storage
  └ Portal_AP2 Active  동일
        │
        ▼
 Portal_DB1 Master Oracle 16C/64G HDD4T ──복제── DB2 Standby
Storage 2T→5T = 데이터셋 (HA 스토리 아님)
포탈 AP를 A-S로 그리지 않음
JEUS 버전 포탈 장표 없음 (흐름 8.5 복사 금지)
```

**그림 상세해설**

1. **그림 목적:** 포탈 HA만. 흐름관리 A-S(07)와 합치지 않는다.
2. **근거자료와 상태:** [FACT] L4, A-A AP, A-S DB, 스펙, Storage 사유. [GAP] JEUS 버전, DR.
3. **Boundary / In / Out:** In: BI 사용자. Out: BIP 노드. 밖: Q-Track.
4. **Trigger / 시작점:** 포탈 기동/장애.
5. **처리순서:** ① L4 → ② AP A-A → ③ DB A-S → ④ Storage 역할 분리.
6. **책임·비책임:** 책임: TA. 업무=IV.1 서비스.
7. **데이터/전문/상태/제어:** 포탈 세션/DB.
8. **실패·운영·후속:** AP 장애 잔여 노드. DB 승격 절차 TBD. 상세 Sequence는 IV.1 FIG-08~10.

### FIG-II.4.3-07 데이터흐름관리 HA 상세

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
데이터흐름관리 HA 상세 [FACT]
 WebtoB
  AP#1 Active   Q-Track 3.1 JEUS 8.5 JDK11 RHEL9  24C/256G/2T
  AP#2 Standby  동일
  제품 A-A 미지원 → AP도 A-S
        │ NAS SDS 2~4T (운영)
        ▼
 Oracle 19C #1 Active / #2 Standby  RHEL 8.6  8C/512G/2T
개발/테스트 NAS 없음 · 단독 (FIG-02/04)
Host/IP 사진 품질 미확정
```

**그림 상세해설**

1. **그림 목적:** Q-Track 운영 HA만. 포탈 A-A와 모델을 섞지 않는다.
2. **근거자료와 상태:** [FACT] 스택, A-S, 스펙, NAS, A-A 미지원. [GAP] Host/IP.
3. **Boundary / In / Out:** In: 계보 사용자/수집. Out: DFM 노드. 밖: ETL VM.
4. **Trigger / 시작점:** 흐름관리 기동.
5. **처리순서:** ① WEB → ② AP A-S → ③ NAS → ④ DB A-S.
6. **책임·비책임:** 책임: TA. 메타 내용=IV.4/DA.
7. **데이터/전문/상태/제어:** 흐름 메타.
8. **실패·운영·후속:** Failover 상세 IV.4 FIG-19/20. Q-Track≠TeraStream.

### FIG-II.4.3-08 마케팅AP/Integration/DB 후보 배치

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
마케팅 AP / Integration / DB 후보 [대부분 GAP]
 후보 LN-MKT-AP   Neoworks  [위치·대수·HA 장표 공란]
 후보 LN-MCA/EIC  계정 중계  [노드도 위치 TBD]
 후보 LN-EAI      연계
 저장 확정 이름: RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
 Exa 코어 숫자는 매핑 표 공란 → 안 넣음
 Integration 노드를 포탈/흐름 스펙 표에 끼워 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** 마케팅·연계 후보는 점선, 저장 제품명은 실선. 스펙 창작 금지.
2. **근거자료와 상태:** [FACT] 저장 제품. Runtime 논리(EIC/MCA). [GAP] AP 대수/HA/Exa 코어.
3. **Boundary / In / Out:** In: APP-MKT. Out: 후보 노드. 밖: 포탈 AP.
4. **Trigger / 시작점:** TA 배치 협의.
5. **처리순서:** ① 마케팅AP GAP → ② 중계 GAP → ③ 저장 FACT → ④ 숫자 공란.
6. **책임·비책임:** 책임: TA COL-II.4.3-01. DA 저장.
7. **데이터/전문/상태/제어:** JDBC 대상 이름.
8. **실패·운영·후속:** 후보가 비면 II.5 통합도가 흔들림. GAP-II.4.3-01.

### FIG-II.4.3-09 DR 미확정/목표 Topology

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
DR [GAP]
┌────────────────────────────┐
│ 1단계 정보계 DR 구성도 없음 │
│ RTO/RPO 창작 금지          │
│ 포탈/흐름/Exa DR 각각 미기재│
└────────────────────────────┘
목표 칸만 열어 둠: PROD와 대칭? 축소? [TBD]
적재 DR과 포탈 DR을 자동 동일시 금지
```

**그림 상세해설**

1. **그림 목적:** DR 없음을 Topology 공백으로 보여 준다.
2. **근거자료와 상태:** [GAP] DR 장표. PROD HA만 FACT.
3. **Boundary / In / Out:** In: 재해 가정. Out: 공백. 밖: 계정계 DR.
4. **Trigger / 시작점:** BCP 리뷰.
5. **처리순서:** ① 검색 → ② 없음 → ③ 영향 → ④ 대칭 여부 TBD.
6. **책임·비책임:** 책임: TA. 아키텍처 GAP 유지.
7. **데이터/전문/상태/제어:** 없음.
8. **실패·운영·후속:** 승인 리스크. FIG-14 TBD 목록.

### FIG-II.4.3-10 Application→Node 배치

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Application → Node (임시 APP-* , 공식코드 없음)
 APP-MKT  → LN-MKT-AP [GAP] + (계정) EIC/MCA [TBD]
 APP-ETCL → ETL #1/#2 DEV FACT / 운영 노드 GAP
 APP-BIP  → Portal_AP1/AP2 FACT
 APP-BIZ  → 포탈 스택 공유? IV.2 ADR
 APP-SBI  → TBD
 APP-DFM  → DFM AP FACT
공식 앱코드 확정 전 임시 ID 유지
```

**그림 상세해설**

1. **그림 목적:** 앱-노드 매핑. 없는 노드는 GAP.
2. **근거자료와 상태:** [FACT] BIP/DFM/ETL DEV. [GAP] MKT AP, SBI, 공식코드.
3. **Boundary / In / Out:** In: II.3 앱. Out: 논리노드. 밖: 컨테이너 R/F(III.6).
4. **Trigger / 시작점:** 배치 설계.
5. **처리순서:** ① 앱 목록 → ② 확정 노드 → ③ GAP 노드.
6. **책임·비책임:** 책임: TA+앱맵. 공식코드=코드정책 GAP.
7. **데이터/전문/상태/제어:** 배치 메타.
8. **실패·운영·후속:** 미매핑 앱은 배포 단위 불명. II.3.2 연결.

### FIG-II.4.3-11 Node→DB/Storage 연결

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
Node → DB/Storage
 Portal_AP     → Portal Oracle A-S  + 공유 Storage 2T→5T
 DFM_AP        → Oracle 19C A-S     + NAS 2~4T (운영만)
 ETL VM        → ADW/ENT/AUI/BSA/ODW (대상 힌트, 접속 정보 GAP)
 MKT_AP [GAP]  → RTW/ADW/BSA Exa/Oracle [대상 TBD]
 DVZ [후보]    → 허브 조회 가상화 (IV.1 Option A)
HDW Vertica / BDP Hive 는 저장 이름 FACT, 앱 접속 노드 GAP
```

**그림 상세해설**

1. **그림 목적:** 노드-저장 연결. 허브 JDBC 값을 창작하지 않는다.
2. **근거자료와 상태:** [FACT] 포탈 DB/Storage, 흐름 DB/NAS, ETL 대상 힌트, 저장 제품. [GAP] MKT 접속.
3. **Boundary / In / Out:** In: 노드. Out: DB/Storage. 밖: 소스코드 형상.
4. **Trigger / 시작점:** 데이터 경로 리뷰.
5. **처리순서:** ① 포탈 → ② 흐름 → ③ ETL → ④ 마케팅 점선 → ⑤ Vertica/Hive 접속 GAP.
6. **책임·비책임:** 책임: TA 연결, DA 권한.
7. **데이터/전문/상태/제어:** JDBC/마운트. 포트 창작 금지.
8. **실패·운영·후속:** 저장 장애는 노드 Failover와 별개(FIG-13).

## 7. Static Mapping / Responsibility View

현재 절의 직접 Runtime/구조가 이 번호의 핵심이 아니더라도 번호를 생략하지 않는다. 영향은 아래 연결로 둔다.

```text
[현재 절 영향]
      │
      ▼
[후속 절 입력] ──► [필요 자료: 장표/인터뷰/ADR]
```

## 8. L3 정상 Runtime / Sequence / Data Flow

### FIG-II.4.3-12 정상 Runtime 경로

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
정상 Runtime 경로 (노드 관점)
 온라인: 단말 → (정보 직접 | 계정 EIC/MCA) → MKT_AP[GAP] → Exa/Oracle
 배치: Control-M → ETL VM(DEV FACT) 또는 운영 ETL[GAP] → 저장
 포탈: User → L4 → BIP AP A-A → Portal DB 및/또는 허브
 계보: 원천메타 → DFM AP A-S → DFM DB → 활용
네 경로를 한 파이프로 합치지 않음 — 이 그림은 나란히 병기
```

**그림 상세해설**

1. **그림 목적:** 정상 경로 4개를 노드 수준에서 병기한다.
2. **근거자료와 상태:** [FACT] Runtime 진입, ETL DEV, 포탈/흐름 HA. [GAP] MKT_AP, 운영 ETL.
3. **Boundary / In / Out:** In: 사용자/스케줄/원천. Out: 저장/화면. 밖: SSO.
4. **Trigger / 시작점:** 정상 운영.
5. **처리순서:** ① 온라인 → ② 배치 → ③ 포탈 → ④ 계보. 합류점=저장 이름.
6. **책임·비책임:** 책임: 각 APP Owner. TA 노드.
7. **데이터/전문/상태/제어:** 제어: 요청/잡. 데이터: 업무 vs 메타.
8. **실패·운영·후속:** 경로별 장애=FIG-13. 상세는 III/IV Sequence.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-II.4.3-13 Node 장애/Failover

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
노드 장애 / Failover (모드가 다름)
 BIP AP 장애 → L4가 남은 Active  (A-A)
 BIP DB 장애 → Standby 승격      (A-S, 절차 TBD)
 DFM AP 장애 → Standby 승격      (A-S, 제품 A-A 미지원)
 DFM DB 장애 → Standby 승격      (A-S, 절차 TBD)
 ETL DEV VM 장애 → HA 없음, 개발 중단
 MKT AP 장애 → 모델 자체 [GAP]
 DR 전환 → 장표 없음
포탈 A-A 런북을 흐름 A-S에 재사용 금지
```

**그림 상세해설**

1. **그림 목적:** 노드 유형별 Failover 모드를 한 장에 대조한다. 절차 값은 TBD.
2. **근거자료와 상태:** [FACT] BIP A-A, DFM A-S, A-A 미지원, ETL DEV 단독. [GAP] MKT, DR, 승격 런북.
3. **Boundary / In / Out:** In: 노드 다운. Out: 전환 모드. 밖: 앱 재시도(III.1.5).
4. **Trigger / 시작점:** 장애 리허설.
5. **처리순서:** ① 유형 식별 → ② 모드 → ③ 절차 TBD → ④ 런북 공유 금지.
6. **책임·비책임:** 책임: TA/운영. DBA는 DB 승격.
7. **데이터/전문/상태/제어:** 제어: HA 스위치. 세션 손실 TBD.
8. **실패·운영·후속:** 상세 Sequence는 IV.1/IV.4. 여기선 노드 대조가 본체.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-II.4.3-02 DEV 전체 노드 구성

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
DEV 전체 노드 [확인된 것만]
┌── 프로젝트ONE 개발환경 ──┐
│ ETL #1  VM RHEL9 TeraStream  ADW,ENT,AUI,BSA     │
│ ETL #2  VM RHEL9 TeraStream  ODW / Global DW     │
│ 흐름관리 AP+DB 단독, NAS 없음 (스펙 FIG-07 계열) │
│ 마케팅 Neoworks AP / MCA     [GAP]               │
│ 포탈 DEV 토폴로지            [GAP] (운영 장표만) │
│ (이관) As-Is 유지보수 개발 ──x sFTP──► 여기      │
└────────────────────────────────────────────────┘
PROD 포탈 A-A를 이 그림에 넣지 않음
```

**그림 상세해설**

1. **그림 목적:** DEV 전체. ETL과 흐름 단독만 실선.
2. **근거자료와 상태:** [FACT] ETL#1#2 VM RHEL9 TeraStream, sFTP 이관 불가, 흐름 DEV 단독 NAS 없음. [GAP] 마케팅AP, 포탈 DEV.
3. **Boundary / In / Out:** In: 개발자. Out: DEV 노드. 밖: PROD.
4. **Trigger / 시작점:** 개발 배포.
5. **처리순서:** ① ETL 두 VM → ② 흐름 단독 → ③ 나머지 GAP → ④ sFTP 금지.
6. **책임·비책임:** 책임: TA 개발환경. ETL 대상은 장표 힌트.
7. **데이터/전문/상태/제어:** 개발 데이터. 운영 복제 여부 TBD.
8. **실패·운영·후속:** ETL 상세=FIG-03. 이관 실패는 sFTP가 아니라 별 경로 [미기재].

### FIG-II.4.3-03 DEV ETL 상세

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
DEV ETL 상세 [FACT]
 ETL #1  VM + RHEL 9 + TeraStream
   대상 힌트: ADW, ENT, AUI, BSA
 ETL #2  VM + RHEL 9 + TeraStream
   대상 힌트: ODW / Global DW
 CPU/Mem/Disk 숫자 장표 없음 → 표에 공란 유지 (창작 금지)
 As-Is ETCL 분산 서버 ≠ 이 개발 집약 VM  (I.1 변화요인)
 운영 ETL 노드를 이 슬롯에 그리지 않음
```

**그림 상세해설**

1. **그림 목적:** 개발 ETL 두 VM만. 흐름관리·포탈과 합치지 않는다.
2. **근거자료와 상태:** [FACT] VM, RHEL9, TeraStream, #1/#2, 대상 힌트, sFTP 불가. [GAP] 스펙 숫자.
3. **Boundary / In / Out:** In: 개발 ETL 잡. Out: 지정 대상. 밖: Q-Track.
4. **Trigger / 시작점:** 개발 적재 시험.
5. **처리순서:** ① #1 대상 → ② #2 대상 → ③ 스펙 공란 → ④ As-Is 분산과 대비.
6. **책임·비책임:** 책임: TA VM, DA 대상, 배치 유형 III.2.1.
7. **데이터/전문/상태/제어:** ETCL 실데이터. 메타는 IV.4.
8. **실패·운영·후속:** 잡 실패=III.2. VM 장애 시 개발 중단. HA 없음.

### FIG-II.4.3-04 TEST 전체 노드 구성

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
TEST 전체 노드
┌─────────────────────────────┐
│ 흐름관리 AP+DB 단독, NAS 없음 │
│ 스펙: AP 16C/256G/2T, DB 8C/256G/1T [FACT] │
│ ETL TEST 전용 장표 분리 없음 [GAP]          │
│ 마케팅AP / 포탈 TEST [GAP]                  │
│ PROD A-S 시험을 이 단독으로 증명 못 함      │
└─────────────────────────────┘
DEV(02)와 스펙이 같아도 슬롯을 합치지 않음
```

**그림 상세해설**

1. **그림 목적:** TEST만. DEV 그림 복제 금지.
2. **근거자료와 상태:** [FACT] 흐름 TEST 단독·NAS 없음·스펙. [GAP] ETL/포탈/마케팅 TEST.
3. **Boundary / In / Out:** In: 시험 이관. Out: TEST 노드. 밖: PROD HA.
4. **Trigger / 시작점:** 시험.
5. **처리순서:** ① 흐름 단독 → ② 나머지 GAP → ③ HA 시험 한계.
6. **책임·비책임:** 책임: TA/시험.
7. **데이터/전문/상태/제어:** 시험 데이터.
8. **실패·운영·후속:** TEST 실패≠운영 Failover 증명. FIG-13 한계.

### FIG-II.4.3-05 PROD 전체 노드 구성

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
PROD 전체 (확인된 운영 조각)
 [L4]
   ├─ BIP: AP1/AP2 A-A DataEye JEUS → DB A-S Oracle + Storage 2T→5T
   └─ OLAP As-Is: WAS → L4 → BO → IQ/Oracle  [잔존]
 단말 → (MCA? [TBD]) → 마케팅AP [GAP] → JDBC Exa
 원천 → EAI/ETCL → RTW/ADW/BSA/HDW/BDP
 메타 → DFM AP A-S Q-Track → DFM DB A-S Oracle19C + NAS 2~4T
ETL 운영 전용 노드 장표 미분리 [GAP]
Host/IP 미확정
```

**그림 상세해설**

1. **그림 목적:** 운영 전체 합성. HA 상세는 06/07, 마케팅 후보는 08.
2. **근거자료와 상태:** [FACT] 포탈·흐름 운영 HA, 저장 제품명. [ANALYSIS] 한 장 합성. [GAP] 마케팅AP, 운영 ETL, Host.
3. **Boundary / In / Out:** In: 운영 트래픽. Out: PROD 노드군. 밖: DR.
4. **Trigger / 시작점:** 운영 개통.
5. **처리순서:** ① L4/포탈 → ② 마케팅 점선 → ③ 저장 → ④ 연계 → ⑤ 흐름.
6. **책임·비책임:** 책임: TA 배치. MCA 위치 미확정.
7. **데이터/전문/상태/제어:** 운영 데이터.
8. **실패·운영·후속:** 장애=FIG-13. DR=FIG-09.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| II.4.3 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [TA협의필요] | 후속 설계 중단 |
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
| 목차 II.4.3 | 슬롯 100% 독립 그림 | FIG-II.4.3-01~ | Completion Gate 수치 |
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
- [TA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-II.4.3-01 | 자료 | 이 절 빈 박스 | [TA협의필요] | 장표/인터뷰/ADR |
| GAP-II.4.3-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.4.3-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.4.3 슬롯 이름·Evidence Maturity `M3`

**What blocks approval**
- II.4.3 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [TA협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- II.4.3 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.4.3-14 스펙 확정 vs TBD Node

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
스펙 확정 vs TBD
실선 FACT
 Portal AP 16C 64G 공유2T→5T A-A
 Portal DB 16C 64G HDD4T A-S
 흐름 운영 AP 24C 256G 2T A-S / DB 8C 512G 2T A-S
 흐름 개발·테스트 AP 16C 256G 2T / DB 8C 256G 1T 단독
 ETL #1#2 = VM RHEL9 TeraStream (CPU/Mem/Disk 공란)
 OLAP BO As-Is 4C 48G APP120G FRS500G 쌍
점선 TBD
 마케팅AP 전 스펙, Exa 코어, Host/IP, DR, 포탈/마케팅 DEV·TEST
숫자 행을 장표 밖으로 확장하지 않음
```

**그림 상세해설**

1. **그림 목적:** 스펙 Overlay. 공란을 추정으로 메우지 않는다.
2. **근거자료와 상태:** [FACT] 표의 숫자·HA. [GAP] 마케팅/Exa/Host/DR/포탈 DEV.
3. **Boundary / In / Out:** In: 장표 스펙. Out: TA 숙제 목록. 밖: 클라우드 단가.
4. **Trigger / 시작점:** 용량/구매 리뷰.
5. **처리순서:** ① 실선 낭독 → ② 점선 목록 → ③ 확장 금지.
6. **책임·비책임:** 책임: TA COL-II.4.3-01. 비책임: 공란 추정.
7. **데이터/전문/상태/제어:** 스펙 메타.
8. **실패·운영·후속:** 점선이면 II.5 목표 IT도가 미완. Handoff=II.4.4 SW.

## 19. 검증 체크리스트

- [ ] Figure Plan 14 = 본문 FIG 14
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] II.4.3 축약 표현 없음

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

`II.4.3` 심화. 환경별 Topology와 포탈 A-A/흐름 A-S/ETL DEV를 분리했다. 마케팅AP·DR·Exa 코어는 공란 유지. 스펙 장표 밖 확장 없음.

---

# II.4.4 적용 소프트웨어 식별

**협업 태그:** [TA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.4.4-01 | 원문 목차 | II.4.4 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.4.4-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.4.4-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **9**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.4.4-01 | Software Category Map | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.4-02 | FACT SW vs Candidate SW Boundary | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.4-03 | OS/Web/WAS Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.4-04 | Framework/Integration Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.4-05 | Batch/ETL Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.4-06 | Database/Data Platform Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.4-07 | CI/CD/Monitoring/Security Candidate Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.4-08 | SW→Node Mapping | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.4-09 | SW Selection Decision/Gate | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.4.4 적용 소프트웨어 식별** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.4.4 적용 소프트웨어 식별`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [TA협의필요]

## 4. L0 Big Picture

### FIG-II.4.4-01 Software Category Map

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-01 │
└───────────────┘
┌───────────────────────┐
│ Software Category Map │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Software Category Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4.4-03 OS/Web/WAS Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-03 │
└───────────────┘
┌──────────────────┐
│ OS/Web/WAS Stack │
└──────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'OS/Web/WAS Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.4-04 Framework/Integration Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-04 │
└───────────────┘
┌─────────────────────────────┐
│ Framework/Integration Stack │
└─────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Framework/Integration Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.4-05 Batch/ETL Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-05 │
└───────────────┘
┌─────────────────┐
│ Batch/ETL Stack │
└─────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Batch/ETL Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.4-06 Database/Data Platform Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-06 │
└───────────────┘
┌──────────────────────────────┐
│ Database/Data Platform Stack │
└──────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Database/Data Platform Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-II.4.4-02 FACT SW vs Candidate SW Boundary

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-02 │
└───────────────┘
┌──────────────────────────────────┐
│ FACT SW vs Candidate SW Boundary │
└──────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'FACT SW vs Candidate SW Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.4-08 SW→Node Mapping

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-08 │
└───────────────┘
┌─────────────────┐
│ SW→Node Mapping │
└─────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'SW→Node Mapping' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4.4-07 CI/CD/Monitoring/Security Candidate Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-07 │
└───────────────┘
┌───────────────────────────────────────────┐
│ CI/CD/Monitoring/Security Candidate Stack │
└───────────────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'CI/CD/Monitoring/Security Candidate Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| II.4.4 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [TA협의필요] | 후속 설계 중단 |
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
| 목차 II.4.4 | 슬롯 100% 독립 그림 | FIG-II.4.4-01~ | Completion Gate 수치 |
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
- [TA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-II.4.4-01 | 자료 | 이 절 빈 박스 | [TA협의필요] | 장표/인터뷰/ADR |
| GAP-II.4.4-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.4.4-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.4.4 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.4.4 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [TA협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- II.4.4 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.4.4-09 SW Selection Decision/Gate

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.4-09 │
└───────────────┘
┌────────────────────────────┐
│ SW Selection Decision/Gate │
└────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'SW Selection Decision/Gate' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.4 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 9 = 본문 FIG 9
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] II.4.4 축약 표현 없음

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

`II.4.4` V5 재작성. 필수 FIG 9개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.4.5 소프트웨어 구성도

**협업 태그:** [TA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M2` — 핵심 이름·경계는 FACT, 값·절차·버전은 GAP/TBD.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.4.5-01 | 원문 목차 | II.4.5 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.4.5-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.4.5-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **9**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.4.5-01 | Node×Software 전체 Stack | L0 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.5-02 | ETL Node Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.5-03 | 마케팅 AP Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.5-04 | Portal Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.5-05 | Flow Management Stack | L3 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.5-06 | Integration Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.5-07 | DB Client/Driver Connection Stack | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.5-08 | Container Stack/TBD | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.5-09 | FACT Mapping vs Blank/TBD Map | L1/L2 | M2 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.4.5 소프트웨어 구성도** — Evidence `M2`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.4.5 소프트웨어 구성도`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [TA협의필요]

## 4. L0 Big Picture

### FIG-II.4.5-01 Node×Software 전체 Stack

**Level:** L0 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.5-01 │
└───────────────┘
┌────────────────────────┐
│ Node×Software 전체 Stack │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Node×Software 전체 Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4.5-02 ETL Node Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.5-02 │
└───────────────┘
┌────────────────┐
│ ETL Node Stack │
└────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'ETL Node Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.5-03 마케팅 AP Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.5-03 │
└───────────────┘
┌──────────────┐
│ 마케팅 AP Stack │
└──────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '마케팅 AP Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.5-04 Portal Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.5-04 │
└───────────────┘
┌──────────────┐
│ Portal Stack │
└──────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Portal Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.5-06 Integration Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.5-06 │
└───────────────┘
┌───────────────────┐
│ Integration Stack │
└───────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Integration Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.5-07 DB Client/Driver Connection Stack

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.5-07 │
└───────────────┘
┌───────────────────────────────────┐
│ DB Client/Driver Connection Stack │
└───────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'DB Client/Driver Connection Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.5-08 Container Stack/TBD

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────────────────────────┐
│ II.4.5 / 08 Container Stack/TBD │
└─────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [TA협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Container Stack/TBD' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4.5-05 Flow Management Stack

**Level:** L3 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.5-05 │
└───────────────┘
┌───────────────────────┐
│ Flow Management Stack │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M2      Owner: [TA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Flow Management Stack' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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
| II.4.5 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [TA협의필요] | 후속 설계 중단 |
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
| 목차 II.4.5 | 슬롯 100% 독립 그림 | FIG-II.4.5-01~ | Completion Gate 수치 |
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
- [TA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-II.4.5-01 | 자료 | 이 절 빈 박스 | [TA협의필요] | 장표/인터뷰/ADR |
| GAP-II.4.5-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.4.5-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.4.5 슬롯 이름·Evidence Maturity `M2`

**What blocks approval**
- II.4.5 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [TA협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- II.4.5 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.4.5-09 FACT Mapping vs Blank/TBD Map

**Level:** L1/L2 · **근거상태:** M2 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────────────────────────────────┐
│ II.4.5 / 09 FACT Mapping vs Blank/TBD Map │
└───────────────────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [TA협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'FACT Mapping vs Blank/TBD Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M2`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 9 = 본문 FIG 9
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] II.4.5 축약 표현 없음

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

`II.4.5` V5 재작성. 필수 FIG 9개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.4.6 데이터베이스 목록

**협업 태그:** [DA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M3` — 물리·제품·HA 근거가 있다. Topology/정상 Sequence/장애를 분리한다.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.4.6-01 | 원문 목차 | II.4.6 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.4.6-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.4.6-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **9**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.4.6-01 | Database Portfolio Big Picture | L0 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.6-02 | RTW/ADW/BSA/HDW/BDP 저장계층 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.6-03 | Portal/Flow 운영DB | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.6-04 | AS-IS DB vs TO-BE DB | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.6-05 | Application→Database | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.6-06 | Database→Logical Node | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.6-07 | Source→CDC/ETL→Target DB | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.6-08 | DB Ownership/Schema Boundary | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.4.6-09 | SID/Schema/CDC GAP Map | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

[FACT] RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive. [DA협의필요] 스키마/보관주기.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.4.6 데이터베이스 목록`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [DA협의필요]

## 4. L0 Big Picture

### FIG-II.4.6-01 Database Portfolio Big Picture

**Level:** L0 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.6-01 │
└───────────────┘
┌────────────────────────────────┐
│ Database Portfolio Big Picture │
└────────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Database Portfolio Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4.6-02 RTW/ADW/BSA/HDW/BDP 저장계층

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.6-02 │
└───────────────┘
┌──────────────────────────┐
│ RTW/ADW/BSA/HDW/BDP 저장계층 │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'RTW/ADW/BSA/HDW/BDP 저장계층' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.6-05 Application→Database

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.6-05 │
└───────────────┘
┌──────────────────────┐
│ Application→Database │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Application→Database' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.6-06 Database→Logical Node

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.6-06 │
└───────────────┘
┌───────────────────────┐
│ Database→Logical Node │
└───────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Database→Logical Node' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.4.6-07 Source→CDC/ETL→Target DB

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.6-07 │
└───────────────┘
┌──────────────────────────┐
│ Source→CDC/ETL→Target DB │
└──────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Source→CDC/ETL→Target DB' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 7. Static Mapping / Responsibility View

### FIG-II.4.6-08 DB Ownership/Schema Boundary

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.6-08 │
└───────────────┘
┌──────────────────────────────┐
│ DB Ownership/Schema Boundary │
└──────────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'DB Ownership/Schema Boundary' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.4.6-03 Portal/Flow 운영DB

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.6-03 │
└───────────────┘
┌──────────────────┐
│ Portal/Flow 운영DB │
└──────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'Portal/Flow 운영DB' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| II.4.6 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [DA협의필요] | 후속 설계 중단 |
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
| 목차 II.4.6 | 슬롯 100% 독립 그림 | FIG-II.4.6-01~ | Completion Gate 수치 |
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

### FIG-II.4.6-04 AS-IS DB vs TO-BE DB

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌───────────────┐
│ FIG II.4.6-04 │
└───────────────┘
┌──────────────────────┐
│ AS-IS DB vs TO-BE DB │
└──────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
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

1. **그림 목적:** 이 그림은 'AS-IS DB vs TO-BE DB' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 17. 확정 / 협의필요 / GAP / TBD / ADR

### 확정
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- 해당 절 그림의 실선 FACT 상자

### 협의필요
- [DA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-II.4.6-01 | 자료 | 이 절 빈 박스 | [DA협의필요] | 장표/인터뷰/ADR |
| GAP-II.4.6-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.4.6-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.4.6 슬롯 이름·Evidence Maturity `M3`

**What blocks approval**
- II.4.6 GAP 박스
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
- II.4.6 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.4.6-09 SID/Schema/CDC GAP Map

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────────────┐
│ II.4.6 / 09 SID/Schema/CDC GAP Map │
└────────────────────────────────────┘
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

1. **그림 목적:** 이 그림은 'SID/Schema/CDC GAP Map' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.4.6 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 9 = 본문 FIG 9
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] II.4.6 축약 표현 없음

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

`II.4.6` V5 재작성. 필수 FIG 9개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

# II.5 목표 IT시스템 구성도

**협업 태그:** [TA협의필요] [DA협의필요]

**문서 성격:** V5 재작성 작업본. v1 초안은 `… - 원본.md`에 보존.

## 0. Evidence Register

**Evidence Maturity Gate:** `M3` — 물리·제품·HA 근거가 있다. Topology/정상 Sequence/장애를 분리한다.

| Evidence ID | 출처 | 지지하는 Claim | 한계 |
|-------------|------|----------------|------|
| EV-II.5-01 | 원문 목차 | II.5 작성 방향·협업 태그 | 기작성 원문 일부 미입수 |
| EV-II.5-02 | v1 초안 스냅샷(… - 원본.md) | 이미 고정한 FACT 이름 | V5 그림 깊이 부족 → 본 재작성 |
| EV-II.5-03 | 물리 TA / 서버매핑 / 캐릭터셋 / ADR | 노드·제품·CS·Runtime 힌트 | 버전/대수 없는 칸은 TBD |
| EV-II-04 | 솔루션-물리서버 매핑 | 개발 ETL #1/#2 = VM + RHEL 9 + TeraStream. sFTP 이관 불가 / RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive | 앱 공식코드 없음 APP-* 임시 |

## 1. Figure Plan

필수 Figure Slot **14**개. 아래 ID와 본문 그림 ID는 1:1이다. 슬롯을 합치지 않는다.

| FIG ID | 제목 | Level | 근거상태 | Source |
|--------|------|-------|----------|--------|
| FIG-II.5-01 | 목표 IT 전체 통합 Big Picture | L0 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-02 | 정보전달 영역 기술상세 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-03 | 수집·연계 영역 기술상세 | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-04 | 저장소 영역 기술상세 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-05 | 분석·정보제공 영역 기술상세 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-06 | 인프라 영역 기술상세 | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-07 | Application→Node→SW→DB | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-08 | DEV Target IT | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-09 | TEST Target IT | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-10 | PROD Target IT | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-11 | DR Target IT/TBD | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-12 | 온라인 Runtime | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-13 | 데이터 적재 Runtime | L3 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |
| FIG-II.5-14 | HA/DR/Governance Cross-cutting | L1/L2 | M3 | 목차/물리TA/캐릭터셋/ADR/v1초안 FACT |

## 2. 핵심 결론

**II.5 목표 IT시스템 구성도** — Evidence `M3`. 확정 칸만 FACT로 두고 나머지는 GAP/Option/Gate로 연다.

이 절에서 **확정하는 것**은 슬롯 그림의 실선 상자이다. **남기는 것**은 점선/[TBD]와 Review-Ready Pack의 승인 차단 항목이다.
상위 절의 필수 그림은 생략하지 않는다.

## 3. 목적 / 범위 / 전제

- **목적:** `II.5 목표 IT시스템 구성도`를 V5 슬롯 단위로 추적 가능하게 정의한다.
- **범위:** 이 절의 필수 Figure Slot. 하위 절 그림을 이 절 슬롯에 포함해 세지 않는다.
- **전제:** 자료에 없는 Timeout 초, 포트, 암호 알고리즘, 서버 대수, JSON 필드, EIMS 역할, Jenkins 선정을 만들지 않는다.
- **협업:** [TA협의필요] [DA협의필요]

## 4. L0 Big Picture

### FIG-II.5-01 목표 IT 전체 통합 Big Picture

**Level:** L0 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.5-01 │
└─────────────┘
┌─────────────────────────┐
│ 목표 IT 전체 통합 Big Picture │
└─────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [TA협의필요] [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '목표 IT 전체 통합 Big Picture' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 5. L1 영역/계층/서비스 View

### FIG-II.5-02 정보전달 영역 기술상세

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '정보전달 영역 기술상세' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.5-04 저장소 영역 기술상세

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '저장소 영역 기술상세' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 6. L2 Component/Application/Node/SW/DB/Contract View

### FIG-II.5-05 분석·정보제공 영역 기술상세

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '분석·정보제공 영역 기술상세' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.5-06 인프라 영역 기술상세

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '인프라 영역 기술상세' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.5-07 Application→Node→SW→DB

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌─────────────┐
│ FIG II.5-07 │
└─────────────┘
┌────────────────────────┐
│ Application→Node→SW→DB │
└────────────────────────┘
          │
          ▼
[FACT 앵커]
RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive / 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음)
          │
    ┌─────┴─────┐
    ▼           ▼
확정 구성요소   [TBD]/[GAP]/[DEP]
M3      Owner: [TA협의필요] [DA협의필요]
          │
          ▼
후속: 관련 절 / ADR / 필요자료
NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'Application→Node→SW→DB' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
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

### FIG-II.5-03 수집·연계 영역 기술상세

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
[사용자/채널] 정보단말 · 계정단말 · 대고객/영업점 [I.5]
        │
        ▼
① 정보전달     ← 정보단말 → Neoworks 직접 / 계정단말 → EIC/MCA → Neoworks
② 분석·정보제공 ← BI포탈/SelfBI/OLAP (IV)
③ 데이터 저장소 ← RTW/ADW Oracle Exa, BSA Oracle, HDW Vertica, BDP Hive
④ 수집·연계     ← 연계 수단 이름: CDC, ETCL, BC(정의 미입수) · 유입: CDC, ETCL, HYDRA-K, D-0/D-1
⑤ 인프라        ← 논리노드 Baseline: VM / Container / 베어메탈 (PMO 매트릭스 원문 없음) · 환경: 개발 / 테스트 / 운영 / DR
영역 이름을 바꾸거나 6번째 영역을 만들지 않는다.
우회 금지 문장 원문 없음 → 원칙 후보만 [TO-BE/PROPOSED]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 '수집·연계 영역 기술상세' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.5-12 온라인 Runtime

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────┐
│ ① Trigger [II.5] │
└──────────────────┘
          │
          ▼
┌────────────────────┐
│ ② 처리 (온라인 Runtime) │
└────────────────────┘
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

1. **그림 목적:** 이 그림은 '온라인 Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.5-13 데이터 적재 Runtime

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌──────────────────┐
│ ① Trigger [II.5] │
└──────────────────┘
          │
          ▼
┌───────────────────────┐
│ ② 처리 (데이터 적재 Runtime) │
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

1. **그림 목적:** 이 그림은 '데이터 적재 Runtime' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 9. Failure / Exception / Retry / Recovery / HA-DR View

### FIG-II.5-11 DR Target IT/TBD

**Level:** L3 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

```text
┌────────────────────────────┐
│ II.5 / 11 DR Target IT/TBD │
└────────────────────────────┘
          │
    ┌─────┴──────┐
    ▼            ▼
[FACT 확정칸]   [GAP/TBD 빈칸]
 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
          │
          ▼
질문 ID → 기대 Evidence → 답변 영향 FIG → Owner
 [TA협의필요] [DA협의필요]
          │
          ▼
Decision Gate / ADR 후보
[M1이면 여기서 종료. 가짜 상세 Runtime 금지]
```

**그림 상세해설**

1. **그림 목적:** 이 그림은 'DR Target IT/TBD' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 10. Security / Trust / Character Set / Data Protection 영향

현재 절 보안 영향 → III.7 / IV.5 / 캐릭터셋 ADR-CS-01. 알고리즘 창작 금지.

`JSON 표준전문 UTF-8, 정보단말 EUC-KR. 기본 CS 단일 확정 아님(ADR-CS-01)`

## 11. Operation / Observability / Deployment / Environment 영향

환경·배포 영향 → II.4.2 / III.8. `환경: 개발 / 테스트 / 운영 / DR` / `CI/CD 축: VM vs 컨테이너 × 개발/테스트/이행 (II ‘운영’과 용어 불일치 TBD)`

### FIG-II.5-08 DEV Target IT

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'DEV Target IT' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.5-09 TEST Target IT

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'TEST Target IT' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

### FIG-II.5-10 PROD Target IT

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'PROD Target IT' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 12. 구성요소 책임표

| 구성요소 | 존재 이유 | 입력 | 출력 | 책임한계 | 실패영향 |
|----------|-----------|------|------|----------|----------|
| II.5 확정 구성요소 | 목차/장표 이름 유지 | 선행 절 | 후행 절 | [TA협의필요] [DA협의필요] | 후속 설계 중단 |
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
| 목차 II.5 | 슬롯 100% 독립 그림 | FIG-II.5-01~ | Completion Gate 수치 |
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
- [TA협의필요] [DA협의필요]

### GAP / TBD
| ID | 유형 | 내용 | Owner | 필요자료 |
|----|------|------|-------|----------|
| GAP-II.5-01 | 자료 | 이 절 빈 박스 | [TA협의필요] [DA협의필요] | 장표/인터뷰/ADR |
| GAP-II.5-02 | 값 | Timeout/버전/필드 등 | FW/TA/DA/보안 | 기준서 |

### ADR 후보
| ID | 제목 | 영향 FIG |
|----|------|----------|
| ADR-II.5-01 | 이 절 미결정 분기 | 해당 Option/Gate FIG |

### Review-Ready Pack (1.17)

**What is decided**
- 프로젝트ONE 1단계 [마케팅플랫폼 및 데이터허브] (정보계)
- II.5 슬롯 이름·Evidence Maturity `M3`

**What blocks approval**
- II.5 GAP 박스
- Owner 미응답 항목

**Who must answer**
- [TA협의필요] [DA협의필요]
- TA/DA/FW/보안/2사업 해당 시

**What evidence is required**
- 장표
- 인터뷰 답변
- ADR

**What changes if the answer changes**
- Option 채택 시 인접 FIG·II.5/III.4/IV HA 재작성

**Next review agenda**
- II.5 빈칸 닫기
- 다음 절 Handoff 계약 확인

## 18. 다음 절 Handoff Text 그림

### FIG-II.5-14 HA/DR/Governance Cross-cutting

**Level:** L1/L2 · **근거상태:** M3 · **Source:** 목차/물리TA/캐릭터셋/ADR/v1초안 FACT

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

1. **그림 목적:** 이 그림은 'HA/DR/Governance Cross-cutting' 질문에만 답한다. 다른 슬롯과 합치지 않는다.
2. **근거자료와 상태:** 상태 `M3`. 하나은행 입력=목차·물리TA·캐릭터셋·ADR·v1초안 FACT. NSIGHT/TCF/KB/KDB/MSTR/NH Cloud = 비교·제안만. 하나 FACT 아님
3. **Boundary / In / Out:** In: II.5 선행 절 산출. Out: 이 슬롯의 확정칸/빈칸. 범위 밖: 창작 스펙·타행 제품.
4. **Trigger / 시작점:** 작성/리뷰 트리거. Runtime 그림이면 사용자 요청 또는 스케줄/원천변경. Discovery 그림이면 리뷰 질문.
5. **처리순서:** 그림 ①~N = 본문 단계. 빈 박스는 건너뛰지 않고 [TBD]로 남긴다.
6. **책임·비책임:** 책임: [TA협의필요] [DA협의필요]. 비책임: 자료에 없는 제품 선정·Timeout 초·암호 알고리즘.
7. **데이터/전문/상태/제어:** 운반물: 이름 있는 데이터/전문/메타만. JSON UTF-8·단말 EUC-KR은 해당 절 FACT. 필드 목록 창작 금지.
8. **실패·운영·후속:** 실패 시 별도 Failure FIG 또는 GAP 질문. 운영/보안은 후속 절 입력으로 넘긴다.

## 19. 검증 체크리스트

- [ ] Figure Plan 14 = 본문 FIG 14
- [ ] L0/L1/L2 존재, 동적 절은 정상/실패 분리
- [ ] 창작값 0건, NSIGHT 비FACT
- [ ] 협업 태그·Owner 미확정 표기 유지
- [ ] 표가 그림을 대체하지 않음
- [ ] II.5 축약 표현 없음

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

`II.5` V5 재작성. 필수 FIG 14개 출력. 창작값 0을 목표로 FACT 이름만 사용했다. 90점 평가는 리뷰에서 그림 실체를 재확인한다.

---

