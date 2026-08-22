# DTO 아키텍처 구조

현재 **PDMG 실제 소스 기준**에서 DTO(Data Transfer Object)는 단순히 데이터를 담는 Java Bean이 아니라, **표준전문과 업무 계층 사이에서 서비스별 입력·출력 계약을 정의하는 객체**입니다.

PDMG의 기본 원칙은 다음과 같습니다.

```text
외부 통신
   │
   ▼
표준전문
hdr_nhnis + dto
   │
   ├─ hdr_nhnis → Framework / ServiceContext
   │
   └─ dto       → 업무 DTO
                    │
                    ▼
                 Handler
                    ↓
                 Facade
                    ↓
                 Service
                    ↓
              DAO / Mapper
                    ↓
                   DB
```

즉 **공통 헤더는 Framework가 관리하고, 업무 데이터만 DTO로 업무 계층에 전달**합니다. 전체 표준전문을 Handler·Service까지 끌고 내려가지 않는 것이 핵심입니다.

---

## 1. DTO의 위치

현재 `pdmg-service`의 업무 패키지는 대략 다음 구조입니다.

```text
pdmg-service
└─ nhnis.mg.co.a
    │
    ├─ entry
    │   └─ handler
    │
    ├─ application
    │   ├─ controller
    │   ├─ facade
    │   └─ service
    │
    ├─ dto                  ★
    │   ├─ mgcoa9001S0DTOin
    │   ├─ mgcoa9001S0DTOout
    │   ├─ mgcoa9001C0DTOin
    │   ├─ mgcoa9001C0DTOout
    │   └─ ...
    │
    └─ persistence
        └─ dao
```

실제 PDMG 계층 구조에서도 `dto`는 업무 패키지의 독립 영역으로 존재하며, Handler → Facade → Service → DAO 구조와 함께 사용됩니다.

---

# 2. DTO 아키텍처의 핵심 구조

PDMG에서는 **ServiceId 단위로 입·출력 DTO를 정의**하는 구조가 가장 자연스럽습니다.

예를 들어 프로그램:

```text
Program ID
mgcoa9001
```

에 다음 거래가 있다고 하면:

```text
mgcoa9001S0   조회
mgcoa9001C0   등록
mgcoa9001U0   수정
mgcoa9001D0   삭제
```

DTO도 거래 단위로 대응합니다.

```text
mgcoa9001
   │
   ├─ mgcoa9001S0
   │     ├─ mgcoa9001S0DTOin
   │     └─ mgcoa9001S0DTOout
   │
   ├─ mgcoa9001C0
   │     ├─ mgcoa9001C0DTOin
   │     └─ mgcoa9001C0DTOout
   │
   ├─ mgcoa9001U0
   │     ├─ mgcoa9001U0DTOin
   │     └─ mgcoa9001U0DTOout
   │
   └─ mgcoa9001D0
         ├─ mgcoa9001D0DTOin
         └─ mgcoa9001D0DTOout
```

즉 핵심 관계는:

```text
ServiceId
      │
      ├── Request DTO
      │
      └── Response DTO
```

입니다.

---

# 3. 전체 요청 처리에서 DTO가 어떻게 움직이는가

예를 들어 조회 서비스:

```text
mgcoa8888S0
```

를 호출한다고 하겠습니다.

### Client 요청

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgcoa8888S0",
      "std_gbl_id": "GUID-..."
    }
  },
  "dto": {
    "pageNo": 1,
    "pageSize": 20,
    "serviceId": "mgcoa5530S0"
  }
}
```

Framework는 이 요청을 두 영역으로 분리합니다.

```text
                    Standard Request
                           │
               ┌───────────┴───────────┐
               │                       │
               ▼                       ▼
          hdr_nhnis                   dto
               │                       │
               ▼                       ▼
       ServiceContext           mgcoa8888S0DTOin
               │                       │
               │                       ▼
               │                    Handler
               │                       │
               │                       ▼
               │                    Facade
               │                       │
               └───────────────►    Service
```

현재 PDMG 표준전문 구조 역시 `hdr_nhnis.sys_comm + dto`로 구성되고, Controller 이후 업무계층에는 전체 전문이 아니라 업무 `dto`를 전달하는 구조입니다.

---

# 4. DTO의 가장 중요한 책임

DTO에는 **업무 데이터 계약만 들어가야 합니다.**

| DTO에 포함  | 예                                 |
| ----------- | ---------------------------------- |
| 검색조건    | 고객번호, 기간, 상태               |
| 업무 입력값 | 이름, 상품코드, 금액               |
| Paging 요청 | `pageNo`, `pageSize`               |
| 업무 결과   | 고객목록, 상품정보                 |
| Paging 결과 | `totalCount`, `totalPages`, `rows` |

반대로 다음 값은 원칙적으로 업무 DTO에 반복해서 넣지 않는 것이 좋습니다.

```text
GUID
사용자 ID
점코드
Client IP
JWT Token
ServiceId 공통 Header
Screen ID
```

이 값들은:

```text
hdr_nhnis
      ↓
ServiceContext
```

의 책임입니다.

따라서:

```text
DTO
= 업무 데이터

Context
= 실행 환경 데이터
```

로 분리해야 합니다.

---

# 5. DTO와 Context를 혼동하면 안 됩니다

이 부분이 DTO 아키텍처에서 매우 중요합니다.

## 잘못된 구조

```java
public class CustomerRequestDto {

    private String guid;
    private String userId;
    private String branchCode;
    private String clientIp;

    private String customerId;
    private String customerName;
}
```

그러면 모든 DTO마다:

```text
GUID
User
Branch
IP
...
```

를 반복하게 됩니다.

### 권장 구조

```text
ServiceContext
 ├─ guid
 ├─ userId
 ├─ branch
 ├─ clientIp
 ├─ screenId
 └─ serviceId

CustomerRequestDTO
 ├─ customerId
 └─ customerName
```

즉:

```text
시스템 정보 ≠ 업무 DTO
```

입니다.

---

# 6. DTO와 Entity/DB 객체도 분리해야 합니다

DTO를 DB Table 구조와 동일하게 만드는 것도 피해야 합니다.

예를 들어 DB가:

```text
TB_CUSTOMER
────────────────────────
CUST_NO
CUST_NM
CUST_STS_CD
REG_DTM
REG_USER_ID
UPD_DTM
UPD_USER_ID
DEL_YN
```

이라고 해서 화면 조회 DTO를 그대로:

```text
모든 DB 컬럼
    ↓
DTO
```

로 만들 필요는 없습니다.

조회 화면에는 실제로:

```text
고객번호
고객명
상태
```

만 필요할 수 있습니다.

따라서:

```text
DB Model
   │
   │ 필요한 정보만 Mapping
   ▼
Business DTO
   │
   ▼
Response
```

가 되어야 합니다.

---

# 7. Request DTO와 Response DTO를 분리한다

하나의 DTO를 입력·출력에 모두 사용하는 구조는 권장하지 않습니다.

### 비권장

```text
CustomerDTO
  ↑       ↓
Request  Response
```

이렇게 되면 시간이 지나면서:

```text
조회조건
+
등록값
+
DB 결과
+
화면 표시값
+
Paging
```

이 한 객체에 모두 섞입니다.

### 권장

```text
mgcoa9000S0DTOin
      │
      ▼
조회 조건

mgcoa9000S0DTOout
      │
      ▼
조회 결과
```

즉:

```text
Input Contract
       ≠
Output Contract
```

입니다.

---

# 8. DTO와 ServiceId의 추적성

PDMG 네이밍 구조에서는 DTO 이름 자체가 ServiceId 추적성을 제공합니다.

```text
ServiceId
mgcoa9000S0
     │
     ├─ mgcoa9000Handler
     ├─ mgcoa9000Facade
     ├─ mgcoa9000Service
     ├─ mgcoa9000DAO
     ├─ mgcoa9000-ORA.xml
     │
     ├─ mgcoa9000S0DTOin
     └─ mgcoa9000S0DTOout
```

현재 PDMG 네이밍도 ServiceId → 클래스 → DTO → DAO → Mapper → SQL까지 연결하는 추적성 구조를 핵심으로 둡니다.

따라서 ServiceId 하나만 알아도:

```text
mgcoa9000S0
```

에서 어느 DTO를 찾아야 하는지 바로 알 수 있어야 합니다.

---

# 9. Java 표준을 적용한 TO-BE DTO 네이밍

현재 AS-IS에는 다음 형식이 있습니다.

```text
mgcoa9000S0DTOin
mgcoa9000S0DTOout
```

프로젝트에서 Java 표준 네이밍을 강화한다면 TO-BE로는 다음 형식이 더 읽기 좋습니다.

```text
Mgcoa9000S0Request
Mgcoa9000S0Response
```

또는 DTO 명시가 필요하다면:

```text
Mgcoa9000S0RequestDto
Mgcoa9000S0ResponseDto
```

현재 Java 네이밍 기준에서도 클래스는 `UpperCamelCase`를 사용하고, DTO를 `Mgcoa9000S0Request`, `Mgcoa9000S0Response`처럼 구성하는 방향이 권장되어 있습니다.

즉 상태를 나누면:

| 구분           | 이름                  |
| -------------- | --------------------- |
| **AS-IS**      | `mgcoa9000S0DTOin`    |
| **AS-IS**      | `mgcoa9000S0DTOout`   |
| **TO-BE 권장** | `Mgcoa9000S0Request`  |
| **TO-BE 권장** | `Mgcoa9000S0Response` |

기존 소스를 한 번에 바꿀 필요는 없지만 **신규 개발 표준은 TO-BE 형식을 적용**하는 것이 좋습니다.

---

# 10. DTO Validation

DTO는 데이터 구조만 정의하는 것에서 끝나지 않고 **형식 수준의 입력 검증**을 담당할 수 있습니다.

예:

```java
public class Mgcoa9000S0Request {

    @NotBlank
    private String customerId;

    @Min(1)
    private int pageNo = 1;

    @Min(1)
    @Max(100)
    private int pageSize = 20;
}
```

다만 Validation도 책임을 구분해야 합니다.

```text
DTO Validation
────────────────────
필수값
길이
형식
범위
Null 여부


Service / Rule
────────────────────
고객이 실제 존재하는가?
거래가 허용되는가?
잔액이 충분한가?
상품 가입조건이 맞는가?
```

즉:

```text
형식 Validation → DTO
업무 Validation → Service / Rule
```

입니다.

---

# 11. Paging DTO 구조

Paging에서도 DTO의 역할이 명확합니다.

요청:

```text
Mgcoa8888S0Request
 ├─ pageNo
 ├─ pageSize
 ├─ serviceId
 └─ exceptionOnly
```

Service 내부:

```text
pageNo
pageSize
   ↓
offset 계산
```

응답:

```text
Mgcoa8888S0Response
 ├─ rows
 ├─ size
 ├─ pageNo
 ├─ pageSize
 ├─ totalCount
 └─ totalPages
```

중요한 것은 외부 DTO에:

```text
offset
startRow
endRow
rownum
```

등 DB 구현정보를 노출하지 않는 것입니다. 현재 PDMG Paging 기준도 Client는 `pageNo/pageSize`만 전달하고 Service가 `offset`을 계산하도록 정의합니다.

---

# 12. DAO까지 DTO를 그대로 전달할 것인가

여기에는 조금 더 엄격한 기준이 필요합니다.

단순 조회에서는:

```text
Service
   ↓
DTO
   ↓
DAO
```

가 편할 수 있습니다.

하지만 장기적으로는:

```text
Request DTO
     │
     ▼
Service
     │
     ├─ 업무 Validation
     │
     ├─ 업무변환
     ▼
Query / Parameter Model
     │
     ▼
DAO
```

처럼 **외부 계약 DTO와 DB Parameter를 분리하는 것이 더 안정적**입니다.

왜냐하면:

```text
화면 계약 변경
      ↓
DTO 변경
      ↓
SQL까지 영향
```

이 바로 전파되는 것을 막을 수 있기 때문입니다.

따라서 권장 방향은:

```text
Web Contract DTO
       ↓
Application
       ↓
Persistence Parameter
       ↓
Mapper
```

입니다.

---

# 13. `Map<String,Object>`와 DTO

현재 PDMG DAO AS-IS에는 `Map<String,Object>`를 사용하는 영역이 존재합니다.

예:

```java
List<Map<String, Object>> mgcoa9001S0_S0(
    Map<String, Object> params
);
```

이 구조는 빠르게 만들기는 편하지만 규모가 커지면:

```text
컴파일 시 타입검증 X
컬럼 오타 검출 X
자동완성 약함
리팩터링 어려움
데이터 계약 불명확
```

문제가 생깁니다.

따라서 신규 개발에서는:

```text
Map
 ↓
Typed DTO / Query DTO / Result DTO
```

방향을 권장합니다.

예:

```java
List<Mgcoa9001Row> selectTransactionControls(
    Mgcoa9001SearchCondition condition
);
```

---

# 14. DTO 아키텍처에서 금지할 구조

```text
① Controller
      ↓
   Map<String,Object>
      ↓
   Service
      ↓
   DAO
```

**금지/지양** — 계약이 사라집니다.

```text
② DTO 안에
   JWT / Connection / HttpServletRequest
```

**금지** — Framework 기술객체가 업무 DTO에 침투합니다.

```text
③ 하나의 DTO를
   조회/등록/수정/삭제에 공통 사용
```

**지양** — 거래 계약이 불분명해집니다.

```text
④ DB Entity = API DTO
```

**지양** — DB 변경이 외부 계약으로 전파됩니다.

```text
⑤ ServiceContext 정보를
   모든 DTO 필드로 복제
```

**금지** — Context와 업무 데이터 책임이 섞입니다.

```text
⑥ Response DTO에서
   Entity/Map 그대로 반환
```

**지양** — 내부 구조가 외부에 노출됩니다.

---

# 15. PDMG DTO 권장 아키텍처

최종적으로는 다음 형태가 가장 안정적입니다.

```text
                    STANDARD MESSAGE
                         │
             ┌───────────┴───────────┐
             │                       │
       hdr_nhnis                    dto
             │                       │
             ▼                       ▼
      ServiceContext         Request DTO
                                     │
                                     ▼
                                  Handler
                                     │
                                     ▼
                                  Facade
                                     │
                                     ▼
                                  Service
                             ┌───────┼────────┐
                             │       │        │
                             ▼       ▼        ▼
                           Rule   Query DTO  Integration DTO
                                     │
                                     ▼
                                    DAO
                                     │
                                     ▼
                                  Mapper
                                     │
                                     ▼
                                    DB
                                     │
                                     ▼
                              Result / Row DTO
                                     │
                                     ▼
                                  Service
                                     │
                                     ▼
                               Response DTO
                                     │
                                     ▼
                           Standard Response
                         hdr_nhnis + dto
```

---

## 16. 계층별 DTO 책임 정리

| 계층              | 사용하는 객체        | 책임                   |
| ----------------- | -------------------- | ---------------------- |
| Client            | JSON `dto`           | 외부 계약              |
| Resolver          | Request DTO          | JSON → Java 변환       |
| Handler           | Request/Response DTO | Use Case 전달          |
| Facade            | Request/Response DTO | Use Case 경계          |
| Service           | DTO + Domain/Query   | 업무 처리              |
| Rule              | Domain/Value         | 업무 규칙              |
| DAO               | Query/Parameter DTO  | DB 계약                |
| Mapper            | Parameter/Result     | SQL Mapping            |
| Response Resolver | Response DTO         | 표준전문 생성          |
| Framework         | `ServiceContext`     | GUID/User/ServiceId 등 |

---

# 17. 한 줄로 정리

PDMG DTO 아키텍처는 다음 원칙으로 정리하는 것이 좋습니다.

```text
표준전문
   ↓
Header는 Context로
업무 Data는 DTO로
   ↓
ServiceId별 Request/Response 계약
   ↓
Handler
   ↓
Facade
   ↓
Service
   ↓
필요 시 Query/Result DTO로 변환
   ↓
DAO / Mapper
```

그리고 가장 중요한 개발 표준은:

> **DTO는 “데이터를 아무거나 담는 통”이 아니라 ServiceId의 입·출력 계약이다.**

따라서 NSIGHT/PDMG의 추적성도 최종적으로는 다음처럼 관리하는 것이 좋습니다.

```text
Screen
  ↓
ServiceId
  ↓
Request DTO
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper / SQL
  ↓
Table
  ↓
Result
  ↓
Response DTO
```

이 구조를 잡으면 이후 **Validation 아키텍처, Service 아키텍처, Rule 아키텍처, Mapper 아키텍처**까지 자연스럽게 연결됩니다.
