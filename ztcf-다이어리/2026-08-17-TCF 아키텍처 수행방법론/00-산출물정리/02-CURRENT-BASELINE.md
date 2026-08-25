# NSIGHT Current Architecture Baseline — STEP 02

## 1. Baseline 상태 모델

| 상태 | 정의 |
|---|---|
| FACT | Source/Config/문서/이미지에서 직접 확인 |
| CONFIRMED | 복수 Evidence가 일치 |
| AS-IS | 현재 Source 또는 운영 구조 |
| TO-BE | 목표 아키텍처 |
| WORKING | 현재 작업 기준이나 검증/승인 필요 |
| GAP | AS-IS와 TO-BE 또는 Evidence 간 불일치 |
| UNKNOWN | Evidence 부족 |

## 2. Current Baseline 핵심

### 2.1 Strategy

- `[CONFIRMED]` NSIGHT는 단순 DW 고도화가 아니라 실시간·데이터 중심 경영 플랫폼으로 전환한다.
- `[CONFIRMED]` Architecture 방법론은 `Vision → Big Picture → Logical → Physical → Mechanism → Runtime`이다.
- `[TO-BE]` FAST(실시간 반응)와 DEEP(분석/전략)의 자원·경로를 분리한다.
- `[TO-BE]` RDW와 ADW를 물리/책임 관점에서 분리한다.

### 2.2 Application / Framework

- `[AS-IS]` PDMG의 Framework/업무 기본 흐름은 `Filter → Interceptor → Controller → TCF/STF → Timeout/Transaction → Dispatcher → Handler → Facade → Service → DAO/Mapper`로 분석된다.
- `[CONFIRMED]` ServiceId는 Routing과 Traceability의 핵심 식별자다.
- `[AS-IS]` PDMG Source Snapshot에는 독립 `pdmg-om` 모듈이 없다.
- `[AS-IS]` `tcf-om`은 별도 모듈이며 실제 Handler는 25개다.

### 2.3 Build / Runtime

- `[FACT]` PDMG 주요 모듈 Java Toolchain = 21.
- `[FACT]` PDMG 주요 Source는 Spring Boot 3.5.14 계열.
- `[FACT]` PDMG Service는 WAR 배포 구조.
- `[GAP]` TCF 전체를 묶는 Root Gradle Build는 현재 ZIP Root에서 확인되지 않는다.

### 2.4 WEB/WAS

- `[CONFIRMED]` WEB 표준 = Apache.
- `[CONFIRMED]` WAS 표준 = Tomcat.
- `[DECISION/WORKING]` 구성도상의 Container는 독립 Tomcat JVM Instance로 해석한다.
- `[OPEN]` 실제 `CATALINA_BASE`, JVM/Port/Deploy mapping은 운영 Config Evidence로 최종 확정한다.

### 2.5 Physical / Inventory

- `[CURRENT WORKING]` 서버 Master Inventory는 71대 Working Baseline을 사용한다.
- `[CONFIRMED]` 서버 1대 = Master Inventory 1 Row.
- `[CONFIRMED]` 운영/개발/DR, Hostname, 역할, CPU/MEM/Disk/tpmC, HA/DR를 연결하는 방향을 사용한다.

### 2.6 Performance

현재는 Versioned Baseline으로 유지한다.

| Metric | Working | Historical/Alternative | 상태 |
|---|---:|---:|---|
| 전체 사용자 | 36,000 | 동일 | CONFIRMED |
| 설계 세션 | 약 43,000~47,000 | 동일 | CONFIRMED |
| Session Idle | 90m | 60m | OPEN |
| 일반 Peak | 600 TPS | 동일 | WORKING |
| 설계 Peak | 1,200 TPS | 동일 | WORKING |
| Stress | 1,800 TPS | 동일 | WORKING |
| 일반 온라인 SLA | p95 ≤ 3s | 동일 | WORKING/Target |
| 16Core Capacity | 약 855 TPS | 500 TPS | Working vs Conservative |
| Tomcat maxThreads | 800~1,000 | 1,200~1,500/이전 산정 | Scope별 분리 필요 |
| Hikari 일반 | 120~150 또는 150 | 80~100 | Load/DB Session 검증 필요 |

### 2.7 Security

- `[TO-BE]` SSO + JWT 구조.
- `[TO-BE]` Private Key는 Token Issuer 경계에 둔다.
- `[TO-BE]` Public Key/JWKS는 검증 주체에 배포 가능.
- `[OPEN]` Key Rotation, `kid`, Refresh Rotation/Revocation, Gateway 우회 방어는 상세 ADR/Test 필요.

### 2.8 Data

- `[TO-BE]` CDC → RDW 실시간 경로.
- `[TO-BE]` Kafka → Marketing Rule 실시간 이벤트 경로.
- `[TO-BE]` RDW → DataStage → ADW 분석 경로.
- `[TO-BE]` P2P/DB Link 직접 연결을 통제하고 표준 Integration 경로 사용.

### 2.9 Closed Loop

- `[TO-BE]` Document → Model → Code → Test → Runtime Evidence → Drift → GAP/ADR → New Baseline.
- `[GAP]` Model/Code는 상당히 존재하나 Test→Runtime Evidence→Gate 연결은 미완성.

## 3. Current Baseline Architecture Anchor

```text
Strategy / Requirement
        ↓
Domain / Data Boundary
        ↓
ServiceId / Application
        ↓
TCF Runtime
        ↓
WEB / WAS / JVM / DB
        ↓
Capacity / Security / HA-DR
        ↓
Logging / OM / Runtime Evidence
        ↓
Architecture Gate
```

## 4. 다음 확정 대상

1. Session Strategy/Timeout ADR
2. Transaction Owner/Timeout ADR
3. 16Core Capacity 성능시험 기준
4. Tomcat/Hikari 정합화
5. Apache/Tomcat 운영 Config Evidence
6. JWT Key Rotation/Revocation
7. Application/WAR ↔ JVM ↔ Server Mapping
