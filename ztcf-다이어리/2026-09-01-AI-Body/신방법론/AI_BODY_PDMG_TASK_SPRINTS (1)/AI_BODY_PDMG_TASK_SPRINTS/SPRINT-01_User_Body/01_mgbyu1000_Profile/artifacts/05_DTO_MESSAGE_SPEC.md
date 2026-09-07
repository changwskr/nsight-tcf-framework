# mgbyu1000 Profile DTO / 전문 / 인터페이스 계약서

- 문서 ID: `BY-DTO-MGBYU1000`
- Program ID: `mgbyu1000`
- 작성 단계: `05_TASK_DTO_MESSAGE`
- 상태: `BASELINE / 일부 TBD`
- 기준일: 2026-09-01
- 선행 산출물:
  - `01_REQUIREMENTS.md`
  - `02_SERVICE_REGISTRY.md`
  - `04_LOGICAL_DATA_MODEL.md`
  - `04_PHYSICAL_DATA_MODEL.md`
  - `04_SQL_LIST.md`
- 선행 GAP: `03_ARCHITECTURE.md` 미작성
- 다음 정상 순서: TASK 03 Architecture 보완 후 TASK 06 Backend Design

---

# 1. 목적

`mgbyu1000`의 3개 Service ID에 대해
PDMG 표준 전문의 업무 본문인 `dto` 계약을 확정한다.

```text
mgbyu1000S0  Profile 조회
mgbyu1000C0  Profile 최초 등록
mgbyu1000U0  Profile 수정
```

핵심 목표:

```text
Client Input
    ↓
hdr_nhnis + dto
    ↓
PDMG Framework
    ├─ hdr_nhnis → ServiceContext
    └─ dto       → Business dtoBody
                     ↓
                  Facade
              Object → DTOin
                     ↓
                  Service
                     ↓
                  DTOout
                     ↓
ResponseBodyAdvice
    ↓
hdr_nhnis + dto
```

실패 시에는 성공 `dto` 대신 PDMG 공통 오류 구조를 사용한다.

```text
hdr_nhnis + result(NH_NIS_ERR_DTO)
```

---

# 2. 실제 PDMG 전문에서 확인된 FACT

## FACT-DTO-001 — Header와 업무 DTO는 분리된다

PDMG 요청 루트:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000S0",
      "scid": "mgbyu1000"
    }
  },
  "dto": {
  }
}
```

Framework 처리:

```text
hdr_nhnis
    ↓
DefaultFilter
    ↓
ServiceContext

dto
    ↓
OnlineTransactionController
    ↓
Handler Object dtoBody
    ↓
Facade ObjectMapper.convertValue
    ↓
*DTOin
```

따라서 업무 DTO에 `hdr_nhnis`를 복사하지 않는다.

## FACT-DTO-002 — DTO Naming은 Service ID 전체를 사용한다

```text
mgbyu1000S0DTOin
mgbyu1000S0DTOout

mgbyu1000C0DTOin
mgbyu1000C0DTOout

mgbyu1000U0DTOin
mgbyu1000U0DTOout
```

신규 수기 DTO 필드는 camelCase를 사용한다.

## FACT-DTO-003 — TCF ON에서 Facade가 DTO Type을 결정한다

```text
Handler
  → Object dtoBody

Facade
  → ObjectMapper.convertValue(dtoBody, mgbyu1000S0DTOin.class)

Service
  → typed DTOin
```

## FACT-DTO-004 — 현재 Bean Validation은 PDMG 업무 DTO 전반에서 확인되지 않는다

따라서 `@NotBlank`, `@Valid`가 이미 공통 실행된다고 가정하지 않는다.

본 Program은 우선 다음 방식으로 검증한다.

```text
DTO 구조/타입 변환
     ↓
Service / ProfileValidationRule
     ↓
Business Validation
     ↓
DAO
```

향후 공통 Bean Validation을 적용하면 TCF ON/OFF 양쪽에서 같은 계약으로 실행되는지 별도 검증한다.

---

# 3. 보안 신뢰 경계 — userId 계약

## 3.1 핵심 결정

**Client 업무 DTO에는 `userId`를 넣지 않는다.**

적용 대상:

```text
mgbyu1000S0DTOin
mgbyu1000C0DTOin
mgbyu1000U0DTOin
```

이유:

1. `mgbyu1000`은 “본인 Profile” Program이다.
2. Client가 `userId`를 보내면 다른 회원 ID 변조 시도가 가능하다.
3. PDMG Header 자체에 조작 가능한 사용자 관련 필드가 있을 수 있으므로,
   단순 Header 값만으로 인증을 확정해서는 안 된다.
4. Backend가 **검증된 인증 Context**에서 본인 ID를 얻어야 한다.

## 3.2 Backend 내부 Key

업무 처리 내부에서는 다음 Key가 필요하다.

```text
authenticatedUserId
```

논리 DB Key:

```text
UserProfile.userId
```

하지만 이 값은 Client DTO Field가 아니다.

## 3.3 아직 남은 Architecture TBD

`authenticatedUserId`의 실제 취득 Source:

```text
Verified JWT Claim
or
Trusted SSO Security Context
or
Framework가 검증 후 설치한 Principal/ServiceContext
```

정확한 추출 컴포넌트는 TASK 03 Architecture에서 확정한다.

금지:

```text
dto.userId를 그대로 DB WHERE USER_ID에 사용
```

---

# 4. DTO 전체 목록

| Service ID | DTO Class | 방향 | 역할 | Status |
|---|---|---|---|---|
| `mgbyu1000S0` | `mgbyu1000S0DTOin` | Input | 본인 Profile 조회 | BASELINE |
| `mgbyu1000S0` | `mgbyu1000S0DTOout` | Output | 본인 Profile 결과 | BASELINE |
| `mgbyu1000C0` | `mgbyu1000C0DTOin` | Input | 최초 Profile 등록 | BASELINE |
| `mgbyu1000C0` | `mgbyu1000C0DTOout` | Output | 등록 처리결과 | BASELINE |
| `mgbyu1000U0` | `mgbyu1000U0DTOin` | Input | Profile 전체 수정 | BASELINE |
| `mgbyu1000U0` | `mgbyu1000U0DTOout` | Output | 수정 처리결과 | BASELINE |

`DTOSub0`은 사용하지 않는다.

이유:

```text
Profile S0 = 단건 0..1 조회
```

목록/Grid 계약이 아니므로 `mgbyu1000S0DTOSub0`을 불필요하게 생성하지 않는다.

---

# 5. 공통 Profile Field 계약

## 5.1 DTO에서 노출하는 Profile Field

```text
displayName
birthDate
genderCode
trainingExperienceCode
activityLevelCode
```

## 5.2 DTO에서 제외하는 Field

```text
userId
height
weight
bodyFatPercentage
skeletalMuscleMass
createdAt
updatedAt
```

제외 이유:

| Field | 이유 |
|---|---|
| `userId` | 인증 Context에서 확보, Client 변조 방지 |
| `height` 등 신체수치 | `BodyMeasurement` 소유 |
| `createdAt/updatedAt` | 현재 Profile 화면 요구에 필요하지 않아 외부 계약 최소화 |

즉 DB Entity와 DTO는 동일하지 않다.

---

# 6. mgbyu1000S0DTOin

## 6.1 목적

로그인한 Member 자신의 Profile을 조회한다.

## 6.2 Field

**업무 Field 없음.**

```java
public class mgbyu1000S0DTOin {
}
```

실제 타입이 `DataObject` 생성형인지 단순 POJO인지는
TASK 06에서 현재 BY 신규 DTO 생성방식과 PDMG 소스를 다시 확인해 확정한다.

## 6.3 Contract

| Service ID | DTO Class | Field | Type | Required | Validation | Source | Description | Sensitive | Example |
|---|---|---|---|---|---|---|---|---|---|
| `mgbyu1000S0` | `mgbyu1000S0DTOin` | - | - | - | - | - | 업무 입력 없음 | - | `{}` |

사용자 식별은 Backend 인증 Context에서 얻는다.

---

# 7. mgbyu1000S0DTOout

## 7.1 목적

현재 Member의 Profile 정보를 반환한다.

## 7.2 Field Contract

| Service ID | DTO Class | Field | Java/JSON Type | Required | Validation/Format | Source | Description | Sensitive | Example |
|---|---|---|---|---|---|---|---|---|---|
| `mgbyu1000S0` | `mgbyu1000S0DTOout` | `displayName` | `String` | N | max 100 후보 | DB | 표시명 | Y | `"홍길동"` |
| `mgbyu1000S0` | `mgbyu1000S0DTOout` | `birthDate` | `String` | N | `yyyy-MM-dd` | DB | 생년월일 | Y | `"1995-07-21"` |
| `mgbyu1000S0` | `mgbyu1000S0DTOout` | `genderCode` | `String` | N | 코드 ≤20, 코드값 TBD | DB | 성별/개인화 코드 | Y | `"M"` |
| `mgbyu1000S0` | `mgbyu1000S0DTOout` | `trainingExperienceCode` | `String` | Y | 코드 ≤20, 코드값 TBD | DB | 운동경력 | Y | `"INTERMEDIATE"` |
| `mgbyu1000S0` | `mgbyu1000S0DTOout` | `activityLevelCode` | `String` | Y | 코드 ≤20, 코드값 TBD | DB | 활동수준 | Y | `"MODERATE"` |

### 날짜 타입 결정

JSON 계약은 명시적 문자열:

```text
yyyy-MM-dd
```

로 고정한다.

Java 내부에서 `String`을 유지할지 `LocalDate`로 변환할지는
PDMG DTO 생성기/Mapper 호환성 확인 후 TASK 06에서 최종 결정한다.

**외부 JSON 계약 자체는 `yyyy-MM-dd`이다.**

---

# 8. mgbyu1000C0DTOin

## 8.1 목적

Profile이 없는 Member가 최초 Profile을 등록한다.

## 8.2 Field Contract

| Service ID | DTO Class | Field | Java/JSON Type | Required | Validation | Source | Description | Sensitive | Example |
|---|---|---|---|---|---|---|---|---|---|
| `mgbyu1000C0` | `mgbyu1000C0DTOin` | `displayName` | `String` | N | trim, blank→null, max 100 | CLIENT | 표시명 | Y | `"홍길동"` |
| `mgbyu1000C0` | `mgbyu1000C0DTOin` | `birthDate` | `String` | N | `yyyy-MM-dd`, 실제 날짜 검증, 미래일 금지 | CLIENT | 생년월일 | Y | `"1995-07-21"` |
| `mgbyu1000C0` | `mgbyu1000C0DTOin` | `genderCode` | `String` | N | trim, max 20, 허용코드 TBD | CLIENT | 성별/개인화 코드 | Y | `"M"` |
| `mgbyu1000C0` | `mgbyu1000C0DTOin` | `trainingExperienceCode` | `String` | Y | not blank, max 20, 허용코드 TBD | CLIENT | 운동경력 | Y | `"INTERMEDIATE"` |
| `mgbyu1000C0` | `mgbyu1000C0DTOin` | `activityLevelCode` | `String` | Y | not blank, max 20, 허용코드 TBD | CLIENT | 활동수준 | Y | `"MODERATE"` |

## 8.3 Required 결정

상위 요구사항이 Profile에서 `운동경력 / 활동수준` 관리를 직접 요구하고,
현재 별도 Profile Draft 요구사항이 없으므로 다음을 MVP 등록 필수값으로 정한다.

```text
trainingExperienceCode
activityLevelCode
```

상태: `BASELINE for MVP`

다음은 선택값:

```text
displayName
birthDate
genderCode
```

개인정보 최소수집 원칙 때문에 선택값으로 유지한다.

---

# 9. mgbyu1000C0DTOout

등록 성공 결과는 최소 정보만 반환한다.

| Service ID | DTO Class | Field | Type | Required | Validation | Source | Description | Sensitive | Example |
|---|---|---|---|---|---|---|---|---|---|
| `mgbyu1000C0` | `mgbyu1000C0DTOout` | `processedCount` | `Integer` | Y | 성공 시 `1` | SERVICE/DAO | 처리 건수 | N | `1` |

등록 후 화면에서 Profile 전체 상태가 필요하면 `mgbyu1000S0`을 다시 호출한다.

다음 레거시 필드명을 신규 수기 DTO 표준으로 복사하지 않는다.

```text
PROC_CNT
RSLT_CD
RSLT_MSG
```

신규 수기 DTO는 camelCase를 사용한다.

---

# 10. mgbyu1000U0DTOin

## 10.1 수정 Semantic

`U0`은 PATCH가 아니라 **Profile Full Update**로 정의한다.

이유:

- 현재 PDMG 거래는 Service ID 기반 POST이고 REST PATCH 계약이 아니다.
- DTO에서 `null = 미변경`인지 `null = 값 제거`인지 혼동을 피해야 한다.
- C0와 U0의 Profile 입력 구조를 동일하게 유지하면 UI와 업무규칙이 단순하다.

따라서 U0 호출 시 Profile의 사용자 입력 가능한 현재값 전체를 전달한다.

## 10.2 Field Contract

| Service ID | DTO Class | Field | Type | Required | Validation | Source | Description | Sensitive | Example |
|---|---|---|---|---|---|---|---|---|---|
| `mgbyu1000U0` | `mgbyu1000U0DTOin` | `displayName` | `String` | N | trim, blank→null, max 100 | CLIENT | 표시명 | Y | `"홍길동"` |
| `mgbyu1000U0` | `mgbyu1000U0DTOin` | `birthDate` | `String` | N | `yyyy-MM-dd`, 실제 날짜 검증, 미래일 금지 | CLIENT | 생년월일 | Y | `"1995-07-21"` |
| `mgbyu1000U0` | `mgbyu1000U0DTOin` | `genderCode` | `String` | N | trim, max 20, 허용코드 TBD | CLIENT | 성별/개인화 코드 | Y | `"M"` |
| `mgbyu1000U0` | `mgbyu1000U0DTOin` | `trainingExperienceCode` | `String` | Y | not blank, max 20, 허용코드 TBD | CLIENT | 운동경력 | Y | `"ADVANCED"` |
| `mgbyu1000U0` | `mgbyu1000U0DTOin` | `activityLevelCode` | `String` | Y | not blank, max 20, 허용코드 TBD | CLIENT | 활동수준 | Y | `"HIGH"` |

---

# 11. mgbyu1000U0DTOout

| Service ID | DTO Class | Field | Type | Required | Validation | Source | Description | Sensitive | Example |
|---|---|---|---|---|---|---|---|---|---|
| `mgbyu1000U0` | `mgbyu1000U0DTOout` | `processedCount` | `Integer` | Y | 성공 시 `1` | SERVICE/DAO | 처리 건수 | N | `1` |

수정 후 최신 Profile 화면이 필요하면 `S0` 재조회한다.

---

# 12. 코드값 계약

현재 실제 값은 아직 확정하지 않는다.

## 12.1 trainingExperienceCode

Concept:

```text
운동경력 구간
```

현재 예시:

```text
BEGINNER
INTERMEDIATE
ADVANCED
```

**예시는 계약값이 아니다.**

최종 코드값과 코드 소유 위치(정적 Enum / OM_COMMON_CODE / BY 코드 테이블)는 `TBD`.

## 12.2 activityLevelCode

Concept:

```text
일상 활동수준
```

예시:

```text
LOW
MODERATE
HIGH
```

**예시는 계약값이 아니다.**

최종 코드값은 `TBD`.

## 12.3 genderCode

개인화 필요성이 확인되는 범위에서만 사용한다.

허용 코드와 선택항목 정책은 `TBD`.

---

# 13. Null / Empty 계약

## 13.1 Request Root

운영/통합 표준 요청:

```text
hdr_nhnis = Required
dto       = Required object
```

로 본다.

Local Profile에서 Framework가 Header를 합성할 수 있어도 운영 계약으로 일반화하지 않는다.

## 13.2 S0 DTO

정상:

```json
"dto": {}
```

`dto: null` 허용 여부는 TCF/Fascade 변환 계약 테스트에서 검증한다.
신규 UI는 명시적으로 빈 object `{}`를 보낸다.

## 13.3 String

입력 문자열은 업무 검증 전에 trim한다.

```text
""      → null 취급 후보
"   "   → null 취급 후보
```

필수 필드는 trim 후 null/empty이면 Validation Error.

## 13.4 Output

Profile S0에서 선택값이 없으면 JSON `null`을 사용한다.

```json
{
  "displayName": null,
  "birthDate": null
}
```

빈 문자열로 의미를 대체하지 않는다.

목록 DTO가 없으므로 빈 배열 계약은 본 Program에 해당하지 않는다.

---

# 14. Validation 계약

현재 PDMG의 업무 DTO 전반에 Bean Validation이 자동 적용된다고 가정하지 않는다.

## 14.1 실행 위치

```text
Facade
  Object → DTOin
      ↓
Service
      ↓
Profile Validation
      ↓
DAO
```

`ProfileValidationRule` 분리 여부:

```text
단순 1회성 검증
→ Service private method 가능

복수 거래(C0/U0) 공유
+ 독립 테스트 가치 높음
→ ProfileValidationRule 권장
```

C0/U0가 동일 규칙을 공유하므로
**`ProfileValidationRule` 분리를 PROPOSED**로 둔다.

## 14.2 검증 항목

### C0 / U0

```text
trainingExperienceCode
  - trim 후 필수
  - 최대 20
  - 허용 코드

activityLevelCode
  - trim 후 필수
  - 최대 20
  - 허용 코드

displayName
  - trim
  - 최대 100

birthDate
  - yyyy-MM-dd
  - 실제 존재하는 날짜
  - 미래 날짜 금지

genderCode
  - 최대 20
  - 허용 코드
```

### S0

업무 입력값 없음.

---

# 15. Request 전문 샘플

> Header 값은 샘플이며 인증 신뢰값 자체를 의미하지 않는다.

## 15.1 S0

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000S0",
      "scid": "mgbyu1000",
      "std_tgrm_rqr_rsp_dsc": "Q"
    }
  },
  "dto": {}
}
```

## 15.2 C0

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000C0",
      "scid": "mgbyu1000",
      "std_tgrm_rqr_rsp_dsc": "Q"
    }
  },
  "dto": {
    "displayName": "홍길동",
    "birthDate": "1995-07-21",
    "genderCode": "M",
    "trainingExperienceCode": "INTERMEDIATE",
    "activityLevelCode": "MODERATE"
  }
}
```

## 15.3 U0

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000U0",
      "scid": "mgbyu1000",
      "std_tgrm_rqr_rsp_dsc": "Q"
    }
  },
  "dto": {
    "displayName": "홍길동",
    "birthDate": "1995-07-21",
    "genderCode": "M",
    "trainingExperienceCode": "ADVANCED",
    "activityLevelCode": "HIGH"
  }
}
```

---

# 16. Success Response 샘플

Framework가 Header를 응답에 다시 구성하므로,
Business Layer는 `DTOout`만 반환한다.

## 16.1 S0

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000S0"
    }
  },
  "dto": {
    "displayName": "홍길동",
    "birthDate": "1995-07-21",
    "genderCode": "M",
    "trainingExperienceCode": "INTERMEDIATE",
    "activityLevelCode": "MODERATE"
  }
}
```

## 16.2 C0

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000C0"
    }
  },
  "dto": {
    "processedCount": 1
  }
}
```

## 16.3 U0

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000U0"
    }
  },
  "dto": {
    "processedCount": 1
  }
}
```

응답 Header는 Framework가 GUID/IP/Service ID 등을 보강할 수 있으므로
요청 Header와 byte-for-byte 동일하다고 가정하지 않는다.

---

# 17. Error Response 계약

업무 오류/시스템 오류는 개별 DTOout에서 오류코드를 만들지 않는다.

PDMG 공통 오류 경로:

```text
BizException / Framework Exception
     ↓
GlobalExceptionHandler
     ↓
NH_NIS_ERR_DTO
```

응답 개념:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000C0"
    }
  },
  "result": {
    "stdErrCode": "...",
    "stdErrMsgCntn": "...",
    "errType": "BIZ"
  }
}
```

Profile 전용 오류코드 값은 아직 만들지 않는다.

TBD:

```text
Profile 없음
Duplicate Profile
Invalid Profile Code
```

의 실제 Error Code Registry.

---

# 18. DTO ↔ DB Mapping

## S0 Output

```text
DB / Logical              DTOout
------------------------------------------------
DISPLAY_NAME              displayName
BIRTH_DATE                birthDate
GENDER_CD                 genderCode
TRAIN_EXP_CD              trainingExperienceCode
ACTIVITY_LEVEL_CD         activityLevelCode
```

## C0 / U0 Input

```text
DTOin                      DB / Logical
------------------------------------------------
displayName               DISPLAY_NAME
birthDate                 BIRTH_DATE
genderCode                GENDER_CD
trainingExperienceCode    TRAIN_EXP_CD
activityLevelCode         ACTIVITY_LEVEL_CD
```

내부에서 추가하는 값:

```text
authenticatedUserId
    ↓
USER_ID
```

System-generated:

```text
CREATED_AT
UPDATED_AT
```

---

# 19. DTO ↔ Requirement Traceability

| Requirement | DTO Contract |
|---|---|
| `BY-USR-PRF-001` | S0 empty input + Profile output |
| `BY-USR-PRF-002` | C0 Profile input + processedCount |
| `BY-USR-PRF-003` | U0 Full Update input + processedCount |
| `BY-USR-PRF-004` | `trainingExperienceCode` |
| `BY-USR-PRF-005` | `activityLevelCode` |
| `BY-USR-PRF-006` | S0 최소 Profile Context |
| `BY-USR-PRF-007` | Client DTO에서 `userId` 제거 |
| `BY-USR-PRF-008` | AI에 전체 DTO를 직접 전달하지 않음 |
| `BY-USR-PRF-009` | 민감 Field 로그 최소화 |
| `BY-USR-PRF-010` | 삭제 DTO 없음 |

---

# 20. Contract Test Specification

TASK 10에서 반드시 검증한다.

## 20.1 TCF ON 변환

```text
{} → mgbyu1000S0DTOin
정상 C0 JSON → mgbyu1000C0DTOin
정상 U0 JSON → mgbyu1000U0DTOin
```

## 20.2 Invalid JSON

```text
잘못된 object/type
unknown field
null dto
필수 field 누락
잘못된 birthDate
허용되지 않은 code
```

TCF ON/OFF 두 경로가 존재한다면 오류 응답이 계약 수준에서 동등한지 확인한다.

## 20.3 Security

```text
dto.userId를 보내도 업무 Key로 사용할 수 없는가?
Client가 Header 사용자값을 조작해도 권한을 획득하지 못하는가?
검증된 Principal의 사용자만 USER_ID에 적용되는가?
```

unknown field의 Jackson 처리 정책은 현재 Framework 설정 확인 후 테스트로 고정한다.

---

# 21. 결정사항

TASK 05에서 다음을 고정한다.

1. DTO 타입명은 Service ID 전체를 사용한다.
2. S0/C0/U0 모두 Client DTO에서 `userId`를 제거한다.
3. Backend는 검증된 인증 Context의 Member ID를 DB Key로 사용한다.
4. S0 DTOin은 업무 Field 없는 빈 DTO다.
5. S0 DTOout은 최소 Profile 5개 Field만 노출한다.
6. `height`는 Profile DTO에서 제외하고 Body Domain으로 이동한다.
7. C0/U0 입력은 동일한 Profile Field 구조를 사용한다.
8. U0는 Partial Patch가 아니라 Full Update다.
9. C0/U0 출력은 `processedCount` 하나로 최소화한다.
10. `DTOSub0`은 만들지 않는다.
11. 신규 수기 DTO Field는 camelCase를 사용한다.
12. 성공은 `hdr_nhnis + dto`, 실패는 `hdr_nhnis + result(NH_NIS_ERR_DTO)`를 사용한다.
13. 현재 PDMG에 Bean Validation 자동 적용을 가정하지 않는다.
14. C0/U0 공유 Validation은 `ProfileValidationRule` 후보로 둔다.

---

# 22. 종료되는 TBD

| TBD | 처리 |
|---|---|
| `TBD-PRF-005` userId Client DTO 포함 여부 | **제외로 결정** |
| `TBD-PRF-006` Profile 외부 DTO Field | 본 문서 5개 Field로 결정 |
| height Profile 소유 여부 | Body Domain 소유로 결정 |

---

# 23. 유지되는 TBD

| TBD | 내용 | 처리 단계 |
|---|---|---|
| `TBD-PRF-003` | Profile 없음 S0: Empty vs Business Error | TASK 03/06 |
| `TBD-PRF-004` | authenticatedUserId 실제 추출 위치 | TASK 03 |
| `TBD-PRF-007` | trainingExperienceCode 실제 코드값 | 기준정보 설계 |
| `TBD-PRF-008` | activityLevelCode 실제 코드값 | 기준정보 설계 |
| `TBD-PRF-009` | 중복 오류 실제 Error Code | TASK 06/11 |
| `TBD-PRF-010` | 탈퇴/삭제 Retention | TASK 11 |
| `TBD-PRF-011` | Coach 직접 S0 접근 | TASK 03/11 |
| `TBD-PRF-012` | Admin 직접 수정 | TASK 03/11 |
| `TBD-PRF-013` | 개인정보 암호화/마스킹 | TASK 11 |
| `TBD-DTO-001` | DTO 구현체 DataObject vs POJO | TASK 06 실제 소스 패턴 확인 |
| `TBD-DTO-002` | unknown JSON field 허용/실패 | Framework Jackson 설정/Test |
| `TBD-DTO-003` | Java birthDate 실제 타입 | TASK 06 |
| `TBD-DTO-004` | genderCode 최종 CodeSet | 기준정보 설계 |
| `TBD-DTO-005` | Profile 전용 Error Code | TASK 06/11 |

---

# 24. 다음 TASK 입력

정상 순서상 다음은 Backend 상세설계지만,
현재 TASK 03 Architecture가 미완료 상태이므로 먼저 Architecture를 보완한다.

## Architecture에 전달할 계약

```text
Client DTO에는 userId 없음
    ↓
Verified Authentication Context
    ↓
authenticatedUserId
    ↓
Service/DAO USER_ID

S0 dto = {}
C0/U0 dto =
  displayName
  birthDate
  genderCode
  trainingExperienceCode
  activityLevelCode

S0 out =
  Profile 5 fields

C0/U0 out =
  processedCount
```

## Architecture 완료 후 TASK 06에 전달

```text
DTO Files
mgbyu1000S0DTOin.java
mgbyu1000S0DTOout.java
mgbyu1000C0DTOin.java
mgbyu1000C0DTOout.java
mgbyu1000U0DTOin.java
mgbyu1000U0DTOout.java

No DTOSub
No client userId
No height

Validation
ProfileValidationRule candidate

Error
PDMG Global Error
```

---

# 25. TASK 05 완료 게이트

- [x] Service ID별 DTOin/out 정의
- [x] Client Field와 인증 Context Field 분리
- [x] userId Client 변조 경계 제거
- [x] Validation 규칙 정의
- [x] 날짜 형식 정의
- [x] Null/Empty 정책 정의
- [x] DTOSub 필요성 판정
- [x] 요청 JSON 샘플
- [x] 성공 응답 JSON 샘플
- [x] 오류 응답 계약
- [x] DTO ↔ DB Mapping
- [x] Requirement Traceability
- [x] Contract Test 항목 정의

**TASK 05 데이터/전문 계약 상태: COMPLETE**

단, **TASK 03 Architecture 선행 GAP 때문에 TASK 06 실행은 HOLD**한다.
