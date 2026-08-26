# NSIGHT 캐릭터 셋 정의 및 표준화 분석

> **문서 유형**: Architecture Wiki / Development & Data Standard  
> **대상**: NH 농협 상호금융 NSIGHT 차세대 정보계  
> **상태**: Working Baseline  
> **작성 근거**: 사용자 제공 「캐릭터 셋 정의」 장표  
> **작성일**: 2026-08-22

---

## 1. 문서 목적

본 문서는 「캐릭터 셋 정의」 장표에 정의된 NSIGHT 차세대 정보계의 문자 인코딩 기준을 구조화하고,  
채널·업무시스템·RDW·ADW·대외 인터페이스 간 문자 변환 경계와 개발/운영 시 유의사항을 아키텍처 표준 관점에서 정리한다.

핵심 목적은 다음과 같다.

1. 채널 및 업무 애플리케이션의 문자셋 기준을 명확히 한다.
2. RDW/ADW의 `MS949` 데이터베이스와 `UTF-8` 애플리케이션 사이의 변환 책임을 정의한다.
3. API G/W 또는 GSE 연계 시 전사 인터페이스 표준과의 경계를 명확히 한다.
4. 한글·특수문자·다국어·파일·배치·ETL·DB 컬럼 길이에서 발생할 수 있는 데이터 손실을 예방한다.
5. 개발표준, 인터페이스 표준, 데이터 표준, 테스트 및 운영 점검 항목으로 재사용할 수 있는 Architecture Rule을 제시한다.

---

# 2. 원본 장표의 핵심 정의

장표의 최상위 정의는 다음과 같다.

> **차세대 정보계 시스템의 캐릭터 셋은 채널과 업무 시스템은 `UTF-8`, ADW와 RDW 데이터베이스는 `MS949`를 기준으로 정의한다.**

## 2.1 영역별 캐릭터 셋

| 영역 | 시스템/구성요소 | 장표 기준 캐릭터 셋 |
|---|---|---|
| 채널 | 정보 단말 | `UTF-8` |
| 채널 | Package UI | `UTF-8` |
| 차세대 정보계 | 마케팅 플랫폼 | `UTF-8` |
| 차세대 정보계 | 신용실적 | `UTF-8` |
| 차세대 정보계 | 업무 솔루션(BI 포탈, Self-BI, OLAP 등) | `UTF-8` |
| 데이터베이스 | ADW | `MS949` |
| 데이터베이스 | RDW | `MS949` |
| 전사 인터페이스 | API G/W 또는 GSE 연계 | 전사 인터페이스 기준에 따름 |

## 2.2 장표의 표준화 내용

장표에는 다음 표준화 원칙이 명시되어 있다.

- 차세대 정보계 **채널 시스템 캐릭터 셋은 `UTF-8`**을 기준으로 한다.
- 차세대 정보계 **업무 시스템 캐릭터 셋은 `UTF-8`**을 기준으로 한다.
- **ADW, RDW 데이터베이스 캐릭터 셋은 `MS949`**를 기준으로 한다.
- 데이터베이스 연계 시 **DB와 연계하는 시스템에서 DB 캐릭터 셋(`MS949`)에 맞춰 변환 처리**를 수행한다.
- **API G/W 또는 GSE와 연계하는 경우 전사 인터페이스 기준에 따라 캐릭터 셋을 처리**한다.

---

# 3. 캐릭터 셋 Big Picture

```text
                       NSIGHT CHARACTER SET BIG PICTURE

┌──────────────────────────────────────────────────────────────────────┐
│                              CHANNEL                                 │
│                                                                      │
│   정보 단말                           Package UI                      │
│   UTF-8                              UTF-8                           │
└───────────────┬─────────────────────────┬────────────────────────────┘
                │                         │
                └───────────── UTF-8 ─────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────────────┐
│                    차세대 정보계 업무 시스템                        │
│                                                                      │
│   마케팅 플랫폼       신용실적       BI 포탈 / Self-BI / OLAP       │
│      UTF-8             UTF-8                   UTF-8                 │
│                                                                      │
│      ★ 애플리케이션 내부 처리 기준 = UTF-8                           │
└──────────────────────────────┬───────────────────────────────────────┘
                               │
                               │ DB 연계 경계
                               │ MS949 호환 여부 확인 / 변환 처리
                               ▼
               ┌──────────────────────────────┐
               │          DATA ZONE           │
               │                              │
               │   ADW              RDW       │
               │  MS949            MS949      │
               └──────────────────────────────┘


외부/전사 연계
──────────────────────────────────────────────────────────────────────
업무 시스템 ── API G/W / GSE ── 전사 인터페이스
                    │
                    └─ 캐릭터 셋은 전사 인터페이스 표준 적용
```

---

# 4. 아키텍처 해석

본 장표는 단순히 “UTF-8을 사용한다”는 내용이 아니다.

NSIGHT는 실제로 다음 **두 개의 문자셋 영역**을 동시에 운영하는 구조이다.

```text
Application / Channel Domain
        UTF-8
          │
          │ Character Set Boundary
          ▼
Database Domain
        MS949
```

따라서 가장 중요한 아키텍처 포인트는 **문자 변환 경계(Character Set Boundary)** 이다.

## 4.1 애플리케이션 영역

채널과 업무시스템은 `UTF-8`을 기준으로 정의되어 있다.

즉 다음 영역은 원칙적으로 동일한 문자 표현 체계를 사용해야 한다.

```text
정보단말
   │
Package UI
   │
HTTP / JSON / 업무 데이터
   │
마케팅플랫폼
   │
신용실적
   │
BI / Self-BI / OLAP
```

이 구간의 핵심 목표는 **불필요한 문자셋 변환을 제거하는 것**이다.

## 4.2 DB 영역

RDW와 ADW는 장표상 `MS949`를 사용한다.

따라서 애플리케이션이 DB와 통신할 때는 다음 문제가 발생할 수 있다.

```text
UTF-8 문자열
      │
      ▼
MS949 표현 가능 여부 확인
      │
      ├─ 표현 가능 → DB 저장
      │
      └─ 표현 불가 → 오류 / 대체문자 / 데이터 손실 가능
```

즉 `UTF-8 → MS949`는 단순 포맷 변환이 아니라 **정보 손실 가능성이 존재하는 비대칭 변환**이다.

---

# 5. 핵심 분석 결과

## 5.1 장표의 설계 방향은 명확하다

장표 기준으로 다음 3개 원칙은 명확하다.

| 원칙 | 판단 |
|---|---|
| 채널 표준을 UTF-8로 통일 | 명확 |
| 차세대 업무 애플리케이션을 UTF-8로 통일 | 명확 |
| 기존/목표 RDW·ADW DB는 MS949 유지 | 명확 |
| DB 연계 시 문자셋 변환 필요 | 명확 |
| API G/W/GSE 연계는 전사 표준을 따름 | 명확 |

따라서 NSIGHT의 캐릭터 셋 전략은 다음과 같이 표현할 수 있다.

> **Application은 UTF-8로 표준화하고, Legacy/Database Compatibility는 연계 경계에서 통제한다.**

---

# 6. 가장 중요한 아키텍처 위험

## 6.1 UTF-8 문자가 MS949에 존재하지 않는 경우

`UTF-8`은 Unicode 전체를 표현할 수 있지만 `MS949`는 표현 가능한 문자 범위가 제한된다.

예를 들어 향후 다음 데이터가 들어올 가능성이 있다.

- 일부 확장 한자
- 특수 기호
- 외국어 문자
- Emoji
- Unicode 확장 문자
- 외부 채널에서 입력되는 비표준 기호

이 데이터가 애플리케이션에서는 정상인데 DB 저장 시 `MS949`로 표현되지 않을 수 있다.

```text
UI
UTF-8
  │
  │ 😀 / 확장문자 / 일부 외국어
  ▼
Application
UTF-8
  │
  ▼
RDW / ADW
MS949
  │
  └─ 표현 불가능 가능성
```

### 필요 정책

문자 변환 실패 시 다음 중 어떤 정책을 사용할지 반드시 정의해야 한다.

- 거래 오류 처리
- 입력 차단
- 대체 문자 처리
- 원문 별도 보관
- 특정 필드 Unicode 전용 처리

**현재 장표에는 이 실패 처리 정책까지는 정의되어 있지 않다.**

---

## 6.2 Byte Length 차이

문자셋이 달라지면 동일한 문자열도 실제 저장 Byte 수가 달라질 수 있다.

따라서 다음 항목을 점검해야 한다.

- DB 컬럼 길이
- `VARCHAR2` 길이 의미
- `BYTE / CHAR` Length Semantics
- 전문 고정 길이
- 파일 레코드 길이
- 배치 인터페이스 길이
- 화면 입력 최대 길이
- DTO Validation 길이

예:

```text
화면 입력 길이
       │
       ▼
Java String Length
       │
       ▼
UTF-8 Byte Length
       │
       ▼
MS949 Byte Length
       │
       ▼
DB Column Length
```

이 네 값은 항상 같은 의미가 아니다.

---

## 6.3 이중 변환(Double Conversion)

가장 위험한 금지 패턴 중 하나이다.

```text
UTF-8
  ↓
MS949 변환
  ↓
다시 UTF-8 변환
  ↓
Driver가 다시 DB Charset으로 변환
  ↓
문자 깨짐
```

업무 프로그램마다 임의로 다음과 같은 코드를 넣기 시작하면 장애 원인을 찾기 어렵다.

```java
new String(value.getBytes(...), ...)
```

따라서 문자 변환 책임은 **업무 Service가 아니라 공통 Data/Integration Boundary**에서 관리해야 한다.

---

# 7. 권장 Character Set Boundary

원본 장표는 “DB와 연계하는 시스템에서 변환 처리”라고 정의한다.

이를 아키텍처 책임으로 구체화하면 다음 구조가 적절하다.

```text
               UTF-8 DOMAIN
──────────────────────────────────────────────

Channel
  ↓
HTTP / JSON
  ↓
Controller
  ↓
TCF / Framework
  ↓
Handler / Facade / Service
  ↓
DAO
  ↓
──────────────────────────────────────────────
        ★ CHARACTER SET BOUNDARY ★
──────────────────────────────────────────────
  ↓
JDBC / DB Access / Integration Adapter
  ↓
Oracle Session / DB Character Set
  ↓
RDW / ADW
        MS949 DOMAIN
```

### 권장 원칙

> **업무 계층은 UTF-8/Unicode 문자열만 다루고, DB 문자셋 호환 및 변환 책임은 데이터 접근 경계에서 중앙 통제한다.**

단, 실제 Oracle/JDBC 환경에서 변환을 애플리케이션이 직접 해야 하는지, JDBC Driver/DB Session에서 자동 처리되는지는 **실제 DB NLS 설정과 JDBC 설정을 확인한 후 확정**해야 한다.

---

# 8. DB 실제 Character Set 확인 필요

장표에는 `MS949`라고 표현되어 있다.

그러나 실제 Oracle 환경에서는 DB 캐릭터셋이 제품별 명칭으로 설정될 수 있으므로 다음 값을 반드시 확인해야 한다.

```sql
SELECT parameter, value
FROM nls_database_parameters
WHERE parameter IN (
    'NLS_CHARACTERSET',
    'NLS_NCHAR_CHARACTERSET'
);
```

확인 대상:

| 항목 | 확인 필요 내용 |
|---|---|
| RDW | 실제 `NLS_CHARACTERSET` |
| ADW | 실제 `NLS_CHARACTERSET` |
| RDW | `NLS_NCHAR_CHARACTERSET` |
| ADW | `NLS_NCHAR_CHARACTERSET` |
| DB 컬럼 | `VARCHAR2 / NVARCHAR2 / CLOB / NCLOB` 사용현황 |
| Length | `BYTE / CHAR` 기준 |
| JDBC | Driver 버전 및 Character Conversion 동작 |
| Connection | Session NLS 관련 설정 |

> **주의:** 본 문서에서는 원본 장표의 표현인 `MS949`를 기준으로 사용하며, 실제 Oracle DB 캐릭터셋 명칭은 Runtime/DB 설정 확인 후 Baseline으로 확정해야 한다.

---

# 9. API G/W / GSE 연계 해석

원본 장표는 다음을 명확히 한다.

> **API G/W 또는 GSE와 연계하는 경우 전사 인터페이스 기준에 따라 캐릭터 셋 처리**

따라서 NSIGHT 애플리케이션 표준이 UTF-8이라고 해서 모든 대외/전사 인터페이스를 임의로 UTF-8로 강제하면 안 된다.

구조는 다음과 같다.

```text
NSIGHT
UTF-8
  │
  ▼
Integration Boundary
  │
  ├─ API G/W
  └─ GSE
       │
       ▼
전사 Interface Standard
       │
       ├─ UTF-8
       ├─ MS949
       └─ 기타 규격
```

### 원칙

- 내부 애플리케이션은 UTF-8을 유지한다.
- 외부 규격 변환은 Integration Adapter/Gateway 경계에서 수행한다.
- 업무 Service 안에 인터페이스별 문자 변환 로직을 분산시키지 않는다.
- 연계 전문 단위로 `sourceCharset`, `targetCharset`, 오류 처리 기준을 관리한다.

---

# 10. 데이터 흐름별 캐릭터 셋 표준

| 데이터 흐름 | Source | Target | 기준 | 변환 책임 |
|---|---|---|---|---|
| 정보단말 → 업무시스템 | UTF-8 | UTF-8 | UTF-8 유지 | 변환 없음 |
| Package UI → 업무시스템 | UTF-8 | UTF-8 | UTF-8 유지 | 변환 없음 |
| 업무시스템 → 업무시스템 | UTF-8 | UTF-8 | UTF-8 유지 | 변환 없음 |
| 업무시스템 → RDW | UTF-8 | MS949 | DB 호환 | DB Access Boundary |
| 업무시스템 → ADW | UTF-8 | MS949 | DB 호환 | DB Access Boundary |
| RDW → 업무시스템 | MS949 | UTF-8 | 애플리케이션 표준 복원 | DB Access Boundary |
| ADW → BI/Self-BI/OLAP | MS949 | UTF-8 | 솔루션/Driver 기준 확인 | DB/솔루션 Boundary |
| NSIGHT → API G/W | UTF-8 | 전사기준 | 전사 IF 기준 | Integration Boundary |
| NSIGHT → GSE | UTF-8 | 전사기준 | 전사 IF 기준 | Integration Boundary |
| File/Batch | 별도 확인 | 별도 확인 | 파일 규격별 명시 | Batch/File Adapter |

---

# 11. 개발 표준 권고

## 11.1 Source / Configuration

다음 파일은 모두 UTF-8 저장을 기본으로 한다.

- Java
- XML
- YAML
- Properties
- JSON
- JavaScript / TypeScript
- HTML / CSS
- SQL Source
- Markdown
- Shell Script의 한글 메시지 포함 파일

빌드 및 IDE 환경에서도 동일한 Encoding을 사용해야 한다.

```text
Developer IDE
     UTF-8
       ↓
Git Repository
     UTF-8
       ↓
Build / CI
     UTF-8
       ↓
Runtime
     UTF-8
```

---

## 11.2 업무 프로그램 금지패턴

### 금지 1 — 업무 코드에서 임의 Charset 변환

```java
new String(value.getBytes("UTF-8"), "MS949")
```

이런 코드가 Service/Rule/Controller마다 분산되는 구조는 금지한다.

### 금지 2 — Charset 생략

```java
value.getBytes()
```

OS 기본 Encoding에 따라 결과가 달라질 수 있으므로 파일/Byte 변환 시 Charset을 명시해야 한다.

### 금지 3 — 변환 실패 무시

대체 문자 `?`, 깨진 문자, replacement character가 발생했는데 정상처리하는 방식은 금지한다.

### 금지 4 — 동일 데이터 다중 변환

Framework, DAO, JDBC, DB에서 같은 문자열을 여러 번 변환하지 않는다.

---

# 12. 파일·배치·ETL 영역

캐릭터 셋 장애는 온라인보다 파일/배치에서 더 많이 발생할 수 있다.

다음 구조를 별도 확인해야 한다.

```text
RDW / ADW
   │
   ├─ ETL
   ├─ Batch
   ├─ CSV
   ├─ Fixed Length File
   ├─ DataStage
   ├─ CDC
   └─ External File
```

파일 인터페이스에는 반드시 다음 Metadata가 필요하다.

| 항목 | 필수 여부 |
|---|---|
| File Charset | 필수 |
| Record Delimiter | 필수 |
| Field Delimiter | 필수 |
| Quote/Escape 규칙 | 필수 |
| Fixed/Variable Length | 필수 |
| Length 단위(Byte/Character) | 필수 |
| Conversion Error 정책 | 필수 |
| BOM 사용 여부 | 필수 |
| 한글/특수문자 테스트 | 필수 |

---

# 13. 테스트 시나리오

Character Set은 정상 한글만 테스트하면 안 된다.

## 13.1 필수 테스트 데이터

| 분류 | 테스트 예 |
|---|---|
| 일반 한글 | 농협상호금융 |
| 영문 | NSIGHT Marketing Platform |
| 숫자 | 0123456789 |
| 한글+영문 | 고객ABC123 |
| 일반 기호 | `-_/()[]{}.,` |
| 통화/수학기호 | ₩, ±, × |
| 한자 | 고객명/주소에 사용 가능한 한자 |
| 외국어 | 영문 외 유럽/아시아 문자 |
| 확장 Unicode | MS949 비지원 가능 문자 |
| Emoji | 😀 등의 입력 가능성 검증 |
| 최대길이 | 컬럼/전문 최대 길이 |
| 공백 | 선행/후행/다중 공백 |
| 개행 | CR/LF 포함 문자열 |

---

## 13.2 E2E 테스트

```text
[CASE-01]
정보단말 UTF-8
  → 마케팅플랫폼
  → RDW 저장
  → 재조회
  → 원문 동일성 검증


[CASE-02]
Package UI UTF-8
  → 업무시스템
  → ADW 저장
  → BI 조회
  → 원문 동일성 검증


[CASE-03]
MS949 비지원 문자 입력
  → 저장 시도
  → 정의된 오류코드 발생 여부
  → 데이터 손실 여부 확인


[CASE-04]
API G/W 연계
  → 전사 Interface Charset
  → 변환
  → 왕복 데이터 동일성 검증
```

---

# 14. 운영 관측 항목

캐릭터 셋 문제는 장애 발생 후 로그만 보고 찾기 어렵다.

운영에서는 다음 항목을 관측하는 것을 권장한다.

| 관측 항목 | 설명 |
|---|---|
| Character Conversion Error Count | 문자 변환 실패 건수 |
| Unmappable Character Count | 대상 Charset에서 표현 불가능한 문자 |
| Replacement Character Count | `?`, `�` 등 대체 문자 발생 |
| ServiceId | 발생 거래 |
| GUID/TraceId | 거래 추적 |
| Source Charset | 입력 Encoding |
| Target Charset | 변환 대상 Encoding |
| Interface ID | API/GSE/File 연계 식별자 |
| DB/System | RDW/ADW/연계시스템 |
| Field Name | 오류 필드 |
| Sample Hash | 개인정보 원문 노출 없이 오류 패턴 추적 |

개인정보나 고객 원문을 그대로 로그에 남기는 것은 피하고,  
필요 시 마스킹·Hash·길이·Code Point 수준의 진단정보를 사용해야 한다.

---

# 15. Architecture Rule

## CHAR-001 — Channel UTF-8

> 모든 NSIGHT 채널 시스템은 UTF-8을 기본 Character Set으로 사용한다.

**적용 대상**
- 정보단말
- Package UI
- 신규 NSIGHT Channel

---

## CHAR-002 — Application UTF-8

> NSIGHT 업무 애플리케이션 내부의 표준 문자처리는 UTF-8/Unicode를 기준으로 한다.

**적용 대상**
- 마케팅 플랫폼
- 신용실적
- BI 포탈
- Self-BI
- OLAP
- 신규 업무 애플리케이션

---

## CHAR-003 — RDW/ADW DB Charset

> RDW 및 ADW는 장표 기준 MS949를 사용하며 실제 DB의 NLS Character Set 값을 별도 Runtime Evidence로 확정한다.

---

## CHAR-004 — DB Boundary Conversion

> UTF-8 애플리케이션과 MS949 DB 사이의 문자 변환은 데이터 접근 경계에서 중앙 통제한다.

**금지**
- Controller 임의 변환
- Service 임의 변환
- Rule 임의 변환
- 화면별 임의 변환

---

## CHAR-005 — Interface Standard

> API G/W 또는 GSE 연계는 NSIGHT 내부 표준이 아니라 전사 인터페이스 Character Set 규격을 우선한다.

---

## CHAR-006 — Explicit Charset

> Byte/File/Stream 변환 시 Platform Default Charset 사용을 금지하고 Character Set을 명시한다.

---

## CHAR-007 — No Silent Data Loss

> 표현 불가능한 문자를 대체문자로 자동 치환한 뒤 정상 처리하는 것을 금지한다.

---

## CHAR-008 — Length Validation

> UI, DTO, 전문, DB 컬럼의 길이 정의는 Character Length와 Byte Length를 구분하여 관리한다.

---

## CHAR-009 — File Encoding Contract

> 모든 File/Batch 인터페이스는 Character Set을 인터페이스 계약에 명시한다.

---

## CHAR-010 — Charset Test Gate

> 주요 ServiceId 및 데이터 연계는 한글·특수문자·비지원문자·최대길이 테스트를 통과해야 Baseline 승인한다.

---

# 16. 현재 GAP / 확인 필요사항

| GAP ID | 확인 항목 | 현재 상태 | 조치 |
|---|---|---|---|
| `GAP-CHAR-001` | RDW 실제 Oracle NLS Character Set | 미확인 | DB 조회 |
| `GAP-CHAR-002` | ADW 실제 Oracle NLS Character Set | 미확인 | DB 조회 |
| `GAP-CHAR-003` | NCHAR/NVARCHAR2 Character Set | 미확인 | DB 조회 |
| `GAP-CHAR-004` | JDBC Driver Character Conversion 방식 | 미확인 | Runtime Test |
| `GAP-CHAR-005` | DB Length Semantics(BYTE/CHAR) | 미확인 | DDL/NLS 점검 |
| `GAP-CHAR-006` | MS949 미지원 문자 오류정책 | 미정 | ADR 필요 |
| `GAP-CHAR-007` | API G/W Character Set 상세표 | 별도 기준 필요 | 전사 IF 기준 확보 |
| `GAP-CHAR-008` | GSE Character Set 상세표 | 별도 기준 필요 | 전사 IF 기준 확보 |
| `GAP-CHAR-009` | ETL/File/Batch Encoding 목록 | 미확인 | Interface Inventory 작성 |
| `GAP-CHAR-010` | 로그/이미지로그 Character Set | 확인 필요 | Logging 설정 점검 |
| `GAP-CHAR-011` | 소스/빌드/CI Encoding 강제 설정 | 확인 필요 | 개발표준 반영 |
| `GAP-CHAR-012` | 데이터 이행 시 Encoding 변환 규칙 | 확인 필요 | Migration Rule 작성 |

---

# 17. Architecture Gate 검증안

## G10 — Document

다음 문서에 캐릭터 셋 기준이 일치하는지 확인한다.

- 개발표준
- 데이터표준
- 인터페이스표준
- 전문표준
- DB 설계표준
- 배치/File 표준
- 데이터 이행 표준

## G30 — Configuration

다음 실제 설정을 검사한다.

```text
IDE / Source Encoding
Gradle / Maven
Spring
Tomcat
HTTP
JDBC
Oracle NLS
ETL
Batch
File
API G/W
GSE
```

## G40 — Test

- UTF-8 정상 한글
- 최대길이
- 특수문자
- 한자
- MS949 비지원문자
- DB Round Trip
- API Round Trip
- File Round Trip

## G50 — Runtime Evidence

실제 운영/검증환경에서 다음 Evidence를 확보한다.

```text
DB NLS Query Result
JDBC Driver Version
실제 저장/조회 결과
Conversion Error Log
Interface Test Result
Character Round Trip Result
```

---

# 18. 권장 최종 표준 구조

```text
                        NSIGHT CHARSET STANDARD

               ┌─────────────────────────┐
               │      UTF-8 DOMAIN       │
               │                         │
               │ Channel                 │
               │ UI                      │
               │ Application             │
               │ Framework               │
               │ Business DTO            │
               └────────────┬────────────┘
                            │
                 Character Set Boundary
                            │
          ┌─────────────────┼──────────────────┐
          │                                    │
          ▼                                    ▼
┌────────────────────┐             ┌────────────────────────┐
│ DB DATA BOUNDARY   │             │ INTERFACE BOUNDARY     │
│                    │             │                        │
│ RDW  : MS949       │             │ API G/W                │
│ ADW  : MS949       │             │ GSE                    │
│                    │             │                        │
│ DB 표준에 맞춤     │             │ 전사 IF 표준에 맞춤   │
└────────────────────┘             └────────────────────────┘
```

---

# 19. 최종 판단

이번 장표의 캐릭터 셋 정책은 다음과 같이 이해하는 것이 정확하다.

> **NSIGHT 신규 채널과 업무 애플리케이션은 UTF-8을 표준으로 통일한다.  
> 다만 RDW/ADW 데이터베이스는 MS949를 유지하므로, 애플리케이션과 DB 사이에 명확한 Character Set Boundary가 존재한다.  
> API G/W/GSE 등 전사 연계는 전사 인터페이스 표준이 우선한다.**

따라서 설계의 핵심은 단순한 `UTF-8 사용`이 아니라 다음 세 가지이다.

```text
① UTF-8 Application Standard
             +
② MS949 Database Compatibility Boundary
             +
③ Enterprise Interface Charset Boundary
```

현재 장표만으로는 기본 방향은 충분히 정의되어 있으나, 실제 Baseline 확정을 위해서는 다음 4개를 반드시 추가 확인해야 한다.

1. **RDW/ADW 실제 Oracle NLS Character Set**
2. **MS949 비지원 문자 처리 정책**
3. **DB/API/File/ETL 변환 주체와 실패 처리 규칙**
4. **문자/Byte 길이 및 Round-Trip 테스트 기준**

이 네 가지가 확정되면 본 캐릭터 셋 정의는 단순 장표 수준을 넘어  
**개발표준 + 데이터표준 + 인터페이스표준 + Runtime 검증이 가능한 Character Set Architecture Baseline**으로 사용할 수 있다.

---

# 20. 한 줄 Baseline

> **NSIGHT는 “채널·업무 UTF-8 / RDW·ADW MS949 / 전사 연계는 전사 인터페이스 표준”을 기본 Character Set 정책으로 하며, 모든 문자 변환은 명확한 데이터·인터페이스 경계에서 중앙 통제한다.**
