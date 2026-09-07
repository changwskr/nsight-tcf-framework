# NSIGHT PDMG 아키텍처 정의서
# 제2장. 정보계 패러다임의 전환
## Story: “저장소와 Application 중심에서 살아 움직이는 Platform Architecture로”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 작성 버전: `REWRITE V2 — Story Quality / Figure-to-Explanation Consistency 보완`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference

---

# 0. Opening Script

1장에서 왜 다시 정의해야 하는지 확인했다면, 2장에서는 **무엇이 달라져야 하는가**를 봅니다.

기존 정보계는 화면·업무 Java·DB를 중심으로 설명해도 어느 정도 이해할 수 있었습니다. 그러나 차세대 정보계는 실시간 거래, 인증, 데이터 이동, 대량 분석, 장애격리, 운영증적이 하나의 플랫폼 안에서 동시에 움직입니다.

따라서 설명의 중심도 Application 자체에서 **Responsibility, Runtime, Data Flow, Failure Domain, Evidence**로 이동해야 합니다.

## FIG-02-01. 장 전체 Architecture

```text
과거
User → Application → DB

        ↓ 전환

현재/목표
User
 ↓
UI / Authentication
 ↓
Application Runtime
 ↓
Framework Control
 ↓
Data / Integration
 ↓
Operations / Evidence
```

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-02-02. Drill-down Route

```text
L0 전체 Story
 ↓
L1 책임 / Boundary
 ↓
L2 Logical / Application / Platform
 ↓
L3 Component / Contract
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Deployment / Evidence
```

---

# 1. Application-centric의 한계

## FIG-02-03. Application-centric의 한계

```text
Screen
 ↓
Business Java
 ↓
SQL
 ↓
DB

보이지 않는 것
Thread / Security / Timeout / Deployment / Evidence
```

기존 방식이 틀렸다는 의미는 아닙니다. 기능 중심 개발에는 충분했습니다. 문제는 시스템 규모와 운영복잡도가 커졌는데도 같은 관점으로 설명하려 할 때 생깁니다.

Thread와 Connection Pool, Token Verification, Interface Contract, Deployment Artifact를 설명하지 못하면 장애 원인을 Application 내부에서만 찾게 됩니다.

그래서 Architecture의 범위를 Source Code 밖으로 확장합니다.

여기까지가 `Application-centric의 한계`의 역할입니다. 이제 이 구조를 더 내려가 **Responsibility-centric 전환**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. Responsibility-centric 전환

## FIG-02-04. Responsibility-centric 전환

```text
UI Delivery
Authentication
Application Runtime
Framework Control
Data Service
Integration
Operations

각 책임은 분리
연결은 Contract
```

이번 전환의 첫 번째 핵심은 Application 이름 대신 **책임을 먼저 보는 것**입니다.

UI가 인증을 소유하지 않고, 인증이 Business Logic을 소유하지 않으며, Framework가 특정 업무 SQL을 소유하지 않도록 경계를 둡니다. 책임은 안으로 고정하고 연결은 경계에서 통제하는 방식입니다.

이렇게 해야 한 영역의 변경이 다른 영역으로 번지는 범위를 줄일 수 있습니다.

여기까지가 `Responsibility-centric 전환`의 역할입니다. 이제 이 구조를 더 내려가 **Module에서 Runtime Boundary로**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. Module에서 Runtime Boundary로

## FIG-02-05. Module에서 Runtime Boundary로

```text
Build Module
 ↓
Runtime Relationship
 ↓
Process / JVM
 ↓
Spring Context

Module ≠ Process ≠ JVM
```

두 번째 전환은 Module과 Runtime을 구분하는 것입니다. `pdmg-service`와 `pdmg-fw`가 각각 Build Module이라고 해서 반드시 서로 다른 Process라는 뜻은 아닙니다.

현재 분석에서는 `scanBasePackages="nhnis"`에 의해 Framework Bean과 Business Bean이 같은 ApplicationContext에서 협력할 수 있습니다.

따라서 Build 구조는 Source Management 관점, Runtime Boundary는 실행/장애 관점으로 분리해서 봐야 합니다.

여기까지가 `Module에서 Runtime Boundary로`의 역할입니다. 이제 이 구조를 더 내려가 **Product에서 Capability로**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. Product에서 Capability로

## FIG-02-06. Product에서 Capability로

```text
Tomcat
 ↓
Application Runtime Capability

HikariCP
 ↓
Connection Pool Capability

MyBatis
 ↓
SQL Mapping Capability
```

제품은 Architecture가 아닙니다. 제품은 Architecture가 요구하는 Capability를 구현하는 수단입니다.

예를 들어 Tomcat을 쓴다는 사실보다 중요한 것은 Request Thread, Connector, JVM Failure Domain을 어떻게 가져갈지입니다. HikariCP 역시 제품명보다 Connection Pool의 Capacity와 Timeout 관계가 중요합니다.

그래서 Logical 장에서는 제품보다 Capability를 먼저 정의합니다.

여기까지가 `Product에서 Capability로`의 역할입니다. 이제 이 구조를 더 내려가 **Online에서 FAST/DEEP Workload로**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. Online에서 FAST/DEEP Workload로

## FIG-02-07. Online에서 FAST/DEEP Workload로

```text
FAST
HTTP / Event / CDC
 ↓
bounded latency

DEEP
ETL / ADW / BI
 ↓
heavy workload
```

차세대 정보계는 모든 처리를 같은 Runtime으로 묶지 않습니다. 즉시 응답이 필요한 FAST와 장시간·대량 연산이 가능한 DEEP Workload를 분리합니다.

PDMG Current는 HTTP/Transaction 중심의 FAST Runtime Evidence가 강합니다. Event/CDC/ETL/BI는 NSIGHT Target Reference로 별도 관리합니다.

이 구분이 나중에 RDW/ADW 분리와 Capacity Isolation의 근거가 됩니다.

여기까지가 `Online에서 FAST/DEEP Workload로`의 역할입니다. 이제 이 구조를 더 내려가 **Server에서 Logical→Physical로**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. Server에서 Logical→Physical로

## FIG-02-08. Server에서 Logical→Physical로

```text
Responsibility
 ↓
Logical Node
 ↓
Scale / State / Failure
 ↓
VM / JVM / WAR
```

세 번째 전환은 Application을 곧바로 Server에 배치하지 않는 것입니다.

Logical Node 단계에서 Stateless인지, 어떤 Scale Unit을 가지는지, 어떤 Failure Domain을 가져야 하는지 결정한 뒤 Physical Resource로 내려갑니다.

이렇게 해야 VM 크기나 Server 수량이 Architecture Intent와 연결됩니다.

여기까지가 `Server에서 Logical→Physical로`의 역할입니다. 이제 이 구조를 더 내려가 **Logging에서 Evidence로**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Logging에서 Evidence로

## FIG-02-09. Logging에서 Evidence로

```text
Log
= 사건 기록

Evidence
= Rule 준수 증명

Rule
 ↓
Runtime Metric/Test
 ↓
Evidence
 ↓
PASS
```

로그는 중요하지만 로그가 있다고 Architecture가 지켜졌다고 말할 수는 없습니다.

예를 들어 ServiceId가 로그에 찍힌다는 것과 “모든 거래가 올바른 Handler로 라우팅됐다”는 것은 다른 주장입니다. 후자는 Registry/Runtime Test가 필요합니다.

따라서 마지막에는 Log를 Metric/Trace/Test와 묶어 Evidence로 승격합니다.

여기까지가 `Logging에서 Evidence로`의 역할입니다. 이제 이 구조를 더 내려가 **Document에서 Closed Loop로**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Document에서 Closed Loop로

## FIG-02-10. Document에서 Closed Loop로

```text
Document
 ↓
Rule
 ↓
Source / CI
 ↓
Deployment
 ↓
Runtime
 ↓
Drift / ADR
```

패러다임 전환의 마지막은 문서 운영 방식입니다.

문서가 Source를 따라가는 것이 아니라, Architecture Rule이 Source와 Deployment를 검사하고 Runtime Evidence가 이를 다시 확인하는 구조로 전환합니다.

이제 Architecture는 산출물이 아니라 **변경을 통제하는 운영체계**가 됩니다.

---

# 정상패턴과 금지패턴

## FIG-02-11. Normal Pattern

```text
Responsibility → Capability → Runtime → Evidence
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-02-12. Forbidden Pattern

```text
Module=Server / Product=Node / Current=Target
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-02-13. 주안과 대안

```text
[주안]
Runtime/Platform까지 Architecture Scope 확장

        VS

[대안]
Application Source 중심 Scope
```

이번 장의 주안은 **Runtime/Platform까지 Architecture Scope 확장**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **Application Source 중심 Scope**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-02-14. Current GAP

```text
Current
│
├─ Module→Physical mapping
├─ Operations current scope
├─ Current↔Target mapping registry
└─ Evidence automation
```

- `[GAP/OPEN]` Module→Physical mapping
- `[GAP/OPEN]` Operations current scope
- `[GAP/OPEN]` Current↔Target mapping registry
- `[GAP/OPEN]` Evidence automation

## FIG-02-15. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
PARTIAL

Runtime Evidence
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

Architecture가 잘 정의되었다는 것과 현재 구현이 그 정의를 지킨다는 것은 별도 판단입니다. 이 문서는 둘을 분리해 평가하며, `[OPEN]`과 `[UNKNOWN]`을 임의로 채우지 않습니다.

---

# Evidence Card

## FIG-02-16. Evidence Chain

```text
Architecture Rule
 ↓
Source / Config
 ↓
Build / Artifact
 ↓
Deployment
 ↓
ServiceId / GUID
 ↓
Metric / Log / Trace / Test
 ↓
Runtime Evidence
 ↓
PASS / GAP / ADR
```

본문에서는 Story와 Architecture 설명을 우선하고, Evidence는 이 카드에서 정리합니다. 앞으로 자동화 단계에서는 이 Chain을 Manifest/Registry로 기계적으로 생성하는 것이 목표입니다.

---

# Chapter Closing Script

## FIG-02-17. 다음 장 Handoff

```text
정보계 패러다임의 전환
 ↓
저장소와 Application 중심에서 살아 움직이는 Platform Architecture로
 ↓
남은 질문
"비전에서 시작해 실제 Runtime 검증까지 내려간다"
 ↓
아키텍처 6단계 수립 방법론
```

여기까지가 **정보계 패러다임의 전환**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **비전에서 시작해 실제 Runtime 검증까지 내려간다**. 그 질문이 다음 단계인 **아키텍처 6단계 수립 방법론**의 출발점입니다.
