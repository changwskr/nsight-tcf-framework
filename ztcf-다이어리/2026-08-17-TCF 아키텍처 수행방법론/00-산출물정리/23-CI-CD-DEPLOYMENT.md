# 23. NSIGHT CI/CD / Deployment Architecture

## 1. 목적

NSIGHT CI/CD는 소스 빌드 자동화만을 의미하지 않는다. **Source → Build → Test → Artifact → 승인 → 배포 → Health → Runtime Validation → Rollback**을 하나의 변경통제 체계로 관리한다.

## 2. 현재 도구 Baseline

전략/운영자료에서 확인되는 역할:

| 영역 | 도구/구성 | 상태 |
|---|---|---|
| Source Control | GitLab | CONFIRMED |
| Dev CI/CD | GitLab Runner / GitRunner | CONFIRMED |
| Artifact/Library | Nexus 표기 | CURRENT/AS-IS |
| Prod Deployment | eCAMS | STRATEGY BASELINE |
| Runtime | Apache / Tomcat / NH Cloud Framework | CURRENT |

세부 버전·파이프라인 구현·승인 단계는 Evidence 부족으로 UNKNOWN을 유지한다.

## 3. 표준 Pipeline

```text
Developer
  ↓
GitLab
  ↓
Build
  ↓
Unit Test
  ↓
Architecture Rule / Conformance
  ↓
Security / Quality Check
  ↓
Artifact Versioning
  ↓
Nexus / Repository
  ↓
DEV Deploy - GitLab Runner
  ↓
Integration / Performance / Regression
  ↓
Approval
  ↓
PROD Deploy - eCAMS
  ↓
Node Health / ServiceId Smoke Test
  ↓
Runtime Evidence
  ↓
Promote or Rollback
```

## 4. Artifact 원칙

모든 배포단위는 다음 정보를 가져야 한다.

- Application/WAR ID
- Artifact Version
- Git Commit/Tag
- Build ID
- Configuration Version
- DB Change Version
- Dependency Version
- Deployment Target JVM/Host
- Approval Record
- Rollback Artifact

## 5. Rolling Deployment

Tomcat JVM Peer HA를 이용하여 한 번에 전체 노드를 교체하지 않는다.

```text
1. Node A Pool 제외
2. Active TX Drain
3. Deploy Artifact
4. JVM Restart / Health
5. ServiceId Smoke Test
6. Pool 복귀
7. Node B 반복
```

Rolling 중 남은 노드가 Peak Capacity를 감당할 수 있어야 한다. 따라서 Deployment는 G60 Capacity와 결합한다.

## 6. Residual Capacity Rule

```text
배포 중 Available Capacity
>= 운영 목표 Peak × Safety Factor
```

예: 2노드 중 1노드를 제외할 경우 1노드가 Peak 1,200 TPS를 단독 처리할 수 없다면 무중단 Rolling이라고 판정할 수 없다.

따라서 2+2/3+3/8Core Scale-Out ADR은 배포 아키텍처에도 직접 영향을 준다.

## 7. Configuration Management

배포 Artifact와 설정을 분리하되 Version 관계를 고정한다.

```text
Artifact Version
↔ application.yml/properties
↔ server.xml/setenv.sh
↔ Route Config
↔ DB Schema/SQL
↔ Secret/Key Version
```

운영 설정의 수동 변경은 Drift Detection 대상이다.

## 8. Rollback

Rollback은 WAR만 이전 버전으로 되돌리는 것으로 끝나지 않는다.

검증 대상:

- Artifact compatibility
- Configuration compatibility
- DB Schema backward compatibility
- Cache/Session compatibility
- JWT/Key compatibility
- Message Contract compatibility
- Migration 여부

DB 변경이 비가역이면 별도의 Roll-forward 전략이 필요하다.

## 9. Deployment Gate

배포 전 최소 확인:

```text
Build PASS
Unit/Contract PASS
Architecture Rule PASS
Security PASS
Config Snapshot
DB Change Review
Capacity Residual Check
Rollback Artifact 확보
Approval
```

배포 후:

```text
Process/JVM Health
ServiceId Smoke Test
Error Rate
p95
Thread/Hikari
DB Error
Log/ImageLog
Version 확인
```

## 10. 주요 GAP

| GAP ID | 내용 | 우선순위 |
|---|---|---:|
| CICD-G01 | GitLab→Runner→Nexus→eCAMS 실제 Pipeline Evidence 부족 | P0 |
| CICD-G02 | WAR↔JVM↔Host Deployment Target Catalog 미완성 | P0 |
| CICD-G03 | Rolling 시 Residual Capacity 승인기준 미완성 | P0 |
| CICD-G04 | Config/DB Change Versioning 규칙 미확정 | P0 |
| CICD-G05 | Automated Smoke/Conformance Gate 연결 미완성 | P0 |
| CICD-G06 | Rollback Runbook/DB Compatibility 미완성 | P0 |

## 11. Gate

현재 판정: **CONDITIONAL PASS**

PASS 조건:

- 실제 Pipeline/Approval Evidence
- Artifact/Config/DB Version Traceability
- Rolling Residual Capacity Test
- 자동 Smoke/Runtime Evidence
- Rollback Test
