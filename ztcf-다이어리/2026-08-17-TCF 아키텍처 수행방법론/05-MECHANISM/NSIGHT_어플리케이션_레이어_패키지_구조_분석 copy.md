# NSIGHT 어플리케이션 레이어 패키지 구조 분석

## 1. 문서 목적

본 문서는 첨부된 **「온라인 프레임워크」 아키텍처 이미지**를 기준으로, 어플리케이션 레이어의 **업무 분류체계, Java 패키지 구조, 계층별 책임, 클래스/DTO/Mapper 네이밍, Java 소스와 Resource 간 매핑 관계**를 정리한 문서이다.

> 주의: 본 문서는 이미지에서 확인 가능한 내용을 우선하여 정리하였다.  
> 이미지에 명확히 표시되지 않은 세부 구현 방식은 확정 사실로 간주하지 않는다.

---

## 2. 이미지에서 확인되는 전체 분류 구조

이미지의 상단 분류체계는 다음 4단계로 구성되어 있다.

| 구분 | 이미지 예시 | 의미 |
|---|---|---|
| Top | `nhnis` | 최상위 시스템/기관 패키지 |
| 어플리케이션 그룹(대구분) | `mp` | 애플리케이션 그룹 |
| 어플리케이션(업무 구분) | `co` | 업무/애플리케이션 구분 |
| 기능(세부 업무 구분) | `a`, `b` | 세부 업무/기능 구분 |

이를 패키지 경로로 표현하면 다음과 같다.

```text
nhnis
└─ mp
   └─ co
      ├─ a
      └─ b
```

즉 기본 업무 식별 구조는 다음 공식으로 해석할 수 있다.

```text
[Top].[Application Group].[Application].[Function]
```

이미지 예시:

```text
nhnis.mp.co.a
```

---

## 3. 어플리케이션 레이어 패키지 구조

`nhnis.mp.co.a` 하위에는 다음 4개 주요 책임 계층이 존재한다.

```text
nhnis.mp.co.a
│
├─ controller
│   └─ mpcoaController.java
│
├─ service
│   └─ mpcoa0001Service.java
│
├─ dao
│   └─ mpcoa0001DAO.java
│
└─ dto
    ├─ mpcoa0001S0DTOin.java
    ├─ mpcoa0001S0DTOout.java
    └─ mpcoa0001S0DTOio.java
```

전체적으로 보면 이 구조는 다음과 같은 전형적인 계층형 애플리케이션 구조이다.

```text
Controller
    │
    ▼
Service
    │
    ▼
DAO
    │
    ▼
OR Mapper / SQL
    │
    ▼
DB
```

DTO는 Controller/Service/DAO 사이에서 업무 데이터를 전달하는 계약 객체 역할을 수행한다.

---

# 4. 계층별 책임 분석

## 4.1 Controller

이미지의 Controller 설명은 다음 의미로 정리할 수 있다.

- 서비스 ID와 Controller Class Method를 연계
- 업무 본 처리에 해당하는 거래 처리 흐름을 제어
- Service Method 호출

패키지:

```text
package nhnis.mp.co.a.controller
```

예시 클래스:

```text
mpcoaController.java
```

### 책임

```text
HTTP/거래 요청 수신
        │
        ▼
ServiceId 또는 Method 식별
        │
        ▼
입력 데이터 전달
        │
        ▼
Service Method 호출
```

Controller는 DB 처리나 복잡한 비즈니스 로직을 직접 구현하기보다 **거래 진입점 및 흐름 제어**를 담당하는 계층으로 해석된다.

---

## 4.2 Service

이미지의 Service 설명은 다음과 같다.

- 서비스 ID와 Service Class 연계
- 업무 처리를 위한 Biz Logic 구현
- DTO와 DAO 객체를 사용하여 업무 로직 처리

패키지:

```text
package nhnis.mp.co.a.service
```

예시 클래스:

```text
mpcoa0001Service.java
```

### 책임

```text
Controller
    │
    ▼
Service
    │
    ├─ 업무 검증
    ├─ 업무 처리 순서
    ├─ DTO 처리
    └─ DAO 호출
```

따라서 Service는 해당 구조에서 **실질적인 비즈니스 처리 중심 계층**이다.

---

## 4.3 DAO

이미지의 DAO 설명은 다음 의미로 정리할 수 있다.

- 서비스 ID와 DAO Class 연계
- DB Access(C/R/U/D) 로직 구현
- SQL ID를 호출하여 OR Mapper를 통해 DB Data Access

패키지:

```text
package nhnis.mp.co.a.dao
```

예시 클래스:

```text
mpcoa0001DAO.java
```

### 책임

```text
Service
   │
   ▼
DAO
   │
   ├─ SQL ID 호출
   ▼
OR Mapper
   │
   ▼
Database
```

DAO는 비즈니스 판단이 아니라 **데이터 접근 책임**을 담당한다.

---

## 4.4 DTO

이미지의 DTO 설명은 다음과 같다.

- 서비스의 입력값과 출력값을 처리하는 데이터 구현체
- 입력, 출력 또는 입출력이 동일한 데이터 세트를 처리

패키지:

```text
package nhnis.mp.co.a.dto
```

예시:

```text
mpcoa0001S0DTOin.java
mpcoa0001S0DTOout.java
mpcoa0001S0DTOio.java
```

### DTO 구분

| 접미어 | 용도 해석 |
|---|---|
| `DTOin` | 서비스 입력 데이터 |
| `DTOout` | 서비스 출력 데이터 |
| `DTOio` | 입력/출력이 동일하거나 공용으로 사용하는 데이터 |

DTO는 다음 위치에 존재한다.

```text
Controller
   │
   │ DTO
   ▼
Service
   │
   │ DTO
   ▼
DAO
```

단, 실제 구현에서 DAO까지 DTO를 그대로 전달하는지는 이미지에서 완전하게 확정할 수 없으므로 별도 소스 확인이 필요하다.

---

# 5. Resource / Mapper 구조

Java 소스와 별도로 `resource` 영역이 존재하며, 업무 분류체계가 Java 패키지와 동일한 형태로 반복된다.

```text
resource
└─ mp
   └─ co
      └─ a
         ├─ mpcoa0001-ORA.xml
         ├─ mpcoa0002-ORA.xml
         └─ ...
```

이미지 하단에는 다수의 Mapper XML이 존재하는 것으로 표시되어 있다.

이를 통해 다음 구조를 추론할 수 있다.

```text
Java
nhnis.mp.co.a.dao
        │
        ▼
DAO / SQL ID
        │
        ▼
Resource
resource/mp/co/a/
        │
        ├─ mpcoa0001-ORA.xml
        ├─ mpcoa0002-ORA.xml
        └─ ...
        │
        ▼
Oracle DB
```

`-ORA.xml` 접미어는 **Oracle용 SQL Mapper 파일**을 의미하는 네이밍으로 해석된다.

---

# 6. Java 패키지와 Resource의 대칭 구조

이미지에서 중요한 특징은 **Java 업무분류 경로와 Resource SQL 경로가 같은 업무축을 사용한다는 점**이다.

```text
Java Source

nhnis
└─ mp
   └─ co
      └─ a
         ├─ controller
         ├─ service
         ├─ dao
         └─ dto
```

```text
Resource

resource
└─ mp
   └─ co
      └─ a
         ├─ mpcoa0001-ORA.xml
         └─ ...
```

즉 공통 식별축은 다음과 같다.

```text
MP → CO → A
```

이 방식의 장점은 특정 업무를 찾을 때 Java와 SQL을 동일한 경로 규칙으로 추적할 수 있다는 점이다.

---

# 7. 네이밍 규칙 분석

이미지의 예시를 기준으로 프로그램 식별자는 다음과 같이 구성된 것으로 보인다.

```text
mp + co + a + 0001
│    │    │     │
│    │    │     └─ 프로그램 번호
│    │    └────── 세부 업무
│    └─────────── 업무
└──────────────── 애플리케이션 그룹
```

즉:

```text
mpcoa0001
```

을 하나의 프로그램 식별자로 볼 수 있다.

이 프로그램 ID가 여러 산출물에 반복된다.

| 대상 | 예 |
|---|---|
| Service | `mpcoa0001Service.java` |
| DAO | `mpcoa0001DAO.java` |
| DTO | `mpcoa0001S0DTOin.java` |
| Mapper | `mpcoa0001-ORA.xml` |

따라서 핵심 추적성은 다음과 같다.

```text
업무분류
MP / CO / A
   │
   ▼
Program ID
mpcoa0001
   │
   ├─ Service
   ├─ DAO
   ├─ DTO
   └─ Mapper XML
```

---

# 8. ServiceId와 프로그램 네이밍 관계

DTO 이름에는 다음 값이 추가되어 있다.

```text
mpcoa0001S0DTOin
          │
          └─ S0
```

`S0`는 프로그램 ID 뒤에 붙는 **거래 또는 서비스 구분 코드**로 해석할 수 있다.

따라서 이미지가 보여주는 식별 구조는 다음과 같이 확장 가능하다.

```text
MP
└─ CO
   └─ A
      └─ 0001
         └─ S0
```

예시:

```text
mpcoa0001S0
```

다만 이미지 자체에는 `S0`의 세부 의미(조회/등록 등)가 별도로 설명되어 있지 않으므로, **S0의 정확한 거래유형 정의는 별도 네이밍 문서나 소스 확인이 필요하다.**

---

# 9. End-to-End 패키지 흐름

이미지를 전체 흐름으로 정리하면 다음과 같다.

```text
                     ONLINE REQUEST
                           │
                           ▼
┌───────────────────────────────────────────────┐
│ nhnis.mp.co.a.controller                     │
│                                               │
│ mpcoaController.java                         │
│ - 거래 진입                                  │
│ - Service Method 호출                        │
└───────────────────────┬───────────────────────┘
                        │
                        ▼
┌───────────────────────────────────────────────┐
│ nhnis.mp.co.a.service                        │
│                                               │
│ mpcoa0001Service.java                        │
│ - Biz Logic                                  │
│ - DTO / DAO 사용                             │
└───────────────────────┬───────────────────────┘
                        │
                        ▼
┌───────────────────────────────────────────────┐
│ nhnis.mp.co.a.dao                            │
│                                               │
│ mpcoa0001DAO.java                            │
│ - DB C/R/U/D                                 │
│ - SQL ID 호출                                │
└───────────────────────┬───────────────────────┘
                        │
                        ▼
┌───────────────────────────────────────────────┐
│ resource/mp/co/a                             │
│                                               │
│ mpcoa0001-ORA.xml                            │
│ - OR Mapper / SQL                            │
└───────────────────────┬───────────────────────┘
                        │
                        ▼
                      ORACLE
```

DTO는 거래 데이터 계약으로 전 구간에서 사용된다.

---

# 10. 구조적 특징

## 10.1 장점

### ① 업무 분류와 패키지 위치가 직접 연결된다

```text
MP → CO → A
```

라는 업무 분류가 그대로 패키지 경로가 되므로 개발자가 업무 위치를 빠르게 찾을 수 있다.

### ② 프로그램 ID 기반 추적성이 좋다

`mpcoa0001`이 Service, DAO, DTO, Mapper에 반복되므로 프로그램 단위 추적이 쉽다.

### ③ Java와 SQL Resource가 동일한 업무 분류를 사용한다

```text
nhnis.mp.co.a.*
resource/mp/co/a/*
```

와 같이 대응되므로 SQL 추적성이 좋다.

### ④ 계층 책임이 비교적 단순하다

```text
Controller → Service → DAO → Mapper
```

흐름이 명확하다.

---

# 11. 설계상 확인이 필요한 부분

아래 항목은 이미지에서 구조적으로 확인되지만, 실제 표준을 확정하려면 소스 또는 네이밍 정의서 확인이 필요하다.

## 11.1 ServiceId의 단일 기준 필요

이미지 설명에는 Controller, Service, DAO 각각에서 Service ID와 Class가 연결되는 표현이 존재한다.

이 경우 다음 질문을 명확히 해야 한다.

```text
ServiceId의 실제 정본은 어디인가?

Controller Method?
Service Class?
Service Method?
DAO Class?
```

ServiceId가 여러 계층의 클래스명과 동시에 직접 결합되면 계층 변경 시 영향도가 커질 수 있다.

권장 관리 관점은 다음과 같다.

```text
ServiceId
   │
   ▼
거래 진입점
   │
   ▼
Program / Use Case
   │
   ├─ Service
   ├─ DAO
   ├─ DTO
   └─ Mapper
```

즉 ServiceId는 **하나의 거래 식별자**로 관리하고, 각 계층은 추적성으로 연결하는 방식이 관리상 명확하다.

> 위 권장안은 이미지의 사실 설명이 아니라 아키텍처 분석 의견이다.

---

## 11.2 Controller 네이밍의 프로그램 번호 부재

이미지에는 다음과 같이 보인다.

```text
mpcoaController.java
```

반면 Service/DAO/DTO는:

```text
mpcoa0001Service.java
mpcoa0001DAO.java
mpcoa0001S0DTOin.java
```

형태다.

이는 하나의 Controller가 `CO/A` 영역의 여러 프로그램을 수용하는 **공용 Controller**일 가능성이 있다.

예:

```text
mpcoaController
    │
    ├─ mpcoa0001
    ├─ mpcoa0002
    ├─ mpcoa0003
    └─ ...
```

하지만 이는 이미지 구조를 바탕으로 한 해석이므로 실제 Method 구조는 소스 확인이 필요하다.

---

## 11.3 DAO와 Mapper의 1:1 여부 확인

이미지에는 Program 단위 DAO와 Mapper가 보이지만 실제로 다음 중 어떤 방식인지는 확정되지 않는다.

```text
DAO 1개 : Mapper XML 1개
```

또는

```text
DAO 1개 : Mapper XML 여러 개
```

따라서 `DAO Method ↔ SQL ID ↔ Mapper XML`의 상세 Mapping Rule을 별도 표준으로 관리하는 것이 필요하다.

---

# 12. 패키지 구조를 한 문장으로 정의

> **온라인 프레임워크의 어플리케이션 패키지는 `Top → Application Group → Application → Function → Layer` 구조로 업무 책임을 계층화하고, `Controller → Service → DAO → OR Mapper → DB`의 실행 흐름과 `DTO` 기반 데이터 계약을 적용하며, 동일한 업무 분류 및 Program ID를 Java Class와 SQL Resource에 공통 적용하여 추적성을 확보하는 구조이다.**

---

# 13. 최종 기준 구조

```text
TOP
nhnis
│
└─ APPLICATION GROUP
   mp
   │
   └─ APPLICATION / BUSINESS
      co
      │
      ├─ FUNCTION
      │  a
      │  │
      │  ├─ controller
      │  │   └─ mpcoaController.java
      │  │
      │  ├─ service
      │  │   └─ mpcoa0001Service.java
      │  │
      │  ├─ dao
      │  │   └─ mpcoa0001DAO.java
      │  │
      │  └─ dto
      │      ├─ mpcoa0001S0DTOin.java
      │      ├─ mpcoa0001S0DTOout.java
      │      └─ mpcoa0001S0DTOio.java
      │
      └─ FUNCTION
         b
         └─ ...
```

Resource:

```text
resource
└─ mp
   └─ co
      └─ a
         ├─ mpcoa0001-ORA.xml
         ├─ mpcoa0002-ORA.xml
         └─ ...
```

---

# 14. 핵심 요약

| 영역 | 이미지 기준 구조 |
|---|---|
| 업무 분류 | `nhnis → mp → co → a` |
| 기본 패키지 | `nhnis.mp.co.a.[layer]` |
| 실행 계층 | `Controller → Service → DAO → Mapper → DB` |
| 데이터 계약 | DTO |
| 프로그램 ID 예 | `mpcoa0001` |
| 거래/서비스 코드 예 | `S0` |
| 입력 DTO | `mpcoa0001S0DTOin` |
| 출력 DTO | `mpcoa0001S0DTOout` |
| 공용 DTO | `mpcoa0001S0DTOio` |
| SQL Resource | `resource/mp/co/a` |
| Oracle Mapper | `mpcoa0001-ORA.xml` |
| 핵심 장점 | 업무분류–Java–SQL 추적성 |
| 주요 확인사항 | ServiceId 정본, Controller 범위, DAO↔Mapper 매핑 규칙 |

---

## 15. Architecture Review 결론

이 구조의 핵심은 **“업무 분류체계를 코드 구조에 직접 투영한다”**는 것이다.

```text
업무분류
   ↓
패키지
   ↓
Program ID
   ↓
Class / DTO
   ↓
DAO
   ↓
Mapper / SQL
```

따라서 단순한 폴더 정리 규칙이 아니라 **업무 식별체계와 애플리케이션 구현체를 연결하는 추적성 아키텍처**로 볼 수 있다.

다만 장기적으로 표준화하려면 다음 세 가지를 명시적으로 결정해야 한다.

1. **ServiceId의 유일한 정본 및 라우팅 위치**
2. **Program ID와 Controller/Service/DAO/DTO/Mapper 간 1:N 규칙**
3. **Java Package와 Resource Mapper 간 자동 검증 가능한 Naming Rule**

이 세 항목을 고정하면 이후 Source Scan이나 Architecture Conformance Rule로 자동 점검할 수 있는 구조로 발전시킬 수 있다.
