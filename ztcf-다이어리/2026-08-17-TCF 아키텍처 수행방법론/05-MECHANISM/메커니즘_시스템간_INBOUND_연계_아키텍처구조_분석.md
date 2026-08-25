# INBOUND 거래 처리 아키텍처 분석

> **분석 대상**: 제공 이미지 `In bound 거래 처리`  
> **범위**: Core Banking/기타 송신처에서 EAI HUB와 Cruz APIM을 거쳐 Framework로 유입되는 INBOUND 거래  
> **분석 일자**: 2026-08-22  
> **분석 원칙**: 이미지에서 확인되는 설계, 문맥상 해석, 현재 저장소 구현을 구분한다.

---

## 1. 핵심 결론

이 아키텍처는 송신 채널마다 다른 `Fixed Length(FLD)` 또는 `JSON` 전문을 APIG/W까지 원형에 가깝게 전달하고, Framework의 업무 Service 영역에서 공통 `Map`으로 변환해 비즈니스 로직을 실행하는 **채널 어댑터 + 내부 표준 모델** 구조다.

핵심 책임 경계는 다음과 같다.

- **EAI HUB**: Core Banking의 Fixed Length 전문을 중계한다.
- **Cruz APIM**: 외부 인터페이스 ID를 내부 `/nh/eai/{serviceId}` URL에 1:1 매핑한다. 전문 변환은 하지 않고 HTTP body를 전달한다.
- **NhInboundController**: 서비스 ID 기반 진입, 원문 저장, 시스템 선처리·후처리, 서비스 호출과 응답 반환을 담당한다.
- **Service**: `FLD/JSON → Map`과 `Map → FLD/JSON` 변환 및 업무 로직을 담당한다.

현재 저장소에는 이미지와 동일한 `NhInboundController`, `/nhapi/INBOUND`, `/nh/eai/{serviceId}` 구현은 확인되지 않는다. 현행 PDMG 온라인 경로는 `DefaultFilter → OnlineTransactionController → TcfFacade → TransactionDispatcher → Handler`로 유사한 제어 구조를 제공하지만, 입력을 JSON Map으로 파싱하므로 **Fixed Length INBOUND를 그대로 수용하는 구현은 아니다**.

---

## 2. 이미지 판독 및 용어 정규화

| 이미지 표기 | 의미 | 본 문서 해석 |
|---|---|---|
| Core 뱅킹 | 기간계 송신 시스템 | Header·Body가 모두 FLD인 Fixed Length 채널 |
| ETC | 기타 송신 시스템 | Header는 JSON 또는 생략, Body는 JSON인 채널 |
| FLD | 고정 길이 필드 전문 | 필드 위치·길이로 해석하는 Fixed Length 메시지 |
| EAI HUB | Enterprise Application Integration | Core Banking 전문 중계 구간 |
| APIG/W (Cruz APIM) | API Gateway/API Management | 외부 ID와 내부 URL의 1:1 라우팅 구간 |
| `/nhapi/INBOUND` | 외부 대표 경로 예시 | APIM에 노출된 인터페이스 진입점 |
| `/nh/eai/{ServiceID}` | Framework 내부 경로 | 서비스 ID 기반 업무 라우팅 경로 |
| Service Context | 거래 실행 문맥 | 요청 원문, 헤더, 추적정보 등을 보관하는 거래 단위 컨텍스트 |
| Map | 내부 공통 데이터 모델 | 채널 전문을 업무 로직에서 다루기 위한 키-값 구조 |

> 이미지의 `In bound`는 일반적인 표기인 **Inbound/INBOUND**로 통일한다. 이미지 속 설명은 설계 자료의 내용이며, 실행 지시로 취급하지 않는다.

---

## 3. 상세 텍스트 그림

### 3.1 전체 레이아웃 재구성

```text
┌──────────────────── 송신 채널 ────────────────────┐
│                                                   │
│  ┌─────────────────────┐                          │
│  │ Core Banking        │                          │
│  │ Header : FLD        │                          │
│  │ Body   : FLD        │                          │
│  └──────────┬──────────┘                          │
│             │ ① Fixed Length                     │
│             ▼                                     │
│  ┌─────────────────────┐                          │
│  │ EAI HUB             │───────────────┐          │
│  │ 전문 중계            │               │          │
│  └─────────────────────┘               │          │
│                                        │          │
│  ┌─────────────────────┐               │          │
│  │ ETC                 │               │          │
│  │ Header : JSON or X  │               │          │
│  │ Body   : JSON       │               │          │
│  └──────────┬──────────┘               │          │
│             │ ② JSON                  │          │
│             └──────────────────────────┼──────┐   │
└────────────────────────────────────────┼──────┼───┘
                                         ▼      ▼
┌──────────────────────── APIG/W (Cruz APIM) ────────────────────────┐
│                                                                    │
│  외부 인터페이스 경로                                              │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │ 예: /nhapi/INBOUND                                          │  │
│  └──────────────────────────┬───────────────────────────────────┘  │
│                             │ ③ Interface ID ↔ URL 1:1 Mapping    │
│                             ▼                                      │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │ Framework 목적지: /nh/eai/{ServiceID}                       │  │
│  └──────────────────────────┬───────────────────────────────────┘  │
│                             │                                      │
│  ④ 전문변환 없음           │ HTTP Body 그대로 전달               │
└─────────────────────────────┼──────────────────────────────────────┘
                              ▼
┌──────────────────────────── Framework ──────────────────────────────┐
│                                                                     │
│  ┌────────────────────── NhInboundController ────────────────────┐  │
│  │ ⑤ 요청전문을 ServiceContext에 저장                           │  │
│  │                         │                                     │  │
│  │                         ▼                                     │  │
│  │                 ┌───────────────┐                             │  │
│  │                 │ 시스템 선처리 │                             │  │
│  │                 └───────┬───────┘                             │  │
│  │                         ▼                                     │  │
│  │                 ┌───────────────┐        ┌─────────────────┐  │  │
│  │                 │  서비스 호출  │───────▶│     Service     │  │  │
│  │                 └───────────────┘        │                 │  │  │
│  │                                          │ ⑥ FLD/JSON      │  │  │
│  │                                          │       ↓         │  │  │
│  │                                          │      Map        │  │  │
│  │                                          │       ↓         │  │  │
│  │                                          │ 업무 로직 처리  │  │  │
│  │                                          │       ↓         │  │  │
│  │                                          │ ⑦ Map          │  │  │
│  │                                          │       ↓         │  │  │
│  │                                          │ FLD/JSON 응답   │  │  │
│  │                 ┌───────────────┐        └────────┬────────┘  │  │
│  │                 │ 시스템 후처리 │◀────────────────┘           │  │
│  │                 └───────┬───────┘                             │  │
│  │                         ▼                                     │  │
│  │ ⑧ 응답전문(Fixed Length 또는 JSON) Return                    │  │
│  └───────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────┘
                              │
                              └── APIM/EAI 경로를 역방향으로 송신처에 응답
```

### 3.2 두 채널의 수렴과 응답 분기

```text
[Core Banking]
 Header FLD + Body FLD
          │
          ▼
    Fixed Length ───▶ EAI HUB ───┐
                                  │
                                  ▼
                            Cruz APIM
                                  │  변환 없음
                                  ▼
                         NhInboundController
                                  │ 원문 보관
                                  ▼
              ┌──────────── Service Adapter ────────────┐
              │                                         │
 Fixed Length ├── FLD parser ──┐                        │
              │                ├──▶ Map ──▶ 업무 로직   │
 JSON         ├── JSON parser ─┘              │         │
              │                               ▼         │
              │                       결과 Map          │
              │                         ┌─────┴─────┐    │
              │                         ▼           ▼    │
              │                    FLD formatter JSON serializer
              └─────────────────────────┬───────────┬────┘
                                        │           │
                               Fixed Length       JSON
                                        │           │
                                  Core Banking     ETC
```

이 구조의 중심은 `Map`이다. 채널별 형식은 Service 입구에서 Map으로 수렴하고, 업무 처리 후 송신 채널 계약에 맞춰 다시 FLD 또는 JSON으로 분기한다.

### 3.3 요청·응답 시퀀스

```text
송신처       EAI HUB       Cruz APIM       NhInboundController       Service
  │             │              │                    │                   │
  │ ① FLD       │              │                    │                   │
  ├────────────▶│─────────────▶│                    │                   │
  │ 또는 ② JSON ───────────────▶│                    │                   │
  │             │              │                    │                   │
  │             │              │ ③ URL 1:1 매핑     │                   │
  │             │              │ ④ body 무변환 전달 │                   │
  │             │              ├───────────────────▶│                   │
  │             │              │                    │ ⑤ 원문 Context 저장
  │             │              │                    │ 선처리            │
  │             │              │                    ├──────────────────▶│
  │             │              │                    │   ⑥ FLD/JSON→Map │
  │             │              │                    │   업무 로직       │
  │             │              │                    │   ⑦ Map→FLD/JSON │
  │             │              │                    │◀──────────────────┤
  │             │              │                    │ 후처리            │
  │             │              │◀───────────────────┤ ⑧ 응답전문 Return │
  │◀────────────┴──────────────┤                    │                   │
  │       Fixed Length 또는 JSON 응답                │                   │
```

### 3.4 책임 경계

```text
┌───────────────┬───────────────────────────────┬──────────────────────────────┐
│ 구간          │ 수행 책임                     │ 하지 않아야 할 책임           │
├───────────────┼───────────────────────────────┼──────────────────────────────┤
│ EAI HUB       │ FLD 연결·중계                 │ 업무 데이터 의미 해석          │
│ Cruz APIM     │ 인증/정책/라우팅, 1:1 URL 매핑│ FLD↔JSON 업무 전문 변환        │
│ Controller    │ 문맥 생성, 선후처리, 호출 제어│ 개별 업무 필드 매핑             │
│ Service/Util  │ 전문↔Map 변환, 업무 로직      │ 채널 라우팅 정책                │
└───────────────┴───────────────────────────────┴──────────────────────────────┘
```

---

## 4. 이미지 번호 기준 처리 단계

| 단계 | 처리 | 입력 → 출력 | 분석 |
|---:|---|---|---|
| 1 | Core Banking 전문 송신 | FLD Header + FLD Body → Fixed Length | 표준 헤더와 본문을 고정 길이 전문으로 EAI HUB에 전달 |
| 2 | 기타 시스템 전문 송신 | JSON Header 또는 없음 + JSON Body | 헤더 포함 여부가 선택적인 JSON 채널 |
| 3 | APIM 1:1 매핑 | 외부 Interface ID → `/nh/eai/{serviceId}` | 외부 대표 API와 내부 서비스 식별 경로를 대응 |
| 4 | 무변환 전달 | HTTP body → 동일 HTTP body | APIM은 업무 전문을 변환하지 않음 |
| 5 | 요청 원문 저장 | 수신 전문 → `ServiceContext.requestBody` | 추적·감사·장애분석에 필요한 원문 보존 |
| 6 | 입력 전문 변환 | FLD 또는 JSON → Map | 업무팀 제공 Util이 채널 형식을 내부 모델로 정규화 |
| 7 | 출력 전문 변환 | 결과 Map → FLD 또는 JSON | 요청 채널 계약에 맞는 응답 형식 생성 |
| 8 | 응답 반환 | Fixed Length 또는 JSON → 송신처 | Controller 후처리 후 역방향 반환 |

---

## 5. 데이터 계약 분석

### 5.1 Fixed Length 요청

```text
┌──────────────────────── Fixed Length Message ────────────────────────┐
│ Header (FLD)                                                         │
│ ├─ 인터페이스/서비스 식별자                                         │
│ ├─ 거래 추적 식별자(GUID 등)                                        │
│ ├─ 송수신 시스템 정보                                               │
│ └─ 결과·길이·문자셋 관련 제어값                                     │
├──────────────────────────────────────────────────────────────────────┤
│ Body (FLD)                                                           │
│ └─ 업무별 필드: 정의된 offset/length/type 순서로 배치                │
└──────────────────────────────────────────────────────────────────────┘
```

Fixed Length 처리는 단순 문자열 분할이 아니다. 전문 버전, 문자셋, 바이트 길이, 한글 멀티바이트, 숫자 padding, trim 규칙과 필수값 검증이 계약에 포함되어야 한다.

### 5.2 JSON 요청

```json
{
  "header": {
    "serviceId": "업무 서비스 ID",
    "guid": "거래 추적 ID"
  },
  "body": {
    "businessField1": "value"
  }
}
```

이미지의 `Header : Json or X`는 헤더가 JSON이거나 아예 없을 수 있다는 의미로 읽힌다. 헤더가 없을 때 서비스 ID·추적 ID를 URL 또는 HTTP Header 중 어디서 취득할지 별도 계약이 필요하다.

### 5.3 내부 Map

```text
외부 표현                       내부 업무 표현
────────────────────────────────────────────────
Fixed Length ─┐
              ├──▶ Map<String, Object> ──▶ 업무 로직
JSON ─────────┘

Map 예시
├─ 표준 헤더/거래 문맥
├─ 업무 입력 필드
├─ 전문 유형(FLD/JSON)
└─ 응답 포맷 결정을 위한 채널 정보
```

Map은 채널 독립성을 높이지만 무타입 구조이므로 필드명 오타나 형변환 오류가 런타임까지 지연될 수 있다. 경계에서 스키마 검증을 수행하거나 내부에서는 typed DTO로 한 번 더 변환하는 방식이 안전하다.

---

## 6. 아키텍처 의도와 장점

### 6.1 APIM의 역할을 라우팅으로 제한

전문 변환을 Gateway에 넣지 않으면 배포 주체와 변경 책임이 분명해진다. 업무 필드가 변경되어도 APIM 라우팅 정책은 유지되고, 업무 Service와 전문 Util만 버전 관리할 수 있다.

### 6.2 채널 다양성을 내부 Map으로 흡수

Core Banking의 FLD와 기타 시스템의 JSON이 하나의 업무 로직을 공유한다. 채널별 파서는 달라도 업무 로직은 Map 이후부터 재사용할 수 있다.

### 6.3 원문과 변환 결과의 분리

Controller가 원문을 ServiceContext에 저장하므로 변환 오류가 발생해도 수신 당시 전문을 기준으로 원인을 추적할 수 있다. 단, 개인정보·계좌정보가 포함될 수 있으므로 원문 로깅은 마스킹과 보존기간 정책이 필수다.

### 6.4 선후처리의 공통화

인증, 거래제어, GUID, 로깅, 예외 표준화 같은 횡단 관심사를 Controller/Framework 구간에 두고, 업무 Service는 전문 변환과 업무 처리에 집중한다.

---

## 7. 현재 저장소 구현과의 대응

### 7.1 개념 대응표

| 이미지 설계 | 현재 저장소 | 대응 수준 |
|---|---|---|
| Cruz APIM `/nhapi/INBOUND` | `tcf-gateway`의 업무별 `/online` Proxy | 개념 유사, URL·제품·계약 다름 |
| `/nh/eai/{serviceId}` | `OnlineTransactionController`의 `/{serviceId}` | 서비스 ID 경로 개념 유사 |
| NhInboundController | `DefaultFilter` + `OnlineTransactionController` | 책임이 Filter와 Controller로 분산 |
| 요청전문 ServiceContext 저장 | `DefaultFilter`가 `requestBody` 저장 | 직접 대응 |
| 시스템 선후처리 | `TcfFacade.stf()` / `etf()` | 직접 대응 |
| 서비스 ID 라우팅 | `TransactionDispatcher` | 직접 대응 |
| FLD/JSON → Map | 현재 Filter의 JSON → Map 파싱 | JSON만 대응, FLD 미확인 |
| Map → FLD/JSON | 일반 객체 응답 직렬화 | FLD 응답 포매터 미확인 |
| INBOUND 전용 Controller | 검색 결과 없음 | 구현 공백 |
| Outbound EAI Client | `DefaultTcfServiceClient` | 반대 방향 기능, 직접 대응 아님 |

### 7.2 현행 PDMG 요청 경로

```text
HTTP JSON Request
       │
       ▼
DefaultFilter
├─ URI에서 serviceId 추출
├─ JSON body를 Map으로 파싱
├─ ServiceContext 생성
└─ 원문 requestBody 저장
       │
       ▼
OnlineTransactionController
├─ /online
├─ /{businessCode}/online
└─ /{serviceId}
       │
       ▼
TcfFacade
├─ stf() : 시스템 선처리
├─ TransactionDispatcher
└─ etf() : 시스템 후처리
       │
       ▼
Handler → Facade/Service → DAO/외부연계
       │
       ▼
JSON Response
```

이미지의 목표 구조와 가장 큰 차이는 현행 `DefaultFilter`가 수신 body를 JSON으로 파싱한다는 점이다. Fixed Length 전문이 들어오면 현재 흐름에서는 JSON 파싱 단계에서 실패할 가능성이 높다.

### 7.3 코드 근거

| 파일 | 근거 |
|---|---|
| `pdmg-fw/.../DefaultFilter.java` | URI 기반 서비스 ID 추출, JSON Map 파싱, ServiceContext 생성과 원문 body 저장 |
| `pdmg-fw/.../OnlineTransactionController.java` | `/online`, `/{businessCode}/online`, `/{serviceId}` 요청 진입과 DTO/Map 추출 |
| `pdmg-fw/.../tcf/core/facade/TcfFacade.java` | 시스템 선처리 → Dispatcher → 시스템 후처리 흐름 |
| `pdmg-fw/.../tcf/core/dispatch/TransactionDispatcher.java` | 서비스 ID 기반 Handler 선택과 호출 |
| `tcf-gateway/.../AbstractBusinessProxyController.java` | 문자열 request body를 Gateway Route Service로 전달 |
| `tcf-gateway/.../BusinessRouteService.java` | raw body를 downstream GRF 경로에 위임 |
| `tcf-eai/.../DefaultTcfServiceClient.java` | HTTP/JSON 표준 요청을 외부 endpoint로 보내는 outbound client |
| `tcf-eai/.../StandardRequestBuilder.java` | `header`와 `body` Map으로 표준 요청 생성 |

> `tcf-eai`라는 모듈명만으로 이미지의 INBOUND EAI Controller 구현이라고 판단하면 안 된다. 확인된 코드는 Framework가 외부로 요청을 보내는 **outbound HTTP/JSON client**다.

---

## 8. 사실·해석·구현 공백 구분

| 구분 | 내용 |
|---|---|
| 이미지에서 확인되는 사실 | FLD와 JSON 두 입력 형식, EAI HUB, Cruz APIM 1:1 매핑, APIM 무변환, ServiceContext 저장, Service 변환, FLD/JSON 응답 |
| 합리적 해석 | Map이 채널 독립 내부 모델이고 원문 저장은 추적·감사·장애분석을 지원함 |
| 현재 코드에서 확인 | JSON Map 파싱, ServiceContext requestBody 저장, 서비스 ID 라우팅, 시스템 선후처리 |
| 현재 코드에서 미확인 | `NhInboundController`, Cruz APIM 경로, FLD codec, 채널별 응답 포매터, APIM 정책 |
| 추가 확인 필요 | 실제 전문 정의서, FLD byte layout, 문자셋, 오류전문, timeout/retry, 서명·인증 방식 |

---

## 9. 주요 위험과 검토 항목

| 위험 | 영향 | 권고 |
|---|---|---|
| APIM과 Service 양쪽에서 전문 변환 | 이중 변환·책임 충돌 | APIM 무변환 원칙을 API 정책으로 명문화 |
| Fixed Length 문자셋/byte 길이 불일치 | 필드 절단·한글 깨짐 | charset과 byte 기준 offset 테스트 고정 |
| 서비스 ID 위변조 | 다른 업무 Handler 호출 | 허용 목록, 경로 정규화, 인증 주체별 권한 검사 |
| 원문 무제한 저장·로그 | 개인정보 노출·저장소 증가 | 필드 마스킹, 크기 제한, 암호화, 보존기간 적용 |
| Map 무타입 사용 | 런타임 형변환 오류 | 입력 스키마 검증 및 내부 typed DTO 권고 |
| 헤더 없는 JSON | 추적 ID·서비스 ID 누락 | URL/HTTP Header 대체 규칙과 필수값 정의 |
| 오류 응답 형식 불일치 | 송신처 파싱 실패 | FLD/JSON별 표준 오류전문과 오류코드 정의 |
| 재시도 중복 처리 | 금융 거래 중복 실행 | GUID/idempotency key 기반 중복 방지 |
| 전체 body 메모리 적재 | 대형 전문에서 메모리 압박 | 최대 크기 제한과 필요 시 streaming 검토 |

---

## 10. 권장 논리 계약

### 10.1 Controller 계약

```text
InboundRequest
├─ serviceId       : URL 또는 검증된 표준 Header
├─ messageType     : FLD | JSON
├─ rawBody         : 변형 전 수신 전문
├─ charset         : FLD 해석용 문자셋
├─ correlationId   : E2E 거래 추적 ID
└─ channelId       : CORE | ETC | 기타 채널
```

Controller는 `serviceId`, `messageType`, `rawBody`를 확정하고 ServiceContext에 기록하되, 업무 필드 해석은 Service 전문 변환기로 위임하는 편이 이미지의 책임 경계와 일치한다.

### 10.2 Codec 계약

```text
interface MessageCodec {
    supports(messageType, serviceId)
    decode(rawBody, schemaVersion) -> Map
    encode(resultMap, schemaVersion) -> bytes/string
}

FixedLengthCodec : MessageCodec
JsonCodec        : MessageCodec
```

업무팀 Util을 공통 인터페이스로 감싸면 Controller나 APIM에 조건문이 확산되는 것을 막고, 서비스 ID·전문 버전별 codec 선택을 테스트할 수 있다.

### 10.3 표준 실패 흐름

```text
수신
 ├─ 라우팅 실패      → 404/표준 미등록 서비스 오류
 ├─ 인증·권한 실패   → 401/403 또는 채널 오류전문
 ├─ 전문 해석 실패   → 형식 오류 + 필드/offset 정보
 ├─ 업무 검증 실패   → 업무 오류코드
 ├─ 시스템 예외      → 공통 시스템 오류코드
 └─ 성공             → 요청 채널 형식(FLD 또는 JSON) 응답
```

HTTP status와 업무 오류전문을 함께 정의해야 Gateway, Framework, 송신 시스템이 같은 실패를 서로 다르게 해석하지 않는다.

---

## 11. 검증 시나리오

1. 정상 FLD 요청이 EAI HUB와 APIM에서 byte 변경 없이 Framework에 도착하는지 확인한다.
2. 한글·숫자·공백 padding이 포함된 FLD를 정의된 charset과 byte offset으로 변환하는지 확인한다.
3. Header가 있는 JSON과 없는 JSON을 각각 처리하고 서비스 ID·GUID의 취득 경로를 검증한다.
4. 외부 Interface ID가 허용된 내부 `serviceId`에만 1:1 매핑되는지 확인한다.
5. APIM이 JSON 재직렬화나 Fixed Length trim을 수행하지 않는지 확인한다.
6. 수신 원문과 correlation ID가 ServiceContext에 남고 민감정보는 로그에서 마스킹되는지 확인한다.
7. 같은 업무 입력이 FLD와 JSON으로 들어왔을 때 동일 Map과 동일 업무 결과를 생성하는지 확인한다.
8. 응답이 요청 채널에 맞는 FLD 또는 JSON 형식으로 반환되는지 확인한다.
9. 미등록 serviceId, 잘못된 길이, 잘못된 JSON, timeout, 내부 예외의 표준 오류전문을 검증한다.
10. 동일 GUID 재전송 시 중복 거래 방지 정책이 작동하는지 확인한다.

---

## 12. 최종 정리

이 INBOUND 아키텍처는 **Gateway에서는 라우팅만 수행하고, Framework Controller에서는 거래 문맥과 공통 선후처리를 관리하며, Service에서는 채널 전문과 내부 Map 간 변환 및 업무 로직을 수행**하는 구조다. Fixed Length와 JSON이라는 이질적인 채널 계약을 Map으로 수렴시키는 것이 핵심 설계 포인트다.

현재 저장소의 JSON 온라인 거래 경로는 서비스 ID 라우팅, ServiceContext, 시스템 선후처리 측면에서 기반 구조를 제공한다. 그러나 원본 이미지 수준의 INBOUND 지원을 완성하려면 전용 경로/Controller, FLD codec, 응답 포매터, 전문 스키마·버전·문자셋 정책과 APIM 1:1 매핑 계약을 추가로 확인하거나 구현해야 한다.

---

## 13. 관련 자료

- [단말-어플리케이션 프레임워크 연계 분석](./단말_어플리케이션_프레임워크_연계_분석.md)
- [전문 표준화 분석](./전문_표준화_분석.md)
- [GUID 관리 체계 분석](./GUID_관리_체계_분석.md)
- [거래 처리 구조 분석](./거래_처리_구조_분석.md)
- [원본 아키텍처 분석 Evidence: 인터페이스 거래 처리](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85631/36_인터페이스_거래_처리.md)
