네. 올려주신 **「도메인 정의」 화면 기준**으로 보면 NSIGHT의 URL 체계는 기본적으로 **`업무영역별 Sub Domain + 환경구분 + 업무/서비스 경로`** 구조로 정의되어 있습니다.

## 1. URL 도메인 정의 총괄

| 순번 | 업무 영역            | 개발 환경      | 운영 환경      | 운영 URL 패턴                                               |
| ---: | -------------------- | -------------- | -------------- | ----------------------------------------------------------- |
|    1 | 미니 싱글뷰          | `ms.test.nacf` | `ms.prod.nacf` | `http://ms.prod.nacf/ms/{서비스코드}`                       |
|    2 | 마케팅 플랫폼        | `mp.test.nacf` | `mp.prod.nacf` | `http://mp.prod.nacf/{업무코드}/{서비스코드}`               |
|    3 | 신용 실적(BI포털)    | `cr.test.nacf` | `cr.prod.nacf` | `http://cr.prod.nacf/cr/{서비스코드}`                       |
|    4 | BI 포털(업무 솔루션) | `pt.test.nacf` | `pt.prod.nacf` | `http://pt.prod.nacf/{솔루션 경로}`                         |
|    5 | Self-BI(업무 솔루션) | `sb.test.nacf` | `sb.prod.nacf` | 화면 비고에는 `http://ms.prod.nacf/{솔루션 경로}`로 기재    |
|    6 | OLAP(업무 솔루션)    | `oa.test.nacf` | `oa.prod.nacf` | 화면 비고에는 `http://ms.prod.nacf/ms/{솔루션 경로}`로 기재 |

가장 기본적인 환경 구분 규칙은 다음으로 읽을 수 있습니다.

```text
개발
<업무도메인>.test.nacf

운영
<업무도메인>.prod.nacf
```

즉:

```text
ms.test.nacf  → ms.prod.nacf
mp.test.nacf  → mp.prod.nacf
cr.test.nacf  → cr.prod.nacf
pt.test.nacf  → pt.prod.nacf
sb.test.nacf  → sb.prod.nacf
oa.test.nacf  → oa.prod.nacf
```

---

# 2. 마케팅플랫폼 URL 규칙

이미지에서 가장 구체적으로 정의된 영역이 **마케팅플랫폼 `mp`**입니다.

기본 Domain:

```text
개발 : mp.test.nacf
운영 : mp.prod.nacf
```

Root URL:

```text
http://mp.prod.nacf/
```

그 아래 업무코드를 Path로 분리합니다.

| 업무코드 | URL 패턴                              |
| -------- | ------------------------------------- |
| `ic`     | `http://mp.prod.nacf/ic/{서비스코드}` |
| `pc`     | `http://mp.prod.nacf/pc/{서비스코드}` |
| `bc`     | `http://mp.prod.nacf/bc/{서비스코드}` |
| `sa`     | `http://mp.prod.nacf/sa/{서비스코드}` |
| `pd`     | `http://mp.prod.nacf/pd/{서비스코드}` |
| `cm`     | `http://mp.prod.nacf/cm/{서비스코드}` |
| `eb`     | `http://mp.prod.nacf/eb/{서비스코드}` |
| `ss`     | `http://mp.prod.nacf/ss/{서비스코드}` |
| `cs`     | `http://mp.prod.nacf/cs/{서비스코드}` |
| `ct`     | `http://mp.prod.nacf/ct/{서비스코드}` |
| `mg`     | `http://mp.prod.nacf/mg/{서비스코드}` |

따라서 구조는 다음과 같습니다.

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

즉 여기서는 **도메인이 시스템을 식별하고, 1차 Path가 업무영역을 식별하며, 마지막 Path가 서비스 거래를 식별**하는 형태입니다.

---

# 3. URL 계층 구조로 정리

이미지의 정의를 아키텍처 관점에서 구조화하면 다음처럼 볼 수 있습니다.

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

구성요소별 의미는:

| 구성             | 예             | 역할             |
| ---------------- | -------------- | ---------------- |
| Protocol         | `http`         | 통신 프로토콜    |
| 시스템 Domain    | `mp`           | 마케팅플랫폼     |
| 환경             | `prod`         | 운영             |
| 기관/기준 Domain | `nacf`         | 공통 Domain      |
| 업무 Path        | `ic`           | 업무 영역        |
| 거래 Path        | `{서비스코드}` | 실제 서비스 식별 |

따라서 URL을 논리적으로 보면:

```text
Domain
   ↓
Application/System
   ↓
Business
   ↓
Service
```

구조입니다.

---

# 4. 시스템 도메인 코드 의미

이미지 기준으로 도메인 앞부분은 다음과 같이 사용되고 있습니다.

| 코드 | 업무 영역     |
| ---- | ------------- |
| `ms` | 미니 싱글뷰   |
| `mp` | 마케팅 플랫폼 |
| `cr` | 신용 실적     |
| `pt` | BI 포털       |
| `sb` | Self-BI       |
| `oa` | OLAP          |

따라서 기본 명명 공식은:

```text
[시스템코드].[환경].nacf
```

입니다.

예를 들어:

```text
mp.prod.nacf
│   │     │
│   │     └─ NACF Domain
│   └────── 운영환경
└────────── Marketing Platform
```

---

# 5. 서비스형 URL과 솔루션형 URL이 분리되어 있음

이 화면에서 중요한 점은 URL 구조가 크게 **두 종류**라는 것입니다.

### 서비스형

마케팅플랫폼, 미니싱글뷰, 신용실적처럼 서비스코드를 기반으로 호출합니다.

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

이는 NSIGHT의 ServiceId/서비스 단위 거래 구조와 연결하기 좋은 패턴입니다. 기존 프로젝트의 Dispatcher도 ServiceId를 실제 거래의 논리적 주소로 사용하고 있습니다.

### 솔루션형

BI Portal, Self-BI, OLAP은 이미지에서 `{서비스코드}`가 아니라 `{솔루션 경로}`로 표현되어 있습니다.

```text
http://pt.prod.nacf/{솔루션 경로}
```

즉:

```text
업무 Application
→ ServiceId

Package/Solution
→ Solution Route
```

처럼 관리대상을 구분할 필요가 있습니다.

---

# 6. 반드시 확인해야 할 두 가지 불일치

이미지를 그대로 기준으로 하면 **Self-BI와 OLAP의 비고 URL에 정합성 문제가 있습니다.**

Self-BI는 본문 Domain이:

```text
개발 : sb.test.nacf
운영 : sb.prod.nacf
```

인데 비고에는:

```text
http://ms.prod.nacf/{솔루션 경로}
```

로 표시되어 있습니다.

OLAP도 본문 Domain은:

```text
개발 : oa.test.nacf
운영 : oa.prod.nacf
```

인데 비고에는 화면상:

```text
http://ms.prod.nacf/ms/{솔루션 경로}
```

형태로 기재되어 있습니다.

따라서 이것을 제가 임의로 `sb.prod.nacf`, `oa.prod.nacf`로 수정해서 확정하면 안 됩니다. **원본 화면의 Domain 컬럼과 비고 URL이 서로 다르므로 `확인필요` 항목으로 관리하는 것이 맞습니다.**

---

# 7. NSIGHT URL 표준으로 정리할 때 권장하는 관리체계

향후 URL 정의서를 만들 때는 단순 도메인 목록이 아니라 다음 체계로 관리하는 것이 좋습니다.

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

최종적으로 한 문장으로 정의하면:

> **NSIGHT URL은 시스템별 독립 Sub Domain을 기본 경계로 하고, `test/prod`로 환경을 분리하며, 서비스형 애플리케이션은 `업무코드/{서비스코드}`, 솔루션형 애플리케이션은 `{솔루션 경로}`를 하위 Route로 사용하는 구조이다.**

그리고 **Self-BI·OLAP의 운영 URL은 Domain 컬럼과 비고가 불일치하므로 원본 설계 확인 후 Baseline으로 확정해야 합니다.**
