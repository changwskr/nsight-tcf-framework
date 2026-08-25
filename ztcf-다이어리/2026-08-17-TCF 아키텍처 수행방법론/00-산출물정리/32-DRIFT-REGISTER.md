# NSIGHT Architecture Drift Register — G80

## 1. 분류

`REFERENCE_INTERNAL_DRIFT / REFERENCE_DOCUMENT_DRIFT / REFERENCE_RUNTIME_DRIFT / TARGET_CONFORMANCE_DRIFT / ALLOWED_VARIANT / TARGET_EXCEPTION / GOVERNANCE_DRIFT / INVENTORY_RUNTIME_DRIFT`를 사용한다.

## 2. Drift Register

| Drift ID | Type | Domain | Source A | Source B | Difference | Impact | Severity | Action |
|---|---|---|---|---|---|---|---|---|
| DRIFT-001 | REFERENCE_DOCUMENT_DRIFT | OM | 문서 Handler 24 | tcf-om Source Handler 25 | OmMessageStructureHandler 포함 수량 차이 | OM Catalog/문서 신뢰성 | P1 | Source 기반 Catalog 재생성 |
| DRIFT-002 | TARGET_CONFORMANCE_DRIFT | Security | KMS/HSM Signing Key 설계 | JwtKeyConfiguration RSAKeyGenerator(2048) | 외부 Key SoT 대신 process-local key 생성 | 재기동/다중 Issuer 검증 위험 | P0 | ADR-SEC-001 + 구현 변경 |
| DRIFT-003 | TARGET_CONFORMANCE_DRIFT | Security | Key Version/Rotation 필요 | 고정 kid nsight-jwt-rs256 | Version/Grace Period 없음 | 무중단 Key Rotation 불가 | P0 | ADR-SEC-002 |
| DRIFT-004 | REFERENCE_DOCUMENT_DRIFT | Runtime | Java 17 표기 문서 | Current PDMG/Target 일부 Java 21 | Toolchain 기준 혼재 | Build/CI 재현성 | P1 | Build Baseline 확정 |
| DRIFT-005 | REFERENCE_INTERNAL_DRIFT | Capacity | 16Core 500 TPS 보수 기준 | 16Core 855 TPS Working 산정 | 동일 VM 처리량 버전 혼재 | AP 수량/HA 잔여용량 | P0 | Runtime Approved TPS로 승격 |
| DRIFT-006 | REFERENCE_INTERNAL_DRIFT | Session | Session 60m 문서 | Session 90m Working 문서 | Idle timeout 기준 혼재 | 세션 메모리/운영정책 | P0 | ADR-SES-001 |
| DRIFT-007 | REFERENCE_INTERNAL_DRIFT | Tomcat | maxThreads 1,552 산정 버전 | 800~1,000 Working | Thread 상한 혼재 | Native memory/DB 압력 | P0 | Runtime 승인 |
| DRIFT-008 | REFERENCE_INTERNAL_DRIFT | DB Pool | Hikari 80~120 기준 | 120~150 / 150~180 Working | Pool 기준 혼재 | DB Session/Wait | P0 | Hold-Time 기반 Runtime 검증 |
| DRIFT-009 | TARGET_CONFORMANCE_DRIFT | Transaction | Facade = TX Owner Target | 표준 `*-service` 범위 Service TX 2건(EbUserService/EpUserEventService) + 별도 `tcf-oc` scope TX 2파일 | 표준업무 2건은 중복 TX 후보; tcf-oc는 별도 scope 판정 필요 | Rollback/Timeout 경계 | P0 | 표준업무 2건 제거/예외승인 + tcf-oc scope 승인 |
| DRIFT-010 | TARGET_CONFORMANCE_DRIFT | Observability | SLF4J/통합 Runtime Evidence 원칙 | tcf-core 5개 파일 System.out 사용 | 운영 로그 통제 우회 | 상관관계/로그 품질 | P1 | Console trace 제거/프로파일 제어 |
| DRIFT-011 | ALLOWED_VARIANT | Reference/Target | PDMG outer TransactionTemplate | NSIGHT Facade TX Owner 방향 | Reference와 Target 메커니즘 차이 | 오해 가능 | P0 | 두 Baseline 분리 + ADR |
| DRIFT-012 | ALLOWED_VARIANT | Message | PDMG hdr_nhnis+dto/result | NSIGHT StandardRequest header+body/result | 전문 모델 차이 | 연계 호환성 | P0 | Mapping Contract 확정 |
| DRIFT-013 | REFERENCE_DOCUMENT_DRIFT | PDMG Scope | Expected pdmg-om | 현재 Source Snapshot 독립 pdmg-om 미확인 | Expected vs Snapshot 차이 | 운영모듈 책임 혼선 | P1 | AS-IS/TO-BE Scope 명시 |
| DRIFT-014 | TARGET_CONFORMANCE_DRIFT | Rule Layer | 독립 Rule Target | PDMG AS-IS Service 내부 Rule 성격 로직 | 계층 목표와 Reference 구현 차이 | 표준/교육 혼선 | P1 | Target Rule 정책 ADR |
| DRIFT-015 | GOVERNANCE_DRIFT | Closed Loop | Runtime Evidence Required | 실제 Mandatory Run 원본 미연결 | Document/Source까지만 닫힘 | Architecture Gate 신뢰성 | P0 | G80 HOLD 유지 |
| DRIFT-016 | INVENTORY_RUNTIME_DRIFT | Physical | 71대 Master Inventory | Server→JVM→WAR 전수 Runtime mapping 미완료 | 논리/물리 추적성 단절 | 장애/배포 영향분석 불완전 | P0 | Inventory Runtime Mapper |

## 3. 원칙

`ALLOWED_VARIANT`는 오류가 아니지만 Reference와 Target을 혼합 설명하지 않기 위해 명시적으로 관리한다. `P0 TARGET_CONFORMANCE_DRIFT`와 `GOVERNANCE_DRIFT`는 HG90 이전에 승인/해소가 필요하다.
