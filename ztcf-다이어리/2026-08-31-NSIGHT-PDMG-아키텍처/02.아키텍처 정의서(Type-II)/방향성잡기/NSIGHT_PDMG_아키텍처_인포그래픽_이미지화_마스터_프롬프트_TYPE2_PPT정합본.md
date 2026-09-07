# NSIGHT / PDMG 아키텍처 인포그래픽 이미지화 마스터 프롬프트 — TYPE2 / PPT 정합본

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 기준 PPT: `NSIGHT_아키텍처_정의서_통합본_20260825.pptx`  
> 기준 시점: 2026-08-25 통합본 / 총 163 Slides  
> 목적: **실제 작성된 NSIGHT 아키텍처 정의서 PPT의 목차·장표 명칭·전개 순서를 최대한 보존**하면서, 전체 Architecture를 **VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME**의 6개 상위 관점으로 다시 읽고 장별 인포그래픽으로 시각화한다.  
> 문서 성격: PPT-First Architecture Visualization Master Prompt / TYPE2  
> 산출 형식: PPT 원목차 기반 장별 세로형 인포그래픽 + Inventory/Mapping/Traceability + GAP/ADR 카드

---

# 0. 이번 TYPE2/PPT 정합본의 핵심 원칙

이번 버전에서는 기존 TYPE2의 6단계 Top-down 철학은 유지하되, **세부 목차는 실제 PPT를 최우선으로 따른다.**

```text
[상위 설명 순서]
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

[세부 장/절 이름]
실제 PPT의 1~11장 목차를 최대한 그대로 보존
```

즉, 6단계는 **Architecture Level / Reading Route**이고,
PPT의 1~11장은 **Official Chapter / Deliverable Structure**다.

두 체계를 충돌시키지 않는다.

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
+ Architecture Visual Designer
```

일반적인 금융권 Best Practice를 새로 창작하는 것이 목적이 아니다.

반드시 다음 순서로 작업한다.

```text
① 실제 PPT 목차와 장표의 구조를 먼저 고정
② PPT에 실제 들어 있는 Architecture 내용을 전사
③ 기존 정의서/분석 MD로 의미를 보강
④ PDMG Source/Config/Runtime은 AS-IS Evidence로 연결
⑤ 근거가 없는 부분은 GAP/OPEN/UNKNOWN으로 유지
⑥ 이미지화
```

---

# 2. 구조 우선순위와 사실 우선순위를 분리한다

## 2.1 문서 구조 우선순위

장 번호, 절 번호, 장표 그룹, 장표 명칭은 다음 순서로 따른다.

```text
1. NSIGHT_아키텍처_정의서_통합본_20260825.pptx 실제 목차
2. PPT 본문의 실제 장/절 표기
3. 기존 01~11 정의서
4. 기존 TYPE2 마스터 프롬프트
5. 신규 재분류/분석
```

**PPT와 기존 TYPE2가 충돌하면 세부 목차는 PPT를 우선한다.**

## 2.2 Architecture 사실 우선순위

AS-IS / TO-BE의 사실성은 별도 기준으로 판단한다.

```text
Current Source / Config / Runtime Evidence
        >
Approved Architecture Baseline / ADR
        >
공식 PPT / 공식 전략·설계 문서
        >
Working Baseline / 분석 Markdown
        >
과거 문서 / 참고 그림
        >
일반론 / 추론
```

주의:

```text
PPT가 목차의 기준
≠
PPT의 모든 기술값이 최신 Runtime Fact
```

```text
Source가 AS-IS의 강한 Evidence
≠
Source 구현을 NSIGHT TO-BE로 자동 승격
```

---

# 3. Evidence 상태 표기

| 상태 | 의미 |
|---|---|
| `[PPT-TOC]` | 실제 PPT 목차에 선언된 장/절 |
| `[PPT-BODY]` | 실제 PPT 본문 장표에서 확인 |
| `[PPT-CONTENT-GAP]` | 목차에는 있으나 독립 상세 장표가 부족/미확인 |
| `[NUMBERING-DRIFT]` | 목차와 본문 장/절 명칭 또는 번호가 다름 |
| `[DUPLICATE-BLOCK]` | 동일/유사 장표가 반복 삽입됨 |
| `[FACT]` | 공식 자료/Source/Config/Runtime에서 직접 확인 |
| `[CONFIRMED]` | 복수 근거가 일치 |
| `[AS-IS]` | 현재 구현/현행 |
| `[TO-BE]` | 승인된 목표 |
| `[DECISION]` | 승인된 결정 |
| `[PROPOSED]` | 제안 |
| `[GAP]` | 목표·자료·구현 간 차이 |
| `[CONFLICT]` | 복수 근거 충돌 |
| `[RISK]` | 위험 |
| `[OPEN]` | 결정 필요 |
| `[UNKNOWN]` | 현재 근거로 확인 불가 |
| `[DEPRECATED]` | 폐기/과거 기준 |

---

# 4. PPT 실제 공식 목차 — 절대 보존 기준

아래를 **PPT 공식 장/절 목차의 SSOT**로 사용한다.

## 4.1 1~6장

```text
1. 아키텍처 정의
   1.1 개요
   1.2 어플리케이션 분류 체계
   1.3 데이터 주제영역 정의
   1.4 시스템 아키텍처 구성

2. 논리 기술 아키텍처
   2.1 전사 IT Zone 기반 구성 기준
   2.2 시스템 노드 정의 및 식별
   2.3 기술 컴포넌트 정의
   2.4 논리 기술 아키텍처 정의

3. 물리 인프라 아키텍처
   3.1 하드웨어 구성도
   3.2 소프트웨어 구성도
   3.3 하드웨어 목록
   3.4 소프트웨어 목록

4. 데이터베이스 아키텍처
   4.1 DB 아키텍처 구성도
   4.2 DB 이중화 구성도
   4.3 OGG 구성도
   4.4 OLTP 및 대용량 배치 수행 방안

5. 시스템 표준 정의
   5.1 서버 호스트 명명규칙
   5.2 파일 시스템 구성
   5.3 사용자 계정
   5.4 서비스 포트 현황

6. 인터페이스 아키텍처
   6.1 인터페이스 표준 정의
   6.2 인터페이스 구성도
```

## 4.2 7~11장

```text
7. 런타임 아키텍처
   7.1 업무 처리 유형
   7.2 단말 거래 처리
   7.3 미니 싱글뷰
   7.4 UMS 고객 통지

8. 아키텍처 표준화
   8.1 어플리케이션 계층 구조
   8.2 전문 표준화 정의
   8.3 GUID 관리 체계 정의
   8.4 캐릭터 셋 정의
   8.5 XX
   8.6 XX
   8.7 XX
   8.8 XX
   8.9 XX

9. 아키텍처 구성 요소
   9.1 단말 프레임워크
   9.2 온라인 프레임워크
   9.3 배치 프레임워크

10. 업무 솔루션 아키텍처
   10.1 SELF-BI
   10.2 OLAP
   10.3 EBM
   10.4 데이터 흐름

11. 기타
   11.1 시스템 모니터링
   11.2 시스템 가용성
   11.3 시스템 확장성
   11.4 DR 구성 (센터 간 가용성)
   11.5 백업 구성
```

**장/절 이름을 임의로 바꾸지 않는다.**

---

# 5. 6단계 Architecture Route와 PPT 목차 매핑

공식 번호를 유지하면서 다음 Architecture Level로 묶어 설명한다.

| Architecture Level | PPT 공식 장/절 | 설명 |
|---|---|---|
| **VISION** | 1.1 개요 | 왜 개편하며 어디로 갈 것인가 |
| **BIG PICTURE** | 1.2~1.4 | Application/Data/System 전체 공간과 책임 |
| **LOGICAL** | 2장 + 6장의 논리 경계 View | Zone·논리노드·기술컴포넌트·논리 연결 |
| **PHYSICAL** | 3장 + 4장 + 5장 | HW/SW/DB/Host/FS/Account/Port |
| **MECHANISM** | 6장 + 8장 + 9장 + 10장 | Interface/표준전문/GUID/Framework/Solution 동작원리 |
| **RUNTIME** | 7장 + 11장 | 업무 처리 유형·실제 시간순 실행·운영/HA/DR |

권장 Reading Route:

```text
1.1
 → 1.2 → 1.3 → 1.4
 → 2
 → 3 → 4 → 5
 → 6 → 8 → 9 → 10
 → 7 → 11
```

주의:

- **공식 산출물 번호는 1→11을 유지한다.**
- 위 Reading Route는 인포그래픽/설명 순서일 뿐 번호를 재번호화하지 않는다.
- PPT의 `7. 런타임` 번호를 `10. 런타임` 등으로 바꾸지 않는다.

---

# 6. 실제 PPT Slide Inventory — 작업 기준

현재 PPT는 총 **163 Slides**다.

## 6.1 실제 상세 범위

| 구간 | 실제 내용 | 처리 원칙 |
|---|---|---|
| 1~2 | 전체 목차 | 기준 |
| 3~22 | 1장 아키텍처 정의 | 상세 존재 |
| 23~24 | 전체 목차 반복 | Navigation |
| 25~62 | 2장 논리 기술 아키텍처 | 상세 존재 |
| 63 | 목차 | Navigation |
| 64~77 | 3장 물리 인프라 | 상세 존재 |
| 78 | 목차 | Navigation |
| 79~83 | 4장 DB | 상세 존재 |
| 84 | 목차 | Navigation |
| 85~89 | 5장 시스템 표준 | 상세 존재 |
| 90 | 목차 | Navigation |
| 91~100 | 6장 인터페이스 | 상세 존재 |
| 101 | 목차 | Navigation |
| 102~108 | 7장 런타임 중 7.1 중심 | 상세 일부 |
| 109 | 목차 | Navigation |
| 110~123 | 8장에 대응하는 표준화/거래구조 블록 | 1차본 |
| 124 | 목차 | Navigation |
| 125~138 | 110~123 유사 반복 블록 | 후행/보완본 우선 비교 |
| 139 | 목차 | Navigation |
| 140~162 | 9장 구성요소 | 상세 존재 |
| 163 | 목차 | Navigation |
| - | 10장 독립 상세 | `[PPT-CONTENT-GAP]` |
| - | 11장 독립 상세 | `[PPT-CONTENT-GAP]` |

---

# 7. PPT 정합성 특수 규칙

## 7.1 2장 Numbering Drift

목차:

```text
2.1 전사 IT Zone 기반 구성 기준
2.2 시스템 노드 정의 및 식별
```

실제 본문 주요 표기:

```text
2.2 시스템 영역 및 구성요소 정의
```

따라서 다음처럼 기록한다.

```text
[PPT-TOC]  2.2 시스템 노드 정의 및 식별
[PPT-BODY] 2.2 시스템 영역 및 구성요소 정의
[NUMBERING-DRIFT]
```

**임의로 둘 중 하나를 삭제하지 않는다.**

`2.1 전사 IT Zone 기반 구성 기준`은 목차에는 있으나 독립 상세 슬라이드가 명확하지 않으면:

```text
[PPT-CONTENT-GAP]
```

으로 남긴다.

---

## 7.2 6장 삽입 Context 장표

6장 구간에는 다음이 삽입되어 있다.

```text
상위 수준 시스템 Context 다이어그램
마케팅 플랫폼 Context
```

일부 장표의 내부 번호가 `2.`로 표시되어도,
파일 내 실제 위치가 6장 인터페이스 구간이면:

```text
[PPT-BODY / INSERTED CONTEXT]
```

로 관리하고 **자동으로 2장에 이동시키지 않는다.**

---

## 7.3 7장 Content Gap

목차에는:

```text
7.1 업무 처리 유형
7.2 단말 거래 처리
7.3 미니 싱글뷰
7.4 UMS 고객 통지
```

가 있으나 현재 상세 구간은 주로 다음 6대 업무 처리 유형을 설명한다.

```text
채널
연계
마케팅 이벤트
데이터 분석/제공
파일 연계
배치
```

따라서:

- `7.1 업무 처리 유형` → `[PPT-BODY]`
- `7.2~7.4` → 독립 상세가 확인되지 않으면 `[PPT-CONTENT-GAP]`
- 기존 분석 문서/PDMG Source를 사용해 보강할 수 있으나 **PPT 원본 내용처럼 표시하지 않는다.**

---

## 7.4 8장 XX와 실제 확장 장표

PPT 목차:

```text
8.1 어플리케이션 계층 구조
8.2 전문 표준화 정의
8.3 GUID 관리 체계 정의
8.4 캐릭터 셋 정의
8.5 XX
8.6 XX
8.7 XX
8.8 XX
8.9 XX
```

실제 8장 성격의 장표에는 다음이 존재한다.

```text
정보 단말
온라인 프레임워크
거래 처리 구조
선/후 처리 범위
호출 구조 정의
도메인 정의
거래 처리 경로
미니 싱글뷰 거래 처리 경로
마케팅 플랫폼 거래 처리 경로
BI 포탈 거래 처리 경로
업무 솔루션 거래 처리 경로
전문 표준화
GUID 관리 체계 정의
캐릭터 셋 정의
```

중요:

**8.5~8.9의 공식 이름을 임의로 결정하지 않는다.**

대신:

```text
[8.X 확장 주제 후보]
- 거래 처리 구조
- 선/후 처리
- 호출 구조
- 도메인
- 거래 처리 경로
```

처럼 표기한다.

공식 번호 확정 전까지 `8.5 도메인 정의` 식으로 단정 금지.

---

## 7.5 8장 Duplicate Block

110~123과 125~138은 거의 동일한 블록이 반복된다.

처리 원칙:

```text
[DUPLICATE-BLOCK]
전행본 110~123
후행본 125~138
```

- 동일 내용은 인포그래픽을 2번 생성하지 않는다.
- 두 버전이 다르면 **후행본을 우선 후보**로 사용하되 차이를 기록한다.
- 예: 거래 처리 구조와 마케팅 플랫폼 URL/WAR 표현에는 일부 보완 차이가 존재한다.
- 중복은 삭제 사실이 아니라 `PPT 구조상 반복`으로 기록한다.

---

## 7.6 10장 / 11장 Content Gap

목차에는 장이 선언되어 있으나 현재 163 Slides에서 독립 상세가 충분히 전개되지 않았다.

```text
10. 업무 솔루션 아키텍처       [PPT-CONTENT-GAP]
11. 기타                      [PPT-CONTENT-GAP]
```

기존 작성 정의서로 보강할 경우:

```text
[PPT-TOC]
+
[SUPPLEMENTAL-DEFINITION]
```

으로 구분한다.

PPT에 없는 내용을 `[PPT-BODY]`로 표시하지 않는다.

---

# 8. 이미지 디자인 스타일

기존 TYPE2의 여행계획표형 스타일을 유지한다.

```text
배경      : Cream / Ivory
주색      : Teal / Mint / Sky Blue / Deep Navy
강조      : Orange / Amber / Purple / Red
형태      : Rounded Card / Route Line / Number Badge
분위기    : 프리미엄 여행 일정표 + Enterprise Architecture
텍스트    : 한글 중심, 짧고 정확
```

단, **PPT의 구조와 명칭을 디자인보다 우선**한다.

장식 때문에 다음이 바뀌면 안 된다.

```text
장 번호
절 번호
Application 이름
System 이름
Zone 이름
Server/DB 이름
Interface 방식
Flow 방향
AS-IS / TO-BE 상태
```

---

# 9. 모든 장의 공통 인포그래픽 레이아웃

```text
┌────────────────────────────────────────┐
│ Chapter No / PPT Official Title        │
│ Architecture Level Badge               │
│ PPT Evidence Status                    │
├────────────────────────────────────────┤
│ 1. 이 장이 답하는 핵심 질문           │
├────────────────────────────────────────┤
│ 2. PPT 원본 구조 재현                  │
├────────────────────────────────────────┤
│ 3. Architecture Top-down 해석          │
├────────────────────────────────────────┤
│ 4. 상세 Card / Inventory / Mapping     │
├────────────────────────────────────────┤
│ 5. 허용 / 금지 / 책임 경계             │
├────────────────────────────────────────┤
│ 6. PDMG / Source Evidence (해당 시)    │
├────────────────────────────────────────┤
│ 7. GAP / DRIFT / OPEN / Next           │
└────────────────────────────────────────┘
```

모든 장은 첫 카드에 반드시 다음을 넣는다.

```text
PPT 장/절:
Architecture Level:
PPT Slide Range:
Evidence:
다음 장:
```

---

# 10. PART I — VISION
## PPT 공식 범위: 1.1 개요

### 10.1 목적

```text
왜 차세대 정보계를 개편하는가?
어떤 방향과 목표를 갖는가?
Architecture를 왜 정의하는가?
```

### 10.2 PPT에서 우선 반영할 실제 주제

```text
1.1 개요
  ├─ 아키텍처 정의 목적
  ├─ 차세대 정보계 개편 기본 방향
  └─ 차세대 정보계 구축 방향 및 목표
```

실제 장표에서 확인되는 핵심 축:

```text
고객 중심 서비스 강화
데이터 기반 의사결정 강화
통합 정보 활용 기반
실시간 정보 활용
운영 효율 / 민첩성
```

### 10.3 필수 이미지

```text
FIG-VSN-01 아키텍처 정의 목적
FIG-VSN-02 개편 기본 방향
FIG-VSN-03 구축 배경 → 전략 방향 → 목표
FIG-VSN-04 Vision → Big Picture Handoff
```

### 10.4 금지

- PDMG Handler/TCF로 시작 금지
- 서버/Port/Thread 수치로 Vision 설명 금지
- PPT에 없는 전략을 확정 방향으로 추가 금지

---

# 11. PART II — BIG PICTURE
## PPT 공식 범위: 1.2 / 1.3 / 1.4

### 11.1 1.2 어플리케이션 분류 체계

PPT 실내용을 다음 순서로 최대한 보존한다.

```text
차세대 정보계 개념 아키텍처
  ↓
어플리케이션 도메인 구성 정의
  ↓
어플리케이션 분류 체계 (1/5~5/5)
  ↓
시스템 그룹 업무 구분 (1/6~6/6)
```

#### 필수 5개 Application Domain

```text
마케팅플랫폼
데이터플랫폼
BI 포탈
데이터거버넌스
IT서비스 및 인프라/업무지원
```

#### Application Classification Inventory

| 필드 | 필수 |
|---|---|
| 대구분 코드 | Y |
| 도메인명 | Y |
| 업무구분 코드 | Y |
| Application | Y |
| 기능/세부업무 | Y |
| System Group | Y |
| Owner | 가능 시 |
| 근거 Slide/문서 | Y |

#### System Group Mapping

PPT의 6개 그룹을 그대로 다룬다.

```text
마케팅플랫폼 시스템 그룹
데이터플랫폼 시스템 그룹
BI 포탈 시스템 그룹
데이터거버넌스 시스템 그룹
IT서비스 및 인프라 지원 시스템 그룹
인프라 임시 시스템 그룹
```

---

### 11.2 1.3 데이터 주제영역 정의

PPT의 `데이터 주제영역 구성 정의(1/2),(2/2)`를 원본 구조대로 먼저 재현한다.

필수 표현:

```text
Subject Area
  → 데이터 역할
  → RDW/ADW 위치
  → SoR 성격
  → 주요 소비 Application
```

#### Data Subject Inventory

| 필드 | 필수 |
|---|---|
| 주제영역 ID | Y |
| 주제영역명 | Y |
| 설명 | Y |
| RDW/ADW | Y |
| SoR/집계/분석 성격 | Y |
| 주요 Source | Y |
| 주요 Consumer | Y |
| Owner | 가능 시 |
| 근거 | Y |

---

### 11.3 1.4 시스템 아키텍처 구성

반드시 실제 PPT의 두 장표를 중심으로 한다.

```text
전체 시스템 아키텍처 구조 정의
주요 시스템 대상 서버 식별
```

이 장에서 아직 Hostname/물리대수까지 내려가지 않는다.

```text
채널
  ↓
시스템 영역
  ↓
Application/System
  ↓
Logical Server Group
```

#### 필수 이미지

```text
FIG-BP-01 차세대 정보계 개념 아키텍처
FIG-BP-02 Application Domain Map
FIG-BP-03 Application Classification Inventory
FIG-BP-04 System Group Business Map
FIG-BP-05 Application ↔ System Mapping
FIG-BP-06 Data Subject Area Map
FIG-BP-07 Data Subject Inventory
FIG-BP-08 전체 시스템 아키텍처
FIG-BP-09 주요 시스템 대상 서버 식별
```

---

# 12. PART III — LOGICAL
## PPT 공식 범위: 2장 중심 + 6장 Logical Cross Reference

# 12.1 2. 논리 기술 아키텍처

공식 목차:

```text
2.1 전사 IT Zone 기반 구성 기준
2.2 시스템 노드 정의 및 식별
2.3 기술 컴포넌트 정의
2.4 논리 기술 아키텍처 정의
```

---

## 12.2 2.1 전사 IT Zone 기반 구성 기준

독립 상세가 부족하면 다음처럼 시각화한다.

```text
[PPT-TOC]
2.1 전사 IT Zone 기반 구성 기준

[PPT-CONTENT-GAP]
본문 근거 확보 필요
```

기존 정의서/분석자료를 보강 근거로 쓸 수 있으나 별도 태그를 붙인다.

필수 Logical Zone View:

```text
대내 채널
대고객 채널
대외 채널
채널 통합
서비스 제공
대내 통합
```

---

## 12.3 2.2 시스템 노드 정의 및 식별

PPT 본문의 실제 명칭 Drift를 같이 보여 준다.

```text
[PPT-TOC]
시스템 노드 정의 및 식별

[PPT-BODY]
시스템 영역 및 구성요소 정의
```

실제 환경별 전개를 그대로 반영:

```text
운영환경
  ├─ 5개 상시 시스템
  └─ 이행용 임시 구성

DR환경
  └─ 3개 시스템

개발환경
  └─ 5개 시스템

선도환경
  └─ 3개 시스템
```

각 환경에 대해:

```text
Zone
 → System
 → Logical Node
 → Major Component
 → External/Channel Relation
```

을 보여 준다.

---

## 12.4 2.3 기술 컴포넌트 정의

PPT 장표 순서를 최대한 보존한다.

```text
공통 기술 컴포넌트
마케팅플랫폼 (1/3~3/3)
데이터플랫폼
BI 포탈 (1/4~4/4)
데이터거버넌스
IT서비스 및 업무지원 (1/3~3/3)
기타 기술 컴포넌트
```

각 Component Card 필드:

| 필드 | 내용 |
|---|---|
| 시스템/도메인 | 소속 |
| 논리 노드 | WEB/WAS/AP/DB 등 |
| 기술 역할 | Web/Business/Stream/ETL/DB 등 |
| 주요 SW/FW | PPT 근거 |
| 주요 연결 | Source/Target |
| 물리 매핑 | 3장 참조 |
| Runtime Type | 7장 참조 |

---

## 12.5 2.4 논리 기술 아키텍처 정의

현재 PPT 상세로 확인되는 도메인별 View:

```text
마케팅플랫폼
데이터플랫폼
BI 포탈
데이터거버넌스
```

IT서비스 및 업무지원 논리기술 View가 독립 장표로 없으면 Gap으로 남긴다.

필수 이미지:

```text
FIG-LG-01 6 Zone 기준
FIG-LG-02 운영환경 시스템/노드
FIG-LG-03 DR환경 시스템/노드
FIG-LG-04 개발환경 시스템/노드
FIG-LG-05 선도환경 시스템/노드
FIG-LG-06 기술 컴포넌트 Map
FIG-LG-07 마케팅 Logical
FIG-LG-08 데이터 Logical
FIG-LG-09 BI Logical
FIG-LG-10 거버넌스 Logical
FIG-LG-11 Logical GAP/Drift
```

---

# 13. PART IV — PHYSICAL
## PPT 공식 범위: 3장 + 4장 + 5장

# 13.1 3. 물리 인프라 아키텍처

## 3.1 하드웨어 구성도

실제 순서:

```text
운영/DR 환경
개발 환경
선도 환경
```

반드시 센터/환경을 분리해서 표현한다.

```text
의왕 센터
안성 DR 센터
```

## 3.2 소프트웨어 구성도

```text
운영
DR
개발
선도
```

HW 구성도와 SW 구성도를 섞지 않는다.

## 3.3 하드웨어 목록

```text
운영 1/3
운영 2/3
운영 3/3
DR
개발
선도
```

Inventory 중심으로 시각화한다.

## 3.4 소프트웨어 목록

PPT에 선언된 **운영 S/W 17종 및 버전**을 기준으로 작성한다.
목록 외 제품/버전 생성 금지.

---

# 13.2 4. 데이터베이스 아키텍처

공식 목차를 그대로 유지한다.

```text
4.1 DB 아키텍처 구성도
4.2 DB 이중화 구성도
4.3 OGG 구성도
4.4 OLTP 및 대용량 배치 수행 방안
```

필수 핵심:

```text
RDW
ADW
Exadata RAC
OGG
Downstream
OLTP / 대용량 배치 분리
Application ↔ DB Matrix
```

특히:

```text
CDC/OGG ≠ ETL
RDW ≠ ADW
OLTP 부하 ≠ 대용량 배치 부하
```

를 명확히 한다.

---

# 13.3 5. 시스템 표준 정의

```text
5.1 서버 호스트 명명규칙
5.2 파일 시스템 구성
5.3 사용자 계정
5.4 서비스 포트 현황
```

PPT의 실제 정책값을 우선 사용한다.

필수 이미지:

```text
FIG-PH-01 운영/DR HW
FIG-PH-02 개발 HW
FIG-PH-03 선도 HW
FIG-PH-04 환경별 SW
FIG-PH-05 HW Inventory
FIG-PH-06 SW Inventory
FIG-PH-07 DB Architecture
FIG-PH-08 DB HA
FIG-PH-09 OGG
FIG-PH-10 OLTP/Batch
FIG-PH-11 App↔DB Matrix
FIG-PH-12 Hostname Anatomy
FIG-PH-13 Filesystem
FIG-PH-14 Account
FIG-PH-15 Port
```

---

# 14. PART V — MECHANISM
## PPT 공식 범위: 6장 + 8장 + 9장 + 10장

# 14.1 6. 인터페이스 아키텍처

## 6.1 인터페이스 표준 정의

실제 PPT 세부 내용을 다음 순서로 전개한다.

```text
정보계 인터페이스 표준 정의
온라인 인터페이스 표준 정의
파일 인터페이스 표준 정의
데이터 인터페이스 표준 정의
인터페이스 구성의 상위 View
```

PPT 핵심 원칙:

```text
온라인 AP / 배치 AP 자원 분리
ETL 독립
Event 처리 자원 분리
CDC 중계
```

연계 수단을 하나로 통일하지 않는다.

```text
Online → MCA/API
File   → FOS/MFT
Data   → CDC/ETL
Event  → Kafka 계열
```

## 6.2 인터페이스 구성도

현재 상세이 존재하는 중심:

```text
마케팅플랫폼
데이터플랫폼
BI포탈
```

데이터거버넌스/IT서비스가 없으면 GAP으로 둔다.

---

# 14.2 8. 아키텍처 표준화

8장은 PPT 목차보다 실제 장표가 더 풍부하다.

## 8.1 어플리케이션 계층 구조

실제 관련 장표를 하위 Card로 재배치한다.

```text
정보 단말
온라인 프레임워크
거래 처리 구조
선/후 처리 범위
호출 구조 정의
도메인 정의
거래 처리 경로
```

단, 위 항목의 공식 8.x 번호는 새로 발명하지 않는다.

### 거래 8단계

```text
1 시스템 선 처리
2 공통 선 처리
3 업무 선 처리
4 Controller / 업무 본 처리
5 Biz Service
6 업무 후 처리
7 공통 후 처리
8 시스템 후 처리
```

### 호출 구조

```text
Controller
  → Service
  → DAO
  → SQL/DB
```

업무간 직접 DAO 접근 등 PPT/정의서에서 금지된 구조를 별도 경고 Card로 표시한다.

### 도메인 / URL / 거래처리 경로

실제 PPT 경로를 임의 일반화하지 않는다.

```text
미니 싱글뷰
마케팅 플랫폼
BI 포탈
업무 솔루션
```

Domain/URL은 PPT 값과 Source/운영값을 구분한다.

---

## 8.2 전문 표준화 정의

```text
채널 ↔ 채널통합 ↔ 정보계 ↔ 대내통합
```

같은 논리 표준전문이 적용되는 범위와 Package UI 예외를 구분한다.

---

## 8.3 GUID 관리 체계 정의

필수 표현:

```text
최초 생성
  → 노드 경유
  → 진행 일련번호
  → End-to-End Trace
```

Source에서 실제 진행번호 증가 구현이 확인되지 않으면 `[GAP]`.

---

## 8.4 캐릭터 셋 정의

PPT의 기준을 그대로 표시한다.

```text
채널 / 업무 시스템 → UTF-8
RDW / ADW DB       → MS949
```

이 값은 설계 기준과 실제 DB/NLS 설정을 구분하여 검증한다.

---

## 8.5~8.9

공식 제목:

```text
XX
```

따라서 이미지에 새 공식 이름을 붙이지 않는다.

대신 다음을 `확장 후보`로만 표시한다.

```text
[8.X 후보]
거래 처리 구조
선/후 처리
호출 구조
도메인
거래 처리 경로
```

---

# 14.3 9. 아키텍처 구성 요소

## 9.1 단말 프레임워크

PPT 실제 장표:

```text
단말 프레임워크
```

단말 Project/Directory 세부가 다른 장에 있으면 Cross Reference.

## 9.2 온라인 프레임워크

실제 장표 전개를 최대한 보존:

```text
온라인 프레임워크
단말 - Application Framework 연계
File Upload
File Download
레포트(RD) 연계
In bound 거래 처리
SSO 연계
온라인 선/후처리
Exception Handling (1/2, 2/2)
거래로그
Master Solution (1/2, 2/2)
클라우드 프레임워크 제공 기능
상용 F/W 대비 대응 기능 - 개발 환경
상용 F/W 대비 대응 기능 - 실행 환경 (1/4~4/4)
상용 F/W 대비 대응 기능 - 운영 환경
업무 개발을 위한 프레임워크 사용 기준
```

중요:

- 이 실제 순서를 별도 Detail Card로 최대한 보존한다.
- PDMG Source와 다른 Framework 명칭/구조는 `[PPT TO-BE] vs [PDMG AS-IS]`로 구분한다.
- `Nh...Controller` 등의 PPT 구성요소를 PDMG Source의 클래스라고 단정하지 않는다.

## 9.3 배치 프레임워크

PPT의 마지막 실제 상세 장표를 기준으로:

```text
작업 자동화 관리
  → 배치 작업 등록/조회
  → Scheduler / Control-M
  → Batch Framework
```

를 표현한다.

---

# 14.4 10. 업무 솔루션 아키텍처

PPT 공식 목차:

```text
10.1 SELF-BI
10.2 OLAP
10.3 EBM
10.4 데이터 흐름
```

현재 독립 상세 장표가 충분하지 않으므로:

```text
[PPT-CONTENT-GAP]
```

을 첫 카드에 명시한다.

기존 작성된 `업무 솔루션 아키텍처 정의서`를 보강 근거로 사용할 수 있으나:

```text
[PPT-TOC]
[SUPPLEMENTAL-DEFINITION]
```

을 구분한다.

---

# 15. PART VI — RUNTIME
## PPT 공식 범위: 7장 + 11장

# 15.1 7. 런타임 아키텍처

## 7.1 업무 처리 유형

실제 PPT에서 확인되는 6대 유형:

```text
채널 업무 처리
연계 업무 처리
마케팅 이벤트 업무 처리
데이터 분석/제공 업무 처리
파일 연계 업무 처리
배치 업무 처리
```

이 6대 분류를 Runtime의 최상위 기준으로 쓴다.

각 Runtime Figure는 반드시 다음을 가진다.

```text
Actor
Entry
Interface
Application/System
Data
Sequence
SYNC/ASYNC
Failure
Retry/Replay
SLO
Owner
Evidence
```

---

## 7.2 단말 거래 처리

목차는 유지한다.

독립 상세 PPT가 부족하면:

```text
[PPT-CONTENT-GAP]
```

기존 8장/9장 거래처리경로, PDMG Runtime 정의서로 보강하되 원본과 구분한다.

---

## 7.3 미니 싱글뷰

목차는 유지하고,
실제 PPT의 `미니 싱글뷰 거래 처리 경로`를 Cross Reference로 사용할 수 있다.

표기:

```text
[PPT-TOC 7.3]
[PPT-BODY CROSS-REFERENCE: 8장 성격 장표]
```

---

## 7.4 UMS 고객 통지

목차를 유지한다.

실제 독립 상세가 확인되지 않으면:

```text
[PPT-CONTENT-GAP]
```

마케팅 이벤트/UMS 기존 정의서로 보강 가능.

---

# 15.2 PDMG Runtime Reference

PPT Runtime을 실제 Source Reference로 Drill-down할 필요가 있을 때만 사용한다.

```text
HTTP Request
  ↓
Filter / Security / MVC
  ↓
Controller
  ↓
TCF / Dispatcher
  ↓
Handler / Facade / Service
  ↓
DAO / Mapper / DB
```

중요:

- PDMG Runtime은 PPT 7장의 전사 Runtime 유형을 대체하지 않는다.
- `PDMG = Online Application Runtime Reference`
- Event/CDC/ETL/File/BI 전체 Runtime을 PDMG로 설명하지 않는다.

---

# 15.3 Thread / Timeout / Transaction Runtime

PDMG Source가 근거일 때만 다음을 별도 보조 Figure로 사용한다.

```text
Request Thread
  ↓ submit
Worker Thread
  ↓
TransactionTemplate
  ↓
Dispatcher → Handler → Facade → Service → DAO
```

다음 네 개를 같은 의미로 쓰지 않는다.

```text
HTTP Timeout
Worker 종료
JDBC Statement 취소
DB Transaction Rollback
```

---

# 15.4 11. 기타 — 운영 Runtime / Resilience

공식 목차:

```text
11.1 시스템 모니터링
11.2 시스템 가용성
11.3 시스템 확장성
11.4 DR 구성 (센터 간 가용성)
11.5 백업 구성
```

현재 PPT 상세가 충분하지 않으면 각 절을:

```text
[PPT-CONTENT-GAP]
```

으로 시작한다.

기존 11장 정의서와 Physical/Runtime Evidence를 보강 근거로 사용한다.

필수 운영 관점:

```text
Monitoring
Availability
Scalability
DR
Backup / Restore
Runtime Evidence
```

---

# 16. PDMG Source Baseline

PDMG는 다음 기준 모듈을 중심으로 분석한다.

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

단:

- 실제 Source Snapshot에 없는 모듈은 `[EXPECTED BASELINE] / [UNKNOWN]`
- `pdmg-fw` 별도 모듈 ≠ 독립 원격 서버
- Spring Context/JVM 경계는 Source로 확인
- PDMG는 PPT/NSIGHT 전체 표준으로 자동 승격하지 않음

---

# 17. Inventory 규칙

Architecture 정의는 그림만으로 끝내지 않는다.

## 17.1 Application Inventory

```text
Domain
Application Group
Application
Function
System Group
Owner
ServiceId 범위
Runtime Type
Evidence
```

## 17.2 System / Node Inventory

```text
Environment
Zone
System
Logical Node
Technology Component
Physical Host
SW
Owner
Evidence
```

## 17.3 Data Inventory

```text
Subject Area
RDW/ADW
Source
Target
SoR
Refresh/Latency
Owner
Evidence
```

## 17.4 Interface Inventory

```text
InterfaceId
Source
Target
Type
Protocol/Medium
SYNC/ASYNC
Schema
Timeout
Retry
Idempotency
Owner
Runtime Type
Evidence
```

## 17.5 Physical Inventory

```text
Environment
Center
Hostname
Role
HW
OS
SW
Capacity
HA Pair
DR Pair
Port
Backup
Evidence
```

---

# 18. Text Architecture 선행 규칙

이미지를 생성하기 전에 반드시 원본 구조를 ASCII/Text로 재현한다.

예:

```text
[PPT 원본 구조]
채널
  ↓
채널 통합
  ↓
서비스 제공
  ├─ 마케팅
  ├─ 데이터
  ├─ BI
  ├─ 거버넌스
  └─ IT지원
  ↓
대내 통합
```

그 다음에만 디자인한다.

금지:

```text
원본 구조 해석 없이 바로 이미지 생성
표를 읽지 않고 일반론으로 Box 추가
화살표 방향 임의 변경
PPT에 없는 System/Component를 FACT로 삽입
```

---

# 19. 선과 박스 의미

모든 화살표에는 의미를 부여한다.

```text
HTTP
MCA
API
File/FOS
CDC/OGG
ETL
Kafka/Event
JDBC
Deployment
Replication
Failover
Trace
```

단순 장식 화살표 금지.

박스에도 유형을 표시한다.

```text
Channel
System
Application
Framework
Integration
DB
File
Event
Control Plane
Monitoring
External
```

---

# 20. PPT 원문 보존 규칙

다음은 최대한 원문을 유지한다.

```text
장/절 명칭
System 이름
Application 이름
솔루션 이름
Zone 이름
환경 이름
센터 이름
Host 역할
DB 이름
Protocol/Interface 이름
URL/Domain
Framework 구성요소
```

맞춤법이나 띄어쓰기 보정 때문에 Architecture Entity가 바뀌면 안 된다.

예:

```text
BI 포탈 ↔ BI포탈
IT서비스 및 인프라 지원 ↔ IT서비스 및 업무지원
```

PPT 내 표현이 혼재하면 하나를 임의 삭제하지 말고:

```text
[PPT TERMINOLOGY DRIFT]
```

로 기록한다.

---

# 21. PPT Gap 보강 규칙

PPT에 비어 있는 부분은 세 등급으로 보강한다.

```text
Level A
PPT 다른 장표에서 직접 Cross Reference

Level B
기존 NSIGHT 정의서/분석 Markdown

Level C
PDMG Source/Config/Runtime Evidence
```

그래도 근거가 없으면:

```text
[OPEN]
[UNKNOWN]
[PPT-CONTENT-GAP]
```

으로 남긴다.

**일반론으로 채우지 않는다.**

---

# 22. 장별 이미지 생성 템플릿

아래 형식을 매 장 반복한다.

```text
[작성 대상]
PPT 공식 장/절:
Architecture Level:
PPT Slide Range:
PPT Body Title:
Evidence Status:

[목표]
이 장이 해결할 Architecture 질문을 1~3문장으로 설명

[원본 구조]
PPT 구조를 ASCII/Text로 재현

[필수 Figure]
FIG-...
FIG-...
FIG-...

[Inventory]
필요한 표/매핑

[AS-IS / TO-BE]
PPT Target와 PDMG Source Reference 분리

[금지]
임의 이름변경
임의 번호변경
근거 없는 기술값
일반론으로 Gap 채움

[하단 상태]
PPT-TOC
PPT-BODY
FACT
GAP
OPEN
NUMBERING-DRIFT
Next Chapter
```

---

# 23. 최종 실행용 통합 프롬프트

```text
너는 NH 농협 상호금융 차세대 정보계 NSIGHT 프로젝트의
Chief Enterprise Architect이자 Architecture Visual Designer다.

이번 작업의 최우선 기준 문서는
`NSIGHT_아키텍처_정의서_통합본_20260825.pptx`이다.

목표는 이 PPT의 실제 목차와 장표를 최대한 준수하면서
전체 내용을 다음 Architecture Reading Route로 재구성해
여행계획표형 상세 인포그래픽으로 만드는 것이다.

VISION
→ BIG PICTURE
→ LOGICAL
→ PHYSICAL
→ MECHANISM
→ RUNTIME

그러나 PPT의 공식 장 번호 1~11과 절 번호는 변경하지 않는다.

공식 목차:
1. 아키텍처 정의
2. 논리 기술 아키텍처
3. 물리 인프라 아키텍처
4. 데이터베이스 아키텍처
5. 시스템 표준 정의
6. 인터페이스 아키텍처
7. 런타임 아키텍처
8. 아키텍처 표준화
9. 아키텍처 구성 요소
10. 업무 솔루션 아키텍처
11. 기타

Architecture Level 매핑:
VISION      = 1.1
BIG PICTURE = 1.2~1.4
LOGICAL     = 2 + 6 Logical Cross Reference
PHYSICAL    = 3 + 4 + 5
MECHANISM   = 6 + 8 + 9 + 10
RUNTIME     = 7 + 11

절대 규칙:
1. PPT의 장/절 명칭을 임의 변경하지 않는다.
2. PPT 목차와 본문 명칭이 다르면 NUMBERING-DRIFT로 병기한다.
3. PPT 목차에만 있고 본문 상세가 부족하면 PPT-CONTENT-GAP으로 남긴다.
4. 8.5~8.9의 XX는 공식 이름을 새로 만들지 않는다.
5. 110~123과 125~138의 반복 블록은 DUPLICATE-BLOCK으로 관리하고 중복 이미지를 만들지 않는다.
6. 10장/11장은 PPT 상세 부족을 숨기지 않는다.
7. PDMG는 NSIGHT 전체가 아니라 Application Runtime Reference다.
8. Source/Config 값은 AS-IS Evidence이지 TO-BE 자동 확정값이 아니다.
9. 모든 그림 작성 전에 PPT 원본 구조를 Text Architecture로 재현한다.
10. Inventory/Mapping/Traceability를 그림과 함께 작성한다.
11. 화살표에는 실제 관계 의미를 붙인다.
12. UNKNOWN/GAP/OPEN Box를 삭제하지 않는다.
13. 숫자는 시점/근거 없이 생성하지 않는다.
14. 마지막에는 GAP/ADR/Next Chapter를 표시한다.

시각 스타일:
Cream/Ivory 배경,
Teal/Mint/Sky/Navy 중심,
Rounded Card,
Route Line,
Number Badge,
여행계획표처럼 읽기 쉽고
Enterprise Architecture 문서처럼 정교하게 표현한다.

이미지의 목적은 PPT를 예쁘게 바꾸는 것이 아니라,
PPT의 Architecture 의도·구조·책임·연결을
정확하게 보존한 상태에서
Top-down으로 더 쉽게 읽히게 만드는 것이다.
```

---

# 24. 최종 Quality Gate

이미지를 생성하거나 장별 정의서를 작성하기 전에 아래를 확인한다.

## 24.1 PPT 정합성

```text
[ ] 공식 장 번호가 유지되었는가
[ ] 공식 절 번호가 유지되었는가
[ ] PPT 장표 제목이 사라지지 않았는가
[ ] 2장 Numbering Drift가 병기되었는가
[ ] 6장 삽입 Context를 잘못 2장으로 이동하지 않았는가
[ ] 7.2~7.4 Gap을 숨기지 않았는가
[ ] 8.5~8.9 XX를 임의 명명하지 않았는가
[ ] 8장 반복 블록을 중복 생성하지 않았는가
[ ] 10/11장 PPT Content Gap을 표시했는가
```

## 24.2 Architecture 정합성

```text
[ ] VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME 흐름이 보이는가
[ ] Application/Data/System Responsibility가 분리되었는가
[ ] Logical과 Physical이 섞이지 않았는가
[ ] Mechanism과 Runtime이 섞이지 않았는가
[ ] Interface 목적별 방식이 분리되었는가
[ ] RDW/ADW, CDC/ETL이 구분되었는가
[ ] Framework 책임과 업무 책임이 구분되었는가
```

## 24.3 Evidence 정합성

```text
[ ] PPT-TOC / PPT-BODY를 구분했는가
[ ] AS-IS / TO-BE를 구분했는가
[ ] Source를 Target으로 자동 승격하지 않았는가
[ ] 근거 없는 숫자를 생성하지 않았는가
[ ] GAP/OPEN/UNKNOWN을 삭제하지 않았는가
```

## 24.4 시각화 정합성

```text
[ ] 원본 Text Architecture를 먼저 만들었는가
[ ] 박스의 책임이 명확한가
[ ] 화살표 의미가 명확한가
[ ] 표/Inventory가 그림과 일치하는가
[ ] 한 이미지에 지나치게 많은 정보를 넣지 않았는가
[ ] PPT 원본 용어가 최대한 보존되었는가
```

---

# 25. 최종 완성 모습

최종 결과는 다음 두 축이 동시에 보이면 성공이다.

```text
[공식 PPT 구조]
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 → 10 → 11
```

그리고:

```text
[Architecture Reading Route]
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

즉:

```text
PPT의 산출물 구조를 깨지 않으면서
Architecture의 Top-down 의미를 더 선명하게 만든다.
```

이것이 TYPE2 / PPT 정합본의 최종 목적이다.
