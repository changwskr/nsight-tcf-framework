# AI Coaching Architecture

- 상태: BASELINE
- 핵심 원칙: AI는 Domain Logic을 대체하지 않는다.
- 처리 순서: Calculate → Rule → Analyze → Explain

---

## 1. AI 책임

AI가 담당한다.

- 데이터 해석
- 사용자 친화적 설명
- Daily Coaching
- Weekly Summary
- Pattern Summary
- 자연어 Q&A
- 대안 제시
- Coach 보조

AI가 담당하지 않는다.

- BMR/TDEE 확정 계산의 유일한 출처
- 운동 Volume 계산의 유일한 출처
- Readiness 숫자 임의 생성
- 의료 진단
- 의약품 처방
- 규칙을 무시한 운동/식단 강제 변경

---

## 2. AI Pipeline

```text
User / Scheduled Trigger
        ↓
Context Builder
        ↓
Personal Body Model
        ↓
Recent Body Timeline
        ↓
Relevant Workout/Nutrition/Recovery
        ↓
Rule / Algorithm Results
        ↓
Knowledge Retrieval
        ↓
AI Reasoning
        ↓
Safety Validator
        ↓
Response Formatter
        ↓
AIInsight
```

---

## 3. Context Builder

모든 사용자 데이터를 매 요청마다 LLM에 보내지 않는다.

Context Builder가 목적에 따라 최소 Context를 선택한다.

### Daily Coaching 예

```text
Today Check-in
Recent 7d Sleep
Recent Workout Load
Today Workout Plan
Today Nutrition Target
Current Goal
Recent Adjustment
```

### Weekly Review 예

```text
7d Body Trend
7d Workout
7d Nutrition
7d Recovery
Goal Progress
Compliance
Previous Recommendation
```

---

## 4. 구조화 Output

AI 응답은 문자열만 저장하지 않는다.

```json
{
  "recommendation": "...",
  "reason": "...",
  "evidence": [],
  "expectedEffect": "...",
  "caution": "...",
  "severity": "YELLOW",
  "actions": []
}
```

---

## 5. Severity

Coach Attention과 AI Insight에 활용할 수준 후보:

```text
GREEN
YELLOW
ORANGE
RED
```

정확한 기준은 Safety/Rule Specification에서 정의한다.

---

## 6. Human in the Loop

Coach 관리 회원:

```text
AI Recommendation
       ↓
Coach Attention Queue
       ↓
Coach Review
  ┌────┼────┐
  ▼    ▼    ▼
Approve Modify Reject
       ↓
Member
```

Coach 승인 대상의 범위는 정책으로 결정한다.

---

## 7. AI Failure Fallback

```text
AI 호출 성공
   → AI Coaching

AI Timeout / Error
   → Rule Based Coaching
   → AI 장애 상태 기록
   → 사용자 기록/조회는 정상 제공
```

---

## 8. Safety

### 반드시 차단/완화해야 하는 영역

- 의료진단
- 의약품 복용/중단 지시
- 위험한 극단적 식이
- 사용자 경험수준을 벗어난 과도한 운동
- 심각한 이상 상태를 Fitness 문제로 단정

### Escalation

```text
Fitness Scope
   → Coaching

Out of Scope / Risk
   → Safety Message
   → 전문 의료/전문가 상담 안내
```

---

## 9. AI Observability

기록 후보:

```text
provider
model
promptVersion
contextVersion
latencyMs
tokenUsage
resultStatus
fallbackUsed
safetyResult
userFeedback
coachDecision
```

개인 원문 데이터를 운영로그에 과도하게 남기지 않는다.

---

## 10. 결정 대기

- LLM Provider
- Model
- Prompt Versioning 저장방식
- Knowledge Retrieval 구현
- Vector Store 여부
- AI Timeout
- Retry
- 비용한도
- 개인정보 Masking 정책
- AI Evaluation Dataset
