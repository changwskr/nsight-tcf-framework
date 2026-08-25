# NSIGHT URL 도메인 정의

## 1. 문서 목적

본 문서는 NH 농협 상호금융 NSIGHT의 URL 도메인 체계를 정리한 문서이다.

기본 구조는 다음과 같다.

> **업무영역별 Sub Domain + 환경구분 + 업무/서비스 경로**

서비스형 애플리케이션은 업무코드와 서비스코드를 URL 하위 경로로 사용하며,
솔루션형 애플리케이션은 솔루션별 경로를 사용한다.

---

## 2. URL 도메인 정의 총괄

| 순번 | 업무 영역 | 개발 환경 | 운영 환경 | 운영 URL 패턴 |
|---:|---|---|---|---|
| 1 | 미니 싱글뷰 | `ms.test.nacf` | `ms.prod.nacf` | `http://ms.prod.nacf/ms/{서비스코드}` |
| 2 | 마케팅 플랫폼 | `mp.test.nacf` | `mp.prod.nacf` | `http://mp.prod.nacf/{업무코드}/{서비스코드}` |
| 3 | 신용 실적(BI포털) | `cr.test.nacf` | `cr.prod.nacf` | `http://cr.prod.nacf/cr/{서비스코드}` |
| 4 | BI 포털(업무 솔루션) | `pt.test.nacf` | `pt.prod.nacf` | `http://pt.prod.nacf/{솔루션 경로}` |
| 5 | Self-BI(업무 솔루션) | `sb.test.nacf` | `sb.prod.nacf` | 화면 비고에는 `http://ms.prod.nacf/{솔루션 경로}`로 기재 |
| 6 | OLAP(업무 솔루션) | `oa.test.nacf` | `oa.prod.nacf` | 화면 비고에는 `http://ms.prod.nacf/ms/{솔루션 경로}`로 기재 |

---

## 3. 환경별 도메인 규칙

기본 환경 구분은 다음과 같다.

```text
개발
<업무도메인>.test.nacf

운영
<업무도메인>.prod.nacf
```

시스템별 매핑은 다음과 같다.

```text
ms.test.nacf  → ms.prod.nacf
mp.test.nacf  → mp.prod.nacf
cr.test.nacf  → cr.prod.nacf
pt.test.nacf  → pt.prod.nacf
sb.test.nacf  → sb.prod.nacf
oa.test.nacf  → oa.prod.nacf
```

---

## 4. 시스템 도메인 코드

| 코드 | 업무 영역 |
|---|---|
| `ms` | 미니 싱글뷰 |
| `mp` | 마케팅 플랫폼 |
| `cr` | 신용 실적 |
| `pt` | BI 포털 |
| `sb` | Self-BI |
| `oa` | OLAP |

도메인 기본 명명 규칙은 다음과 같다.

```text
[시스템코드].[환경].nacf
```

예:

```text
mp.prod.nacf
│   │     │
│   │     └─ NACF Domain
│   └────── 운영환경
└────────── Marketing Platform
```

---

## 5. 마케팅플랫폼 URL 규칙

### 5.1 기본 Domain

```text
개발 : mp.test.nacf
운영 : mp.prod.nacf
```

Root URL:

```text
http://mp.prod.nacf/
```

### 5.2 업무별 URL

| 업무코드 | URL 패턴 |
|---|---|
| `ic` | `http://mp.prod.nacf/ic/{서비스코드}` |
| `pc` | `http://mp.prod.nacf/pc/{서비스코드}` |
| `bc` | `http://mp.prod.nacf/bc/{서비스코드}` |
| `sa` | `http://mp.prod.nacf/sa/{서비스코드}` |
| `pd` | `http://mp.prod.nacf/pd/{서비스코드}` |
| `cm` | `http://mp.prod.nacf/cm/{서비스코드}` |
| `eb` | `http://mp.prod.nacf/eb/{서비스코드}` |
| `ss` | `http://mp.prod.nacf/ss/{서비스코드}` |
| `cs` | `http://mp.prod.nacf/cs/{서비스코드}` |
| `ct` | `http://mp.prod.nacf/ct/{서비스코드}` |
| `mg` | `http://mp.prod.nacf/mg/{서비스코드}` |

구조는 다음과 같다.

```text
http://mp.prod.nacf
        │
        ├─ /ic/{서비스코드}
        ├─ /pc/{서비스코드}
        ├─ /bc/{서비스코드}
        ├─ /sa/{서비스코드}
        ├─ /pd/{서비스코드}
        ├─ /cm/{서비스코드}
        ├─ /eb/{서비스코드}
        ├─ /ss/{서비스코드}
        ├─ /cs/{서비스코드}
        ├─ /ct/{서비스코드}
        └─ /mg/{서비스코드}
```

---

## 6. URL 계층 구조

URL을 아키텍처 관점으로 분해하면 다음과 같다.

```text
Protocol
   │
   ▼
http://
   │
   ▼
System Domain
mp.prod.nacf
   │
   ▼
Business Path
/ic
   │
   ▼
Service
/{서비스코드}
```

예:

```text
http://mp.prod.nacf/ic/{서비스코드}
```

| 구성 | 예 | 역할 |
|---|---|---|
| Protocol | `http` | 통신 프로토콜 |
| 시스템 Domain | `mp` | 마케팅플랫폼 |
| 환경 | `prod` | 운영 |
| 기관/기준 Domain | `nacf` | 공통 Domain |
| 업무 Path | `ic` | 업무 영역 |
| 거래 Path | `{서비스코드}` | 실제 서비스 식별 |

논리 구조는 다음과 같다.

```text
Domain
   ↓
Application/System
   ↓
Business
   ↓
Service
```

---

## 7. 서비스형 URL과 솔루션형 URL

### 7.1 서비스형

마케팅플랫폼, 미니싱글뷰, 신용실적 등은 서비스코드를 기준으로 호출한다.

```text
Domain
  ↓
업무코드
  ↓
서비스코드
```

예:

```text
http://mp.prod.nacf/ic/{서비스코드}
http://cr.prod.nacf/cr/{서비스코드}
http://ms.prod.nacf/ms/{서비스코드}
```

### 7.2 솔루션형

BI Portal, Self-BI, OLAP은 `{서비스코드}`가 아니라 `{솔루션 경로}`를 사용한다.

```text
http://pt.prod.nacf/{솔루션 경로}
```

관리 관점에서는 다음과 같이 구분한다.

```text
업무 Application
→ ServiceId

Package / Solution
→ Solution Route
```

---

## 8. URL 아키텍처 관리체계

향후 URL 정의서는 다음 계층으로 관리한다.

```text
URL Architecture
│
├─ Environment
│   ├─ test
│   └─ prod
│
├─ System Domain
│   ├─ ms
│   ├─ mp
│   ├─ cr
│   ├─ pt
│   ├─ sb
│   └─ oa
│
├─ Business Path
│   ├─ ic
│   ├─ pc
│   ├─ bc
│   ├─ sa
│   ├─ pd
│   ├─ cm
│   ├─ eb
│   ├─ ss
│   ├─ cs
│   ├─ ct
│   └─ mg
│
└─ Resource
    ├─ {서비스코드}
    └─ {솔루션 경로}
```

---

## 9. 확인 필요사항

이미지 원본 기준으로 Self-BI와 OLAP의 도메인 컬럼과 비고 URL 사이에 불일치가 존재한다.

### 9.1 Self-BI

도메인 컬럼:

```text
개발 : sb.test.nacf
운영 : sb.prod.nacf
```

비고 URL:

```text
http://ms.prod.nacf/{솔루션 경로}
```

판정:

> **확인 필요**

---

### 9.2 OLAP

도메인 컬럼:

```text
개발 : oa.test.nacf
운영 : oa.prod.nacf
```

비고 URL:

```text
http://ms.prod.nacf/ms/{솔루션 경로}
```

판정:

> **확인 필요**

따라서 Self-BI와 OLAP의 URL은 임의로 수정하거나 확정하지 않고,
원본 설계 확인 후 Architecture Baseline으로 확정한다.

---

## 10. 최종 아키텍처 정의

> **NSIGHT URL은 시스템별 독립 Sub Domain을 기본 경계로 하고, `test/prod`로 환경을 분리하며, 서비스형 애플리케이션은 `업무코드/{서비스코드}`, 솔루션형 애플리케이션은 `{솔루션 경로}`를 하위 Route로 사용하는 구조이다.**

핵심 구조:

```text
[Protocol]
    ↓
[System Domain]
    ↓
[Environment]
    ↓
[Business Path]
    ↓
[ServiceId / Solution Route]
```

즉 URL은 단순 접속 주소가 아니라 다음 구조를 연결하는 아키텍처 식별체계로 관리한다.

```text
시스템
  ↓
업무
  ↓
서비스
  ↓
ServiceId
  ↓
실행 애플리케이션
```

---

## 11. 상태

| 항목 | 상태 |
|---|---|
| 시스템별 Sub Domain | 확인 |
| 개발/운영 환경 분리 | 확인 |
| 마케팅플랫폼 업무별 Path | 확인 |
| 서비스형 URL 패턴 | 확인 |
| 솔루션형 URL 패턴 | 확인 |
| Self-BI 운영 URL | **확인 필요** |
| OLAP 운영 URL | **확인 필요** |

