# NSIGHT / PDMG 아키텍처 정의서 — VIII. Infrastructure / WAS / Capacity / HA / DR Architecture

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: NSIGHT Physical Infrastructure + PDMG Runtime Deployment Mapping  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_VII_Security_SSO_JWT_Session.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-VIII-01 | `NSIGHT_아키텍처_정의서_물리인프라아키텍처_도형표수정.pdf/pptx` | 운영/DR 물리센터, WEB/WAS/AP/DB 역할, 의왕/안성, 마케팅 WEB/WAS 이중화 | `[FACT-SOURCE]` |
| EV-VIII-02 | `2026-08-17-NSIGHT_전체_아키텍처_통합분석_정의_마스터_프롬프트.md` | GSLB→L4→Apache→Tomcat Working Baseline, Server/JVM/WAR 분리, Inventory/Capacity Chain | `[WORKING BASELINE]` |
| EV-VIII-03 | `NSIGHT_용량산정_세션60분_TomcatThread산정_재작성본.docx` | 36,000 사용자, 43k~47k 세션, Session 60분, Tomcat/Hikari/JVM 후보 | `[CAPACITY BASELINE VARIANT]` |
| EV-VIII-04 | `2026-05-31-NSIGHT_용량산정_세션60분_32core_256G_기준.docx` | 32C/256G 비교, maxThreads 1,200~1,500, Heap 32~48GB, 60분 세션 | `[CAPACITY BASELINE VARIANT]` |
| EV-VIII-05 | `NSIGHT_용량산정_6000지점_세션90분_16CORE128G_정상적용_수정본_확인.docx` | 90분 세션 Variant, 16C/128G, Hikari/Thread 비교 | `[CAPACITY BASELINE VARIANT]` |
| EV-VIII-06 | `NSIGHT_용량산정_6000지점_세션90분_8CORE_16CORE_8_2_HikariCP_재점검수정본.docx` | 90분 Variant, AP 수량/Thread/Pool 비교 | `[CAPACITY BASELINE VARIANT]` |
| EV-VIII-07 | `2026-06-03 NSIGHT_용량산정_화면설계서_WAS실행쓰레드포함.docx` | End-to-End Capacity Chain, Thread/DB Pool Ratio, 설정점검 | `[CAPACITY DESIGN EVIDENCE]` |
| EV-VIII-08 | `NSIGHT_PDMG_아키텍처_정의서_VII_Security_SSO_JWT_Session.md` | JWT/JWKS/Key/State 물리 Handoff | `[CURRENT BASELINE DRAFT]` |
| EV-VIII-09 | `NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture.md` | PDMG Worker 20/Queue100/5s와 WAS/DB Capacity 관계 | `[CURRENT BASELINE DRAFT]` |
| EV-VIII-10 | `NSIGHT_PDMG_아키텍처_정의서_II_BigPicture_SystemBoundary.md` | Logical→Physical Working Baseline | `[CURRENT BASELINE DRAFT]` |
| EV-VIII-11 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | VIII장 필수 View/금지 규칙 | `[WORKING BASELINE]` |

---

# 1. Evidence Conflict Register — 이 장에서 숫자를 읽는 법

이 장은 다른 장보다 **수치 자료의 시점·가정 충돌**이 많다.

따라서 다음 규칙을 사용한다.

```text
[FACT-SOURCE]
물리 구성도에 명시된 서버/센터/역할

[CAPACITY BASELINE VARIANT]
특정 용량산정 문서 시점의 가정값

[WORKING BASELINE]
프로젝트에서 반복 사용됐지만
최종 Inventory/Config로 아직 닫히지 않은 구조

[TO-BE/PROPOSED]
설계 원칙 후보

[OPEN/GAP]
최신 승인값 미확정
```

## 1.1 CONFLICT-VIII-01 — Session 60분 vs 90분

현재 Evidence Set에는 모두 존재한다.

```text
Variant A
Session Timeout = 60분
Sticky = 70~80분

Variant B
Session Timeout = 90분
Sticky = 100~120분
```

따라서 본 장은 **현재 최종 Session Timeout을 임의로 확정하지 않는다.**

최종 승인본/환경설정/운영정책을 통해 닫아야 한다.

## 1.2 CONFLICT-VIII-02 — SingleView Hikari Pool

자료별 예:

```text
60분 Variant
SingleView = 70~80

90분 16C Variant
SingleView = 100~120
```

동일 항목이 문서별로 다르므로 최신 승인 Baseline을 확인해야 한다.

## 1.3 CONFLICT-VIII-03 — Timeout Layer

용량산정 자료에는:

```text
DB Query Timeout
Spring Transaction Timeout
Client/WebTopSuite
Apache Proxy
L4
```

값이 포함되지만 V장에서 확인한 **PDMG Current Source의 OnlineTimeoutExecutor 5000ms**와 동일 Source가 아니다.

즉:

```text
Capacity Recommendation
≠
Current PDMG Runtime Config
```

이다.

---

# 2. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-VIII-01 | NSIGHT Logical → Physical Big Picture | L0~L2 | Logical Application의 물리 투영 | Y |
| FIG-VIII-02 | 의왕 Main / 안성 DR Center Topology | L1~L3 | 센터·GSLB·DR 경계 | Y |
| FIG-VIII-03 | Online WEB/WAS Working Baseline | L2~L3 | GSLB/L4/Apache/Tomcat | Y |
| FIG-VIII-04 | Server / JVM / WAR Boundary | L2 | 서버·Tomcat·WAR 혼동 제거 | Y |
| FIG-VIII-05 | PDMG Deployment Mapping | L2~L3 | ui/jwt/service/fw 물리위치 후보와 Unknown | Y |
| FIG-VIII-06 | Marketing WEB/WAS Physical Evidence | L2 | 구성도 FACT와 PDMG Mapping 분리 | Y |
| FIG-VIII-07 | JVM Memory Boundary | L2~L4 | Heap/Native/Thread/Metaspace | Y |
| FIG-VIII-08 | Tomcat Thread / PDMG Worker / Hikari Chain | L2~L4 | 실행자원 정합성 | Y |
| FIG-VIII-09 | Capacity Assumption → TPS → AP | L2~L4 | 36k→1,200/1,800 흐름 | Y |
| FIG-VIII-10 | CPU/Memory Variant Comparison | L2~L4 | 8C/16C/32C 후보 비교 | Y |
| FIG-VIII-11 | Hikari / DB Session Boundary | L2~L4 | Pool≠Thread, DB 부담 | Y |
| FIG-VIII-12 | Session / JWT / Stateless Boundary | L2~L4 | Tomcat Session과 Token State | Y |
| FIG-VIII-13 | DeltaManager HA Boundary | L2~L4 | 센터 내부 복제/센터 간 미복제 | Y |
| FIG-VIII-14 | WEB/WAS Node Failure Sequence | L3~L4 | 노드 장애 Failover | Y |
| FIG-VIII-15 | Center Failure / DR Sequence | L3~L5 | DR 재라우팅/재로그인 | Y |
| FIG-VIII-16 | JWT/JWKS/Key HA Physical Boundary | L2~L5 | VII장 Security 물리화 | Y |
| FIG-VIII-17 | Timeout Budget Physical Layers | L2~L4 | UI/L4/Apache/Tomcat/PDMG/DB | Y |
| FIG-VIII-18 | Scale-Up vs Scale-Out | L2~L4 | 대형 VM vs 다수 VM | Y |
| FIG-VIII-19 | Failure Domain Map | L3~L5 | Process/JVM/VM/Center 장애 | Y |
| FIG-VIII-20 | Server Master Inventory Trace | L2~L5 | Logical→Hostname→JVM→WAR→DB | Y |
| FIG-VIII-21 | Physical Configuration Drift Check | L4~L5 | Capacity vs Actual Config | Y |
| FIG-VIII-22 | Security / Network Zone Boundary | L2~L5 | Direct Access/Gateway/TLS/Open Port | Y |
| FIG-VIII-23 | HA/DR Decision & GAP Map | L4~L5 | 미결정 정리 | Y |
| FIG-VIII-24 | IX장 Handoff | L5 | 운영/OM/Observability 연결 | Y |

---

# 3. 핵심 결론

VIII장의 핵심 결론은 다음과 같다.

1. NSIGHT의 물리 아키텍처는 논리 Application 이름을 서버 목록으로 치환하는 작업이 아니라 **Center → Network → WEB → WAS/JVM → WAR → Framework/Business → Pool → DB**의 실행 경계를 고정하는 작업이다.
2. 프로젝트의 온라인 실행 Working Baseline은 **User → GSLB → L4 → Apache → Tomcat/JVM → Business WAR → Hikari/MyBatis → RDW/ADW**다.
3. 이 구조는 현재 통합 Baseline에서 반복 사용되지만, 물리 구성도 자체가 모든 WEB 노드의 실제 Web Server 제품을 Apache라고 명시하는 것은 아니므로 **Apache는 Current Working Standard**, 노드별 설치 Software는 Inventory/Config로 검증해야 한다.
4. 물리 인프라 자료에는 **의왕 센터를 주센터, 안성 센터를 DR 센터**로 표시하고 내부 GSLB를 통해 센터를 연결하는 운영/DR 구성이 존재한다.
5. 운영/DR 물리도에서 마케팅플랫폼은 의왕 기준 WEB #1,#2와 WAS #1,#2 등 이중화된 역할로 표현된다. 그러나 `pdmg-service.war`가 정확히 이 마케팅 WAS 노드에 배포된다는 Source Mapping은 본 장에서 완전히 확인되지 않았으므로 **PDMG→Physical Host 매핑은 GAP**로 둔다.
6. `WAS Server/VM`, `Tomcat JVM Instance`, `Application/WAR`는 서로 다른 단위다.
7. `pdmg-fw`는 별도 원격 WAS가 아니라 Business Application JVM 내부 Framework Library/Bean이므로 별도 Physical Server Box를 만들지 않는다.
8. `pdmg-ui`, `pdmg-service`, `pdmg-jwt`는 Process/HTTP 경계가 존재할 수 있으나 실제 운영 Server/JVM/WAR 배치는 최신 Deployment Inventory가 필요하다.
9. PDMG의 `OnlineTimeoutExecutor` Worker 20/Queue100은 **애플리케이션 내부 Worker Pool**이며 Tomcat `maxThreads`와 동일하지 않다.
10. Tomcat Thread, PDMG Worker, Hikari Connection Pool, Oracle Session은 하나의 Capacity Chain이지만 같은 크기로 맞추는 값이 아니다.
11. 용량산정 자료는 전체 사용자 36,000명(6,000 지점 × 6명), 동시요청률 5/10/15%, 목표응답시간 3초 기준으로 600/1,200/1,800 TPS 후보를 사용한다.
12. 8C/32GB, 16C/64GB, 16C/128GB, 32C/256GB VM Variant가 존재하며 CPU Core가 같다고 Memory가 커졌다는 이유만으로 Thread/TPS/DB Pool을 자동 상향하지 않는 원칙이 자료에 명시돼 있다.
13. 32C/256GB Variant에는 Tomcat maxThreads 1,200~1,500, JVM Heap 32~48GB 같은 1차 후보가 존재하지만 최종 성능시험 보정 전 값이다.
14. Session Timeout은 현재 자료에 60분/90분 Variant가 모두 존재하므로 최종 승인값을 재확정해야 한다.
15. DeltaManager는 자료상 **센터 내부 AP Cluster에 적용하고 센터 간 Session 복제는 기본 미적용**으로 반복 정의되어 있다.
16. 따라서 센터 장애 시 기존 Session을 DR센터에서 완전히 유지하는 구조가 아니라 **재로그인 정책**을 기본 방향으로 보는 자료가 있다.
17. JWT를 도입하더라도 Refresh/Denylist/User/Auth State와 기존 UI/Session 요구가 남기 때문에 “JWT이므로 Session 설계 불필요”라고 볼 수 없다.
18. VII장에서 확인한 현재 per-process RSA Key 생성은 다중 JWT Instance와 DR 구조에서 직접 충돌하므로 Production에서는 **센터/인스턴스와 독립적인 Key Store + Versioned kid**가 필요하다.
19. HA와 DR은 같은 것이 아니다. AP Node 장애, JVM 장애, WEB 장애, DB 장애, Center 장애를 분리해야 한다.
20. DR의 RPO/RTO는 현재 확보자료만으로 최종값을 확정하지 않는다.
21. 물리 구성은 최종적으로 Server Master Inventory와 연결되어야 하며 최소 `Hostname → Role → JVM → WAR → Port → DB → HA Group → DR Pair`까지 추적 가능해야 한다.
22. Capacity 설계가 문서에만 있고 실제 `server.xml/application.yml/httpd.conf/JVM option/Hikari config`와 다르면 Architecture Drift다.

---

# 4. 목적 / 범위 / 전제

## 4.1 목적

본 장은 다음 질문에 답한다.

1. NSIGHT Logical Application은 어떤 물리 실행 계층에 배치되는가?
2. WEB/WAS/JVM/WAR의 관계는 무엇인가?
3. 의왕과 안성 센터는 어떤 역할을 갖는가?
4. PDMG 모듈은 물리적으로 어디에 배치되는가?
5. `pdmg-fw`는 물리 Server인가?
6. 전체 사용자/동시요청/TPS가 AP 수량과 Thread로 어떻게 변환되는가?
7. Tomcat maxThreads와 PDMG Worker Pool의 관계는 무엇인가?
8. Worker Pool과 Hikari Pool/DB Session은 어떻게 연결되는가?
9. JVM Heap은 VM Memory와 어떻게 구분되는가?
10. Session과 JWT는 어떻게 공존/전환되는가?
11. DeltaManager는 어느 범위에서 HA를 보장하는가?
12. AP/WAS 노드 장애와 Center 장애는 어떻게 다른가?
13. DR 전환 시 Session/JWT/Key/DB는 어떤 영향을 받는가?
14. JWT 발급 Server Scale-out에서 Key/JWKS는 어떻게 HA되어야 하는가?
15. Server Inventory와 Architecture Component를 어떻게 매핑할 것인가?
16. Capacity Design과 실제 설정의 Drift를 어떻게 검증할 것인가?

## 4.2 포함

```text
의왕 센터
안성 DR 센터
GSLB
L4
Apache [Working Baseline]
WEB Node
WAS Node
Tomcat JVM
WAR
PDMG Modules
JVM Heap
Native Memory
GC
Tomcat Thread
PDMG Worker
Queue
HikariCP
Oracle/RDW/ADW
Session
DeltaManager
Sticky
JWT/JWKS/Key
Scale-out
HA
DR
Server Inventory
Capacity
Timeout Layer
Configuration Drift
```

## 4.3 제외

```text
OM Dashboard 구현 상세              → IX
CI/CD Deployment Pipeline           → IX
ServiceId→Server Runtime Evidence   → X
Oracle Appliance 자체 DB 내부구조   → Data/DB 별도장
Network 장비 상세설정               → TA 상세설계
RPO/RTO 최종 승인값                 → DR 정책 승인자료
```

---

# 5. FIG-VIII-01 — NSIGHT Logical → Physical Big Picture

```text
[Logical]
Channel / Information Application / Data Platform
        │
        ▼
[Access]
GSLB
        │
        ▼
L4
        │
        ▼
[WEB Layer]
WEB Node
Apache [Working Standard]
        │
        │ Reverse Proxy / Routing
        ▼
[WAS Layer]
WAS Server / VM
        │
        ▼
Tomcat JVM Instance
        │
        ├─ Business WAR
        │    ├─ pdmg-service code
        │    └─ pdmg-fw library / bean
        │
        ├─ JWT Application [배치 확인 필요]
        └─ Other WAR
        │
        ▼
[Execution Resource]
Tomcat Request Thread
PDMG Worker
HikariCP
        │
        ▼
[Data]
RDW / ADW / Token State / Log DB
```

## 5.1 논리→물리 Mapping 원칙

```text
Logical Application
≠
Server

Module
≠
JVM

WAR
≠
VM

Tomcat
≠
VM 자체
```

하나의 Server/VM에 여러 JVM이 있을 수 있고, 하나의 JVM에 여러 WAR이 있을 수 있으므로 실제 Inventory로 확인해야 한다.

---

# 6. FIG-VIII-02 — 의왕 Main / 안성 DR Center

물리 인프라 구성도에 다음이 표시된다.

```text
┌──────────────────── 의왕 센터 ────────────────────┐
│                    [주 센터]                       │
│                                                   │
│ WEB / WAS / AP / DB / ETL                        │
│ Marketing / BI / Governance / Operation / Data   │
│                                                   │
└───────────────────────┬───────────────────────────┘
                        │
                  Internal GSLB
                        │
┌───────────────────────▼───────────────────────────┐
│                    안성 센터                      │
│                    [DR 센터]                      │
│                                                   │
│ DR 대응 WEB / WAS / AP / DB Roles                │
│                                                   │
└───────────────────────────────────────────────────┘
```

## 6.1 Source가 말하는 것

```text
의왕 = 주센터
안성 = DR센터
내부 GSLB 존재
```

## 6.2 Source만으로 확정하지 않는 것

```text
RPO
RTO
자동/수동 전환 상세
DNS/GSLB TTL 실제 운영값
DB DR 방식 전부
JWT Key DR 절차
```

---

# 7. 운영/DR 물리 Role Evidence

운영/DR 물리도에는 의왕 주센터에서 예를 들어 다음 역할이 표현된다.

```text
Marketing
├─ 마케팅플랫폼 WEB #1,#2
├─ 마케팅플랫폼 WAS #1,#2
├─ 미니싱글뷰 WEB #1,#2
├─ 미니싱글뷰 WAS #1,#2
├─ 실시간처리 AP #1,#2
├─ 행동정보처리 AP #1,#2
└─ 고객행동데이터 AP #1,#2,#3

BI / Analysis
├─ BI Portal WEB/WAS #1,#2
├─ Self-BI WEB/WAS #1,#2
├─ Self-BI AP
└─ 신용실적 WEB/WAS #1,#2

Governance / Operation
├─ 비즈메타/데이터품질 WAS
├─ 데이터흐름 WAS
├─ 출력물 WAS
├─ 단말관리
├─ 단말배포
└─ Dashboard

Data
└─ RDW / ADW Appliance
```

이는 **NSIGHT Physical Role Evidence**다.

PDMG의 실제 WAR/Process가 어느 Role에 배치되는지는 별도의 Deployment Mapping이 필요하다.

---

# 8. FIG-VIII-03 — Online WEB/WAS Working Baseline

프로젝트 통합 기준에서 온라인 실행은 다음 구조로 반복 사용된다.

```text
User / WebTopSuite / Browser
       │
       ▼
      GSLB
       │
       ▼
       L4
       │
       ▼
┌────────────── WEB ──────────────┐
│ Apache                          │
│ VirtualHost / Proxy             │
│ Health Check / Access Log       │
└───────────────┬─────────────────┘
                │ HTTP/AJP/etc
                ▼
┌────────────── WAS ──────────────┐
│ Tomcat JVM                      │
│                                 │
│ Business WAR                    │
│   ├─ pdmg-service               │
│   └─ pdmg-fw                    │
│                                 │
└───────────────┬─────────────────┘
                │
                ▼
             HikariCP
                │
                ▼
             RDW / DB
```

## 8.1 Evidence 상태

```text
GSLB/L4/Apache/Tomcat
= NSIGHT 통합 Working Baseline

실제 물리도
= WEB/WAS Role를 명시

노드별 Apache/Tomcat 설치버전/포트
= Config/Inventory 확인 필요
```

---

# 9. WEB Architecture

## 9.1 WEB Layer 책임

```text
VirtualHost
Context Routing
Reverse Proxy
TLS Termination [실제 위치 확인]
Health Check
KeepAlive
Connect/Read Timeout
Access Log
Static Resource [구성에 따라]
```

## 9.2 WEB가 하면 안 되는 것

```text
Business SQL
ServiceId Business Rule
JWT Private Key 보유
RDW 직접 업무조회
```

## 9.3 Apache Instance ≠ WEB VM

```text
WEB VM
  └─ Apache Instance #1
       ├─ Listen A
       ├─ Listen B
       └─ VirtualHost ...
```

한 VM 안에 Instance/Listen/VHost 관계는 실제 설정으로 확인한다.

---

# 10. FIG-VIII-04 — Server / JVM / WAR Boundary

```text
Physical Server / VM
┌────────────────────────────────────────────┐
│ OS                                         │
│                                            │
│  Tomcat JVM #1                             │
│  ┌──────────────────────────────────────┐  │
│  │ Heap / Metaspace / Thread           │  │
│  │                                      │  │
│  │ WAR-A                                │  │
│  │ WAR-B [실제 존재 여부 Inventory]    │  │
│  └──────────────────────────────────────┘  │
│                                            │
│  Tomcat JVM #2 [가능한 구조]              │
│  ┌──────────────────────────────────────┐  │
│  │ 다른 CATALINA_BASE / Port / Heap    │  │
│  └──────────────────────────────────────┘  │
└────────────────────────────────────────────┘
```

## 10.1 반드시 분리할 관리단위

```text
Server / VM
Tomcat JVM
WAR
Port
PID
OS Account
CATALINA_BASE
Heap
Thread
Datasource
Session
Log
HA Group
```

---

# 11. `Container` 용어 주의

과거 NSIGHT 자료에서 `Container`는 문맥에 따라:

```text
Tomcat JVM Instance
```

를 뜻하는 자료가 있고, 다른 문서에서는:

```text
OCI/Kubernetes Container
```

를 뜻할 수 있다.

따라서:

```text
Container
```

라는 단어만 보고 Kubernetes/Pod라고 해석하지 않는다.

본 장에서는:

```text
Tomcat JVM Instance
```

를 명시적으로 사용한다.

---

# 12. FIG-VIII-05 — PDMG Deployment Mapping

현재 Source/구조로 확인되는 것은 다음이다.

```text
pdmg-ui
= 독립 HTTP Process 가능

pdmg-jwt
= 독립 HTTP Process 가능

pdmg-service
= Business Application Runtime

pdmg-fw
= pdmg-service / pdmg-jwt 내부 Library/Bean

pdmg-om
= Current Physical Deployment UNKNOWN
```

물리 Mapping은:

```text
[WEB/WAS Physical Nodes]
        │
        ├─ pdmg-ui ?             [TBD]
        ├─ pdmg-service WAR ?    [TBD]
        ├─ pdmg-jwt WAR/JAR ?    [TBD]
        ├─ pdmg-om ?             [TBD]
        └─ pdmg-fw
             └─ 별도 Server 아님
```

## 12.1 중요한 GAP

현재 확보된 PDMG Source 구조는 Local/Module Runtime을 잘 보여주지만:

```text
운영 Hostname
Tomcat Instance
Context Path
External Port
L4 Pool
Apache VHost
JWT URL
OM URL
```

까지 확정하는 Deployment Manifest가 본 장 Evidence에 충분히 연결되지 않았다.

---

# 13. FIG-VIII-06 — Marketing WEB/WAS Physical Evidence

물리도 Source:

```text
의왕 주센터

마케팅플랫폼 WEB
├─ #1
└─ #2

마케팅플랫폼 WAS
├─ #1
└─ #2
```

Architecture Mapping 후보:

```text
GSLB/L4
   ↓
Marketing WEB #1/#2
   ↓
Marketing WAS #1/#2
   ↓
Marketing Application / PDMG Reference ?
```

마지막 매핑은 `[TBD]`.

물리 노드명과 PDMG Repository가 같은 “마케팅”이라는 이유만으로 자동 1:1 매핑하지 않는다.

---

# 14. PDMG Local Port는 운영 Port가 아니다

IV장 Local 분석에는 예를 들어:

```text
pdmg-ui      :8090
pdmg-service :8080
```

이 등장한다.

이 값은 Local Runtime Evidence다.

따라서:

```text
운영 WAS Port = 8080
운영 UI Port  = 8090
```

이라고 쓰지 않는다.

운영 Port는 `server.xml/application.yml/Apache proxy/L4 Pool`의 실제 Deployment Evidence가 필요하다.

---

# 15. FIG-VIII-07 — JVM Memory Boundary

```text
VM Memory
┌──────────────────────────────────────┐
│                                      │
│ JVM Process                          │
│ ┌──────────────────────────────────┐ │
│ │ Java Heap                        │ │
│ │                                  │ │
│ │ Metaspace                        │ │
│ │ Thread Stack                     │ │
│ │ Code Cache                       │ │
│ │ Direct / Native Buffer           │ │
│ │ GC Native Structures             │ │
│ │ JNI / Native Library             │ │
│ └──────────────────────────────────┘ │
│                                      │
│ OS / Page Cache / Agent / Other      │
└──────────────────────────────────────┘
```

## 15.1 핵심 원칙

```text
VM 256GB
≠
JVM Heap 256GB
```

Capacity Variant는 32C/256GB 구성에서도:

```text
Heap 32~48GB 권장 후보
```

를 제시하고 전체 Memory를 Heap으로 쓰지 말라고 한다.

---

# 16. JVM Heap Baseline Variants

문서 Variant 예:

| VM | Heap 후보 |
|---|---|
| 8C / 32GB | 일반 12GB / SingleView 14GB 수준 |
| 16C / 64GB | 일반 24GB / SingleView 28GB 수준 |
| 16C / 128GB | 일반 32GB / SingleView 40GB 이내 Variant |
| 32C / 256GB | 32~48GB 후보 |

이 표는 **Capacity Study Candidate**다.

실제 Production `-Xms/-Xmx` 값은 `setenv.sh`/JVM Process로 검증해야 한다.

---

# 17. GC

일부 Capacity 자료는 G1GC를 운영 후보로 제시한다.

```text
G1GC
GC Log
Pause
Full GC
Heap Usage
```

그러나 PDMG 운영 JVM의 실제 GC Option은 현재 본 장 Evidence에서 Source Runtime으로 확인되지 않았다.

`[OPEN-VIII-01]`

따라서:

```text
PDMG 운영은 G1GC로 이미 확정
```

이라고 쓰지 않는다.

---

# 18. FIG-VIII-08 — Tomcat Thread / PDMG Worker / Hikari Chain

```text
Client Requests
       │
       ▼
Tomcat Request Thread Pool
       │
       │ Request 1개가 TCF에서 Worker 결과 대기
       ▼
PDMG Online Worker Pool
       │
       │ Business Execution
       ▼
Hikari Connection Pool
       │
       │ JDBC
       ▼
Oracle / RDW Session
```

## 18.1 서로 다른 Pool

```text
Tomcat maxThreads
= HTTP 요청 실행자

PDMG worker pool
= Timeout/거래 업무 실행자

Hikari maxPoolSize
= DB Connection 자원
```

따라서:

```text
maxThreads = worker = Hikari
```

로 맞추지 않는다.

---

# 19. PDMG Current Worker vs Capacity Baseline

V장에서 확인한 PDMG Current:

```text
worker pool = 20
queue       = 100
timeout     = 5000ms
```

Capacity Study의 Tomcat 후보:

```text
400~500
800~1000
1200~1500
```

등과 숫자 규모가 매우 다르다.

이것은 모순이 아니라 **다른 계층의 Pool**이다.

다만 20 Worker가 실제 목표 1,200 TPS를 처리하기에 충분한지는 평균 업무시간/DB 사용률/Test Evidence로 검증해야 한다.

`[GAP-VIII-01]`

---

# 20. Thread Utilization Rule 후보

Capacity 화면설계 자료는 다음과 같은 운영 판정기준을 제시한다.

```text
산정 Thread
≤ maxThreads 70% : 정상 후보

70~85%
: 주의

85% 초과
: AP 증설 / DB/외부 병목 확인
```

이 기준은 `[CAPACITY DESIGN RULE]`이며 운영 Metric과 함께 검증해야 한다.

---

# 21. FIG-VIII-09 — Capacity Assumption → TPS → AP

용량산정 문서의 대표 전제:

```text
지점 수
6,000
  ↓
지점당 사용자
6
  ↓
전체 사용자
36,000
  ↓
동시요청률
5% / 10% / 15%
  ↓
동시 요청자
1,800 / 3,600 / 5,400
  ↓
목표 응답시간
3초
  ↓
TPS
600 / 1,200 / 1,800
  ↓
AP / VM 수량
  ↓
Tomcat Threads
  ↓
Worker / Hikari / DB Session
```

## 21.1 산정식

```text
Concurrent Request User
= Total User × Concurrent Request Rate

TPS
= Concurrent Request User ÷ Response Time
```

예:

```text
36,000 × 10% = 3,600
3,600 ÷ 3s = 1,200 TPS
```

---

# 22. 전체 세션과 TPS를 혼동하지 않는다

```text
36,000 로그인 사용자
≠
36,000 동시 요청자
```

Session은 Memory/Replication Capacity에 영향을 준다.

TPS는:

```text
동시 요청률
응답시간
업무 복잡도
```

에 영향을 받는다.

---

# 23. FIG-VIII-10 — CPU / Memory Variant Comparison

Capacity Study Variant:

```text
8C / 32GB
  │
  ├─ maxThreads 400~500 후보
  ├─ Hikari 일반 50 후보
  └─ Heap 12GB 수준 후보

16C / 64GB
  │
  ├─ maxThreads 800~1000 후보
  ├─ Hikari 일반 80~100 후보
  └─ Heap 24GB 수준 후보

16C / 128GB
  │
  ├─ CPU는 16C와 동일
  ├─ Thread/TPS 자동상향 금지
  └─ Heap 여유 증대 후보

32C / 256GB
  │
  ├─ maxThreads 1200~1500 후보
  ├─ Heap 32~48GB 후보
  └─ Scale-Up 단일 장애영역 커짐
```

## 23.1 핵심 원칙

```text
Memory 증가
≠
CPU 처리능력 증가
```

16C/64와 16C/128은 CPU Core가 같으므로 Thread/TPS를 메모리만 보고 늘리지 않는다.

---

# 24. AP Server Count Variant

용량산정 자료에는 Peak/Stress 기준으로 VM Size별 권장 수량 후보가 존재한다.

예:

```text
8C  Peak    센터당 6
16C Peak    센터당 4
32C Peak    센터당 3

Stress 시 더 증가
```

이 값들은 **Capacity Study의 1차 후보**이며 최신 물리 Inventory의 확정 서버 수가 아니다.

따라서 운영/DR 물리도에 표시된 Role #1,#2 등과 바로 동일시하지 않는다.

---

# 25. FIG-VIII-11 — Hikari / DB Session Boundary

```text
Tomcat / Worker
       │
       ▼
Hikari Pool
┌────────────────────────┐
│ idle                   │
│ active                 │
│ pending                │
└──────────┬─────────────┘
           │
           ▼
Oracle
┌────────────────────────┐
│ Session / Process      │
│ SQL / Lock / PGA       │
└────────────────────────┘
```

## 25.1 Capacity 자료 후보

일반 AP:

```text
8C        ≈ 50
16C       ≈ 80~100
```

SingleView:

```text
문서별 70~80 또는 100~120 Variant
```

최신 승인값은 미확정.

---

# 26. Thread / DB Pool Ratio

Capacity 화면설계 자료는 Thread/Pool 비율을 운영 검증 대상으로 둔다.

예시 판단 후보:

```text
4:1~8:1    정상 후보
8:1~12:1   주의
12:1 초과  Pool Wait/SQL 병목 확인
```

이는 모든 업무에 절대 법칙은 아니며 테스트 기반 판정 기준이다.

---

# 27. Hikari Pool이 작을 때

```text
Request/Worker
   ↓
Connection 필요
   ↓
Pool Full
   ↓
Pending
   ↓
Worker 점유
   ↓
PDMG Timeout
   ↓
Queue 증가
```

따라서:

```text
Worker Queue
Hikari Pending
SQL Time
```

을 함께 봐야 한다.

---

# 28. Hikari Pool이 클 때

```text
Pool 확대
   ↓
DB Session 증가
   ↓
Oracle CPU/PGA/Lock/IO 증가
```

Pool 확대는 무료 성능향상이 아니다.

---

# 29. FIG-VIII-12 — Session / JWT / Stateless Boundary

```text
Browser
│
├─ Access Token
├─ Refresh Token
└─ UI State
     │
     ▼
WEB/WAS
│
├─ HTTP Session [업무 요구 시]
│   ├─ userId
│   ├─ branchId
│   ├─ role
│   └─ 최소 권한정보
│
├─ JWT Verification
│
└─ PDMG Request Context
     │
     ▼
Server State
├─ Refresh Token Hash
├─ Denylist
├─ User/Auth Policy
└─ Optional Session Store
```

## 29.1 JWT ≠ 세션 전체 제거

JWT를 사용해도:

```text
Session 필요 업무?
UI 상태?
Legacy 단말?
SSO Compatibility?
```

를 별도로 판단한다.

---

# 30. Session Size Rule — Capacity Variant

60분 Capacity 자료의 정책 후보:

```text
목표 Session Size ≤ 2KB
최대           ≤ 5KB
```

저장 가능 후보:

```text
userId
branchId
role
authLevel
maskingLevel
```

저장 금지 후보:

```text
고객조회 결과
Single View 결과
거래목록
대용량 객체
```

이 기준은 DeltaManager Memory/Network 부하를 줄이기 위한 용량정책이다.

---

# 31. Session Timeout Conflict

## Variant A

```text
Session Timeout = 60분
Sticky          = 70~80분
```

## Variant B

```text
Session Timeout = 90분
Sticky          = 100~120분
```

두 문서 모두 2026-08 프로젝트 자료에 존재한다.

따라서 최종 Architecture Baseline에는 다음 하나만 남겨야 한다.

```text
Final Session Idle Timeout = TBD
Final Sticky Timeout       = TBD
```

`[ADR-VIII-01]`

---

# 32. FIG-VIII-13 — DeltaManager HA Boundary

Capacity 자료의 반복 원칙:

```text
센터 내부 AP Cluster
┌───────────────────────────────┐
│ Tomcat A                      │
│ Session A ───── replication ─┐│
│                               ││
│ Tomcat B                      ││
│ Session A ◄───────────────────┘│
└───────────────────────────────┘

센터 간
의왕 ───── X Session Replication ───── 안성
```

## 32.1 DeltaManager 기준 후보

```text
센터 내부만
Sticky 사용
<distributable/>
Session Object Serializable
jvmRoute
NTP 동기화
```

## 32.2 센터 장애

자료의 기본 방향:

```text
Main Center Session
   ↓
Center Failure
   ↓
DR Center
   ↓
Session 없음
   ↓
재로그인
```

---

# 33. DeltaManager의 한계

Session Replication이 있다고:

```text
Application State 전체
DB Transaction
Worker Context
JWT Refresh State
```

까지 복제되는 것은 아니다.

복제대상은 HttpSession Attribute다.

따라서 Session에 대량업무 결과를 넣지 않는다.

---

# 34. FIG-VIII-14 — WEB/WAS Node Failure

```text
Client
  ↓
L4
  ↓
WEB #1 ───── X Failure
  │
  └────────────► WEB #2
                    ↓
                  WAS Pool
                    │
              ┌─────┴─────┐
              ▼           ▼
           WAS #1       WAS #2
```

WAS #1 Failure:

```text
L4/WEB Health
   ↓
WAS #1 제외
   ↓
WAS #2
   ↓
Session
   ├─ DeltaManager 복제 성공 → 일부 연속성
   └─ 복제/Sticky 상황에 따라 재처리
```

정확한 L4 Health Check Method/Interval은 Config Evidence가 필요하다.

---

# 35. HA ≠ 무중단 보장

다음 장애마다 결과가 다르다.

```text
Apache Process 장애
Tomcat JVM 장애
VM 장애
Network 장애
DB 장애
센터 장애
```

HA는 각각:

```text
Detect
Remove
Route
Recover
State Restore
```

를 정의해야 한다.

서버가 2대라는 사실만으로 HA가 완성되는 것은 아니다.

---

# 36. FIG-VIII-15 — Center Failure / DR Sequence

```text
정상

User
 ↓
GSLB
 ↓
의왕 L4
 ↓
WEB/WAS
 ↓
DB


의왕 센터 장애
 ↓
GSLB / DR 판단
 ↓
안성 L4
 ↓
DR WEB/WAS
 ↓
DR Data / Service
 ↓
Session?
   └─ 센터간 미복제
       ↓
     재로그인
```

## 36.1 DR에서 별도 확인할 것

```text
DB DR Active/Standby 방식
Data Replication lag
Kafka/CDC/ETL DR
JWT/Refresh/Denylist DB
Key/JWKS
External Interface Route
DNS/GSLB
Batch
File
OM/Monitoring
```

본 장의 PDMG 중심 정의에서 위 전체를 임의로 닫지 않는다.

---

# 37. DR RPO / RTO

현재 Evidence Set에서 최종 승인된 PDMG/NSIGHT 전체:

```text
RPO
RTO
```

값을 확정할 수 없다.

따라서 일반 은행권 값을 넣지 않는다.

`[GAP-VIII-02]`

각 시스템등급/업무중요도에 따른 공식 DR 요구사항 자료가 필요하다.

---

# 38. FIG-VIII-16 — JWT / JWKS / Key HA Physical Boundary

VII장의 Current 문제:

```text
JWT Instance 시작
→ 새 RSA Key
→ 같은 kid
```

이 구조는 Physical HA와 충돌한다.

## AS-IS Risk

```text
L4
 ├─ JWT #1 : Key-A / kid=K
 └─ JWT #2 : Key-B / kid=K

JWKS
어느 Key가 보이는가?
```

## TO-BE Physical

```text
                  ┌────────────────────┐
                  │ Central Key Store  │
                  │ KMS/HSM/Secret     │
                  └─────────┬──────────┘
                            │
             ┌──────────────┴───────────────┐
             ▼                              ▼
        JWT Instance #1                JWT Instance #2
        kid=K2 / same key              kid=K2 / same key
             │                              │
             └──────────────┬───────────────┘
                            ▼
                         L4 / VIP
                            │
                            ▼
                         JWKS URL
                            │
                            ▼
                  Business Verifier Cache
```

제품은 `[TBD]`.

---

# 39. JWT DR

DR 전환 시 질문:

```text
Main/DR JWT Issuer가 같은 Key를 쓰는가?
JWKS URL은 동일한가?
Refresh DB는 복제됐는가?
Denylist는 복제됐는가?
기존 Token은 DR에서 검증되는가?
```

TO-BE 원칙 후보:

```text
Center 독립 Process
+
Center 공통 Logical Issuer
+
안전하게 공유/복제된 Key Material
+
Refresh/Denylist State DR
```

정확한 구현은 보안/TA/DBA 협업이 필요하다.

---

# 40. Key Store Failure

Key Store가 외부화되면 새로운 Dependency가 생긴다.

```text
JWT Start
  ↓
Key Store
  ↓ Failure
```

정책:

```text
발급기 Start Fail?
Cached Key 사용?
Sign 계속 가능?
Rotation 중단?
```

을 결정해야 한다.

Private Key 가용성을 이유로 Key를 로컬 파일에 무제한 복사하는 방식은 금지한다.

---

# 41. FIG-VIII-17 — Timeout Budget Physical Layers

V장의 PDMG Current:

```text
Online Worker Wait = 5초
```

Capacity 자료 후보를 전체 계층으로 보면:

```text
Client / WebTopSuite
        │
        ▼
GSLB / L4
        │
        ▼
Apache Proxy
        │
        ▼
Tomcat Request
        │
        ▼
PDMG Worker Deadline
        │
        ▼
Hikari Connection Wait
        │
        ▼
JDBC / DB Query
```

## 41.1 목표 정합성 원칙

```text
DB/Connection의 하위 실패가
PDMG 상위 Deadline을 무한 초과하지 않아야 함
```

정확한 숫자는 Capacity Recommendation과 Current Config를 대조해 결정한다.

---

# 42. Capacity 문서의 Timeout 후보를 Current Config로 쓰지 않는다

자료 예:

```text
Apache Connect/Read
Spring Transaction
DB Query
Client
L4
```

하지만 이 값들은 시점별 Capacity Candidate다.

최종 Architecture Baseline은 다음 Evidence를 대조해야 한다.

```text
httpd.conf
vhost.conf
server.xml
application.yml
Hikari Config
MyBatis Config
JDBC Properties
L4 Console
Client Config
```

---

# 43. FIG-VIII-18 — Scale-Up vs Scale-Out

```text
[Scale-Up]
VM 32C / 256G
   ↓
Large JVM / Threads
   ↓
서버수 감소

장점
- 관리노드 감소
- 큰 Capacity

위험
- 한 노드 장애영향 증가
- GC/Thread/Native 복잡도
- 장애 시 잔여 Capacity 급감


[Scale-Out]
VM 8C/32G or 16C variants
   ↓
더 많은 Node
   ↓
L4 Distribution

장점
- 장애격리
- 점진확장
- 잔여용량 분산

비용
- Node 운영수 증가
- Session/Deploy/Monitoring 복잡도
```

## 43.1 선택 기준

```text
CPU
Memory
GC
Thread
DB Pool
Node Failure Residual Capacity
Deploy Blast Radius
License
Operation
```

만으로 결정해야 한다.

---

# 44. N+1 / Residual Capacity

HA 수량산정은 정상 TPS만 보면 안 된다.

예:

```text
4 Node
정상 각 50% 부하
  ↓
1 Node 장애
  ↓
3 Node가 전체부하 처리
```

각 Node가:

```text
100/3 = 33%씩이 아니라
정상 분배 기준 대비 부하 증가
```

를 감당해야 한다.

Capacity 화면설계는 장애 잔여 처리량을 산정 대상으로 둔다.

최종 N+1/N+2 정책은 승인 필요.

---

# 45. FIG-VIII-19 — Failure Domain Map

```text
Application Code Failure
   ↓
WAR

Framework Bean Failure
   ↓
JVM ApplicationContext

Tomcat JVM Failure
   ↓
해당 JVM의 모든 WAR

VM Failure
   ↓
해당 VM의 모든 JVM/Process

WEB Failure
   ↓
해당 WEB Route

L4 Failure
   ↓
VIP/Pool

DB Failure
   ↓
여러 AP 영향

Center Failure
   ↓
전체 Center
```

## 45.1 핵심

같은 JVM에 여러 WAR가 있으면:

```text
WAR별 장애영역 완전독립
```

이 아니다.

JVM Crash/OutOfMemory/GC Freeze는 같이 영향을 준다.

---

# 46. Business Group Isolation

인포그래픽 마스터는 Application Group A/B 분리를 필수 View로 제시한다.

그러나 본 장의 현재 검색 Evidence에서는 **PDMG의 실제 A/B 업무그룹별 Host/JVM/WAR Deployment Matrix**를 확인하지 못했다.

따라서:

```text
Application Group A
Application Group B
```

를 물리 AS-IS로 창작하지 않는다.

`[GAP-VIII-03]`

다만 설계 원칙으로는:

```text
업무부하 / 장애영향 / 배포주기
```

가 크게 다르면 JVM/VM 분리 후보가 된다.

---

# 47. JVM 하나에 여러 WAR 배치 시 고려

```text
WAR A
WAR B
   │
   └─ same JVM
```

공유:

```text
Heap
GC
Thread Pool
Process
Native Memory
JVM Crash
```

분리:

```text
Spring Context
ClassLoader [container 구조에 따라]
Application Log
Context Path
```

따라서 업무격리가 필요하면 별도 JVM 배치가 더 강한 격리다.

---

# 48. FIG-VIII-20 — Server Master Inventory Trace

최종 Server Inventory는 최소 다음 Chain을 가져야 한다.

```text
Architecture Component
  ↓
Application Group
  ↓
System / Business Code
  ↓
Environment
  ↓
Center
  ↓
Hostname
  ↓
Server Role
  ↓
VM CPU / Memory
  ↓
Middleware
  ↓
Tomcat JVM / PID
  ↓
Port / CATALINA_BASE
  ↓
WAR / Context
  ↓
Datasource / DB
  ↓
HA Group
  ↓
DR Pair
```

## 48.1 Master Row 최소 항목

```text
Organization
Environment
Center
Application Group
System Group
Business Code
Hostname
Role
OS
Server Type
CPU
Memory
Disk
IP / VIP
Apache
Tomcat
JVM
WAR
Port
DB Connection
HA Group
DR Pair
Evidence
Verification
```

---

# 49. Physical Hostname Evidence

물리 구성도에는 마케팅 개발/운영 노드들의 실제 Hostname 예가 있다.

예:

```text
sbmpcoltwb01-02
sbmpcoltws01-02
...
```

Hostname은 물리 Role를 식별하는 강한 Evidence다.

다만 PDMG `pdmg-service`의 배포 대상이 해당 Hostname임을 별도 Deployment Evidence 없이 확정하지 않는다.

---

# 50. Logical Node와 Physical Server를 분리한다

```text
Logical Node
= Architecture 실행역할

Physical Server
= 실제 Host/VM

Tomcat JVM
= Process

WAR
= Application Artifact
```

예:

```text
Marketing WAS Logical Role
   ↓
WAS Host #1/#2
   ↓
Tomcat JVM
   ↓
Business WAR
```

---

# 51. FIG-VIII-21 — Capacity vs Actual Config Drift

```text
[Capacity Baseline]
CPU / MEM
Thread
Hikari
Session
Timeout
        │
        ▼ compare
[Actual Runtime Config]
VM Inventory
server.xml
setenv.sh
application.yml
HikariConfig
L4 Config
Apache Config
        │
        ▼
[Runtime Metric]
CPU
Heap
GC
Threads
Queue
Pool
TPS
Latency
        │
        ▼
DRIFT / PASS
```

## 51.1 Drift 예

```text
Design maxThreads = 800
Actual = 1500
→ DRIFT

Design Session = 60m
Actual = 90m
→ DRIFT 또는 Baseline 불명

Design Hikari = 80
Actual = 200
→ DRIFT

Design Heap = 32G
Actual = 96G
→ DRIFT
```

실제 값은 Inventory/Runtime Evidence로 확인해야 한다.

---

# 52. Capacity Closed Loop

단순 산정표에서 끝나지 않는다.

```text
Assumption
↓
Sizing
↓
Config
↓
Load Test
↓
Runtime Metric
↓
Bottleneck
↓
Tune / Scale
↓
Updated Baseline
```

---

# 53. Performance Test Gate

최종 Parameter는 다음 Test 이후 승인한다.

```text
Normal Load
Peak
Stress
Soak
Failover
One-node-down
DB Slow
External Slow
Session Load
GC Pressure
Timeout
Queue Saturation
```

특히:

```text
Node Down + Peak
```

을 통과해야 HA Capacity가 증명된다.

---

# 54. FIG-VIII-22 — Security / Network Zone Boundary

```text
Client
   │ TLS
   ▼
GSLB / L4
   │
   ▼
WEB
   │ Proxy
   ▼
WAS / Business
   │
   ├─ JWT Verify
   ├─ Service Authorization
   └─ TCF
   │
   ▼
DB Network
```

별도:

```text
pdmg-jwt
   │
   ├─ Key Store
   ├─ JWKS
   ├─ Refresh/Denylist DB
   └─ Internal SSO Caller
```

## 54.1 확인 필요

```text
TLS Termination Point
WEB→WAS TLS
mTLS
Firewall Rule
Gateway
Direct WAS Port Access
Admin Port
JWKS Exposure Scope
Key Store Network
```

현재 Source에서 전부 확정할 수 없다.

---

# 55. Business WAR Direct Access

VII장 보안 원칙:

```text
Gateway/L4를 우회하여
Business WAR 직접접근 가능?
```

이 Physical Network에 따라:

```text
Gateway만 JWT Verify
```

가 안전한지가 결정된다.

현재 PDMG는 Business `DefaultFilter`에서 검증하기 때문에 Defense-in-depth 후보와 정합적이다.

Direct Port 차단 여부는 Network Evidence가 필요하다.

---

# 56. JWT Server Scale-Out

JWT 발급 Server를 2대 이상 두면 현재 임시 Key 생성은 사용할 수 없다.

TO-BE:

```text
L4
├─ JWT #1 ─┐
└─ JWT #2 ─┴─ Same Active Signing Key / kid
                │
                ▼
             Key Store
```

JWKS:

```text
L4/JWKS VIP
→ 어느 Instance라도 동일 Public JWK Set
```

이어야 한다.

---

# 57. Refresh / Denylist State HA

JWT는 서명검증만 보면 Stateless지만 다음 DB State가 있다.

```text
Refresh Token Hash
Rotation
Family
Revocation
Denylist
User/Policy
```

따라서 JWT Server HA는:

```text
JWT Process HA
+
Security State DB HA
```

둘 다 필요하다.

---

# 58. DR Token Compatibility

DR 전환 후 기존 Access Token을 계속 허용하려면:

```text
same logical issuer
same/current public keys
compatible clock
denylist state
```

가 필요하다.

기존 Refresh Token을 계속 쓰려면:

```text
Refresh Hash DB DR
Token Family State DR
```

가 필요하다.

이 정책은 현재 `[OPEN]`.

---

# 59. Clock / NTP

Session 만료, JWT `iat/exp`, Internal SSO Timestamp, Log Correlation은 시간동기화에 의존한다.

따라서:

```text
WEB
WAS
JWT
DB
L4/Monitoring
```

간 NTP 정합성이 중요하다.

Capacity/Session 자료에서도 NTP를 Session Cluster 기준으로 요구한다.

---

# 60. DR와 Clock

DR Center의 시간 차이가 크면:

```text
JWT exp/iat
SSO HMAC Timestamp
Session Expiry
Log Sequence
```

에서 오류가 발생한다.

DR Test에는 Clock Sync 검증을 포함한다.

---

# 61. Log Disk / GC Log / Access Log

물리 Architecture는 CPU/Memory만으로 닫히지 않는다.

서버별 최소:

```text
OS Disk
Application Disk
Log Disk
GC Log
Access Log
Dump
Temp
```

Capacity가 필요하다.

현재 PDMG별 실제 Log Disk Size/Retention은 IX장 Source에서 확인한다.

---

# 62. Heap Dump / Thread Dump

장애 대응을 위해:

```text
Heap Dump
Thread Dump
GC Log
JFR/APM
```

수집위치/용량/보안이 필요하다.

OOM시 자동 Heap Dump는 개인정보/Token이 Memory에 포함될 수 있으므로 접근통제도 필요하다.

---

# 63. Backup ≠ DR

물리도에는 보안/백업/OS 백업 범례가 존재하지만:

```text
Backup
≠
HA
≠
DR
```

이다.

```text
HA
= 서비스 계속성

DR
= Center 재해대응

Backup
= 데이터/시스템 복구자산
```

별도 RPO/RTO/복구절차가 필요하다.

---

# 64. Database HA / DR

본 장은 PDMG Application 중심이므로 RDW/ADW Appliance의 내부 HA를 일반론으로 채우지 않는다.

물리도 Source는:

```text
RDW / ADW Appliance
```

와 운영/DR 역할을 보여준다.

하지만 정확한:

```text
RAC
Data Guard
Replication
SCAN
VIP
```

구성은 DB Architecture Evidence로 확정해야 한다.

`[GAP-VIII-04]`

---

# 65. PDMG Database Dependency

현재 PDMG에서:

```text
Business
→ RDW

JWT
→ User/Token/Refresh/Denylist DB
```

가 필요하다.

두 DB Dependency의 가용성 요구는 다를 수 있다.

예:

```text
RDW Down
→ Business 거래 실패

JWT State DB Down
→ Login/Refresh/Revoke 영향
→ Access Token 검증은 JWKS만으로 가능할 수도 있음
```

정확한 Fail-open/closed 정책은 VII/DB HA Decision과 연계한다.

---

# 66. AP Failure vs JWT Failure

```text
Business WAS Failure
→ 다른 WAS로 Routing

JWT Issuer Failure
→ 기존 Access Token 검증 가능?
   YES if JWKS cached + key valid
→ 신규 Login/Refresh는 영향
```

따라서 JWT Server HA와 Business Server HA의 서비스 영향이 다르다.

---

# 67. JWKS Failure

TO-BE Verifier가 Cached Public Key를 사용하면:

```text
JWKS Endpoint 일시 Down
BUT
cached kid available
→ 기존 Token 검증 가능 후보
```

Unknown kid/New Rotation 상황에서는:

```text
JWKS 필요
```

하므로 가용성 정책이 필요하다.

---

# 68. Capacity and Security Interaction

보안은 Capacity에도 영향을 준다.

```text
JWT Verify
Password BCrypt
Denylist Lookup
Refresh Rotation
Audit Log
```

특히 로그인 BCrypt는 일반 Business GET보다 CPU 비용이 클 수 있다.

JWT/JWKS Cache와 Denylist DB Call을 Capacity Test에 포함한다.

---

# 69. Capacity and Session Interaction

Session이 길수록:

```text
TPS 직접 증가 X
Active Session Memory 증가 O
DeltaManager Replication 대상 증가 O
```

따라서 60→90분 변경은:

```text
Thread Count
```

보다는:

```text
Memory / Replication / Security Exposure
```

영향이 크다.

---

# 70. Capacity and PDMG Worker Interaction

PDMG Worker 20은:

```text
동시 PDMG TCF Business Execution
```

을 제한한다.

만약 TPS 1,200, 평균 Worker 점유시간 100ms라면 단순 Little's Law 관점에서 평균 동시실행은 약 120개가 필요할 수 있지만, **100ms는 현재 Source Fact가 아니므로 계산값을 Baseline으로 쓰지 않는다.**

따라서 Worker 20의 적정성은 실제:

```text
Worker execution time distribution
Queue wait
Timeout rate
DB hold time
```

으로 검증해야 한다.

---

# 71. Worker vs Tomcat Thread 위험

Tomcat 800 Thread 후보인데 PDMG Worker 20이라면:

```text
많은 Request Thread
→ Worker Queue 대기
→ Tomcat Thread도 Future.get 대기
```

할 수 있다.

반대로 Worker를 과도하게 늘리면:

```text
DB 압력
```

이 커진다.

따라서 두 Pool을 별도 산정하되 통합 Test한다.

---

# 72. FIG-VIII-23 — HA/DR Decision & GAP Map

```text
[Center]
의왕 Main / 안성 DR
   │
   ├─ RPO/RTO                 [GAP]
   ├─ Automatic Failover      [OPEN]
   └─ Test Runbook            [GAP]

[WEB/WAS]
   ├─ Node Redundancy         [FACT-SOURCE]
   ├─ Apache/Tomcat Config    [VERIFY]
   └─ Health Check            [VERIFY]

[Session]
   ├─ DeltaManager In-Center  [BASELINE]
   ├─ Cross-Center Replication X
   └─ 60m vs 90m              [CONFLICT]

[JWT]
   ├─ Multi-instance Key      [GAP]
   ├─ JWKS HA                 [GAP]
   ├─ Refresh/Denylist DR     [GAP]
   └─ Existing Token at DR    [OPEN]

[Capacity]
   ├─ 8C/16C/32C Variants     [CANDIDATE]
   ├─ Thread/Hikari Conflict  [VERIFY]
   └─ Worker 20/Queue100      [CURRENT PDMG]
```

---

# 73. Current GAP

| ID | GAP | 영향 |
|---|---|---|
| GAP-VIII-01 | PDMG Worker 20/Queue100의 목표 TPS 적정성 근거 부족 | Performance |
| GAP-VIII-02 | 최종 DR RPO/RTO 미확정 | DR |
| GAP-VIII-03 | Application Group A/B 실제 Host/JVM Deployment Matrix 미확보 | Isolation |
| GAP-VIII-04 | RDW/ADW 실제 DB HA/DR 상세 미확정 | Data Availability |
| GAP-VIII-05 | PDMG Module→운영 Host/JVM/WAR/Port Mapping 미확정 | Deployment |
| GAP-VIII-06 | Node별 Apache/Tomcat 실제 Version/Config 미확정 | Middleware |
| GAP-VIII-07 | Session 60분 vs 90분 Baseline 충돌 | Session |
| GAP-VIII-08 | SingleView Hikari 70~80 vs 100~120 충돌 | DB Capacity |
| GAP-VIII-09 | 실제 JVM Heap/GC Option과 Capacity Candidate 대조 미완료 | JVM |
| GAP-VIII-10 | 실제 Tomcat maxThreads/acceptCount/maxConnections 대조 미완료 | WAS |
| GAP-VIII-11 | 실제 Hikari maximumPoolSize/connectionTimeout 대조 미완료 | DB Pool |
| GAP-VIII-12 | Client/L4/Apache/PDMG/DB Timeout Matrix 최신화 미완료 | Timeout |
| GAP-VIII-13 | JWT Private Key 중앙 Store 미구현/미확정 | Security HA |
| GAP-VIII-14 | JWKS HA/Cache/DR 구조 미확정 | Authentication |
| GAP-VIII-15 | Refresh/Denylist State HA/DR 미확정 | Token Lifecycle |
| GAP-VIII-16 | Direct WAS Access/Network Bypass 통제 미확정 | Security |
| GAP-VIII-17 | TLS termination/mTLS 구간 미확정 | Transport Security |
| GAP-VIII-18 | DR Center에서 기존 Token 유효성 정책 미확정 | Security DR |
| GAP-VIII-19 | Server Master Inventory와 Architecture 모델 연결 미완료 | Traceability |
| GAP-VIII-20 | Capacity Baseline과 Runtime Metric Closed Loop 미완료 | Governance |

---

# 74. Current RISK

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-VIII-01 | 용량산정 Variant를 최신 운영값으로 오인 | High |
| RISK-VIII-02 | Session 60/90 정책 Drift | High |
| RISK-VIII-03 | maxThreads 과대설정 | High |
| RISK-VIII-04 | Hikari 과대설정으로 DB Session 폭증 | High |
| RISK-VIII-05 | Worker 과소설정으로 Queue/Timeout | High |
| RISK-VIII-06 | Worker 과대설정으로 DB 압박 | High |
| RISK-VIII-07 | 대형 JVM 장애 Blast Radius | High |
| RISK-VIII-08 | VM Memory 전체를 Heap으로 사용 | Critical |
| RISK-VIII-09 | 센터간 Session 미복제 인지 없이 무중단 DR 가정 | High |
| RISK-VIII-10 | JWT 인스턴스마다 다른 RSA Key | Critical |
| RISK-VIII-11 | same kid / different key | Critical |
| RISK-VIII-12 | Refresh/Denylist DB DR 미비 | High |
| RISK-VIII-13 | Gateway/L4 우회 직접 WAS 접근 | Critical |
| RISK-VIII-14 | Direct Port/TLS 경계 미정 | High |
| RISK-VIII-15 | 실제 Config와 Capacity 설계 Drift | High |
| RISK-VIII-16 | 여러 WAR 공유 JVM에서 장애격리 오판 | High |
| RISK-VIII-17 | DR Test 없이 문서상 이중화만 존재 | Critical |
| RISK-VIII-18 | Heap/GC/Thread Dump 보안관리 미비 | Medium/High |

---

# 75. OPEN Issue

| ID | 질문 |
|---|---|
| OPEN-VIII-01 | PDMG Production JVM의 실제 GC와 Xms/Xmx는 무엇인가 |
| OPEN-VIII-02 | 최종 Session Idle은 60분인가 90분인가 |
| OPEN-VIII-03 | PDMG service/jwt/ui/om의 실제 Host/JVM/Port는 무엇인가 |
| OPEN-VIII-04 | 마케팅 WEB/WAS와 PDMG Artifact의 공식 매핑은 무엇인가 |
| OPEN-VIII-05 | 하나의 Tomcat JVM에 몇 WAR를 배치하는가 |
| OPEN-VIII-06 | Application Group A/B의 공식 배치정책은 무엇인가 |
| OPEN-VIII-07 | 실제 Apache VirtualHost/Proxy/Timeout 설정은 무엇인가 |
| OPEN-VIII-08 | 실제 Tomcat maxThreads/minSpare/acceptCount/maxConnections는 무엇인가 |
| OPEN-VIII-09 | 실제 Hikari Pool/Timeout/Lifetime은 무엇인가 |
| OPEN-VIII-10 | PDMG Worker 20/100은 운영에서도 유지하는가 |
| OPEN-VIII-11 | JWKS/Key Store의 Production 제품/위치는 무엇인가 |
| OPEN-VIII-12 | JWT Issuer를 몇 Instance로 운영할 것인가 |
| OPEN-VIII-13 | DR에서 기존 Access Token을 유지할 것인가 |
| OPEN-VIII-14 | DR에서 Refresh/Denylist State를 어떻게 복구하는가 |
| OPEN-VIII-15 | 센터 전환은 자동인가 수동인가 |
| OPEN-VIII-16 | DR RPO/RTO는 무엇인가 |
| OPEN-VIII-17 | L4 Health Check/Sticky/TTL 실제정책은 무엇인가 |
| OPEN-VIII-18 | TLS 종료와 내부암호화 구간은 어디인가 |
| OPEN-VIII-19 | Server Inventory의 SSOT 시스템/파일은 무엇인가 |
| OPEN-VIII-20 | Capacity 승인값의 Version Owner는 누구인가 |

---

# 76. ADR 후보

| ADR | 결정 주제 |
|---|---|
| ADR-VIII-01 | Session Idle Timeout 60 vs 90 최종 Baseline |
| ADR-VIII-02 | WEB/WAS/Tomcat Production Standard Mapping |
| ADR-VIII-03 | PDMG Artifact Deployment Unit / JVM Isolation |
| ADR-VIII-04 | Application Group A/B Physical Isolation |
| ADR-VIII-05 | VM Size / Scale-Out 표준 |
| ADR-VIII-06 | Tomcat Thread Capacity Baseline |
| ADR-VIII-07 | Hikari Pool Capacity Baseline |
| ADR-VIII-08 | PDMG Worker Pool Capacity Baseline |
| ADR-VIII-09 | JWT Key Store / Multi-instance Architecture |
| ADR-VIII-10 | JWKS HA/Cache/DR |
| ADR-VIII-11 | Refresh/Denylist DB HA/DR |
| ADR-VIII-12 | Gateway/Business Direct Access 통제 |
| ADR-VIII-13 | TLS/mTLS Boundary |
| ADR-VIII-14 | DR RPO/RTO/Failover Mode |
| ADR-VIII-15 | Server Master Inventory SSOT |
| ADR-VIII-16 | Capacity→Config→Runtime Drift Gate |

---

# 77. Infrastructure Architecture Rules

## 77.1 Must

1. Server/VM, Tomcat JVM, WAR를 같은 개념으로 쓰지 않는다.
2. `pdmg-fw`를 별도 Physical Server로 그리지 않는다.
3. Local 8080/8090을 운영 Port로 승격하지 않는다.
4. 물리도에 WEB가 있다고 node별 Apache 설치를 Source 확인 없이 FACT로 쓰지 않는다.
5. Capacity Candidate를 Actual Config로 쓰지 않는다.
6. Session 60/90 충돌을 숨기지 않는다.
7. Hikari Variant 충돌을 숨기지 않는다.
8. VM Memory 전체를 JVM Heap으로 사용하지 않는다.
9. Tomcat maxThreads와 PDMG Worker/Hikari를 동일 Pool로 쓰지 않는다.
10. 센터 내부 DeltaManager를 센터간 Session Replication으로 표현하지 않는다.
11. 센터간 Session 미복제 상태에서 무중단 Session DR을 보장한다고 쓰지 않는다.
12. DR RPO/RTO를 일반 금융권 값으로 창작하지 않는다.
13. per-process RSA Key를 Production JWT HA 구조로 인정하지 않는다.
14. 같은 `kid`에 서로 다른 RSA Key를 허용하지 않는다.
15. Direct WAS Port 접근 가능성을 검증 없이 무시하지 않는다.
16. HA=DR=Backup이라고 설명하지 않는다.
17. 서버 수를 Capacity 문서만 보고 최신 Inventory 수량으로 확정하지 않는다.
18. 실제 Config와 Design Baseline의 Drift를 검증한다.

## 77.2 Should

1. `Hostname→JVM→WAR→Datasource→HA/DR` Master Inventory를 유지한다.
2. VM/JVM/WAR별 장애 Blast Radius를 명시한다.
3. Tomcat Thread/PDMG Worker/Hikari/DB Session을 통합 Capacity Test한다.
4. Session 객체 크기를 자동 측정한다.
5. Node Failure + Peak Load 시험을 수행한다.
6. JWT Key/JWKS/Refresh State를 DR Test에 포함한다.
7. Timeout Matrix를 UI→DB까지 한 장으로 관리한다.
8. Runtime JVM/Thread/Pool 값을 Capacity Baseline과 자동 비교한다.
9. Server Inventory를 Architecture Model/X장 Trace와 연결한다.
10. Physical Change는 ADR/Inventory/Monitoring을 함께 갱신한다.

---

# 78. Capacity Verification Test

## 78.1 Normal

```text
Expected TPS
Normal Node Count
CPU/Heap/Thread/Pool
```

검증.

## 78.2 Peak

```text
10% 동시 요청률
1,200 TPS 후보
```

가정 기준 검증.

## 78.3 Stress

```text
15%
1,800 TPS 후보
```

에서 Saturation 지점 측정.

## 78.4 One WAS Down

```text
Peak
+
WAS 1대 Down
```

잔여노드가 SLA 유지하는지 검증.

## 78.5 Hikari Saturation

Pool를 제한하고:

```text
pending
worker
timeout
```

전파 확인.

## 78.6 DB Slow

SQL 지연이:

```text
Worker Queue
Tomcat Thread
Timeout
```

으로 전파되는지 확인.

## 78.7 Session Load

```text
43k~47k Session 후보
```

에 대해 Heap/Replication Traffic 측정.

최종 Session 60/90 결정 후 반복.

## 78.8 JWT Restart

현재/TO-BE:

```text
JWT instance restart
existing token verify
```

검증.

## 78.9 JWT Multi-instance

```text
#1/#2 JWKS identical?
kid/key consistent?
```

검증.

## 78.10 Center DR

```text
의왕 장애 가정
→ 안성 전환
→ Login
→ Business
→ DB
→ JWT
→ Session
→ External
```

전체 Runbook Test.

---

# 79. Architecture Conformance Rule 후보

```text
RULE-VIII-01
Every production WAS host must map to a logical application role

RULE-VIII-02
Every Tomcat JVM must have unique instance identity and ports

RULE-VIII-03
Every WAR must map to an approved JVM

RULE-VIII-04
pdmg-fw must not be deployed as standalone remote server unless architecture changes

RULE-VIII-05
Actual maxThreads must be within approved capacity baseline

RULE-VIII-06
Actual Hikari pool must be within approved baseline

RULE-VIII-07
Actual session timeout must equal approved session baseline

RULE-VIII-08
Cross-center DeltaManager replication is prohibited unless explicitly approved

RULE-VIII-09
JWT issuer instances must expose consistent JWKS

RULE-VIII-10
Production private key must not be generated ephemerally per process

RULE-VIII-11
Every HA service must have node-failure test evidence

RULE-VIII-12
Every DR service must have DR pair/runbook evidence

RULE-VIII-13
No direct public/client access to protected WAS port

RULE-VIII-14
Capacity candidate values cannot be marked FACT without config/runtime evidence

RULE-VIII-15
Server inventory must include evidence and verification status
```

---

# 80. IX장 운영 Metric Handoff

IX장으로 넘길 최소 인프라 Metric:

```text
WEB
- active connection
- response status
- upstream error
- proxy latency

Tomcat
- currentThreads
- busyThreads
- maxThreads
- accept queue
- connection count

PDMG Worker
- active
- queue depth
- rejected
- timeout

JVM
- heap used
- old gen
- GC pause
- full GC
- metaspace
- thread count

Hikari
- active
- idle
- pending
- timeout

DB
- session
- active SQL
- wait
- slow query

Session
- active sessions
- average size
- replication error

JWT
- instance health
- JWKS health
- key id
- denylist DB
- refresh DB

HA/DR
- pool member health
- node failover
- DR readiness
```

현재 OM에 구현되어 있다고 단정하지 않는다.

---

# 81. Traceability

## 81.1 VII → VIII

```text
VII RSA Key
    ↓
VIII Key Store / JWT Instance

VII JWKS
    ↓
VIII VIP / HA / Cache

VII Refresh/Denylist
    ↓
VIII Security State DB HA/DR

VII Principal
    ↓
VIII Business WAR Runtime
```

## 81.2 VIII → IX

```text
Server/JVM/WAR
    ↓
Monitoring

Thread/Pool
    ↓
Metric

HA/DR
    ↓
Alert / Runbook

Capacity
    ↓
Dashboard / Threshold
```

## 81.3 VIII → X

```text
ServiceId
→ WAR
→ JVM
→ Hostname
→ DB
```

Closed Loop가 필요하다.

---

# 82. FIG-VIII-24 — IX장 Handoff

```text
VIII. Infrastructure / WAS / Capacity / HA / DR
        │
        ├─ Center / GSLB / L4
        ├─ WEB / Apache
        ├─ WAS / Tomcat JVM
        ├─ WAR
        ├─ JVM Heap / GC
        ├─ Tomcat Thread
        ├─ PDMG Worker
        ├─ Hikari
        ├─ Session / DeltaManager
        ├─ JWT / JWKS / Key
        ├─ HA
        ├─ DR
        └─ Server Inventory
                 │
                 ▼
IX. DevOps / OM / Observability
                 │
                 ├─ Source / Build
                 ├─ Artifact
                 ├─ Deploy
                 ├─ Config Promotion
                 ├─ OM
                 ├─ APM
                 ├─ JVM Metric
                 ├─ Thread / Queue
                 ├─ Hikari / DB
                 ├─ GUID / ServiceId
                 ├─ Log / Alert
                 ├─ Runbook
                 └─ Runtime Evidence
```

## IX장에서 반드시 답할 질문

1. Source가 어떤 Build/Artifact로 만들어지는가?
2. PDMG FW/JWT/Service/UI는 어떤 Deployment Unit인가?
3. Config는 환경별로 어떻게 승격되는가?
4. Secret/Key는 CI/CD와 어떻게 분리되는가?
5. OM은 실제 Source에서 무엇을 구현하고 있는가?
6. `pdmg-om`의 Current Runtime/Package/기능은 무엇인가?
7. Tomcat Thread, PDMG Worker, Hikari Pool을 어떤 Dashboard로 보는가?
8. GUID+ServiceId로 Log/APM/SQL/ImageLog를 어떻게 연결하는가?
9. Timeout/Overload/JWT/Error/GC/Pool 장애를 어떤 Alert로 감지하는가?
10. Node/Center Failover Runbook은 어디에 있는가?
11. Capacity Baseline과 Runtime Metric의 Drift를 어떻게 탐지하는가?
12. 배포 후 Runtime Evidence가 Architecture Gate로 어떻게 돌아오는가?

---

# 83. 검증 체크리스트

## 83.1 Physical

- [x] 의왕/안성 센터가 표시되는가
- [x] WEB/WAS/AP/DB Role가 구분되는가
- [x] Server/JVM/WAR가 구분되는가
- [x] PDMG Physical Mapping Gap를 숨기지 않았는가
- [x] pdmg-fw를 Server로 그리지 않았는가

## 83.2 Capacity

- [x] 36,000 사용자 기반 산정축이 있는가
- [x] 600/1,200/1,800 TPS Variant를 Capacity Candidate로 표시했는가
- [x] 8C/16C/32C Variant를 구분했는가
- [x] maxThreads/Hikari/Heap를 Actual이라고 쓰지 않았는가
- [x] Worker 20과 Tomcat Thread를 구분했는가
- [x] Hikari Variant Conflict를 표시했는가

## 83.3 Session

- [x] 60/90분 Conflict가 표시되는가
- [x] DeltaManager 센터 내부 원칙이 있는가
- [x] 센터 간 미복제가 표시되는가
- [x] 재로그인 DR 방향을 설명했는가
- [x] JWT와 HttpSession을 동일시하지 않았는가

## 83.4 Security Infrastructure

- [x] JWT Multi-instance Key 문제를 표시했는가
- [x] JWKS HA가 있는가
- [x] Refresh/Denylist State HA가 있는가
- [x] Direct WAS Access를 Open으로 남겼는가
- [x] Private Key 원문/제품을 창작하지 않았는가

## 83.5 HA/DR

- [x] Node Failure와 Center Failure가 분리되는가
- [x] HA/DR/Backup을 분리했는가
- [x] RPO/RTO를 창작하지 않았는가
- [x] DR Test Requirement가 있는가

## 83.6 Inventory

- [x] Hostname→JVM→WAR→DB Chain이 있는가
- [x] Design vs Actual Config Drift가 있는가
- [x] Runtime Metric Closed Loop가 IX장으로 넘어가는가

---

# 84. Completion Gate

```text
Figure Plan                         24
실제 Text Figure                   24

Logical→Physical                   PASS
Main/DR Center                     PASS
WEB/WAS Working Baseline           PASS
Server/JVM/WAR                     PASS
PDMG Deployment Mapping            CONDITIONAL
Marketing Physical Evidence        PASS
JVM Memory                         PASS
Thread/Worker/Hikari               PASS
Capacity Assumption                PASS
CPU/Memory Variants                PASS
Hikari/DB                          CONDITIONAL
Session/JWT                        PASS
DeltaManager                       PASS
Node Failover                      PASS
Center DR                          CONDITIONAL
JWT/JWKS/Key HA                    CONDITIONAL
Timeout Physical                   CONDITIONAL
Scale-Up/Out                       PASS
Failure Domain                     PASS
Inventory Trace                    PASS
Config Drift                       PASS
Security Network                   CONDITIONAL
HA/DR GAP                          PASS
IX Handoff                         PASS

최종 Session 임의선택               0건
RPO/RTO 창작                       0건
Local Port→운영 Port 승격           0건
Capacity Candidate→Actual 승격      0건
pdmg-fw Standalone Server 오기재    0건
```

**판정: CONDITIONAL PASS**

## PASS 전환 조건

```text
Condition-VIII-01
PDMG Production Deployment Manifest 확보
Host/JVM/WAR/Port/VHost/L4 Mapping

Condition-VIII-02
Session 60/90 최종 승인 Baseline 확정

Condition-VIII-03
Actual server.xml / JVM Option / Hikari Config 수집

Condition-VIII-04
Capacity Baseline Version 1개 승인

Condition-VIII-05
PDMG Worker 20/Queue100 부하시험 근거 확보

Condition-VIII-06
JWT Multi-instance + Central Key Store 설계 확정

Condition-VIII-07
JWKS HA/Cache/DR Integration Test

Condition-VIII-08
Refresh/Denylist DB HA/DR 확정

Condition-VIII-09
Network Direct WAS Access / TLS Boundary 검증

Condition-VIII-10
DR RPO/RTO + Failover Runbook 승인

Condition-VIII-11
Node Failure + Peak Load Test

Condition-VIII-12
Server Master Inventory와 Architecture Model 연결
```

---

# 85. 장 최종 평가

VIII장은 NSIGHT/PDMG의 논리·Runtime 구조를 **실제 물리 실행 단위와 Capacity/HA/DR 관점**으로 전환했다.

가장 중요한 결론은 다음과 같다.

> **NSIGHT 온라인 물리 Working Baseline은 GSLB → L4 → WEB/Apache → WAS/Tomcat JVM → Business WAR → Hikari/MyBatis → RDW/ADW이며, Server/VM·JVM·WAR는 서로 다른 관리단위다.**

> **물리 구성도는 의왕 주센터와 안성 DR센터, 내부 GSLB 및 마케팅 WEB/WAS 이중화 역할을 보여 주지만 PDMG Artifact가 어느 Host/JVM에 실제 배포되는지는 아직 Deployment Evidence로 닫혀 있지 않다.**

> **`pdmg-fw`는 독립 물리 Server가 아니라 `pdmg-service` Runtime 내부 Framework다.**

> **Tomcat Request Thread, PDMG Worker 20/Queue100, Hikari Pool, Oracle Session은 서로 다른 자원 Pool이며 숫자를 동일하게 맞추는 구조가 아니다.**

> **Capacity 문서는 36,000 전체 사용자와 600/1,200/1,800 TPS 후보, 8C/16C/32C VM Variant를 제공하지만 이 값들은 최종 Runtime Config가 아니라 성능시험으로 보정해야 하는 설계 후보다.**

> **Session Baseline에는 60분과 90분 자료가 공존하므로 현재 정의서에서 하나를 임의로 선택하지 않았으며, DeltaManager는 센터 내부에 한정하고 센터간 Session 복제는 기본 미적용이라는 원칙은 반복적으로 확인된다.**

> **따라서 Center DR에서 HttpSession을 그대로 보존하는 것보다 재로그인을 기본으로 보는 자료가 존재하며, JWT/Refresh/Denylist/Key State의 DR은 별도로 설계해야 한다.**

> **VII장에서 확인한 Process-local RSA Key 생성은 다중 JWT Instance/DR과 양립하지 않으므로 중앙 Key Store와 Versioned kid, 동일 JWKS가 Production HA의 필수 보완점이다.**

> **최종 Infrastructure Baseline은 Capacity 문서가 아니라 `Server Inventory + Apache/Tomcat/JVM/Hikari 실제 Config + Load Test + Runtime Metric`이 서로 일치할 때 닫힌다.**

다음 IX장에서는 이 물리 실행환경이 실제로 **어떻게 Build/Deploy되고, 운영자가 어떻게 관측하고, 장애·Drift를 어떻게 검증하는지**를 정의한다.

```text
VIII
Physical / Capacity / HA / DR
          ↓
IX
DevOps / OM / Observability / Runtime Evidence
```
