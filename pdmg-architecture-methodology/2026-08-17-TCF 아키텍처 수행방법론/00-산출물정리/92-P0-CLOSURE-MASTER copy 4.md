# P0 Closure Execution — Wave 1

> Candidate: `NSIGHT-ARCH-CANDIDATE-2026-08-19`  
> 목적: HG90 HOLD를 해제하기 위한 첫 Closure Wave.

## 1. Wave 1 결과

**Gate는 여전히 HOLD**다. 이번 Wave는 Runtime Evidence 없이 닫을 수 있는 정적 항목만 처리했다.

### Closed Static

- Architecture Model JSON Schema 생성
- Architecture Model Validator PASS
- Policy-driven Transaction Timeout 구현 경로 확인
- MyBatis Query Timeout 구현 경로 확인
- Timeout Worker Context 전파/정리 구현 경로 확인

### Confirmed Target Fail / Change Required

- JWT Signing Key = process-local RSA generation
- fixed `kid` / rotation-grace 미구현
- 표준 업무 `*-service` 범위의 Service-level TX 중복 2건

### Runtime/Human Blocking

- Timeout rollback/late commit/connection return
- 500 vs 855 TPS 승인
- Session/HA/DR
- 71 Server→JVM→WAR→Route
- E2E Trace
- Rolling/Rollback
- Migration Cutover
- Critical ADR Sign-off

## 2. Gate 영향

```text
G80  HOLD 유지
HG90 HOLD 유지
```

단, `OPEN-013 Model JSON Schema/Validator`는 이번 Wave에서 해소 가능하다.

## 3. 다음 Wave

```text
Wave 2A — Code/Config Change
  JWT Key Provider / kid rotation
  Service TX duplicate cleanup

Wave 2B — Production Mapping Evidence
  71 Server→JVM→WAR→Route
  GSLB/L4/Apache/Tomcat config

Wave 3 — Runtime Test
  Timeout → Capacity → Hikari/SQL → N-1 → Session/Center → Trace → Rolling → JWT Rotation

Wave 4 — Human ADR / Re-Gate
  G80 재평가 → HG90 Sign-off
```


## 4. Wave 2A Change Specification

상태: **SPEC READY / NOT IMPLEMENTED**

- JWT Signing Key Provider SPI 및 Production External Provider 경계 정의
- Versioned kid / Rotation / JWKS Grace lifecycle 정의
- EB/EP Service 중복 Transaction 제거 변경점 정의
- Timeout Safety Harness + Policy Audit/Enforce Guard 정의

생성문서: `98`, `98A`, `98B`, `98C`, `98D`, `98E`.

Gate는 변경 구현/시험 전이므로 `G80 HOLD / HG90 HOLD` 유지.

## 5. Wave 2A Implementation Candidate

상태: **IMPLEMENTED CANDIDATE / CANONICAL BUILD BLOCKED**

- JWT Key Provider / External Provider 경계 구현 후보 생성
- Versioned kid/JWKS lifecycle 후보 반영
- EB/EP Service 중복 Transaction 제거 후보 반영
- Timeout Policy Validator 실행 PASS
- Root canonical Gradle build 및 실제 KMS/HSM Adapter/Runtime test는 미완료

## 6. Wave 2B Production Mapping

상태: **PARTIAL MAPPING / BLOCKED EVIDENCE**

- 71 Server Master 전수 정규화
- Hostname Unique 71
- WEB 20 / WAS 28 / AP 13 / DB 10
- Actual Apache Route, JVM, WAR, ServiceId Deployment, Datasource는 운영 Config 부족으로 UNKNOWN 유지

## 7. Wave 2C Production Config Evidence Ingestion

상태: **INGESTION READY / PRODUCTION EVIDENCE BLOCKED**

- Production Evidence Manifest JSON Schema 생성
- Apache/Tomcat/setenv/Spring Parser + Validator 생성
- Test-First pytest 11개 PASS
- `znsight-config-info` Canonical Candidate 122개 파싱: PASS 122 / ERROR 0
- Production Acceptance Contract: `Environment + Hostname + Path + SHA256 + Timestamp + PRODUCTION_RUNTIME`
- 현재 Source Repository Config Production Accepted: **0 / 122**
- 이유: Branch/Commit은 있으나 실제 운영 Hostname/Capture Provenance가 결합되어 있지 않음

Gate는 `G80 HOLD / HG90 HOLD` 유지한다.

---

## Wave 3 — Runtime Evidence Harness & Local Preflight

- 12개 Mandatory Run Harness/Template 작성
- Runtime Manifest JSON Schema 작성
- pytest 11건 통과
- Synthetic Evidence Runtime 승격 방지 확인
- Java 21 Timeout Policy Preflight 수행: current default `3/5/5`는 `TX_NOT_LT_ONLINE`
- 실제 Production Runtime Run은 환경 미연결로 0/12
- G80/HG90 HOLD 유지

상세: `98U-P0-WAVE3-RUNTIME-EVIDENCE-EXECUTION.md`

---

## Wave 3B — Runtime Execution Preparation Automation

상태: **AUTOMATION READY / PRODUCTION RUNTIME BLOCKED**

- JMeter property-driven HTTP/JSON load plan 및 Wrapper 생성
- Gatling 동등 시나리오 Reference 생성
- OS/JVM/Micrometer/Oracle Metric collector 준비
- Timeout Client Probe + Oracle Before/After Template 준비
- N-1/Session/Center/Rolling/JWT Rotation Safe Operator Hook 준비
- JMeter JTL → Runtime `summary.json` 자동 정규화
- Operator Hook 기본 `DRY_RUN`, PROD는 `APPROVED:<change-id>` 필수
- TDD pytest 11 PASS, Python/Shell/JMX 구조검증 PASS
- 현재 환경 JMeter/Gatling/SQL*Plus 미설치, 실제 Production Runtime 0/12
- G80/HG90 HOLD 유지

상세: `98AA-P0-WAVE3B-RUNTIME-AUTOMATION.md`
