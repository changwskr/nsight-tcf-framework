# NSIGHT 시스템간 인터페이스 원칙 및 추진 방안
## TOP-DOWN TEXT 아키텍처 기반 프로젝트 추진 기준안

> **문서 성격**: 차세대 정보계 시스템간 인터페이스 Architecture Working Baseline  
> **작성 기준**:  
> 1. `시스템간 인터페이스 방안_20080306_v1.00.doc`  
> 2. `시스템간 인터페이스 방안_20080303[1].v1.1.ppt`  
> 3. `빅픽처_전체시스템아키텍처구조정의_TOPDOWN_강화분석.md`  
>
> **작성 방식**: 전체 구조 → 인터페이스 도메인 → 원칙 → 유형선택 → 패턴 → 계약/통제 → 운영/검증 → 프로젝트 추진 순서의 **TOP-DOWN**  
> **표현 구분**:
>
> - `SOURCE` : 과거 인터페이스 방안 또는 Big Picture에서 직접 확인되는 기준
> - `ANALYSIS` : 소스의 의도를 현재 차세대 정보계 구조에 맞게 해석한 내용
> - `TO-BE` : 본 프로젝트에서 적용할 것을 제안하는 기준
> - `확인 필요` : 선행자료만으로 확정할 수 없어 설계·의사결정이 필요한 항목

---

# 0. 문서 목적

본 문서의 목적은 과거의 **전행 시스템간 인터페이스 표준화 원칙**을 그대로 기술적으로 복제하는 것이 아니라, 그 안에 있는 핵심 Architecture Intent를 현재 NSIGHT 차세대 정보계 Big Picture에 맞게 재구성하여 다음 프로젝트 활동의 상위 기준선으로 사용하는 데 있다.

```text
과거 전행 인터페이스 방안
        │
        │  핵심 원칙 추출
        │  - Point-to-Point 최소화
        │  - 중앙 통제
        │  - 표준 전문/프로토콜
        │  - SYNC/ASYNC 구분
        │  - 파일/대량데이터 분리
        │  - 예외 승인
        │
        ▼
현재 NSIGHT Big Picture
        │
        │  목적별 Integration Mechanism
        │
        ├─ MCA
        ├─ API Gateway / API / JSON
        ├─ Kafka / Event
        ├─ CDC
        ├─ ETL
        ├─ FOS / MFT
        └─ JDBC / Polling / File
        │
        ▼
프로젝트 인터페이스 Architecture Baseline
        │
        ├─ 원칙
        ├─ 선택기준
        ├─ 표준계약
        ├─ Runtime 통제
        ├─ 운영/추적
        ├─ 테스트
        └─ 변경/예외 관리
```

---

# 1. L0 — 인터페이스 아키텍처 한 문장 정의

> **NSIGHT의 시스템간 인터페이스는 모든 연계를 하나의 기술로 통일하지 않고, 계정거래·온라인 서비스·이벤트·변경데이터·분석데이터·파일·대외연계라는 업무 목적에 따라 MCA, API Gateway, Kafka, CDC, ETL, FOS/MFT 등의 표준 Integration Mechanism을 선택하고, 모든 연계를 계약·보안·Timeout·추적·재처리·정합성·변경관리까지 포함하는 관리 가능한 Architecture Object로 통제한다.**

---

# 2. L0 — 전체 인터페이스 MAIN TEXT 아키텍처

```text
┌──────────────────────────────────────────────────────────────────────────────┐
│                          [L0] Consumer / Producer                            │
│                                                                              │
│ Channel / Marketing / BI / Core / Related / Big Data / External            │
└────────────────────────────────────┬─────────────────────────────────────────┘
                                     │
                                     ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                       [L1] Interface Entry / Boundary                        │
│                                                                              │
│   MCA          API Gateway         Collector         MFT/FOS                │
│   Transaction  Online Service      Event Entry       File Boundary          │
└───────────────┬───────────────┬───────────────┬───────────────┬───────────────┘
                │               │               │               │
                ▼               ▼               ▼               ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                    [L2] Integration Mechanism by Purpose                     │
│                                                                              │
│  Transaction       Service/API       Event        Data Sync      Data/File  │
│  MCA               API/JSON          Kafka        CDC            ETL/MFT    │
└───────────────┬───────────────┬───────────────┬───────────────┬───────────────┘
                │               │               │               │
                ▼               ▼               ▼               ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                         [L3] Target Processing                               │
│                                                                              │
│ Core / Related Service / Marketing / BI / EBM / RDW / ADW / External       │
└────────────────────────────────────┬─────────────────────────────────────────┘
                                     │
                                     ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                         [L4] Runtime Control                                 │
│                                                                              │
│ Auth / Validation / Timeout / Retry / Idempotency / Error / DLQ / Replay   │
└────────────────────────────────────┬─────────────────────────────────────────┘
                                     │
                                     ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                      [L5] Trace / Evidence / Governance                      │
│                                                                              │
│ Interface ID / ServiceId / GUID / Tx ID / Event ID / Batch ID              │
│ Log / Metric / Trace / Audit / Reconciliation / SLA / Version / Owner      │
└──────────────────────────────────────────────────────────────────────────────┘
```

### 핵심 해석

```text
연결(Connection)
   ↓
계약(Contract)
   ↓
실행(Runtime)
   ↓
통제(Control)
   ↓
증적(Evidence)
   ↓
변경(Governance)
```

인터페이스는 단순한 시스템 간 연결선이 아니라 이 전체 구조를 포함해야 한다.

---

# 3. L1 — 과거 인터페이스 방안에서 계승할 Architecture Intent

과거 문서의 주요 내용은 다음과 같이 해석한다.

## 3.1 Point-to-Point 최소화

`SOURCE`  
과거 방안은 Point-to-Point가 구축은 쉽지만 연계 수 증가에 따라 장애 가능성과 유지보수 비용이 증가한다고 보고, 데이터 전송 보증·정합성·통합운영 측면에서 Hub & Spoke를 지향하였다.  
(참조: `시스템간 인터페이스 방안_20080306_v1.00.doc`, p.9)

```text
[과거 문제]
System A ─────────▶ System B
   │  ╲               ▲
   │    ╲             ╱
   ▼      ▼         ╱
System C ───────▶ System D
   ╲               ╱
     ─────────────

연계 증가
→ 연결선 증가
→ 변경 영향 증가
→ 장애 영향 분석 어려움
→ 통합 운영 불가
```

`ANALYSIS`

현재는 이를 **단일 EAI Hub**로 해석하지 않는다.

```text
[현재 해석]

                     Integration by Purpose
                             │
       ┌─────────────────────┼──────────────────────┐
       ▼                     ▼                      ▼
 Transaction Hub        Service Hub             Event Hub
     MCA               API Gateway               Kafka
       │                     │                      │
       └──────────────┬──────┴──────┬──────────────┘
                      ▼             ▼
                  Data Hub       File Hub
                  CDC/ETL        MFT/FOS
```

즉 **“Point-to-Point 최소화”는 유지하되, “Hub는 업무 목적별로 분리”**한다.

---

## 3.2 채널·시스템·대외연계를 표준 경계로 통제

`SOURCE`

과거 문서는 채널은 MCI, 시스템도메인 간 및 대외기관은 EAI를 통한 표준 연계를 기본 원칙으로 정의하였다.  
(참조: p.13~15)

`TO-BE`

현재는 다음과 같이 재정의한다.

```text
채널 계정거래
    ↓
   MCA
    ↓
  Core

정보계 Online
    ↓
Web / Application
    ↓
API Gateway
    ↓
Related Service

고객행동 Event
    ↓
Collector
    ↓
Kafka
    ↓
EBM / Consumer

대량/분석 Data
    ↓
CDC / ETL
    ↓
RDW / ADW

File / External
    ↓
MFT / FOS / 대외 MCA
    ↓
External / Affiliate
```

---

## 3.3 서비스는 서비스로 연계

`SOURCE`

과거 EAI 방안은 실시간으로 Source DB를 직접 Read/Write하는 서비스-to-DB, DB-to-Service, DB-to-DB 방식이 데이터 관리 정책과 부하 측면에서 문제가 있으므로 **서비스-to-서비스를 기본**으로 제한하였다.  
(참조: p.21)

`TO-BE`

```text
[원칙]

Application A
     │
     ▼
Service Contract
     │
     ▼
API Gateway / MCA / Event
     │
     ▼
Application B

[금지]

Application A
     │
     ├──────────────▶ B DB 직접 DML
     └──────────────▶ DB-Link
```

단, Big Picture에 존재하는 `JDBC`는 **자기 책임 데이터 또는 승인된 Data Platform 접근**으로 범위를 한정한다.

---

## 3.4 Online과 대량/파일의 경로를 분리

`SOURCE`

과거 문서는 전문 온라인 거래와 파일 전송을 분리하고, 파일을 EAI에 무조건 경유시키는 것이 EAI 부하와 온라인 성능 저하를 유발할 수 있다고 정의하였다.  
(참조: p.14, p.19)

`TO-BE`

```text
Online Service
→ API / MCA

Event
→ Kafka

Change Data
→ CDC

Analytical / Bulk
→ ETL

File
→ MFT / FOS
```

**온라인 플랫폼을 대량 데이터 전송 수단으로 사용하지 않는다.**

---

## 3.5 SYNC와 ASYNC를 업무 의미로 선택

`SOURCE`

과거 방안은 SYNC의 장기 대기로 인한 Lock/Hang/자원 비효율 위험을 지적하고, 처리시간이 길거나 여러 시스템을 경유하는 경우 ASYNC를 고려하도록 하였다.  
(참조: p.22~23)

`TO-BE`

```text
즉시 응답이 업무적으로 반드시 필요한가?
        │
        ├─ YES
        │    ↓
        │  SYNC 후보
        │    ↓
        │  Timeout / Dependency 수 / SLA 검증
        │
        └─ NO
             ↓
          ASYNC 우선
             ↓
       Event / Queue / Batch
```

---

# 4. L1 — NSIGHT 인터페이스 도메인 정의

Big Picture를 기준으로 인터페이스는 7개 Domain으로 분리한다.

```text
IF-D01 Transaction Integration
IF-D02 Online Service / API Integration
IF-D03 Event Integration
IF-D04 Change Data Integration
IF-D05 Analytical / Batch Data Integration
IF-D06 File / External Integration
IF-D07 Governance / Metadata Integration
```

## 4.1 Domain 구조

```text
┌───────────────────┐
│ Transaction       │
│ MCA               │
└─────────┬─────────┘
          │
┌─────────▼─────────┐
│ Service / API     │
│ API Gateway       │
└─────────┬─────────┘
          │
┌─────────▼─────────┐
│ Event             │
│ Kafka             │
└─────────┬─────────┘
          │
┌─────────▼─────────┐
│ Change Data       │
│ CDC               │
└─────────┬─────────┘
          │
┌─────────▼─────────┐
│ Analytical Data   │
│ ETL               │
└─────────┬─────────┘
          │
┌─────────▼─────────┐
│ File / External   │
│ MFT / FOS / MCA   │
└─────────┬─────────┘
          │
┌─────────▼─────────┐
│ Governance        │
│ JDBC/Polling/File │
└───────────────────┘
```

---

# 5. L2 — 인터페이스 최상위 원칙

## IF-PR-01 Purpose-Specific Integration

```text
거래
→ MCA

업무 서비스
→ API Gateway / API

이벤트
→ Kafka

변경데이터
→ CDC

분석/대량 데이터
→ ETL

파일
→ MFT / FOS

대외 거래
→ 대외 MCA / API / MFT
```

**모든 인터페이스를 REST/API 하나로 통일하지 않는다.**

---

## IF-PR-02 No Uncontrolled Point-to-Point

```text
[금지]
Source ─────────────────────▶ Target

[권고]
Source
  ↓
Approved Integration Boundary
  ↓
Target
```

예외는 Architecture Review + ADR + 운영책임 승인 후 허용한다.

---

## IF-PR-03 Service-to-Service First

```text
Application
  ↓
Service Contract
  ↓
Integration
  ↓
Application
```

타 시스템 DB에 대한 직접 Read/Write는 기본 금지한다.

---

## IF-PR-04 Transaction Isolation

```text
계정성 Transaction
        ↓
       MCA
        ↓
       Core
```

정보계 조회·분석·Event·Batch 처리와 Transaction 경로를 분리한다.

---

## IF-PR-05 Event Isolation

```text
Web/Mobile Event
      ↓
 Collector
      ↓
    Kafka
      ↓
 Consumer / EBM
```

대량 행동 이벤트를 Online WAS Request Thread에서 직접 처리하지 않는다.

---

## IF-PR-06 Data Integration Separation

```text
실시간 변경
→ CDC

대량 변환/집계
→ ETL

단순 서비스 조회
→ API/JDBC(승인 범위)
```

데이터 성격에 따라 연계 수단을 구분한다.

---

## IF-PR-07 Managed File Transfer

```text
External / Related
      ↓
    MFT/FOS
      ↓
Landing
      ↓
Validation
      ↓
ETL / Application
```

파일 연계는 전송 성공뿐 아니라 무결성·중복·재처리·완료 상태를 관리해야 한다.

---

## IF-PR-08 Async by Default for Decoupled Work

즉시 응답이 필요하지 않은 경우 비동기 방식을 우선 검토한다.

```text
Producer
  ↓
Event / Queue / Batch
  ↓
Consumer
```

---

## IF-PR-09 Controlled Sync

SYNC는 다음을 만족하는 경우에만 적용한다.

```text
즉시 응답 필요
AND
Target SLA 확보
AND
Timeout 명확
AND
장애 전파 통제
AND
Dependency Chain 최소화
```

---

## IF-PR-10 Contract First

모든 인터페이스는 구현 전에 계약이 정의되어야 한다.

```text
Interface ID
Source
Target
Purpose
Protocol
Schema
Required Fields
Code
Error
Timeout
Retry
Security
Owner
Version
SLA
```

---

## IF-PR-11 End-to-End Traceability

모든 연계는 최소 다음 식별자를 통해 추적 가능해야 한다.

```text
GUID
Transaction ID
ServiceId
Interface ID
Event ID
Batch ID
```

모든 종류에 모든 ID가 필요한 것은 아니며 업무유형에 맞는 상관관계 키를 의무화한다.

---

## IF-PR-12 Failure Containment

```text
Target 장애
  ↓
Source 전체 Hang 금지

Kafka 장애
  ↓
계정거래 영향 최소화

ADW 장애
  ↓
RDW 실시간 업무 영향 최소화

MFT 장애
  ↓
Online Service 영향 최소화
```

Failure Domain을 분리한다.

---

## IF-PR-13 Idempotency and Duplicate Control

Retry가 가능한 모든 인터페이스는 중복 처리 방지 기준을 가진다.

```text
Request
  ↓
Idempotency Key / Business Key
  ↓
Duplicate Check
  ↓
Process Once
```

---

## IF-PR-14 Versioned Change

```text
Compatible Change
→ Same Major Version

Breaking Change
→ New Version
```

무통보 필드 의미변경, 코드값 변경, 필수값 변경을 금지한다.

---

## IF-PR-15 Exception by Architecture Decision

```text
표준 미준수 요청
      ↓
사유 / 대안 / 영향 / SLA / 보안 / 운영 분석
      ↓
Architecture Review
      ↓
ADR
      ↓
승인 / 조건부 승인 / 반려
```

---

# 6. L2 — 인터페이스 방식 선택 Decision Tree

```text
[START]
   │
   ▼
업무가 계정성 Transaction인가?
   │
   ├─ YES ───────────────▶ MCA / 승인된 Transaction 경로
   │
   └─ NO
       │
       ▼
업무 기능을 즉시 호출해야 하는가?
       │
       ├─ YES ───────────▶ API Gateway / API / JSON
       │
       └─ NO
           │
           ▼
행동/상태변경 Event인가?
           │
           ├─ YES ───────▶ Kafka / Event
           │
           └─ NO
               │
               ▼
원천 DB 변경분을 실시간/준실시간 반영해야 하는가?
               │
               ├─ YES ───▶ CDC
               │
               └─ NO
                   │
                   ▼
대량 변환/집계/적재인가?
                   │
                   ├─ YES ─▶ ETL
                   │
                   └─ NO
                       │
                       ▼
파일 단위 교환인가?
                       │
                       ├─ YES ─▶ MFT / FOS
                       │
                       └─ NO
                           │
                           ▼
                    설계 검토 / 예외 ADR
```

---

# 7. L3 — 유형별 TO-BE 인터페이스 패턴

과거 문서의 전문거래 패턴을 현재 목적별 Integration Mechanism에 맞게 재해석한다.

## 7.1 Pattern P01 — 동기 Request / Response

```text
Source
  │ Request
  ▼
API Gateway / MCA
  │
  ▼
Target Service
  │ Response
  ▼
Source
```

적용:

```text
계정거래
정보조회
즉시 검증
사용자 화면 응답
```

필수 통제:

```text
Timeout
Error Mapping
Trace ID
No Unlimited Retry
Circuit/Isolation 정책
```

---

## 7.2 Pattern P02 — 단방향 Event

```text
Producer
  │
  ▼
Kafka Topic
  │
  ▼
Consumer
```

적용:

```text
고객행동
상태변경 알림
후속처리
Audit/Event
```

---

## 7.3 Pattern P03 — Fan-Out Event

과거의 “단방향 다중통신”에 대응한다.

```text
                   ┌────────▶ Consumer A
Producer ─▶ Kafka ─┼────────▶ Consumer B
                   └────────▶ Consumer C
```

원칙:

```text
Producer는 Consumer 수를 알지 않는다.
Consumer별 장애를 Producer에 직접 전파하지 않는다.
```

---

## 7.4 Pattern P04 — 비동기 Request / Reply

과거의 양방향 비동기 전송 패턴을 현재식으로 재해석한다.

```text
Requester
   │ Request(Event)
   ▼
Kafka / Async Channel
   │
   ▼
Responder
   │
   │ Response(Event)
   ▼
Reply Topic / Callback
   │
   ▼
Requester

Correlation ID 필수
```

적용:

```text
긴 처리시간
외부기관 비동기 응답
비실시간 후속 결과
```

---

## 7.5 Pattern P05 — 동기 Aggregate

과거의 “양방향 동기 집합전송”에 대응한다.

```text
                 ┌────────▶ Target A ──┐
Source ─▶ Orchestrator ─────▶ Target B ─┼─▶ Merge ─▶ Source
                 └────────▶ Target C ──┘
```

`TO-BE 원칙`

- API Gateway 자체에 복잡한 업무 Orchestration을 집중시키지 않는다.
- Aggregate 로직은 업무 Service/Orchestrator 책임으로 둔다.
- Target 일부 장애 시 Partial Result 또는 Compensation 기준을 사전 정의한다.

---

## 7.6 Pattern P06 — 순차 Orchestration

과거의 “양방향 동기 다중전송”에 대응한다.

```text
Source
  ↓
Orchestrator
  ↓
Target A
  ↓ Result A
Target B
  ↓ Result B
Target C
  ↓
Result
```

주의:

```text
Hop 증가
→ Latency 증가
→ Failure Propagation 증가
→ SYNC 장기화
```

3단계 이상 연속 호출은 비동기 전환 또는 업무 재설계를 우선 검토한다.

---

## 7.7 Pattern P07 — CDC

```text
Core DB
  ↓
Capture
  ↓
CDC Relay
  ↓
RDW SoR
  ↓
Near-real-time Use
```

필수 통제:

```text
Lag
Replay
Delete 처리
Schema Change
Source/Target Reconciliation
```

---

## 7.8 Pattern P08 — ETL

```text
Source / RDW
     ↓
Extract
     ↓
Transform
     ↓
Load
     ↓
ADW
     ↓
Mart / BI
```

필수 통제:

```text
Batch ID
Start/End
Count
Reject
Restart
Reconciliation
```

---

## 7.9 Pattern P09 — File / MFT

```text
Sender
  ↓
MFT/FOS
  ↓
Landing
  ↓
Checksum / File Name / Encoding / Record Count
  ↓
Load / Process
  ↓
Archive
```

필수 통제:

```text
Duplicate
Partial File
Checksum
Encryption
Retention
Restart
Completion Signal
```

---

# 8. L3 — SYNC / ASYNC 선택 기준

## 8.1 기본 원칙

```text
SYNC
= 결과가 현재 업무 트랜잭션을 완료하는 데 반드시 필요

ASYNC
= 결과를 나중에 받아도 업무 정합성을 유지할 수 있음
```

---

## 8.2 판단 TEXT

```text
즉시 응답 필수?
  │
  ├─ NO → ASYNC
  │
  └─ YES
      │
      ▼
Target 장애 시 Source 업무도 실패해야 하는가?
      │
      ├─ NO → ASYNC / Store-and-Forward
      │
      └─ YES
          │
          ▼
처리시간과 Dependency가 SLA 내 통제 가능한가?
          │
          ├─ NO → 업무분리 / ASYNC
          │
          └─ YES → SYNC
```

---

## 8.3 과거 기준의 활용 방법

과거 문서에는 다음과 같은 실무 기준이 있다.

```text
- 장시간 거래 → ASYNC 고려
- 3개 이상 시스템 경유 → ASYNC 고려
- 대외기관 → ASYNC 우선
```

본 프로젝트에서는 이를 **절대 수치로 복사하지 않고**, NFR/SLA를 기준으로 재확정한다.

```text
과거 Rule
   ↓
현재 NFR
   ↓
API Timeout
   ↓
End-to-End p95/p99
   ↓
Dependency Budget
   ↓
SYNC/ASYNC 확정
```

---

# 9. L3 — 프로토콜 / 데이터 형식 원칙

## 9.1 신규 Online

`TO-BE`

```text
API / JSON
HTTPS
UTF-8
Schema/Contract
```

단, 실제 표준 프로토콜·TLS·헤더는 프로젝트 보안/개발표준에서 확정한다.

---

## 9.2 Legacy Compatibility

과거 문서의 SNA LU0, X.25, TCP Socket, FML, RMI/IIOP, EUC-KR 등은 **당시 시스템 제약을 반영한 기술적 구현**이다.

현재는 다음 원칙으로 수용한다.

```text
Legacy Protocol / Encoding
        ↓
Boundary Adapter
        ↓
Canonical / Project Standard
        ↓
Internal Service
```

```text
[금지]
Legacy 특수 프로토콜/인코딩을
정보계 내부 전체로 전파
```

---

## 9.3 Encoding

`SOURCE`

과거 문서는 당시 XML parser/전문크기 제약 때문에 EUC-KR을 권장하였다.

`TO-BE`

```text
신규 API / JSON / Event
→ UTF-8 기본

Legacy / External
→ 상대 표준 준수

Boundary
→ Encoding Conversion

내부 Canonical
→ UTF-8
```

정확한 표준은 전문/데이터 표준 산출물에서 최종 승인한다.

---

# 10. L3 — 전문/메시지 Size 정책

과거 문서는 M/F LU0 32K, WAN 대역폭 등의 당시 제약을 고려하여 전문 크기를 적극 통제하였다.

현재의 핵심 원칙은 **“큰 메시지를 Online Interface로 보내지 않는다”**로 계승한다.

```text
Small Transaction Payload
→ API / MCA

Large Payload
→ File / Object / Batch

High-volume Event
→ Kafka

Bulk Data Change
→ CDC / ETL
```

`TO-BE`

```text
API Maximum Payload
Event Maximum Payload
File Threshold
Image/Binary 처리
Compression 기준
Chunking 기준
```

은 성능 BMT와 NFR을 통해 별도 수치로 확정한다.

---

# 11. L3 — DB 직접연계 정책

과거 문서는 DRDA, 직접 DB Interface, DB-Link가 편리하지만 원격 Lock, 네트워크 Pending, 성능저하, 변경결합, 패치/권한 관리 어려움 등의 위험을 상세히 지적한다.  
(참조: p.29~30)

## 11.1 기본 정책

```text
[금지]
System A
  ↓ DB-Link / Direct DML
System B DB

[권고]
System A
  ↓
Service / CDC / ETL
  ↓
System B
```

---

## 11.2 JDBC 허용 범위

Big Picture에는 JDBC가 존재하므로 JDBC 자체를 금지하지 않는다.

```text
JDBC 허용
├─ Application → 자기 소유 DB
├─ Application → 승인된 RDW/ADW Read
└─ Governance 수집용 승인 Read

JDBC 제한/금지
├─ 타 업무 DB 임의 DML
├─ 업무 Owner 미승인 Read
├─ Cross-System Transaction
└─ DB-Link 기반 분산 Join
```

---

# 12. L4 — Interface Contract 표준

모든 인터페이스는 다음 계약을 가진다.

```text
┌──────────────────────────────────────────────┐
│              Interface Contract              │
├──────────────────────────────────────────────┤
│ Interface ID                                 │
│ Name / Purpose                               │
│ Source / Target                              │
│ Owner                                        │
│ Integration Type                             │
│ Protocol / Endpoint                          │
│ Sync / Async                                 │
│ Request / Response Schema                    │
│ Header / Common Context                      │
│ Required / Optional Field                    │
│ Code / Domain                                │
│ Encoding                                     │
│ Error / Response Code                        │
│ Timeout                                      │
│ Retry                                        │
│ Idempotency                                  │
│ Security                                     │
│ SLA / Volume                                 │
│ Version                                      │
│ Effective Date                               │
│ Trace Key                                    │
└──────────────────────────────────────────────┘
```

---

# 13. L4 — 공통 Header / 추적 Context

`TO-BE 권고`

```text
Common Context
├─ interfaceId
├─ serviceId
├─ guid / correlationId
├─ transactionId
├─ sourceSystem
├─ targetSystem
├─ requestTimestamp
├─ user/channel context (필요 시)
├─ version
└─ security/audit context
```

Event:

```text
eventId
eventType
eventTime
producer
schemaVersion
correlationId
```

Batch/File:

```text
batchId
fileId
businessDate
sequence
recordCount
checksum
```

---

# 14. L4 — Timeout / Retry / Circuit / Idempotency

## 14.1 Timeout

```text
Client Timeout
   >
Gateway Timeout
   >
Downstream Timeout Budget
```

무제한 Wait를 금지한다.

---

## 14.2 Retry

```text
Retry 가능
├─ 일시적 Network 오류
├─ 일시적 5xx
└─ Idempotency 보장

Retry 금지/주의
├─ 금융 DML 중복 위험
├─ Validation Error
├─ 인증/권한 오류
└─ 명확한 Business Reject
```

---

## 14.3 Retry Storm 방지

```text
Failure
  ↓
Backoff
  ↓
Max Retry
  ↓
DLQ / Manual Recovery
```

---

## 14.4 Idempotency

```text
Request Key
  ↓
Already Processed?
  ├─ YES → Previous Result
  └─ NO  → Process → Store Result
```

---

# 15. L4 — Error Architecture

```text
Error
├─ Validation
├─ Authentication
├─ Authorization
├─ Business
├─ Dependency
├─ Timeout
├─ System
└─ Data/Reconciliation
```

표준 응답에는 다음을 구분한다.

```text
Technical Error Code
Business Error Code
User Message
Operation Message
Retryable 여부
Correlation ID
```

---

# 16. L4 — Security Boundary

```text
Channel
→ 사용자 인증 / 세션

MCA
→ 거래 전문 검증 / 거래권한

API Gateway
→ API 인증 / 인가 / ACL

Kafka
→ Producer / Consumer ACL

CDC
→ Replication Account / 최소권한

ETL
→ Source/Target 계정 분리

MFT/FOS
→ 전송계정 / 암호화 / 무결성

External
→ 인증서 / Allowlist / Network Zone
```

`TO-BE`

외부기관이 내부 DB에 직접 접근하는 구조는 허용하지 않는다.

---

# 17. L4 — Observability / Log

## 17.1 End-to-End Trace

```text
Channel
  │ GUID
  ▼
Application
  │ ServiceId / InterfaceId
  ▼
Gateway / MCA
  │ Correlation
  ▼
Target
  │
  ▼
Response / Event / Batch
```

---

## 17.2 유형별 핵심 지표

### API / MCA

```text
TPS
Latency p50/p95/p99
Error Rate
Timeout Rate
Downstream Latency
```

### Kafka / Event

```text
Produce Rate
Consume Rate
Lag
DLQ
Duplicate
Processing Latency
```

### CDC

```text
Capture Lag
Relay Lag
Apply Lag
Replay
Mismatch
```

### ETL / Batch

```text
Start / End
Window
Processed
Reject
Restart
Completion
```

### MFT / File

```text
File Count
Bytes
Transfer Time
Checksum Failure
Duplicate
Incomplete
```

---

# 18. L5 — 정합성(Reconciliation) 원칙

API는 응답 성공만으로 충분할 수 있지만, CDC/ETL/File은 반드시 Source-Target 정합성을 검증해야 한다.

```text
Source
  │
  ├─ Count
  ├─ Key Count
  ├─ Amount Sum
  │
  ▼
Transform / Transfer
  │
  ▼
Target
  │
  ├─ Count
  ├─ Reject
  ├─ Duplicate
  ├─ Missing
  │
  ▼
Reconciliation Result
```

---

# 19. L5 — 인터페이스 금지 패턴

## IF-FB-01 Channel → DB 직접접근 금지

```text
Channel ─────▶ DB
```

대신:

```text
Channel → Application → Data
```

---

## IF-FB-02 External → Internal DB 금지

```text
External → JDBC → Internal DB
```

대신:

```text
External → MCA/API/MFT → Service/Data Process
```

---

## IF-FB-03 모든 인터페이스를 REST로 통일 금지

```text
Transaction → MCA
Service     → API
Event       → Kafka
Change      → CDC
Bulk        → ETL
File        → MFT
```

---

## IF-FB-04 Online API로 대량 Batch 전달 금지

```text
API
  ↓
수십/수백 MB 대량 데이터
```

대신:

```text
ETL / File / CDC
```

---

## IF-FB-05 DB-Link / 원격 분산 Transaction 상시사용 금지

원격 Lock, Network Pending, 성능/변경결합 문제 때문이다.

---

## IF-FB-06 무제한 Retry 금지

```text
Retry → Retry → Retry → ...
```

Retry Storm과 중복거래를 유발한다.

---

## IF-FB-07 Interface ID 없는 연계 금지

운영추적과 영향분석이 불가능하다.

---

## IF-FB-08 Owner 없는 인터페이스 금지

```text
Source Owner ?
Target Owner ?
Interface Owner ?
Operation Owner ?
```

하나라도 미정이면 운영 이관하지 않는다.

---

# 20. L5 — Interface Ownership Model

| 역할 | 핵심 책임 |
|---|---|
| Source Owner | 호출/데이터 생성, 요청 계약 준수 |
| Target Owner | 처리/응답, Target SLA |
| Interface Owner | 계약, 버전, 변경, 영향도 |
| Platform Owner | MCA/API/Kafka/CDC/ETL/MFT 운영 |
| Data Owner | 데이터 의미, 코드, 품질 |
| Security Owner | 인증/권한/키/접근통제 |
| Operation Owner | 모니터링, 장애, 재처리 |
| Architecture | 표준, 예외, ADR, Baseline 승인 |

---

# 21. L5 — Interface Lifecycle

```text
[1] 요구사항 식별
      ↓
[2] Interface Inventory 등록
      ↓
[3] 유형 선택
      ↓
[4] Contract 설계
      ↓
[5] Architecture Review
      ↓
[6] 개발
      ↓
[7] Contract / Integration Test
      ↓
[8] Failure / Performance / Recovery Test
      ↓
[9] Runtime Evidence 확인
      ↓
[10] 운영 승인
      ↓
[11] 변경 / 버전 / 폐기 관리
```

---

# 22. L5 — 프로젝트 단계별 Gate

## G10 — Interface Discovery Gate

```text
시스템
Source
Target
업무목적
데이터
현재방식
빈도
볼륨
SLA
Owner
```

산출:

```text
Interface Inventory
```

---

## G20 — Classification Gate

각 인터페이스를 다음 중 하나로 확정한다.

```text
MCA
API
EVENT
CDC
ETL
FILE
GOVERNANCE
EXCEPTION
```

---

## G30 — Contract Gate

```text
Schema
Header
Code
Error
Timeout
Retry
Security
Version
SLA
```

미정 항목이 있는 인터페이스는 개발 착수하지 않는다.

---

## G40 — Design Conformance Gate

```text
P2P 여부
Direct DB 여부
SYNC 장기호출 여부
대용량 API 여부
Trace ID 여부
Owner 여부
```

표준 위반 시 ADR 필요.

---

## G50 — Runtime Test Gate

```text
정상
Timeout
Target Down
Network Error
Duplicate
Malformed Data
Retry
Replay
Restart
Reconciliation
Performance
```

---

## G60 — Operation Readiness Gate

```text
Dashboard
Alert
Runbook
Retry/Replay
Owner Contact
SLA
Capacity
DR
```

---

# 23. L6 — 주요 Runtime 시나리오

## 23.1 계정거래

```text
User
  ↓
Account Terminal
  ↓
MCA
  ↓
Core
  ↓
Response
```

검증:

```text
Transaction ID
전문 Validation
Timeout
Duplicate
Response Code
```

---

## 23.2 정보계 → 유관 시스템 Online

```text
User
  ↓
Marketing / BI
  ↓
Application
  ↓
API Gateway
  ↓
Related System
  ↓
Response
```

검증:

```text
Authentication
Interface ID
ServiceId
Timeout
Error Mapping
Trace
```

---

## 23.3 고객행동 Event

```text
Web / Mobile
  ↓
Wise Collector
  ↓
Kafka
  ↓
Behavior / EBM
  ↓
UMS
  ↓
SMS / PUSH / MAIL
```

검증:

```text
Event ID
Ordering
Lag
Retry
DLQ
Duplicate
```

---

## 23.4 Core → RDW CDC

```text
Core DB
  ↓
CDC Capture
  ↓
CDC Relay
  ↓
RDW SoR
  ↓
Near-real-time Aggregation
```

검증:

```text
Lag
Delete
Schema Change
Replay
Reconciliation
```

---

## 23.5 Source → ADW ETL

```text
Source / RDW
  ↓
ETL
  ↓
ADW SoR
  ↓
Integration / Aggregation
  ↓
Mart
  ↓
BI
```

검증:

```text
Batch ID
Window
Count
Reject
Restart
Reconciliation
```

---

## 23.6 External File

```text
External
  ↓
MFT / FOS
  ↓
Landing
  ↓
Validation
  ↓
ETL / Application
  ↓
RDW / ADW
```

검증:

```text
File ID
Encoding
Checksum
Record Count
Duplicate
Encryption
```

---

# 24. L6 — Big Picture와 Interface Mechanism 매핑

| Big Picture 흐름 | 표준 연계수단 | 처리성격 | 주요 대상 |
|---|---|---|---|
| 계정단말 → Core | MCA | Transaction / SYNC | 계정거래 |
| Marketing/BI → 유관 | API Gateway / API | Online / SYNC | 업무서비스 |
| Web/Mobile → 행동처리 | Collector + Kafka | Event / ASYNC | EBM/행동정보 |
| Core → RDW | CDC | Near-real-time / ASYNC | 실시간 SoR |
| Source/RDW → ADW | ETL | Batch / ASYNC | 분석/마트 |
| External/Related → 정보계 | MFT/FOS | File / ASYNC | 대량/파일 |
| External 거래 | 대외 MCA / API | Transaction/Service | 기관/계열사 |
| Governance 수집 | JDBC/Polling/File | Periodic | Meta/DQ/Lineage |

---

# 25. L6 — 과거 기술요소 → 현재 NSIGHT 전환 매핑

| 과거 방안 | 과거 의미 | 현재 적용 판단 | NSIGHT 방향 |
|---|---|---|---|
| MCI | 채널 표준화 | 개념 계승 | MCA / 채널 접근경계 |
| EAI | 시스템간 중앙허브 | 단일허브 개념은 재구성 | API/Kafka/CDC/ETL/MFT 목적별 Hub |
| Point-to-Point 제한 | 결합도/운영 문제 | 유지 | 승인되지 않은 P2P 금지 |
| Hub & Spoke | 통합운영 | 유지하되 분산 | Integration by Purpose |
| SNA LU0/X.25 | Legacy Protocol | 호환영역만 | Boundary Adapter |
| TCP Socket | 전문연계 | 필요 시 Legacy/대외 | 표준 예외 또는 전용 Adapter |
| XML | 전문형식 | 신규 기본 아님 | JSON/API 중심, 필요 시 XML |
| EUC-KR | 당시 권장 인코딩 | 신규 기준으로 미사용 권고 | UTF-8 + Boundary 변환 |
| NDM/FTP+ | 파일전송 | 현대화 필요 | MFT/FOS |
| MQ | 비동기 | 개념 계승 | Kafka/Event |
| DB2FL/FL2FL | 대량데이터 | 개념 계승 | ETL/MFT |
| DB-Link/DRDA | 직접 DB 접근 | 제한/금지 | Service/CDC/ETL로 대체 |

---

# 26. L6 — Interface Catalog 최소 항목

```text
Interface ID
Interface Name
Business Domain
Source System
Target System
Direction
Purpose
Interface Type
Protocol
Endpoint / Topic / File Path
Sync/Async
Data Class
Schema
Encoding
Expected TPS/Volume
Peak TPS/Volume
SLA
Timeout
Retry
Idempotency
Security
Owner
Operation Owner
Version
Status
Go-Live Date
Dependency
DR Target
Monitoring
Reconciliation
Exception/ADR
```

---

# 27. L6 — 개발 산출물 체계

```text
Big Picture
   ↓
Interface Architecture
   ↓
Interface Inventory
   ↓
Interface Catalog
   ↓
Interface Contract
   ↓
Message/API/Event/File Standard
   ↓
Runtime Sequence
   ↓
Security / NFR
   ↓
Configuration
   ↓
Test Case
   ↓
Runtime Evidence
   ↓
Operation Runbook
   ↓
Baseline
```

---

# 28. L7 — Interface Test Architecture

## 28.1 Contract Test

```text
Schema
Required
Length
Format
Code
Version
Error
```

## 28.2 Integration Test

```text
Source
→ Integration Platform
→ Target
```

## 28.3 Failure Test

```text
Timeout
Target Down
Network Error
Malformed Data
Auth Failure
Duplicate
Partial Failure
```

## 28.4 Performance Test

```text
TPS
Latency
Volume
Lag
Batch Window
File Transfer Time
```

## 28.5 Recovery Test

```text
Retry
Replay
Restart
DLQ
Reconciliation
```

---

# 29. L7 — 운영 기준

모든 인터페이스는 운영 전 다음 질문에 답할 수 있어야 한다.

```text
1. 지금 정상인가?
2. 어디에서 느린가?
3. 어디에서 실패했는가?
4. 몇 건이 누락되었는가?
5. 재처리 가능한가?
6. 중복처리는 없는가?
7. 누구에게 연락해야 하는가?
8. 변경이 언제 적용되었는가?
9. 어떤 버전이 실행 중인가?
10. DR 전환 시 Endpoint가 무엇으로 바뀌는가?
```

---

# 30. L7 — DR / Failover 점검항목

```text
API
├─ DNS / VIP
├─ Certificate
└─ Endpoint

Kafka
├─ Cluster
├─ Topic
├─ Offset
└─ Consumer Recovery

CDC
├─ Source
├─ Relay
├─ Target
└─ Position

ETL
├─ Schedule
├─ Checkpoint
└─ Restart

MFT
├─ Destination
├─ Credential
├─ Allowlist
└─ Pending File

External
├─ Partner Endpoint
├─ Firewall
├─ Certificate
└─ Contact
```

---

# 31. 프로젝트 추진 방안 — TOP-DOWN 실행계획

## Phase 0 — Architecture Baseline 고정

목표:

```text
"우리 프로젝트에서 어떤 인터페이스 수단을
무엇에 사용할 것인가"를 먼저 확정
```

작업:

```text
Big Picture 확인
→ Interface Domain 확정
→ 원칙 승인
→ 금지패턴 승인
→ 예외 프로세스 승인
```

산출:

```text
Interface Architecture Baseline v1.0
```

---

## Phase 1 — 전수 Interface Inventory

```text
전체 시스템
  ↓
Source / Target Pair 추출
  ↓
업무 목적 확인
  ↓
현재/신규 구분
  ↓
인터페이스 목록화
```

필수 구분:

```text
AS-IS 유지
재사용
변경
신규
폐기
```

---

## Phase 2 — 유형 분류 및 목표방식 매핑

```text
각 Interface
   ↓
Transaction?
Service?
Event?
CDC?
ETL?
File?
External?
   ↓
Target Mechanism 확정
```

결과:

```text
AS-IS 방식
→ TO-BE 방식
→ GAP
→ 전환방안
```

---

## Phase 3 — Contract / 표준 설계

```text
Interface ID
Header
Schema
Code
Error
Timeout
Retry
Trace
Security
Version
SLA
```

이 단계에서 업무팀·인터페이스 플랫폼팀·아키텍처팀이 공동 승인한다.

---

## Phase 4 — Runtime / Failure 설계

```text
정상 Flow
Timeout Flow
Target Down
Duplicate
Retry
Replay
Partial Failure
Reconciliation
```

Sequence Diagram/Text Diagram을 필수 산출한다.

---

## Phase 5 — 구현 및 자동검증

```text
Contract Test
Integration Test
Failure Test
Performance Test
Recovery Test
```

CI/CD 또는 테스트 자동화가 가능한 항목은 Rule/Test로 전환한다.

---

## Phase 6 — 운영전환

```text
Dashboard
Alert
Runbook
Owner
SLA
Replay
DR
Capacity
```

운영증적이 없는 인터페이스는 완료로 간주하지 않는다.

---

# 32. 프로젝트 우선 실행 과제

## Priority 1 — Interface Inventory부터 확정

가장 먼저 해야 할 일:

```text
전체 시스템 연결선
  ↓
Source / Target
  ↓
Interface Purpose
  ↓
현재 방식
  ↓
TO-BE 후보
```

---

## Priority 2 — 7개 유형 분류

```text
MCA
API
EVENT
CDC
ETL
FILE
GOVERNANCE
```

분류되지 않는 연계는 예외 후보로 별도 관리한다.

---

## Priority 3 — P2P / Direct DB 위험연계 선별

```text
P2P
DB-Link
Direct DB
Long SYNC
Large API
Unmanaged FTP
```

위 항목을 우선 GAP 대상으로 잡는다.

---

## Priority 4 — Interface ID / Trace 기준 조기 확정

소스 개발이 시작된 뒤 도입하면 전 시스템 수정이 필요하므로 초기에 고정한다.

---

## Priority 5 — SYNC/ASYNC 기준을 NFR과 연결

```text
업무 중요도
+
End-to-End SLA
+
Dependency Count
+
Target Availability
+
Retry/Compensation
=
SYNC / ASYNC 결정
```

---

## Priority 6 — 파일/CDC/ETL 정합성 기준 확정

대량 데이터는 “전송 성공”만으로 성공 처리하지 않는다.

---

# 33. Architecture Review Checklist

```text
[ ] Interface ID가 있는가
[ ] Source / Target Owner가 있는가
[ ] Integration Type이 목적에 맞는가
[ ] 승인되지 않은 P2P가 아닌가
[ ] Direct DB / DB-Link가 아닌가
[ ] SYNC가 반드시 필요한가
[ ] Timeout이 있는가
[ ] Retry 횟수/간격이 있는가
[ ] Idempotency가 가능한가
[ ] Error Code가 정의되었는가
[ ] Trace ID가 전달되는가
[ ] 대량데이터를 API로 보내지 않는가
[ ] Event DLQ/Replay가 있는가
[ ] CDC/ETL/File 정합성 검증이 있는가
[ ] 인증/인가가 정의되었는가
[ ] Version / Change 절차가 있는가
[ ] Monitoring / Alert가 있는가
[ ] DR 전환항목이 정의되었는가
[ ] 예외는 ADR이 있는가
```

---

# 34. 확인 필요 / 의사결정 항목

| 항목 | 상태 |
|---|---|
| API Gateway 세부 적용범위 | 확인 필요 |
| MCA와 API Gateway 업무경계 | 확인 필요 |
| 대외 MCA와 일반 API/MFT 구분 | 확인 필요 |
| API Timeout 표준값 | 확인 필요 |
| Sync End-to-End SLA | NFR 연계 필요 |
| Event Platform Kafka Topic 기준 | 상세설계 필요 |
| Retry / DLQ 공통 기준 | 상세설계 필요 |
| API/Event Payload 최대크기 | BMT 필요 |
| 신규 인코딩 표준 UTF-8 확정 | 표준승인 필요 |
| FOS와 MFT 역할차이 | 상세설계 필요 |
| CDC 제품/Relay 구조 | 상세설계 필요 |
| ETL Restart / Checkpoint 기준 | 상세설계 필요 |
| Interface ID Naming Rule | 표준정의 필요 |
| 공통 Header | 표준정의 필요 |
| 에러코드 체계 | 표준정의 필요 |
| External 보안 Zone | 보안아키텍처 연계 |
| DR Interface Endpoint 전환 | 인프라/운영 연계 |

---

# 35. 최종 TOP-DOWN Interface Architecture Baseline

```text
L0  Business / Channel / System / External
       ↓
L1  Interface Boundary
       ↓
    MCA / API Gateway / Collector / MFT
       ↓
L2  Integration by Purpose
       ↓
    Transaction / API / Event / CDC / ETL / File
       ↓
L3  Target Processing
       ↓
    Core / Marketing / BI / EBM / RDW / ADW / Related
       ↓
L4  Runtime Control
       ↓
    Auth / Validation / Timeout / Retry / Idempotency / Error
       ↓
L5  Trace / Reconciliation
       ↓
    Interface ID / GUID / ServiceId / Event ID / Batch ID
       ↓
L6  Governance
       ↓
    Owner / Version / SLA / Change / ADR
       ↓
L7  Runtime Evidence
       ↓
    Log / Metric / Trace / Alert / Test / Recovery / DR
```

---

# 36. 최종 강화 TEXT 그림 — 한 장 요약

```text
┌──────────────────────────────────────────────────────────────────────────────┐
│                    CHANNEL / APPLICATION / CORE / EXTERNAL                   │
└───────────────┬──────────────────┬──────────────────┬────────────────────────┘
                │                  │                  │
       계정 Transaction        Online Service      Event / Data / File
                │                  │                  │
                ▼                  ▼                  ▼
             ┌─────┐        ┌─────────────┐    ┌────────────────────────┐
             │ MCA │        │ API Gateway │    │ Collector / MFT / CDC │
             └──┬──┘        └──────┬──────┘    └────────────┬───────────┘
                │                  │                         │
                │                  │              ┌──────────┼──────────┐
                │                  │              ▼          ▼          ▼
                │                  │            Kafka       CDC        ETL
                │                  │              │          │          │
                ▼                  ▼              ▼          ▼          ▼
              Core          Related Service      EBM        RDW        ADW
                │                  │              │          │          │
                └──────────────────┴──────────────┴──────────┴──────────┘
                                           │
                                           ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                              RUNTIME CONTROL                                 │
│ Auth / Schema / Timeout / Retry / Idempotency / Error / DLQ / Replay       │
└──────────────────────────────────────┬───────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                         TRACE / GOVERNANCE / EVIDENCE                        │
│ Interface ID / ServiceId / GUID / Event ID / Batch ID                      │
│ SLA / Owner / Version / Log / Metric / Audit / Reconciliation / ADR        │
└──────────────────────────────────────────────────────────────────────────────┘
```

---

# 37. 최종 결론

과거 전행 인터페이스 방안의 가장 중요한 자산은 특정 제품이나 프로토콜 자체가 아니다.

```text
표준 경로 사용
Point-to-Point 최소화
서비스 간 계약
SYNC/ASYNC 분리
Online / File / Batch 분리
DB 직접 접근 제한
패턴 표준화
예외 통제
```

이 원칙을 NSIGHT Big Picture에 맞게 현대화하면 다음 구조가 된다.

```text
Transaction
→ MCA

Service
→ API Gateway

Event
→ Kafka

Change Data
→ CDC

Analytical Data
→ ETL

File / External
→ MFT / FOS / 대외 MCA

All Interfaces
→ Contract + Runtime Control + Trace + Reconciliation + Governance
```

따라서 프로젝트 추진의 핵심은 **인터페이스 기술을 먼저 고르는 것이 아니라, 전체 연계를 전수 식별하고 목적별로 분류한 후, 표준 계약과 Runtime 통제를 적용하고 운영 증적까지 검증하는 것**이다.

최종적으로 인터페이스는 다음 순서로 관리되어야 한다.

```text
Big Picture
   ↓
Interface Inventory
   ↓
Purpose Classification
   ↓
Target Integration Mechanism
   ↓
Interface Contract
   ↓
Runtime / Security / NFR
   ↓
Implementation
   ↓
Test / Runtime Evidence
   ↓
Operation / Change / ADR
   ↓
Architecture Baseline
```
