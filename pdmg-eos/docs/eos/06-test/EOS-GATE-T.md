# EOS GATE-T — Test Gate

> 판정일: 2026-08-16  
> 환경: local H2 · port **8082** · `PdmgEosApplication`

## 판정: **PASS** (스모크 + JUnit)

| # | 검증 | 결과 |
|---|------|------|
| T-01 | bootRun 기동 | ✅ `Started PdmgEosApplication` |
| T-02 | schema.sql BOM | ✅ 제거 후 기동 성공 |
| T-03 | flat JSON → dto 호환 | ✅ `OnlineTransactionController` fallback |
| T-04 | 0110/0120/0130/0140/0141/0150/0151/0160/0170/0180/0190/0100 S0 | ✅ RSLT_CD=0000 |
| T-05 | 0150C0 재평가 | ✅ RSLT_CD=0000 |
| T-07 | `@SpringBootTest` IT | ✅ `PdmgEosApplicationIT` |

요청 예:

```http
POST /eoscoa0120S0
Content-Type: application/json
rms_svc_c: eoscoa0120S0

{"pageNo":1,"pageSize":5}
```

(또는 `{"dto":{...}}` 래핑)

## JUnit (자동화)

```text
./gradlew :test
```

| 테스트 | 커버 |
|--------|------|
| `EosRiskCalculatorTest` | 총점·등급·점수범위 |
| `EosStatusEngineTest` | NORMAL/APPROACHING/DUE/OVERDUE/EOL |
| `EosActionStateMachineTest` | 전이·DONE 직행 금지 |
| `EosIdGeneratorTest` | CONF-003 ID |
| `eoscoa0150ServiceRiskTest` | 서버 등급 계산 |
| `eoscoa0160ServiceTransitionTest` | 전이 + **0165 SoD** |
| `PdmgEosApplicationIT` | 컨텍스트 + 0110/0120/0130 MockMvc + batch.runAll |

## 잔여

- Spring Boot 통합 테스트(`@SpringBootTest`) — ✅
- UI E2E (Playwright 등) — 선택(P1)
- 알림 연동 — P1

## NEXT

배치 스케줄 또는 Boot 통합 테스트
