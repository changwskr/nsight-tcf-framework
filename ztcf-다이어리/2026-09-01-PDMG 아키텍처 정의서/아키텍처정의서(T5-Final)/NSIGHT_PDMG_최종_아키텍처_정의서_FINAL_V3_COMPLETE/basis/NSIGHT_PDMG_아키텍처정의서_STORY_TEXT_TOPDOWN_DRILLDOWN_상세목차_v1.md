# NSIGHT PDMG 아키텍처 정의서
# Story + TEXT Architecture + Top-down → Drill-down 상세 목차

> 목차 원칙: **발표스크립트 13장 Story를 그대로 유지한다.**  
> 각 장은 **전체 TEXT Architecture → L0→L5 해부 → Runtime/Failure/Security → 정상/금지 → 의사결정 → PASS/GAP → 다음 장**으로 구성한다.

---

# 전체 Story

```text
01 왜 다시 짓는가
 ↓
02 정보계 패러다임의 전환
 ↓
03 아키텍처 6단계 수립 방법론
 ↓
04 Big Picture
 ↓
05 논리 아키텍처
 ↓
06 물리 아키텍처
 ↓
07 DR 센터 활용 전략
 ↓
08 메커니즘
 ↓
09 런타임 서비스
 ↓
10 데이터플랫폼
 ↓
11 마케팅플랫폼
 ↓
12 BI 포탈
 ↓
13 표준화와 10년 지속 가능성
 ↓
HG90 Evidence-backed Baseline
```

---

# 제1장. 왜 다시 짓는가
## Story: “이미 시스템은 있지만, Architecture는 하나로 보이지 않는다”

### 1.1 장 전체 대표 그림 — 왜 Architecture를 다시 정의하는가
### 1.2 기존 PDMG는 어떻게 보이는가
### 1.3 Inventory와 Architecture의 차이
### 1.4 Module → Responsibility
### 1.5 Source Structure → Runtime Structure
### 1.6 Request Thread / Worker Thread
### 1.7 Timeout / Transaction 경계
### 1.8 Authentication / Authorization / JWT GAP
### 1.9 ServiceId Backbone
### 1.10 Application → Logical → Physical
### 1.11 Capacity / Failure Chain
### 1.12 PDMG Current ↔ NSIGHT Target
### 1.13 Interface Contract
### 1.14 Operations / Runtime Evidence
### 1.15 Architecture Closed Loop
### 1.16 정상 / 금지패턴
### 1.17 주안 / 대안
### 1.18 Current GAP
### 1.19 PASS
### 1.20 제2장 Handoff

---

# 제2장. 정보계 패러다임의 전환
## Story: “저장소와 Application 중심에서 살아 움직이는 Platform Architecture로”

### 2.1 장 전체 전환 그림
### 2.2 기존 Application-centric 정보계
### 2.3 Responsibility-centric 전환
### 2.4 Module → Runtime Boundary
### 2.5 Product → Technical Capability
### 2.6 Online → FAST / DEEP Workload
### 2.7 Server → Logical → Physical
### 2.8 Logging → Runtime Evidence
### 2.9 Document → Closed Loop
### 2.10 PDMG Current ↔ NSIGHT Target
### 2.11 정상 / 금지패턴
### 2.12 주안 / 대안
### 2.13 PASS / GAP
### 2.14 제3장 Handoff

---

# 제3장. 아키텍처 6단계 수립 방법론
## Story: “비전에서 시작해 실제 Runtime 검증까지 내려간다”

### 3.1 6단계 전체 Journey
### 3.2 VISION
### 3.3 BIG PICTURE
### 3.4 LOGICAL
### 3.5 PHYSICAL
### 3.6 MECHANISM
### 3.7 RUNTIME
### 3.8 Evidence / Gate
### 3.9 단계간 Input / Output
### 3.10 Top-down + Bottom-up
### 3.11 정상 / 금지패턴
### 3.12 Architecture Gate
### 3.13 PASS / GAP
### 3.14 제4장 Handoff

---

# 제4장. Big Picture
## Story: “화려한 박스가 아니라 책임과 경계를 먼저 본다”

### 4.1 PDMG 전체 System Context
### 4.2 User / Channel Boundary
### 4.3 UI Delivery Boundary
### 4.4 Authentication Boundary
### 4.5 Application Runtime Boundary
### 4.6 Framework / Business Boundary
### 4.7 Data Boundary
### 4.8 External Integration Boundary
### 4.9 Security Cross-cutting
### 4.10 Observability Cross-cutting
### 4.11 Allowed Path
### 4.12 Forbidden Path
### 4.13 Current GAP
### 4.14 주안 / 대안
### 4.15 PASS
### 4.16 제5장 Handoff

---

# 제5장. 논리 아키텍처
## Story: “기술을 고르기 전에 무엇을 분리하고 허용할지 정한다”

### 5.1 장 전체 Logical Architecture
### 5.2 Application → Technical Capability
### 5.3 Logical Node Catalog
### 5.4 UI Delivery Node
### 5.5 Authentication Node
### 5.6 Application Runtime Node
### 5.7 Framework Runtime Capability
### 5.8 Data Access Capability
### 5.9 Data Service Node
### 5.10 Integration Node
### 5.11 Operations Node
### 5.12 State Model
### 5.13 Scale Unit
### 5.14 Failure Domain
### 5.15 Security Boundary
### 5.16 Allowed / Forbidden Path
### 5.17 Logical → Physical Handoff
### 5.18 주안 / 대안
### 5.19 PASS / GAP
### 5.20 제6장 Handoff

---

# 제6장. 물리 아키텍처
## Story: “속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다”

### 6.1 장 전체 Physical Architecture
### 6.2 Logical → Physical Mapping
### 6.3 Environment / Center
### 6.4 GSLB / L4
### 6.5 WEB / Apache
### 6.6 WAS / Tomcat
### 6.7 Server / VM / JVM / WAR
### 6.8 JVM Group / WAR Isolation
### 6.9 Data / DB
### 6.10 Network / Port / Firewall
### 6.11 Storage / Filesystem
### 6.12 Monitoring / Backup
### 6.13 Capacity Candidate
### 6.14 Physical Traceability
### 6.15 HA Handoff
### 6.16 주안 / 대안
### 6.17 PASS / GAP
### 6.18 제7장 Handoff

---

# 제7장. DR 센터 활용 전략
## Story: “완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다”

### 7.1 장 전체 DR Architecture
### 7.2 HA와 DR의 차이
### 7.3 Main Center Local HA
### 7.4 Center Failure
### 7.5 Detection / Isolation
### 7.6 Traffic Reroute
### 7.7 DR WEB / WAS
### 7.8 Artifact / Config / Key
### 7.9 DB Consistency
### 7.10 AP Active-Active vs DB Active-Active
### 7.11 RTO / RPO
### 7.12 Backup / Restore
### 7.13 Failover
### 7.14 Failback
### 7.15 Business Validation
### 7.16 DR Test
### 7.17 주안 / 대안
### 7.18 PASS / GAP
### 7.19 제8장 Handoff

---

# 제8장. 메커니즘
## Story: “시스템은 서버가 아니라 표준과 실행규칙으로 움직인다”

### 8.1 장 전체 Mechanism Architecture
### 8.2 Framework vs Business
### 8.3 DefaultFilter
### 8.4 Header / GUID
### 8.5 ServiceContext
### 8.6 SecurityFilterChain
### 8.7 DispatcherServlet / Interceptor
### 8.8 ServiceId Resolution
### 8.9 TCF
### 8.10 TransactionDispatcher
### 8.11 Handler Registry
### 8.12 Worker / Timeout
### 8.13 TransactionTemplate
### 8.14 Context Propagation
### 8.15 Error Taxonomy
### 8.16 Response Envelope
### 8.17 Logging / ImageLog
### 8.18 TCF OFF
### 8.19 Normal / Forbidden
### 8.20 주안 / 대안
### 8.21 PASS / GAP
### 8.22 제9장 Handoff

---

# 제9장. 런타임 서비스
## Story: “정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다”

### 9.1 End-to-End Runtime
### 9.2 Request Thread
### 9.3 Worker Thread
### 9.4 Context Install
### 9.5 Transaction BEGIN
### 9.6 ServiceId Routing
### 9.7 Handler / Facade / Service
### 9.8 DAO / Mapper / JDBC
### 9.9 DB Session
### 9.10 Success Response
### 9.11 Known Error
### 9.12 Timeout
### 9.13 Overload
### 9.14 Late Worker
### 9.15 TCF OFF Runtime
### 9.16 FAST / DEEP
### 9.17 Runtime Evidence
### 9.18 Failure Matrix
### 9.19 Normal / Forbidden
### 9.20 주안 / 대안
### 9.21 PASS / GAP
### 9.22 제10장 Handoff

---

# 제10장. 데이터플랫폼
## Story: “RDW는 실시간을 지키고 ADW는 분석을 극대화한다”

### 10.1 장 전체 Data Platform
### 10.2 Data Architecture vs Database
### 10.3 PDMG Current Data Access
### 10.4 Data Ownership
### 10.5 RDW
### 10.6 ADW
### 10.7 Datasource / Transaction
### 10.8 DAO / Mapper / SqlId
### 10.9 Table / View
### 10.10 Read / Write Boundary
### 10.11 CDC
### 10.12 ETL
### 10.13 Metadata
### 10.14 Lineage
### 10.15 Data Quality
### 10.16 Data Security
### 10.17 Workload Isolation
### 10.18 Runtime Evidence
### 10.19 주안 / 대안
### 10.20 PASS / GAP
### 10.21 제11장 Handoff

---

# 제11장. 마케팅플랫폼
## Story: “배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로”

### 11.1 장 전체 Marketing Platform
### 11.2 PDMG Runtime Reference
### 11.3 Application / Business Code
### 11.4 Program ID
### 11.5 ServiceId
### 11.6 Handler / Facade / Service
### 11.7 Customer Context
### 11.8 RDW / Data Access
### 11.9 External Interface
### 11.10 Event / Kafka Target
### 11.11 Real-time Decision Target
### 11.12 Security / Authorization
### 11.13 MP ↔ mg Mapping
### 11.14 Runtime Evidence
### 11.15 Normal / Forbidden
### 11.16 주안 / 대안
### 11.17 PASS / GAP
### 11.18 제12장 Handoff

---

# 제12장. BI 포탈
## Story: “데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다”

### 12.1 장 전체 BI Architecture
### 12.2 BI Application Boundary
### 12.3 Operational vs Analytical
### 12.4 RDW → ADW
### 12.5 Data Contract
### 12.6 Dataset
### 12.7 Report
### 12.8 Self-BI
### 12.9 AI / Natural Language Analysis
### 12.10 BI Security
### 12.11 Row / Column Access
### 12.12 Freshness SLA
### 12.13 Performance Isolation
### 12.14 Data Governance
### 12.15 Normal / Forbidden
### 12.16 주안 / 대안
### 12.17 PASS / GAP
### 12.18 제13장 Handoff

---

# 제13장. 표준화와 10년 지속 가능성
## Story: “좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다”

### 13.1 장 전체 Governance / Closed Loop
### 13.2 Architecture Drift
### 13.3 Naming
### 13.4 Program / ServiceId
### 13.5 Package / Mapper
### 13.6 Development Rule
### 13.7 Static Scanner
### 13.8 CI Gate
### 13.9 Build Once
### 13.10 Immutable Artifact
### 13.11 Config / Secret
### 13.12 DeploymentId
### 13.13 Host / JVM Trace
### 13.14 Metric / Log / Trace
### 13.15 Runtime Evidence
### 13.16 Drift Detection
### 13.17 GAP / ADR
### 13.18 G00 → HG90
### 13.19 Architecture Baseline Release
### 13.20 Final Critical GAP
### 13.21 최종 PASS 조건

---

# 최종 통합부

## A. 13장 Story Master Map
## B. PDMG Integrated Architecture
## C. Current vs Target Overlay
## D. Security Overlay
## E. Data / Interface Overlay
## F. Physical / HA / DR Overlay
## G. Runtime / Failure Overlay
## H. Naming / Traceability Overlay
## I. PASS / GAP Heatmap
## J. Critical ADR
## K. Runtime Evidence Gate
## L. HG90 Final Baseline
