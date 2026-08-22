# NSIGHT GAP Register — G80

| GAP ID | Domain | AS-IS | TO-BE | Impact | Priority | Action | Owner |
|---|---|---|---|---|---|---|---|
| GAP-001 | Closed Loop | Runtime Evidence Registry만 존재 | Mandatory runtime run 원본과 결과 연결 | Gate 증명 불가 | P0 | 성능/장애/Timeout/Trace Run 실행 및 보관 | Performance/Ops |
| GAP-002 | Security | Process-local JWT key | KMS/HSM Key SoT + versioned kid rotation | HA/재기동/Rotation 위험 | P0 | JWT Key 구현 변경 | Security |
| GAP-003 | Transaction | Facade TX + 일부 Service TX | 승인된 단일 TX Owner + 예외정책 | Rollback/Timeout 경계 불명확 | P0 | 4 Service Transaction 검토 | Application/Framework |
| GAP-004 | Timeout | 정적 정책/설계 존재 | Cancel/Rollback/Connection Return Runtime 증명 | Late Commit 가능성 | P0 | Fault Injection Test | Framework/DB |
| GAP-005 | Traceability | ServiceId→Code 일부 자동추출 | Requirement→Screen→ServiceId→DB→Server→Evidence 전수연결 | 영향분석/감사 한계 | P0 | Traceability Index 구축 | Architecture |
| GAP-006 | Physical | 71대 서버 인벤토리 존재 | Server→JVM→WAR→HA Peer 전수매핑 | 배포/장애 범위 불완전 | P0 | Runtime/Deployment inventory 연결 | Infra |
| GAP-007 | HA/DR | 운영/DR 일부 Pair와 후보구조 | 전체 Pair + RTO/RPO + Failover/Failback Evidence | DR 승인 불가 | P0 | DR Catalog 및 실행시험 | Infra/Ops |
| GAP-008 | Session | 60/90분, DeltaManager/JDBC 후보 혼재 | 최종 Session Strategy 및 Failover 기준 | 로그인/메모리/장애정책 불명확 | P0 | ADR-SES-001 + Test | Architecture/Ops |
| GAP-009 | Capacity | 500/855 TPS 등 Working 값 | Runtime Approved Capacity | AP 수량·N-1 산정 불확실 | P0 | P600/P1200/S1800/N1 Run | Performance |
| GAP-010 | Data | RDW/ADW 원칙 존재 | Domain/Table/View Owner + Read/Write Matrix | 데이터 책임 불명확 | P0 | Data Catalog | Data |
| GAP-011 | Integration | HTTP/JSON Client/Timeout 존재 | Deadline 전파 + S2S Auth + Route Registry | 장애전파/보안 위험 | P0 | Integration Contract Registry | Integration/Security |
| GAP-012 | Operations | OM/Logging 구조 정의 | 실제 Service Catalog/Metric/Alert/Runbook 폐쇄루프 | 운영 자동통제 미완성 | P0 | OM Runtime Catalog | Ops |
| GAP-013 | Deployment | CI/CD 역할 정의 | 실제 Pipeline/Artifact/Config/Rollback Evidence | 릴리즈 재현성 부족 | P0 | Pipeline Evidence 연결 | DevOps |
| GAP-014 | Migration | Stage/Target 구조 정의 | Reconciliation + Go/No-Go + Rollback Evidence | Cutover 승인 위험 | P0 | Migration Gate | Data Migration |
| GAP-015 | Model | Partial source model 생성 | Schema 검증된 전체 Architecture Model SoT | 자동 Drift 탐지 제한 | P0 | Model Schema + missing edges 보강 | Architecture |
