    # Paging Processing 아키텍처 구조

현재 **PDMG 실제 소스와 페이징 기준문서**를 기준으로 보면 Paging Processing의 핵심은 다음 한 문장입니다.

> **화면은 `pageNo/pageSize`만 전달하고, Service가 페이지 정책과 `offset`을 계산하며, DAO/Mapper가 Oracle에서 해당 페이지 범위만 조회한다.**

즉 다음과 같은 **DB Paging Architecture**입니다. 전체 데이터를 조회한 뒤 Java `subList()`로 자르는 방식은 금지됩니다.

---

## 1. 전체 Paging Processing Big Picture

```text
                         PDMG PAGING PROCESSING

┌───────────────────────────────────────────────────────────────┐
│                    pdmg-ui / Client                           │
│                                                               │
│  검색조건                                                     │
│  pageNo   = 2                                                 │
│  pageSize = 20                                                │
│                                                               │
│  ※ offset은 보내지 않음                                      │
└─────────────────────────────┬─────────────────────────────────┘
                              │
                              │ HTTP / JSON
                              ▼
┌───────────────────────────────────────────────────────────────┐
│                       pdmg-fw                                 │
│                                                               │
│ Filter → Interceptor → Controller → TCF                       │
│                              │                                │
│                              ▼                                │
│                           STF                                 │
│                              │                                │
│                              ▼                                │
│                      Timeout Executor                         │
│                              │                                │
│                              ▼                                │
│                         Dispatcher                            │
│                              │ ServiceId                      │
└──────────────────────────────┼────────────────────────────────┘
                               ▼
┌───────────────────────────────────────────────────────────────┐
│                     pdmg-service                              │
│                                                               │
│ Handler                                                       │
│    │                                                          │
│    ▼                                                          │
│ Facade                                                        │
│    │ @Transactional(readOnly = true)                          │
│    ▼                                                          │
│ Service                                                       │
│    │                                                          │
│    ├─ ① pageNo 보정                                          │
│    ├─ ② pageSize 보정 / 최대 100                             │
│    ├─ ③ offset 계산                                          │
│    ├─ ④ COUNT DAO 호출                                       │
│    ├─ ⑤ LIST DAO 호출                                        │
│    └─ ⑥ Paging Response 조립                                 │
│          │                                                    │
│          ▼                                                    │
│ DAO                                                           │
│    ├─ xxxS0_S0_count()                                        │
│    └─ xxxS0_S0()                                              │
└──────────┬────────────────────────────────────────────────────┘
           │
           ▼
┌───────────────────────────────────────────────────────────────┐
│                  MyBatis Mapper XML                           │
│                                                               │
│        ┌───────────────────────────┐                          │
│        │ 공통 검색조건 WHERE       │                          │
│        └───────┬───────────┬───────┘                          │
│                │           │                                  │
│                ▼           ▼                                  │
│             COUNT       PAGE LIST                              │
│                         ORDER BY                               │
│                         ROWNUM                                 │
└────────────────┬──────────────────────────────────────────────┘
                 ▼
               Oracle
                 │
          ┌──────┴──────┐
          │             │
      totalCount    현재 페이지 rows
          │             │
          └──────┬──────┘
                 ▼
┌───────────────────────────────────────────────────────────────┐
│                        DTOout                                 │
│                                                               │
│ rows                                                          │
│ size                                                          │
│ pageNo                                                        │
│ pageSize                                                      │
│ totalCount                                                    │
│ totalPages                                                    │
└──────────────────────────────┬────────────────────────────────┘
                               ▼
                           pdmg-ui
```

현재 PDMG 문서도 이를 `pageNo/pageSize → Service 보정 → offset → COUNT → 목록 SQL → DTO 조립` 구조로 정의하고 있습니다.

---

# 2. Paging은 어느 계층에서 무엇을 담당하는가

책임을 분리하는 것이 중요합니다.

| 계층        | Paging 책임                         |
| ----------- | ----------------------------------- |
| UI          | `pageNo`, `pageSize`, 검색조건 전달 |
| DTOin       | Paging 요청 계약                    |
| Handler     | ServiceId → Facade 연결             |
| Facade      | 조회 Transaction 경계               |
| **Service** | **Paging 정책의 핵심**              |
| DAO         | COUNT/LIST SQL 계약                 |
| Mapper      | 실제 DB Paging                      |
| DB          | WHERE/ORDER BY/Index 기반 범위 조회 |
| DTOout      | Paging 결과 반환                    |

핵심은 다음입니다.

```text
Paging Policy
     =
   Service

Paging Execution
     =
 Mapper / DB
```

따라서 다음처럼 만들면 안 됩니다.

```text
Controller에서 offset 계산       X
Handler에서 pageSize 제한         X
DAO에서 업무 기본값 결정          X
UI에서만 최대 100 제한             X
전체 SELECT 후 Service subList    X
```

현재 기준도 Service가 보정·offset·count/list·응답 조립을 담당하고, DAO/XML은 SQL을 담당하도록 명확히 구분합니다.

---

# 3. 요청 전문 구조

예를 들어 `mgcoa8888S0` 이미지로그 조회라고 하면:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgcoa8888S0"
    }
  },
  "dto": {
    "pageNo": 2,
    "pageSize": 20,
    "serviceId": "mgcoa5530S0",
    "exceptionOnly": true
  }
}
```

여기서 중요한 원칙은:

```text
외부 계약

pageNo        O
pageSize      O
검색조건       O

offset        X
rnum          X
startRow      X
endRow        X
```

입니다.

`offset`은 DB 구현 상세이므로 Client에게 받지 않습니다. **Service가 계산합니다.**

---

# 4. Service의 Paging 처리

현재 PDMG 핵심 구현은 다음 패턴입니다.

```text
입력
pageNo = 2
pageSize = 20
      │
      ▼
Service
      │
      ├─ pageNo 유효성 검사
      │
      ├─ pageSize 유효성 검사
      │
      ├─ 최대 100 제한
      │
      ▼
offset 계산
      │
      ▼
(2 - 1) × 20
      │
      ▼
offset = 20
```

공식은:

```text
offset = (pageNo - 1) × pageSize
```

예를 들면:

| pageNo | pageSize | offset | 조회 범위 |
| -----: | -------: | -----: | --------- |
|      1 |       20 |      0 | 1~20      |
|      2 |       20 |     20 | 21~40     |
|      3 |       20 |     40 | 41~60     |
|     10 |       20 |    180 | 181~200   |

---

# 5. 기본값과 최대값

현재 구현은 거래별 기본값이 완전히 동일하지 않습니다.

| ServiceId     | 기본 pageNo | 기본 pageSize | 최대 |
| ------------- | ----------: | ------------: | ---: |
| `mgcoa5530S0` |           1 |            20 |  100 |
| `mgcoa8888S0` |           1 |            20 |  100 |
| `mgcoa9000S0` |           1 |        **10** |  100 |
| `mgcoa9001S0` |           1 |            20 |  100 |

따라서:

```text
"PDMG의 모든 pageSize 기본값 = 20"
```

이라고 정의하면 현재 소스와 맞지 않습니다. 현행 분석자료에서도 `mgcoa9000S0`의 기본값 10을 별도 차이로 관리합니다.

### TO-BE에서는

이 부분은 표준화하는 것이 좋습니다.

```text
PagingPolicy

DEFAULT_PAGE_NO   = 1
DEFAULT_PAGE_SIZE = 20
MAX_PAGE_SIZE     = 100
```

다만 `mgcoa9000=10`이 업무적으로 의도된 값이라면 Service별 정책 Override로 관리해야 합니다.

---

# 6. COUNT와 LIST 두 개의 SQL을 실행한다

현재 PDMG Paging은 한 페이지 조회에 기본적으로 SQL이 **2번** 실행됩니다.

```text
Service
   │
   ├──── DAO.count()
   │          │
   │          ▼
   │      COUNT(*)
   │          │
   │       1,253건
   │
   └──── DAO.list()
              │
              ▼
          PAGE SQL
              │
            20건
```

예:

```text
mgcoa8888S0

        ↓

mgcoa8888S0_S0_count
        +
mgcoa8888S0_S0
```

결과:

```text
totalCount = 1253
rows       = 20
```

---

# 7. COUNT와 LIST의 WHERE는 반드시 같아야 한다

이것이 매우 중요한 Architecture Rule입니다.

```text
             검색조건

serviceId = "mgcoa5530S0"
exceptionOnly = true
       │
       ├─────────────────┐
       ▼                 ▼

COUNT SQL             LIST SQL
WHERE A               WHERE A
WHERE B               WHERE B
WHERE C               WHERE C
       │                 │
       ▼                 ▼
   totalCount           rows
```

잘못하면:

```text
COUNT
WHERE serviceId = ...
        ↓
100건

LIST
WHERE serviceId = ...
AND exception = ...
        ↓
5건
```

이 되어 화면에는:

```text
전체 100건
그런데 실제 검색조건은 5건
```

같은 오류가 발생합니다.

그래서 Mapper에서는 공통 WHERE를:

```xml
<sql id="mgcoa8888Where">
    ...
</sql>
```

로 정의한 뒤:

```text
COUNT ───┐
         ├─ same WHERE
LIST  ───┘
```

로 재사용하는 것이 현재 표준입니다.

---

# 8. Oracle ROWNUM Paging 구조

현재 PDMG AS-IS의 핵심 DB Paging은 Oracle `ROWNUM` 이중 래핑 방식입니다.

```text
원본 데이터
    │
    ▼
WHERE
    │
    ▼
ORDER BY
    │
    ▼
앞에서 offset + pageSize까지 제한
    │
    ▼
ROWNUM 부여
    │
    ▼
offset 이하 제거
    │
    ▼
현재 페이지 반환
```

Mapper의 개념적인 형태는:

```xml
SELECT *
FROM (
    SELECT a.*, ROWNUM rnum
    FROM (
        SELECT ...
        FROM TABLE T1
        WHERE ...
        ORDER BY ...
    ) a
    WHERE ROWNUM <= #{offset} + #{pageSize}
)
WHERE rnum > #{offset}
```

예를 들어:

```text
pageNo   = 2
pageSize = 20
offset   = 20
```

이면:

```text
ROWNUM <= 40
       ↓
1 ~ 40까지 생성
       ↓
rnum > 20
       ↓
21 ~ 40 반환
```

입니다.

---

# 9. ORDER BY는 선택이 아니라 필수다

Paging에서 `ORDER BY`가 없으면:

```text
1페이지 조회
A B C D ...

다른 요청 발생 / DB 실행계획 변경

2페이지 조회
C D E F ...
```

처럼 중복·누락이 발생할 수 있습니다.

따라서:

```text
Paging
    +
Deterministic ORDER BY
```

가 하나의 세트입니다.

더 좋은 구조는 마지막 정렬 컬럼에 **Unique Key를 Tie-breaker**로 두는 것입니다.

예를 들어 이미지로그는:

```sql
ORDER BY REQUEST_TIME DESC,
         GUID DESC
```

처럼 구성되어 있습니다.

```text
REQUEST_TIME
     │
     └─ 같은 값이 여러 개면
             ↓
           GUID
             ↓
       순서를 확정
```

현재 분석자료도 고정 ORDER BY와 유일한 tie-breaker를 핵심 점검 항목으로 봅니다.

---

# 10. Paging 응답 구조

DB 결과를 Service에서 다음과 같이 조립합니다.

```text
DB

COUNT = 95
LIST  = 20 rows

       ↓

Service

       ↓

DTOout
```

예:

```json
{
  "pageNo": 2,
  "pageSize": 20,
  "size": 20,
  "totalCount": 95,
  "totalPages": 5,
  "rows": ["... 20건 ..."]
}
```

`totalPages`는:

```text
ceil(totalCount / pageSize)
```

개념입니다.

예:

```text
95 / 20
    ↓
4.75
    ↓
5 pages
```

---

# 11. 0건은 일반적으로 오류가 아니다

검색결과가 없다면:

```text
COUNT
 ↓
0

LIST
 ↓
[]
```

응답:

```json
{
  "pageNo": 1,
  "pageSize": 20,
  "size": 0,
  "totalCount": 0,
  "totalPages": 0,
  "rows": []
}
```

처럼 처리하는 것이 Paging 조회의 일반적인 구조입니다.

즉:

```text
검색 결과 0건
      ≠
BizException
```

입니다.

---

# 12. Transaction + Paging 관계

앞에서 정리한 PDMG Transaction/Timeout과 연결하면 전체 구조는 다음과 같습니다.

```text
Request
   │
   ▼
TCF
   │
   ▼
Timeout Worker
   │
   ▼
TransactionTemplate
   │
========= TX BEGIN =========
   │
   ▼
Dispatcher
   ↓
Handler
   ↓
Facade
@Transactional(readOnly=true)
   ↓
Service
   │
   ├──── COUNT SQL
   │
   └──── LIST SQL
   │
========= TX END ===========
   │
   ├─ 성공 → COMMIT
   └─ 실패 → ROLLBACK
```

따라서 기본적으로 한 Paging 요청 안에서:

```text
COUNT
+
LIST
```

를 연속 실행합니다. 현재 기준문서도 목록 거래를 `readOnly` Transaction으로 처리하고 count/list를 같은 요청에서 연속 호출하는 구조로 설명합니다.

다만 중요한 점은:

> **같은 Transaction이라고 해서 동시 INSERT/DELETE 상황의 Offset Paging 결과가 항상 완벽하게 고정되는 것은 아닙니다.**

DB isolation과 데이터 변경 시점에 따라 별도로 판단해야 합니다.

---

# 13. Paging과 Timeout 관계

Paging에서 다음을 혼동하면 안 됩니다.

```text
pageSize = 20
```

이라고 해서 SQL이 빠르다는 뜻은 아닙니다.

예를 들어:

```text
전체 데이터 = 5천만건

COUNT(*)
       ↓
8초

LIST 20건
       ↓
0.2초
```

일 수도 있습니다.

또는:

```text
pageNo = 100000
pageSize = 20

offset = 1,999,980
```

이라면 DB는 상당히 깊은 범위까지 처리해야 할 수 있습니다.

따라서 성능비용은:

```text
Paging Cost
   │
   ├─ COUNT Cost
   ├─ WHERE Selectivity
   ├─ ORDER BY / Sort
   ├─ Index
   ├─ Offset Depth
   ├─ Row Width
   ├─ CLOB / 전문
   ├─ JSON Serialization
   ├─ DB Connection
   └─ Timeout
```

을 함께 봐야 합니다. 실제 PDMG 분석자료도 pageSize 하나만으로 대용량 위험이 해결되지 않는다고 지적합니다.

---

# 14. 현재 Offset 방식의 가장 큰 약점

일반 화면에는 매우 적합합니다.

```text
1페이지
2페이지
3페이지
...
10페이지
```

하지만:

```text
100,000 페이지
```

로 가면 문제가 달라집니다.

```text
pageNo = 100000
pageSize = 20

offset
= 1,999,980
```

즉 원하는 데이터는 20건인데 앞쪽의 매우 많은 행을 처리해야 할 수 있습니다.

```text
          Offset Paging

1 ─────────────────────── 1,999,980 ───── 2,000,000
│                              │                │
│         처리 비용             │ 원하는 20건    │
└──────────────────────────────┴────────────────┘
```

그래서 현재 문서도 깊은 페이지에서 Offset 비용이 증가한다고 명시합니다.

---

# 15. 그래서 Keyset/Cursor Paging이 필요할 수 있다

Offset 방식:

```text
"2,000,000번째부터 20건 주세요."
```

Keyset 방식:

```text
"지난번 마지막 값 이후 20건 주세요."
```

예를 들면:

```text
첫 요청

ORDER BY
REQUEST_TIME DESC,
GUID DESC

20건
 ↓
마지막 값
2026-08-17 10:00:00
GUID-900
```

다음 요청:

```text
lastRequestTime = ...
lastGuid = GUID-900
pageSize = 20
```

DB:

```text
WHERE
(REQUEST_TIME, GUID)
   < (:lastRequestTime, :lastGuid)

ORDER BY REQUEST_TIME DESC, GUID DESC
```

이렇게 하면 깊은 Offset을 계속 건너뛸 필요가 없습니다.

---

# 16. Offset과 Keyset은 목적이 다르다

| 구분          | Offset Paging | Keyset Paging |
| ------------- | ------------- | ------------- |
| `pageNo`      | O             | 보통 X        |
| 페이지 점프   | **좋음**      | 어려움        |
| totalPages    | **좋음**      | 보통 없음     |
| 깊은 페이지   | 비용 증가     | **유리**      |
| 무한스크롤    | 보통          | **적합**      |
| 관리 화면     | **적합**      | 보통          |
| 대량 순차조회 | 보통          | **적합**      |

따라서 PDMG 표준을:

```text
모든 Paging = Keyset
```

으로 변경할 필요는 없습니다.

권장 구조는:

```text
일반 관리 화면
      ↓
Offset Paging

초대용량 / 무한스크롤
      ↓
Keyset Paging

전체 대량 추출
      ↓
Batch / File / Async Export
```

입니다. 현재 기준문서도 Offset은 일반 목록, Keyset은 깊은 페이지·무한 스크롤, 대량 전체 추출은 별도 거래로 분리하도록 권고합니다.

---

# 17. 대량 다운로드는 Paging으로 해결하면 안 된다

예를 들어 사용자가:

```text
1,000만건 Excel 다운로드
```

를 요구한다고 해서:

```text
pageSize = 10,000,000
```

으로 만들면 안 됩니다.

현재 `MAX_PAGE_SIZE=100`을 두는 이유도 이 때문입니다.

```text
온라인 목록 조회
        │
        ├─ pageSize <= 100
        │
        ▼
    화면 응답

──────────────────────────

대량 데이터 추출
        │
        ▼
별도 ServiceId
        │
        ▼
Batch / Async
        │
        ▼
File 생성
        │
        ▼
Download
```

이 경계를 유지하는 것이 중요합니다.

---

# 18. 현재 AS-IS에서 개선해야 할 부분

현재 Paging 구현은 기본적인 DB Paging으로는 정상적이지만 몇 가지 개선점이 있습니다.

| 항목         | AS-IS              | 권장 TO-BE                 |
| ------------ | ------------------ | -------------------------- |
| `pageNo`     | `Integer`          | 최대값 검증                |
| `pageSize`   | `Integer`, max 100 | 유지                       |
| `offset`     | `int`              | **long + overflow 검증**   |
| `totalCount` | `int`              | 초대용량은 **long**        |
| Paging       | Offset             | 일반 조회 유지             |
| 깊은 페이지  | Offset             | Keyset 선택                |
| COUNT        | 항상 수행          | 업무별 생략/근사/캐시 검토 |
| ORDER BY     | 존재               | Unique tie-breaker 강제    |
| 대량 추출    | Paging과 혼용 위험 | 별도 비동기 거래           |

특히 현재 분석에서는 `int offset/count`, 깊은 페이지 비용, 동시 데이터 변경 시 중복·누락 가능성을 명시적인 제약으로 잡고 있습니다.

---

# 19. 권장 TO-BE Paging Processing Architecture

제가 PDMG/NSIGHT 최종 표준으로 잡는다면 다음 구조를 권장합니다.

```text
                       TO-BE PAGING ARCHITECTURE

Request
 pageNo
 pageSize
 filters
    │
    ▼
┌──────────────────────────┐
│ PagingPolicy             │
│                          │
│ pageNo normalize         │
│ pageSize normalize       │
│ maxPageSize              │
│ maxPageNo                │
│ overflow check           │
└────────────┬─────────────┘
             │
             ▼
        PagingCriteria
             │
      ┌──────┴──────┐
      │             │
      ▼             ▼
 Count Policy    Paging Mode
      │             │
      │        ┌────┴────┐
      │        │         │
      ▼        ▼         ▼
   EXACT    OFFSET    KEYSET
      │        │         │
   APPROX       │         │
      │         │         │
   NONE         │         │
      └─────────┼─────────┘
                ▼
              DAO
                │
                ▼
             Mapper
                │
                ▼
             Oracle
                │
     ┌──────────┴──────────┐
     ▼                     ▼
Execution Plan         Runtime Evidence
Index                  SQL elapsed
Sort                   rows scanned
Buffer Gets            timeout
                │
                ▼
           PagingResponse
```

즉 Paging도 단순 코딩 패턴이 아니라:

```text
Contract
+
Policy
+
DB Slice
+
Index
+
Timeout
+
Runtime Evidence
```

로 관리하는 것이 좋습니다.

---

# 20. Architecture Rule로 만들면

향후 Architecture Closed Loop에서 다음 규칙들을 자동검증하면 좋습니다.

```text
R-PAGING-001
pageNo는 1-based여야 한다.

R-PAGING-002
pageSize는 서버에서 상한을 강제한다.

R-PAGING-003
offset은 Client 입력을 허용하지 않는다.

R-PAGING-004
전체 목록 조회 후 Java subList 페이징을 금지한다.

R-PAGING-005
COUNT와 LIST의 WHERE 조건은 동일해야 한다.

R-PAGING-006
Paging SQL에는 결정적 ORDER BY가 있어야 한다.

R-PAGING-007
ORDER BY 마지막에는 가능한 Unique Tie-Breaker를 둔다.

R-PAGING-008
offset 계산은 Overflow를 검증해야 한다.

R-PAGING-009
대량 추출을 pageSize 확대로 처리하지 않는다.

R-PAGING-010
깊은 페이지는 Keyset 또는 별도 거래 적용 여부를 검토한다.
```

---

# 21. 초보자라면 이렇게 기억하면 됩니다

```text
사용자
"2페이지, 20건 주세요"
      │
      ▼
Service
"2페이지니까
 offset은 20이네"
      │
      ├─ 전체 몇 건이지?
      │      ↓
      │   COUNT SQL
      │      ↓
      │     95건
      │
      └─ 21~40번만 줘
             ↓
          LIST SQL
             ↓
            20건
      │
      ▼
Response

현재 페이지 = 2
한 페이지   = 20
현재 건수   = 20
전체 건수   = 95
전체 페이지 = 5
```

즉 가장 쉽게 말하면:

> **Paging은 “DB에서 필요한 만큼만 가져오는 기술”이고, PDMG에서는 Service가 페이지 계산을 하고 Mapper/Oracle이 실제 데이터를 잘라 가져오는 구조입니다.**

그리고 PDMG 전체 실행 흐름에 넣으면 최종적으로 다음 위치입니다.

```text
표준전문
   ↓
TCF
   ↓
STF
   ↓
Timeout / Transaction
   ↓
Dispatcher
   ↓
Handler
   ↓
Facade
   ↓
Service
   │
   ├─ ★ Paging Policy
   │     pageNo
   │     pageSize
   │     offset
   │
   ├─ DAO COUNT
   │     ↓
   │   Mapper COUNT
   │
   └─ DAO LIST
         ↓
       Mapper
         ↓
   ★ ORDER BY + ROWNUM
         ↓
        Oracle
   ↓
Paging DTOout
   ↓
ETF
   ↓
표준응답
```

이 구조를 **PDMG Paging Processing 아키텍처의 현재 AS-IS 기준**으로 잡는 것이 가장 정확하며, TO-BE의 핵심은 **`long/overflow 보호 + 결정적 정렬 + COUNT 정책화 + Keyset 선택 + 대량추출 분리`**입니다.
