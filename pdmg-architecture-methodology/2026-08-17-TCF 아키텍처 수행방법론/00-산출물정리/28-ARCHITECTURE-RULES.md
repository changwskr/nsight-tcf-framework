# NSIGHT Architecture Rules — G80

## 1. 목적

본 문서는 G00~G70에서 정의된 NSIGHT Architecture를 **기계 검증 가능한 Architecture Rule**로 전환한다.

상태는 `VERIFIED_* / PARTIAL / CANDIDATE / OPEN / OPEN_RUNTIME / FAIL_TARGET`로 구분한다. `VERIFIED_STATIC`은 Source 정적 점검을 통과했다는 의미이며 Runtime PASS를 의미하지 않는다.

## 2. Rule 분류

```text
R1 Structural
R2 Framework
R3 Security
R4 Runtime / Operational
MSG Standard Message
DOMAIN Domain Boundary
PERF Capacity / Performance
INFRA WEB/WAS/Server
DATA Data / Integration
GOV Governance / Closed Loop
```

## 3. Rule Registry

| Rule ID | Category | Title | Severity | Expected | Forbidden | Validation | Automation | Status |
|---|---|---|---|---|---|---|---|---|
| R1-001 | R1 Structural | ServiceId Unique in Runtime Scope | P0 | 동일 Dispatcher Runtime Scope에서 ServiceId는 유일해야 한다. | Duplicate ServiceId | Source Scanner + Dispatcher startup | AUTO | VERIFIED_STATIC |
| R1-002 | R1 Structural | ServiceId → Handler Mapping | P0 | 등록된 ServiceId는 정확히 하나의 TransactionHandler로 매핑되어야 한다. | 미등록/다중 매핑 | ServiceId Indexer | AUTO | VERIFIED_STATIC |
| R1-003 | R1 Structural | Handler → Facade Dependency | P0 | Handler는 Use Case 진입을 Facade로 위임한다. | Handler가 DAO/Mapper 직접 호출 | Dependency Scanner / ArchUnit | AUTO | VERIFIED_STATIC |
| R1-004 | R1 Structural | Facade → Service Dependency | P0 | Facade는 Service를 조합하고 Use Case 경계를 형성한다. | Facade가 Mapper 직접 접근 | Dependency Scanner / ArchUnit | AUTO | PARTIAL |
| R1-005 | R1 Structural | Service → DAO/Integration Boundary | P0 | Service는 DB 접근을 DAO/Mapper 경계로 위임하고 외부 연계는 Client/Port를 사용한다. | Service가 타 Domain 내부 Mapper/Table 직접 접근 | Dependency Scanner | AUTO | PARTIAL |
| R1-006 | R1 Structural | DAO ↔ Mapper Contract | P0 | DAO/Mapper namespace와 SQL ID는 일관된 계약을 가져야 한다. | Namespace/SQL ID 불일치 | Mapper/SQL Scanner | AUTO | CANDIDATE |
| R1-007 | R1 Structural | Package / Naming Conformance | P1 | 업무코드·Domain·계층이 Package/Class/ServiceId Naming과 추적 가능해야 한다. | 임의 패키지/명칭 | Naming Scanner | AUTO | CANDIDATE |
| R1-008 | R1 Structural | No Cross-Domain Internal Import | P0 | 다른 업무 Domain의 내부 구현 패키지를 직접 의존하지 않는다. | 타 Domain DAO/Mapper/Service 내부 import | Dependency Scanner / ArchUnit | AUTO | VERIFIED_HANDLER_SCOPE |
| R2-001 | R2 Framework | System / TCF / Business Pre-Post Separation | P0 | Filter/Interceptor, STF/ETF, Business AOP의 책임을 분리한다. | 공통처리 중복/역전 | Source Scanner + Architecture Test | SEMI | PARTIAL |
| R2-002 | R2 Framework | STF Before Business | P0 | 업무 실행 전에 STF가 거래통제·정책·Context 검증을 수행한다. | 업무 선실행 | Integration Test | AUTO | CANDIDATE |
| R2-003 | R2 Framework | ETF Finalization | P0 | 정상/예외 흐름 모두에서 ETF/후처리·Context 정리가 보장되어야 한다. | 예외 시 후처리 누락 | Integration Test + Runtime Evidence | AUTO | OPEN_RUNTIME |
| R2-004 | R2 Framework | Timeout Owner Explicit | P0 | Timeout/Deadline Owner와 Worker 경계를 명시한다. | 중첩 Timeout Owner | Source/Config Scanner + Runtime Test | SEMI | PARTIAL |
| R2-005 | R2 Framework | Transaction Owner Explicit | P0 | NSIGHT Target의 최외곽 업무 DB Transaction Owner는 승인된 경계로 단일화한다. | 의도하지 않은 중첩/외곽 TX | Transactional Scanner + Runtime Test | AUTO | DRIFT_CANDIDATE |
| R2-006 | R2 Framework | Context Propagation and Cleanup | P0 | Worker 전환 시 Context/MDC를 전파하고 finally에서 ThreadLocal을 정리한다. | Context leak | Thread leak test | AUTO | OPEN_RUNTIME |
| R2-007 | R2 Framework | Standard Error Flow | P0 | 업무코드는 예외를 던지고 Framework가 표준 오류응답을 생성한다. | 업무가 오류 JSON 직접 생성 | Source Scanner + Contract Test | AUTO | PARTIAL |
| R2-008 | R2 Framework | Dispatcher Duplicate Fail-Fast | P0 | 중복 ServiceId는 기동 시 즉시 실패해야 한다. | 마지막 등록이 이전 등록을 덮어씀 | Source/Startup Test | AUTO | VERIFIED_SOURCE |
| R3-001 | R3 Security | Private Signing Key SoT | P0 | JWT Private Key는 KMS/HSM 등 승인된 외부 Key SoT에서 관리한다. | 프로세스 기동 시 임시 RSA Key 생성 | Security Scanner | AUTO | FAIL_TARGET |
| R3-002 | R3 Security | Key Version / kid Rotation | P0 | kid는 Key Version과 연결되고 Rotation/Grace Period가 운영 가능해야 한다. | 고정 kid 단일키 | Security Test | SEMI | FAIL_TARGET |
| R3-003 | R3 Security | JWKS Validation Boundary | P0 | 검증 주체는 JWKS/Public Key로 Signature/issuer/audience/exp를 검증한다. | 검증 우회 | Security Contract Test | AUTO | PARTIAL |
| R3-004 | R3 Security | Refresh Token Protection | P0 | Refresh Token은 원문 저장을 피하고 Rotation/Revoke 정책을 적용한다. | 평문 장기 저장 | Source/DB Test | AUTO | PARTIAL |
| R3-005 | R3 Security | Denylist Enforcement | P0 | 로그아웃/강제폐기된 Access Token은 모든 보호 경로에서 차단된다. | Gateway/직접호출 우회 | Security Integration Test | AUTO | OPEN_RUNTIME |
| R3-006 | R3 Security | JWT / Session Mode Decision | P0 | JWT와 Session의 책임 및 Failover 정책은 ADR로 확정한다. | Hybrid 상태 무정책 운영 | ADR + Failover Test | MANUAL | OPEN |
| R3-007 | R3 Security | ServiceId / Menu / Data Authorization | P0 | 사용자 권한이 메뉴·기능·ServiceId·데이터 범위까지 연결된다. | 화면만 권한체크 | AuthZ Test | AUTO | OPEN |
| R3-008 | R3 Security | Service-to-Service Authentication | P0 | Domain 간 호출은 서비스 신원 검증을 적용한다. | 무인증 내부 HTTP | Integration Security Test | AUTO | OPEN |
| R3-009 | R3 Security | Sensitive Logging Prohibition | P0 | Token/주민번호/비밀번호 등 민감정보를 일반 로그/ImageLog에 원문 저장하지 않는다. | 전문 원문 무제한 로깅 | Log Scanner + Runtime Sample | AUTO | OPEN |
| R4-001 | R4 Runtime | GUID + ServiceId Correlation | P0 | 모든 주요 로그/Metric/Evidence가 GUID+ServiceId로 상관 가능해야 한다. | 레이어별 상관키 단절 | Runtime Trace Test | AUTO | OPEN_RUNTIME |
| R4-002 | R4 Runtime | Transaction Evidence | P0 | Begin/Commit/Rollback/elapsed가 Runtime Evidence로 남아야 한다. | 결과만 로그 | Runtime Probe | AUTO | OPEN_RUNTIME |
| R4-003 | R4 Runtime | Timeout Evidence | P0 | Timeout 시 Cancel/Rollback/Connection Return/Late Commit 여부를 증명한다. | Timeout 응답만 확인 | Fault Injection Test | AUTO | OPEN_RUNTIME |
| R4-004 | R4 Runtime | JVM/Tomcat/Hikari/DB Metrics | P0 | 성능 승인에는 JVM·Thread·Pool·DB Session/SQL 지표가 함께 존재해야 한다. | TPS만 측정 | Load Test Collector | AUTO | OPEN_RUNTIME |
| R4-005 | R4 Runtime | Runtime Config Snapshot | P0 | 각 Run은 Build/Commit/Config Version을 증적에 포함한다. | 설정 미식별 성능결과 | Config Scanner | AUTO | OPEN_RUNTIME |
| R4-006 | R4 Runtime | OM Catalog ↔ Runtime ServiceId | P0 | OM Service Catalog가 실제 Dispatcher ServiceId와 일치해야 한다. | 문서/OM Catalog drift | Catalog Diff | AUTO | OPEN |
| R4-007 | R4 Runtime | Deployment Evidence | P0 | 배포는 Artifact/Config/Health/Smoke/Rollback Evidence를 남긴다. | 배포 성공 여부 수동 판단 | Pipeline Evidence | AUTO | OPEN_RUNTIME |
| R4-008 | R4 Runtime | HA/DR Evidence | P0 | N-1/센터 장애/Failback/RTO/RPO는 실행 증적을 가져야 한다. | 구성도만 존재 | Failover Test | AUTO | OPEN_RUNTIME |
| MSG-001 | MSG Standard Message | Header / Body Responsibility | P0 | 공통 Header는 Framework/Context가 관리하고 업무에는 Business Body/DTO를 전달한다. | 전체 전문을 DAO까지 전달 | Source/Contract Test | AUTO | PARTIAL |
| MSG-002 | MSG Standard Message | Standard Error Contract | P0 | 오류는 표준 Result/Error DTO Contract로 응답한다. | 서비스별 오류형식 상이 | Contract Test | AUTO | PARTIAL |
| MSG-003 | MSG Standard Message | PDMG ↔ NSIGHT Message Mapping | P0 | hdr_nhnis+dto와 StandardRequest(header+body)의 Mapping Rule을 명시한다. | 암묵 변환 | Schema/Mapping Test | AUTO | OPEN |
| DOMAIN-001 | DOMAIN Boundary | No Direct DAO/Mapper Across Domain | P0 | 타 Domain DAO/Mapper 직접 호출을 금지한다. | Java 내부 직접호출 | ArchUnit / Dependency Scanner | AUTO | VERIFIED_HANDLER_SCOPE |
| DOMAIN-002 | DOMAIN Boundary | No Direct Foreign Table Update | P0 | 타 Domain 소유 Table을 직접 갱신하지 않는다. | 공유 DB를 통한 암묵 결합 | SQL/Table Ownership Scanner | SEMI | OPEN |
| DOMAIN-003 | DOMAIN Boundary | Published Service Contract | P0 | Domain 간 기능은 ServiceId/API Contract로 공개한다. | 내부 클래스 계약 | Contract Registry Diff | SEMI | OPEN |
| DOMAIN-004 | DOMAIN Boundary | Cross-Domain Transaction Independence | P0 | Domain 간 동기 호출은 하나의 Local TX로 간주하지 않고 멱등/보상/재처리를 설계한다. | 분산 Local TX 가정 | Design Review + Failure Test | SEMI | OPEN |
| PERF-001 | PERF Capacity | Session ≠ Concurrent Request | P0 | 세션 수와 동시요청자/TPS를 분리하여 산정한다. | Session 수를 TPS로 사용 | Capacity Model Validator | AUTO | VERIFIED_DOCUMENT |
| PERF-002 | PERF Capacity | Runtime Approved TPS | P0 | VM당 TPS는 부하시험 후 Runtime Approved 값으로 승격한다. | 산정값을 운영승인값으로 사용 | Load Test | AUTO | OPEN_RUNTIME |
| PERF-003 | PERF Capacity | Thread / Pool / DB Session Consistency | P0 | Tomcat Thread·Hikari Pool·DB Session 상한을 함께 검증한다. | 독립 설정 | Capacity Validator | AUTO | OPEN_RUNTIME |
| PERF-004 | PERF Capacity | Timeout Hierarchy | P0 | DB Query < Transaction < Server/External < Client Deadline을 유지한다. | 하위 Timeout이 상위보다 김 | Config Validator | AUTO | PARTIAL |
| PERF-005 | PERF Capacity | Stress / N-1 / Center Failure Test | P0 | Peak/Stress/N-1/센터장애 시 SLA와 잔여용량을 검증한다. | 정상 Peak만 테스트 | Load/Fault Test | AUTO | OPEN_RUNTIME |
| PERF-006 | PERF Capacity | Rolling Residual Capacity | P0 | Rolling 배포 중 제외 노드를 고려한 잔여 처리량이 Peak를 수용해야 한다. | 배포 중 과부하 | Deployment Load Test | AUTO | OPEN_RUNTIME |
| INFRA-001 | INFRA | Server ≠ JVM ≠ Application | P0 | WAS Server/VM, Tomcat JVM Instance, Application/WAR를 별도 Entity로 관리한다. | 개념 혼용 | Inventory/Config Diff | AUTO | VERIFIED_DOCUMENT |
| INFRA-002 | INFRA | Server → JVM → Application Mapping | P0 | 71대 Server Inventory가 JVM/Application/WAR에 전수 연결되어야 한다. | Hostname만 존재 | Inventory Mapper | SEMI | OPEN |
| INFRA-003 | INFRA | Apache Routing Evidence | P0 | Listen/VirtualHost/Proxy와 Tomcat Connector 실제 설정을 증적화한다. | 그림만 존재 | Config Scanner | AUTO | OPEN |
| INFRA-004 | INFRA | Tomcat Instance Evidence | P0 | CATALINA_BASE/server.xml/setenv.sh로 독립 JVM 경계를 증명한다. | Container 명칭만 존재 | Config Scanner | AUTO | OPEN |
| INFRA-005 | INFRA | HA Peer / DR Pair Catalog | P0 | Application HA Peer JVM과 운영↔DR Pair를 전수 관리한다. | 일부 Pair만 확인 | Inventory Diff | SEMI | OPEN |
| DATA-001 | DATA | RDW / ADW Responsibility Separation | P0 | RDW는 온라인/Near Real-time, ADW는 분석/대량조회 책임을 분리한다. | 동일 자원 무제한 경쟁 | Data Architecture Review | SEMI | VERIFIED_DOCUMENT |
| DATA-002 | DATA | Data Ownership / Read-Write Matrix | P0 | Domain별 Table/View Owner 및 RDW/ADW Read/Write 권한을 관리한다. | 소유권 불명확 | Metadata Diff | SEMI | OPEN |
| DATA-003 | DATA | Integration via Controlled Mechanism | P0 | CDC/Kafka/ETL/HTTP 계약을 통제하고 P2P/DB Link 암묵결합을 금지한다. | 비표준 직접연계 | Integration Registry | SEMI | PARTIAL |
| DATA-004 | DATA | Migration Reconciliation Gate | P0 | Migration은 Count/PK/Aggregate/Hash/Business Validation과 Rollback Runbook을 갖는다. | 이동 완료만 확인 | Migration Test | AUTO | OPEN |
| GOV-001 | GOV Closed Loop | Rule has Validation Method | P0 | Critical Rule은 반드시 검증방법과 Owner를 갖는다. | 문장형 표준만 존재 | Rule Schema Validator | AUTO | VERIFIED_DRAFT |
| GOV-002 | GOV Closed Loop | Architecture Model Schema | P0 | Document에서 추출한 Model은 JSON/YAML Schema로 기계 검증 가능해야 한다. | 비정형 문서만 존재 | JSON Schema Validation | AUTO | PARTIAL |
| GOV-003 | GOV Closed Loop | Runtime Evidence Required for PASS | P0 | Runtime 성질을 주장하는 Gate는 실제 실행 Evidence 없이는 PASS하지 않는다. | 문서만으로 PASS | Gate Evaluator | AUTO | ENFORCED |
| GOV-004 | GOV Closed Loop | Drift Register Required | P0 | Document/Model/Code/Config/Runtime 차이는 Drift Register로 관리한다. | 구두 정리 | Drift Diff | AUTO | VERIFIED_DRAFT |
| GOV-005 | GOV Closed Loop | Critical Decision via ADR | P0 | Session/HA/JWT Key/TX Owner 등 Critical Decision을 ADR로 남긴다. | 의사결정 이력 없음 | ADR Gate | SEMI | OPEN |
| GOV-006 | GOV Closed Loop | Baseline Versioning | P0 | 승인된 Model/Rule/Config/Test/Evidence를 동일 Baseline Version으로 묶는다. | 문서별 버전 분산 | Baseline Manifest Validator | AUTO | OPEN |

## 4. G80 정적 Source Scan 요약

| 항목 | 결과 | 판정 |
|---|---|---|
| Target Handler | 59 | Scope 확정 후 기준 |
| ServiceId Mapping | 121 | 15개 Module에서 추출 |
| Module 내부 ServiceId Duplicate | 0 | STATIC PASS |
| Handler → DAO 직접 import | 0 | STATIC PASS |
| Handler → Mapper 직접 import | 0 | STATIC PASS |
| Handler Cross-Domain import | 0 | STATIC PASS (Handler scope) |
| Facade | 50 | 50개 모두 @Transactional 확인 |
| Service @Transactional | 4 | 4개 — 예외/Drift 검토 |
| tcf-core System.out 포함 파일 | 5 | 운영 Logging Rule 위반 후보 |
| JWT KMS/HSM 참조 | 없음 | FAIL_TARGET — 현재 Source는 RSAKeyGenerator(2048) |

## 5. Rule Gate 원칙

- `P0` Rule의 `OPEN_RUNTIME`/`FAIL_TARGET`이 남아 있으면 G80은 PASS할 수 없다.
- `STATIC PASS`는 Source 구조만 검증하며 성능·보안·HA·Timeout의 실제 실행을 증명하지 않는다.
- 허용 예외는 `TARGET_EXCEPTION`으로 등록하고 ADR/만료시점을 가져야 한다.
