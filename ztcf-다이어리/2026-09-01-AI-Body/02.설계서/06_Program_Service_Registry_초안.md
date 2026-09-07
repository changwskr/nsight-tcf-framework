# BY Program / Service Registry 초안

- 상태: PROPOSED
- 목적: 요구사항 → 화면 → ServiceId → Java → SQL 추적성 기준
- 주의: 프로그램 번호와 세부 Service 목록은 구현 전 최종 승인 필요

---

## 1. 번호 원칙

현재 초안은 도메인 내부에서 다음 간격을 사용한다.

```text
1000  핵심 기본 기능
1100  두 번째 주요 기능
1200  분석/보조 기능
1300+ 확장
```

이는 관리 편의성을 위한 초안이며 PDMG Runtime 규칙 자체는 아니다.

---

## 2. User

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyu1000` | Profile | `S0`, `C0`, `U0` |
| `mgbyu1100` | Goal | `S0`, `C0`, `U0`, `D0` |
| `mgbyu1200` | Consent | `S0`, `C0`, `U0` |

예:

```text
mgbyu1000S0 Profile 조회
mgbyu1000U0 Profile 수정
mgbyu1100S0 Goal 조회
mgbyu1100C0 Goal 등록
```

---

## 3. Body

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyb1000` | Body Measurement | `S0`, `C0` |
| `mgbyb1100` | Body Timeline | `S0` |
| `mgbyb1200` | Body Photo | `S0`, `C0`, `D0` |

Body Measurement는 원칙적으로 Append 중심이므로 일반 수정 거래는 최소화한다.

---

## 4. Assessment

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbya1000` | Initial Assessment | `S0`, `A0` |
| `mgbya1100` | Body Status | `S0`, `A0` |
| `mgbya1200` | Readiness Assessment | `S0`, `A0` |

---

## 5. Workout

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyw1000` | Workout Program | `S0`, `C0`, `U0`, `D0` |
| `mgbyw1100` | Workout Session/Log | `S0`, `C0`, `U0` |
| `mgbyw1200` | Workout Analytics | `S0`, `R0` |
| `mgbyw1300` | Workout Adjustment | `S0`, `A0` |

---

## 6. Nutrition

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyn1000` | Nutrition Plan | `S0`, `C0`, `U0` |
| `mgbyn1100` | Meal/Nutrition Log | `S0`, `C0`, `U0`, `D0` |
| `mgbyn1200` | Nutrition Analytics | `S0`, `R0` |
| `mgbyn1300` | Nutrition Adjustment | `S0`, `A0` |

---

## 7. Recovery

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyr1000` | Daily Check-in | `S0`, `C0` |
| `mgbyr1100` | Sleep/Recovery | `S0`, `C0` |
| `mgbyr1200` | Readiness Trend | `S0`, `R0` |

---

## 8. Coaching

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyc1000` | Daily Coaching | `S0`, `A0` |
| `mgbyc1100` | Weekly Review | `S0`, `A0`, `R0` |
| `mgbyc1200` | Coaching Adjustment | `S0`, `A0` |

---

## 9. AI

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyi1000` | AI Insight | `S0`, `A0` |
| `mgbyi1100` | AI Context / Explain | `S0`, `A0` |
| `mgbyi1200` | AI Evaluation / Feedback | `S0`, `C0` |

---

## 10. Progress

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyp1000` | Progress Dashboard | `S0` |
| `mgbyp1100` | Body Trend | `S0`, `R0` |
| `mgbyp1200` | Transformation | `S0`, `R0` |

---

## 11. Human Coach

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyh1000` | Coach Member Dashboard | `S0` |
| `mgbyh1100` | Attention Queue | `S0` |
| `mgbyh1200` | Coach Feedback | `S0`, `C0` |
| `mgbyh1300` | AI Review | `S0`, `A0` |

---

## 12. Knowledge

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyk1000` | Knowledge Search | `S0` |
| `mgbyk1100` | Knowledge Management | `S0`, `C0`, `U0`, `D0` |

---

## 13. Notification

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyt1000` | Notification Setting | `S0`, `U0` |
| `mgbyt1100` | Notification History | `S0` |
| `mgbyt1200` | Notification Dispatch | `A0` |

---

## 14. External Integration

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbye1000` | Integration Account | `S0`, `C0`, `D0` |
| `mgbye1100` | Health Sync | `S0`, `A0` |
| `mgbye1200` | Device Data | `S0`, `A0` |

---

## 15. Management / Admin

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbym1000` | User/Admin Management | `S0`, `U0` |
| `mgbym1100` | Policy/Code Management | `S0`, `C0`, `U0`, `D0` |
| `mgbym1200` | AI/Service Operation | `S0`, `U0` |

---

## 16. 최종 Registry에 추가해야 할 Column

```text
Requirement ID
Domain Code
Program ID
Program Name
Service ID
Transaction Type
Service Name
Package
Handler
Facade
Service
Rule/Algorithm
DAO
Mapper
SQL ID
UI Path
Input DTO
Output DTO
DB Table
MVP
Status
Owner
Test ID
```
