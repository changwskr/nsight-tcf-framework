# 04. NSIGHT Big Picture Architecture

## 0. 문서 상태

- 단계: STEP 04 — Big Picture
- Gate: G20 Big Picture / Logical
- 상태: **CONDITIONAL PASS**
- 기준일: 2026-08-18
- 목적: NSIGHT 전체를 제품 목록이 아니라 **책임·경계·허용 연결·장애 영향** 기준으로 재구성한다.

> [DECISION] NSIGHT Big Picture는 `Channel → Access → Security → Application → Framework → Integration → Data → Operation → Delivery → Infrastructure`의 10개 Architecture Zone으로 관리한다.
>
> [DECISION] 물리 시스템 그룹은 운영 구성자료의 `MP / RD / AD / DG / BL / IM` 축을 사용하되, 논리 Architecture Zone과 물리 시스템 그룹을 동일 개념으로 취급하지 않는다.
>
> [AS-IS] PDMG Reference는 `ServiceId`를 중심으로 인증→표준전문→공통처리→TCF→Timeout/Transaction→업무→DB→응답/운영추적을 연결한다.
>
> [TO-BE] NSIGHT 전체는 PDMG Reference에서 검증된 실행 메커니즘을 참고하되 NSIGHT TCF, 데이터 플랫폼, 인프라, 운영통제를 포함한 상위 Architecture로 확장한다.

---

## 1. Big Picture를 정의하는 원칙

NSIGHT Big Picture는 다음 4개를 동시에 보여주어야 한다.

```text
1. 무엇이 존재하는가        → Component / System
2. 누가 무엇을 책임지는가   → Responsibility / Ownership
3. 무엇과 무엇이 연결되는가 → Allowed Dependency / Contract
4. 장애가 어디까지 퍼지는가 → Failure Boundary / Isolation
```

따라서 제품명만 나열하는 그림은 Big Picture로 인정하지 않는다.

---

## 2. NSIGHT Enterprise Big Picture

```text
┌─────────────────────────────────────────────────────────────────────────────┐
│ 01 CHANNEL                                                                  │
│ Browser / 전용브라우저 / WebTopSuite / React / API Client                  │
│ Screen / Menu / Event / Program ID / ServiceId 결정                         │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 02 ACCESS / NETWORK                                                         │
│ GSLB → L4 → Apache WEB                                                      │
│ DNS / VIP / TLS / Routing / Health Check / Reverse Proxy                    │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 03 SECURITY                                                                 │
│ SSO / IdP / JWT Issuer / Access·Refresh Token / KMS / JWKS / Authorization │
│ User / Role / Menu / Function / ServiceId / Data Authorization              │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 04 APPLICATION                                                              │
│ MP Marketing Platform / Mini SingleView / BL / Credit / OM / Business Apps │
│ Business Domain / Program / ServiceId / Handler / Facade / Service          │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 05 FRAMEWORK / TCF                                                          │
│ System Pre/Post → Controller → TCF → STF → Timeout/Transaction              │
│ → ServiceId Dispatcher → Handler → ETF                                      │
│ Context / Validation / Error / Logging / Transaction Control                │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                 ┌──────────────┴───────────────┐
                 │                              │
                 ▼                              ▼
┌────────────────────────────────┐  ┌─────────────────────────────────────────┐
│ 06 INTEGRATION                 │  │ 07 DATA                                 │
│ HTTP/JSON / EAI / CDC / Kafka │  │ RDW / ADW / DB / Cache / Stage          │
│ External API / Domain Contract │  │ MyBatis / SQL / Table / View            │
└────────────────┬───────────────┘  └───────────────────┬─────────────────────┘
                 │                                      │
                 └──────────────────┬───────────────────┘
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 08 OPERATION / CONTROL                                                      │
│ OM / Service Catalog / Transaction Control / Runtime / Logging / ImageLog   │
│ Metric / Audit / Error / Batch / Deploy / Health / Slow Transaction         │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 09 DELIVERY                                                                 │
│ Git / Gradle·Maven / CI / Artifact / Deploy / Configuration / Rollback      │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 10 INFRASTRUCTURE                                                           │
│ DEV / PROD / DR                                                             │
│ WEB VM → Apache                                                             │
│ WAS VM → Tomcat JVM Instance → Application/WAR                              │
│ AP / DB / Appliance / Network / Storage                                     │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 해석

1. **구성요소 책임**: Channel은 사용자 상호작용, Access는 네트워크 진입, Security는 인증·인가, Application은 업무, TCF는 거래 생명주기, Integration은 시스템 경계, Data는 데이터 소유/접근, Operation은 통제·관측, Delivery는 변경 전달, Infrastructure는 실행 자원을 책임진다.
2. **연결 이유**: 업무의 논리적 거래 Identity를 `ServiceId`, 기술 추적 Identity를 `GUID/TraceId`로 연결하여 전 구간을 추적하기 위함이다.
3. **장애 영향**: Zone 경계마다 Timeout·Bulkhead·HA·Transaction·Ownership을 분리하여 장애 전파를 통제해야 한다.
4. **운영 포인트**: 운영자는 최소 `GUID + ServiceId + Host + Tomcat JVM + Thread + SQL/External`로 거래를 추적할 수 있어야 한다.

---

## 3. 물리 시스템 그룹과 Architecture Zone의 관계

운영 시스템 구성자료의 시스템 그룹은 다음과 같다.

| 코드 | 시스템 그룹 | 핵심 역할 | Big Picture Zone |
|---|---|---|---|
| `MP` | Marketing Platform | 온라인 마케팅, Mini SingleView, 실시간/행동 데이터 처리 | Application / Integration |
| `RD` | Real-time Data Warehouse | 온라인·실시간 조회 중심 데이터 | Data |
| `AD` | Analytical Data Warehouse | 분석·대용량 데이터 | Data |
| `DG` | Data Governance | 데이터 품질·흐름·Lineage | Data / Operation |
| `BL` | Business Analysis Layer | OLAP, BI Portal, Self-BI, 신용실적 | Application / Data |
| `IM` | Information Management | Framework, 단말, Batch, CDC, ETL, 출력, IMDG | Operation / Integration / Delivery |
| `TEMP` | Migration Temporary | 이행·검증 임시 자원 | Migration / Infrastructure |

> [DECISION] `MP/RD/AD/DG/BL/IM`은 물리·시스템 관리축이며, `Channel/Security/Application/...`은 논리 책임축이다. 둘을 1:1 동일시하지 않는다.

---

## 4. Online Transaction Big Picture

PDMG Reference에서 확인된 실행 구조를 NSIGHT 논리 모델로 정리하면 다음과 같다.

```text
Client / UI
   │
   │ Standard Request
   │ Common Header + Business DTO
   ▼
GSLB / L4 / Apache
   ▼
Tomcat JVM Instance
   ▼
System Filter / Interceptor / Resolver
   │  GUID / JWT / Header / ServiceContext / Log
   ▼
OnlineTransactionController
   ▼
TCF / TcfFacade
   ├─ TransactionContext
   ├─ STF / Control
   ├─ Timeout Executor
   ├─ Transaction Boundary
   ▼
ServiceId Dispatcher
   ▼
Handler
   ▼
Facade
   ▼
Service
   ├───────────┬───────────────┐
   ▼           ▼               ▼
 Rule*        DAO       Integration Client
               │               │
               ▼               ▼
             Mapper        HTTP / EAI
               │
               ▼
              SQL
               │
               ▼
             RDW / DB
   │
   ├─ Success → COMMIT
   └─ Error   → ROLLBACK
   ▼
ETF / Exception / Standard Response
   ▼
Client

       ↕ GUID + ServiceId
       ↕ Log / ImageLog / Metric / Audit / OM
```

`Rule*`은 목표 책임으로는 유효하나 PDMG AS-IS에서 독립 계층이 일반화되지 않았으므로 별도 표시한다.

---

## 5. Big Picture Zone 책임표

| Zone | 책임 | Inbound | Outbound | 허용 연결 | 금지 연결 | 대표 장애 영향 | 운영 포인트 |
|---|---|---|---|---|---|---|---|
| Channel | 화면·이벤트·요청 생성 | 사용자 | Access/Security | 공개 API/Service Contract | DB/DAO 직접접근 | 화면/API 장애 | Screen→ServiceId 추적 |
| Access | 라우팅·TLS·Health | Channel | WAS | Apache Proxy, L4 Pool | 업무로직 | WEB 장애/라우팅 장애 | Access/Proxy Log |
| Security | 인증·인가·키 경계 | Channel/IdP | Application/TCF | JWT/JWKS/Context | Private Key 확산 | 인증 전체 장애 | Token/Key/Audit |
| Application | 업무 Use Case | TCF/Domain Client | Data/Integration | Handler→Facade→Service | 타 Domain DAO/Table | 업무 도메인 장애 | ServiceId/Business Log |
| Framework | 거래 생명주기 | Controller | Handler | STF/Timeout/TX/Dispatcher | 업무 데이터 소유 | Framework 공통 장애 | Transaction/Timeout Metric |
| Integration | 도메인/외부 계약 | Service | Target System | HTTP/JSON/EAI/Kafka | 내부 DAO 공유 | 장애 전파 | Timeout/Error/Retry |
| Data | 데이터 소유·조회·변경 | DAO/ETL/CDC | DB/Downstream | Owner를 통한 접근 | 타 Domain 직접 갱신 | DB/Pool/SQL 병목 | SQL/Session/Storage |
| Operation | 통제·관측·감사 | Runtime | Operator | Catalog/Log/Metric | 업무 DB 임의변경 | 관측 상실/통제 오류 | GUID+ServiceId |
| Delivery | Build/Deploy | Source | Runtime | Versioned Artifact | 수동 비추적 배포 | 잘못된 Release | Commit→Artifact→Deploy |
| Infrastructure | 실행자원 | Delivery/Traffic | Runtime | 표준 VM/JVM/Network | 논리 책임 혼재 | Node/JVM/Center 장애 | Host/JVM/Capacity |

---

## 6. Domain Boundary Big Picture

도메인 간 연계는 **내부 구현 공유가 아니라 공개 계약**으로 취급한다.

```text
MG Domain
   │
   │ 공개 Service Contract
   │ target ServiceId = mk...
   ▼
MG → MK Client / Port
   │ HTTP + Standard Message
════════════════ DOMAIN BOUNDARY ════════════════
   ▼
MK Entry / TCF
   ▼
MK Service
   ▼
MK DAO / Mapper
   ▼
MK Owned Data
```

### 필수 정책

- 다른 Domain DAO 직접 호출 금지
- 다른 Domain Mapper 직접 호출 금지
- 다른 Domain 전용 Table 직접 갱신 금지
- 별도 WAR/Application 경계는 HTTP + 표준전문 + 대상 ServiceId 사용
- 호출자는 상대 Domain의 내부 Facade/Service 구조를 알지 않는다
- MG→MK→MG 형태의 순환 동기 호출 금지
- 변경성 연계는 분산 Local Transaction으로 간주하지 않는다
- 호출 계약에는 ServiceId, Timeout, 오류, 권한, 로그, 멱등성/보상 정책을 포함한다

---

## 7. Data Flow Big Picture

NSIGHT 전략은 실시간 반응 경로와 분석 경로를 분리한다.

```text
                    Source / Customer Event
                              │
          ┌───────────────────┴────────────────────┐
          │                                        │
          ▼                                        ▼
     FAST FLOW                                 DEEP FLOW
  Event / Kafka                              CDC / ETL
          │                                        │
          ▼                                        ▼
 Real-time Processing                          RDW / Stage
          │                                        │
          ▼                                        ▼
 Offer / Action                         DataStage / Transform
                                                   │
                                                   ▼
                                                  ADW
                                                   │
                                                   ▼
                                          BI / Analytics / OLAP
```

> [TO-BE] FAST와 DEEP은 처리 목적·Latency·자원 사용이 다르며 동일 런타임/DB 자원을 무제한 공유하지 않는다.
>
> [DECISION] RDW는 온라인·실시간 조회 보호, ADW는 분석·대용량 처리 격리를 우선한다.

---

## 8. WEB/WAS Big Picture

```text
GSLB / L4
   ↓
WEB Server / VM
   ↓
Apache Instance
   ├─ Listen / VirtualHost #1
   ├─ Listen / VirtualHost #2
   └─ ...
   ↓
WAS Server / VM
   ├─ Tomcat JVM Instance #1
   │    └─ Application / WAR
   ├─ Tomcat JVM Instance #2
   │    └─ Application / WAR
   └─ ...
   ↓
DB / Integration
```

### Big Picture 결정

- `[DECISION]` WEB 표준은 Apache, WAS 표준은 Tomcat으로 관리한다.
- `[DECISION]` `WAS Server/VM`, `Tomcat JVM Instance`, `Application/WAR`를 서로 다른 관리단위로 본다.
- `[FACT/WORKING]` 기존 구성도의 `Container`는 독립 Tomcat JVM Instance로 해석하는 기준이 적용돼 왔다.
- `[OPEN]` 모든 실제 서버에 대해 `CATALINA_BASE`, Port, WAR, JVM PID가 증적화된 것은 아니다.

---

## 9. Operation / Control Big Picture

```text
CONTROL PLANE
Operator / OM
   ├─ Service Catalog
   ├─ Transaction Control
   ├─ Timeout Policy
   ├─ User / Role / Menu
   ├─ Error / Config / Common Code
   └─ Deployment / Batch / Session

                ↓ policy / control

RUNTIME PLANE
ServiceId
   ↓
TCF / Transaction
   ↓
Application
   ↓
DB / Integration
   ↓
Log / ImageLog / Metric / Audit
                │
                └──────────────→ OM / Runtime Evidence
```

최종 운영 질문은 다음 수준까지 내려가야 한다.

```text
어느 ServiceId가 느린가?
→ 어느 Host/Tomcat JVM인가?
→ 어느 Thread인가?
→ 어느 SQL/외부연계인가?
→ Transaction은 어디까지 진행됐는가?
→ Timeout/Pool/GC 중 무엇이 원인인가?
```

---

## 10. 정상 연결 / 금지 연결

### 정상

```text
Channel → Public API / ServiceId
Apache → Tomcat Connector
TCF Dispatcher → Handler
Handler → Facade
Facade → Service
Service → Rule / DAO / Integration Port
DAO → Mapper → Owned Data
Domain A → Domain B Public Service Contract
Runtime → OM Evidence
```

### 금지

```text
UI → DB
Controller → DAO 직접 호출
Handler → DAO 직접 호출
Service → Mapper 직접 호출
Domain A → Domain B DAO
Domain A → Domain B Mapper
Domain A → Domain B Owned Table 직접 갱신
WAR A → WAR B 내부 구현 Java Dependency
Client/업무 WAR에 JWT Private Key 배포
분석성 부하가 온라인 RDW를 무제한 점유
```

일부 계층 금지는 Target Logical Rule이며 실제 AS-IS와 다르면 GAP으로 관리한다.

---

## 11. 장애 격리 관점

| 장애 단위 | 1차 격리 단위 | 상위 보호장치 | 주요 확인 |
|---|---|---|---|
| Apache | WEB Instance/VM | L4/GSLB | Health, Routing |
| Tomcat JVM | JVM Instance | L4/Apache Pool | JVM/Port/App |
| Application | ServiceId/Application | TCF/OM | Error/Control |
| Thread | Executor/Pool | Timeout/Bulkhead | Busy/Pending |
| DB Pool | Datasource/JVM | Pool Limit/Timeout | Active/Pending |
| SQL | Statement | Query Timeout | Elapsed/Plan |
| Domain Call | Integration Port | Timeout/CB/Idempotency | Failure Propagation |
| DB | RDW/ADW | HA/DR | Session/Replication |
| Center | PROD/DR | DR | RTO/RPO/Failback |

---

## 12. Current vs Target

| 영역 | 현재 확인 | 목표 | 상태 |
|---|---|---|---|
| ServiceId 중심 실행 | PDMG/TCF에서 확인 | NSIGHT 전체 Trace Key | `[AS-IS][TO-BE]` |
| TCF | Reference와 NSIGHT Source 존재 | 공통 거래 Runtime | `[CURRENT/TO-BE]` |
| Domain Boundary | MG/MK 규칙 정의 | 전 Domain 표준화 | `[PARTIAL]` |
| RDW/ADW | 시스템 분리 자료 존재 | 부하·책임 격리 | `[DECISION]` |
| WEB/WAS | Apache/Tomcat 구성 확인 | JVM/App까지 자산화 | `[PARTIAL]` |
| OM | 기능/Source가 분산 또는 Drift | Control+Evidence Plane | `[GAP]` |
| Runtime Evidence | Log/Metric 기반 존재 | Gate와 Closed Loop 연결 | `[GAP]` |

---

## 13. Big Picture GAP

| GAP ID | 내용 | 영향 | 우선순위 |
|---|---|---|---|
| `G20-BP-001` | 전체 Application/WAR ↔ Domain ↔ ServiceId Catalog 미완성 | Traceability | P0 |
| `G20-BP-002` | 모든 Domain의 Data Ownership Catalog 미완성 | 직접 DB 결합 위험 | P0 |
| `G20-BP-003` | 실제 Apache→Tomcat Routing 증적 미완성 | Physical 연결 오판 | P1 |
| `G20-BP-004` | Tomcat JVM↔WAR↔Server 실증 매핑 미완성 | 장애단위 오판 | P0 |
| `G20-BP-005` | OM Control Plane과 Runtime Evidence 연결 미완성 | 운영 Gate 약화 | P0 |
| `G20-BP-006` | FAST/DEEP 전체 Interface Catalog 미완성 | 데이터 흐름 추적 부족 | P1 |

---

## 14. G20 Big Picture 판정

**CONDITIONAL PASS**

### PASS 근거

- 전략, 시스템 그룹, PDMG Reference Runtime, ServiceId/TCF, RDW/ADW, Apache/Tomcat 등 상위 구조는 서로 연결 가능한 수준으로 확인됨.
- 책임과 경계, 허용/금지 연결을 정의할 수 있는 Evidence가 확보됨.

### 조건

1. Application/WAR/ServiceId Inventory 완성
2. Domain별 Data Ownership 확정
3. Apache/Tomcat/JVM 실제 설정 증적 확보
4. OM/Runtime Evidence 책임 경계 확정
5. Integration Contract Catalog 작성

---

## 15. 다음 단계

G30 Physical Architecture에서는 다음을 실제 자원에 매핑한다.

```text
Logical Component
   ↓
Application / WAR
   ↓
Tomcat JVM Instance
   ↓
WAS Server / VM
   ↓
WEB / L4 / GSLB
   ↓
DB / Appliance
   ↓
PROD / DR Pair
```
