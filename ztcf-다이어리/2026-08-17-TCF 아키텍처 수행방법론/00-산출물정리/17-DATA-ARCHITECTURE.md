# 17. NSIGHT Data Architecture

## 1. 목적

NSIGHT의 데이터 아키텍처는 단순 DB 구성도가 아니라 **데이터 목적, 소유권, 흐름, 지연시간, 처리유형, 자원격리**를 강제하는 정책으로 정의한다.

전략 자료에서 반복적으로 확인되는 핵심은 다음이다.

```text
Data-Centric
+
FAST / DEEP 분리
+
RDW / ADW 물리 분리
+
CDC / Kafka / ETL 전용 경로
+
온라인 / 배치 자원격리
```

---

## 2. Data Zone

```text
                    SOURCE / OPERATIONAL
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
       CDC             Event Capture       Batch/File
        │                  │                  │
        ▼                  ▼                  ▼
      RDW               Kafka             ETL/Batch
        │                  │                  │
        │ FAST             │ FAST             │ DEEP
        │                  │                  │
        ├──────────┐       ▼                  ▼
        │          │   Real-time AP       DataStage
        │          │       │                  │
        ▼          ▼       ▼                  ▼
 Single View   Marketing Response          ADW
                                           │
                                           ▼
                                    BI / OLAP / Analysis
```

---

## 3. Core Data Role

| 영역 | 핵심 역할 | 주요 Consumer | 정책 |
|---|---|---|---|
| Source/Operational | 원천 업무 데이터 | CDC/ETL | 직접 부하 최소화 |
| RDW | Near Real-time 조회/온라인 지원 | Single View, Marketing | 온라인 조회 우선 |
| ADW | 대용량 분석/정제 데이터 | BI, OLAP, Analytics | 분석 전용 |
| Kafka | 고객행동 이벤트 고속 전달 | Real-time/Event AP | FAST Lane |
| CDC | 원천 변경분 캡처/중계 | RDW | 원천 DB 부하 분산 |
| ETL/DataStage | 정제/변환/대량 이동 | ADW | DEEP Lane |
| Batch | 주기/대량 업무 | RDW/ADW | 온라인과 자원분리 |
| IMDG | Session/Cache 등 | Online Runtime | DB와 역할분리 |
| Migration Stage | 이행 중간 격리 | Migration Engine | 운영과 분리 |

---

## 4. [DECISION] RDW / ADW 물리 분리

전략 자료의 핵심 설계 정책이다.

```text
RDW
= Online / Near Real-time Read

ADW
= Large Analytics / BI / ETL Result
```

금지:

```text
분석 쿼리 → RDW에 무제한 실행
대량 ETL → 온라인 조회와 동일 자원 경쟁
```

목적:

- 분석 부하가 온라인 거래 성능에 전파되는 것을 차단
- 각 Workload별 Capacity/IO/Session 독립 관리
- 장애 영향 범위 분리

---

## 5. FAST Track

전략 자료상 FAST는 고객 행동 발생에 즉시 반응하기 위한 경로이다.

```text
Customer Behavior
      ↓
Event Capture
      ↓
Kafka
      ↓
Real-time Processing AP
      ↓
Marketing Decision / Offering
```

NFR 예시:

- Marketing Offering: 1초 이내
- Event 처리 SLA 강제

이 값은 일반 온라인 p95 3초 기준과 Scope가 다르므로 혼합하지 않는다.

---

## 6. DEEP Track

```text
Operational Source
      ↓
CDC
      ↓
RDW
      ↓
DataStage / ETL
      ↓
ADW
      ↓
BI / OLAP / Analytics
```

CDC 통합 목표는 전략 자료에서 Near Real-time / 30초 이내 기준이 제시되어 있다.

---

## 7. No P2P / Data Flow Control

전략 브리핑의 Integration 통제 원칙:

- DB Link 금지
- 시스템 간 연계는 통제된 API/Integration 경계 사용
- 파일 연계와 대외 연계는 별도 표준 경로 사용
- 데이터 흐름 경로를 단일화/강제화

Data Architecture 관점에서 이는 다음을 의미한다.

```text
Domain A
  X→ Domain B Table Direct Update

Domain A
  → Published Service/API
  → Domain B
  → Domain B Owned Data
```

---

## 8. Data Ownership Model

각 Table/View/Data Product는 다음 Metadata를 가져야 한다.

| 필드 | 설명 |
|---|---|
| Data ID | Table/View/Data Product ID |
| Owner System | MP/RD/AD/DG/BL/IM 등 |
| Owner Domain | MG/MK/... |
| Physical DB | RDW/ADW/기타 |
| Write Owner | 변경 책임 |
| Read Consumer | 조회 가능 Consumer |
| Source | 원천 |
| Freshness | 지연 허용 |
| Retention | 보존기간 |
| Classification | 개인정보/민감정보 등 |
| Masking | 마스킹 정책 |
| HA/DR | 가용성 |
| Evidence | 근거 |

현재 전체 Owned Table/View Catalog는 아직 완성되지 않았다.

판정:

```text
[G20-C02 / G50 P0]
Domain별 Owned Table/View Catalog 필요
```

---

## 9. Data Security

데이터 접근은 Authentication만으로 허용하지 않는다.

```text
Identity
 ↓
Role / Auth Group
 ↓
ServiceId Permission
 ↓
Data Scope
 ↓
Masking / Audit
```

반드시 분리할 정책:

- 개인정보 원문 접근권한
- 마스킹 결과 접근
- 운영자/개발자 데이터 접근
- Migration 데이터 접근
- Audit Trail

---

## 10. Runtime Data Protection

### RDW 보호

- Hikari Pool 상한
- Slow SQL
- Query Timeout
- Transaction Timeout
- 분석성 쿼리 차단/이관

### ADW 보호

- 대량 쿼리 Resource 관리
- ETL Batch Window
- BI/OLAP Concurrency

### Source DB 보호

- CDC 중계
- Migration Extraction Throttle
- 직접 대량 조회 제한

---

## 11. Data Observability

최소 수집:

```text
CDC Lag
Kafka Consumer Lag
ETL Start/End/Rows/Error
RDW Session/SQL/Wait
ADW Query/IO/Concurrency
Batch Duration
Migration Throughput
Data Quality Error Count
```

---

## 12. Data Architecture GAP

| ID | GAP | 우선순위 |
|---|---|---:|
| DATA-G01 | Domain/Table/View Owner Catalog 미완성 | P0 |
| DATA-G02 | RDW/ADW Read/Write Matrix 미완성 | P0 |
| DATA-G03 | CDC/Kafka/ETL 상세 Interface Registry 미완성 | P0 |
| DATA-G04 | Data Freshness SLA 전수 Catalog 미완성 | P1 |
| DATA-G05 | 개인정보/마스킹 Data Classification 전수화 필요 | P0 |
| DATA-G06 | Runtime Data Metric → Gate 연결 미완성 | P1 |

---

## 13. G50 Data 판정

**CONDITIONAL PASS**

전략적 Data Boundary와 물리 분리 원칙은 명확하지만, 데이터 소유권·실제 Table/View·Interface·SLA의 전수 Catalog가 아직 필요하다.
