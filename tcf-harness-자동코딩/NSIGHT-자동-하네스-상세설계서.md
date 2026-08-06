# NSIGHT 요구분석·설계·구현 자동 하네스 상세설계서

> 원본: `NSIGHT 자동 하네스 상세설계서.docx`  
> 변환: Markdown (구조·표·흐름 보존)

---

**Harness Orchestrator · 상태/메타모델 · Agent 계약 · Gate Engine · 작업공간**

---

## 1. 도입 전 안내말

NSIGHT 자동 하네스의 핵심은 여러 Agent를 순서대로 호출하는 것이 아니다.

하네스는 다음 다섯 요소가 하나의 통제 구조로 동작할 때 비로소 개발 실행 플랫폼이 된다.


```text
Harness Orchestrator
  ├─ 어떤 작업을 언제 실행할 것인가
  ├─ 실패하면 어디에서 재개할 것인가
  └─ 다음 단계로 진행 가능한가

상태·메타모델
  ├─ 현재 무엇이 확정되었는가
  ├─ 어떤 산출물이 생성되었는가
  └─ 요구사항부터 코드까지 어떻게 연결되는가

Agent 입출력 계약
  ├─ Agent가 무엇을 입력받는가
  ├─ 무엇을 반환해야 하는가
  └─ 무엇을 변경해서는 안 되는가

Gate Engine
  ├─ 통과 기준이 무엇인가
  ├─ 어떤 Evidence가 필요한가
  └─ 누가 승인해야 하는가

작업공간
  ├─ 원본과 생성물을 어떻게 분리하는가
  ├─ 어느 시점에 산출물을 승격하는가
  └─ 실행 결과를 어떻게 재현하는가
```

자동 하네스는 완전 자율형 코드 생성기가 아니다.


```text
업무·아키텍처 판단
= 사람이 책임

반복적인 분석·설계·생성
= Agent가 수행

단계 전환과 실행 통제
= Orchestrator가 수행

완료 여부 판정
= Gate Engine이 수행

완료 사실 입증
= Evidence가 수행
```

기존 요구사항 정의서는 자동 하네스를 요구사항 분석부터 설계, 구현, 테스트, Drift 검증과 최종 패키징까지 관리하는 승인형 개발 실행 플랫폼으로 정의하고 있다. 또한 중요 의사결정은 사람이 승인하고, Build·Test·추적성·보안 검증 결과를 완료 증적으로 남기도록 요구한다.

본 상세설계는 그 요구사항을 실제 구현 가능한 구조로 구체화한다.


## 2. 문서 개요


### 2.1 목적

본 설계서의 목적은 NSIGHT 자동 하네스의 다음 구성요소에 대한 상세 아키텍처를 정의하는 것이다.


| 설계 대상 | 목적 |
| --- | --- |
| Harness Orchestrator | Run·단계·작업·Agent·재시도·복구 통제 |
| 상태·메타모델 | 요구사항부터 Evidence까지 단일 상태원장 구성 |
| Agent 입출력 계약 | Agent 간 결합을 낮추고 결과 형식을 표준화 |
| Gate Engine | 규칙·증적·사람 승인을 통한 단계 승격 통제 |
| 작업공간 디렉터리 | 원본·초안·승인본·증적·최종본의 물리적 분리 |
| 공통 운영기준 | 보안·감사·성능·관측성·변경관리 기준 정의 |


### 2.2 적용범위

본 설계는 다음 자동화 시나리오에 적용한다.


- 신규 온라인 조회 거래
- CRUD 거래
- 기존 ServiceId 변경
- 신규 업무 WAR 생성
- 화면 이벤트와 서버 거래 연결
- Handler·Facade·Service·Rule·DAO·Mapper 생성
- Mapper XML·SQL·DDL 생성
- OM Service Catalog·Timeout·거래통제 등록 초안
- 테스트 코드와 테스트 시나리오 생성
- Gradle Build·JUnit·ArchUnit 검증
- 요구사항–설계–코드–테스트 추적성 검증
- As-Designed와 As-Built Drift 검증
- 최종 문서·소스·Evidence 패키징

다음 행위는 적용범위에서 제외한다.


```text
운영 DB에 DDL 직접 실행
운영 서버 자동 배포
승인 없는 Git Commit·Push
승인 없는 OM 운영 기준정보 변경
보안정책·데이터 소유권의 자동 확정
테스트 실패를 숨기기 위한 테스트 삭제
```


### 2.3 대상 독자


- 애플리케이션 아키텍트
- 프레임워크 개발자
- 자동화 플랫폼 개발자
- 데이터 아키텍트
- 보안 아키텍트
- 업무 개발자
- QA·테스트 담당자
- DevOps 담당자
- PMO·Architecture Review Board
- 운영 담당자


### 2.4 선행조건


| 항목 | 선행조건 |
| --- | --- |
| 기준 소스 | 대상 Git 저장소와 Branch·Commit SHA 확정 |
| 기준 문서 | 공식 설계·개발·보안·운영 기준원 지정 |
| 개발환경 | JDK·Gradle·DB·테스트 실행환경 준비 |
| 업무식별 | 프로젝트·업무코드·도메인 식별 |
| 저장소 | 상태 DB, Artifact 저장소, Git 작업공간 준비 |
| 보안 | Agent별 계정·권한·Secret 접근정책 준비 |
| 승인체계 | BA·AA·DA·SEC·QA 승인자 등록 |
| 규칙 | Gate Rule과 예외 승인정책 등록 |


### 2.5 용어 정의


| 용어 | 정의 |
| --- | --- |
| Run | 하나의 요구사항 묶음을 처리하는 최상위 실행 단위 |
| Stage | 분석·설계·구현·검증과 같은 생명주기 단계 |
| Step | Stage 안에서 실행되는 최소 Orchestration 단위 |
| Task | Agent 또는 Tool에 할당되는 실행 가능한 작업 |
| Attempt | Task의 개별 실행 시도 |
| Checkpoint | 재시작 가능한 일관된 실행 시점 |
| Artifact | 문서·소스·SQL·설정·테스트 등 생성 결과 |
| Evidence | Build 로그·Test 결과·검사 보고서 등 완료 증적 |
| Gate | 다음 Stage로 승격할 수 있는지를 판정하는 관문 |
| Promotion | 산출물을 다음 작업공간으로 승격하는 행위 |
| Agent Contract | Agent가 준수해야 할 입력·출력·권한 계약 |
| Idempotency | 같은 작업을 재실행해도 결과가 중복되지 않는 성질 |
| Drift | 기준 설계와 실제 구현 또는 등록정보 간 차이 |


## 3. 본문


## 3.1 문제 정의 및 설계 배경

자동 하네스를 단순한 Agent 호출 순서로 구현하면 다음 문제가 발생한다.


```text
분석 Agent가 설계까지 임의 수행
→ 책임 경계 불명확

Agent가 자유 형식 문장을 반환
→ 다음 Agent가 결과를 해석하지 못함

실패 후 처음부터 재실행
→ 비용·시간 낭비와 결과 불일치

설계 승인 전에 구현 Agent 실행
→ 미확정 요구사항이 코드로 고정

테스트 실패 후 자동으로 테스트 수정
→ 실제 결함 은폐

파일을 동일 디렉터리에서 직접 수정
→ 원본과 생성물 구분 불가

통과 기준이 프롬프트에만 존재
→ 동일 결과에 대한 판정이 달라짐
```

따라서 자동 하네스는 다음을 강제해야 한다.


```text
흐름
= Orchestrator의 상태머신으로 통제

정보
= 공통 메타모델로 관리

Agent 연계
= 구조화된 계약으로 통제

단계 승격
= Gate Engine으로 통제

파일
= 불변 원본과 단계별 작업공간으로 통제
```

화면 이벤트에서 ServiceId, Handler, 프로그램, SQL, DB 객체까지 정방향·역방향 추적할 수 있어야 하므로 하네스의 상태모델도 단순한 파일 목록이 아니라 관계형 추적 그래프를 제공해야 한다.


## 3.2 현행 구조와 문제점


| 현행 방식 | 문제 | 상세설계 대응 |
| --- | --- | --- |
| Agent가 다음 작업 결정 | 통제 불가능 | Orchestrator만 상태 전이 가능 |
| 자유 형식 Prompt·Response | 파싱 불안정 | JSON Schema 기반 계약 |
| 디렉터리 직접 수정 | 원본 훼손 | Staging·Patch·Promotion 분리 |
| 메모리 기반 실행상태 | 재기동 시 유실 | 영속 상태 DB와 Checkpoint |
| 단일 성공·실패 상태 | 원인 분석 곤란 | Run·Stage·Step·Task별 상태 |
| Agent 자체 품질판정 | 이해관계 충돌 | 별도 Quality Agent와 Gate Engine |
| 파일 존재 여부로 완료판정 | 품질 미입증 | Evidence 기반 Gate |
| 재실행 시 중복 생성 | 결과 오염 | Idempotency Key·Content Hash |
| 예외를 구두 승인 | 감사 불가능 | Approval·Exception 엔터티 |
| 과거 실행 덮어쓰기 | 비교 불가능 | Run별 독립 작업공간·Archive |


## 3.3 요구사항과 제약조건


### 3.3.1 핵심 상세 요구사항


| ID | 상세 요구사항 |
| --- | --- |
| HD-001 | Orchestrator만 Run과 Stage 상태를 변경할 수 있어야 한다. |
| HD-002 | Agent는 자신에게 할당된 Task 상태만 갱신할 수 있어야 한다. |
| HD-003 | 모든 Task는 재실행 가능한 Idempotency Key를 가져야 한다. |
| HD-004 | 모든 Agent 결과는 공통 Output Envelope로 반환해야 한다. |
| HD-005 | Agent의 자연어 설명과 기계 처리 데이터를 분리해야 한다. |
| HD-006 | Gate는 Evidence 없이 PASS할 수 없어야 한다. |
| HD-007 | Hard Rule 실패는 점수와 관계없이 Gate를 FAIL 처리해야 한다. |
| HD-008 | 사람 승인이 필요한 Gate는 자동으로 PASS할 수 없어야 한다. |
| HD-009 | 승인되지 않은 Artifact는 다음 Stage의 입력으로 사용할 수 없어야 한다. |
| HD-010 | 원본 파일은 Run 종료까지 변경할 수 없어야 한다. |
| HD-011 | 파일 이동은 실제 이동보다 Manifest 기반 승격을 우선해야 한다. |
| HD-012 | 실행 중 장애 발생 시 마지막 Checkpoint부터 재개할 수 있어야 한다. |
| HD-013 | 동일 프로젝트의 충돌 가능한 Run을 동시 실행하지 않아야 한다. |
| HD-014 | Agent가 사용한 모델·Prompt·Tool 버전을 기록해야 한다. |
| HD-015 | 모든 결과는 Source Evidence 또는 설계 가정과 연결되어야 한다. |


### 3.3.2 기술 제약


- Run 상태는 관계형 DB에 영속화한다.
- 대용량 파일과 로그는 Object Storage 또는 파일 저장소에 보관한다.
- 소스 변경은 Git Worktree 또는 격리된 Clone에서 수행한다.
- 상태 DB에 소스 파일 전문을 저장하지 않는다.
- Agent 결과는 JSON Schema로 검증한다.
- Agent는 운영망과 운영 DB에 직접 접근하지 않는다.
- Shell·Network·DB Tool은 Allowlist 기반으로 호출한다.
- Agent 간 직접 호출을 허용하지 않는다.
- 모든 Agent 호출은 Orchestrator를 경유한다.
- Gate 규칙은 코드와 분리된 Policy 파일로 관리한다.


## 3.4 설계 원칙


| 원칙 | 상세 기준 |
| --- | --- |
| 결정적 흐름 | Agent 응답 내용이 아니라 상태머신과 Rule로 다음 단계를 결정 |
| 단일 작성자 | 상태 전이는 Orchestrator만 수행 |
| 계약 우선 | Agent 구현보다 입출력 계약을 먼저 확정 |
| 불변 원본 | 00-IN, Baseline, Evidence는 수정 금지 |
| 복사 후 변경 | 기존 소스는 격리 작업공간에서만 변경 |
| 승격 방식 | Gate 통과 결과만 다음 디렉터리와 상태로 승격 |
| Evidence 우선 | 설명보다 실행 로그와 결과 파일을 우선 |
| 최소 권한 | Agent별 읽기·쓰기·Tool 권한 분리 |
| 실패 격리 | Agent 실패가 다른 Run이나 승인된 Artifact를 훼손하지 않음 |
| 재현 가능 | 입력·버전·규칙·Prompt·Tool 정보 보존 |
| 양방향 추적 | 요구사항에서 코드, 코드에서 요구사항 모두 조회 가능 |
| 사람 책임 유지 | 업무·보안·데이터·운영 의사결정은 승인자 책임 |


## 3.5 대안 비교 및 의사결정


### 3.5.1 Orchestrator 구현 대안


| 대안 | 방식 | 장점 | 단점 |
| --- | --- | --- | --- |
| A | Agent가 다음 Agent를 호출 | 구현 단순 | 통제·감사·복구 어려움 |
| B | 고정 배치 스크립트 | 재현성 양호 | 분기·승인·재처리 부족 |
| C | 영속 상태머신 기반 Orchestrator | 분기·복구·승인·감사 가능 | 초기 설계 복잡 |
| D | 범용 BPM 제품 중심 | 화면·승인기능 풍부 | Agent·Artifact 세밀 통제가 어려울 수 있음 |


#### 결정

C. 영속 상태머신 기반 Orchestrator를 적용한다.

BPM 기능은 승인 화면과 운영 UI에 활용할 수 있으나, Run·Task·Artifact·Evidence의 핵심 상태는 하네스 자체 메타모델이 소유한다.


### 3.5.2 Agent 통신 대안


| 대안 | 판단 |
| --- | --- |
| 자유 형식 Markdown | 설명에는 적합하나 자동 연계에 부적합 |
| 공유 DB 직접 수정 | 강결합과 데이터 오염 위험 |
| 파일 Drop 방식 | 단순하지만 상태·실패 관리 부족 |
| JSON Contract + Artifact URI | 최종 권장 |


### 3.5.3 Gate 판정 대안


```text
평균 점수 방식만 적용
→ 보안 위반이 다른 점수로 상쇄될 수 있음
→ 금지

Hard Rule + Soft Score + Human Approval
→ 최종 권장
```


## 3.6 목표 아키텍처


```text
┌───────────────────────────────────────────────────────┐
│                    Harness Console                    │
│ Run 등록 · 상태조회 · 승인 · 반려 · Evidence 조회    │
└──────────────────────────┬────────────────────────────┘
                           │ Command / Query
                           ▼
┌───────────────────────────────────────────────────────┐
│                 Harness Orchestrator                  │
│                                                       │
│ Run Manager          State Machine                    │
│ Workflow Compiler    Stage/Step Scheduler             │
│ Task Dispatcher      Retry/Timeout Controller         │
│ Checkpoint Manager   Lock/Concurrency Manager         │
│ Promotion Manager    Recovery Manager                 │
└──────────────┬──────────────────────┬─────────────────┘
               │ Task Contract        │ Gate Request
               ▼                      ▼
┌──────────────────────────┐  ┌─────────────────────────┐
│       Agent Runtime      │  │       Gate Engine       │
│                          │  │                         │
│ Intake Agent             │  │ Rule Evaluator          │
│ Requirement Agent        │  │ Evidence Validator      │
│ Domain/Design Agent      │  │ Score Calculator        │
│ Code Agent               │  │ Approval Controller     │
│ Test Agent               │  │ Exception Manager       │
│ Quality/Security Agent   │  │ Promotion Decision      │
│ Documentation Agent      │  └────────────┬────────────┘
└──────────────┬───────────┘               │
               │ Artifact/Evidence          │
               ▼                            ▼
┌───────────────────────────────────────────────────────┐
│                    State Platform                     │
│                                                       │
│ Relational State DB  Traceability Graph               │
│ Artifact Registry    Evidence Registry                │
│ Object/File Storage  Audit Log                        │
└──────────────────────────┬────────────────────────────┘
                           │
                           ▼
┌───────────────────────────────────────────────────────┐
│                  Isolated Workspace                   │
│ IN → BASELINE → ANALYSIS → DESIGN → IMPLEMENTATION   │
│ → TEST → EVIDENCE → REVIEW → OUT → ARCHIVE           │
└───────────────────────────────────────────────────────┘
```


## 3.7 Harness Orchestrator 상세설계


### 3.7.1 책임

Harness Orchestrator는 업무 내용을 판단하는 구성요소가 아니다.

다음 실행 통제만 담당한다.


```text
Run 생성
→ Workflow 결정
→ Stage 진입조건 확인
→ Step 생성
→ Task 할당
→ Agent 결과 수신
→ 결과 계약 검증
→ Checkpoint 저장
→ Gate 요청
→ Gate 결과에 따른 승격·반려·대기
→ 재시도·복구
→ 종료·Archive
```


### 3.7.2 내부 구성요소


| 구성요소 | 책임 |
| --- | --- |
| Run Manager | Run 생성·조회·취소·종료 |
| Workflow Registry | 업무유형별 Workflow 정의 관리 |
| Workflow Compiler | Workflow 정의를 Stage·Step 실행계획으로 변환 |
| State Machine | 허용 상태전이 검증 |
| Stage Scheduler | 실행 가능한 Stage 탐색 |
| Step Scheduler | 의존성이 해결된 Step 탐색 |
| Task Dispatcher | Agent Runtime에 Task 전달 |
| Result Receiver | Agent 결과 수신과 계약 검증 |
| Checkpoint Manager | 성공한 단계의 재시작 지점 저장 |
| Retry Controller | 재시도 정책과 Backoff 적용 |
| Timeout Controller | Agent·Step·Stage Timeout 통제 |
| Lock Manager | 프로젝트·모듈·파일 단위 동시 실행 통제 |
| Promotion Manager | Artifact의 단계 간 승격 |
| Recovery Manager | 비정상 종료 Run 복구 |
| Cancellation Manager | 안전한 취소와 실행 중 Tool 종료 |
| Audit Publisher | 상태전이·명령·결과 감사 이벤트 발행 |


### 3.7.3 Workflow 표준


#### Golden Path Workflow


```text
WF-ONLINE-INQUIRY-V1

S00 등록
S10 입력검증
S20 Baseline
S30 요구사항 분석
S40 분석 검토
S50 상세설계
S60 설계 검토
S70 구현계획
S80 구현
S90 Build
S100 Test
S110 품질·보안검증
S120 Drift·추적성검증
S130 최종검토
S140 패키징
S150 완료
```


#### Workflow 정의 예시


```yaml
workflowId: WF-ONLINE-INQUIRY-V1
version: 1.0.0
stages:
  - id: S30
    name: REQUIREMENT_ANALYSIS
    dependsOn: [S20]
    agent: requirement-agent
    timeoutSeconds: 1800
    retryPolicy: LLM_STANDARD
    outputContract: requirement-analysis-output-v1
    gate: HG-20

  - id: S50
    name: DETAIL_DESIGN
    dependsOn: [S40]
    agent: design-agent
    timeoutSeconds: 3600
    outputContract: design-output-v1
    gate: HG-30
```


### 3.7.4 Run 상태모델


```text
CREATED
  ↓
REGISTERED
  ↓
BASELINING
  ↓
BASELINED
  ↓
ANALYZING
  ↓
ANALYSIS_REVIEW
  ↓
DESIGNING
  ↓
DESIGN_REVIEW
  ↓
PLANNING
  ↓
IMPLEMENTING
  ↓
BUILDING
  ↓
TESTING
  ↓
VERIFYING
  ↓
FINAL_REVIEW
  ↓
PACKAGING
  ↓
COMPLETED
  ↓
ARCHIVED
```


#### 보조 상태


| 상태 | 의미 |
| --- | --- |
| WAITING | 선행 작업 또는 외부 자원 대기 |
| WAITING_APPROVAL | 사람 승인 대기 |
| RETRYING | 재시도 대기 또는 수행 |
| BLOCKED | 해결되지 않은 의존성·결함 존재 |
| SUSPENDED | 운영자에 의해 일시 중지 |
| FAILED | 복구 불가능한 실패 |
| REJECTED | Gate 또는 승인자 반려 |
| CANCEL_REQUESTED | 취소 요청 접수 |
| CANCELLED | 안전한 취소 완료 |


### 3.7.5 Stage·Step·Task 상태


```text
PENDING
→ READY
→ DISPATCHED
→ RUNNING
→ OUTPUT_VALIDATING
→ SUCCEEDED
```

실패 흐름:


```text
RUNNING
→ TIMED_OUT
→ RETRY_WAIT
→ READY

또는

RUNNING
→ FAILED
→ MANUAL_REVIEW
```


### 3.7.6 상태 전이 규칙


| 현재 상태 | 이벤트 | 다음 상태 | 조건 |
| --- | --- | --- | --- |
| REGISTERED | BASELINE_START | BASELINING | HG-00 PASS |
| BASELINING | BASELINE_DONE | BASELINED | Baseline Hash 생성 |
| ANALYZING | AGENT_DONE | ANALYSIS_REVIEW | Output Schema 정상 |
| ANALYSIS_REVIEW | GATE_PASS | DESIGNING | HG-20 PASS |
| ANALYSIS_REVIEW | GATE_FAIL | BLOCKED | 필수 결함 존재 |
| DESIGN_REVIEW | APPROVED | PLANNING | 승인자 확인 |
| IMPLEMENTING | GENERATION_DONE | BUILDING | Patch Manifest 생성 |
| BUILDING | BUILD_PASS | TESTING | 종료코드 0 |
| TESTING | TEST_PASS | VERIFYING | 필수 Test 성공 |
| VERIFYING | GATE_PASS | FINAL_REVIEW | HG-70·80 PASS |
| FINAL_REVIEW | APPROVED | PACKAGING | 최종 승인 |
| PACKAGING | PACKAGE_DONE | COMPLETED | Manifest·Hash 생성 |


### 3.7.7 동시성 통제


#### Lock 수준


| Lock | 적용 대상 |
| --- | --- |
| Project Lock | 동일 프로젝트 Baseline 변경 |
| Module Write Lock | 동일 Gradle 모듈 소스 변경 |
| Artifact Lock | 동일 Artifact 재생성·승격 |
| Gate Lock | 동일 Gate 중복 판정 |
| Approval Lock | 중복 승인·반려 |


#### 기본 정책


```text
분석 Run
→ 동일 프로젝트에서 병렬 허용

소스 수정 Run
→ 동일 모듈에 대해 직렬화

읽기 전용 검증 Run
→ 병렬 허용

Baseline 변경 Run
→ 프로젝트 배타 Lock
```


### 3.7.8 Idempotency

각 Task는 다음 키를 사용한다.


```text
idempotencyKey =
  RunId
+ StepId
+ InputArtifactHash
+ AgentContractVersion
+ PromptVersion
+ RuleVersion
```

같은 키의 성공 결과가 존재하면 기본적으로 재사용한다.

단, 다음 경우에는 재실행한다.


- forceRun=true
- 모델 버전 변경
- Prompt 버전 변경
- Gate Rule 변경
- 입력 Artifact Hash 변경
- 기존 결과가 폐기됨
- 보안정책상 Cache 재사용 금지


### 3.7.9 재시도 정책


| 오류 유형 | 재시도 | 기준 |
| --- | --- | --- |
| LLM 일시 Timeout | 2~3회 | 지수 Backoff |
| Tool 일시 장애 | 3회 | 동일 입력 |
| JSON Schema 오류 | 1회 | 수정 Prompt 적용 |
| Build Compile 오류 | 자동 수정 2회 | Patch 단위 |
| 테스트 업무 실패 | 자동 재시도 금지 | 결함 분석 |
| 보안 Gate 실패 | 자동 재시도 금지 | 사람 조치 |
| 입력파일 손상 | 재시도 금지 | 입력 교체 |
| 승인 반려 | 재시도 금지 | 설계·구현 수정 후 신규 Attempt |


### 3.7.10 Checkpoint

Checkpoint는 다음 시점에 생성한다.


- Baseline 확정
- 분석 Artifact 등록 완료
- 분석 Gate 완료
- 설계 Artifact 등록 완료
- 설계 Gate 완료
- 구현 Patch 생성 완료
- Build 성공
- Test 성공
- 최종 Gate 성공
- 패키징 완료

Checkpoint에는 다음 정보가 포함된다.


```yaml
checkpointId: CP-RUN-20260805-0001-S90
runId: RUN-20260805-0001
stageId: S90
stateVersion: 37
baselineHash: sha256:...
artifactManifestHash: sha256:...
completedTaskIds:
  - TASK-S80-001
  - TASK-S90-001
createdAt: 2026-08-05T23:30:00+09:00
```


## 3.8 상태·메타모델 상세설계


### 3.8.1 모델 계층

메타모델은 네 계층으로 분리한다.


```json
[1] 실행 통제 모델
Run·Stage·Step·Task·Attempt·Checkpoint

[2] 업무·설계 모델
Requirement·Screen·Event·ServiceId·Program·SQL·DB Object

[3] 산출물·증적 모델
Artifact·ArtifactVersion·Evidence·Manifest·SourceEvidence

[4] 거버넌스 모델
Gate·GateResult·Approval·Exception·Issue·Drift·Decision
```


### 3.8.2 핵심 엔터티


#### 실행 통제 엔터티


| 엔터티 | 핵심 속성 |
| --- | --- |
| HarnessRun | runId, projectId, workflowId, status, baselineId |
| RunStage | stageId, stageType, status, startedAt, endedAt |
| RunStep | stepId, stepType, dependency, agentId, status |
| AgentTask | taskId, contractId, inputManifest, status |
| TaskAttempt | attemptNo, startedAt, endedAt, exitType, usage |
| Checkpoint | checkpointId, stateVersion, manifestHash |
| LockRecord | resourceType, resourceId, ownerRunId, expiresAt |


#### 설계·추적 엔터티


| 엔터티 | 핵심 속성 |
| --- | --- |
| Requirement | requirementId, type, statement, status, acceptanceCriteria |
| Assumption | assumptionId, statement, risk, approvalStatus |
| BusinessDomain | domainId, businessCode, ownerOrganization |
| Screen | screenId, name, channel |
| ScreenEvent | eventId, screenId, triggerType |
| ServiceDefinition | serviceId, transactionCode, processingType |
| ProgramComponent | componentId, type, className, packageName |
| SqlStatement | sqlId, mapperNamespace, operationType |
| DatabaseObject | objectName, objectType, ownerDomain |
| TestCase | testCaseId, type, requirementId |
| TraceLink | sourceType, sourceId, targetType, targetId, relationType |


#### 산출물·Evidence 엔터티


| 엔터티 | 핵심 속성 |
| --- | --- |
| Artifact | artifactId, artifactType, logicalName, status |
| ArtifactVersion | versionId, uri, contentHash, schemaVersion |
| ArtifactManifest | manifestId, artifact 목록, 전체 Hash |
| SourceEvidence | evidenceId, sourceUri, locator, contentHash |
| ExecutionEvidence | evidenceId, evidenceType, command, exitCode |
| ToolInvocation | toolName, version, argumentsHash, resultHash |


#### 거버넌스 엔터티


| 엔터티 | 핵심 속성 |
| --- | --- |
| Decision | adrId, subject, alternatives, selectedOption |
| GateDefinition | gateId, rulesetId, requiredApprovals |
| GateResult | resultId, decision, score, hardFailureCount |
| RuleResult | ruleId, status, evidenceIds, message |
| Approval | approvalId, approver, decision, comment |
| ExceptionApproval | exceptionId, expiryDate, compensatingControl |
| Issue | issueId, severity, owner, dueDate |
| DriftIssue | driftId, baselineObject, actualObject, difference |


### 3.8.3 주요 관계


```text
HarnessRun
  1 ── N RunStage
  1 ── N AgentTask
  1 ── N Artifact
  1 ── N GateResult

Requirement
  N ── N SourceEvidence
  N ── N ScreenEvent
  N ── N ServiceDefinition
  N ── N TestCase

ServiceDefinition
  N ── 1 ProgramComponent[Handler]
  1 ── N ProgramComponent
  1 ── N SqlStatement
  1 ── N GateRule

Artifact
  1 ── N ArtifactVersion
  N ── N Requirement
  N ── N ExecutionEvidence

GateResult
  1 ── N RuleResult
  1 ── N Approval
  0 ── N ExceptionApproval
```


### 3.8.4 식별자 표준


| 대상 | 형식 | 예시 |
| --- | --- | --- |
| Run | RUN-{일자}-{순번} | RUN-20260805-0001 |
| Stage | STG-{Run순번}-{단계} | STG-0001-DESIGN |
| Task | TSK-{Run순번}-{단계}-{순번} | TSK-0001-DESIGN-003 |
| Requirement | REQ-{업무코드}-{순번} | REQ-AV-0001 |
| Assumption | ASM-{업무코드}-{순번} | ASM-AV-0001 |
| Artifact | ART-{유형}-{순번} | ART-PROG-0001 |
| Evidence | EVD-{유형}-{순번} | EVD-BUILD-0001 |
| Gate 결과 | GTR-{Gate}-{순번} | GTR-HG30-0001 |
| Drift | DRF-{업무코드}-{순번} | DRF-AV-0001 |
| Approval | APR-{Gate}-{순번} | APR-HG30-0001 |


### 3.8.5 Artifact 상태


```text
DRAFT
→ GENERATED
→ VALIDATED
→ REVIEWED
→ APPROVED
→ PROMOTED
→ RELEASED
→ SUPERSEDED
→ RETIRED
```

금지 전이:


```text
DRAFT → RELEASED
GENERATED → PROMOTED
VALIDATED → RELEASED
REJECTED → PROMOTED
```


### 3.8.6 TraceLink 유형


| 관계 | 의미 |
| --- | --- |
| DERIVED_FROM | 산출물이 원본에서 도출됨 |
| SATISFIES | 설계·코드가 요구사항을 충족 |
| IMPLEMENTS | 프로그램이 ServiceId를 구현 |
| INVOKES | 화면 이벤트가 ServiceId를 호출 |
| USES | 프로그램이 SQL·DB 객체를 사용 |
| TESTED_BY | 요구사항·프로그램이 테스트로 검증됨 |
| EVIDENCED_BY | 완료 사실이 Evidence로 입증됨 |
| SUPERSEDES | 새 버전이 이전 버전을 대체 |
| CONFLICTS_WITH | 두 기준 또는 산출물이 충돌 |
| DRIFTS_FROM | 실제 구현이 기준 설계와 다름 |


### 3.8.7 상태 저장소 구성


```text
Relational DB
- Run·Stage·Task 상태
- Requirement·TraceLink
- Gate·Approval
- Artifact 메타정보

Object/File Storage
- 입력 원본
- 문서·소스·로그
- Build·Test 결과
- 최종 패키지

Git Repository
- 소스 변경
- Patch
- 설계·설정의 버전 관리

Search Index
- 문서 본문
- 소스 심볼
- Source Evidence 검색
```

상태 DB와 파일 저장소 간 연결은 URI와 Hash로 관리한다.


```json
{
  "artifactId": "ART-PROG-0001",
  "uri": "workspace://RUN-20260805-0001/40-IMPLEMENTATION/source/...",
  "contentHash": "sha256:8a21...",
  "size": 18340,
  "mediaType": "text/x-java-source"
}
```


## 3.9 Agent별 입출력 계약 상세설계


### 3.9.1 공통 원칙

Agent는 다음 행위를 할 수 없다.


```text
다음 Stage를 직접 시작
Run 상태를 직접 변경
다른 Agent를 직접 호출
승인 상태를 직접 변경
Gate 결과를 직접 PASS 처리
운영 저장소에 직접 Commit
계약에 없는 디렉터리 수정
확인되지 않은 정보를 확정값으로 저장
```


### 3.9.2 공통 Input Envelope


```json
{
  "contractVersion": "1.0.0",
  "task": {
    "runId": "RUN-20260805-0001",
    "stageId": "STG-0001-DESIGN",
    "stepId": "STEP-DESIGN-001",
    "taskId": "TSK-0001-DESIGN-001",
    "attempt": 1,
    "idempotencyKey": "..."
  },
  "baseline": {
    "baselineId": "BASE-0001",
    "gitRepository": "nsight-tcf-framework",
    "branch": "develop",
    "commitSha": "abcdef...",
    "documentBaselineHash": "sha256:..."
  },
  "inputs": [
    {
      "artifactId": "ART-REQ-0001",
      "artifactType": "REQUIREMENT_REGISTER",
      "uri": "workspace://...",
      "contentHash": "sha256:..."
    }
  ],
  "scope": {
    "readPaths": [],
    "writePaths": [],
    "allowedTools": [],
    "networkPolicy": "DENY_BY_DEFAULT"
  },
  "policies": {
    "ruleSetVersion": "NSIGHT-ARCH-1.0",
    "promptVersion": "DESIGN-1.2",
    "maxExecutionSeconds": 3600
  }
}
```


### 3.9.3 공통 Output Envelope


```json
{
  "contractVersion": "1.0.0",
  "taskId": "TSK-0001-DESIGN-001",
  "status": "SUCCEEDED",
  "summary": "거래·프로그램·데이터 설계 초안을 생성했습니다.",
  "outputs": [
    {
      "artifactType": "TRANSACTION_DESIGN",
      "uri": "workspace://...",
      "contentHash": "sha256:...",
      "schemaVersion": "transaction-design-v1"
    }
  ],
  "findings": [
    {
      "findingId": "FND-0001",
      "type": "ASSUMPTION",
      "severity": "MEDIUM",
      "message": "자산평가 테이블 소유 도메인이 확인되지 않았습니다.",
      "requiresHumanDecision": true
    }
  ],
  "traceLinks": [
    {
      "sourceType": "REQUIREMENT",
      "sourceId": "REQ-AV-0001",
      "targetType": "SERVICE_ID",
      "targetId": "AV.AssetValuation.selectList",
      "relationType": "SATISFIES"
    }
  ],
  "evidence": [
    {
      "evidenceType": "SOURCE_REFERENCE",
      "uri": "source://...",
      "contentHash": "sha256:..."
    }
  ],
  "metrics": {
    "durationMs": 125600,
    "inputTokens": 13500,
    "outputTokens": 5200
  }
}
```


### 3.9.4 Agent Registry


| Agent | 주요 입력 | 주요 출력 | 쓰기 허용 | 금지 |
| --- | --- | --- | --- | --- |
| Intake Agent | 원본 파일 | 입력 Manifest·검사 결과 | 00-IN/meta | 원본 수정 |
| Baseline Agent | Git·문서 목록 | Baseline·인벤토리 | 10-BASELINE | 소스 수정 |
| Requirement Agent | 요구 문서·Evidence | 요구사항 원장·가정·Gap | 20-ANALYSIS | 설계·코드 생성 |
| Domain Agent | 요구사항·용어 | 도메인·데이터 소유권 초안 | 20-ANALYSIS/domain | 소유권 자동 확정 |
| Design Agent | 승인 분석 결과 | 화면·거래·프로그램·DB 설계 | 30-DESIGN | 구현 소스 수정 |
| ADR Agent | 대안·제약조건 | ADR 초안 | 30-DESIGN/adr | 최종 승인 |
| Planning Agent | 승인 설계 | 구현계획·파일계획 | 30-DESIGN/plan | 실제 구현 |
| Code Agent | 승인 설계·기준 소스 | 신규 소스·Patch | 40-IMPLEMENTATION | 원본 Branch Push |
| SQL Agent | 데이터 설계 | Mapper·SQL·DDL 초안 | 40-IMPLEMENTATION/sql | 운영 DB 실행 |
| Test Agent | 요구사항·설계·코드 | Test 소스·시나리오 | 50-TEST | 기존 정상 Test 삭제 |
| Build Agent | 구현 작업공간 | Build 결과·로그 | 60-EVIDENCE/build | 코드 임의 수정 |
| Quality Agent | 소스·설계·테스트 | 품질 결과 | 60-EVIDENCE/quality | Gate 판정 |
| Security Agent | 입력·소스·설정 | 보안 검사 결과 | 60-EVIDENCE/security | Secret 원문 출력 |
| Trace Agent | 전체 메타모델 | 추적성·Drift 보고서 | 60-EVIDENCE/trace | 기준정보 수정 |
| Documentation Agent | 승인된 Artifact | 최종 문서 | 90-OUT/documents | 미승인 내용 확정표현 |
| Packaging Agent | 승인 결과 | 최종 Manifest·ZIP | 90-OUT | 미승인 Artifact 포함 |


### 3.9.5 Agent 결과 상태


| 상태 | 의미 |
| --- | --- |
| SUCCEEDED | 계약된 출력이 모두 생성됨 |
| PARTIAL | 일부 출력 생성, 제한사항 존재 |
| FAILED_RETRYABLE | 동일 입력 재시도 가능 |
| FAILED_NON_RETRYABLE | 입력·정책 변경 필요 |
| NEEDS_HUMAN_INPUT | 사람 결정 없이는 진행 불가 |
| BLOCKED_BY_DEPENDENCY | 선행 Artifact 또는 Tool 미준비 |
| CANCELLED | Orchestrator 취소에 따라 종료 |

PARTIAL은 Gate PASS를 의미하지 않는다.


### 3.9.6 Agent Handoff

Agent 간 인계는 파일 경로나 자연어 메시지가 아니라 다음 세 가지로 구성한다.


```text
1. Artifact Manifest
2. TraceLink 집합
3. Findings·Assumptions 집합
```

다음 Agent는 승인된 Manifest만 입력으로 받을 수 있다.


```text
Requirement Agent 결과
  ↓ HG-20
승인된 Analysis Manifest
  ↓
Design Agent

Design Agent 결과
  ↓ HG-30
승인된 Design Manifest
  ↓
Planning·Code Agent
```


### 3.9.7 계약 버전관리


- Major: 호환되지 않는 필드 변경
- Minor: 선택 필드 추가
- Patch: 설명·Validation 보완
- Agent는 자신이 지원하는 계약 버전을 Registry에 등록한다.
- Orchestrator는 호환 가능한 Agent만 Task에 할당한다.
- 미지원 계약이면 Task를 BLOCKED 처리한다.


## 3.10 Gate Engine 상세설계


### 3.10.1 책임

Gate Engine은 Agent 결과의 품질을 직접 생성하지 않는다.

다음 항목을 판정한다.


```text
필수 Artifact가 존재하는가
→ 형식이 유효한가
→ 필수 Rule을 통과했는가
→ 필요한 Evidence가 있는가
→ 미해결 결함이 존재하는가
→ 사람 승인이 완료되었는가
→ 예외가 유효한가
→ 다음 단계로 승격 가능한가
```

Architecture Gate는 문서 존재 여부가 아니라 설계·소스·OM 기준정보·환경설정·시험·운영 증적의 정합성을 확인해야 한다.


### 3.10.2 Gate 목록


| Gate | 명칭 | 핵심 판정 |
| --- | --- | --- |
| HG-00 | Input Gate | 입력 안전성·완전성 |
| HG-10 | Baseline Gate | Branch·Commit·문서 기준선 |
| HG-20 | Analysis Gate | 요구사항·가정·충돌·수용기준 |
| HG-30 | Design Gate | 구조·책임·데이터·보안·ADR |
| HG-40 | Implementation Gate | 승인 설계와 생성물 일치 |
| HG-50 | Build Gate | Clean Build와 산출물 |
| HG-60 | Test Gate | 필수 Test 성공 |
| HG-70 | Security/Quality Gate | 보안·계층·명명·표준 |
| HG-80 | Trace/Drift Gate | 추적성·OM·설계–코드 정합성 |
| HG-90 | Final Gate | 잔여 위험·예외·승인·패키징 |


### 3.10.3 Rule 유형


| Rule 유형 | 설명 | 예시 |
| --- | --- | --- |
| HARD | 실패 시 즉시 FAIL | Secret 발견, Build 실패 |
| REQUIRED | 반드시 평가되어야 함 | ServiceId–Handler 정합성 |
| SCORED | 품질점수에 반영 | 문서 완전성 |
| ADVISORY | 경고만 제공 | 설명 분량 부족 |
| HUMAN | 사람 승인 필요 | 데이터 소유권 확정 |
| EXCEPTIONABLE | 예외 승인 가능 | 일시적 Coverage 미달 |
| NON_EXCEPTIONABLE | 예외 불가 | 악성파일, Private Key 포함 |


### 3.10.4 Gate 결과


| 결과 | 의미 |
| --- | --- |
| PASS | 모든 필수 조건 충족 |
| PASS_WITH_EXCEPTION | 승인된 유효 예외가 존재 |
| PENDING_APPROVAL | 자동 Rule 통과, 사람 승인 대기 |
| FAIL | Hard·Required Rule 실패 |
| BLOCKED | 평가에 필요한 입력·Evidence 누락 |
| NOT_APPLICABLE | 해당 Workflow에는 적용되지 않음 |


### 3.10.5 판정 순서


```text
1. Gate Definition 로딩
2. 대상 Manifest 고정
3. 필수 Artifact 검사
4. 필수 Evidence 검사
5. Hard Rule 실행
6. Required Rule 실행
7. Scored Rule 실행
8. 미해결 Issue 검사
9. Exception 유효성 검사
10. 사람 승인 검사
11. 최종 판정
12. GateResult·RuleResult 저장
13. Promotion 허용 또는 차단
```


### 3.10.6 점수 계산

점수는 Soft Rule에만 적용한다.


```text
Quality Score =
Σ(규칙 점수 × 규칙 가중치)
──────────────────────
Σ(적용 규칙 최대점수 × 가중치)
× 100
```

예:


| 영역 | 가중치 |
| --- | --- |
| 요구사항 완전성 | 20 |
| 추적성 | 20 |
| 아키텍처 준수 | 20 |
| 테스트 | 15 |
| 보안 | 15 |
| 문서·운영성 | 10 |

단, 다음 조건에서는 점수와 관계없이 FAIL이다.


```text
Build 실패
필수 Test 실패
ServiceId 중복
Handler 미등록
Secret 검출
승인 없는 데이터 소유권
Hard Rule 실패
```


### 3.10.7 Rule 정의 예시


```yaml
ruleId: ARCH-SERVICE-NO-DIRECT-MAPPER
name: Service 계층 Mapper 직접호출 금지
gateId: HG-70
type: HARD
scope:
  language: JAVA
  packagePattern: "..service.."
condition:
  forbiddenDependency: "..mapper.."
evidence:
  required:
    - ARCHUNIT_REPORT
failure:
  severity: CRITICAL
  message: "Service는 Mapper를 직접 호출할 수 없습니다."
exceptionAllowed: false
```


```yaml
ruleId: TRACE-REQ-TEST-COVERAGE
name: 요구사항 테스트 추적률
gateId: HG-80
type: SCORED
condition:
  metric: requirementTestTraceRate
  passThreshold: 100
  warningThreshold: 95
exceptionAllowed: true
requiredApproverRole: QA_LEAD
```


### 3.10.8 승인모델


| 승인 대상 | 필수 승인자 |
| --- | --- |
| 업무규칙 | BA 또는 업무책임자 |
| 도메인 경계 | AA·업무책임자 |
| 데이터 소유권 | DA |
| 트랜잭션 경계 | AA |
| 개인정보·권한 | SEC |
| 테스트 예외 | QA |
| 운영 반영 | OPS·변경관리자 |
| 아키텍처 예외 | ARB |


#### 승인 상태


```text
REQUESTED
→ IN_REVIEW
→ APPROVED

또는

REQUESTED
→ REJECTED

또는

REQUESTED
→ CHANGES_REQUESTED
```


### 3.10.9 예외 승인

예외는 다음 속성을 반드시 가진다.


- 대상 Rule
- 예외 사유
- 업무 필요성
- 위험
- 보완 통제
- 책임자
- 승인자
- 적용 범위
- 시작일
- 만료일
- 제거 계획

만료된 예외는 자동으로 무효화한다.


### 3.10.10 Promotion 결정


```text
Gate PASS
→ Artifact Manifest를 다음 Stage의 Approved Input으로 등록
→ 작업공간 Promotion 수행

Gate PASS_WITH_EXCEPTION
→ 예외 Manifest 포함
→ 만료일 모니터링

Gate FAIL
→ Promotion 금지
→ Issue 생성
→ Run BLOCKED 또는 이전 Stage 복귀

Gate PENDING_APPROVAL
→ 작업공간 유지
→ 후속 Agent 실행 금지
```


## 3.11 작업공간 디렉터리 상세설계


### 3.11.1 최상위 구조


```text
harness-root/
├─ shared/
│  ├─ standards/
│  ├─ templates/
│  ├─ rules/
│  ├─ schemas/
│  ├─ prompts/
│  ├─ agent-registry/
│  └─ tool-registry/
│
├─ projects/
│  └─ {projectId}/
│     ├─ project.yaml
│     ├─ baselines/
│     ├─ references/
│     └─ runs/
│        └─ {runId}/
│
└─ archives/
   └─ {projectId}/
```


### 3.11.2 Run 디렉터리


```json
{runId}/
├─ 00-IN/
│  ├─ requirements/
│  ├─ source/
│  ├─ database/
│  ├─ reference/
│  ├─ constraints/
│  ├─ quarantine/
│  └─ input-manifest.json
│
├─ 10-BASELINE/
│  ├─ baseline.yaml
│  ├─ source-inventory.json
│  ├─ document-inventory.json
│  ├─ terminology.yaml
│  ├─ technology-baseline.yaml
│  └─ baseline-manifest.json
│
├─ 20-ANALYSIS/
│  ├─ requirements/
│  │  ├─ requirement-register.yaml
│  │  ├─ acceptance-criteria.yaml
│  │  └─ conflicts.yaml
│  ├─ domain/
│  │  ├─ domain-model.yaml
│  │  └─ data-ownership-draft.yaml
│  ├─ assumptions/
│  │  └─ assumption-register.yaml
│  ├─ gaps/
│  │  └─ gap-register.yaml
│  ├─ evidence/
│  │  └─ source-evidence.yaml
│  ├─ trace/
│  │  └─ traceability-draft.yaml
│  └─ analysis-manifest.json
│
├─ 30-DESIGN/
│  ├─ architecture/
│  ├─ screen/
│  ├─ transaction/
│  ├─ program/
│  ├─ data/
│  ├─ interface/
│  ├─ security/
│  ├─ operation/
│  ├─ adr/
│  ├─ plan/
│  └─ design-manifest.json
│
├─ 40-IMPLEMENTATION/
│  ├─ worktree/
│  ├─ generated/
│  │  ├─ source/
│  │  ├─ resources/
│  │  ├─ mapper/
│  │  ├─ sql/
│  │  ├─ config/
│  │  └─ om/
│  ├─ patches/
│  ├─ diff/
│  ├─ rejected/
│  └─ implementation-manifest.json
│
├─ 50-TEST/
│  ├─ unit/
│  ├─ integration/
│  ├─ contract/
│  ├─ architecture/
│  ├─ security/
│  ├─ performance/
│  ├─ fixtures/
│  └─ test-manifest.json
│
├─ 60-EVIDENCE/
│  ├─ build/
│  ├─ test/
│  ├─ quality/
│  ├─ security/
│  ├─ trace/
│  ├─ drift/
│  ├─ runtime/
│  ├─ tool-invocations/
│  └─ evidence-manifest.json
│
├─ 70-REVIEW/
│  ├─ gates/
│  ├─ approvals/
│  ├─ exceptions/
│  ├─ issues/
│  ├─ comments/
│  └─ review-manifest.json
│
├─ 80-STAGING/
│  ├─ documents/
│  ├─ source/
│  ├─ sql/
│  ├─ configuration/
│  ├─ evidence/
│  └─ staging-manifest.json
│
├─ 90-OUT/
│  ├─ documents/
│  ├─ source-package/
│  ├─ database-package/
│  ├─ om-package/
│  ├─ test-package/
│  ├─ evidence-package/
│  ├─ final-report/
│  └─ release-manifest.json
│
├─ 95-CHECKPOINT/
│  ├─ state/
│  ├─ manifests/
│  └─ recovery/
│
├─ 99-ARCHIVE/
│  ├─ audit/
│  ├─ logs/
│  └─ archive-manifest.json
│
└─ run.yaml
```


### 3.11.3 디렉터리별 속성


| 디렉터리 | 변경 가능 여부 | 생성 주체 | 다음 단계 입력 가능 |
| --- | --- | --- | --- |
| 00-IN | 원본 변경 금지 | 사용자·Intake | Baseline 후 가능 |
| 10-BASELINE | 확정 후 불변 | Baseline Agent | 가능 |
| 20-ANALYSIS | Analysis Gate 전까지 | 분석 Agent | HG-20 후 가능 |
| 30-DESIGN | Design Gate 전까지 | 설계 Agent | HG-30 후 가능 |
| 40-IMPLEMENTATION | 구현 중 가능 | Code·SQL Agent | Build 후 가능 |
| 50-TEST | Test 설계 중 가능 | Test Agent | HG-60 후 가능 |
| 60-EVIDENCE | Append Only | Tool·Quality Agent | Gate 입력 |
| 70-REVIEW | 승인자·Gate만 | Gate Engine·사용자 | Gate 결정 |
| 80-STAGING | Promotion Manager만 | Orchestrator | 최종 검토 입력 |
| 90-OUT | 불변 | Packaging Agent | 사용자 제공 |
| 95-CHECKPOINT | Orchestrator만 | Orchestrator | 복구용 |
| 99-ARCHIVE | 불변 | Archive Manager | 감사·보관 |


### 3.11.4 Agent별 디렉터리 권한


| Agent | Read | Write |
| --- | --- | --- |
| Intake | 외부 입력 | 00-IN |
| Baseline | 00-IN, 공유 기준 | 10-BASELINE |
| Requirement | 00-IN, 10-BASELINE | 20-ANALYSIS |
| Design | 승인된 20-ANALYSIS | 30-DESIGN |
| Code | 승인된 30-DESIGN, Worktree | 40-IMPLEMENTATION |
| Test | 승인 설계·구현 | 50-TEST |
| Build | 40, 50 | 60-EVIDENCE/build |
| Quality | 30, 40, 50 | 60-EVIDENCE/quality |
| Security | 관련 전체 | 60-EVIDENCE/security |
| Trace | 전체 Manifest | 60-EVIDENCE/trace·drift |
| Gate | Manifest·Evidence | 70-REVIEW/gates |
| Packaging | 승인 Manifest | 80-STAGING, 90-OUT |


### 3.11.5 파일 작성 원칙


#### Atomic Write


```text
파일 생성
→ .tmp 확장자로 작성
→ Schema·Hash 검증
→ fsync
→ 최종 파일명으로 Atomic Rename
→ Artifact Registry 등록
```


#### Content Hash

모든 파일은 SHA-256 Hash를 가진다.


```text
파일 내용 변경
→ 새 ArtifactVersion 생성
→ 기존 파일 덮어쓰기 금지
```


#### Manifest 중심 관리

디렉터리 존재 자체는 완료를 의미하지 않는다.


```json
{
  "manifestId": "MAN-DESIGN-0001",
  "runId": "RUN-20260805-0001",
  "stage": "DESIGN",
  "status": "APPROVED",
  "artifacts": [
    {
      "artifactId": "ART-TX-0001",
      "versionId": "VER-0003",
      "path": "30-DESIGN/transaction/AV.AssetValuation.selectList.md",
      "hash": "sha256:..."
    }
  ],
  "gateResultId": "GTR-HG30-0001"
}
```


### 3.11.6 Promotion 방식

실제 파일의 반복적인 이동보다 Manifest 기반 승격을 우선한다.


```text
30-DESIGN의 파일
→ HG-30 PASS
→ Artifact 상태 APPROVED
→ Design Approved Manifest 생성
→ 40-IMPLEMENTATION의 입력으로 참조
```

최종 배포 패키지를 구성할 때만 80-STAGING으로 복사한다.


#### Promotion 조건


- Gate PASS 또는 유효한 PASS_WITH_EXCEPTION
- Artifact Hash 일치
- 필수 Approval 완료
- 미해결 Critical Issue 없음
- 대상 디렉터리 Write Lock 확보
- Manifest Schema 정상


### 3.11.7 Worktree 운영

기존 소스 변경은 다음 방식으로 수행한다.


```text
기준 Commit
→ Run 전용 Git Worktree 생성
→ Code Agent가 Worktree 수정
→ Diff·Patch 생성
→ Build·Test
→ 사용자 검토
→ 승인 후 Commit 후보 생성
```

금지:


```text
기준 Branch 직접 수정
사용자의 미커밋 변경 덮어쓰기
Force Push
생성 결과 자동 Merge
검증되지 않은 소스 OUT 포함
```


### 3.11.8 보관기간


| 데이터 | 권장 보관 |
| --- | --- |
| 입력 원본 | 프로젝트 종료 후 정책기간 |
| Baseline | 영구 또는 프로젝트 수명 전체 |
| 생성 중 임시파일 | Run 종료 후 30일 |
| Build·Test Evidence | 품질·감사 정책기간 |
| Approval·Exception | 감사 정책기간 |
| 최종 OUT | 형상관리 정책기간 |
| Agent 세부 Debug 로그 | 보안 검토 후 단기 보관 |
| Prompt·응답 원문 | 개인정보·보안정책에 따라 제한 |


## 3.12 책임 경계와 RACI


| 활동 | Orchestrator | Agent | Gate Engine | 승인자 | 저장소 |
| --- | --- | --- | --- | --- | --- |
| Run 생성 | R | I | I | I | C |
| Workflow 선택 | R | I | I | C | I |
| Task 실행 | A | R | I | I | C |
| Artifact 생성 | C | R | I | I | A |
| 상태 전이 | R | 금지 | C | I | A |
| 품질검사 실행 | C | R | I | I | A |
| Gate 판정 | C | Evidence 제공 | R | C/A | A |
| 사람 승인 | I | I | 요청 | R/A | A |
| Promotion | R | 금지 | 허용결정 | I | A |
| 최종 패키징 | A | R | 통과확인 | C | A |
| 운영 반영 | 금지 | 금지 | 금지 | OPS R/A | 외부 절차 |


## 3.13 정상 처리 흐름


### 3.13.1 조회 거래 1건 Golden Path


```json
[1] Run 등록
    RunId 생성
    입력 Manifest 생성
        ↓
[2] HG-00 입력 Gate
    파일 안전성·필수정보 확인
        ↓
[3] Baseline Agent
    Git Commit·문서·기술버전 확정
        ↓
[4] HG-10 Baseline Gate
        ↓
[5] Requirement Agent
    REQ-AV-0001 생성
    가정·Gap·수용기준 생성
        ↓
[6] HG-20 Analysis Gate
    BA 승인
        ↓
[7] Design Agent
    화면·ServiceId·거래·프로그램·DB 설계
        ↓
[8] HG-30 Design Gate
    AA·DA·SEC 승인
        ↓
[9] Planning Agent
    생성 파일·수정 파일·테스트 계획
        ↓
[10] Code·SQL·Test Agent
     Worktree에 생성
        ↓
[11] Build Agent
     Clean Build
        ↓
[12] Test Agent
     Unit·Integration·Architecture Test
        ↓
[13] Quality·Security·Trace Agent
     계층·ServiceId·Mapper·OM·Drift 검사
        ↓
[14] HG-50·60·70·80
        ↓
[15] Final Review
     Diff·잔여 위험·예외 확인
        ↓
[16] HG-90
        ↓
[17] Staging·Packaging
        ↓
[18] COMPLETED·ARCHIVED
```


## 3.14 오류·Timeout·장애 흐름


### 3.14.1 Agent Timeout


```text
Task RUNNING
→ Timeout 감지
→ Tool 종료 요청
→ Agent 결과 수신 대기
→ Task TIMED_OUT
→ Attempt Evidence 저장
→ 재시도 정책 확인
→ RETRY_WAIT 또는 MANUAL_REVIEW
```


### 3.14.2 Orchestrator 장애


```text
Orchestrator 비정상 종료
→ 상태 DB의 RUNNING Task 탐색
→ Lease 만료 확인
→ 마지막 Checkpoint 복원
→ 외부 Tool 실제 상태 확인
→ 중복 실행 여부 확인
→ Task 재연결 또는 재시도
```


### 3.14.3 상태 DB 장애


- 상태 전이와 Outbox Event를 하나의 트랜잭션으로 처리한다.
- DB 저장 실패 시 Artifact Promotion을 수행하지 않는다.
- 파일은 생성되었으나 DB 등록이 실패하면 ORPHAN 후보로 표시한다.
- 복구 작업이 Hash와 Manifest를 검사해 재등록 또는 격리한다.


### 3.14.4 Gate Engine 장애


```text
Gate 평가 중 장애
→ 기존 Artifact 상태 유지
→ Promotion 금지
→ GateResult ERROR
→ 재평가 가능 상태로 저장
```


### 3.14.5 작업공간 용량 부족


- 실행 전 예상용량을 확인한다.
- 임계치 초과 시 신규 Code·Build Task를 시작하지 않는다.
- 임시 Build Cache부터 정리한다.
- 입력·Baseline·Evidence는 자동 삭제하지 않는다.
- Run을 BLOCKED_RESOURCE로 전환한다.


### 3.14.6 부분 성공


```text
문서 생성 성공
코드 생성 성공
Build 성공
Integration Test 실패
```

최종 상태는 COMPLETED가 아니다.


```text
Run = BLOCKED
Build Artifact = VALIDATED
Test Artifact = REJECTED
Final Promotion = 금지
```


## 3.15 정상 예시


### 3.15.1 Agent 결과


```json
{
  "taskId": "TSK-0001-CODE-001",
  "status": "SUCCEEDED",
  "outputs": [
    {
      "artifactType": "JAVA_SOURCE",
      "logicalName": "AvAssetValuationHandler",
      "uri": "workspace://.../40-IMPLEMENTATION/generated/source/...",
      "contentHash": "sha256:..."
    }
  ],
  "findings": [],
  "traceLinks": [
    {
      "sourceType": "SERVICE_ID",
      "sourceId": "AV.AssetValuation.selectList",
      "targetType": "PROGRAM",
      "targetId": "AvAssetValuationHandler",
      "relationType": "IMPLEMENTS"
    }
  ]
}
```


### 3.15.2 Gate 결과


```json
{
  "gateId": "HG-70",
  "decision": "PASS",
  "hardFailureCount": 0,
  "qualityScore": 98,
  "ruleResults": {
    "serviceIdHandlerConsistency": "PASS",
    "layerDependency": "PASS",
    "secretScan": "PASS",
    "mapperConsistency": "PASS"
  }
}
```


## 3.16 금지 예시


### 3.16.1 Agent가 상태 변경


```text
Design Agent가 설계가 충분하다고 판단
→ Run 상태를 IMPLEMENTING으로 직접 변경

금지 사유:
Agent가 자신의 결과를 스스로 승인하게 된다.
```


### 3.16.2 Evidence 없는 Gate


```text
Build Agent 설명:
“코드상 문제가 없어 보입니다.”

Build Gate:
PASS

금지 사유:
실제 Gradle 종료코드와 Build 로그가 없다.
```


### 3.16.3 파일 직접 승격


```text
Code Agent가 생성한 파일을
90-OUT/source-package로 직접 복사

금지 사유:
Build·Test·Security·Trace Gate를 우회한다.
```


### 3.16.4 자유 형식 Agent 인계


```text
“설계는 다 끝났고 코드 만들어 주세요.”

금지 사유:
Artifact·Version·Hash·미확정사항·TraceLink가 없다.
```


### 3.16.5 승인 결과 덮어쓰기


```text
승인된 거래설계서 파일을
Code Agent가 구현 편의를 위해 수정

금지 사유:
As-Designed 기준선이 손실된다.
```


## 3.17 연계 규칙


| 연계 시스템 | 연계 기준 |
| --- | --- |
| Git | 읽기 Baseline과 쓰기 Worktree 분리 |
| Gradle | 명시된 Wrapper 사용, 종료코드 저장 |
| DB Metadata | 조회 전용 계정 사용 |
| LLM | Agent별 Model·Prompt·Token 한도 |
| CI/CD | GateResult를 Pipeline 상태로 전달 |
| OM | SQL·CSV·API Payload 초안만 생성 |
| Issue Tracker | Gate FAIL·Drift·예외를 Issue로 연계 |
| 문서 저장소 | Source Evidence URI와 Hash 관리 |
| Secret Store | Agent에 Secret 원문 비노출 원칙 |
| 알림 | 승인 대기·실패·예외 만료 알림 |


## 3.18 데이터 및 상태관리


### 3.18.1 일관성 원칙


- 상태 DB가 실행 상태의 기준원이다.
- Artifact 파일은 Hash로 상태 DB와 연결한다.
- Git Commit은 소스 버전의 기준원이다.
- GateResult는 승격 가능 여부의 기준원이다.
- Approval은 사람 결정의 기준원이다.
- Evidence는 완료 사실의 기준원이다.


### 3.18.2 낙관적 잠금

HarnessRun, RunStage, AgentTask에는 version 컬럼을 둔다.


```sql
UPDATE HarnessRun
SET status = ?, version = version + 1
WHERE run_id = ?
  AND version = ?
```

갱신 건수가 0이면 동시 상태 변경으로 판단하고 재조회한다.


### 3.18.3 Event Outbox

상태 변경과 이벤트 발행의 불일치를 방지한다.


```text
상태 DB Transaction
  ├─ Run 상태 변경
  └─ Outbox Event 저장
        ↓
Outbox Publisher
        ↓
Task Queue·Audit·Notification
```


### 3.18.4 삭제 정책

다음 데이터는 물리 삭제하지 않는다.


- 승인 기록
- Gate 결과
- Architecture Exception
- 최종 Evidence
- Baseline
- Release Manifest
- 감사로그

잘못 생성된 Artifact는 RETIRED 또는 INVALIDATED 상태로 관리한다.


## 3.19 성능·용량·확장성


### 3.19.1 처리 분리


```text
Control Plane
- Orchestrator
- Gate
- 상태 DB
- 승인 UI

Execution Plane
- LLM Agent
- Source 분석
- Build·Test
- Security Scan
```

Execution Plane은 Queue 기반으로 수평 확장한다.


### 3.19.2 Queue 분리


| Queue | 목적 |
| --- | --- |
| analysis.queue | 문서·소스 분석 |
| design.queue | 설계 생성 |
| generation.queue | 코드·SQL 생성 |
| build.queue | Gradle Build |
| test.queue | Test |
| quality.queue | 정적검증·보안 |
| packaging.queue | 최종 패키징 |


### 3.19.3 자원한도


| 자원 | 통제 기준 |
| --- | --- |
| LLM Token | Task·Run별 한도 |
| Agent 실행시간 | Contract별 Timeout |
| CPU·Memory | Worker Container별 Limit |
| Build 병렬수 | 프로젝트·모듈별 제한 |
| 파일 크기 | 입력 유형별 제한 |
| Workspace 용량 | Run별 Quota |
| 동시 Run | 프로젝트별 한도 |
| 재시도 | 오류유형별 최대 횟수 |


### 3.19.4 Cache

Hash가 동일한 다음 결과는 재사용할 수 있다.


- 문서 파싱 결과
- 소스 인벤토리
- Dependency 분석
- 정적 Source Evidence
- 변경되지 않은 모듈 Build 결과

다음은 기본적으로 재사용하지 않는다.


- 보안 Scan 결과
- 최종 Gate 판정
- 사람 승인
- 시간 의존 환경 Test
- 변경된 외부 연계 Contract Test


## 3.20 보안·개인정보·감사


### 3.20.1 Agent Sandbox

각 Agent는 격리된 실행공간에서 동작한다.


```text
파일 접근
→ Contract의 Read/Write Path만 허용

명령 실행
→ Tool Allowlist만 허용

Network
→ 기본 차단, 목적지 Allowlist

Secret
→ 단기 Token 또는 Proxy 방식

운영환경
→ 직접 접근 금지
```


### 3.20.2 Prompt 보안


- 입력 문서의 Prompt Injection 탐지
- 문서 안의 명령을 System Instruction으로 취급하지 않음
- Agent 권한을 Prompt가 변경할 수 없도록 구현
- Secret 요청 Prompt 차단
- 외부 URL 자동 호출 차단
- 출력에 개인정보·Token 포함 여부 검사


### 3.20.3 감사 이벤트


| 이벤트 | 기록 내용 |
| --- | --- |
| RUN_CREATED | 생성자·Workflow·Baseline |
| TASK_DISPATCHED | Agent·Contract·입력 Hash |
| TOOL_INVOKED | Tool·인자 Hash·실행자 |
| ARTIFACT_CREATED | URI·Hash·Agent |
| STATE_CHANGED | 이전·이후 상태·원인 |
| GATE_EVALUATED | Rule·Evidence·결과 |
| APPROVAL_RECORDED | 승인자·결정·시각 |
| FILE_PROMOTED | 출발·도착 Manifest |
| EXCEPTION_GRANTED | 사유·만료일 |
| RUN_ARCHIVED | Archive Hash |


## 3.21 운영·모니터링·장애 대응


### 3.21.1 운영화면


```text
하네스 운영
├─ Run 현황
├─ Stage·Task 현황
├─ Agent Worker 현황
├─ 승인 대기
├─ Gate 실패
├─ 재시도·Timeout
├─ Workspace 용량
├─ Drift·Exception
├─ 비용·Token 사용량
└─ Audit 조회
```


### 3.21.2 주요 Metric


- harness_run_total
- harness_run_completed_total
- harness_run_failed_total
- harness_stage_duration_seconds
- harness_task_retry_total
- harness_agent_timeout_total
- harness_gate_pass_total
- harness_gate_fail_total
- harness_approval_wait_seconds
- harness_workspace_bytes
- harness_artifact_created_total
- harness_drift_issue_total
- harness_llm_tokens_total
- harness_build_duration_seconds


### 3.21.3 장애 우선순위


| 등급 | 장애 |
| --- | --- |
| P1 | 상태 DB 손상, 승인정보 유실, Evidence 변조 |
| P2 | Orchestrator 전체 중단, 전체 Queue 정지 |
| P3 | 특정 Agent·Build Worker 장애 |
| P4 | 개별 Run·Task 실패 |


### 3.21.4 복구 원칙


```text
상태 DB
→ Backup·Point-in-Time Recovery

Artifact Storage
→ Versioning·Object Lock

Git Worktree
→ 기준 Commit으로 재생성

Task
→ Idempotency Key 기반 재시도

Run
→ Checkpoint 기반 재개
```


## 3.22 자동검증 및 품질 Gate


### 3.22.1 Orchestrator 검증


- 허용되지 않은 상태 전이 차단
- 동일 Task 중복 실행 검사
- 만료 Lock 정리
- Checkpoint와 Manifest Hash 검사
- 완료 Stage의 필수 Artifact 검사
- 승인 전 후속 Stage 생성 차단


### 3.22.2 계약 검증


- Input·Output JSON Schema
- 필수 필드
- Artifact URI 유효성
- Hash 일치
- Agent 권한 범위
- 계약 버전 호환성
- 허용되지 않은 상태값
- TraceLink 대상 존재 여부


### 3.22.3 Workspace 검증


- 원본 수정 여부
- Manifest 미등록 파일
- Hash 불일치
- 승인 없는 OUT 파일
- Evidence 디렉터리 수정
- Worktree 기준 Commit 불일치
- 임시파일 잔존
- 미등록 Secret 파일


### 3.22.4 NSIGHT 개발검증

기존 요구사항 정의서에서 지정한 다음 항목을 Gate Rule로 구현한다.


- ServiceId 형식·중복
- ServiceId–Handler 정합성
- 패키지·계층 의존성
- Facade 트랜잭션 경계
- Service의 Mapper 직접 접근
- Rule의 DB·외부 호출
- DAO–Mapper 정합성
- Mapper XML Statement 중복
- SQL ID–설계서 정합성
- 표준 Header–DTO 정합성
- 오류코드 등록
- Timeout 정책
- OM Catalog
- Requirement–Test 연결
- Secret·개인정보·금지 API
- 미사용·미연결 Artifact
- As-Designed–As-Built Drift


## 3.23 테스트 시나리오


| ID | 테스트 | 기대 결과 |
| --- | --- | --- |
| ORC-001 | 정상 Golden Path | 모든 Stage 순차 완료 |
| ORC-002 | 설계 승인 전 구현 실행 | Task 생성 차단 |
| ORC-003 | Orchestrator 재기동 | Checkpoint부터 재개 |
| ORC-004 | 동일 Task 중복 메시지 | 한 번만 반영 |
| ORC-005 | 모듈 Write Lock 충돌 | 후속 Run 대기 |
| ORC-006 | Agent Timeout | 정책에 따라 재시도 |
| ORC-007 | Output Schema 오류 | 결과 반려·제한 재시도 |
| ORC-008 | Build 실패 | Test Stage 진입 차단 |
| ORC-009 | Hard Rule 실패 | Gate FAIL |
| ORC-010 | Soft Score 미달 | 정책에 따른 FAIL·예외대기 |
| ORC-011 | 사람 승인 대기 | Run WAITING_APPROVAL |
| ORC-012 | 예외 만료 | 후속 Gate에서 FAIL |
| ORC-013 | 원본 파일 변경 | 무결성 Gate 실패 |
| ORC-014 | Artifact Hash 불일치 | Promotion 차단 |
| ORC-015 | 승인 없는 OUT 복사 | 보안·Workspace 검사 실패 |
| ORC-016 | Agent가 권한 외 파일 쓰기 | Sandbox 차단 |
| ORC-017 | Prompt Injection 문서 | 명령 무시·보안 Finding |
| ORC-018 | 상태 DB 일시 장애 | 파일 승격 없이 재시도 |
| ORC-019 | 작업공간 용량 부족 | Run BLOCKED_RESOURCE |
| ORC-020 | 동일 Baseline 재실행 | 의미적으로 동일 결과 |
| ORC-021 | 설계 후 수동 코드 변경 | Drift 탐지 |
| ORC-022 | 부분 Test 성공 | 최종 Gate 차단 |
| ORC-023 | Archive 후 조회 | 전체 Evidence 재현 가능 |
| ORC-024 | 계약 Major 버전 불일치 | Agent 할당 차단 |


## 3.24 체크리스트


### Orchestrator


- Run·Stage·Step·Task 상태가 분리되어 있는가
- 상태 전이는 Orchestrator만 수행하는가
- Idempotency Key가 정의되었는가
- 재시도·Timeout·취소 정책이 있는가
- Checkpoint와 복구절차가 있는가
- 프로젝트·모듈 Lock이 있는가
- 승인 전 후속 실행이 차단되는가


### 상태·메타모델


- 요구사항부터 Evidence까지 엔터티가 연결되는가
- Artifact 버전과 Hash를 관리하는가
- Source Evidence와 가정을 구분하는가
- TraceLink 관계유형이 표준화되어 있는가
- 승인·예외·Drift 이력을 보존하는가
- 삭제 대신 상태 기반 폐기를 적용하는가


### Agent 계약


- 공통 Input·Output Envelope가 있는가
- JSON Schema 검증을 수행하는가
- Agent별 Read·Write 경로가 제한되는가
- Tool·Network 권한이 제한되는가
- Agent가 Gate·Run 상태를 변경할 수 없는가
- Agent 계약 버전 호환성을 검사하는가
- 부분 성공과 사람 입력 필요 상태가 구분되는가


### Gate Engine


- Hard·Soft·Human Rule이 구분되는가
- Evidence 없는 PASS를 차단하는가
- 예외 가능·불가능 Rule이 구분되는가
- 승인자 역할이 정의되어 있는가
- 예외 만료일을 관리하는가
- Gate 결과가 Promotion과 연결되는가
- Rule 버전을 Baseline에 기록하는가


### 작업공간


- 00-IN 원본이 불변인가
- Run별 작업공간이 격리되는가
- Git Worktree를 사용하는가
- 임시·초안·승인·최종 공간이 구분되는가
- Evidence가 Append Only인가
- Manifest와 실제 파일 Hash가 일치하는가
- 승인되지 않은 파일이 90-OUT에 들어갈 수 없는가
- Archive에서 Run을 재현할 수 있는가


## 3.25 변경·호환성·폐기 관리


### 3.25.1 Workflow 변경


- 실행 중 Run은 시작 당시 Workflow 버전을 유지한다.
- 신규 Workflow는 새 버전으로 등록한다.
- 실행 중 Run에 Workflow를 소급 적용하지 않는다.
- 긴급 Rule 변경 시 영향 Run 목록을 산출한다.


### 3.25.2 메타모델 변경


| 변경 유형 | 처리 |
| --- | --- |
| 선택 필드 추가 | Minor 버전 |
| 필수 필드 추가 | Major 버전 |
| 상태값 추가 | 상태머신 호환성 검토 |
| 관계유형 추가 | Trace Validator 변경 |
| 필드 삭제 | 유예기간과 Migration 제공 |


### 3.25.3 Agent 교체

Agent는 계약만 유지하면 내부 구현과 모델을 교체할 수 있다.


```text
Agent 구현 교체
→ Contract Test
→ Golden Dataset Test
→ 재현성 비교
→ 보안검사
→ Registry 버전 등록
→ 제한된 Pilot Run
→ 정식 승격
```


### 3.25.4 Gate Rule 변경


- Rule 변경 사유를 ADR로 기록한다.
- Hard Rule 완화는 ARB 승인을 받는다.
- 기존 PASS Run에 대한 재평가 여부를 결정한다.
- Rule 변경 전후 결과 차이를 회귀시험한다.
- 예외 승인 정책도 Rule 버전과 함께 관리한다.


### 3.25.5 폐기

다음 순서로 폐기한다.


```text
DEPRECATED
→ 신규 Workflow 사용 차단
→ 기존 Run 종료 대기
→ 대체 Agent·Rule·Schema 제공
→ Archive 검증
→ RETIRED
```

실행 이력이 존재하는 Contract·Rule·Prompt는 물리 삭제하지 않는다.


## 4. 시사점


### 4.1 핵심 아키텍처 판단

자동 하네스에서 가장 중요한 구성요소는 생성 Agent가 아니라 Orchestrator와 Gate Engine이다.


```text
Agent가 똑똑해도
흐름과 권한이 통제되지 않으면
자동화 플랫폼이 아니라 위험한 자동 수정 도구가 된다.
```

최종 책임은 다음과 같이 분리해야 한다.


```text
Orchestrator
= 실행 순서와 상태 책임

Agent
= 계약된 작업 결과 책임

Gate Engine
= 자동 규칙 판정 책임

사람 승인자
= 업무·아키텍처 의사결정 책임

Evidence
= 완료 사실 입증 책임
```


### 4.2 주요 위험


| 위험 | 대응 |
| --- | --- |
| Orchestrator 단일 장애점 | 영속 상태·다중 인스턴스·Lease |
| 잘못된 상태 전이 | 상태머신·낙관적 잠금 |
| Agent 결과 형식 불안정 | JSON Schema·계약 Test |
| Agent 권한 초과 | Sandbox·Path·Tool Allowlist |
| Gate 형식화 | Hard Rule·실행 Evidence |
| 승인 병목 | 역할별 SLA·대리 승인정책 |
| 작업공간 오염 | Run 격리·Hash·Manifest |
| 중복 실행 | Idempotency Key·Lock |
| 설계와 코드 불일치 | Trace·Drift Gate |
| 예외의 상시화 | 만료일·보완 통제·자동 재검사 |


### 4.3 우선 보완 과제


#### P0 — 선도 구현 전


- Run·Stage·Task 상태모델
- Artifact·Evidence·TraceLink 메타모델
- Agent 공통 Input·Output Schema
- HG-00·10·20·30 최소 Gate
- Run별 작업공간과 Manifest
- Checkpoint·재시도·Lock
- Source Evidence Registry


#### P1 — 조회 거래 Pilot


- Requirement·Design·Code·Test Agent 계약
- Git Worktree와 Patch Manager
- Gradle Build·JUnit·ArchUnit Agent
- ServiceId·Handler·Mapper 검증 Rule
- HG-50·60·70·80
- 승인 UI
- 최종 Packaging


#### P2 — 확대 적용


- 등록·변경·삭제 거래
- 신규 업무 WAR
- 외부 연계·배치·파일
- 운영 장애 기반 변경요청
- 다중 프로젝트·다중 조직
- Agent 성능·품질 자동평가


### 4.4 중장기 발전 방향


```text
1단계
결정적 Workflow + 사람 승인

2단계
구조화된 요구사항·설계 원장

3단계
표준 Code·SQL·Test 생성

4단계
Gate as Code

5단계
Architecture as Code

6단계
Drift 자동 수정안 생성

7단계
NSIGHT Golden Path 개발 플랫폼
```


## 5. 마무리말

NSIGHT 자동 하네스의 상세 구조는 다음 한 문장으로 정리할 수 있다.


```text
Harness Orchestrator가
영속 상태머신에 따라 Agent를 실행하고,

Agent가
계약된 Artifact와 Evidence를 생성하며,

Gate Engine이
자동 규칙과 사람 승인을 결합하여 판정하고,

Promotion Manager가
통과한 결과만 다음 작업공간과 최종 OUT으로 승격하는 구조
```

따라서 자동 하네스의 구현 우선순위도 코드 생성 Agent가 아니다.


```text
1. 상태·메타모델
2. 작업공간과 Manifest
3. Orchestrator 상태머신
4. Agent 입출력 계약
5. Gate Engine
6. Evidence 수집
7. 조회 거래용 Agent
8. NSIGHT 코드 생성 확대
```

이 순서를 지켜야 자동 하네스가 단순 LLM 연계 도구가 아니라 요구사항·설계·구현·검증·승인을 하나의 추적 가능한 실행체계로 통제하는 엔터프라이즈 개발 플랫폼이 된다.
