# NSIGHT OLTP 및 대용량 배치 수행 방안 분석

## 0. 문서 개요

본 문서는 제공된 **「OLTP 및 대용량 배치 수행 방안」 장표(4.4 OLTP 및 대용량 배치 수행 방안)**를 기준으로,
NSIGHT 정보계의 **ADW / RDW 노드별 업무 배치, 대용량 배치와 일반 배치의 분리,
온라인(OLTP) 업무 수행 노드 분리, 마케팅플랫폼 및 OGG 수행 위치, Failover 기준**을 분석하여 정리한 문서이다.

> 원본 장표 핵심 문구  
> **“업무별 부하 및 기능적 요소를 고려하여 OLTP 및 대용량 배치의 부하 분산 아키텍처를 설계하며  
> ADW DB는 2노드를 한 그룹으로 지정하고 업무별 부하를 분산함”**

### 작성 원칙

- 원본 장표에서 직접 확인되는 내용은 **[FACT]**로 기록한다.
- 장표 구조에서 도출되는 아키텍처적 의미는 **[ANALYSIS]**로 구분한다.
- 원본 장표에서 확인되지 않는 실제 RAC Service명, DB Instance Affinity, 세션 분배방식 등은 임의로 확정하지 않는다.
- 상세 구현이 필요한 항목은 **확인 필요(GAP)** 로 관리한다.

---

# 1. 핵심 요약

| 구분 | 노드 | 주요 수행 업무 |
|---|---|---|
| ADW 대용량 배치 수행 Node | **ADW #1, #2** | ETL 대용량 배치, OLAP |
| ADW 일반 배치 수행 Node | **ADW #3, #4** | 일반 배치, BI포탈, 신용실적 |
| ADW Online 수행 Node | **ADW #5, #6** | 데이터흐름관리, 비즈메타, 데이터품질 |
| RDW 운영 Node | **RDW #1, #2** | 마케팅플랫폼, 미니싱글뷰, ETL 배치 |
| RDW OGG 수행 Node | **RDW #2** | OGG |
| RDW 대용량 배치 수행 | **RDW #2** | 마케팅플랫폼 계열 대용량 배치 |
| DR(안성) | 마케팅플랫폼 #51/#52, 미니싱글뷰 #51/#52 | DR 업무 구성 |

---

# 2. 전체 아키텍처 구조

```text
                  NSIGHT OLTP / BATCH WORKLOAD DISTRIBUTION

                              [의왕]
┌────────────────────────────────────────────────────────────────────────────┐
│                                                                            │
│  ETL #1,#2        신용실적 #1,#2      데이터흐름관리 #1   마케팅플랫폼 #1,#2 │
│  배치 AP #1       Self-BI #1          비즈메타/          미니싱글뷰 #1,#2   │
│  OLAP #1,#2       BI포탈 #1,#2        데이터품질 #1,#2                      │
│        │                │                    │                   │           │
│        ▼                ▼                    ▼                   ▼           │
│  ┌────────────┐   ┌────────────┐      ┌────────────┐      ┌────────────┐   │
│  │ ADW #1,#2  │   │ ADW #3,#4  │      │ ADW #5,#6  │      │ RDW #1,#2  │   │
│  │            │   │            │      │            │      │            │   │
│  │ 대용량배치 │   │ 일반배치   │      │ Online     │      │ Marketing  │   │
│  │ 수행 Node  │   │ 수행 Node  │      │ 수행 Node  │      │ / RDW      │   │
│  └────────────┘   └────────────┘      └────────────┘      └────────────┘   │
│   데이터플랫폼       BI포탈             데이터거버넌스      마케팅플랫폼        │
│                                                                            │
└────────────────────────────────────────────────────────────────────────────┘

                              [안성]
                    ┌────────────────────────────┐
                    │ 마케팅플랫폼 #51,#52       │
                    │ 미니싱글뷰 #51,#52         │
                    └────────────────────────────┘
```

---

# 3. 아키텍처 기본 원칙

## 3.1 [FACT] ADW는 2개 노드를 한 그룹으로 구성

원본 장표는 ADW 6개 노드를 다음과 같이 **2개 노드 단위 3개 그룹**으로 분리한다.

```text
ADW #1 + ADW #2
        ↓
대용량 배치 수행 Node

ADW #3 + ADW #4
        ↓
일반 배치 수행 Node

ADW #5 + ADW #6
        ↓
Online 수행 Node
```

즉 ADW 전체를 하나의 동일 부하 영역으로 사용하지 않고,
**업무 특성과 처리부하에 따라 RAC 노드를 역할별 그룹으로 분리**한다.

---

# 4. ADW 노드 그룹 구성

## 4.1 대용량 배치 수행 Node

### [FACT]

```text
ADW #1
ADW #2
```

원본 장표 하단 분류:

```text
대용량 배치 수행 Node
        ↓
데이터플랫폼
```

### 주요 연결 업무

- ETL #1, #2
- 배치 AP #1
- OLAP #1, #2

### 원본 설명

1. **ETL 서버 2대 중 1번 서버를 ADW 전용으로 사용**
2. ETL 배치
   - **대용량 배치는 ADW #1, #2에서 수행**
   - **일반 배치는 ADW #3, #4에서 수행**

### [ANALYSIS]

ADW #1/#2는 OLTP 또는 사용자 Online 질의보다
**CPU / I/O / TEMP / UNDO 사용량이 큰 배치와 OLAP성 workload를 우선 수용하는 전용 노드 그룹**으로 볼 수 있다.

---

# 5. 일반 배치 수행 Node

## 5.1 [FACT]

```text
ADW #3
ADW #4
```

원본 장표 하단 분류:

```text
일반 배치 수행 Node
        ↓
BI포탈
```

### 주요 연결 업무

- 신용실적 #1, #2
- Self-BI #1
- BI포탈 #1, #2

### 원본 설명

- **일반 배치는 ADW #3, #4에서 수행**
- **BI포탈, 신용실적은 ADW #3, #4에서 수행**

### [ANALYSIS]

이 그룹은 대용량 배치보다 상대적으로 안정적인
일반 배치와 BI 업무를 분리 수용함으로써
ADW #1/#2의 대용량 처리와 자원경합을 줄이려는 구조이다.

---

# 6. Online 수행 Node

## 6.1 [FACT]

```text
ADW #5
ADW #6
```

원본 장표 하단 분류:

```text
Online 수행 Node
        ↓
데이터 거버넌스
```

### 주요 연결 업무

- 데이터흐름관리 #1
- 비즈메타 / 데이터품질 #1, #2

### 원본 설명

- **데이터흐름관리, 비즈메타, 데이터품질은 ADW #5, #6에서 수행**

### [ANALYSIS]

ADW #5/#6는
대용량 배치 및 일반 배치와 분리된 **온라인성/관리성 workload 전용 그룹**이다.

즉 다음과 같이 물리적으로 부하영역을 분리한다.

```text
대용량 배치
     ↓
ADW #1,#2

일반 배치 / BI
     ↓
ADW #3,#4

Online / Data Governance
     ↓
ADW #5,#6
```

---

# 7. OLAP 수행 위치

## 7.1 [FACT]

원본 장표 설명:

> **OLAP은 배치성 업무로 ADW #1, #2에서 수행**

즉 OLAP은 사용자 화면 기반 기능이라 하더라도
이 설계에서는 **대용량/배치성 workload**로 분류된다.

```text
OLAP #1,#2
    │
    ▼
ADW #1,#2
```

### [ANALYSIS]

OLAP의 대규모 Scan/Join/Aggregation 특성을 고려하여
일반 Online DB 노드와 분리한 것으로 해석할 수 있다.

---

# 8. BI포탈 / 신용실적 수행 위치

## 8.1 [FACT]

원본 장표 설명:

> **BI포탈, 신용실적은 ADW #3, #4에서 수행**

```text
BI포탈 #1,#2
신용실적 #1,#2
      │
      ▼
ADW #3,#4
```

Self-BI #1 또한 동일 업무 박스에 배치되어 있다.

### [ANALYSIS]

BI포탈 계열을 #3/#4에 배치함으로써
OLAP·대용량 ETL과 동일 노드에서 경쟁하지 않도록 분리한 구조이다.

---

# 9. 데이터거버넌스 수행 위치

## 9.1 [FACT]

원본 장표 설명:

> **데이터흐름관리, 비즈메타, 데이터품질은 ADW #5, #6에서 수행**

```text
데이터흐름관리 #1
비즈메타/데이터품질 #1,#2
       │
       ▼
ADW #5,#6
```

이는 ADW의 6개 노드 중
**#5/#6를 Online/데이터거버넌스 업무 그룹으로 지정**한 구조이다.

---

# 10. ADW 대용량 배치용 자원 보강

## 10.1 [FACT]

원본 비고:

> **ADW #1, #2는 대용량 배치 수행을 위주로 수행하며,  
> Undo/Temp TS 용량 증설 및 Redo Log Member 추가**

즉 대용량 배치 노드는 단순히 서비스 라우팅만 분리하는 것이 아니라
DB Storage/Log 구조도 별도 보강 대상으로 정의한다.

```text
ADW #1,#2
  │
  ├─ 대용량 배치
  ├─ Undo TS 증설
  ├─ Temp TS 증설
  └─ Redo Log Member 추가
```

### [ANALYSIS]

이 구조는 대량 DML / Sort / Hash / Aggregate / Temp Spill 등으로 인해
배치 노드에서 발생할 수 있는
Undo / Temp / Redo 부하를 사전에 고려한 설계로 볼 수 있다.

---

# 11. ADW Failover 기준

## 11.1 [FACT]

원본 비고:

> **ADW의 Online용 Node의 Failover는 각 업무 그룹 기준으로 설정**

원본 예시:

```text
주노드 #1인 경우
→ 보조노드 #2 설정

주노드 #2인 경우
→ 보조노드 #1 설정
```

### [ANALYSIS]

핵심은 **노드 Failover를 전체 6노드 임의 분산이 아니라 동일 업무 그룹 내부의 Pair를 기준으로 처리**하려는 것이다.

즉 개념적으로:

```text
Group A
ADW #1 ↔ #2

Group B
ADW #3 ↔ #4

Group C
ADW #5 ↔ #6
```

와 같은 Pair 기준의 Failover 정책으로 해석할 수 있다.

> 다만 원본 예시는 #1/#2만 직접 표기하고 있으므로  
> #3↔#4, #5↔#6까지 동일 정책을 적용하는지는 상세 설계에서 확인해야 한다.

---

# 12. RDW 구성

## 12.1 [FACT]

RDW 영역은 다음 2개 노드로 표현된다.

```text
RDW #1
RDW #2
```

장표 하단 영역명은 다음과 같이 읽힌다.

```text
마케팅플랫폼
고객행동정보수집
```

### 주요 연결 업무

- 마케팅플랫폼 #1, #2
- 미니싱글뷰 #1, #2
- 안성 DR 마케팅플랫폼 #51, #52
- 안성 DR 미니싱글뷰 #51, #52

---

# 13. OGG 수행 위치

## 13.1 [FACT]

원본 장표 RDW 설명:

> **OGG는 RDW #2에서 수행**

```text
OGG
 │
 ▼
RDW #2
```

### [ANALYSIS]

앞 단계 OGG 구성도와 연결하면
RDW Target 측 OGG 처리 workload를 **RDW #2에 지정**한 것으로 볼 수 있다.

다만 다음은 본 장표에서 확인되지 않는다.

- Replicat만 RDW #2에서 수행하는지
- OGG Manager/AdminServer 포함 여부
- 장애 시 RDW #1로 자동 Failover되는지
- ACFS 공유 Trail과의 관계

---

# 14. 마케팅플랫폼 OLTP 수행 위치

## 14.1 [FACT]

원본 설명:

> **마케팅플랫폼은 AP #1, #2 기준 각 RDW #1, #2에서 수행**

구조:

```text
마케팅플랫폼 AP #1
        │
        ▼
      RDW #1

마케팅플랫폼 AP #2
        │
        ▼
      RDW #2
```

### [ANALYSIS]

마케팅플랫폼 AP와 RDW 노드를 Pair 형태로 매핑하여
Online workload를 분산시키려는 구조로 해석할 수 있다.

다만 애플리케이션이 실제로 개별 RAC Instance에 직접 연결하는지,
RAC Service를 통해 Instance Affinity를 부여하는지는 본 장표에서 확인할 수 없다.

---

# 15. RDW ETL 배치 수행 위치

## 15.1 [FACT]

원본 설명:

> **ETL 배치**
>
> - **RDW #1, #2에서 수행**
> - **대용량 배치는 RDW #2에서 수행**

따라서 RDW는 다음과 같이 배치 workload를 분리한다.

```text
일반 ETL 배치
   ↓
RDW #1 + RDW #2

대용량 ETL 배치
   ↓
RDW #2
```

### [ANALYSIS]

RDW #2는 다음 workload가 겹칠 가능성이 있다.

```text
RDW #2
├─ 마케팅플랫폼 AP #2
├─ OGG
├─ 일반 ETL
└─ 대용량 ETL
```

따라서 RDW #2는 **자원경합 검증이 특히 중요한 노드**로 볼 수 있다.

---

# 16. 의왕 / 안성 환경 구조

## 16.1 [FACT] 의왕

원본 장표의 주 처리 환경은 `의왕`으로 표시되어 있다.

의왕의 주요 업무 구성:

| 영역 | 업무 |
|---|---|
| 배치/OLAP | ETL #1,#2 / 배치 AP #1 / OLAP #1,#2 |
| BI | 신용실적 #1,#2 / Self-BI #1 / BI포탈 #1,#2 |
| 데이터거버넌스 | 데이터흐름관리 #1 / 비즈메타·데이터품질 #1,#2 |
| 마케팅 | 마케팅플랫폼 #1,#2 / 미니싱글뷰 #1,#2 |

## 16.2 [FACT] 안성

안성에는 다음 DR 업무가 표현되어 있다.

```text
마케팅플랫폼 #51,#52
미니싱글뷰 #51,#52
```

### [ANALYSIS]

안성은 DR용 마케팅계 서비스 영역으로 보이나,
본 장표 자체는 DR 전환절차나 DB DR 구성까지 설명하지 않는다.

---

# 17. 업무-DB 노드 매핑표

| 업무 | 구분 | Primary 수행 노드 |
|---|---|---|
| ETL 대용량 배치 | Batch | ADW #1,#2 |
| ETL 일반 배치 | Batch | ADW #3,#4 |
| OLAP | Batch성 | ADW #1,#2 |
| BI포탈 | BI | ADW #3,#4 |
| 신용실적 | BI | ADW #3,#4 |
| Self-BI | BI | 장표상 BI포탈 그룹 |
| 데이터흐름관리 | Online/관리 | ADW #5,#6 |
| 비즈메타 | Online/관리 | ADW #5,#6 |
| 데이터품질 | Online/관리 | ADW #5,#6 |
| 마케팅플랫폼 AP #1 | OLTP | RDW #1 |
| 마케팅플랫폼 AP #2 | OLTP | RDW #2 |
| OGG | CDC | RDW #2 |
| RDW 일반 ETL | Batch | RDW #1,#2 |
| RDW 대용량 ETL | Batch | RDW #2 |

---

# 18. 부하분산 아키텍처의 핵심 의도

## 18.1 Workload Isolation

이 설계의 가장 중요한 원칙은 다음과 같다.

```text
대용량 Batch
    ≠
일반 Batch
    ≠
Online
```

즉 모든 업무를 동일 RAC Node에 균등 배분하는 것이 아니라
**업무 특성과 부하 패턴에 따라 Node Group을 분리**한다.

## 18.2 DB 자원경합 최소화

특히 다음 자원을 서로 분리하고자 하는 구조이다.

- CPU
- Buffer Cache
- PGA
- TEMP
- UNDO
- Redo
- I/O
- DB Session
- Parallel Query

### [ANALYSIS]

OLAP/대량 ETL과 Online 업무를 분리함으로써
대량 SQL의 순간 부하가 온라인 응답시간에 직접 영향을 주는 것을 줄이려는 구조이다.

---

# 19. 논리 Workload Zone

원본 장표를 논리적인 workload zone으로 재구성하면 다음과 같다.

```text
ADW
│
├─ HEAVY BATCH ZONE
│   └─ ADW #1,#2
│      ├─ ETL 대용량
│      └─ OLAP
│
├─ GENERAL BATCH / BI ZONE
│   └─ ADW #3,#4
│      ├─ 일반 ETL
│      ├─ BI포탈
│      └─ 신용실적
│
└─ ONLINE / GOVERNANCE ZONE
    └─ ADW #5,#6
       ├─ 데이터흐름관리
       ├─ 비즈메타
       └─ 데이터품질
```

> `ZONE` 명칭은 원본 장표의 직접 용어가 아니라 **[ANALYSIS] 구조화 표현**이다.

RDW:

```text
RDW
│
├─ RDW #1
│   ├─ 마케팅플랫폼 AP #1
│   └─ 일반 ETL
│
└─ RDW #2
    ├─ 마케팅플랫폼 AP #2
    ├─ OGG
    ├─ 일반 ETL
    └─ 대용량 ETL
```

---

# 20. Architecture Rule 후보

| Rule ID | Rule | 상태 |
|---|---|---|
| `DB-WL-001` | ADW는 2개 노드를 하나의 업무 그룹으로 구성한다 | 장표 근거 |
| `DB-WL-002` | ADW #1,#2는 대용량 배치 수행 Node로 사용한다 | 장표 근거 |
| `DB-WL-003` | ADW #3,#4는 일반 배치 및 BI 업무 Node로 사용한다 | 장표 근거 |
| `DB-WL-004` | ADW #5,#6는 Online/데이터거버넌스 Node로 사용한다 | 장표 근거 |
| `DB-WL-005` | OLAP은 ADW #1,#2에서 수행한다 | 장표 근거 |
| `DB-WL-006` | BI포탈/신용실적은 ADW #3,#4에서 수행한다 | 장표 근거 |
| `DB-WL-007` | 데이터흐름/비즈메타/데이터품질은 ADW #5,#6에서 수행한다 | 장표 근거 |
| `DB-WL-008` | ADW #1,#2의 Undo/Temp를 증설하고 Redo Log Member를 추가한다 | 장표 근거 |
| `DB-WL-009` | Failover는 업무 Group 내부 Pair 기준으로 설정한다 | 장표 취지 |
| `DB-WL-010` | OGG는 RDW #2에서 수행한다 | 장표 근거 |
| `DB-WL-011` | 마케팅플랫폼 AP #1/#2는 각각 RDW #1/#2에 분산한다 | 장표 근거 |
| `DB-WL-012` | RDW 일반 ETL은 #1/#2에서 수행한다 | 장표 근거 |
| `DB-WL-013` | RDW 대용량 배치는 #2에서 수행한다 | 장표 근거 |
| `DB-WL-014` | 대용량 Batch와 Online Workload는 가능하면 동일 DB Node에서 경쟁시키지 않는다 | 분석 |
| `DB-WL-015` | 노드별 Resource Manager/Service 정책을 업무 그룹과 일치시켜야 한다 | 설계 필요 |

---

# 21. 주요 아키텍처 리스크

## 21.1 RDW #2 부하 집중

원본 배치 기준을 그대로 적용하면 RDW #2에는 다음이 집중된다.

```text
RDW #2
│
├─ 마케팅플랫폼 AP #2
├─ OGG
├─ 일반 ETL
└─ 대용량 ETL
```

### [ANALYSIS]

따라서 RDW #2에 대해서는 다음을 반드시 검증해야 한다.

- CPU 사용률
- DB Session
- DB Wait Event
- Redo 생성량
- OGG Apply Lag
- TEMP/UNDO
- I/O
- Online p95
- 배치 수행시간

---

# 22. Failover 시 리스크

ADW를 두 노드씩 Grouping하면 정상시에는 workload isolation 효과가 있지만,
한 노드 장애 시에는 Pair의 다른 노드에 업무가 집중될 수 있다.

예:

```text
정상
ADW #1 ← 50%
ADW #2 ← 50%

#1 장애
   ↓
ADW #2 ← 100%
```

따라서 설계 검증은 정상상태가 아니라 **N-1 상태 처리용량**까지 포함해야 한다.

---

# 23. 확인 필요 GAP

| GAP ID | 항목 | 현재 상태 |
|---|---|---|
| `GAP-WL-001` | ADW RAC Service명 | 미표기 |
| `GAP-WL-002` | 각 업무별 Service → Node Affinity | 미표기 |
| `GAP-WL-003` | Preferred / Available Instance | 미표기 |
| `GAP-WL-004` | ADW #3↔#4 Failover 정책 | 상세 미표기 |
| `GAP-WL-005` | ADW #5↔#6 Failover 정책 | 상세 미표기 |
| `GAP-WL-006` | RAC Load Balancing 사용 여부 | 미표기 |
| `GAP-WL-007` | DB Resource Manager Plan | 미표기 |
| `GAP-WL-008` | Consumer Group별 CPU 제한 | 미표기 |
| `GAP-WL-009` | Parallel Degree 기준 | 미표기 |
| `GAP-WL-010` | OLAP 최대 동시 실행수 | 미표기 |
| `GAP-WL-011` | ETL 배치 동시실행 정책 | 미표기 |
| `GAP-WL-012` | Undo TS 목표 용량 | 미표기 |
| `GAP-WL-013` | Temp TS 목표 용량 | 미표기 |
| `GAP-WL-014` | Redo Log Member 추가 수 | 미표기 |
| `GAP-WL-015` | 대용량 배치 판정 기준 | 미표기 |
| `GAP-WL-016` | 일반/대용량 배치 경계 | 미표기 |
| `GAP-WL-017` | RDW #2 OGG+Batch 동시수행 기준 | 미표기 |
| `GAP-WL-018` | RDW #2 장애 시 OGG Failover | 미표기 |
| `GAP-WL-019` | 마케팅 AP→RDW Instance 연결 상세 | 미표기 |
| `GAP-WL-020` | 안성 DR 전환 시 동일 workload mapping | 미표기 |
| `GAP-WL-021` | Batch Window | 미표기 |
| `GAP-WL-022` | Online SLA/p95 | 미표기 |
| `GAP-WL-023` | N-1 처리용량 | 미표기 |
| `GAP-WL-024` | Node Group별 성능 임계치 | 미표기 |

---

# 24. 권장 운영 모니터링 항목

아래는 장표의 직접 기재사항이 아니라
본 workload 분리구조를 운영하기 위한 **[ANALYSIS] 권장항목**이다.

| 영역 | 모니터링 |
|---|---|
| Node | CPU 사용률 |
| Node | Load Average |
| Oracle | Active Session |
| Oracle | DB Time |
| Oracle | Top Wait Event |
| Oracle | GC/RAC Wait |
| Oracle | Parallel Session |
| TEMP | 사용률 |
| UNDO | 사용량 / Retention |
| Redo | Redo Size / sec |
| Batch | 수행시간 |
| Batch | 동시 Job 수 |
| OLAP | Query Duration |
| OGG | Extract/Replicat Lag |
| Online | TPS |
| Online | p95 Response |
| Failover | Pair Node 잔여용량 |

---

# 25. 권장 검증 시나리오

| Test ID | 시나리오 | 검증 목적 |
|---|---|---|
| `WLT-001` | ADW #1 대용량 ETL | #1/#2 배치 처리성능 |
| `WLT-002` | OLAP + ETL 동시 수행 | Heavy Batch 경합 |
| `WLT-003` | BI포탈 Peak | ADW #3/#4 Online/BI 부하 |
| `WLT-004` | Data Governance Peak | ADW #5/#6 안정성 |
| `WLT-005` | ADW #1 장애 | #2 단독 처리용량 |
| `WLT-006` | ADW #3 장애 | #4 업무 지속 |
| `WLT-007` | ADW #5 장애 | #6 Online 지속 |
| `WLT-008` | RDW #1 장애 | 마케팅 AP #1 Failover |
| `WLT-009` | RDW #2 장애 | OGG + 대용량 Batch 영향 |
| `WLT-010` | OGG + RDW 대용량 Batch 동시 수행 | RDW #2 경합 |
| `WLT-011` | Undo/Temp Peak | 대용량 배치 Storage 검증 |
| `WLT-012` | DR 안성 전환 | 업무 Group Mapping 재현 |

---

# 26. 최종 Workload Big Picture

```text
                         NSIGHT DATABASE WORKLOAD

                            ┌──────── ADW ────────┐
                            │                    │
                     ┌──────┴──────┐             │
                     │             │             │
                     ▼             ▼             ▼

              ADW #1,#2      ADW #3,#4      ADW #5,#6
              Heavy Batch    General Batch   Online
              / OLAP         / BI            / Governance

              ETL            BI포탈          데이터흐름
              OLAP           신용실적        비즈메타
                              Self-BI         데이터품질

                     │             │             │
                     └─────────────┴─────────────┘
                               │
                         Workload Isolation


                            ┌──────── RDW ────────┐
                            │                    │
                     ┌──────┴──────┐             │
                     ▼             ▼             │

                   RDW #1         RDW #2
                     │             │
              Marketing AP #1  Marketing AP #2
              일반 ETL        일반 ETL
                              대용량 ETL
                              OGG

```

---

# 27. 결론

이번 「OLTP 및 대용량 배치 수행 방안」 장표에서 확인되는 핵심은 다음과 같다.

1. **ADW 6개 노드를 2개씩 3개 업무 그룹으로 분리**한다.
2. **ADW #1/#2는 대용량 배치 및 OLAP 전용 성격**으로 사용한다.
3. **ADW #3/#4는 일반 배치, BI포탈, 신용실적 업무**를 수행한다.
4. **ADW #5/#6는 데이터흐름관리, 비즈메타, 데이터품질 등 Online/데이터거버넌스 업무**를 수행한다.
5. **대용량 배치 노드 #1/#2는 Undo/Temp TS 증설 및 Redo Log Member 추가**를 고려한다.
6. ADW Failover는 **업무 그룹 단위 Pair 기준**으로 설정하는 방향을 가진다.
7. 마케팅플랫폼 AP #1/#2는 각각 **RDW #1/#2에 분산**하여 수행한다.
8. **OGG는 RDW #2에서 수행**한다.
9. RDW 일반 ETL은 #1/#2에서 수행하지만, **대용량 배치는 RDW #2에서 수행**한다.
10. 이로 인해 RDW #2에는 OGG·마케팅 AP·일반 ETL·대용량 ETL이 겹칠 수 있으므로 **성능 및 장애 상태의 자원경합 검증이 중요**하다.
11. 전체 설계의 핵심은 **OLTP / 일반 Batch / 대용량 Batch를 업무 특성에 따라 물리 DB Node Group으로 분리하여 자원경합을 최소화하는 것**이다.

본 문서는 제공된 장표를 기준으로 한 **NSIGHT OLTP / Batch Workload Distribution Architecture Working Baseline**으로 활용한다.
