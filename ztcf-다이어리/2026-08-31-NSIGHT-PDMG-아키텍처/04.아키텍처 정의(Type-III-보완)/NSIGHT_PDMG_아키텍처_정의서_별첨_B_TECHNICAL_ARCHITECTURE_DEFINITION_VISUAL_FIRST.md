# NSIGHT / PDMG 아키텍처 정의서
# 별첨 B. TECHNICAL ARCHITECTURE DEFINITION
## Logical Technical / Physical Infrastructure / Runtime Platform / System Standard / TRM
## Visual-First / Target + Current Evidence / Standalone Appendix

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-TA-APPENDIX-B`  
> 문서 유형: **별첨 / 독립 Technical Architecture 정의서**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 연계 장: `03 LOGICAL`, `04 PHYSICAL`, `05 MECHANISM`, `06 RUNTIME`, `09 OPERATIONS`, `10 BASELINE`, `11 DEVELOPMENT STANDARD`  
> 연계 별첨: `별첨 A. APPLICATION ARCHITECTURE DEFINITION`

---

# 0. 이 별첨의 목적

## FIG-TA-01. Technical Architecture가 답해야 하는 질문

```text
Application / Data / Interface Requirement
                ↓
어떤 기술영역이 필요한가?
                ↓
어떤 Logical Technical Node로 분리하는가?
                ↓
어떤 Technology Component가 책임지는가?
                ↓
어떤 Runtime Stack으로 실행하는가?
                ↓
어떤 Host / VM / Appliance / Storage에 배치하는가?
                ↓
어떻게 연결·보호·이중화·확장하는가?
                ↓
어떤 기술표준과 Inventory로 관리하는가?
                ↓
Runtime에서 실제 구성과 성능을 어떻게 증명하는가?
```

> **Technical Architecture는 Application/Data/Interface 요구를 실행 가능한 기술 구조로 변환하여, Logical Technical Boundary부터 Physical Resource, Runtime Platform, HA/DR, System Standard, Technology Inventory와 Runtime Evidence까지 정의하는 Architecture 영역이다.**

---

# 1. Technical Architecture 한 문장 정의

## FIG-TA-02. Definition

```text
Business / Application Need
          ↓
Technical Capability
          ↓
Logical Technical Architecture
          ↓
Technology Component
          ↓
Runtime Platform
          ↓
Physical Infrastructure
          ↓
System Standard
          ↓
Operations / Evidence
```

---

# 2. Technical Architecture가 아닌 것

## FIG-TA-03. Not Technical Architecture

```text
Technical Architecture
≠ 서버 목록

Technical Architecture
≠ 제품 목록

Technical Architecture
≠ 네트워크 구성도 한 장

Technical Architecture
≠ TRM 제품 카탈로그

Technical Architecture
≠ JVM 설정값 목록
```

이 항목들은 Technical Architecture의 **구현표현 또는 Inventory**다.

---

# 3. Technical Architecture와 Infrastructure Architecture의 차이

## FIG-TA-04. TA vs Infrastructure

```text
TECHNICAL ARCHITECTURE
│
├─ Technical Principle
├─ Zone / Logical Node
├─ Technology Component
├─ Runtime Platform
├─ Network / Security Boundary
├─ DB / Storage / Integration Platform
├─ Capacity / HA / DR
├─ Technical Standard
└─ Technology Governance
        │
        ▼
PHYSICAL INFRASTRUCTURE
│
├─ Center
├─ Host / VM
├─ CPU / Memory
├─ Storage
├─ Network Device
├─ JVM / Process
└─ Physical Connection
```

### 핵심

```text
Physical Infrastructure
⊂
Technical Architecture
```

---

# 4. Technical Architecture와 TRM의 차이

## FIG-TA-05. Architecture vs TRM

```text
Technical Architecture
= 어떤 기술 Capability와 구조가 필요한가?

          ↓

Technology Reference Model
= 어떤 기술분류와 제품/표준 후보로 구현할 것인가?

          ↓

Technology Inventory
= 실제 어느 제품/버전/Host에 적용되어 있는가?
```

### 금지

```text
TRM 후보 제품
=
Target 선정 제품

X
```

---

# 5. 전체 Enterprise Architecture에서 위치

## FIG-TA-06. EA Position

```text
Business Architecture
        ↓
Application Architecture
        ↓
Data Architecture
        ↓
Interface Architecture
        ↓
┌──────────────────────────────┐
│ TECHNICAL ARCHITECTURE       │
│                              │
│ Zone / Node                  │
│ Platform / Runtime           │
│ Network / Compute / Storage  │
│ DB / Integration             │
│ HA / DR / Capacity           │
└──────────────┬───────────────┘
               ↓
Security / Operations / DevOps
               ↓
Runtime Evidence
```

---

# 6. Technical Architecture 공식 전개 구조

## FIG-TA-07. NSIGHT TA Route

```text
논리 기술 아키텍처
  ↓
물리 인프라 아키텍처
  ↓
데이터베이스 아키텍처
  ↓
시스템 표준 정의
  ↓
요소기술 / Runtime
  ↓
Operations / Evidence
```

본 별첨은 기존의 `논리기술 → 물리인프라 → DB → 시스템표준` 흐름을 하나의 Technical Architecture 관점으로 통합한다.

---

# 7. Technical Architecture 10대 축

## FIG-TA-08. Ten Axes

```text
1. Technical Principle
      ↓
2. IT Zone / Boundary
      ↓
3. Logical Technical Node
      ↓
4. Technology Component
      ↓
5. Runtime Platform
      ↓
6. Physical Infrastructure
      ↓
7. Database / Storage / Integration
      ↓
8. Capacity / HA / DR / Security
      ↓
9. System Standard / TRM / Inventory
      ↓
10. Operations / Evidence / Governance
```

---

# 8. Technical Architecture 기본 원칙

## FIG-TA-09. Core Principles

```text
Responsibility Separation
        +
Standard Connection
        +
Resource Isolation
        +
Horizontal Scalability
        +
Failure Isolation
        +
Security Boundary
        +
Observability
        +
Traceability
```

---

# 9. 원칙 — 논리와 물리를 분리한다

## FIG-TA-10. Logical ≠ Physical

```text
Logical Node
= 역할 / 책임 / Runtime 특성

        ↓ mapping

Physical Resource
= Center / Host / VM / Appliance
```

### MUST NOT

```text
Hostname
→ Logical Architecture의 Node 명칭으로 사용
```

---

# 10. 원칙 — Application과 Technical Node를 분리한다

## FIG-TA-11. Application vs Technical

```text
Application
= Business Responsibility

Logical Technical Node
= Runtime / Technology Responsibility

Physical Node
= Compute Resource
```

하나의 Application이 여러 Technical Node를 사용할 수 있다.

---

# 11. 원칙 — Technology Product와 Component를 분리한다

## FIG-TA-12. Component vs Product

```text
Technology Component
"Web Server"
      ↓ implements
Product
"Apache HTTP Server" [if approved]

Technology Component
"WAS"
      ↓ implements
Product
"Tomcat" [if approved]
```

Architecture는 제품명보다 **역할**을 먼저 정의한다.

---

# 12. Technical Capability Model

## FIG-TA-13. Capability Map

```text
Technical Capability
│
├─ Traffic Management
├─ Web Serving
├─ Application Runtime
├─ Transaction / Thread Control
├─ Data Access
├─ Database
├─ Streaming / Event
├─ CDC / ETL
├─ File Transfer
├─ Scheduling / Batch
├─ Security
├─ Monitoring / Logging
├─ Build / Deployment
├─ Storage / Backup
└─ HA / DR
```

---

# 13. IT Zone Architecture

## FIG-TA-14. Zone Concept

```text
External / Channel
      ↓
Access Zone
      ↓
Service Delivery Zone
      ↓
Integration / Data Access Zone
      ↓
Data Zone
      ↓
Management / Operations Zone
```

정확한 Zone 명칭/번호는 승인된 전사 IT Zone 기준을 따른다.

---

# 14. Zone이 아닌 것

```text
Zone
≠ VLAN
≠ Subnet
≠ Host Group
≠ Application Group
```

Zone은 **보안·접속·책임 경계**다.

---

# 15. Zone 간 연결원칙

## FIG-TA-15. Zone Connection

```text
Zone A
  ↓
Approved Technical Boundary
  ↓
Zone B
```

### 금지

```text
Channel
  ─────────────► DB

External
  ─────────────► WAS Direct without equivalent security

Analytics
  ─────────────► Core OLTP direct heavy query
```

---

# 16. Logical Technical Node

## FIG-TA-16. Logical Node Model

```text
Logical Technical Node
│
├─ Responsibility
├─ Runtime Type
├─ Workload
├─ State
├─ Scale Unit
├─ Failure Domain
├─ Security Boundary
├─ Data Dependency
└─ Environment
```

---

# 17. Logical Node 필수속성

```text
Node ID
Node Name
Technical Role
System/Application Mapping
Runtime Type
HA Requirement
Capacity Characteristic
Inbound / Outbound
Security Zone
Monitoring
Owner
```

---

# 18. Logical Node 예시

## FIG-TA-17. Node Examples

```text
WEB Node
WAS Node
Event Processing Node
CDC Relay Node
ETL Node
BI / Analytical Node
DB Node
Operations Node
```

정확한 실제 Node Set은 Logical Technical Baseline과 대조한다.

---

# 19. Logical Technical Architecture

## FIG-TA-18. Logical Stack

```text
Channel / User
      ↓
Traffic / Access
      ↓
WEB
      ↓
Application Runtime
      ↓
Integration / Event
      ↓
Data Access
      ↓
RDW / ADW
      ↓
Operations / Security / Monitoring
```

---

# 20. Marketing Logical Technical Route

## FIG-TA-19. Marketing Technology Route

```text
Channel
   ↓
Traffic Control
   ↓
Marketing WEB
   ↓
Marketing WAS
   ↓
Business Runtime
   ├────────► RDW
   ├────────► Internal Interface
   └────────► Event Processing
```

---

# 21. Data Platform Logical Route

## FIG-TA-20. Data Platform

```text
Source Systems
    ↓
CDC / ETL
    ↓
RDW
    ↓
ETL / Transformation
    ↓
ADW
    ↓
BI / Analytics
```

---

# 22. FAST / DEEP Technical Separation

## FIG-TA-21. Runtime Separation

```text
FAST
Event / CDC / Near Real-time
       │
       └─ Low Latency / Isolation

DEEP
ETL / ADW / BI / Batch
       │
       └─ Throughput / Analytical Capacity
```

### 원칙

FAST와 DEEP를 같은 Runtime 자원에 무조건 혼재시키지 않는다.

---

# 23. Environment Architecture

## FIG-TA-22. Deployment Environments

```text
Development
  ↓
Test / Verification
  ↓
Production
  ↓
DR
```

선도/이행 등 추가 환경은 프로젝트 Baseline에 따라 별도 식별한다.

---

# 24. 개발환경 / 실행환경 / 운영환경 구분

## FIG-TA-23. Three Meanings

```text
개발환경
= 개발·빌드·단위시험을 수행하는 배포 환경

실행환경
= Application이 실행되기 위해 필요한 Runtime Stack
  JDK / WAS / Library / Container / Process

운영환경
= Production Business Service를 제공하는 배포 환경
```

### 핵심

```text
개발환경 / 운영환경
= Environment Axis

실행환경
= Runtime Technology Axis
```

---

# 25. Environment Isolation

## FIG-TA-24. Isolation

```text
DEV Resource
  ≠
PROD Resource

DEV Credential
  ≠
PROD Credential

DEV Data
  ≠
PROD Data [unless approved masking/process]
```

---

# 26. Physical Architecture Mapping

## FIG-TA-25. Logical → Physical

```text
Logical Node
    ↓
Environment
    ↓
Center
    ↓
Physical Host / VM / Appliance
    ↓
OS / Software
    ↓
Runtime Process / JVM
    ↓
Artifact
```

---

# 27. Center Architecture

## FIG-TA-26. Main / DR

```text
MAIN Center
  ↓
Normal Service
  ↓ failure
DR Center
  ↓
Recovery Service
```

Current NSIGHT Physical Baseline 자료에서는 주센터/DR센터가 분리되어 있다.

---

# 28. Center와 Environment 구분

## FIG-TA-27. Two Axes

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

# 29. Technical Main Path

## FIG-TA-28. Standard Online Physical Path

```text
User / Channel
      ↓
GSLB
      ↓
L4
      ↓
Apache WEB
      ↓
Tomcat / JVM
      ↓
Business WAR
      ↓
HikariCP / MyBatis
      ↓
Oracle / RDW / ADW
```

### 상태

```text
[WORKING BASELINE]
```

실제 시스템별 적용 여부는 Deployment/Inventory에서 검증한다.

---

# 30. Traffic Management

## FIG-TA-29. Traffic Responsibilities

```text
GSLB
= Center / Site Routing

L4
= Server Pool Load Balancing

WEB
= HTTP Entry / Reverse Proxy / Static / Routing

WAS
= Application Runtime
```

각 역할을 혼동하지 않는다.

---

# 31. GSLB Architecture

## FIG-TA-30. GSLB

```text
Client
  ↓
GSLB
  ├─ Main Site
  └─ DR Site
```

GSLB의 실제 전환조건/DNS TTL/Health Policy는 Network 상세설계에서 확정한다.

---

# 32. L4 Architecture

## FIG-TA-31. L4

```text
VIP
 ↓
L4
 ├─ WEB #1
 └─ WEB #2
```

또는 WAS Direct 구조가 존재하면 Security/Architecture Exception으로 확인한다.

---

# 33. WEB Architecture

## FIG-TA-32. WEB Layer

```text
L4
 ↓
Apache Instance
 │
 ├─ HTTP Entry
 ├─ Reverse Proxy
 ├─ Static Content
 └─ WAS Routing
 ↓
Tomcat
```

---

# 34. WEB VM vs Apache Instance

## FIG-TA-33. WEB Boundary

```text
WEB VM / Host
  │
  ├─ Apache Instance A
  └─ Apache Instance B [if configured]
```

```text
VM
≠
Apache Instance
```

---

# 35. WAS Architecture

## FIG-TA-34. WAS Boundary

```text
WAS Server / VM
  │
  ├─ Tomcat JVM #1
  │    └─ WAR
  │
  └─ Tomcat JVM #2 [if configured]
       └─ WAR
```

---

# 36. Server / JVM / WAR 구분

## FIG-TA-35. Three Runtime Objects

```text
Server / VM
     │
     └─ JVM
          │
          └─ WAR / Application
```

### 핵심

```text
WAS Server
≠ Tomcat JVM
≠ WAR
```

---

# 37. JVM Architecture

## FIG-TA-36. JVM Memory

```text
JVM
│
├─ Heap
│   ├─ Young
│   └─ Old
├─ Metaspace
├─ Thread Stack
├─ Direct / Native Memory
└─ GC
```

Heap Size만으로 JVM Capacity를 설명하지 않는다.

---

# 38. JVM Sizing

## FIG-TA-37. Sizing Inputs

```text
Concurrent Request
       ↓
Object Allocation
       ↓
Session / Cache
       ↓
Thread Count
       ↓
GC Behavior
       ↓
Native Memory
       ↓
JVM Sizing
```

정확한 Heap 값은 Capacity Baseline과 Load Test Evidence로 확정한다.

---

# 39. Thread Architecture

## FIG-TA-38. Thread Chain

```text
Tomcat Request Thread
        ↓
PDMG Worker Thread [when enabled]
        ↓
Hikari Connection
        ↓
DB Session
```

각 Pool의 Capacity 단위를 구분한다.

---

# 40. Request Thread vs Worker

## FIG-TA-39. Thread Split

```text
HTTP Request Thread
        │ submit
        ▼
Worker Thread
        │
        ▼
Transaction / DB
```

PDMG의 Timeout ON Runtime에서는 이 Thread 경계가 중요하다.

---

# 41. Worker Pool

## FIG-TA-40. Worker Capacity

```text
Worker Pool
│
├─ Core / Max
├─ Active
├─ Queue
├─ Reject
└─ Task Time
```

Current PDMG Snapshot:

```text
Worker = 20
Queue  = 100
Timeout = 5000ms
```

### 태그

```text
[AS-IS SNAPSHOT]
```

Target Capacity로 자동 승격하지 않는다.

---

# 42. HikariCP Architecture

## FIG-TA-41. Connection Pool

```text
Business Thread
    ↓
Hikari Pool
    ├─ Active
    ├─ Idle
    ├─ Pending
    └─ Max
    ↓
DB Session
```

---

# 43. Thread / Hikari / DB 관계

## FIG-TA-42. Capacity Chain

```text
Tomcat Busy Thread
        ↓
Worker Active / Queue
        ↓
Hikari Active / Pending
        ↓
DB Sessions / SQL Wait
```

병목은 한 계층의 숫자만 크게 늘려 해결하지 않는다.

---

# 44. Database Technical Architecture

## FIG-TA-43. DB Role

```text
Application Runtime
      ↓
DataSource
      ↓
Connection Pool
      ↓
DB Service
      ↓
DB Instance / Appliance
      ↓
Storage
```

---

# 45. RDW / ADW Technical Separation

## FIG-TA-44. Data Workload Separation

```text
RDW
= Operational / Near Real-time Workload

ADW
= Analytical / Mart / Heavy Query Workload
```

```text
RDW
   ──ETL──►
ADW
```

---

# 46. DB Local HA와 DR

## FIG-TA-45. HA vs DR

```text
DB Local HA
Node / Instance Failure
       ↓
Local Service Continuity

          ≠

DB DR
Center Failure
       ↓
Remote Recovery
```

---

# 47. RAC / Cluster 원칙

## FIG-TA-46. DB Cluster

```text
DB Service
  ↓
Cluster / RAC
  ├─ Node A
  └─ Node B
```

정확한 DB Node 수/Role은 DB Inventory로 확정한다.

---

# 48. CDC / OGG Architecture

## FIG-TA-47. CDC Physical Route

```text
Source DB
  ↓
Capture
  ↓
Trail / Queue
  ↓
Relay / Network
  ↓
Apply
  ↓
RDW
```

운영 핵심:

```text
Capture Position
Lag
Apply Error
Restart Position
```

---

# 49. CDC SLA

## FIG-TA-48. Freshness

```text
Source Commit
    ↓
Capture
    ↓
Transport
    ↓
Apply
    ↓
Consumer Visible
```

기존 자료의 `30s vs 3s` 같은 값 충돌은 `[CONFLICT]`로 유지하고 측정지점을 먼저 정의한다.

---

# 50. ETL Technical Architecture

## FIG-TA-49. ETL

```text
Source
  ↓
Extract
  ↓
Transform
  ↓
Load
  ↓
RDW / ADW / Target
```

---

# 51. Online vs ETL Resource Isolation

## FIG-TA-50. Workload Isolation

```text
Online Peak
  └─ Online Compute / DB Resource

Large ETL / Batch
  └─ Batch / ETL Resource Window
```

### 원칙

```text
Online SLA
≠
Batch Throughput
```

---

# 52. Event / Streaming Technical Architecture

## FIG-TA-51. Event Platform

```text
Producer
  ↓
Event Broker
  ↓
Partition / Topic
  ↓
Consumer Group
  ↓
Business / Data Target
```

---

# 53. Event Platform 관측항목

```text
Produce Rate
Consume Rate
Lag
Partition
Retry
DLQ
Consumer Health
```

---

# 54. File Transfer Technical Architecture

## FIG-TA-52. File Runtime

```text
Producer
  ↓
File Landing
  ↓
MFT / FOS
  ↓
Transfer
  ↓
Validate
  ↓
Consumer
```

---

# 55. Batch Technical Architecture

## FIG-TA-53. Batch Platform

```text
Scheduler
  ↓
Batch Runtime
  ↓
Job
  ↓
Step
  ↓
DB / File / Interface
```

---

# 56. Scheduler / Job 분리

```text
Scheduler
= 언제 실행할 것인가?

Batch Framework
= 무엇을 어떤 Step으로 실행할 것인가?
```

---

# 57. Integration Technology Architecture

## FIG-TA-54. Integration Capability

```text
Online → MCA / MCI / API
Event  → Broker
CDC    → CDC Platform
Bulk   → ETL
File   → FOS / MFT
```

Interface Architecture가 Contract와 선택정책을 소유하고,
Technical Architecture는 **그 Contract를 실행하는 Platform/Runtime**을 소유한다.

---

# 58. Technical Architecture와 Interface Architecture 경계

## FIG-TA-55. TA / IA

```text
INTERFACE ARCHITECTURE
InterfaceId / Contract / Sync / Error / Retry
                 ↓
TECHNICAL ARCHITECTURE
Gateway / Broker / CDC / ETL / MFT Runtime
                 ↓
PHYSICAL
Host / Network / Port / Storage
```

---

# 59. Network Architecture

## FIG-TA-56. Network Layers

```text
External / Channel
      ↓
DNS / GSLB
      ↓
L4 / Load Balancer
      ↓
WEB Network
      ↓
WAS Network
      ↓
DB / Data Network
      ↓
Management / Backup Network
```

실제 VLAN/Subnet/ACL 값은 Network 상세설계에서 확정한다.

---

# 60. Network Security Boundary

## FIG-TA-57. Trust Boundary

```text
Untrusted
   ↓
Controlled Entry
   ↓
Semi-trusted Service
   ↓
Protected Data
```

Zone 간 최소접속 원칙을 적용한다.

---

# 61. Firewall / ACL Standard

```text
Source
  ↓
Destination
  ↓
Protocol
  ↓
Port
  ↓
Purpose
  ↓
Owner
  ↓
Approval
```

---

# 62. Port Architecture

## FIG-TA-58. Port Trace

```text
Application Config
      ↕
Port Inventory
      ↕
Firewall Rule
      ↕
Load Balancer
      ↕
Monitoring
```

불일치 시 개통 금지 후보.

---

# 63. Port Standard 원칙

### MUST

모든 서비스 Port는 Inventory로 관리한다.

### MUST NOT

```text
임의 Port 생성
→ 상시 방화벽 개통
```

---

# 64. Storage Architecture

## FIG-TA-59. Storage Domains

```text
Compute
  ↓
Application / File Storage
  ↓
DB Storage
  ↓
Backup Storage
  ↓
Archive / DR
```

각 Storage는 Workload/SLA에 맞게 구분한다.

---

# 65. Filesystem Standard

## FIG-TA-60. Filesystem

```text
OS Standard Mount
      ↓
Application Path
      ↓
Log Path
      ↓
Data / File Path
      ↓
Backup / Cleanup Policy
```

---

# 66. Filesystem Governance

```text
Path
Owner
Capacity
Retention
Backup
Cleanup
Permission
Evidence
```

---

# 67. Account Architecture

## FIG-TA-61. Account Separation

```text
Environment
  ↓
Technical Role
  ↓
Service Account
  ↓
Least Privilege
  ↓
Credential Management
  ↓
Audit
```

---

# 68. User / Service Account 구분

```text
Human Account
= 운영자 개인 식별

Service Account
= Runtime Process Identity
```

공용 개인계정 사용을 최소화한다.

---

# 69. Hostname Standard

## FIG-TA-62. Host Identity

```text
Hostname
  ↓
Organization / System
  ↓
Platform / Role
  ↓
Environment
  ↓
Sequence
```

기존 NSIGHT Physical Baseline의 고정 자리수 규칙은 실제 서버 Inventory와 대조한다.

---

# 70. Hostname의 목적

```text
Hostname
→ Role
→ Environment
→ Inventory
→ Monitoring
→ Deployment
→ Incident
```

---

# 71. Hardware Inventory

## FIG-TA-63. HW Inventory

```text
Logical Node
  ↓
Hostname
  ↓
Center / Environment
  ↓
Type
  ↓
CPU / Memory / Storage / Network
  ↓
HA / DR Pair
  ↓
Owner / Evidence
```

---

# 72. Software Inventory

## FIG-TA-64. SW Inventory

```text
Host / Node
  ↓
Software Category
  ↓
Product
  ↓
Version
  ↓
Role
  ↓
License / Support
  ↓
Owner / Evidence
```

---

# 73. TRM 분류

## FIG-TA-65. Technical Reference Model

```text
Technology Domain
│
├─ Development
├─ Runtime
├─ Middleware
├─ Database
├─ Integration
├─ Data / Analytics
├─ Security
├─ Operations
├─ DevOps
├─ Storage
└─ Infrastructure
```

---

# 74. TRM L1~L4

## FIG-TA-66. TRM Hierarchy

```text
L1 Technology Domain
   ↓
L2 Capability
   ↓
L3 Technology Category
   ↓
L4 Product / Standard Candidate
```

### 중요

L4 후보는 승인 제품을 의미하지 않을 수 있다.

---

# 75. 개발환경 TRM

## FIG-TA-67. Development Technology

```text
IDE / JDK
   ↓
SCM
   ↓
Build
   ↓
Test / Quality
   ↓
Artifact
```

---

# 76. 실행환경 TRM

## FIG-TA-68. Runtime Technology

```text
OS / Container / VM
   ↓
JDK
   ↓
WEB / WAS
   ↓
Framework / Library
   ↓
DB / Integration Client
```

---

# 77. 운영환경 TRM

## FIG-TA-69. Operations Technology

```text
Monitoring
  ↓
Logging
  ↓
APM / Trace
  ↓
Scheduler / Operations
  ↓
Backup
  ↓
Deployment / Change
```

---

# 78. Technology Lifecycle

## FIG-TA-70. Lifecycle

```text
Candidate
  ↓
Assessment
  ↓
Approved
  ↓
Adopted
  ↓
Standard
  ↓
Deprecated
  ↓
Retired
```

---

# 79. EOL / EOS 관리

```text
Product / Version
  ↓
Vendor Support
  ↓
EOL / EOS Date
  ↓
Risk
  ↓
Upgrade / Replace
```

---

# 80. Technology Debt

## FIG-TA-71. Technical Debt

```text
Unsupported Technology
       ↓
Operational Risk
       ↓
Security / Compatibility Risk
       ↓
Migration Plan
       ↓
Target Standard
```

---

# 81. Capacity Architecture

## FIG-TA-72. Capacity Inputs

```text
Users
  ↓
Concurrency
  ↓
TPS
  ↓
Response Time
  ↓
Thread
  ↓
Connection
  ↓
CPU / Memory
  ↓
Server Count
```

---

# 82. Capacity Candidate vs Fact

## FIG-TA-73. Capacity States

```text
Business Assumption
        ↓
Capacity Calculation
        ↓
Candidate Sizing
        ↓
Load Test
        ↓
Measured Evidence
        ↓
Approved Baseline
```

과거 계산값을 운영 Fact로 자동 승격하지 않는다.

---

# 83. Current Capacity Assumption Reference

기존 산정자료에서 사용한 대표 가정:

```text
6,000 branches × 6 users = 36,000 users
```

동시율과 응답시간 가정은 Baseline별 Variant가 있으므로 날짜/버전과 함께 관리한다.

---

# 84. Scale-up vs Scale-out

## FIG-TA-74. Scaling Decision

```text
Capacity Need
  ↓
Stateful?
  ↓
Parallelizable?
  ↓
License / Cost?
  ↓
Failure Domain?
  ↓
Scale-up / Scale-out
```

---

# 85. Scale-out Principle

```text
Stateless / Shareable State
      ↓
Multiple JVM / Node
      ↓
Load Balance
```

---

# 86. Scale-up Principle

```text
Large Memory / CPU Workload
      ↓
Bigger Node
```

Scale-up만 반복하면 Failure Domain이 커질 수 있다.

---

# 87. N+1 Capacity

## FIG-TA-75. Failure Capacity

```text
Normal
Node A + Node B + Node C

Node A Failure
      ↓
Node B + Node C
      ↓
SLO 유지?
```

HA는 이중화 개수보다 **장애 후 잔존 Capacity**가 중요하다.

---

# 88. Availability Architecture

## FIG-TA-76. Availability Layers

```text
Network HA
  ↓
WEB HA
  ↓
WAS HA
  ↓
DB HA
  ↓
Integration HA
  ↓
Storage / Backup
  ↓
DR
```

---

# 89. Active-Active / Active-Standby

## FIG-TA-77. HA Pattern

```text
Active-Active
A ↔ B
Both serve traffic

Active-Standby
A → service
B → ready / takeover
```

역할별로 적합한 방식이 다르다.

---

# 90. Session / State Architecture

## FIG-TA-78. State Types

```text
Application State
│
├─ JWT Access Token
├─ Refresh State
├─ HttpSession
├─ Cache
└─ DB State
```

HA/DR 설계는 State 종류별로 결정한다.

---

# 91. Session Replication

```text
Session Required?
  ├─ NO → Avoid replication
  └─ YES
       ↓
   Sticky / Replication / Shared Store
       ↓
   Failure Test
```

기존 자료의 Session 60/90분, DeltaManager 등은 `[CONFLICT]` 상태를 유지하고 최종 결정과 분리한다.

---

# 92. JWT HA

## FIG-TA-79. JWT Technical HA

```text
JWT Instance #1
JWT Instance #2
       │
       ▼
Shared / Versioned Signing Key
       ↓
kid / JWKS
       ↓
Business Verifier
```

---

# 93. JWT Key DR

```text
Main Key Set
   ↓ secure sync / managed source
DR Key Set
   ↓
Same Active kid / Verification Compatibility
```

---

# 94. DR Architecture

## FIG-TA-80. DR Components

```text
Main Center
  ↓ failure
DR Activation
  │
  ├─ Traffic
  ├─ Application
  ├─ Config
  ├─ Key / Secret
  ├─ Database
  ├─ Interface
  ├─ Batch
  └─ Monitoring
  ↓
Business Validation
```

---

# 95. RTO / RPO

## FIG-TA-81. Recovery Objective

```text
Business Service
  ↓
Technical Dependency
  ↓
RTO / RPO
  ↓
HA / Backup / Replication Design
  ↓
DR Test
  ↓
Evidence
```

정확한 RTO/RPO는 서비스별 승인값이 없으면 `[OPEN]`.

---

# 96. Backup Architecture

## FIG-TA-82. Backup / Restore

```text
Production Data
  ↓
Backup
  ↓
Backup Storage
  ↓
Retention
  ↓
Restore Test
  ↓
Business Validation
```

---

# 97. Backup ≠ Recovery

```text
Backup Success
≠
Restore Success
≠
Business Recovery Success
```

---

# 98. Security Technical Architecture

## FIG-TA-83. Security Controls

```text
Network Security
       +
Host Security
       +
Runtime Security
       +
Identity / Key
       +
Data Protection
       +
Audit
```

Security Architecture가 정책을 정의하고,
Technical Architecture는 이를 실행하는 Platform/Boundary를 구현한다.

---

# 99. Secret Architecture

## FIG-TA-84. Secret Boundary

```text
Source / Artifact
   │
   └─ Secret Reference
          ↓
Protected Secret Delivery
          ↓
Runtime
```

---

# 100. Certificate / Key Inventory

```text
Key / Certificate
  ↓
Owner
  ↓
Purpose
  ↓
Environment
  ↓
Expiry
  ↓
Rotation
  ↓
Runtime Mapping
```

---

# 101. Observability Technical Architecture

## FIG-TA-85. Observability Stack

```text
Runtime
  ↓
Metric
  +
Log
  +
Trace
  ↓
Central Collection
  ↓
Dashboard / Alert
  ↓
Runbook / Evidence
```

---

# 102. Technical Monitoring Layers

## FIG-TA-86. Monitoring Map

```text
GSLB / L4
  ↓
WEB
  ↓
Tomcat / JVM
  ↓
Worker
  ↓
Hikari
  ↓
DB / SQL
  ↓
Storage / Network
```

---

# 103. Technical Metric

```text
CPU
Memory
GC
Thread
Connection
Queue
I/O
Network
DB Wait
Disk Capacity
Replication Lag
```

---

# 104. Application Metric와 Technical Metric

## FIG-TA-87. Correlation

```text
Application Metric
ServiceId p95 / Error / TPS
          ↓ correlate
Technical Metric
JVM / Worker / Hikari / DB / Network
```

---

# 105. Runtime Evidence

## FIG-TA-88. Technical Evidence Chain

```text
Architecture Baseline
      ↓
Technical Model
      ↓
Config Baseline
      ↓
DeploymentId
      ↓
Host / JVM
      ↓
Runtime Metric / Log
      ↓
Failure / Performance Test
      ↓
Evidence
```

---

# 106. Physical Inventory SSOT

## FIG-TA-89. Inventory Set

```text
Server Inventory
Software Inventory
DB Inventory
Port Inventory
Account Inventory
Filesystem Inventory
Network Rule
HA / DR Mapping
```

---

# 107. Inventory Cross-check

## FIG-TA-90. Cross Validation

```text
Architecture
     ↓ compare
CMDB
     ↓ compare
DNS
     ↓ compare
IaaS / Virtualization
     ↓ compare
Monitoring
     ↓ compare
Backup
     ↓ compare
Deployment
```

---

# 108. Server Master Inventory

```yaml
server:
  hostname:
  logicalNode:
  systemGroup:
  environment:
  center:
  role:
  os:
  cpu:
  memory:
  storage:
  jvm:
  artifact:
  ports:
  datasource:
  haGroup:
  drPair:
  monitoringId:
  backup:
  owner:
  evidence:
```

---

# 109. Software Inventory

```yaml
software:
  category:
  product:
  version:
  vendor:
  license:
  support:
  logicalNode:
  host:
  environment:
  role:
  lifecycle:
  owner:
  evidence:
```

---

# 110. Port Inventory

```yaml
port:
  source:
  destination:
  host:
  port:
  protocol:
  purpose:
  environment:
  firewallRule:
  loadBalancer:
  owner:
  evidence:
```

---

# 111. Account Inventory

```yaml
account:
  accountId:
  accountType:
  technicalRole:
  environment:
  hostOrPlatform:
  privilege:
  credentialSource:
  owner:
  expiry:
  audit:
```

---

# 112. Filesystem Inventory

```yaml
filesystem:
  host:
  mount:
  purpose:
  capacity:
  owner:
  permission:
  retention:
  backup:
  alertThreshold:
```

---

# 113. Technical Configuration Baseline

## FIG-TA-91. Config Baseline

```text
Expected Technical Config
        ↓ compare
Actual Runtime Config
        ↓
PASS / DRIFT
```

대표:

```text
JVM
Tomcat
Worker
Hikari
DB
Port
Session
JWT Key
Logging
```

---

# 114. Config Drift

```text
Architecture / Approved Config
             ↓
           Actual
             ↓
          DRIFT
```

Critical Config는 자동탐지 대상으로 올린다.

---

# 115. Technical Architecture Rule

## FIG-TA-92. Principle → Rule

```text
Technical Principle
       ↓
Architecture Rule
       ↓
Inventory / Config / Runtime Scanner
       ↓
PASS / FAIL
```

---

# 116. Logical / Physical Rule

```text
R-TA-LOGICAL-PHYSICAL-MAPPING
R-TA-NODE-RESPONSIBILITY
R-TA-ENVIRONMENT-SEPARATION
```

---

# 117. Runtime Rule

```text
R-TA-JVM-INVENTORY
R-TA-THREAD-POOL
R-TA-CONNECTION-POOL
R-TA-TIMEOUT-HIERARCHY
```

---

# 118. Network Rule

```text
R-TA-PORT-INVENTORY
R-TA-FW-LB-PORT-ALIGNMENT
R-TA-DIRECT-WAS-CONTROL
```

---

# 119. DB Rule

```text
R-TA-DB-ROLE
R-TA-RDW-ADW-ISOLATION
R-TA-CDC-LAG
R-TA-DB-HA
```

---

# 120. HA / DR Rule

```text
R-TA-NPLUS1
R-TA-HA-FAILOVER
R-TA-DR-ARTIFACT
R-TA-DR-CONFIG
R-TA-DR-KEY
R-TA-RESTORE-EVIDENCE
```

---

# 121. Technology Lifecycle Rule

```text
R-TA-APPROVED-TECH
R-TA-EOL-EOS
R-TA-LICENSE
R-TA-SUPPORT
```

---

# 122. Technical Architecture Test

## FIG-TA-93. Test Stack

```text
Config Test
   ↓
Connectivity Test
   ↓
Load Test
   ↓
Stress / Soak
   ↓
Node Failure
   ↓
DB Failure
   ↓
Network Failure
   ↓
DR / Restore
   ↓
Runtime Evidence
```

---

# 123. Connectivity Test

```text
Source
  ↓
DNS / Route
  ↓
LB / Firewall
  ↓
Port
  ↓
Target
```

---

# 124. Performance Test

## FIG-TA-94. Performance Drill-down

```text
Load
  ↓
Response p95
  ↓
Tomcat Busy
  ↓
Worker Queue
  ↓
Hikari Pending
  ↓
DB / SQL Wait
```

---

# 125. Failure Test

## FIG-TA-95. Failure Scenario

```text
Node Failure
  ↓
Detect
  ↓
Isolate
  ↓
Reroute
  ↓
Residual Capacity
  ↓
Business Validation
  ↓
Evidence
```

---

# 126. 8대 장애 시나리오 관점

```text
AP VM
AP Group
RDW
ADW
Event Platform
CDC Relay
Integration
Center DR
```

실제 시스템별 Failure Catalog로 구체화한다.

---

# 127. Technology Change Governance

## FIG-TA-96. Change

```text
Technology Change
  ↓
Impact
  ↓
Compatibility
  ↓
Security / Support
  ↓
Capacity
  ↓
Test
  ↓
Deployment
  ↓
Runtime Evidence
  ↓
Baseline Update
```

---

# 128. New Technology Introduction

```text
Need
  ↓
TRM Candidate
  ↓
PoC / Assessment
  ↓
Security / License / Support
  ↓
Architecture Review
  ↓
ADR
  ↓
Approved Standard
```

---

# 129. Deprecated Technology Removal

```text
Deprecated
  ↓
Dependency Scan
  ↓
Migration
  ↓
Parallel Verification
  ↓
Remove
  ↓
Evidence
```

---

# 130. Technical Architecture와 Application Architecture 관계

## FIG-TA-97. AA ↔ TA

```text
APPLICATION ARCHITECTURE
Application / Program / ServiceId
          ↓ requires
TECHNICAL ARCHITECTURE
WEB / WAS / JVM / DB / Integration / HA
          ↓ deployed as
PHYSICAL RUNTIME
```

Application Architecture는 **무엇을 실행할 것인가**,
Technical Architecture는 **어떤 기술구조로 실행할 것인가**를 정의한다.

---

# 131. Technical Architecture와 Data Architecture 관계

## FIG-TA-98. DA ↔ TA

```text
DATA ARCHITECTURE
Subject / Model / Ownership / Lifecycle
            ↓
TECHNICAL ARCHITECTURE
RDW / ADW / DB / CDC / ETL / Storage
```

---

# 132. Technical Architecture와 Security Architecture 관계

## FIG-TA-99. SA ↔ TA

```text
SECURITY ARCHITECTURE
Policy / Trust / Authentication / Authorization
             ↓
TECHNICAL ARCHITECTURE
Firewall / TLS / Key Store / Runtime Security / Audit Platform
```

---

# 133. Technical Architecture와 Operations 관계

## FIG-TA-100. OPS ↔ TA

```text
TECHNICAL ARCHITECTURE
JVM / Pool / DB / Network / Storage
        ↓
OPERATIONS
Metric / Log / Alert / Runbook
        ↓
Runtime Evidence
```

---

# 134. Technical Architecture와 Development Standard 관계

## FIG-TA-101. TA → Dev Standard

```text
Technical Baseline
  ↓
JDK / Runtime / Port / Pool / Security / Config
  ↓
Development Standard
  ↓
Source / application.yml / server.xml / build
```

---

# 135. PDMG Technical Reference Position

## FIG-TA-102. PDMG Reference

```text
NSIGHT Technical Target
          │
          │ compare
          ▼
PDMG Current Runtime
│
├─ Spring Boot / Java
├─ pdmg-fw
├─ Tomcat / JVM Runtime
├─ Worker Timeout
├─ Hikari / MyBatis
├─ JWT
└─ Logging / ImageLog
          │
          ▼
CONFORM / GAP / DRIFT
```

---

# 136. PDMG에서 확인되는 Technical Evidence

```text
Java 21
Spring Boot 3.5.14
Gradle Multi-project
TCF enabled
Timeout enabled
Worker 20
Queue 100
Timeout 5000ms
Hikari/MyBatis/JDBC path
```

### 태그

```text
[AS-IS REFERENCE]
```

---

# 137. PDMG에서 Target 승격 전 검증할 항목

## FIG-TA-103. Promotion Review

```text
PDMG Technical Pattern
      ↓
Capacity Fit
      ↓
HA / DR Fit
      ↓
Security Fit
      ↓
Operations Fit
      ↓
Compatibility
      ↓
ADR
      ↓
NSIGHT Technical Standard
```

---

# 138. Technical Architecture Master Inventory

## FIG-TA-104. Master Technical Trace

```text
Logical Node
  ↓
Technology Component
  ↓
Product / Version
  ↓
Host / VM
  ↓
JVM / Process
  ↓
Artifact
  ↓
Port / DataSource
  ↓
Monitoring
  ↓
HA / DR
  ↓
Evidence
```

---

# 139. 신규 Technical Node 설계 Route

## FIG-TA-105. New Node

```text
Application / NFR Need
       ↓
Technical Responsibility
       ↓
Logical Node
       ↓
Runtime Type
       ↓
Capacity
       ↓
Technology Component
       ↓
Environment / Center
       ↓
Physical Resource
       ↓
Network / Security
       ↓
HA / DR / Monitoring
       ↓
Inventory / Evidence
```

---

# 140. 신규 기술도입 Checklist

```text
[ ] Capability?
[ ] Existing Standard로 해결 가능한가?
[ ] Product/Version?
[ ] License?
[ ] Support?
[ ] Security?
[ ] Compatibility?
[ ] HA/DR?
[ ] Capacity?
[ ] Monitoring?
[ ] Backup?
[ ] Deployment?
[ ] ADR?
```

---

# 141. 서버 설계 Checklist

```text
[ ] Logical Node?
[ ] Environment?
[ ] Center?
[ ] Hostname?
[ ] CPU/Memory?
[ ] Storage?
[ ] OS?
[ ] Runtime?
[ ] Port?
[ ] Account?
[ ] Monitoring?
[ ] Backup?
[ ] HA/DR?
[ ] Owner?
```

---

# 142. WAS/JVM Checklist

```text
[ ] JVM Count?
[ ] WAR Mapping?
[ ] Heap?
[ ] Native Memory?
[ ] GC?
[ ] Thread?
[ ] Worker?
[ ] Hikari?
[ ] Timeout?
[ ] Session?
[ ] Health?
[ ] Metrics?
```

---

# 143. DB Checklist

```text
[ ] DB Role?
[ ] RDW/ADW?
[ ] HA?
[ ] DR?
[ ] Connection Pool?
[ ] Query Timeout?
[ ] Backup?
[ ] Restore?
[ ] CDC?
[ ] Monitoring?
```

---

# 144. Network Checklist

```text
[ ] Zone?
[ ] Source/Destination?
[ ] Protocol?
[ ] Port?
[ ] L4?
[ ] Firewall?
[ ] TLS?
[ ] Direct Access Exception?
[ ] Monitoring?
```

---

# 145. Capacity Checklist

```text
[ ] User / TPS Assumption?
[ ] Concurrency?
[ ] p95 Target?
[ ] Thread?
[ ] Connection?
[ ] CPU?
[ ] Memory?
[ ] N+1?
[ ] Load Test?
[ ] Evidence?
```

---

# 146. HA/DR Checklist

```text
[ ] Failure Unit?
[ ] Detection?
[ ] Reroute?
[ ] State?
[ ] Residual Capacity?
[ ] Artifact Sync?
[ ] Config Sync?
[ ] Key Sync?
[ ] Data Sync?
[ ] RTO/RPO?
[ ] Test?
[ ] Evidence?
```

---

# 147. Technical Architecture 금지패턴

## FIG-TA-106. Anti-patterns

```text
Logical Node 없는 서버 신설
Technology Role 없는 제품 도입
Module = Server로 간주
WAS Server = JVM = WAR로 간주
Port Inventory 없는 방화벽 개통
Unlimited Query Timeout
Online / Batch 무통제 자원혼재
Backup만 있고 Restore Test 없음
HA 장비는 있으나 Failure Test 없음
TRM 후보를 승인제품으로 표시
Manual Production Config Drift
```

---

# 148. Technical Architecture Go-Live Blocker

## FIG-TA-107. Go-Live Block

```text
Logical↔Physical Mapping 없음
       OR
Critical Port/Network 미확정
       OR
Unknown Runtime Config
       OR
Unknown TX/Timeout Critical Path
       OR
HA Failure Test 없음
       OR
DR/Restore Evidence 없음
       OR
Critical Security Technical GAP
       OR
Monitoring 없음
       ↓
GO-LIVE BLOCK
```

---

# 149. Technical Architecture GAP Register

## TEXT ARCHITECTURE — GAP Lifecycle

```text
Target Technical Architecture
        ↓ compare
Actual Infrastructure / Config
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
| GAP-TA-01 | Logical Node→Physical 전수 Mapping 미완료 | Trace |
| GAP-TA-02 | Production Host/JVM/WAR Manifest 미완료 | Deployment |
| GAP-TA-03 | HW/SW Inventory 최신성 확인 필요 | Inventory |
| GAP-TA-04 | Port/FW/LB 전수 정합 자동화 미완료 | Network |
| GAP-TA-05 | Query/TX Timeout 확정 미완료 | Runtime |
| GAP-TA-06 | Capacity Candidate→Measured Baseline 전환 필요 | Performance |
| GAP-TA-07 | Session 정책 60/90 Conflict | HA |
| GAP-TA-08 | JWT Key HA/DR 정합 보완 | Security |
| GAP-TA-09 | CDC SLA Conflict | Data Runtime |
| GAP-TA-10 | DR RTO/RPO 서비스별 확정 필요 | DR |
| GAP-TA-11 | Restore Evidence 자동연계 미완료 | Recovery |
| GAP-TA-12 | TRM Approved/Proposed 상태 정리 필요 | Technology |
| GAP-TA-13 | EOL/EOS Lifecycle Registry 보강 | Lifecycle |
| GAP-TA-14 | Runtime Config Drift 자동화 미완료 | Operations |
| GAP-TA-15 | pdmg-om 실제 Runtime 확인 필요 | Operations |

---

# 150. Technical Architecture Risk Register

## TEXT ARCHITECTURE — Risk Lifecycle

```text
Technical Cause
  ↓
Failure / Capacity / Security Event
  ↓
Business Impact
  ↓
Severity
  ↓
Mitigation / Recovery
  ↓
Runtime Evidence
```

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-TA-01 | Logical/Physical 혼동 | High |
| RISK-TA-02 | JVM/WAR/Host Mapping 오류 | Critical |
| RISK-TA-03 | Thread/Pool 과대설정 연쇄병목 | Critical |
| RISK-TA-04 | DB Connection Exhaustion | Critical |
| RISK-TA-05 | Timeout Late Commit | Critical |
| RISK-TA-06 | Online/Batch 자원경합 | High |
| RISK-TA-07 | Port/FW/LB Drift | Critical |
| RISK-TA-08 | JWT Key Instance 불일치 | Critical |
| RISK-TA-09 | DB/CDC Lag 미탐지 | High |
| RISK-TA-10 | DR Artifact/Config/Key 불일치 | Critical |
| RISK-TA-11 | Restore 미검증 | Critical |
| RISK-TA-12 | Unsupported Technology | High |
| RISK-TA-13 | TRM 후보를 표준으로 오판 | High |
| RISK-TA-14 | Manual Config Drift | Critical |

---

# 151. Technical Architecture ADR 후보

## FIG-TA-108. ADR Areas

```text
Technical Decision
│
├─ Runtime Platform
├─ VM / Bare Metal / Container
├─ WEB / WAS Pattern
├─ JVM / Pool Standard
├─ DB / HA
├─ CDC / ETL
├─ Network Boundary
├─ Session / State
├─ JWT Key Platform
├─ Backup / DR
├─ TRM / Lifecycle
└─ Monitoring
```

대표 ADR:

```text
ADR-TA-01 Logical Technical Node Standard
ADR-TA-02 Runtime Platform Standard
ADR-TA-03 WEB/WAS/JVM Deployment Pattern
ADR-TA-04 Thread/Pool Capacity Standard
ADR-TA-05 DB HA/DR
ADR-TA-06 CDC Technical Platform
ADR-TA-07 Online/Batch Resource Isolation
ADR-TA-08 Network/Port Governance
ADR-TA-09 Session/State
ADR-TA-10 JWT Key Management
ADR-TA-11 Backup/Restore
ADR-TA-12 TRM Lifecycle
ADR-TA-13 Runtime Config Drift
```

---

# 152. Technical Architecture Review Gate

## FIG-TA-109. Review

```text
Logical Responsibility?
       ↓
Technical Node?
       ↓
Runtime Stack?
       ↓
Physical Mapping?
       ↓
Network / Data?
       ↓
Capacity?
       ↓
HA / DR?
       ↓
Security?
       ↓
Inventory?
       ↓
Monitoring / Evidence?
       ↓
PASS / GAP
```

---

# 153. Technical Architecture Completion Gate

## FIG-TA-110. Completion

```text
Technical Principles Defined?
   ↓ YES
Zone / Node Defined?
   ↓ YES
Technology Components Defined?
   ↓ YES
Runtime Platform Defined?
   ↓ YES
Physical Mapping Complete?
   ↓ YES
DB / Network / Storage Defined?
   ↓ YES
Capacity / HA / DR Defined?
   ↓ YES
System Standards Defined?
   ↓ YES
TRM / Inventory Current?
   ↓ YES
Monitoring / Runtime Evidence?
   ↓ YES
TECHNICAL ARCHITECTURE PASS
```

---

# 154. 별첨 A Application Architecture와의 최종 연결

## FIG-TA-111. AA → TA → Runtime

```text
별첨 A
APPLICATION ARCHITECTURE
Application / Program / ServiceId
          ↓
별첨 B
TECHNICAL ARCHITECTURE
Node / Platform / Runtime / Resource
          ↓
PHYSICAL / OPERATIONS
Host / JVM / Network / DB / Evidence
```

---

# 155. 최종 Technical Architecture 지도

## FIG-TA-112. Final TA Map

```text
APPLICATION / DATA / INTERFACE NEED
                ↓
────────────────────────────────────────
TECHNICAL PRINCIPLE
Responsibility / Isolation / Standard
                ↓
────────────────────────────────────────
LOGICAL TECHNICAL
Zone → Node → Component
                ↓
────────────────────────────────────────
RUNTIME PLATFORM
GSLB → L4 → WEB → WAS/JVM → Pool
                ↓
Integration / Event / CDC / ETL / Batch
                ↓
DB / RDW / ADW / Storage
                ↓
────────────────────────────────────────
PHYSICAL
Center → Host/VM → Port → Filesystem
                ↓
────────────────────────────────────────
NFR
Capacity → HA → DR → Security
                ↓
────────────────────────────────────────
GOVERNANCE
TRM → Inventory → Config → Monitoring
                ↓
Runtime Evidence → Drift / ADR → Baseline
```

---

# 156. Definition of Done

## TEXT ARCHITECTURE — Technical Architecture DoD

```text
Principle
  ↓
Logical Technical
  ↓
Runtime Platform
  ↓
Physical Infrastructure
  ↓
NFR / HA / DR
  ↓
Standard / Inventory
  ↓
Runtime Evidence
  ↓
TECHNICAL ARCHITECTURE DoD
```

## Logical / Technical
- [x] Technical Architecture 정의
- [x] TA vs Infra vs TRM 구분
- [x] IT Zone
- [x] Logical Node
- [x] Technical Capability / Component
- [x] Environment / Runtime 구분

## Runtime / Physical
- [x] GSLB/L4/WEB/WAS
- [x] Server/JVM/WAR
- [x] Thread/Worker/Hikari
- [x] DB/RDW/ADW
- [x] CDC/ETL/Event/File/Batch
- [x] Network/Storage

## Standards
- [x] Hostname
- [x] Filesystem
- [x] Account
- [x] Port
- [x] HW/SW Inventory
- [x] TRM / Lifecycle

## NFR
- [x] Capacity
- [x] Scale
- [x] HA
- [x] Session/State
- [x] DR / RTO / RPO
- [x] Backup/Restore
- [x] Security

## Operations / Governance
- [x] Monitoring
- [x] Runtime Evidence
- [x] Config Drift
- [x] Rule/Test
- [x] GAP/RISK/ADR
- [x] Go-Live Gate

**TECHNICAL ARCHITECTURE 별첨 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. Logical Node→Physical Host 전수 Mapping
2. Production Host/JVM/WAR Deployment Manifest
3. 최신 HW/SW/DB/Port Inventory 승인
4. Query/TX/JDBC Timeout 실제값 승인
5. Capacity Load Test Evidence 반영
6. Session/Replication Conflict 해소
7. JWT Key HA/DR 구조 확정
8. CDC Freshness SLA 확정
9. 서비스별 RTO/RPO 확정
10. DR/Restore Test Evidence 확보
11. TRM Approved/Proposed/Deprecated 상태 확정
12. EOL/EOS Lifecycle Registry 운영
13. Runtime Config Drift 자동탐지
14. Technical Rule CI/Operations Gate 적용

---

# 157. 장 최종 결론

## FIG-TA-113. Technical Architecture Final

```text
Application / Data Requirement
           ↓
Technical Capability
           ↓
Logical Node / Component
           ↓
Runtime Platform
           ↓
Physical Resource
           ↓
Capacity / HA / DR / Security
           ↓
System Standard / TRM / Inventory
           ↓
Monitoring / Runtime Evidence
           ↓
Technical Architecture Baseline
```

> **Technical Architecture의 본질은 서버와 제품을 나열하는 것이 아니라, Application과 Data가 요구하는 실행 Capability를 논리 기술구조와 Runtime Platform으로 정의하고 이를 물리 Resource·NFR·운영 Evidence까지 일관되게 연결하는 것이다.**

> **Application Architecture가 “무엇을 실행할 것인가”를 정의한다면 Technical Architecture는 “그 Application을 어떤 기술구조, Runtime, Resource, 연결, 이중화 및 운영체계로 실행할 것인가”를 정의한다.**

> **NSIGHT의 Technical Architecture는 Logical → Physical → DB → System Standard → Runtime Evidence의 연결이 끊기지 않아야 하며, PDMG의 Java/Spring/Tomcat/Worker/Hikari/JWT 등은 이 Target을 검증하는 AS-IS Reference로 사용한다.**
