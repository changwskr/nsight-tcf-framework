# 레포팅(RD) 연계 아키텍처 분석

> **분석 대상**: 제공 이미지 `레포트(RD) 연계`
> **범위**: UI F/W(xFrame)에서 RD(Report Designer) 연계 시 DB 처리를 수행하는 Controller 구조
> **분석 일자**: 2026-08-22
> **분석 원칙**: 이미지에서 직접 확인되는 구조, 문맥상 해석, 현재 저장소 구현을 구분한다.

---

## 1. 핵심 결론

이미지는 xFrame이 보고서 생성에 필요한 조회 조건을 `Map`으로 `/rd/{serviceId}`에 전달하면, 공통 RD Controller가 서비스 ID를 식별하고 시스템 선처리를 수행한 뒤 동일 Map을 Service에 전달하는 구조다. Service는 Map을 이용해 Database 작업을 수행하고 결과를 Controller에 반환한다. Controller는 결과를 `String`으로 변환하고 시스템 후처리를 수행한 뒤 xFrame에 반환한다.

이 구조에서 RD Controller의 본질은 보고서 파일 자체를 렌더링하는 엔진이 아니라, **Report Designer가 사용할 데이터 또는 실행 결과를 DB에서 조회하는 프레임워크 진입점**이다. 입력·업무 호출 계약은 Map, 최종 반환 계약은 String으로 단순화되어 있다.

현재 저장소에는 `/rd/{serviceId}` 전용 Controller가 없다. 다만 `OnlineTransactionController → TcfFacade → TransactionDispatcher → Handler → Facade/Service → DAO/DB` 경로가 서비스 ID 식별, 시스템 선후처리와 DB 호출이라는 동일한 개념을 제공한다. 현재 PDMG는 Map/String 전용 RD 계약이 아니라 JSON `dto`와 DTO/객체 응답을 사용하는 일반 온라인 거래 경로다.

---

## 2. 이미지 판독 및 표기 보정

| 이미지 표기 | 판독 | 해석 |
|---|---|---|
| 제목 | 레포트(RD) 연계 | Report Designer DB 연계 |
| URL | `http://.../rd/{serviceId}` | RD 전용 서비스 진입 경로 |
| Controller 상단 | `NhFileController(Download)` | 문맥상 `NhRDController` 표기 오류 가능성 |
| 하단 설명 | `NhRDController` | RD Controller가 올바른 명칭으로 판단 |
| Input | `Map` | 보고서 조회조건·파라미터 |
| Service | `Map` | DB 처리용 업무 파라미터 |
| Output | `String` | RD/xFrame이 소비할 문자열 결과 |
| 외부 시스템 | Database | 보고서 데이터 조회 또는 DB 작업 |

> 이미지 상단 박스의 `NhFileController(Download)`는 제목, URL `/rd/`, 하단 설명의 `NhRDController`와 일치하지 않는다. 따라서 복사·편집 과정에서 남은 표기로 보고, 본 문서에서는 **NhRDController**로 정규화한다.

---

## 3. 이미지 상세 텍스트 그림

### 3.1 원본 레이아웃 재구성

```text
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                     레포트(RD) 연계                                                     │
│       UI F/W(xFrame)에서 RD(Report Designer) 연계 시 DB 처리를 수행할 때 사용하는 Controller            │
└──────────────────────────────────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────┐     ┌───────────────────────────────────────────────────────────────────┐
│      UI F/W (xFrame)     │     │                         Framework                                 │
│                          │     │                                                                   │
│   ┌──────────────────┐   │     │  ┌─────────────────────────────────────────────┐                  │
│   │   사용자 / 화면   │   │     │  │       NhRDController (RD 연계)             │                  │
│   │ Report Designer  │   │     │  │                                             │                  │
│   └──────────────────┘   │     │  │ URL: http://{host}/rd/{serviceId}           │                  │
│            │             │     │  │                                             │                  │
│            │ ① Map 전달 │────▶│  │ ┌───────────────────────┐                   │                  │
│            │             │     │  │ │ ② 서비스 ID 인식      │                   │                  │
│            │             │     │  │ └───────────┬───────────┘                   │                  │
│            │             │     │  │             ▼                               │                  │
│            │             │     │  │ ┌───────────────────────┐                   │                  │
│            │             │     │  │ │ ② 시스템 선처리       │                   │                  │
│            │             │     │  │ │ 인증·거래제어·로깅    │                   │                  │
│            │             │     │  │ └───────────┬───────────┘                   │                  │
│            │             │     │  │             │                               │                  │
│            │             │     │  │             └──────────────┐                │                  │
│            │             │     │  │                            ▼                │                  │
│            │             │     │  │              ┌─────────────────────────┐    │                  │
│            │             │     │  │              │      ③ 서비스 호출      │    │                  │
│            │             │     │  │              ├─────────────────────────┤    │                  │
│            │             │     │  │              │ Input 값                 │    │                  │
│            │             │     │  │              │ ┌─────────────────────┐ │    │                  │
│            │             │     │  │              │ │ Map                 │ │────┼──────────────┐   │
│            │             │     │  │              │ │ - reportId          │ │    │              │   │
│            │             │     │  │              │ │ - 조회 조건          │ │    │              │   │
│            │             │     │  │              │ │ - 출력 파라미터      │ │    │              │   │
│            │             │     │  │              │ └─────────────────────┘ │    │              │   │
│            │             │     │  │              │                         │    │              │   │
│            │             │     │  │              │ Output 값                │    │              │   │
│            │             │     │  │              │ ┌─────────────────────┐ │◀───┼──────────┐   │   │
│            │             │     │  │              │ │ String              │ │    │          │   │   │
│            │             │     │  │              │ │ - 조회 결과/응답값   │ │    │          │   │   │
│            │             │     │  │              │ └──────────┬──────────┘ │    │          │   │   │
│            │             │     │  │              └─────────────┼────────────┘    │          │   │   │
│            │             │     │  │                            │                 │          │   │   │
│            │             │     │  │ ┌───────────────────────┐  │                 │          │   │   │
│            │             │     │  │ │ ⑤ 시스템 후처리       │◀─┘                 │          │   │   │
│            │             │     │  │ │ 예외·응답·종료 로깅    │                    │          │   │   │
│            │             │     │  │ └───────────┬───────────┘                    │          │   │   │
│            │             │     │  │             ▼                                │          │   │   │
│            │             │     │  │ ┌───────────────────────┐                    │          │   │   │
│   ◀────────┼─────────────│─────│──│ │ ⑥/⑦ Return : String  │                    │          │   │   │
│   결과 문자열            │     │  │ └───────────────────────┘                    │          │   │   │
│                          │     │  └─────────────────────────────────────────────┘          │   │   │
└──────────────────────────┘     │                                                              │   │   │
                                 │  ┌───────────────────────────────┐                           │   │   │
                                 │  │            Service            │                           │   │   │
                                 │  │                               │◀──────────────────────────┘   │   │
                                 │  │ ④ ┌────────────────────────┐  │                               │   │
                                 │  │    │ Map                    │  │                               │   │
                                 │  │    │ - SQL 조회 조건         │  │───────────────┐               │   │
                                 │  │    │ - RD 실행 파라미터      │  │               │               │   │
                                 │  │    └────────────────────────┘  │◀──────────┐    │               │   │
                                 │  └───────────────────────────────┘           │    │               │   │
                                 └──────────────────────────────────────────────┼────┼───────────────┘   │
                                                                                │    │                   │
                                                                          DB 결과    │ DB 요청            │
                                                                                │    ▼                   │
                                                                            ┌───────────────────────────┐
                                                                            │         Database          │
                                                                            │  RDW/업무 DB              │
                                                                            │  SELECT / Procedure       │
                                                                            └───────────────────────────┘

요청 방향(파란색 원도식): xFrame ─Map→ Controller ─Map→ Service ─Query→ Database
응답 방향(빨간색 원도식): Database ─Result→ Service ─String→ Controller ─String→ xFrame
```

### 3.2 계층별 단순화 그림

```text
┌────────────── Presentation ──────────────┐
│ xFrame / Report Designer                 │
│ Map { reportId, conditions, parameters } │
└──────────────────┬───────────────────────┘
                   │ ① POST /rd/{serviceId}
                   ▼
┌────────────── Framework / BT ────────────┐
│ NhRDController                           │
│ ② serviceId 식별                         │
│ ② 시스템 선처리                          │
│ ③ Input Map으로 Service 호출             │
└──────────────────┬───────────────────────┘
                   │ Map
                   ▼
┌──────────────── Service ─────────────────┐
│ ④ 조회조건 검증·DB 작업                  │
│ Map → DAO/SQL Parameter                  │
└──────────────────┬───────────────────────┘
                   │ SQL / Procedure
                   ▼
┌──────────────── Database ────────────────┐
│ RDW / 업무 DB                            │
│ 보고서 출력용 데이터 조회                │
└──────────────────┬───────────────────────┘
                   │ Result Set
                   ▼
┌──────────────── Service ─────────────────┐
│ DB 결과 → RD 응답 문자열                 │
└──────────────────┬───────────────────────┘
                   │ String
                   ▼
┌────────────── Framework / BT ────────────┐
│ ⑤ 시스템 후처리                          │
│ ⑥ 예외·응답 규격화                       │
│ ⑦ Return String                         │
└──────────────────┬───────────────────────┘
                   │ String
                   ▼
┌────────────── Presentation ──────────────┐
│ xFrame / Report Designer                 │
│ 조회 결과 수신 후 보고서 렌더링          │
└──────────────────────────────────────────┘
```

### 3.3 요청·응답 시퀀스 그림

```text
xFrame          NhRDController       System Pre/Post       Service/DAO          Database
  │                    │                    │                    │                   │
  │ ① Map              │                    │                    │                   │
  │ POST /rd/{svcId}   │                    │                    │                   │
  ├───────────────────▶│                    │                    │                   │
  │                    │ ② serviceId 식별   │                    │                   │
  │                    │────────┐           │                    │                   │
  │                    │◀───────┘           │                    │                   │
  │                    │ ② 선처리 요청      │                    │                   │
  │                    ├───────────────────▶│                    │                   │
  │                    │     승인/Context   │                    │                   │
  │                    │◀───────────────────┤                    │                   │
  │                    │ ③ Input Map                             │                   │
  │                    ├────────────────────────────────────────▶│                   │
  │                    │                    │                    │ ④ SQL/Procedure   │
  │                    │                    │                    ├──────────────────▶│
  │                    │                    │                    │     Result Set    │
  │                    │                    │                    │◀──────────────────┤
  │                    │         String/처리결과                  │                   │
  │                    │◀────────────────────────────────────────┤                   │
  │                    │ ⑤ 후처리 요청      │                    │                   │
  │                    ├───────────────────▶│                    │                   │
  │                    │ ⑥ 응답/종료 결과  │                    │                   │
  │                    │◀───────────────────┤                    │                   │
  │ ⑦ Return String   │                    │                    │                   │
  │◀───────────────────┤                    │                    │                   │
  │                    │                    │                    │                   │
```

---

## 4. 이미지 기준 처리 단계

| 단계 | 주체 | 처리 내용 |
|---:|---|---|
| ① | xFrame | RD Controller를 호출하고 보고서 조회조건 Map 전달 |
| ② | NhRDController | `/rd/{serviceId}`에서 서비스 ID 식별 후 시스템 선처리 수행 |
| ③ | Controller | Input Map을 변경하거나 DTO로 강제하지 않고 Service 인자로 전달 |
| ④ | Service | Map의 조회조건을 이용해 DAO/Database 작업 수행 |
| ⑤ | Controller/Framework | Service 완료 후 시스템 후처리 수행 |
| ⑥ | Controller | 후처리 결과와 업무 결과를 RD 응답 계약으로 정리 |
| ⑦ | Controller → xFrame | 최종 결과를 String 타입으로 반환 |

> 원본 하단 설명에는 6번이 보이지 않고 5 다음에 7이 표기된다. 그림의 `시스템 후처리 → Return` 흐름을 기준으로 ⑥을 응답 정리 단계로 보완했다.

---

## 5. 데이터 계약 해석

### 5.1 입력 Map

```text
Map
├─ reportId       : 보고서/서식 식별자
├─ serviceId      : 실행할 RD 데이터 서비스
├─ parameters     : 보고서 입력 파라미터
├─ conditions     : 조회조건·필터·정렬
├─ locale         : 언어·지역 정보
├─ format         : PDF/XLS/화면 미리보기 등
└─ userContext    : 권한검증에 필요한 사용자·조직 정보
```

이미지가 명시하는 것은 Map 형식뿐이며 실제 키는 제품 규격에서 확인해야 한다. 위 항목은 안전한 논리 모델의 예시이지 이미지에서 직접 판독된 물리 필드가 아니다.

### 5.2 출력 String

String은 다음 중 하나일 수 있으나 이미지에는 세부 포맷이 없다.

| 후보 | 설명 |
|---|---|
| JSON String | RD가 데이터셋으로 해석하는 JSON 문자열 |
| XML String | Report Designer 전용 XML 데이터 |
| 조회 결과 ID | 별도 결과 캐시·세션을 가리키는 키 |
| 상태 문자열 | 성공·실패 및 오류 메시지 |

권장 계약은 단순 문자열 연결보다 명시적인 Content-Type과 오류 코드를 가진 JSON/XML 스키마다.

---

## 6. 아키텍처 설계 의도

### 6.1 RD 전용 진입점

일반 온라인 JSON 거래와 보고서 데이터 조회는 응답 포맷, 조회량, Timeout, 감사 기준이 다르다. `/rd/{serviceId}`로 진입점을 분리하면 RD 전용 응답 변환과 조회 정책을 공통화할 수 있다.

### 6.2 Map 기반 느슨한 결합

Report Designer의 동적 파라미터를 Map으로 받으면 보고서마다 DTO를 만들지 않아도 된다. 반면 키 오타, 타입 불일치, SQL 파라미터 오염을 컴파일 시점에 발견할 수 없으므로 Controller/Service 경계에서 허용 키와 타입을 검증해야 한다.

### 6.3 Controller와 Service 책임

| 계층 | 책임 |
|---|---|
| Controller | serviceId 식별, 인증·Context, 입력 크기 제한, Service 호출, 응답 Content-Type |
| Service | 보고서 접근권한, 파라미터 검증, 조회정책, DAO 호출, 결과 변환 |
| DAO | 사전 등록 SQL/Procedure 실행, 파라미터 바인딩 |
| Database | 보고서 출력용 데이터 조회 |

Controller가 전달 Map으로 직접 DB를 호출하는 것은 계층 책임을 흐리므로, 이미지의 4번 문구는 Service가 Map을 사용해 DB 작업을 수행하는 것으로 해석하는 것이 일관적이다.

---

## 7. 현재 PDMG/TCF 구현과의 매핑

| 이미지 목표 구조 | 현재 구현 | 판정 |
|---|---|---|
| `/rd/{serviceId}` | `/online`, `/{businessCode}/online`, `/{serviceId}` | RD 전용 prefix 없음 |
| `NhRDController` | `OnlineTransactionController` | 일반 온라인 Controller만 존재 |
| Input Map | JSON의 `dto` 노드 또는 flat JSON | 개념상 대응, 계약 차이 |
| 시스템 선처리 | Filter/Interceptor + `stf.preProcess` | 구현 존재 |
| serviceId 라우팅 | `TransactionDispatcher.handlerMap` | 구현 존재 |
| Service/DB | Handler → Facade → Service → DAO/MyBatis | 구현 존재 |
| Output String | 업무 DTO/Object → JSON Response | RD String 전용 변환 없음 |
| 시스템 후처리 | `etf.postProcess` + ResponseBodyAdvice | 구현 존재 |

### 7.1 현재 PDMG 대응 텍스트 그림

```text
┌────────────── Client / UI ──────────────┐
│ JSON { hdr_nhnis, dto }                 │
└────────────────┬────────────────────────┘
                 ▼
┌──────────── DefaultFilter/Interceptor ──┐
│ ServiceContext·GUID·인증·거래제어       │
└────────────────┬────────────────────────┘
                 ▼
┌──────── OnlineTransactionController ────┐
│ serviceId: Header → JSON → Path         │
│ dtoBody 추출                            │
└────────────────┬────────────────────────┘
                 ▼
┌──────────────── TcfFacade ──────────────┐
│ stf.preProcess                          │
│ Timeout/Transaction 실행                │
│ TransactionDispatcher.dispatch          │
│ etf.postProcess                         │
└────────────────┬────────────────────────┘
                 ▼
┌──────── Handler → Facade → Service ─────┐
│ serviceId 라우팅                        │
│ Map/Object → DTO 변환                   │
│ 업무 처리                               │
└────────────────┬────────────────────────┘
                 ▼
┌──────────── DAO / MyBatis / DB ─────────┐
│ SQL 실행 → Result                       │
└────────────────┬────────────────────────┘
                 ▼
┌────────── ResponseBodyAdvice ───────────┐
│ hdr_nhnis + dto JSON 응답               │
└─────────────────────────────────────────┘

장표 목표: /rd/{serviceId} + Map → String
현재 PDMG: 일반 온라인 URL + JSON dto → DTO/Object → JSON
```

### 7.2 소스 검증 근거

| 확인 사항 | 근거 | 판정 |
|---|---|---|
| HTTP 진입점 | `OnlineTransactionController.java:51-115` | 일반 온라인 URL과 dtoBody 전달 확인 |
| serviceId 결정 | `OnlineTransactionController.java:121-154` | Context Header → JSON → Path 순서 |
| 선후처리·Dispatcher | `TcfFacade.java:76-120` | STF → Dispatcher → ETF 확인 |
| 서비스 라우팅 | `TransactionDispatcher.java:29-67` | serviceId별 Handler 등록·호출 확인 |
| Handler → Facade | `mgcoa5530Handler.java:29-40` | serviceId에 따른 Facade 호출 예시 |
| Map/Object → DTO | `mgcoa5530Facade.java:31-35` | ObjectMapper 기반 DTO 변환 확인 |
| DB 트랜잭션 | `mgcoa5530Facade.java:31-35` | `rdwTransactionManager`, readOnly 적용 |
| Service → DAO | `mgcoa5530Service.java:84-85` | count/select DAO 호출 확인 |
| RD Controller | 저장소 전체 `NhRDController`, `/rd/` 검색 | 전용 구현 미확인 |

---

## 8. 주요 Gap과 위험

| Gap/위험 | 영향 | 권고 |
|---|---|---|
| RD 전용 Controller 없음 | 보고서 응답·Timeout·보안 정책을 일반 거래와 구분하기 어려움 | 실제 RD 제품 계약이 확정된 경우 전용 Adapter 도입 |
| Map 무타입 계약 | 키·타입 오류와 과도한 파라미터 전달 | 허용 키 스키마와 타입 검증 적용 |
| 동적 SQL 가능성 | SQL Injection·임의 테이블 접근 위험 | SQL ID 화이트리스트와 Prepared Binding 강제 |
| String 응답 모호성 | 성공·오류·데이터 포맷 구분 어려움 | JSON/XML 스키마와 Content-Type 명시 |
| 대용량 조회 | Heap·DB·네트워크 장기 점유 | 행 수·페이지·Timeout·다운로드 전환 기준 설정 |
| 일반 온라인 Timeout 공유 | 복잡한 보고서가 일반 거래 Worker를 고갈시킬 수 있음 | RD 전용 Pool·Bulkhead·Deadline 적용 |
| 권한 검증 위치 불명확 | 사용자가 허용되지 않은 보고서·데이터 조회 가능 | reportId+사용자+조직 기반 권한 검사 |
| 민감정보 출력 | 보고서 결과를 통한 개인정보 유출 | 컬럼 마스킹·감사로그·워터마크 정책 |

---

## 9. 운영·보안 기준

- 외부 입력으로 SQL 문장, 테이블명, 정렬식을 직접 받지 않는다.
- `serviceId`와 `reportId`를 사전 등록 카탈로그에서 검증한다.
- 사용자·조직·업무권한과 보고서 권한을 함께 검사한다.
- 최대 조회 행 수, 실행시간, 응답 크기와 동시 실행 수를 제한한다.
- DB 계정은 보고서 조회에 필요한 최소 SELECT 권한만 부여한다.
- 보고서별 데이터 분류등급과 개인정보 마스킹 규칙을 적용한다.
- GUID, userId, serviceId, reportId, 조회건수, 소요시간, 결과코드를 감사로그에 남긴다.
- Client Disconnect와 Timeout 발생 시 DB Statement 취소를 전파한다.
- String 결과에 내부 SQL, Stack Trace, DB 접속정보를 포함하지 않는다.
- 동일 조건의 반복 조회는 데이터 민감도와 최신성 기준에 따라 캐시를 검토한다.

---

## 10. 권장 논리 계약

```java
public record ReportRequest(
        String serviceId,
        String reportId,
        Map<String, Object> parameters,
        String outputFormat,
        String guid,
        String userId
) {}

public record ReportDataResponse(
        boolean success,
        String reportId,
        String contentType,
        Object data,
        String errorCode,
        String errorMessage
) {}
```

Map을 유지해야 한다면 최소한 `reportId`별 JSON Schema 또는 서버 측 파라미터 정의표로 키, 타입, 필수 여부, 최대 길이를 검증해야 한다.

---

## 11. 최종 평가

이 장표는 xFrame과 Report Designer가 DB 데이터를 얻기 위한 경로를 `/rd/{serviceId}`와 NhRDController로 표준화한 구조다. Controller는 서비스 ID와 시스템 선후처리를 책임지고, Service는 입력 Map을 이용해 Database를 조회하며, 최종 결과는 String으로 반환된다. 요청 Map과 결과 String을 이용해 특정 보고서 제품과 Java 업무 서비스를 느슨하게 결합하려는 의도가 핵심이다.

현재 PDMG/TCF는 서비스 ID Dispatcher, 공통 선후처리, Handler/Facade/Service/DAO와 RDW 트랜잭션을 이미 갖고 있어 내부 실행 구조는 상당 부분 대응한다. 그러나 RD 전용 URL·Controller·Map/String 변환·보고서 권한·대용량 조회 정책은 구현되지 않았다. 실제 RD 제품 도입 시 일반 온라인 코어를 복제하기보다, 기존 `TcfFacade` 앞뒤에 RD 전용 Protocol Adapter를 두고 공통 실행 파이프라인을 재사용하는 방향이 적절하다.

---

## 12. 관련 문서

- [온라인 프레임워크 구성 분석](./온라인_프레임워크_구성_분석.md)
- [단말·애플리케이션 프레임워크 연계 분석](./단말_어플리케이션_프레임워크_연계_분석.md)
- [파일 업로드 연계 분석](./파일_업로드_연계_분석.md)
- [파일 다운로드 연계 분석](./파일_다운로드_연계_분석.md)
- [원본 OCR/Evidence — 85631/35 RD 연계](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85631/35_RD%28Report_Designer%29_연계.md)

*입력 Map의 실제 키, String 응답 형식, RD 제품 프로토콜과 DB 조회 규격은 Report Designer 제품 정의서 및 운영 표준으로 최종 확정해야 한다.*
