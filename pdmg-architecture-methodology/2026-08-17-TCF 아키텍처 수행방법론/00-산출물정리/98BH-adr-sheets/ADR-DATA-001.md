# ADR-DATA-001 — RDW/ADW Ownership / Read-Write Boundary

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Data Architecture  
> Required Approver Role: Data Architect + Domain Owners

## 권고안

RDW는 온라인/Near Real-time, ADW는 분석/대량조회 책임을 기본으로 하고 Domain/Table/View Owner 및 Read/Write Matrix를 SoT로 관리한다.

## 대안

- A. Explicit ownership matrix (recommended)
- B. Shared/unowned data access

## 근거

논리 원칙은 존재하지만 전수 Owner/Read-Write Matrix가 부족하다.

## 결과 / Trade-off

- 직접 cross-domain update/DB link를 통제할 수 있다.

## 승인 전 선행조건

- Data Catalog

## Runtime Evidence

- Runtime Run 직접 의존 없음

## Closure Criteria

- Domain/Table/View owner catalog complete
- read-write matrix approved

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
