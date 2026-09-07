# NSIGHT / PDMG 아키텍처 정의서
# 별첨 C. INFRASTRUCTURE ARCHITECTURE DEFINITION
## Center / Compute / Network / WEB-WAS / DB / Storage / Security / HA-DR / Operations
## Visual-First / Physical Resource + Runtime Mapping / Standalone Appendix

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-INFRA-APPENDIX-C`  
> 문서 유형: **별첨 / 독립 Infrastructure Architecture 정의서**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 연계 장: `04 PHYSICAL`, `06 RUNTIME`, `09 OPERATIONS`, `10 BASELINE`, `11 DEVELOPMENT STANDARD`  
> 연계 별첨: `별첨 A Application Architecture`, `별첨 B Technical Architecture`

---

# 0. 이 별첨의 목적

## FIG-INF-01. Infrastructure Architecture가 답해야 하는 질문

```text
Application / Technical Architecture
                ↓
어느 Center / Environment에 배치할 것인가?
                ↓
어떤 Compute Resource를 사용할 것인가?
                ↓
어떤 Network Path를 사용할 것인가?
                ↓
WEB / WAS / JVM / DB를 어떻게 배치할 것인가?
                ↓
Storage / File / Backup을 어떻게 구성할 것인가?
                ↓
어떻게 이중화·확장·복구할 것인가?
                ↓
Port / Account / Filesystem / Hostname을 어떻게 표준화할 것인가?
                ↓
실제 Runtime Inventory와 어떻게 추적할 것인가?
```

> **Infrastructure Architecture는 Technical Architecture에서 정의한 Logical Node와 Runtime Platform을 실제 Center·Network·Compute·Storage·Database·Security·Operations Resource에 매핑하고, 용량·이중화·DR·표준·Inventory와 Runtime Evidence까지 정의하는 Architecture 영역이다.**

---

# 1. Infrastructure Architecture 한 문장 정의

## FIG-INF-02. Definition

```text
Logical Technical Node
        ↓
Environment / Center
        ↓
Compute / Network / Storage
        ↓
Runtime Platform
        ↓
Application Artifact
        ↓
HA / DR / Operations
        ↓
Physical Runtime Evidence
```

---

# 2. Infrastructure Architecture가 아닌 것

## FIG-INF-03. Not Infrastructure Architecture

```text
Infrastructure Architecture
≠ 서버 사양표

Infrastructure Architecture
≠ 네트워크 포트 목록

Infrastructure Architecture
≠ 장비 구매목록

Infrastructure Architecture
≠ VM 목록

Infrastructure Architecture
≠ 구성도 한 장
```

이들은 모두 Infrastructure Architecture의 **하위 Inventory 또는 구현증적**이다.

---

# 3. Technical Architecture와 Infrastructure Architecture의 관계

## FIG-INF-04. TA → IA

```text
별첨 B
TECHNICAL ARCHITECTURE
│
├─ Technical Principle
├─ Zone / Logical Node
├─ Technology Component
├─ Runtime Platform
└─ NFR
        ↓
별첨 C
INFRASTRUCTURE ARCHITECTURE
│
├─ Center
├─ Network
├─ Compute / VM
├─ WEB / WAS / JVM
├─ DB / Storage
├─ Security Infra
├─ Backup / DR
└─ Physical Inventory
```

### 핵심

```text
Technical Architecture
= 어떤 기술구조가 필요한가?

Infrastructure Architecture
= 그 기술구조를 실제 어떤 Resource로 구현하는가?
```

---

# 4. Infrastructure Architecture 12대 축

## FIG-INF-05. Twelve Axes

```text
1. Center
2. Environment
3. Compute / Virtualization
4. Network / Traffic
5. WEB / WAS / JVM
6. Database / Data Appliance
7. Storage / Filesystem
8. Integration Infrastructure
9. Security Infrastructure
10. Monitoring / Backup
11. Capacity / HA / DR
12. Inventory / Evidence
```

---

# 5. Center Architecture

## FIG-INF-06. Center Model

```text
MAIN CENTER
  ↓
Normal Production Service

DR CENTER
  ↓
Disaster Recovery Service
```

Current NSIGHT Physical Baseline에서는 주센터와 DR센터를 분리하여 다룬다.

---

# 6. Center와 Environment 구분

## FIG-INF-07. Center ≠ Environment

```text
Center
= 물리 위치

Environment
= 개발 / 테스트 / 운영 / DR 목적
```

```text
Center
≠
Environment
```

---

# 7. Environment Architecture

## FIG-INF-08. Environment Set

```text
Development
    ↓
Test / Verification
    ↓
Production
    ↓
DR
```

선도/이행/교육 환경은 별도 Baseline으로 관리할 수 있다.

---

# 8. Environment Isolation

## FIG-INF-09. Isolation

```text
DEV Compute / Network / Credential
             ≠
PROD Compute / Network / Credential
```

### MUST

환경별 Resource와 Credential을 분리한다.

---

# 9. Environment Mapping

## FIG-INF-10. Logical to Environment

```text
Logical Node
  ↓
Environment
  ↓
Center
  ↓
Physical Resource
```

---

# 10. Compute Architecture

## FIG-INF-11. Compute Model

```text
Physical Server / Appliance
        ↓
Virtualization / VM
        ↓
OS
        ↓
Runtime Process
        ↓
JVM / Application
```

---

# 11. Compute Resource Type

## FIG-INF-12. Resource Types

```text
Compute
│
├─ Physical Server
├─ Virtual Machine
├─ Dedicated Appliance
└─ Container / Cloud Runtime [if approved]
```

정확한 적용방식은 시스템/워크로드별 Baseline으로 확정한다.

---

# 12. VM과 Application의 관계

## FIG-INF-13. VM Boundary

```text
VM
│
├─ OS
├─ Runtime
├─ Process
└─ Application Artifact
```

```text
VM
≠ Application
```

---

# 13. VM과 JVM의 관계

## FIG-INF-14. VM / JVM

```text
VM
 ├─ JVM #1
 │    └─ WAR A
 └─ JVM #2 [if configured]
      └─ WAR B
```

```text
VM
≠ JVM
```

---

# 14. JVM과 WAR의 관계

## FIG-INF-15. JVM / WAR

```text
JVM
  ↓
Tomcat
  ↓
WAR / Context
```

```text
JVM
≠ WAR
```

---

# 15. Physical Server / VM / JVM / WAR

## FIG-INF-16. Four-level Mapping

```text
Physical Server
      ↓
VM
      ↓
JVM
      ↓
WAR
```

Infrastructure Inventory는 이 4단계를 전수 추적 가능해야 한다.

---

# 16. Compute Sizing

## FIG-INF-17. Sizing Inputs

```text
TPS / User
   ↓
Concurrency
   ↓
Thread
   ↓
Connection
   ↓
CPU / Memory
   ↓
VM Size
   ↓
Server Count
```

---

# 17. Candidate Sizing vs Approved Sizing

## FIG-INF-18. Sizing State

```text
Assumption
  ↓
Candidate
  ↓
Load Test
  ↓
Measured Result
  ↓
Approved Sizing
```

---

# 18. VM Sizing Variant

대표 Candidate는 과거 분석에서 여러 Variant가 존재했다.

```text
8C / 32G
16C / 64G
16C / 128G
32C / 256G
```

### 주의

```text
Candidate
≠ Approved Production Fact
```

---

# 19. Scale-up

## FIG-INF-19. Scale-up

```text
VM
  ↓
CPU ↑
Memory ↑
```

장점:

```text
구성 단순
```

위험:

```text
Failure Domain 확대
```

---

# 20. Scale-out

## FIG-INF-20. Scale-out

```text
Instance A
Instance B
Instance C
    ↓
Load Balancer
```

장점:

```text
Failure Isolation
Horizontal Capacity
```

---

# 21. Compute Failure Domain

## FIG-INF-21. Failure Unit

```text
Physical Host
  ↓
VM
  ↓
JVM
  ↓
Application Instance
```

어느 단위의 장애를 견뎌야 하는지 명시한다.

---

# 22. Network Architecture

## FIG-INF-22. Network Stack

```text
Client / Channel
      ↓
DNS / GSLB
      ↓
L4
      ↓
WEB Network
      ↓
WAS Network
      ↓
DB / Data Network
      ↓
Management / Backup Network
```

---

# 23. Network Zone

## FIG-INF-23. Zone to Network

```text
Logical Security Zone
      ↓
Subnet / VLAN
      ↓
Firewall / ACL
      ↓
Route
```

### 핵심

```text
Zone
≠ VLAN
```

---

# 24. GSLB

## FIG-INF-24. GSLB

```text
Client
  ↓
GSLB
  ├─ Main Center
  └─ DR Center
```

---

# 25. L4

## FIG-INF-25. L4

```text
VIP
 ↓
L4
 ├─ WEB #1
 └─ WEB #2
```

---

# 26. WEB Layer

## FIG-INF-26. WEB Runtime

```text
L4
 ↓
Apache WEB
 │
 ├─ Static
 ├─ Reverse Proxy
 ├─ Routing
 └─ Access Control Candidate
 ↓
Tomcat WAS
```

---

# 27. WEB VM vs Apache Instance

## FIG-INF-27. WEB Boundary

```text
WEB VM
  ├─ Apache Instance #1
  └─ Apache Instance #2 [if configured]
```

---

# 28. WAS Layer

## FIG-INF-28. WAS Runtime

```text
WEB
 ↓
Tomcat JVM
 ↓
WAR / Business Runtime
```

---

# 29. Standard Online Infrastructure Path

## FIG-INF-29. Online Path

```text
User
 ↓
GSLB
 ↓
L4
 ↓
WEB
 ↓
WAS / JVM
 ↓
WAR
 ↓
Hikari
 ↓
DB
```

---

# 30. Direct WAS Access

## FIG-INF-30. Direct Access Risk

```text
Approved
Client → WEB → WAS

Exception
Client ─────────► WAS
```

Direct WAS가 존재하면 동일한 Security Control이 보장되는지 검증한다.

---

# 31. Firewall Architecture

## FIG-INF-31. Firewall Rule

```text
Source
 ↓
Firewall
 ↓
Destination
```

Rule은 최소:

```text
Source
Destination
Protocol
Port
Purpose
Owner
Approval
```

을 가진다.

---

# 32. Port Architecture

## FIG-INF-32. Port Trace

```text
Application Config
      ↕
Port Inventory
      ↕
L4 / LB
      ↕
Firewall
      ↕
Monitoring
```

---

# 33. Port Inventory Rule

### MUST

모든 Listening Port를 Inventory에 등록한다.

### MUST NOT

```text
임시 Port
→ 영구 운영
```

---

# 34. Network HA

## FIG-INF-33. Network HA

```text
Network Path A
       +
Network Path B
       ↓
Failure
       ↓
Alternative Route
```

실제 이중화수준은 Network 상세설계로 확정한다.

---

# 35. DNS Architecture

## FIG-INF-34. DNS

```text
Service Name
  ↓
DNS
  ↓
VIP / Endpoint
```

Hostname/VIP 변경과 Application URL Contract를 분리한다.

---

# 36. WEB HA

## FIG-INF-35. WEB HA

```text
L4
 ├─ WEB #1
 └─ WEB #2
```

Node 한 대 장애 후에도 잔존 Capacity가 SLO를 만족해야 한다.

---

# 37. WAS HA

## FIG-INF-36. WAS HA

```text
L4 / WEB
   ↓
WAS Group
├─ JVM #1
├─ JVM #2
└─ JVM #N
```

---

# 38. N+1 Capacity

## FIG-INF-37. N+1

```text
Normal
A + B + C

A Down
  ↓
B + C
  ↓
SLO 유지?
```

---

# 39. Session Infrastructure

## FIG-INF-38. Session State

```text
Client
 ↓
WAS #1
 ↓
Session State
```

Failover 시:

```text
Sticky?
Replication?
Shared Store?
JWT-only?
```

정책에 따라 달라진다.

---

# 40. Session Replication

## FIG-INF-39. Replication

```text
Tomcat #1
  ↕
Tomcat #2
```

DeltaManager/JDBC 등은 Current/Target Baseline을 분리한다.

---

# 41. JWT Infrastructure

## FIG-INF-40. JWT HA

```text
JWT #1
JWT #2
  ↓
Shared / Managed Key Source
  ↓
JWKS
  ↓
Business Verifier
```

---

# 42. JWT Key Infrastructure

## FIG-INF-41. Key Infrastructure

```text
Key Store
  ↓
Versioned Key
  ↓
kid
  ↓
JWT Instances
  ↓
JWKS
```

same `kid`는 동일 Public Key를 의미해야 한다.

---

# 43. Database Infrastructure

## FIG-INF-42. DB Stack

```text
Application
  ↓
Hikari
  ↓
DB Service
  ↓
DB Cluster
  ↓
DB Node
  ↓
Storage
```

---

# 44. RDW Infrastructure

## FIG-INF-43. RDW

```text
Online / Near Real-time
      ↓
RDW DB Service
      ↓
DB Cluster / Appliance
      ↓
Storage
```

---

# 45. ADW Infrastructure

## FIG-INF-44. ADW

```text
ETL / BI / Analytics
      ↓
ADW DB Service
      ↓
DB Cluster / Appliance
      ↓
Storage
```

---

# 46. RDW / ADW Resource Isolation

## FIG-INF-45. Data Isolation

```text
RDW
  └─ Operational Workload

ADW
  └─ Analytical Workload
```

Heavy Analytical Query가 Online/Operational 자원을 침해하지 않도록 분리한다.

---

# 47. DB HA

## FIG-INF-46. DB HA

```text
DB Service
  ↓
Cluster / RAC
  ├─ Node A
  └─ Node B
```

정확한 노드 수는 DB Inventory로 확정한다.

---

# 48. DB DR

## FIG-INF-47. DB DR

```text
Main DB
  ↓ replication
DR DB
```

RPO/RTO와 정합성 요구를 함께 정의한다.

---

# 49. CDC Infrastructure

## FIG-INF-48. CDC

```text
Source DB
  ↓
Capture
  ↓
Trail / Relay
  ↓
Network
  ↓
Apply
  ↓
RDW
```

---

# 50. CDC HA

## FIG-INF-49. CDC HA

```text
Capture / Relay
     ↓ failure
Restart / Failover
     ↓
Resume Position
     ↓
Catch-up
```

---

# 51. ETL Infrastructure

## FIG-INF-50. ETL

```text
ETL Scheduler
  ↓
ETL Engine
  ↓
Source / Target DB
```

---

# 52. Batch Infrastructure

## FIG-INF-51. Batch

```text
Scheduler
  ↓
Batch JVM / Runtime
  ↓
Job / Step
  ↓
DB / File / Interface
```

---

# 53. Online / Batch Resource Isolation

## FIG-INF-52. Workload Isolation

```text
Online Runtime
  ──► Online Compute / DB

Batch / ETL
  ──► Batch Resource / Window
```

---

# 54. Event Infrastructure

## FIG-INF-53. Event Platform

```text
Producer
  ↓
Broker Cluster
  ↓
Topic / Partition
  ↓
Consumer Group
```

---

# 55. Event HA

## FIG-INF-54. Event HA

```text
Broker #1
Broker #2
Broker #3 [candidate]
   ↓
Replication / Partition
```

실제 Cluster Size는 승인 Inventory로 확정한다.

---

# 56. File Infrastructure

## FIG-INF-55. File

```text
Producer
  ↓
Landing
  ↓
MFT / FOS
  ↓
Storage
  ↓
Consumer
```

---

# 57. File Storage

```text
Inbound
Processing
Archive
Error / Quarantine
```

용도별 Directory/Filesystem을 분리한다.

---

# 58. Storage Architecture

## FIG-INF-56. Storage Domains

```text
Application Storage
Database Storage
File Transfer Storage
Log Storage
Backup Storage
Archive Storage
```

---

# 59. Filesystem Architecture

## FIG-INF-57. Filesystem

```text
OS Mount
  ↓
Application Path
  ↓
Log Path
  ↓
File/Data Path
  ↓
Backup / Cleanup
```

---

# 60. Filesystem Inventory

```text
Host
Mount
Purpose
Capacity
Owner
Permission
Retention
Backup
Threshold
```

---

# 61. Disk Capacity

## FIG-INF-58. Disk Capacity

```text
Used
  ↓ trend
Threshold
  ↓
Alert
  ↓
Cleanup / Expand
```

---

# 62. Log Storage

## FIG-INF-59. Log Storage

```text
Application Log
Security Log
Transaction Log
ImageLog
Deployment Log
  ↓
Central Collection / Storage
```

---

# 63. Backup Infrastructure

## FIG-INF-60. Backup

```text
DB / Config / Artifact / Key Metadata
      ↓
Backup
      ↓
Backup Storage
      ↓
Retention
      ↓
Restore Test
```

---

# 64. Backup 대상

```text
Database
Configuration
Deployment Manifest
Artifact
Certificate/Key Metadata
Batch Metadata
Critical File
```

---

# 65. Backup Success ≠ Restore Success

## FIG-INF-61. Restore Evidence

```text
Backup SUCCESS
      ↓
Restore
      ↓
Application Start
      ↓
Business Validation
      ↓
Recovery PASS
```

---

# 66. Security Infrastructure

## FIG-INF-62. Security Layers

```text
Network Security
      ↓
Host Security
      ↓
Runtime Security
      ↓
Identity / Key
      ↓
Data Protection
      ↓
Audit
```

---

# 67. Host Security

```text
OS Hardening
Patch
Account
Privilege
Process
File Permission
Audit
```

세부 기준은 Security Standard에서 정의한다.

---

# 68. Account Architecture

## FIG-INF-63. Account

```text
Human Account
  ↓
Personal Identification / Audit

Service Account
  ↓
Runtime Identity / Least Privilege
```

---

# 69. Service Account Standard

```text
Role
Environment
Host / Platform
Privilege
Secret Source
Owner
Expiry
Audit
```

---

# 70. Secret Infrastructure

## FIG-INF-64. Secret Delivery

```text
Protected Secret Source
        ↓
Runtime Injection
        ↓
Application
```

### MUST NOT

```text
Private Key / Password
→ Source Repository
```

---

# 71. Certificate Infrastructure

## FIG-INF-65. Certificate Lifecycle

```text
Issue
  ↓
Deploy
  ↓
Monitor Expiry
  ↓
Rotate
  ↓
Retire
```

---

# 72. Monitoring Infrastructure

## FIG-INF-66. Monitoring Stack

```text
Network
  ↓
WEB / WAS
  ↓
JVM
  ↓
Worker / Pool
  ↓
DB / Storage
  ↓
Metric / Log / Trace
  ↓
Dashboard / Alert
```

---

# 73. Infrastructure Monitoring

```text
CPU
Memory
GC
Thread
Queue
Connection
Network
Disk
DB Session
Replication Lag
Backup
```

---

# 74. Technical vs Business Metric

## FIG-INF-67. Correlation

```text
Business
ServiceId p95 / Error
        ↓
Infrastructure
JVM / Worker / Hikari / DB / Network
```

---

# 75. Infrastructure Health

## FIG-INF-68. Health Tree

```text
Service Health
  ↓
Application Health
  ↓
JVM Health
  ↓
Host Health
  ↓
Network / DB / Storage Health
```

---

# 76. Capacity Architecture

## FIG-INF-69. Capacity Chain

```text
Business Load
  ↓
TPS
  ↓
Thread
  ↓
Worker
  ↓
Connection
  ↓
CPU / Memory
  ↓
Node Count
```

---

# 77. Capacity Assumption

기존 분석에서 대표 사용자 가정:

```text
6,000 지점 × 6명 = 36,000명
```

동시율/응답시간 값은 Baseline별 Candidate로 관리한다.

---

# 78. Capacity Evidence

## FIG-INF-70. Sizing Evidence

```text
Candidate
  ↓
Load Test
  ↓
CPU / Memory / Thread / DB
  ↓
Measured Limit
  ↓
Headroom
  ↓
Approved Capacity
```

---

# 79. Tomcat Capacity

## FIG-INF-71. Tomcat

```text
maxThreads
  ↓
Busy Threads
  ↓
Queue / Connection
  ↓
Response Time
```

정확한 수치는 Load Test 기준으로 확정한다.

---

# 80. Worker Capacity

## FIG-INF-72. Worker

```text
Worker Pool
  ↓
Active
  ↓
Queue
  ↓
Reject
  ↓
Timeout
```

PDMG Current Snapshot:

```text
20 / 100 / 5000ms
```

---

# 81. Hikari Capacity

## FIG-INF-73. Hikari

```text
Active
Idle
Pending
Max
Acquire Time
```

---

# 82. DB Capacity

## FIG-INF-74. DB

```text
Sessions
Active Sessions
CPU
I/O
Wait
Long SQL
```

---

# 83. Capacity Cascade

## FIG-INF-75. Cascade

```text
DB Slow
  ↓
Hikari Pending
  ↓
Worker Queue
  ↓
Tomcat Busy
  ↓
p95 / Timeout
```

---

# 84. HA Architecture

## FIG-INF-76. HA Layers

```text
GSLB
  ↓
L4
  ↓
WEB
  ↓
WAS
  ↓
DB
  ↓
Storage / Integration
```

---

# 85. HA Failure Detection

```text
Failure
  ↓
Health Check
  ↓
Isolation
  ↓
Traffic Shift
  ↓
Recovery
```

---

# 86. HA Residual Capacity

## FIG-INF-77. Residual Capacity

```text
N Nodes
  ↓ failure
N-1 Nodes
  ↓
Can SLO survive?
```

---

# 87. DR Architecture

## FIG-INF-78. Center DR

```text
MAIN
  ↓ disaster
DR
  │
  ├─ Network
  ├─ WEB/WAS
  ├─ Artifact
  ├─ Config
  ├─ Key
  ├─ DB
  ├─ Interface
  ├─ Batch
  └─ Monitoring
```

---

# 88. DR Activation

## FIG-INF-79. Activation

```text
Detect
  ↓
Declare
  ↓
Route
  ↓
Start / Validate
  ↓
Business Open
```

---

# 89. DR Failback

## FIG-INF-80. Failback

```text
DR Service
  ↓
Main Recover
  ↓
Data Reconcile
  ↓
Route Back
  ↓
Business Validate
```

---

# 90. RTO / RPO

## FIG-INF-81. Objectives

```text
Business Service
  ↓
Technical Dependency
  ↓
RTO / RPO
  ↓
Infrastructure Design
  ↓
DR Test
```

---

# 91. Infrastructure Naming

## FIG-INF-82. Naming Objects

```text
Center
Environment
Hostname
VM
JVM
WAR
VIP
Port
Filesystem
Account
Datasource
```

모든 Infrastructure Object는 식별가능해야 한다.

---

# 92. Hostname Standard

## FIG-INF-83. Hostname

```text
Organization
  ↓
Application / System
  ↓
Platform / Role
  ↓
Environment
  ↓
Sequence
```

기존 자리수 규칙은 승인 Hostname Standard와 대조한다.

---

# 93. Hostname 목적

```text
Hostname
  ↓
Inventory
  ↓
Monitoring
  ↓
Deployment
  ↓
Incident
```

---

# 94. VM Naming

## FIG-INF-84. VM Identity

```text
VM Name
  ↓
Environment
  ↓
Technical Role
  ↓
Sequence
```

---

# 95. JVM Naming

```text
Host
  ↓
JVM Name / Instance
  ↓
Port
  ↓
WAR
```

---

# 96. VIP Naming

```text
Service
  ↓
VIP
  ↓
Pool
  ↓
Member
```

---

# 97. Datasource Naming

```text
Application
  ↓
Datasource
  ↓
DB Service
```

---

# 98. Infrastructure Inventory SSOT

## FIG-INF-85. Inventory Set

```text
Server Inventory
VM Inventory
Software Inventory
Network Inventory
Port Inventory
DB Inventory
Storage Inventory
Filesystem Inventory
Account Inventory
HA/DR Inventory
```

---

# 99. Server Inventory

```yaml
server:
  hostname:
  center:
  environment:
  logicalNode:
  physicalType:
  cpu:
  memory:
  storage:
  os:
  owner:
  haGroup:
  drPair:
  monitoring:
  evidence:
```

---

# 100. VM Inventory

```yaml
vm:
  vmName:
  physicalHost:
  environment:
  vcpu:
  memory:
  disk:
  os:
  logicalNode:
  owner:
```

---

# 101. JVM Inventory

```yaml
jvm:
  jvmId:
  host:
  javaVersion:
  heap:
  gc:
  ports:
  wars:
  datasource:
  worker:
  monitoring:
```

---

# 102. WAR Inventory

```yaml
artifact:
  application:
  war:
  version:
  hash:
  jvm:
  context:
  deploymentId:
```

---

# 103. Network Inventory

```yaml
network:
  zone:
  subnet:
  vlan:
  gateway:
  firewall:
  route:
  owner:
```

---

# 104. Port Inventory

```yaml
port:
  source:
  destination:
  protocol:
  port:
  purpose:
  firewallRule:
  loadBalancer:
  owner:
```

---

# 105. Storage Inventory

```yaml
storage:
  storageId:
  type:
  hostOrCluster:
  capacity:
  purpose:
  redundancy:
  backup:
  owner:
```

---

# 106. Account Inventory

```yaml
account:
  id:
  type:
  role:
  environment:
  host:
  privilege:
  credentialSource:
  owner:
```

---

# 107. Infrastructure Cross-check

## FIG-INF-86. Cross Validation

```text
Architecture Model
      ↓ compare
CMDB / Inventory
      ↓ compare
Virtualization / IaaS
      ↓ compare
DNS / LB
      ↓ compare
Firewall
      ↓ compare
Monitoring
      ↓ compare
Backup
      ↓ compare
Deployment
```

---

# 108. Deployment Mapping

## FIG-INF-87. Application to Infra

```text
Application
  ↓
Artifact
  ↓
DeploymentId
  ↓
JVM
  ↓
VM
  ↓
Physical Host
  ↓
Center
```

---

# 109. Runtime Mapping

```text
ServiceId
  ↓
WAR
  ↓
JVM
  ↓
Host
  ↓
Metric / Log
```

---

# 110. Infrastructure Configuration Baseline

## FIG-INF-88. Config

```text
Expected Config
  ↓
Actual Runtime Config
  ↓
PASS / DRIFT
```

---

# 111. Config Items

```text
OS
JVM
Tomcat
Apache
Worker
Hikari
Port
Session
Logging
Key
```

---

# 112. Drift Architecture

## FIG-INF-89. Drift

```text
Approved Baseline
      ↓ compare
Actual Infrastructure
      ↓
Drift
      ↓
Fix / ADR / Exception
```

---

# 113. Infrastructure Change

## FIG-INF-90. Change Lifecycle

```text
Change Request
  ↓
Impact
  ↓
Approval
  ↓
Apply
  ↓
Verify
  ↓
Evidence
  ↓
Baseline Update
```

---

# 114. Dynamic Config Change

```text
Before
  ↓
Approved Change
  ↓
After
  ↓
Runtime Verify
```

Evidence 없는 수동변경을 금지한다.

---

# 115. Infrastructure Rule

## FIG-INF-91. Rule Lifecycle

```text
Infrastructure Principle
      ↓
Rule
      ↓
Inventory / Config Scan
      ↓
Runtime Test
      ↓
PASS / FAIL
```

---

# 116. Compute Rule

```text
R-INF-HOST-VM-MAPPING
R-INF-VM-JVM-MAPPING
R-INF-JVM-WAR-MAPPING
R-INF-CAPACITY
```

---

# 117. Network Rule

```text
R-INF-PORT-INVENTORY
R-INF-FW-PORT-ALIGNMENT
R-INF-LB-MEMBER-ALIGNMENT
R-INF-DIRECT-WAS-CONTROL
```

---

# 118. Storage Rule

```text
R-INF-FS-INVENTORY
R-INF-CAPACITY-THRESHOLD
R-INF-BACKUP
R-INF-RESTORE
```

---

# 119. HA / DR Rule

```text
R-INF-NPLUS1
R-INF-FAILOVER
R-INF-DR-ARTIFACT
R-INF-DR-CONFIG
R-INF-DR-KEY
R-INF-DR-DATA
```

---

# 120. Security Infra Rule

```text
R-INF-ACCOUNT
R-INF-SECRET
R-INF-CERTIFICATE
R-INF-LEAST-PRIVILEGE
```

---

# 121. Infrastructure Test

## FIG-INF-92. Test Stack

```text
Connectivity
  ↓
Load
  ↓
Stress
  ↓
Node Failure
  ↓
Network Failure
  ↓
DB Failure
  ↓
Storage / Backup
  ↓
DR
  ↓
Runtime Evidence
```

---

# 122. Connectivity Test

## FIG-INF-93. Connectivity

```text
Source
 ↓
DNS
 ↓
Route
 ↓
Firewall
 ↓
L4 / VIP
 ↓
Target Port
```

---

# 123. Load Test

```text
Traffic
  ↓
WEB
  ↓
WAS
  ↓
Worker / Hikari
  ↓
DB
```

---

# 124. Node Failure Test

## FIG-INF-94. Node Failure

```text
Node Down
  ↓
Health Detect
  ↓
Pool Remove
  ↓
Traffic Shift
  ↓
SLO Check
```

---

# 125. Network Failure Test

```text
Network Path Failure
  ↓
Route / LB behavior
  ↓
Application Impact
  ↓
Recovery
```

---

# 126. DB Failure Test

```text
DB Node Failure
  ↓
DB HA
  ↓
Connection Recover
  ↓
Business Validation
```

---

# 127. Backup / Restore Test

```text
Backup
  ↓
Restore
  ↓
Runtime Start
  ↓
Data Validation
```

---

# 128. DR Test

## FIG-INF-95. DR Test

```text
Main Down
  ↓
DR Activate
  ↓
Route
  ↓
Application
  ↓
DB
  ↓
Interface
  ↓
Business Validate
  ↓
RTO / RPO Evidence
```

---

# 129. Infrastructure Monitoring Gate

## FIG-INF-96. Monitoring Gate

```text
Host Metric?
  ↓
JVM Metric?
  ↓
Network Metric?
  ↓
DB Metric?
  ↓
Disk Metric?
  ↓
Backup Metric?
  ↓
Alert / Runbook?
```

---

# 130. Infrastructure Go-Live Blocker

## FIG-INF-97. Go-Live Block

```text
Host/JVM/WAR Mapping 없음
       OR
Port/FW/LB 정합 미확정
       OR
Critical Capacity Evidence 없음
       OR
HA Failover 미검증
       OR
Backup Restore 미검증
       OR
DR Evidence 없음
       OR
Critical Security Infra GAP
       OR
Monitoring 없음
       ↓
GO-LIVE BLOCK
```

---

# 131. Infrastructure GAP Register

## TEXT ARCHITECTURE — GAP Lifecycle

```text
Target Infrastructure
      ↓ compare
Actual Infrastructure
      ↓
GAP
 ├─ Severity
 ├─ Owner
 ├─ Action
 ├─ ADR
 └─ Evidence
```

| ID | GAP | 영향 |
|---|---|---|
| GAP-INF-01 | Host/VM/JVM/WAR 전수 Mapping 미완료 | Trace |
| GAP-INF-02 | 최신 HW Inventory 승인 필요 | Compute |
| GAP-INF-03 | 최신 SW Inventory 승인 필요 | Runtime |
| GAP-INF-04 | Port/FW/LB 전수 정합 자동화 미완료 | Network |
| GAP-INF-05 | Capacity 실측 Baseline 미완료 | Performance |
| GAP-INF-06 | Query/TX Timeout Infra 정합 미완료 | Runtime |
| GAP-INF-07 | Session 60/90 Conflict | HA |
| GAP-INF-08 | JWT Key HA/DR 구조 보완 | Security |
| GAP-INF-09 | DB/CDC DR 정합 검증 필요 | Data |
| GAP-INF-10 | RTO/RPO 서비스별 확정 필요 | DR |
| GAP-INF-11 | Restore Evidence 자동화 미완료 | Recovery |
| GAP-INF-12 | Account/Port/FileSystem Inventory 최신화 | Standard |
| GAP-INF-13 | Runtime Config Drift 자동탐지 미완료 | Operations |
| GAP-INF-14 | pdmg-om Infrastructure Mapping 미확인 | Operations |

---

# 132. Infrastructure Risk Register

## TEXT ARCHITECTURE — Risk Lifecycle

```text
Infrastructure Weakness
  ↓
Failure
  ↓
Service Impact
  ↓
Severity
  ↓
Mitigation
  ↓
Recovery Evidence
```

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-INF-01 | Host/JVM/WAR Mapping 오류 | Critical |
| RISK-INF-02 | L4/FW/Port Drift | Critical |
| RISK-INF-03 | VM 과대집중 | High |
| RISK-INF-04 | Worker/Hikari 연쇄병목 | Critical |
| RISK-INF-05 | DB Capacity Exhaustion | Critical |
| RISK-INF-06 | Online/Batch 자원경합 | High |
| RISK-INF-07 | Disk Full | Critical |
| RISK-INF-08 | Backup만 있고 Restore 미검증 | Critical |
| RISK-INF-09 | JWT Key DR 불일치 | Critical |
| RISK-INF-10 | DR Artifact/Config 불일치 | Critical |
| RISK-INF-11 | Manual Config Drift | Critical |
| RISK-INF-12 | Monitoring Blind Spot | High |

---

# 133. Infrastructure ADR 후보

## FIG-INF-98. ADR Areas

```text
Infrastructure Decision
│
├─ Center / DR
├─ VM / Physical
├─ WEB / WAS Pattern
├─ JVM Density
├─ Network / L4
├─ DB / Storage
├─ Session
├─ Backup / Restore
├─ Capacity
├─ Monitoring
└─ Inventory Governance
```

대표 ADR:

```text
ADR-INF-01 Center / DR Pattern
ADR-INF-02 Compute Virtualization
ADR-INF-03 WEB/WAS/JVM Placement
ADR-INF-04 Capacity / N+1
ADR-INF-05 Network/L4/FW
ADR-INF-06 DB/Storage HA
ADR-INF-07 Session State
ADR-INF-08 JWT Key HA/DR
ADR-INF-09 Backup/Restore
ADR-INF-10 Monitoring
ADR-INF-11 Port/Account/FileSystem Standard
ADR-INF-12 Infrastructure Drift
```

---

# 134. Infrastructure Review Checklist

## FIG-INF-99. Review Gate

```text
Center / Environment?
  ↓
Compute?
  ↓
Network?
  ↓
WEB/WAS/JVM?
  ↓
DB/Storage?
  ↓
Security?
  ↓
Capacity?
  ↓
HA/DR?
  ↓
Monitoring?
  ↓
Inventory?
  ↓
Evidence?
  ↓
PASS / GAP
```

---

# 135. 신규 서버 설계 Route

## FIG-INF-100. New Server

```text
Logical Node Need
  ↓
Environment / Center
  ↓
Physical / VM
  ↓
CPU / Memory / Disk
  ↓
OS / Runtime
  ↓
Network / Port
  ↓
Account / Filesystem
  ↓
Monitoring / Backup
  ↓
HA / DR
  ↓
Inventory
```

---

# 136. 신규 VM Checklist

```text
[ ] Logical Node
[ ] Center
[ ] Environment
[ ] vCPU
[ ] Memory
[ ] Disk
[ ] OS
[ ] Network
[ ] Account
[ ] Monitoring
[ ] Backup
[ ] HA/DR
```

---

# 137. 신규 JVM Checklist

```text
[ ] Host/VM
[ ] Java Version
[ ] Heap
[ ] GC
[ ] Thread
[ ] Port
[ ] WAR
[ ] Datasource
[ ] Worker
[ ] Monitoring
```

---

# 138. 신규 Port Checklist

```text
[ ] Source
[ ] Destination
[ ] Protocol
[ ] Port
[ ] Purpose
[ ] FW
[ ] L4
[ ] Owner
[ ] Monitoring
```

---

# 139. 신규 Filesystem Checklist

```text
[ ] Host
[ ] Mount
[ ] Purpose
[ ] Capacity
[ ] Permission
[ ] Retention
[ ] Backup
[ ] Alert
```

---

# 140. 신규 Account Checklist

```text
[ ] Human / Service
[ ] Role
[ ] Environment
[ ] Privilege
[ ] Secret Source
[ ] Owner
[ ] Expiry
[ ] Audit
```

---

# 141. Application Architecture와 관계

## FIG-INF-101. AA → Infra

```text
Application Architecture
Application / Program / ServiceId
        ↓
Technical Architecture
Logical Node / Platform
        ↓
Infrastructure Architecture
VM / Host / Network / DB / Storage
```

---

# 142. Technical Architecture와 관계

## FIG-INF-102. TA → Infra

```text
Technical Component
  ↓
Runtime Platform
  ↓
Infrastructure Resource
```

---

# 143. Operations Architecture와 관계

## FIG-INF-103. Infra → Ops

```text
Infrastructure
Host / JVM / Network / DB / Storage
        ↓
Operations
Metric / Log / Alert / Runbook
        ↓
Evidence
```

---

# 144. Development Standard와 관계

## FIG-INF-104. Infra → Dev

```text
Infrastructure Standard
JDK / Port / Datasource / Runtime
        ↓
Development Config
application.yml / server.xml / build
```

---

# 145. Physical Traceability

## FIG-INF-105. Full Physical Trace

```text
Application
  ↓
Artifact
  ↓
DeploymentId
  ↓
WAR
  ↓
JVM
  ↓
VM
  ↓
Physical Host
  ↓
Center
  ↓
Metric / Evidence
```

---

# 146. Reverse Physical Trace

```text
Host Incident
  ↑
VM
  ↑
JVM
  ↑
WAR
  ↑
Application
  ↑
ServiceId
```

---

# 147. Infrastructure Runtime Evidence

## FIG-INF-106. Evidence Chain

```text
Infrastructure Baseline
      ↓
Inventory Version
      ↓
DeploymentId
      ↓
Host / JVM / Port
      ↓
Runtime Metric
      ↓
Failure / Load Test
      ↓
Evidence
```

---

# 148. Infrastructure Baseline Package

## FIG-INF-107. Baseline Package

```text
Infrastructure Architecture
│
├─ Center Map
├─ Environment Map
├─ Network Diagram
├─ Server/VM Inventory
├─ JVM/WAR Mapping
├─ DB/Storage Map
├─ Port Inventory
├─ Account Inventory
├─ Filesystem Inventory
├─ HA/DR Map
├─ Capacity Baseline
├─ Monitoring Map
└─ Runtime Evidence
```

---

# 149. Infrastructure Completion Gate

## FIG-INF-108. Completion Gate

```text
Center / Environment Defined?
   ↓ YES
Compute Defined?
   ↓ YES
Network Defined?
   ↓ YES
WEB/WAS/JVM Defined?
   ↓ YES
DB/Storage Defined?
   ↓ YES
Security Infra Defined?
   ↓ YES
Capacity Defined?
   ↓ YES
HA/DR Defined?
   ↓ YES
Inventory Current?
   ↓ YES
Monitoring / Evidence?
   ↓ YES
INFRASTRUCTURE ARCHITECTURE PASS
```

---

# 150. 최종 Infrastructure Architecture 지도

## FIG-INF-109. Final Infra Map

```text
LOGICAL / TECHNICAL NODE
          ↓
────────────────────────────────────
CENTER / ENVIRONMENT
          ↓
COMPUTE
Physical / VM / OS
          ↓
NETWORK
DNS / GSLB / L4 / Firewall
          ↓
WEB / WAS / JVM / WAR
          ↓
DB / STORAGE / CDC / ETL / EVENT
          ↓
SECURITY / ACCOUNT / SECRET
          ↓
BACKUP / MONITORING
          ↓
CAPACITY / HA / DR
          ↓
HOSTNAME / PORT / FILESYSTEM INVENTORY
          ↓
DEPLOYMENT / RUNTIME EVIDENCE
```

---

# 151. Definition of Done

## TEXT ARCHITECTURE — Infrastructure Architecture DoD

```text
Center
  ↓
Compute
  ↓
Network
  ↓
Runtime
  ↓
DB / Storage
  ↓
HA / DR
  ↓
Standard / Inventory
  ↓
Monitoring / Evidence
  ↓
INFRASTRUCTURE DoD
```

## Center / Environment
- [x] Main / DR 구분
- [x] Center ≠ Environment
- [x] 환경격리

## Compute
- [x] Physical / VM / JVM / WAR
- [x] Sizing
- [x] Scale-up/out
- [x] Failure Domain

## Network
- [x] DNS/GSLB/L4
- [x] WEB/WAS Path
- [x] Firewall/Port
- [x] Network HA

## Data / Storage
- [x] RDW/ADW
- [x] DB HA/DR
- [x] CDC/ETL/Event/File
- [x] Filesystem/Storage

## Security / Operations
- [x] Account
- [x] Secret/Certificate
- [x] Monitoring
- [x] Backup/Restore

## NFR / Governance
- [x] Capacity
- [x] HA
- [x] DR
- [x] Inventory
- [x] Drift
- [x] Rule/Test
- [x] GAP/RISK/ADR
- [x] Go-Live Blocker

**INFRASTRUCTURE ARCHITECTURE 별첨 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. 최신 Center/Environment 배치 승인
2. Host/VM/JVM/WAR 전수 Mapping
3. 최신 HW/SW/DB/Storage Inventory 승인
4. Port/FW/LB 전수 정합성 확보
5. Capacity Load Test Evidence 반영
6. Session/State 정책 확정
7. JWT Key HA/DR 구조 확정
8. DB/CDC DR 구조 검증
9. 서비스별 RTO/RPO 확정
10. Backup/Restore Evidence 확보
11. Account/FileSystem/Port Standard 승인
12. Runtime Config Drift 자동탐지
13. Monitoring Coverage 확보
14. Critical Infra Drift 0

---

# 152. 장 최종 결론

## FIG-INF-110. Infrastructure Architecture Final

```text
Logical Technical Node
        ↓
Center / Environment
        ↓
Compute / Network / Storage
        ↓
WEB / WAS / JVM / DB
        ↓
Capacity / HA / DR / Security
        ↓
Standard / Inventory
        ↓
Monitoring / Runtime Evidence
        ↓
Infrastructure Architecture Baseline
```

> **Infrastructure Architecture의 본질은 서버와 장비를 나열하는 것이 아니라, Logical/Technical Architecture의 실행구조를 실제 Center·Compute·Network·Storage·DB Resource에 배치하고, 용량·이중화·복구·운영·Inventory까지 일관되게 관리하는 것이다.**

> **Technical Architecture가 “어떤 기술구조가 필요한가”를 정의한다면 Infrastructure Architecture는 “그 기술구조를 어떤 물리·가상 Resource와 Network·Storage에 배치하고 어떻게 가용하게 운영할 것인가”를 정의한다.**

> **NSIGHT Infrastructure Architecture는 Application → Artifact → JVM → VM → Host → Center → Runtime Evidence가 단절 없이 추적되어야 하며, Hostname·Port·Account·Filesystem·Capacity·HA/DR를 운영 가능한 Baseline으로 관리해야 한다.**
