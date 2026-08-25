# NSIGHT Architecture Vision & NFR — G10

## 1. Architecture Vision

> **끊김 없는 데이터 관리를 통해 고객 행동에 즉시 반응하는 실시간 경영 기반의 시스템**

NSIGHT의 목표는 기존 배치 중심 정보계를 단순히 교체하는 것이 아니라, 데이터가 흐르고 반응하며 경영 판단을 지원하는 플랫폼으로 전환하는 것이다.

```text
Batch-centric Information System
            ↓
Near Real-time Data Flow
            +
Event-driven Response
            +
Separated Analytics
            +
Observable / Governed Runtime
            ↓
Real-time Management Platform
```

## 2. 핵심 Architecture Principle

| Principle | 정의 |
|---|---|
| Domain Separation | 역할·책임·장애영향을 도메인/기능별로 분리 |
| Data-Centric | 데이터 흐름을 명시하고 경로를 강제 |
| Integration Control | P2P/DB Link를 통제하고 표준 연계 사용 |
| FAST/DEEP Isolation | 실시간 반응과 분석 경로의 절대적 자원 격리 |
| Fault Isolation | 장애가 다른 도메인/경로로 확산되지 않도록 설계 |
| Scale-Out First | 온라인 서비스는 작은 단위의 수평 확장 우선 |
| Runtime Evidence | 문서가 아니라 실제 Test/Metric/Log로 NFR 검증 |

## 3. 5대 NFR

### 3.1 Performance

| 대상 | 목표 | 상태 |
|---|---:|---|
| 실시간 마케팅 오퍼링 | 1초 이내 | Strategy Target |
| 이벤트 처리 | 1초 이내 | Strategy Target |
| CDC 데이터 통합 | 30초 이내 | Strategy Target |
| 일반 온라인 거래 | p95 ≤ 3초 | Application Capacity Target |
| 일반 Peak | 600 TPS | Working |
| 설계 Peak | 1,200 TPS | Working |
| Stress | 1,800 TPS | Working/Test |

`1초`와 `p95 3초`는 충돌값으로 보지 않는다. 대상 서비스 Scope가 다르다.

- 1초: 이벤트/오퍼링 Fast Path
- p95 3초: 일반 온라인 업무 트랜잭션

### 3.2 Availability

- AP 레벨 Active-Active 목표
- 기능/도메인별 서버 독립 분리
- 장애 격리
- DR 활용
- RTO 30분 이내 목표
- Fail-Over뿐 아니라 Fail-Back 절차까지 Architecture 범위에 포함

### 3.3 Scalability

- 온라인 서비스: VM Scale-Out 우선
- RDW/ADW: Exadata 노드/병렬 확장
- FAST와 DEEP 경로를 독립적으로 확장
- 서버/JVM/Application 장애영향 단위를 작게 유지

### 3.4 Security

- 물리 망분리
- SSO 통합인증
- JWT 기반 인증/인가 구조
- 구간 암호화
- 개인정보 마스킹
- 설계 단계 보안 내재화
- Key/Token lifecycle은 Runtime Security Gate에서 검증

### 3.5 Observability

- APM 기반 관측
- GUID/Trace-ID 전문 체계
- ServiceId 기반 거래 추적
- 전사 통합 로그/알림
- Host/JVM/Thread/DB/External Call까지 End-to-End 연계
- Runtime Evidence 없는 항목은 최종 PASS 금지

## 4. NFR ↔ Architecture Mechanism 연결

| NFR | 주요 Mechanism |
|---|---|
| Performance | Kafka Fast Path, RDW/ADW 분리, Tomcat/Hikari/SQL Capacity Chain |
| Availability | Active-Active, N+1, DR, Domain/Fault Isolation |
| Scalability | VM Scale-Out, 독립 Tomcat JVM, Exadata 병렬 확장 |
| Security | SSO/JWT/JWKS/KMS, Authorization, Masking, Audit |
| Observability | GUID + ServiceId, APM, Log, ImageLog, Runtime Metric |

## 5. FAST / DEEP Runtime Vision

### FAST

```text
Customer Action
    ↓
Kafka Event
    ↓
Marketing Rule
    ↓
Real-time Offering
```

목표: 이벤트/오퍼링 1초 이내.

### DEEP

```text
Source DB
   ↓ CDC
RDW
   ↓ DataStage
ADW
   ↓
BI / Analytics / Management Decision
```

목표: CDC 지연 30초 이내, 분석 부하를 온라인 거래에서 격리.

## 6. G10 판정

**PASS**

Vision, 5대 NFR, FAST/DEEP 경계, Logical/Physical/Mechanism으로 내려갈 상위 원칙이 복수 전략 문서에서 일치한다.

단, `NFR 목표가 실제 Runtime에서 달성되는지`는 G60/G80에서 별도 검증한다.
