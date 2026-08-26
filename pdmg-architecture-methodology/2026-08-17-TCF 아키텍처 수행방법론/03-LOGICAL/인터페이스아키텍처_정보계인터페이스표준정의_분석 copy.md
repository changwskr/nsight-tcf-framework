# 인터페이스 아키텍처 — 정보계 인터페이스 표준 정의 분석

## 1. 핵심 결론

정보계 인터페이스 표준의 목표는 **시스템 간 자원 결합을 최소화하여 각 서비스 플랫폼의 독립성을 유지**하는 것이다. 이를 위해 인터페이스를 세 유형으로 표준화한다.

| 유형 | 표준 수단 | 적용 대상 | 핵심 특성 |
|---|---|---|---|
| 온라인 인터페이스 | JSON/HTTP, API Gateway, Kafka | 동기 API·이벤트 | 실시간, 계약 기반, GUID 추적 |
| 데이터 인터페이스 | CDC, ETL | DB 변경·대량 데이터 | 실시간 변경 동기화 또는 배치 적재 |
| 파일 인터페이스 | FOS, MFT | 대용량·정형 파일 | 파일 전송 표준화, 무결성·재처리 |

```text
                 ┌──────────────────────────────────────┐
                 │ 정보계 인터페이스 표준               │
                 ├────────────┬────────────┬────────────┤
                 │ 온라인     │ 데이터     │ 파일       │
                 │ JSON/HTTP  │ CDC        │ FOS        │
                 │ API G/W    │ ETL        │ MFT(내부)  │
                 │ Kafka      │            │            │
                 └────────────┴────────────┴────────────┘
                                │
                  자원 격리 + 계약 표준 + 추적성
                                │
                     플랫폼 독립성·장애 격리
```

장표에서 API Gateway–EAI, FOS–MFT 사이의 연계는 인터페이스 솔루션 내부에서 처리하도록 정의한다. 즉 업무 애플리케이션은 EAI·MFT 제품 세부사항에 직접 결합되지 않고, 표준 진입점인 API Gateway 또는 FOS를 사용한다.

## 2. 장표 원문 전사

### 2.1 차세대 정보계 시스템 구축 기준

> “도메인 특성을 고려한 서비스 플랫폼 독립 정의”

| 구축 기준 | 장표 설명 | 아키텍처 의미 |
|---|---|---|
| 온라인 AP / 배치 AP 자원 분리 | 온라인 실시간 거래와 배치 처리 자원 충돌 방지 | 서로 다른 부하 특성·장애 도메인 격리 |
| ETL 서버 독립 서버 구성 | DataStage 기반 대량 배치 데이터 가공/정제 전용 공간으로 대량 배치 공간 마련 | 대용량 I/O·CPU 작업을 온라인에서 분리 |
| 이벤트 서버 분리 | Kafka 이벤트 스트리밍 전용 고속 실시간 고객 오퍼링 | 비동기 이벤트 처리와 버퍼링 전담 |
| CDC 중계서버 제공 | 계정계 원천 DB 변경사항 실시간 캡처, 원천 DB 부하 분산 | Source DB 영향 최소화·중계 계층 확보 |
| ADW | 전략 분석 전용 대용량 데이터 관리 | 분석·집계·대량 조회 워크로드 수용 |
| RDW | 실시간 OLTP 운영 관리로 CDC로 수집된 데이터 관리 | 실시간 원천 변경과 운영성 데이터 수용 |

### 2.2 차세대 정보계 인터페이스 원칙

#### 온라인 인터페이스

- 정보계 애플리케이션 시스템은 JSON/HTTP 방식으로 프로토콜 일원화
- GUID 기반 전문 적용으로 거래 모니터링
- 이벤트 데이터 Streaming은 Kafka로 처리
- 시스템 간 연계는 API Gateway(Cruz APIM)로 일원화
- API Gateway와 EAI 간 연계는 인터페이스 솔루션 내부에서 처리

#### 데이터 인터페이스

- 실시간 데이터 동기화는 CDC로 처리
- 배치 처리 데이터 동기화는 ETL로 처리

#### 파일 인터페이스

- 파일 연계는 FOS로 일원화
- FOS와 MFT 간 연계는 인터페이스 솔루션 내부에서 처리

## 3. 전체 논리 아키텍처

```text
┌────────────────── Source / Channel / Internal Systems ──────────────────┐
│                                                                          │
│  동기 요청 ──JSON/HTTP──> [API Gateway: Cruz APIM] ──> [EAI 내부연계]    │
│                                  │                                       │
│                                  └──────────────> [Online AP]            │
│                                                                          │
│  이벤트 ────────────────> [Kafka Event Server] ─────> [Event Consumer]  │
│                                                                          │
│  DB 변경 ───────────────> [CDC Relay] ──────────────> [RDW]             │
│                                                                          │
│  배치 데이터 ───────────> [ETL / DataStage] ─────────> [RDW / ADW]       │
│                                                                          │
│  파일 ──────────────────> [FOS] ──[MFT 내부연계]────> [Target System]   │
└──────────────────────────────────────────────────────────────────────────┘

자원 격리
  Online AP ≠ Batch AP ≠ ETL ≠ Kafka Event ≠ CDC Relay

데이터 역할
  RDW = 실시간·운영성 데이터
  ADW = 전략 분석·대용량 데이터
```

## 4. 독립성 확보 원칙

### 4.1 자원 결합 최소화

- 온라인 AP와 배치 AP는 CPU·메모리·Thread Pool·Connection Pool을 분리한다.
- ETL은 전용 서버에서 대량 I/O와 변환을 처리한다.
- Kafka는 이벤트 저장·전달을 담당하며 업무 AP의 동기 처리와 분리한다.
- CDC 중계는 원천 DB에서 추출과 전송 부하를 분산한다.
- RDW와 ADW는 데이터 목적과 워크로드에 따라 저장·접근 노드를 분리한다.

### 4.2 기술 결합 최소화

- 업무 시스템은 EAI 또는 MFT 제품 API를 직접 호출하지 않는다.
- 온라인 연계는 API Gateway의 논리 API 계약에 의존한다.
- 파일 연계는 FOS의 논리 송수신 계약에 의존한다.
- Kafka 생산자·소비자는 Topic과 Event Schema 계약을 기준으로 통신한다.
- CDC·ETL 소비자는 원천 물리 테이블보다 표준 데이터 모델·매핑 계약을 우선한다.

### 4.3 장애 결합 최소화

- 동기 호출의 Timeout·Retry·Circuit Breaker를 표준화한다.
- 이벤트는 Consumer 장애 시 Broker에 보존하고 재처리한다.
- ETL·파일은 작업 상태와 체크포인트로 재시작 가능하게 한다.
- CDC 지연이 원천 DB 트랜잭션을 장시간 방해하지 않도록 추출 구조를 격리한다.
- 한 인터페이스의 실패가 전체 배치 또는 온라인 Thread를 고갈시키지 않게 Bulkhead를 적용한다.

## 5. 온라인 인터페이스 표준

### 5.1 JSON/HTTP 프로토콜 일원화

정보계 애플리케이션의 동기 서비스는 JSON Payload와 HTTP 기반으로 표준화한다.

#### 권장 요청 구조

```http
POST /api/v1/<resource-or-service> HTTP/1.1
Content-Type: application/json
Accept: application/json
X-GUID: <globally-unique-transaction-id>
X-Correlation-ID: <end-to-end-correlation-id>
Idempotency-Key: <request-deduplication-key-if-required>
Authorization: Bearer <token>
```

```json
{
  "header": {
    "guid": "<GUID>",
    "requestTimestamp": "<ISO-8601>",
    "sourceSystem": "<system-code>",
    "serviceId": "<service-id>",
    "schemaVersion": "1.0"
  },
  "body": {
    "data": {}
  }
}
```

#### 표준화 항목

- UTF-8, `application/json`, ISO-8601 시간 및 명시적 Timezone
- HTTP Method와 Status Code의 일관된 의미
- 필수·선택 필드, 길이, 자료형, Null·빈 문자열 규칙
- 금액·소수점·날짜·코드 값의 직렬화 규칙
- API 및 Message Schema 버전 정책
- 오류 코드, 사용자 메시지와 운영 상세 메시지 분리
- 최대 Payload와 압축·Paging 기준
- 인증·인가·TLS/mTLS·민감정보 마스킹

### 5.2 GUID 기반 거래 추적

GUID는 요청이 API Gateway, EAI, AP, DB, Kafka, 외부 시스템을 거치는 동안 유지되는 거래 식별자다.

```text
Client GUID 생성/수신
  → API Gateway 검증·전달
  → EAI/AP 로그 MDC 저장
  → 하위 호출 Header 전달
  → Kafka Event Header 포함
  → 오류·성능·거래 로그에서 동일 GUID 검색
```

#### GUID 원칙

- 전 구간에서 변경하지 않고 전달한다.
- 외부 GUID가 없으면 최초 진입점에서 생성한다.
- GUID 자체에 개인정보나 시스템 의미를 인코딩하지 않는다.
- 요청/응답·오류·성능 로그에 기록하되 Payload 전체 기록은 제한한다.
- 재시도 식별자와 업무 멱등성 키는 GUID와 목적을 구분한다.
- Batch ID, File ID, Kafka Offset과 상호 연계할 수 있어야 한다.

### 5.3 API Gateway(Cruz APIM) 일원화

```text
Consumer
  → API Gateway
      ├─ 인증·인가
      ├─ TLS 종료/mTLS
      ├─ Rate Limit·Quota
      ├─ Routing·Version
      ├─ Schema/Size 기본 검증
      ├─ GUID·Access Log
      └─ EAI 또는 Target AP 호출
```

API Gateway는 공통 정책과 진입점 통제를 담당하고 업무 로직·복잡한 데이터 변환을 과도하게 포함하지 않는다. EAI 연계는 솔루션 내부에서 처리하여 소비자가 EAI 물리 주소나 제품별 프로토콜을 알지 않게 한다.

### 5.4 동기 호출 복원력

| 항목 | 표준 원칙 |
|---|---|
| Timeout | 연결·응답 Timeout을 호출 경로 전체 예산 내에서 설정 |
| Retry | 읽기·멱등 요청 중심, 지수 Backoff와 Jitter 적용 |
| Circuit Breaker | 하위 시스템 장애 시 빠른 실패와 자원 보호 |
| Bulkhead | 서비스/하위 시스템별 Thread·Connection Pool 격리 |
| Rate Limit | 소비자·API별 과부하 방지 |
| Idempotency | 중복 요청이 업무 결과를 중복 생성하지 않게 처리 |
| Fallback | 데이터 정합성을 훼손하지 않는 범위에서만 적용 |

## 6. 이벤트 인터페이스 — Kafka Streaming

Kafka는 고속 실시간 고객 오퍼링 등 비동기 이벤트 데이터를 처리한다.

```text
Producer
  → Topic / Partition
      → Kafka Broker Cluster
          ├─ Consumer Group A: 행동정보 처리
          ├─ Consumer Group B: 실시간 오퍼링
          └─ Consumer Group C: 모니터링/분석
```

### 이벤트 계약

```json
{
  "eventId": "<unique-event-id>",
  "guid": "<origin-transaction-guid>",
  "eventType": "<domain.event-name>",
  "eventVersion": "1.0",
  "occurredAt": "<ISO-8601>",
  "producer": "<system-code>",
  "key": "<partition-key>",
  "payload": {}
}
```

### 운영 원칙

- Topic 명명, 소유자, Partition Key, 보존 기간과 데이터 등급을 등록한다.
- Schema Registry 또는 동등한 계약 관리로 호환성을 통제한다.
- 기본 전달 의미를 명시하고 소비자는 중복 이벤트에 멱등해야 한다.
- 처리 실패는 제한 재시도 후 Retry Topic 또는 DLQ로 격리한다.
- Consumer Lag, 처리율, 오류율, Rebalance, ISR 상태를 감시한다.
- 이벤트 순서가 필요한 범위를 Partition Key로 제한한다.
- 개인정보는 최소화·암호화하고 삭제·보존 요구를 설계에 포함한다.

## 7. 데이터 인터페이스 표준

### 7.1 실시간 동기화 — CDC

CDC는 원천 DB의 변경 로그를 읽어 Insert·Update·Delete 변경을 실시간에 가깝게 전달한다.

```text
계정계 Source DB
  → Redo/Transaction Log
    → CDC Extract
      → CDC Relay / Trail / Queue
        → Apply
          → RDW 실시간 데이터
```

#### 핵심 통제

- 원천 DB 부하와 Log Retention 영향을 사전 측정한다.
- Table/Column, PK, DDL 변경과 TRUNCATE 처리 범위를 정의한다.
- 초기 적재와 증분 적용의 전환 지점을 일관되게 관리한다.
- Commit 순서, Transaction 경계와 데이터 정합성을 보존한다.
- 지연, Extract/Apply 오류, Trail/Queue 용량과 Checkpoint를 감시한다.
- 재기동·재처리·역전환 시 중복 및 데이터 유실을 검증한다.
- 민감 컬럼은 전달 제외·마스킹·암호화한다.

### 7.2 배치 동기화 — ETL

DataStage 기반 ETL은 대량 데이터의 추출·가공·정제·적재를 독립 서버에서 수행한다.

```text
Source
  → Extract
    → Stage
      → Validate / Cleanse / Transform
        → Load RDW/ADW
          → Reconcile / Audit
```

#### 핵심 통제

- Full/Incremental 추출 방식과 기준 시점을 명시한다.
- Watermark·Batch ID·Checkpoint로 재시작 가능하게 한다.
- 입력·출력 건수, 합계, Hash와 Reject 건수를 대사한다.
- 대량 배치 창, 병렬도, DB Node/Resource Group과 Lock 영향을 관리한다.
- 오류 데이터는 Reject 영역에 격리하고 승인된 재처리 절차를 둔다.
- 원천 Schema 변경을 사전 탐지하고 매핑 버전을 관리한다.
- ETL 완료 신호와 후속 Job 의존성을 Control-M 등 스케줄러에서 통제한다.

### 7.3 CDC와 ETL 선택 기준

| 기준 | CDC | ETL |
|---|---|---|
| 지연 요구 | 초·분 단위 | 분·시간·일 배치 |
| 데이터 단위 | 변경 행/트랜잭션 | 대량 집합 |
| 변환 복잡도 | 낮음~중간 | 중간~높음 |
| 원천 영향 | 로그 기반, 관리 필요 | 추출 Query·파일 부하 관리 |
| 재처리 | Checkpoint/Trail 기반 | Batch/Partition 단위 |
| 주요 대상 | RDW 실시간 데이터 | RDW/ADW 정제·분석 데이터 |
| 적합 사례 | 실시간 운영정보·변경 전파 | 집계·정제·대량 이력 적재 |

CDC와 ETL을 같은 데이터에 병행하면 중복·순서 역전이 발생할 수 있으므로, 데이터셋별 System of Record와 적재 책임을 하나로 지정해야 한다.

## 8. 파일 인터페이스 표준 — FOS/MFT

업무 시스템은 파일 연계 표준 진입점으로 FOS를 사용하고, 외부·대내 전송 제품인 MFT와의 연계는 인터페이스 솔루션 내부에서 처리한다.

```text
Sending System
  → FOS 표준 요청
    → 파일 등록·메타데이터·상태 관리
      → MFT 내부 전송
        → 수신 FOS/Agent
          → Target System
```

### 파일 계약

| 항목 | 정의 내용 |
|---|---|
| Interface ID | 송수신 계약의 고유 식별자 |
| File ID / GUID | 파일·거래 추적 식별자 |
| 송신·수신 시스템 | 시스템 코드, 담당자, 환경 |
| 파일명 규칙 | 업무코드·기준일·순번·확장자 |
| 형식 | CSV, Fixed Length, JSON, Binary 등 |
| 문자셋·개행 | UTF-8/EUC-KR, LF/CRLF |
| 압축·암호화 | 알고리즘, 키 관리, 순서 |
| 무결성 | 크기, 건수, Hash/Checksum |
| 완료 신호 | 임시 확장자 Rename, Manifest, Control File |
| SLA | 전송 시각, 최대 크기·시간, 재시도 |
| 보존·삭제 | 송·수신·중계 구간별 기간 |

### 처리 상태

```text
REGISTERED → TRANSFERRING → DELIVERED → VALIDATED → CONSUMED
                   └──────> RETRYING / FAILED / QUARANTINED
```

### 운영 원칙

- 파일 작성 중 수신 측이 읽지 않도록 임시명 후 원자적 Rename 또는 완료 Control File을 사용한다.
- 동일 File ID의 중복 수신은 멱등하게 차단하거나 버전으로 관리한다.
- 크기·건수·Checksum을 송신·중계·수신 구간에서 대조한다.
- 개인정보 파일은 전송·저장 암호화와 최소 보존을 적용한다.
- 실패 재시도와 업무 재처리를 구분하고 운영 승인 이력을 남긴다.

## 9. 인터페이스 선택 의사결정

```text
연계 요구
├─ 즉시 응답이 필요한가?
│  └─ Yes → 온라인 JSON/HTTP + API Gateway
│
├─ 비동기 이벤트·다수 소비자인가?
│  └─ Yes → Kafka Streaming
│
├─ DB 변경을 저지연 복제하는가?
│  └─ Yes → CDC
│
├─ 대량 가공·정제·집계가 필요한가?
│  └─ Yes → ETL
│
└─ 대용량 파일·대외 전송 계약인가?
   └─ Yes → FOS + 내부 MFT
```

| 요구 | 권장 방식 | 피해야 할 방식 |
|---|---|---|
| 단건 조회·명령과 즉시 결과 | API | DB 직접 조회, 공유 테이블 |
| 고객 행동 이벤트·다중 구독 | Kafka | 동기 API Fan-out |
| 원천 DB 변경 실시간 전파 | CDC | 초단위 Full Query |
| 대량 정제·집계 | ETL | 온라인 AP에서 대량 처리 |
| 대용량 정형 파일·대외 송수신 | FOS/MFT | 개인 SFTP·수동 복사 |

## 10. 공통 전문·계약 표준

모든 방식은 공통 메타데이터를 가져야 한다.

```yaml
interface_id: <unique-id>
interface_type: online|event|cdc|etl|file
source_system: <system-code>
target_system: <system-code>
owner: <organization>
guid_or_run_id: <trace-id>
schema_version: <version>
data_classification: public|internal|confidential|personal
sla:
  availability: <target>
  latency_or_deadline: <target>
  recovery: <RTO/RPO>
retention: <period>
```

### 오류 모델 예시

```json
{
  "guid": "<GUID>",
  "timestamp": "<ISO-8601>",
  "interfaceId": "<interface-id>",
  "error": {
    "code": "<standard-error-code>",
    "category": "VALIDATION|AUTH|BUSINESS|SYSTEM|TIMEOUT",
    "message": "<safe-client-message>",
    "retryable": false
  }
}
```

내부 Stack Trace, SQL, 개인정보, 인증 토큰은 응답 전문에 노출하지 않고 GUID로 운영 로그와 연결한다.

## 11. 보안 표준

| 영역 | 보안 통제 |
|---|---|
| 온라인 API | TLS/mTLS, OAuth2/JWT 또는 승인 인증, Schema·Rate Limit, WAF/APIM 정책 |
| Kafka | Broker TLS, Producer/Consumer 인증, Topic ACL, Schema·민감정보 통제 |
| CDC | 최소 DB 권한, Log 접근 통제, Trail 암호화, 대상 Apply 권한 제한 |
| ETL | 서비스 계정 분리, DB Credential Vault, Stage 암호화·정리 |
| 파일 | MFT/FOS 인증, 전송·저장 암호화, Checksum, 악성코드 검사 |

공통적으로 출발지·목적지·포트를 화이트리스트로 제한하고 운영·개발·DR 자격증명을 분리한다.

## 12. 관측성과 거래 모니터링

### 공통 추적 키

- GUID / Correlation ID
- Interface ID / Service ID
- Event ID / Topic / Partition / Offset
- CDC Process / Trail / Checkpoint / SCN 계열 위치
- ETL Batch ID / Job / Stage / Watermark
- File ID / Transfer ID / Checksum

### 방식별 지표

| 방식 | 핵심 지표 |
|---|---|
| API | TPS, 성공률, P95/P99 지연, Timeout, 4xx/5xx, Circuit 상태 |
| Kafka | Produce/Consume Rate, Consumer Lag, ISR, DLQ, Rebalance |
| CDC | Capture/Apply Lag, Checkpoint, Trail 용량, 오류·재시작 |
| ETL | 시작·종료, 처리/Reject 건수, 처리량, 지연, 대사 결과 |
| FOS/MFT | 전송 성공률, 대기시간, 재시도, 파일 크기·Hash 불일치 |

GUID를 모든 방식의 유일 키로 강제하기보다 방식별 실행 ID를 유지하고 상호 연계 관계를 저장하는 것이 적합하다.

## 13. 장애·재처리 원칙

| 방식 | 실패 단위 | 재처리 기준 | 중복 방지 |
|---|---|---|---|
| API | 요청 | 멱등 요청만 제한 재시도 | Idempotency Key·업무키 |
| Kafka | Event/Offset | Retry Topic·DLQ 후 재소비 | Event ID·소비 이력 |
| CDC | Transaction/Checkpoint | Trail·Checkpoint 기준 재개 | Apply 상태·PK·Commit 순서 |
| ETL | Batch/Partition | Checkpoint·Watermark 기준 | Batch ID·적재 이력 |
| File | File/Chunk | 동일 File ID 재전송 | Hash·Manifest·처리 상태 |

재시도 횟수만 늘리는 방식은 장애를 증폭할 수 있으므로 Backoff, 최대 횟수, 격리 큐 및 운영 승인 재처리를 적용한다.

## 14. 변경·버전 관리

- API는 하위 호환 변경과 Breaking Change를 구분하고 버전을 관리한다.
- JSON 필드는 소비자가 알 수 없는 필드를 허용하도록 확장 가능하게 설계한다.
- Kafka Event Schema는 호환성 정책과 소비자 전환 기간을 둔다.
- CDC 원천 DDL 변경은 사전 영향분석과 Apply 매핑 변경을 거친다.
- ETL Mapping·Job·Schema는 소스관리와 배포 승인을 적용한다.
- 파일 Layout 변경은 새 Interface/Version과 병행 기간을 둔다.
- 인터페이스 폐기 시 소비자 확인, 트래픽 0 확인, 정책·Topic·계정·포트 회수를 수행한다.

## 15. 인터페이스 등록부

각 인터페이스는 중앙 Repository 또는 CMDB에 다음 정보를 등록해야 한다.

| 분류 | 필수 정보 |
|---|---|
| 식별 | Interface ID, 이름, 유형, 버전, 상태 |
| 관계 | 송신·수신 시스템, 소유자, 담당자 |
| 계약 | API/Schema/File Layout, Topic/Table, 필수 필드 |
| 기술 | URL, Topic, CDC Group, ETL Job, FOS Route, 포트 |
| 운영 | 주기, SLA, Timeout, Retry, 재처리, 모니터링 |
| 보안 | 데이터 등급, 인증, 암호화, 접근정책 |
| 이력 | 변경·승인·배포·폐기 일자와 근거 |

## 16. 주요 위험과 대응

| 위험 | 영향 | 대응 |
|---|---|---|
| JSON/HTTP만 통일하고 계약 미관리 | 필드·오류·버전 불일치 | OpenAPI/Schema와 호환성 통제 |
| GUID 재생성·누락 | 종단 거래 추적 단절 | 최초 생성 후 전 구간 전달·검증 |
| API Gateway에 업무 로직 집중 | Gateway 병목·제품 종속 | 공통 정책만 배치, 업무 로직은 AP/EAI |
| Kafka 중복·순서 가정 | 고객 오퍼 중복·상태 역전 | 멱등 소비·Partition Key·Event ID |
| CDC와 ETL 중복 적재 | 중복·정합성 오류 | 데이터셋별 적재 책임과 SoR 명시 |
| CDC 원천 부하·로그 보존 부족 | 운영 DB 영향·데이터 유실 | 중계서버·Lag/Log Retention 감시 |
| ETL 대사 미흡 | 대량 누락·중복 | 건수·합계·Hash·Reject 대사 |
| FOS/MFT 내부 동작 불투명 | 장애 위치 추적 어려움 | 구간별 Transfer ID·상태·SLA 공개 |
| 자원은 분리했으나 DB Pool 공유 | 배치가 온라인 DB 자원 고갈 | 계정·Service·Resource Group·Pool 분리 |
| 제품별 직접 연계 | 플랫폼 교체 비용 증가 | API Gateway/FOS 표준 진입점 준수 |

## 17. 검증 체크리스트

- [ ] 인터페이스가 온라인·이벤트·CDC·ETL·파일 중 적절한 유형으로 분류되었는가?
- [ ] 온라인 AP, 배치 AP, ETL, Kafka, CDC 자원이 실제로 분리되어 있는가?
- [ ] 온라인 API가 JSON/HTTP와 공통 Header·오류 표준을 준수하는가?
- [ ] GUID가 최초 진입부터 하위 호출·로그·이벤트까지 유지되는가?
- [ ] API Gateway를 우회하는 시스템 간 직접 호출이 통제되는가?
- [ ] Gateway–EAI 내부 연계의 소유자·SLA·모니터링이 정의되어 있는가?
- [ ] Kafka Topic, Partition Key, Schema, ACL, Retention, DLQ가 등록되어 있는가?
- [ ] 실시간 변경은 CDC, 대량 가공은 ETL이라는 선택 원칙을 준수하는가?
- [ ] CDC와 ETL이 동일 데이터를 중복 적재하지 않는가?
- [ ] ETL의 Batch ID·Watermark·Checkpoint·대사 기준이 있는가?
- [ ] 파일 연계가 FOS를 경유하고 FOS–MFT 내부 상태를 추적할 수 있는가?
- [ ] 파일명·Layout·문자셋·Checksum·완료 신호·재처리 규칙이 있는가?
- [ ] 모든 방식에 인증·암호화·최소 권한·민감정보 통제가 적용되는가?
- [ ] Timeout·Retry·Circuit Breaker·멱등성 원칙이 적용되는가?
- [ ] 방식별 지표와 실행 ID가 GUID/Interface ID에 연계되는가?
- [ ] 인터페이스 계약·버전·소유자·SLA가 중앙 등록부에 존재하는가?
- [ ] 운영·개발·DR 전환과 장애·재처리 시나리오가 시험되었는가?

## 18. 최종 평가

이 표준은 정보계 연계를 온라인, 데이터, 파일이라는 세 축으로 단순화하고 각각 API Gateway·Kafka, CDC·ETL, FOS·MFT라는 책임 컴포넌트에 수렴시켜 플랫폼 간 결합을 줄이는 구조다. 실효성을 확보하려면 제품 경유 원칙뿐 아니라 **계약 버전, GUID 종단 추적, 멱등성, 재처리, 데이터 대사, 보안, SLA와 중앙 인터페이스 등록부**까지 운영 표준으로 구현해야 한다. 특히 동기 API와 이벤트, CDC와 ETL의 선택 경계를 명확히 하고 API Gateway 또는 FOS 내부의 EAI·MFT 연계도 가시성과 책임 범위를 잃지 않도록 관리하는 것이 핵심이다.

