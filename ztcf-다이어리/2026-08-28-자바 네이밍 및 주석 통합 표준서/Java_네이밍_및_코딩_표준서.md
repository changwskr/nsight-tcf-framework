# Java 네이밍 및 코딩 표준서

대상: `pdmg-service` (`nhnis.mg.*`) · NSIGHT/PDMG 온라인 거래  
결정: **AS-IS** (업무 타입은 소문자 시작)  
근거: [`Java_네이밍_및_주석_통합_표준서.docx`](Java_네이밍_및_주석_통합_표준서.docx)를 10종 원문에서 한 권으로 재구성  
목차: [`Java_네이밍_및_코딩_표준서_목차.md`](Java_네이밍_및_코딩_표준서_목차.md)

원문 문장·현재 소스가 확인한 규칙 = `FACT`. 10종 충돌을 이 문서가 고른 값 = `결정`.

---

# 1. 개요

## 1.1 목적

이름은 표찰이 아니라 **한 거래를 끝까지 추적하는 계약**이다. `mgcoa9000S0`이 Handler 등록값, Facade/Service 메서드, DTO, URL, SQL ID, 로그에 같은 문자열로 남으면 URL 하나로 Java와 SQL을 찾을 수 있다. 규칙이 깨지면 컴파일은 되어도 MyBatis 바인딩·운영 추적이 실패한다.

## 1.2 적용 범위

적용한다.

- Java 패키지, 파일, 타입, 메서드, 변수, 상수
- Handler, Facade, Controller, Service, Rule, DAO, DTO
- Config, Filter, Aspect, Client, Support, Boot
- TCF (`OnlineTransactionController`, `TransactionHandler`), 표준 전문
- API, JSON, HTTP Header
- DB 객체, MyBatis XML, SQL ID
- YAML, Spring Bean, 예외 코드, 로그·MDC
- 테스트, 리소스, 문서

신규·변경 코드에 즉시 적용한다. 외부 연계·생성 DTO·기존 화면 계약은 11.2 호환 예외를 따른다.

이번 1권에 **넣지 않는 것**: 들여쓰기, 중괄호 위치, import 순서, null 처리 일반론. 부록 C.

## 1.3 규칙의 강도

| 구분 | 의미 |
|---|---|
| 필수 | 신규·변경 코드가 지킨다 |
| 권고 | 사유가 없으면 따른다 |
| 금지 | 신규에 쓰지 않는다 |
| 호환 예외 | 외부 계약·생성기·기존 FW 때문에 유지 |

우선순위: `외부 계약 > 호환 예외 > 필수 > 권고`. 외부 계약과 붙은 이름은 영향 분석 없이 바꾸지 않는다.

## 1.4 AS-IS와 TO-BE — 클래스 Case

**결정: AS-IS.**

```text
업무 프로그램 타입   mgcoa9000Handler   소문자 시작  (필수)
공통 FW · Config     GlobalExceptionHandler, RdwDataSourceConfig
                     PascalCase          (필수)
원문 01 TO-BE        Mgcoa9000Handler    채택하지 않음
```

FACT: 프로그램 접두를 PascalCase로 고치면 전문 생성기, 기존 타입, Mapper namespace와 어긋난다. `Mgcoa9000Handler`, `MgCoa9000Facade`, `MGCOA9000Service`는 업무 타입으로 쓰지 않는다.

## 1.5 원문 정본 우선순위

충돌하면 다음 순서로 판정한다.

```text
1. 이 문서 1.4 결정 (AS-IS Case)
2. 현재 pdmg-service 소스
3. 원문 06·07 (05장 네이밍 규칙 / ASCII 확장) · 원문 02·04 (원칙·형식)
4. 원문 03 (다이어그램)
5. 원문 01 (JavaDoc·계층 주석만 채택. 클래스 Case는 채택하지 않음)
6. 원문 05·10 (AS-IS 위반 분석 — 본문 규칙이 아님)
```

동일 제목의 다른 해시는 부록 A. 본문은 한 번만 쓴다.

---

# 2. 네이밍의 두 축

## 2.1 Architecture Key

런타임·외부 계약 문자열. 바꾸면 UI, 통제 데이터, 로그 대시보드가 깨질 수 있다.

```text
Program ID    mgcoa9000
Service ID    mgcoa9000S0
URL           /mgcoa9000S0          (TCF OFF) 또는 /{serviceId} (TCF ON)
SQL ID        mgcoa9000S0_S0
등록 키       Handler.serviceIds()  반환 문자열
```

## 2.2 Java Identifier

컴파일러가 보는 이름. 업무 타입은 Architecture Key의 Program ID를 **그대로** 접두로 쓴다.

```text
타입     mgcoa9000Handler.java  ↔  public class mgcoa9000Handler
메서드   mgcoa9000S0(...)         거래 진입만 Service ID (동사 camelCase 예외)
필드     pageNo, totalCount       camelCase
```

## 2.3 한 거래의 이름 유도 흐름

```text
업무 분류  mg + co + a + 9000
        → Program ID  mgcoa9000
        → Service ID  mgcoa9000C0

프로그램 단위 (S0 안 붙임)
  mgcoa9000Handler / Facade / Service / DAO / Controller
  mgcoa9000-ORA.xml

거래 단위 (Service ID)
  URL, DTO, Facade·Service 메서드, SQL ID 앞부분
```

## 2.4 이름을 바꾸면 깨지는 계약

| 이름 | 깨지는 것 |
|---|---|
| Service ID | URL, 로그, Handler 등록, Facade/Service 메서드, DTO |
| DAO FQCN | Mapper `namespace` |
| DAO 메서드 | XML `id` |
| 패키지 | Component Scan, Mapper Scan, AOP Pointcut |
| Bean 이름 | 주입 실패 |

Java private 변수 Rename과 같은 취급을 하지 않는다.

---

# 3. 식별자 체계

## 3.1 Program Prefix

FACT: 9자리 Program ID.

```text
대그룹  업무  세부  프로그램 번호
  mg  + co +  a  +    9000
                  ↓
              mgcoa9000
```

같은 접두를 공유하는 것: Handler, Facade, Controller, Service, DAO, Mapper 파일.  
여기에 `S0`/`C0`를 넣지 않는다.

## 3.2 Service ID 11자리

```text
[대구분2][업무2][세부1][식별4][구분자1][순번1]
     mg    co     a     9000     C       0
              Program ID = mgcoa9000
              Service ID = mgcoa9000C0
```

| 세그먼트 | 길이 | 대소문자 | 예 |
|---|---:|---|---|
| 대구분 | 2 | 소문자 | `mg` |
| 업무 | 2 | 소문자 | `co` |
| 세부 | 1 | 소문자 | `a` |
| 식별 | 4 | 숫자 | `9000` |
| 구분자 | 1 | **대문자** | `C` |
| 순번 | 1 | 숫자·A–Z | `0` |

금지: `MG.Xxx.yyy` 점 구분 체계, 서비스 ID 대문자화(`MGCOA9000S0`).

## 3.3 거래구분

| 코드 | 의미 | 예 |
|---|---|---|
| S | 조회 | `mgcoa9000S0` |
| C | 등록 | `mgcoa9000C0` |
| U | 수정 | `mgcoa9000U0` |
| D | 삭제 | `mgcoa9000D0` |
| A | 혼합 | `mgcoa0000A0` |
| R | 리포트 | `mgcoa0000R0` |

## 3.4 Program 단위 vs 거래 단위

```text
mgcoa9000S0 ┐
mgcoa9000C0 ├─> mgcoa9000Handler / Facade / Service / DAO
mgcoa9000U0 ┤
mgcoa9000D0 ┘
```

금지:

```text
mgcoa9000S0Handler     클래스에 구분자
mgcoa9000DTOin         거래 DTO에서 구분자 생략
mgcoa9000S0Service     거래마다 Service 분리
```

거래 메서드명이 Service ID인 것은 일반 동사 camelCase의 **명시적 예외**다. 보조 메서드는 `trimToNull`, `putIfHasText`처럼 동사+대상을 쓴다.

---

# 4. Java 언어 네이밍

## 4.1 패키지

필수: 모두 소문자, 점 구분.

```text
공통 FW     nhnis.fw…
Boot만      nhnis.mg.PdmgApplication
업무        nhnis.mg.[업무].[세부].[계층].[세부패키지]
Mapper      rdw.mg.[업무].[세부]/
```

샘플 (`co.a`):

```text
nhnis.mg.co.a.entry.handler
nhnis.mg.co.a.entry.aspect
nhnis.mg.co.a.application.controller
nhnis.mg.co.a.application.facade      ← AS-IS 현재 위치
nhnis.mg.co.a.application.service
nhnis.mg.co.a.dto
nhnis.mg.co.a.persistence.dao
nhnis.mg.co.a.config
nhnis.mg.co.a.client
nhnis.mg.co.a.support
```

FACT: 상위 아키텍처 문서는 `entry.facade`를 거론한다. **현재 구현은 `application.facade`**. 이 표준서는 AS-IS 경로를 필수로 둔다. `entry.facade`로의 이전은 패키지 아키텍처 결정과 함께 한다.

금지: `nhnis.mg.entry`(업무축 없음), Java 축과 `rdw.mg.*` 축 불일치, Handler를 `application`에 두기, `common`/`misc`/`util` 패키지.

## 4.2 클래스 · 인터페이스 · Enum

| 대상 | 형식 | 예 |
|---|---|---|
| 업무 타입 | Program ID + 역할 접미사, **소문자 시작** | `mgcoa9000Service` |
| 공통 타입 | PascalCase + 역할 | `SecurityConfig` |
| public 타입 | 파일명과 **완전 일치** | `mgcoa9000DAO.java` |
| 인터페이스 `I` 접두 | 습관적 사용 금지 | `CustomerRepository` |
| enum 타입 | PascalCase | `ProcessingType` |
| enum 값 | UPPER_SNAKE_CASE | `INQUIRY` |
| 예외 | `…Exception` | `BizException` |

`ServiceImpl`/`DAOImpl`을 관성적으로 붙이지 않는다. 현재 DAO는 Mapper Interface다.

## 4.3 메서드

| 종류 | 형식 | 예 |
|---|---|---|
| 거래 진입 (Facade/Service/Controller) | Service ID 그대로 | `mgcoa9000C0` |
| DAO | SQL ID 그대로 | `mgcoa9000S0_S0` |
| 일반 보조 | camelCase 동사+대상 | `select…List`, `validate`, `exists` |

금지: `doIt`, `proc`, `process1`, `handleData`, 거래 메서드를 `execute`/`process`로 감추기.

## 4.4 변수 · 필드 · 상수

- 변수·필드: camelCase. 컬렉션은 복수.
- 개수 `count`, 크기 `size`, 위치 `offset`.
- boolean: `is`/`has`/`can`/`should`/`enabled`.
- `static final`: UPPER_SNAKE_CASE.
- 시간·용량에 단위: `elapsedMs`, `sizeBytes`.
- 같은 개념은 같은 단어: 사용자 식별자는 `userId`.

```java
String serviceId;
List<mgcoa9000S0DTOout> records;
long totalCount;
boolean clientSuppliedHeader;
static final String HEADER_REQUEST_ID = "X-Request-Id";
```

## 4.5 예외 타입

업무 오류 `BizException`, 미분류는 일반 `Exception`. 실패 응답 조립은 `GlobalExceptionHandler` → `NH_NIS_ERR_DTO`. Handler/Controller별 오류 형식을 만들지 않는다.

## 4.6 Case Map

```text
Package              lowercase + .
업무 Type            mgcoa9000Service     소문자 시작
일반 Type            RdwDataSourceConfig  PascalCase
거래 메서드          mgcoa9000S0          Service ID
일반 메서드·필드     pageNo               camelCase
상수                 DEFAULT_PAGE_SIZE    UPPER_SNAKE
DB                   TRT_BRC              UPPER_SNAKE
SQL ID               mgcoa9000S0_S0
DTO 접미사           DTOin / DTOout       DtoIn 금지
```

---

# 5. 계층별 컴포넌트 네이밍

식별번호가 같으면 Handler/Facade/Controller/Service/DAO **접두는 같고**, 구분자(S/C/U/D)는 DTO·URL·SQL에만 붙인다.

```text
TCF ON:
  Filter → OnlineTransactionController (FW)
    → Handler(entry) → Facade(application, @Transactional)
      → [BizPrePostAspect] Service → DAO → DB

TCF OFF:
  Filter → Controller(application) → Service → DAO
```

FACT: TCF OFF Controller는 AS-IS로 Facade가 아니라 Service를 직접 호출한다. `Controller` 이름이 트랜잭션 경계를 뜻하지 않는다.

## 5.1 Handler

```text
패키지  nhnis.mg.co.a.entry.handler
이름    mgcoa9000Handler
구현    TransactionHandler
```

필수:

- 동일 식별번호의 여러 Service ID → Handler **1개**
- `serviceIds()` / `handle(...)`만 제공
- Facade만 주입. Service·DAO·`ObjectMapper`·`@Transactional` 금지
- TCF ON Bean: `@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")`
- 클래스명으로 자동 등록하지 않는다. **등록 문자열**이 Registry Key다

```java
private static final String S0 = "mgcoa9000S0";
public Collection<String> serviceIds() {
    return List.of(S0, C0, U0, D0);
}
```

업무 `*Handler`와 FW `GlobalExceptionHandler`를 혼동하지 않는다.

## 5.2 Facade

```text
패키지  nhnis.mg.co.a.application.facade
이름    mgcoa9000Facade
스테레오타입  @Service  (역할명은 Facade)
```

필수:

- 메서드명 = Service ID. 입력 `Object dtoBody`, 출력 typed `*DTOout`
- `@Transactional(transactionManager = "rdwTransactionManager")` — 조회 `readOnly = true`, 쓰기 `rollbackFor = Exception.class`
- Service만 호출. SQL·HTTP 조립 금지
- 이름만으로 최외곽 TX를 단정하지 않는다. Timeout ON이면 Worker Thread의 `TransactionTemplate`이 외곽이고 Facade REQUIRED가 참여한다

금지: `mgcoa9000ServiceFacade`, 여러 프로그램을 한 `CommonFacade`에 모으기.

## 5.3 Service · Rule

```text
패키지  nhnis.mg.co.a.application.service
이름    mgcoa9000Service
메서드  public [서비스ID]DTOout [서비스ID]([서비스ID]DTOin in)
```

TX는 Facade. Service에 기본 `@Transactional` 없음.

Rule: 재사용·독립 시험할 정책에 `Rule` 접미사 + **업무 의미** (`Util`/`mgcoa9000Rule`만으로는 부족). FACT: 현재 `mgcoa9000`에 Rule 클래스 없음. 없으면 AS-IS라고 쓰지 않는다.

## 5.4 Controller와 URL

```text
패키지  nhnis.mg.co.a.application.controller
이름    mgcoa9000Controller
조건    tcf.enabled=false (matchIfMissing true)
```

- 클래스 `@RequestMapping` 금지
- 메서드 `@PostMapping("/서비스ID")` — POST 고정
- URL = Service ID 문자열 그대로 (`C`는 대문자)

```text
TCF ON   POST /{serviceId}  또는 POST /online
TCF OFF  POST /mgcoa9000C0
UI 중계  POST /api/relay/{serviceId}
```

금지(업무 온라인): `/api/mg/co/a/8888/list`, GET `@GetMapping`, REST 자원명과 Service ID 혼용.

원문 일부의 `/api/mp/co/a/9999/list`는 **일반 REST 예시**이며 PDMG 온라인 거래 계약이 아니다.

신규 TCF ON은 Handler+Facade를 우선하고 Controller는 필요 시에만 둔다.

## 5.5 DAO

```text
패키지  nhnis.mg.co.a.persistence.dao
이름    mgcoa9000DAO
애노테이션  @RDWMapper
메서드  = SQL ID
```

DAO는 업무 판단을 이름에 넣지 않는다. `validateAndInsert…` 금지.

## 5.6 Config · Aspect · Client · Support · Boot

| 종류 | 형식 | 예 |
|---|---|---|
| Aspect | `{역할}Aspect` PascalCase | `BizPrePostAspect` |
| Config | `{대상}Config` | `RdwDataSourceConfig` |
| Properties | `{대상}Properties` | `CorsProperties` |
| Client | `{대상}Client` | |
| Support | 책임 명시 유틸 | 모호한 `Util` 패키지 금지 |
| Filter/Interceptor (FW) | `*Filter` / `*Interceptor` | `DefaultFilter` |
| Boot | `PdmgApplication` | `nhnis.mg`만 |

## 5.7 Spring Bean

Bean 이름 lowerCamelCase. 같은 타입이 여러 개면 데이터소스·목적을 붙인다.

```text
rdwDataSource
rdwSqlSessionFactory
rdwTransactionManager
securityFilterChain
```

YAML: lowercase kebab-case, 시간에 단위.

```yaml
nsight:
  tcf:
    slow-transaction-ms: 3000
```

비밀값·접속정보를 이름·예시에 넣지 않는다.

---

# 6. DTO · 전문 · JSON

## 6.1 업무 DTO 타입

```text
입력    <서비스ID>DTOin     mgcoa9000C0DTOin
출력    <서비스ID>DTOout    mgcoa9000C0DTOout
목록행  <서비스ID>DTOSub0   mgcoa9000S0DTOSub0
패키지  nhnis.mg.co.a.dto
```

`DTOin`/`DTOout`/`DTOSub`는 `DtoIn`과 혼용하지 않는다. 한 프로그램 안에서 두 표기를 섞지 않는다.

## 6.2 필드와 생성 DTO 예외

신규 수기 DTO: Java 필드 camelCase. 외부가 대문자 물리명이면 `@JsonProperty`로 분리.

```java
@JsonProperty("PROC_CNT")
private Integer procCnt;
```

FACT 생성 DTO 예외: `getmgcoa9000S0DTOSub0List`, 결과 필드 `PROC_CNT`/`RSLT_CD`. 생성기 계약을 수동으로 일부만 고치지 않는다.

## 6.3 표준 전문 봉투

```text
요청/성공  { "hdr_nhnis": {…}, "dto": {…} }
실패       NH_NIS_ERR_DTO  (stdErrCode, stdErrMsgCntn, errType)
```

쓰지 않음: `StandardRequestDto`, `result.resultCode`, `S0000`/`E0001` 봉투.

## 6.4 JSON

DTO와 JSON 필드는 camelCase로 맞춘다. 이전 이름 호환은 `@JsonAlias`로 제한. HTTP 헤더:

```text
X-Request-Id
X-Trace-Id
X-User-Id
X-Forwarded-For
Authorization
```

---

# 7. Persistence 네이밍

## 7.1 Mapper 파일명

```text
[Program ID]-[DBMS].xml
rdw.mg.co.a/mgcoa9000-ORA.xml
```

Program 단위 한 파일. `-ORA`면 Oracle 문법만.

## 7.2 namespace = DAO FQCN

```xml
<mapper namespace="nhnis.mg.co.a.persistence.dao.mgcoa9000DAO">
```

한 글자도 다르면 런타임 바인딩 실패.

## 7.3 SQL Statement ID

```text
[서비스ID]_[DML][순번]
[서비스ID]_[DML][순번]_count
[서비스ID]_[DML][순번]_exists     ← AS-IS 소스에 있음
```

```text
mgcoa9000S0_S0
mgcoa9000S0_S0_count
mgcoa9000C0_C0
```

금지: `selectList`, `mgcoa8888S0S0`(_ 없음), 대문자화.

재사용 `<sql id="mgcoa9000Where">`는 DAO 메서드가 아니다. Statement ID 형식을 억지로 맞추지 않는다.

## 7.4 DAO 메서드 = SQL ID

필수 코딩 계약. DAO 메서드명과 XML `id`는 완전 일치. 한쪽만 배포하지 않는다.

바인딩 실패(`Invalid bound statement`)면 SQL 문법보다 먼저 namespace · statementId · 메서드명을 대조한다.

## 7.5 Java 필드 ↔ DB 컬럼

```text
DB TX_ID  ↔ Java txId     (신규 원칙)
```

FACT `mgcoa9000` 조회: `HashMap` + SQL alias `TX_ID` → Service가 명시 변환 → DTO `txId`. `mapUnderscoreToCamelCase` 자동 매핑을 AS-IS로 단정하지 않는다.

쓰기: Service camelCase Map Key `txId` ↔ Mapper `#{txId}`.

## 7.6 보조 SQL 접미사

AS-IS: `_count`(목록 건수), `_exists`(존재). 원문 일부에 `_exists`가 없어도 소스에 있으면 규칙에 포함한다.

---

# 8. 오류 · 로그 · 테스트 · 리소스

## 8.1 예외 · 결과 코드

FACT: `exceptionCode.yml`에 형식이 섞여 있다. “모든 오류는 `FW`+4자리”라고 쓰지 않는다.

| 형식 | 예 | 해석 |
|---|---|---|
| 공통 숫자 | `FW0001`, `FW0401`, `FW9999` | 시스템 |
| 업무 숫자 | `MP0404` | 업무 영역 |
| 기술 상태 | `FW_TIMEOUT`, `FW_OVERLOADED` | 상태 |

미분류 기본 `FW9999`. 코드와 메시지 문자열을 분리. 폐기 코드를 다른 의미로 즉시 재사용하지 않는다.

`mgcoa9000` 등록 실패는 `RSLT_CD`/`RSLT_MSG` 필드. 모든 업무 오류가 이 방식이어야 한다고 일반화하지 않는다.

## 8.2 MDC · 로그

MDC (camelCase, 값당 이름 하나):

```text
guid  traceId  userId  serviceId  ip  sqlId  ifId  errCode
```

같은 값을 `requestId`/`reqId`/`guid`로 중복 정의하지 않는다.

로그 파일: `pk_framework.log`, `pk_service.log` (`pk_` = pdmg-service 권고 접두).

## 8.3 테스트

```java
class mgcoa8888ServiceTest { }
void mgcoa8888S0_missingKey_throwsBizException() { }
```

금지: `test1`, `successTest`, `tempTest`.

## 8.4 리소스 · 문서

`application.yml`, `log4j2.xml`, Mapper `-ORA.xml`. 같은 주제의 기준 문서를 둘 이상 만들지 않는다.

---

# 9. 주석 및 JavaDoc 코딩 표준

이름에 업무 의미를 다 넣지 않는다. Program ID + 접미사로 추적을 보장하고, **업무 설명은 JavaDoc**에 둔다. (원문 01의 주석 규칙. 클래스명은 AS-IS.)

## 9.1 클래스 JavaDoc

지원 Service ID 목록과 계층 책임을 적는다.

```java
/**
 * MG 공통관리 프로그램(9000) 거래 Handler.
 *
 * <p>지원 ServiceId:</p>
 * <ul>
 *   <li>mgcoa9000S0 : 조회</li>
 *   <li>mgcoa9000C0 : 등록</li>
 *   <li>mgcoa9000U0 : 수정</li>
 *   <li>mgcoa9000D0 : 삭제</li>
 * </ul>
 */
public class mgcoa9000Handler implements TransactionHandler {
}
```

Service:

```java
/**
 * 거래 파라미터 관리 프로그램(9000) 업무 서비스.
 * Transaction Boundary는 Facade에서 관리한다.
 */
public class mgcoa9000Service {
}
```

## 9.2 메서드 JavaDoc

거래 메서드는 Service ID가 이름이므로 `@param`/`@return`/`@throws`로 의미를 보완한다.

```java
/**
 * 거래 파라미터를 등록한다.
 *
 * @param in 등록 입력
 * @return 처리 결과
 * @throws BizException 중복 등 업무 오류
 */
public mgcoa9000C0DTOout mgcoa9000C0(mgcoa9000C0DTOin in) { }
```

상수:

```java
/** 기본 페이지당 조회 건수. */
private static final int DEFAULT_PAGE_SIZE = 20;
```

## 9.3 SQL 주석

권고: Statement 본문에 SQL ID를 넣는다. 운영에서 SQL만 보여도 거래를 찾을 수 있다.

```xml
<select id="mgcoa9000S0_S0" …>
  /* mgcoa9000S0_S0 */
```

## 9.4 주석으로 보완할 것 / 이름에 담지 말 것

| 이름에 담는다 | 주석에 둔다 |
|---|---|
| Program ID, 계층 접미사, Service ID | 한글 업무명, 정책 설명 |
| SQL ID 기계적 일치 | 왜 그 SQL이 필요한지 |
| 추적 키 | 구현 세부의 장황한 나열 |

---

# 10. 구현과 결합되는 코딩 계약

## 10.1 Scan과 패키지

업무 코드는 `nhnis.mg.[업무].[세부]` 아래. Boot만 `nhnis.mg`. Mapper는 `rdw.mg.[업무].[세부]`. 패키지를 옮기면 Scan·XML classpath를 같이 바꾼다.

## 10.2 AOP Pointcut

`BizPrePostAspect` 등 Pointcut은 **패키지·타입 이름**에 걸린다. Service를 `application.service` 밖으로 빼거나 접미사를 바꾸면 선후처리가 조용히 빠진다.

## 10.3 트랜잭션 경계와 이름

TX 선언 위치는 Facade 메서드(AS-IS). 이름만으로 최외곽 TX를 단정하지 않는다. Handler에 `@Transactional`을 걸지 않는다.

## 10.4 자동검증 (권고)

| 검사 | 내용 |
|---|---|
| Handler | 접미사, `entry.handler`, `serviceIds()` ⊆ 분기 대상 |
| Facade/Service | 메서드명 = 등록 Service ID |
| DAO/Mapper | FQCN = namespace, 메서드 = `id` |
| DTO | 생성 코드 예외(`getmgcoa…`)를 일반 JavaBeans로 오탐하지 않음 |

---

# 11. 금지 · 예외 · 체크리스트

## 11.1 금지 이름 · 금지 구조

금지 이름: `data`, `obj`, `temp`, `tmp`, `proc`, `doIt`, `value1`, `list2`, `commonUtil`, `misc`.

금지 구조:

- public 타입 ≠ 파일명
- Handler/Facade/Controller에서 SQL, DAO에서 HTTP
- Handler → Service/DAO 직행, Handler에 `@Transactional`
- Facade 없이 Handler만으로 업무·TX
- DTO에 Service/DAO 주입
- DAO 메서드 ≠ XML `id`, namespace ≠ DAO FQCN
- `DTOin`과 `DtoIn`, `DAO`와 `Dao` 혼용
- Java `mg.co.a`와 Mapper `rdw.mg.co.b` 불일치
- 비밀·개인정보를 이름·로그·예시

| 좋음 | 나쁨 |
|---|---|
| `mgcoa9000Handler` | `Mgcoa9000Handler` (업무 타입 TO-BE) |
| `mgcoa9000C0` 메서드 | `executeC0` |
| `mgcoa9000S0_S0` | `selectList` |
| `elapsedMs` | `elapsed` |
| `rdwTransactionManager` | `txManager2` |

## 11.2 레거시 호환 예외

이 이름들은 **일부만 독립적으로 바꾸지 않는다.**

| 이름 | 유지 이유 |
|---|---|
| `mgcoa*Handler/Facade/Service/DAO` | 소문자 접두 결합 · namespace |
| `mgcoa*Controller` | TCF OFF |
| `*DTOin/out` | 전문·생성기 |
| `*_S0` SQL ID | MyBatis 계약 |
| 생성 DTO 메서드·대문자 필드 | 생성기 |
| Facade `application.facade` | 현재 소스 |

새 예외는 외부 계약, 영향 범위, 종료 조건을 문서화한다.

## 11.3 신규 프로그램 12-STEP

1. 업무 영역(대그룹·업무·세부) 확정  
2. Program ID 배정  
3. Service ID 부여  
4. 패키지 업무축 `nhnis.mg.[업무].[세부]`  
5. 프로그램 단위 타입 (Handler/Facade/Service/DAO…)  
6. 거래 메서드·URL = Service ID  
7. DTO 이름  
8. Rule 필요 여부  
9. DAO·Mapper 이름  
10. 필드 ↔ 컬럼 매핑  
11. 오류 코드  
12. 전체 Chain 검증 (등록 문자열 · namespace · SQL ID · Scan)

## 11.4 이름 유도 워크북

```text
Program ID     : mgcoa______
Service IDs    : ________S0 / C0 / U0 / D0
패키지 축      : nhnis.mg.__.__
Handler        : ________Handler
Facade         : ________Facade
Service        : ________Service
DAO            : ________DAO
Mapper         : ________-ORA.xml
DTO (거래별)   : ________S0DTOin / out
SQL            : ________S0_S0
URL            : /________S0
```

## 11.5 코드 리뷰 순서

```text
1. Program ID · Service ID가 업무 경계와 맞는가
2. 파일명 = public 타입 = 접두 공유
3. Handler.serviceIds() ↔ switch ↔ Facade 메서드
4. Facade TX · Service 무TX
5. DAO 메서드 = XML id, namespace = FQCN
6. DTO에 구분자 포함, 필드 계약
7. 생성 DTO를 일반 스타일로 부분 수정하지 않았는가
```

## 11.6 좋음 / 나쁨 (업무 온라인)

```text
좋음  mgcoa8888Handler + mgcoa8888S0 / mgcoa8888D0
나쁨  mgcoa8888S0Handler
나쁨  MgCoa8888Service
나쁨  POST /api/mg/co/a/8888/list   (온라인 거래)
좋음  POST /mgcoa8888S0
```

---

# 부록 A. 원문 10종과 정본

| 원문 | 역할 | 이 문서에서 |
|---|---|---|
| 01 Java 네이밍+주석 | JavaDoc, 계층 예시. 클래스 PascalCase 권고 | 주석만. Case는 1.4가 기각 |
| 02·09 네이밍원칙 | 강도, 금지, 체크리스트 | 본문 채택. “타입은 PascalCase” 항목은 업무 타입에 적용하지 않음 |
| 03 다이어그램 | 유도 흐름 | 2.3 · 3 |
| 04·08 네이밍 형식 | 형식·SQL ID | 본문 |
| 05·10 형식-1 | AS-IS 위반 분석 | 규칙이 아님. 갭 확인용 |
| 06·07 05장 | AS-IS Case, Chain, 체크리스트 | **정본** |

---

# 부록 B. 추적 예시

## mgcoa9000

```text
Program    mgcoa9000
Services   S0 C0 U0 D0
Handler    nhnis.mg.co.a.entry.handler.mgcoa9000Handler
Facade     …application.facade.mgcoa9000Facade
Service    …application.service.mgcoa9000Service
DAO        …persistence.dao.mgcoa9000DAO
Mapper     rdw.mg.co.a/mgcoa9000-ORA.xml
DTO        mgcoa9000C0DTOin / mgcoa9000C0DTOout
SQL        mgcoa9000C0_C0
```

## mgcoa8888

동일 공식. SQL 예: `mgcoa8888S0_S0`, `mgcoa8888D0_D0`.

---

# 부록 C. 범위 밖 (원문 없음)

다음는 이 1권의 필수 규칙이 아니다. 별도 Java Style Guide가 있으면 그쪽을 따른다.

```text
들여쓰기 · 탭/스페이스
중괄호 K&R vs Allman
import 순서 · wildcard import
라인 길이
null 처리 일반 패턴
equals/hashCode 구현 스타일
스트림 vs for
로거 프레임워크 API 선택
```
