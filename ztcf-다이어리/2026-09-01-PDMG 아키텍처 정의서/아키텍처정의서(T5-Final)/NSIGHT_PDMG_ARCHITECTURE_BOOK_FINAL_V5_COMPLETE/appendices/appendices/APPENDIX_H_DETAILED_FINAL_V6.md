# APPENDIX H. ADR Register — Detailed Final
## Architecture Decision Register / PASS Evaluation

### H.1 40 Decision Tasks

| ID | Domain | Priority | Decision Task | Architecture PASS 여부 | 현행/PDMG 정합 | 판정근거 | 미해결 / PASS 전환조건 | 관련 Architecture |
|---|---|---|---|---|---|---|---|---|
| ADR-TASK-001 | Architecture Governance | P0 | Architecture Baseline / SSOT 운영체계 | PASS | PARTIAL | Architecture Closed Loop와 G00~G80/HG90 Baseline/SSOT 체계를 이미 정의함. | Registry/Rule/Runtime Evidence 자동화와 실제 승인 Baseline 운영 필요 | 01 VISION / 07 CLOSED LOOP / 10 INTEGRATED BASELINE |
| ADR-TASK-002 | Architecture Governance | P0 | Application/Naming/ServiceId Code Registry SSOT | PASS | PARTIAL | Naming·Application Code 표준에서 중앙 Registry와 CI 검증을 SSOT 방향으로 정의함. | ServiceId/InterfaceId/Code Registry 실제 구축 및 UI↔Backend 자동정합 적용 | 별첨 F NAMING / Application Code 정의서 |
| ADR-TASK-003 | Architecture Governance | P0 | PDMG AS-IS ↔ NSIGHT Target Mapping | PASS | PARTIAL | NSIGHT=Target, PDMG=AS-IS Reference로 분리하고 Mapping/ADR을 통해 선택 승격하도록 정의함. | PDMG mg 계열과 NSIGHT MP/업무코드의 전수 Mapping Registry 작성 | 01 VISION / 별첨 A,F |
| ADR-TASK-004 | Application | P0 | Business Core 진입점 표준 | CONDITIONAL PASS | GAP | Target은 Handler와 Controller가 동일 Business Facade/Core를 공유하는 구조로 정리됨. | TCF OFF Controller→Service 직접호출을 Facade 중심으로 정합시키고 Architecture Test 확보 | 별첨 A APPLICATION |
| ADR-TASK-005 | Application | P0 | TCF 적용 정책 | CONDITIONAL PASS | PARTIAL | TCF ON 표준 Runtime과 OFF 예외경로를 구분했고 ON 중심 통제구조가 정의됨. | 신규 Online의 TCF ON 기본정책을 ADR로 승인하고 OFF 예외목록/성능근거 확정 | 별첨 A / 06 RUNTIME / 11 DEV STANDARD |
| ADR-TASK-006 | Application | P1 | Rule Layer 도입범위 | PASS | PARTIAL | Rule Layer는 복잡·재사용 규칙에 선택 적용하는 구조가 Architecture 정의와 정합함. | Rule 적용 기준과 Naming/Package 규칙을 개발표준에 최종 반영 | 별첨 A / 별첨 F |
| ADR-TASK-007 | Application | P0 | 표준 메시지/헤더 구조 | CONDITIONAL PASS | PARTIAL | hdr_nhnis + dto/result 표준 메시지와 Header/GUID 책임을 정의함. | Filter 조기오류/Resolver 모호성 포함 전 Runtime 경로의 동일 Envelope 보장 및 Contract Test | 06 MESSAGE/CONTEXT/ERROR / 별첨 D |
| ADR-TASK-008 | Application | P0 | Error Handling / Error Code 표준 | CONDITIONAL PASS | GAP | 중앙 Error Taxonomy/표준 Error Contract 방향은 정의되었으나 현행 Generic/Filter 오류 경로 GAP가 존재함. | Global/Filter 오류 통합, exceptionCode Catalog 연계, 공통 Error Envelope 증적 | 06 MESSAGE/ERROR / 별첨 D |
| ADR-TASK-009 | Application | P0 | ServiceContext Worker 전달 방식 | PASS | GAP | Worker 경계에서 필요한 Context만 Snapshot으로 전달해야 한다는 Target 원칙이 명확함. | Mutable ServiceContext/Servlet 객체 공유 제거 및 동시성 Test | 06 RUNTIME / 별첨 A |
| ADR-TASK-010 | Security | P0 | JWT 검증 알고리즘 표준 | PASS | GAP | Security Target은 RS256 Issuer + JWKS 기반 검증으로 정합시키는 방향이 명확함. | pdmg-fw HMAC 검증경로 제거/정합 및 End-to-End JWT 검증 Test | Security/JWT 정의 / 별첨 B |
| ADR-TASK-011 | Security | P0 | JWT Key Management / Rotation | PASS | GAP | 다중 Instance/DR에서 Versioned Key·kid·JWKS·공유 Key Source가 필요하다고 정의함. | Managed Key Store, Rotation, DR Sync, same-kid same-key 검증 | Security/JWT 정의 / 별첨 B,C |
| ADR-TASK-012 | Security | P0 | Session / Token State 전략 | CONDITIONAL PASS | OPEN | JWT 중심 + Refresh/Revoke State, HttpSession 최소화 방향은 정해졌으나 Session 60/90분 및 복제방식 Conflict가 남음. | Session TTL/필요범위/Sticky·Replication·Shared Store 중 최종정책 ADR | Security/JWT / 별첨 B,C |
| ADR-TASK-013 | Security | P0 | Identity Binding / 업무사용자 신뢰체계 | CONDITIONAL PASS | GAP | Trusted Principal을 Header 사용자값보다 우선하는 보안 원칙은 명확하나 현행 Binding GAP가 존재함. | JWT Subject→Business User Context Binding과 Header mismatch 차단 Test | Security/JWT / 06 CONTEXT |
| ADR-TASK-014 | Interface | P0 | Interface Type Selection 정책 | PASS | PARTIAL | Transaction→API/MCA, Event→Kafka, Change→CDC, Bulk→ETL, File→MFT/FOS로 Purpose-driven 선택을 정의함. | 전 Interface Catalog에 Type 분류 적용 및 예외 ADR | 05 MECHANISM / 별첨 D |
| ADR-TASK-015 | Interface | P0 | Cross-System Direct DB / DB-Link 정책 | PASS | OPEN | Cross-System Direct DML/DB-Link 금지, Approved Interface/Data Contract 사용 원칙을 명시함. | 현행 Direct DB/DB-Link 전수 Inventory와 예외 승인 정리 | 03 LOGICAL / 별첨 D,E |
| ADR-TASK-016 | Interface | P1 | SYNC / ASYNC 기본정책 | PASS | OPEN | 현재 거래 완료에 Target 결과가 필수인 경우만 SYNC, 그 외 ASYNC 우선이라는 원칙과 정합함. | 주요 Interface별 Sync/Async 분류와 Failure/Consistency 검증 | 별첨 D INTERFACE |
| ADR-TASK-017 | Interface | P0 | Timeout Budget 표준 | CONDITIONAL PASS | OPEN | DB Query < Worker/TX < Server/Downstream < Client의 Timeout Hierarchy는 정의됨. | 서비스별 실제 Timeout 값·측정구간·DB Query Timeout을 승인하고 테스트 | 05/06 RUNTIME / 별첨 D |
| ADR-TASK-018 | Interface | P0 | Retry / Idempotency 정책 | PASS | OPEN | Retryable Error + Backoff + Max Retry + Idempotency/Compensation 정책이 명확히 정의됨. | 거래별 Retry Matrix와 Idempotency Key/보상·DLQ 정책 전수화 | 05 MECHANISM / 별첨 D |
| ADR-TASK-019 | Interface | P1 | Event Platform 표준 | CONDITIONAL PASS | OPEN | Event Broker/Kafka 기반 비동기 Event 구조는 Target으로 정의되나 제품/버전/Cluster는 미결정. | 표준 Event 제품/Cluster/Schema Registry/운영모델 PoC 및 ADR | 02 BIG PICTURE / 05 MECHANISM / 별첨 D |
| ADR-TASK-020 | Interface | P1 | 대량/파일 연계 표준 | PASS | PARTIAL | Bulk=ETL, File=MFT/FOS, Online=소량 Transaction 분리 원칙이 정의됨. | 파일/대량 Interface Catalog와 Volume/Reconciliation Test 확보 | 05 MECHANISM / 별첨 D |
| ADR-TASK-021 | Data | P0 | RDW / ADW 역할분리 | PASS | PARTIAL | RDW=운영·준실시간, ADW=분석·집계·마트의 역할분리가 Baseline으로 명확함. | 실제 DB/Workload/권한/ETL 배치를 Inventory와 성능시험으로 검증 | 별첨 E DATA / 04 PHYSICAL |
| ADR-TASK-022 | Data | P0 | CDC Freshness SLA | CONDITIONAL PASS | CONFLICT | CDC 경로와 Freshness 측정개념은 정의되었지만 3초 vs 30초 SLA 충돌이 공식적으로 남아 있음. | Source Commit→Consumer Visible 측정점 고정 후 Tier/SLA ADR 승인 | 별첨 E / 별첨 D / 06 RUNTIME |
| ADR-TASK-023 | Data | P0 | Data Subject / Ownership SSOT | CONDITIONAL PASS | PARTIAL | Data Subject Registry와 Owner/Steward/SOR 구조는 정의됨. | Subject별 Owner/Steward/SOR/Consumer 전수 지정과 Registry 승인 | 별첨 E DATA |
| ADR-TASK-024 | Data | P1 | Metadata / Lineage / Data Quality 운영방식 | CONDITIONAL PASS | OPEN | Metadata/Lineage/DQ를 Architecture 핵심영역으로 정의했으나 자동수집 도구/범위가 미확정. | Critical Data 우선 자동수집 PoC, DQ Rule Coverage/Repository 운영방식 결정 | 별첨 E DATA |
| ADR-TASK-025 | Data | P1 | Heavy Analytical Query 격리 | PASS | PARTIAL | Heavy Analytical Query는 ADW로 격리하고 RDW Operational SLA를 보호하도록 정의함. | Query Workload Profile과 Resource Test로 예외/한계치 확정 | 별첨 E / 04 PHYSICAL |
| ADR-TASK-026 | Infrastructure | P0 | WAS Compute Sizing / Scale-out 단위 | CONDITIONAL PASS | CANDIDATE | Scale-out/Failure Domain 원칙상 중형 VM 다수 구성이 우세하지만 현재 값은 Capacity Candidate임. | 16C/128G vs 32C/256G Load/Soak/N+1/License 비교 후 승인 | 별첨 B,C / HW-SW Matrix |
| ADR-TASK-027 | Infrastructure | P0 | WEB/WAS 표준 접속 Topology | PASS | PARTIAL | 표준 Working Path를 GSLB→L4→Apache→Tomcat→WAR로 명시함. | 실제 VIP/Apache/Tomcat Topology와 성능·보안 Evidence 확보 | 04 PHYSICAL / 별첨 B,C |
| ADR-TASK-028 | Infrastructure | P0 | JVM / WAR 배치 및 업무그룹 격리 | PASS | OPEN | 업무그룹별 JVM/WAR 자원·장애격리 원칙과 Server≠JVM≠WAR를 명확히 정의함. | 17 WAR의 그룹 A/B 실제 배치표와 JVM별 Load/Heap/배포영향 검증 | 04 PHYSICAL / 별첨 C |
| ADR-TASK-029 | Runtime | P0 | Thread / Worker / Hikari Capacity 정책 | CONDITIONAL PASS | OPEN | Tomcat→Worker→Hikari→DB End-to-End Capacity Budget과 Backpressure 원칙은 정의됨. | maxThreads/Hikari/Heap/Worker 승인값을 Load/Stress/Soak 결과로 확정 | 06 RUNTIME / 별첨 B,C / HW-SW Matrix |
| ADR-TASK-030 | Infrastructure | P0 | 주센터 HA Pattern | PASS | OPEN | WEB/WAS Stateless 계층 Active-Active/N+1, Stateful 기술은 제품특성별 HA라는 구조가 정의됨. | Node Failure/N+1/Session/Key/DB HA 실제 Failure Test | 04 PHYSICAL / 06 RUNTIME / 별첨 C |
| ADR-TASK-031 | Infrastructure | P0 | DR 운영모델 | CONDITIONAL PASS | OPEN | DR은 Traffic+Artifact+Config+Key+Data+Interface+Monitoring까지 포함하는 구조로 정의됨. | 서비스 Criticality별 Hot/Warm/Cold Tier 및 RTO/RPO, DR Drill 승인 | 06 RUNTIME / 별첨 B,C |
| ADR-TASK-032 | Data/Infrastructure | P0 | DB HA / DR 구조 | CONDITIONAL PASS | OPEN | RDW/ADW Local HA와 Center DR을 분리하는 원칙은 명확하나 실제 Node/Replication 방식은 미확정. | DB Node 수/제품/Replication/RTO-RPO/Failover-Restore Test 확정 | 04 PHYSICAL / 별첨 C,E |
| ADR-TASK-033 | DevOps | P1 | CI/CD Orchestration 도구 | OPEN | OPEN | GitLab/Gradle 기반은 확인되지만 GitLab Runner 중심 vs Jenkins 중심의 최종 Orchestration 선택은 Architecture에서 확정하지 않음. | 기존 Jenkins 자산/플러그인/운영역량 조사 후 CI Orchestrator ADR | 09 DEVOPS / 11 DEV STANDARD |
| ADR-TASK-034 | DevOps | P0 | Artifact Promotion / Deployment Trace | PASS | PARTIAL | sourceCommit→buildId→artifactHash→deploymentId의 Immutable Promotion/Trace 체계를 Baseline으로 정의함. | Artifact Repository/Promotion Pipeline과 Hash 검증 운영 Evidence | 07 CLOSED LOOP / 09 DEVOPS / 10 BASELINE |
| ADR-TASK-035 | Operations | P0 | Observability 표준 | PASS | PARTIAL | Metric+Structured Log+Trace, ServiceId/GUID/DeploymentId 연계 Observability가 명확히 정의됨. | 실제 APM/Logging 제품, Coverage, Alert/Runbook, Runtime Evidence 연결 | 09 OM/DEVOPS/OBSERVABILITY |
| ADR-TASK-036 | Operations | P1 | OM Control Plane 구조 | CONDITIONAL PASS | UNKNOWN | OM을 Business Runtime과 분리된 Control Plane으로 보는 Architecture 원칙은 정의됨. | pdmg-om 실제 Source/Runtime Scope 확인 후 Target OM 기능/HA/권한 ADR | 09 OM / 별첨 B |
| ADR-TASK-037 | Security/DevOps | P0 | Configuration / Secret Management | PASS | OPEN | 환경별 Config 외부화, Secret/Key 분리·보호·DR 동기화 원칙이 Technical/Security Architecture와 정합함. | Secret Store/Config Delivery 제품과 Rotation/Drift Test 확정 | 별첨 B,C / Security Architecture |
| ADR-TASK-038 | Operations | P0 | Backup / Restore 운영기준 | PASS | PARTIAL | Backup 성공과 Restore/Business Recovery를 분리하고 Restore Test를 필수로 정의함. | 정기 Restore Drill/정합성 검증/증적 보관 프로세스 운영 | 별첨 C,E / 06 RUNTIME |
| ADR-TASK-039 | DevOps/Operations | P0 | Production Change / Release 통제 | PASS | PARTIAL | Release Manifest·Artifact Hash·DeploymentId·Rollback/Verify 기반의 변경통제 방향이 정의됨. | 생산 배포도구/eCAMS 연계와 승인/자동 Rollback/DR Promotion 운영 검증 | 09 DEVOPS / 10 BASELINE |
| ADR-TASK-040 | Architecture Governance | P0 | Runtime Evidence 자동수집 / Baseline Release Gate | PASS | PARTIAL | 문서→모델→코드→테스트→Runtime Evidence→Drift→ADR→HG90 Closed Loop가 최상위 원칙으로 확정됨. | Critical Rule의 Evidence Collector/자동 Gate 구현과 G50~HG90 운영 | 01 VISION / 07 CLOSED LOOP / 10 BASELINE |

### H.2 Gate Matrix

| Gate | 의미 | 필수 Decision Domain | 주요 산출/Evidence | Exit Criteria |
|---|---|---|---|---|
| G00 | Source 기준 | Governance | Source/Config Inventory | 기준 Source 고정 |
| G10 | Document 분류 | Governance/Application/Data | FACT/AS-IS/TO-BE/GAP 분류 | 문서 상태 정리 |
| G20 | Architecture Model | Application/Data/Interface/Technical | Model/Registry/Mapping | 핵심 Boundary/ID 결정 |
| G30 | Conformance | Naming/Application/Interface/Security | Static Scan/Config Check | Critical Drift 식별 |
| G40 | Rule/Test | 전체 | Architecture Test/Contract/Security | P0 Rule PASS 또는 승인예외 |
| G50 | Runtime Evidence | Runtime/Infra/Ops | Load/Failure/DR/Metric/Trace | Critical Runtime Evidence 확보 |
| G60 | Drift | 전체 | Baseline vs Actual Diff | Critical Drift 0 또는 ADR |
| G70 | GAP/ADR | 전체 | ADR Pack/Risk Closure | 미결정 P0 해소 |
| G80 | Approval | EA/PMO | Approval Record | Release 승인 |
| HG90 | Baseline Release | 전체 | Baseline Manifest/Evidence Index | 공식 Architecture Baseline 발행 |

### H.3 Aggregate

```text
Architecture
PASS              23
CONDITIONAL PASS  16
OPEN               1
FAIL               0

Current / PDMG
PARTIAL           17
GAP                6
CONFLICT           1
OPEN              14
CANDIDATE          1
UNKNOWN            1
PASS               0
```

### H.4 Important Interpretation

`Architecture PASS ≠ Current Implementation Complete`.

유일한 Architecture OPEN은 `ADR-TASK-033 CI/CD Orchestration 도구`다.
