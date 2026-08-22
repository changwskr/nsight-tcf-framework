# CHG-TX-001 — Facade Transaction Boundary Cleanup Specification

> 상태: **CHANGE SPEC READY / IMPLEMENTATION NOT EXECUTED**  
> P0 연계: `P0-TX-001`, `ADR-TX-001`

## 1. Target Rule

표준 온라인 업무의 기본 Transaction Owner는 **Facade / Use Case Boundary**다.

```text
Handler
  ↓
Facade @Transactional   ← Owner
  ↓
Service                 ← 기본 REQUIRED TX 선언 없음
  ↓
Rule / DAO / Mapper
```

Service-level Transaction은 `REQUIRES_NEW`, 독립 Commit 단위, 별도 Outbox Boundary처럼 **의도가 명확한 예외**에서만 허용한다.

## 2. 실제 중복 후보

### EB

```text
EbUserFacade.create()
  @Transactional(timeout = 5)
        ↓
EbUserService.create()
  @Transactional(timeout = 5)   ← 제거 후보
```

현재 Source 검색상 `EbUserService.create(request, context)` 호출자는 `EbUserFacade.create()` 한 곳이다.

### EP

```text
EpUserEventFacade.receive()
  @Transactional(timeout = 5)
        ↓
EpUserEventService.receive()
  @Transactional(timeout = 5)   ← 제거 후보
```

현재 Source 검색상 `EpUserEventService.receive(request, context)` 호출자는 `EpUserEventFacade.receive()` 한 곳이다.

따라서 현재 Source 범위에서는 두 Service의 Transaction 선언을 제거해도 **표준 진입 경로의 TX Boundary는 Facade에 남는다.**

## 3. 코드 변경

### 3.1 `EbUserService.java`

삭제:

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = 5)
public UserCreateResponse create(...) { ... }
```

변경 후:

```java
public UserCreateResponse create(...) { ... }
```

### 3.2 `EpUserEventService.java`

동일하게 Service-level `@Transactional(timeout=5)` 및 사용되지 않는 import를 제거한다.

### 3.3 Facade는 유지

```java
@Transactional(timeout = 5)
public Map<String, Object> create(...)

@Transactional(timeout = 5)
public Map<String, Object> receive(...)
```

단, 장기적으로 Timeout은 정책 기반 `PolicyDrivenTransactionAttributeSource`가 적용되므로 Annotation의 숫자 5는 fallback인지 표준값인지 별도 정리한다. 이번 Change의 범위는 **Owner 중복 제거**다.

## 4. Behavior Check

변경 전후 다음 의미는 유지되어야 한다.

### EB create

```text
User Insert
  +
Outbox Event Insert
  = same transaction
```

User Insert 후 Event Insert 실패 시 두 변경 모두 Rollback되어야 한다.

### EP receive

```text
Duplicate Check
  ↓
Received Event Insert
```

예외 발생 시 Insert가 Commit되지 않아야 한다.

## 5. Exception Rule

향후 Service Transaction이 필요한 경우 다음 metadata를 요구한다.

```text
Exception ID
Service / Method
Propagation
Reason
Owner
Rollback Semantics
Test
ADR / Rule Exception
Expiry / Review Date
```

단순히 "안전하게 하려고" Service에 `@Transactional`을 추가하는 것은 금지한다.

## 6. Conformance Rule

권장 Rule:

```text
R2-TX-OWNER-001
Standard online transaction owner = Facade
```

정적검증:

- `*/entry/facade/*Facade`의 변경성 Use Case에는 TX Boundary 존재
- `*/application/service/*Service`의 `@Transactional`은 allowlist 없으면 FAIL/WARN
- `REQUIRES_NEW`는 반드시 Exception Registry에 존재

## 7. Test Specification

### Static

- EB/EP Service `@Transactional` 0건
- EB/EP Facade 해당 Method `@Transactional` 유지
- Service 직접 호출자 신규 생성 여부 0건

### Integration

- EB User insert 후 Event insert 강제실패 → User/Event 모두 Rollback
- EP insert 강제실패 → row 0
- Policy-driven TX Timeout이 Facade Boundary에 적용

## 8. Risk

Service가 테스트/배치/내부 코드에서 Facade를 우회해 직접 호출되면 Transaction이 사라질 수 있다. 현재 Source 검색에서는 대상 Method의 직접 호출자가 Facade 1곳씩이지만, 실제 변경 후 전체 프로젝트 재검색 및 테스트로 확인해야 한다.

## 9. Acceptance Criteria

- [ ] EB/EP 중복 Service TX 제거
- [ ] Facade TX 유지
- [ ] Direct Service bypass caller 0 또는 승인예외
- [ ] EB Outbox atomic rollback PASS
- [ ] EP rollback PASS
- [ ] Conformance rule/allowlist 추가
- [ ] G40 Static Re-scan PASS
