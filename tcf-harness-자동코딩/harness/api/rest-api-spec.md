# NSIGHT 자동 하네스 REST API 명세

## 1. 공통 기준

- Base Path: `/harness/api/v1`
- Content-Type: `application/json`
- 비동기 처리 생성 응답: `202 Accepted`
- 상태 변경 요청: `Idempotency-Key` 필수
- 낙관적 잠금 요청: `If-Match`에 `VERSION_NO` 전달
- 응답 추적: `X-Trace-Id`
- 오류 형식: RFC 9457 Problem Details 확장
- 사용자 API: JWT Bearer 인증
- Worker 내부 API: 별도 Worker JWT와 Task Lease 검증

## 2. API 목록

| 영역 | Method | URI | 목적 |
|---|---|---|---|
| 프로젝트 | GET | `/projects` | 프로젝트 목록 |
| 프로젝트 | POST | `/projects` | 프로젝트 등록 |
| Workflow | GET | `/workflows` | Workflow 목록 |
| Workflow | POST | `/workflows` | Workflow 등록 |
| Workflow | GET | `/workflows/{workflowId}/versions/{version}` | Workflow 상세 |
| Workflow | POST | `/workflows/{workflowId}/versions/{version}:validate` | Workflow 검증 |
| Run | POST | `/projects/{projectId}/runs` | Run 생성 |
| Run | GET | `/runs/{runId}` | Run 상세 |
| Run | GET | `/runs/{runId}/stages` | Stage 목록 |
| Run | GET | `/runs/{runId}/tasks` | Task 목록 |
| Run | POST | `/runs/{runId}:suspend` | 일시정지 |
| Run | POST | `/runs/{runId}:resume` | 재개 |
| Run | POST | `/runs/{runId}:cancel` | 안전한 취소 요청 |
| Task | POST | `/tasks/{taskId}:retry` | 실패 Task 재시도 |
| Run | POST | `/runs/{runId}:recover` | Checkpoint 복구 |
| Artifact | GET | `/runs/{runId}/artifacts` | Artifact 목록 |
| Artifact | GET | `/artifacts/{artifactId}` | Artifact·버전 상세 |
| Manifest | GET | `/runs/{runId}/manifests` | Manifest 목록 |
| Evidence | GET | `/runs/{runId}/evidence` | Evidence 목록 |
| Trace | GET | `/runs/{runId}/trace-links` | 추적관계 |
| Trace | GET | `/runs/{runId}/impact` | 영향분석 |
| Drift | GET | `/runs/{runId}/drifts` | Drift 목록 |
| Gate | POST | `/runs/{runId}/gates/{gateId}:evaluate` | Gate 평가 요청 |
| Gate | GET | `/runs/{runId}/gates` | Gate 결과 목록 |
| Gate | GET | `/gate-results/{gateResultId}` | Gate 결과 상세 |
| Approval | POST | `/approvals/{approvalId}:approve` | 승인 |
| Approval | POST | `/approvals/{approvalId}:reject` | 반려 |
| Exception | POST | `/runs/{runId}/exceptions` | 예외 신청 |
| Exception | POST | `/exceptions/{exceptionId}:approve` | 예외 승인 |
| Worker | POST | `/internal/agents/{agentId}/tasks:claim` | Task Lease 획득 |
| Worker | POST | `/internal/tasks/{taskId}/attempts/{attemptNo}:heartbeat` | Lease 연장 |
| Worker | POST | `/internal/tasks/{taskId}/attempts/{attemptNo}:complete` | 결과 제출 |
| Worker | POST | `/internal/tasks/{taskId}/attempts/{attemptNo}:fail` | 실패 제출 |

## 3. Run 생성 예시

```http
POST /harness/api/v1/projects/NSIGHT/runs
Idempotency-Key: 95a39dd0-57cb-4f4c-a8b7-560ec1ed0315
Content-Type: application/json
```

```json
{
  "workflowId": "WF-ONLINE-INQUIRY",
  "workflowVersion": "1.0.0",
  "runType": "ONLINE_INQUIRY",
  "runName": "AV 자산평가 목록 조회",
  "requestedBy": "architect01",
  "priority": "NORMAL",
  "baselineId": "BASE-NSIGHT-0001",
  "businessCode": "AV",
  "domainCode": "AssetValuation",
  "inputArtifacts": [{
    "artifactId": "ART-REQ-SOURCE-0001",
    "artifactType": "REQUIREMENT_SOURCE",
    "uri": "upload://requirements/av-list.md",
    "contentHash": "sha256:0000000000000000000000000000000000000000000000000000000000000000"
  }]
}
```

## 4. 승인 예시

```http
POST /harness/api/v1/approvals/APR-HG30-0001:approve
If-Match: "3"
```

```json
{
  "approverId": "aa-lead01",
  "role": "APPLICATION_ARCHITECT",
  "comment": "ServiceId와 Facade 트랜잭션 경계를 승인합니다."
}
```

## 5. 주요 오류코드

| 코드 | HTTP | 설명 |
|---|---:|---|
| `HAR-REQ-400` | 400 | 요청 Schema 오류 |
| `HAR-AUTH-403` | 403 | 역할·Resource 권한 부족 |
| `HAR-RUN-404` | 404 | Run 미존재 |
| `HAR-STATE-409` | 409 | 허용되지 않은 상태 전이 |
| `HAR-LOCK-409` | 409 | Project·Module Lock 충돌 |
| `HAR-VERSION-412` | 412 | `If-Match` 버전 불일치 |
| `HAR-GATE-422` | 422 | Gate 입력·Evidence 부족 |
| `HAR-ART-422` | 422 | Artifact Hash·Manifest 불일치 |
| `HAR-TASK-423` | 423 | Task Lease 소유권 불일치 |
| `HAR-LIMIT-429` | 429 | Run·Token·Worker 한도 초과 |
| `HAR-INT-500` | 500 | 내부 처리 오류 |
