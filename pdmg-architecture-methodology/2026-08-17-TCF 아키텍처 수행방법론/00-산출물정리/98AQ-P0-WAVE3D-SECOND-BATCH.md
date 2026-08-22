# 98AQ. P0 Closure Wave 3D — Remaining Runtime Batch Operationalization

## 1. 목적

Wave 3C의 1차 실행군(`RUN-TIMEOUT → RUN-P600 → RUN-P1200`) 이후 남은 9개 Runtime Run을 운영자 실행 수준으로 구체화한다. 이 문서는 **실행결과가 아니라 실행준비 산출물**이다.

## 2. 실행 순서

```text
RUN-S1800
  ↓
RUN-HIKARI / RUN-SLOWSQL
  ↓
RUN-N1
  ↓
RUN-SESSION
  ↓
RUN-CF
  ↓
RUN-TRACE
  ↓
RUN-ROLLING
  ↓
RUN-JWT-ROTATE
  ↓
G80 Re-Gate
```

## 3. 핵심 판정정책

- Stress 1,800 TPS는 포화/열화/복구 특성 확인이 목적이며 p95 3초를 자동 Hard Gate로 임의 승격하지 않는다.
- Hikari의 70~80%는 Working 관리범위이며 최종 Pool/DB Session Hard Limit은 DBA/Runtime 승인값이 필요하다.
- Session Run은 Session ADR 없이는 PASS 불가하다.
- Center Failure Run은 승인된 RTO/RPO 없이는 PASS 불가하다. 전략상 Online AP RTO 30분 이내 기준은 참고값이며 개별 시스템 승인값으로 자동 대체하지 않는다.
- Rolling Run은 Peak 잔여용량과 DB/Config backward compatibility를 함께 검증한다.
- JWT Rotation은 canonical build의 KMS/HSM Key Provider가 먼저 완료되어야 한다.

## 4. 현재 Gate

`G80 = HOLD`, `HG90 = HOLD`를 유지한다. Runtime Evidence 0/12 상태는 문서 준비만으로 변경하지 않는다.
