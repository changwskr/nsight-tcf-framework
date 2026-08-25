# NSIGHT 미들웨어 아키텍처 정의서

## 1. 문서 목적

본 문서는 농협 상호금융 NSIGHT 정보계의 WEB/WAS 미들웨어 구조를 현재까지 확인된 개발·운영·DR 구성도와 미들웨어 점검 결과를 기준으로 통합 정리한 문서이다.

본 문서의 핵심 목적은 다음과 같다.

- WEB 서버와 Apache HTTP Server의 관계 정의
- WAS 서버와 Tomcat JVM Instance의 관계 정의
- 이미지에서 사용된 `Container` 용어의 기술적 의미 확정
- Apache Service Port와 Tomcat Connector Port의 관계 정의
- Application/WAR 배치 구조 정의
- 운영 HA 및 DR 구조 정의
- JVM/Thread/HikariCP 자원관리 단위 정의
- 미들웨어 인벤토리 관리 모델 정의
- 추가 확인이 필요한 설정 파일과 Architecture Gate 항목 정의

---

# 2. 기준 정의

## 2.1 핵심 기술 기준

| 구분 | NSIGHT 기준 |
|---|---|
| WEB | Apache HTTP Server |
| WAS | Apache Tomcat |
| WEB Server | Apache가 실행되는 물리/가상 서버 |
| WAS Server | Tomcat JVM이 실행되는 물리/가상 서버 |
| Apache Instance | 하나의 Apache HTTP Server 실행 인스턴스 |
| Tomcat JVM Instance | 독립 Java Process로 실행되는 Tomcat 인스턴스 |
| Container | 본 문서에서는 독립 Tomcat JVM Instance와 동일한 의미 |
| Application | Tomcat JVM에서 실행되는 주요 업무 애플리케이션 |
| WAR | Application 배포 Artifact |
| Service Port | L4/Client가 Apache에 접근하는 포트 |
| Connector Port | Apache가 Tomcat JVM으로 전달할 때 사용하는 Tomcat HTTP 포트 |
| DB Pool | JVM/Application 단위 HikariCP Connection Pool |
| HA Pool | 동일 Application을 제공하는 복수 JVM의 서비스 묶음 |

핵심 정의는 다음과 같다.

> **NSIGHT WAS의 표준 실행단위는 Tomcat JVM Instance이며, 하나의 WAS Server(VM) 안에 1개 이상의 독립 Tomcat JVM Instance가 존재할 수 있다.**

---

# 3. 전체 미들웨어 Big Picture

```text
                         사용자 / APP
                              │
                              ▼
                         내부망 L4
                              │
                    Service Port
                  9000 / 9001 / ...
                              │
                 ┌────────────┴────────────┐
                 ▼                         ▼
            WEB Server #1             WEB Server #2
            Apache Instance           Apache Instance
                 │                         │
                 │       Cross Route       │
                 ├────────────┬────────────┤
                 │            │            │
                 ▼            ▼            ▼
           WAS Server #1                 WAS Server #2
           ┌─────────────┐               ┌─────────────┐
           │ JVM #1      │               │ JVM #1      │
           │ :19000      │               │ :19000      │
           │ App A       │               │ App A       │
           ├─────────────┤               ├─────────────┤
           │ JVM #2      │               │ JVM #2      │
           │ :19001      │               │ :19001      │
           │ App B       │               │ App B       │
           └──────┬──────┘               └──────┬──────┘
                  │                             │
                  └─────────────┬───────────────┘
                                ▼
                            RDW / ADW
                                │
                         Primary / Standby
```

전체 계층은 다음과 같다.

```text
L4
 ↓
Apache
 ↓
Tomcat JVM
 ↓
Application / WAR
 ↓
Spring / TCF
 ↓
HikariCP
 ↓
RDW / ADW
```

---

# 4. WEB 아키텍처

## 4.1 Apache 표준 구조

WEB Server에는 Apache HTTP Server를 배치한다.

```text
WEB Server / VM
└─ Apache Instance
   ├─ Listen 9000
   ├─ Listen 9001
   ├─ Listen 9010
   └─ Listen 9011
```

하나의 Apache Instance는 여러 포트를 동시에 Listen할 수 있다.

예:

```apache
Listen 9000
Listen 9001
Listen 9010
Listen 9011
```

포트별 VirtualHost 또는 Proxy/Worker 정책을 통해 각각 다른 Tomcat JVM으로 전달할 수 있다.

```apache
<VirtualHost *:9000>
    ProxyPass        / http://was01:19000/
    ProxyPassReverse / http://was01:19000/
</VirtualHost>

<VirtualHost *:9001>
    ProxyPass        / http://was01:19001/
    ProxyPassReverse / http://was01:19001/
</VirtualHost>
```

## 4.2 Apache 포트 분리 원칙

```text
Apache
 ├─ 9000  → Tomcat JVM #1 : 19000
 ├─ 9001  → Tomcat JVM #2 : 19001
 ├─ 9010  → Tomcat JVM #3 : 19010
 └─ 9011  → Tomcat JVM #4 : 19011
```

서비스 포트와 Connector Port를 분리하여 관리한다.

| 구분 | 예 | 역할 |
|---|---:|---|
| Apache Service Port | 9000 | L4/Client → Apache |
| Apache Service Port | 9001 | 다른 Application 진입 |
| Tomcat Connector Port | 19000 | Apache → JVM #1 |
| Tomcat Connector Port | 19001 | Apache → JVM #2 |

---

# 5. WAS 아키텍처

## 5.1 WAS Server와 Tomcat JVM 분리

WAS Server는 VM/서버 자원 단위이고 Tomcat JVM은 실행 Process 단위이다.

```text
WAS Server / VM
│
├─ Tomcat JVM Instance #1
│   ├─ CATALINA_BASE #1
│   ├─ Connector : 19000
│   ├─ Application A
│   ├─ Heap
│   ├─ Thread Pool
│   └─ Hikari Pool
│
└─ Tomcat JVM Instance #2
    ├─ CATALINA_BASE #2
    ├─ Connector : 19001
    ├─ Application B
    ├─ Heap
    ├─ Thread Pool
    └─ Hikari Pool
```

## 5.2 Container 용어 정의

구성도에 표시된 `Container #1`, `Container #2`는 본 NSIGHT 아키텍처 문서에서 다음처럼 표준화한다.

```text
Container
   =
Tomcat JVM Instance
```

따라서 앞으로 구성도에서는 가능하면:

```text
WAS(tomcat)
 └─ Container #1
```

보다 다음 표현을 사용한다.

```text
WAS Server
 └─ Tomcat JVM Instance #1
```

---

# 6. Application 배치 원칙

기본 원칙은 다음과 같다.

> **1 Tomcat JVM = 1 주요 Application 실행단위**

예:

```text
WAS01
├─ JVM01 : 마케팅플랫폼 공통
└─ JVM02 : 마케팅플랫폼 화면
```

이 구조의 장점은 다음과 같다.

- JVM 장애 격리
- Heap 독립 관리
- Thread 독립 관리
- Hikari Pool 독립 관리
- Application별 재기동 가능
- 포트별 트래픽 통제 가능
- Application별 성능분석 가능

---

# 7. 개발환경 구성

개발환경은 운영보다 자원 통합도가 높다.

대표적으로 마케팅플랫폼 개발 WAS는 다음 구조로 해석한다.

```text
개발 WAS #1
│
├─ JVM01 : 마케팅 공통      :19000
├─ JVM02 : 마케팅 화면      :19001
├─ JVM03 : 미니싱글뷰 공통  :19010
└─ JVM04 : 미니싱글뷰 화면  :19011
```

즉:

```text
개발환경
= Consolidation 중심
```

으로 정의한다.

개발환경은 자원 효율성을 위해 하나의 WAS Server에 여러 JVM을 통합할 수 있다.

---

# 8. 운영환경 구성

운영환경은 시스템별 장애격리와 HA를 우선한다.

```text
마케팅플랫폼

WEB01 ─────┐
           ├──── WAS01
WEB02 ─────┤
           └──── WAS02
```

WAS는 동일 Application JVM을 대칭 배치한다.

```text
WAS01
├─ JVM01 : 공통
└─ JVM02 : 화면

WAS02
├─ JVM01 : 공통
└─ JVM02 : 화면
```

운영환경의 기본 방향은:

```text
운영환경
= Isolation + HA 중심
```

이다.

---

# 9. 운영 시스템별 미들웨어 패턴

현재 구성자료를 기준으로 정리하면 다음과 같다.

| 시스템 | 환경 | WEB | WAS | WAS당 JVM | 주요 포트 | DR | 구조 판정 |
|---|---|---:|---:|---:|---|---|---|
| 마케팅플랫폼 | 개발 | 1 | 1 | 4 | 19000/19001/19010/19011 | 없음 | 적정 |
| 마케팅플랫폼 | 운영 | 2 | 2 | 2 | 19000/19001 | #51/#52 | 적정 |
| 미니싱글뷰 | 운영 | 2 | 2 | 2 | 19000/19001 | #51/#52 | 적정 |
| BI포털 | 운영 | 2 | 2 | 1 | 19000 계열 | 별도 확인 | 적정 |
| 신용실적 | 개발 | 1 | 1 | 2 | 19000/19001 | 없음 | 적정 |
| 신용실적 | 운영 | 2 | 2 | 2 | 19000/19001 | 별도 확인 | 적정 |
| OLAP | 개발 | 1 | 1 | 1 | 19000 | 없음 | 적정 |
| OLAP | 운영 | 2 | 2 | 1 | 19000 | 별도 확인 | 적정 |
| 단말관리 | 운영 | 2 | 2 | 1 | 19000 | #51/#52 | 적정 |
| 단말배포 | 운영 | #1/#6 | #1/#6 | 1 | 19000 | #51/#56 | 적정, 번호체계 특이 |
| 보고서디자이너 | 개발 | 통합 | 통합 | 1 | 내부연계 | 없음 | 예외 패턴 |
| 보고서디자이너 | 운영 | 2 | 2 | 1 | 9000 계열 | 별도 확인 | 적정 |

---

# 10. HA 아키텍처

운영 WEB과 WAS는 상호 Cross Routing을 기본으로 한다.

```text
                   L4
             ┌──────┴──────┐
             ▼             ▼
          Apache01       Apache02
        9000/9001      9000/9001
          │  ╲           ╱  │
          │    ╲       ╱    │
          ▼      ▼   ▼      ▼
      WAS01/JVM1      WAS02/JVM1
         19000           19000

      WAS01/JVM2      WAS02/JVM2
         19001           19001
```

WEB과 WAS는 1:1 종속관계가 아니다.

```text
WEB01 → WAS01
WEB01 → WAS02
WEB02 → WAS01
WEB02 → WAS02
```

으로 구성할 수 있어야 한다.

---

# 11. HA 단위

HA 단위는 WAS 서버 전체가 아니라 Application/JVM 단위로 본다.

정상 구조:

```text
Application A HA Pool
├─ WAS01 / JVM01
└─ WAS02 / JVM01

Application B HA Pool
├─ WAS01 / JVM02
└─ WAS02 / JVM02
```

금지 구조:

```text
WAS01 JVM01
WAS01 JVM02
WAS02 JVM01
WAS02 JVM02

→ 업무 구분 없이 하나의 Cluster로 관리
```

Application별 장애격리와 세션/트래픽 정책을 구분해야 한다.

---

# 12. 장애격리 모델

장애 수준을 3단계로 정의한다.

| 장애 Level | 장애 대상 | 대응 |
|---|---|---|
| Level 1 | Tomcat JVM | Peer JVM으로 우회 |
| Level 2 | WAS Server | Peer WAS로 우회 |
| Level 3 | 센터 | DR센터로 전환 |

```text
JVM 장애
  ↓
Peer JVM

Server 장애
  ↓
Peer WAS

센터 장애
  ↓
DR WAS
```

---

# 13. DR 아키텍처

DR센터는 Primary와 동일한 JVM/Application 구조를 유지하는 것을 원칙으로 한다.

```text
주센터

WAS01
├─ JVM01
└─ JVM02

WAS02
├─ JVM01
└─ JVM02

        │
        │ DR
        ▼

DR센터

WAS51
├─ JVM01
└─ JVM02

WAS52
├─ JVM01
└─ JVM02
```

대표 Pair:

```text
WAS01:JVM01 ↔ WAS51:JVM01
WAS02:JVM02 ↔ WAS52:JVM02
```

단, 단말배포처럼 #1/#6 ↔ #51/#56 형태가 존재하므로 DR Pair를 Hostname 기반으로 명시적으로 관리해야 한다.

---

# 14. JVM 자원관리

JVM 자원은 WAS Server 단위가 아니라 Tomcat JVM Instance 단위로 관리한다.

| 관리항목 | 관리단위 |
|---|---|
| Xms / Xmx | JVM |
| GC | JVM |
| Metaspace | JVM |
| Thread Stack | JVM |
| maxThreads | JVM |
| acceptCount | JVM |
| Connector | JVM |
| Hikari Pool | JVM/Application |
| Log | JVM/Application |
| Heap Dump | JVM |

예:

```text
WAS01
│
├─ JVM01
│   ├─ Heap
│   ├─ Thread Pool
│   └─ Hikari Pool
│
└─ JVM02
    ├─ Heap
    ├─ Thread Pool
    └─ Hikari Pool
```

---

# 15. DB Connection Pool 구조

HikariCP는 JVM별로 독립 관리한다.

예:

```text
WAS01
├─ JVM01 → Hikari 50
└─ JVM02 → Hikari 50

WAS02
├─ JVM01 → Hikari 50
└─ JVM02 → Hikari 50
```

DB의 최대 이론 Pool은:

```text
50 × 4 JVM
= 200 Connections
```

이 된다.

따라서 DB Session 산정 시 단순히 WAS 서버 수만 사용하면 안 된다.

```text
DB Session 총량
= JVM 수 × JVM별 Pool
```

이 관점은 NSIGHT 용량산정의 핵심 기준으로 관리한다.

---

# 16. WEB/WAS 성능 Chain

미들웨어 성능은 개별 파라미터가 아니라 다음 Chain으로 관리한다.

```text
사용자
  ↓
동시 요청자
  ↓
TPS
  ↓
Apache Connection
  ↓
Tomcat Busy Thread
  ↓
Application 처리
  ↓
Hikari Connection
  ↓
DB Session
  ↓
SQL
```

따라서 다음 항목은 함께 검증해야 한다.

| 계층 | 주요 지표 |
|---|---|
| L4 | Connection, Failover |
| Apache | Busy Worker, Connection |
| Tomcat | maxThreads, Busy Threads, acceptCount |
| JVM | Heap, GC Pause, CPU |
| Spring | Transaction Timeout |
| Hikari | Active/Idle/Pending |
| DB | Session, SQL Time |
| 전체 | TPS, p95, Error Rate |

---

# 17. 미들웨어 인벤토리 모델

단순 서버 인벤토리 한 장으로는 현재 구조를 표현하기 어렵다.

권장 관리 모델은 다음과 같다.

```text
SERVER_MASTER
     │ 1:N
     ▼
MIDDLEWARE_INSTANCE
     │ 1:N
     ▼
APPLICATION_DEPLOYMENT
     │ N:M
     ▼
DATASOURCE_DB_MAPPING
```

## 17.1 SERVER_MASTER

| 관리항목 |
|---|
| System Code |
| System Name |
| Environment |
| Hostname |
| IP |
| Server Role |
| HA Group |
| Primary/Secondary |
| DR Hostname |

## 17.2 MIDDLEWARE_INSTANCE

| 관리항목 |
|---|
| Hostname |
| Middleware Type |
| Apache/Tomcat Version |
| Engine Account |
| Instance ID |
| CATALINA_HOME |
| CATALINA_BASE |
| Listen/Connector Port |
| Shutdown Port |
| AJP 사용 여부 |

## 17.3 APPLICATION_DEPLOYMENT

| 관리항목 |
|---|
| WAS Host |
| JVM Instance |
| Application Code |
| Application Name |
| WAR |
| Context Path |
| Service Port |
| Connector Port |

## 17.4 DATASOURCE_DB_MAPPING

| 관리항목 |
|---|
| JVM Instance |
| DataSource |
| Hikari maximumPoolSize |
| DB Service |
| Primary DB |
| Secondary DB |
| Timeout |

---

# 18. Apache 관리항목

| 영역 | 관리항목 |
|---|---|
| Apache | Version |
| Apache | Engine Account |
| Apache | Apache Instance |
| Apache | Listen Port |
| Apache | Service Port |
| Apache | VirtualHost |
| Apache | Proxy/Worker |
| Apache | Backend Route |
| Apache | DocumentRoot |
| Apache | Timeout |
| Apache | KeepAlive |
| Apache | MaxRequestWorkers |
| Apache | Access/Error Log |
| Apache | L4 VIP |
| Apache | HA Pool |

---

# 19. Tomcat 관리항목

| 영역 | 관리항목 |
|---|---|
| Tomcat | Version |
| Tomcat | Engine Account |
| Tomcat | CATALINA_HOME |
| Tomcat | CATALINA_BASE |
| Tomcat | JVM Instance ID |
| Tomcat | HTTP Connector Port |
| Tomcat | Shutdown Port |
| Tomcat | AJP 여부 |
| JVM | Xms/Xmx |
| JVM | GC |
| JVM | Metaspace |
| JVM | Xss |
| Thread | maxThreads |
| Thread | minSpareThreads |
| Thread | acceptCount |
| APP | WAR |
| APP | Context Path |
| Session | Session Timeout |
| Session | Cluster/Replication |
| DB Pool | Hikari maximumPoolSize |
| DB Pool | connectionTimeout |
| DB Pool | maxLifetime |

---

# 20. 운영 계정

구성자료에서는 시스템별 WEB/WAS 엔진 계정을 분리하는 형태가 확인된다.

```text
WEB : apache....
WAS : tomcat....
```

운영 원칙은 다음과 같다.

- Apache 실행 계정과 Tomcat 실행 계정을 분리한다.
- 시스템 또는 서비스 영역별 엔진계정 분리를 원칙으로 한다.
- 설정/로그/배포 파일 권한을 계정 기준으로 통제한다.
- Root 직접 실행은 금지한다.

---

# 21. 예외 Deployment Pattern

보고서디자이너는 일부 환경에서 WEB/WAS가 동일 서버에 위치한다.

```text
Server
├─ Apache
└─ Tomcat JVM
   └─ Report Designer
```

따라서 표준형과 구분하여:

```text
Deployment Pattern
= WEB/WAS Combined
```

으로 관리한다.

개발/특수 시스템에서는 허용할 수 있으나 운영 표준 WEB/WAS 분리 구조와 동일하게 취급하면 안 된다.

---

# 22. Architecture Rule

| Rule ID | 규칙 | 판정 |
|---|---|---|
| MW-001 | WEB는 Apache HTTP Server를 사용 | 필수 |
| MW-002 | WAS는 Tomcat을 사용 | 필수 |
| MW-003 | WAS Server와 Tomcat JVM Instance를 구분 | 필수 |
| MW-004 | Container는 독립 Tomcat JVM Instance로 관리 | 필수 |
| MW-005 | 각 JVM은 독립 Connector Port 사용 | 필수 |
| MW-006 | JVM별 Heap/Thread/Hikari를 독립 관리 | 필수 |
| MW-007 | 동일 Application JVM을 복수 WAS에 대칭 배치 | 운영 필수 |
| MW-008 | WEB→WAS Cross Routing 가능 구조 | 운영 필수 |
| MW-009 | DR에 Primary와 동일 JVM 구조 유지 | DR 대상 필수 |
| MW-010 | Apache 1 Instance에서 복수 Listen Port 허용 | 허용 |
| MW-011 | Service Port와 Connector Port를 구분 | 필수 |
| MW-012 | Application별 HA Pool 구분 | 필수 |
| MW-013 | Hikari Pool 산정 시 총 JVM 수 반영 | 필수 |
| MW-014 | WEB/WAS Combined는 예외 Pattern으로 관리 | 예외 |
| MW-015 | 실제 Apache/Tomcat 설정은 설정파일로 검증 | 필수 |

---

# 23. 금지 구조

다음 구조는 금지 또는 비권장한다.

```text
1. WAS Server = Tomcat JVM이라고 동일시
2. Container를 단순 WAR 폴더로만 관리
3. 여러 JVM의 Hikari Pool을 WAS당 하나처럼 계산
4. Apache와 WAS를 1:1로 고정
5. 운영계에서 Single WAS만 구성
6. 업무 구분 없이 모든 JVM을 하나의 HA Cluster로 관리
7. Service Port와 Connector Port를 동일 개념으로 관리
8. DR에서 Primary와 Application/JVM 배치가 다름
9. CATALINA_BASE 식별 없이 다중 Tomcat 운영
10. 실제 설정파일 검증 없이 구성도만으로 설정값 확정
```

---

# 24. 추가 확인이 필요한 설정

현재 배치 구조는 상당 부분 확인되었으나 실제 미들웨어 Baseline을 확정하려면 다음 파일이 필요하다.

| 우선순위 | 확인 대상 | 파일/명령 | 확인내용 |
|---|---|---|---|
| P0 | Apache Routing | `httpd.conf` | Listen, VirtualHost, Proxy/Worker |
| P0 | Tomcat Instance | `server.xml` | Connector, Thread, Port |
| P0 | Tomcat JVM | `setenv.sh` | Heap, GC, JVM Option |
| P0 | Spring/Hikari | `application.yml/properties` | Pool, Session, Timeout |
| P0 | 실제 Process | `ps -ef` | Tomcat JVM 개수 |
| P0 | CATALINA_BASE | Process/환경설정 | 독립 Instance 여부 |
| P1 | Session | `server.xml`, Spring 설정 | Cluster/Replication |
| P1 | Logging | log 설정 | JVM/Application별 로그 분리 |

---

# 25. 현재 파악도

| 영역 | 파악도 | 상태 |
|---|---:|---|
| WEB/WAS 서버 배치 | 95% | 거의 완료 |
| 운영 HA 구조 | 95% | 거의 완료 |
| DR 관계 | 90% | Pair 명시 필요 |
| Apache 배치 | 90% | 확인 |
| Tomcat 배치 | 90% | 확인 |
| Container/Application | 85% | 상당 부분 확인 |
| Service Port | 85% | 확인 |
| Apache→Tomcat Routing | 60% | 설정파일 필요 |
| Tomcat Instance 상세 | 60% | CATALINA_BASE 필요 |
| JVM 파라미터 | 20% | setenv.sh 필요 |
| Tomcat Thread | 20% | server.xml 필요 |
| Session/Cluster | 20% | 설정 확인 필요 |
| Hikari Pool | 20% | Spring 설정 필요 |
| Apache 상세설정 | 20% | httpd.conf 필요 |

---

# 26. 최종 아키텍처 원칙

NSIGHT 미들웨어 아키텍처는 다음 원칙으로 정의한다.

1. **WEB는 Apache HTTP Server로 구성한다.**
2. **WAS는 Tomcat으로 구성한다.**
3. **WAS Server와 Tomcat JVM을 분리해서 관리한다.**
4. **구성도의 Container는 독립 Tomcat JVM Instance로 정의한다.**
5. **하나의 WAS Server에 1~N개의 Tomcat JVM을 배치할 수 있다.**
6. **각 Tomcat JVM은 독립 Connector Port를 가진다.**
7. **1 JVM = 1 주요 Application 실행단위를 기본으로 한다.**
8. **JVM별 Heap, Thread, Hikari Pool을 독립 관리한다.**
9. **하나의 Apache Instance는 복수 Service Port를 Listen할 수 있다.**
10. **Apache는 Service Port별로 대응 Tomcat JVM으로 라우팅한다.**
11. **운영에서는 동일 Application JVM을 복수 WAS에 대칭 배치한다.**
12. **WEB→WAS는 Cross Routing 가능한 구조로 한다.**
13. **DR센터에도 Primary와 동일 Application/JVM 구조를 유지한다.**
14. **DB Pool/Session은 WAS 서버 수가 아니라 전체 JVM 수까지 반영하여 산정한다.**
15. **개발은 Consolidation, 운영은 Isolation + HA를 기본 방향으로 한다.**
16. **실제 Baseline은 `httpd.conf`, `server.xml`, `setenv.sh`, `application.yml`로 검증한다.**

---

# 27. 최종 정의

> **NSIGHT 미들웨어 아키텍처는 L4 → Apache WEB → Tomcat JVM → Application → HikariCP → RDW/ADW의 계층 구조를 기본으로 한다. WEB 서버에는 Apache HTTP Server를 배치하고 하나의 Apache Instance가 업무/Application별 복수 Service Port를 Listen할 수 있도록 구성한다. WAS 서버에는 하나 이상의 독립 Tomcat JVM Instance를 배치하며, 각 JVM은 독립 Application, Connector Port, JVM Memory, Thread Pool, DB Connection Pool을 가진다. 운영환경에서는 동일 Application JVM을 복수 WAS 서버에 대칭 배치하고 Apache에서 Cross Routing하여 JVM 및 서버 장애를 격리하며, DR센터에도 동일 실행구조를 유지하여 센터 장애까지 대응한다.**

---

# 28. 후속 Architecture Gate

현재 구성도 기준 구조 판정은 **CONDITIONAL PASS**로 본다.

배치/HA/DR 구조는 타당하지만 다음 증적을 확보해야 최종 PASS가 가능하다.

```text
httpd.conf
   ↓
Apache Listen / Route 확인
   ↓
server.xml
   ↓
Tomcat Connector / Thread 확인
   ↓
setenv.sh
   ↓
JVM Heap / GC 확인
   ↓
application.yml
   ↓
Hikari / Session / Timeout 확인
   ↓
ps -ef + CATALINA_BASE
   ↓
실제 Tomcat JVM Instance 구조 확인
   ↓
Runtime Test
   ↓
Final PASS
```

---

## 부록 A. 미들웨어 추적성

```text
System
  ↓
Server
  ↓
Middleware Instance
  ↓
Port
  ↓
Tomcat JVM
  ↓
Application / WAR
  ↓
Thread
  ↓
Hikari
  ↓
DataSource
  ↓
DB
```

이 추적성을 기준으로 서버 인벤토리와 미들웨어 인벤토리를 연계 관리한다.
