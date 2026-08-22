# NSIGHT 성능튜닝 및 환경 구성 정보 아키텍처

## 1. 목적

본 문서는 농협 상호금융 NSIGHT 정보계의 온라인 서비스에 대해 다음 영역을 하나의 성능 아키텍처로 통합 정의한다.

- 사용자 및 세션 용량
- 동시 요청자 및 목표 TPS
- TPMC 기반 VM 처리용량
- AP/VM 배치 및 센터 이중화
- JVM 및 GC
- Tomcat Thread/Connection
- HikariCP DB Connection Pool
- Spring Transaction
- MyBatis Query Timeout
- GSLB/L4/Web Timeout
- Session 및 DeltaManager
- 성능 운영 임계치
- 성능시험 및 튜닝 절차

성능 파라미터를 개별 설정값으로 관리하지 않고 다음의 **End-to-End Capacity Chain**으로 관리하는 것을 기본 원칙으로 한다.

```text
사용자
  ↓
세션
  ↓
동시 요청자
  ↓
목표 TPS
  ↓
VM 처리용량
  ↓
AP/VM 수량
  ↓
Tomcat Thread
  ↓
JVM/GC
  ↓
HikariCP
  ↓
DB Query
  ↓
Transaction
  ↓
응답시간
  ↓
운영 임계치
  ↓
성능시험 결과
```

---

# 2. 성능 아키텍처 Big Picture

```text
                    NSIGHT PERFORMANCE ARCHITECTURE

┌──────────────────────────────────────────────────────────────┐
│                     사용자 / 단말                            │
│                                                              │
│ 전체 사용자                  36,000                         │
│ 설계 Session                 43,200 이상                    │
│ Peak 동시요청자               3,600                         │
│ Stress 동시요청자             5,400                         │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                     PERFORMANCE SLA                          │
│                                                              │
│ 일반 운영                     600 TPS                       │
│ Peak                         1,200 TPS                       │
│ Stress                       1,800 TPS                       │
│ 온라인 응답                  p95 ≤ 3초                      │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
                        GSLB / L4
                              │
                              ▼
                           Apache
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                  WAS / Tomcat JVM                           │
│                                                              │
│ VM                         16 vCPU / 64GB                   │
│ VM 산정 처리량              855 TPS                         │
│ JVM Heap                   24~28GB 검증                     │
│ GC                         G1GC                             │
│ Tomcat maxThreads          800~1,000                       │
│ Busy Thread                ≤70%                            │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                   SPRING / APPLICATION                       │
│                                                              │
│ Transaction Timeout       3~5초 중심                        │
│ 장시간 거래               비동기 / 배치                    │
│ 조회 거래                 readOnly=true                    │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│                HikariCP / MyBatis / RDW                      │
│                                                              │
│ DB Pool                    일반 150 / SV 180 검증           │
│ Connection Timeout         3초                              │
│ Query Timeout              1~3초                            │
│ 정상 SQL 시간              100~300ms 목표                  │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
                            RDW
```

---

# 3. 사용자 및 세션 기준

## 3.1 사용자 기준

| 항목 | 기준 |
|---|---:|
| 지점 수 | 6,000 |
| 지점당 사용자 | 6명 |
| 전체 사용자 | **36,000명** |
| 세션 여유율 | 20~30% |
| 설계 세션 | **43,200~46,800** |
| 현재 화면 기준 | **43,200** |

기존 용량산정 자료도 전체 사용자 36,000명, 운영 여유율을 포함한 약 43,000~47,000 세션을 설계 기준으로 정의한다.

### 원칙

```text
Session 수
≠
동시 요청자
≠
Tomcat Thread
≠
DB Connection
```

세션은 로그인 상태 유지 규모이고, TPS·Thread·DB Pool은 실제 동시 요청량을 기준으로 산정한다.

---

# 4. 동시 요청자 및 TPS 기준

## 4.1 TPS 산정식

```text
동시 요청자
=
전체 사용자 × 동시 요청률
```

```text
목표 TPS
=
동시 요청자 ÷ 목표 응답시간
```

목표 응답시간은 일반 온라인 기준 **3초**를 적용한다.

## 4.2 시나리오

| 시나리오 | 동시 요청률 | 동시 요청자 | 목표 TPS |
|---|---:|---:|---:|
| 낮은 부하 | 3% | 1,080 | 360 |
| 일반 Peak | 5% | 1,800 | **600** |
| 높은 Peak | 10% | 3,600 | **1,200** |
| Stress | 15% | 5,400 | **1,800** |

동일한 600/1,200/1,800 TPS 시나리오는 기존 NSIGHT 기준 문서에도 정의되어 있다.

### Architecture Baseline

```text
운영 기준       600 TPS
설계 Peak     1,200 TPS
Stress        1,800 TPS
SLA           p95 ≤ 3 sec
```

---

# 5. 현행 실측 거래량 활용

업로드된 현행 단말 거래 자료에서는 Mini SingleView 계열을 기준으로 약 **77.5~78 TPS** 수준의 실제 거래 처리량이 제시되어 있다.

이 값은 신규 NSIGHT 목표 TPS와 직접 동일시하지 않는다.

```text
현행 78 TPS
=
AS-IS Runtime Evidence

1,200 TPS
=
NSIGHT TO-BE Peak Capacity Target
```

따라서 현행 실측값은 **업무 거래 패턴과 기준 부하를 이해하기 위한 Evidence**로 사용하고 신규 용량산정은 동시 요청자 및 목표 SLA 기준으로 별도 수행한다.

---

# 6. VM 처리용량 아키텍처

## 6.1 현재 Working Baseline

| 항목 | 기준 |
|---|---:|
| vCPU | **16 Core** |
| Memory | **64GB** |
| CPU 운영 임계치 | 평균 ≤70% |
| TPMC 기반 VM 처리량 | **855 TPS** |
| JVM Heap | 24~28GB 검증 |
| Scale 방식 | Scale-Out |
| 장애 영향 단위 | VM 1대 |

현재 화면에서는 Core당 TPMC 106,932, 업무 1 TPS당 약 2,000 TPMC를 적용하여 Core당 약 53 TPS, 16Core 기준 약 855 TPS를 계산한다.

단,

> **855 TPS는 설계·산정 Capacity이며 성능 보장값은 아니다.**

최종 VM 처리량은 부하테스트를 통해 확정한다.

---

# 7. 기존 Capacity 기준과 현재 기준의 관계

기존 NSIGHT 용량산정 문서에는 보수적 기준으로:

| VM | 기존 기준 TPS |
|---|---:|
| 8Core/32GB | 250 TPS |
| 16Core/64GB | 500 TPS |
| 16Core/128GB | 500 TPS |

를 사용한 문서가 존재한다.

현재 화면에서는:

```text
16Core/64GB
500 TPS 보수 기준
        ↓
TPMC 기반 재산정
        ↓
855 TPS/VM
```

으로 발전한 상태다.

따라서 상태를 다음과 같이 관리한다.

| 값 | 상태 |
|---|---|
| 500 TPS/VM | 기존 보수 Capacity Baseline |
| **855 TPS/VM** | Current TPMC Working Baseline |
| 최종 보장 TPS | **성능시험 후 확정** |

---

# 8. AP 수량 산정

855 TPS를 적용하면:

| 시나리오 | 목표 TPS | 이론 VM |
|---|---:|---:|
| 일반 | 600 | 0.70 |
| Peak | 1,200 | 1.40 |
| Stress | 1,800 | 2.11 |

물리 VM 수량은 반드시 올림 처리한다.

```text
600 TPS    → 최소 1 VM
1,200 TPS  → 최소 2 VM
1,800 TPS  → 최소 3 VM
```

다만 이는 **순수 Capacity 산정값**이며 HA 및 센터 장애를 고려한 운영 수량과 동일하지 않다.

---

# 9. 센터 이중화 아키텍처

센터 이중화에서는 VM의 최대 Capacity를 모두 운영용량으로 사용하지 않는다.

운영 허용률을 80%로 보면:

```text
855 × 0.8
=
684 TPS / VM
```

양센터 각각 2 VM, 총 4 VM을 가정하면:

| 상태 | 잔존 VM | 운영 Capacity |
|---|---:|---:|
| 정상 | 4 | 2,736 TPS |
| VM 1대 장애 | 3 | 2,052 TPS |
| 센터 1개 장애 | 2 | **1,368 TPS** |
| VM 1대만 잔존 | 1 | 684 TPS |

센터 1개 전체 장애에서도:

```text
1,368 TPS
>
Peak 1,200 TPS
```

가 되므로 Peak 기준을 수용할 수 있다.

### 원칙

> 센터 장애 Capacity는 `VM 최대 TPS`가 아니라 **운영 허용 Capacity × 잔존 VM 수**로 판단한다.

---

# 10. JVM 아키텍처

## 10.1 현재 설정 후보

```text
-Xms28g
-Xmx28g
-Xss512k
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
-XX:MetaspaceSize=512m
-XX:MaxMetaspaceSize=2g
-XX:ReservedCodeCacheSize=256m
```

기존 문서에서는 16Core/64GB의 일반 AP Heap을 약 24GB, SingleView AP를 28GB 수준으로 제시한다.

따라서 Current Baseline은 다음처럼 관리한다.

| 업무 | 초기값 | 검증 상한 |
|---|---:|---:|
| 일반 AP | **24GB** | 28GB |
| SingleView | **28GB** | 성능시험 검증 |

## 10.2 JVM 운영 기준

| 지표 | 기준 |
|---|---:|
| Heap 사용률 | ≤70% |
| GC | G1GC |
| GC Pause 목표 | ≤200ms |
| Full GC | 정상 운영 중 반복 발생 금지 |
| GC Log | 필수 |
| Heap Dump | OOM 시 자동 |
| CPU | 평균 ≤70% |

G1GC와 `MaxGCPauseMillis=200` 적용 및 GC 로그 상시 수집은 기존 성능 기준에도 포함되어 있다.

---

# 11. VM 메모리 Budget

64GB VM에서는 Heap뿐 아니라 Native Memory와 OS 영역을 동시에 관리해야 한다.

권장 구조:

| 메모리 영역 | Budget |
|---|---:|
| JVM Heap | 24GB |
| Metaspace + CodeCache | 2GB |
| Thread Stack | 1~2GB |
| Direct/NIO | 2~4GB |
| JVM Native/GC | 2~4GB |
| APM/보안/로그 Agent | 2~4GB |
| OS/Page Cache/Network | 8~12GB |
| 장애/Peak 운영 여유 | 잔여 메모리 |

### 금지

```text
64GB VM
→ 50~60GB Heap 할당
```

Heap을 확대하여 TPS를 증가시키는 방식은 금지한다.

---

# 12. Tomcat Thread 아키텍처

현재 화면에는 다음 산정이 존재한다.

```text
855 TPS
× 1.5초 평균 처리시간
× 1.1
≈ 1,411 Thread
```

그리고 10% 추가하여 약 1,552 Thread가 제시되어 있다.

이 값은 **동시 In-flight 요구량 계산값**으로 관리하고 실제 `maxThreads` 설정값과 분리한다.

## 12.1 운영 설정

| 항목 | Current Baseline |
|---|---:|
| maxThreads | **800~1,000** |
| 초기 적용 권장 | 800 |
| minSpareThreads | 150~200 |
| acceptCount | 500~800 |
| maxConnections | 16,000~20,000 |
| connectionTimeout | 8초 |
| keepAliveTimeout | 5초 |
| maxKeepAliveRequests | 100 |

기존 16Core NSIGHT 기준 역시 Tomcat `maxThreads=800~1,000` 범위를 사용한다.

### 운영 임계치

| Busy Thread | 상태 |
|---|---|
| <70% | 정상 |
| 70~85% | 주의 |
| >85% | 위험 |
| 100% 지속 | 장애 징후 |

---

# 13. Thread 튜닝 원칙

Thread만 증가시키는 튜닝은 금지한다.

```text
Thread 증가
      ↓
DB Connection 요청 증가
      ↓
Pool Wait 증가
      ↓
DB 부하 증가
      ↓
응답시간 증가
      ↓
Busy Thread 증가
```

따라서 다음 지표를 동시에 판단한다.

```text
TPS
+
CPU
+
Tomcat Busy Thread
+
Hikari Active
+
Hikari Pending
+
SQL Time
+
GC Pause
+
p95
```

---

# 14. HikariCP 아키텍처

## 14.1 현재 화면의 Working Setting

| 파라미터 | 일반 AP | SingleView |
|---|---:|---:|
| maximumPoolSize | **150** | **180** |
| minimumIdle | 30 | 40 |
| connectionTimeout | 3초 | 3초 |
| validationTimeout | 3초 | 3초 |
| idleTimeout | 10분 | 10분 |
| maxLifetime | ≤30분 | ≤30분 |
| keepaliveTime | 5분 | 5분 |
| autoCommit | FALSE | FALSE |

기존 보수 기준 문서에서는 16Core/64GB에 일반 `80~100`, SingleView `100~120`을 적용한 버전이 있다.

따라서:

| 값 | 상태 |
|---|---|
| 80~100 / 100~120 | 기존 보수 Baseline |
| **150 / 180** | 현재 DB 점유시간 기반 Working Baseline |
| 최종값 | 성능시험 + DB Session 검증 후 확정 |

---

# 15. DB Pool 산정식

기본적으로:

```text
Pool
≈
TPS × DB Connection 점유시간
```

855 TPS 기준:

| DB 점유시간 | 이론 Pool |
|---:|---:|
| 100ms | 86 |
| 150ms | 128 |
| 200ms | 171 |
| 300ms | 257 |

따라서:

```text
일반 AP 150
≈ 150~175ms 수준 DB 점유시간 대응

SingleView 180
≈ 약 200ms DB 점유시간 대응
```

으로 해석할 수 있다.

다만 실제 산정은 다음 보정이 필요하다.

```text
Pool
=
TPS
× DB Hold Time
× DB 사용률
× Safety Factor
```

---

# 16. DB Connection 총량 통제

Pool은 JVM 하나만 보고 결정하지 않는다.

```text
전체 DB Session
=
Pool Size
× JVM 수
× DataSource 수
× 센터 수
```

예:

```text
SingleView Pool 180
× JVM 4
=
720 DB Connections
```

일반 Pool과 별도 DataSource까지 같이 존재한다면 총 Session은 더 증가한다.

### Architecture Rule

> Hikari Pool 변경 시 반드시 Oracle `processes/sessions`, DataSource 개수, JVM 수, 센터 전체 Connection 총량을 재검증한다.

---

# 17. Hikari 운영 임계치

| 사용률 | 상태 |
|---|---|
| <70% | 정상 |
| 70~80% | 주의 |
| 80~90% | 경고 |
| ≥90% | Critical |
| Pending 지속 | 장애 징후 |

특히 Pool Usage보다 **Pending Connection 발생 여부**를 우선 관찰한다.

---

# 18. Spring Transaction 아키텍처

업무유형별 Transaction Timeout을 차등 적용한다.

| 업무 | 목표 응답 | Transaction Timeout |
|---|---:|---:|
| 공통코드 | ≤1초 | 3초 |
| 일반 마케팅 조회 | ≤3초 | 4초 |
| SingleView 조회 | ≤3초 | 5초 |
| 저장/변경 | 3~5초 | 5~10초 |
| 10초 초과 | - | **온라인 금지** |

### 조회 Transaction

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    readOnly = true,
    timeout = 5,
    rollbackFor = Exception.class
)
```

### 원칙

```text
조회
→ readOnly=true

온라인 TX
→ 짧게

10초 이상
→ 비동기 / Batch 전환
```

---

# 19. MyBatis Query Timeout 아키텍처

## 19.1 기본 설정

```yaml
mybatis:
  configuration:
    default-statement-timeout: 3
    default-fetch-size: 200
    map-underscore-to-camel-case: true
```

## 19.2 업무별 기준

| Query | Timeout |
|---|---:|
| 공통코드 | 1~2초 |
| 일반 마케팅 | 2~3초 |
| SingleView | 3초 |
| 기본 Statement | 3초 |
| 대용량/대량조회 | 온라인 금지 |

Fetch Size는 조회 특성에 따라 약 `100~500` 범위에서 조정한다.

---

# 20. Timeout Architecture

거래 Timeout은 안쪽 계층이 먼저 종료되도록 설계한다.

```text
DB Query Timeout
        <
Spring Transaction Timeout
        <
Web Request / Client Timeout
```

업무 유형 예:

```text
공통
Query 1~2s
   <
TX 3s

일반
Query 2~3s
   <
TX 4s

SingleView
Query 3s
   <
TX 5s
```

Web 요청은 일반적으로 약 **6~8초** 수준을 적용한다.

기존 NSIGHT 문서도 Spring Transaction `4~5초`, DB Query `2~3초`, Web 요청 `6~8초`의 계층 관계를 사용한다.

---

# 21. L4 Idle Timeout은 거래 Timeout과 분리

다음 두 개는 서로 다른 정책이다.

### 거래 Timeout

```text
DB Query
<
Transaction
<
Web Request
<
Client Request
```

### Connection Lifecycle

```text
Tomcat KeepAlive
L4 Idle Timeout
L4 Sticky Timeout
Session Timeout
```

따라서 `L4 Idle Timeout`을 Transaction Timeout의 최종 단계라고 정의하지 않는다.

---

# 22. GSLB/L4 환경

현재 자료를 기준으로 관리대상은 다음과 같다.

| 항목 | 기준 |
|---|---:|
| GSLB DNS TTL | 30~60초 |
| L4 Health Check Interval | 5~10초 |
| Health Check Timeout | 2~3초 |
| Fail Count | 3회 |
| Sticky/Persistence | 적용 |
| L4 Sticky | Session보다 길게 |
| Web Request Timeout | 6~8초 |
| Tomcat KeepAlive | 5초 |

기존 90분 세션 기준 문서에서도 GSLB TTL 30~60초, L4 Sticky 100~120분, Tomcat Session 90분 등의 관계가 제시되어 있다.

---

# 23. Session 아키텍처

## 23.1 Session 기준

| 항목 | Current Working Baseline |
|---|---:|
| 전체 사용자 | 36,000 |
| 설계 세션 | ≥43,200 |
| Idle Session Timeout | **90분 Working Baseline** |
| Absolute Timeout | 12시간 |
| 권장 Session 크기 | ≤2KB |
| 최대 Session 크기 | ≤5KB |

### Session 저장 금지

- 고객 대량조회 결과
- 거래 목록
- 대용량 DTO
- 업무성 Cache
- Binary 객체

---

# 24. DeltaManager 아키텍처

```text
센터 A
┌─────────────────────────────┐
│ Tomcat JVM #1               │
│       ↕                     │
│ DeltaManager                │
│       ↕                     │
│ Tomcat JVM #2               │
└─────────────────────────────┘

센터 B
┌─────────────────────────────┐
│ 별도 AP Cluster             │
└─────────────────────────────┘
```

### 원칙

| 항목 | 기준 |
|---|---|
| 복제 범위 | 센터 내부 AP Cluster |
| 센터 간 | 기본 미복제 |
| `<distributable/>` | 필수 |
| jvmRoute | 필수 |
| Sticky | 적용 |
| NTP | 필수 |
| Session 객체 | 최소화 |

기존 NSIGHT 세션 기준 역시 DeltaManager를 **센터 내부 AP Cluster**에 제한한다.

---

# 25. 성능 운영 임계치

| 지표 | 정상 기준 |
|---|---:|
| VM TPS | Capacity 범위 내 |
| CPU | 평균 ≤70% |
| JVM Heap | ≤70% |
| GC Pause | ≤200ms |
| Tomcat Busy Thread | ≤70% |
| Hikari Pool | <70~80% |
| Hikari Pending | 지속 발생 금지 |
| DB SQL | 정상 조회 100~300ms 목표 |
| Query Timeout | 1~3초 |
| Transaction Timeout | 3~5초 중심 |
| Web Timeout | 6~8초 |
| p95 | ≤3초 |
| Error Rate | 성능시험 기준 별도 관리 |

---

# 26. 성능 튜닝 순서

성능 문제 발생 시 Thread 또는 Pool부터 임의 확대하지 않는다.

```text
STEP 1
TPS / p95 확인
        ↓
STEP 2
CPU 확인
        ↓
STEP 3
GC / Heap 확인
        ↓
STEP 4
Busy Thread 확인
        ↓
STEP 5
Hikari Active / Pending 확인
        ↓
STEP 6
SQL Time 확인
        ↓
STEP 7
외부연계 시간 확인
        ↓
STEP 8
병목 원인 수정
        ↓
STEP 9
Thread / Pool / Heap 재조정
        ↓
STEP 10
재시험
```

---

# 27. 금지 원칙

| Rule ID | 금지사항 |
|---|---|
| PERF-001 | Heap 확대만으로 TPS 향상을 판단하지 않는다 |
| PERF-002 | Tomcat Thread를 무조건 증가시키지 않는다 |
| PERF-003 | Hikari Pool을 DB Session 검증 없이 증가시키지 않는다 |
| PERF-004 | 전체 사용자 수를 Thread 수로 사용하지 않는다 |
| PERF-005 | Session 수와 동시 요청자를 동일하게 보지 않는다 |
| PERF-006 | Query Timeout을 Transaction Timeout보다 길게 두지 않는다 |
| PERF-007 | 10초 이상 장시간 거래를 일반 온라인 거래로 유지하지 않는다 |
| PERF-008 | 센터 장애 시 최대 Capacity만으로 수용 여부를 판단하지 않는다 |
| PERF-009 | SingleView 대량 결과를 HttpSession에 저장하지 않는다 |
| PERF-010 | 성능시험 없이 855 TPS를 보장 TPS로 확정하지 않는다 |

---

# 28. Architecture Conformance Rule

성능설정은 다음 관계를 자동 검증할 수 있어야 한다.

```text
Query Timeout
<
Transaction Timeout
<
Request Timeout
```

```text
Busy Thread
<
maxThreads
```

```text
Active Connection
<
maximumPoolSize
```

```text
Total DB Pool
<
DB Session Capacity
```

```text
JVM Heap
+
Native
+
Agent
+
OS
+
Reserve
<
VM Memory
```

```text
센터 장애 잔존 Capacity
>=
Peak TPS
```

---

# 29. 성능시험 Gate

## G-PERF-01 정상 부하

```text
600 TPS
```

검증:

- p95 ≤3초
- CPU ≤70%
- Heap ≤70%
- Busy Thread ≤70%
- Pool 대기 없음
- Timeout 오류 없음

## G-PERF-02 Peak

```text
1,200 TPS
```

검증:

- p95 SLA
- CPU
- GC
- Thread
- Pool
- SQL
- DB Session

## G-PERF-03 Stress

```text
1,800 TPS
```

검증:

- 시스템 한계
- Queue 증가시점
- Pool 대기시점
- Error 발생지점
- CPU Saturation
- p95/p99 변화

## G-PERF-04 장애

- VM 1대 Down
- 센터 1개 Down
- DB 지연
- 외부연계 Timeout
- GC 증가

## G-PERF-05 장시간 안정성

- 장시간 부하
- Memory Leak
- Connection Leak
- Thread Leak
- Full GC
- Session 누적

---

# 30. 현재 자료 간 기준 충돌 관리

지금까지 업로드된 문서에는 시점별 기준값이 서로 다르다.

## 30.1 VM당 TPS

```text
기존 보수 기준
16Core = 500 TPS

현재 TPMC Working 기준
16Core = 855 TPS
```

**결론: 855는 성능시험 검증 전 Working Baseline으로 관리한다.**

## 30.2 JVM Heap

```text
기존 일반 AP = 24GB
SingleView = 28GB

현재 JVM 화면
-Xms28g
-Xmx28g
```

**결론: 일반 AP 24GB 초기, SingleView 28GB를 우선 적용하고 28GB 일반 적용은 시험 후 결정한다.**

## 30.3 Hikari

```text
기존
일반 80~100
SingleView 100~120

현재 화면
일반 150
SingleView 180
```

**결론: 현재 값은 DB 점유시간 기반 Working Value이며 전체 DB Session 수 검증 후 확정한다.**

## 30.4 Session Timeout

기존 문서에는 60분 기준과 90분 기준이 모두 존재한다. 90분 기준 자료에서는 43,000~47,000 세션과 DeltaManager 센터 내부 적용이 명확히 정의돼 있다.

**결론: 현재 Excel의 90분을 Working Baseline으로 사용하되 운영 정책 확정 시 공식 Baseline으로 승격한다.**

---

# 31. 최종 Current Working Baseline

| 영역 | 항목 | 기준 |
|---|---|---:|
| 사용자 | 전체 사용자 | 36,000 |
| 세션 | 설계 | 43,200~46,800 |
| 세션 | Idle | 90분 Working |
| TPS | 운영 | 600 |
| TPS | Peak | **1,200** |
| TPS | Stress | **1,800** |
| SLA | p95 | ≤3초 |
| VM | 사양 | 16Core/64GB |
| VM | Capacity | **855 TPS 산정값** |
| JVM | Heap 일반 | **24GB 초기** |
| JVM | Heap SV | **28GB** |
| JVM | GC | G1GC |
| JVM | Pause | ≤200ms |
| Tomcat | maxThreads | **800~1,000** |
| Tomcat | minSpare | 150~200 |
| Tomcat | acceptCount | 500~800 |
| Hikari | 일반 | **150 검증값** |
| Hikari | SV | **180 검증값** |
| Hikari | Connection Timeout | 3초 |
| MyBatis | Query | 1~3초 |
| Spring | TX | 3~5초 중심 |
| WEB | Request | 6~8초 |
| Session | DeltaManager | 센터 내부 |
| CPU | 운영 임계 | ≤70% |
| Heap | 운영 임계 | ≤70% |
| Thread | 운영 임계 | ≤70% |
| Pool | 운영 임계 | ≤70~80% |

---

# 32. 최종 아키텍처 원칙

NSIGHT 성능 아키텍처의 핵심은 다음 한 문장으로 정의한다.

> **NSIGHT의 성능은 특정 JVM·Thread·DB Pool 값을 크게 설정하여 확보하는 것이 아니라, 사용자 → 동시 요청 → TPS → VM → Thread → Connection → SQL → Transaction → Runtime Evidence를 하나의 연속된 Capacity Chain으로 관리하고, 실제 성능시험 결과를 이용하여 설정값을 반복 보정함으로써 확보한다.**

최종 Closed Loop는 다음과 같다.

```text
Capacity Planning
       ↓
Environment Baseline
       ↓
Load Test
       ↓
Runtime Metrics
       ↓
Bottleneck Analysis
       ↓
Parameter Tuning
       ↓
Regression Test
       ↓
Peak / Failure Test
       ↓
Architecture Baseline 확정
       │
       └───────────────┐
                       ↓
                 운영 Evidence
                       │
                       └──→ 재튜닝
```

이를 NSIGHT의 **Performance & Runtime Configuration Architecture Baseline**으로 관리한다.