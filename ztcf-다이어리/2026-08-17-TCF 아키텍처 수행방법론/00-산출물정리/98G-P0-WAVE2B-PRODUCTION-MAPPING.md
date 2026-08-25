# 98G. P0 Closure Wave 2B — Production Mapping

> 판정: **PARTIAL / EVIDENCE BLOCKED**  
> 목적: 71대 Server Master를 `WEB → Apache → WAS → Tomcat JVM → Application/WAR → ServiceId → DB/DR` 관계로 확장한다.  
> 원칙: 실제 Config Evidence가 없는 연결은 반드시 `UNKNOWN` 또는 `CANDIDATE`로 유지한다.

## 1. 근거

- `NSIGHT_서버_상세인벤토리_CPU_MEM_DISK_TPMC.xlsx`: 71대 상세 서버 원장
- `13-WEB-APACHE.md`: WEB/Apache 관리모델 및 9000→19000 등의 설계 패턴
- `14-WAS-TOMCAT-JVM.md`: WAS Server ≠ Tomcat JVM ≠ Application 원칙
- `15-SERVER-MASTER-INVENTORY.md`: 71 Server Current Working Baseline
- `22-HA-DR.md`: HA/DR Working Architecture
- `97-P0-EVIDENCE-REQUEST-PACK.md`: 실제 운영 Config 증적 요청 목록

## 2. 71대 Server Master 재구성 결과

| 기준 | 결과 |
|---|---:|
| Server Master 행 | **71** |
| Hostname Unique | **71** |
| WEB | **20** |
| WAS | **28** |
| AP | **13** |
| DB | **10** |
| 삭제 | **4** |
| 삭제검토 | **1** |
| DR=O 표기 | **30** |

System Group은 `마케팅플랫폼 15, 신BI포털시스템 16, 데이터거버넌스 4, IT 서비스 및 업무지원 28, 데이터플랫폼 시스템 8`로 재검증된다.

## 3. Production Trace 목표

```text
Server
  ↓
Apache Instance / Listener
  ↓
Route Target
  ↓
WAS Server
  ↓
Tomcat JVM
  ↓
CATALINA_BASE / Connector
  ↓
WAR / Context
  ↓
ServiceId Set
  ↓
DB / External Interface
  ↓
HA Peer / DR Target
```

## 4. 현재 증적 수준

| 관계 | 현재 결과 | 판정 |
|---|---|---|
| Server → Hostname / Role / Resource | 71/71 | **WORKING BASELINE** |
| Hostname → Coarse Application | 71/71 (서버명/Hostname 기반) | **CLASSIFICATION ONLY** |
| WEB → Apache 제품 역할 | WEB 20대 | **ARCHITECTURE RULE**, 실제 Instance ID 미확인 |
| Apache → WAS Route | 실제 `httpd.conf` 부재 | **UNKNOWN** |
| WAS → Tomcat 제품 역할 | WAS 28대 | **ARCHITECTURE RULE**, 실제 JVM Inventory 미확인 |
| WAS → JVM/Connector | 마케팅/미니SV 4대에 8개 Working Pattern만 존재 | **CANDIDATE**, config evidence 아님 |
| JVM → WAR | 전수 실제 배포목록 부재 | **UNKNOWN** |
| WAR → ServiceId Set | Source ServiceId는 존재하나 Production Deployment 관계 없음 | **UNKNOWN** |
| WAS/AP → DB Target | 실제 운영 Datasource 원장 부재 | **UNKNOWN** |
| HA Group | Hostname stem으로 Candidate Group 생성 가능 | **CANDIDATE**, L4/Cluster Evidence 필요 |
| 운영 → DR Exact Target | 명시적 예시 7건 | **PARTIAL** |
| DR Naming Candidate | 추가 23건 | **CANDIDATE**, 실제 자산 아님 |

## 5. 가장 중요한 결론

71대 Server Master 자체는 전수 재구성할 수 있으나, 현재 증적만으로 **`71 Server → JVM → WAR → ServiceId` 전수 Production Mapping을 확정할 수 없다.**

이는 데이터 부족이 아니라 증적 종류의 문제다. 소스의 개발 `ztomcat` Packaging이나 설계상의 19000/19001 패턴을 운영 서버에 자동 대입하지 않는다.

## 6. Working Runtime Pattern (Production 확정 아님)

| WAS | JVM | Connector | Application | 상태 |
|---|---|---:|---|---|
| sbmpcolows01 | JVM01 | 19000 | Marketing Common | WORKING_PATTERN |
| sbmpcolows01 | JVM02 | 19001 | Marketing UI | WORKING_PATTERN |
| sbmpcolows02 | JVM01 | 19000 | Marketing Common | WORKING_PATTERN |
| sbmpcolows02 | JVM02 | 19001 | Marketing UI | WORKING_PATTERN |
| sbmpmslows01 | JVM01 | 19000 | MiniSV Common | WORKING_PATTERN |
| sbmpmslows01 | JVM02 | 19001 | MiniSV UI | WORKING_PATTERN |
| sbmpmslows02 | JVM01 | 19000 | MiniSV Common | WORKING_PATTERN |
| sbmpmslows02 | JVM02 | 19001 | MiniSV UI | WORKING_PATTERN |

이 8개 행도 `server.xml/setenv.sh/CATALINA_BASE`와 실제 배포 디렉터리 확인 전에는 `CONFIRMED`로 올리지 않는다.

## 7. DR Mapping

명시적 Project Evidence가 있는 Exact Example은 7건이다. 그 외 `DR=O` 서버는 `#01~49 → #51~99` Naming Rule로 후보값을 만들 수 있지만 이는 실제 자산 존재를 증명하지 않는다.

## 8. P0 Closure를 위해 필요한 최소 증적

| 우선 | Evidence | 닫히는 관계 |
|---:|---|---|
| 1 | 운영 `httpd.conf` / includes | WEB → Apache Listener → WAS/JVM |
| 2 | WAS별 `server.xml` | Hostname → JVM → Connector |
| 3 | WAS별 `setenv.sh` + CATALINA_BASE 목록 | JVM Identity / Heap / Instance |
| 4 | 실제 `webapps`/배포 Manifest | JVM → WAR/Application |
| 5 | Runtime Service Catalog 또는 OM Catalog | WAR → ServiceId Set |
| 6 | 운영 `application.yml/properties` | JVM/WAR → Datasource/RDW/ADW |
| 7 | L4/GSLB Pool/Health/Sticky | HA Group / Peer / Route |
| 8 | DR 자산원장 + Failover Runbook | 운영 → DR Exact Target / RTO/RPO |

## 9. Wave 2B 판정

**`P0-PHY-001 = PARTIAL_MAPPING / BLOCKED_EVIDENCE`**

- 71대 Server Master: 전수 정규화
- Coarse Application/Role: 전수 분류
- Actual Apache Route: 미확정
- Actual Tomcat JVM: 미확정
- Actual WAR: 미확정
- Actual ServiceId Deployment Set: 미확정
- Actual DB Target: 미확정
- HA/DR: 부분만 명시적 증적

따라서 G30/G80/HG90의 Physical Trace P0는 아직 닫지 않는다.
