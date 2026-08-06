# NSIGHT Auto Harness Platform Contracts Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 요구사항 분석부터 설계·구현·검증·승격까지 통제하는 NSIGHT 자동 하네스의 모듈, 상태 DB, REST API, Agent 계약, Workflow와 Gate Rule을 구현한다.

**Architecture:** Control Plane은 영속 상태머신 기반 Orchestrator와 Gate Engine을 중심으로 구성하고, Execution Plane의 Agent Worker는 JSON Schema 계약을 통해서만 작업을 주고받는다. Artifact 원문은 Run별 격리 Workspace에 저장하고, 관계형 DB는 상태·메타정보·추적성·승인·Evidence 인덱스를 관리한다.

**Tech Stack:** Java 21, Spring Boot 3.3+, Gradle 8.x, Oracle 19c+, Jackson, JSON Schema Draft 2020-12, OpenAPI 3.1, YAML, JUnit 5, ArchUnit, MyBatis.

## Global Constraints

- 업무별 Agent는 Run·Stage 상태를 직접 변경하지 않는다.
- Orchestrator만 상태 전이와 Artifact Promotion을 수행한다.
- Agent 입력·출력은 `task-input-envelope.schema.json`, `task-output-envelope.schema.json`을 준수한다.
- Gate PASS에는 필수 Artifact, Evidence, Hard/Required Rule, 사람 승인이 모두 필요하다.
- 원본 입력과 승인 Evidence는 불변으로 관리한다.
- 운영 DB DDL 실행, 운영 배포, Git Push는 자동화 범위에서 제외한다.
- 패키지 Root는 `com.nh.nsight.harness`를 사용한다.

---

### Task 1: Gradle 멀티모듈 골격과 의존성 경계

**Files:**
- Create: `settings.gradle`
- Create: `build.gradle`
- Create: `harness-contract/build.gradle`
- Create: `harness-domain/build.gradle`
- Create: `harness-application/build.gradle`
- Create: `harness-orchestrator/build.gradle`
- Create: `harness-gate/build.gradle`
- Create: `harness-persistence/build.gradle`
- Create: `harness-api/build.gradle`
- Create: `harness-agent-runtime/build.gradle`
- Test: `harness-architecture-test/src/test/java/com/nh/nsight/harness/ModuleDependencyTest.java`

**Interfaces:**
- Produces: Gradle 프로젝트 `harness-*` 모듈과 단방향 의존성 규칙.

- [ ] **Step 1: 모듈 의존성 실패 테스트를 작성한다.**
- [ ] **Step 2: `./gradlew :harness-architecture-test:test`를 실행해 모듈이 없어 실패함을 확인한다.**
- [ ] **Step 3: `settings.gradle`과 모듈별 `build.gradle`을 생성한다.**
- [ ] **Step 4: ArchUnit에서 `domain`이 Spring·Persistence에 의존하지 않음을 검증한다.**
- [ ] **Step 5: 전체 테스트를 실행하고 Commit `feat: scaffold harness modules`를 생성한다.**

### Task 2: 실행 상태 도메인 모델

**Files:**
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/run/HarnessRun.java`
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/run/RunStatus.java`
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/task/AgentTask.java`
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/task/TaskStatus.java`
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/run/InvalidStateTransitionException.java`
- Test: `harness-domain/src/test/java/com/nh/nsight/harness/domain/run/HarnessRunTest.java`

**Interfaces:**
- Produces: `HarnessRun.transitionTo(RunStatus, TransitionContext)`와 상태 전이 규칙.

- [ ] **Step 1: 허용·금지 상태 전이 테스트를 작성한다.**
- [ ] **Step 2: 테스트가 모델 부재로 실패함을 확인한다.**
- [ ] **Step 3: 불변 식별자와 낙관적 버전을 가진 Aggregate를 구현한다.**
- [ ] **Step 4: 테스트를 통과시킨다.**
- [ ] **Step 5: Commit `feat: add harness run state machine`을 생성한다.**

### Task 3: Artifact·Evidence·Trace 메타모델

**Files:**
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/artifact/Artifact.java`
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/artifact/ArtifactVersion.java`
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/evidence/Evidence.java`
- Create: `harness-domain/src/main/java/com/nh/nsight/harness/domain/trace/TraceLink.java`
- Test: `harness-domain/src/test/java/com/nh/nsight/harness/domain/artifact/ArtifactTest.java`

**Interfaces:**
- Consumes: `RunId`.
- Produces: Hash 기반 Artifact 버전과 양방향 TraceLink.

- [ ] **Step 1: 동일 Hash 중복 버전 방지와 TraceLink 유효성 테스트를 작성한다.**
- [ ] **Step 2: 실패를 확인한다.**
- [ ] **Step 3: Artifact 상태와 관계유형 enum을 구현한다.**
- [ ] **Step 4: 테스트를 통과시킨다.**
- [ ] **Step 5: Commit `feat: add artifact evidence trace model`을 생성한다.**

### Task 4: Oracle 스키마와 MyBatis Repository

**Files:**
- Apply: `database/oracle/01_harness_core_tables.sql`
- Apply: `database/oracle/02_harness_trace_governance_tables.sql`
- Apply: `database/oracle/03_harness_indexes.sql`
- Apply: `database/oracle/04_harness_reference_data.sql`
- Create: `harness-persistence/src/main/java/com/nh/nsight/harness/persistence/run/HarnessRunMapper.java`
- Create: `harness-persistence/src/main/resources/mapper/harness/HarnessRunMapper.xml`
- Test: `harness-persistence/src/test/java/com/nh/nsight/harness/persistence/run/HarnessRunRepositoryIT.java`

**Interfaces:**
- Consumes: Domain Aggregate.
- Produces: `HarnessRunRepository.save`, `findById`, `updateWithVersion`.

- [ ] **Step 1: Testcontainers 또는 개발 Oracle 호환 환경에 Migration을 적용하는 통합 테스트를 작성한다.**
- [ ] **Step 2: Mapper 부재로 실패함을 확인한다.**
- [ ] **Step 3: Mapper Interface·XML·Repository Adapter를 구현한다.**
- [ ] **Step 4: 낙관적 잠금 충돌 테스트를 통과시킨다.**
- [ ] **Step 5: Commit `feat: persist harness state and metadata`를 생성한다.**

### Task 5: Workflow 로더와 컴파일러

**Files:**
- Create: `harness-orchestrator/src/main/java/com/nh/nsight/harness/orchestrator/workflow/WorkflowLoader.java`
- Create: `harness-orchestrator/src/main/java/com/nh/nsight/harness/orchestrator/workflow/WorkflowCompiler.java`
- Copy: `workflows/*.yaml` to `harness-orchestrator/src/main/resources/workflows/`
- Copy: `schemas/workflow-definition.schema.json` to resources.
- Test: `harness-orchestrator/src/test/java/com/nh/nsight/harness/orchestrator/workflow/WorkflowCompilerTest.java`

**Interfaces:**
- Produces: `CompiledWorkflow compile(WorkflowDefinition)`.

- [ ] **Step 1: Schema 오류, 미존재 의존 Stage, 순환 의존 테스트를 작성한다.**
- [ ] **Step 2: 테스트 실패를 확인한다.**
- [ ] **Step 3: YAML 파싱·Schema 검증·위상정렬을 구현한다.**
- [ ] **Step 4: 세 Workflow가 정상 컴파일되는지 검증한다.**
- [ ] **Step 5: Commit `feat: compile versioned harness workflows`를 생성한다.**

### Task 6: Agent 계약 Validator와 Task Dispatcher

**Files:**
- Create: `harness-contract/src/main/java/com/nh/nsight/harness/contract/TaskInputEnvelope.java`
- Create: `harness-contract/src/main/java/com/nh/nsight/harness/contract/TaskOutputEnvelope.java`
- Create: `harness-contract/src/main/java/com/nh/nsight/harness/contract/ContractValidator.java`
- Create: `harness-agent-runtime/src/main/java/com/nh/nsight/harness/agent/TaskDispatcher.java`
- Test: `harness-contract/src/test/java/com/nh/nsight/harness/contract/ContractValidatorTest.java`

**Interfaces:**
- Consumes: JSON Envelope.
- Produces: `ValidationResult validate(JsonNode, ContractRef)`와 `TaskLease dispatch(TaskId)`.

- [ ] **Step 1: 필수 필드·경로권한·Contract Major 불일치 테스트를 작성한다.**
- [ ] **Step 2: 실패를 확인한다.**
- [ ] **Step 3: JSON Schema Draft 2020-12 Validator를 구현한다.**
- [ ] **Step 4: Task Lease와 중복 Claim 방지 로직을 구현한다.**
- [ ] **Step 5: Commit `feat: enforce agent input output contracts`를 생성한다.**

### Task 7: Gate Engine과 Policy Evaluator

**Files:**
- Create: `harness-gate/src/main/java/com/nh/nsight/harness/gate/GateEngine.java`
- Create: `harness-gate/src/main/java/com/nh/nsight/harness/gate/RuleEvaluator.java`
- Create: `harness-gate/src/main/java/com/nh/nsight/harness/gate/GateDecision.java`
- Copy: `gate-rules/*.yaml` to resources.
- Test: `harness-gate/src/test/java/com/nh/nsight/harness/gate/GateEngineTest.java`

**Interfaces:**
- Consumes: `GateEvaluationRequest`, Artifact Manifest, Evidence, Approval.
- Produces: `GateResult evaluate(GateEvaluationRequest)`.

- [ ] **Step 1: Hard 실패, 점수 미달, 승인 대기, 유효 예외 테스트를 작성한다.**
- [ ] **Step 2: 테스트 실패를 확인한다.**
- [ ] **Step 3: Rule Set 로딩과 Evaluator Registry를 구현한다.**
- [ ] **Step 4: PASS·PASS_WITH_EXCEPTION·PENDING_APPROVAL·FAIL 판정을 구현한다.**
- [ ] **Step 5: Commit `feat: add evidence based gate engine`을 생성한다.**

### Task 8: Workspace와 Promotion Manager

**Files:**
- Create: `harness-artifact/src/main/java/com/nh/nsight/harness/artifact/workspace/WorkspaceManager.java`
- Create: `harness-artifact/src/main/java/com/nh/nsight/harness/artifact/workspace/PromotionManager.java`
- Create: `harness-artifact/src/main/java/com/nh/nsight/harness/artifact/workspace/ManifestService.java`
- Test: `harness-artifact/src/test/java/com/nh/nsight/harness/artifact/workspace/PromotionManagerTest.java`

**Interfaces:**
- Produces: Atomic Write, SHA-256 등록, `promote(ManifestId, GateResultId)`.

- [ ] **Step 1: 승인 없는 승격, Hash 불일치, 원본 수정 차단 테스트를 작성한다.**
- [ ] **Step 2: 실패를 확인한다.**
- [ ] **Step 3: Run 디렉터리 생성과 Atomic Write를 구현한다.**
- [ ] **Step 4: Gate PASS Manifest만 Promotion하도록 구현한다.**
- [ ] **Step 5: Commit `feat: isolate workspaces and gate promotions`를 생성한다.**

### Task 9: Orchestrator Application Service

**Files:**
- Create: `harness-application/src/main/java/com/nh/nsight/harness/application/run/CreateRunService.java`
- Create: `harness-application/src/main/java/com/nh/nsight/harness/application/task/CompleteTaskService.java`
- Create: `harness-orchestrator/src/main/java/com/nh/nsight/harness/orchestrator/RunCoordinator.java`
- Test: `harness-orchestrator/src/test/java/com/nh/nsight/harness/orchestrator/RunCoordinatorTest.java`

**Interfaces:**
- Consumes: Workflow, Task Output, Gate Result.
- Produces: 결정적 상태 전이와 후속 Task 생성.

- [ ] **Step 1: Golden Path와 실패·재시도·Checkpoint 복구 테스트를 작성한다.**
- [ ] **Step 2: 실패를 확인한다.**
- [ ] **Step 3: Stage Scheduler와 Result Receiver를 구현한다.**
- [ ] **Step 4: Outbox 이벤트와 Checkpoint 저장을 구현한다.**
- [ ] **Step 5: Commit `feat: orchestrate durable harness runs`를 생성한다.**

### Task 10: REST API 구현

**Files:**
- Copy: `api/openapi.yaml` to `harness-api/src/main/resources/openapi/`.
- Create: `harness-api/src/main/java/com/nh/nsight/harness/api/RunController.java`
- Create: `harness-api/src/main/java/com/nh/nsight/harness/api/GateController.java`
- Create: `harness-api/src/main/java/com/nh/nsight/harness/api/ApprovalController.java`
- Create: `harness-api/src/main/java/com/nh/nsight/harness/api/InternalAgentController.java`
- Test: `harness-api/src/test/java/com/nh/nsight/harness/api/RunControllerTest.java`

**Interfaces:**
- Implements: `api/openapi.yaml` operationId 전부.

- [ ] **Step 1: OpenAPI 계약 기반 MockMvc 실패 테스트를 작성한다.**
- [ ] **Step 2: Controller 부재 실패를 확인한다.**
- [ ] **Step 3: Public Control API와 Internal Worker API를 구현한다.**
- [ ] **Step 4: Idempotency-Key·If-Match·Problem Details 처리를 구현한다.**
- [ ] **Step 5: Commit `feat: expose harness control plane api`를 생성한다.**

### Task 11: 보안·감사·관측성

**Files:**
- Create: `harness-api/src/main/java/com/nh/nsight/harness/api/security/HarnessSecurityConfiguration.java`
- Create: `harness-application/src/main/java/com/nh/nsight/harness/application/audit/AuditPublisher.java`
- Create: `harness-observability/src/main/java/com/nh/nsight/harness/observability/HarnessMetrics.java`
- Test: `harness-api/src/test/java/com/nh/nsight/harness/api/security/HarnessSecurityTest.java`

**Interfaces:**
- Produces: 역할 기반 API 인가, 불변 Audit Event, Run·Task·Gate Metric.

- [ ] **Step 1: 권한 없는 승인·Agent API 접근 차단 테스트를 작성한다.**
- [ ] **Step 2: 실패를 확인한다.**
- [ ] **Step 3: JWT Role 매핑과 Worker Token 분리를 구현한다.**
- [ ] **Step 4: 상태 전이·Tool 호출·Promotion Audit와 Metric을 구현한다.**
- [ ] **Step 5: Commit `feat: secure and observe harness operations`를 생성한다.**

### Task 12: End-to-End Golden Path 검증

**Files:**
- Create: `harness-e2e-test/src/test/java/com/nh/nsight/harness/e2e/OnlineInquiryGoldenPathIT.java`
- Create: `harness-e2e-test/src/test/resources/fixtures/online-inquiry-input/`
- Modify: CI Pipeline configuration.

**Interfaces:**
- Consumes: 전체 구현.
- Produces: RUN 등록부터 HG-90과 Release Manifest까지의 E2E 증적.

- [ ] **Step 1: 조회 거래 1건의 E2E 테스트를 작성한다.**
- [ ] **Step 2: 전체 구성 미완성으로 실패함을 확인한다.**
- [ ] **Step 3: Fake Agent Worker와 실제 Gate Rule을 연결한다.**
- [ ] **Step 4: `./gradlew clean test`와 E2E Test를 통과시킨다.**
- [ ] **Step 5: Commit `test: verify harness golden path end to end`를 생성한다.**
