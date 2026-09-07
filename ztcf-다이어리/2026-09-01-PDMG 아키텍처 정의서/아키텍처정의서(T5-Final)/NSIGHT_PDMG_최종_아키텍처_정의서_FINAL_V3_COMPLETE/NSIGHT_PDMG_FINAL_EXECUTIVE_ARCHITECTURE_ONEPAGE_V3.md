# NSIGHT / PDMG Executive Architecture One-page

```text
User / Channel
 ↓
pdmg-ui
 ↓
pdmg-jwt
 ↓
pdmg-service + pdmg-fw
 ↓
Handler → Facade → Service → DAO → Mapper
 ↓
RDW / DB

NSIGHT Target
RDW → ADW → BI
Event / CDC / ETL / Standard Interface

Physical
GSLB → L4 → Apache → Tomcat/JVM → WAR → DB

Evidence
SourceCommit → ArtifactHash → DeploymentId
→ ServiceId → GUID → Runtime Evidence
→ GAP / ADR → Baseline
```

## 핵심 원칙

- PDMG Current와 NSIGHT Target을 분리한다.
- Module/Process/JVM/Server를 구분한다.
- Framework는 실행정책, Business는 업무를 책임진다.
- ServiceId를 Business→Source→Runtime Trace의 중심축으로 사용한다.
- HTTP Timeout과 Worker/JDBC/DB 종료를 동일시하지 않는다.
- Cross-system Direct DB DML을 기본 금지한다.
- RDW와 ADW Workload를 분리한다.
- Architecture PASS와 Current Implementation PASS를 분리한다.
- Final HG90는 Runtime/DR Evidence가 닫힌 뒤 Release한다.

## Critical GAP

```text
JWT RS256 ↔ HMAC
Identity Binding
Artifact → JVM → Host
JDBC Timeout / Cancel
External Interface Inventory
RDW/ADW Ownership / Lineage
pdmg-om Scope
RTO / RPO / DR Evidence
Runtime Evidence Automation
```
