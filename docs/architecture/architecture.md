# NSIGHT TCF Framework — 아키텍처 정의서

| 항목 | 내용 |
|------|------|
| 문서명 | NSIGHT TCF Framework Architecture Definition |
| 대상 시스템 | `nsight-tcf-framework` (Gradle 멀티 모듈) |
| 버전 | 1.0.0-SNAPSHOT |
| 기술 스택 | Java 21, Spring Boot 3.3, Gradle 8.x, MyBatis, H2, EhCache |
| 최종 갱신 | 2026-06 |

---

## 1. 문서 목적

본 문서는 **NSIGHT TCF(Transaction Control Framework) Framework**의 전체 구조, 구성 요소, 처리 흐름, 배포 모델, 데이터·운영 아키텍처를 정의한다.

대상 독자:

- 프레임워크·업무 서비스 개발자
- 운영(OM) 포털·배치 담당자
- 로컬/통합 환경 구축 담당자

관련 문서:

| 문서 | 경로 |
|------|------|
| 어플리케이션 계층 | [01-application-layer.md](architecture/01-application-layer.md) |
| 표준 전문(준문) | [02-junmun.md](architecture/02-junmun.md) |
| 트랜잭션 처리 | [03-transaction.md](architecture/03-transaction.md) |
| 메시지 처리 | [04-messaging.md](architecture/04-messaging.md) |
| 예외 처리 | [05-exception.md](architecture/05-exception.md) |
| 프로젝트 개요 | [README.md](../../README.md) |
| TCF 개발 가이드 | [TCF_FRAMEWORK_GUIDE.md](../TCF_FRAMEWORK_GUIDE.md) |
| 모듈 재구성 이력 | [TCF_MODULE_RESTRUCTURE.md](../TCF_MODULE_RESTRUCTURE.md) |
| 소스 인덱스 | [SOURCE_INDEX.md](../SOURCE_INDEX.md) |
| Tomcat 배포 | [ztomcat/README.md](../../ztomcat/README.md) |

---

## 2. 시스템 개요

NSIGHT TCF Framework는 마케팅 플랫폼 업무 서비스를 **표준 HTTP/JSON 전문**으로 처리하기 위한 공통 프레임워크이다.

핵심 설계 원칙:

1. **Handler 중심 개발** — 업무 개발자는 `TransactionHandler` 구현과 `serviceId` 등록에 집중한다.
2. **공통 파이프라인** — 검증·세션·권한·로깅·응답 조립은 STF/TCF/ETF가 담당한다.
3. **업무 독립 WAR** — 16개 업무는 동일 패턴의 독립 Spring Boot WAR로 배포한다.
4. **이중 배포 모드** — 개발은 `bootRun`(포트 분리), 통합 검증은 `ztomcat`(8080 게이트웨이)을 지원한다.

```text
                    ┌─────────────────────────────────────────┐
                    │           Client / Channel              │
                    │  (브라우저, tcf-ui Relay, 외부 REST)     │
                    └──────────────────┬──────────────────────┘
                                       │ POST /{code}/online (JSON)
                    ┌──────────────────▼──────────────────────┐
                    │     업무 WAR / tcf-om (Spring Boot)      │
                    │  OnlineTransactionController / Gateway   │
                    └──────────────────┬──────────────────────┘
                                       │
                    ┌──────────────────▼──────────────────────┐
                    │              TCF Engine                  │
                    │         STF → Dispatcher → ETF             │
                    └──────────────────┬──────────────────────┘
                                       │
                    ┌──────────────────▼──────────────────────┐
                    │   Handler → Facade → Service → DAO       │
                    └─────────────────────────────────────────┘
```

---

## 3. 논리 아키텍처 — 모듈 계층

### 3.1 모듈 맵

```text
nsight-tcf-framework
│
├─ [Foundation]
│   ├─ tcf-util          순수 Java 유틸 (Spring 비의존)
│   ├─ tcf-core          TCF 엔진, 표준 전문, STF/TCF/ETF, Dispatcher
│   ├─ tcf-web           HTTP 진입점, Gateway, 자동설정, WAR Bootstrap
│   └─ tcf-cache         EhCache / Spring Cache (선택)
│
├─ [Platform Services]
│   ├─ tcf-om            운영관리(OM) + 파일 업·다운로드(UD)
│   ├─ tcf-batch         OM 대시보드 상태 수집 배치
│   └─ tcf-ui            거래 테스트 UI · OM Admin Relay
│
├─ [Business Domain]
│   └─ cc-service … mg-service   16개 업무 WAR
│
├─ [Legacy]
│   └─ om-service        레거시 OM (tcf-om 권장)
│
└─ [Tooling]
    ├─ tcf-scripts       빌드·실행·배포 스크립트
    └─ ztomcat           로컬 Tomcat 8080 WAR 배포 도구
```

### 3.2 의존 방향

```text
tcf-util
   ↑
tcf-core
   ↑
tcf-web ──→ tcf-cache (선택)
   ↑
   ├── *-service (cc … mg)
   ├── tcf-om
   └── tcf-batch

tcf-ui  ──(HTTP Relay)──→ *-service / tcf-om
tcf-batch ──(JDBC MERGE)──→ 공유 H2 (tcf-om DB)
```

| 모듈 | 산출물 | 역할 |
|------|--------|------|
| `tcf-util` | JAR | GUID, DateTime 등 공통 유틸 |
| `tcf-core` | JAR | TCF 처리 엔진·표준 메시지·Handler 계약 |
| `tcf-web` | JAR | `/online` Controller, `TcfGateway`, 거래로그 DS, WAR 부트스트랩 |
| `tcf-cache` | JAR | 공통코드·카탈로그 EhCache |
| `*-service` | WAR (`{code}.war`) | 업무별 Handler·Facade·Service |
| `tcf-om` | WAR (`tcf-om.war` → `om.war`) | OM Admin API, Spring Session, UD |
| `tcf-batch` | JAR/WAR | Actuator·JDBC 기반 대시보드 수집 |
| `tcf-ui` | JAR/WAR | 정적 UI + API Relay |

---

## 4. TCF 처리 아키텍처

### 4.1 처리 파이프라인

모든 온라인 거래는 `TCF.process()` 단일 진입점을 통과한다.

```text
HTTP POST /online  또는  /{businessCode}/online
        │
        ▼
OnlineTransactionController  /  TcfGateway
        │
        ▼
┌───────────────────────────────────────────────────┐
│ TCF.process(request)                              │
│                                                   │
│  STF.preProcess()                                 │
│    ├─ StandardHeader 검증                         │
│    ├─ GUID / TraceId 부여                         │
│    ├─ SessionValidator (선택)                     │
│    ├─ AuthorizationValidator (선택)               │
│    ├─ IdempotencyChecker (선택)                   │
│    └─ TransactionLogService (거래 시작 로그)       │
│                                                   │
│  TransactionDispatcher.dispatch()                 │
│    └─ header.serviceId → TransactionHandler       │
│                                                   │
│  Handler.doHandle()                                 │
│    └─ Facade → Service → Rule → DAO/Mapper      │
│                                                   │
│  ETF.success() | businessFail() | systemError()   │
│    ├─ StandardResponse 조립                       │
│    ├─ 오류코드 매핑                               │
│    ├─ 감사·메트릭                                 │
│    └─ 거래 종료 로그                              │
└───────────────────────────────────────────────────┘
        │
        ▼
StandardResponse JSON (HTTP 200, resultCode 기반 성공/실패)
```

### 4.2 핵심 컴포넌트

| 컴포넌트 | 패키지/위치 | 책임 |
|----------|-------------|------|
| `TCF` | `tcf-core.processor` | 파이프라인 오케스트레이션 |
| `STF` | `tcf-core.processor` | 전처리(검증·컨텍스트·시작 로그) |
| `ETF` | `tcf-core.processor` | 후처리(응답·오류·종료 로그) |
| `TransactionDispatcher` | `tcf-core.dispatch` | `serviceId` → Handler 라우팅 |
| `TransactionHandler` | `tcf-core.transaction` | 업무 거래 구현 계약 |
| `OnlineTransactionController` | `tcf-web.controller` | REST `/online` 엔드포인트 |
| `TcfGateway` | `tcf-web.gateway` | 비표준 REST → TCF 위임 (UD 등) |
| `TransactionContext` | `tcf-core.context` | 요청 단위 컨텍스트(ThreadLocal) |

### 4.3 업무 확장 모델

업무 모듈은 다음 계층 패턴을 따른다.

```text
handler/     @Component TransactionHandler — serviceId 등록
facade/      업무 오케스트레이션
service/     도메인 로직
rule/        검증 규칙
dao/         JDBC 접근
mapper/      MyBatis Mapper
```

Handler 구현 예:

```java
@Component
public class SvSampleInquiryHandler implements TransactionHandler {
    @Override
    public String serviceId() { return "SV.Sample.inquiry"; }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request,
                           TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
```

---

## 5. 표준 전문 아키텍처

### 5.1 요청/응답 구조

| 필드 영역 | 클래스 | 설명 |
|-----------|--------|------|
| Header | `StandardHeader` | systemId, businessCode, serviceId, transactionCode, processingType, guid, traceId, userId … |
| Body | `Map<String, Object>` | 업무별 가변 페이로드 |
| Result | `Result` | resultCode, resultMessage, errorCode, errorMessage |
| Response | `StandardResponse` | header + result + body |

### 5.2 ProcessingType

| 값 | 용도 |
|----|------|
| `INQUIRY` | 조회 |
| `REGISTER` | 등록 |
| `UPDATE` | 수정 |
| `DELETE` | 삭제 |
| `EXECUTE` | 실행·배치 트리거 등 |

### 5.3 API 계약

| 항목 | 규칙 |
|------|------|
| Method | **POST only** (`GET /online` 불가) |
| Content-Type | `application/json` |
| Path | `/online` 또는 `/{businessCode}/online` |
| HTTP Status | 비즈니스 오류도 대부분 **200 OK** + `result.resultCode`로 구분 |
| 식별 키 | `header.serviceId` (Dispatcher 라우팅 기준) |

---

## 6. 배포 아키텍처

### 6.1 배포 모드 비교

| 구분 | bootRun | ztomcat (Tomcat 8080) |
|------|---------|------------------------|
| 목적 | 모듈 단위 개발·디버깅 | 운영 유사 통합 테스트 |
| 프로세스 | JVM per module | 단일 Tomcat, WAR 19개 |
| 업무 URL | `http://localhost:8086/sv/online` | `http://localhost:8080/sv/online` |
| OM API | `:8097/om/online` | `:8080/om/online` |
| OM UI | `:8099/om/admin/...` | `:8080/ui/om/admin/...` |
| Batch | `:8098/batch/jobs/...` | `:8080/batch/jobs/...` |
| Spring Profile | `bootrun` (batch 등) | `local,tomcat` |

### 6.2 ztomcat Context 맵 (19 WAR)

| Context | Gradle 모듈 | WAR 파일 |
|---------|-------------|----------|
| `/cc` … `/mg` | `cc-service` … `mg-service` | `cc.war` … `mg.war` |
| `/om` | `tcf-om` | `om.war` |
| `/batch` | `tcf-batch` | `batch.war` |
| `/ui` | `tcf-ui` | `ui.war` |

빌드 태스크:

```bash
gradle buildBusinessWars   # 17 WAR (16 업무 + tcf-om)
gradle buildZtomcatWars    # 19 WAR (+ tcf-batch, tcf-ui)
```

### 6.3 WAR 부트스트랩

외부 Tomcat 배포 시 각 애플리케이션은 `NsightWarBootstrap`을 상속한다.

```java
public class NsightSvServiceApplication extends NsightWarBootstrap {
    public NsightSvServiceApplication() {
        super(NsightSvServiceApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(NsightSvServiceApplication.class, args);
    }
}
```

`application-tomcat.yml`이 `local,tomcat` 프로파일과 함께 로드되어 게이트웨이 URL·context 기반 설정이 적용된다.

---

## 7. 데이터 아키텍처

### 7.1 데이터소스 구분

| DS | 용도 | 설정 |
|----|------|------|
| **업무 DS** | 업무별 H2/DB (MyBatis) | `spring.datasource.*` |
| **거래로그 DS** | TCF 거래 시작/종료 로그 | `nsight.tcf.transaction-log-*`, `transactionLogDataSource` |
| **OM DS** | 운영 메타·대시보드·세션 | tcf-om `spring.datasource` → `nsight_om` H2 |
| **Batch DS** | 수집 결과 적재 | tcf-batch → 동일 `nsight_om` H2 |

### 7.2 공유 H2 경로

로컬 환경에서 거래로그·OM·Batch가 동일 파일 DB를 참조하려면 `nsight.txlog.path`를 통일한다.

```text
{project}/data/nsight-txlog/
  ├─ nsight_om.mv.db      ← OM 운영 DB, Batch 적재 대상
  └─ (거래로그 테이블)     ← transactionLogDataSource
```

| 환경 | 설정 방법 |
|------|-----------|
| bootRun | Gradle `bootRun`이 `nsight.txlog.path` 자동 주입 |
| ztomcat | `ztomcat/conf/setenv.bat` → `-Dnsight.txlog.path=...` |

### 7.3 주요 OM 테이블 (대시보드)

| 테이블 | 작성 주체 | 용도 |
|--------|-----------|------|
| `OM_AP_STATUS` | tcf-batch | AP CPU/Heap/Thread |
| `OM_DB_STATUS` | tcf-batch | DB Pool·Health |
| `OM_SESSION_STATUS` | tcf-batch | Spring Session / Tomcat Session |
| `OM_DEPLOY_STATUS` | tcf-batch | WAR 배포·기동 시각 |
| `OM_BATCH_HISTORY` | tcf-batch | 수집 Job 이력 |
| `SPRING_SESSION` | tcf-om | OM Admin 로그인 세션 |
| `OM_USER`, `OM_MENU`, `OM_SERVICE_CATALOG` … | tcf-om | 운영 마스터 |

---

## 8. 운영(OM) 아키텍처

### 8.1 구성 요소 역할

```text
┌─────────────┐     Relay (REST)      ┌─────────────┐
│   tcf-ui    │ ────────────────────→ │   tcf-om    │
│  /ui (WAR)  │   OM.* serviceId      │  /om (WAR)  │
│  정적 HTML  │                       │  Handler 40+│
└──────┬──────┘                       └──────┬──────┘
       │                                     │
       │ relay /api/relay/{code}/online      │ OM.Dashboard.inquiry
       ▼                                     ▼
┌─────────────┐                       ┌─────────────┐
│ *-service   │                       │  공유 H2    │
│  업무 WAR   │                       │ nsight_om   │
└─────────────┘                       └──────▲──────┘
                                           │
                                    ┌──────┴──────┐
                                    │  tcf-batch  │
                                    │  수집 Job   │
                                    └─────────────┘
```

| 컴포넌트 | 책임 |
|----------|------|
| **tcf-ui** | 브라우저 UI, `om-admin.js`/`ui-context.js`로 context path 보정, API Relay |
| **tcf-om** | OM Admin TCF Handler, Spring Session JDBC, UD 파일 API, 대시보드 조회 |
| **tcf-batch** | Actuator/JDBC 수집 → OM 테이블 MERGE, 스케줄·수동 API 제공 |

### 8.2 tcf-ui Relay 흐름

```text
브라우저 (dashboard.html)
  → om-admin.js relayFetch()
  → tcf-ui /api/... 또는 /ui/api/...
  → tcf-om POST /om/online  (OM.Dashboard.inquiry 등)
  → TCF → OmDashboardHandler → DAO
```

Tomcat 모드에서는 `deployment-mode: tomcat`, `tomcat-gateway-url: http://localhost:8080`으로 업무 Relay 대상이 8080 게이트웨이를 가리킨다.

### 8.3 tcf-batch 수집 아키텍처

| Job ID | 수집 유형 | 소스 | 저장 |
|--------|-----------|------|------|
| BAT-BATCH-001 | AP 상태 | `/actuator/health`, `/actuator/metrics` | `OM_AP_STATUS` |
| BAT-BATCH-002 | DB 상태 | Actuator db health, Hikari, JDBC ping | `OM_DB_STATUS` |
| BAT-BATCH-003 | 세션 | Spring Session 집계, Tomcat session metric | `OM_SESSION_STATUS` |
| BAT-BATCH-004 | 배포 | Actuator health, `process.start.time` | `OM_DEPLOY_STATUS` |

프로파일별 수집 대상:

- `application-bootrun.yml` — 개별 bootRun 포트 (8097, 8086 …)
- `application-tomcat.yml` — `nsight.gateway.base-url` 기준 19 context

수동 실행:

```bash
POST /batch/jobs/ap-status/run
POST /batch/jobs/db-status/run
POST /batch/jobs/session-status/run
POST /batch/jobs/deploy-status/run
```

### 8.4 환경설정 화면 (System Config)

`OM.SystemConfig.inquiry`는 DB 시드(`OM_SYSTEM_CONFIG`)와 **런타임 Environment**를 병합한다.

| 배포 모드 | 표시 카테고리 예 |
|-----------|------------------|
| Tomcat | `deployment`, `gateway`, `application`, `hikari` |
| bootRun | `deployment`, `bootrun`, `application`, `hikari` |

구현: `OmSystemConfigRuntimeSupport` — `tomcat` 프로파일 감지 후 모드별 행 생성·필터.

---

## 9. 보안·세션 아키텍처

### 9.1 OM Admin 인증

| 항목 | 구현 |
|------|------|
| 인증 | `OM.Auth.login` Handler |
| 세션 저장 | Spring Session JDBC (`SPRING_SESSION`) |
| 세션 검증 | `SessionValidator` (TCF STF 단계, OM 거래) |
| 권한 | `AuthorizationValidator`, `OM_USER`·역할·메뉴 |

### 9.2 세션 모니터링 정책 (Tomcat)

| Scope | 수집 방식 | 비고 |
|-------|-----------|------|
| `OM-PORTAL` | Spring Session JDBC 집계 | 운영 포털 로그인 세션 (대표) |
| `{CODE}-AP` | `tomcat.sessions.active.current` | 업무별 HTTP Session |
| `OM-AP` | — | **수집 제외** (Spring Session과 중복) |

---

## 10. 관측·로깅 아키텍처

### 10.1 Actuator

업무·플랫폼 WAR는 최소 `health`, `info`, `metrics`를 노출한다. tcf-batch 수집과 ztomcat `verify-deploy`가 이를 전제한다.

```text
GET /{context}/actuator/health
GET /{context}/actuator/metrics/process.cpu.usage
GET /{context}/actuator/metrics/process.start.time
```

### 10.2 거래 로그

| 계층 | 담당 |
|------|------|
| STF/ETF | `TransactionLogService` — 시작·종료 이벤트 |
| OM 화면 | `OM.TransactionLog.inquiry` — 거래로그 조회 |
| 파일 | `ztomcat/logs/`, `logback-spring.xml` (업무별·공통) |

### 10.3 대시보드 KPI

OM 대시보드(`OM.Dashboard.inquiry`)는 tcf-batch가 적재한 스냅샷을 조회한다.

- AP 상태: CPU/Heap/Thread, NORMAL/WARN/FAIL
- DB 상태: Pool 사용률
- 세션: 활성/만료/사용자 수
- 배포: WAR 버전, 기동 시각, UP/DOWN

---

## 11. 캐시 아키텍처

`tcf-cache` 모듈은 EhCache 3(JCache) + Spring Cache를 제공한다.

| Cache Region | TTL | 주 사용처 |
|--------------|-----|-----------|
| `commonCode` | 30분 | tcf-om 공통코드 |
| `serviceCatalog` | 60분 | ServiceId 카탈로그 |
| `sessionRegion` | 10분 | 리전 캐시 |

OM Cache 관리: `OM.Cache.inquiry` / `OM.Cache.delete`

---

## 12. 물리 배포 참고 (운영 연계)

로컬 `ztomcat`은 단일 Tomcat 인스턴스에 19 WAR를 올리는 **개발·통합 테스트** 모델이다.

운영 환경에서는 일반적으로:

- Apache HTTP Server / Reverse Proxy — 업무별 context 라우팅
- JVM/Tomcat 클러스터 — WAR 단위 스케일아웃
- 공유 DB — 거래로그·OM 메타 (운영 DBMS)

참고: [deploy/apache/nsight-marketing-routing.conf](../../deploy/apache/nsight-marketing-routing.conf)

---

## 13. 업무 코드·포트 매핑 (bootRun)

| 코드 | 모듈 | 포트 | Context |
|------|------|------|---------|
| CC | cc-service | 8081 | /cc |
| IC | ic-service | 8082 | /ic |
| PC | pc-service | 8083 | /pc |
| BC | bc-service | 8084 | /bc |
| MS | ms-service | 8085 | /ms |
| SV | sv-service | 8086 | /sv |
| PD | pd-service | 8087 | /pd |
| CM | cm-service | 8088 | /cm |
| EB | eb-service | 8089 | /eb |
| EP | ep-service | 8090 | /ep |
| BP | bp-service | 8091 | /bp |
| BD | bd-service | 8092 | /bd |
| SS | ss-service | 8093 | /ss |
| CS | cs-service | 8094 | /cs |
| CT | ct-service | 8095 | /ct |
| MG | mg-service | 8096 | /mg |
| OM | tcf-om | 8097 | /om |
| BATCH | tcf-batch | 8098 | /batch |
| UI | tcf-ui | 8099 | /ui |

---

## 14. 아키텍처 결정 사항 (ADR 요약)

| ID | 결정 | 근거 |
|----|------|------|
| ADR-01 | `common-core/web` → `tcf-core/web` 명칭 변경 | TCF 책임 명확화 |
| ADR-02 | Handler + serviceId 디스패치 | Controller 난립 방지, 표준 파이프라인 강제 |
| ADR-03 | HTTP 200 + resultCode | 레거시 채널·게이트웨이 호환 |
| ADR-04 | tcf-om으로 OM·UD 통합 | om-service 레거시 축소 |
| ADR-05 | tcf-batch 분리 | 수집 부하·스케줄 OM 본체와 분리 |
| ADR-06 | tcf-ui Relay | CORS·브라우저에서 다중 업무 테스트 |
| ADR-07 | ztomcat 19 WAR | 운영 context path 통합 검증 |
| ADR-08 | 공유 H2 `nsight.txlog.path` | bootRun/Tomcat/Batch DB 일치 |

---

## 15. 변경 이력

| 일자 | 버전 | 변경 내용 |
|------|------|-----------|
| 2026-06 | 1.0 | 최초 작성 — 19 WAR ztomcat, tcf-batch 대시보드, 이중 배포 모드 반영 |
