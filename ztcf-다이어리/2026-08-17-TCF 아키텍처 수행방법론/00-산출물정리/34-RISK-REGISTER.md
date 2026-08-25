# NSIGHT Risk Register — G80

| Risk ID | Risk | Probability | Impact | Trigger | Mitigation | Contingency |
|---|---|---|---|---|---|---|
| RISK-001 | JWT process-local signing key로 다중 노드/재기동 후 Token 검증 실패 | HIGH | CRITICAL | Issuer 재기동/Scale-out | KMS/HSM SoT, versioned kid | 긴급 Key Rollback/재로그인 |
| RISK-002 | Timeout 응답 후 DB Late Commit | MEDIUM | CRITICAL | Worker cancel과 DB TX 분리 | Fault injection + TX owner 정리 | 보상/거래차단 |
| RISK-003 | 2+2 구조 센터장애 후 추가노드 장애 시 용량부족 | MEDIUM | HIGH | Center Failure 직후 Peak | 3+3/8Core scale-out 검토 | Traffic throttling/우회 |
| RISK-004 | Hikari 과대 설정으로 DB Session 폭증 | MEDIUM | HIGH | Peak/Slow SQL | Hold-Time 기반 Pool 검증 | Pool 제한/DB 보호 |
| RISK-005 | Session 전략 미확정으로 장애 시 대량 재로그인/복제부하 | MEDIUM | HIGH | WAS/Center failover | Session ADR + 시험 | 재로그인 정책/우회 |
| RISK-006 | Server-JVM-WAR 매핑 미완료로 배포/장애 영향 오판 | HIGH | HIGH | 배포/장애 | Runtime Inventory | 변경동결/수동 검증 |
| RISK-007 | Runtime Evidence 부재로 잘못된 Gate PASS | HIGH | CRITICAL | 오픈 승인 | GOV-003 강제 | HG90 HOLD |
| RISK-008 | System.out 기반 TCF trace로 운영 로그 상관/제어 저하 | MEDIUM | MEDIUM | 고부하/장애 | SLF4J/MDC 통합 | 운영 profile에서 출력차단 |
| RISK-009 | Domain/Table Ownership 미정으로 직접 DB 결합 재발 | MEDIUM | HIGH | 개발 확장 | Data Ownership Gate | SQL review 차단 |
| RISK-010 | CI/CD rollback·DB/config 호환 증적 부족 | MEDIUM | HIGH | 운영 배포 실패 | Rollback rehearsal | 수동 rollback/runbook |
