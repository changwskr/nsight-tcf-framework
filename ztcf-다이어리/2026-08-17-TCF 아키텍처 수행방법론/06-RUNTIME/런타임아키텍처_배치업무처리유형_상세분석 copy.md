# 런타임 아키텍처 — 배치 업무 처리 유형 상세 분석

## 1. 핵심 결론

이 장표는 차세대 정보계의 전체 런타임 아키텍처 가운데 **배치 프로그램의 작성·기동·실행·데이터 접근·후속 연계**를 설명하는 상세 런타임 장표다.

원본 상단 설명은 다음 의미를 가진다.

> **NH Cloud Framework의 배치 프레임워크를 기반으로 작성된 배치 프로그램을 Control-M 작업 자동화 시스템을 통해 실행하는 업무 유형**

원본 우측 업무 처리 유형은 다음 하나를 강조한다.

```text
⑫ 배치 처리
```

우측 설명의 핵심 FACT는 다음과 같다.

- **배치 프레임워크(NH Cloud Framework) 기반으로 작성된 어플리케이션이 Control-M 작업 자동화 시스템을 통해 정해진 시간에 동작**
- **배치, 배치 프레임워크, 일괄처리**

즉 장표가 정의하는 런타임 모델은 다음과 같다.

```text
Control-M
   │
   │ 스케줄 / 선후행 / 자동기동
   ▼
배치 AP
   │
   │ NH Cloud Framework Batch
   ▼
배치 프로그램
   │
   ├─ DB/JDBC
   ├─ RDW / ADW
   ├─ ETL / FOS / MFT
   ├─ API / 대내연계
   └─ 후속 Job
```

이 구조의 핵심은 **“업무 배치를 각 프로그램이 임의 스케줄링하는 것이 아니라, 배치 프레임워크로 표준화하고 Control-M이 중앙에서 작업 실행을 통제한다”**는 점이다.

따라서 이 장표는 단순한 Batch Job 설명이 아니라,

```text
배치 프로그램 표준
      +
중앙 스케줄링
      +
데이터 접근 표준
      +
선후행/재기동/운영통제
=
Batch Runtime Architecture
```

를 설명하는 기준선으로 볼 수 있다.

---

# 2. 원본 장표 FACT 전사

## 2.1 장표 제목

- 대제목: **배치 업무 처리 유형**
- 중앙 제목: **배치 업무 처리 유형**
- 우측 제목: **업무 처리 유형**

---

## 2.2 상단 설명

원본에서 확인되는 설명:

```text
NH Cloud Framework의 배치 프레임워크를 기반으로 작성된
배치 프로그램을 Control-M 작업 자동화 시스템을 통해
배치 작업을 실행하는 유형
```

※ 원본 상단 문장은 화면 폭에 따라 일부 문장 연결이 잘려 보이지만, 우측 상세 설명에서 `Control-M 작업 자동화 시스템을 통해 정해진 시간에 동작`이라고 명확히 보강된다.

---

## 2.3 업무 처리 유형 ⑫ — 배치 처리

원본 우측 설명:

- **배치 프레임워크 (NH Cloud Framework) 기반으로 작성된 어플리케이션이 Control-M 작업 자동화 시스템을 통해 정해진 시간에 동작**
- **배치, 배치 프레임워크, 일괄처리**

---

# 3. 원본 장표 주요 구성요소

## 3.1 배치 핵심 구성요소

- **배치 AP**
- **NH Cloud Framework 배치 프레임워크**
- **Control-M**
  - 우측 설명문에 명시
- JDBC
- Database
- RDW
- ADW
- ETL
- FOS
- MFT

## 3.2 애플리케이션 영역

### 마케팅플랫폼

- 미니 싱글뷰
  - Service
  - NH Cloud FWK
  - WAS
- 마케팅플랫폼
  - Service
  - NH Cloud FWK
  - WAS

### BI포탈

- BI Portal
  - Data Eye
  - Spring Boot
  - WAS
- 신용실적
  - Service
  - WAS/FWK 계열
- Self BI
  - 솔루션
  - Engine
  - WAS
- OLAP
  - Service
  - WAS
- OLAP AP
  - MSTR
  - WAS

### 데이터거버넌스

- 비즈메타/데이터품질 계열
- 데이터흐름관리 계열
- VM

※ 일부 세부 텍스트는 이미지 해상도상 확인 필요.

---

## 3.3 데이터플랫폼

- RDW
  - 실시간
  - DBMS
- ADW
  - 대용량
  - DBMS
- Database
- JDBC

---

## 3.4 대내 연계

- 코어뱅킹
- 연계뱅킹
- 단위업무
- 코어DB
- BCV
- GSE
- 농협은행
- EAI
- API Gateway(Cruz APIM)
- CDC

---

## 3.5 파일/데이터 연계

- MFT
- FOS
- ETL

---

## 3.6 Legacy

- 카드정보계
- 카드DW
- 회계관리
- 리스크관리
- 경제
- 로우코드
- Big Data
- 기타 Legacy

---

## 3.7 마케팅 이벤트 영역

- 고객행동 데이터
- Kafka
- 고객행태
- 이벤트정보
- 행동정보처리서버 / Daemon
- 실시간처리서버 / EBM
- Wise Collector
- UMS

---

## 3.8 개발/운영 환경

- 통합개발환경
  - GitLab
  - Git Runner
  - NEXUS
- 통합관리
  - FDS
  - ITSM
  - 배치작업관리
- eCAMS
- 연계정보
- 단말 / FWK / 마케팅 / BI포탈 배포 영역

---

# 4. 원본 장표 전체 구조 — 상세 텍스트 재현

> 아래 텍스트 그림은 원본에서 ⑫번으로 강조된 `배치 AP`와 데이터플랫폼, JDBC, ETL/FOS/MFT, 운영환경을 중심으로 재구성한 것이다.

```text
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                               배치 업무 처리 유형                                                            │
├──────────────────────────────┬──────────────────────────────────────────────────────┬────────────────────────────────────────┤
│ Application / Batch         │ Data / Integration                                   │ 대내 / Legacy / 운영                    │
│                              │                                                      │                                        │
│ ┌────────────────────────┐   │                                                      │ ┌──────────────────────────────────┐  │
│ │ 어플리케이션            │   │                                                      │ │ 대내 연계                        │  │
│ │ 마케팅플랫폼            │   │                                                      │ │ 코어뱅킹 / 연계뱅킹 / 단위업무   │  │
│ │ 미니싱글뷰 / Service   │   │                                                      │ │ 코어DB / BCV                     │  │
│ └────────────────────────┘   │                                                      │ └──────────────────────────────────┘  │
│                              │                                                      │                                        │
│ ┌────────────────────────┐   │                                                      │ ┌──────────────────────────────────┐  │
│ │ BI포탈                 │   │                                                      │ │ Legacy                           │  │
│ │ BI Portal / 신용실적   │   │                                                      │ │ 카드정보계 / 카드DW              │  │
│ │ Self BI / OLAP         │   │                                                      │ │ 회계 / 리스크 / 경제 / 로우코드  │  │
│ └────────────────────────┘   │                                                      │ │ Big Data                         │  │
│                              │                                                      │ └──────────────────────────────────┘  │
│ ┌────────────────────────┐   │                                                      │                                        │
│ │ 데이터거버넌스          │   │                                                      │                                        │
│ └────────────────────────┘   │                                                      │                                        │
│                              │                                                      │                                        │
│                              │      ┌──────────────── 데이터플랫폼 ──────────────┐  │                                        │
│                              │      │                                             │  │                                        │
│                              │      │  ┌──────────────┐  ┌──────────────┐       │  │                                        │
│                              │      │  │ RDW          │  │ ADW          │       │  │                                        │
│                              │      │  │ 실시간       │  │ 대용량       │       │  │                                        │
│                              │      │  │ DBMS         │  │ DBMS         │       │  │                                        │
│                              │      │  └──────────────┘  └──────────────┘       │  │                                        │
│                              │      └────────────────┬────────────────────────────┘  │                                        │
│                              │                       │                               │                                        │
│                              │           [Database / JDBC]                           │                                        │
│                              │                       │                               │                                        │
│                              │                    ┌──▼───┐                           │                                        │
│                              │                    │배치 AP│ ⑫                         │                                        │
│                              │                    └──┬───┘                           │                                        │
│                              │                       │                               │                                        │
│                              │             NH Cloud Framework                       │                                        │
│                              │                Batch Runtime                          │                                        │
│                              │                       │                               │                                        │
│                              │        ┌──────────────┼───────────────┐               │                                        │
│                              │        │              │               │               │                                        │
│                              │        ▼              ▼               ▼               │                                        │
│                              │      [ETL]          [FOS]           [MFT]              │                                        │
│                              │        │              │               │               │                                        │
│                              │        └──────────────┴───────────────┘               │                                        │
│                              │                       │                               │                                        │
│                              │                 Data/File Work                        │                                        │
│                              │                                                      │                                        │
│                              │                                                      │ ┌──────────────────────────────────┐  │
│                              │                                                      │ │ 배치 작업 관리 / 통합관리       │  │
│                              │                                                      │ │ Control-M (설명문 명시)         │  │
│                              │                                                      │ │ FDS / ITSM                       │  │
│                              │                                                      │ └──────────────────────────────────┘  │
│                              │                                                      │                                        │
│                              │                                                      │ ┌──────────────────────────────────┐  │
│                              │                                                      │ │ 통합개발환경                     │  │
│                              │                                                      │ │ GitLab / Git Runner / NEXUS      │  │
│                              │                                                      │ └──────────────────────────────────┘  │
└──────────────────────────────┴──────────────────────────────────────────────────────┴────────────────────────────────────────┘
```

> **FACT:** 원본 중앙에는 `배치 AP`가 ⑫로 강조되어 있고, 인접하여 `JDBC`, `Database`, RDW/ADW가 배치되어 있다.  
> **FACT:** 우측 설명에서 `Control-M 작업 자동화 시스템`을 명시한다.  
> **ANALYSIS:** Control-M → 배치 AP의 직접 호출선은 장표에 명확한 화살표로 판독되지 않으므로, 상세 런타임에서는 설명문을 근거로 실행관계를 재구성한다.

---

# 5. 배치 런타임 핵심 구조

```text
                  ┌──────────────────────┐
                  │      Control-M       │
                  │ Job Automation       │
                  └──────────┬───────────┘
                             │
                    Schedule / Trigger
                             │
                             ▼
                  ┌──────────────────────┐
                  │       배치 AP        │
                  │ NH Cloud Framework   │
                  │   Batch Program      │
                  └──────────┬───────────┘
                             │
          ┌──────────────────┼───────────────────┐
          │                  │                   │
          ▼                  ▼                   ▼
      JDBC / DB          ETL / Data          FOS / MFT
          │                  │                   │
          ▼                  ▼                   ▼
      RDW / ADW          Data Load          File Interface
```

이 그림이 장표의 핵심 런타임 모델이다.

---

# 6. 배치 처리 End-to-End 상세 흐름

```text
┌────────────────────────────── 운영 스케줄 ──────────────────────────────┐
│                                                                        │
│ Control-M                                                              │
│   │                                                                    │
│   ├─ 정해진 시간                                                       │
│   ├─ 선행 Job 완료                                                     │
│   ├─ 파일 도착                                                         │
│   ├─ 업무일자                                                          │
│   └─ 운영자 수동기동                                                   │
│                                                                        │
└───────────────────────────────┬────────────────────────────────────────┘
                                │
                                ▼
┌────────────────────────────── 배치 실행 ────────────────────────────────┐
│                                                                        │
│ 배치 AP                                                                │
│   │                                                                    │
│   ▼                                                                    │
│ NH Cloud Framework Batch                                               │
│   │                                                                    │
│   ├─ Job 초기화                                                        │
│   ├─ Parameter 해석                                                    │
│   ├─ 업무일자 결정                                                     │
│   ├─ Step 실행                                                         │
│   ├─ Commit / Rollback                                                 │
│   └─ 상태/로그 기록                                                    │
│                                                                        │
└───────────────────────────────┬────────────────────────────────────────┘
                                │
        ┌───────────────────────┼────────────────────────┐
        │                       │                        │
        ▼                       ▼                        ▼
┌───────────────┐      ┌─────────────────┐       ┌─────────────────┐
│ JDBC / DB     │      │ ETL / Data Load │       │ FOS / MFT       │
│ RDW / ADW     │      │ 변환/적재       │       │ 파일 송수신     │
└───────┬───────┘      └────────┬────────┘       └────────┬────────┘
        │                       │                         │
        └───────────────────────┼─────────────────────────┘
                                ▼
                     ┌────────────────────┐
                     │ Job Result         │
                     │ Success / Failure  │
                     └─────────┬──────────┘
                               │
                               ▼
                           Control-M
                               │
                 ┌─────────────┼─────────────┐
                 ▼             ▼             ▼
             Next Job       Retry        Alert/Stop
```

---

# 7. Control-M 역할 상세 분석

## 7.1 원본 FACT

원본 우측 설명은 다음을 명확히 말한다.

```text
NH Cloud Framework 기반 배치 어플리케이션이
Control-M 작업 자동화 시스템을 통해
정해진 시간에 동작
```

따라서 Control-M의 최소 책임은 다음과 같이 해석할 수 있다.

- 배치 작업 기동
- 지정 시간 실행
- 작업 자동화

---

## 7.2 ANALYSIS — 운영 스케줄러로서의 책임

후속 상세설계에서는 Control-M에 다음 책임이 정의될 가능성이 높다.

```text
Control-M
 ├─ Job Schedule
 ├─ Calendar
 ├─ Dependency
 ├─ Resource
 ├─ Retry
 ├─ Alert
 ├─ Manual Rerun
 └─ Job History
```

### 중요한 원칙

배치 프로그램 자체에 다음을 하드코딩하지 않는 것이 좋다.

```text
X 매일 02:00 실행
X 선행 Job 3개 완료 대기
X 재시도 3회
X 휴일 캘린더
```

이러한 **운영 스케줄 정책은 Control-M**이 관리하고, 배치 프로그램은 **업무 로직 수행**에 집중하는 구조가 적절하다.

---

# 8. 배치 AP 역할

원본 중앙에는 `배치 AP`가 별도 런타임 컴포넌트로 표시되어 있다.

```text
Application Runtime
      ≠
Batch Runtime
```

즉 일반 온라인 WAS와 배치 실행 영역을 구분하려는 구조로 해석할 수 있다.

### 배치 AP의 역할

```text
배치 AP
 ├─ Batch Program Runtime
 ├─ Framework 실행
 ├─ JDBC / DB 접근
 ├─ 파일 처리 연계
 ├─ ETL 연계
 └─ Job 상태 반환
```

### 구조적 의미

- 온라인 요청 Thread와 배치 작업을 분리
- 배치성 CPU/I/O 부하 격리
- 장시간 Job이 온라인 WAS에 영향을 주지 않도록 함
- 운영 스케줄러와 업무 프로그램의 경계를 분리

---

# 9. NH Cloud Framework 배치 프레임워크 분석

## 9.1 원본 FACT

- `NH Cloud Framework의 배치 프레임워크`
- `배치 프레임워크(NH Cloud Framework) 기반`

따라서 배치 프로그램은 일반 독립 Java 프로그램이 아니라 **프레임워크 기반 표준 프로그램**으로 작성되는 구조다.

---

## 9.2 배치 프레임워크가 제공해야 할 표준 책임

**ANALYSIS / 권고**:

```text
Batch Framework
 ├─ Job Context
 ├─ Step Context
 ├─ Parameter
 ├─ Transaction
 ├─ Commit Interval
 ├─ Error Handling
 ├─ Restart
 ├─ Logging
 ├─ Execution History
 └─ Resource Management
```

즉 업무 개발자는 모든 공통 처리를 직접 구현하지 않고 다음에 집중해야 한다.

```text
Reader
  → Processor
  → Writer
```

또는

```text
Input
  → Business Logic
  → Output
```

---

# 10. 온라인 처리와 배치 처리 비교

| 항목 | 온라인 | 배치 |
|---|---|---|
| 시작 주체 | 사용자/채널 | Control-M |
| 실행 시점 | 요청 즉시 | 예약/조건 기반 |
| 처리 단위 | 거래 | Job / Step / 대량 데이터 |
| 응답 | 즉시 응답 | Job 결과 |
| 런타임 | WAS | 배치 AP |
| Timeout | 초 단위 | 분/시간 단위 가능 |
| 실패 처리 | 오류 응답 | Restart/Rerun |
| 핵심 지표 | TPS/P95 | 처리건수/완료시간 |

핵심 원칙:

```text
Online ≠ Batch
```

배치 작업을 온라인 WAS에서 직접 실행하는 구조는 피하는 것이 적절하다.

---

# 11. 배치 Job 표준 계층

```text
Control-M Job
    │
    ▼
Application Batch Job
    │
    ├─ Step 1
    ├─ Step 2
    └─ Step N
```

예시:

```text
JOB: CUSTOMER_DAILY_SUMMARY
 ├─ STEP01_EXTRACT
 ├─ STEP02_TRANSFORM
 ├─ STEP03_LOAD
 └─ STEP04_RECONCILE
```

### 관리 단위

- Job
- Step
- Execution
- Business Date
- Run ID

---

# 12. 배치 실행 파라미터 표준

권고 파라미터:

```text
jobName
businessDate
runId
executionMode
restartFlag
partitionNo
sourceSystem
targetSystem
```

예시:

```text
--jobName=RDW_DAILY_LOAD
--businessDate=20260823
--runId=20260823-001
--restartFlag=N
```

### 원칙

- 시스템 시간을 업무일자로 직접 사용하지 말 것
- 업무일자는 명시적 파라미터 또는 공통 캘린더에서 결정
- 재처리 시 원 업무일자를 유지

---

# 13. 배치 데이터 접근 구조

원본 중앙의 `배치 AP`는 `JDBC / Database` 및 데이터플랫폼과 인접한다.

이를 상세화하면 다음과 같다.

```text
Batch AP
   │
   ├─ JDBC
   │    │
   │    ├─ RDW
   │    └─ ADW
   │
   ├─ ETL
   │    └─ Data Load
   │
   └─ FOS/MFT
        └─ File Input/Output
```

### 데이터 접근 원칙

- 배치용 DB 계정 분리
- 온라인 Pool과 Batch Pool 분리
- 대량 조회 시 Fetch Size 적용
- Batch Commit Size 표준화
- 장기 Transaction 최소화

---

# 14. JDBC 기반 배치 처리

```text
Control-M
   ↓
Batch AP
   ↓
Batch Framework
   ↓
JDBC
   ↓
RDW / ADW
```

### 주요 성능 항목

- Fetch Size
- Batch Size
- Commit Interval
- Connection Timeout
- Query Timeout
- DB Session 수
- Parallel Degree

### 위험

배치 프로그램이 한 건씩 DB를 호출하면 성능이 급격히 떨어질 수 있다.

금지 예:

```text
for 10,000,000 rows:
    SELECT
    UPDATE
    COMMIT
```

권장:

```text
Chunk Read
   → Bulk Process
   → Batch Write
   → Periodic Commit
```

---

# 15. ETL과 업무 배치의 관계

장표에는 `ETL`과 `배치 AP`가 동시에 존재한다.

이는 다음 책임 분리를 의미하는 것으로 해석할 수 있다.

```text
ETL
= 데이터 이동/변환 중심

Batch AP
= 업무 규칙/업무 상태 처리 중심
```

| 구분 | ETL | 배치 AP |
|---|---|---|
| 중심 | 데이터 | 업무 로직 |
| 단위 | Table/File/Partition | Job/업무객체 |
| 변환 | 대량 변환 | 업무 규칙 |
| 실행 | ETL Engine | NH Cloud Batch |
| 스케줄 | Control-M 가능 | Control-M |

### 금지 패턴

- 복잡한 업무규칙을 ETL Script에 과도하게 구현
- 단순 데이터 Copy를 Java Batch로 모두 구현

---

# 16. FOS/MFT와 배치의 관계

파일 도착을 조건으로 배치가 실행될 수 있다.

```text
외부/Legacy
    ↓
   MFT
    ↓
   FOS
    ↓
File Arrival
    ↓
Control-M
    ↓
Batch AP / ETL
    ↓
RDW / ADW
```

### 배치에서 관리해야 할 파일 조건

- Expected File
- File Arrival Time
- File Size
- Checksum
- Record Count
- Duplicate 여부

---

# 17. Control-M 선후행 관계

배치 시스템의 핵심은 Job 간 Dependency다.

```text
JOB_A
  ↓
JOB_B
  ↓
JOB_C
```

병렬 예:

```text
       ┌→ JOB_B ─┐
JOB_A ─┤          ├→ JOB_D
       └→ JOB_C ─┘
```

### 선행 조건 예

- Job Success
- File Arrived
- 특정 시간
- 업무일
- 외부 Resource
- Manual Approval

---

# 18. 배치 캘린더

금융/정보계에서는 단순 월~금 기준만으로 충분하지 않다.

필요 캘린더 예:

```text
영업일
월말
분기말
반기말
연말
휴일
공휴일 전일
공휴일 익일
```

### 원칙

- 업무 캘린더는 중앙 관리
- 프로그램 소스에 휴일로직 하드코딩 금지
- Control-M Calendar 또는 공통 운영 캘린더 활용

---

# 19. 배치 Restart / Rerun 구조

배치는 온라인 거래와 달리 **재기동 가능성**이 핵심이다.

```text
JOB
 ├─ STEP01 SUCCESS
 ├─ STEP02 SUCCESS
 ├─ STEP03 FAILED
 └─ STEP04 NOT STARTED
```

재기동:

```text
Restart
   ↓
STEP03
   ↓
STEP04
```

또는 데이터 특성에 따라 전체 재실행:

```text
Rerun
   ↓
STEP01부터 재시작
```

### 필요 정책

- Restart 가능 Job
- Full Rerun 필요 Job
- 중간 Commit 처리
- 임시데이터 정리
- 중복 처리 방지

---

# 20. 배치 멱등성

재실행 시 같은 결과를 보장해야 한다.

좋은 패턴:

```text
DELETE business_date
  ↓
RELOAD business_date
```

또는

```text
MERGE by business_key
```

나쁜 패턴:

```text
재실행할 때마다 INSERT
```

### 배치 멱등 키 예

```text
BusinessDate + BusinessKey + RunType
```

---

# 21. 배치 Transaction 전략

대량 배치를 하나의 Transaction으로 묶으면 위험하다.

금지:

```text
10,000,000건
   ↓
1 Transaction
   ↓
마지막 COMMIT
```

권장:

```text
Chunk 1 → COMMIT
Chunk 2 → COMMIT
Chunk 3 → COMMIT
...
```

### 고려사항

- Commit Size
- Rollback 범위
- Undo/Redo
- Lock
- 장애 복구

---

# 22. 배치 오류 처리 구조

```text
Control-M
   ↓
Batch Job
   ↓
Step
   │
   ├─ Business Error
   ├─ DB Error
   ├─ File Error
   ├─ Network Error
   └─ Framework Error
   │
   ▼
Execution Result
   │
   ├─ SUCCESS
   ├─ WARNING
   └─ FAILURE
```

### 실패 시

```text
FAILURE
   ↓
Control-M Alert
   ↓
Operator
   ↓
원인 분석
   ↓
Restart / Rerun
```

---

# 23. Job 상태 표준

권고 상태 모델:

```text
WAITING
READY
RUNNING
SUCCESS
WARNING
FAILED
STOPPED
RESTARTING
```

이 상태는 Framework와 Control-M 사이에서 일관되게 매핑되어야 한다.

---

# 24. 배치 로그 / 실행 이력

최소 로그 필드:

```text
Job Name
Step Name
Run ID
Business Date
Start Time
End Time
Elapsed Time
Read Count
Write Count
Skip Count
Error Count
Result Code
Host
Process ID
```

예시:

```text
JOB=RDW_DAILY_LOAD
STEP=LOAD_CUSTOMER
RUN_ID=20260823-001
BIZ_DATE=20260822
READ=12,500,000
WRITE=12,499,998
SKIP=2
RESULT=SUCCESS_WITH_WARNING
```

---

# 25. 배치 종단 추적 구조

```text
Control-M Order ID
       │
       ▼
Batch Run ID
       │
       ├─ Job Log
       ├─ Step Log
       ├─ SQL Execution
       ├─ ETL Job ID
       ├─ File ID
       └─ Target Load ID
```

이를 통해 다음 질문에 답할 수 있어야 한다.

- 어느 Control-M 작업이 실행했는가?
- 어느 업무일자인가?
- 어느 Step에서 실패했는가?
- 몇 건 처리했는가?
- 어떤 파일을 읽었는가?
- 어느 테이블에 적재했는가?

---

# 26. 성능 및 용량 관점

배치는 TPS보다 **처리량과 완료시간**이 중요하다.

핵심 지표:

```text
Rows / Sec
Files / Hour
GB / Hour
Elapsed Time
CPU %
Memory
DB IOPS
DB Session
Network Throughput
```

### 배치 완료시간

```text
Batch Window
=
Available Time Before Next Business
```

예:

```text
업무 종료 23:00
다음 업무 준비 06:00

Batch Window = 7 Hours
```

모든 Job의 Critical Path가 이 안에 끝나야 한다.

---

# 27. Critical Path 분석

```text
JOB_A  30m
   ↓
JOB_B  90m
   ↓
JOB_C  45m
   ↓
JOB_D  60m
```

총 Critical Path:

```text
225분
```

병렬화 가능한 Job은 분리해야 한다.

```text
         ┌─ JOB_B ─┐
JOB_A ───┤          ├─ JOB_D
         └─ JOB_C ─┘
```

---

# 28. Resource Contention

배치와 온라인이 같은 DB를 사용할 경우 자원 경합이 발생할 수 있다.

```text
Online
  ↓
RDW

Batch
  ↓
RDW
```

동시에 대량 처리하면:

- I/O 증가
- Lock
- Buffer Cache 경쟁
- CPU 상승
- Online 응답지연

### 권고

- 배치 Window 조정
- Resource Manager
- Batch 계정/Session 제한
- Parallel Degree 제한
- 온라인 Peak 시간 회피

---

# 29. 배치 병렬처리

대량 데이터는 Partition 기반 병렬처리가 가능하다.

```text
Master Job
  ├─ Partition 01
  ├─ Partition 02
  ├─ Partition 03
  └─ Partition 04
```

### 주의

병렬도 증가가 항상 성능 개선은 아니다.

```text
Parallel ↑
   ↓
DB Session ↑
I/O ↑
Lock ↑
```

따라서 CPU/DB/Storage 기준으로 최적값을 검증해야 한다.

---

# 30. 보안 아키텍처

## 30.1 실행 권한

- Control-M 운영권한
- Job 실행권한
- 수동 재실행 권한
- Kill 권한
- 운영/개발 분리

## 30.2 DB 권한

- Batch 전용 계정
- 최소권한
- Table별 권한
- DDL 제한

## 30.3 파일 권한

- FOS/MFT 전용 계정
- 민감파일 암호화
- 임시파일 자동삭제

## 30.4 Secret

- DB Password 소스 하드코딩 금지
- Vault/Secret 관리체계 권고

---

# 31. 운영 관측성

배치 운영 대시보드 권고:

| 지표 | 의미 |
|---|---|
| Running Jobs | 현재 실행중 |
| Failed Jobs | 실패 |
| Delayed Jobs | 지연 |
| SLA Miss | 완료시간 위반 |
| Avg Elapsed | 평균 수행시간 |
| Processed Rows | 처리건수 |
| Restart Count | 재기동 |
| DB Session | DB 사용량 |
| CPU/MEM | 배치 AP 자원 |

---

# 32. SLA / SLO

배치의 SLO 예:

```text
JOB_A 완료 < 01:00
JOB_B 완료 < 03:30
전체 EOD 완료 < 05:30
```

온라인 SLO와 다르게 **마감시각** 중심으로 관리한다.

### 필요 지표

- Scheduled Start
- Actual Start
- Expected End
- Actual End
- Delay
- SLA Miss

---

# 33. 배치 작업 변경관리

배치 Job 변경은 Application 변경 + Scheduler 변경이 동시에 발생할 수 있다.

```text
Source Change
   ↓
Build / Deploy
   ↓
Batch Program Version
   ↓
Control-M Job Definition
   ↓
Schedule / Parameter
   ↓
Production
```

따라서 변경관리 대상:

- 프로그램 버전
- Job 정의
- 스케줄
- 파라미터
- 캘린더
- 선후행 관계
- 운영 Runbook

---

# 34. CI/CD와 배치

원본 하단에는 GitLab / Git Runner / NEXUS가 배치되어 있다.

배치 프로그램 배포 흐름은 다음처럼 구성 가능하다.

```text
Developer
   ↓
GitLab
   ↓
Git Runner
   ↓
Build/Test
   ↓
NEXUS
   ↓
Deploy Batch AP
   ↓
Control-M Job Version Match
```

### 중요한 원칙

Control-M Job은 최신인데 Batch AP 프로그램은 이전 버전이면 장애가 발생할 수 있다.

따라서 다음 매핑이 필요하다.

```text
Job Definition Version
 ↔
Application Artifact Version
```

---

# 35. 배치와 데이터거버넌스

배치가 데이터를 대량 생성/변환하므로 데이터거버넌스와 연결되어야 한다.

관리 대상:

- Source / Target
- 데이터 Lineage
- Business Date
- Transformation Rule
- Data Quality
- Reconciliation

```text
Source
  ↓
Batch
  ↓
Target
  ↓
Lineage / Quality Evidence
```

---

# 36. 배치와 ETL / CDC 관계

전체 데이터 런타임에서 역할 구분:

```text
CDC
= 실시간 복제

ETL
= 데이터 중심 배치 변환/적재

Batch AP
= 업무 로직 중심 일괄처리
```

### 예

```text
CDC
코어 거래 → RDW

ETL
Legacy Table → ADW Mart

Batch AP
캠페인 대상 고객 선정 → 결과 생성
```

---

# 37. 금지 패턴

## 37.1 온라인 WAS에서 장기 Batch 실행

```text
HTTP Request
   ↓
1시간 Batch
```

금지.

## 37.2 프로그램 내부 자체 Scheduler

```java
@Scheduled(cron="...")
```

프로젝트 표준이 Control-M 중앙통제라면 운영 배치는 무분별하게 자체 스케줄링하지 않아야 한다.

## 37.3 재실행 시 중복 적재

멱등성 미보장 배치 금지.

## 37.4 하나의 대형 Transaction

장시간 Lock / Rollback 위험.

## 37.5 업무일자를 System Date로 하드코딩

재처리/소급처리 불가능.

---

# 38. 주요 위험과 개선 권고

| 위험 | 영향 | 개선 권고 |
|---|---|---|
| Batch Program 자체 스케줄링 | 중앙 통제 불가 | Control-M 일원화 |
| Online/Batch 자원 혼재 | 온라인 성능저하 | Batch AP 격리 |
| 재기동 미지원 | 장애복구 지연 | Restart 설계 |
| 멱등성 미보장 | 중복 데이터 | Business Key 기반 처리 |
| 장기 Transaction | Lock/Undo 급증 | Chunk Commit |
| 업무일자 하드코딩 | 재처리 불가 | Parameter 표준 |
| Control-M/Program 버전 불일치 | 실행장애 | 버전 매핑 |
| Job Dependency 미정 | 순서 오류 | DAG 관리 |
| Batch Window 미관리 | 영업 시작 지연 | SLA/Critical Path |
| DB 병렬도 과다 | DB 장애 | Resource Limit |

---

# 39. 테스트 기준

## 39.1 기능

- 정상 Job 실행
- 업무일자 파라미터
- Step 처리
- DB 처리
- 파일 처리

## 39.2 재기동

- 중간 Step 실패
- Restart
- Full Rerun
- 중복 방지

## 39.3 Control-M

- 시간 스케줄
- 선행 Job
- 파일 도착
- 휴일 캘린더
- 실패 Alert

## 39.4 성능

- 대량 데이터
- 병렬 처리
- Batch Window
- DB 부하
- Online 동시 부하

## 39.5 장애

- DB 장애
- 파일 미도착
- 네트워크 장애
- 배치 AP 중단
- Control-M 통신 장애

---

# 40. 검증 체크리스트

- [ ] 운영 배치가 Control-M을 통해 중앙 통제되는가?
- [ ] 모든 배치 프로그램이 NH Cloud Framework 배치 표준을 따르는가?
- [ ] Batch AP가 온라인 실행환경과 격리되어 있는가?
- [ ] Job / Step / Run ID가 정의되었는가?
- [ ] 업무일자 파라미터가 표준화되어 있는가?
- [ ] Restart / Rerun 기준이 정의되었는가?
- [ ] 멱등성이 보장되는가?
- [ ] Commit Interval이 대량 처리에 적절한가?
- [ ] JDBC/DB Session 사용량이 제한되는가?
- [ ] Control-M 선후행 관계가 관리되는가?
- [ ] 업무/휴일 캘린더가 중앙 관리되는가?
- [ ] Critical Path와 Batch Window가 검증되었는가?
- [ ] FOS/MFT 파일도착과 배치 기동 연계가 정의되었는가?
- [ ] ETL과 업무 Batch 책임이 분리되어 있는가?
- [ ] Control-M Job과 배포 Artifact 버전이 추적되는가?
- [ ] 실패 Job Alert/운영 Runbook이 있는가?

---

# 41. FACT / ANALYSIS / 확인 필요 구분

| 구분 | 내용 |
|---|---|
| FACT | ⑫ 배치 처리 |
| FACT | NH Cloud Framework 배치 프레임워크 기반 |
| FACT | Control-M 작업 자동화 시스템을 통해 지정 시간 실행 |
| FACT | 배치 / 배치 프레임워크 / 일괄처리 |
| FACT | 중앙 장표에 배치 AP 표시 |
| FACT | 배치 AP 인접 영역에 JDBC / Database / RDW / ADW 표시 |
| FACT | FOS / ETL / MFT 구성 존재 |
| FACT | 하단 통합관리 영역에 배치작업관리 표기 |
| ANALYSIS | Control-M → Batch AP 런타임 기동 흐름 |
| ANALYSIS | Batch AP를 온라인 WAS와 분리된 실행공간으로 해석 |
| ANALYSIS | Restart / Job/Step / Chunk / 멱등성 표준 필요 |
| ANALYSIS | Control-M Calendar / Dependency / SLA 관리 필요 |
| 확인 필요 | NH Cloud Batch Framework의 실제 구현제품/클래스 구조 |
| 확인 필요 | Control-M Agent 설치 위치 |
| 확인 필요 | 배치 AP 서버/VM 실제 배치 구조 |
| 확인 필요 | Control-M Job Naming / Calendar / Queue 설정 |
| 확인 필요 | Batch Framework의 실제 Restart Repository 구조 |
| 확인 필요 | ETL Job과 Java Batch Job의 실제 분류 기준 |

---

# 42. 장표 해석 시 유의사항

이 장표는 배치 업무 처리의 **상위 런타임 패턴**을 설명하며, 다음 상세정보는 직접 제공하지 않는다.

- 실제 Control-M Job 이름
- Cron / Calendar 설정
- Agent 위치
- Batch Framework 클래스 구조
- Job Repository 구조
- Transaction/Commit Size
- 실제 Batch AP 서버 수
- JVM Heap
- Thread 수
- DB Pool
- 배치별 SLA
- 재기동 방식

따라서 본 문서에서는 장표의 명시 내용을 **FACT**로 보존하고, 배치 표준 설계에 필요한 내용은 **ANALYSIS / 권고**로 분리한다.

---

# 43. 최종 평가

이 장표는 차세대 정보계의 배치 처리 아키텍처를 다음처럼 정의한다.

```text
NH Cloud Framework Batch Program
             +
         Batch AP
             +
          Control-M
             =
   Standard Batch Runtime
```

전체 구조는 다음과 같다.

```text
Control-M
   ↓
Scheduled Batch Job
   ↓
Batch AP
   ↓
NH Cloud Framework
   ↓
Business Batch Logic
   ├─ JDBC → RDW / ADW
   ├─ ETL
   ├─ FOS / MFT
   └─ 대내 연계
   ↓
Execution Result
   ↓
Control-M
```

이 구조의 가장 중요한 아키텍처 원칙은 다음이다.

1. **배치 실행 스케줄은 Control-M으로 중앙 통제한다.**
2. **배치 프로그램은 NH Cloud Framework 배치 표준으로 작성한다.**
3. **배치 실행영역은 Batch AP로 분리한다.**
4. **배치와 온라인 처리의 자원경계를 분리한다.**
5. **재기동, 멱등성, 업무일자, 선후행을 표준화해야 한다.**
6. **ETL / FOS / JDBC와의 책임경계를 명확히 해야 한다.**
7. **Batch Window와 Critical Path를 운영 SLO로 관리해야 한다.**

후속 상세설계에서는 다음 구조가 구체화되어야 한다.

```text
Control-M Job Definition
   ↓
Schedule / Calendar / Dependency
   ↓
Batch AP
   ↓
NH Cloud Batch Framework
   ↓
Job / Step / Transaction
   ↓
DB / ETL / File / Interface
   ↓
Restart / Reconcile / Evidence
```

결론적으로 이 장표는 **차세대 정보계 배치 업무를 NH Cloud Framework + Batch AP + Control-M으로 표준화한 핵심 Batch Runtime Architecture**다.
