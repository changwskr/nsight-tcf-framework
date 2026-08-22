# NSIGHT Requirement Traceability — G80 Draft

## 1. 목적

요구사항을 `Requirement → Architecture → Rule → Model → Source/Config → Test → Runtime Evidence → Gate`로 연결한다. 현재 RT-IA 요구사항군은 문서 수준 대응은 상당히 존재하지만 Test/Runtime Evidence 연결은 미완료다.

## 2. Traceability 상태

| Trace Link | 상태 | G80 판단 |
|---|---|---|
| Requirement → Architecture Domain | PARTIAL | RT-IA 요구사항을 Vision/Physical/HA/Dev/Data에 매핑 가능 |
| Requirement → Architecture Rule | PARTIAL | G80 Rule Registry 생성 |
| Rule → Model | PARTIAL | Source-extracted model 생성 |
| Model → Source | PARTIAL | ServiceId/Handler/Facade/Service/DAO 관계 일부 자동추출 |
| Source → Configuration | OPEN | 실제 운영 config snapshot 부족 |
| Configuration → Test | OPEN | Run별 config version evidence 부족 |
| Test → Runtime Evidence | OPEN | Mandatory Runs 미실행/미연결 |
| Runtime Evidence → Gate | POLICY READY | GOV-003으로 PASS 차단 정책 적용 |

## 3. 최우선 Vertical Slice

```text
Requirement 1건
 → Screen/Event
 → ServiceId
 → Handler
 → Facade
 → Service
 → DAO/Mapper/SQL/Table
 → WAR/JVM/Server
 → Timeout/Security Policy
 → Test Run
 → GUID+ServiceId Runtime Evidence
```

G80에서는 전체 전수보다 대표 ServiceId 1~3건의 Vertical Slice를 먼저 완전히 닫은 뒤 전수 확장하는 방식을 권장한다.
