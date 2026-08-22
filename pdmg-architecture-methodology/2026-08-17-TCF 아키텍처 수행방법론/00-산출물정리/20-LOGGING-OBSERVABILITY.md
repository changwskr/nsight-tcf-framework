# 20. NSIGHT Logging / Observability Architecture

## 1. 목적

NSIGHT의 Logging/Observability는 단순 로그 파일 적재가 아니라 **하나의 거래를 GUID + ServiceId로 식별하고, 시스템 진입부터 TCF·업무·DB·외부연계·오류·응답·런타임 상태까지 End-to-End로 추적**하는 운영 증적 아키텍처다.

본 문서는 PDMG AS-IS의 Logging/ImageLog 구조를 Reference로 사용하되 NSIGHT TO-BE의 다중 WAR·다중 Tomcat JVM·다중 서버 환경으로 확장한 기준을 정의한다.

## 2. Evidence Basis

- `Logging Architecture 설명.txt`
- `Imaging Logging 아키텍처.txt`
- `운영 통제 아키텍처.txt`
- `31-RUNTIME-EVIDENCE.md`
- `09-TRANSACTION-TIMEOUT.md`
- `16-CAPACITY-PERFORMANCE.md`

## 3. 핵심 식별키

```text
GUID      = 거래 인스턴스 Identity
ServiceId = 거래 종류 / Use Case Identity
Host      = 물리 실행 노드
JVM       = Tomcat 실행 인스턴스
Thread    = 실제 실행 Thread
SQL ID    = DB 실행 식별
```

최종적으로 다음 관계가 유지되어야 한다.

```text
GUID + ServiceId
      ↓
WEB / Apache
      ↓
Tomcat JVM / Thread
      ↓
TCF / Timeout / Transaction
      ↓
Handler / Facade / Service
      ↓
DAO / Mapper / SQL
      ↓
External Call / DB
      ↓
Response / Error
      ↓
OM / Runtime Evidence
```

## 4. Logging 분류

| 로그 유형 | 책임 영역 | 핵심 목적 | 필수 Key |
|---|---|---|---|
| System Log | Filter / Interceptor | HTTP 요청 생명주기 | GUID, ServiceId, User, IP |
| Transaction Log | TCF / STF / ETF | 거래 시작·통제·종료 | GUID, ServiceId, TX 상태 |
| Business Log | Handler/Facade/Service/AOP | 업무 흐름 | GUID, ServiceId, Method |
| ImageLog | Framework | 요청/응답/오류 증적 | GUID, ServiceId, Message |
| Error Log | Exception Handler | 장애 원인 | GUID, ErrorCode, Exception |
| Runtime Evidence | OM/APM/Metric | JVM/Thread/Pool/SQL 상태 | Host, JVM, ServiceId, Thread |

## 5. 표준 흐름

```text
Request
  ↓
GUID / ServiceId 확정
  ↓
MDC / Context 구성
  ↓
Request Log
  ↓
PRE ImageLog
  ↓
TCF / Business / DB
  ↓
정상 ───────────── 오류
  ↓                 ↓
Response Log     Error Log
  ↓                 ↓
POST ImageLog   EXCEPTION ImageLog
       \           /
        Runtime Evidence
              ↓
      Context / MDC Clear
              ↓
           Response
```

## 6. Thread 전환 Observability

Timeout Executor가 Request Thread에서 Worker Thread로 전환하는 경우 GUID·ServiceId·SecurityContext·ServiceContext·MDC가 동일하게 유지되어야 한다.

```text
Tomcat Request Thread
GUID=A001 / ServiceId=mg...
        ↓ capture
Worker Context
        ↓ restore
Worker Thread
GUID=A001 / ServiceId=mg...
```

Thread 전환 후 GUID/ServiceId가 소실되면 G80 Runtime Evidence Gate에서 FAIL 처리한다.

## 7. ImageLog 원칙

ImageLog는 업무 코드가 직접 저장하지 않고 Framework가 관리한다.

```text
Business Service → ImageLog 직접 INSERT  [금지]
Framework ImageLog Handler               [허용]
```

표준 이벤트:

- PRE: 요청 수신 시점
- POST: 정상 응답 완료 시점
- EXCEPTION: 오류 응답 시점

민감정보는 원문 적재를 기본으로 하지 않으며 Masking/암호화/필드제외 정책을 별도 Security Rule로 관리한다.

## 8. 운영 지표

최소 Runtime Evidence:

| 영역 | Metric |
|---|---|
| HTTP | TPS, p95/p99, Error Rate, Timeout Rate |
| Tomcat | Active/Busy Thread, Queue, Connector |
| JVM | Heap, GC Pause, Metaspace, CPU |
| Hikari | Active, Idle, Pending, Timeout |
| DB | SQL elapsed, Session, Wait, Slow SQL |
| Transaction | Begin/Commit/Rollback/Timeout |
| Integration | Target, elapsed, timeout, status |
| HA | Node state, health check, failover event |
| Deploy | version, deployment time, rollback |

## 9. Alert 원칙

Alert는 단일 CPU 임계치가 아니라 서비스 영향 기준으로 묶는다.

```text
ServiceId p95 증가
+ Busy Thread 증가
+ Hikari Pending 증가
+ SQL Elapsed 증가
        ↓
동일 GUID / ServiceId로 Correlation
        ↓
원인 후보 식별
```

## 10. 주요 GAP

| GAP ID | 내용 | 우선순위 |
|---|---|---:|
| OBS-G01 | 다중 WAR/JVM 통합 Runtime Evidence Catalog 부재 | P0 |
| OBS-G02 | Slow Transaction → SQL ID 추적 전수 구현 미확인 | P0 |
| OBS-G03 | Worker Thread Context/MDC Leak Test Evidence 없음 | P0 |
| OBS-G04 | ImageLog 민감정보 정책 확정 필요 | P0 |
| OBS-G05 | Error/Timeout 공식 운영 임계치 미승인 | P1 |
| OBS-G06 | Deployment/Failover 이벤트와 거래 Evidence 연결 미완성 | P1 |

## 11. Gate

현재 판정: **CONDITIONAL PASS**

완전 PASS 조건:

1. GUID + ServiceId E2E Trace 실제 증적
2. Thread 전환 Context 유지 시험
3. Slow Transaction / Slow SQL 연계
4. 다중 Host/JVM 통합 OM 수집
5. 민감정보 Logging 정책 승인
6. 운영 Alert/Runbook 연결
