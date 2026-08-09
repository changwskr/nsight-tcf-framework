---
name: MG-NAMING_CONVENTION
description: 농협 상호금융 PDMG 프로젝트 소스 코드 명명 규칙. nhnis.mg 6계층 패키지, Handler/Facade/Controller/Service/DAO/DTO, MyBatis Mapper/SQL ID, 변수·주석 규칙을 정의한다.
---

# PDMG Naming Convention Guide

## 1. 목적

이 문서는 **현재 `pdmg-service` 소스**(`src/main/java/nhnis/mg`)를 기준으로 한 명명 규칙이다.  
새 프로그램은 임의 변형보다 본 규칙과 기준 샘플을 따른다.

기준 프로그램 샘플: **mgcoa8888**  
- 조회 `POST /mgcoa8888S0`  
- 삭제 `POST /mgcoa8888D0`  
- 동일 식별번호 통합: `mgcoa8888Handler` + `mgcoa8888Facade` (TCF ON)  
- TCF OFF 호환: `mgcoa8888Controller`

공통 FW: [`pdmg-fw`](../../pdmg-fw/README.md) (`OnlineTransactionController`, `DefaultFilter`, `RequestBody` resolver)

호출 흐름 (TCF ON, `nhnis.fw.tcf.enabled=true`):

```text
DefaultFilter / ServicePreventionInterceptor (pdmg-fw)
  → OnlineTransactionController (pdmg-fw)
    → TcfFacade → TransactionHandler (nhnis.mg.entry.handler)
      → Facade (@Transactional, nhnis.mg.entry.facade)
        → [BizPrePostAspect] Service (nhnis.mg.application.service)
          → DAO (nhnis.mg.persistence.dao)
            → Mapper XML (rdw.mg.co.a/*.xml)
```

호출 흐름 (TCF OFF, 호환):

```text
ServicePreventionInterceptor (pdmg-fw)
  → Controller (nhnis.mg.entry.controller)
    → Service (nhnis.mg.application.service)
      → DAO → Mapper XML
```

---

## 2. 애플리케이션 분류 체계

서비스 ID·클래스명·Mapper **리소스 폴더**에 쓰인다.  
Java 패키지 경로에는 업무/세부업무 세그먼트를 넣지 않는다.

### 2.1 대구분

- 애플리케이션 그룹 코드: `MG`
- Boot / 루트 패키지: `nhnis.mg`
- 메인 클래스: `nhnis.mg.PdmgApplication`

### 2.2 업무구분

| 코드 | 업무구분 |
| ---- | -------- |
| CO | 공통 |
| IC | 통합고객 |
| MS | 미니 상품부 |
| SA | 사업관리 |

### 2.3 세부업무구분

| 코드 | 세부구분 |
| ---- | -------- |
| A | 상담 |
| B | 고객 |

현재 샘플은 모두 `CO` + `A` → 서비스 ID 접두 `mgcoa`.

---

## 3. 서비스 ID 규칙

### 3.1 기본 구조

```text
[대구분 2자][업무구분 2자][세부업무구분 1자][식별번호 4자][구분자 1자][순번 1자]
```

예시:

```text
mgcoa8888S0
```

### 3.2 구성요소

| 구분 | 길이 | 예시 | 설명 |
| ---- | ---: | ---- | ---- |
| 대구분 | 2 | mg | 애플리케이션 그룹 |
| 업무구분 | 2 | co | CO, IC, MS, SA |
| 세부업무구분 | 1 | a | A, B |
| 식별번호 | 4 | 8888 | 화면번호 또는 일반번호 |
| 구분자 | 1 | S/C/U/D/A/R | 조회/등록/수정/삭제/혼합/리포트 |
| 순번 | 1 | 0~9, A~Z | 동일 기능 내 순번 |

### 3.3 구분자

| 구분자 | 의미 |
| ------ | ---- |
| S | 조회 |
| C | 등록 |
| U | 수정 |
| D | 삭제 |
| A | 혼합 |
| R | 리포트 |

### 3.4 예시

```text
조회   : mgcoa8888S0
삭제   : mgcoa8888D0
등록   : mgcoa0000C0
수정   : mgcoa0000U0
혼합   : mgcoa0000A0
리포트 : mgcoa0000R0
```

현재 레포 샘플:

| 프로그램 | API | 설명 |
| -------- | --- | ---- |
| `mgcoa8888` | `POST /mgcoa8888S0`, `POST /mgcoa8888D0` | 이미지로그 조회/삭제 |
| `mgcoa5530` | `POST /mgcoa5530S0` | 마케팅희망고객 목록 |
| `mgcoa9999` | `POST /mgcoa9999S0` | 영업팁 실적 목록 |

---

## 4. Java 패키지 구조 (6계층)

루트는 `nhnis.mg` 이다. 그 아래를 **계층(layer)** 으로 나눈다.

```text
src/main/java/nhnis/mg
├── PdmgApplication.java
├── ServletInitializer.java
├── entry/
│   ├── aspect/              # BizPrePostAspect
│   ├── handler/             # mgcoa*Handler (TCF ON)
│   ├── facade/              # mgcoa*Facade (TCF ON, @Transactional)
│   └── controller/          # mgcoa*Controller (TCF OFF 호환)
├── application/
│   ├── service/             # mgcoa*Service
│   └── dto/                 # mgcoa*DTOin/out/Sub(/MsgJson)
├── persistence/
│   └── dao/                 # mgcoa*DAO (@RDWMapper)
├── client/                  # 외부 연동 (현재 package-info만)
├── config/                  # Security, MyBatis, CORS, WebMvc …
└── support/                 # MappingUtil 등 유틸
```

| 계층 | 패키지 | 역할 | 현재 소스 예 |
| ---- | ------ | ---- | ------------ |
| entry | `nhnis.mg.entry.handler` | serviceId → Facade 라우팅 | `mgcoa8888Handler` |
| entry | `nhnis.mg.entry.facade` | DTO 변환·트랜잭션·Service 호출 | `mgcoa8888Facade` |
| entry | `nhnis.mg.entry.controller` | HTTP 진입 (TCF OFF) | `mgcoa8888Controller` |
| entry | `nhnis.mg.entry.aspect` | 업무 선/후처리 Aspect | `BizPrePostAspect` |
| application | `nhnis.mg.application.service` | 업무 절차 | `mgcoa8888Service` |
| application | `nhnis.mg.application.dto` | 입출력 DTO | `mgcoa8888S0DTOin` |
| persistence | `nhnis.mg.persistence.dao` | MyBatis DAO | `mgcoa8888DAO` |
| client | `nhnis.mg.client` | 외부 WAS/API 호출 | `package-info.java` |
| config | `nhnis.mg.config` | Spring 설정 | `RdwDataSourceConfig`, `SecurityConfig` |
| support | `nhnis.mg.support` | 유틸 | `MappingUtil` |

원칙:

- Java 패키지에 `co.a` 같은 업무 세그먼트를 **넣지 않는다**.
- 업무/세부업무는 **서비스 ID·클래스명·`rdw.mg.co.a` 리소스 경로**에 둔다.
- REST 스타일(`/api/.../list`, `DtoIn`/`Dao`)은 사용하지 않는다.
- `serviceId`는 `mgcoa8888S0` 형태를 쓴다. `MG.Xxx.yyy` 점(.) 구분 체계는 사용하지 않는다.
- TCF ON 신규 거래는 **Handler + Facade** 를 추가한다. Controller는 TCF OFF 호환용이다.

---

## 5. Handler (`entry.handler`)

TCF ON 시 FW `OnlineTransactionController` → `TcfFacade` 가 `TransactionHandler` 구현체를 찾아 호출한다.  
Handler는 **serviceId 라우팅만** 담당하고, 업무·트랜잭션·DTO 변환은 Facade에 둔다.

### 5.1 파일명 / 패키지

```text
패키지 : nhnis.mg.entry.handler
파일명 : [대구분][업무구분][세부업무구분][식별번호4자리]Handler.java
```

예: `mgcoa8888Handler.java`, `mgcoa5530Handler.java`

클래스명에는 구분자(S/C/U/D…)를 넣지 않는다.  
동일 식별번호의 여러 서비스는 **하나의 Handler**로 통합한다.

```text
mgcoa8888S0 + mgcoa8888D0  →  mgcoa8888Handler
```

### 5.2 애노테이션 / 구현

```java
@Slf4j
@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class mgcoa8888Handler implements TransactionHandler {
```

| 항목 | 규칙 |
| ---- | ---- |
| 인터페이스 | `nhnis.fw.tcf.core.handler.TransactionHandler` |
| Bean 조건 | `nhnis.fw.tcf.enabled=true` 일 때만 등록 |
| 의존 | 동일 식별번호의 `*Facade` 만 주입 |
| `serviceIds()` | 지원 서비스 ID 목록 반환 |
| `handle(...)` | `context.getServiceId()` 로 Facade 메서드 분기 |

### 5.3 메서드 패턴

```java
@Override
public Collection<String> serviceIds() {
    return List.of(S0, D0);
}

@Override
public Object handle(Object dtoBody, TransactionContext context) throws Exception {
    return switch (context.getServiceId()) {
        case S0 -> facade.mgcoa8888S0(dtoBody);
        case D0 -> facade.mgcoa8888D0(dtoBody);
        default -> throw new ServiceHandlerNotFound(
                "mgcoa8888Handler 미지원 serviceId: " + context.getServiceId());
    };
}
```

금지:

- Handler에서 `@Transactional` 사용
- Handler에서 Service/DAO 직접 호출
- Handler에서 DTO 타입 변환 (`ObjectMapper` 등)

---

## 6. Facade (`entry.facade`)

Handler와 Service 사이의 **업무 진입 경계**.  
입력 `Object`(또는 Map) → typed DTO 변환, Spring 트랜잭션, Service 호출을 담당한다.

### 6.1 파일명 / 패키지

```text
패키지 : nhnis.mg.entry.facade
파일명 : [대구분][업무구분][세부업무구분][식별번호4자리]Facade.java
```

예: `mgcoa8888Facade.java`

클래스명에는 구분자(S/C/U/D…)를 넣지 않는다.  
동일 식별번호의 여러 서비스는 **하나의 Facade**로 통합한다.

```text
mgcoa8888S0 + mgcoa8888D0  →  mgcoa8888Facade
```

### 6.2 애노테이션 / 메서드

```java
@Slf4j
@Service
public class mgcoa8888Facade {
```

| 항목 | 규칙 |
| ---- | ---- |
| Spring 스테레오타입 | `@Service` (Facade 역할이지만 Bean은 Service로 등록) |
| 트랜잭션 | 메서드에 `@Transactional(transactionManager = "rdwTransactionManager")` |
| 조회 | `readOnly = true` |
| 메서드명 | **서비스 ID와 동일** (`mgcoa8888S0`, `mgcoa8888D0`) |
| 입력 | `Object dtoBody` → `ObjectMapper.convertValue` → `*DTOin` |
| 출력 | Service가 반환한 `*DTOout` |
| 의존 | 동일 식별번호의 `*Service` + `ObjectMapper` |

### 6.3 메서드 시그니처

```java
@Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
public mgcoa8888S0DTOout mgcoa8888S0(Object dtoBody) throws Exception {
    Object source = dtoBody == null ? Collections.emptyMap() : dtoBody;
    mgcoa8888S0DTOin input = objectMapper.convertValue(source, mgcoa8888S0DTOin.class);
    return service.mgcoa8888S0(input);
}
```

책임 경계:

| 계층 | 담당 | 비담당 |
| ---- | ---- | ------ |
| Handler | serviceId 분기 | TX, DTO 변환, 업무 로직 |
| Facade | TX, DTO 변환, Service 호출 | SQL, HTTP |
| Service | 업무 절차, DAO 호출 | `@Transactional` (Facade에 둠) |

업무 선·후처리(`BizPrePostAspect`)는 **Service 메서드**에 걸린다.  
따라서 `Facade(@Transactional BEGIN) → [BizPre] Service → DAO → [BizPost] → COMMIT` 순서가 된다.

---

## 7. Controller (`entry.controller`)

TCF OFF(`nhnis.fw.tcf.enabled=false`) 또는 호환용 HTTP 진입점.  
TCF ON에서는 보통 `@ConditionalOnProperty(..., havingValue = "false", matchIfMissing = true)` 로 비활성한다.  
**신규 거래는 Handler + Facade를 우선**하고, Controller는 필요 시에만 유지한다.

### 7.1 파일명 / 패키지

```text
패키지 : nhnis.mg.entry.controller
파일명 : [대구분][업무구분][세부업무구분][식별번호4자리]Controller.java
```

예: `mgcoa8888Controller.java`

클래스명에는 구분자(S/C/U/D…)를 넣지 않는다.  
동일 식별번호의 여러 서비스는 **하나의 Controller**로 통합한다.

```text
mgcoa8888S0 + mgcoa8888D0  →  mgcoa8888Controller
```

### 7.2 애노테이션

```java
@Slf4j
@RestController
```

- 클래스 레벨 `@RequestMapping` **금지**
- 메서드: `@PostMapping("/서비스ID")`
- 입력: `nhnis.fw.commons.resolver.RequestBody`  
  (요청 JSON의 `dto` 노드 바인딩)

### 7.3 메서드 시그니처

```java
@PostMapping("/mgcoa8888S0")
public mgcoa8888S0DTOout mgcoa8888S0(
        @RequestBody mgcoa8888S0DTOin input
) throws Throwable
```

요청 Body 예: `{"hdr_nhnis":{...},"dto":{...}}`  
local 프로파일에서는 `{"dto":{...}}` 만으로도 가능하다.

---

## 8. Service (`application.service`)

### 8.1 파일명 / 패키지

```text
패키지 : nhnis.mg.application.service
파일명 : [대구분][업무구분][세부업무구분][식별번호4자리]Service.java
```

예: `mgcoa8888Service.java`

### 8.2 애노테이션 / 메서드

```java
@Service
public class mgcoa8888Service {
    public mgcoa8888S0DTOout mgcoa8888S0(mgcoa8888S0DTOin input) throws Exception { … }
    public mgcoa8888D0DTOout mgcoa8888D0(mgcoa8888D0DTOin input) throws Exception { … }
}
```

- 메서드명 = 서비스 ID
- DAO·DTO는 각각 `persistence.dao`, `application.dto` 를 import
- `@Transactional`은 Service가 아니라 **Facade**에 둔다 (TCF ON)

---

## 9. DAO (`persistence.dao`)

### 9.1 파일명 / 패키지

```text
패키지 : nhnis.mg.persistence.dao
파일명 : [대구분][업무구분][세부업무구분][식별번호4자리]DAO.java
```

예: `mgcoa8888DAO.java`

### 9.2 규칙

- `@RDWMapper` (`nhnis.mg.config.RDWMapper`) 사용
- **DAO 메서드명 = MyBatis SQL ID** (반드시 동일)
- 입력: `Map<String, Object>`
- 출력: `List<Map<String, Object>>` 또는 `int`
- `@MapperScan(basePackages = "nhnis.mg.persistence.dao")`

### 9.3 메서드명 / SQL ID

```text
[서비스ID]_[DML구분][순번]
[서비스ID]_[DML구분][순번]_count
```

예 (`mgcoa8888DAO`):

```text
mgcoa8888S0_S0
mgcoa8888S0_S0_count
mgcoa8888D0_D0
```

DML 구분: `S` 조회 / `C` 등록 / `U` 수정 / `D` 삭제 / `A` 혼합  
순번: `0~9`, 초과 시 `A~Z`

---

## 10. DTO (`application.dto`)

### 10.1 파일명

```text
[서비스ID]DTOin.java
[서비스ID]DTOout.java
[서비스ID]DTOSub[순번].java
[서비스ID]DTO*MsgJson.java   (필요 시)
```

패키지: `nhnis.mg.application.dto`

예:

```text
mgcoa8888S0DTOin.java
mgcoa8888S0DTOout.java
mgcoa8888S0DTOSub0.java
mgcoa8888D0DTOin.java
mgcoa8888D0DTOout.java
```

### 10.2 기본 요건

- `com.ims.superspring.dto.DataObject` 상속
- Getter/Setter, `clone`, `toString`, `getFieldPropertyMap` 구현
- 필드명: camelCase (`guid`, `pageNo`, `totalCount` …)

### 10.3 Sub DTO

GRID/목록 행용. 메인 out DTO가 Sub 목록을 보유한다.

| 세그먼트 | 예시 |
| -------- | ---- |
| 서비스 ID | `mgcoa8888S0` |
| 고정 | `DTOSub` |
| 순번 | `0` |

예: `mgcoa8888S0DTOSub0`, `mgcoa5530S0DTOSub0`, `mgcoa9999S0DTOSub0`

---

## 11. entry.aspect / config / support / client

### 11.1 entry.aspect (`nhnis.mg.entry.aspect`)

| 클래스 | 역할 |
| ------ | ---- |
| `BizPrePostAspect` | Service 선/후처리 로그. pointcut: `nhnis.mg.application.service..*` |

Facade `@Transactional` 안쪽에서 Service 호출 전후에 적용되도록 **application.service** 를 대상으로 한다.

### 11.2 config (`nhnis.mg.config`)

| 클래스 | 역할 |
| ------ | ---- |
| `RdwDataSourceConfig` | RDW DataSource, MyBatis, `@MapperScan` |
| `RDWMapper` | DAO용 Mapper 애노테이션 |
| `MybatisLogInterceptor` | SQL 로깅 |
| `SecurityConfig` | 무상태 보안 |
| `WebMvcConfig` / `CorsProperties` | MVC·CORS |

### 11.3 support (`nhnis.mg.support`)

| 클래스 | 역할 |
| ------ | ---- |
| `MappingUtil` | Map ↔ 객체 매핑 유틸 |

### 11.4 client (`nhnis.mg.client`)

외부 시스템 호출용. 현재 샘플 연동 없음 → `package-info.java`만 존재.  
연동 추가 시 이 패키지에 클라이언트를 둔다.

---

## 12. MyBatis Mapper 리소스

### 12.1 위치

리소스 폴더는 **서비스 ID의 대구분·업무·세부업무**를 점(`.`)으로 연결한다.  
Java DAO 패키지와 물리 폴더 경로는 **일치하지 않아도 된다**.

```text
src/main/resources/rdw.[대구분].[업무구분].[세부업무구분]/
```

현재 샘플:

```text
src/main/resources/rdw.mg.co.a/
  mgcoa8888-ORA.xml
  mgcoa5530-ORA.xml
  mgcoa9999-ORA.xml
```

스캔 패턴: `classpath*:rdw.*/*.xml`

### 12.2 파일명

```text
[대구분][업무구분][세부업무구분][식별번호4자리]-[DB구분].xml
```

| 코드 | DB |
| ---- | -- |
| ORA | Oracle (로컬 H2 Oracle 모드 포함) |
| MYS | MySQL |
| MSS | MS SQL Server |

### 12.3 namespace

Java DAO FQCN과 일치:

```xml
<mapper namespace="nhnis.mg.persistence.dao.mgcoa8888DAO">
```

### 12.4 parameterType / resultType

조회·동적 처리 시 `java.util.HashMap` 사용.  
SQL 본문에 SQL ID 주석 포함 (`/* mgcoa8888S0_S0 */`).

---

## 13. SQL 작성 표준

- Oracle SQL 문법 기준 (로컬은 H2 `MODE=Oracle`)
- DB 키워드·Object: 대문자 / 바인딩 변수: 소문자 camelCase
- 테이블 Alias: `T1`, `T2`, `T3` / 컬럼 Alias: `AS` 사용
- Static SQL 원칙, 필요 시 Dynamic SQL
- 부정 비교: `<>`
- 성능: 긍정 조건 우선, `OR` 제한, `UNION ALL` 검토, `LIKE 'AB%'` 등

---

## 14. 변수·주석

### 14.1 Java 변수

- camelCase, 의미 있는 이름 (`guid`, `serviceId`, `totalCount`, `pageNo`)

### 14.2 주석

클래스:

```java
/**
 * 이미지로그 조회/삭제 Controller.
 *
 * @since YYYY.MM.DD
 */
```

날짜 형식: `YYYY.MM.DD`

---

## 15. 코드 생성 워크플로우

1. 대구분 / 업무구분 / 세부업무구분 / 기능(S·C·U·D·A·R) / 식별번호 결정  
2. 서비스 ID 생성 → 예: `mgcoa8888S0`  
3. 파일 생성 (TCF ON 기본):

```text
entry/handler/mgcoa8888Handler.java
entry/facade/mgcoa8888Facade.java
application/service/mgcoa8888Service.java
application/dto/mgcoa8888S0DTOin.java
application/dto/mgcoa8888S0DTOout.java
application/dto/mgcoa8888S0DTOSub0.java   (목록 시)
persistence/dao/mgcoa8888DAO.java
resources/rdw.mg.co.a/mgcoa8888-ORA.xml
```

TCF OFF 호환이 필요하면 `entry/controller/mgcoa8888Controller.java` 도 추가한다.

4. 검증:

- 계층 패키지 (`entry.handler` / `entry.facade` / `application` / `persistence` …)
- Handler `serviceIds()` · Facade/Service 메서드명 = 서비스 ID
- DAO 메서드명 = SQL ID
- Mapper `namespace` = `nhnis.mg.persistence.dao.*DAO`
- 리소스 경로 `rdw.mg.co.a/` (업무 세그먼트 기준)
- `@Transactional` 은 Facade, BizPrePost 는 Service

---

## 16. 핵심 체크리스트

| 점검항목 | 기준 |
| -------- | ---- |
| 패키지 | `nhnis.mg` + 6계층 |
| 서비스 ID | `mg` + 업무 + 세부 + 식별번호 + 구분자 + 순번 |
| Handler | `entry.handler`, 식별번호 단위, `TransactionHandler`, serviceId 라우팅만 |
| Facade | `entry.facade`, 식별번호 단위, `@Transactional`, 메서드명 = 서비스 ID |
| Controller | `entry.controller` (TCF OFF 호환), 클래스 `@RequestMapping` 금지 |
| Service | `application.service`, 메서드명 = 서비스 ID, TX는 Facade |
| DTO | `application.dto`, `DataObject`, `DTOin`/`DTOout`/`DTOSub` |
| DAO | `persistence.dao`, `@RDWMapper`, 메서드명 = SQL ID |
| Mapper XML | `rdw.mg.co.a/[식별]-ORA.xml`, namespace = persistence DAO |
| URL | `POST /[서비스ID]` (TCF: FW `OnlineTransactionController`) |
| Aspect | `entry.aspect.BizPrePostAspect` → `application.service` |
| client | 외부 연동 시에만 추가 |
| REST/`MG.Xxx.yyy` | 사용하지 않음 |

---

## 참고

- 본 문서는 `src/main/java/nhnis/mg` 실제 구조에 맞춰 작성한다.
- 공통 FW 클래스명 `PdmgTxLog`, 모듈명 `pdmg-fw` 는 FW 공유 식별자로 유지한다.
- meta `.dto` 파일은 현재 사용하지 않는다.
