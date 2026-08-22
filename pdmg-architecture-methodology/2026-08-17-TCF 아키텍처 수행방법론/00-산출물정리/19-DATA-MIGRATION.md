# 19. NSIGHT Data Migration Architecture

## 1. 목적

Migration을 일회성 데이터 복사 작업이 아니라 **Source → Stage → Target → Validation → Cut-over → Evidence**의 Runtime Architecture로 관리한다.

---

## 2. Current Migration Flow

```text
AS-IS
DW / ODS / OLAP / 실적 / ILM
        │
        ▼
Extraction
        │
        ▼
Migration Server / SQL Canvas
        │
        ▼
STAGE
S1 / S2 / Reject
        │
        ▼
Cleanse / Transform / Validate
        │
        ▼
DataStage / Migration Engine
        │
        ▼
TARGET
ADW / RDW / ILM
        │
        ▼
Reconciliation
        │
        ├─ FAIL → Reject / Restart / Rollback
        └─ PASS → Cut-over
```

---

## 3. Environment Evolution

현재 자료의 일정:

| 단계 | 기간 | 역할 |
|---|---|---|
| 개발 초기 | 2026.09 ~ 2027.01 | 개발용 데이터/이행프로그램 |
| 1차 통합 | 2027.01 ~ 2027.02 | 운영 데이터 기반 검증 |
| 2·3차 통합/영업점 | 2027.03 ~ 2027.04 | 대용량/운영조건 검증 |
| 사전/본이행 | 2027.05 ~ Open | 최종 운영 전환 |

핵심 전략:

```text
Small Sample
  ↓
Operational Extract
  ↓
Repeated Migration
  ↓
Large Volume Test
  ↓
Pre-Migration
  ↓
Main Cut-over
```

---

## 4. Stage Architecture

STAGE는 단순 임시 DB가 아니다.

역할:

- Source 보호
- 반복 실행
- 오류 데이터 격리
- 변환 전/후 비교
- 차수 병행
- Reconciliation Evidence

권장 모델:

```text
STAGE_S1 = Current Run
STAGE_S2 = Next/Retry Run
REJECT   = Invalid/Failed Rows
TARGET_T1/T2 = Validation/Comparison
```

---

## 5. Source / Target Inventory

각 Migration Object마다 최소 다음이 필요하다.

| Field | 설명 |
|---|---|
| Migration Object ID | 고유 ID |
| Source System | DW/ODS/OLAP 등 |
| Source Table/File | 원천 |
| Extract Condition | 기준시점/조건 |
| Stage Object | Stage Mapping |
| Transform Rule | 변환 |
| Target System | ADW/RDW/ILM |
| Target Table | 대상 |
| Key | PK/Business Key |
| Volume | 예상 건수/크기 |
| Dependency | 선후관계 |
| Validation Rule | 검증 |
| Owner | 담당 |

현재 전체 Source→Target Mapping Registry는 미완성이다.

---

## 6. Validation Architecture

건수 일치만으로 성공 처리하지 않는다.

최소 검증:

```text
1. Count
2. Key / Duplicate
3. Amount / Aggregate
4. Null / Code / Domain
5. Hash / Sample
6. PK/FK Integrity
7. Business Validation
```

권장 Gate:

```text
Source
 ↓
Stage Validation
 ↓
Transform Validation
 ↓
Target Validation
 ↓
Business Reconciliation
 ↓
Migration Gate
```

---

## 7. Restart / Re-run

모든 Job은 다음을 식별할 수 있어야 한다.

```text
Run ID
Chunk/Partition
Start/End Time
Source Count
Success Count
Reject Count
Restart Point
Target Commit State
```

금지:

```text
전체 이행 실패 후 무조건 처음부터 다시 시작
이전 Target 적재상태 불명확
중복 적재 가능 상태에서 재실행
```

---

## 8. Migration Performance

본이행 시간 예측을 위해 각 테스트에서 다음을 누적한다.

```text
Data Volume
Elapsed
Rows/sec
MB/sec
Parallelism
Source CPU/IO
Stage CPU/IO
Target CPU/IO
DB Wait
Index Build
Statistics
Validation Time
```

---

## 9. Cut-over

```text
AS-IS Operating
   ↓
Business Cut-off
   ↓
Final Delta Extract
   ↓
Stage
   ↓
Transform/Load
   ↓
Reconciliation
   ↓
Smoke Test
   ↓
Business Confirmation
   ↓
Go / No-Go
   ↓
Open
```

Go/No-Go 조건은 정량화해야 한다.

예:

- Critical Count mismatch = 0
- Financial aggregate mismatch = 0
- Reject threshold 충족
- Validation Complete
- Smoke Test PASS
- Cut-over Window 내 완료

---

## 10. Rollback / Fallback

본이행 실패 시 다음을 사전에 정의한다.

- Rollback Deadline
- AS-IS Resume 가능시점
- Target partial data 처리
- Final Delta 재추출 여부
- User/Batch 재개 절차
- Business Communication

---

## 11. Migration Security

Migration 데이터는 운영 데이터와 동일한 보안등급으로 취급한다.

- 최소권한 계정
- 암호화 전송
- Temporary File 통제
- 개인정보 Masking 필요 여부
- Export/Delete Lifecycle
- Audit Log

---

## 12. Migration Evidence

각 Run은 다음 Evidence Pack을 남긴다.

```text
RUN-MANIFEST
Source Snapshot
Extract Log
Stage Load Log
Transform Log
Target Load Log
Reject List
Count/Sum/Hash Result
Performance Result
Business Validation
Go-NoGo Decision
Rollback Result (if any)
```

---

## 13. Migration GAP

| ID | GAP | Priority |
|---|---|---:|
| MIG-G01 | 전체 Source→Target Mapping Registry 미완성 | P0 |
| MIG-G02 | Cut-off/Final Delta 기준 확정 필요 | P0 |
| MIG-G03 | Go/No-Go 정량 기준 미완성 | P0 |
| MIG-G04 | Restart/Idempotent Load 규칙 미완성 | P0 |
| MIG-G05 | Rollback/Fallback Runbook 미완성 | P0 |
| MIG-G06 | Migration Evidence Gate 미연결 | P1 |

---

## 14. G50 Migration 판정

**CONDITIONAL PASS**

환경 및 반복 이행 전략은 적정하나, 통제/검증/재실행/Cut-over/증적이 Architecture Gate로 완성되어야 한다.
