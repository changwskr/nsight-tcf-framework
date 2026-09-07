# NSIGHT / PDMG 아키텍처 정의서 — 01. VISION

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 ID: NSIGHT-ARCH-TYPE2-VISION-01  
> Architecture Level: **VISION**  
> PPT 공식 범위: **1. 아키텍처 정의 / 1.1 개요**  
> PPT Slide Range: 1장 구간 중 1.1 관련 장표  
> 문서 상태: Draft / Evidence-First / PPT-Aligned  
> 작성일: 2026-08-31  
> 후속: `02_BIG_PICTURE`

---

# 0. Evidence Register

| ID | 근거 | 사용 목적 | 상태 |
|---|---|---|---|
| EV-VSN-01 | TYPE2/PPT 정합 프롬프트 | 1.1의 공식 범위와 핵심 방향 | `[PPT-TOC]` / `[PPT-BODY]` |
| EV-VSN-02 | 기존 `아키텍처 정의 — 정의서 (1장)` | 목적·3축·적용 범위 | `[WORKING BASELINE]` |
| EV-VSN-03 | 기존 `NSIGHT_PDMG_아키텍처_정의서_I_비전_전략` | 전략/NFR/FAST-DEEP 보강 | `[WORKING BASELINE]` |
| EV-VSN-04 | PDMG Source/Config/Runtime | VISION의 AS-IS 검증 Reference | `[AS-IS REFERENCE]` |

---

# 1. 핵심 결론

NSIGHT VISION의 출발점은 특정 Framework나 서버 제품이 아니라 **차세대 정보계가 제공해야 할 업무·데이터·운영 가치**다.

[PPT-BODY] 기준 핵심 방향은 다음과 같다.

```text
고객 중심 서비스 강화
      +
데이터 기반 의사결정 강화
      +
통합 정보 활용 기반
      +
실시간 정보 활용
      +
운영 효율 / 민첩성
```

이를 Architecture 언어로 재정리하면:

```text
[Business / Data Need]
        ↓
[개편 기본 방향]
        ↓
[Architecture Vision]
Scalable · Resilient · Data-Centric
        ↓
[Architecture Principle]
책임 분리 · 표준 연결 · 자원 분리 · 추적 가능
        ↓
[NFR]
Performance · Availability · Scalability · Security · Observability
        ↓
[BIG PICTURE]
Application / System / Data 책임 공간 정의
```

PDMG는 이 VISION 자체가 아니라 하위 Application/Framework의 **AS-IS Reference**다.

---

# 2. 목적 / 범위 / 전제

## 2.1 목적

본 장은 다음 질문에 답한다.

1. 차세대 정보계를 왜 개편하는가?
2. 개편의 기본 방향은 무엇인가?
3. 구축방향과 Architecture 목표는 무엇인가?
4. 어떤 NFR이 후속 설계의 Guardrail인가?
5. NSIGHT Target과 PDMG Reference는 어떤 관계인가?

## 2.2 포함

```text
아키텍처 정의 목적
개편 기본 방향
구축 방향 및 목표
Architecture Vision
Architecture Principle
NFR / SLA 상위기준
적용 범위 / 이해관계자
PDMG Reference Position
```

## 2.3 제외

```text
Application 분류        → BIG PICTURE
System Group            → BIG PICTURE
Zone / Logical Node     → LOGICAL
Host / DB / SW          → PHYSICAL
Interface / GUID / FW   → MECHANISM
실제 Runtime Sequence   → RUNTIME
```

---

# 3. PPT 공식 구조

```text
1. 아키텍처 정의
   └─ 1.1 개요
       ├─ 아키텍처 정의 목적
       ├─ 차세대 정보계 개편 기본 방향
       └─ 차세대 정보계 구축 방향 및 목표
```

[FACT] 기존 1장 정의서에서는 Architecture를 다음 3개 축으로 본다.

```text
Technical Architecture
Application Architecture
Data Architecture
```

VISION에서는 이 세 축의 상세 기술을 정의하지 않고 **왜 이 세 축을 함께 관리해야 하는지**를 정의한다.

---

# 4. Architecture Vision Journey

```text
[현행 정보계의 구조적 한계]
배치 중심
분산된 정보
분석과 운영의 자원 경합
실시간 반응 한계
비표준/개별 연계
        │
        ▼
[개편 기본 방향]
고객 중심
데이터 중심
통합 활용
실시간 활용
운영 민첩성
        │
        ▼
[NSIGHT Architecture Vision]
Scalable
Resilient
Data-Centric
        │
        ▼
[Architecture Goal]
Data → Insight → Action
실시간 반응 + 전략 분석의 공존
        │
        ▼
[BIG PICTURE]
5대 Application/System/Data Responsibility 정의
```

`[WORKING BASELINE]` 기존 전략 정의서는 이를 **Batch 제거**가 아니라 **Batch 중심 구조에서 Real-time 경로를 병행하는 전환**으로 해석한다.

```text
AS-IS
Batch → DW → Next Day

TO-BE
Real-time Event / CDC
        +
Strategic Batch / ETL
```

---

# 5. 개편 기본 방향

## 5.1 고객 중심 서비스 강화

목표:

```text
고객 행동/상태
   ↓
빠른 정보 인지
   ↓
적절한 Offer / 상담 / 지원
```

Architecture Implication:

```text
Event / FAST Runtime
Marketing Platform
Customer Context
Low-latency Integration
Observability
```

## 5.2 데이터 기반 의사결정 강화

목표:

```text
신뢰 가능한 데이터
    ↓
통합/분석
    ↓
BI / 분석 / 의사결정
```

Architecture Implication:

```text
RDW / ADW 역할 분리
Data Subject Area
Data Governance
ETL / CDC 역할 분리
```

## 5.3 통합 정보 활용 기반

목표:

```text
분산된 정보
    ↓
표준 분류 / 표준 연계
    ↓
공통 활용
```

Architecture Implication:

```text
Application Classification
System Group
Interface Standard
Data Ownership
Traceability
```

## 5.4 유연·안정 운영체계

목표:

```text
업무/데이터/플랫폼 책임 분리
    ↓
장애 영향 격리
    ↓
독립 확장 / 운영
```

Architecture Implication:

```text
Zone / Resource Separation
Scale-out
HA / DR
Standard Deployment
Monitoring / Runtime Evidence
```

---

# 6. 구축방향 및 목표

본 정의서에서는 구축 목표를 다음 네 축으로 관리한다.

| 목표 축 | 의미 | 후속 정의서 |
|---|---|---|
| Customer / Service | 고객 중심 서비스·실시간 반응 | BIG PICTURE / RUNTIME |
| Data | 데이터 통합·분석·거버넌스 | BIG PICTURE / PHYSICAL |
| Architecture | 책임·경계·표준·독립 확장 | LOGICAL / MECHANISM |
| Operations | 안정성·관측성·복구·변경관리 | PHYSICAL / RUNTIME |

전체 목표:

```text
Business Value
  +
Data Trust
  +
Architecture Standard
  +
Operational Evidence
```

---

# 7. Architecture Principle

VISION에서 후속 장으로 전달할 원칙은 다음과 같다.

| ID | 원칙 | 의미 |
|---|---|---|
| AP-01 | Responsibility First | 제품보다 업무/시스템/데이터 책임을 먼저 정의 |
| AP-02 | Boundary Controlled | 경계 통과는 표준 Interface로 통제 |
| AP-03 | Data Role Separation | RDW/ADW, CDC/ETL 책임 혼재 금지 |
| AP-04 | Runtime Isolation | Online/Event/Batch/Data Processing 자원 경합 최소화 |
| AP-05 | Standard Mechanism | 전문/GUID/Framework/Interface 표준 적용 |
| AP-06 | Evidence First | Source/Config/Runtime Evidence로 검증 |
| AP-07 | Traceable Change | Requirement→Architecture→Source→Runtime 추적 |
| AP-08 | Exception Governed | 표준 외는 GAP/ADR/승인 |

---

# 8. NFR / 성공 기준

기본 NFR 5축:

```text
Performance
Availability
Scalability
Security
Observability
```

## 8.1 Performance

목표:

```text
FAST Runtime과 DEEP Runtime을 구분
온라인 지연과 대량 처리 자원 경합 방지
```

기존 자료의 세부 SLA 값은 문서 시점별 Baseline로 관리하고 충돌 시 `[CONFLICT]`로 남긴다.

## 8.2 Availability

```text
AP HA
Fault Isolation
DR
Data Integrity
```

## 8.3 Scalability

```text
서비스 Scale-out
Data Platform 병렬/용량 확장
독립적 Resource Scaling
```

## 8.4 Security

```text
SSO
Authentication / Authorization
Encryption
Masking
Network / Trust Boundary
```

## 8.5 Observability

```text
GUID / Trace
ServiceId
APM
Integrated Log
Metric
Alert
Runtime Evidence
```

NFR은 다음 방식으로 후속 장에서 검증한다.

```text
NFR
 → Decision
 → Architecture
 → Metric
 → Owner
 → Validation
```

---

# 9. 적용 범위 / 이해관계자

| 이해관계자 | VISION 사용 목적 |
|---|---|
| Architecture | 설계 방향/Guardrail |
| 업무 | 서비스·데이터 변화 방향 |
| 개발 | 하위 구조가 따라야 할 원칙 |
| 데이터 | Subject Area / RDW/ADW 방향 |
| 인프라 | Logical/Physical 분리 방향 |
| 운영 | NFR/Observability/DR 기준 |
| PMO | Scope/Gap/Decision 추적 |

---

# 10. NSIGHT Target ↔ PDMG Reference

```text
NSIGHT
= Target Architecture / Baseline
        │
        │ Alignment / Verification
        ▼
PDMG
= Application / Framework AS-IS Reference
```

PDMG를 통해 검증할 수 있는 대표 영역:

```text
Application Module
Framework Responsibility
ServiceId
Online Runtime
Transaction / Timeout
Message / Context / Error / Log
JWT / SSO
Deployment / Observability 일부
```

PDMG로 직접 증명하지 않는 영역:

```text
NSIGHT 전체 5대 도메인
RDW/ADW 전체 Data Platform
전사 Interface 전체
Enterprise DR 전체
모든 BI / Governance Solution
```

---

# 11. GAP / OPEN

| 항목 | 상태 | 조치 |
|---|---|---|
| PPT 1.1과 기존 Vision 전략 문서의 세부 용어 | `[OPEN]` | 최종 Baseline 용어 통일 |
| NFR 수치 자료 간 불일치 가능 | `[CONFLICT]` | SLA Register에서 시점별 관리 |
| 일부 Vision 문구의 최종 승인상태 | `[OPEN]` | Architecture Review |
| PDMG 적용범위 | `[AS-IS REFERENCE]` | 전사 Target 자동 승격 금지 |

---

# 12. Verification Checklist

```text
[ ] Vision이 기술제품부터 시작하지 않는가
[ ] 개편 기본 방향이 업무/데이터 가치와 연결되는가
[ ] Architecture Principle이 후속 장에 전달되는가
[ ] NFR 5축이 명시되었는가
[ ] PDMG가 Reference로만 배치되었는가
[ ] AS-IS/TO-BE가 섞이지 않았는가
[ ] 수치 충돌을 임의로 해소하지 않았는가
```

---

# 13. BIG PICTURE Handoff

VISION의 Output:

```text
개편 방향
Architecture Principle
NFR
Scope
PDMG Reference Position
```

BIG PICTURE의 Input:

```text
5대 Domain
Application Classification
System Group
Data Subject Area
Whole System Architecture
Target Server Candidate
```
