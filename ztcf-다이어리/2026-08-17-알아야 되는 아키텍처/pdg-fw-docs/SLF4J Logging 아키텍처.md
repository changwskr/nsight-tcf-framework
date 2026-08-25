# SLF4J 아키텍처 구조

현재 **PDMG/NSIGHT Logging Architecture** 관점에서 SLF4J를 이해할 때 가장 중요한 것은 이것입니다.

> **SLF4J는 로그를 직접 파일에 기록하는 로깅 엔진이 아니라, Java 코드가 특정 로깅 구현체에 종속되지 않도록 해주는 Logging Facade(API)입니다.**

현재 PDMG Logging은 `GUID + ServiceId`를 중심으로 시스템→TCF→업무→DB→오류까지 하나의 거래로 연결하도록 설계되어 있습니다.

PDMG 실제 소스를 기준으로 보면 기본 구조는 다음과 같습니다.

```text
                   PDMG SLF4J ARCHITECTURE

┌─────────────────────────────────────────────────────┐
│                 Application Code                    │
│                                                     │
│  DefaultFilter                                      │
│  Interceptor                                        │
│  TCF                                                │
│  Handler / Facade / Service                         │
│  DAO / Aspect                                       │
│                                                     │
│  private static final Logger log =                  │
│      LoggerFactory.getLogger(...);                  │
│                                                     │
│  또는                                               │
│                                                     │
│  @Slf4j                                             │
│  class XxxService {                                 │
│      log.info(...);                                 │
│  }                                                  │
└───────────────────────┬─────────────────────────────┘
                        │
                        ▼
═══════════════════════════════════════════════════════
                 SLF4J API / FACADE
═══════════════════════════════════════════════════════

             org.slf4j.Logger
             LoggerFactory
             MDC

                        │
                        │ Logging Provider
                        ▼
┌─────────────────────────────────────────────────────┐
│                     Log4j2                          │
│              실제 Logging Engine                   │
│                                                     │
│  Logger                                             │
│  ThreadContext                                      │
│  Appender                                           │
│  Layout                                             │
│  RollingPolicy                                      │
└───────────────────────┬─────────────────────────────┘
                        │
                        ▼
                  log4j2.xml
                        │
      ┌─────────────────┼───────────────────┐
      ▼                 ▼                   ▼
    Console          RollingFile         SQL Log
      │                 │                   │
      ▼                 ▼                   ▼
   stdout       pk_framework.log       SQL Console
                pk_service.log

                        │
                        ▼
                 운영 / ELK / 분석
```

## 1. SLF4J와 Log4j2는 역할이 다릅니다

둘을 같은 것으로 생각하면 Logging 구조가 헷갈립니다.

| 구분                | 역할                               | PDMG                                       |
| ------------------- | ---------------------------------- | ------------------------------------------ |
| **SLF4J**           | 개발자가 사용하는 Logging 표준 API | `Logger`, `LoggerFactory`, `MDC`, `@Slf4j` |
| **Log4j2**          | 실제 로그를 처리하는 Engine        | 현재 PDMG Backend                          |
| `log4j2.xml`        | Log4j2 실행 정책                   | 레벨, 파일, 패턴, Rolling                  |
| MDC / ThreadContext | 거래별 Context                     | GUID, ServiceId 등                         |
| Appender            | 로그 출력 목적지                   | Console, File 등                           |

즉 코드에서는:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerService {

    private static final Logger log =
        LoggerFactory.getLogger(CustomerService.class);

    public void process() {
        log.info("customer processing start");
    }
}
```

라고만 작성합니다.

업무 코드는 이것이 Console로 갈지, 파일로 갈지 알 필요가 없습니다.

```text
CustomerService
      │
      │ log.info()
      ▼
    SLF4J
      │
      ▼
    Log4j2
      │
      ├─ Console
      ├─ Framework Log
      └─ Service Log
```

이것이 SLF4J를 사용하는 가장 큰 이유입니다.

---

# 2. 현재 PDMG의 실제 선택은 `SLF4J + Log4j2`

현재 ZIP의 `pdmg-fw/build.gradle`을 확인하면 Spring Boot 기본 Logging 대신 **Log4j2를 명시적으로 사용**하는 구조입니다.

개념적으로:

```text
Spring Boot
   │
   ├─ spring-boot-starter-logging
   │          │
   │          └─ Logback
   │
   └─ PDMG에서는 제외
              X

대신

spring-boot-starter-log4j2
              │
              ▼
             Log4j2
```

따라서 PDMG 기준으로 정확하게 표현하면:

```text
Application
    ↓
SLF4J API
    ↓
SLF4J → Log4j2 Provider
    ↓
Log4j2 Core
    ↓
log4j2.xml
    ↓
Appender
```

입니다.

반면 NSIGHT의 일부 `tcf-*`, `*-service` 모듈에는 `logback-spring.xml`도 존재하므로 **NSIGHT 전체를 무조건 Log4j2라고 일반화하면 안 됩니다.**

```text
[PDMG]
SLF4J
   ↓
Log4j2


[일부 NSIGHT TCF 서비스]
SLF4J
   ↓
Logback
```

SLF4J를 사용하기 때문에 애플리케이션 소스는 이런 Backend 차이에서 상대적으로 자유로울 수 있습니다.

---

# 3. `@Slf4j`는 SLF4J 자체가 아니라 Lombok 편의 기능입니다

개발자가 다음처럼 작성하는 경우가 많습니다.

```java
@Slf4j
@Service
public class Mgcoa9001Service {

    public void process() {
        log.info("거래처리 시작");
    }
}
```

`@Slf4j`가 특별한 Logging Framework인 것은 아닙니다.

Lombok이 컴파일 과정에서 개념적으로 다음 코드를 생성해 주는 것입니다.

```java
private static final org.slf4j.Logger log =
    org.slf4j.LoggerFactory.getLogger(
        Mgcoa9001Service.class
    );
```

따라서 관계는:

```text
Lombok @Slf4j
      │
      │ 코드 자동생성
      ▼
SLF4J Logger
      │
      ▼
Log4j2
```

입니다.

현재 PDMG 실제 소스에서도 `Logger/LoggerFactory` 방식과 Lombok `@Slf4j` 방식이 모두 확인됩니다.

---

# 4. PDMG에서 SLF4J의 핵심은 MDC와 연결됩니다

PDMG Logging에서 더 중요한 것은 단순히:

```java
log.info("업무 시작");
```

하는 것이 아닙니다.

**어떤 거래의 로그인지 자동으로 알 수 있어야 합니다.**

현재 PDMG Logging Architecture는 `GUID + ServiceId`를 중심으로 로그를 연결합니다.

```text
Request
   │
   ▼
GUID      = A001
ServiceId = mgcoa9001S0
UserId    = E0001
IP        = 10.1.1.10
   │
   ▼
MDC
┌──────────────────────────┐
│ guid      = A001         │
│ serviceId = mgcoa9001S0  │
│ userId    = E0001        │
│ ip        = 10.1.1.10    │
└──────────────────────────┘
   │
   ▼
Service
   │
   └─ log.info("조회 시작");
```

업무 코드에서는 메시지만 출력했지만:

```text
조회 시작
```

실제 로그에서는 설정 Pattern을 통해 개념적으로:

```text
A001 | E0001 | mgcoa9001S0 | 10.1.1.10 | 조회 시작
```

처럼 만들어집니다.

현재 문서에서도 `DefaultFilter`가 ServiceContext와 MDC를 준비하고 이후 Interceptor가 GUID/User/IP/ServiceId를 보강하는 구조로 정리되어 있습니다.

---

# 5. `log4j2.xml`은 SLF4J의 설정파일이 아닙니다

이것도 중요한 구분입니다.

```text
SLF4J
   │
   │ API
   │
   ▼
Log4j2
   │
   │ Configuration
   ▼
log4j2.xml
```

현재 PDMG `log4j2.xml`은 MDC 값을 Pattern에서 사용합니다.

실제 구조는 다음과 같은 형태입니다.

```text
%X{guid}
%X{userId}
%X{serviceId}
%X{ip}
%X{sqlId}
%X{ifId}
%X{errCode}
```

따라서 PDMG Logging Context를 구조화하면:

```text
                    공통 MDC

guid
userId
serviceId
ip
 │
 ├──────────── CORE
 │
 ├──────────── BIZ
 │
 ├──────────── IO
 │
 ├──────────── SQL ── + sqlId
 │
 ├──────────── IF  ── + ifId
 │
 └──────────── ERR ── + errCode
```

가 됩니다.

현재 문서 점검에서는 실제 주요 사용 키를 `guid`, `userId`, `serviceId`, `ip`, `sqlId`, `errCode`로 확인했고, `traceId`, `ifId` 등은 문서상 확장/제한적 사용 항목으로 구분하고 있습니다. 또한 SLF4J MDC와 Log4j2 ThreadContext를 혼용하고 있어 논리적 MDC API 표준화가 필요하다는 점도 확인돼 있습니다.

---

# 6. PDMG 로그는 Logger Category로 다시 분리됩니다

현재 `log4j2.xml`의 구조를 보면 크게 다음 방향입니다.

```text
                         SLF4J Logger

                             │
             Logger Name / Package 기준 Routing
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼

   nhnis.fw.*           nhnis.mg.*       MybatisLogInterceptor
        │                    │                    │
        ▼                    ▼                    ▼
      CORE                  BIZ                  SQL
        │                    │                    │
        ▼                    ▼                    ▼
 Framework Log        Service Log          SQL Log
```

전체 Logging Architecture에서는 이를 더 크게 여섯 영역으로 구분하고 있습니다.

| 영역        | 대표 역할            |
| ----------- | -------------------- |
| System      | Filter / Interceptor |
| Transaction | TCF / STF / ETF      |
| Business    | Aspect / Service     |
| ImageLog    | 요청·응답·오류 증적  |
| Error       | 예외 처리            |
| Runtime     | Thread/TX/JVM/Pool   |

여기서 SLF4J는 **이 모든 영역에서 개발 코드가 사용하는 공통 Logging API**라고 보면 됩니다.

---

# 7. Timeout Worker 때문에 MDC Architecture가 특히 중요합니다

PDMG에서는 Timeout ON이면 Request Thread에서 Worker Thread로 실행이 전환됩니다.

```text
Tomcat Request Thread
       │
       │ GUID=A001
       │ ServiceId=mgcoa9001S0
       │ MDC 존재
       │
       ▼
OnlineTimeoutExecutor
       │
       │ submit
       ▼
pdmg-online Worker
```

문제는 일반적인 ThreadLocal/MDC 값이 다른 Thread로 자동 전달되지 않는다는 것입니다.

그래서 현재 PDMG에는 `OnlineTimeoutWorkerContext`가 있습니다. 현재 Logging Architecture도 Request Thread의 Context/MDC를 Worker Thread로 복원하는 것을 핵심 구조로 보고 있습니다.

```text
Request Thread
──────────────────────────
ServiceContext
MDC
GUID
ServiceId
SecurityContext

        │ capture
        ▼

OnlineTimeoutWorkerContext

        │ install
        ▼

Worker Thread
──────────────────────────
ServiceContext
MDC
GUID
ServiceId
SecurityContext

        │
        ▼
TransactionTemplate
        ↓
Dispatcher
        ↓
Handler
        ↓
Facade
        ↓
Service
        ↓
DAO
```

그래야 다음 모든 로그가 같은 GUID를 갖게 됩니다.

```text
Filter             GUID=A001
Interceptor        GUID=A001
TCF                GUID=A001

──────── Thread 전환 ────────

Dispatcher         GUID=A001
Handler            GUID=A001
Facade             GUID=A001
Service            GUID=A001
DAO                GUID=A001
SQL                GUID=A001
Error              GUID=A001
```

이 연결이 끊기면 장애 분석 시 한 거래를 끝까지 추적할 수 없습니다.

---

# 8. Worker가 끝나면 반드시 MDC를 지워야 합니다

Thread Pool의 Thread는 재사용됩니다.

따라서:

```text
거래 A

Worker-1
GUID=A001

       ↓ 종료

Worker-1 재사용

       ↓

거래 B
GUID=B001
```

가 되어야 합니다.

만약 이전 MDC를 지우지 않으면:

```text
거래 B 실행

실제 GUID = B001
로그 GUID = A001     ← 심각한 로그 오염
```

이 발생할 수 있습니다.

그래서 구조는 반드시:

```text
capture()
    ↓
install()
    ↓
업무 실행
    ↓
finally
    ↓
clear()
```

여야 합니다.

Filter 쪽도 동일합니다.

```text
Request 시작
      ↓
MDC 설정
      ↓
처리
      ↓
finally
      ↓
Context remove
MDC clear
```

현재 PDMG 기준에서도 Filter 성공 후 Context/MDC를 설정하고, `finally`에서 이를 제거하여 재사용 Thread에서 GUID가 누수되지 않도록 하는 구조입니다.

---

# 9. SLF4J Logging 표준은 이렇게 잡는 것이 좋습니다

PDMG에서는 애플리케이션 코드가 가능하면 **SLF4J API 하나만 바라보게 하는 것**을 권장합니다.

```text
업무 코드
       │
       ├─ SLF4J Logger       O
       ├─ @Slf4j             O
       │
       ├─ Log4j2 Logger 직접 X
       └─ Logback 직접       X
```

즉:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
```

또는:

```java
import lombok.extern.slf4j.Slf4j;
```

를 사용하는 방향입니다.

왜냐하면 이렇게 하면:

```text
                  Application

                      │
                      ▼
                    SLF4J
                      │
             ┌────────┴────────┐
             │                 │
        현재 PDMG        다른 Runtime
             │                 │
             ▼                 ▼
           Log4j2            Logback
```

처럼 Backend가 바뀌더라도 업무 소스의 변경을 최소화할 수 있기 때문입니다.

특히 현재 PDMG 소스에는 **SLF4J `MDC`와 Log4j2 `ThreadContext`가 혼용되는 부분**이 있으므로, 앞으로는 애플리케이션/업무 계층은 SLF4J API로 통일하고 Framework 내부에서만 구현체 API를 사용할지 명확한 기준을 정하는 것이 좋습니다. 이 혼용 문제는 현재 문서 분석에서도 명시적으로 확인된 항목입니다.

---

# 10. 최종 PDMG SLF4J Architecture

전체 구조를 하나로 합치면 다음과 같습니다.

```text
                       HTTP REQUEST
                            │
                            ▼
                    DefaultFilter
                            │
                    ServiceContext
                            │
                            ▼
                      MDC Context
                   GUID / ServiceId
                  User / IP / sqlId
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│                    APPLICATION LAYER                         │
│                                                              │
│ Filter / Interceptor / TCF / Handler                         │
│ Facade / Aspect / Service / DAO                              │
│                                                              │
│                    log.info()                                │
│                    log.debug()                               │
│                    log.warn()                                │
│                    log.error()                               │
└────────────────────────────┬─────────────────────────────────┘
                             │
                             ▼
════════════════════════════════════════════════════════════════
                           SLF4J
        Logger / LoggerFactory / MDC / Lombok @Slf4j
════════════════════════════════════════════════════════════════
                             │
                             ▼
                  SLF4J → Log4j2 Provider
                             │
                             ▼
════════════════════════════════════════════════════════════════
                           LOG4J2
════════════════════════════════════════════════════════════════
                             │
                       log4j2.xml
                             │
       ┌──────────────┬──────┼──────┬──────────────┐
       ▼              ▼      ▼      ▼              ▼
      CORE           BIZ    SQL     ERR          MONITOR
       │              │      │      │              │
       └──────────────┴──────┼──────┴──────────────┘
                             ▼
                         Appenders
                     ┌───────┴───────┐
                     ▼               ▼
                   Console        RollingFile
                                     │
                              pk_framework.log
                              pk_service.log
                                     │
                                     ▼
                               운영 / 분석
```

따라서 **PDMG에서 SLF4J의 아키텍처적 역할을 한 문장으로 정의하면** 다음과 같습니다.

> **SLF4J는 PDMG의 Filter·TCF·업무·DAO 등 모든 Java 계층이 동일한 Logging API를 사용하도록 만드는 추상화 계층이고, 현재 PDMG에서는 그 아래 Log4j2가 실제 로그 출력·패턴·파일·Rolling을 담당하며, `GUID + ServiceId` MDC가 전체 거래 로그를 하나로 연결한다.**

그리고 PDMG에서 특히 중요한 핵심 흐름은 이것입니다.

```text
GUID / ServiceId 생성
        ↓
ServiceContext
        ↓
MDC
        ↓
SLF4J
        ↓
Log4j2
        ↓
CORE / BIZ / SQL / ERR
        ↓
동일 거래 추적
```

이 구조가 앞서 정리한 **Logging Architecture의 실제 Java Logging 기반 구조**입니다.
