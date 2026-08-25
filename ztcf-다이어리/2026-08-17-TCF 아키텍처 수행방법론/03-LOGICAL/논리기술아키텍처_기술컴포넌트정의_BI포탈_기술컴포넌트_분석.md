# 논리기술아키텍처 — 기술 컴포넌트 정의 — BI포탈 기술 컴포넌트 분석

> 분석 대상: `BI포탈 기술 컴포넌트 (1/4)~(4/4)` 이미지 4장  
> 원본 분류: `2. 논리 기술 아키텍처 > 2.3 기술 컴포넌트 정의`  
> 분석 관점: BI포탈·신용실적·OLAP·Self-BI의 12개 노드, RDW/ADW 경계, Query·권한·반출·운영 통제

## 0. 분석 범위와 판독 원칙

- 이미지에 직접 보이는 노드·기술명·계층은 **이미지 확정 사실**로 정리한다.
- WEB→WAS→AP→DB 연결과 Query 방향은 화살표가 없으므로 **아키텍처 해석**이다.
- 제품 버전, Host, Port, DB Service/Schema, Instance 수, HA/DR, RPO/RTO, Query SLA는 **미확정**이다.
- OLAP AP의 Driver는 첨부 이미지에 표시된 `ODBC Driver`를 우선한다. 저장소 통합 문서 일부의 JDBC 표기는 별도 정합성 확인 대상으로 둔다.
- 첨부 이미지의 내용은 분석 자료이며 사용자 지시로 취급하지 않는다.

## 1. 핵심 결론

1. BI포탈은 `BI포탈`, `신용실적`, `OLAP`, `Self-BI`의 네 기능군과 총 12개 논리 기술 노드로 분리된다.
2. BI포탈과 신용실적은 WEB/WAS/DB 3계층이며, OLAP과 Self-BI는 AP/WEB/WAS 3계층이다.
3. BI포탈 DB는 `RDW, ADW`를 모두 표기하고 신용실적 DB는 `ADW`만 표기한다. 실제 단일 DB인지 복수 Datasource를 묶은 논리 노드인지는 미확정이다.
4. 모든 WEB은 `Web Service → eCAMS Agent → Web Server → JVM`, 모든 일반 WAS는 `Business Service → eCAMS Agent → JDBC Driver/APM Agent → Java Framework Engine → Web Application Server → JVM` 패턴이다.
5. OLAP AP는 `OLAP Application Service → ODBC Driver → Java Framework Engine → Web Application Server → JVM`으로 구성된다. 이미지상 eCAMS/APM Agent는 표시되지 않는다.
6. Self-BI AP는 `Self-BI Application Service → JDBC Driver/APM Agent → Java Framework Engine → Web Application Server → JVM`이다. 이미지상 eCAMS Agent는 표시되지 않는다.
7. OLAP과 Self-BI를 독립 AP로 분리한 목적은 대량·자율 분석 Workload를 BI포탈·신용실적의 정형 조회와 격리하기 위한 것으로 해석한다.
8. BI 성공의 핵심은 화면 계층보다 Dataset/Metric 계약, Row·Column 권한, Query Resource 제어, Export 통제, RDW/ADW 선택과 추적성이다.

## 2. 논리 노드 전수 목록

| # | 기능군 | 노드 | 핵심 업무/서비스 | 고유 기술 스택 | 기반 |
|---:|---|---|---|---|---|
| 1 | BI포탈 | BI포탈 WEB | Web Service | eCAMS Agent, Web Server, JVM | OS→VM→Cloud |
| 2 | BI포탈 | BI포탈 WAS | Business Service | eCAMS Agent, JDBC Driver, APM Agent, Java Framework Engine, Web Application Server, JVM | OS→VM→Cloud |
| 3 | BI포탈 | BI포탈 DB(RDW, ADW) | 업무 데이터 | DBSafer, DBMS | OS→DB 전용 Appliance |
| 4 | 신용실적 | 신용실적 WEB | Web Service | eCAMS Agent, Web Server, JVM | OS→VM→Cloud |
| 5 | 신용실적 | 신용실적 WAS | Business Service | eCAMS Agent, JDBC Driver, APM Agent, Java Framework Engine, Web Application Server, JVM | OS→VM→Cloud |
| 6 | 신용실적 | 신용실적 DB(ADW) | 업무 데이터 | DBSafer, DBMS | OS→DB 전용 Appliance |
| 7 | OLAP | OLAP AP | OLAP Application Service | ODBC Driver, Java Framework Engine, Web Application Server, JVM | OS→VM→Cloud |
| 8 | OLAP | OLAP WEB | Web Service | eCAMS Agent, Web Server, JVM | OS→VM→Cloud |
| 9 | OLAP | OLAP WAS | Business Service | eCAMS Agent, JDBC Driver, APM Agent, Java Framework Engine, Web Application Server, JVM | OS→VM→Cloud |
| 10 | Self-BI | Self-BI AP | Self-BI Application Service | JDBC Driver, APM Agent, Java Framework Engine, Web Application Server, JVM | OS→VM→Cloud |
| 11 | Self-BI | Self-BI WEB | Web Service | eCAMS Agent, Web Server, JVM | OS→VM→Cloud |
| 12 | Self-BI | Self-BI WAS | Business Service | eCAMS Agent, JDBC Driver, APM Agent, Java Framework Engine, Web Application Server, JVM | OS→VM→Cloud |

## 3. 네 장의 구조 비교

| 구분 | 1/4 BI포탈 | 2/4 신용실적 | 3/4 OLAP | 4/4 Self-BI |
|---|---|---|---|---|
| 실행 구조 | WEB/WAS/DB | WEB/WAS/DB | AP/WEB/WAS | AP/WEB/WAS |
| DB 표기 | RDW, ADW | ADW | 별도 DB 노드 미표시 | 별도 DB 노드 미표시 |
| AP Driver | 해당 없음 | 해당 없음 | ODBC Driver | JDBC Driver |
| AP APM | 해당 없음 | 해당 없음 | 이미지상 미표시 | APM Agent 표시 |
| AP eCAMS | 해당 없음 | 해당 없음 | 이미지상 미표시 | 이미지상 미표시 |
| 목적 해석 | 통합·정형 BI | 신용실적 정형 조회 | 다차원 분석 | 사용자 자율 분석 |

## 4. 텍스트 아키텍처 그림

### 4.1 전체 12개 노드

```text
┌──────────────────────────── BI포탈 ─────────────────────────────┐
│                                                                │
│ [BI포탈 WEB] → [BI포탈 WAS] → [BI포탈 DB(RDW, ADW)]           │
│                                                                │
│ [신용실적 WEB] → [신용실적 WAS] → [신용실적 DB(ADW)]          │
│                                                                │
│ [OLAP WEB] → [OLAP WAS] → [OLAP AP/ODBC] → [분석 DB 후보]      │
│                                                                │
│ [Self-BI WEB] → [Self-BI WAS] → [Self-BI AP/JDBC] → [DB 후보] │
│                                                                │
└────────────────────────────────────────────────────────────────┘

주의: 화살표와 OLAP/Self-BI DB 연결 대상은 이미지에 없는 구조 해석이다.
```

### 4.2 BI포탈 3계층

```text
[BI포탈 WEB]
 Web Service → eCAMS Agent → Web Server → JVM
                          │
                          ▼
[BI포탈 WAS]
 Business Service → eCAMS Agent
 → JDBC Driver + APM Agent
 → Java Framework Engine → Web Application Server → JVM
                          │
                    ┌─────┴─────┐
                    ▼           ▼
             [RDW 업무 데이터] [ADW 업무 데이터]
                    └→ DBSafer → DBMS → Appliance
```

### 4.3 신용실적 3계층

```text
[신용실적 WEB]
 Web Service → eCAMS Agent → Web Server → JVM
                          │
                          ▼
[신용실적 WAS]
 Business Service → eCAMS Agent
 → JDBC Driver + APM Agent
 → Java Framework Engine → Web Application Server → JVM
                          │
                          ▼
[신용실적 DB(ADW)]
 업무 데이터 → DBSafer → DBMS → OS → DB 전용 Appliance
```

### 4.4 OLAP 세 노드

```text
[OLAP WEB]
 Web Service → eCAMS Agent → Web Server → JVM
                    │
                    ▼
[OLAP WAS]
 Business Service → eCAMS Agent → JDBC Driver + APM Agent
 → Java Framework Engine → Web Application Server → JVM
                    │ 분석 요청·Session·Metadata
                    ▼
[OLAP AP]
 OLAP Application Service → ODBC Driver
 → Java Framework Engine → Web Application Server → JVM
                    │
                    └→ Cube/Semantic Model/분석 DB 후보
```

### 4.5 Self-BI 세 노드

```text
[Self-BI WEB]
 Web Service → eCAMS Agent → Web Server → JVM
                    │
                    ▼
[Self-BI WAS]
 Business Service → eCAMS Agent → JDBC Driver + APM Agent
 → Java Framework Engine → Web Application Server → JVM
                    │ Dataset·권한·공유·Export
                    ▼
[Self-BI AP]
 Self-BI Application Service → JDBC Driver + APM Agent
 → Java Framework Engine → Web Application Server → JVM
                    │
                    └→ 승인 Dataset/DB 후보
```

### 4.6 WEB 표준 노드

```text
┌──────── WEB 표준 ────────┐
│ Web Service              │
│ eCAMS Agent              │
│ Web Server               │
│ JVM                      │
├──────────────────────────┤
│ 백신 | 계정 | 보안 | 개인정보
│ 백업 | 관제 | 모니터링 | 운영관리
├──────────────────────────┤
│ OS → VM → Cloud          │
└──────────────────────────┘

적용: BI포탈 WEB, 신용실적 WEB, OLAP WEB, Self-BI WEB
```

### 4.7 WAS 표준 노드

```text
┌──────────── WAS 표준 ────────────┐
│ Business Service                 │
│ eCAMS Agent                      │
│ JDBC Driver       | APM Agent    │
│ Java Framework Engine            │
│ Web Application Server           │
│ JVM                              │
├──────────────────────────────────┤
│ 공통 시스템 8종 → OS → VM → Cloud│
└──────────────────────────────────┘

적용: BI포탈 WAS, 신용실적 WAS, OLAP WAS, Self-BI WAS
```

### 4.8 DB 표준 노드

```text
┌──────── BI DB 표준 ──────────┐
│ 업무 데이터                  │
│ DBSafer                      │
│ DBMS                         │
├──────────────────────────────┤
│ 백신 | 계정 | 보안 | 개인정보│
│ 백업 | 관제 | 모니터링 | 운영관리
├──────────────────────────────┤
│ OS → DB 전용 Appliance       │
└──────────────────────────────┘

적용: BI포탈 DB(RDW, ADW), 신용실적 DB(ADW)
```

### 4.9 RDW·ADW Query 분류

```text
[BI Query Request]
        │ Query Classifier
        ├─ 최신성·짧은 정형 조회 ─→ [RDW]
        │                            └→ 짧은 Timeout·높은 동시성
        └─ 이력·집계·대량 분석 ───→ [ADW]
                                     └→ Queue·Quota·Temp/Scan 제한

이미지 사실: BI포탈 DB는 RDW·ADW, 신용실적 DB는 ADW
```

### 4.10 OLAP ODBC와 Self-BI JDBC

```text
OLAP AP                         Self-BI AP
   │ ODBC Driver                   │ JDBC Driver
   ▼                               ▼
[ODBC DSN/Driver Manager]       [JDBC DataSource/Pool]
   │                               │
   ▼                               ▼
[Cube/분석 Engine DB 후보]      [승인 Dataset DB 후보]

공통 계약: DB Service, 인증, TLS, Timeout, Query ID, Read-only
차이 검증: Driver bitness/version, Encoding, Timezone, Pool, Failover
```

### 4.11 Dataset 발행·소비

```text
[RDW/원천]
    │ ETL·집계
    ▼
[ADW Stage]
    │ 품질: 건수·합계·Null·중복·기준시점
    ▼
[Dataset Version N 원자적 발행]
    │
    ├→ BI포탈 정형 Dashboard
    ├→ 신용실적 보고서
    ├→ OLAP Cube/Semantic Model
    └→ Self-BI 승인 Dataset
```

### 4.12 사용자·데이터 권한

```text
[SSO User]
    │ User→Role
    ▼
[BI Menu/Function 권한]
    │ Role→Dataset
    ▼
[Row Filter + Column Masking]
    │ Dataset→Metric/View
    ▼
[Query 실행]
    │
    ├→ 화면 조회
    ├→ 공유/예약
    └→ Export 승인·워터마크·만료·감사
```

### 4.13 Query 부하 격리

```text
BI포탈/신용실적 ─→ [Short Query Pool] ─→ RDW/ADW 정형 View
OLAP             ─→ [OLAP Queue/Pool] ─→ Cube/ADW
Self-BI          ─→ [User Quota/Pool] ─→ 승인 Dataset
ETL/Refresh      ─→ [Batch Resource] ──→ Stage/Publish

격리 단위: Connection Pool | Resource Group | Queue | Timeout | Scan | Temp
```

### 4.14 관측성과 E2E 추적

```text
User/Session
   → WEB requestId
   → WAS transactionId/APM Trace
   → AP queryId/semanticModel
   → JDBC/ODBC datasourceId
   → DB session/sqlId
   → Dataset/metricVersion
   → Export/reportId

각 ID를 시간동기화된 Log·Metric·Audit에 연결한다.
```

### 4.15 장애·저하 운영

```text
WEB 장애     → Health 제외·Route 전환
WAS 장애     → Session 복구/재인증·Query 중복 방지
OLAP AP 장애 → 정형 BI 유지·Cube 이전본 제공
Self-BI 장애 → 자율 분석만 격리
RDW 장애     → 최신성 조회 제한·갱신시각 표시
ADW 장애     → 이전 Dataset 제공·ETL/분석 지연
DBSafer 장애 → 승인된 접속 Fail 정책·비상절차
```

## 5. BI포탈 WEB/WAS/DB 상세 분석

### 5.1 BI포탈 WEB

- Web Service는 통합 포털 화면·정적 자원·Web 요청 진입점이다.
- eCAMS Agent는 구성·배포·상태 관리 지원 역할로 해석한다.
- Web Server는 TLS, Proxy, Routing, Cache, Health Check를 담당한다.
- JVM은 Web Runtime 또는 관리 Agent Runtime의 실행 경계로 보이나 제품 조합은 미확정이다.

### 5.2 BI포탈 WAS

- Business Service는 메뉴, Dashboard, 보고서, Dataset 탐색, 권한 조합을 수행한다.
- JDBC Driver를 통한 RDW/ADW Datasource는 별도 계정·Pool·Timeout으로 분리해야 한다.
- APM Agent는 사용자→Service→SQL Trace를 제공하되 SQL Parameter·개인정보를 마스킹한다.
- Java Framework Engine은 인증 연계, 예외, Transaction, Logging, 데이터 접근 표준을 제공한다.

### 5.3 BI포탈 DB(RDW, ADW)

- 이미지 표기는 한 DB 노드 제목에 RDW와 ADW를 병기한다.
- 이는 두 물리 DB를 논리적으로 묶은 표현일 수 있으므로 단일 DB라고 단정하지 않는다.
- RDW는 최신성 중심, ADW는 분석성 중심으로 Query를 분류하는 것이 합리적이다.
- DBSafer·DBMS·Appliance는 각각 접근통제, 데이터 관리, 하부 인프라 경계다.

## 6. 신용실적 WEB/WAS/DB 상세 분석

- 기술 스택은 BI포탈 WEB/WAS와 동일하며 업무·배포·장애 경계는 별도다.
- 신용실적 DB는 이미지상 ADW이므로 이력·집계 기반 정형 실적 조회 구조로 해석한다.
- 보고서에 기준일, 집계버전, 최종 갱신시각, 확정/정정 상태를 표시해야 한다.
- 조직·직무·정보등급에 따라 Row/Column 권한과 Masking을 적용한다.
- 수치 정정 시 이전 집계본·변경사유·승인·재발행 이력을 보존한다.

## 7. OLAP AP/WEB/WAS 상세 분석

### 7.1 OLAP AP

- OLAP Application Service는 Cube, Semantic Model, Cache, 다차원 Query 실행계로 해석한다.
- 이미지의 DB 연결은 `ODBC Driver`이며 Driver/DSN/bitness/Encoding/Timezone/Failover 호환성을 검증해야 한다.
- 이미지상 eCAMS·APM Agent가 없으므로 운영관리·성능추적 대체 수단 또는 장표 누락 여부를 확인한다.
- AP가 Web Application Server/JVM 위에 있으므로 세션·Thread·Heap·Cache 용량을 함께 산정한다.

### 7.2 OLAP WEB/WAS

- WEB은 Pivot·Chart·탐색 UI, WAS는 Session·권한·Metadata·요청 조합을 담당한다.
- WAS의 JDBC는 Metadata/관리 DB 또는 업무 DB 연결일 수 있으므로 AP의 ODBC와 목적을 구분해야 한다.
- OLAP AP 장애가 BI포탈·신용실적에 전파되지 않도록 Queue, Pool, Route, 배포단위를 분리한다.

## 8. Self-BI AP/WEB/WAS 상세 분석

### 8.1 Self-BI AP

- Self-BI Application Service는 사용자가 선택한 Dataset과 Metric을 실행하는 분석 계층이다.
- JDBC Driver와 APM Agent가 표시되므로 Query·SQL·외부 호출·성능 추적이 가능하다.
- 이미지상 eCAMS Agent는 없으므로 배포·구성관리 연계 방식과 관측 공백을 확인한다.
- 사용자별 CPU·Memory·Query Time·Scan Volume·동시성·Export 용량 Quota가 필요하다.

### 8.2 Self-BI WEB/WAS

- WEB은 분석 화면, WAS는 인증·Dataset 탐색·공유·권한·Export·Session을 관리한다.
- 임의 SQL은 승인 Schema/View에만 허용하고 DDL/DML과 시스템 Catalog 무제한 조회를 금지한다.
- 생성 SQL, Dataset, Metric, 공유, 예약, Export 이력을 감사한다.

## 9. 기술 스택 비교와 계약

| 기술 컴포넌트 | 적용 노드 | 핵심 계약 | 주요 위험 |
|---|---|---|---|
| Web Server | 4개 WEB | TLS, Route, Cache, Health | Route·Timeout 불일치 |
| Web Application Server | 4개 WAS+2개 AP | Thread, Session, Deploy, Pool | Heap/Thread/Pool 고갈 |
| Java Framework Engine | WAS·AP | 인증·예외·Transaction·Logging | Version 파편화 |
| JDBC Driver | 일반 WAS, Self-BI AP | Datasource, Pool, TLS, Timeout | DBMS 호환·Pool 고갈 |
| ODBC Driver | OLAP AP | DSN, Driver Manager, bitness | 환경별 DSN Drift |
| APM Agent | 일반 WAS, Self-BI AP | Trace, SQL, Masking | Overhead·민감정보 수집 |
| eCAMS Agent | WEB·일반 WAS | 구성·배포·상태 | AP 관리 공백·권한 과다 |
| DBSafer | BI포탈·신용실적 DB | DB 접속·정책·감사 | 우회 접속·감사 결측 |

## 10. 데이터·지표 계약

| 계약 영역 | 필수 항목 |
|---|---|
| Dataset | datasetId, owner, schemaVersion, source, 기준일, freshness |
| Metric | metricId, 산식, 단위, grain, 포함·제외 조건, version |
| Query | datasourceId, timeout, maxRows, maxScan, resourceGroup |
| 권한 | role, rowFilter, columnMasking, exportPolicy |
| 품질 | null, duplicate, 참조 무결성, 건수·합계·허용오차 |
| 발행 | datasetVersion, atomic publish, 이전본, rollback |
| 추적 | correlationId, queryId, sqlId, reportId, userId |

같은 지표를 BI포탈·신용실적·OLAP·Self-BI에서 사용할 경우 공통 Semantic Layer와 Metric Version을 사용해야 한다.

## 11. 보안·개인정보·반출 통제

1. 메뉴 권한, Dataset 권한, Row/Column 권한, Export 권한을 분리한다.
2. RDW/ADW Account는 Service·OLAP·Self-BI·ETL·Admin별로 분리하고 Read-only를 기본으로 한다.
3. 고객·신용정보는 화면·SQL Parameter·APM·Log·Cache·Export·Backup까지 동일하게 Masking한다.
4. 대량 Export는 승인, 목적, 대상, 용량, 암호화, 워터마크, 만료, 다운로드 감사를 적용한다.
5. Self-BI의 임의 SQL은 허용 Function·Schema·View·실행시간·결과 건수를 제한한다.
6. DBSafer와 Application Audit를 userId·sessionId·queryId로 연결한다.

## 12. 성능·용량·Workload 격리

### 12.1 계층별 용량

```text
동시 사용자 × 화면당 Query × Query 시간 × Scan 데이터량
  → Web Connection
  → WAS Thread/Heap/Session
  → AP Queue/Cache/Worker
  → JDBC/ODBC Connection
  → RDW/ADW Session/CPU/IO/Temp
  → Export File/Network/Storage
```

### 12.2 격리 원칙

- BI포탈·신용실적 정형 Query는 짧은 Timeout과 우선 Resource Group을 사용한다.
- OLAP은 Cube/Cache·Queue·ODBC Connection을 독립 관리한다.
- Self-BI는 사용자·조직별 Query Quota와 Kill/Cancel 정책을 적용한다.
- ETL·Cube Refresh·통계 수집은 온라인 Peak와 실행창을 분리한다.
- AP/WAS/DB의 Pool 수를 독립 산정하지 말고 E2E 동시성 예산으로 맞춘다.

## 13. 장애·저하·재처리 메커니즘

| 장애 | 영향 | 복구·저하 원칙 |
|---|---|---|
| BI포탈 WEB/WAS | 통합 접점 중단 | 다중 Instance, Health 제외, 무상태화 |
| 신용실적 WAS | 실적 조회 중단 | 기능군 격리, 최종 보고서 제공 |
| OLAP AP | Cube·다차원 분석 중단 | 이전 Cube/Cache, 정형 BI 유지 |
| Self-BI AP | 자율 분석 중단 | 해당 기능만 격리, 실행 Query 정리 |
| JDBC/ODBC 장애 | DB Query 실패 | 제한 Retry, Pool 보호, 명확한 오류 |
| RDW 장애 | 최신 조회 저하 | 갱신시각·부분 응답·복구 우선 |
| ADW 장애 | 분석·실적 지연 | 이전 Dataset 제공·ETL 재개 |
| DBSafer 장애 | 접속 또는 감사 영향 | 승인 Fail 정책·비상계정·사후감사 |
| Dataset 실패 | 수치 불일치 | 미완성본 미발행, 이전 Version 유지 |

## 14. 관측성·통합관제

| 영역 | 주요 지표 |
|---|---|
| WEB | Request, 4xx/5xx, TLS, Route, Connection, p95/p99 |
| WAS | TPS, Error, Thread, Heap, Session, JDBC Pool |
| OLAP AP | ODBC Session, Cube/Cache, Queue, Query, Refresh |
| Self-BI AP | JDBC Pool, Query, Scan, Quota, Export, Cancel |
| DB | Session, Lock, Wait, SQL, I/O, Temp, Dataset Freshness |
| Agent | eCAMS/APM Heartbeat, Version, Trace 수집 결측 |
| 공통 | CPU, Memory, Disk, Network, Process, Backup, Security |

`userId→requestId→transactionId→queryId→sqlId→datasetVersion→reportId`를 연결해 성능·오류·데이터 결과를 하나의 E2E Trace로 분석해야 한다.

## 15. 배포·변경관리

```text
[Source Commit]
     │
     ▼
[WEB/WAS/AP Build + Unit/Security Test]
     │
     ├→ JDBC/ODBC Driver 호환성
     ├→ DB Schema/View Migration
     ├→ Dataset/Metric Contract
     ├→ OLAP Cube/Semantic Model
     └→ Self-BI 권한/Quota Policy
     │
     ▼
[통합·회귀·성능·권한·Export 검증]
     │
     ▼
[승인 → 동일 Artifact 승격 → Health → 증적]
```

- WEB/WAS/AP Artifact와 DB/Dataset/Cube/Metric Version의 호환 순서를 Release Plan으로 관리한다.
- OLAP ODBC DSN과 Self-BI JDBC Datasource는 환경별 설정으로 분리하고 Source에 Credential을 두지 않는다.
- Dataset 비호환 변경은 신·구 Version 병행 기간과 Consumer 전환 절차를 둔다.
- Driver/JVM/WAS/DBMS 버전 호환 Matrix를 관리한다.

## 16. 공통 시스템 컴포넌트 적용

| 컴포넌트 | BI 적용 포인트 |
|---|---|
| 서버 백신 | Export·임시파일·Cache 경로와 성능 예외 관리 |
| 계정 관리 | User·Service·Datasource·Admin·Export 역할 분리 |
| 서버 보안 | WEB/WAS/AP/DB Hardening·Port·Patch |
| 개인정보 | 화면·Log·APM·Cache·Report·Export·Backup 보호 |
| 백업 관리 | Artifact·Config·Dataset·Cube·DB 복구 |
| 인프라 통합관제 | 사용자 영향·Query·DB·Agent Event 상관 |
| 서버 모니터링 | JVM·Process·Filesystem·Connection 관측 |
| 서버운영관리 | CMDB·Version·Patch·기동·변경·Runbook |

## 17. 검증 시나리오

| 번호 | 시나리오 | 합격 기준 |
|---:|---|---|
| 1 | BI포탈 정상 로그인 | WEB→WAS·권한·Session 정상 |
| 2 | BI포탈 RDW Query | 최신성·짧은 Timeout·Read-only |
| 3 | BI포탈 ADW Query | 분석 Queue·Dataset Version 정상 |
| 4 | RDW/ADW Route 오류 | 잘못된 Datasource 접근 차단 |
| 5 | BI포탈 WEB 장애 | Health 제외 후 서비스 유지 |
| 6 | BI포탈 WAS 재기동 | Query 중복 없이 복구 |
| 7 | 신용실적 정상 조회 | ADW 기준일·집계버전 표시 |
| 8 | 신용실적 권한 | Row/Column·Masking 정상 |
| 9 | 신용실적 정정 | 이전본·사유·승인·재발행 추적 |
| 10 | OLAP WEB→WAS→AP | Session·Metadata·Query 연결 |
| 11 | OLAP ODBC 연결 | DSN·TLS·Encoding·Failover 정상 |
| 12 | OLAP 대량 Query | Queue·Timeout·정형 BI 격리 |
| 13 | Cube Refresh 실패 | 이전 Cube 유지·원자적 교체 |
| 14 | OLAP AP Agent 공백 | 관리·APM 대체 또는 누락 확인 |
| 15 | Self-BI 정상 Query | 승인 Dataset·JDBC·Trace 정상 |
| 16 | Self-BI 임의 Schema | 접근 차단 |
| 17 | Self-BI 대량 Query | 사용자 Quota·Cancel 정상 |
| 18 | Self-BI 민감 컬럼 | Masking·감사 정상 |
| 19 | Self-BI Export | 승인·암호화·워터마크·만료 |
| 20 | Self-BI AP eCAMS 공백 | 배포·구성관리 대체 확인 |
| 21 | Dataset 적재 실패 | 미완성본 미노출 |
| 22 | Dataset Schema 변경 | 네 Consumer Contract Test 통과 |
| 23 | DBSafer 허용 접속 | 사용자·SQL 감사 기록 |
| 24 | DB 우회 접속 | ACL·정책으로 차단 |
| 25 | JDBC/ODBC Pool 고갈 | Timeout·Queue·경보 정상 |
| 26 | 개인정보 APM/Log | 민감정보 미수집·Masking |
| 27 | Backup/Restore | DB·Dataset·Cube·Config 복구 |
| 28 | E2E 추적 | User→Query→DB→Export 연결 |

## 18. 저장소 근거와 정합성

- `노드별 기술 컴포넌트 통합정리`는 BI포탈 12개 노드를 공통 WEB/WAS/DB Pattern에 매핑한다.
- 운영·개발 BI포탈 구성 분석은 BI포탈·신용실적·OLAP·Self-BI의 기능·Workload 분리를 보완한다.
- 원본 Evidence 09~13은 네 장의 기술 스택을 확인하는 이미지 기반 증적이다.
- **정합성 주의**: 첨부 이미지의 OLAP AP는 `ODBC Driver`인데 저장소 통합 문서 일부에는 `JDBC Driver`로 정리돼 있다. 본 문서는 첨부 이미지 표기를 우선하며 원본 설계서에서 재확인해야 한다.
- 자동 `code-understand` 추출기는 로컬 설정 권한 문제로 사용할 수 없어 `rg`와 Evidence 직접 판독으로 대체했다.

## 19. 주요 확인사항과 Gap

1. BI포탈 DB(RDW, ADW)가 단일 논리 노드인지 두 DB/Datasource의 병기인지.
2. 신용실적 ADW의 Database/PDB/Schema/View와 Dataset Owner.
3. OLAP AP의 실제 Driver가 ODBC인지 JDBC인지 및 DSN/Driver Manager 구성.
4. OLAP AP의 eCAMS·APM Agent 미표시가 의도인지 장표 누락인지.
5. Self-BI AP의 eCAMS Agent 미표시와 배포·구성관리 방식.
6. OLAP/Self-BI AP가 접근하는 RDW/ADW 및 승인 Dataset.
7. WEB→WAS→AP→DB 실제 Protocol, Route, Context, Port.
8. Dataset·Metric·Cube·Report의 Version 및 Lineage.
9. Query Resource Group, Pool, Queue, Timeout, Scan/Export 제한.
10. Host→VM→JVM→Process→Artifact→Datasource 자산 매핑.
11. HA/DR·Backup·RPO/RTO·동시사용자·Peak Query 용량.

## 20. 사실·해석·미확정 구분

| 구분 | 내용 |
|---|---|
| 이미지 확정 | 12개 노드, 표에 열거한 기술 스택, BI포탈 RDW/ADW, 신용실적 ADW, OLAP ODBC, Self-BI JDBC/APM |
| 이미지 차이 | OLAP AP eCAMS/APM 미표시, Self-BI AP eCAMS 미표시 |
| 저장소 근거 | 공통 Pattern 반복 적용, 12개 노드 통합 목록 |
| 아키텍처 해석 | WEB→WAS→AP→DB, Query 분류, Dataset 발행·Workload 격리 |
| 미확정 | 실제 Driver 최종값·제품·버전·Host/Port·DB 연결·수량·HA/DR·SLO |

## 21. 최종 평가

BI포탈 기술 컴포넌트는 정형 BI와 신용실적, 다차원 OLAP, 사용자 주도 Self-BI를 12개 실행 노드로 분리하고 공통 VM/Appliance 운영 표준을 적용한 구조다. OLAP AP의 ODBC와 Self-BI AP의 JDBC/APM 차이는 단순 기술명 차이가 아니라 Driver, Pool, 추적성, 장애복구, 배포 책임이 달라지는 핵심 설계 지점이다.

실제 완성도를 좌우하는 것은 화면 서버 수가 아니라 `User→Role→Dataset`, `Metric→Version→Consumer`, `WEB/WAS/AP→Datasource`, `Query→Resource Group`, `Result→Export`, `Trace→SQL→DB`, `Backup→Restore Evidence`를 연결하는 것이다. 이 계약이 닫혀야 BI 기능 간 수치 일관성, 개인정보 보호, 분석 자유도, 정형 서비스 SLA를 동시에 보장할 수 있다.

## 22. 관련 문서와 근거

- [공통 기술 컴포넌트 분석](./논리기술아키텍처_기술컴포넌트정의_공통기술컴포넌트_분석.md)
- [데이터플랫폼 기술 컴포넌트 분석](./논리기술아키텍처_기술컴포넌트정의_데이터플랫폼_기술컴포넌트_분석.md)
- [운영환경 BI포탈 시스템 구성](./운영환경_BI포탈_시스템_구성_분석.md)
- [개발환경 BI포탈 시스템 구성](./개발환경_BI포탈_시스템_구성_분석.md)
- [노드별 기술 컴포넌트 통합정리](../../2026-08-19-아키텍처정의서/67_노드별_기술_컴포넌트_통합정리.md)
- [원본 Evidence 1/4](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85737/09_BI포털_기술_컴포넌트_%281_4%29.md)
- [중복 Evidence 1/4](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85737/10_BI포털_기술_컴포넌트_%281_4%29.md)
- [원본 Evidence 2/4](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85737/11_BI포털_기술_컴포넌트_%282_4%29_—_신용실적.md)
- [원본 Evidence 3/4](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85737/12_BI포털_기술_컴포넌트_%283_4%29_—_OLAP.md)
- [원본 Evidence 4/4](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85737/13_BI포털_기술_컴포넌트_%284_4%29_—_Self-BI.md)
