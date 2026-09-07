# Java 네이밍 및 코딩 통합 표준서

> 문서 상태: 본문 1차 작성본  
> 작성 범위: 제0장~제8장
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

# 제5장 Service ID·URL·API 네이밍

## 5.1 목적

서비스 식별자와 URL은 호출 경로를 표현하는 동시에 운영 모니터링, 거래 추적, 권한 통제 및 장애 분석의 기준값으로 사용된다. 따라서 화면이나 구현 클래스의 명칭에 종속시키지 않고 업무 의미가 드러나는 안정적인 식별 체계를 적용한다.

## 5.2 기본 원칙

- `[원문]` 서비스 ID는 Controller의 서비스 메서드와 연결되는 거래 식별자이다.
- `[결정]` 서비스 ID는 배포 후 임의로 변경하지 않는 안정적인 계약으로 관리해야 한다(MUST).
- `[결정]` URL 경로는 소문자를 사용하고 경로 구분에는 `/`를 사용해야 한다(MUST).
- `[결정]` URL에 Java 클래스명, 내부 패키지명, 서버 호스트명 또는 구현 기술명을 직접 노출해서는 안 된다(MUST NOT).
- `[보완]` 서비스 ID는 업무 영역, 기능 및 행위를 식별할 수 있도록 구성하는 것을 권고한다(SHOULD).

## 5.3 서비스 ID 구성

서비스 ID는 조직의 애플리케이션 코드 정의를 우선 적용한다. 공통 구조는 다음과 같이 해석한다.

```text
[애플리케이션 그룹][애플리케이션][기능][일련번호 또는 행위]
```

| 구성요소 | 의미 | 예시 |
|---|---|---|
| 애플리케이션 그룹 | 대분류 시스템 또는 플랫폼 | `mp`, `bl` |
| 애플리케이션 | 캠페인, 신용실적 등 업무 구분 | `cm`, `cr` |
| 기능 | 세부 업무 기능 | `co`, `a` |
| 일련번호/행위 | 거래 또는 서비스 구분 | `0001`, `search` |

서비스 ID의 실제 자리 수와 조합은 애플리케이션 코드 표준을 따른다. 코드 정의가 확정되지 않은 프로젝트가 임의의 자리 수를 만들어 사용해서는 안 된다(MUST NOT).

## 5.4 URL 구성

온라인 서비스의 기본 URL은 다음 구조를 사용한다.

```text
https://{domain}/{application-path}/{service-id}
```

예:

```text
https://mp.prod.nacf/cm/MPCM0001
https://cr.prod.nacf/cr/CRCR0001
```

- `{domain}`은 환경별 DNS 및 GSLB 정책을 따른다(MUST).
- `{application-path}`는 배포 단위 또는 업무 애플리케이션을 식별하는 소문자 경로를 사용한다(MUST).
- `{service-id}`는 프레임워크 라우팅 규칙과 서비스 등록 정보가 일치해야 한다(MUST).
- 개발·검증·운영 환경의 차이는 도메인이나 환경 설정으로 분리하고 소스 코드에 운영 URL을 직접 작성하지 않는다(MUST NOT).

## 5.5 HTTP API 네이밍

신규 REST API를 설계하는 경우 다음 원칙을 적용한다.

- 자원명은 명사를 사용하고 복수형을 권고한다(SHOULD).
- 조회·등록·변경·삭제 의미는 가능한 한 HTTP 메서드로 표현한다(SHOULD).
- URL에 `get`, `insert`, `update`, `delete`와 같은 동사를 중복해서 넣지 않는다(SHOULD NOT).
- 하위 자원은 계층 관계가 명확할 때만 중첩한다(SHOULD).
- 경로 변수와 쿼리 매개변수 이름은 `lowerCamelCase`를 사용한다(MUST).

```http
GET    /customers/{customerId}
POST   /campaigns
PATCH  /campaigns/{campaignId}
DELETE /campaigns/{campaignId}
```

기존 프레임워크가 `/업무경로/{서비스코드}` 형태의 단일 진입점을 요구하는 경우에는 해당 계약을 유지한다. REST 규칙을 이유로 기존 라우팅 계약을 일방적으로 변경해서는 안 된다(MUST NOT).

## 5.6 버전과 호환성

- 호환되지 않는 계약 변경은 별도 버전으로 분리해야 한다(MUST).
- 필드 추가처럼 하위 호환 가능한 변경은 소비자 영향도를 확인한 후 기존 버전에 반영할 수 있다(MAY).
- URL 버전 표기는 조직의 API Gateway 정책이 있을 때 이를 우선한다(MUST).
- 서비스 ID를 재사용하여 전혀 다른 업무 의미를 부여해서는 안 된다(MUST NOT).

## 5.7 제5장 점검표

- [ ] 서비스 ID가 업무 코드 체계와 일치하는가?
- [ ] URL에 내부 구현 클래스나 서버 정보가 노출되지 않는가?
- [ ] 환경별 주소가 설정으로 분리되어 있는가?
- [ ] 서비스 ID와 Controller 매핑이 일치하는가?
- [ ] 계약 변경 시 하위 호환성과 버전 정책을 검토했는가?

---

# 제6장 DTO·전문·JSON 필드 네이밍

## 6.1 목적

DTO와 전문은 계층 및 시스템 사이의 데이터 계약이다. 내부 구현 편의를 위해 계약 이름을 변경하거나 하나의 DTO를 모든 계층에서 재사용하면 결합도가 높아지므로, 경계와 책임을 명확히 표현하는 이름을 사용한다.

## 6.2 DTO 클래스명

- DTO 클래스명은 `UpperCamelCase`를 사용해야 한다(MUST).
- 역할이 명확한 접미사를 사용해야 한다(MUST).
- 의미 없는 `Data`, `Info`, `Obj`만으로 역할을 표현하지 않는다(SHOULD NOT).

| 역할 | 권장 접미사 | 예시 |
|---|---|---|
| API 요청 | `Request` | `CampaignCreateRequest` |
| API 응답 | `Response` | `CampaignDetailResponse` |
| 내부 명령 | `Command` | `CampaignCreateCommand` |
| 내부 조회 조건 | `Query` | `CustomerSearchQuery` |
| 조회 결과 | `Result` | `CustomerSearchResult` |
| 외부 전문 입력 | `Input`, `In` | `AccountInquiryInput` |
| 외부 전문 출력 | `Output`, `Out` | `AccountInquiryOutput` |

기존 프레임워크에서 `S0DTOin`, `S0DTOout`, `S0DTOio`와 같은 생성 규칙을 강제하는 경우에는 생성 규칙을 유지한다. 다만 신규 수기 DTO까지 같은 축약형을 무조건 확산하지 않는다(SHOULD NOT).

## 6.3 요청·응답 DTO 분리

- 외부 요청 DTO와 외부 응답 DTO는 분리해야 한다(MUST).
- 영속성 엔티티를 API 요청 또는 응답에 직접 노출해서는 안 된다(MUST NOT).
- Controller DTO와 Service 내부 모델의 책임이 다르면 명시적으로 변환한다(SHOULD).
- 변경 가능성이 큰 외부 전문은 Adapter 또는 Mapper 계층에서 내부 모델로 변환한다(SHOULD).

```java
public record CampaignCreateRequest(
        String campaignName,
        LocalDate startDate,
        LocalDate endDate) {
}

public record CampaignCreateResponse(
        String campaignId,
        String status) {
}
```

## 6.4 JSON 필드명

- 내부 표준 JSON 필드명은 `lowerCamelCase`를 사용해야 한다(MUST).
- Java 필드명과 JSON 필드명이 같으면 별도의 매핑 애너테이션을 사용하지 않는다(SHOULD).
- 외부 계약이 `snake_case` 등 다른 형식을 요구하면 경계 DTO에서만 명시적으로 매핑한다(MUST).
- 약어도 일반 단어처럼 취급한다. 예: `customerId`, `apiUrl`, `guid`.

```java
public record CustomerResponse(
        String customerId,
        String customerName,
        String guid) {
}
```

외부 계약 예:

```java
public record ExternalCustomerResponse(
        @JsonProperty("customer_id") String customerId,
        @JsonProperty("customer_name") String customerName) {
}
```

## 6.5 표준 전문 구조

표준 전문은 공통 헤더와 업무 본문을 논리적으로 구분한다.

```text
StandardMessage
├── header
│   ├── guid
│   ├── serviceId
│   ├── transactionTime
│   └── resultCode
└── body
    └── 업무별 데이터
```

- 공통 추적 항목은 `header`에서 일관되게 관리해야 한다(MUST).
- 업무 데이터는 `body`에 배치해야 한다(MUST).
- 동일 의미의 필드를 시스템마다 다른 이름으로 중복 정의하지 않는다(SHOULD NOT).
- 원천 시스템의 필드명이 내부 표준과 다르면 변환 책임을 인터페이스 경계에 둔다(SHOULD).

## 6.6 컬렉션·선택값·널 처리

- 컬렉션 필드명은 복수형을 사용한다(SHOULD).
- 빈 컬렉션과 `null`의 의미를 계약서에 구분하여 정의해야 한다(MUST).
- 선택 항목은 단순히 `null` 허용으로 끝내지 않고 누락, 빈 값, 기본값의 의미를 정의한다(MUST).
- boolean JSON 필드는 `true`와 `false`의 업무 의미를 문서화한다(SHOULD).

## 6.7 DTO 변환 위치

DTO 변환은 경계 가까이에 둔다.

```text
Controller Request
    -> Request Mapper
    -> Command / Query
    -> Service
    -> Result
    -> Response Mapper
    -> Controller Response
```

Controller에서 대규모 필드 변환 로직을 직접 구현하거나 Service가 UI 전용 응답 DTO에 의존하는 구조는 피한다(SHOULD NOT).

## 6.8 제6장 점검표

- [ ] DTO 이름만으로 입력·출력·명령·조회 역할을 알 수 있는가?
- [ ] 요청 DTO와 응답 DTO가 분리되어 있는가?
- [ ] 엔티티가 외부 계약으로 직접 노출되지 않는가?
- [ ] JSON 필드명이 `lowerCamelCase` 표준을 따르는가?
- [ ] 외부 형식 변환이 경계 계층에 격리되어 있는가?
- [ ] `null`, 빈 문자열, 빈 컬렉션의 의미가 정의되어 있는가?

---

# 제7장 메서드·변수·상수 상세 규칙

## 7.1 메서드명 기본 규칙

- 메서드명은 `lowerCamelCase`를 사용해야 한다(MUST).
- 메서드명은 동사 또는 동사구로 시작해야 한다(MUST).
- 이름은 구현 방법보다 수행 목적과 결과를 표현해야 한다(SHOULD).
- 하나의 메서드가 여러 책임을 수행하는 경우 이름을 길게 만드는 대신 책임을 분리한다(SHOULD).

```java
findCustomerById()
createCampaign()
validateCampaignPeriod()
calculateExpectedRevenue()
```

## 7.2 행위별 동사

| 의미 | 권장 동사 | 사용 기준 |
|---|---|---|
| 단건 조회 | `find`, `get` | 부재 가능은 `find`, 반드시 존재는 `get`을 권고 |
| 목록 검색 | `find`, `search` | 조건 기반 검색 |
| 존재 확인 | `exists`, `has` | boolean 반환 |
| 생성 | `create` | 새로운 업무 객체 생성 |
| 등록 | `register` | 등록 행위 자체가 업무 의미일 때 |
| 저장 | `save` | 신규·변경을 저장소 정책에 따라 처리 |
| 변경 | `update`, `change` | 기존 상태 수정 |
| 삭제 | `delete`, `remove` | 영속 삭제와 컬렉션 제거를 구분 |
| 검증 | `validate` | 실패 시 예외 또는 검증 결과 반환 |
| 변환 | `to`, `from`, `convert`, `map` | 타입 또는 표현 변환 |

`process`, `handle`, `execute`, `doWork`와 같은 포괄적 이름은 실제 책임을 더 구체적으로 표현할 수 없을 때만 사용한다(SHOULD).

## 7.3 boolean 메서드와 변수

boolean은 질문으로 읽히도록 이름을 작성한다.

```java
boolean active;
boolean hasPermission;
boolean isExpired();
boolean canApprove();
boolean shouldRetry();
```

- 상태 값은 형용사 또는 `is` 계열을 사용할 수 있다(MAY).
- 소유·포함 여부는 `has`, 능력은 `can`, 정책 판단은 `should`를 권고한다(SHOULD).
- 부정형 이름은 이중 부정을 유발하므로 피한다(SHOULD NOT). 예: `notInvalid`, `disableNotAllowed`.

## 7.4 변수와 매개변수

- 변수와 매개변수는 `lowerCamelCase`를 사용해야 한다(MUST).
- 타입이 아니라 업무 의미를 표현해야 한다(MUST).
- 한 글자 이름은 짧은 반복문의 인덱스처럼 범위가 매우 좁을 때만 허용한다(MAY).
- `temp`, `data`, `value`, `obj`, `info`처럼 의미가 약한 이름을 단독으로 사용하지 않는다(SHOULD NOT).

```java
String customerId;
Money campaignBudget;
List<Customer> eligibleCustomers;
Map<String, Campaign> campaignsById;
```

## 7.5 컬렉션과 Map

- 컬렉션은 복수 명사를 사용한다(SHOULD).
- Map은 키와 값의 관계가 드러나는 이름을 사용한다(SHOULD).
- 컬렉션 구현 타입을 이름에 포함하지 않는다(SHOULD NOT).

```java
List<Order> pendingOrders;
Set<String> allowedServiceIds;
Map<String, Customer> customersById;
Map<CampaignType, List<Campaign>> campaignsByType;
```

## 7.6 상수

- 상수는 `UPPER_SNAKE_CASE`를 사용해야 한다(MUST).
- `static final`이라고 해서 모든 객체를 상수 형태로 명명하지 않는다. 값의 불변성과 전역적 상수 의미를 함께 고려한다(SHOULD).
- 숫자와 문자열 리터럴에 업무 의미가 있으면 명명된 상수로 추출한다(SHOULD).

```java
private static final int MAX_RETRY_COUNT = 3;
private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);
private static final String DEFAULT_RESULT_CODE = "0000";
```

## 7.7 약어와 숫자

- 약어는 일반 단어와 동일하게 대소문자를 적용한다(MUST).
- 클래스명에서는 `ApiClient`, `GuidGenerator`, `UrlBuilder`를 사용한다.
- 변수명에서는 `apiClient`, `guid`, `urlBuilder`를 사용한다.
- 이름 끝의 숫자는 업무상 버전 또는 정식 코드일 때만 사용한다(SHOULD).
- `data1`, `data2`, `new2`처럼 의미 없는 번호는 사용하지 않는다(MUST NOT).

## 7.8 생명주기와 범위

- 변수는 사용하는 지점과 가장 가까운 위치에서 선언한다(SHOULD).
- 변수의 유효 범위는 가능한 한 작게 유지한다(SHOULD).
- 같은 이름을 중첩 범위에서 재정의하여 가독성을 떨어뜨리지 않는다(SHOULD NOT).
- 필드는 꼭 필요한 상태만 보관하고 계산 가능한 임시 값을 필드로 승격하지 않는다(SHOULD NOT).

## 7.9 제7장 점검표

- [ ] 메서드명이 동사로 시작하고 목적을 표현하는가?
- [ ] boolean 이름이 질문 형태로 자연스럽게 읽히는가?
- [ ] 변수명이 타입보다 업무 의미를 표현하는가?
- [ ] 컬렉션과 Map의 관계가 이름에 드러나는가?
- [ ] 상수가 `UPPER_SNAKE_CASE`를 따르는가?
- [ ] 약어가 일관된 대소문자 규칙을 따르는가?

---

# 제8장 코딩 형식 및 소스 구성

## 8.1 인코딩과 기본 형식

- Java 소스, 설정 파일 및 문서는 UTF-8로 저장해야 한다(MUST).
- 들여쓰기는 공백 4칸을 사용해야 한다(MUST).
- 탭 문자를 들여쓰기에 사용하지 않는다(MUST NOT).
- 한 줄에는 하나의 문장만 작성한다(SHOULD).
- 줄 끝 공백을 남기지 않고 파일 마지막에는 개행을 둔다(MUST).
- 한 줄 길이는 120자 이내를 권고한다(SHOULD).

## 8.2 파일과 최상위 타입

- 하나의 Java 파일에는 하나의 `public` 최상위 타입만 선언해야 한다(MUST).
- 파일명은 `public` 최상위 타입명과 같아야 한다(MUST).
- 관련성이 낮은 여러 클래스를 편의상 한 파일에 모으지 않는다(MUST NOT).
- 작은 전용 타입은 의미가 명확한 경우 중첩 타입으로 둘 수 있다(MAY).

## 8.3 소스 파일 구성 순서

Java 소스는 다음 순서로 구성한다.

1. 라이선스 또는 저작권 주석
2. `package` 선언
3. `import` 선언
4. 최상위 타입 Javadoc
5. 타입 선언
6. 상수
7. 정적 필드
8. 인스턴스 필드
9. 생성자
10. 공개 메서드
11. 보호 메서드
12. 비공개 메서드
13. 중첩 타입

프레임워크가 요구하는 순서가 있으면 팀 규칙으로 일관되게 적용할 수 있다(MAY).

## 8.4 import 규칙

- 와일드카드 import를 사용하지 않는다(MUST NOT).
- 사용하지 않는 import는 제거해야 한다(MUST).
- `java`, `javax` 또는 `jakarta`, 외부 라이브러리, 사내 패키지를 그룹으로 구분하는 것을 권고한다(SHOULD).
- 같은 그룹 안에서는 사전순 정렬을 권고한다(SHOULD).
- 정적 import는 의미가 명확해지는 테스트 코드나 잘 알려진 유틸리티에 제한한다(SHOULD).

## 8.5 중괄호와 줄바꿈

여는 중괄호는 선언 또는 제어문의 같은 줄에 둔다.

```java
if (campaign.isActive()) {
    publishCampaign(campaign);
} else {
    archiveCampaign(campaign);
}
```

- 단일 문장이라도 제어문에 중괄호를 사용해야 한다(MUST).
- `else`, `catch`, `finally`는 앞 블록의 닫는 중괄호와 같은 줄에 둔다(MUST).
- 긴 메서드 호출은 인수 단위로 줄바꿈하고 후속 행을 일관되게 들여쓴다(SHOULD).

```java
Campaign campaign = campaignFactory.create(
        request.campaignName(),
        request.startDate(),
        request.endDate(),
        request.budget());
```

## 8.6 애너테이션

- 클래스와 메서드 애너테이션은 원칙적으로 선언 위에 한 줄씩 작성한다(SHOULD).
- 매개변수나 타입 사용 애너테이션은 대상과 같은 줄에 둘 수 있다(MAY).
- 프레임워크 애너테이션이 과도하게 누적되면 구성 클래스 또는 조합 애너테이션으로 책임을 분리한다(SHOULD).

## 8.7 예외 처리

- 예외를 잡고 아무 처리 없이 무시해서는 안 된다(MUST NOT).
- 복구할 수 없는 예외는 업무 또는 시스템 예외로 변환하되 원인 예외를 보존해야 한다(MUST).
- `Exception` 또는 `Throwable`의 포괄적 처리는 최상위 경계나 프레임워크 공통 처리 영역으로 제한한다(SHOULD).
- 사용자에게 내부 클래스명, SQL, 파일 경로, 스택 추적을 직접 노출하지 않는다(MUST NOT).
- 트랜잭션 경계에서 실패 시 rollback 정책이 명확해야 한다(MUST).

```java
try {
    return campaignRepository.findById(campaignId)
            .orElseThrow(() -> new CampaignNotFoundException(campaignId));
} catch (DataAccessException exception) {
    throw new CampaignSystemException("캠페인 조회에 실패했습니다.", exception);
}
```

## 8.8 로깅

- 표준 로깅 프레임워크를 사용하고 `System.out`과 `System.err`를 사용하지 않는다(MUST NOT).
- 로그 메시지에는 거래 추적이 가능한 GUID 또는 상관 ID를 포함해야 한다(MUST).
- 비밀번호, 인증 토큰, 주민등록번호, 계좌번호 등 민감정보를 원문 그대로 기록하지 않는다(MUST NOT).
- 동일 예외를 여러 계층에서 반복 기록하지 않는다(SHOULD NOT).
- 문자열 결합보다 파라미터 바인딩 방식의 로그를 사용한다(SHOULD).

```java
log.info("campaign created. campaignId={}, guid={}", campaignId, guid);
log.error("campaign creation failed. guid={}", guid, exception);
```

## 8.9 주석과 Javadoc

- 주석은 코드가 무엇을 하는지 반복하기보다 의도, 제약 및 선택 이유를 설명해야 한다(MUST).
- 공개 API와 재사용되는 확장 지점에는 Javadoc을 작성해야 한다(MUST).
- 업무 규칙이 복잡한 메서드에는 입력 조건, 반환 의미, 예외 조건을 기록한다(SHOULD).
- 변경 이력, 작성자 이름, 날짜를 소스 헤더에 누적하지 않는다(SHOULD NOT). 이력은 형상관리 시스템에서 관리한다.
- 주석 처리된 코드를 저장소에 남기지 않는다(MUST NOT).

```java
/**
 * 캠페인을 등록하고 생성된 식별자를 반환한다.
 *
 * @param command 검증이 완료된 캠페인 등록 명령
 * @return 생성된 캠페인 식별자
 * @throws DuplicateCampaignException 동일 기간에 중복 캠페인이 존재하는 경우
 */
public String createCampaign(CampaignCreateCommand command) {
    // 구현
}
```

## 8.10 표준 소스 골격

```java
package com.example.marketing.campaign.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.marketing.campaign.domain.Campaign;
import com.example.marketing.campaign.repository.CampaignRepository;

/**
 * 캠페인 등록 업무를 처리한다.
 */
@Service
public class CampaignService {

    private static final int MAX_RETRY_COUNT = 3;

    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = Objects.requireNonNull(campaignRepository);
    }

    public String createCampaign(CampaignCreateCommand command) {
        Campaign campaign = Campaign.create(command);
        return campaignRepository.save(campaign).getCampaignId();
    }
}
```

## 8.11 자동화 권고

다음 항목은 빌드 또는 CI 단계에서 자동 검증하는 것을 권고한다(SHOULD).

- 소스 인코딩 및 줄 끝 형식
- import 정렬과 미사용 import
- 들여쓰기, 중괄호 및 줄 길이
- 패키지·타입·메서드·상수 명명 규칙
- 금지 API와 `System.out` 사용
- 테스트, 정적 분석 및 취약점 점검

자동 포매터와 정적 분석 도구의 설정 파일은 저장소에서 버전 관리해야 한다(MUST).

## 8.12 제8장 점검표

- [ ] 모든 소스가 UTF-8과 공백 4칸을 사용하는가?
- [ ] 파일명과 `public` 타입명이 일치하는가?
- [ ] 와일드카드 및 미사용 import가 없는가?
- [ ] 단일 문장 제어문에도 중괄호가 있는가?
- [ ] 예외의 원인이 보존되고 민감정보가 노출되지 않는가?
- [ ] 로그에 GUID가 포함되고 민감정보가 마스킹되는가?
- [ ] 주석이 구현 반복이 아니라 의도와 제약을 설명하는가?
- [ ] 포맷과 정적 분석 규칙이 CI에서 검증되는가?

---

# 다음 작성 범위

다음 본문 묶음에서는 아래 장을 작성한다.

1. 제9장 주석 및 Javadoc 상세 표준
2. 제10장 예외·로그·보안 코딩 표준
3. 제11장 계층별 구현 표준
4. 제12장 테스트 코드 표준
