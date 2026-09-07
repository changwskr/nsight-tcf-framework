# NSIGHT PDMG 아키텍처 정의서
# STORY + TEXT ARCHITECTURE + TOP-DOWN → DRILL-DOWN
# MASTER INDEX

> 기준 마스터 프롬프트: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 기준 상세목차: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 작성일: `2026-09-01`  
> 상태: `[WORKING INTEGRATED BASELINE]`

# 1. 전체 Story Master Map

```text
01 왜 다시 짓는가
 ↓
02 정보계 패러다임의 전환
 ↓
03 아키텍처 6단계 수립 방법론
 ↓
04 Big Picture
 ↓
05 논리 아키텍처
 ↓
06 물리 아키텍처
 ↓
07 DR 센터 활용 전략
 ↓
08 메커니즘
 ↓
09 런타임 서비스
 ↓
10 데이터플랫폼
 ↓
11 마케팅플랫폼
 ↓
12 BI 포탈
 ↓
13 표준화와 10년 지속 가능성
 ↓
HG90 Evidence-backed Architecture Baseline
```

# 2. 작성 방식

```text
각 장 전체 TEXT Architecture
 ↓
L0 STORY / LANDSCAPE
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
 ↓
NORMAL / FORBIDDEN
 ↓
PRIMARY / ALTERNATIVE
 ↓
PASS / GAP / ADR
 ↓
NEXT CHAPTER
```

# 3. 장별 현황

| 장 | 제목 | 파일 | Lines | TEXT Figures | Architecture | Current |
|---|---|---|---|---|---|---|
| 01 | 왜 다시 짓는가 | 01_왜_다시_짓는가.md | 1031 | 32 | CONDITIONAL PASS | PARTIAL / GAP |
| 02 | 정보계 패러다임의 전환 | 02_정보계_패러다임의_전환.md | 793 | 26 | PASS | PARTIAL |
| 03 | 아키텍처 6단계 수립 방법론 | 03_아키텍처_6단계_수립_방법론.md | 726 | 26 | PASS | PARTIAL |
| 04 | Big Picture | 04_Big_Picture.md | 831 | 28 | PASS | CONDITIONAL / PARTIAL |
| 05 | 논리 아키텍처 | 05_논리_아키텍처.md | 922 | 32 | PASS | CONDITIONAL / PARTIAL |
| 06 | 물리 아키텍처 | 06_물리_아키텍처.md | 895 | 30 | PASS | PARTIAL / OPEN |
| 07 | DR 센터 활용 전략 | 07_DR_센터_활용_전략.md | 826 | 31 | PASS | OPEN / CONDITIONAL |
| 08 | 메커니즘 | 08_메커니즘.md | 936 | 34 | PASS | PARTIAL / GAP |
| 09 | 런타임 서비스 | 09_런타임_서비스.md | 956 | 34 | PASS | PARTIAL / CONDITIONAL |
| 10 | 데이터플랫폼 | 10_데이터플랫폼.md | 881 | 33 | PASS | PARTIAL / CONDITIONAL |
| 11 | 마케팅플랫폼 | 11_마케팅플랫폼.md | 795 | 30 | CONDITIONAL PASS | PARTIAL / GAP |
| 12 | BI 포탈 | 12_BI_포탈.md | 788 | 30 | TARGET REFERENCE / CONDITIONAL PASS | N-A / OPEN |
| 13 | 표준화와 10년 지속 가능성 | 13_표준화와_10년_지속_가능성.md | 936 | 33 | CONDITIONAL PASS | PARTIAL / GAP |

# 4. Evidence 원칙

```text
PDMG
= Current / Source / Config / Runtime

NSIGHT
= Target / Alignment / Strategy Reference

Evidence Priority
Source/Config
 > Runtime/Deployment
 > PDMG Current Analysis
 > Approved Decision
 > Official Docs
 > Presentation
 > Historical Standard
 > General Knowledge
```

# 5. 최종 PASS 원칙

```text
Architecture Definition PASS
 ≠
Current Implementation PASS
 ≠
Final HG90 PASS

HG90
=
Critical GAP Closure
+ Source/Config Conformance
+ Security Integration
+ Performance/Failure Evidence
+ Deployment Trace
+ Runtime Evidence
+ DR/Restore Evidence
+ G80 Approval
```
