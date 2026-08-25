# P0 Closure Wave 4 — ADR Finalization & Re-Gate Preparation

> Candidate: `NSIGHT-ARCH-CANDIDATE-2026-08-19`  
> Current Gate: **G80 HOLD / HG90 HOLD**

## 1. 목적

Wave 4는 Runtime 결과를 생성하는 단계가 아니라, HG90을 막고 있는 Human Decision을 승인 가능한 ADR 형태로 정리하고 실제 Runtime Evidence가 들어오는 즉시 G80을 재판정할 수 있게 만드는 단계다.

## 2. ADR 준비상태

| Readiness | Count | 의미 |
|---|---:|---|
| READY_FOR_HUMAN_DECISION | 11 | 권고안/대안/영향이 정리되어 승인 가능하나 아직 승인되지 않음 |
| NEEDS_OWNER_INPUT | 1 | 업무/운영 승인값 입력 필요 |
| NEEDS_OWNER_INPUT_AND_RUNTIME | 2 | 정책 입력과 Runtime 검증 모두 필요 |
| RUNTIME_DEPENDENT | 2 | Runtime 결과 전 최종 선택 금지 |

모든 ADR의 `APPROVED` 여부는 Human Sign-off가 기록되기 전까지 승격하지 않는다.

## 3. Human Decision과 Runtime Evidence 분리

```text
ADR Proposal
   ↓
Human Decision
   ↓
Implementation / Configuration
   ↓
Runtime Evidence
   ↓
Closure Check
   ↓
G80 Re-Gate
   ↓
HG90 Sign-off
```

Human Decision이 있다고 Runtime Evidence가 생기는 것은 아니며, Runtime PASS가 있어도 해당 정책/기준의 Owner 승인이 없으면 HG90을 통과하지 않는다.

## 4. Re-Gate Hard Rule

G80 재판정에서 다음이 하나라도 남으면 `HOLD`다.

- P0 ADR 미승인
- Mandatory Runtime Run이 `PASS + PRODUCTION_RUNTIME + runtime_approved=true`가 아님
- P0 Closure Item이 `CLOSED_STATIC/CLOSED_RUNTIME/CLOSED_APPROVED`가 아님

P0 Hard Blocker가 모두 닫혔지만 P1/Open 조건이 남으면 `CONDITIONAL_PASS`, 모두 닫히면 `PASS_CANDIDATE`로 평가한 뒤 HG90 Human Sign-off로 넘긴다.

## 5. 현재 결과

실제 Production Runtime은 아직 `0/12`이고 ADR Human Approval도 기록되지 않았으므로 Wave4 Preflight는 **HOLD**가 정상 결과다.
