# ADR-INFRA-001 — Tomcat JVM : Application Deployment Unit

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Infra / Application  
> Required Approver Role: Infra Architect + Application Architect

## 권고안

운영 기준은 WAS Server/VM, Tomcat JVM, Application/WAR을 별도 Entity로 관리하고 장애격리가 필요한 업무는 독립 JVM을 기본으로 한다. Multi-WAR은 명시적 예외로 관리한다.

## 대안

- A. Independent JVM per application/failure domain (recommended)
- B. Multi-WAR consolidated JVM

## 근거

개발 snapshot의 통합 Tomcat과 운영 물리모델을 혼동하면 장애/배포 영향도가 왜곡된다.

## 결과 / Trade-off

- 운영 인벤토리에 CATALINA_BASE/JVM/WAR 관계가 필요하다.

## 승인 전 선행조건

- production config evidence

## Runtime Evidence

- `RUN-N1`
- `RUN-ROLLING`

## Closure Criteria

- 71 server runtime mapping
- deployment/failure evidence

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
