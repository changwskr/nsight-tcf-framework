# NSIGHT 업무 처리 유형 및 채널 업무 처리 유형 분석
## 채널 · 연계 · 마케팅 이벤트 · 데이터 · 파일 · 배치 처리 유형 통합 정리

## 0. 문서 개요

본 문서는 제공된 **「업무 처리 유형」 및 「채널 업무 처리 유형」 장표(페이지 113~114)**를 기준으로,
차세대 정보계의 업무 처리를 **12개 유형**으로 분류하고, 이 가운데 **채널 업무 처리 유형 3종**의 처리방식을 상세 분석한 문서이다.

대상 장표:

- 페이지 113: 업무 처리 유형
- 페이지 114: 채널 업무 처리 유형

### 작성 원칙

- 원본 장표에서 직접 확인되는 명칭과 설명을 우선 기록한다.
- 장표에 세부 설명이 없는 유형은 명칭과 아키텍처 배치 위치만 정리한다.
- 기존 인터페이스 구성도와 연결되는 부분은 **[ANALYSIS]**로 구분한다.
- Source에 없는 상세 URL, Port, Timeout, Retry, 인증방식 등은 임의로 보완하지 않는다.

---

# 1. 업무 처리 유형 전체 분류

원본 장표는 차세대 정보계의 업무 처리를 총 **12개 유형**으로 분류한다.

```text
업무 처리 유형
│
├─ 1. 채널 업무 처리 유형
│  ├─ ① 정보계 단말 거래
│  ├─ ② 통합업무시스템 거래
│  └─ ③ 미니 싱글뷰
│
├─ 2. 연계 업무 처리 유형
│  ├─ ④ 대내 시스템간 연계
│  └─ ⑤ 대외 시스템간 연계
│
├─ 3. 마케팅 이벤트 업무 처리 유형
│  ├─ ⑥ 실시간 고객 반응형 정보 수집
│  └─ ⑦ 실시간 고객 오퍼링 제공
│
├─ 4. 데이터 분석/제공 업무 처리 유형
│  ├─ ⑧ CDC 실시간 데이터 동기화
│  ├─ ⑨ ETL 연계
│  └─ ⑩ 데이터 분석 기반 의사결정 지원
│
├─ 5. 파일 연계 업무 처리 유형
│  └─ ⑪ FOS 파일 처리 연계
│
└─ 6. 배치 업무 처리 유형
   └─ ⑫ 배치 처리
```

---

# 2. 전체 업무 처리 유형 표

| 대분류 | No | 업무 처리 유형 | 장표상 주요 연계 영역 |
|---|---:|---|---|
| 채널 | 1 | 정보계 단말 거래 | 정보계 단말 ↔ Application |
| 채널 | 2 | 통합업무시스템 거래 | 통합업무시스템 ↔ 영업점 MCA ↔ Application |
| 채널 | 3 | 미니 싱글뷰 | 통합업무 C/S 화면 / Web View ↔ Application |
| 연계 | 4 | 대내 시스템간 연계 | GSE, API Gateway, EAI, 대내 시스템 |
| 연계 | 5 | 대외 시스템간 연계 | API Gateway, 대외 MCA, 대외 기관 |
| 마케팅 이벤트 | 6 | 실시간 고객 반응형 정보 수집 | Wise Collector / Event / Kafka |
| 마케팅 이벤트 | 7 | 실시간 고객 오퍼링 제공 | 실시간처리서버 EBM |
| 데이터 분석/제공 | 8 | CDC 실시간 데이터 동기화 | Core DB / CDC / CDC 중계 / RDW |
| 데이터 분석/제공 | 9 | ETL 연계 | ETL / RDW / ADW / Legacy |
| 데이터 분석/제공 | 10 | 데이터 분석 기반 의사결정 지원 | BI포탈 / RDW / ADW |
| 파일 | 11 | FOS 파일 처리 연계 | FOS / MFT |
| 배치 | 12 | 배치 처리 | 배치 AP / DB / ETL |

---

# 3. 전체 텍스트 아키텍처 그림

```text
                             NSIGHT 업무 처리 유형

┌────────────────────────────────────────────────────────────────────────────┐
│                             CHANNEL                                        │
│                                                                            │
│ ① 정보계 단말 거래                                                        │
│ ② 통합업무시스템 거래                                                     │
│ ③ 미니 싱글뷰                                                             │
│                                                                            │
│ 정보계 단말 ───────────────────────────────┐                               │
│ 통합업무시스템 ─▶ 영업점 MCA ──────────────┼─▶ Application                 │
│ C/S 화면 ─▶ Web View ──────────────────────┘                               │
└──────────────────────────────┬─────────────────────────────────────────────┘
                               │
                               ▼
┌────────────────────────────────────────────────────────────────────────────┐
│                           APPLICATION                                      │
│                                                                            │
│ 마케팅플랫폼 / 미니싱글뷰 / BI포탈 / Self-BI / OLAP / 데이터거버넌스       │
└───────────┬───────────────────────┬────────────────────────┬───────────────┘
            │                       │                        │
            │                       │                        │
            ▼                       ▼                        ▼
┌───────────────────┐   ┌─────────────────────┐    ┌────────────────────────┐
│ ④/⑤ 시스템 연계  │   │ ⑥/⑦ 이벤트 처리    │    │ ⑧/⑨/⑩ 데이터 처리    │
│                   │   │                     │    │                        │
│ API Gateway       │   │ Wise Collector      │    │ CDC                    │
│ EAI / GSE         │   │ Kafka               │    │ ETL                    │
│ 대내/대외 MCA     │   │ Realtime EBM        │    │ RDW / ADW / BI         │
└─────────┬─────────┘   └─────────┬───────────┘    └───────────┬────────────┘
          │                       │                             │
          ▼                       ▼                             ▼
    대내/대외 시스템        고객행동/오퍼링                데이터플랫폼
                                                             │
                                ┌────────────────────────────┴────────────┐
                                ▼                                         ▼
                       ⑪ FOS 파일 연계                           ⑫ 배치 처리
                          FOS / MFT                               Batch AP
```

---

# 4. 채널 업무 처리 유형의 정의

페이지 114의 설명은 다음과 같이 요약된다.

> **정보단말 기반의 차세대 정보계 업무 처리와 통합업무시스템 미니 싱글뷰, 싱글뷰 업무를 HTTP 웹 기반의 요청 거래로 처리하는 업무 유형**

즉 채널 업무는 공통적으로 다음 특성을 가진다.

```text
사용자 화면
    │
    ▼
Client
    │
JSON / HTTP
    │
    ▼
Application
    │
    ▼
Request / Response
```

다만 화면 유형과 인터페이스 경유 방식에 따라 ①~③으로 나뉜다.

---

# 5. ① 정보계 단말 거래

## 5.1 원본 설명

- 웹 기반 전용 브라우저 업무 화면에서 Application 시스템으로 거래를 요청하고 응답받는 업무 처리
- JSON/HTTP 기반 통신으로 Client와 Server 간 거래 처리
- 거래별 유일한 식별자(GUID)를 로그에 기록
- 동기(Sync), JSON/HTTP, 요청/응답

## 5.2 텍스트 구조

```text
사용자
  │
  ▼
정보계 전용 브라우저
  │
  │ JSON / HTTP
  │ GUID
  ▼
Application
  │
  ▼
Service
  │
  ▼
Response
```

## 5.3 처리 특성

| 항목 | 정의 |
|---|---|
| UI | 웹 기반 전용 브라우저 |
| 호출 방식 | JSON/HTTP |
| 통신 | Client ↔ Server |
| 동기/비동기 | Sync |
| 메시지 패턴 | 요청/응답 |
| 거래추적 | GUID |
| 인터페이스 시스템 | 장표상 별도 MCA/API G/W 경유 표시 없음 |

### [ANALYSIS]

정보계 단말 거래는 **전용 브라우저 기반의 직접 Application 호출 유형**으로 볼 수 있다.

기존 인터페이스 표준의:

```text
정보계 단말
→ 인터페이스 시스템 없음
→ 정보계
```

유형과 정합성이 있다.

---

# 6. ② 통합업무시스템 거래

## 6.1 원본 설명

- C/S 기반 업무 화면에서 Application 시스템으로 거래를 요청하고 응답받는 업무 처리
- 영업점 MCA 인터페이스 시스템을 경유해서 거래 요청
- 동기(Sync), JSON/HTTP, 요청/응답

## 6.2 텍스트 구조

```text
사용자
  │
  ▼
통합업무시스템
(C/S 업무 화면)
  │
  ▼
영업점 MCA
  │
  │ JSON / HTTP
  ▼
Application
  │
  ▼
Response
```

## 6.3 처리 특성

| 항목 | 정의 |
|---|---|
| UI | C/S 기반 업무 화면 |
| 인터페이스 시스템 | 영업점 MCA |
| 호출 방식 | JSON/HTTP |
| 동기/비동기 | Sync |
| 메시지 패턴 | 요청/응답 |
| 대상 | 정보계 Application |

### [ANALYSIS]

정보계 단말 거래와 가장 큰 차이는 **영업점 MCA 경유 여부**다.

```text
정보계 단말 거래
정보계 단말 → Application

통합업무시스템 거래
통합업무시스템 → 영업점 MCA → Application
```

---

# 7. ③ 미니 싱글뷰

## 7.1 원본 설명

- C/S 기반 업무 화면에서 Web View(웹 클라이언트)를 통해 정보계 Application 시스템으로 거래를 요청하고 응답받는 업무 처리
- JSON/HTTP 기반 통신으로 Client와 Server 간 거래 처리
- 동기(Sync), JSON/HTTP, 요청/응답

## 7.2 텍스트 구조

```text
통합업무 C/S 화면
      │
      ▼
   Web View
(웹 클라이언트)
      │
      │ JSON / HTTP
      ▼
미니 싱글뷰 Application
      │
      ▼
   Service
      │
      ▼
   Response
```

## 7.3 처리 특성

| 항목 | 정의 |
|---|---|
| Host UI | C/S 기반 업무 화면 |
| 화면 기술 | Web View / Web Client |
| 호출 방식 | JSON/HTTP |
| 동기/비동기 | Sync |
| 메시지 패턴 | 요청/응답 |
| 대상 | 정보계 Application |

### [ANALYSIS]

미니 싱글뷰는 **C/S Host 화면 안에 Web View를 삽입해 Web Application을 호출하는 Hybrid UI 유형**으로 볼 수 있다.

---

# 8. 채널 3유형 비교

| 구분 | 정보계 단말 | 통합업무시스템 | 미니 싱글뷰 |
|---|---|---|---|
| No | 1 | 2 | 3 |
| UI | 전용 웹 브라우저 | C/S 업무화면 | C/S + Web View |
| 인터페이스 경유 | 장표상 직접 | 영업점 MCA | 장표상 Web View 직접 |
| Protocol | JSON/HTTP | JSON/HTTP | JSON/HTTP |
| 통신 | Sync | Sync | Sync |
| 패턴 | 요청/응답 | 요청/응답 | 요청/응답 |
| GUID | 명시됨 | 장표 상세 설명에는 별도 미기재 | 장표 상세 설명에는 별도 미기재 |
| 핵심 특성 | 웹 전용 단말 | MCA 기반 C/S | C/S 내 Web UI |

---

# 9. 채널 업무 처리 통합 그림

```text
                       CHANNEL TRANSACTION TYPES

               ┌───────────────────────────────┐
               │       ① 정보계 단말 거래      │
               │                               │
사용자 ───────▶│ 전용 Browser                  │
               │      │ JSON/HTTP + GUID       │
               │      ▼                        │
               │ Application                   │
               └───────────────────────────────┘


               ┌───────────────────────────────┐
               │    ② 통합업무시스템 거래      │
               │                               │
사용자 ───────▶│ C/S 화면                      │
               │      │                        │
               │      ▼                        │
               │ 영업점 MCA                    │
               │      │ JSON/HTTP              │
               │      ▼                        │
               │ Application                   │
               └───────────────────────────────┘


               ┌───────────────────────────────┐
               │       ③ 미니 싱글뷰           │
               │                               │
사용자 ───────▶│ C/S 화면                      │
               │      │                        │
               │      ▼                        │
               │ Web View                      │
               │      │ JSON/HTTP              │
               │      ▼                        │
               │ Mini SingleView App            │
               └───────────────────────────────┘
```

---

# 10. ④ 대내 시스템간 연계

페이지 113에서는 `대내 시스템간 연계`를 별도의 업무 처리유형으로 분류한다.

장표에서 번호 ④는 다음 위치들에 배치되어 있다.

- GSE 인근
- API Gateway(Cruz APIM) 인근

### [ANALYSIS]

앞선 인터페이스 표준을 함께 보면 대내 연계는 다음 메커니즘과 연결된다.

```text
정보계
   │
   ▼
API Gateway
(Cruz APIM)
   │
   ▼
EAI / 대내 시스템
```

또한 타 법인/농협은행 연계에는 GSE가 위치한다.

> 본 장표 페이지 113에는 ④의 세부 요청/응답 규칙은 별도로 적혀 있지 않으므로,
> 상세 프로토콜은 앞선 인터페이스 표준 설계서를 참조해야 한다.

---

# 11. ⑤ 대외 시스템간 연계

페이지 113에서 `대외 시스템간 연계`는 ⑤로 구분된다.

장표상 번호 ⑤는 API Gateway 우측, 대외 MCA와 대외기관 방향에 배치되어 있다.

```text
Application / 정보계
        │
        ▼
 API Gateway
        │
        ▼
   대외 MCA
        │
        ▼
   대외 기관
```

대외기관으로 장표에 보이는 예:

- NH생명
- NH손해
- NH멤버스
- 농협신용보증
- NH경제지주
- KT
- Nice
- Ko

---

# 12. ⑥ 실시간 고객 반응형 정보 수집

페이지 113에서는 마케팅 이벤트 업무 처리 유형의 첫 번째로 다음을 정의한다.

> **실시간 고객 반응형 정보 수집**

장표에서 번호 ⑥은 고객 이벤트/Wise Collector 부근에 배치되어 있다.

```text
고객 행태 / Event
      │
      ▼
Wise Collector
      │
      ▼
Kafka / 고객행동 데이터
```

### [ANALYSIS]

이는 고객의 실시간 반응/행동 이벤트를 수집하여
마케팅 이벤트 처리 기반으로 전달하는 Inbound Event Processing 유형이다.

---

# 13. ⑦ 실시간 고객 오퍼링 제공

장표에서 번호 ⑦은 하단 `실시간처리서버 EBM` 부근에 배치되어 있다.

```text
고객행동 Event
      │
      ▼
Kafka / Event
      │
      ▼
행동정보처리
      │
      ▼
실시간처리 EBM
      │
      ▼
고객 오퍼링
```

### [ANALYSIS]

⑥이 고객 반응을 **수집**하는 Inbound 흐름이라면,
⑦은 처리된 결과를 바탕으로 실시간 Offer를 **제공**하는 Outbound/Decision Flow로 해석할 수 있다.

---

# 14. ⑥/⑦ 마케팅 이벤트 처리 비교

| 구분 | ⑥ 고객 반응형 정보 수집 | ⑦ 고객 오퍼링 제공 |
|---|---|---|
| 목적 | 고객 이벤트 수집 | 실시간 Offer 제공 |
| 방향 성격 | Inbound | Outbound/Decision |
| 핵심 컴포넌트 | Wise Collector / Kafka | 행동정보처리 / EBM |
| 처리 유형 | Event Collection | Realtime Offering |
| 연계 특성 | Event Streaming | Event/Realtime Processing |

---

# 15. ⑧ CDC 실시간 데이터 동기화

페이지 113에서 번호 ⑧은 `CDC` 영역에 배치된다.

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

원본 분류:

> **CDC 실시간 데이터 동기화**

### 의미

- 원천 DB 변경정보를 실시간 수집
- CDC 중계를 통한 Source DB 부하 분산
- RDW 실시간 데이터 동기화

---

# 16. ⑨ ETL 연계

번호 ⑨는 `ETL` 블록에 배치되어 있다.

```text
Source DB / RDW
      │
      ▼
     ETL
      │
      ▼
ADW / Legacy / Target DB
```

원본 분류:

> **ETL 연계**

### 의미

- Batch 데이터 이동
- 대량 데이터 적재/변환
- RDW → ADW 동기화
- 타 시스템 DB 연계

---

# 17. ⑩ 데이터 분석 기반 의사결정 지원

번호 ⑩은 장표에서 다음 위치에 표시된다.

- BI포탈 영역
- 데이터플랫폼 ADW 인근

```text
ADW
 │
 ▼
BI / OLAP / Self-BI
 │
 ▼
분석
 │
 ▼
의사결정 지원
```

원본 분류:

> **데이터 분석 기반 의사결정 지원**

### [ANALYSIS]

⑩은 단순 데이터 이동 인터페이스가 아니라,
ADW 분석데이터를 기반으로 BI/OLAP/Self-BI가 사용자에게 분석 결과를 제공하는
**Analytics Consumption 업무 유형**으로 볼 수 있다.

---

# 18. 데이터 분석/제공 3유형 비교

| No | 유형 | 처리성격 | 대표 컴포넌트 |
|---:|---|---|---|
| 8 | CDC 실시간 데이터 동기화 | Realtime Data Replication | CDC / CDC 중계 / RDW |
| 9 | ETL 연계 | Batch/Data Integration | ETL / RDW / ADW |
| 10 | 데이터 분석 기반 의사결정 지원 | Analytics Consumption | ADW / BI / OLAP / Self-BI |

---

# 19. ⑪ FOS 파일 처리 연계

번호 ⑪은 FOS 블록에 배치된다.

```text
Source
  │
  ▼
 FOS
  │
  ├─ 필요 시 MFT
  ▼
Target
```

원본 분류:

> **FOS 파일 처리 연계**

### 의미

- 파일 기반 시스템 연계
- 정보계/대내/대외 파일전송
- 필요 시 MFT 연계

---

# 20. ⑫ 배치 처리

번호 ⑫는 `배치 AP` 부근에 배치되어 있다.

```text
Scheduler / Batch Request
        │
        ▼
     배치 AP
        │
        ▼
   DB / ETL / File
```

원본 분류:

> **배치 처리**

### [ANALYSIS]

배치 처리는 온라인 거래와 분리된 독립 Application/Resource를 사용하는
비동기성 대량 처리 유형으로 볼 수 있다.

---

# 21. 12개 유형과 인터페이스 기술 매핑

| No | 업무 유형 | 주요 기술/컴포넌트 |
|---:|---|---|
| 1 | 정보계 단말 거래 | Browser, JSON/HTTP, GUID |
| 2 | 통합업무시스템 거래 | C/S, 영업점 MCA, JSON/HTTP |
| 3 | 미니 싱글뷰 | C/S + Web View, JSON/HTTP |
| 4 | 대내 시스템간 연계 | API Gateway, EAI, GSE |
| 5 | 대외 시스템간 연계 | API Gateway, 대외 MCA |
| 6 | 실시간 고객 반응형 정보 수집 | Wise Collector, Kafka |
| 7 | 실시간 고객 오퍼링 제공 | 행동정보처리, EBM |
| 8 | CDC 실시간 데이터 동기화 | CDC, CDC 중계, RDW |
| 9 | ETL 연계 | ETL, RDW, ADW |
| 10 | 데이터 분석 기반 의사결정 지원 | ADW, BI Portal, OLAP, Self-BI |
| 11 | FOS 파일 처리 연계 | FOS, MFT |
| 12 | 배치 처리 | Batch AP |

---

# 22. 업무 처리 유형과 동기/비동기 성격

장표에서 명시적으로 Sync가 확인되는 것은 채널 3개 유형이다.

| No | 유형 | 동기/비동기 | 원본 근거 |
|---:|---|---|---|
| 1 | 정보계 단말 거래 | Sync | 장표 직접 명시 |
| 2 | 통합업무시스템 거래 | Sync | 장표 직접 명시 |
| 3 | 미니 싱글뷰 | Sync | 장표 직접 명시 |
| 4 | 대내 시스템간 연계 | 별도 확인 | 페이지 113만으로 확정 불가 |
| 5 | 대외 시스템간 연계 | 별도 확인 | 페이지 113만으로 확정 불가 |
| 6 | 실시간 고객 반응형 정보 수집 | Event 성격 | 구조상 Kafka/Event |
| 7 | 실시간 고객 오퍼링 제공 | Realtime Event 성격 | EBM 위치 |
| 8 | CDC | Streaming/Replication | CDC |
| 9 | ETL | Batch | ETL |
| 10 | 분석지원 | 사용자/분석 처리 | 세부 호출방식 미표기 |
| 11 | 파일 | File Transfer | FOS |
| 12 | 배치 | Batch | Batch AP |

---

# 23. Architecture Rule 후보

| Rule ID | 규칙 | 상태 |
|---|---|---|
| `TX-001` | 정보계 단말 거래는 Web 기반 전용 브라우저를 사용한다 | 장표 근거 |
| `TX-002` | 정보계 단말 거래는 JSON/HTTP 요청/응답 방식으로 처리한다 | 장표 근거 |
| `TX-003` | 정보계 단말 거래의 거래 추적에는 GUID를 사용한다 | 장표 근거 |
| `TX-004` | 통합업무시스템 거래는 영업점 MCA를 경유한다 | 장표 근거 |
| `TX-005` | 통합업무시스템 거래는 Sync JSON/HTTP 요청/응답 방식이다 | 장표 근거 |
| `TX-006` | 미니 싱글뷰는 C/S 화면 내 Web View를 통해 처리한다 | 장표 근거 |
| `TX-007` | 미니 싱글뷰는 Sync JSON/HTTP 요청/응답 방식이다 | 장표 근거 |
| `TX-008` | 대내 시스템 연계는 별도 연계 처리유형으로 관리한다 | 장표 근거 |
| `TX-009` | 대외 시스템 연계는 별도 연계 처리유형으로 관리한다 | 장표 근거 |
| `TX-010` | 실시간 고객 반응 정보는 Event 기반으로 수집한다 | 장표 근거 |
| `TX-011` | 실시간 고객 오퍼링은 실시간 처리서버를 통해 제공한다 | 장표 근거 |
| `TX-012` | 실시간 데이터 동기화는 CDC 유형으로 처리한다 | 장표 근거 |
| `TX-013` | 배치 데이터 연계는 ETL 유형으로 처리한다 | 장표 근거 |
| `TX-014` | 데이터 분석 기반 의사결정 지원을 별도 업무 유형으로 관리한다 | 장표 근거 |
| `TX-015` | 파일 연계는 FOS 처리 유형으로 관리한다 | 장표 근거 |
| `TX-016` | 배치 처리는 독립 배치 업무 유형으로 관리한다 | 장표 근거 |

---

# 24. 기존 인터페이스 아키텍처와의 연결

| 업무 처리 유형 | 기존 인터페이스 아키텍처 |
|---|---|
| 정보계 단말 거래 | 정보계 단말 직접 온라인 연계 |
| 통합업무시스템 거래 | 영업점 MCA |
| 미니 싱글뷰 | Web View → Application |
| 대내 시스템 연계 | API Gateway / EAI / GSE |
| 대외 시스템 연계 | API Gateway / 대외 MCA |
| 실시간 고객 반응 수집 | Wise Collector / Kafka |
| 실시간 고객 오퍼링 | 행동정보처리 / EBM |
| CDC | OGG/CDC 중계 → RDW |
| ETL | DataStage / RDW→ADW |
| 분석 의사결정 | ADW / BI Portal |
| FOS | FOS / MFT |
| 배치 | Batch AP |

---

# 25. 채널 업무 End-to-End 흐름

```text
[Type 1]
사용자
  ↓
전용 Browser
  ↓ JSON/HTTP + GUID
Application
  ↓
Response


[Type 2]
사용자
  ↓
C/S 업무화면
  ↓
영업점 MCA
  ↓ JSON/HTTP
Application
  ↓
Response


[Type 3]
사용자
  ↓
C/S 업무화면
  ↓
Web View
  ↓ JSON/HTTP
Mini SingleView
  ↓
Response
```

---

# 26. 전체 End-to-End 처리유형 Map

```text
사용자 / Channel
      │
      ├─① 정보계 단말
      ├─② 통합업무 + MCA
      └─③ C/S + WebView
      │
      ▼
Application
      │
      ├─④ 대내 연계 ──▶ API G/W / EAI / GSE
      ├─⑤ 대외 연계 ──▶ API G/W / 대외 MCA
      │
      ├─⑥ 이벤트 수집 ─▶ Wise Collector / Kafka
      ├─⑦ 오퍼링 ─────▶ EBM
      │
      ├─⑧ CDC ─────────▶ RDW
      ├─⑨ ETL ─────────▶ RDW / ADW / Legacy
      ├─⑩ 분석 ────────▶ ADW / BI
      ├─⑪ 파일 ────────▶ FOS / MFT
      └─⑫ 배치 ────────▶ Batch AP
```

---

# 27. 확인 필요 GAP

| GAP ID | 확인항목 |
|---|---|
| `GAP-TX-001` | 정보계 단말 전용 Browser 제품/버전 |
| `GAP-TX-002` | GUID 생성 위치와 Header/Body 위치 |
| `GAP-TX-003` | JSON 전문 Schema |
| `GAP-TX-004` | 채널 거래 Timeout |
| `GAP-TX-005` | 채널 거래 Retry 정책 |
| `GAP-TX-006` | MCA 장애 시 대체경로 |
| `GAP-TX-007` | Web View와 Host C/S 간 Session/SSO 연계 |
| `GAP-TX-008` | 미니 싱글뷰 URL/Endpoint |
| `GAP-TX-009` | 대내 연계 상세 Protocol |
| `GAP-TX-010` | 대외 연계 인증/암호화 |
| `GAP-TX-011` | Kafka Topic/Event Schema |
| `GAP-TX-012` | 실시간 Offer SLA |
| `GAP-TX-013` | CDC 동기화 지연 기준 |
| `GAP-TX-014` | ETL 배치 주기 |
| `GAP-TX-015` | 분석 의사결정 데이터 적시성 |
| `GAP-TX-016` | FOS 파일명/재처리 |
| `GAP-TX-017` | 배치 Scheduler/Job Control |
| `GAP-TX-018` | 각 유형별 모니터링/알람 |
| `GAP-TX-019` | 각 유형별 장애/보상처리 |
| `GAP-TX-020` | 12개 업무 유형별 Interface ID 매핑 |

---

# 28. 운영/설계 관리 권장 속성

각 업무 처리유형을 실제 표준으로 관리할 때 다음 속성을 연결할 수 있다.

```text
Processing Type ID
Processing Type Name
Channel / Source
UI Type
Interface System
Protocol
Sync / Async
Request / Response
GUID
Target Application
Timeout
Retry
Security
Monitoring
HA/DR
Owner
```

> 위 속성은 원본 장표에 직접 제시된 표가 아니라 **[ANALYSIS] 확장안**이다.

---

# 29. 최종 분석 결론

제공된 두 장표에서 확인되는 핵심은 다음과 같다.

1. 차세대 정보계 업무는 **총 12개 처리 유형**으로 분류된다.
2. 이 중 채널 업무는 **정보계 단말 거래 / 통합업무시스템 거래 / 미니 싱글뷰**의 3종으로 나뉜다.
3. 정보계 단말 거래는 **웹 기반 전용 브라우저 + JSON/HTTP + GUID + Sync 요청/응답** 방식이다.
4. 통합업무시스템 거래는 **C/S 업무화면 + 영업점 MCA + JSON/HTTP + Sync 요청/응답** 방식이다.
5. 미니 싱글뷰는 **C/S 화면 내 Web View + JSON/HTTP + Sync 요청/응답** 방식이다.
6. 대내/대외 시스템간 연계는 채널거래와 별도의 업무 처리유형으로 관리된다.
7. 마케팅 이벤트 업무는 **실시간 고객 반응형 정보 수집**과 **실시간 고객 오퍼링 제공**으로 분리된다.
8. 데이터 처리업무는 **CDC 실시간 동기화 / ETL 연계 / 데이터 분석 기반 의사결정 지원**으로 나뉜다.
9. 파일 처리업무는 **FOS 파일 처리 연계**, 배치업무는 **배치 처리**로 독립 분류된다.
10. 이 12개 유형은 앞서 정의한 API Gateway, Kafka, CDC, ETL, FOS, Batch AP 등의 인터페이스 메커니즘과 일관되게 연결된다.
11. 현재 장표는 처리유형과 상위 흐름을 정의하는 수준이며, Timeout/Retry/보안/Schema/장애복구 등은 상세 설계에서 추가 정의해야 한다.

본 문서는 제공된 페이지 113~114 장표를 기준으로 한
**NSIGHT Business Processing Type Working Baseline**으로 활용한다.
