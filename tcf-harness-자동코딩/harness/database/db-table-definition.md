# NSIGHT 자동 하네스 DB 테이블 정의서

## 1. 설계 원칙

- Prefix: `HAR_`
- 문자열 업무 ID를 PK로 사용한다.
- 변경 가능한 테이블은 `VERSION_NO`로 낙관적 잠금을 수행한다.
- 대용량 Artifact·로그 본문은 파일/Object Storage에 보관하고 DB에는 URI·Hash·크기를 저장한다.
- 삭제보다 상태 전이와 `SUPERSEDED`, `RETIRED`, `INVALIDATED`를 사용한다.
- 상태 변경과 이벤트 발행은 Outbox Pattern으로 원자적으로 처리한다.

## 2. 테이블 그룹

| 그룹 | 테이블 |
|---|---|
| 프로젝트·기준선 | `HAR_PROJECT`, `HAR_BASELINE`, `HAR_WORKFLOW_DEF` |
| 실행 통제 | `HAR_RUN`, `HAR_RUN_STAGE`, `HAR_RUN_STEP`, `HAR_AGENT_TASK`, `HAR_TASK_ATTEMPT` |
| 복구·동시성 | `HAR_CHECKPOINT`, `HAR_RESOURCE_LOCK` |
| 산출물 | `HAR_ARTIFACT`, `HAR_ARTIFACT_VER`, `HAR_MANIFEST`, `HAR_MANIFEST_ITEM` |
| 요구·설계 | `HAR_REQUIREMENT`, `HAR_ASSUMPTION`, `HAR_DOMAIN`, `HAR_SERVICE_DEF`, `HAR_PROGRAM_COMP`, `HAR_SQL_STMT`, `HAR_DB_OBJECT`, `HAR_TEST_CASE` |
| 추적·Drift | `HAR_TRACE_LINK`, `HAR_DRIFT_ISSUE` |
| Evidence | `HAR_SOURCE_EVIDENCE`, `HAR_EXEC_EVIDENCE`, `HAR_TOOL_INVOCATION` |
| Gate·승인 | `HAR_GATE_DEF`, `HAR_GATE_RESULT`, `HAR_RULE_RESULT`, `HAR_APPROVAL`, `HAR_EXCEPTION_APPROVAL` |
| 품질·감사 | `HAR_ISSUE`, `HAR_AUDIT_LOG`, `HAR_OUTBOX_EVENT` |

## 3. 물리 DDL

1. `database/oracle/01_harness_core_tables.sql`
2. `database/oracle/02_harness_trace_governance_tables.sql`
3. `database/oracle/03_harness_indexes.sql`
4. `database/oracle/04_harness_reference_data.sql`
