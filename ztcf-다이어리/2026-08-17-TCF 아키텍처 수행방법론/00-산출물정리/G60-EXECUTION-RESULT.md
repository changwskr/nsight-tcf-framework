# G60 Capacity / Runtime Execution Result

## 1. Gate 판정

**G60 = CONDITIONAL PASS**

성능/용량 Chain과 Working Baseline은 기준화되었으나 성능시험 기반 `Runtime Approved` 값은 아직 존재하지 않는다.

---

## 2. 이번 단계 완료사항

- 36,000 User / 43,200~46,800 Session Baseline 정규화
- 600 / 1,200 / 1,800 TPS 시나리오 확정
- 500 TPS 보수 기준과 855 TPS Working 기준 분리
- 855 TPS의 80% = 684 TPS Operational Working Capacity 정의
- `2+2` Center Failure Capacity와 `3+3` N+1 후보 분리
- Tomcat 800 initial / 1,000 test upper 정규화
- JVM 24GB General / 28GB SingleView Working 기준화
- Hikari Legacy 80~120 vs Working 120~180 분리
- Query < TX < Client Timeout Chain 정의
- L4 Idle Timeout 충돌을 OPEN으로 격리
- Runtime Test Matrix 및 Evidence Schema 생성

---

## 3. 핵심 Architecture Decision 상태

| 항목 | 현재 상태 |
|---|---|
| Peak 1,200 TPS | WORKING CONFIRMED |
| Stress 1,800 TPS | WORKING CONFIRMED |
| 16Core 500 TPS | LEGACY/CONSERVATIVE |
| 16Core 855 TPS | CURRENT WORKING |
| 16Core Runtime Approved TPS | UNKNOWN |
| Session 90m | WORKING |
| Session Final | OPEN |
| Tomcat 800 | WORKING INITIAL |
| Hikari 150/180 | VALIDATION CANDIDATE |
| Center 2+2 | WORKING EXAMPLE |
| Center 3+3 | N+1 CANDIDATE / INFERENCE |

---

## 4. Critical Conditions

| ID | 조건 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G60-C01 | 500 vs 855 VM 승인 TPS를 Load Test로 확정 | P0 | G60/G70 |
| G60-C02 | Session 60 vs 90 운영정책 ADR | P0 | G70 |
| G60-C03 | Tomcat 800~1,000 최종값 | P0 | G60 |
| G60-C04 | Hikari Pool Hold-Time/DB Session 기반 승인 | P0 | G60 |
| G60-C05 | JVM Heap 업무유형별 승인 | P0 | G60 |
| G60-C06 | ServiceId별 Query/TX/Overall Deadline 검증 | P0 | G60/G80 |
| G60-C07 | Timeout Late Commit/Connection 반환 Test | P0 | G60/G80 |
| G60-C08 | 2+2 vs 3+3 HA Capacity ADR | P0 | G70 |
| G60-C09 | Stress 1,800 열화/복구 Evidence | P0 | G60 |
| G60-C10 | DeltaManager Session Failover/Memory Test | P0 | G70 |
| G60-C11 | 실제 server.xml/setenv/application Config Snapshot | P0 | G70 |
| G60-C12 | L4 Idle/Sticky/KeepAlive 정합성 | P1 | G70 |
| G60-C13 | Error/Timeout Runtime 합격 임계치 | P1 | G70/G80 |
| G60-C14 | 전체 AP Pool × DB Session 상한 검증 | P0 | G60/G70 |

---

## 5. 다음 단계

```text
G60  CONDITIONAL PASS
 ↓
G70  Operations / HA-DR / Deployment
```

G70에서는 다음을 봉합한다.

- OM/Monitoring/Alert
- Failover/Failback
- RTO/RPO
- Center/Node Failure
- Session HA
- Rolling Deploy/Residual Capacity
- DR Cutover
- Runbook/Owner
- 운영 증적 수집 경계

