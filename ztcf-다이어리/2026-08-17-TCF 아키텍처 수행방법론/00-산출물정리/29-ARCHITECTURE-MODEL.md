# NSIGHT Architecture Model — G80 Draft

## 1. 목적

문서의 Architecture를 `Document → Model → Code`로 연결하기 위해 구조화 모델을 정의한다. 현재 모델은 **PARTIAL**이다. Source에서 자동 추출 가능한 ServiceId/Component 관계는 실제 ZIP을 스캔했으며, Requirement/Screen/Table Ownership/Server-JVM/Runtime Evidence 관계는 후속 Evidence가 필요하다.

## 2. Target Meta Model

```text
Requirement
  ↓ realizes
BusinessDomain / Application
  ↓ owns
ServiceId
  ↓ routesTo
Handler
  ↓ invokes
Facade
  ↓ invokes
Service
  ├─ accesses → DAO → Mapper → SQL → Table
  └─ calls    → IntegrationClient → Target ServiceId

ServiceId
  ├─ governedBy → TimeoutPolicy / SecurityPolicy
  ├─ deployedOn → Application/WAR
  └─ evidencedBy → TestRun / Log / Metric

Application/WAR → runsOn → TomcatJVM → hostedBy → Server
```

## 3. Source Extracted Model 현황

| Model 항목 | 수량 | 상태 |
|---|---|---|
| Node | 380 | Source 추출 |
| Edge | 380 | Source 추출 |
| Handler | 59 | Source 추출 |
| Facade | 50 | Source 추출 |
| Service | 90 | Source 추출 |
| DAO | 32 | Source 추출 |
| Mapper Interface | 29 | Source 추출 |
| ServiceId Mapping | 121 | Source 추출 |
| Target Mapper XML | 28 | Source 추출 |

## 4. 현재 닫힌 관계

```text
ServiceId → Handler           상당부분 자동 추출
Handler → Facade              자동 추출
Facade → Service              자동 추출
Service → DAO / Client        자동 추출
DAO → Mapper                  부분 자동 추출
```

## 5. 아직 열려 있는 관계

| 관계 | 상태 | 필요 Evidence |
|---|---|---|
| Requirement → Screen → ServiceId | OPEN | 요구사항/화면/Transaction Catalog 전수 Index |
| Mapper/SQL → Table Ownership | OPEN | SQL Parser + Data Catalog |
| Application/WAR → Tomcat JVM | OPEN | 배포목록 + server.xml/CATALINA_BASE |
| Tomcat JVM → Server | OPEN | 71대 Inventory + Runtime Process/Port |
| ServiceId → Timeout/Security Policy | OPEN | OM Policy Catalog |
| ServiceId → Runtime Evidence | OPEN | Load/Failover/Trace Run Registry |
| Domain → Owned Table/View | OPEN | Data Ownership Catalog |

## 6. Model SoT 원칙

`29-ARCHITECTURE-MODEL-DRAFT.json`은 승인 전 Draft이며, HG90 승인 후에만 Current Model SoT로 승격한다.
