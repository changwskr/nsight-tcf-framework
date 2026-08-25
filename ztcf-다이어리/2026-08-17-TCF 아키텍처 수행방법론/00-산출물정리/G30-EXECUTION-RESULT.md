# G30 Execution Result — Physical Architecture

## 결과

- STEP 06 Physical Architecture: 완료
- WEB/Apache Physical Baseline: 완료
- WAS/Tomcat JVM Physical Baseline: 완료
- Server Master Inventory Architecture: 완료
- G30: **CONDITIONAL PASS**
- 다음 단계: **G40 Mechanism / Source Conformance**

## 핵심 확정

1. Physical Server Baseline은 71대 Working Baseline을 사용한다.
2. 서버 1대는 Hostname 기준 Master Inventory 1행으로 관리한다.
3. `WAS Server ≠ Tomcat JVM ≠ Application/WAR`로 물리 모델을 분리한다.
4. 구성도 `Container`는 Tomcat JVM Instance로 표준화한다.
5. Apache 1 Instance에서 Multi Listen이 가능하고 포트별 Tomcat Connector로 Routing한다.
6. 운영 WEB/WAS는 Cross Routing 및 동일 Application Peer JVM HA 구조로 정의한다.
7. 개발은 Consolidation, 운영은 Isolation+HA를 기본 방향으로 한다.
8. RDW/ADW, 실시간/배치/ETL은 물리 자원격리를 유지한다.
9. Minimum / Allocated / Capacity Requirement를 분리한다.
10. DR 노드 존재와 DR 용량/RTO/RPO 충족을 분리하여 판정한다.

## G30 Conditional Pass 조건

| ID | 조건 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G30-C01 | 71대 Server↔JVM↔Application/WAR 전수 Mapping | P0 | G40 |
| G30-C02 | Apache Routing Config Evidence | P0 | G70 |
| G30-C03 | Tomcat Instance Config Evidence | P0 | G60 |
| G30-C04 | Application HA Peer JVM Catalog | P0 | G60/G70 |
| G30-C05 | 운영↔DR 전수 Mapping + RTO/RPO | P0 | G70 |
| G30-C06 | 삭제/Review/Appliance Resource 정규화 | P1 | G60 |

## 다음 실행

G40에서는 실제 Source/Framework Mechanism을 기준으로 다음을 연결한다.

```text
HTTP
→ Filter / Interceptor
→ TCF
→ STF
→ Timeout / Transaction
→ Dispatcher
→ Handler
→ Facade
→ Service
→ DAO / Mapper
→ ETF
→ Error / Response
```

그리고 PDMG AS-IS와 NSIGHT TCF Target을 분리하여 Source Conformance를 수행한다.
