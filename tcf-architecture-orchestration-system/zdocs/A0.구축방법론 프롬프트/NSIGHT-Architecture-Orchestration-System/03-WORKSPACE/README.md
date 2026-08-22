# Workspace

실행 중 상태 충돌을 막기 위해 각 Run은 독립 공간을 사용한다.

권장:

```text
03-WORKSPACE/
├─ _TEMPLATE/
│   ├─ 00-IN
│   ├─ 10-DOCUMENT
│   ├─ 20-MODEL
│   ├─ 30-CODE
│   ├─ 40-TEST
│   ├─ 50-RUNTIME-EVIDENCE
│   ├─ 60-DRIFT
│   ├─ 70-GAP-ADR
│   ├─ 80-GATE
│   └─ 90-OUT
└─ RUNS/
    └─ ACL-RUN-YYYYMMDD-NNN/
```

이 패키지는 `_TEMPLATE`의 Markdown 가이드를 제공한다.
