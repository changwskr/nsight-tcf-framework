# NSIGHT WAS / Tomcat JVM Architecture

> 상태: **G30 Physical Baseline / Current Working**

## 1. 핵심 정의

```text
WAS Server / VM
      ↓ 1..N
Tomcat JVM Instance
      ↓
Application / WAR
```

구성도상의 `Container`는 본 Baseline에서 **Tomcat JVM Instance**로 표준화한다.

## 2. JVM Instance의 독립 관리항목

| 영역 | 관리항목 |
|---|---|
| Identity | JVM ID / PID / OS Account |
| Directory | CATALINA_HOME / CATALINA_BASE |
| Network | Connector / Shutdown / AJP 여부 |
| JVM | Xms/Xmx/Xss / Metaspace / GC |
| Thread | maxThreads / minSpareThreads / acceptCount |
| App | Application / WAR / Context Path |
| DB | Datasource / Hikari maximumPoolSize |
| Session | Local / DeltaManager / JDBC 등 |
| Logging | Access/App/GC/Error |
| Monitoring | APM/JMX/Metric |
| Availability | HA Peer JVM / DR Peer |

## 3. Application Isolation 원칙

기본 Working Rule:

> **1 Tomcat JVM = 1 주요 Application 실행단위**

이는 JVM 재기동, Heap, Thread, Hikari, 장애 영향범위를 Application 중심으로 분리하기 위한 원칙이다.

실제 WAR 전수배치가 이 Rule과 일치하는지는 G40 Source/Deployment Mapping에서 검증한다.

## 4. 대표 운영 패턴

### 마케팅플랫폼

```text
WAS01
├─ JVM01 :19000 → Marketing Common [WORKING]
└─ JVM02 :19001 → Marketing UI     [WORKING]

WAS02
├─ JVM01 :19000 → Marketing Common [WORKING]
└─ JVM02 :19001 → Marketing UI     [WORKING]
```

### Mini SingleView

```text
WAS01/02
├─ JVM01 :19000 → MiniSV Common [WORKING]
└─ JVM02 :19001 → MiniSV UI     [WORKING]
```

### 개발 Consolidation

```text
DEV WAS
├─ JVM01 :19000
├─ JVM02 :19001
├─ JVM03 :19010
└─ JVM04 :19011
```

개발 구성은 운영 표준으로 자동 승격하지 않는다.

## 5. Resource Consistency

```text
VM Memory
  ├─ JVM Heap
  ├─ Metaspace
  ├─ Thread Stack
  ├─ Direct/Native Memory
  ├─ APM/Agent
  └─ OS/Page Cache/Buffer
```

VM 전체 Memory를 Heap으로 할당하지 않는다.

```text
CPU Core
  ↓
maxThreads
  ↓
Transaction Concurrency
  ↓
Hikari Pool
  ↓
DB Session
```

숫자 최종값은 G60에서 Versioned Performance Baseline을 통해 확정한다.

## 6. HA 단위

HA는 `WAS Server`라는 이름만으로 정의하지 않고 동일 Application의 Peer JVM으로 정의한다.

```text
Application A HA Pool
├─ WAS01/JVM01
└─ WAS02/JVM01
```

## 7. G30 미확정

- 실제 CATALINA_BASE 전수목록
- 실제 PID/OS Account
- server.xml Connector 전수값
- setenv.sh JVM Option 전수값
- Application/WAR 전수배치
- Session 전략 최종 ADR
- JVM별 Hikari Datasource/Pool
- HA Peer JVM Catalog
- Runtime Metric/JMX Evidence

## 8. Physical Rule

- WAS Server/JVM/Application을 독립 Entity로 관리한다.
- Connector Port는 JVM Instance별 Unique해야 한다.
- 한 VM 장애 시 동일 VM의 모든 JVM이 함께 손실되는 것을 Capacity에 반영한다.
- JVM 수×Heap 합계만으로 VM Memory를 산정하지 않고 Native/OS 여유를 포함한다.
- JVM Runtime Inventory는 Server Master와 FK로 연결한다.
