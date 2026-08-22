# NSIGHT 인터페이스 구성도 분석
## 채널 · Application · 데이터플랫폼 · API Gateway · CDC · ETL · FOS · Kafka · 개발/배포 통합 구조

## 0. 문서 개요

본 문서는 제공된 **「인터페이스 구성도」 장표(페이지 105)**를 기준으로,
차세대 정보계의 인터페이스 아키텍처를 다음 관점에서 분석·정리한 문서이다.

- 채널/단말 연계
- 마케팅플랫폼
- BI 포탈
- 데이터거버넌스
- RDW / ADW 데이터플랫폼
- API Gateway(Cruz APIM)
- EAI
- CDC 및 CDC 중계
- ETL
- FOS / MFT 파일 연계
- Kafka 이벤트 스트리밍
- 고객행동 이벤트 처리
- 대내/대외 시스템 및 Legacy 연계
- 통합개발환경(GitLab / GitLab Runner / Nexus)
- 운영/배포 지원영역

### 작성 원칙

1. 원본 장표에서 직접 확인되는 명칭과 연결관계를 우선 보존한다.
2. 장표의 선이 복잡하여 방향이 명확하지 않은 경우 임의로 Source/Target 방향을 확정하지 않는다.
3. 상세 프로토콜, Port, Timeout, Retry, 인증방식 등 장표에 없는 값은 추가하지 않는다.
4. 원본에 직접 표기된 내용은 **[FACT]**, 구조상 해석은 **[ANALYSIS]**, 추가 확인사항은 **[GAP]**으로 구분한다.

---

# 1. 아키텍처 전체 요약

이 장표는 차세대 정보계 인터페이스를 하나의 단일 연계기술로 처리하지 않고,
연계 특성별로 다음과 같이 분리한 구조를 보여준다.

```text
온라인/전문
    → HTTP / JSON / GUID
    → API Gateway(Cruz APIM)
    → MCA / MCI / GSE / EAI

DB 실시간 변경
    → CDC
    → CDC 중계
    → RDW

DB/대량 데이터
    → ETL
    → RDW / ADW / Legacy

파일
    → FOS
    → 필요 시 MFT

이벤트
    → Kafka
    → 고객행태 / 이벤트정보
    → 행동정보처리 / 실시간처리

Application DB Access
    → JDBC

개발/배포
    → GitLab / GitLab Runner / Nexus
```

---

# 2. 전체 텍스트 아키텍처 그림

```text
┌─────────────────────────────────────────────────────────────────────────────────────┐
│                               채널 / 대면 / 단말                                     │
│                                                                                     │
│  [통합업무시스템] ───────────────┐                                                   │
│                                  │                                                   │
│                         [채널통합/영업점 MCA] ───── 계정계/연계                       │
│                                  │                                                   │
│                             싱글뷰 │ HTTP · JSON · GUID                              │
│                                  ▼                                                   │
│                       ┌───────────────────────────────┐                               │
│                       │        어플리케이션           │                               │
│                       │                               │                               │
│  [정보계 단말] ──────▶│  ┌──── 마케팅플랫폼 ───────┐ │                               │
│  [Package UI] ─HTTP──▶│  │ 미니싱글뷰              │ │                               │
│                       │  │ Service                  │ │                               │
│                       │  │ NH Cloud FWK / WAS       │ │                               │
│                       │  │                         │ │                               │
│                       │  │ 마케팅플랫폼            │ │                               │
│                       │  │ Service                  │ │                               │
│                       │  │ NH Cloud FWK / WAS       │ │                               │
│                       │  └─────────────────────────┘ │                               │
│                       │                               │                               │
│                       │  ┌──── BI 포탈 ─────────────┐ │                               │
│                       │  │ BI Portal / Data Eye     │ │                               │
│                       │  │ 신용실적 Service          │ │                               │
│                       │  │ Self BI Solution Engine  │ │                               │
│                       │  │ OLAP Service / OLAP AP   │ │                               │
│                       │  └─────────────────────────┘ │                               │
│                       │                               │                               │
│                       │  ┌──── 데이터거버넌스 ──────┐ │                               │
│                       │  │ 비즈메타/데이터품질       │ │                               │
│                       │  │ 데이터흐름관리            │ │                               │
│                       │  └─────────────────────────┘ │                               │
│                       └──────────────┬────────────────┘                               │
│                                      │ JDBC                                          │
└──────────────────────────────────────┼───────────────────────────────────────────────┘
                                       │
                                       ▼
                    ┌────────────────────────────────────────┐
                    │             데이터플랫폼               │
                    │                                        │
                    │   ┌───────────┐      ┌─────────────┐   │
                    │   │ RDW       │      │ ADW         │   │
                    │   │ 실시간    │      │ 대량분석    │   │
                    │   │ DBMS      │      │ DBMS        │   │
                    │   └───────────┘      └─────────────┘   │
                    └─────────────┬──────────────────────────┘
                                  │
                    ┌─────────────┼───────────────────────────┐
                    │             │                           │
                    │             │                           │
              CDC / CDC중계       │ ETL                     FOS / MFT
                    │             │                           │
                    ▼             ▼                           ▼
        ┌────────────────┐  ┌──────────────┐         ┌─────────────────┐
        │ 코어DB/계정계   │  │ Legacy / DB  │         │ 파일 연계 대상  │
        │ 코어뱅킹        │  │ 타 시스템    │         │ 대내/대외       │
        │ 연계뱅킹        │  └──────────────┘         └─────────────────┘
        │ 단위업무 / BCV  │
        └──────┬─────────┘
               │
               │ EAI
               ▼
       ┌────────────────────────────┐
       │ API Gateway (Cruz APIM)    │
       └─────────────┬──────────────┘
                     │
            ┌────────┴─────────────────────────┐
            │                                  │
            ▼                                  ▼
      [대내 연계]                          [대외 MCA]
                                                │
               ┌────────────────────────────────┼─────────────────────┐
               ▼                ▼               ▼            ▼        ▼
            NH생명           NH손해          NH멤버스    농협신용보증  ...
                                                       NH경제지주 / KT /
                                                       Nice / Ko 등
```

---

# 3. 장표의 논리적 영역

장표를 아키텍처 영역으로 분리하면 다음과 같다.

| 영역 | 주요 구성요소 |
|---|---|
| 채널/단말 | 통합업무시스템, 영업점 MCA, 정보계 단말, Package UI |
| Application | 마케팅플랫폼, 미니싱글뷰, BI포탈, 신용실적, Self BI, OLAP, 데이터거버넌스 |
| 데이터접근 | JDBC, Batch AP |
| 데이터플랫폼 | RDW, ADW |
| 온라인 연계 | API Gateway(Cruz APIM), EAI, GSE |
| CDC | CDC, CDC 중계 |
| 파일 연계 | FOS, MFT |
| 배치 데이터 | ETL |
| 이벤트 | Wise Collector, Kafka, 고객행태, 이벤트정보, 행동정보처리서버, 실시간처리서버 |
| 메시지 | UMS, SMS/PUSH/MAIL |
| 대내 시스템 | 코어뱅킹, 연계뱅킹, 단위업무, BCV, 코어 DB |
| 대외 | NH생명, NH손해, NH멤버스, 농협신용보증, NH경제지주, KT, Nice, Ko |
| Legacy | 카드정보계, 카드DW, 회계관리, 리스크관리, 경제, 로우코드, Big Data 등 |
| 개발/배포 | GitLab, GitLab Runner, Nexus |
| 운영지원 | 통신관리, FDS, ITSM, 배치작업관리, 단말, FWK, 마케팅, BI포탈 등 |

---

# 4. 채널 및 단말 인터페이스

## 4.1 [FACT] 영업점/통합업무 계열

장표 좌측 상단에는 다음 요소가 표현되어 있다.

```text
통합업무시스템
      │
      ▼
채널통합 / 영업점 MCA
      │
      ├─ 계정계 연계
      │
      └─ 싱글뷰
           │
           ▼
      Application
```

마케팅플랫폼 영역으로 진입하는 온라인 연계 구간에는 다음이 직접 표기되어 있다.

```text
HTTP · JSON · GUID
```

따라서 앞서 제시된 인터페이스 표준정의와 동일하게,
Application 온라인 연계의 핵심 형식이 **HTTP/JSON + GUID**임을 다시 확인할 수 있다.

---

# 5. 정보계 단말

장표 좌측에는 다음 구성요소가 확인된다.

```text
정보계 단말
│
├─ 인증/인가
│
├─ 단말배포
│
└─ 단말관리
```

정보계 단말에서 Application으로 이어지는 구간에도 다음이 표기되어 있다.

```text
HTTP · JSON · GUID
```

### [ANALYSIS]

정보계 단말에서 마케팅플랫폼/BI 영역으로 직접 진입하는 온라인 호출은
앞 장에서 정의된 “정보계 단말 → 정보계 직접거래” 유형과 연결된다.

다만 실제 Endpoint와 인증/인가 방식은 이 장표에서 확인되지 않는다.

---

# 6. Package UI

장표에는 `Package UI`가 별도로 존재하며 Application BI/솔루션 영역과 HTTP 기반으로 연결된다.

```text
Package UI
    │
   HTTP
    │
    ▼
BI / Package Solution
```

이는 패키지 기반 UI가 API Gateway를 반드시 경유하는 일반 시스템연계와 달리
Application 솔루션과 직접 연계하는 유형이 존재함을 보여준다.

---

# 7. Application 영역

Application 영역은 크게 3개 서비스군으로 나뉜다.

```text
Application
│
├─ 마케팅플랫폼
├─ BI 포탈
└─ 데이터거버넌스
```

---

# 8. 마케팅플랫폼

장표의 마케팅플랫폼은 두 개의 주요 Application 영역으로 표현된다.

## 8.1 미니싱글뷰

```text
미니싱글뷰
├─ Service
├─ NH Cloud FWK
└─ WAS
```

## 8.2 마케팅플랫폼

```text
마케팅플랫폼
├─ Service
├─ NH Cloud FWK
└─ WAS
```

### [ANALYSIS]

장표는 미니싱글뷰와 마케팅플랫폼을 동일 계층의 독립 서비스 실행영역으로 두고,
공통적으로 `Service → NH Cloud FWK → WAS` 구조를 표현한다.

---

# 9. BI 포탈

BI 포탈에는 다음 컴포넌트가 표현된다.

| 영역 | 장표 표기 |
|---|---|
| BI Portal | BI Portal / Data Eye / Spring Boot / WAS |
| 신용실적 | 신용실적 / Service / 프레임워크 / WAS |
| Self BI | Self BI / 솔루션 / Engine / WAS |
| OLAP | Service / WAS |
| OLAP AP | MSTR |

이를 텍스트로 표현하면:

```text
BI 포탈
│
├─ BI Portal
│  ├─ Data Eye
│  ├─ Spring Boot
│  └─ WAS
│
├─ 신용실적
│  ├─ Service
│  ├─ 프레임워크
│  └─ WAS
│
├─ Self BI
│  ├─ 솔루션
│  ├─ Engine
│  └─ WAS
│
├─ OLAP
│  ├─ Service
│  └─ WAS
│
└─ OLAP AP
   └─ MSTR
```

---

# 10. 데이터거버넌스

장표의 데이터거버넌스 영역은 다음처럼 구성되어 있다.

```text
데이터거버넌스
│
├─ 비즈메타/데이터품질
│  ├─ 솔루션 서비스
│  └─ AP
│
└─ 데이터흐름관리
   ├─ 솔루션 서비스
   └─ AP
```

하단에는 이 영역이 `VM`으로 표현된다.

---

# 11. Application → Database 접근

Application 영역 오른쪽에는 다음 데이터 접근 구조가 보인다.

```text
Application
     │
     ▼
   JDBC
     │
     ▼
Database
```

또한 `배치 AP`가 별도 노드로 배치되어 데이터플랫폼과 연결된다.

```text
Batch Processing
      │
      ▼
    배치 AP
      │
      ▼
Database Platform
```

### [ANALYSIS]

장표는 온라인 Application의 JDBC DB 접근과
배치 AP의 데이터 처리 경로를 논리적으로 구분해서 표현한다.

---

# 12. 데이터플랫폼

데이터플랫폼은 두 개의 핵심 DB 영역으로 구성된다.

```text
데이터플랫폼
│
├─ RDW
│  ├─ 실시간
│  └─ DBMS
│
└─ ADW
   ├─ 대량분석
   └─ DBMS
```

즉 장표 자체에서 RDW와 ADW의 역할을 매우 간단하게 다음처럼 구분한다.

| DB | 역할 |
|---|---|
| RDW | 실시간 |
| ADW | 대량분석 |

이는 앞서 분석한 DB 아키텍처의 책임분리와 일치한다.

---

# 13. 계정계/대내 연계

장표 상단 중앙에는 다음 대내 업무영역이 있다.

```text
대내 연계
│
├─ 코어뱅킹
├─ 연계뱅킹
├─ 단위업무
└─ BCV
```

코어뱅킹 하단에는 `코어 DB`가 표현된다.

```text
코어뱅킹
   │
   ▼
코어 DB
```

---

# 14. CDC 구조

코어 DB와 데이터플랫폼 사이에는 다음 구조가 표현된다.

```text
코어 DB
   │
   ▼
  CDC
   │
   ▼
CDC 중계
   │
   ▼
  RDW
```

### [FACT]

장표는 `CDC`와 `CDC중계`를 분리해서 표시한다.

이는 앞서 OGG 구성도에서 확인된
**Source DB → Downstream CDC 중계 → RDW** 구조의 상위 논리 표현으로 볼 수 있다.

---

# 15. API Gateway

대내연계 영역 하단에는 다음 중심 컴포넌트가 있다.

```text
API Gateway (Cruz APIM)
```

API Gateway는 장표에서 다음 영역들과 연결되어 있다.

- EAI
- 대내 연계
- 대외 연계
- 정보계/데이터플랫폼 주변 시스템

### 전체 개념

```text
            EAI
             │
             ▼
      API Gateway
       (Cruz APIM)
          /      \
         /        \
   대내 시스템    대외 시스템
```

---

# 16. EAI

API Gateway 상단에는 `EAI`가 별도 구성요소로 표현된다.

```text
대내 업무
   │
   ▼
  EAI
   │
   ▼
API Gateway
```

장표 자체만으로 EAI와 API Gateway의 세부 호출방향은 완전히 확정할 수 없으나,
앞서 인터페이스 표준 장표에서 제시된
**API G/W와 EAI 간 연계는 인터페이스 솔루션 내부에서 처리** 원칙과 연결된다.

---

# 17. GSE / 농협은행

장표 상단에는 다음 구조가 있다.

```text
GSE
 │
 ▼
농협 은행
```

### [ANALYSIS]

앞서 온라인 인터페이스 표준에서 정의한
**타 법인(은행) ↔ 정보계 간 GSE 경유** 원칙을 전체 구성도에 배치한 것으로 볼 수 있다.

---

# 18. 대외 MCA

장표 우측 상단에는 `대외 MCA`와 다수 외부기관이 연결되어 있다.

원본에서 읽히는 외부기관:

- NH생명
- NH손해
- NH멤버스
- 농협신용보증
- NH경제지주
- KT
- Nice
- Ko

구조:

```text
정보계 / API Gateway
       │
       ▼
    대외 MCA
       │
       ├─ NH생명
       ├─ NH손해
       ├─ NH멤버스
       ├─ 농협신용보증
       ├─ NH경제지주
       ├─ KT
       ├─ Nice
       └─ Ko
```

---

# 19. 파일 인터페이스

데이터플랫폼 오른쪽/하단에는 다음 파일연계 구성요소가 표현된다.

```text
MFT
 │
 ▼
FOS
```

단, 실제 선은 여러 시스템으로 분기되어 있으므로 모든 연계방향을 단방향으로 확정하면 안 된다.

전체 기능적 의미는 다음과 같이 정리할 수 있다.

```text
System / Data Platform
        │
        ▼
       FOS
        │
        ▼
       MFT
        │
        ▼
Internal / External System
```

또는 시스템별로 FOS가 직접 연계되고,
외부 MFT 연계가 필요한 경우 MFT와 연결되는 구조로 볼 수 있다.

---

# 20. ETL

`ETL`은 FOS와 동일한 인터페이스 계층 부근에 독립 블록으로 배치된다.

```text
Source DB / RDW / Legacy
       │
       ▼
      ETL
       │
       ▼
ADW / Target DB
```

앞서 데이터 인터페이스 표준에서 확인된 핵심 규칙:

```text
RDW
 │
 ▼
ETL
 │
 ▼
ADW
```

가 이번 전체 구성도에서 데이터플랫폼/Legacy 연결선으로 통합되어 있다.

---

# 21. Legacy 영역

우측에는 별도 `Legacy` 영역이 있다.

원본 장표에서 읽히는 항목은 다음과 같다.

```text
Legacy
│
├─ 카드정보계
├─ 카드DW
├─ 회계관리
├─ 리스크관리
├─ ...
├─ 회계관리
├─ 리스크관리
├─ 경제
└─ 로우코드

Big Data
```

### 주의

원본 장표 내부에 일부 항목이 중복 표기되거나 흐릿한 영역이 존재하므로,
Legacy 전체 Inventory를 이 장표만으로 확정해서는 안 된다.

---

# 22. 고객행동 이벤트 처리

장표 하단은 온라인/DB 인터페이스와 별도로
고객 행동 이벤트 기반 실시간 처리 구조를 표현한다.

전체 구조:

```text
고객 행태
    │
    ▼
Wise Collector
    │
   Event
    │
    ▼
고객행동 데이터
   Kafka
    │
    ▼
 고객행태
    │
    ▼
 이벤트정보
    │
    ▼
행동정보처리서버
   Daemon
    │
    ▼
실시간처리서버
   EBM
```

이 영역은 하단에 `VM`으로 표시된다.

---

# 23. 이벤트 처리 ASCII 상세

```text
┌──────────────┐
│ 고객 행태     │
└──────┬───────┘
       ▼
┌────────────────┐
│ Wise Collector │
└──────┬─────────┘
       │ Event
       ▼
┌────────────────────┐
│ 고객행동 데이터     │
│ Kafka              │
└──────┬─────────────┘
       ▼
┌──────────────┐
│ 고객행태      │
└──────┬───────┘
       ▼
┌──────────────┐
│ 이벤트 정보   │
└──────┬───────┘
       ▼
┌────────────────────┐
│ 행동정보처리서버    │
│ Daemon             │
└──────┬─────────────┘
       ▼
┌────────────────────┐
│ 실시간처리서버      │
│ EBM                │
└────────────────────┘
```

---

# 24. UMS / 메시지

장표 하단 좌측에는 다음 구조가 있다.

```text
SMS
PUSH
MAIL
   ▲
   │
  UMS
```

### [ANALYSIS]

UMS가 SMS/PUSH/MAIL 전달을 위한 메시징 연계 영역으로 배치되어 있음을 알 수 있다.

세부 메시지 프로토콜과 호출방향은 본 장표에서 확인되지 않는다.

---

# 25. 통합개발 환경

장표 하단 중앙/우측에는 통합개발 환경이 별도 영역으로 표현된다.

```text
통합개발 환경
│
├─ GitLab
├─ GitLab Runner
└─ NEXUS
```

### 역할 해석

| 구성요소 | 장표상 의미 |
|---|---|
| GitLab | 소스 관리 |
| GitLab Runner | 빌드/배포 실행 |
| Nexus | 라이브러리/Artifact 저장소 |

---

# 26. 소스/용어/배포 연계

통합개발환경 오른쪽에는 소스/배포 관련 운영 영역이 연결된다.

장표에서 읽히는 주요 키워드:

- 소스 배포
- 용어 배포
- 배치작업 관리
- 단말
- FWK
- 마케팅
- BI포탈
- 통신관리
- FDS
- ITSM
- eCAMS
- 연계정보

### [ANALYSIS]

통합개발환경에서 생성·관리되는 소스와 산출물이
각 Application/Framework/단말/마케팅/BI 계열에 배포되는 운영 흐름을
하단에서 별도 표현한 것으로 볼 수 있다.

일부 명칭은 이미지 해상도상 완전한 판독이 어렵기 때문에
최종 운영컴포넌트명은 원본 설계서 확인이 필요하다.

---

# 27. 인터페이스 기술별 역할 분류

| 메커니즘 | 역할 | 장표 위치 |
|---|---|---|
| HTTP / JSON / GUID | Application 온라인 거래 | 채널/단말 → Application |
| JDBC | Application → DB 접근 | Application → 데이터플랫폼 |
| API Gateway(Cruz APIM) | 시스템 간 API 연계 | 대내/대외 연계 |
| EAI | 기업 내부 연계 | API Gateway 상단 |
| GSE | 타 법인/은행 연계 | 상단 |
| CDC | 원천 DB 실시간 변경 수집 | 코어 DB → CDC |
| CDC 중계 | Source DB 부하 분산/중계 | CDC → RDW |
| ETL | 배치/대량 데이터 동기화 | 데이터플랫폼/Legacy |
| FOS | 표준 파일 연계 | 데이터/Legacy/대외 |
| MFT | 파일 전송 연계 | FOS 연계영역 |
| Kafka | 이벤트 스트리밍 | 고객행동 이벤트 |
| UMS | 메시징 | SMS/PUSH/MAIL |
| GitLab | 소스 관리 | 통합개발 |
| GitLab Runner | 배포 실행 | 통합개발 |
| Nexus | Artifact/라이브러리 | 통합개발 |

---

# 28. 인터페이스 경로별 구분

## 28.1 온라인 동기 호출

```text
정보계 단말 / 채널
       │
 HTTP / JSON / GUID
       ▼
Application
       │
       ▼
Service / WAS
```

---

## 28.2 Application DB 접근

```text
Application
    │
   JDBC
    │
    ▼
RDW / ADW
```

---

## 28.3 대내/대외 API 연계

```text
Application / 정보계
       │
       ▼
API Gateway(Cruz APIM)
       │
       ├─ EAI
       ├─ 대내 시스템
       └─ 대외 연계
```

---

## 28.4 실시간 DB 변경 연계

```text
Core DB
   │
  CDC
   │
   ▼
CDC 중계
   │
   ▼
RDW
```

---

## 28.5 데이터 배치 연계

```text
RDW / Source DB
      │
     ETL
      │
      ▼
ADW / Target DB / Legacy
```

---

## 28.6 파일 연계

```text
System
  │
  ▼
 FOS
  │
  └─ 필요 시 MFT
       │
       ▼
Target
```

---

## 28.7 이벤트 연계

```text
Event
  │
  ▼
Kafka
  │
  ▼
Event Processing
  │
  ▼
Realtime Processing
```

---

# 29. 기존 인터페이스 표준과의 정합성

앞 장의 인터페이스 표준과 이번 전체 구성도를 비교하면 다음과 같이 연결된다.

| 표준 정의 | 이번 구성도 구현 위치 |
|---|---|
| JSON/HTTP 온라인 표준 | 채널/단말 ↔ Application |
| GUID 거래추적 | Application 진입선 |
| API Gateway 일원화 | Cruz APIM |
| Kafka 이벤트 | 고객행동 데이터 Kafka |
| CDC 실시간 데이터 | Core DB → CDC → CDC중계 → RDW |
| ETL 배치 데이터 | ETL 블록 |
| FOS 파일 표준 | FOS 블록 |
| MFT 내부연계 | MFT |
| GSE 타 법인 연계 | GSE ↔ 농협은행 |
| MCA 대외/채널 | 영업점 MCA / 대외 MCA |

따라서 이 장표는 **앞에서 정의한 인터페이스 정책을 실제 시스템 컴포넌트와 연결한 전체 배치도**로 볼 수 있다.

---

# 30. 데이터플랫폼 관점의 핵심 Flow

```text
             Core Banking
                  │
                 CDC
                  │
                  ▼
             CDC Relay
                  │
                  ▼
               RDW
             실시간 DB
                  │
            ┌─────┴─────┐
            │           │
           ETL       Application
            │          JDBC
            ▼           │
           ADW ◀────────┘
        대량분석 DB
```

### [ANALYSIS]

RDW는 온라인/실시간 계열의 데이터 기반,
ADW는 대량 분석 계열의 데이터 기반으로 분리되어 있다.

---

# 31. 전체 End-to-End 온라인 Flow

```text
사용자
  │
  ▼
정보계 단말 / 영업점 채널
  │
  │ HTTP / JSON / GUID
  ▼
Application
  │
  ├─ 미니싱글뷰
  ├─ 마케팅플랫폼
  ├─ BI Portal
  ├─ 신용실적
  ├─ Self BI
  └─ OLAP
  │
  ├──────── JDBC ────────▶ RDW / ADW
  │
  └──────── API ─────────▶ API Gateway
                              │
                              ├─ EAI
                              ├─ 대내
                              └─ 대외
```

---

# 32. 전체 End-to-End 데이터 Flow

```text
Core DB
   │
   ├─ CDC
   │
   ▼
CDC Relay
   │
   ▼
  RDW
   │
   │ ETL
   ▼
  ADW
   │
   ├─ BI
   ├─ 분석
   └─ 데이터거버넌스
```

---

# 33. 전체 End-to-End 이벤트 Flow

```text
고객 Event
   │
   ▼
Wise Collector
   │
   ▼
Kafka
   │
   ▼
고객행태
   │
   ▼
이벤트정보
   │
   ▼
행동정보처리 Daemon
   │
   ▼
실시간처리 EBM
```

---

# 34. Architecture Rule 후보

| Rule ID | Rule | 상태 |
|---|---|---|
| `IF-CFG-001` | 정보계 온라인 호출은 HTTP/JSON/GUID 표준을 적용한다 | 장표 근거 |
| `IF-CFG-002` | Application DB 접근은 JDBC 계층을 통해 수행한다 | 장표 근거 |
| `IF-CFG-003` | 시스템 간 API 연계는 API Gateway(Cruz APIM)를 중심으로 구성한다 | 장표 근거 |
| `IF-CFG-004` | EAI는 API Gateway와 연계하여 대내 시스템 연계를 지원한다 | 장표 근거 |
| `IF-CFG-005` | Core DB 실시간 변경은 CDC 및 CDC 중계를 통해 RDW로 전달한다 | 장표 근거 |
| `IF-CFG-006` | RDW는 실시간 데이터 처리 DB로 사용한다 | 장표 근거 |
| `IF-CFG-007` | ADW는 대량분석 DB로 사용한다 | 장표 근거 |
| `IF-CFG-008` | 대량/배치 데이터 연계는 ETL을 사용한다 | 장표 근거 |
| `IF-CFG-009` | 파일 연계는 FOS를 중심으로 하고 필요 시 MFT와 연계한다 | 장표 근거 |
| `IF-CFG-010` | 고객행동 이벤트는 Kafka를 통해 Streaming 처리한다 | 장표 근거 |
| `IF-CFG-011` | 타 법인/은행 연계에 GSE를 사용한다 | 장표 근거 |
| `IF-CFG-012` | 대외기관 연계는 대외 MCA 계층을 사용한다 | 장표 근거 |
| `IF-CFG-013` | 소스/배포 기반환경은 GitLab/GitLab Runner/Nexus를 사용한다 | 장표 근거 |
| `IF-CFG-014` | 온라인/데이터/파일/이벤트 인터페이스 메커니즘을 목적별로 분리한다 | 분석 |

---

# 35. 주요 아키텍처 특징

## 35.1 인터페이스 허브 분리

하나의 ESB나 단일 인터페이스 도구로 모든 연계를 처리하지 않는다.

```text
API        → API Gateway
Realtime DB→ CDC
Batch Data → ETL
File       → FOS/MFT
Event      → Kafka
```

이는 연계 메커니즘을 workload 특성별로 분리한 구조이다.

---

## 35.2 Application과 데이터플랫폼 분리

```text
Application
   │
  JDBC
   │
   ▼
Data Platform
```

Application과 DB를 논리적으로 독립 계층으로 분리한다.

---

## 35.3 실시간과 분석 데이터 분리

```text
RDW
→ Realtime

ADW
→ Large-scale Analytics
```

데이터 처리 목적에 따라 DB 플랫폼 역할을 분리한다.

---

## 35.4 이벤트 스트리밍 독립

고객행동 데이터는 Application 동기 호출과 분리하여 Kafka 이벤트 처리영역으로 구성되어 있다.

이는 실시간 고객행동 처리의 트래픽과 온라인 요청 트래픽을 분리하는 구조로 볼 수 있다.

---

# 36. 확인 필요 GAP

| GAP ID | 확인 항목 | 상태 |
|---|---|---|
| `GAP-CFG-001` | HTTP API URI 규칙 | 미표기 |
| `GAP-CFG-002` | GUID 구조/생성주체 | 미표기 |
| `GAP-CFG-003` | API Gateway 인증/인가 | 미표기 |
| `GAP-CFG-004` | API Gateway ↔ EAI 상세 프로토콜 | 미표기 |
| `GAP-CFG-005` | API Timeout / Retry / Circuit Breaker | 미표기 |
| `GAP-CFG-006` | JDBC URL / RAC Service | 미표기 |
| `GAP-CFG-007` | DB Connection Pool 정책 | 미표기 |
| `GAP-CFG-008` | CDC 제품/Process 상세 | 본 장표 미표기 |
| `GAP-CFG-009` | CDC 중계 HA | 미표기 |
| `GAP-CFG-010` | ETL Job/Schedule | 미표기 |
| `GAP-CFG-011` | FOS ↔ MFT 상세 방향/Protocol | 미표기 |
| `GAP-CFG-012` | Kafka Topic/Partition/Replication | 미표기 |
| `GAP-CFG-013` | Kafka Producer/Consumer 명세 | 미표기 |
| `GAP-CFG-014` | 고객행동 이벤트 Schema | 미표기 |
| `GAP-CFG-015` | UMS 인터페이스 상세 | 미표기 |
| `GAP-CFG-016` | 대외 MCA 전문 규격 | 미표기 |
| `GAP-CFG-017` | GSE 전문 규격 | 미표기 |
| `GAP-CFG-018` | Legacy 전체 시스템 Inventory | 일부만 표기 |
| `GAP-CFG-019` | 파일 재처리/보관정책 | 미표기 |
| `GAP-CFG-020` | API/CDC/ETL/FOS/Kafka 통합모니터링 | 미표기 |
| `GAP-CFG-021` | 통합개발환경 배포 Workflow | 상세 미표기 |
| `GAP-CFG-022` | GitLab Runner Target 배포 매핑 | 미표기 |
| `GAP-CFG-023` | Nexus Artifact 정책 | 미표기 |
| `GAP-CFG-024` | DR 전환 시 인터페이스 Endpoint 전환 | 미표기 |
| `GAP-CFG-025` | 장애 시 보상/재처리/Idempotency | 미표기 |

---

# 37. 운영 인터페이스 Inventory 권장항목

본 장표를 실제 Architecture Baseline으로 관리하기 위해 다음 속성을 별도로 연결하는 것이 적절하다.

| 영역 | 관리 항목 |
|---|---|
| 식별 | Interface ID |
| Source | Source System / Application / Server |
| Target | Target System / Application / Server |
| 유형 | Online / Data / File / Event |
| 기술 | HTTP / JDBC / API G/W / CDC / ETL / FOS / MFT / Kafka |
| 전문 | Request/Response Schema / GUID |
| 네트워크 | Protocol / Port / VIP / URL |
| 보안 | 인증/인가/암호화 |
| 성능 | TPS / Size / Throughput |
| 제어 | Timeout / Retry / Circuit Breaker |
| 데이터 | Sync/Async / Realtime/Batch |
| 운영 | Schedule / Monitoring / Alert |
| 장애 | Failover / Replay / Compensation |
| 책임 | Owner / 운영조직 |
| 추적 | 로그/거래ID/GUID |
| DR | DR Endpoint / 전환방식 |

---

# 38. 최종 인터페이스 Big Picture

```text
                                  사용자 / 채널
                                       │
                       HTTP / JSON / GUID
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────┐
│                         APPLICATION                              │
│                                                                  │
│ Marketing          BI Portal                Data Governance      │
│ ├ Mini SingleView  ├ BI Portal              ├ Meta / DQ         │
│ └ Marketing        ├ Credit                 └ Data Flow         │
│                    ├ Self BI                                      │
│                    └ OLAP                                         │
└───────────────┬──────────────────────────┬───────────────────────┘
                │ JDBC                     │ API
                ▼                          ▼
┌──────────────────────────────┐     ┌────────────────────────────┐
│       DATA PLATFORM          │     │ API Gateway (Cruz APIM)   │
│                              │     │                            │
│ RDW            ADW           │     │ EAI / Internal / External │
│ Realtime       Analytics     │     └──────────────┬─────────────┘
└──────┬───────────────┬───────┘                    │
       │               │                            ▼
       │               │                       대외 MCA / GSE
       │               │
       │ ETL           │ FOS / MFT
       ▼               ▼
 Legacy / DB       File Systems

       ▲
       │
   CDC Relay
       ▲
       │
     Core DB


                              EVENT PLATFORM
                                     │
Customer Event → Wise Collector → Kafka → Event Info
                                     │
                                     ▼
                         Behavior Processing Daemon
                                     │
                                     ▼
                              Realtime EBM


                              DEV / DEPLOYMENT
                                     │
                         GitLab / GitLab Runner
                                     │
                                   Nexus
                                     │
                                     ▼
                          Application / FWK / Batch
```

---

# 39. 최종 분석 결론

제공된 「인터페이스 구성도」 장표에서 확인되는 핵심은 다음과 같다.

1. 차세대 정보계 인터페이스는 **온라인, DB 실시간, 배치 데이터, 파일, 이벤트**를 각각 다른 표준 메커니즘으로 분리한다.
2. 채널/정보계 단말의 Application 호출에는 **HTTP / JSON / GUID**가 표현되어 있다.
3. Application은 **마케팅플랫폼, BI포탈, 데이터거버넌스**의 3개 주요 영역으로 구성된다.
4. 미니싱글뷰와 마케팅플랫폼은 `Service → NH Cloud FWK → WAS` 구조로 표현된다.
5. BI 영역에는 **BI Portal/Data Eye/Spring Boot, 신용실적, Self BI, OLAP**이 포함된다.
6. 데이터거버넌스에는 **비즈메타/데이터품질 및 데이터흐름관리**가 포함된다.
7. Application과 데이터플랫폼은 **JDBC**로 연결되는 구조가 명시되어 있다.
8. 데이터플랫폼은 **RDW(실시간)** 와 **ADW(대량분석)** 로 역할을 분리한다.
9. Core DB 변경정보는 **CDC → CDC중계 → RDW** 구조로 전달되는 상위 논리 구조가 표현된다.
10. 대내/대외 온라인 연계의 중심에는 **API Gateway(Cruz APIM)** 가 배치되어 있고 EAI와 연계된다.
11. 타 법인/은행 연계에는 **GSE**, 외부기관 연계에는 **대외 MCA**가 배치된다.
12. 파일 연계는 **FOS / MFT**, 배치 데이터 연계는 **ETL**로 분리되어 있다.
13. 고객행동 이벤트는 **Wise Collector → Kafka → 고객행태/이벤트정보 → 행동정보처리 Daemon → 실시간처리 EBM** 구조로 표현된다.
14. 메시징에는 **UMS → SMS/PUSH/MAIL** 구조가 포함된다.
15. 개발/배포 기반에는 **GitLab / GitLab Runner / Nexus**가 배치된다.
16. 이 장표는 이전 인터페이스 표준 정의에서 제시한 정책을 실제 시스템·솔루션·데이터플랫폼에 매핑한 **Physical/Logical Interface Integration View**로 사용할 수 있다.
17. 운영 Baseline으로 확정하려면 API URL, GUID 규격, JDBC/RAC Service, CDC HA, ETL Schedule, FOS/MFT 경로, Kafka Topic, 보안/Timeout/Retry, DR Endpoint 등의 상세설계를 추가해야 한다.

본 문서는 제공된 페이지 105 장표를 기준으로 한 **NSIGHT Interface Architecture Configuration Working Baseline**으로 활용한다.
