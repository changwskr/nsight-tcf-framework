# TX · Timeout · Worker · Statement — 현행 인덱스

> 작성: 2026-08-25  
> `ztcf-다이어리/2026-08-17-알아야 되는 아키텍처` 내 트랜잭션·타임아웃·Worker·JDBC Statement 관련 문서 진입점.

## 한 줄 요약

```text
TcfFacade → ExecutionDeadline → OnlineTimeoutExecutor(Worker + Future.get)
  → TransactionTemplate(Policy) → Dispatcher → Handler → Facade → Service → DAO
  → MyBatis queryTimeout + TrackingStatement 등록
Timeout: cancelAll(Statement.cancel) → future.cancel(true) → 504 FW_TIMEOUT
```

## 정책·설명 (우선 읽기)

| 주제 | 문서 |
| --- | --- |
| Timeout 정책 본문 | [pdmg-service-docs/20.타임아웃.md](./pdmg-service-docs/20.타임아웃.md) |
| Timeout Q&A | [pdmg-service-docs/20.타임아웃-1.md](./pdmg-service-docs/20.타임아웃-1.md) |
| 요청 Thread vs Worker | [pdmg-service-docs/33.요청쓰레드와 Work쓰레드 분리아키텍처.md](./pdmg-service-docs/33.요청쓰레드와%20Work쓰레드%20분리아키텍처.md) |
| serviceId별 조정 | [pdmg-service-docs/34.서비스ID별 타임아웃조정 방법.md](./pdmg-service-docs/34.서비스ID별%20타임아웃조정%20방법.md) |
| FW Timeout | [pdg-fw-docs/01.timeout.md](./pdg-fw-docs/01.timeout.md) |
| TX + Timeout 구조 | [pdg-fw-docs/PDMG Transaction + Timeout 아키텍처 구조.md](./pdg-fw-docs/PDMG%20Transaction%20+%20Timeout%20아키텍처%20구조.md) |
| TCF Big Picture | [pdg-fw-docs/pdmg-fw TCF 아키텍처.md](./pdg-fw-docs/pdmg-fw%20TCF%20아키텍처.md) |
| 온라인 거래 전체 | [pdg-fw-docs/온라인 거래 아키텍처.md](./pdg-fw-docs/온라인%20거래%20아키텍처.md) |

## 책·설계 (보조)

| 문서 | 비고 |
| --- | --- |
| [book/chapter/23장.타임아웃과 작업 취소.md](./pdmg-service-docs/book/chapter/23장.타임아웃과%20작업%20취소.md) | 장문 정책 반영본 |
| [book/chapter-확장본/23장…ASCII…](./pdmg-service-docs/book/chapter-확장본/23장.타임아웃과_작업_취소_ASCII_확장본.md) | 서두 현행 배너만; 본문은 학습용 |
| [2026-08-09-…design.md](./pdmg-service-docs/2026-08-09-pdmg-online-timeout-executor-design.md) | 설계 스냅샷 + IMPLEMENTED 배너 |

## 현행 vs 구버전 주의

| 구버전 서술 | 현행 |
| --- | --- |
| serviceId별 timeout 없음 | `nhnis.fw.timeout.overrides` |
| JDBC cancel 없음 | `ActiveJdbcStatementRegistry.cancelAll` → `Statement.cancel()` |
| interrupt만으로 즉시 중단 기대 | cancel + interrupt + QueryTimeout + Deadline 재검사 (best-effort) |
| Facade `@Transactional`만으로 최외곽 TX | Worker `TransactionTemplate` + optional `transaction.services` Policy |

원본 백업은 각 문서와 같은 폴더의 `{이름} - 원본.md`를 본다.
