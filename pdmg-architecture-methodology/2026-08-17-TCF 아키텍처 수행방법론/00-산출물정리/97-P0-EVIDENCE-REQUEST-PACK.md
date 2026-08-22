# P0 Evidence Request Pack

> 다음 Wave에서 사용자가/운영팀이 제공하면 즉시 Gate를 닫을 수 있는 실제 증적 목록.

| 우선 | 증적 | 목적 | Gate |
|---:|---|---|---|
| 1 | 운영 `httpd.conf` / conf.d | Apache→Tomcat 실제 Route | G30/G70/G80 |
| 2 | WAS별 `server.xml`, `setenv.sh`, CATALINA_BASE 목록 | 71 Server→JVM→WAR | G30/G80 |
| 3 | L4/GSLB Pool/Health/Sticky 설정 | N-1/센터 장애 | G70/G80 |
| 4 | 운영 application.yml/properties | Session/TX/Timeout/Hikari 실제값 | G60/G80 |
| 5 | JWT KMS/HSM 연계 설계/설정 | Signing Key SoT | G50/G80 |
| 6 | 성능시험 원본(JMeter/APM/DB) | 500/855 Runtime 승인 | G60/G80 |
| 7 | HA/DR 시험 결과 | RTO/RPO/Failback | G70/G80 |
| 8 | 배포 Pipeline 실행/rollback 로그 | Rolling/Rollback | G70/G80 |
| 9 | Migration reconciliation 결과 | Cutover readiness | G70/G80 |
| 10 | E2E 로그 Bundle | GUID+ServiceId trace | G70/G80 |

증적을 받을 때 파일명만으로 CURRENT를 판단하지 않고 Environment/Hostname/Version/Timestamp와 함께 등록한다.
