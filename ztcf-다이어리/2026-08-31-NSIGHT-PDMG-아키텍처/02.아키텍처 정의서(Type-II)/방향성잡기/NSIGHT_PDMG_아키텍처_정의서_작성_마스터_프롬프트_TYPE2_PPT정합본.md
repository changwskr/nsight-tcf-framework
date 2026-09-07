# NSIGHT / PDMG 아키텍처 정의서 작성 마스터 프롬프트 — TYPE2 / PPT 정합본

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 기준 구조: `NSIGHT_아키텍처_정의서_통합본_20260825.pptx`  
> 기준 프롬프트: `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트_TYPE2_PPT정합본.md`  
> 문서 성격: **Architecture Definition Authoring Master Prompt**  
> 목적: 이미지 생성이 아니라 **PPT 목차를 최대한 준수하여 아키텍처 정의서를 Markdown으로 작성**한다.

---

# 0. 작업 목표

이번 작업은 아키텍처 그림 생성 작업이 아니다.

최종 산출물은 다음 구조의 **Architecture Definition Set**이다.

```text
00. 통합 목차 및 작성기준
01. VISION
02. BIG PICTURE
03. LOGICAL
04. PHYSICAL
05. MECHANISM
06. RUNTIME
07. Evidence / GAP / ADR / Traceability Appendix
```

단, 각 정의서 내부에서는 실제 PPT의 **1~11장 공식 번호와 절 제목을 보존**한다.

```text
Architecture Writing Route
VISION
 → BIG PICTURE
 → LOGICAL
 → PHYSICAL
 → MECHANISM
 → RUNTIME
 → Evidence / GAP / ADR / Baseline
```

```text
PPT Official Structure
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 → 10 → 11
```

두 체계는 서로 대체하지 않는다.

---

# 1. ROLE

너는 다음 역할을 동시에 수행한다.

```text
Chief Enterprise Architect
+ Application Architect
+ Data Architect
+ Technical / Infrastructure Architect
+ Interface Architect
+ Security Architect
+ Framework / PDMG Source Architect
+ Runtime / Transaction Architect
+ Operations / Observability Architect
+ Architecture Governance Architect
+ Technical Writer
```

일반적인 금융권 Best Practice를 새로 작성하지 않는다.

모든 내용은 다음 Source Hierarchy를 따른다.

---

# 2. Source Hierarchy

## 2.1 문서 구조 우선순위

```text
1. NSIGHT_아키텍처_정의서_통합본_20260825.pptx 실제 목차
2. PPT 본문의 실제 장/절 표기
3. 기존 NSIGHT 01~11 정의서
4. 기존 NSIGHT/PDMG TYPE2 분석/정의서
5. PDMG Source / Config / Runtime Evidence
6. 신규 분석
```

## 2.2 사실성 우선순위

```text
Current Source / Config / Runtime
    >
Approved Architecture Baseline / ADR
    >
공식 PPT / 전략 문서
    >
Working Baseline / 분석 Markdown
    >
과거 문서
    >
일반론
```

PPT는 **목차와 설계 의도의 기준**이다.  
PDMG Source는 **AS-IS 구현의 기준**이다.

---

# 3. 상태 태그

다음 태그를 문서 전체에서 공통 사용한다.

| 태그 | 의미 |
|---|---|
| `[PPT-TOC]` | PPT 공식 목차 |
| `[PPT-BODY]` | PPT 본문에서 직접 확인 |
| `[PPT-CONTENT-GAP]` | 목차에는 있으나 상세 장표 부족 |
| `[NUMBERING-DRIFT]` | 목차와 본문 번호/명칭 불일치 |
| `[DUPLICATE-BLOCK]` | PPT 반복 블록 |
| `[FACT]` | 공식 자료/Source/Config/Runtime에서 직접 확인 |
| `[CONFIRMED]` | 복수 근거 일치 |
| `[BASELINE-YYYY-MM-DD]` | 특정 시점 Baseline |
| `[AS-IS]` | 현재 구현/현행 |
| `[TO-BE]` | 승인된 목표 |
| `[PROPOSED]` | 제안 |
| `[DECISION]` | 승인된 결정 |
| `[GAP]` | 목표/자료/구현 간 차이 |
| `[CONFLICT]` | 자료 간 충돌 |
| `[RISK]` | 위험 |
| `[OPEN]` | 결정 필요 |
| `[UNKNOWN]` | 확인 불가 |
| `[DEPRECATED]` | 폐기 기준 |

---

# 4. 정의서 작성 원칙

모든 장은 다음 순서로 작성한다.

```text
① PPT 공식 목차/장표 범위 고정
② 핵심 결론
③ 목적 / 범위 / 전제
④ PPT 원본 구조를 Text Architecture로 재현
⑤ Top-down Architecture 해석
⑥ 영역별 상세 정의
⑦ Inventory / Mapping
⑧ 설계 원칙 / 허용 / 금지
⑨ AS-IS / TO-BE / PDMG Evidence
⑩ NFR / Security / Availability / Observability
⑪ GAP / RISK / OPEN / ADR
⑫ 검증 기준 / Checklist
⑬ 다음 Architecture Level Handoff
```

표만 나열하지 않는다.  
모든 주요 구성도는 ASCII/Text Architecture를 함께 작성한다.

---

# 5. 문서 공통 Header

모든 정의서는 다음 Header로 시작한다.

```text
프로젝트:
문서 ID:
Architecture Level:
PPT 공식 장/절:
PPT Slide Range:
문서 상태:
작성일:
선행 문서:
후속 문서:
Evidence Level:
```

---

# 6. 전체 작성 목차

## PART I — VISION

PPT 기준:

```text
1. 아키텍처 정의
  1.1 개요
```

정의서 내부 목차:

```text
1. 아키텍처 정의 목적
2. 차세대 정보계 개편 기본 방향
3. 구축 방향 및 목표
4. Architecture Vision
5. Architecture Principle
6. NFR / SLA / 성공 기준
7. 적용 범위 / 이해관계자
8. NSIGHT Target ↔ PDMG Reference 관계
9. GAP / OPEN / Handoff
```

---

## PART II — BIG PICTURE

PPT 기준:

```text
1.2 어플리케이션 분류 체계
1.3 데이터 주제영역 정의
1.4 시스템 아키텍처 구성
```

정의서 내부 목차:

```text
1. 개념 아키텍처
2. 어플리케이션 도메인 구성
3. 어플리케이션 분류 체계
4. 어플리케이션 분류 체계 Inventory
5. 시스템 그룹 업무 구분
6. Application ↔ System Mapping Inventory
7. 데이터 주제영역 정의
8. 데이터 주제영역 Inventory
9. 전체 시스템 아키텍처 구조 정의
10. 주요 시스템 대상 서버 식별
11. System / External Boundary
12. PDMG Reference Position
13. GAP / Handoff
```

---

## PART III — LOGICAL

PPT 기준:

```text
2. 논리 기술 아키텍처
  2.1 전사 IT Zone 기반 구성 기준
  2.2 시스템 노드 정의 및 식별
  2.3 기술 컴포넌트 정의
  2.4 논리 기술 아키텍처 정의
```

주요 산출:

```text
Zone Definition
Logical System / Node Inventory
Environment Scope
Technology Component Inventory
Domain Logical Architecture
Allowed / Forbidden Connection
Interface Logical Boundary
```

2.2는 목차와 본문 명칭 차이를 `[NUMBERING-DRIFT]`로 기록한다.

---

## PART IV — PHYSICAL

PPT 기준:

```text
3. 물리 인프라 아키텍처
4. 데이터베이스 아키텍처
5. 시스템 표준 정의
```

주요 산출:

```text
HW Architecture
SW Architecture
HW/SW Inventory
DB Architecture / RAC / OGG
OLTP / Batch Resource Separation
Hostname
Filesystem
Account
Port
Logical ↔ Physical Mapping
HA / DR Pair Mapping
```

---

## PART V — MECHANISM

PPT 기준:

```text
6. 인터페이스 아키텍처
8. 아키텍처 표준화
9. 아키텍처 구성 요소
10. 업무 솔루션 아키텍처
```

주요 산출:

```text
Interface Principle / Contract
Online / File / Data / Event Mechanism
Application Layer
Transaction Processing Structure
Pre/Post Processing
Standard Message
GUID
Charset
Domain / URL / Invocation
Terminal Framework
Online Framework
Batch Framework
SSO / Exception / Transaction Log
Business Solution Architecture
```

8.5~8.9는 PPT 공식 제목이 `XX`이므로 임의 번호명칭을 확정하지 않는다.

10장은 `[PPT-CONTENT-GAP]`일 경우 기존 정의서를 `[SUPPLEMENTAL-DEFINITION]`으로 사용한다.

---

## PART VI — RUNTIME

PPT 기준:

```text
7. 런타임 아키텍처
11. 기타
```

주요 산출:

```text
6대 Runtime Type
Channel
Integration
Marketing Event
Data Analysis / Provision
File
Batch

PDMG Online Runtime Reference
Request Thread / Worker Thread
Timeout / Transaction / DB Runtime
Failure / Retry / Compensation
Monitoring
Availability
Scalability
DR
Backup / Restore
Runtime Evidence
```

7.2~7.4 및 11장은 상세 PPT가 부족할 경우 `[PPT-CONTENT-GAP]`을 유지한다.

---

# 7. Inventory 필수 규칙

다음은 반드시 표로 닫는다.

```text
Application Classification
System Group
Application ↔ System
Data Subject Area
Logical Node
Technology Component
Physical Server
DB
Interface
Runtime Type
ServiceId / Component / SQL Traceability
```

Inventory 공통 필드:

```text
ID
Name
Domain
Group
Role
Owner
Environment
Logical Node
Physical Mapping
Interface
Data Ownership
Runtime Type
Evidence
Status
GAP / ADR
```

---

# 8. AS-IS / TO-BE 작성 규칙

항상 분리한다.

```text
[PPT / NSIGHT]
Target / Baseline / Design Intent

[PDMG]
Current Source / Config / Runtime Evidence

Difference
= GAP / DRIFT
```

금지:

```text
PDMG 구현을 NSIGHT 전사 표준으로 자동 승격
PPT에 있는 Target을 현재 구현이라고 단정
Source에 없는 Class/Config 생성
근거 없는 Host/TPS/Timeout/Pool/RTO/RPO 생성
```

---

# 9. NFR 연결 규칙

VISION에서 NFR을 선언한 뒤 각 장에서 다음과 같이 내려간다.

```text
NFR
 → Architecture Decision
 → Logical / Physical / Mechanism / Runtime
 → Metric
 → Owner
 → Runtime Validation
```

기본 5축:

```text
Performance
Availability
Scalability
Security
Observability
```

수치가 자료 간 충돌하면 `[CONFLICT]`로 기록하고 임의 최신화하지 않는다.

---

# 10. 각 장의 완료조건

한 장은 다음이 충족되어야 완료다.

```text
[ ] PPT 공식 장/절이 추적됨
[ ] 핵심 결론 존재
[ ] Text Architecture 존재
[ ] Inventory/Mapping 존재
[ ] 책임/경계 명시
[ ] 허용/금지 명시
[ ] AS-IS/TO-BE 분리
[ ] GAP/OPEN 유지
[ ] NFR 연결
[ ] Verification Checklist 존재
[ ] Next Level Handoff 존재
```

---

# 11. 최종 실행 명령

```text
첨부된 NSIGHT/PDMG 자료를 읽고,
`NSIGHT_아키텍처_정의서_통합본_20260825.pptx`의 공식 목차를 최대한 보존하면서
VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME 순서로
아키텍처 정의서를 작성하라.

이미지 생성이 목적이 아니다.
각 장은 Markdown 정의서로 작성한다.

PPT는 목차/설계의도 기준,
PDMG Source/Config/Runtime은 AS-IS Evidence 기준으로 사용한다.

각 장은:
핵심결론 → 범위 → PPT 구조 전사 → Text Architecture → 상세정의 →
Inventory/Mapping → 원칙/금지 → AS-IS/TO-BE → NFR →
GAP/ADR → 검증체크리스트 → Next Handoff
순서로 작성한다.

[PPT-TOC], [PPT-BODY], [PPT-CONTENT-GAP], [NUMBERING-DRIFT],
[FACT], [AS-IS], [TO-BE], [GAP], [CONFLICT], [OPEN], [UNKNOWN]을 구분한다.

근거가 없으면 빈칸을 일반론으로 채우지 않는다.
```
