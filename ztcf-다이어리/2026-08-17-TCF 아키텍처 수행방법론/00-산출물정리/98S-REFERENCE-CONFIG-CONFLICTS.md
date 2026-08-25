# 98S. Reference Configuration Variant Register

> 이 문서는 `znsight-config-info`에 공존하는 값을 비교한 것이다. Production 승인값 표가 아니다.

| ID | 영역 | 발견값 | 영향 | 처리 |
|---|---|---|---|---|
| CFG-VAR-001 | Tomcat Thread | 500 / 1600 / 3200 | TPS/CPU/Context Switching | Host Runtime Evidence로 확정 |
| CFG-VAR-002 | JVM Heap | Xmx 12g / 48g / 64g / 192g | GC/메모리/VM 자원 | VM 유형별 승인값 필요 |
| CFG-VAR-003 | Java | setenv에 Java 17 경로 | Current Source Java21과 충돌 가능 | Production JAVA_HOME 증적 필요 |
| CFG-VAR-004 | Session | DeltaManager vs Spring Session JDBC | Failover/운영복잡도 | ADR-SES + Runtime Test |
| CFG-VAR-005 | Hikari RDW | 8 / 32 / 50 / 200 등 | DB Session 총량 | WAR/JVM별 운영 Config로 확정 |
| CFG-VAR-006 | Session Timeout | 60m / absolute 480m 등 | 사용자/메모리/보안 | 승인 Session Policy 필요 |
| CFG-VAR-007 | Apache ProxyTimeout | 10 / 30 sec | Deadline Chain | WEB Host Config로 확정 |
| CFG-VAR-008 | Connector | 8080 / placeholder | Runtime Mapping | server.xml+setenv 결합 필요 |
| CFG-VAR-009 | Timeout | DB 3s / TX 5s | Transaction safety | RUN-TIMEOUT과 결합 |

현재 모든 Variant는 `REFERENCE`이며 실제 적용값과 혼합하지 않는다.
