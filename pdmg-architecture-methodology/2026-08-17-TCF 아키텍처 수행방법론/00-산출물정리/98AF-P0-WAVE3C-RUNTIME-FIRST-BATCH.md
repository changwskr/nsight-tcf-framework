# P0 Closure Wave 3C — Runtime First Batch Operationalization

> 상태: **OPERATOR READY / PRODUCTION RUNTIME 0/3 / G80 HOLD / HG90 HOLD**  
> 범위: `RUN-TIMEOUT`, `RUN-P600`, `RUN-P1200`

## 1. 목적

Wave3B의 공통 자동화 도구를 첫 번째 실제 실행군 3개에 대해 **운영자 실행 Runbook**으로 구체화한다.

이번 Wave는 실제 Production Runtime PASS를 만들지 않는다. 현재 실행환경에는 NSIGHT 성능시험 대상, 실제 Runtime Identity, JMeter/Gatling, Oracle CLI가 연결되어 있지 않다.

## 2. 실행순서

```text
RUN-TIMEOUT
    ↓ Safety Gate
RUN-P600
    ↓ Operating Peak Gate
RUN-P1200
    ↓ Design Peak Gate
1st Runtime Review
```

`RUN-TIMEOUT=NO_GO`이면 P600/P1200으로 진행하지 않는다.

## 3. 이번에 추가된 산출

1. First Batch Machine-readable Run Catalog
2. RUN-TIMEOUT Operator Runbook
3. RUN-P600 Operator Runbook
4. RUN-P1200 Operator Runbook
5. First Batch Operator Checklist
6. Machine Go/No-Go Validator
7. Facts Worksheet Template
8. 실행 Bundle 3종
9. Local Preflight / Tool Availability 결과

## 4. Go/No-Go 설계

### Hard Gate

`RUN-TIMEOUT`

```text
rollback = true
late commit = 0
TX result = ROLLBACK
client result = TIMEOUT
Hikari pending = 0
Hikari active restored
worker returned
context leak = 0
```

`RUN-P600`

```text
TPS >= 600
p95 <= 3s
```

`RUN-P1200`

```text
TPS >= 1200
p95 <= 3s
```

### Human/Open Gate

현재 최종 승인상한이 정해지지 않은 Error/Timeout Rate와 Resource Ceiling은 자동 Hard Fail로 만들지 않았다.

Working Review Threshold:

```text
CPU <= 70%
Busy Thread <= 70%
Hikari Active <= 80%
```

초과 시 Warning/Review 대상으로 분류한다.

## 5. Timeout Formal Acceptance 주의점

현재 알려진 기본 Timeout은:

```text
DB Query = 3s
TX       = 5s
Online   = 5s
```

Formal Acceptance 목표는:

```text
DB Query < TX < Online < Client
```

이므로 현재 `3 < 5 < 5` 구성은 Formal Acceptance Run 사전조건을 충족하지 않는다. 현재값으로 실행할 경우 Diagnostic Evidence로만 취급한다.

## 6. Local Preflight

| Tool | 상태 |
|---|---|
| Python/pytest | 사용 가능 |
| Java/javac/jcmd | 사용 가능 |
| curl | 사용 가능 |
| JMeter | 미설치 |
| Gatling | 미설치 |
| SQL*Plus | 미설치 |
| 실제 Runtime Identity | 미제공 |

따라서 실제 3개 Run 실행건수는 `0/3`이다.

## 7. 검증 결과

- TDD 최초 RED: Module 미구현으로 Collection Error 확인
- 구현 후 GREEN: 8 PASS
- Null Facts Template 버그 재현: 1 FAIL / 8 PASS
- Root Cause: key 존재만 검사하고 `null`을 incomplete로 보지 않음
- 수정 후 전체: 9 PASS
- Run Catalog: accepted=true, 3 Runs
- Execution Plan: `TIMEOUT → P600 → P1200`
- Empty Facts Template: 세 Run 모두 `INCOMPLETE`

## 8. Gate 영향

| Gate | 상태 | Wave3C 영향 |
|---|---|---|
| G40 | CONDITIONAL PASS | Timeout 실행절차 상세화 |
| G60 | CONDITIONAL PASS | 600/1200 운영 Runbook 상세화 |
| G80 | **HOLD** | 실제 Runtime Evidence 없음 |
| HG90 | **HOLD** | G80 미해소 |

## 9. 다음 단계

실제 환경에서 첫 3개 Run을 수행한 후 다음 절차로 진행한다.

```text
Runtime Bundle Upload/Ingestion
     ↓
Machine Evidence Evaluate
     ↓
P600 vs P1200 Comparison
     ↓
Hard Failure / Review 분류
     ↓
RUN-S1800 / HIKARI / SLOWSQL / N1
```
