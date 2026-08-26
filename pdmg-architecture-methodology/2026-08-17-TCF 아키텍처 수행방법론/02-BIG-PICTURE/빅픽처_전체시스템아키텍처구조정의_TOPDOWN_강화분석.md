# 빅픽처 — 전체 시스템 아키텍처 구조 정의
# TOP-DOWN 강화 분석 및 상세 TEXT 아키텍처

## 0. 문서 목적

본 문서는 원본 이미지 **「전체 시스템 아키텍처 구조 정의」**를 기준으로, 기존의 단순 박스 나열 방식이 아니라 **TOP-DOWN 관점**에서 차세대 정보계 전체 구조를 단계적으로 해체·재구성한 상세 아키텍처 분석 문서다.

이번 문서는 특히 다음을 강화한다.

```text
L0  전체 시스템 Context
 ↓
L1  핵심 Architecture Domain
 ↓
L2  Application / Runtime
 ↓
L3  Interface / Integration
 ↓
L4  Data Flow / Event Flow
 ↓
L5  Data Governance
 ↓
L6  Legacy / External Integration
 ↓
L7  Runtime Scenario / Failure Domain / 검증 포인트
```

원본 이미지에서 직접 확인 가능한 내용은 `FACT`, 이미지의 연결관계를 구조적으로 풀어낸 내용은 `ANALYSIS`, 이미지 해상도나 후속 설계가 필요한 내용은 `확인 필요`로 구분한다.

---

# 1. L0 — 전체 시스템 Context

## 1.1 한 문장 Architecture Definition

원본 전체 그림은 다음 구조를 표현한다.

> **채널에서 발생하는 정보조회·계정거래·고객행동 이벤트를 마케팅플랫폼·BI포탈·실시간 이벤트 처리영역이 수용하고, RDW/ADW 데이터플랫폼과 데이터거버넌스를 기반으로 분석·활용하며, 계정계·유관시스템·Big Data·외부기관과 MCA/API/CDC/ETL/FOS/MFT 방식으로 연계하는 차세대 정보계 아키텍처**

---

## 1.2 L0 최상위 TOP-DOWN TEXT 그림

```text
┌──────────────────────────────────────────────────────────────────────────────┐
│                          [0] 사용자 / 업무 채널                              │
│                                                                              │
│  계정단말 / 정보계단말 / Package UI / Web Channel / Mobile Channel          │
└──────────────────────────────────────┬───────────────────────────────────────┘
                                       │
                     JSON / 계정거래 / Event / PUSH
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                       [1] 채널통합 / 접근 계층                               │
│                                                                              │
│  MCA / 단말거래 / Marketing Web / BI Portal Web                             │
└──────────────────────────────────────┬───────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                    [2] 정보계 Application Service                           │
│                                                                              │
│  ① Marketing Platform                         ③ BI Portal                   │
│  - 고객/상품/상담/캠페인/메시지               - BI/실적/OLAP/Self BI       │
└───────────────────────┬─────────────────────────────┬────────────────────────┘
                        │                             │
                        │                             │
                        ▼                             ▼
┌──────────────────────────────┐        ┌──────────────────────────────────────┐
│ [3] Marketing Event Runtime  │        │       [4] Data Platform             │
│                              │        │                                      │
│ Wise Collector               │        │  ② RDW              ② ADW           │
│ Kafka                        │        │  실시간/준실시간     분석/마트        │
│ EBM                          │        │                                      │
│ UMS                          │        │  JDBC / Agent / CDC / ETL            │
└───────────────┬──────────────┘        └──────────────────┬───────────────────┘
                │                                          │
                └──────────────────┬───────────────────────┘
                                   ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                       [5] Data Governance                                    │
│                                                                              │
│      ④ Biz Meta / Data Quality / Data Flow                                  │
│      JDBC / Polling / File 기반 메타·품질·흐름 수집                         │
└──────────────────────────────────────┬───────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                    [6] 내부/외부 연계 생태계                                │
│                                                                              │
│ 계정 Core / 계정 연계 / 경영관리 / 리스크 / 정보계 유관 / Big Data         │
│ 계열사 / 외부기관                                                           │
│                                                                              │
│ API Gateway / MCA / CDC / ETL / FOS / MFT / 대외 MCA                       │
└──────────────────────────────────────────────────────────────────────────────┘
```

---

# 2. L1 — 핵심 Architecture Domain

원본에서 가장 중요한 Architecture Domain은 다음 7개다.

```text
1. Channel Domain
2. Channel Integration Domain
3. Information Application Domain
4. Marketing Event Processing Domain
5. Data Platform Domain
6. Data Governance Domain
7. Legacy / External Integration Domain
```

이를 다시 그림으로 표현하면 다음과 같다.

```text
Channel
  │
  ▼
Channel Integration
  │
  ├──────────────┬───────────────────┐
  ▼              ▼                   ▼
Marketing      BI Portal        Account/Core
  │              │
  ├──────┬───────┘
  │      │
  ▼      ▼
Event   Data Platform
          │
          ▼
    Data Governance
          │
          ▼
Legacy / External
```

---

# 3. L1-1 — Channel Domain

## 3.1 FACT

원본 좌측에는 다음 채널이 보인다.

```text
채널
├─ 계정 단말
│  ├─ UI
│  └─ Single View
│
├─ 정보계 단말
│  ├─ UI
│  └─ Package UI
│
├─ Web 채널
│  └─ UI
│
├─ Mobile 채널
│  └─ UI
│
└─ SMS / PUSH / MAIL
```

---

## 3.2 Channel TOP-DOWN

```text
사용자
│
├─ 내부 사용자
│  ├─ 계정 단말
│  └─ 정보계 단말
│
└─ 고객 / 외부 접점
   ├─ Web Channel
   ├─ Mobile Channel
   └─ SMS / PUSH / MAIL
```

---

## 3.3 Channel의 3가지 주요 흐름

```text
A. 계정거래
계정단말 → MCA → 계정계

B. 정보계 서비스
정보계단말 / Package UI → JSON → Marketing / BI

C. 고객행동 이벤트
Web/Mobile → Event → Wise Collector → Kafka → EBM
```

즉 채널은 단순 UI 계층이 아니라 **거래·조회·행동 이벤트의 3가지 진입점**이다.

---

# 4. L1-2 — Channel Integration Domain

## 4.1 FACT

원본에는 다음이 표시된다.

```text
채널통합
├─ MCA
└─ 단말거래
```

또한 JSON 기반 서비스 호출이 정보계 Application 쪽으로 직접 연결된다.

---

## 4.2 접근 유형 분리

```text
[계정성 거래]
Channel
  ↓
MCA
  ↓
Core Banking

[정보계 업무]
Channel
  ↓ JSON
Marketing Web / BI Web
  ↓
Application

[행동 이벤트]
Channel
  ↓ Event
Collector
  ↓
Kafka / EBM
```

### ANALYSIS

이 구조는 `모든 요청을 하나의 통합 Gateway로 수렴`시키는 구조가 아니라, **업무성격별 진입경로를 분리**한다.

---

# 5. L1-3 — Information Application Service

## 5.1 원본 핵심 영역

```text
정보계 어플리케이션 서비스
│
├─ ① 마케팅플랫폼
├─ ③ BI포탈
├─ 마케팅플랫폼 Web
├─ BI포탈 Web
├─ 단말관리
└─ 단말배포
```

---

# 6. L2 — ① Marketing Platform

## 6.1 FACT 기능 목록

원본 마케팅플랫폼에는 다음 업무가 보인다.

```text
공통
통합고객
개인고객
기업고객
상담판매
통합상품
캠페인
EBM
영업지원
CS
컨텐츠
메시지
미니싱글뷰
실시간처리
행동정보처리
고객행동데이터
```

---

## 6.2 Marketing Platform TOP-DOWN 상세 TEXT

```text
┌──────────────────────── Marketing Platform ────────────────────────┐
│                                                                    │
│ [Common / Portal]                                                  │
│ ├─ 공통                                                           │
│                                                                    │
│ [Customer]                                                         │
│ ├─ 통합고객                                                       │
│ ├─ 개인고객                                                       │
│ ├─ 기업고객                                                       │
│ └─ 미니싱글뷰                                                     │
│                                                                    │
│ [Sales / Product]                                                  │
│ ├─ 상담판매                                                       │
│ └─ 통합상품                                                       │
│                                                                    │
│ [Campaign / Event]                                                 │
│ ├─ 캠페인                                                         │
│ ├─ EBM                                                            │
│ ├─ 실시간처리                                                     │
│ ├─ 행동정보처리                                                   │
│ └─ 고객행동데이터                                                 │
│                                                                    │
│ [Sales Support]                                                    │
│ ├─ 영업지원                                                       │
│ └─ CS                                                             │
│                                                                    │
│ [Customer Contact]                                                 │
│ ├─ 컨텐츠                                                         │
│ └─ 메시지                                                         │
└────────────────────────────────────────────────────────────────────┘
```

---

## 6.3 Marketing의 역할

```text
Customer Information
      +
Product Information
      +
Behavior Event
      +
Campaign
      +
Consultation / Sales
      ↓
Customer-Centric Marketing Execution
```

---

# 7. L2 — ③ BI Portal

## 7.1 FACT

```text
BI포탈
├─ BI포탈
├─ 신용실적
├─ OLAP
├─ Self BI
└─ 신BI포털UIUX
```

---

## 7.2 BI TOP-DOWN

```text
┌──────────────────────────── BI Portal ─────────────────────────────┐
│                                                                    │
│ Portal Access                                                      │
│ └─ BI포탈                                                         │
│                                                                    │
│ Business Performance                                               │
│ └─ 신용실적                                                       │
│                                                                    │
│ Analytical Processing                                              │
│ └─ OLAP                                                           │
│                                                                    │
│ Self Service Analytics                                             │
│ └─ Self BI                                                        │
│                                                                    │
│ User Experience                                                    │
│ └─ 신BI포털UIUX                                                   │
└────────────────────────────────────────────────────────────────────┘
```

---

## 7.3 BI의 위치

```text
RDW / ADW
   ↓
BI Portal
   ↓
User
```

즉 BI는 원천 데이터 생산보다 **분석·조회·소비 계층**의 성격이 강하다.

---

# 8. L2 — ① Marketing Event Processing

## 8.1 원본 구성

```text
마케팅 이벤트 처리
├─ 데이터 수집
├─ 행동정보처리서버
├─ 고객행태
├─ Wise Collector Proxy
├─ Wise Collector 수집
├─ 고객행동데이터 Kafka
├─ KAFKA
├─ 실시간 처리 서버
│  ├─ EBM
│  └─ 이벤트 정보
├─ UMS
└─ 데이터 분석
```

---

## 8.2 가장 중요한 Event TOP-DOWN TEXT

```text
[고객 행동 발생]
        │
        ▼
Web Channel / Mobile Channel
        │ Event
        ▼
Wise Collector Proxy
        │
        ▼
비대면 로그 / 행동 로그
        │
        ▼
Wise Collector 수집
        │
        ▼
고객행동데이터 Kafka
        │
        ▼
KAFKA
        │
        ├───────────────┐
        │               │
        ▼               ▼
행동정보 처리       실시간 처리 서버
                     ├─ EBM
                     └─ 이벤트 정보
                          │
                          ▼ API
                         UMS
                          │
                          ▼
                 SMS / PUSH / MAIL
```

---

## 8.3 실시간 Marketing Loop

```text
Observe
  ↓
Collect
  ↓
Stream
  ↓
Analyze / Decide
  ↓
Act
  ↓
Contact Customer
```

즉 실시간 이벤트 처리의 종착점은 Kafka 저장이 아니라 **고객 접촉 실행**이다.

---

# 9. L2 — ② Data Platform

## 9.1 원본 구조

```text
데이터플랫폼
├─ RDW
└─ ADW
```

상단에는 다음 개념이 표시된다.

```text
데이터 분석/처리
데이터 수집
```

---

# 10. L3 — RDW 상세

## 10.1 FACT

```text
RDW
├─ 공통
├─ 실시간SoR
├─ 준실시간 요약집계
├─ 준실시간 보고서마트
├─ Feedback
└─ 마케팅정보
```

---

## 10.2 RDW TOP-DOWN 데이터 계층

```text
┌────────────────────────────── RDW ────────────────────────────────┐
│                                                                  │
│ [L1] Common                                                      │
│      공통                                                        │
│                                                                  │
│ [L2] Source of Record                                            │
│      실시간SoR                                                   │
│                                                                  │
│ [L3] Aggregation                                                 │
│      준실시간 요약집계                                           │
│                                                                  │
│ [L4] Serving                                                     │
│      준실시간 보고서마트                                         │
│                                                                  │
│ [L5] Feedback                                                    │
│      Feedback                                                    │
│                                                                  │
│ [L6] Marketing Data                                              │
│      마케팅정보                                                  │
└──────────────────────────────────────────────────────────────────┘
```

---

## 10.3 RDW 처리 구조

```text
Core / Event / Source
      ↓
실시간SoR
      ↓
준실시간 요약집계
      ↓
준실시간 보고서마트
      ↓
BI / Marketing
      ↓
Feedback
```

`마케팅정보`의 정확한 데이터 책임은 이미지에서 정의되지 않으므로 `확인 필요`.

---

# 11. L3 — ADW 상세

## 11.1 FACT

```text
ADW
├─ 공통
├─ 분석SoR
├─ 분석 통합/요약/집계
├─ 분석 단위 업무마트
├─ 분석 보고서마트
├─ Feedback
└─ 분석지원
```

---

## 11.2 ADW TOP-DOWN 데이터 계층

```text
┌────────────────────────────── ADW ────────────────────────────────┐
│                                                                  │
│ [L1] Common                                                      │
│      공통                                                        │
│                                                                  │
│ [L2] Analytical SoR                                              │
│      분석SoR                                                     │
│                                                                  │
│ [L3] Integration / Aggregation                                   │
│      분석 통합 / 요약 / 집계                                    │
│                                                                  │
│ [L4-A] Business Mart                                             │
│      분석 단위 업무마트                                          │
│                                                                  │
│ [L4-B] Report Mart                                               │
│      분석 보고서마트                                             │
│                                                                  │
│ [L5] Feedback                                                    │
│      Feedback                                                    │
│                                                                  │
│ [L6] Analysis Assistance                                         │
│      분석지원                                                    │
└──────────────────────────────────────────────────────────────────┘
```

---

# 12. L3 — RDW vs ADW 역할 경계

```text
                       Data Platform
                             │
              ┌──────────────┴──────────────┐
              ▼                             ▼
             RDW                           ADW
     Real-time / Near RT            Analytical / Mart
              │                             │
              ├─ 실시간SoR                 ├─ 분석SoR
              ├─ 준실시간집계              ├─ 통합/요약/집계
              ├─ 준실시간보고              ├─ 단위업무마트
              └─ Marketing                 └─ 분석보고서마트
```

### 핵심 판단

```text
RDW = 최신성 / 운영 활용
ADW = 분석 / 집계 / 장기 활용
```

실제 물리 데이터 이동경로는 별도 Data Flow 설계에서 확인 필요.

---

# 13. L1-5 — ④ Data Governance

## 13.1 FACT

```text
데이터 거버넌스
├─ 비즈 메타
├─ 데이터 품질관리
└─ 데이터 흐름관리
```

---

## 13.2 Data Governance TOP-DOWN

```text
                     ┌──────────────────────┐
                     │   Data Governance    │
                     └──────────┬───────────┘
                                │
             ┌──────────────────┼──────────────────┐
             ▼                  ▼                  ▼
      ┌────────────┐     ┌──────────────┐    ┌──────────────┐
      │ Biz Meta   │     │ Data Quality │    │ Data Flow    │
      │ 업무 의미  │     │ 품질검증     │    │ Lineage      │
      └─────┬──────┘     └──────┬───────┘    └──────┬───────┘
            │                   │                   │
            └──────────┬────────┴────────┬──────────┘
                       ▼                 ▼
                      RDW               ADW
                       │                 │
                       └──── BI / Marketing ────────┘
```

---

## 13.3 원본에서 보이는 Governance 수집 방식

```text
JDBC
Polling
File
```

즉 Governance는 데이터플랫폼 메타를 수작업 등록만 하는 구조가 아니라 **자동 수집형 구조**를 포함한다.

---

# 14. L3 — Integration Architecture

원본 전체에서 확인되는 연계수단은 다음과 같다.

```text
JSON
MCA
API Gateway (CruzAPIM)
JDBC
CDC
Kafka
Agent
ETL
FOS
MFT
Polling
File
API
PUSH
```

---

# 15. Interface를 목적별로 재분류

```text
┌─────────────────────────────────────────────────────────────┐
│  Online / Request-Response                                  │
├─────────────────────────────────────────────────────────────┤
│ JSON                                                        │
│ API Gateway                                                 │
│ MCA                                                         │
│ JDBC                                                        │
│ API                                                         │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  Event / Streaming                                          │
├─────────────────────────────────────────────────────────────┤
│ Kafka                                                       │
│ Agent                                                       │
│ PUSH                                                        │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  Data Synchronization                                       │
├─────────────────────────────────────────────────────────────┤
│ CDC                                                         │
│ ETL                                                         │
│ Polling                                                     │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  File Integration                                           │
├─────────────────────────────────────────────────────────────┤
│ FOS                                                         │
│ MFT                                                         │
│ File                                                        │
└─────────────────────────────────────────────────────────────┘
```

---

# 16. L3 — API Gateway

## 16.1 FACT

```text
API Gateway (CruzAPIM)
```

원본에서 유관 시스템과 차세대 정보계의 중앙 온라인 연계 허브로 표현된다.

---

## 16.2 API Gateway 역할

```text
Marketing / BI / Internal App
          │
          ▼
API Gateway (CruzAPIM)
          │
          ▼
Related Systems
```

### 후속 확인 필요

- 인증/인가
- Routing
- Timeout
- Retry
- Circuit Breaker
- API Versioning
- API Catalog

---

# 17. L3 — MCA

## 17.1 내부 계정 거래

```text
계정단말
  ↓
MCA
  ↓
계정 코어
```

## 17.2 대외 연계

하단에는:

```text
대외 MCA
```

가 별도 존재한다.

즉 MCA는 내부 계정거래와 대외거래 모두에서 사용되지만 **논리 경계가 분리**되어 있다.

---

# 18. L3 — CDC

## 18.1 FACT 흐름

원본 우측 상단:

```text
계정정보
  ↓
CDC
  ↓
CDC 중계
```

---

## 18.2 CDC TOP-DOWN

```text
Core DB / Account Data
        │
        ▼
       CDC
        │
        ▼
   CDC Relay
        │
        ▼
       RDW
        │
        ▼
Near-real-time Use
```

이 흐름은 차세대 정보계의 실시간 데이터 기반 중 핵심이다.

---

# 19. L3 — ETL / FOS / MFT

원본 하단 우측에는 수평 연계 Layer가 명확하게 구분된다.

```text
ETL
FOS
MFT
대외 MCA
```

TOP-DOWN:

```text
Source / Related / External
        │
        ├─ ETL : 데이터 변환/적재
        ├─ FOS : 파일 연계
        ├─ MFT : 관리형 파일 전송
        └─ MCA : 거래성 대외연계
        │
        ▼
Information / Data Platform
```

---

# 20. L4 — 전체 데이터 흐름

## 20.1 계정성 데이터

```text
계정 코어
  ↓
계정정보
  ↓
CDC
  ↓
CDC 중계
  ↓
RDW
  ↓
준실시간 집계 / 마케팅 / BI
```

---

## 20.2 분석 데이터

```text
원천 / RDW / 유관
      ↓
ETL
      ↓
ADW
      ↓
분석 통합/집계
      ↓
업무마트 / 보고서마트
      ↓
BI / OLAP / Self BI
```

---

## 20.3 고객행동 데이터

```text
Web / Mobile
     ↓
Event
     ↓
Wise Collector
     ↓
Kafka
     ↓
실시간 처리 / EBM
     ├─ UMS → PUSH/SMS/MAIL
     └─ RDW/분석 활용
```

---

# 21. L4 — 전체 Runtime 흐름

```text
┌─────────────── Online ───────────────┐
Channel
  ↓ JSON
Web
  ↓
Application
  ↓ JDBC/API
Data / Related System
└───────────────────────────────────────┘

┌────────────── Transaction ────────────┐
Account Terminal
  ↓
MCA
  ↓
Core
└───────────────────────────────────────┘

┌────────────── Event ──────────────────┐
Web/Mobile
  ↓
Collector
  ↓
Kafka
  ↓
EBM
  ↓
UMS
└───────────────────────────────────────┘

┌────────────── Data ───────────────────┐
Core/Legacy
  ↓ CDC/ETL/File
RDW/ADW
  ↓
BI/Marketing
└───────────────────────────────────────┘
```

---

# 22. L5 — 유관 시스템

원본 우측 상단:

```text
유관 시스템
├─ 계정 코어
├─ 계정 연계
├─ 경영 관리
└─ 리스크 관리
```

이 영역은 정보계가 업무 거래 또는 데이터를 제공받는 주요 내부 시스템군이다.

---

# 23. L5 — 정보계 유관

원본 우측 중단:

```text
정보계 유관
├─ 정보 단위업무
│  ├─ 종합수익관리
│  ├─ 업적평가
│  ├─ 자금세탁방지
│  └─ ...
│
└─ Big Data
   ├─ 데이터 수집/적재
   ├─ 실시간 수집/적재
   ├─ 데이터 분석
   └─ Big Data
```

---

# 24. L5 — 외부기관 / 계열사

원본 하단에 다음 기관이 보인다.

```text
NH생명
NH손해
NH멤버스
KT
Nice
KoData
```

이를 일반화하면:

```text
External / Affiliate
  ↓
대외 MCA / API / File
  ↓
Information System
```

---

# 25. L6 — 전체 통합 TOP-DOWN 강화 TEXT

아래 그림은 원본의 주요 모든 컴포넌트와 연계방식을 한 번에 읽을 수 있도록 재구성한 강화 그림이다.

```text
                                  ┌─────────────────────┐
                                  │      사용자         │
                                  └─────────┬───────────┘
                                            │
                    ┌───────────────────────┼───────────────────────┐
                    │                       │                       │
                    ▼                       ▼                       ▼
             계정 단말                정보계 단말              Web/Mobile
             UI/SingleView            UI/Package UI               UI
                    │                       │                       │
             계정거래│                 JSON  │                 Event │
                    ▼                       ▼                       ▼
                  MCA           Marketing Web / BI Web       Wise Collector
                    │                       │                       │
                    ▼                       ▼                       ▼
               계정 코어         ┌─────────────────────┐          Kafka
                    │             │ Information Service │            │
                    │             │                     │            ▼
                    │             │ Marketing Platform  │      Behavior / EBM
                    │             │ BI Portal           │            │
                    │             └─────────┬───────────┘            ▼
                    │                       │                    UMS/API
                    │                       │ JDBC/API               │
                    │                       │                        ▼
                    │                       │                 SMS/PUSH/MAIL
                    │                       ▼
                    │              ┌──────────────────────────┐
                    │              │      Data Platform       │
                    │              │                          │
               CDC  │              │   RDW          ADW       │
                    └─────────────▶│ Real-time    Analytics   │
                                   └────────────┬─────────────┘
                                                │
                                                ▼
                                   ┌──────────────────────────┐
                                   │    Data Governance       │
                                   │ BizMeta / DQ / DataFlow  │
                                   └────────────┬─────────────┘
                                                │
                 ┌──────────────────────────────┼───────────────────────────┐
                 │                              │                           │
                 ▼                              ▼                           ▼
          Related Systems                 Info Related                 External
      Core/Account/Risk/Manage       Unit Business/Big Data     Affiliate/Institution
                 │                              │                           │
                 └──── API Gateway / ETL / FOS / MFT / 대외 MCA ──────────┘
```

---

# 26. L6 — Architecture Responsibility Map

| 영역 | 핵심 책임 |
|---|---|
| Channel | 사용자/고객 접점 |
| Channel Integration | 거래/서비스 진입 통제 |
| Marketing Platform | 고객·상품·상담·캠페인 실행 |
| BI Portal | 정보 분석·조회·Self BI |
| Event Processing | 행동 이벤트 수집·판단·실시간 실행 |
| RDW | 실시간/준실시간 데이터 |
| ADW | 분석/마트 데이터 |
| Data Governance | 메타·품질·흐름 통제 |
| API Gateway | 온라인 시스템 연계 |
| MCA | 계정/대외 거래 연계 |
| CDC | 변경데이터 동기화 |
| ETL | 데이터 변환·적재 |
| FOS/MFT | 파일 기반 연계 |
| UMS | 고객 메시지 전달 |

---

# 27. TOP-DOWN 핵심 호출 시나리오 1 — 정보계 조회

```text
[1] 사용자
  ↓
[2] 정보계 단말 / Package UI
  ↓ JSON
[3] Marketing Web 또는 BI Web
  ↓
[4] Application
  ↓ JDBC
[5] RDW / ADW
  ↓
[6] 결과 반환
```

### 주요 검증 포인트

```text
JSON 전문
Authentication
ServiceId
JDBC Pool
Query Timeout
Response Time
```

---

# 28. TOP-DOWN 핵심 호출 시나리오 2 — 계정거래

```text
[1] 계정 단말
  ↓
[2] 계정거래
  ↓
[3] MCA
  ↓
[4] 계정 Core
  ↓
[5] 거래결과
```

---

# 29. TOP-DOWN 핵심 호출 시나리오 3 — 대내 시스템 API

```text
[1] Marketing / BI
  ↓
[2] API Gateway (CruzAPIM)
  ↓
[3] 계정 연계 / 경영 / 리스크 등
  ↓
[4] Response
```

---

# 30. TOP-DOWN 핵심 호출 시나리오 4 — 고객 행동 이벤트

```text
[1] 고객 행동
  ↓
[2] Web / Mobile Event
  ↓
[3] Wise Collector Proxy
  ↓
[4] Wise Collector
  ↓
[5] Kafka
  ↓
[6] Behavior Processing / EBM
  ↓
[7] UMS
  ↓
[8] PUSH / SMS / MAIL
```

---

# 31. TOP-DOWN 핵심 호출 시나리오 5 — CDC 동기화

```text
[1] Core DB 변경
  ↓
[2] CDC Capture
  ↓
[3] CDC Relay
  ↓
[4] RDW SoR
  ↓
[5] Near-real-time Aggregation
  ↓
[6] Marketing / BI Use
```

---

# 32. TOP-DOWN 핵심 호출 시나리오 6 — 분석 데이터

```text
[1] Source / RDW
  ↓
[2] ETL
  ↓
[3] ADW SoR
  ↓
[4] 통합/요약/집계
  ↓
[5] 단위업무마트 / 보고서마트
  ↓
[6] BI / OLAP / Self BI
```

---

# 33. TOP-DOWN 핵심 호출 시나리오 7 — 파일 연계

```text
[1] Related / External
  ↓
[2] MFT / FOS
  ↓
[3] File Landing / Processing
  ↓
[4] ETL / Application
  ↓
[5] RDW / ADW
```

---

# 34. TOP-DOWN 핵심 호출 시나리오 8 — Governance 수집

```text
[1] DB / ETL / File / Interface
  ↓
[2] JDBC / Polling / File
  ↓
[3] Data Governance
     ├─ BizMeta
     ├─ Data Quality
     └─ Data Flow
  ↓
[4] Search / Quality / Lineage
```

---

# 35. 아키텍처 핵심 경계

## 35.1 Transaction Boundary

```text
계정거래
→ MCA
→ Core
```

## 35.2 Information Service Boundary

```text
JSON
→ Web
→ Application
```

## 35.3 Event Boundary

```text
Event
→ Collector
→ Kafka
→ EBM
```

## 35.4 Data Boundary

```text
CDC / ETL
→ RDW / ADW
```

## 35.5 Governance Boundary

```text
Metadata / DQ / Lineage
```

---

# 36. Failure Domain 관점

```text
Failure Domain
├─ Channel
├─ Marketing Application
├─ BI
├─ Kafka/Event Processing
├─ RDW
├─ ADW
├─ API Gateway
├─ MCA
├─ File Integration
└─ External System
```

예:

```text
Kafka 장애
→ 고객행동 실시간 마케팅 영향
→ 일반 계정거래는 MCA 경로로 분리

BI 장애
→ 분석 서비스 영향
→ Marketing Transaction과 분리 가능

ADW 장애
→ 분석/보고서 영향
→ RDW 실시간 활용은 논리적으로 별도 영역
```

실제 HA 구조는 이미지에서 확정되지 않는다.

---

# 37. Performance Domain 관점

## Online

```text
Channel → Web → App → JDBC/API
```

주요 지표:

```text
TPS
p95
Thread
Connection Pool
API Latency
```

## Event

```text
Collector → Kafka → EBM
```

주요 지표:

```text
Event Rate
Kafka Lag
Consumer Lag
Processing Latency
```

## Data

```text
CDC / ETL → RDW / ADW
```

주요 지표:

```text
CDC Lag
Data Freshness
ETL Window
Batch Completion
```

## BI

```text
BI → ADW/RDW
```

주요 지표:

```text
Query Time
Concurrent Users
Long Query
Resource Usage
```

---

# 38. Security Boundary

```text
Channel
→ 사용자 인증 / 세션

API Gateway
→ API 인증 / 인가

MCA
→ 거래 보안 / 전문 검증

JDBC
→ DB 계정 / ACL / Pool

Kafka
→ Producer/Consumer ACL

CDC
→ Replication Account

ETL/MFT/FOS
→ 전송계정 / 파일 암호화 / 무결성

Data Governance
→ Admin / Steward 권한

External
→ 인증서 / 네트워크 접근통제
```

세부 보안정책은 이미지에 없으므로 후속 설계 필요.

---

# 39. Observability TOP-DOWN

전체 흐름을 추적하기 위해 다음 키가 필요하다.

```text
GUID
Transaction ID
ServiceId
Interface ID
Event ID
Batch ID
Customer/Session Context
```

예:

```text
Online
GUID
→ Marketing
→ API Gateway
→ Related System

Event
Event ID
→ Collector
→ Kafka
→ EBM
→ UMS
```

---

# 40. 원본 이미지가 직접 보여주는 Architecture Intent

## Intent 1

```text
채널과 Application은 직접 결합하지 않는다.
```

## Intent 2

```text
계정 거래와 정보계 서비스를 분리한다.
```

## Intent 3

```text
실시간 Event Processing과 일반 Online Processing을 분리한다.
```

## Intent 4

```text
RDW와 ADW를 목적별로 분리한다.
```

## Intent 5

```text
Data Governance는 횡단기능으로 둔다.
```

## Intent 6

```text
API / CDC / ETL / File / MCA를 목적별로 병행한다.
```

## Intent 7

```text
기존/유관/외부 시스템과 단계적으로 공존한다.
```

---

# 41. 강화된 Architecture Principle

## BP-SYS-01 Channel Decoupling

```text
Channel은 DB/Legacy에 직접 접근하지 않고
Application / Integration Layer를 통한다.
```

## BP-SYS-02 Transaction Separation

```text
계정 Transaction과 정보계 Query를 분리한다.
```

## BP-SYS-03 Event Isolation

```text
고객행동 Event는 Kafka 기반 비동기 처리영역으로 분리한다.
```

## BP-SYS-04 Dual Data Platform

```text
RDW = 최신성
ADW = 분석성
```

## BP-SYS-05 Integration by Purpose

```text
API / MCA / CDC / ETL / FOS / MFT를
업무 목적에 따라 구분한다.
```

## BP-SYS-06 Governance by Design

```text
Meta / Quality / Flow를 Data Platform에 횡단 적용한다.
```

## BP-SYS-07 Legacy Coexistence

```text
Legacy/Big Data/외부 시스템을 즉시 제거하지 않고
표준 연계계층을 통해 공존시킨다.
```

---

# 42. 강화된 금지패턴

## 금지 1 — Channel → DB 직접접근

```text
[금지]
Channel
  ↓
DB

[권고]
Channel
  ↓
Application
  ↓
Data
```

---

## 금지 2 — Event를 Online WAS Thread에서 직접 처리

```text
[금지]
HTTP Thread
  ↓
고객행동 대량 처리

[권고]
Event
  ↓
Kafka
  ↓
Consumer / EBM
```

---

## 금지 3 — RDW/ADW 기능 혼재

```text
RDW
→ 실시간/준실시간

ADW
→ 분석/마트
```

---

## 금지 4 — 모든 Interface를 REST로 강제

원본은 목적별로 다음을 분리한다.

```text
MCA
API
CDC
Kafka
ETL
FOS
MFT
```

---

## 금지 5 — Data Governance 사후 수동관리

```text
JDBC / Polling / File
→ 자동 수집
```

이 가능한 구조로 설계되어 있다.

---

# 43. 주요 GAP / 확인 필요

| 항목 | 상태 |
|---|---|
| 마케팅정보의 공식 정의 | 확인 필요 |
| Feedback의 정확한 Source/Target | 확인 필요 |
| Agent 제품/프로토콜 | 확인 필요 |
| Polling 대상 및 주기 | 확인 필요 |
| JDBC Read/Write 범위 | 확인 필요 |
| RDW → ADW 실제 흐름 | 확인 필요 |
| API Gateway 적용 기준 | 확인 필요 |
| MCA와 API Gateway 업무구분 | 확인 필요 |
| FOS와 MFT 역할차이 | 확인 필요 |
| Big Data와 ADW 역할중복 | 확인 필요 |
| Kafka Topic/Partition | 확인 필요 |
| EBM 처리 SLA | 확인 필요 |
| 외부기관별 Interface | 확인 필요 |
| HA/DR Node 구조 | 이미지에 없음 |

---

# 44. 최종 TOP-DOWN Architecture Baseline

```text
L0 Business / User
   ↓
Channel

L1 Access
   ↓
MCA / JSON / Event

L2 Application
   ↓
Marketing / BI

L3 Runtime
   ↓
Event Processing / API Gateway / Web

L4 Data
   ↓
RDW / ADW

L5 Governance
   ↓
BizMeta / DQ / Data Flow

L6 Integration
   ↓
CDC / ETL / FOS / MFT / 대외 MCA

L7 Ecosystem
   ↓
Core / Related / Big Data / External

L8 Operations
   ↓
Performance / Security / HA / Monitoring / Traceability
```

---

# 45. 최종 강화 TEXT 그림 — 한 장 요약

```text
┌──────────────────────────────────────────────────────────────────────────────┐
│                               USER / CHANNEL                                 │
│                                                                              │
│ 계정단말      정보계단말        Web           Mobile         SMS/PUSH/MAIL  │
└────┬─────────────┬──────────────┬──────────────┬────────────────────────────┘
     │             │              │              │
     │ 계정거래    │ JSON         │ Event        │ Event
     ▼             ▼              ▼              ▼
   ┌─────┐   ┌────────────┐   ┌─────────────────────────────────────┐
   │ MCA │   │ Web Layer  │   │      Wise Collector / Kafka        │
   └──┬──┘   └─────┬──────┘   └─────────────────┬───────────────────┘
      │            │                            │
      ▼            ▼                            ▼
┌──────────┐ ┌──────────────────────┐      ┌──────────────────┐
│Core/Acct │ │ Information Service  │      │ Event Processing │
│          │ │  Marketing / BI      │      │ EBM / Behavior   │
└────┬─────┘ └──────────┬───────────┘      └─────────┬────────┘
     │ CDC               │ JDBC/API                   │ API
     │                   │                            ▼
     │                   │                           UMS
     │                   │                            │
     │                   │                            ▼
     │                   │                      SMS/PUSH/MAIL
     │                   │
     └──────────────┬────┘
                    ▼
          ┌────────────────────────────┐
          │        DATA PLATFORM       │
          │                            │
          │   RDW            ADW       │
          │   실시간         분석      │
          └────────────┬───────────────┘
                       │
                       ▼
          ┌────────────────────────────┐
          │       DATA GOVERNANCE      │
          │ BizMeta / Quality / Flow   │
          └────────────┬───────────────┘
                       │
        ┌──────────────┼───────────────────────────────────────┐
        ▼              ▼                                       ▼
 API Gateway       ETL / FOS / MFT                        대외 MCA
        │              │                                       │
        └──────────────┼───────────────────────────────────────┘
                       ▼
          ┌────────────────────────────┐
          │ LEGACY / RELATED / EXTERNAL│
          │ Core / Risk / BigData /    │
          │ Affiliate / Institution    │
          └────────────────────────────┘
```

---

# 46. 최종 평가

이 장표는 단순 시스템 배치 그림이 아니라 다음 5개 Architecture View를 동시에 담고 있다.

```text
1. Application View
2. Integration View
3. Runtime View
4. Data View
5. Governance View
```

TOP-DOWN으로 해석했을 때 전체 의도는 다음과 같다.

```text
Channel
  ↓
Purpose-specific Integration
  ↓
Business Application
  ↓
Event / Online Runtime
  ↓
Real-time + Analytical Data Platform
  ↓
Data Governance
  ↓
Legacy / External Ecosystem
```

따라서 이 장표는 향후 다음 문서의 상위 기준선으로 사용하는 것이 적절하다.

```text
Big Picture
  ↓
Logical Architecture
  ↓
Application Architecture
  ↓
Interface Architecture
  ↓
Runtime Architecture
  ↓
Data Architecture
  ↓
Data Governance
  ↓
Physical Architecture
  ↓
Security / Operation / NFR
  ↓
Runtime Validation
```

결론적으로 본 장표는 **차세대 정보계의 채널·업무·이벤트·데이터·거버넌스·내외부 연계를 하나의 TOP-DOWN 구조로 설명하는 최상위 System Architecture Baseline**이다.
