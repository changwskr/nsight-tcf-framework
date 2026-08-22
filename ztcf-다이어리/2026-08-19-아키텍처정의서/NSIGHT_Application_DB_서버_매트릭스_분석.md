# NSIGHT OLTP 및 대용량 배치 AP-DB 서버 매트릭스 분석

## 0. 문서 개요

본 문서는 제공된 **「OLTP 및 대용량 배치 수행 방안」의 어플리케이션-데이터베이스 서버 간 매트릭스 장표**를 기준으로,
NSIGHT 정보계의 **업무/Application별 주노드·보조노드 배치 관계**를 정리한 문서이다.

원본 장표의 핵심 문구는 다음과 같다.

> **“업무별 부하 및 기능적 요소를 고려하여 어플리케이션 - 데이터베이스 서버 간 매트릭스 정보”**

### 범례

- `●` : **주노드**
- `○` : **보조노드**
- 공란 : **장표상 매핑 없음**

### 작성 원칙

- 장표에 표시된 `● / ○`를 그대로 옮긴다.
- 장표에서 직접 확인되지 않는 DB Service, SCAN/VIP, Connection String, Failover 방식은 임의로 보완하지 않는다.
- 장표상 명칭을 우선 사용한다.
- 해석이 필요한 부분은 **[ANALYSIS]**로 구분한다.

---

# 1. 전체 매트릭스 구조

장표의 행은 Database Node, 열은 Application/업무를 의미한다.

```text
                         Application / 업무

       데이터플랫폼      BI포탈             데이터거버넌스          마케팅플랫폼
          │                │                     │                    │
          ▼                ▼                     ▼                    ▼

       ETL           신용실적 / OLAP       배치AP / 데이터흐름관리   마케팅플랫폼
                     BI포탈 / Self-BI      / 비즈메타                / 미니싱글뷰
          │                │                     │                    │
          └────────────────┴─────────────────────┴────────────────────┘
                                   │
                                   ▼
                              Database Node

                         RDW #1 / RDW #2
                  ADW #1 / #2 / #3 / #4 / #5 / #6
```

---

# 2. 컬럼 구성

## 2.1 데이터플랫폼

| 업무 | Application |
|---|---|
| ETL | ETL #1 |
| ETL | ETL #2 |

## 2.2 BI포탈

| 업무 | Application |
|---|---|
| 신용실적 | 신용실적 #1 |
| 신용실적 | 신용실적 #2 |
| OLAP | OLAP #1 |
| OLAP | OLAP #2 |
| BI포탈 | BI포탈 #1 |
| BI포탈 | BI포탈 #2 |
| Self-BI | Self-BI #1 |

## 2.3 데이터거버넌스

| 업무 | Application |
|---|---|
| 배치 AP | 배치 AP #1 |
| 데이터흐름관리 | 데이터흐름관리 #1 |
| 데이터흐름관리 | 데이터흐름관리 #2 |
| 비즈메타 | 비즈메타 #1 |
| 비즈메타 | 비즈메타 #2 |

> 원본 장표에는 `데이터품질`이 별도 컬럼으로 표시되지 않고,
> 데이터거버넌스 영역에 `배치 AP / 데이터흐름관리 / 비즈메타`가 표시되어 있다.

## 2.4 마케팅플랫폼

| 업무 | Application |
|---|---|
| 마케팅플랫폼 | 마케팅플랫폼 #1 |
| 마케팅플랫폼 | 마케팅플랫폼 #2 |
| 마케팅플랫폼 | 마케팅플랫폼 #51 |
| 마케팅플랫폼 | 마케팅플랫폼 #52 |
| 미니싱글뷰 | 미니싱글뷰 #1 |
| 미니싱글뷰 | 미니싱글뷰 #2 |
| 미니싱글뷰 | 미니싱글뷰 #51 |
| 미니싱글뷰 | 미니싱글뷰 #52 |

---

# 3. 전체 AP-DB 매트릭스

| DB Node | ETL#1 | ETL#2 | 신용실적#1 | 신용실적#2 | OLAP#1 | OLAP#2 | BI포탈#1 | BI포탈#2 | Self-BI#1 | 배치AP#1 | 데이터흐름#1 | 데이터흐름#2 | 비즈메타#1 | 비즈메타#2 | 마케팅#1 | 마케팅#2 | 마케팅#51 | 마케팅#52 | 미니싱글뷰#1 | 미니싱글뷰#2 | 미니싱글뷰#51 | 미니싱글뷰#52 |
|---|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| **RDW #1** | ● | ○ |  |  | ● | ○ | ● | ○ |  |  |  |  |  |  | ● | ○ | ● | ○ | ● | ○ | ● | ○ |
| **RDW #2** | ○ | ● |  |  | ○ | ● | ○ | ● |  |  |  |  |  |  | ○ | ● | ○ | ● | ○ | ● | ○ | ● |
| **ADW #1** | ● | ○ |  |  | ● | ○ |  |  |  | ● |  |  |  |  |  |  |  |  |  |  |  |  |
| **ADW #2** | ○ | ● |  |  | ○ | ● |  |  |  | ○ |  |  |  |  |  |  |  |  |  |  |  |  |
| **ADW #3** |  |  | ● | ○ |  |  | ● | ○ | ● |  |  |  |  |  |  |  |  |  |  |  |  |  |
| **ADW #4** |  |  | ○ | ● |  |  | ○ | ● | ○ |  |  |  |  |  |  |  |  |  |  |  |  |  |
| **ADW #5** |  |  |  |  |  |  |  |  |  |  | ● | ○ | ● | ○ |  |  |  |  |  |  |  |  |
| **ADW #6** |  |  |  |  |  |  |  |  |  |  | ○ | ● | ○ | ● |  |  |  |  |  |  |  |  |

---

# 4. RDW #1 / #2 매핑

## 4.1 RDW #1

### 주노드

- ETL #1
- OLAP #1
- BI포탈 #1
- 마케팅플랫폼 #1
- 마케팅플랫폼 #51
- 미니싱글뷰 #1
- 미니싱글뷰 #51

### 보조노드

- ETL #2
- OLAP #2
- BI포탈 #2
- 마케팅플랫폼 #2
- 마케팅플랫폼 #52
- 미니싱글뷰 #2
- 미니싱글뷰 #52

```text
RDW #1
│
├─ PRIMARY
│  ├─ ETL #1
│  ├─ OLAP #1
│  ├─ BI포탈 #1
│  ├─ 마케팅플랫폼 #1
│  ├─ 마케팅플랫폼 #51
│  ├─ 미니싱글뷰 #1
│  └─ 미니싱글뷰 #51
│
└─ SECONDARY
   ├─ ETL #2
   ├─ OLAP #2
   ├─ BI포탈 #2
   ├─ 마케팅플랫폼 #2
   ├─ 마케팅플랫폼 #52
   ├─ 미니싱글뷰 #2
   └─ 미니싱글뷰 #52
```

---

# 5. RDW #2 매핑

## 5.1 RDW #2

### 주노드

- ETL #2
- OLAP #2
- BI포탈 #2
- 마케팅플랫폼 #2
- 마케팅플랫폼 #52
- 미니싱글뷰 #2
- 미니싱글뷰 #52

### 보조노드

- ETL #1
- OLAP #1
- BI포탈 #1
- 마케팅플랫폼 #1
- 마케팅플랫폼 #51
- 미니싱글뷰 #1
- 미니싱글뷰 #51

```text
RDW #2
│
├─ PRIMARY
│  ├─ ETL #2
│  ├─ OLAP #2
│  ├─ BI포탈 #2
│  ├─ 마케팅플랫폼 #2
│  ├─ 마케팅플랫폼 #52
│  ├─ 미니싱글뷰 #2
│  └─ 미니싱글뷰 #52
│
└─ SECONDARY
   ├─ ETL #1
   ├─ OLAP #1
   ├─ BI포탈 #1
   ├─ 마케팅플랫폼 #1
   ├─ 마케팅플랫폼 #51
   ├─ 미니싱글뷰 #1
   └─ 미니싱글뷰 #51
```

---

# 6. ADW #1 / #2 매핑

## 6.1 ADW #1

### 주노드

- ETL #1
- OLAP #1
- 배치 AP #1

### 보조노드

- ETL #2
- OLAP #2

```text
ADW #1
├─ PRIMARY
│  ├─ ETL #1
│  ├─ OLAP #1
│  └─ 배치 AP #1
│
└─ SECONDARY
   ├─ ETL #2
   └─ OLAP #2
```

## 6.2 ADW #2

### 주노드

- ETL #2
- OLAP #2

### 보조노드

- ETL #1
- OLAP #1
- 배치 AP #1

```text
ADW #2
├─ PRIMARY
│  ├─ ETL #2
│  └─ OLAP #2
│
└─ SECONDARY
   ├─ ETL #1
   ├─ OLAP #1
   └─ 배치 AP #1
```

### [ANALYSIS]

장표상 ADW #1/#2는 ETL/OLAP을 서로 반대 방향의 주/보조로 배치하고,
`배치 AP #1`은 ADW #1이 주노드, ADW #2가 보조노드로 지정되어 있다.

---

# 7. ADW #3 / #4 매핑

## 7.1 ADW #3

### 주노드

- 신용실적 #1
- BI포탈 #1
- Self-BI #1

### 보조노드

- 신용실적 #2
- BI포탈 #2

```text
ADW #3
├─ PRIMARY
│  ├─ 신용실적 #1
│  ├─ BI포탈 #1
│  └─ Self-BI #1
│
└─ SECONDARY
   ├─ 신용실적 #2
   └─ BI포탈 #2
```

## 7.2 ADW #4

### 주노드

- 신용실적 #2
- BI포탈 #2

### 보조노드

- 신용실적 #1
- BI포탈 #1
- Self-BI #1

```text
ADW #4
├─ PRIMARY
│  ├─ 신용실적 #2
│  └─ BI포탈 #2
│
└─ SECONDARY
   ├─ 신용실적 #1
   ├─ BI포탈 #1
   └─ Self-BI #1
```

---

# 8. ADW #5 / #6 매핑

## 8.1 ADW #5

### 주노드

- 데이터흐름관리 #1
- 비즈메타 #1

### 보조노드

- 데이터흐름관리 #2
- 비즈메타 #2

```text
ADW #5
├─ PRIMARY
│  ├─ 데이터흐름관리 #1
│  └─ 비즈메타 #1
│
└─ SECONDARY
   ├─ 데이터흐름관리 #2
   └─ 비즈메타 #2
```

## 8.2 ADW #6

### 주노드

- 데이터흐름관리 #2
- 비즈메타 #2

### 보조노드

- 데이터흐름관리 #1
- 비즈메타 #1

```text
ADW #6
├─ PRIMARY
│  ├─ 데이터흐름관리 #2
│  └─ 비즈메타 #2
│
└─ SECONDARY
   ├─ 데이터흐름관리 #1
   └─ 비즈메타 #1
```

---

# 9. Application 기준 매핑표

## 9.1 데이터플랫폼 / ETL

| Application | RDW 주노드 | RDW 보조노드 | ADW 주노드 | ADW 보조노드 |
|---|---|---|---|---|
| ETL #1 | RDW #1 | RDW #2 | ADW #1 | ADW #2 |
| ETL #2 | RDW #2 | RDW #1 | ADW #2 | ADW #1 |

### [ANALYSIS]

ETL은 장표상 **RDW와 ADW 양쪽 모두에 주/보조 매핑**이 존재한다.

---

# 10. BI포탈 업무 매핑

## 10.1 신용실적

| Application | 주노드 | 보조노드 |
|---|---|---|
| 신용실적 #1 | ADW #3 | ADW #4 |
| 신용실적 #2 | ADW #4 | ADW #3 |

## 10.2 OLAP

| Application | RDW 주노드 | RDW 보조노드 | ADW 주노드 | ADW 보조노드 |
|---|---|---|---|---|
| OLAP #1 | RDW #1 | RDW #2 | ADW #1 | ADW #2 |
| OLAP #2 | RDW #2 | RDW #1 | ADW #2 | ADW #1 |

> 장표상 OLAP은 RDW와 ADW 양쪽에 매핑표시가 존재한다.

## 10.3 BI포탈

| Application | RDW 주노드 | RDW 보조노드 | ADW 주노드 | ADW 보조노드 |
|---|---|---|---|---|
| BI포탈 #1 | RDW #1 | RDW #2 | ADW #3 | ADW #4 |
| BI포탈 #2 | RDW #2 | RDW #1 | ADW #4 | ADW #3 |

> 장표상 BI포탈 역시 RDW와 ADW 양쪽에 매핑표시가 존재한다.

## 10.4 Self-BI

| Application | 주노드 | 보조노드 |
|---|---|---|
| Self-BI #1 | ADW #3 | ADW #4 |

---

# 11. 데이터거버넌스 업무 매핑

## 11.1 배치 AP

| Application | 주노드 | 보조노드 |
|---|---|---|
| 배치 AP #1 | ADW #1 | ADW #2 |

## 11.2 데이터흐름관리

| Application | 주노드 | 보조노드 |
|---|---|---|
| 데이터흐름관리 #1 | ADW #5 | ADW #6 |
| 데이터흐름관리 #2 | ADW #6 | ADW #5 |

## 11.3 비즈메타

| Application | 주노드 | 보조노드 |
|---|---|---|
| 비즈메타 #1 | ADW #5 | ADW #6 |
| 비즈메타 #2 | ADW #6 | ADW #5 |

---

# 12. 마케팅플랫폼 업무 매핑

## 12.1 마케팅플랫폼

| Application | 주노드 | 보조노드 |
|---|---|---|
| 마케팅플랫폼 #1 | RDW #1 | RDW #2 |
| 마케팅플랫폼 #2 | RDW #2 | RDW #1 |
| 마케팅플랫폼 #51 | RDW #1 | RDW #2 |
| 마케팅플랫폼 #52 | RDW #2 | RDW #1 |

## 12.2 미니싱글뷰

| Application | 주노드 | 보조노드 |
|---|---|---|
| 미니싱글뷰 #1 | RDW #1 | RDW #2 |
| 미니싱글뷰 #2 | RDW #2 | RDW #1 |
| 미니싱글뷰 #51 | RDW #1 | RDW #2 |
| 미니싱글뷰 #52 | RDW #2 | RDW #1 |

---

# 13. 주/보조 Pair 패턴

장표는 여러 업무에서 동일한 Pairing 패턴을 사용한다.

```text
#1 Application
    ↓
주노드 #1
보조노드 #2

#2 Application
    ↓
주노드 #2
보조노드 #1
```

대표 예:

```text
ETL #1
  ├─ Primary  : RDW #1 / ADW #1
  └─ Secondary: RDW #2 / ADW #2

ETL #2
  ├─ Primary  : RDW #2 / ADW #2
  └─ Secondary: RDW #1 / ADW #1
```

마케팅플랫폼도 동일하다.

```text
마케팅플랫폼 #1
  ├─ Primary  : RDW #1
  └─ Secondary: RDW #2

마케팅플랫폼 #2
  ├─ Primary  : RDW #2
  └─ Secondary: RDW #1
```

---

# 14. ADW 업무 그룹 구조

장표의 매트릭스를 업무별로 묶으면 ADW는 다음 세 Pair로 분리된다.

```text
ADW #1 ↔ ADW #2
│
├─ ETL
├─ OLAP
└─ 배치 AP


ADW #3 ↔ ADW #4
│
├─ 신용실적
├─ BI포탈
└─ Self-BI


ADW #5 ↔ ADW #6
│
├─ 데이터흐름관리
└─ 비즈메타
```

### [ANALYSIS]

이 구조는 앞선 업무부하 분리 원칙을 실제 **Application → Database Node Affinity Matrix** 형태로 구체화한 것으로 볼 수 있다.

---

# 15. RDW 업무 그룹 구조

```text
RDW #1 ↔ RDW #2
│
├─ ETL
├─ OLAP
├─ BI포탈
├─ 마케팅플랫폼
└─ 미니싱글뷰
```

각 Application 번호가 주/보조 노드에 교차 매핑되어 있다.

---

# 16. 운영/DR 번호 체계

마케팅플랫폼과 미니싱글뷰에는 다음 번호가 함께 존재한다.

```text
운영
#1 / #2

DR 계열
#51 / #52
```

장표상 매핑은 다음과 같다.

```text
#1  → RDW #1 Primary / #2 Secondary
#2  → RDW #2 Primary / #1 Secondary

#51 → RDW #1 Primary / #2 Secondary
#52 → RDW #2 Primary / #1 Secondary
```

> 이 매트릭스는 #51/#52의 DB 접속 관계를 보여주지만,
> 센터 전환 시 실제 DR DB 자체가 어떤 구조인지까지 설명하지는 않는다.

---

# 17. 아키텍처적 의미

## 17.1 [ANALYSIS] Application Affinity

매트릭스는 단순 DB 접속 가능 여부보다
**Application마다 우선적으로 사용할 DB Node와 장애 시 보조 DB Node를 지정하는 Affinity 구조**로 해석할 수 있다.

```text
Application
     │
     ├─ Primary DB Node
     │
     └─ Secondary DB Node
```

## 17.2 [ANALYSIS] Workload 분산

주노드가 Application 번호별로 교차 배치되므로
정상 상태에서 부하를 노드별로 나누는 효과를 의도한 것으로 볼 수 있다.

예:

```text
Application #1 → Node #1
Application #2 → Node #2
```

## 17.3 [ANALYSIS] 장애 대응

주노드 장애 시 보조노드를 사용하도록 설계 의도가 보인다.

```text
Primary 정상
   ↓
Primary 사용

Primary 장애
   ↓
Secondary 사용
```

다만 실제 자동 Failover 메커니즘은 장표에서 명시하지 않는다.

---

# 18. 중요한 원본 특징

이번 매트릭스에서 특히 주의해야 할 사항은 다음과 같다.

## 18.1 ETL은 RDW와 ADW 양쪽에 모두 표시

```text
ETL #1
├─ RDW #1 Primary / #2 Secondary
└─ ADW #1 Primary / #2 Secondary

ETL #2
├─ RDW #2 Primary / #1 Secondary
└─ ADW #2 Primary / #1 Secondary
```

## 18.2 OLAP도 RDW와 ADW 양쪽에 표시

```text
OLAP #1
├─ RDW #1 Primary
└─ ADW #1 Primary

OLAP #2
├─ RDW #2 Primary
└─ ADW #2 Primary
```

## 18.3 BI포탈도 RDW와 ADW 양쪽에 표시

```text
BI포탈 #1
├─ RDW #1 Primary
└─ ADW #3 Primary

BI포탈 #2
├─ RDW #2 Primary
└─ ADW #4 Primary
```

따라서 이 장표는 단순히 **업무 하나당 DB 하나**의 구조가 아니다.

어떤 Application은 **RDW와 ADW 각각에 별도의 Primary/Secondary 관계**를 가진다.

---

# 19. Architecture Rule 후보

| Rule ID | Rule | 근거 |
|---|---|---|
| `DB-MAP-001` | Application별 Primary/Secondary DB Node를 명시한다 | 장표 근거 |
| `DB-MAP-002` | RDW #1/#2는 상호 Primary/Secondary Pair로 구성한다 | 장표 근거 |
| `DB-MAP-003` | ADW #1/#2는 ETL/OLAP/배치AP 그룹의 Pair다 | 장표 근거 |
| `DB-MAP-004` | ADW #3/#4는 신용실적/BI포탈/Self-BI 그룹의 Pair다 | 장표 근거 |
| `DB-MAP-005` | ADW #5/#6는 데이터흐름관리/비즈메타 그룹의 Pair다 | 장표 근거 |
| `DB-MAP-006` | #1 Application은 원칙적으로 #1 계열 DB를 주노드로 배치한다 | 장표 패턴 |
| `DB-MAP-007` | #2 Application은 원칙적으로 #2 계열 DB를 주노드로 배치한다 | 장표 패턴 |
| `DB-MAP-008` | 마케팅플랫폼 #51/#52도 RDW #1/#2 Pair로 매핑한다 | 장표 근거 |
| `DB-MAP-009` | 미니싱글뷰 #51/#52도 RDW #1/#2 Pair로 매핑한다 | 장표 근거 |
| `DB-MAP-010` | Application의 DB Node Affinity와 RAC Service 정책을 일치시켜야 한다 | 설계 필요 |
| `DB-MAP-011` | Primary 장애 시 Secondary 전환방식을 별도 정의해야 한다 | GAP |
| `DB-MAP-012` | Application이 RDW와 ADW 양쪽에 연결되는 경우 연결 목적을 분리 정의해야 한다 | GAP |

---

# 20. 확인 필요 GAP

| GAP ID | 항목 | 현재 상태 |
|---|---|---|
| `GAP-MAP-001` | Primary/Secondary가 RAC Instance인지 DB Service인지 | 미표기 |
| `GAP-MAP-002` | 실제 JDBC URL / SCAN 주소 | 미표기 |
| `GAP-MAP-003` | RAC Service 이름 | 미표기 |
| `GAP-MAP-004` | Preferred / Available Instance | 미표기 |
| `GAP-MAP-005` | Client-side Load Balancing | 미표기 |
| `GAP-MAP-006` | Fast Connection Failover | 미표기 |
| `GAP-MAP-007` | Application Continuity | 미표기 |
| `GAP-MAP-008` | Failover 자동/수동 여부 | 미표기 |
| `GAP-MAP-009` | Failback 정책 | 미표기 |
| `GAP-MAP-010` | ETL이 RDW/ADW 양쪽을 사용하는 실제 목적 | 미표기 |
| `GAP-MAP-011` | OLAP이 RDW/ADW 양쪽을 사용하는 구체적 데이터 범위 | 미표기 |
| `GAP-MAP-012` | BI포탈이 RDW/ADW 양쪽을 사용하는 구체적 기능 | 미표기 |
| `GAP-MAP-013` | Self-BI #2 존재 여부 | 장표에 없음 |
| `GAP-MAP-014` | 배치 AP #2 존재 여부 | 장표에 없음 |
| `GAP-MAP-015` | 데이터품질 별도 Application 컬럼 | 장표에 없음 |
| `GAP-MAP-016` | #51/#52의 센터/DR DB 연결 세부 | 미표기 |
| `GAP-MAP-017` | DB Connection Pool별 Node Affinity | 미표기 |
| `GAP-MAP-018` | 장애 후 N-1 처리용량 | 미표기 |
| `GAP-MAP-019` | Primary/Secondary 전환 시 Session 영향 | 미표기 |
| `GAP-MAP-020` | Transaction Retry 정책 | 미표기 |

---

# 21. 검증 시나리오 후보

| Test ID | 시나리오 | 검증 목적 |
|---|---|---|
| `MAPT-001` | RDW #1 장애 | #1/#51 Application의 #2 전환 확인 |
| `MAPT-002` | RDW #2 장애 | #2/#52 Application의 #1 전환 확인 |
| `MAPT-003` | ADW #1 장애 | ETL#1/OLAP#1/배치AP#1의 #2 전환 |
| `MAPT-004` | ADW #2 장애 | ETL#2/OLAP#2의 #1 전환 |
| `MAPT-005` | ADW #3 장애 | 신용실적#1/BI포탈#1/Self-BI의 #4 전환 |
| `MAPT-006` | ADW #4 장애 | 신용실적#2/BI포탈#2의 #3 전환 |
| `MAPT-007` | ADW #5 장애 | 데이터흐름#1/비즈메타#1의 #6 전환 |
| `MAPT-008` | ADW #6 장애 | 데이터흐름#2/비즈메타#2의 #5 전환 |
| `MAPT-009` | RDW/ADW 동시 업무 수행 | Cross-DB workload 검증 |
| `MAPT-010` | Failback | 복구 후 주노드 원복 정책 검증 |

---

# 22. 최종 Application-DB Mapping Big Picture

```text
                          APPLICATION - DB MAPPING


                  ┌────────────────────────────┐
                  │         RDW Pair           │
                  │      RDW #1 ↔ RDW #2      │
                  └─────────────┬──────────────┘
                                │
                ┌───────────────┼───────────────────────┐
                │               │                       │
                ▼               ▼                       ▼
              ETL            OLAP / BI          Marketing / MSV
            #1 / #2            #1/#2            #1/#2/#51/#52


                  ┌────────────────────────────┐
                  │       ADW Pair A           │
                  │      ADW #1 ↔ ADW #2      │
                  └─────────────┬──────────────┘
                                │
                       ETL / OLAP / 배치AP


                  ┌────────────────────────────┐
                  │       ADW Pair B           │
                  │      ADW #3 ↔ ADW #4      │
                  └─────────────┬──────────────┘
                                │
                    신용실적 / BI포탈 / Self-BI


                  ┌────────────────────────────┐
                  │       ADW Pair C           │
                  │      ADW #5 ↔ ADW #6      │
                  └─────────────┬──────────────┘
                                │
                      데이터흐름관리 / 비즈메타
```

---

# 23. 결론

이번 어플리케이션-데이터베이스 서버 간 매트릭스에서 확인되는 핵심은 다음과 같다.

1. **모든 주요 Application에 대해 주노드(●)와 보조노드(○) 관계를 명시**한다.
2. RDW는 **#1 ↔ #2 Pair**로 구성되며 ETL, OLAP, BI포탈, 마케팅플랫폼, 미니싱글뷰가 교차 배치된다.
3. ADW는 **#1/#2, #3/#4, #5/#6의 3개 Pair**로 업무를 분리한다.
4. ADW #1/#2는 **ETL, OLAP, 배치 AP**를 담당한다.
5. ADW #3/#4는 **신용실적, BI포탈, Self-BI**를 담당한다.
6. ADW #5/#6는 **데이터흐름관리, 비즈메타**를 담당한다.
7. 마케팅플랫폼 및 미니싱글뷰의 **#1/#51은 RDW #1이 주노드**, **#2/#52는 RDW #2가 주노드**로 표현된다.
8. ETL, OLAP, BI포탈은 장표상 **RDW와 ADW 양쪽에 매핑**이 존재하므로 각 DB 연결 목적을 상세 설계에서 분리 정의해야 한다.
9. 이번 장표는 Application별 **DB Node Affinity와 Failover Pair를 시각적으로 명확히 표현한 매트릭스**로 볼 수 있다.
10. 운영 Baseline 확정을 위해서는 RAC Service, Preferred/Available Instance, JDBC/SCAN, 자동 Failover/Failback, Connection Pool 설정과의 연결을 추가 정의해야 한다.

본 문서는 제공된 장표를 기준으로 한 **NSIGHT Application-Database Node Mapping Working Baseline**으로 활용한다.
