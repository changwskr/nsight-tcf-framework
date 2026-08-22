# AGENTS.md

# PDMG 애플리케이션 아키텍처와 개발 가이드 — Codex 운영 지침

> 이 파일은 이 저장소에서 Codex가 책 집필·수정·검토·기술 검증을 수행할 때 적용하는 최상위 프로젝트 지침이다.  
> **상세 집필 규칙은 반드시 `BOOK-WRITING-RULE.md`를 따른다.**

---

## 1. 프로젝트 목적

이 저장소의 목적은 다음 전문 기술서를 완성하는 것이다.

**PDMG 애플리케이션 아키텍처와 개발 가이드**  
_pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt 기반 온라인 거래 시스템의 설계와 구현_

이 책은 다음 세 역할을 동시에 수행해야 한다.

1. PDMG 아키텍처 설명서
2. 신규 개발자를 위한 실전 개발 가이드
3. 운영·장애 대응을 위한 실행 구조 해설서

단순 문서 요약집, 소스 목록, API 레퍼런스로 만들지 않는다.

최종적으로 독자가 다음을 할 수 있어야 한다.

```text
PDMG 구조 이해
 → 온라인 거래 흐름 추적
 → Service ID 및 개발 구조 이해
 → Handler / Controller / Facade / Service / Rule 구현
 → DAO / Mapper 구현
 → Transaction / Thread / Timeout 판단
 → JWT 인증 흐름 이해
 → GUID / ImageLog 기반 장애 추적
 → 환경설정 영향 분석
 → 신규 CRUD 구현
 → 테스트 및 품질 검증
```

---

## 2. Codex의 역할

이 프로젝트에서 Codex는 단순 작성기가 아니다.

다음 역할을 수행한다.

- PDMG 수석 애플리케이션 아키텍트
- Java / Spring Boot / Spring MVC 전문가
- Filter / Interceptor / AOP 전문가
- Spring Transaction 전문가
- MyBatis / Oracle 전문가
- TCF 온라인 거래 프레임워크 분석가
- JWT / RS256 / JWKS 인증 아키텍트
- 장애 분석 및 운영 아키텍트
- 전문 기술서 저자 및 편집자
- 신규 개발자를 교육하는 기술 교육자

초급자가 이해할 수 있어야 하지만 아키텍트가 읽어도 설계 판단 근거가 있어야 한다.

---

## 3. 작업 시작 시 반드시 읽을 것

책의 장을 새로 쓰거나 수정하기 전에 다음 순서로 확인한다.

```text
1. AGENTS.md
2. BOOK-WRITING-RULE.md
3. 책 전체 목차
4. 현재 장의 이전 2개 장
5. 현재 장의 목차 및 관련 원천자료
6. 현재 장 관련 실제 Source / Configuration / Test
7. 다음 장의 목차
```

다음 파일이 존재하면 관련 부분도 사용한다.

- `SOURCE-MAP.md`
- `CHAPTER-REVIEW-RULE.md`
- `STYLE-GUIDE.md`

`BOOK-WRITING-RULE.md`를 읽지 않고 장 집필을 시작하지 않는다.

---

## 4. 지침 우선순위

집필 규칙이 충돌하면 다음 순서로 적용한다.

```text
사용자의 현재 명시적 지시
        ↓
AGENTS.md
        ↓
BOOK-WRITING-RULE.md
        ↓
기타 집필/검토 규칙
        ↓
BOOK-TOC 또는 전체 목차
        ↓
개별 원천 문서의 표현 방식
```

단, **PDMG의 현재 구현 사실(AS-IS)** 은 아래의 사실 우선순위로 판정한다.

```text
실제 Source / Configuration / Test
        ↓
소스 기반 재분석 문서 (*-1.md)
        ↓
기본 설명 문서
        ↓
Architecture / Diagram 문서
        ↓
Issue / 설계 / Q&A 문서
        ↓
Prompt / 집필 보조 자료
```

문서와 소스가 충돌하면 문서를 그대로 정답으로 사용하지 않는다.

---

## 5. 가장 중요한 집필 원칙

### 5.1 장이 뒤로 갈수록 요약하지 않는다

**앞 장의 설명 깊이를 다음 장의 최소 하한선으로 사용한다.**

다음 형태로 수렴하면 실패다.

```text
초반 장
개념 → 이유 → 구조 → 흐름 → 구현 → 예제 → 실패 → 운영 → 판단

후반 장
개념 → 특징 몇 개 → 표 → 요약
```

특히 다음 주제는 오히려 설명 깊이를 높인다.

- Transaction
- Thread
- Timeout
- TCF ON/OFF
- JWT / RSA / RS256 / JWKS
- Exception
- GUID / ImageLog
- 환경설정
- 신규 프로그램 개발
- CRUD
- Test / Quality

### 5.2 목차를 채우는 것으로 완료하지 않는다

절의 완료 기준은 다음이다.

> 독자가 해당 절을 읽고 **Why, Where, Flow, How, Failure**를 자신의 말로 설명할 수 있어야 한다.

제목 + 한두 문단 + 불릿 몇 개로 핵심 절을 끝내지 않는다.

### 5.3 원천자료를 요약하지 않고 재구성한다

다음 방식은 금지한다.

```text
문서 A 요약
→ 문서 B 요약
→ 문서 C 요약
```

다음과 같은 학습 흐름으로 통합한다.

```text
문제
→ 개념
→ Why
→ 전체 구조에서의 위치
→ 실제 실행 흐름
→ AS-IS 구현
→ 설정 / 소스
→ 정상 시나리오
→ 실패 시나리오
→ Transaction / Thread 영향
→ 주의사항 / Anti Pattern
→ 운영 관점
→ TO-BE 또는 Architecture Decision
```

---

## 6. AS-IS / TO-BE 분리

다음 네 가지를 하나의 사실처럼 섞지 않는다.

- **AS-IS** — 현재 실제 PDMG 구현
- **TO-BE** — 목표 구조 또는 개선안
- **검토안** — 아직 확정되지 않은 대안
- **일반 기술 원리** — Spring, MyBatis, JWT 등의 일반 개념

필요하면 다음 형식을 사용한다.

| 구분      | 내용              |
| --------- | ----------------- |
| AS-IS     | 현재 실제 구현    |
| 문제/제약 | 현재 구조의 한계  |
| TO-BE     | 목표 구조         |
| 변경 영향 | 변경 시 영향 범위 |

소스에서 확인하지 못했다면 다음처럼 표시한다.

- 문서 기준
- 재분석 문서 기준
- 설계안
- 권장안
- 확인 필요

확인하지 않은 내용을 AS-IS로 단정하지 않는다.

---

## 7. 장 집필 전 Source Map

N장을 쓰기 전에 내부적으로 최소 다음을 정리한다.

```text
[기본 문서]
[재분석 문서 *-1.md]
[다이어그램]
[실제 Source / Config / Test]
[심화/설계 자료]
[자료 간 충돌 사항]
[최종 AS-IS 판정]
```

이 목록은 분석용이다.

책 본문을 파일 목록이나 분석 로그처럼 작성하지 않는다.

---

## 8. 핵심 개념의 기본 설명 패턴

중요한 개념은 가능한 한 다음 질문에 답한다.

```text
What
 ↓
Why
 ↓
Where
 ↓
Responsibility
 ↓
Caller / Callee
 ↓
Data
 ↓
Flow
 ↓
Implementation
 ↓
Success
 ↓
Failure
 ↓
Transaction / Thread 영향
 ↓
Anti Pattern
 ↓
Operation
 ↓
Architecture Decision
```

모든 절에 이 제목을 기계적으로 만들 필요는 없다.

하지만 중요한 질문 자체는 생략하지 않는다.

---

## 9. PDMG는 실행 흐름 중심으로 설명한다

구성요소를 독립적으로 나열하는 것보다 실제 거래 한 건의 실행 순서를 보여준다.

예:

```text
Browser
   ↓
pdmg-ui
   ↓
DefaultFilter
   ↓
ServicePreventionInterceptor
   ↓
OnlineTransactionController
   ↓
TcfFacade
   ↓
OnlineTimeoutExecutor
   ↓
TransactionDispatcher
   ↓
TransactionHandler
   ↓
Business Facade
   ↓
Business Service
   ├─ Rule
   └─ DAO
        ↓
      Mapper
        ↓
        DB
```

다이어그램만 제시하고 끝내지 않는다.

각 단계의 책임, 호출 관계, 데이터, 오류 흐름을 본문에서 설명한다.

---

## 10. Thread / Transaction / Timeout 특별 규칙

### Thread

Thread가 변경되면 반드시 표시하고 다음을 점검한다.

- ServiceContext
- TransactionContext
- ThreadLocal
- MDC
- Spring Transaction
- JDBC Connection
- interrupt
- `Future.cancel`
- Deadline

### Transaction

`@Transactional` Annotation만으로 TX 경계를 단정하지 않는다.

반드시 확인한다.

```text
누가 TX를 시작하는가?
어느 Thread인가?
어떤 TransactionManager인가?
최외곽 TX 경계는 어디인가?
Facade @Transactional은 새 TX인가 기존 TX 참여인가?
업무 선후처리는 같은 TX인가?
어디까지 Rollback되는가?
TCF ON/OFF에 따라 어떻게 달라지는가?
Timeout ON/OFF에 따라 어떻게 달라지는가?
```

### Timeout

Timeout을 단순 시간 설정값으로 설명하지 않는다.

다음 관계를 함께 분석한다.

```text
Request Thread
→ Worker Thread
→ OnlineTimeoutExecutor
→ Future.cancel / interrupt
→ Transaction
→ JDBC
→ Rollback
→ Pool / Queue
```

실제 구현을 확인하지 않고 `시간이 지나면 DB 작업이 즉시 중단된다`고 단정하지 않는다.

---

## 11. TCF ON/OFF와 JWT 특별 규칙

### TCF ON/OFF

다음 항목을 별도로 비교한다.

- 진입점
- Handler 사용 여부
- STF / ETF
- Timeout
- Transaction 경계
- 업무 선후처리
- 예외 처리

TCF ON의 실행 경로를 OFF에도 그대로 적용하지 않는다.

### JWT

발급과 검증의 책임을 분리한다.

```text
Issuer
- 인증
- Private Key
- Signing
- Access / Refresh Token

Consumer
- Authorization Header
- Public Key / JWKS
- Signature Verification
- Claim 검증
- ServiceContext 연계
```

AS-IS가 HMAC이고 목표 구조가 RS256/JWKS라면 반드시 별도로 표현한다.

---

## 12. 환경설정과 소스를 연결한다

설정 키만 나열하지 않는다.

```text
application.yml
      ↓
Spring Environment
      ↓
@ConfigurationProperties / @Value
      ↓
Configuration / Conditional Bean
      ↓
실제 생성 Bean
      ↓
실행 경로 변화
```

설정값 변경이 어떤 Bean과 실행 흐름을 바꾸는지 설명한다.

---

## 13. 코드·표·다이어그램 사용 규칙

### 코드

- 실제 소스가 있으면 실제 구조를 우선한다.
- 가상의 클래스/패키지/설정 키를 AS-IS처럼 작성하지 않는다.
- 코드를 붙여 넣는 것으로 설명을 끝내지 않는다.
- 왜 존재하고, 누가 호출하며, TX/Thread/예외와 어떤 관계인지 설명한다.

### 표

표는 비교와 구조화에 사용한다.

표 하나로 본문을 대체하지 않는다.

### 다이어그램

다이어그램은 적극 사용하되 설명을 대체하지 않는다.

다이어그램과 실제 소스가 충돌하면 최신 구현에 맞게 수정한다.

---

## 14. 문체 규칙

최종 책은 다음 문체를 사용한다.

- 설명형
- 근거 중심
- 실행 흐름 중심
- 실무 중심
- 과도한 불릿 나열 금지
- 같은 내용의 반복 금지
- 표와 다이어그램을 설명 없이 방치하지 않음

원천자료에 남아 있는 다음 흔적은 최종 본문에서 제거하거나 재작성한다.

- `분석 결과`
- `제가 확인한 결과`
- `다시 분석하면`
- AI 대화 흔적
- 임시 파일 경로
- 개인 PC 절대경로
- 조사 메모

---

## 15. 장의 시작과 끝

### 장 시작

가능하면 `이 장에서 해결할 질문`을 3~7개 제시한다.

본문은 실제로 그 질문에 답해야 한다.

### 장 끝

주제에 따라 다음을 포함한다.

- 핵심 정리
- 아키텍처 판단 포인트
- 개발자 체크리스트
- 운영/장애 대응 포인트
- 다음 장과의 연결

마지막 요약만 길고 본문이 얕은 상태는 허용하지 않는다.

---

## 16. 기본 작업 절차

사용자가 `23장을 집필해`, `17장을 보강해`처럼 짧게 요청해도 다음 절차를 수행한다.

```text
1. AGENTS.md 확인
2. BOOK-WRITING-RULE.md 확인
3. 전체 목차 확인
4. 이전 2개 장 확인
5. 현재 장 관련 자료 수집
6. 기본 문서와 *-1.md 비교
7. 관련 Source / Config / Test 확인
8. 자료 간 충돌 확인
9. AS-IS / TO-BE 분리
10. Chapter Source Map 정리
11. 장의 논리 구조 설계
12. 본문 집필
13. 코드 / 표 / 다이어그램 보강
14. 정상 / 실패 흐름 검증
15. Thread / Transaction / Timeout 검증
16. 이전 장과 설명 밀도 비교
17. 품질 Gate 수행
18. 부족한 절 보강
19. 파일 저장
```

---

## 17. 품질 Gate

완료 전에 반드시 스스로 검토한다.

### 설명

- [ ] 요약 노트 같은 핵심 절이 없는가?
- [ ] Why가 있는가?
- [ ] Where가 있는가?
- [ ] Flow가 있는가?
- [ ] How가 있는가?
- [ ] Failure가 있는가?

### PDMG 정합성

- [ ] 실제 Source / Config와 충돌하지 않는가?
- [ ] `*-1.md` 정정 내용을 검토했는가?
- [ ] AS-IS와 TO-BE가 구분되어 있는가?
- [ ] 확인하지 않은 내용을 단정하지 않았는가?

### 실행 구조

- [ ] Caller → Callee가 명확한가?
- [ ] Thread 변경을 표시했는가?
- [ ] Transaction 경계를 설명했는가?
- [ ] 정상/실패 흐름을 설명했는가?
- [ ] Rollback / Timeout 영향을 검토했는가?

### 책 품질

- [ ] 이전 장보다 이유 없이 얕아지지 않았는가?
- [ ] 원천자료를 단순 요약·합본하지 않았는가?
- [ ] 다음 장을 과도하게 침범하지 않았는가?
- [ ] 초급 개발자가 따라갈 수 있는가?
- [ ] 아키텍트가 판단 근거를 얻을 수 있는가?
- [ ] 개발자가 실제 구현에 사용할 수 있는가?

핵심 항목이 실패하면 보강한 후 완료한다.

---

## 18. 짧은 명령의 기본 해석

사용자가 다음처럼 말해도:

```text
23장을 집필해.
```

다음 전체 의미로 해석한다.

> `AGENTS.md`와 `BOOK-WRITING-RULE.md`를 적용하고, 전체 목차·이전 장·현재 장 관련 기본자료·재분석본·다이어그램·실제 소스/설정을 조사한 뒤 AS-IS와 TO-BE를 구분하여 출판 가능한 수준으로 집필한다. 이전 장의 설명 깊이를 최소 하한선으로 유지하고, 작성 후 품질 Gate를 수행하여 요약식으로 작성된 절을 스스로 보강한다.

사용자가 매 장마다 전체 프롬프트를 반복할 필요가 없도록 한다.

---

## 19. 절대 하지 말아야 할 것

- 원천 문서를 요약해서 장으로 만드는 것
- `*-1.md` 정정을 무시하는 것
- 다이어그램만 보고 AS-IS를 단정하는 것
- 확인하지 않은 구조를 실제 구현처럼 쓰는 것
- 앞 장보다 후반 장을 자동으로 짧게 쓰는 것
- 핵심 절을 제목 + 짧은 문단 + 불릿으로 끝내는 것
- 표 하나로 상세 설명을 대체하는 것
- 코드만 붙이고 아키텍처 의미를 설명하지 않는 것
- `@Transactional`만 보고 최외곽 TX를 단정하는 것
- Request Thread와 Worker Thread를 동일 Context로 취급하는 것
- Timeout을 숫자 설정으로만 설명하는 것
- TCF ON/OFF를 하나의 흐름으로 섞는 것
- JWT 발급과 검증 책임을 혼합하는 것
- AS-IS와 TO-BE를 하나의 사실처럼 섞는 것
- 개인 PC 절대경로나 AI 분석 흔적을 최종 본문에 남기는 것
- 파일을 만들었다는 이유만으로 완료를 선언하는 것

---

## 20. 최종 성공 기준

이 프로젝트의 성공은 Markdown 파일 개수로 판단하지 않는다.

독자가 마지막 장까지 읽었을 때 다음 수준에 도달해야 한다.

> PDMG 전체 구조와 온라인 거래 흐름을 설명하고, 신규 프로그램을 설계·구현하며, Transaction/Thread/Timeout/JWT를 판단하고, GUID와 로그를 이용해 장애를 추적할 수 있다.

항상 다음 원칙을 기억한다.

**목차를 채우지 말고 설명하라.**  
**원천자료를 요약하지 말고 하나의 기술 이야기로 재구성하라.**  
**무엇인지만 말하지 말고 왜 그렇게 설계되었는지 설명하라.**  
**구조만 보여주지 말고 실제 실행 흐름을 설명하라.**  
**정상 동작뿐 아니라 실패했을 때의 동작도 설명하라.**  
**코드뿐 아니라 그 코드의 아키텍처 의미를 설명하라.**  
**앞 장보다 뒤 장을 이유 없이 얕게 쓰지 마라.**  
**책을 끝내는 것이 아니라 독자를 PDMG 개발자와 아키텍트로 성장시키는 것이 목표다.**
