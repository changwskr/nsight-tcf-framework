# HG90 Approval Checklist

> 현재 상태: **BLOCKED / HOLD**

## A. Source / Configuration

- [x] System Scope 분리
- [x] Current / Legacy / Proposed 분리
- [x] PDMG AS-IS / NSIGHT TCF TO-BE 분리
- [ ] 운영 Branch/Commit/Build Baseline 확정
- [ ] 실제 Apache/L4/GSLB Routing Config 연결
- [ ] 실제 Tomcat `server.xml` / `setenv.sh` / CATALINA_BASE 전수 연결
- [ ] 71 Server → JVM → WAR 전수 mapping

## B. Application / TCF

- [x] ServiceId Dispatcher 구조 검증
- [x] Handler→DAO/Mapper 직접 의존 정적검증
- [x] Facade `@Transactional` 기본패턴 정적검증
- [ ] Service Transaction 2건(EbUserService.create, EpUserEventService.receive) 예외승인 또는 수정
- [ ] Timeout Late Commit/Connection Return fault test
- [ ] Context/ThreadLocal leak test

## C. Security

- [ ] JWT KMS/HSM Signing Key SoT
- [ ] Versioned `kid`
- [ ] Key Rotation + JWKS Grace test
- [ ] SSO Assertion Verification Owner
- [ ] S2S Authentication
- [ ] ServiceId/Menu/Data Authorization 전수검증

## D. Data / Integration

- [ ] Domain/Table/View Owner Catalog
- [ ] RDW/ADW Read/Write Matrix
- [ ] ServiceId/API Route Registry
- [ ] Remaining Deadline Propagation
- [ ] Retry/CB/Bulkhead/Idempotency 승인

## E. Capacity / Runtime

- [ ] RUN-P600
- [ ] RUN-P1200
- [ ] RUN-S1800
- [ ] RUN-N1
- [ ] RUN-HIKARI
- [ ] RUN-SLOWSQL
- [ ] Runtime Approved VM/Tomcat/JVM/Hikari Baseline

## F. HA / DR / Session

- [ ] Session Strategy ADR
- [ ] RUN-SESSION
- [ ] HA Topology ADR
- [ ] Service RTO/RPO
- [ ] RUN-CF failover/failback
- [ ] 운영↔DR 전수 Pair Catalog

## G. Operations / Observability

- [ ] OM Runtime Catalog
- [ ] RUN-TRACE GUID+ServiceId E2E
- [ ] Alert→Runbook→Evidence Closed Loop
- [ ] Critical Config Change Approval/Audit/Expiration

## H. CI/CD / Migration

- [ ] RUN-ROLLING
- [ ] Deployment Rollback test
- [ ] DB/Config backward compatibility
- [ ] Migration reconciliation
- [ ] Migration Go/No-Go
- [ ] Migration rollback rehearsal

## I. Architecture Governance

- [x] Architecture Rule Registry
- [x] Partial Architecture Model
- [x] Static Conformance Result
- [x] Drift/GAP/Risk/ADR/Open Registers
- [x] Architecture Model JSON Schema
- [x] Model Validator PASS
- [ ] Requirement→Screen→ServiceId→DB→Server→Evidence full traceability
- [ ] Critical ADR approved
- [ ] P0 FAIL_TARGET = 0
- [ ] P0 OPEN_RUNTIME = 0 or formally approved exception

## Final

- [ ] G80 PASS/approved conditional re-evaluation
- [ ] HG90 Human Sign-off
- [ ] Baseline Release Manifest signed
- [ ] `99-MASTER-ARCHITECTURE.md` status changed from CANDIDATE to APPROVED


## J. P0 Closure Change Specification

- [x] Wave 2A Change Spec — JWT Key Provider
- [x] Wave 2A Change Spec — Versioned kid / Rotation / JWKS Grace
- [x] Wave 2A Change Spec — Facade TX Owner cleanup
- [x] Wave 2A Harness Spec — Timeout rollback/late commit/connection return
- [ ] Wave 2A Source implementation
- [ ] Wave 2A Build/Test evidence
- [ ] RUN-TIMEOUT
- [ ] RUN-JWT-ROTATE
