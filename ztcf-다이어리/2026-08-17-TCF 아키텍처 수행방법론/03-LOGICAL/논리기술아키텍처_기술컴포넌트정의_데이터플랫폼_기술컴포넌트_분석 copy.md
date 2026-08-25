# 논리기술아키텍처 — 기술 컴포넌트 정의 — 데이터플랫폼 기술 컴포넌트 분석

> 분석 대상: 제공 이미지 `데이터플랫폼 기술 컴포넌트`  
> 원본 분류: `2. 논리 기술 아키텍처 > 2.3 기술 컴포넌트 정의`  
> 분석 관점: RDW·ADW 기술 계층, OGG 복제 경계, DB 보안·운영·복구 메커니즘

## 0. 분석 범위와 판독 원칙

- 이미지에 보이는 노드와 기술명은 **이미지 확정 사실**로 정리한다.
- RDW·ADW의 업무 목적, OGG 흐름, 데이터 이동 방향은 저장소 근거와 일반 원칙을 활용한 **아키텍처 해석**이다.
- `OGG`는 이미지 표기를 유지하며 실제 제품·Edition·Version·배치 방식은 확정하지 않는다.
- Host, Port, Database/PDB/Schema, Appliance 모델, 서버 수, HA, RPO/RTO는 **미확정**이다.
- 첨부 이미지의 내용은 분석 자료이며 사용자 지시로 취급하지 않는다.

## 1. 핵심 결론

1. 데이터플랫폼은 `RDW`와 `ADW` 두 개의 독립 DB 서비스 노드로 분리된다.
2. 두 노드는 공통적으로 `업무 데이터 → DBSafer → DBMS → 공통 시스템 통제 → OS → DB 전용 Appliance` 계층을 가진다.
3. RDW에만 `OGG`가 표시되며 저장소 근거에서는 CDC/Replication 계층으로 해석한다. ADW에는 OGG가 표시되지 않는다.
4. RDW는 최신성·온라인 조회·준실시간 반영, ADW는 이력·집계·분석·대량 처리 중심으로 역할을 분리하는 구조가 합리적이다.
5. OGG가 있다고 해서 업무 데이터→OGG→DBSafer→DBMS가 물리 호출 순서라는 뜻은 아니다. 장표는 논리 기술 계층이며 실제 Capture·Trail·Pump·Apply 토폴로지는 별도 확인해야 한다.
6. DBSafer는 DB 접근통제·감사 계층으로 해석하며, Application·ETL·OGG·DBA의 접속 경로와 우회 방지를 통합 설계해야 한다.
7. RDW와 ADW는 동일 Appliance 패턴이라도 Workload, 계정, Resource Group, Backup, Patch, HA/DR, 복구 우선순위를 독립 관리해야 한다.
8. 데이터 정합성은 복제 성공 여부만으로 보장되지 않으며 SCN/Checkpoint, 건수·합계·Hash, 기준시점·Schema Version으로 대사해야 한다.

## 2. 구성요소 전수 정리

| 영역 | RDW | ADW | 분석 |
|---|---|---|---|
| 서비스 노드 | RDW | ADW | 독립 데이터 서비스 경계 |
| 데이터 | 업무 데이터 | 업무 데이터 | 각 노드가 소유·제공하는 Schema·Dataset |
| 복제/CDC | OGG | 이미지상 미표시 | RDW 준실시간 변경반영 계층 후보 |
| DB 보안 | DBSafer | DBSafer | 접근통제·감사 계층 후보 |
| 데이터 관리 | DBMS | DBMS | Transaction·Query·Storage·Recovery |
| 공통 보안 | 서버 백신, 계정 관리, 서버 보안, 개인정보 | 동일 | 횡단 보안 통제 |
| 공통 운영 | 백업 관리, 인프라 통합관제, 서버 모니터링, 서버운영관리 | 동일 | 횡단 운영 통제 |
| OS | OS | OS | Process·Network·Filesystem 기반 |
| 인프라 | DB 전용 Appliance | DB 전용 Appliance | DB 최적화 Compute·Storage·Network 기반 |

## 3. 텍스트 아키텍처 그림

### 3.1 전체 기술 컴포넌트 구조

```text
┌────────────── RDW ──────────────┐   ┌────────────── ADW ──────────────┐
│ 업무 데이터                     │   │ 업무 데이터                     │
├─────────────────────────────────┤   ├─────────────────────────────────┤
│ OGG                             │   │ (OGG 이미지상 미표시)           │
│ DBSafer                         │   │ DBSafer                         │
│ DBMS                            │   │ DBMS                            │
├─────────────────────────────────┤   ├─────────────────────────────────┤
│ 백신 | 계정 | 보안 | 개인정보   │   │ 백신 | 계정 | 보안 | 개인정보   │
│ 백업 | 관제 | 모니터링 | 운영관리│   │ 백업 | 관제 | 모니터링 | 운영관리│
├─────────────────────────────────┤   ├─────────────────────────────────┤
│ OS                              │   │ OS                              │
│ DB 전용 Appliance               │   │ DB 전용 Appliance               │
└─────────────────────────────────┘   └─────────────────────────────────┘
```

### 3.2 역할·Workload 분리

```text
                 데이터플랫폼
        ┌──────────────┴──────────────┐
        ▼                             ▼
┌──────────────────┐          ┌──────────────────┐
│ RDW              │          │ ADW              │
│ 최신·준실시간    │          │ 이력·집계·분석   │
│ 짧은 응답 조회   │          │ 대량 Scan/Join   │
│ 높은 동시성      │          │ Batch/BI 처리    │
│ OGG 반영 후보    │          │ ETL 적재 후보    │
└──────────────────┘          └──────────────────┘
        │                             │
        └── 기준시점·지표·품질 대사 ──┘
```

### 3.3 OGG 논리 복제 경로

```text
[원천 DB 변경 로그]
        │ Capture
        ▼
   [OGG Capture]
        │ Local/Remote Trail
        ▼
 [Data Pump/전송 후보]
        │
        ▼
   [OGG Apply 후보]
        │ Checkpoint·순서·변환
        ▼
     [RDW DBMS]
        │
        └→ 업무 데이터 최신화

주의: 세부 OGG 프로세스와 방향은 일반 패턴이며 이미지에서 확정되지 않음
```

### 3.4 Fast Path와 Deep Path

```text
[업무 원천]
    │
    ├─ Fast Path ─ OGG/CDC ─→ RDW ─→ 최신 조회·싱글뷰
    │                           │
    │                           └→ Freshness/Replication Lag
    │
    └─ Deep Path ─ ETL/Batch ─→ ADW ─→ BI·OLAP·통계
                                │
                                └→ Dataset Version/품질 대사

RDW → 정제·집계 ETL → ADW 경로도 별도 Data Flow Contract 필요
```

### 3.5 DB 접근통제 경로

```text
[Application] [ETL] [OGG] [DBA]
      │          │     │     │
      └──────────┴─────┴─────┘
                 │ 사용자·Service 계정
                 ▼
             [DBSafer]
                 │ 인증·정책·명령·SQL 감사
                 ▼
              [DBMS]
                 │
        ┌────────┴────────┐
        ▼                 ▼
   허용 Session       차단·경보·감사

필수: 우회 Port/Local 접속/공유계정 통제
```

### 3.6 RDW→ADW Dataset 발행

```text
[RDW Snapshot / Watermark]
            │
            ▼
      [Extract / Stage]
            │
            ▼
[Validate → Transform → Aggregate]
            │ 건수·합계·Null·중복·기준시점
            ▼
      [ADW Load Stage]
            │ 품질 Gate
            ▼
[ADW Dataset Version N 원자적 발행]
            │
            └→ BI / OLAP / Self-BI
```

### 3.7 계정·권한 분리

```text
DBA Admin ───── DDL·운영관리 (승인·감사)
Schema Owner ── Object 소유 (직접 Login 제한)
Application ─── 승인 Procedure/View, 최소 DML
OGG Capture ─── Log/Capture 최소권한
OGG Apply ───── 지정 Schema Apply 권한
ETL ─────────── Stage/Target 제한 DML
BI/Analyst ──── 승인 Dataset Read-only
Backup ──────── Backup/Recovery 전용

금지: 공유 DBA 계정, Application DBA 권한, 환경 간 Credential 공유
```

### 3.8 백업·복구·복제 구분

```text
HA          : 현재 서비스 연속성 (Node/Instance 장애)
Replication : 변경 데이터 전달 (OGG 등)
Backup      : 시점별 복구 가능한 보존 Copy
DR          : 재해 시 별도 Site 서비스 복구

        [DBMS]
          ├→ HA Peer
          ├→ OGG Target
          ├→ Backup + Archive/Log
          └→ DR Target

Replication은 논리 삭제·오염도 전달하므로 Backup을 대체하지 않는다.
```

### 3.9 관측성과 통합관제

```text
OGG  : Capture/Apply Status, Lag, Checkpoint, Error
DBMS : Session, Lock, Wait, SQL, I/O, Temp, Log
OS   : CPU, Memory, Disk, Network, Process
Appliance: Compute, Storage, Interconnect, Component
DBSafer: 접속, 차단, 정책, 감사 수집 상태
Backup: 성공, 복구점, 보존, Restore 결과
          │
          ▼
 [서버 모니터링 / DB 모니터링]
          │ Event 표준화·상관
          ▼
      [인프라 통합관제]
          └→ Alert / Ticket / Runbook / Escalation
```

### 3.10 Schema 변경 호환성

```text
[Schema Change Request]
          │
          ▼
[RDW DDL 영향 분석]
  ├→ Application SQL/View
  ├→ OGG Mapping/Apply
  ├→ ETL Mapping
  └→ ADW Dataset/BI Consumer
          │
          ▼
[Backward-compatible 배포 또는 Version 분리]
          │
          ▼
[Contract Test → 대사 → 승인 → 배포 → Rollback/Roll-forward]
```

### 3.11 장애·재처리 경계

```text
OGG Capture 실패 → 원천 Log 보존 → Checkpoint 재개
Trail 전송 실패  → Queue/Storage 보호 → 재전송
Apply 실패       → 오류 Record 격리 → 원인 수정 → 재적용
RDW 장애         → HA/복구 → OGG Apply 재개 → Gap 대사
ETL 실패         → Stage 유지 → Job/Step 재시작
ADW 장애         → 이전 Dataset 유지 → 복구 후 원자적 재발행
```

### 3.12 책임 경계

```text
Data Owner       : 업무 데이터 정의·품질·보존·사용승인
DBA              : DBMS·Schema·성능·Backup·Recovery
Replication Team : OGG Mapping·Lag·Checkpoint·Replay
ETL Team         : RDW→ADW 변환·대사·Dataset 발행
Security Team    : DBSafer·계정·개인정보·취약점
Infra Team       : OS·Appliance·Storage·Network·관제
Service Owner    : Consumer SLA·장애 영향·복구 우선순위
```

## 4. RDW 상세 분석

### 4.1 역할

- 운영성·최신성 중심의 업무 데이터 제공 계층으로 해석한다.
- OGG를 통해 원천 변경을 준실시간 반영하는 Target 후보이나 실제 방향은 확인이 필요하다.
- 온라인·싱글뷰 Consumer를 보호하기 위해 짧은 Query, 높은 동시성, 안정된 Connection을 우선한다.
- ADW/BI 대량 Query가 RDW의 온라인 SLA를 침범하지 않도록 접근 경계를 둔다.

### 4.2 OGG 통제 항목

| 구분 | 필수 정의 |
|---|---|
| 소유 | Source/Target DB Owner, Replication Owner |
| 범위 | Database/PDB/Schema/Table, 포함·제외 Column |
| 식별 | processId, mappingId, trail, checkpoint |
| 처리 | Insert/Update/Delete, DDL, LOB, Code 변환 |
| 순서 | Transaction 순서, Key, Commit 기준 |
| 장애 | Retry, Gap, Error Table, Replay, Backfill |
| 지표 | Capture Lag, Apply Lag, Throughput, Error |
| 보안 | 전용 계정, TLS, Trail 암호화, 최소권한 |

### 4.3 일관성 위험

- Key가 없거나 변경되면 Update/Delete Apply가 모호해질 수 있다.
- DDL 비호환 변경은 Capture는 성공하고 Apply가 중단되는 상황을 만들 수 있다.
- 장기 Transaction은 Commit 시점에 Lag를 급증시킬 수 있다.
- 재처리 시 동일 변경이 중복 적용되지 않도록 Transaction/Checkpoint 기준이 필요하다.
- 부분 Table만 복제하면 업무 참조 무결성이 깨질 수 있으므로 복제 Group을 정의한다.

## 5. ADW 상세 분석

### 5.1 역할

- 이력, 분석, 집계, BI·OLAP, 대량 Scan을 담당하는 분석성 DB로 해석한다.
- 이미지에는 OGG가 없으므로 ETL·Batch·파일 등 다른 적재 경로를 사용하는 구조가 합리적이나, 실제 유입 방식은 미확정이다.
- Dataset은 적재 중인 중간 상태를 Consumer에 노출하지 않고 검증 완료본을 Version 단위로 발행해야 한다.
- 분석 사용자와 Batch Job별 Resource Group, Queue, Timeout, Temp/Scan Limit을 분리한다.

### 5.2 ADW 적재 계약

| 항목 | 정의 |
|---|---|
| Source | RDW/원천/파일 및 Owner |
| Watermark | 기준시각, 영업일, 마지막 성공 위치 |
| Transform | Code, Type, Timezone, Character Set, 집계 Grain |
| Quality | Null, 중복, 참조 무결성, 건수·합계·Hash |
| Publish | datasetVersion, atomic switch, 이전본 유지 |
| Recovery | Job/Step Checkpoint, 재실행·보상, Backfill |

## 6. 공통 기술 컴포넌트 상세

### 6.1 업무 데이터

- Dataset·Schema·Table·View·지표의 의미와 Owner를 포함한다.
- 데이터 등급, 개인정보 여부, 보존·파기, Consumer, 기준시점을 카탈로그화한다.
- RDW와 ADW에 동일 이름 지표가 있으면 산식·Grain·Freshness 차이를 명시한다.

### 6.2 DBSafer

- 이미지에는 DBMS 상단의 보안 계층으로 표시된다.
- DB 접속 인증·권한·SQL 감사·명령 통제 솔루션으로 해석한다.
- Local DBA, Batch, OGG, Monitoring, Backup 등 특수 계정의 예외와 보완통제를 관리한다.
- DBSafer 장애 시 Fail-open/Fail-close 정책과 업무·운영 영향범위를 확정해야 한다.

### 6.3 DBMS

- Transaction, Lock, Query Optimizer, Buffer/Cache, Log, Storage, Recovery를 관리한다.
- RDW는 TPS·Session·Lock·p95/p99, ADW는 Throughput·Queue·Temp·Scan·ETL SLA를 우선 관측한다.
- DBMS Patch와 Optimizer 변화가 SQL Plan에 미치는 영향을 회귀 검증한다.

### 6.4 OS·DB 전용 Appliance

- OS는 Process, User, Network, Filesystem, 시간동기화의 기반이다.
- DB 전용 Appliance는 Compute·Storage·Network가 통합된 DB 최적화 기반으로 해석한다.
- Agent·백신 설치는 Appliance 지원정책과 성능 영향을 검토한다.
- Firmware, OS, DBMS Patch의 인증 조합과 순서를 준수한다.

## 7. 횡단 공통 통제

| 컴포넌트 | 데이터플랫폼 책임 | 주요 증적 |
|---|---|---|
| 서버 백신 | 지원 범위 내 악성코드 통제, DB 경로 예외 관리 | Agent/Pattern, 제외경로 승인 |
| 계정 관리 | DBA·Schema·OGG·ETL·BI·Backup 계정 분리 | Owner, Role, 만료, 사용 이력 |
| 서버 보안 | OS/Appliance Hardening·취약점·Port | Baseline, Scan, 예외·조치기한 |
| 개인정보 | DB·Trail·Log·Backup·Export 보호 | 등급, Masking, 접근·반출·파기 |
| 백업 관리 | 일관성 Backup·Log·보존·Restore | 복구점, 불변 Copy, 복구시험 |
| 인프라 통합관제 | DB·OGG·Appliance Event 통합 | Rule, Route, Ticket, On-call |
| 서버 모니터링 | DB/OS/Storage/Network 상태 관측 | Metric, Threshold, 결측 탐지 |
| 서버운영관리 | 자산·Patch·구성·용량·기동·변경 | CMDB, Version, Runbook, 작업 이력 |

## 8. 데이터 흐름 계약

```text
Data Flow Contract
 ├─ flowId / owner / source / target
 ├─ schemaVersion / key / datatype / nullable
 ├─ eventTime / watermark / timezone / 기준일
 ├─ transfer: OGG·ETL·File·API / 주기 / 순서
 ├─ quality: count / sum / hash / null / duplicate
 ├─ security: 등급 / masking / encryption / retention
 ├─ recovery: checkpoint / replay / backfill / compensation
 └─ observability: correlationId / batchId / mappingId / SLO
```

동일 Target Table에 OGG와 ETL이 동시에 쓰는 이중 Writer 구조는 금지한다. Backfill이 필요하면 실시간 Apply를 일시 조정하거나 별도 Stage에서 대사 후 승인된 병합 절차를 사용한다.

## 9. 보안 아키텍처

1. Application, OGG Capture/Apply, ETL, BI, Monitoring, Backup 계정을 분리한다.
2. Schema Owner는 직접 Login을 제한하고 Application에 DBA/DDL 권한을 부여하지 않는다.
3. DB Network는 승인 Service와 운영 도구만 허용하고 우회 Port를 차단한다.
4. OGG Trail, ETL Stage, Backup, Export에 DB와 동일한 개인정보 분류·암호화·보존을 적용한다.
5. DBSafer·DB Audit·OS Audit의 시간과 사용자 식별자를 동기화한다.
6. 비상계정은 사전 봉인, 사용 승인, Session 기록, 사후 검토를 적용한다.

## 10. 가용성·백업·DR

| 영역 | RDW | ADW |
|---|---|---|
| 가용성 우선 | 온라인·싱글뷰 영향 최소화 | 분석·배치 연속성 |
| 복구 우선순위 | 상대적으로 높을 수 있음 | 승인된 이전 Dataset으로 저하 운영 가능 |
| OGG | Capture/Apply·Checkpoint 복구 | 이미지상 미표시 |
| Backup | DB+Log+OGG 관련 Metadata | DB+Log+ETL Metadata/Dataset Version |
| DR 검증 | 서비스·OGG Endpoint·Gap 대사 | ETL Endpoint·Dataset·Consumer 전환 |

- HA는 현재 장애 연속성, Backup은 시점 복구, OGG는 변경전달, DR은 Site 재해복구다.
- DB 복구 후 OGG·ETL의 Checkpoint를 함께 복원하지 않으면 중복·유실이 발생할 수 있다.
- 정기적으로 격리 환경 Restore와 업무 건수·합계·표본 대사를 수행한다.

## 11. 성능·용량·Workload 격리

### 11.1 RDW

- Online TPS, Concurrent Session, Query p95/p99, Connection Pool, Lock, Buffer/IO.
- OGG Capture/Apply Throughput과 Peak 변경량, Trail Storage, 허용 Lag.
- BI·Batch 대량 Query의 Read-only Replica 또는 Resource Group 사용 여부.

### 11.2 ADW

- ETL Window, Load Throughput, Workload Queue, Temp/Spill, Scan Volume, Storage 증가율.
- BI/OLAP/Self-BI 사용자별 Query Quota와 동시성.
- Dataset Publish 시간과 이전본 보존 용량.

### 11.3 공통

- Backup·백신·보안 Scan·통계수집·Patch가 Peak와 충돌하지 않도록 실행창을 조정한다.
- Appliance Compute·Storage·Interconnect 병목과 DB Wait를 같은 시간축에서 분석한다.
- 개발환경의 축소 데이터 성능을 운영 Capacity 보증으로 사용하지 않는다.

## 12. 변경·배포 관리

```text
[DDL/Index/Partition/DBMS Patch 요청]
              │
              ▼
[영향 분석]
  Application SQL | OGG Mapping | ETL | ADW Dataset | BI
              │
              ▼
[개발 검증]
  기능 | Plan | 성능 | 복제 | 대사 | Backup/Restore | Rollback
              │
              ▼
[승인 변경창 → 배포 → Health → 관제 → 증적]
```

- RDW와 ADW 변경은 독립 Release가 가능하되 Data Contract 호환성을 유지한다.
- DDL을 OGG가 자동 전달한다고 가정하지 않고 명시적 DDL Policy를 둔다.
- 비호환 변경은 새 Column/View/Schema Version을 먼저 배포하고 Consumer 전환 후 구 버전을 제거한다.
- DBMS·OS·Firmware는 Appliance 인증 조합을 기준으로 Patch한다.

## 13. 관측성 및 경보

| 영역 | 주요 지표 | 대표 경보 |
|---|---|---|
| RDW | TPS, p95/p99, Session, Lock, Wait, Freshness | SLA, Pool, Lock, Freshness 저하 |
| ADW | ETL 시간, Throughput, Queue, Temp, Scan, Storage | ETL SLA, Queue/Temp/용량 임계 |
| OGG | Capture/Apply, Lag, Checkpoint, Trail, Error | Process 중단, Lag, Trail 용량 |
| DBSafer | 접속, 차단, 정책, 감사 수집 상태 | 우회·정책 실패·감사 결측 |
| DBMS | Instance, Log, Archive, Backup, Replication | Instance/Log/Archive/Backup 오류 |
| OS | CPU, Memory, Disk, Network, Process, Time | Filesystem·Process·시간 불일치 |
| Appliance | Component, Storage, Interconnect | 장애·성능 편차·지원 임계 |

모든 지표는 `databaseId`, `serviceName`, `schema`, `environment`, `mappingId`, `batchId`, `datasetVersion`으로 식별한다.

## 14. 장애·재처리 시나리오

| 장애 | 영향 | 복구 원칙 |
|---|---|---|
| OGG Capture 중단 | RDW Freshness 저하 | 원천 Log 보존, Checkpoint 재개 |
| Trail 전송 중단 | Queue·Disk 증가 | 전송 복구, 순서·Gap 검증 |
| Apply 오류 | 특정 Transaction 정체 | 오류 격리, 원인 수정, 재적용·대사 |
| RDW DB 장애 | 온라인 조회 영향 | HA/Restore, OGG 재개, Gap 대사 |
| ETL 부분 실패 | ADW Dataset 불완전 | 부분본 미발행, Step 재시작 |
| ADW DB 장애 | 분석·BI 지연 | 이전 Dataset 제공, 복구 후 재발행 |
| DBSafer 장애 | 접속 차단 또는 감사 공백 | 승인된 Fail 정책·비상절차·사후감사 |
| Backup 실패 | 복구점 상실 | 즉시 경보, 재수행, RPO 영향 평가 |
| Appliance 구성요소 장애 | DB 성능·가용성 저하 | 장애 Domain 격리·벤더 Runbook |

## 15. 검증 시나리오

| 번호 | 시나리오 | 합격 기준 |
|---:|---|---|
| 1 | RDW 정상 Query | 승인 Service·View·최소권한 사용 |
| 2 | ADW 대량 Query | RDW SLA 영향 없음 |
| 3 | OGG Insert/Update/Delete | 순서·값·건수 일치 |
| 4 | OGG Capture 재시작 | Checkpoint 이후 유실 없이 재개 |
| 5 | OGG Apply 오류 | 오류 격리·재적용·대사 가능 |
| 6 | OGG 장기 Lag | Freshness 경보와 Consumer 표시 |
| 7 | Trail 용량 임계 | 사전 경보와 증가 억제 |
| 8 | DDL 호환 변경 | OGG·ETL·Consumer 정상 |
| 9 | DDL 비호환 변경 | 배포 Gate에서 차단 |
| 10 | DBSafer 허용 접속 | 정책·사용자·SQL 감사 기록 |
| 11 | DB 우회 접속 | ACL·정책으로 차단 |
| 12 | 권한 분리 | DBA·App·OGG·ETL·BI 역할 분리 |
| 13 | 개인정보 조회 | Masking·감사·반출 통제 |
| 14 | RDW→ADW 적재 | 기준시점·건수·합계·Hash 일치 |
| 15 | ETL 부분 실패 | 미완성 Dataset 미노출 |
| 16 | Backfill | OGG와 이중 Writer 충돌 없음 |
| 17 | RDW 부하 Peak | Online p99·Session SLA 유지 |
| 18 | ADW 부하 Peak | Queue·Temp·Throughput 허용 범위 |
| 19 | RDW Backup/Restore | 목표 RPO/RTO·업무 대사 통과 |
| 20 | ADW Backup/Restore | Dataset Version·Consumer 정상 |
| 21 | OGG+DB 복구 | Checkpoint와 DB 시점 정합 |
| 22 | Appliance Patch | 인증 조합·기능·성능·Rollback 통과 |
| 23 | Monitoring 결측 | Agent/수집 장애 별도 경보 |
| 24 | E2E 추적 | Source→OGG/ETL→RDW/ADW→Consumer 추적 |

## 16. 저장소 근거와 대응

- `노드별 기술 컴포넌트 통합정리`는 RDW를 `업무 데이터·OGG·DBSafer·DBMS`, ADW를 `업무 데이터·DBSafer·DBMS`로 정리한다.
- 운영·개발 데이터플랫폼 분석 문서는 RDW를 최신·준실시간, ADW를 분석·대량 처리 계층으로 해석한다.
- 저장소의 CDC/ETL 정책은 RDW Fast Path와 ADW Deep Path, Checkpoint·재처리·대사를 요구한다.
- 자동 `code-understand` 추출기는 로컬 설정 권한 문제로 사용할 수 없어 `rg` 교차 검색과 Evidence 직접 판독으로 대체했다.

## 17. 주요 확인사항과 Gap

1. OGG의 실제 제품·Edition·Version·License와 Capture/Apply 배치.
2. Source/Target Database, Schema, Table, Key, DDL·LOB 처리 범위.
3. RDW·ADW의 DBMS·OS·Appliance 모델, Patch 인증 Matrix.
4. DBSafer 배치 방식, Fail-open/close, 우회 접속 차단, 감사 보존.
5. OGG와 ETL 대상 Table의 Writer 소유권 및 Backfill 절차.
6. RDW→ADW 데이터 흐름, Watermark, Dataset Version, 품질 기준.
7. HA·Backup·DR 토폴로지와 RPO/RTO·복구 우선순위.
8. Resource Group, Connection, Session, Queue, Timeout, 용량 기준.
9. Host→OS→DBMS→Database/PDB→Schema→Service 자산 매핑.
10. 계정·보안·개인정보·백업·관제 시스템 간 공통 databaseId 사용 여부.

## 18. 사실·해석·미확정 구분

| 구분 | 내용 |
|---|---|
| 이미지 확정 | RDW·ADW, 업무 데이터, RDW의 OGG, 양쪽 DBSafer·DBMS·공통통제·OS·DB 전용 Appliance |
| 저장소 확정 | OGG를 CDC/Replication 계층으로 분류, RDW·ADW 공통 Template 반복 적용 |
| 아키텍처 해석 | RDW=최신성, ADW=분석성, OGG Fast Path, ETL Deep Path |
| 미확정 | OGG·DBMS·Appliance 제품/버전, 흐름 방향, Host/Port, 수량, HA/DR, SLO |

## 19. 최종 평가

데이터플랫폼 기술 컴포넌트는 RDW와 ADW를 같은 DB Appliance 표준 위에 두되, RDW에만 OGG 복제 계층을 추가해 최신성 중심 데이터 경로와 분석성 데이터 경로를 분리한 구조다. 두 노드가 같은 기술 형태라고 해서 하나의 운영 단위가 되는 것은 아니며, Workload·계정·변경·복구·용량·SLO를 독립 관리해야 한다.

실제 완성도를 결정하는 핵심은 `Source→OGG/ETL→Target`, `DBSafer→DBMS`, `Schema→Consumer`, `Checkpoint→Backup/Restore`, `Metric→Alert→Runbook`의 매핑이다. 이 계약과 증적이 닫혀야 데이터플랫폼이 최신성·분석성·보안·복구 가능성을 동시에 보장할 수 있다.

## 20. 관련 문서와 근거

- [공통 기술 컴포넌트 분석](./논리기술아키텍처_기술컴포넌트정의_공통기술컴포넌트_분석.md)
- [마케팅플랫폼 기술 컴포넌트 분석](./논리기술아키텍처_기술컴포넌트정의_마케팅플랫폼_기술컴포넌트_분석.md)
- [개발환경 데이터플랫폼 시스템 구성](./개발환경_데이터플랫폼_시스템_구성_분석.md)
- [운영환경 데이터플랫폼 시스템 구성](./운영환경_데이터플랫폼_시스템_구성_분석.md)
- [노드별 기술 컴포넌트 통합정리](../../2026-08-19-아키텍처정의서/67_노드별_기술_컴포넌트_통합정리.md)
- [원본 Evidence: 데이터플랫폼 기술 컴포넌트](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85737/08_데이터플랫폼_기술_컴포넌트.md)
