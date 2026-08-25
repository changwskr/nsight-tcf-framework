## MG 업무 ↔ MK 업무 도메인 간 서비스 연계 규칙

현재 자료를 기준으로 보면 **MG와 MK는 ServiceId·패키지·업무 소유권이 분리된 별도 업무 도메인**으로 보는 것이 적절합니다. MK는 `pdmk-service`라는 별도 애플리케이션 경계를 가지고 `mk...` ServiceId를 사용하고 있습니다.

따라서 최상위 원칙은 다음과 같이 잡는 것을 권장합니다.

```text
MG 업무
  │
  │  다른 도메인의 기능 필요
  ▼
MG Service
  │
  ▼
MG → MK Client / Port
  │
  │ HTTP + 표준전문
  │ Target ServiceId = mk...
  ▼
MK 업무 진입점
  │
  ▼
MK Service
  │
  ▼
MK DAO → MK Mapper → MK 소유 데이터
```

**MG가 MK의 Service/DAO/Mapper/Table을 직접 호출하지 않는 것**이 핵심입니다. 기존 NSIGHT 기준도 도메인 간 호출을 단순 Java 호출이 아니라 업무 책임, 데이터 소유권, 트랜잭션, 보안, Timeout, 장애전파, 운영책임의 경계를 넘는 행위로 정의합니다.

### 1. MG↔MK 기본 Architecture Rule

| Rule ID        | 규칙                                                        | 판정 |
| -------------- | ----------------------------------------------------------- | ---- |
| `R-DOMAIN-001` | MG와 MK는 독립 Business Domain으로 관리                     | 필수 |
| `R-DOMAIN-002` | MG → MK DAO 직접 호출 금지                                  | 금지 |
| `R-DOMAIN-003` | MG → MK Mapper 직접 호출 금지                               | 금지 |
| `R-DOMAIN-004` | MG가 MK 전용 Table을 직접 갱신하지 않음                     | 금지 |
| `R-DOMAIN-005` | MK → MG도 동일한 역방향 규칙 적용                           | 필수 |
| `R-DOMAIN-006` | 도메인 간 호출은 대상 도메인의 **공개 ServiceId** 사용      | 필수 |
| `R-DOMAIN-007` | 별도 WAR/애플리케이션이면 HTTP + 표준전문 사용              | 필수 |
| `R-DOMAIN-008` | WAR 간 Java Project Dependency로 업무 호출 금지             | 금지 |
| `R-DOMAIN-009` | MG→MK→MG와 같은 순환 동기 호출 금지                         | 금지 |
| `R-DOMAIN-010` | 호출자는 대상 도메인의 내부 Facade/Service 구조를 알지 않음 | 필수 |
| `R-DOMAIN-011` | 데이터 변경 책임은 데이터 소유 도메인에 둠                  | 필수 |
| `R-DOMAIN-012` | ServiceId·Timeout·오류·로그·권한을 호출계약에 포함          | 필수 |

기존 도메인 기준에서도 `도메인 A → 도메인 B DAO/Mapper`는 금지하고, 도메인 B의 공개 계약을 사용하도록 명시하고 있습니다. 다른 Service의 직접 주입 역시 같은 배포단위·공개 인터페이스·단방향 의존·동일 TX 참여 의도가 모두 맞을 때만 제한적으로 허용됩니다.

---

## 2. 가장 권장하는 MG → MK 호출 구조

예를 들어 MG 거래가:

```text
mgcoa1200S0
```

이고 이 거래 처리 중 MK의:

```text
mkcoa5530S0
```

정보가 필요하다고 가정하면, 다음 구조가 적절합니다.

```text
Client
  │
  │ ServiceId = mgcoa1200S0
  ▼
MG TCF
  ↓
MG Dispatcher
  ↓
MG Handler
  ↓
MG Facade
  ↓
MG Service
  │
  │ MK 정보 필요
  ▼
┌──────────────────────────────┐
│ MgToMkClient                 │
│                              │
│ targetServiceId              │
│ = mkcoa5530S0                │
└──────────────┬───────────────┘
               │
               │ HTTP
               │ hdr_nhnis + dto
               ▼
══════════════ DOMAIN BOUNDARY ══════════════
               ▼
         pdmk-service
               │
               │ ServiceId
               │ mkcoa5530S0
               ▼
        MK Controller
               ↓
         MK Service
               ↓
          MK DAO
               ↓
        MK Mapper XML
               ↓
         MK/RDW Data
               │
               ▼
        Standard Response
               │
══════════════ DOMAIN BOUNDARY ══════════════
               │
               ▼
         MgToMkClient
               ↓
          MG Service
               ↓
          MG Response
```

MK의 실제 ServiceId 역시 `mkcoa8888S0`처럼 `mk + 업무 + 세부업무 + 프로그램 + 거래` 축으로 URL·메서드·DTO·SQL ID를 연결하고 있습니다.

---

## 3. ServiceId 규칙이 특히 중요합니다

MG 요청과 MK 요청의 ServiceId를 섞으면 안 됩니다.

```text
원 요청

ServiceId
mgcoa1200S0
```

MG가 MK를 호출하는 순간에는 **새로운 MK 거래**입니다.

```text
MG Transaction
mgcoa1200S0

        │
        │ domain call
        ▼

MK Transaction
mkcoa5530S0
```

따라서 MK로 보내는 전문에서:

```text
rms_svc_c = mkcoa5530S0
```

이어야 합니다.

다음은 잘못된 구조입니다.

```text
POST MK

rms_svc_c = mgcoa1200S0     X
```

왜냐하면 ServiceId는 해당 요청에서 실행할 거래의 Identity이기 때문입니다. 현재 NSIGHT Dispatcher 역시 ServiceId를 Handler 검색의 핵심 Key로 사용합니다.

원 호출자를 추적해야 한다면 ServiceId를 재사용하지 말고 별도의 추적 문맥을 두는 방향이 좋습니다.

```text
traceId          = 동일
guid             = 동일 또는 연계 가능한 값

callerDomain     = MG
callerServiceId  = mgcoa1200S0

targetDomain     = MK
serviceId        = mkcoa5530S0
```

즉:

```text
Trace Identity는 이어간다.
Business Transaction Identity는 분리한다.
```

가 원칙입니다.

---

## 4. 트랜잭션은 MG와 MK를 하나로 묶지 않습니다

이 부분이 가장 중요합니다.

```text
MG
Transaction #1
   │
   │ HTTP
   ▼
MK
Transaction #2
```

HTTP 호출은 새로운 요청이므로 MG에서 사용 중인 JDBC Connection이나 Spring Transaction이 MK로 전달되지 않습니다. 기존 도메인 호출 기준도 HTTP 호출에서는 호출자 DB 변경과 피호출자 DB 변경을 하나의 Spring Local Transaction으로 원자화할 수 없다고 명시합니다.

따라서 다음 구조로 생각해야 합니다.

```text
MG TX BEGIN
    │
    ├─ MG DB 변경
    │
    ├──── HTTP ───────┐
    │                 │
    │            MK TX BEGIN
    │                 │
    │            MK DB 변경
    │                 │
    │            MK COMMIT
    │◀──── Response ──┘
    │
MG COMMIT
```

여기서 문제가 발생할 수 있습니다.

```text
MK COMMIT 성공
       ↓
MG 후속처리 실패
       ↓
MG ROLLBACK

결과
MG = ROLLBACK
MK = COMMIT
```

따라서 MG↔MK 변경성 거래에는 **분산 Local TX를 기대하지 말고 멱등성·보상처리·상태조회·재처리 정책**이 필요합니다.

---

## 5. 조회와 변경 연계를 구분해야 합니다

조회는 상대적으로 단순합니다.

```text
MG
 ↓
MK 조회 ServiceId
 ↓
MK 데이터 조회
 ↓
Response
 ↓
MG 업무 계속
```

예:

```text
MG.Customer.process
       ↓
MK.CustomerInfo.select
```

반면 변경은 훨씬 엄격하게 설계해야 합니다.

```text
MG 변경
 +
MK 변경
```

을 하나의 로컬 트랜잭션이라고 생각하면 안 됩니다.

권장 모델은:

```text
MG 변경
   ↓
MG 상태 저장
   ↓
MK 변경 요청
   ↓
성공
   ├─ MG 완료처리
   │
실패
   ├─ 재처리
   ├─ 보상
   └─ 운영확인
```

입니다.

---

## 6. Timeout도 하나의 전체 Budget으로 관리해야 합니다

예를 들어 MG 서비스 전체 Timeout이 5초라고 하겠습니다.

```text
MG Service Timeout = 5 sec
```

MG 내부에서 이미 2초를 사용했다면 MK에게 5초를 새로 주면 안 됩니다.

```text
전체 Budget        5 sec
MG 사용            2 sec
────────────────────────
Remaining Budget   3 sec
```

따라서:

```text
MG
remaining = 3 sec
   ↓
MgToMkClient
timeout <= 3 sec
   ↓
MK
```

가 되어야 합니다.

```text
MG Timeout = 5초

MK HTTP Timeout = 30초      X
```

와 같이 계층별로 Timeout을 새로 시작하면 상위 거래가 Timeout된 뒤에도 MK 처리가 계속되는 문제가 생깁니다. 현재 Transaction/Timeout 개선 기준도 하위 SQL·외부연계 Timeout이 Service Deadline보다 길 수 없도록 정의합니다.

---

## 7. 오류 전파 규칙

MG가 MK를 호출했다면 MK 오류를 무조건 MG 오류로 바꿔버리면 안 됩니다.

```text
MK
errorCode = MK0404
errorType = BIZ

        ↓

MG Client Adapter
        ↓
원인 보존

targetDomain   = MK
targetService  = mkcoa5530S0
targetError    = MK0404
```

MG에서 사용자에게 별도 업무코드를 보여줘야 한다면:

```text
MG 오류
MG0412

cause
 ├─ domain = MK
 ├─ serviceId = mkcoa5530S0
 └─ errorCode = MK0404
```

처럼 **업무 오류 Mapping과 원인 추적정보를 동시에 유지**하는 것이 좋습니다.

---

## 8. 데이터 소유권 규칙

예를 들어:

```text
TB_MK_CO_A_5530
```

이 MK 도메인의 데이터라면 MG가 다음처럼 접근하면 안 됩니다.

```text
MG DAO
   ↓
TB_MK_CO_A_5530             X
```

MK 자료에서도 MK 전용 테이블을 `TB_MK_...` 축으로 분류하고 있습니다.

정상 구조는:

```text
MG
 ↓
MK ServiceId
 ↓
MK Service
 ↓
MK DAO
 ↓
TB_MK_...
```

입니다.

즉:

> **데이터를 가진 도메인이 데이터를 변경하는 서비스를 소유한다.**

이 규칙을 지켜야 나중에 테이블 구조가 바뀌어도 MG 프로그램이 영향을 직접 받지 않습니다.

---

## 9. 같은 Tomcat에 MG.war와 MK.war가 있어도 규칙은 동일합니다

예를 들어:

```text
Tomcat JVM
│
├─ mg.war
│
└─ mk.war
```

처럼 같은 JVM에 존재한다고 하더라도:

```text
MG Bean
   ↓
MK Bean 직접 Injection
```

방식으로 처리하는 것은 권장하지 않습니다.

WAR는 각각 Spring ApplicationContext와 업무 소유권을 가진 별도 애플리케이션 경계이므로:

```text
mg.war
   │
   │ HTTP / ServiceId
   ▼
mk.war
```

로 유지하는 것이 좋습니다.

기존 프로젝트 원칙도 WAR 간 Java 직접 의존을 도메인 간 금지 검토 대상으로 정의하고 있습니다.

---

# 최종 표준

MG와 MK의 도메인 연계는 저는 다음 형태로 확정하는 것을 권장합니다.

```text
                    NSIGHT DOMAIN INTEGRATION

┌──────────────────── MG DOMAIN ─────────────────────┐

 ServiceId = mg...
        │
        ▼
 Handler
        ▼
 Facade
        ▼
 Service
        │
        │
        ▼
   MgToMkPort
        │
   MgToMkClient
        │
        │ Standard Message
        │ HTTP
        │ target ServiceId = mk...
        ▼

══════════════ DOMAIN / APPLICATION BOUNDARY ═════════════

        ▼
┌──────────────────── MK DOMAIN ─────────────────────┐

 MK Public ServiceId
        │
        ▼
 Controller / Entry
        ▼
 Service
        ▼
 DAO
        ▼
 Mapper
        ▼
 MK-owned Data

└─────────────────────────────────────────────────────┘
```

이를 한 문장으로 정리하면,

> **MG와 MK는 서로의 내부 Service·DAO·Mapper·Table을 호출하지 않고, 대상 도메인이 소유한 공개 ServiceId를 `Client/Port + HTTP + 표준전문`으로 호출한다. 각 도메인의 DB Transaction은 분리하고, GUID/Trace는 연결하며, Timeout은 Remaining Budget을 전달하고, 변경성 연계는 멱등성·재처리·보상 규칙을 별도로 둔다.**

이 규칙을 적용하면 `MG → MK`, `MK → MG`, 향후 `IC → SV`, `PC → IC` 같은 NSIGHT 전체 도메인 연계를 같은 표준으로 통제할 수 있습니다.
