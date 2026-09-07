# Java 네이밍 및 코딩 통합 표준서

> 문서 상태: 본문 1차 작성본  
> 작성 범위: 제0장~제4장  
> 기준 문서: `Java_네이밍_및_주석_통합_표준서.docx` 및 관련 네이밍 표준 문서  
> 적용 대상: Java/Spring 기반 온라인·배치 애플리케이션

## 표기 및 규범 수준

이 문서는 규칙의 출처와 강도를 다음과 같이 표시한다.

- `[원문]`: 기존 표준 문서에서 확인된 규칙 또는 구조
- `[결정]`: 상충하는 기존 규칙 중 현행 기준으로 선택한 규칙
- `[보완]`: 원문만으로 부족한 부분을 구현·검토 가능한 형태로 구체화한 규칙

규범 수준은 다음 키워드로 구분한다.

- **필수(MUST)**: 예외 승인을 받지 않는 한 반드시 준수한다.
- **권고(SHOULD)**: 특별한 사유가 없다면 준수한다.
- **허용(MAY)**: 프로젝트 특성에 따라 선택할 수 있다.
- **금지(MUST NOT)**: 사용해서는 안 된다.

---

# 제0장 문서 관리

## 0.1 목적

본 표준서는 Java 애플리케이션의 명명, 패키지 구조, 계층별 컴포넌트, 코딩 방식 및 주석 작성 방법을 일관되게 정의한다. 표준의 목적은 단순히 이름의 모양을 통일하는 데 있지 않다. 이름과 구조만으로 다음 정보를 예측할 수 있게 하는 것이 핵심이다.

- 소스가 속한 업무 영역과 세부 기능
- 컴포넌트의 계층과 책임
- 외부에 노출되는 서비스 식별자
- 호출 가능한 방향과 의존성 경계
- 데이터의 입력·출력 및 영속화 책임
- 장애 발생 시 추적해야 할 거래와 코드 위치

## 0.2 적용 대상

다음 산출물에 본 표준을 적용한다.

- Java 소스 코드와 테스트 코드
- 패키지, 모듈, 프로젝트 및 배포 단위
- Controller, Handler, Facade, Service, DAO, DTO, Mapper 등 업무 컴포넌트
- REST/HTTP URL, 서비스 ID, 오류 코드 및 로그 식별자
- 설정 파일, SQL Mapper, 배치 Job 및 리소스 파일
- JavaDoc, 구현 주석, TODO/FIXME 및 변경 이력

다음 항목은 별도 제품 표준이 우선할 수 있다.

- 상용 패키지가 자동 생성하는 클래스·필드
- 외부 전문과 계약된 JSON/XML 필드명
- 데이터베이스 물리 컬럼 및 레거시 인터페이스 식별자
- 프레임워크가 고정한 메서드·Bean 이름

## 0.3 적용 우선순위

규칙이 충돌하면 다음 순서로 판단한다.

1. 외부 시스템과 체결된 인터페이스 계약
2. 본 표준서의 `[결정]` 규칙
3. 본 표준서의 `[원문]` 규칙
4. 프로젝트별 승인 표준
5. 일반 Java 및 Spring 관례

기존 시스템의 호환성 때문에 본 표준과 다른 이름을 유지해야 하는 경우, 신규 코드까지 예외를 확산해서는 안 된다.

## 0.4 예외 승인

표준 예외는 다음 정보를 기록한 후 아키텍처 담당자의 승인을 받아야 한다.

| 항목 | 내용 |
|---|---|
| 대상 | 모듈, 패키지, 클래스 또는 API |
| 위반 규칙 | 표준서 절 번호와 규칙 |
| 사유 | 외부 계약, 레거시 호환, 제품 제약 등 |
| 영향 범위 | 호출자, DB, 배포, 모니터링 영향 |
| 종료 조건 | 영구 예외 또는 개선 예정 일자 |
| 승인자 | 아키텍처·개발 책임자 |

## 0.5 변경 관리

- 규칙 변경은 버전과 변경 사유를 기록해야 한다.
- 명명 규칙 변경 시 소스 코드뿐 아니라 URL, 설정, SQL Mapper, 모니터링, 배포 스크립트의 영향을 함께 검토한다.
- 대규모 일괄 변경은 컴파일, 단위 테스트, 정적 분석 및 회귀 테스트를 통과한 뒤 반영한다.
- 신규 표준은 신규 개발부터 적용하고, 기존 코드는 변경 작업이 발생한 범위에서 점진적으로 정비한다.

---

# 제1장 적용 범위와 기본 원칙

## 1.1 표준화 원칙

### 1.1.1 의미 우선

이름은 구현 방식보다 업무 의미와 책임을 먼저 표현해야 한다.

```java
// 권고
CustomerConsentService
findActiveCampaigns()

// 금지
DataService
processData()
```

### 1.1.2 하나의 이름, 하나의 책임

동일한 접미사는 동일한 책임을 가져야 한다. 예를 들어 `Service`가 붙은 클래스는 업무 규칙과 트랜잭션 경계를 담당하며, HTTP 요청 파싱이나 SQL 문자열 조립을 직접 수행하지 않는다.

### 1.1.3 계층을 이름으로 노출

`Controller`, `Service`, `DAO`, `DTO`, `Mapper` 등 표준 접미사를 사용해 소스의 역할을 즉시 식별할 수 있어야 한다.

### 1.1.4 축약어 최소화

프로젝트 공통 사전에 등록된 업무 코드와 널리 알려진 기술 약어만 허용한다. 임의 축약은 금지한다.

```text
허용: URL, HTTP, JSON, DTO, DAO, API, GUID
업무 사전 등록 후 허용: MP, CM, CR, EBM
금지 예: MktCustProcSvc처럼 문맥 없이는 해석하기 어려운 축약
```

### 1.1.5 호환성과 신규 표준의 분리

`[결정]` 기존 업무 프로그램 클래스는 기존 식별 체계를 유지할 수 있다. 예를 들어 `mpcoa0001Service`와 같은 소문자 업무 코드 시작 형식은 레거시 호환 영역에서 허용한다. 다만 독립된 공통 컴포넌트와 신규 범용 컴포넌트는 Java 관례인 UpperCamelCase를 적용한다.

## 1.2 식별자 계층

업무 식별자는 다음 계층으로 구성한다.

```text
애플리케이션 그룹(대구분)
└─ 애플리케이션(업무구분)
   └─ 기능(세부업무구분)
      └─ 프로그램/서비스 식별자
         └─ 계층 접미사(Controller, Service, DAO, DTO 등)
```

예시:

```text
mp + co + a + 0001 + Service
│    │    │   │      └─ 컴포넌트 역할
│    │    │   └──────── 서비스 일련번호
│    │    └──────────── 세부 기능
│    └───────────────── 애플리케이션
└────────────────────── 애플리케이션 그룹
```

## 1.3 업무 코드 구성

`[원문]` 업무 코드의 기본 축은 애플리케이션 그룹, 애플리케이션, 기능이다.

| 구분 | 의미 | 예시 |
|---|---|---|
| 애플리케이션 그룹 | 대분류 또는 플랫폼 | `mp`(마케팅 플랫폼), `bl`(BI 포털) |
| 애플리케이션 | 업무 분류 | `co`(공통), `cm`(캠페인), `cr`(신용실적) |
| 기능 | 세부 업무 | `a`, `b` 등 프로젝트 정의 코드 |
| 일련번호 | 프로그램 또는 서비스 번호 | `0001` |

- 업무 코드는 소문자를 사용한다.
- 코드 길이와 허용 값은 애플리케이션 코드 사전을 기준으로 한다.
- 이미 다른 의미로 등록된 코드를 재사용해서는 안 된다.
- 신규 코드는 코드 사전 등록 후 사용한다.

## 1.4 서비스 ID와 프로그램 식별자

`[결정]` 서비스 ID는 업무 코드를 기반으로 한 고유 식별자이며, Controller의 진입 메서드와 1:1로 대응하는 것을 기본으로 한다.

```text
서비스 ID: mpcoa0001
Controller: mpcoaController
Service:    mpcoa0001Service
DAO:        mpcoa0001DAO
```

- 하나의 서비스 ID가 여러 독립 거래를 동시에 의미해서는 안 된다.
- 조회·등록·수정·삭제 구분이 필요한 경우 프로젝트가 정의한 거래 구분 코드를 적용한다.
- URL의 서비스 ID와 로그의 서비스 ID는 동일해야 한다.
- 서비스 ID 변경은 외부 계약 변경으로 간주한다.

## 1.5 레거시와 신규 개발의 경계

| 영역 | 기본 원칙 |
|---|---|
| 기존 업무 프로그램 | 기존 소문자 업무 식별자 형식 유지 가능 |
| 신규 업무 프로그램 | 조직이 승인한 업무 코드 체계를 사용 |
| 공통 라이브러리 | UpperCamelCase 및 의미 중심 이름 사용 |
| 외부 계약 DTO | 계약 필드명 유지, 내부 모델과 분리 |
| 신규 REST API | 자원 중심 URL과 명시적 Request/Response 모델 권고 |

## 1.6 제1장 점검표

- [ ] 이름만으로 업무 영역과 책임을 식별할 수 있는가?
- [ ] 업무 코드가 코드 사전에 등록되어 있는가?
- [ ] 서비스 ID가 하나의 거래 진입점과 대응하는가?
- [ ] 레거시 호환 규칙이 신규 공통 코드로 확산되지 않았는가?
- [ ] 임의 축약어를 사용하지 않았는가?

---

# 제2장 프로젝트·모듈·패키지 구조

## 2.1 표준 소스 구조

`[보완]` Maven/Gradle 표준 디렉터리 구조를 사용한다.

```text
project-root/
├─ build.gradle 또는 pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ 기관도메인/시스템/업무영역/...
│  │  └─ resources/
│  │     ├─ application.yml
│  │     ├─ mapper/
│  │     └─ messages/
│  └─ test/
│     ├─ java/
│     └─ resources/
└─ docs/
```

운영 코드와 테스트 코드는 동일한 패키지 구조를 유지한다.

## 2.2 패키지 명명

- 패키지는 모두 소문자로 작성한다.
- 영문자와 숫자만 사용하며 하이픈, 밑줄, 한글을 사용하지 않는다.
- 숫자로 시작할 수 없다.
- 복수형보다 역할을 명확히 드러내는 단수형을 우선한다.
- 물리 조직명처럼 자주 바뀌는 정보는 최상위 패키지에 포함하지 않는다.

```java
// 권고
com.organization.marketing.campaign.service
com.organization.marketing.campaign.dto

// 금지
com.Organization.Marketing.Campaign_Service
com.organization.developmentteam1.campaign
```

## 2.3 계층 패키지

`[원문]` 업무 구현은 Controller, Service, DAO, DTO 계층을 기본으로 구성한다.

```text
업무기준패키지/
├─ controller
├─ service
├─ dao
├─ dto
├─ mapper
├─ client
├─ config
├─ exception
└─ support
```

프로젝트가 업무 기능 중심 패키징을 채택한 경우에도 기능 내부의 계층 명칭은 동일하게 유지한다.

```text
marketing/
├─ campaign/
│  ├─ controller
│  ├─ service
│  ├─ dao
│  └─ dto
└─ customer/
   ├─ controller
   ├─ service
   ├─ dao
   └─ dto
```

## 2.4 리소스 구조

```text
src/main/resources/
├─ application.yml
├─ application-local.yml
├─ application-dev.yml
├─ application-prod.yml
├─ mapper/
│  └─ 업무영역/
├─ messages/
│  ├─ messages.properties
│  └─ errors.properties
└─ static 또는 templates/
```

- 환경별 비밀값은 소스 저장소에 저장하지 않는다.
- SQL Mapper의 경로는 대응하는 DAO 또는 업무 영역을 식별할 수 있어야 한다.
- 동일 이름의 설정을 여러 파일에 중복 정의하지 않는다.

## 2.5 의존 방향

표준 호출 방향은 다음과 같다.

```text
Controller → Service → DAO → Database
                 ├──→ Client/Adapter → External System
                 └──→ Mapper/Converter
```

다음 의존은 금지한다.

- DAO가 Service 또는 Controller를 호출하는 구조
- Service가 웹 요청·응답 객체에 직접 의존하는 구조
- DTO가 DAO나 Service를 호출하는 구조
- 공통 모듈이 개별 업무 모듈을 참조하는 구조
- 순환 패키지 의존성

## 2.6 공통 코드 배치

업무 공통과 시스템 공통을 구분한다.

| 구분 | 예시 | 배치 원칙 |
|---|---|---|
| 시스템 공통 | 오류, 로깅, 보안, 트랜잭션 | 공통 프레임워크 또는 공통 모듈 |
| 업무 공통 | 고객 조회, 캠페인 검증 | 해당 애플리케이션 그룹의 공통 영역 |
| 기능 전용 | 특정 화면·거래 로직 | 해당 기능 패키지 |

공통화는 둘 이상의 실제 사용처가 확인되고 책임이 안정된 경우에 수행한다. 단순 중복 제거만을 목적으로 성급하게 공통 모듈로 이동하지 않는다.

## 2.7 금지 구조

```text
controller/
└─ 모든 업무 로직과 SQL을 포함한 거대 Controller   // 금지

common/
└─ 의미가 다른 모든 클래스의 임시 보관소          // 금지

util/
└─ 상태와 업무 규칙을 가진 서비스 클래스           // 금지
```

## 2.8 제2장 점검표

- [ ] 표준 빌드 디렉터리를 사용하는가?
- [ ] 패키지가 모두 소문자로 구성되었는가?
- [ ] 기능 또는 계층의 책임이 패키지 구조에 드러나는가?
- [ ] Controller → Service → DAO 의존 방향을 지키는가?
- [ ] 공통 모듈이 개별 업무 모듈에 의존하지 않는가?
- [ ] 리소스와 테스트가 운영 코드 구조에 맞춰 배치되었는가?

---

# 제3장 Java 기본 네이밍 규칙

## 3.1 클래스와 인터페이스

일반 Java 타입은 UpperCamelCase를 사용한다.

```java
public class CampaignService {}
public interface CustomerRepository {}
public record CampaignRequest(String campaignId) {}
```

`[결정]` 업무 코드 기반 레거시 프로그램 클래스는 다음 형식을 허용한다.

```java
public class mpcoa0001Service {}
public class mpcoa0001DAO {}
public class mpcoa0001SODTOin {}
```

신규 범용 타입에 `C`, `I`, `Impl` 같은 의미 없는 접두·접미사를 일률적으로 붙이지 않는다. 인터페이스와 구현체를 구분해야 하는 경우 책임을 드러내는 이름을 사용한다.

```java
// 권고
CampaignRepository
JdbcCampaignRepository

// 지양
ICampaignRepository
CampaignRepositoryImpl
```

## 3.2 메서드

메서드는 lowerCamelCase의 동사 또는 동사구를 사용한다.

| 목적 | 권고 동사 | 예시 |
|---|---|---|
| 단건 조회 | `find`, `get` | `findCustomerById()` |
| 목록 조회 | `find`, `search` | `findActiveCampaigns()` |
| 존재 확인 | `exists`, `has` | `existsActiveConsent()` |
| 생성 | `create`, `register` | `createCampaign()` |
| 변경 | `update`, `change` | `updateCustomerStatus()` |
| 삭제 | `delete`, `remove` | `deleteExpiredCampaigns()` |
| 검증 | `validate`, `verify` | `validateRequest()` |
| 변환 | `to`, `from`, `convert` | `toResponse()` |

`process`, `handle`, `execute`는 대상과 결과가 이름에 함께 나타나는 경우에만 사용한다.

```java
// 지양
void process();

// 권고
CampaignResult processCampaign(CampaignCommand command);
```

## 3.3 변수와 매개변수

- lowerCamelCase를 사용한다.
- 자료형이 아니라 업무 의미를 표현한다.
- 단일 문자 이름은 짧은 반복문의 인덱스 등 제한된 범위에서만 허용한다.

```java
Customer customer;
List<Campaign> activeCampaigns;
int retryCount;

// 지양
Customer data;
List<Campaign> list;
int n;
```

## 3.4 상수

상수는 UPPER_SNAKE_CASE를 사용한다.

```java
private static final int MAX_RETRY_COUNT = 3;
private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);
```

문자열 리터럴을 상수로 올릴 때도 이름이 값의 의미를 설명해야 한다.

## 3.5 Enum

- Enum 타입은 UpperCamelCase를 사용한다.
- Enum 상수는 UPPER_SNAKE_CASE를 사용한다.
- 코드값과 표시명은 필드로 명시한다.

```java
public enum CampaignStatus {
    DRAFT("01", "작성 중"),
    ACTIVE("02", "진행 중"),
    CLOSED("03", "종료");

    private final String code;
    private final String displayName;
}
```

## 3.6 Boolean

Boolean 변수와 반환 메서드는 긍정문으로 작성한다.

```java
boolean active;
boolean eligible;
boolean hasConsent;
boolean isExpired();
boolean canPublish();
```

`notActive`, `noConsent`, `isNotValid`처럼 이중 부정을 유발하는 이름은 피한다.

## 3.7 컬렉션과 Map

컬렉션 이름은 복수형 또는 집합의 의미를 사용한다.

```java
List<Customer> customers;
Set<String> campaignIds;
Map<String, Campaign> campaignById;
```

`customerList`, `campaignMap`처럼 구현 자료형만 반복하는 이름은 지양한다. 자료형 자체가 업무 의미인 특별한 경우만 허용한다.

## 3.8 약어와 대문자

Java 식별자 내부의 약어도 하나의 단어처럼 취급한다.

```java
// 권고
ApiClient
UrlBuilder
JsonConverter
GuidGenerator

// 지양
APIClient
URLBuilder
JSONConverter
GUIDGenerator
```

단, 기존 프레임워크가 정의한 `DTO`, `DAO` 접미사와 외부 계약 명칭은 호환을 위해 유지할 수 있다.

## 3.9 제네릭 타입 매개변수

단순 제네릭은 관례적 한 글자를 사용하고, 의미가 복잡하면 역할 이름을 사용한다.

```java
class Page<T> {}
interface Converter<S, T> {}
class Result<REQUEST, RESPONSE> {} // 장문의 대문자보다 아래 형식을 권고
class Result<RequestType, ResponseType> {}
```

## 3.10 파일명

- 공개 최상위 타입의 파일명은 타입명과 동일해야 한다.
- 하나의 파일에는 하나의 공개 최상위 타입만 둔다.
- 테스트 파일은 대상 타입에 `Test` 또는 `IntegrationTest`를 붙인다.

```text
CampaignService.java
CampaignServiceTest.java
CampaignServiceIntegrationTest.java
```

## 3.11 제3장 점검표

- [ ] 클래스와 메서드 이름이 책임과 동작을 표현하는가?
- [ ] `data`, `info`, `obj`, `temp` 같은 모호한 이름을 피했는가?
- [ ] Boolean 이름이 긍정문인가?
- [ ] 컬렉션 이름이 복수 또는 키 관계를 나타내는가?
- [ ] 상수가 UPPER_SNAKE_CASE인가?
- [ ] 약어 대소문자 규칙이 일관적인가?

---

# 제4장 업무 컴포넌트 및 계층별 네이밍

## 4.1 Controller

Controller는 외부 요청의 진입점이며 요청 검증, 입력 변환, Service 호출 및 응답 변환을 담당한다.

```java
@RestController
public class CampaignController {
    private final CampaignService campaignService;
}
```

`[원문]` 서비스 ID 기반 Controller는 다음 형식을 사용할 수 있다.

```java
public class mpcoaController {
    public Response mpcoa0001(Request request) { ... }
}
```

Controller에서 다음 행위는 금지한다.

- SQL 직접 실행
- 다수 DAO 조합을 통한 업무 규칙 구현
- 장시간 배치 처리
- 요청 객체를 전역 상태로 보관

## 4.2 Handler

Handler는 특정 프로토콜, 이벤트 또는 프레임워크 콜백을 처리하는 진입 컴포넌트에 사용한다.

```java
CustomerEventHandler
FileUploadHandler
GlobalExceptionHandler
```

일반 업무 서비스를 의미하는 이름으로 `Handler`를 사용하지 않는다.

## 4.3 Facade

Facade는 여러 Service 또는 외부 시스템 호출을 하나의 업무 유스케이스로 조정한다.

```java
CampaignExecutionFacade
CustomerSingleViewFacade
```

`[결정]` 현행 패키지명이 `application.facade`인 경우 이를 기준으로 사용한다. `entry.facade` 등 대체 구조는 전환 계획이 승인된 경우에만 적용한다.

Facade는 하위 계층의 세부 모델을 외부에 그대로 노출해서는 안 된다.

## 4.4 Service

Service는 업무 규칙과 트랜잭션 경계를 담당한다.

```java
CampaignService
CustomerConsentService
mpcoa0001Service
```

- 하나의 Service는 응집된 업무 책임을 가져야 한다.
- 메서드명은 업무 행위를 나타내야 한다.
- 다른 업무의 Controller를 호출하지 않는다.
- 타 업무 연계는 허용된 Service, Facade 또는 Client를 통해 수행한다.

## 4.5 DAO 및 Repository

`[결정]` 기존 프레임워크의 업무 코드 기반 영속 계층은 `DAO` 접미사를 유지한다.

```java
mpcoa0001DAO
CampaignDAO
```

Spring Data 또는 도메인 중심 신규 모듈은 `Repository`를 사용할 수 있다.

```java
CampaignRepository
JdbcCampaignRepository
```

한 모듈 안에서 같은 역할에 `DAO`, `Dao`, `Repository`를 무분별하게 혼용하지 않는다.

## 4.6 DTO

DTO는 계층 또는 시스템 경계를 넘는 데이터를 전달한다. 업무 규칙과 DB 접근 로직을 포함해서는 안 된다.

`[결정]` 기존 전문 기반 규칙은 다음 형식을 유지할 수 있다.

```text
mpcoa0001SODTOin
mpcoa0001SODTOout
mpcoa0001SODTOio
```

신규 API 모델은 역할을 명시하는 형식을 권고한다.

```java
CampaignCreateRequest
CampaignCreateResponse
CustomerSummary
CampaignCommand
CampaignResult
```

동일 모델을 Controller 요청, Service 명령, DB 조회 결과에 모두 재사용하지 않는다.

## 4.7 Mapper

Mapper는 객체 간 구조 변환 또는 SQL 매핑 역할을 담당한다.

```java
CampaignMapper          // 객체 변환
CampaignSqlMapper       // SQL Mapper 인터페이스
CampaignResponseMapper  // 응답 변환
```

`Mapper`라는 이름만으로 객체 변환과 SQL 실행 책임을 동시에 갖게 해서는 안 된다.

## 4.8 Client와 Adapter

외부 시스템 호출 컴포넌트는 대상과 역할을 이름에 포함한다.

```java
CoreBankingClient
NotificationClient
CustomerApiClient
LegacyCustomerAdapter
```

- `Client`: 원격 API·메시지·파일 시스템의 기술적 호출을 캡슐화한다.
- `Adapter`: 외부 모델과 내부 모델의 차이를 변환하고 포트를 구현한다.
- 호출 실패 정책과 타임아웃은 Client 경계에서 명시한다.

## 4.9 Converter

Converter는 값 또는 표현 형식의 변환에 사용한다.

```java
CharacterSetConverter
CustomerMessageConverter
```

업무 조회나 저장을 수행하는 클래스에 `Converter`를 사용하지 않는다.

## 4.10 Config와 Properties

```java
DatabaseConfig
SecurityConfig
CampaignProperties
ObjectStorageProperties
```

- Spring 구성 클래스는 `Config`를 사용한다.
- 외부 설정 바인딩 클래스는 `Properties`를 사용한다.
- 비밀값 자체를 상수 또는 기본값으로 코드에 포함하지 않는다.

## 4.11 Aspect와 Interceptor

```java
TransactionLoggingAspect
ServiceMetricAspect
GuidPropagationInterceptor
```

Aspect는 횡단 관심사를 담당하며 핵심 업무 규칙을 숨겨 구현해서는 안 된다. 적용 대상과 실행 순서를 이름·설정·문서에 명시한다.

## 4.12 Exception

예외 타입은 원인이나 업무 실패를 나타내는 명사에 `Exception`을 붙인다.

```java
CampaignNotFoundException
InvalidConsentException
ExternalServiceException
```

`BusinessException`, `RuntimeException`처럼 지나치게 넓은 타입 하나로 모든 오류를 표현하지 않는다. 공통 기반 예외를 사용하더라도 오류 코드와 원인 예외를 보존한다.

## 4.13 Utility와 Helper

Utility는 무상태·범용 함수에만 사용한다.

```java
GuidUtils       // 기존 표준이 허용한 경우
DateTimeUtils
```

신규 코드에서는 구체적인 책임 이름을 우선한다.

```java
GuidGenerator
BusinessDayCalculator
MessageFormatter
```

`CommonUtil`, `StringHelper`, `DataHelper`와 같이 범위가 불명확한 만능 클래스를 만들지 않는다.

## 4.14 계층별 허용 호출

| 호출 주체 | Controller | Service | DAO | 공통 Service | 타 업무 Service | 타 업무 Controller |
|---|---:|---:|---:|---:|---:|---:|
| Controller | 금지 | 허용 | 금지 | 허용 | 승인된 경계만 허용 | 금지 |
| Service | 금지 | 동일 기능 내부 제한 허용 | 허용 | 허용 | Facade/공식 계약을 통해 허용 | 금지 |
| DAO | 금지 | 금지 | 원칙적 금지 | 금지 | 금지 | 금지 |

타 애플리케이션 그룹 호출은 내부 클래스 직접 참조가 아니라 API, 메시지, 파일 또는 승인된 연계 인터페이스를 사용한다.

## 4.15 제4장 점검표

- [ ] Controller가 요청·응답 조정 역할에 한정되는가?
- [ ] Service가 업무 규칙과 트랜잭션을 담당하는가?
- [ ] DAO가 영속화 책임에 한정되는가?
- [ ] 요청·응답·명령·조회 모델이 필요에 따라 분리되었는가?
- [ ] 외부 시스템 호출이 Client 또는 Adapter로 캡슐화되었는가?
- [ ] `Util`, `Helper`, `Manager` 같은 모호한 이름을 남용하지 않았는가?
- [ ] 타 업무 및 타 애플리케이션 호출이 승인된 경계를 통과하는가?

---

# 다음 작성 범위

다음 본문 묶음에서는 아래 장을 작성한다.

1. 제5장 Service ID·URL·API 네이밍
2. 제6장 DTO·전문·JSON 필드 네이밍
3. 제7장 메서드·변수·상수 상세 규칙
4. 제8장 코딩 형식 및 소스 구성

