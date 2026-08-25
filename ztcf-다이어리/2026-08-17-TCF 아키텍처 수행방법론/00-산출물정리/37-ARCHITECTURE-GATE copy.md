# NSIGHT Architecture Gate Status

| Gate | 상태 | 핵심 결과 | 다음 조건 |
|---|---|---|---|
| G00 Source Baseline | CONDITIONAL PASS | Source Scope/Canonical Rule 확정 | Branch/Commit, Root TCF Build, 운영 Config는 UNKNOWN 관리 |
| G10 Vision/NFR | PASS | Vision/5대 NFR/FAST-DEEP 원칙 확정 | Runtime 달성 검증은 후속 Gate |
| G20 Big Picture/Logical | NEXT | 미실행 | Domain/책임/연계금지/Data Flow 기준화 |
| G30 Physical | WAIT | 서버/미들웨어 자료 존재 | App/JVM/Server Mapping |
| G40 Mechanism | WAIT | TCF/PDMG Evidence 풍부 | AS-IS/TO-BE 분리 |
| G50 Security/Data | WAIT | 설계자료 존재 | Key/Data Contract 검증 |
| G60 Capacity/Runtime | WAIT | Working Baseline 존재 | Load Test/Config Evidence 필요 |
| G70 Operations/HA-DR | WAIT | 부분 자료 존재 | Failover/Failback/Residual Capacity |
| G80 Closed Loop | WAIT | Model/Rules 부분 구현 | Runtime Evidence Gate |
| HG90 Human Gate | WAIT | - | Critical ADR/GAP 종료 |

현재 진행 위치:

```text
G00  CONDITIONAL PASS
  ↓
G10  PASS
  ↓
G20  NEXT
```


---

## G20 — Big Picture / Logical Architecture

**판정: CONDITIONAL PASS**

### 완료된 항목

- 10개 Architecture Zone 정의: Channel / Access / Security / Application / Framework / Integration / Data / Operation / Delivery / Infrastructure
- 운영 시스템 그룹 `MP/RD/AD/DG/BL/IM`과 논리 Zone 분리
- ServiceId 중심 Online Transaction Big Picture 정의
- Application Layer 책임/의존 방향 정의
- MG↔MK Cross-Domain Boundary Rule 구체화
- Cross-Domain Transaction 및 Timeout Budget 원칙 정의
- RDW/ADW Data Responsibility 분리
- WEB Server / Apache / WAS Server / Tomcat JVM / Application의 개념 분리
- Big Picture/Logical GAP 및 ADR 후보 등록

### Conditional Pass 조건

| Condition ID | 조건 | 우선순위 | 다음 Gate |
|---|---|---:|---|
| G20-C01 | 전체 Domain Catalog/Owner 확정 | P0 | G30/G40 |
| G20-C02 | Domain별 Owned Table/View Catalog | P0 | G50 |
| G20-C03 | Public ServiceId/Integration Contract Registry | P0 | G40/G50 |
| G20-C04 | 71대 서버 ↔ JVM ↔ Application/WAR 실제 매핑 | P0 | G30 |
| G20-C05 | Apache→Tomcat 실제 Routing Config 증적 | P1 | G30 |
| G20-C06 | ServiceId Deadline/Timeout Metadata 연결 | P0 | G40/G60 |
| G20-C07 | OM/Runtime Evidence 책임 경계 확정 | P0 | G70/G80 |

### Gate 진행상태

```text
G00 Source Baseline             CONDITIONAL PASS
G10 Vision / NFR                PASS
G20 Big Picture / Logical       CONDITIONAL PASS
G30 Physical                    NEXT
G40 Mechanism / Source          WAIT
G50 Security/Data/Integration   WAIT
G60 Capacity / Runtime          WAIT
G70 Operations/HA-DR/Deploy     WAIT
G80 Closed Loop / Drift         WAIT
HG90 Final Human Gate           WAIT
```
