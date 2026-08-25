# 인터페이스 아키텍처 — 표준 인터페이스 구성도 분석

## 1. 핵심 결론

장표는 정보계의 연계를 하나의 제품으로 통합하지 않고, **연계 특성별 표준 중계 컴포넌트**로 분리한다.

| 연계 유형 | 대표 표준 컴포넌트 | 주된 용도 |
|---|---|---|
| 온라인 API | API Gateway(Cruz APIM), GSE, 영업점 MCA, EAI | 대내·대외 동기 거래, 채널 연계 |
| 실시간 데이터 | CDC, CDC 중계 | 코어 DB 변경 데이터의 RDW 실시간 반영 |
| DB 직접 연계 | JDBC, Database 접점 | 애플리케이션·배치의 RDW/ADW 접근 |
| 배치 데이터 | ETL, 배치 AP | 대량 추출·변환·적재 |
| 파일 | FOS, MFT, 대외 MCA | 대내·대외 파일 송수신 |
| 이벤트 스트리밍 | Kafka, 행동정보처리 Daemon, EBM | 고객 행동 이벤트와 실시간 오퍼링 |
| 개발·배포 | GitLab, GitLab Runner, Nexus, eCAMS, LLM | 소스·아티팩트·배포·메타데이터 연계 |

```text
채널/계정계/대내외 시스템
        │
        ├── 온라인 ── API Gateway / GSE / MCA / EAI ──> 애플리케이션
        ├── 실시간 데이터 ── CDC ─────────────────────> RDW
        ├── 배치 데이터 ─── ETL / 배치 AP ───────────> RDW · ADW
        ├── 파일 ────────── FOS / MFT ───────────────> 정보계 · 대내외
        └── 이벤트 ──────── Kafka / EBM ─────────────> 행동정보·마케팅
```

핵심 설계 원칙은 **온라인·데이터·파일·이벤트 인터페이스의 책임을 섞지 않는 것**이다. 모든 경로는 GUID 또는 Interface ID, 데이터 계약, 보안, 정합성, 멱등성, 관측성, 재처리 및 DR 기준을 함께 가져야 한다.

## 2. 장표 구성요소 전사

### 2.1 채널 및 사용자 접점

- 통합업무 시스템
- 영업점 MCA
- 정보계 단말
- Package UI
- 인증/인가
- 단말배포, 단말관리
- 농협은행 및 GSE
- 고객 행태, Wise Collector
- SMS·PUSH·MAIL, UMS

### 2.2 정보계 애플리케이션 영역

```text
애플리케이션
├─ 마케팅플랫폼
│  ├─ 미니 싱글뷰: Service / NH Cloud FWK / WAS
│  └─ 마케팅플랫폼: Service / NH Cloud FWK / WAS
├─ BI 포탈
│  ├─ BI Portal(Data Eye) / Spring Boot / WAS
│  ├─ 신용실적 Service / 프레임워크 / WAS
│  ├─ Self BI 솔루션 / Engine / WAS
│  ├─ OLAP Service / WAS
│  └─ OLAP AP(MSTR)
└─ 데이터거버넌스
   ├─ 비즈메타/데이터품질 솔루션 서비스 / AP
   └─ 데이터흐름관리 솔루션 서비스 / AP

공통 실행 기반: VM
```

### 2.3 데이터 플랫폼

- RDW: 실시간 데이터 영역, DBMS
- ADW: 대량분석 영역, DBMS
- Database 접점
- JDBC
- 배치 AP
- CDC 및 CDC 중계
- ETL

### 2.4 연계 미들웨어

- API Gateway(Cruz APIM)
- EAI
- FOS
- MFT
- GSE
- 영업점 MCA 및 대외 MCA

### 2.5 마케팅 이벤트 처리

- Wise Collector
- Event
- 고객행동데이터 Kafka
- 고객행태 저장/활용
- 행동정보처리서버 Daemon
- 실시간처리서버 EBM
- 이벤트 정보
- UMS 및 SMS·PUSH·MAIL

### 2.6 대내·대외 및 Legacy

- 대내 연계: 코어뱅킹, 연계뱅킹, 단위업무, 코어 DB, BCV
- 대외 기관: NH생명, NH손해, NH멤버스, 농협신용보증, NH경제지주, KT, Nice, Ko 등
- Legacy: 카드정보계, 카드DW, 회계관리, 리스크관리, 경제, 로우코드, Big Data 등

### 2.7 개발 및 운영 지원

- 통합개발환경: GitLab, GitLab Runner, Nexus
- LLM
- 통신관리, FDS, ITSM, 배치작업관리, IT 메타
- 단말·FWK·마케팅·BI포탈 용어 및 소스 배포
- eCAMS 연계정보

## 3. 전체 텍스트 아키텍처

```text
┌──────────────────────────── 채널 영역 ────────────────────────────┐
│ 통합업무 ─ 영업점 MCA ─ 계정거래 ────────────────> 코어뱅킹      │
│ 정보계 단말/Package UI ─ HTTP·JSON·GUID ────────> 정보계 WEB     │
│ 고객 채널 ─ Wise Collector ─ Event ─────────────> Kafka          │
└───────────────────────────────────────────────────────────────────┘
                               │
                               v
┌──────────────────────── 애플리케이션 영역 ───────────────────────┐
│ 마케팅플랫폼 │ BI 포탈 │ 데이터거버넌스                         │
│ Service / Framework / WAS / AP / 솔루션 Engine                   │
└───────────────┬────────────────────┬──────────────────────────────┘
                │ JDBC               │ 온라인 API
                v                    v
┌──────────────────────── 데이터 플랫폼 ────────────────┐   ┌───────┐
│ RDW(실시간) <── CDC ── 코어 DB                      │   │ API   │
│     │                                               │   │ G/W   │
│     └────────── ETL ───────────────> ADW(대량분석)  │   └───┬───┘
│ 배치 AP / Database 접점 / JDBC                      │       │
└───────────────┬─────────────────────────────────────┘       │
                │                                             │
        ┌───────┴────────┐                           ┌─────────┴──────┐
        │ FOS / MFT / ETL│                           │ EAI / 대외 MCA│
        └───────┬────────┘                           └─────────┬──────┘
                │                                             │
                v                                             v
       Legacy / Big Data / 대내 시스템               대외 기관 / 농협은행

이벤트 경로:
Wise Collector → Kafka → 행동정보처리 Daemon → RDW/고객행태
                                      └→ 실시간처리 EBM → UMS → SMS/PUSH/MAIL

개발·배포 경로:
개발자 → GitLab → GitLab Runner → Nexus → 배포 대상
                    └──────── eCAMS / IT 메타 / LLM 연계
```

## 4. 인터페이스 유형별 상세 분석

### 4.1 채널 직접 연계

정보계 단말과 Package UI는 HTTP 기반으로 정보계 애플리케이션에 접근한다. 장표에는 `HTTP · JSON · GUID`가 표시되어 있어 요청 포맷과 거래 추적 식별자를 표준화한 구조로 해석된다.

- GUID는 최초 진입점에서 생성하고 모든 내부 호출에 전파한다.
- 인증 주체, 사용자·단말 ID와 GUID를 분리한다.
- 요청·응답 전문 버전과 오류 코드를 계약화한다.
- Package UI의 파일성 기능은 온라인 HTTP와 FOS 역할을 구분한다.

### 4.2 영업점 MCA 및 GSE

통합업무 시스템과 영업점 MCA는 계정거래 및 싱글뷰 연계를 담당한다. 타 법인 또는 은행 간 표준 전산 연계는 GSE를 경유하는 것으로 표현된다.

- 영업점 거래는 저지연·고가용성이 핵심이다.
- 정보계 장애가 계정거래까지 전파되지 않도록 타임아웃과 차단기를 둔다.
- 전문 번호, GUID, 원거래 번호를 함께 관리한다.
- 조회성 싱글뷰와 갱신성 계정거래의 권한·SLA를 분리한다.

### 4.3 API Gateway(Cruz APIM)

API Gateway는 정보계와 대내·대외 시스템 간 온라인 API의 표준 진입점이다.

```text
Consumer
  → 인증·인가
  → Route/Version 확인
  → Rate Limit/Quota
  → 전문 검증·필요 시 변환
  → Backend 호출
  → 표준 오류·감사 로그
  → Consumer 응답
```

API Gateway는 비즈니스 로직이나 장기 상태를 소유하지 않아야 한다. 정책·라우팅·보안·관측성에 집중하고 업무 처리는 Service가 담당한다.

### 4.4 EAI

장표의 EAI는 코어·연계뱅킹·단위업무와 API Gateway 사이의 기존 인터페이스를 중계하는 위치에 있다.

- 레거시 프로토콜과 표준 API 사이의 어댑터 역할
- 전문 변환 및 라우팅
- 동기·비동기 패턴 중재
- 재시도와 오류 큐 관리

EAI와 API Gateway의 책임이 중복되지 않도록 외부 API 정책은 Gateway, 레거시 전문 중재는 EAI로 구분해야 한다.

### 4.5 CDC 및 CDC 중계

코어 DB의 변경 데이터를 RDW에 실시간 반영하는 경로다. 장표의 CDC 중계는 원천 DB 부하를 낮추고 로그 전달·재전송 책임을 분리하는 구조로 해석된다.

- 커밋 순서와 트랜잭션 경계를 보존한다.
- Capture Lag과 Apply Lag을 분리 측정한다.
- 체크포인트와 로그 보존 기간을 관리한다.
- 스키마 변경과 DDL 호환성을 사전 검증한다.
- 장시간 단절 시 BCV/ETL 재동기화 절차가 필요하다.

### 4.6 JDBC 및 Database 접점

애플리케이션과 배치 AP는 JDBC를 통해 데이터 플랫폼에 접근한다.

- 서비스 계정별 최소 권한
- Connection Pool 상한 및 Timeout
- 읽기·쓰기 DB 서비스 분리
- SQL 추적 시 GUID/실행 ID 연계
- 장기 쿼리와 대량 추출에 대한 Resource Manager 적용

애플리케이션이 다른 시스템의 스키마를 직접 갱신하는 방식은 결합도를 높이므로 금지하고, 소유 서비스/API 또는 승인된 ETL을 사용해야 한다.

### 4.7 ETL 및 배치 AP

ETL은 RDW·ADW·BCV·Legacy 사이의 대량 데이터 변환과 동기화를 맡고, 배치 AP는 업무성 배치 로직을 담당한다.

| 구분 | ETL | 배치 AP |
|---|---|---|
| 중심 책임 | 데이터 추출·정제·변환·적재 | 업무 규칙·서비스 배치 처리 |
| 처리 단위 | 테이블·파티션·업무일자 | Job·Step·업무 객체 |
| 복구 | 워터마크·파티션 체크포인트 | Job Repository·Step 재시작 |
| 정합성 | 건수·합계·해시 | 업무 상태·결과 코드 |

### 4.8 FOS 및 MFT

FOS는 대내 파일 인터페이스의 표준 중계, MFT는 통제된 대외 또는 중요 파일 전송에 사용되는 구조로 보인다.

- 파일명, 레이아웃, 문자셋, 압축·암호화 표준
- Atomic Rename 또는 Manifest 기반 완료 신호
- SHA-256 등 무결성 검증
- 중복 방지용 File ID·Transfer ID
- Quarantine, 재전송, 보존·폐기 정책

FOS와 MFT의 실제 제품 책임은 운영 설계서에서 확정해야 하며, 장표의 선만으로 내부/대외 범위를 단정하지 않는다.

### 4.9 Kafka 이벤트 스트리밍

고객 채널의 행동 데이터는 Wise Collector가 수집하고 Kafka에 발행하며, 행동정보처리서버가 구독한다.

```text
고객 채널
  → Wise Collector
  → Kafka 고객행동 Topic
  → 행동정보처리 Daemon
      ├→ 고객행태/RDW 적재
      └→ 이벤트 판단 → 실시간 EBM → UMS → 고객 메시지
```

- 이벤트 키는 고객 또는 세션 기준 순서 요구에 맞춰 선정한다.
- Schema Registry 또는 동등한 이벤트 계약 관리가 필요하다.
- Consumer Group, Offset, 재처리 및 DLQ 정책을 정의한다.
- 중복 소비를 고려해 소비자는 멱등해야 한다.
- 개인정보의 Topic 보존과 접근 권한을 제한한다.

### 4.10 실시간처리 EBM과 UMS

EBM은 이벤트 기반 마케팅 판단 또는 오퍼링을 수행하고, UMS는 SMS·PUSH·MAIL 발송 채널을 통합한다.

- 고객 동의와 수신 거부를 전송 직전에 확인한다.
- 동일 캠페인 중복 발송과 빈도 제한을 적용한다.
- 판단 이벤트와 실제 발송 결과를 GUID로 연결한다.
- UMS 장애 시 재시도·만료·대체 채널 정책을 둔다.

### 4.11 개발·배포 및 메타데이터 연계

GitLab, GitLab Runner와 Nexus는 소스·빌드·아티팩트 공급망을 구성한다. eCAMS 및 IT 메타는 배포 정보와 구성 메타데이터를 연계하는 것으로 보인다.

- 소스 → 빌드 → 검증 → 아티팩트 → 배포의 추적성을 유지한다.
- 운영 배포는 승인된 불변 아티팩트만 사용한다.
- 비밀정보는 CI 변수 또는 Vault로 관리한다.
- 단말·FWK·마케팅·BI포탈 배포 권한을 분리한다.
- LLM 연계 시 소스·개인정보·운영정보의 반출 통제가 필요하다.

## 5. 표준 책임 매트릭스

| 요구사항 | 담당 컴포넌트 | 금지 또는 주의 사항 |
|---|---|---|
| 외부·대내 API 공개 | API Gateway | Gateway에 업무 로직 집중 금지 |
| 레거시 전문 중재 | EAI/MCA/GSE | 중복 변환 체인 최소화 |
| 실시간 DB 동기화 | CDC | 복잡한 분석 변환 금지 |
| 대량 데이터 이동 | ETL | 준실시간 과다 기동 금지 |
| 업무 배치 | 배치 AP | 임의 타 스키마 갱신 금지 |
| 파일 전송 | FOS/MFT | 미완료 파일 소비 금지 |
| 이벤트 전달 | Kafka | 스키마 없는 이벤트 발행 금지 |
| 메시지 발송 | EBM/UMS | 동의 확인 없는 발송 금지 |
| DB 접근 | JDBC/DB Service | 공유 계정·무제한 Pool 금지 |
| 소스·배포 | GitLab/Runner/Nexus | 재빌드된 운영 배포물 금지 |

## 6. 통합 인터페이스 계약

모든 연계 유형은 다음 공통 필드를 가져야 한다.

| 분류 | 필수 항목 |
|---|---|
| 식별 | Interface ID, GUID/Correlation ID, 명칭, 버전 |
| 소유 | Source·Target 시스템, 담당 조직, RACI |
| 유형 | Online/Data/File/Event, Sync/Async, Push/Pull |
| 계약 | 전문·스키마·파일 Layout·Topic Schema, 호환 버전 |
| 보안 | 인증 방식, 권한, 암호화, 개인정보 등급 |
| 신뢰성 | Timeout, Retry, 멱등 키, 순서, 체크포인트 |
| 운영 | SLA, 모니터링, 알림, 보존, 재처리, DR |

```yaml
interfaceId: IF-MKT-ONLINE-001
version: 1.2.0
type: online
source: channel.mca
mediator: cruz-apim
target: marketing.single-view
protocol: HTTPS
contract: openapi://marketing/single-view/1.2
tracking:
  correlationHeader: X-GUID
reliability:
  timeoutMs: 3000
  retry: 0
  idempotencyKey: requestId
security:
  authentication: mTLS+OAuth2
  classification: confidential
operations:
  slaP95Ms: 1000
  owner: marketing-platform
  drEnabled: true
```

## 7. GUID 및 추적성 표준

GUID는 채널에서 생성되어 API Gateway, Service, JDBC, CDC·ETL 실행, 이벤트, 파일 및 메시지 결과까지 전파되어야 한다.

```text
사용자 요청 GUID
 ├─ API Gateway Access Log
 ├─ WAS Transaction/APM Trace
 ├─ DB Client Identifier / SQL Trace
 ├─ Kafka Header / Event ID
 ├─ ETL·Batch Execution ID
 ├─ File Manifest / Transfer ID
 └─ UMS Message ID
```

서로 다른 처리 단위에는 별도의 Event ID, Job ID, Transfer ID를 사용하되 `parentGuid`로 원거래와 연결한다.

## 8. 오류 및 재처리 표준

### 온라인

- 표준 HTTP 상태와 업무 오류 코드를 분리한다.
- Timeout 후 결과 미확정 상태를 고려해 조회 API 또는 멱등 키를 제공한다.
- 무분별한 자동 재시도를 금지하고 읽기·멱등 요청에만 제한한다.

### CDC·ETL

- 체크포인트부터 재개하고 Source/Target 대사를 수행한다.
- 실패 파티션·Step 단위 재처리를 지원한다.
- CDC 로그 유실 시 기준 데이터 재적재 후 재연결한다.

### 파일

- 오류 파일을 Quarantine으로 격리한다.
- 동일 File ID 재수신 시 멱등하게 처리한다.
- 전송 성공과 업무 처리 성공을 별도 상태로 관리한다.

### 이벤트

- 재시도 Topic과 DLQ를 분리한다.
- Offset 재설정에는 승인과 감사 이력을 남긴다.
- 독성 메시지 반복으로 파티션이 정지하지 않게 한다.

## 9. 보안 아키텍처

- 채널·API: mTLS, OAuth2/JWT, Rate Limit, WAF 연계
- MCA/EAI: 전문 인증, 송수신 기관 식별, 허용 IP
- DB: 인터페이스별 최소 권한 계정, DB 암호화, 감사
- 파일: 전송 암호화, 악성코드 검사, 압축 폭탄·경로 순회 방지
- 이벤트: Topic ACL, 전송 암호화, 민감 필드 최소화
- CI/CD: 서명 아티팩트, Secret Vault, Branch 보호, SBOM
- LLM: 입력 데이터 분류, 비식별화, 프롬프트·응답 감사

## 10. 가용성 및 DR

- API Gateway, EAI, FOS, Kafka는 단일 장애점을 제거한다.
- 주센터와 DR센터의 Active 주체를 명확히 하고 이중 처리를 방지한다.
- CDC는 DR 전환 후 캡처·적용 방향과 체크포인트를 검증한다.
- ETL·배치 스케줄러는 분산 Lock으로 동시 기동을 막는다.
- Kafka는 복제 계수와 최소 ISR을 업무 중요도에 맞춘다.
- 파일은 미전송·전송중·완료·소비 상태를 센터 간 복구할 수 있어야 한다.
- API DNS/GSLB 전환 후 세션·토큰·인증서 유효성을 확인한다.

## 11. 관측성 및 운영 지표

| 영역 | 핵심 지표 |
|---|---|
| API | TPS, P95/P99 지연, 4xx/5xx, Timeout, 차단기 상태 |
| EAI/MCA | 전문 건수, 변환 실패, 큐 적체, 기관별 응답 시간 |
| CDC | Capture Lag, Apply Lag, 로그 위치, 오류 건수 |
| ETL/배치 | Job 상태, 처리량, 배치 윈도, 대사 차이 |
| 파일 | 대기·전송·검증·소비 건수, 전송 시간, 재전송 |
| Kafka | Consumer Lag, 처리량, ISR, DLQ, 재처리 건수 |
| DB | Pool 사용률, 세션, 장기 SQL, Lock, I/O |
| CI/CD | 빌드 성공률, 배포 소요, 롤백, 취약점 결과 |

대시보드는 Interface ID와 GUID를 중심으로 서로 다른 도구의 로그를 연결해야 한다.

## 12. 성능 및 용량 고려사항

- 온라인과 배치 AP의 자원 풀을 분리한다.
- CDC, ETL, Kafka가 DB·네트워크·스토리지에서 경합하지 않게 시간·대역폭을 관리한다.
- API Gateway와 EAI의 이중 변환으로 인한 지연을 최소화한다.
- Kafka 파티션은 처리량뿐 아니라 키별 순서 요구를 고려한다.
- JDBC Pool 합계가 DB 최대 세션을 초과하지 않게 중앙 산정한다.
- 대량 파일은 Chunk, 재개, Checksum 검증을 적용한다.
- UMS 캠페인 급증 시 발송률 제한과 백프레셔를 적용한다.

## 13. 주요 위험과 개선 권고

| 위험 | 영향 | 개선 권고 |
|---|---|---|
| 교차 연결선과 소유 경계 불명확 | 장애 책임·변경 영향 혼선 | 인터페이스 등록부와 RACI 작성 |
| API Gateway·EAI 기능 중복 | 지연·중복 변환 | 정책과 전문 중재 책임 분리 |
| JDBC 직접 결합 확대 | 스키마 변경 연쇄 장애 | API·소유 서비스 우선 원칙 |
| CDC와 ETL 중복 공급 | 데이터 역전·중복 | 데이터 소유권과 우선순위 정의 |
| FOS와 MFT 역할 혼재 | 파일 유실·재처리 혼선 | 내부·대외 전송 책임 계약화 |
| 이벤트 스키마 미관리 | 소비자 장애 | Schema Registry와 호환 정책 |
| GUID 단절 | 종단 추적 불가 | 모든 중계 헤더·로그에 강제 |
| 단일 스케줄러 이중 기동 | 중복 배치·적재 | 센터 간 Active Lock |
| LLM에 민감정보 입력 | 정보 유출 | 분류·차단·비식별·감사 적용 |

## 14. 테스트 기준

| 테스트 | 검증 내용 |
|---|---|
| 온라인 계약 | 요청·응답·오류·버전 호환성 |
| Timeout·멱등 | 응답 유실 후 중복 처리 방지 |
| CDC | CRUD, 순서, 재기동, DDL 변경, 지연 |
| ETL | 전체·증분·부분 실패·대사·재처리 |
| 파일 | 완료 신호, Checksum, 중복, 악성 파일 |
| 이벤트 | 순서, 중복, DLQ, Offset 복구, Schema 호환 |
| DB | Pool 고갈, 장기 SQL, Failover |
| 보안 | 인증·권한·암호화·감사·개인정보 |
| DR | 중계별 전환·원복·중복 기동 방지 |
| 종단 추적 | GUID로 채널부터 DB·메시지까지 조회 |

## 15. 검증 체크리스트

- [ ] 온라인·데이터·파일·이벤트 경로별 표준 중계가 확정되었는가?
- [ ] API Gateway, EAI, MCA, GSE의 책임이 중복 없이 정의되었는가?
- [ ] CDC, ETL, 배치 AP의 데이터 소유권이 정의되었는가?
- [ ] FOS와 MFT의 내부·대외 전송 범위가 확정되었는가?
- [ ] Kafka Topic, Key, Schema, Consumer Group, DLQ가 등록되었는가?
- [ ] Interface ID와 GUID가 모든 계층에서 전파되는가?
- [ ] 전문·스키마·파일·이벤트 계약이 버전 관리되는가?
- [ ] Timeout, Retry, 멱등, 체크포인트, 재처리 기준이 있는가?
- [ ] 최소 권한·암호화·개인정보·감사 기준을 충족하는가?
- [ ] 관측 지표와 종단 대시보드가 구성되었는가?
- [ ] DR 전환·원복 및 이중 처리 방지를 검증했는가?
- [ ] CI/CD 아티팩트와 배포 이력이 추적되는가?

## 16. 장표 해석 시 유의사항

장표는 전체 청사진이므로 일부 연결선의 정확한 Source·Target, 프로토콜, 동기·비동기 여부를 선만으로 확정하기 어렵다. 특히 다음은 상세 인터페이스 목록과 대조해야 한다.

- GSE와 농협은행·정보계 사이의 실제 전문 경로
- API Gateway와 EAI의 호출 방향 및 변환 책임
- FOS와 MFT의 적용 대상 시스템
- Legacy별 온라인·파일·ETL 경로
- LLM의 입력·출력 데이터와 배포 연계 범위
- RDW·ADW 간 적재 방향과 배치 주기

따라서 본 문서는 이미지에서 명확히 표시된 컴포넌트와 표준 패턴을 분석하되, 불명확한 연결은 확정 사실이 아닌 **상세 설계 확인 대상**으로 취급한다.

## 17. 최종 평가

구성도는 채널, 업무 애플리케이션, 데이터 플랫폼, 대내외 연계, 이벤트 처리와 개발·배포 체계를 한 장에 통합해 보여준다. API Gateway·CDC·ETL·FOS·Kafka를 연계 성격에 따라 분리한 방향은 플랫폼 간 결합도를 낮추고 전문성을 확보하는 합리적인 구조다.

성공적인 운영을 위해서는 그림의 연결을 Interface ID 기반 등록부로 구체화해야 한다. 특히 API Gateway와 EAI, FOS와 MFT, CDC와 ETL의 책임 경계를 명확히 하고, GUID 종단 추적, 계약 버전, 멱등성, 정합성, 보안, 관측성 및 DR을 모든 경로의 공통 표준으로 적용해야 한다.
