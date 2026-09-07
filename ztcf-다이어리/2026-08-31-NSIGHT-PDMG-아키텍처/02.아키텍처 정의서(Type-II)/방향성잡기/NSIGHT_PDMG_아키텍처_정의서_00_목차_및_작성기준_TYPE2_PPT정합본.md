# NSIGHT / PDMG 아키텍처 정의서 — 00. 통합 목차 및 작성 기준 (TYPE2 / PPT 정합본)

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 ID: NSIGHT-ARCH-TYPE2-TOC-00  
> 문서 상태: Architecture Definition Baseline Draft  
> 작성일: 2026-08-31  
> 작성 기준: PPT-First / Evidence-First / Top-down / Text Architecture / Inventory / PDMG Reference

---

# 1. 문서 목적

본 정의서는 NSIGHT 아키텍처를 다음 여섯 단계로 설명한다.

```text
VISION
  ↓
BIG PICTURE
  ↓
LOGICAL
  ↓
PHYSICAL
  ↓
MECHANISM
  ↓
RUNTIME
  ↓
Evidence / GAP / ADR / Baseline
```

PPT의 1~11장 번호는 공식 산출물 추적을 위해 유지한다.

```text
PPT 구조 = Deliverable Trace
6단계 Route = Architecture Meaning / 작성 순서
```

---

# 2. 전체 정의서 세트

| 정의서 | Architecture Level | PPT 대응 | 핵심 질문 |
|---|---|---|---|
| 00 | 공통 | 전체 | 어떻게 작성·검증할 것인가 |
| 01 | VISION | 1.1 | 왜 개편하며 무엇을 목표로 하는가 |
| 02 | BIG PICTURE | 1.2~1.4 | 무엇을 만들고 어떻게 분류·배치하는가 |
| 03 | LOGICAL | 2장 + 6장 Cross Ref | 논리적으로 어디에 어떤 역할을 둘 것인가 |
| 04 | PHYSICAL | 3·4·5장 | 어느 물리 자원에 어떻게 구현할 것인가 |
| 05 | MECHANISM | 6·8·9·10장 | 어떤 표준 원리·프레임워크로 동작하는가 |
| 06 | RUNTIME | 7·11장 | 실제 시간순으로 어떻게 실행·운영되는가 |
| 07 | APPENDIX | 전 장 | Evidence/GAP/ADR/Traceability는 어떻게 닫는가 |

---

# 3. PPT 공식 목차 Trace

## 3.1 아키텍처 정의

```text
1. 아키텍처 정의
   1.1 개요                         → 01 VISION
   1.2 어플리케이션 분류 체계      → 02 BIG PICTURE
   1.3 데이터 주제영역 정의        → 02 BIG PICTURE
   1.4 시스템 아키텍처 구성        → 02 BIG PICTURE
```

## 3.2 논리

```text
2. 논리 기술 아키텍처             → 03 LOGICAL
   2.1 전사 IT Zone 기반 구성 기준
   2.2 시스템 노드 정의 및 식별
   2.3 기술 컴포넌트 정의
   2.4 논리 기술 아키텍처 정의
```

`2.2 시스템 노드 정의 및 식별`과 본문의 `시스템 영역 및 구성요소 정의` 차이는 `[NUMBERING-DRIFT]`로 관리한다.

## 3.3 물리

```text
3. 물리 인프라 아키텍처           → 04 PHYSICAL
4. 데이터베이스 아키텍처          → 04 PHYSICAL
5. 시스템 표준 정의               → 04 PHYSICAL
```

## 3.4 메커니즘

```text
6. 인터페이스 아키텍처            → 05 MECHANISM
8. 아키텍처 표준화                → 05 MECHANISM
9. 아키텍처 구성 요소             → 05 MECHANISM
10. 업무 솔루션 아키텍처          → 05 MECHANISM
```

8.5~8.9는 `XX`를 유지하고 공식 확정 전 임의 명명하지 않는다.

## 3.5 런타임

```text
7. 런타임 아키텍처                → 06 RUNTIME
11. 기타                          → 06 RUNTIME
```

---

# 4. 실제 PPT Content 상태

| PPT 구간 | 상태 |
|---|---|
| 1장 | `[PPT-BODY]` 상세 존재 |
| 2장 | `[PPT-BODY]` 상세 존재, 일부 Numbering Drift |
| 3장 | `[PPT-BODY]` 상세 존재 |
| 4장 | `[PPT-BODY]` 상세 존재 |
| 5장 | `[PPT-BODY]` 상세 존재 |
| 6장 | `[PPT-BODY]` 상세 존재 |
| 7장 | 7.1 중심, 7.2~7.4 `[PPT-CONTENT-GAP]` 가능 |
| 8장 | 상세 존재, 반복 블록 `[DUPLICATE-BLOCK]` |
| 9장 | `[PPT-BODY]` 상세 존재 |
| 10장 | `[PPT-CONTENT-GAP]` |
| 11장 | `[PPT-CONTENT-GAP]` |

---

# 5. 상태 태그

| 태그 | 의미 |
|---|---|
| `[PPT-TOC]` | 공식 목차 |
| `[PPT-BODY]` | PPT 본문 |
| `[PPT-CONTENT-GAP]` | PPT 상세 부족 |
| `[NUMBERING-DRIFT]` | 목차/본문 불일치 |
| `[DUPLICATE-BLOCK]` | 반복 장표 |
| `[FACT]` | 확인된 사실 |
| `[AS-IS]` | 현행 |
| `[TO-BE]` | 목표 |
| `[DECISION]` | 승인 결정 |
| `[GAP]` | 차이 |
| `[RISK]` | 위험 |
| `[OPEN]` | 결정 필요 |
| `[UNKNOWN]` | 확인 불가 |

---

# 6. 공통 문서 구조

모든 정의서는 다음 형식을 따른다.

```text
0. Evidence Register
1. 핵심 결론
2. 목적 / 범위 / 전제
3. PPT 원본 구조 / 장표 Trace
4. Main Text Architecture
5. 영역별 상세 정의
6. Inventory / Mapping
7. 책임 / 원칙 / 허용 / 금지
8. Security / NFR / HA / Observability 연결
9. AS-IS / TO-BE / PDMG Reference
10. GAP / RISK / OPEN / ADR
11. Verification / Checklist
12. Next Level Handoff
```

---

# 7. Inventory SSOT

정의서와 별개로 다음 Inventory가 누적 관리되어야 한다.

```text
Application Inventory
Application ↔ System Mapping
Data Subject Area Inventory
Logical Node Inventory
Technology Component Inventory
Physical Server Inventory
HW/SW Inventory
DB Inventory
Interface Inventory
Runtime Type Inventory
ServiceId Traceability
GAP / ADR Register
```

---

# 8. Architecture Level별 핵심 질문

## 8.1 VISION

```text
왜 바꾸는가?
무엇을 목표로 하는가?
어떤 원칙과 NFR을 지켜야 하는가?
```

## 8.2 BIG PICTURE

```text
무엇을 만드는가?
Application/System/Data를 어떻게 분류하는가?
전체 책임 공간과 경계는 무엇인가?
```

## 8.3 LOGICAL

```text
어느 Zone에 어떤 System/Node/Component를 둘 것인가?
어떤 논리 연결을 허용/금지하는가?
```

## 8.4 PHYSICAL

```text
어느 센터/Host/DB/SW에 구현할 것인가?
용량·HA·DR·표준을 어떻게 매핑하는가?
```

## 8.5 MECHANISM

```text
Interface/전문/GUID/Framework/Batch/SSO가 어떤 규칙으로 동작하는가?
```

## 8.6 RUNTIME

```text
거래·이벤트·CDC·ETL·파일·배치가 시간순으로 어떻게 실행되는가?
실패·Timeout·복구·운영은 어떻게 증명되는가?
```

---

# 9. Cross-cutting 규칙

다음 주제는 여러 Level을 횡단한다.

```text
Interface
  Logical   = 허용 경계/경로
  Mechanism = API/Event/CDC/ETL/File 계약
  Runtime   = 실제 요청/응답/Retry/Error Sequence

HA/DR
  Physical = 센터/서버/복제 구조
  Runtime  = 탐지/전환/복구/Drill

Security
  Big Picture = Trust Boundary
  Mechanism   = 인증/인가/암호화
  Runtime     = 인증 실패/Key/JWT Evidence

Observability
  Mechanism = GUID/Logging Contract
  Runtime   = Metric/Trace/Alert/Evidence
```

---

# 10. 작성 및 승인 Gate

```text
G00 Source / PPT Baseline
 ↓
G10 Document Structure
 ↓
G20 Inventory / Mapping
 ↓
G30 Architecture Rule
 ↓
G40 AS-IS / TO-BE GAP
 ↓
G50 Runtime Evidence
 ↓
G60 Drift
 ↓
G70 GAP / ADR
 ↓
HG90 Baseline Release
```

---

# 11. 이번 작성 순서

```text
[완료 대상]
00 통합 목차 및 작성기준
01 VISION
02 BIG PICTURE

[다음]
03 LOGICAL
04 PHYSICAL
05 MECHANISM
06 RUNTIME
07 APPENDIX / Closed Loop
```
