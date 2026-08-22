# 07. NSIGHT Application Architecture — G40 Source Conformance

> Gate: **G40 — Mechanism / Source Conformance**  
> 기준: `nsight-tcf-framework (2).zip`의 실제 Source Snapshot과 기존 NSIGHT Architecture Baseline을 대조한다.  
> 원칙: **PDMG AS-IS와 NSIGHT TCF TO-BE를 동일 구현으로 취급하지 않는다.**


## 1. 목적

본 문서는 G20에서 정의한 `ServiceId → Handler → Facade → Service → Rule/DAO → Mapper → DB` 논리 구조를 실제 Source Snapshot에 대조하여, 현재 구현된 계층·트랜잭션 경계·라우팅 규칙의 적합성을 판정한다.

## 2. Source Scope

| Scope | 기준 | 상태 |
|---|---|---|
| PDMG | `pdmg-fw`, `pdmg-service` 실제 Source | `[AS-IS]` |
| NSIGHT TCF | `tcf-core`, `tcf-web` 실제 Source | `[TO-BE IMPLEMENTATION]` |
| NSIGHT 업무예시 | `sv-service` 등 `com.nh.nsight.*` 업무 Source | `[TO-BE IMPLEMENTATION]` |
| Generated/Reference | `build/`, `bin/`, `tcf-ai-methodology/.../sample-generated` | Current Conformance에서 제외 |

## 3. 계층 실행구조 비교

```text
PDMG AS-IS
HTTP
 ↓
DefaultFilter
 ↓
ServicePreventionInterceptor
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
STF
 ↓
OnlineTimeoutExecutor
 ↓
TransactionDispatcher
 ↓
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO / Mapper / DB
 ↓
ETF

NSIGHT TCF TO-BE
HTTP / Alternative Entry
 ↓
OnlineTransactionController / TcfGateway
 ↓
TCF
 ↓
STF
 ↓
OnlineTransactionTimeoutExecutor
 ↓
TransactionDispatcher
 ↓
TransactionHandler
 ↓
Facade  ← Transaction Boundary
 ↓
Service
 ↓
Rule / DAO / Integration
 ↓
ETF
```

## 4. Source에서 직접 확인된 계층

### 4.1 PDMG Handler → Facade

`mgcoa9000Handler`는 4개의 ServiceId를 선언하고 `context.getServiceId()`에 따라 Facade 메서드로 라우팅한다.

- `mgcoa9000S0`
- `mgcoa9000C0`
- `mgcoa9000U0`
- `mgcoa9000D0`

근거: `pdmg-service/src/main/java/nhnis/mg/co/a/entry/handler/mgcoa9000Handler.java:21-46`

### 4.2 PDMG Facade → Service

`mgcoa9000Facade`는 입력 `Object`를 Typed DTO로 변환한 뒤 Service를 호출하며, Facade 메서드에 `@Transactional`이 존재한다.

근거: `pdmg-service/.../application/facade/mgcoa9000Facade.java:34-56`

### 4.3 PDMG Service → DAO

`mgcoa9000Service`는 Validation/페이징/업무상태 확인 후 DAO를 호출한다. 같은 Service 메서드에도 `@Transactional`이 중복 선언되어 있다.

근거: `pdmg-service/.../application/service/mgcoa9000Service.java`

### 4.4 NSIGHT TCF 업무 예시

`SvCustomerHandler`는 `SV.Customer.selectSummary`를 선언하고 `SvCustomerFacade`로 위임한다. `SvCustomerFacade`가 `@Transactional(readOnly=true, timeout=3)`을 갖고, `SvCustomerService`는 Rule과 DAO를 조합하지만 Transaction Annotation은 없다.

근거:

- `sv-service/.../entry/handler/SvCustomerHandler.java`
- `sv-service/.../entry/facade/SvCustomerFacade.java`
- `sv-service/.../application/service/SvCustomerService.java`

## 5. Dispatcher Conformance

PDMG와 NSIGHT TCF Dispatcher 모두 시작 시 `serviceId → TransactionHandler` Map을 구성하며, 동일 ServiceId가 두 번 등록되면 `IllegalStateException`으로 기동을 실패시킨다.

| 검사 | PDMG | NSIGHT TCF |
|---|---|---|
| ServiceId 필수 | O | O |
| Handler 미등록 오류 | O | O |
| Duplicate ServiceId Fail-Fast | O | O |
| Handler Map 조회 가능 | O | O |

근거:

- `pdmg-fw/.../TransactionDispatcher.java:31-49,55-80`
- `tcf-core/.../TransactionDispatcher.java:22-45,47-68`

### Source Scan 결과

| 항목 | 결과 |
|---|---:|
| PDMG Handler 파일 | **6** |
| PDMG 추출 ServiceId | **13** |
| PDMG 중복 ServiceId | **0** |
| NSIGHT `com.nh.nsight.*` Handler 파일 | **69** |
| 추출된 NSIGHT ServiceId 후보 | **121** |

현재 스캔에서 PDMG ServiceId 중복은 발견되지 않았다.

NSIGHT 전체 저장소 후보에서는 `OM.Sample.inquiry`가 `om-service`와 `tcf-om`에 각각 존재한다. 두 모듈이 동일 Runtime Classpath에 함께 들어갈 경우 Dispatcher가 기동 실패하도록 설계되어 있으므로, 이것은 **Runtime 오류라기보다 Build/Deployment Scope를 확정해야 하는 Source Baseline Drift**로 관리한다.

`SV.Customer.selectSummary`의 추가 중복은 `tcf-ai-methodology/.../sample-generated` 예제에서 발견되므로 Current Source 후보에서는 제외한다.

## 6. Transaction Annotation 분포

Source Scan 결과 `com.nh.nsight.*` 계열에서 `@Transactional`이 포함된 파일은 다음 경향을 보인다.

| 위치 | 파일 수 | 판정 |
|---|---:|---|
| `entry/facade` | **53** | 목표 경계와 대체로 일치 |
| `application/service` | **4** | 예외/Drift 점검 필요 |

Service 계층 Transaction Annotation 발견 파일:
- `eb-service/src/main/java/com/nh/nsight/marketing/eb/application/service/EbUserService.java`
- `ep-service/src/main/java/com/nh/nsight/marketing/ep/application/service/EpUserEventService.java`
- `tcf-oc/src/main/java/com/nh/nsight/marketing/oc/capnew/application/service/CapNewApprovalService.java`
- `tcf-oc/src/main/java/com/nh/nsight/marketing/oc/capnew/application/service/CapNewWizardService.java`


따라서 `[TO-BE] Facade = Use Case Transaction Boundary` 원칙은 상당히 구현되어 있으나 아직 전수 일치하지 않는다.

## 7. 업무 선후처리

PDMG `BizPrePostAspect`는 Service 메서드를 Pointcut으로 하며 다음 순서를 명시한다.

```text
Handler
 → Facade(@Transactional)
 → Biz Pre
 → Service
 → Biz Post
```

근거: `pdmg-service/.../entry/aspect/BizPrePostAspect.java:36-65`

이 구조는 업무 공통처리의 위치를 Service 실행 경계로 분리한다는 점에서 유지 가능한 Reference다. 단, NSIGHT TO-BE에서는 공통 Logging/Audit/Metric 중 Framework 책임과 업무 책임을 분리해야 한다.

## 8. AS-IS → TO-BE 핵심 차이

| 항목 | PDMG AS-IS | NSIGHT TCF TO-BE | 판정 |
|---|---|---|---|
| ServiceId 형식 | `mgcoa9000S0` | `SV.Customer.selectSummary` 등 | `[OPEN] Naming/Mapping` |
| Dispatcher | Map 기반 | Map 기반 | `[CONFIRMED]` |
| Handler 책임 | ServiceId→Facade | 동일 | `[CONFIRMED]` |
| Facade | DTO 변환 + TX | Use Case + TX | `[CONFIRMED]` |
| Service | 업무 + DAO, 일부 TX 중복 | Rule/DAO 조합 | `[GAP]` 일부 Service TX |
| Rule | AS-IS 독립성 낮음 | 업무모듈에 명시적 Rule 존재 | `[TO-BE]` |

## 9. Architecture Rules 후보

- `R-APP-001` ServiceId는 Runtime Scope에서 유일해야 한다.
- `R-APP-002` Dispatcher는 미등록/중복 ServiceId를 Fail-Fast 해야 한다.
- `R-APP-003` Handler는 업무 로직을 직접 구현하지 않고 Facade로 라우팅한다.
- `R-APP-004` Facade를 Use Case/Transaction 경계의 기본 위치로 한다.
- `R-APP-005` Service Transaction Annotation은 명시적 예외사유가 없으면 금지 후보로 검토한다.
- `R-APP-006` Service는 다른 Domain DAO/Mapper/Table을 직접 호출하지 않는다.
- `R-APP-007` Rule은 DB 접근을 소유하지 않는다.

## 10. G40 판정에 미치는 조건

1. `om-service`와 `tcf-om`의 Build/Deployment Scope를 확정해야 한다.
2. Service 계층 `@Transactional` 4개 파일의 의도를 검토해야 한다.
3. PDMG ServiceId → NSIGHT ServiceId Migration/Mapping 정책을 정의해야 한다.
4. Handler/Facade/Service/DAO/Mapper 전수 Traceability는 G80 이전까지 자동화해야 한다.
