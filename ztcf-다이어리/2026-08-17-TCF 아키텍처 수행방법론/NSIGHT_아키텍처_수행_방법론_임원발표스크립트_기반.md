# NSIGHT 아키텍처 수행 방법론
## 임원 발표 스크립트 기반 실행형 정리본

- 문서 성격: Architecture Methodology
- 대상: NH 농협 상호금융 차세대 정보계 NSIGHT
- 기준 자료:
  - `2026-02-18 Future 발표 스크립터 조정의 사본 (임원) - Type 3 - JUNO.docx`
  - `Future 아키텍처 임원 발표 스크립트 — Type 3 JUNO 정리본.md`
  - `2026-03-08-NH_아키텍처전략_정리본_(최종본)_V1.0.docx`
- 핵심 철학: **“아키텍처는 생존을 위한 설계”**
- 핵심 수행 흐름: **Vision → Big Picture → Logical → Physical → Mechanism → Runtime**
- 최종 지향점: **향후 10년을 견딜 수 있는 확장 가능하고, 장애에 강하며, 데이터 중심으로 움직이는 운영 가능한 플랫폼**

> 이 문서는 임원 발표 스크립트에 제시된 6단계 아키텍처 수립 방법론을 실제 프로젝트에서 수행할 수 있도록 활동·산출물·검증질문·완료기준 중심으로 재구성한 문서이다.  
> 발표 자료에 직접 명시되지 않은 세부 활동은 새로운 기술 기준을 추가한 것이 아니라, 발표 내용의 의도를 실행 절차로 풀어 쓴 것이다.

---

# 1. 방법론의 출발점

NSIGHT 아키텍처 수행의 출발점은 기술이나 솔루션 선정이 아니다.

프로젝트가 해결하려는 목적을 먼저 정의하고, 그 목적을 지탱할 비기능 요구사항을 수립한 뒤, 전체 공간과 책임을 나누고, 정책을 만들고, 물리 구조에 배치하고, 실제로 움직이는 규칙을 정의한 다음, 런타임에서 검증한다.

```text
Business / Future Direction
          │
          ▼
┌────────────────────┐
│ 1. Vision          │
│ 목적 + NFR         │
└─────────┬──────────┘
          ▼
┌────────────────────┐
│ 2. Big Picture     │
│ 공간 + 책임 + 경계 │
└─────────┬──────────┘
          ▼
┌────────────────────┐
│ 3. Logical         │
│ 정책 + 허용/금지   │
└─────────┬──────────┘
          ▼
┌────────────────────┐
│ 4. Physical        │
│ 실제 자원 배치     │
└─────────┬──────────┘
          ▼
┌────────────────────┐
│ 5. Mechanism       │
│ 실행 규칙          │
└─────────┬──────────┘
          ▼
┌────────────────────┐
│ 6. Runtime         │
│ 서비스 흐름 검증   │
└─────────┬──────────┘
          ▼
  운영·유지관리·보완
```

핵심 관계는 다음과 같다.

```text
Vision
  ↓
Big Picture
  ↓
Logical Policy
  +
Physical Structure
  ↓
Mechanism
  ↓
Runtime Validation
  ↓
보완사항 발견
  └──────────────→ Logical / Physical / Mechanism 재조정
```

발표 자료의 표현을 수행 관점으로 정리하면 다음과 같다.

> **논리 설계는 정책을 만든다.**  
> **물리 설계는 정책이 실행될 공간을 만든다.**  
> **메커니즘은 논리와 물리를 실제로 움직이게 한다.**  
> **런타임은 그 결과가 최초의 Vision을 만족하는지 검증한다.**

---

# 2. 방법론 전체 요약

| 단계 | 핵심 질문 | 수행 내용 | 대표 산출물 |
|---|---|---|---|
| 1. Vision | 왜 이 시스템을 만드는가? | 비전, 목표, NFR, SLA 수립 | 비전·NFR 정의서 |
| 2. Big Picture | 어떤 공간으로 나누고 누가 책임지는가? | End-to-End 흐름, 도메인, 책임, 경계 정의 | 아키텍처 공간배치도 |
| 3. Logical | 무엇을 허용하고 무엇을 금지할 것인가? | 도메인 분리, 데이터 흐름, 인터페이스, 온라인/배치 정책 | 논리 아키텍처 정책 |
| 4. Physical | 논리 정책을 어떤 실제 자원에 배치할 것인가? | 서버, DB, 클라우드, 전용자원, 이중화, DR, 자원격리 | 물리 아키텍처도 |
| 5. Mechanism | 실제 시스템은 어떤 표준과 규칙으로 움직이는가? | HTTP/JSON, Framework, Integration, CDC/Kafka, DataStage 등 실행 규칙 | 메커니즘 정의서 |
| 6. Runtime | 설계한 구조가 실제 서비스에서 목적을 달성하는가? | FAST/DEEP 등 대표 시나리오를 통한 실행·성능·흐름 검증 | 런타임 검증 결과 |

---

# 3. 전 단계 공통 기준 — 5대 NFR

6단계 수행 전체를 관통하는 기준은 5대 비기능 요구사항이다.

NFR은 특정 단계에서 한 번 정의하고 끝나는 요구사항이 아니라, Big Picture·Logical·Physical·Mechanism·Runtime의 모든 의사결정을 평가하는 공통 기준이다.

| NFR | 발표 기준 | 아키텍처 적용 방향 |
|---|---|---|
| Performance | 마케팅 응답 3초 이내, CDC 최대 30초, 이벤트 1초 이내 | 실시간/배치 분리, RDW/ADW 분리, 이벤트 경량 처리 |
| Availability | 무중단 지향, AP 수준 Active-Active | 기능별 서버 분리, 장애격리, DR 활용 |
| Scalability | 서비스 Scale-Out, 데이터 병렬 확장 | 프라이빗 클라우드 VM 수평 확장, Exadata 노드/랙 확장 |
| Security | 설계 단계 보안 내재화 | 망분리, SSO, 구간 암호화, 개인정보 마스킹 |
| Observability | 전 구간 거래 추적, 사전 통제 | APM, 통합로그, SLA, Trace ID/GUID |

모든 주요 의사결정은 최소한 다음 질문을 통과해야 한다.

```text
이 선택은 성능을 만족하는가?
이 선택은 장애를 격리하는가?
이 선택은 확장 가능한가?
이 선택은 보안을 구조적으로 내재화하는가?
이 선택은 운영자가 측정하고 추적할 수 있는가?
```

---

# 4. STEP 1 — Vision

## 4.1 목적

Vision 단계는 기술을 선택하는 단계가 아니라 **시스템이 존재해야 하는 이유와 향후 지향점을 정의하는 단계**다.

NSIGHT의 비전은 다음 방향으로 제시되어 있다.

> **“끊김 없는 데이터 관리를 통해 고객 행동에 즉시 반응하는 실시간 경영 기반의 시스템”**

기존 배치 중심 정보계를 단순 고도화하는 것이 아니라, 저장 중심 시스템을 **실시간 의사결정 및 반응형 경영 플랫폼**으로 전환하는 것이 핵심이다.

## 4.2 수행 활동

1. 기존 정보계의 한계를 정의한다.
2. 미래 정보계가 제공해야 할 비즈니스 가치를 정의한다.
3. 아키텍처 비전을 한 문장으로 선언한다.
4. 비전을 정량적으로 지탱할 NFR과 SLA를 수립한다.
5. 이후 모든 설계 판단의 기준이 될 핵심 원칙을 정리한다.

## 4.3 핵심 검증 질문

- 이 시스템은 단순 교체인가, 구조 재설계인가?
- 고객 행동과 데이터 변화에 얼마나 빨리 반응해야 하는가?
- 실시간 의사결정이라는 목적을 측정 가능한 SLA로 표현했는가?
- 향후 10년간 확장과 변화에 대응할 수 있는 방향인가?
- 성능·가용성·확장성·보안·관측성이 비전과 연결되어 있는가?

## 4.4 완료 기준

```text
[ ] Architecture Vision이 한 문장으로 정의되었다.
[ ] 5대 NFR이 정의되었다.
[ ] 주요 SLA가 정량화되었다.
[ ] 기존 구조에서 무엇을 바꿔야 하는지 명확하다.
[ ] 이후 설계 의사결정을 평가할 기준이 확보되었다.
```

## 4.5 산출물

- Architecture Vision
- NFR 5대 원칙
- SLA 목표
- 아키텍처 핵심 설계 원칙

---

# 5. STEP 2 — Big Picture

## 5.1 목적

Big Picture의 목적은 구성요소를 많이 그리는 것이 아니다.

**전체 시스템의 공간을 나누고, 각 공간의 책임과 경계를 명확하게 정의하는 것**이 핵심이다.

발표 스크립트에서 강조하는 원칙은 다음과 같다.

> **“경계가 명확해야 확장이 쉽고, 장애가 격리되고, 운영 비용이 통제된다.”**

## 5.2 기본 End-to-End 흐름

```text
Channel
   ↓
Interface
   ↓
Data Platform
   ↓
Analytics
   ↓
Marketing / BI Service
   ↓
Business Feedback
```

고객 접점에서 발생한 데이터는 표준 인터페이스를 통해 데이터 플랫폼으로 들어가고, 통합·저장·분석된 결과는 마케팅 플랫폼과 BI 포탈을 통해 다시 현장으로 피드백된다.

## 5.3 주요 공간과 책임

| 영역 | 책임 |
|---|---|
| Data Platform | RDW/ADW, CDC·Kafka·ETL 기반 데이터 흐름 및 저장 |
| Marketing Platform | 고객 행동 기반 실시간 마케팅 및 Single View |
| BI Portal | 현업 분석, 자연어 질의, Reporting |
| Data Governance | 데이터 표준·품질·메타 관리 |
| IT Service / Infra | SSO, 배치관리, APM, 로그, DevOps 등 공통 운영기반 |

## 5.4 수행 활동

1. End-to-End 데이터 및 서비스 흐름을 그린다.
2. 주요 서비스 도메인과 플랫폼을 식별한다.
3. 각 영역의 책임과 소유 범위를 정한다.
4. 영역 간 연결 지점을 식별한다.
5. 직접 연결이 발생할 수 있는 위험 지점을 찾는다.
6. 장애와 성능 관점에서 반드시 격리해야 할 영역을 표시한다.

## 5.5 Big Picture 핵심 원칙

```text
채널은 채널의 역할만 수행한다.
플랫폼은 플랫폼의 역할만 수행한다.
데이터 영역은 데이터 책임을 가진다.
분석은 온라인 거래 자원과 경쟁하지 않는다.
영역 간 연결은 표준 경계를 통해서만 수행한다.
```

## 5.6 완료 기준

```text
[ ] End-to-End 흐름이 한 장에서 설명된다.
[ ] 주요 도메인/플랫폼의 책임이 겹치지 않는다.
[ ] 각 경계의 In/Out이 식별되어 있다.
[ ] P2P 직접 연결 위험이 식별되어 있다.
[ ] 온라인·배치·분석·이벤트 영역의 경계가 보인다.
[ ] 장애 발생 시 영향 범위를 설명할 수 있다.
```

## 5.7 산출물

- Architecture Big Picture
- 공간/Zone 정의
- Domain Scope
- Responsibility Matrix
- 주요 Interface Boundary

---

# 6. STEP 3 — Logical Architecture

## 6.1 목적

Logical 단계는 솔루션 제품을 고르는 단계가 아니다.

**“이 공간을 어떤 원칙으로 운영할 것인가?”**를 결정하는 단계다.

즉, 논리 아키텍처는 다음을 정의한다.

```text
허용하는 것
+
금지하는 것
+
책임지는 주체
+
데이터가 이동하는 표준 경로
```

## 6.2 핵심 논리 정책

### 정책 1. 도메인 분리

업무와 기능의 책임을 분리하고, 각 책임에 맞는 실행 공간을 식별한다.

대표 분리 대상:

- 온라인 AP
- 배치 AP
- ETL 서버
- 이벤트 서버
- IMDG 서버
- CDC 중계 서버
- RDW
- ADW

### 정책 2. 데이터 중심 설계

데이터 입수·전송·가공·저장·분석 흐름을 시스템 설계의 중심에 둔다.

```text
원천 데이터
   ↓
실시간 변화        이벤트          대용량 배치
   │                 │                │
   ▼                 ▼                ▼
  CDC              Kafka          DataStage
   │                 │                │
   ▼                 ▼                ▼
  RDW          실시간 Marketing      ADW
```

### 정책 3. Integration 통제

개발자가 임의로 연결 경로를 만들지 못하도록 표준 인터페이스를 강제한다.

```text
금지
- DB Link 등 P2P 직접 연결
- 비표준 인터페이스
- 책임영역을 우회하는 직접 접근

표준 경로
- 시스템 간 거래: Cruz APIM
- 파일: FOS / MFT
- 실시간 데이터: CDC
- 이벤트: Kafka
- 배치 가공: DataStage
```

### 정책 4. 규격과 표준

통신과 개발의 편차를 줄이고 운영 가능한 구조를 만든다.

대표 기준:

- 온라인 통신 HTTP/JSON 표준화
- 표준 전문
- Framework 통일
- Trace ID/GUID 기반 추적
- 통합 개발 및 운영 기준

### 정책 5. 실시간과 배치의 분리

실시간 서비스와 대용량 처리의 자원 경합을 차단한다.

특히 RDW와 ADW를 분리하여 대량 분석 쿼리가 온라인 거래 성능을 저해하지 않도록 한다.

## 6.3 수행 활동

1. Big Picture의 각 공간을 실제 책임 단위로 세분화한다.
2. 책임별 허용·금지 정책을 작성한다.
3. 데이터 흐름별 표준 경로를 지정한다.
4. 온라인·배치·이벤트·분석의 실행 책임을 분리한다.
5. 직접 연결, 공유 자원, 책임 중첩을 제거한다.
6. 각 논리 정책이 NFR을 어떻게 보장하는지 연결한다.

## 6.4 완료 기준

```text
[ ] 각 도메인의 책임이 문장으로 정의된다.
[ ] 허용/금지 규칙이 명시되어 있다.
[ ] 데이터 이동 경로가 유형별로 정해져 있다.
[ ] 온라인과 배치가 논리적으로 분리되어 있다.
[ ] RDW와 ADW의 역할이 구분되어 있다.
[ ] Integration이 표준 경로로 통제된다.
[ ] 각 정책과 NFR의 연결을 설명할 수 있다.
```

## 6.5 산출물

- Logical Architecture
- Domain Separation Policy
- Data Flow Policy
- Integration Policy
- Online/Batch Separation Policy
- Standard / Specification Policy

---

# 7. STEP 4 — Physical Architecture

## 7.1 목적

Physical 단계는 논리 정책을 실제 서버·DB·네트워크·클라우드·DR 구조에 배치하는 단계다.

핵심 메시지는 다음과 같다.

> **“속도는 전용 자원으로, 유연성은 클라우드로.”**

또한:

> **“데이터는 깊게 확장하고, 서비스는 넓게 확장한다.”**

## 7.2 주요 물리 설계 방향

### 데이터 영역

- RDW와 ADW를 물리적으로 분리
- Oracle Exadata 기반 고성능 데이터 플랫폼
- 데이터 특성에 맞는 병렬 확장
- 온라인 거래와 분석 자원의 충돌 방지

### 서비스 영역

- 농협 프라이빗 클라우드 기반 VM
- Scale-Out 중심 수평 확장
- 마케팅·BI 등 변화가 많은 서비스의 유연한 증설

### 기능별 자원 격리

```text
Online AP
Batch AP
ETL Server
Event Server
IMDG Server
CDC Relay
RDW
ADW
```

각 기능을 독립 자원으로 분리하여 장애와 부하의 확산을 줄인다.

### DR

DB 수준의 이론적 Active-Active보다 운영 복잡도와 데이터 정합성을 고려하여 **AP 수준 Active-Active**를 활용하는 현실적 고가용성 전략을 적용한다.

## 7.3 수행 활동

1. 논리 컴포넌트를 실제 인프라 자원에 배치한다.
2. 전용자원과 클라우드 적용 영역을 구분한다.
3. 성능 민감 자원과 확장 민감 자원을 구분한다.
4. 온라인/배치/ETL/이벤트/CDC 자원을 격리한다.
5. RDW/ADW를 물리적으로 분리한다.
6. HA/DR 구조를 정의한다.
7. 장애 발생 시 영향 범위와 전환 구조를 점검한다.

## 7.4 완료 기준

```text
[ ] 모든 주요 Logical Component의 배치 위치가 결정되었다.
[ ] 자원 분리 이유가 NFR과 연결된다.
[ ] 온라인과 분석의 자원 경합이 구조적으로 차단되었다.
[ ] Scale-Out 대상과 Scale-Up/병렬확장 대상이 구분되었다.
[ ] HA/DR 방식과 운영 절차를 설명할 수 있다.
[ ] 단일 장애가 전체 플랫폼 장애로 확산되지 않는다.
```

## 7.5 산출물

- Physical Architecture
- Server / VM Placement
- DB Placement
- Network / Interface Placement
- HA / DR Architecture
- Resource Isolation Matrix

---

# 8. STEP 5 — Mechanism

## 8.1 목적

Mechanism은 단순 솔루션 목록이 아니다.

논리 정책과 물리 구조가 **실제로 동일한 방식으로 동작하도록 만드는 표준 실행 규칙**이다.

발표 스크립트의 비유로 보면 논리·물리가 집의 구조라면, 메커니즘은 그 집 안의 전기·배관과 같다.

```text
Logical Policy
      +
Physical Structure
      ↓
Mechanism
      ↓
실제 실행 가능한 Architecture
```

## 8.2 5대 실행 메커니즘

| 구분 | 실행 규칙 |
|---|---|
| Protocol | 온라인 통신 HTTP/JSON 표준화 |
| Framework | 온라인 Framework와 UI 개발 기반 통일 |
| Integration | P2P 금지, 표준 연계 경로 사용 |
| Data Transport | CDC와 Kafka 역할 분리 |
| Batch | 대용량 배치는 DataStage 활용 |

### Protocol

```text
Online Transaction
      ↓
HTTP / JSON
      ↓
표준 전문
      ↓
Trace ID / GUID
```

### Framework

Framework를 통일해 프로젝트와 개발자별 구현 편차를 줄이고 운영 표준을 맞춘다.

### Integration

```text
시스템 거래  → Cruz APIM
파일         → FOS / MFT
실시간 데이터 → CDC
이벤트       → Kafka
대용량 배치  → DataStage
```

### Data Transport 이원화

```text
CDC
= 데이터 변화의 실시간 전달

Kafka
= 이벤트 스트리밍 및 실시간 반응
```

트랜잭션 데이터와 이벤트를 동일한 길로 처리하지 않는다.

## 8.3 수행 활동

1. Logical Policy마다 실제 구현 메커니즘을 연결한다.
2. 통신 규격을 확정한다.
3. 프레임워크와 표준 전문을 정의한다.
4. 연계 유형별 표준 솔루션과 경로를 결정한다.
5. 데이터 전송 유형별 CDC/Kafka/ETL 책임을 명확히 한다.
6. 개발자가 우회 구현할 수 없는 표준을 만든다.
7. 운영·로그·추적 방식까지 실행 규칙에 포함한다.

## 8.4 완료 기준

```text
[ ] 모든 주요 Logical Policy에 구현 메커니즘이 연결된다.
[ ] 통신 규격이 하나의 표준으로 정의된다.
[ ] Integration 유형별 표준 경로가 정해져 있다.
[ ] CDC와 Kafka의 책임이 겹치지 않는다.
[ ] 대용량 배치 처리 경로가 정해져 있다.
[ ] Trace ID/GUID 기반 운영 추적이 가능하다.
[ ] 개발자가 임의 구현할 수 있는 우회 경로가 최소화되었다.
```

## 8.5 산출물

- Mechanism Architecture
- Protocol Standard
- Framework Standard
- Interface / Integration Standard
- Data Transport Standard
- Batch Mechanism
- Logging / Trace Standard

---

# 9. STEP 6 — Runtime Validation

## 9.1 목적

Runtime 단계는 그림을 설명하는 단계가 아니다.

**논리 정책 + 물리 구조 + 메커니즘이 융합된 결과가 실제 서비스 흐름에서 처음의 Vision과 NFR을 만족하는지 확인하는 단계**다.

발표 스크립트는 이를 다음과 같이 설명한다.

> 런타임 과정을 시뮬레이션하여 추가 메커니즘이나 논리·물리 보완사항을 확인한다.

즉 Runtime은 최종 확인 단계이면서 동시에 Architecture Feedback 단계다.

## 9.2 대표 Runtime Scenario

### FAST — 실시간 마케팅 흐름

목적: 고객 행동에 즉시 반응

```text
Customer Action
      ↓
Event
      ↓
Kafka
      ↓
Marketing Rule
      ↓
Real-time Offering
```

핵심 특성:

- 이벤트 기반
- DB 비의존 처리
- 경량 처리
- 실시간 반응

### DEEP — 분석 흐름

목적: 깊이 있는 분석과 전략적 의사결정

```text
Source Data Change
      ↓
CDC
      ↓
RDW / Data Processing
      ↓
DataStage
      ↓
ADW
      ↓
BI / Analytics / Management Decision
```

핵심 특성:

- 대용량 데이터 통합
- 정제·축적
- 분석 중심
- 실시간 온라인 거래와 자원 분리

FAST는 **속도를 위한 길**, DEEP은 **깊이를 위한 길**이다.

둘을 분리함으로써 실시간 처리와 대용량 분석이 서로 방해하지 않도록 한다.

## 9.3 수행 활동

1. 대표 핵심 업무 시나리오를 선정한다.
2. 요청/이벤트 발생부터 최종 결과까지 End-to-End 흐름을 추적한다.
3. 각 구간이 Logical Policy를 준수하는지 확인한다.
4. 실제 Physical Resource가 설계대로 분리되는지 확인한다.
5. 정의한 Mechanism이 실제 흐름에 적용되는지 확인한다.
6. NFR/SLA를 만족하는지 검증한다.
7. 실패·장애 시 영향 범위가 격리되는지 확인한다.
8. 부족한 Logical/Physical/Mechanism을 피드백한다.

## 9.4 Runtime 검증 질문

```text
이 서비스는 Vision에서 정의한 가치를 실제로 제공하는가?
성능 SLA를 만족하는가?
온라인과 배치/분석이 서로 자원을 침범하지 않는가?
장애가 다른 도메인으로 확산되지 않는가?
표준 인터페이스를 우회하는 경로가 없는가?
Trace ID로 End-to-End 추적이 가능한가?
운영자가 이상을 측정하고 판단할 수 있는가?
```

## 9.5 완료 기준

```text
[ ] 대표 서비스 시나리오가 End-to-End로 검증되었다.
[ ] NFR/SLA 관점의 결과가 확인되었다.
[ ] Logical Policy 준수 여부가 확인되었다.
[ ] Physical 자원 분리와 장애격리가 확인되었다.
[ ] Mechanism 적용 여부가 확인되었다.
[ ] 운영 추적성이 확보되었다.
[ ] 발견된 보완사항이 Logical/Physical/Mechanism으로 피드백되었다.
```

## 9.6 산출물

- Runtime Scenario
- FAST Validation
- DEEP Validation
- NFR/SLA Validation Result
- Architecture Gap List
- Architecture Feedback Items

---

# 10. 운영·유지관리 — 6단계 이후

발표 스크립트에서는 Runtime 이후 **표준화와 통합 개발 환경을 통해 기술 부채를 줄이고 10년 이상 지속 가능한 구조를 만든다**고 설명한다.

따라서 NSIGHT 아키텍처는 Runtime 검증으로 종료되지 않는다.

```text
Runtime Validation
      ↓
운영
      ↓
관측 / 측정
      ↓
Gap 발견
      ↓
Architecture 보완
      ↓
표준 개정
      ↓
다시 Runtime 검증
```

운영 단계의 핵심은 다음과 같다.

- 표준화 유지
- 통합 개발 환경
- DevOps 기반 개발·배포
- APM·통합로그·SLA 기반 관측
- Trace ID/GUID 기반 거래 추적
- 기술 부채 관리
- 반복적인 Architecture 보완

---

# 11. 아키텍처 수행 Gate

다음 Gate는 발표 스크립트의 6단계 의도를 실제 프로젝트 수행 관점으로 재구성한 단계별 완료 질문이다.

| Gate | 확인 질문 | PASS 기준 |
|---|---|---|
| G1 Vision | 왜 만드는지와 NFR이 명확한가? | 비전·NFR·SLA가 정의됨 |
| G2 Big Picture | 책임과 경계가 명확한가? | End-to-End + Domain Scope 확정 |
| G3 Logical | 허용/금지 정책이 명확한가? | 도메인·데이터·연계·분리 정책 확정 |
| G4 Physical | 정책이 실제 자원에 올바르게 배치되었는가? | 자원격리·HA·DR·확장 구조 확정 |
| G5 Mechanism | 실제 구현 규칙이 표준화되었는가? | Protocol/Framework/Integration/Data Transport 확정 |
| G6 Runtime | 실제 서비스가 Vision/NFR을 만족하는가? | 대표 시나리오 검증 및 Gap 식별 |

Gate의 목적은 문서의 완성도를 보는 것이 아니라 **다음 단계로 넘어갈 만큼 의사결정이 충분히 끝났는지 확인하는 것**이다.

---

# 12. Architecture Traceability

6단계를 별개의 문서 작업으로 수행하면 안 된다.

각 단계의 결정이 다음 단계로 추적되어야 한다.

예시:

| Vision / NFR | Big Picture | Logical Policy | Physical | Mechanism | Runtime |
|---|---|---|---|---|---|
| 이벤트 1초 이내 | Marketing / Event 영역 분리 | 이벤트와 DB 거래 분리 | Event Server 독립 | Kafka | FAST 시나리오 검증 |
| 온라인 성능 보호 | RDW/ADW 경계 | 온라인/분석 분리 | RDW/ADW 물리 분리 | CDC + ETL | 온라인 부하와 DEEP 분석 동시 검증 |
| 확장성 | Service/Data 책임 분리 | 서비스 수평확장 정책 | Private Cloud VM | 표준 Framework | Scale-Out 시나리오 |
| 관측성 | 공통 운영 영역 | 전구간 추적 정책 | APM/로그 인프라 | GUID/Trace ID | End-to-End 거래 추적 |
| 가용성 | 기능별 Zone | 장애격리 정책 | AP Active-Active / DR | 표준 전환 메커니즘 | 장애 시나리오 검증 |

핵심은 다음과 같다.

```text
NFR
 ↓
Architecture Decision
 ↓
Logical Policy
 ↓
Physical Placement
 ↓
Mechanism
 ↓
Runtime Evidence
```

이 연결이 끊기면 “왜 이 구조를 선택했는지” 설명할 수 없고, Runtime에서 문제가 생겼을 때 어느 설계를 수정해야 하는지 판단하기 어렵다.

---

# 13. 실제 프로젝트 수행 순서

```text
01. 비즈니스 변화와 미래 요구 확인
        ↓
02. Vision 선언
        ↓
03. 5대 NFR / SLA 수립
        ↓
04. End-to-End Big Picture 작성
        ↓
05. Domain / Responsibility / Boundary 확정
        ↓
06. Logical Policy 작성
        ↓
07. 허용 / 금지 / 표준 Interface 확정
        ↓
08. Physical Resource Mapping
        ↓
09. 자원 격리 / HA / DR / 확장 구조 설계
        ↓
10. Protocol / Framework / Integration Mechanism 확정
        ↓
11. FAST / DEEP 등 Runtime Scenario 작성
        ↓
12. NFR / SLA 기준 검증
        ↓
13. Gap 도출
        ↓
14. Logical / Physical / Mechanism 보완
        ↓
15. 운영 표준 및 개발환경에 반영
```

---

# 14. 단계별 필수 산출물 세트

```text
00-ARCHITECTURE-METHODOLOGY/
│
├─ 01-VISION/
│   ├─ ARCHITECTURE-VISION.md
│   ├─ NFR.md
│   └─ SLA.md
│
├─ 02-BIG-PICTURE/
│   ├─ BIG-PICTURE.md
│   ├─ DOMAIN-SCOPE.md
│   └─ RESPONSIBILITY-BOUNDARY.md
│
├─ 03-LOGICAL/
│   ├─ LOGICAL-ARCHITECTURE.md
│   ├─ DOMAIN-POLICY.md
│   ├─ DATA-FLOW-POLICY.md
│   ├─ INTEGRATION-POLICY.md
│   └─ ONLINE-BATCH-POLICY.md
│
├─ 04-PHYSICAL/
│   ├─ PHYSICAL-ARCHITECTURE.md
│   ├─ RESOURCE-MAPPING.md
│   ├─ HA-DR.md
│   └─ RESOURCE-ISOLATION.md
│
├─ 05-MECHANISM/
│   ├─ MECHANISM-ARCHITECTURE.md
│   ├─ PROTOCOL-STANDARD.md
│   ├─ FRAMEWORK-STANDARD.md
│   ├─ INTEGRATION-STANDARD.md
│   └─ DATA-TRANSPORT-STANDARD.md
│
└─ 06-RUNTIME/
    ├─ RUNTIME-SCENARIOS.md
    ├─ FAST-VALIDATION.md
    ├─ DEEP-VALIDATION.md
    ├─ NFR-VALIDATION.md
    └─ ARCHITECTURE-GAPS.md
```

위 파일명 체계는 발표 자료의 6단계 산출물을 실제 문서 관리 단위로 재구성한 예시다.

---

# 15. 아키텍처 설계 시 금지해야 할 접근

발표 스크립트의 핵심 메시지를 역으로 정리하면 다음 접근은 피해야 한다.

| 금지 접근 | 이유 |
|---|---|
| 솔루션부터 선정 | Vision과 정책보다 제품이 설계를 지배하게 됨 |
| Big Picture를 단순 구성도로 작성 | 책임과 경계가 사라짐 |
| P2P/DB Link 직접 연결 | 경계 붕괴, 장애 확산, 기술 부채 |
| 온라인과 배치 자원 공유 | 자원 경합 및 성능 영향 |
| RDW/ADW 역할 혼합 | 분석 부하가 온라인 거래에 영향 |
| CDC와 Kafka 역할 혼합 | 데이터 변화와 이벤트의 책임 불명확 |
| 논리/물리 문서만 만들고 종료 | 실제 Runtime에서 동작 여부 확인 불가 |
| 측정되지 않는 구조 | 운영 통제와 사전 이상 감지 불가 |

---

# 16. 핵심 수행 원칙 10가지

1. **아키텍처는 기술 선택이 아니라 생존을 위한 구조 설계다.**
2. **Vision과 NFR이 모든 설계 판단의 출발점이다.**
3. **Big Picture의 핵심은 구성요소가 아니라 책임과 경계다.**
4. **Logical Architecture는 제품이 아니라 허용·금지 정책을 만든다.**
5. **데이터 흐름은 개발자가 임의로 결정하지 않고 표준 경로로 강제한다.**
6. **실시간·배치·분석·이벤트는 역할과 자원을 분리한다.**
7. **Physical Architecture는 성능과 유연성의 특성에 맞춰 자원을 배치한다.**
8. **Mechanism은 논리 정책과 물리 구조를 실제로 움직이는 실행 규칙이다.**
9. **Runtime에서 Vision과 NFR을 만족하지 못하면 앞 단계의 설계를 다시 보완한다.**
10. **운영에서 측정하고 추적할 수 없는 구조는 완성된 아키텍처가 아니다.**

---

# 17. 최종 수행 모델

```text
                         [ WHY ]
                           │
                           ▼
                    ┌──────────────┐
                    │   Vision     │
                    │   + NFR      │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │ Big Picture  │
                    │책임 / 경계   │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │   Logical    │
                    │ 정책 / 통제  │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │  Physical    │
                    │ 자원 / 배치  │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │  Mechanism   │
                    │ 실행 / 표준  │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │   Runtime    │
                    │ 검증 / 측정  │
                    └──────┬───────┘
                           │
                  NFR 만족?│
                  ┌────────┴────────┐
                  │                 │
                YES                NO
                  │                 │
                  ▼                 ▼
            운영 Baseline     Gap / 보완
                  │                 │
                  ▼                 └──────→ Logical / Physical /
            표준화·운영                  Mechanism 재설계
```

---

# 18. 결론

NSIGHT의 아키텍처 수행 방법론은 단순한 `Vision → 설계 → 구축` 절차가 아니다.

핵심은 다음의 연결 구조다.

```text
목적을 정한다.
      ↓
전체 책임과 경계를 나눈다.
      ↓
허용과 금지 정책을 만든다.
      ↓
정책을 실제 자원에 배치한다.
      ↓
표준 실행 메커니즘을 만든다.
      ↓
실제 Runtime에서 검증한다.
      ↓
부족한 설계를 다시 보완한다.
```

즉 NSIGHT 아키텍처의 완료 조건은 **“좋아 보이는 설계도가 만들어졌는가”가 아니라 “처음 정의한 Vision과 NFR이 실제 Runtime에서 구현되고 운영 가능한가”**이다.

최종적으로 이 방법론이 지향하는 것은 서버와 솔루션의 집합이 아니다.

**데이터가 흐르고, 고객 행동에 즉시 반응하며, 현업의 판단을 지원하고, 장애에 강하고, 지속적으로 확장할 수 있는 Data-Centric 플랫폼을 만드는 것이다.**

---

## 부록 A. 한 장 요약

| 단계 | 한 문장 |
|---|---|
| Vision | 우리가 왜 이 시스템을 만드는지 정한다. |
| Big Picture | 공간을 나누고 책임과 경계를 정한다. |
| Logical | 무엇을 허용하고 금지할지 정한다. |
| Physical | 정책을 실제 자원과 인프라에 배치한다. |
| Mechanism | 시스템이 동일한 방식으로 움직이게 하는 실행 규칙을 만든다. |
| Runtime | 실제 서비스 흐름에서 Vision과 NFR을 만족하는지 검증한다. |

### 기억해야 할 핵심 문장

> **아키텍처는 생존을 위한 설계다.**  
> **Big Picture의 본질은 책임과 경계다.**  
> **논리 설계는 기술이 아니라 정책이다.**  
> **메커니즘은 논리와 물리를 움직이는 실행 규칙이다.**  
> **Runtime은 설계가 실제 서비스로 살아 움직이는지 검증하는 단계다.**
