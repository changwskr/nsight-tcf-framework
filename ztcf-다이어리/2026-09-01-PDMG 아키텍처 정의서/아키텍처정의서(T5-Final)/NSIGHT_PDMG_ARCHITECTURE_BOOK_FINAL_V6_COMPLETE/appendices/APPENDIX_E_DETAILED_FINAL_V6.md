# APPENDIX E. Physical Inventory — Detailed Final

### E.1 HW Inventory

| No | 영역 | HW Role | 자원유형 | 센터 | 환경 | 논리노드 | CPU | Memory | Storage | NIC/Network | HA 구성 | DR Pair | 상태 | 비고 |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| 1 | Traffic | GSLB | Network Appliance/Service | MAIN/DR | PROD | Global Traffic | [OPEN] | [OPEN] | N/A | External/Center | HA 정책 [OPEN] | DR Site | [BASELINE] | DNS/Health/TTL 상세 미확정 |
| 2 | Traffic | L4 | Load Balancer | MAIN/DR | PROD | Load Balancing | [OPEN] | [OPEN] | N/A | WEB/WAS Network | Pair/N+1 [OPEN] | DR L4 | [BASELINE] | VIP/Pool/Member Inventory 필요 |
| 3 | WEB | WEB Node | VM/Physical | MAIN | PROD | WEB | [OPEN] | [OPEN] | [OPEN] | WEB Zone | 다중화/N+1 | DR WEB | [BASELINE] | Apache Instance와 Host 분리 |
| 4 | WAS | Marketing WAS Node | VM | MAIN | PROD | WAS | 16C 또는 32C 후보 | 128G 또는 256G 후보 | [OPEN] | WAS Zone | N+1 | DR WAS | [CANDIDATE] | 정확한 승인 사이징은 Load Test 후 |
| 5 | DB | RDW DB Node | DB Appliance/Cluster | MAIN | PROD | RDW | [OPEN] | [OPEN] | [OPEN] | DB/Data Network | Cluster/RAC [OPEN] | DR RDW | [BASELINE] | 노드 수/사양 DB Inventory로 확정 |
| 6 | DB | ADW DB Node | DB Appliance/Cluster | MAIN | PROD | ADW | [OPEN] | [OPEN] | [OPEN] | DB/Data Network | Cluster/RAC [OPEN] | DR ADW | [BASELINE] | Heavy analytical workload 분리 |
| 7 | Event | Broker Node | VM/Physical | MAIN | PROD | Event Platform | [OPEN] | [OPEN] | [OPEN] | Integration Network | Cluster | DR/Event Recovery [OPEN] | [PROPOSED] | Kafka 등 제품 승인 필요 |
| 8 | CDC | CDC Relay Node | VM/Physical | MAIN | PROD | CDC | [OPEN] | [OPEN] | [OPEN] | Data Network | Failover/Checkpoint | DR CDC | [PROPOSED] | Capture/Relay/Apply topology 확정 필요 |
| 9 | ETL | ETL/Batch Node | VM/Physical | MAIN | PROD | ETL/Batch | [OPEN] | [OPEN] | [OPEN] | Data Network | Restart/Failover | DR ETL | [BASELINE] | Online 자원과 분리 |
| 10 | File | MFT/FOS Node | Appliance/VM | MAIN | PROD | File Transfer | [OPEN] | [OPEN] | [OPEN] | File/DMZ/Internal | HA [OPEN] | DR MFT | [BASELINE] | Landing/Archive Storage 포함 |
| 11 | Ops | Monitoring/OM Node | VM/Platform | MAIN | PROD | Operations | [OPEN] | [OPEN] | [OPEN] | Management Network | HA [OPEN] | DR Ops | [OPEN] | pdmg-om 실제 구성 미확인 |
| 12 | Storage | Backup/Archive | Storage/Backup Appliance | MAIN/DR | PROD | Backup | [OPEN] | [OPEN] | [OPEN] | Backup Network | Redundant Storage | Offsite/DR | [BASELINE] | Restore Test 필수 |

### E.2 SW / Runtime Inventory

| No | Domain | Capability | Software/Product | Version/Config | 적용 Runtime | 적용 HW Role | 상태 | Architecture 역할 | 주요 확인/Gap |
|---|---|---|---|---|---|---|---|---|---|
| 1 | Runtime | Java Runtime | JDK / Java | 21 | PDMG WAS/JWT | WAS/JWT Node | [AS-IS] | JVM 실행환경 | PDMG Build/Runtime Reference |
| 2 | Runtime | Application Framework | Spring Boot | 3.5.14 | PDMG Service/JWT | WAS/JWT Node | [AS-IS] | Application Runtime | PDMG Source 기준 |
| 3 | WEB | Web Server | Apache HTTP Server | [OPEN] | WEB | WEB Node | [BASELINE] | Reverse Proxy/Static/HTTP Entry | 제품/버전 승인 필요 |
| 4 | WAS | Servlet Container | Apache Tomcat | [OPEN] | WAS | WAS Node | [BASELINE] | WAR/JVM Runtime | 제품/버전 승인 필요 |
| 5 | Framework | Transaction Control | Spring Transaction / TransactionTemplate | Framework config | PDMG Service | WAS Node | [AS-IS] | Worker TX Boundary | Physical TX owner 검증 필요 |
| 6 | Framework | Timeout Control | OnlineTimeoutExecutor | 5000ms, pool 20, queue 100 | PDMG Service | WAS Node | [AS-IS] | Worker isolation/deadline | JDBC cancel 보장 아님 |
| 7 | Data Access | Connection Pool | HikariCP | Exact version [OPEN] | Business Runtime | WAS Node | [AS-IS] | DB Connection Pool | Target pool size 별도 승인 |
| 8 | Data Access | SQL Mapping | MyBatis | Exact version [OPEN] | Business Runtime | WAS Node | [AS-IS] | DAO→Mapper→SQL | Namespace/SqlId trace 중요 |
| 9 | Database | RDBMS | Oracle Database | [OPEN] | RDW/ADW | DB Node | [BASELINE] | Operational/Analytical DB | 버전/Edition/Node 수 OPEN |
| 10 | Event | Event Broker | Kafka / Event Broker | [OPEN] | Event | Broker Node | [PROPOSED] | Async Event | 제품 최종 선정 필요 |
| 11 | CDC | CDC Platform | Oracle GoldenGate/CDC 계열 | [OPEN] | CDC | CDC Node | [PROPOSED] | Capture/Relay/Apply | 제품/버전 및 SLA 확정 필요 |
| 12 | ETL | ETL Engine | ETL Platform | [OPEN] | ETL | ETL Node | [BASELINE] | Bulk transform/load | 제품명 미확정 |
| 13 | File | Managed File Transfer | MFT / FOS | [OPEN] | File | MFT/FOS Node | [BASELINE] | File transfer/landing | 제품명 미확정 |
| 14 | Security | Security Framework | Spring Security | [OPEN] | PDMG Service/JWT | WAS/JWT Node | [AS-IS] | SecurityFilterChain/JWT | Business authorization 별도 |
| 15 | Security | JWT Issuer | RS256 JWT + JWKS | Current source | pdmg-jwt | JWT Node | [AS-IS] | Token issue/JWKS | Key lifecycle/HA 보완 |
| 16 | Security | JWT Verifier | HMAC jwt.secret path | Current source | pdmg-fw | WAS Node | [GAP] | Business-side verification | RS256 issuer와 정합성 GAP |
| 17 | Build | Build Tool | Gradle | Multi-project | DEV/CI | Build Node | [AS-IS] | Build/WAR | PDMG Build Reference |
| 18 | SCM | Source Control | GitLab | [OPEN] | DEV/CI | SCM Node | [BASELINE] | Source/Change History | Version/HA 상세 OPEN |
| 19 | CI | CI Runner | GitLab Runner / Jenkins 후보 | [OPEN] | DEV/TEST | Build Node | [PROPOSED] | Build/Test Delivery | 최종 CI 도구구성 확인 필요 |
| 20 | Deploy | Production Change/Deploy | eCAMS 전략 | [OPEN] | PROD/DR | Deploy Node | [PROPOSED] | Production deployment/change control | 구현 상세 OPEN |
| 21 | Observability | Monitoring/APM | Monitoring/APM Platform | [OPEN] | All Runtime | Monitoring Node | [BASELINE] | Metric/Trace/Dashboard | 제품/수집범위 OPEN |
| 22 | Logging | Central Logging | Logging Platform | [OPEN] | All Runtime | Monitoring/Storage | [BASELINE] | GUID/ServiceId correlation | 민감정보 Mask 필요 |
| 23 | Backup | Backup Software | Backup Platform | [OPEN] | DB/File/Config | Backup Node | [BASELINE] | Backup/Restore | Restore evidence 필요 |

### E.3 Capacity Candidate

| 구분 | Option | 노드수 | vCPU/Node | Memory/Node(GB) | 총 vCPU | 총 Memory(GB) | 상태 | 비고 |
|---|---|---|---|---|---|---|---|---|
| WAS | Option-1 | 4 | 32 | 256 | 128 | 1024 | [CANDIDATE] | 대형 VM 4대 |
| WAS | Option-2 | 8 | 16 | 128 | 128 | 1024 | [CANDIDATE] | 중형 VM 8대 |
| WAS | Option-3 | 8 | 16 | 128 | 128 | 1024 | [CANDIDATE] | 16C/128G ×4 ×2 업무그룹 후보 |
|  |  |  |  |  |  |  |  |  |
|  |  |  |  |  |  |  |  |  |
| Runtime Item | Current/Candidate | Value | Unit | 상태 | 의미 | Target 확정방법 | 비고 |  |
| Tomcat maxThreads | Candidate | 1,200~1,500 | threads | [CANDIDATE] | 32C/256G 후보 기준 과거 산정 | Load Test | 승인값 아님 |  |
| Tomcat Busy | Target | ≤70% | ratio | [CANDIDATE] | Headroom | Performance Test | 승인 필요 |  |
| Hikari General | Candidate | 120~160 | connections | [CANDIDATE] | 일반 업무 후보 | DB/Load Test | 승인 필요 |  |
| Hikari SV | Candidate | 100~120 | connections | [CANDIDATE] | SV 후보 | DB/Load Test | 승인 필요 |  |
| JVM Heap | Candidate | 32~48 | GB | [CANDIDATE] | G1GC 후보 | GC/Load Test | Native memory 포함 검증 |  |
| PDMG Worker | AS-IS | 20 | threads | [AS-IS] | 현재 worker pool | Source/Runtime | Target 아님 |  |
| PDMG Queue | AS-IS | 100 | tasks | [AS-IS] | 현재 queue capacity | Source/Runtime | Target 아님 |  |
| PDMG Deadline | AS-IS | 5000 | ms | [AS-IS] | 현재 worker deadline | Source/Runtime | Target SLA 아님 |  |

### E.4 Physical Architecture Rule

```text
Logical Node
 ↓
Center
 ↓
Host / VM
 ↓
OS
 ↓
JVM / Process
 ↓
WAR / Artifact
 ↓
Port / Datasource
 ↓
Runtime Evidence
```

### E.5 Current GAP

- 실제 Hostname/IP/Port/VM/JVM/WAR 전수 Mapping `[OPEN]`.
- Apache/Tomcat/Oracle 정확한 제품/버전 `[OPEN]`.
- WAS 16C/128G 및 32C/256G는 `[CANDIDATE]`, Production Fact가 아니다.
- ArtifactHash / DeploymentId → JVM / Host Trace 미완료.
