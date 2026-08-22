---
document-status: PROPOSED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# PDMG REFERENCE RULES


| Rule ID | 후보 규칙 | 근거 Source | 상태 |
|---|---|---|---|
| RR-STRUCT-001 | 업무 기본 경로는 Handler→Facade→Service→DAO | pdmg-service, pdmg-jwt | CANDIDATE |
| RR-SID-001 | ServiceId는 Scope 내 Unique | UI Catalog + Handler routing | CANDIDATE |
| RR-FW-001 | Timeout ON은 Worker Pool 실행기를 사용 | pdmg-fw `DefaultOnlineTimeoutExecutor` | AS-IS CANDIDATE |
| RR-TX-001 | Timeout Worker가 `TransactionTemplate`로 외곽 TX를 생성 | pdmg-fw | AS-IS CANDIDATE |
| RR-JWT-001 | JWT는 RSA/RS256 및 공개 JWKS 구조 | pdmg-jwt | AS-IS CANDIDATE |
| RR-BUILD-001 | Java 21 / Spring Boot 3.5.14 / Gradle 8.10.1 | 4개 Gradle Root | HIGH CONFIDENCE CANDIDATE |
| RR-DEPLOY-001 | 업무/JWT는 외부 Tomcat WAR, Framework는 Library JAR | build.gradle | CANDIDATE |

`RR-TX-001`은 `pdmg-service`의 Facade/Service `@Transactional`과 중첩될 수 있으므로 Reference Reconciliation 대상이다.
