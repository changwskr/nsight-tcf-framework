# Gate Rules

## 상태

`PASS / CONDITIONAL PASS / HOLD / REJECT`

## 판정 구조

```text
Rule
 ↓
Evaluator
 ↓
Measured Value
 ↓
Threshold
 ↓
Gate Decision
```

## CONDITIONAL PASS

반드시 아래를 포함한다.

- condition
- action
- owner
- dueDate
- risk

## 원칙

최종 HG90은 하위 Gate가 PASS 또는 허용된 CONDITIONAL PASS이며, Runtime Evidence와 Human Approval 조건이 충족되어야 한다.
