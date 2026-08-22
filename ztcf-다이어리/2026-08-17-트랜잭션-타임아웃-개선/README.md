# PDMG 트랜잭션 타임아웃 개선 — 문서 세트

| 항목 | 값 |
| --- | --- |
| document-status | PROPOSED (구현 Phase 1~5 대기) |
| source-verified | 2026-08-22 (`pdmg-fw`, `pdmg-service` main/develop) |
| baseline-branch | `develop` |

## 읽는 순서

| 순서 | 문서 | 대상 | 목적 |
| ---: | --- | --- | --- |
| 1 | [pdmg-tx-timeout-easy.md](./pdmg-tx-timeout-easy.md) | 초보·신규 개발자 | TX / Timeout / SQL 3계층 개념 |
| 2 | [pdmg-tx-processing.md](./pdmg-tx-processing.md) | Spring TX 이해 필요자 | `@Transactional(REQUIRED)` 참여 조건 |
| 3 | [timeout-tx-mg-guide.md](./timeout-tx-mg-guide.md) | 구현 담당자 | **pdmg-fw vs pdmg-service** 수정 위치·순서 |
| 4 | [PDMG_트랜잭션_타임아웃_개선_설계서.md](./PDMG_트랜잭션_타임아웃_개선_설계서.md) | 아키텍트·리뷰어 | AS-IS / GAP / TO-BE / Gate / 테스트 전체 |

## 한 줄 요약

PDMG는 **Worker + `Future.get(timeout)` + `TransactionTemplate`** 로 응답 타임아웃과 Late Commit 방어는 이미 동작한다.  
개선 목표는 **ServiceId Deadline → Remaining Budget → TX Timeout → JDBC Timeout** 까지 End-to-End로 연결하는 것이다.

## 관련 소스 (검증 기준)

```text
pdmg-fw/src/main/java/nhnis/fw/tcf/
 ├─ core/facade/TcfFacade.java
 ├─ timeout/DefaultOnlineTimeoutExecutor.java   ← 1차 수정 대상
 ├─ timeout/OnlineTimeoutConfiguration.java
 ├─ timeout/OnlineTimeoutProperties.java
 ├─ stf/stf.java                                ← 거래통제 (타임아웃과 별개)
 └─ etf/etf.java                                ← Handler 후 elapsed 재점검

pdmg-service/src/main/resources/application.yml  ← nhnis.fw.timeout.*
pdmg-service/.../RdwDataSourceConfig.java

pdmg-fw/src/test/java/nhnis/fw/tcf/timeout/
 └─ DefaultOnlineTimeoutExecutorTest.java
```

## 주의: 타임아웃 ≠ 거래통제

| 구분 | 설정 | 역할 | 대표 프로그램 |
| --- | --- | --- | --- |
| **Timeout** | `nhnis.fw.timeout.*` | 제한시간·Worker Pool | `DefaultOnlineTimeoutExecutor` |
| **TxControl** | `nhnis.fw.txcontrol.*` | 허용/차단 (`TB_MG_TX_CONTROL`) | `mgcoa9001`, `MgTxControlService` |

두 정책은 `TcfFacade`에서 **STF(거래통제) → OnlineTimeoutExecutor(타임아웃)** 순으로 실행되며, 혼동하면 설계·운영 모두 Drift가 발생한다.

## 구현 상태 스냅샷 (2026-08-22)

| 기능 | 상태 |
| --- | --- |
| ServiceId별 YAML timeout override | ✅ 구현 |
| Worker Pool + Queue overload | ✅ 구현 |
| `Future.get(timeout)` + `cancel(true)` | ✅ 구현 |
| Worker 내 `TransactionTemplate` + Late Commit 방어 | ✅ 구현 |
| ETF elapsed 재점검 | ✅ 구현 |
| `TransactionTemplate.setTimeout(remaining)` | ✅ Phase 1 (`DefaultOnlineTimeoutExecutor`) |
| Worker 시작 전 Remaining Budget 검사 | ✅ Phase 1 (`min-start-budget-ms`, 기본 1000) |
| `ExecutionDeadline` | ✅ Phase 1 (`nhnis.fw.tcf.execution`) |
| ServiceId Transaction Policy (readOnly/manager) | ✅ Phase 3 (`TransactionPolicyResolver`) |
| `rdwTransactionManager` 고정 | ✅ 제거 (`TransactionManagerRegistry`) |
| MyBatis/JDBC 동적 Statement timeout | ✅ Phase 2 (`MybatisStatementTimeoutInterceptor`) |
| Client/Worker 종료 상태 분리 Evidence | ❌ Phase 4 |

## 관련 문서 (repo 내)

- `pdmg-fw/docs/timeout/01.timeout.md` — **구현 전 초안**. 현재 `DefaultOnlineTimeoutExecutor` 존재와 불일치. 본 세트를 SSOT로 사용.
- `pdmg-fw/docs/zdiary/2026-08-17-알아야 되는 아키텍처/` — TCF/STF/ETF 배경
