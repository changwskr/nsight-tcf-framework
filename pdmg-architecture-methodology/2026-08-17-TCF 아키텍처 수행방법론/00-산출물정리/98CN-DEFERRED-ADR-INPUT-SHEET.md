# Deferred ADR Input Sheet — 5건

> 아래 5건은 현재 자료만으로 최종 승인하지 않는다. 필요한 Owner 입력 또는 Runtime Evidence를 채운 뒤 다시 Board에 상정한다.

## ADR-SES-001 — Session Strategy
- Readiness: `NEEDS_OWNER_INPUT_AND_RUNTIME`
- 현재 권고: 현재 자료만으로 최종안을 확정하지 않는다. 센터내 Sticky+DeltaManager와 외부 Session Store를 동일 기준으로 비교하고, 센터장애 시 재로그인 허용정책을 먼저 승인한다.
- 승인주체: Application Architect + Ops Owner + Security
- 필요한 입력: Session idle 정책 60/90분 승인, 센터장애 재로그인 허용정책
- 필요한 Runtime: RUN-SESSION, RUN-CF
- Owner 입력: 
- Board Decision: `DEFER` / `APPROVE` / `REJECT`
- Approver / Date / Evidence Ref: 

## ADR-HA-001 — AP HA Topology
- Readiness: `RUNTIME_DEPENDENT`
- 현재 권고: 2+2, 3+3, 8Core Scale-Out 중 최종 토폴로지는 Runtime Approved Capacity와 N-1/Center Failure 결과 후 결정한다.
- 승인주체: Infra Architect + Performance Lead + Ops Owner
- 필요한 입력: ADR-PERF-001 runtime value
- 필요한 Runtime: RUN-P1200, RUN-N1, RUN-CF, RUN-ROLLING
- Owner 입력: 
- Board Decision: `DEFER` / `APPROVE` / `REJECT`
- Approver / Date / Evidence Ref: 

## ADR-DR-001 — Center Failure Session/Data Continuity
- Readiness: `NEEDS_OWNER_INPUT_AND_RUNTIME`
- 현재 권고: 센터 장애 시 세션 유지 여부와 재로그인 허용정책을 업무정책으로 먼저 결정하고, 데이터 일관성/복구 방식과 분리해 승인한다.
- 승인주체: Business Owner + Ops Owner + Architecture
- 필요한 입력: ADR-SES-001, service continuity policy
- 필요한 Runtime: RUN-SESSION, RUN-CF
- Owner 입력: 
- Board Decision: `DEFER` / `APPROVE` / `REJECT`
- Approver / Date / Evidence Ref: 

## ADR-DR-002 — Service RTO/RPO Classes
- Readiness: `NEEDS_OWNER_INPUT`
- 현재 권고: 전사 전략의 RTO 참조값을 시스템 승인값으로 자동 승격하지 않고 서비스 등급별 RTO/RPO를 명시 승인한다.
- 승인주체: Business Owner + Ops Owner + Data Owner
- 필요한 입력: business criticality classes
- 필요한 Runtime: RUN-CF
- Owner 입력: 
- Board Decision: `DEFER` / `APPROVE` / `REJECT`
- Approver / Date / Evidence Ref: 

## ADR-PERF-001 — Runtime Approved VM Capacity
- Readiness: `RUNTIME_DEPENDENT`
- 현재 권고: 500 TPS는 Legacy/Conservative, 855 TPS는 Working으로 유지하고 실제 승인값은 P600/P1200/S1800/N1/Hikari 결과로 확정한다.
- 승인주체: Performance Lead + Infra Architect + DBA
- 필요한 입력: performance environment identity
- 필요한 Runtime: RUN-P600, RUN-P1200, RUN-S1800, RUN-HIKARI, RUN-N1
- Owner 입력: 
- Board Decision: `DEFER` / `APPROVE` / `REJECT`
- Approver / Date / Evidence Ref: 

