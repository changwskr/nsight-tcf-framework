# Rule / Algorithm 설계

- 상태: BASELINE PRINCIPLE / FORMULA PROPOSED
- 원칙: 정량 계산은 AI가 임의 수행하지 않는다.

---

## 1. 역할 분리

```text
Program Algorithm
       │
       ├─ 계산
       ├─ 정규화
       ├─ Trend
       └─ Score
       │
       ▼
Rule Engine
       │
       ├─ Threshold
       ├─ 정책
       ├─ 위험감지
       └─ Adjustment Candidate
       │
       ▼
AI
       ├─ 해석
       ├─ 설명
       ├─ 코칭 문장
       ├─ 패턴 요약
       └─ 대안 제시
```

---

## 2. Body Algorithm 후보

- BMI
- Weight Change
- Rolling Weight Average
- Body Fat Change
- Muscle Mass Change
- Circumference Trend
- Goal Progress %

예:

```text
Weight Change %
= (Current Weight - Baseline Weight) / Baseline Weight × 100
```

공식과 Window는 최종 Algorithm Specification에서 확정한다.

---

## 3. Energy / Nutrition Algorithm 후보

- BMR
- TDEE
- Activity Factor
- Target Calories
- Protein Target
- Carbohydrate Target
- Fat Target
- Calorie Compliance
- Protein Compliance
- Weight Trend Adjustment

중요:

개발 설계서의 핵심은 최초 계산값을 고정하지 않고
실제 체중/수행/준수율을 이용해 재조정하는 것이다.

---

## 4. Workout Algorithm 후보

### 기본 Volume

```text
Volume = Weight × Reps
```

세션/주간 집계:

```text
Session Volume = Σ Set Volume
Weekly Volume  = Σ Session Volume
```

필요 분석:

- Volume
- Frequency
- Intensity
- PR
- Compliance
- RPE Trend
- RIR Trend
- Performance Trend

---

## 5. Workout Adjustment Candidate

출력 후보:

```text
KEEP
INCREASE_LOAD
DECREASE_LOAD
INCREASE_VOLUME
DECREASE_VOLUME
DELOAD
REST
EXERCISE_CHANGE
```

입력 후보:

```text
Performance
+ RPE/RIR
+ Volume Change
+ Sleep
+ Fatigue
+ Soreness
+ Compliance
+ Goal
```

---

## 6. Recovery / Readiness

입력 후보:

```text
Sleep Duration
Sleep Satisfaction
Fatigue
Soreness
Stress
Recent Training Load
Recent Performance
```

출력:

```text
Readiness Score
Recovery State
Training Caution Level
```

정확한 가중치와 Threshold는 별도 Specification이 필요하다.

---

## 7. Plateau Detection

정체 판단은 단일 기록이 아니라 Window 기반으로 한다.

후보 신호:

- 일정 기간 Load 증가 없음
- Reps 개선 없음
- Volume은 증가하지만 Performance 정체
- 높은 RPE 지속
- Compliance가 충분한 상태에서 성과 정체

정체 기간과 최소 표본 수는 확정 필요.

---

## 8. Explainable Evidence 생성

AI에 전달할 Evidence는 Rule/Algorithm 결과에서 구조화한다.

예:

```json
{
  "type": "RECOVERY_RISK",
  "signals": [
    {"metric": "sleepAvg5d", "changePct": -17},
    {"metric": "lowerBodyVolume7d", "changePct": 23}
  ],
  "candidateAction": "DECREASE_VOLUME",
  "suggestedRangePct": "10-15"
}
```

AI는 이 Evidence를 설명하되 숫자를 새로 창작하지 않는다.

---

## 9. 테스트 원칙

Algorithm은 반드시 독립 단위 테스트가 가능해야 한다.

```text
Given
Known Inputs

When
Calculator / Rule 실행

Then
Deterministic Result
```

AI Evaluation Test와 Algorithm Unit Test를 분리한다.
