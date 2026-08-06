# NSIGHT 자동 하네스 모듈·패키지 구조 설계서

## 1. 모듈 구조

```text
nsight-auto-harness/
├─ harness-common
├─ harness-domain
├─ harness-agent-contract
├─ harness-workflow
├─ harness-orchestrator
├─ harness-agent-runtime
├─ harness-gate-engine
├─ harness-workspace
├─ harness-trace
├─ harness-evidence
├─ harness-persistence
├─ harness-security
├─ harness-api
├─ harness-worker
├─ harness-audit
└─ harness-boot
```

## 2. 모듈 책임

| 모듈 | 책임 | 금지사항 |
|---|---|---|
| `harness-common` | ID·Hash·시간·오류·공통 Enum | Spring·DB 의존 |
| `harness-domain` | Run·Task·Artifact·Gate Aggregate와 상태 전이 | REST·MyBatis 의존 |
| `harness-agent-contract` | Agent Input/Output DTO·JSON Schema 검증 | Agent 실행 구현 |
| `harness-workflow` | Workflow 로딩·DAG 검증·컴파일 | Task 직접 실행 |
| `harness-orchestrator` | Run·Stage·Task·Retry·Checkpoint·Lock·Promotion | LLM·Shell 직접 호출 |
| `harness-agent-runtime` | Agent Registry·Dispatcher·Lease·Sandbox | Run 상태 변경 |
| `harness-gate-engine` | Rule 평가·점수·승인·예외·판정 | Artifact 생성 |
| `harness-workspace` | 디렉터리·Manifest·Atomic Write·Git Worktree | Gate 우회 승격 |
| `harness-trace` | TraceLink·영향분석·Drift | 기준정보 자동 확정 |
| `harness-evidence` | Build·Test·Security·Tool Evidence | Evidence 갱신·삭제 |
| `harness-persistence` | MyBatis Repository·Transaction·Outbox | 업무 판단 |
| `harness-security` | RBAC·Secret Proxy·Path·Tool·Network Policy | Secret 원문 로그 |
| `harness-api` | REST Controller·Validation·ProblemDetail | Mapper 직접 호출 |
| `harness-worker` | 분석·생성·Build·Test·Quality Worker | 운영 서버 접근 |
| `harness-audit` | 감사 이벤트·보관·Outbox 소비 | 감사로그 수정·삭제 |
| `harness-boot` | Spring Boot 조립·Control Plane 실행 | 도메인 로직 포함 |

## 3. 의존 방향

```text
common → domain
           ├→ agent-contract → agent-runtime
           ├→ workflow → orchestrator
           ├→ workspace → evidence
           └→ trace
orchestrator + agent-runtime + evidence + trace → gate-engine
모든 구현 Adapter → persistence
api / worker / audit → Application Port
boot → 실행 모듈 조립
```

금지 의존성:

```text
API → MyBatis Mapper 직접호출
Agent Runtime → Run 상태 직접변경
Gate Engine → 소스·문서 생성
Domain → Spring·MyBatis·Filesystem
Worker → 운영 DB·운영 서버
Workspace → Gate 판정
```

## 4. BASE 패키지

```text
com.nh.nsight.harness
├─ common.{id,hash,time,error}
├─ domain.{run,task,artifact,evidence,gate,approval,trace,drift,issue,audit}
├─ orchestrator.{application,statemachine,scheduler,dispatcher,retry,checkpoint,lock,promotion,recovery}
├─ agent.contract.{input,output,schema,validation}
├─ agent.runtime.{registry,dispatch,lease,sandbox,callback}
├─ workflow.{definition,loader,compiler,validation}
├─ gate.{engine,rule,evidence,scoring,approval,exception,promotion}
├─ workspace.{layout,manifest,artifact,atomicwrite,worktree,archive}
├─ trace.{registry,graph,impact,drift}
├─ evidence.{collector,build,test,security,tool}
├─ persistence.mybatis.{run,task,artifact,gate,trace,audit}
├─ persistence.{transaction,outbox}
├─ security.{authentication,authorization,secret,policy,masking}
├─ api.{run,workflow,task,artifact,gate,approval,trace,admin,advice}
├─ worker.{analysis,generation,build,test,quality}
├─ audit.{event,publisher,consumer,retention}
└─ boot
```

## 5. 핵심 인터페이스

```java
public interface RunCommandService {
    HarnessRunId createRun(CreateRunCommand command);
    void suspend(HarnessRunId runId, long expectedVersion, Actor actor);
    void resume(HarnessRunId runId, long expectedVersion, Actor actor);
    void requestCancel(HarnessRunId runId, long expectedVersion, Actor actor);
    void retryTask(TaskId taskId, String idempotencyKey, Actor actor);
}
```

```java
public interface AgentDispatcher {
    DispatchReceipt dispatch(AgentTask task, AgentInputEnvelope input);
    void cancel(TaskId taskId, AttemptNo attemptNo);
}
```

```java
public interface GateEvaluationService {
    GateResult evaluate(GateEvaluationCommand command);
}
```

```java
public interface ArtifactPromotionService {
    PromotionResult promote(PromotionCommand command);
}
```

## 6. 초기 배포 단위

```text
harness-control-plane
- API·Orchestrator·Gate Engine·상태 DB·승인·감사

harness-execution-worker
- LLM Agent·소스분석·코드생성·Build·Test·Security Tool
```

Control Plane과 Worker는 Task Queue와 Agent Contract로만 연계한다.
