# ADR-DR-002 — Service RTO/RPO Classes

> Priority: **P0**  
> Status: **DRAFT**  
> Decision Readiness: **NEEDS_OWNER_INPUT**  
> Owner: Business / Ops / Data  
> Required Approver Role: Business Owner + Ops Owner + Data Owner

## 권고안

전사 전략의 RTO 참조값을 시스템 승인값으로 자동 승격하지 않고 서비스 등급별 RTO/RPO를 명시 승인한다.

## 대안

- A. Service-class based RTO/RPO (recommended)
- B. Single global RTO/RPO

## 근거

현재 30분 RTO는 전략 수준 참조이고 개별 시스템 RPO 승인값은 부족하다.

## 결과 / Trade-off

- DR 투자/복구 우선순위/데이터 보호 방식이 결정된다.

## 승인 전 선행조건

- business criticality classes

## Runtime Evidence

- `RUN-CF`

## Closure Criteria

- approved RTO/RPO inputs
- observed RTO/RPO within approved class

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
