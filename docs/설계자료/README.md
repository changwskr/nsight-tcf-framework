# NSIGHT TCF — 설계자료

본 디렉터리는 NSIGHT TCF Framework의 **공식 설계안(Word)** 을 보관합니다.  
구현·운영 시에는 아래 설계안과 `docs/architecture/` Markdown 문서를 함께 참고하세요.

## 설계안 목록

| 설계안 | 주제 | 구현 모듈 | 아키텍처 문서 |
|--------|------|-----------|---------------|
| [NSIGHT TCF Framework 설계안](NSIGHT%20TCF%20Framework%20설계안.docx) | 프레임워크 전체·모듈·TCF 흐름 | `tcf-core`, `tcf-web`, `*-service` | [architecture.md](../architecture/architecture.md), [33-TCF.md](../architecture/33-TCF.md) |
| [NSIGHT 거래통제 설계안](NSIGHT%20거래통제%20설계안.docx) | Header 7필드 Allow-List | `tcf-core`, `tcf-web`, `tcf-om` | [40-header-7-transaction-control.md](../architecture/40-header-7-transaction-control.md) |
| [서비스별 Timeout 설계안](서비스별%20Timeout%20설계안.docx) | Timeout 종류·기본값·처리 기준 | `tcf-core`, `tcf-web` | [41-service-timeout-policy.md](../architecture/41-service-timeout-policy.md) |
| [NSIGHT TCF Framework 타임아웃 관리 설계안](NSIGHT%20TCF%20Framework%20타임아웃%20관리%20설계안.docx) | Timeout 적용 흐름·OM 화면 | `tcf-core`, `tcf-web`, `tcf-om`, `tcf-ui` | [41-service-timeout-policy.md](../architecture/41-service-timeout-policy.md), [08-timeout.md](../architecture/08-timeout.md) |
| [NSIGHT 거래로그 관리 설계안](NSIGHT%20거래로그%20관리%20설계안.docx) | PROCESSING→SUCCESS/FAIL, LOGDB | `tcf-core`, `tcf-web` | [37-transaction-log.md](../architecture/37-transaction-log.md) |
| [NSIGHT 공통전문조립 설계안](NSIGHT%20공통전문조립%20설계안.docx) | StandardResponse 조립·마스킹 | `tcf-core` (ETF) | [04-messaging.md](../architecture/04-messaging.md), [36-ETF.md](../architecture/36-ETF.md) |
| [NSIGHT 오류코드·메시지 설계안](NSIGHT%20오류코드·메시지%20설계안.docx) | `E-{DOMAIN}-{CATEGORY}-{NNNN}` | `tcf-core`, `tcf-om` | [05-exception.md](../architecture/05-exception.md) |
| [NSIGHT TCF Framework 세션관리 설계안](NSIGHT%20TCF%20Framework%20세션관리%20설계안.docx) | Spring Session JDBC · SESSIONDB | `tcf-core`, `tcf-web`, `tcf-om` | [10-session.md](../architecture/10-session.md), [11-login.md](../architecture/11-login.md) |
| [NSIGHT TCF Framework Cache 관리 설계안](NSIGHT%20TCF%20Framework%20Cache%20관리%20설계안.docx) | EhCache·Spring Cache·OM Cache | `tcf-cache`, `tcf-om`, `tcf-ui` | [12-cache.md](../architecture/12-cache.md) |
| [NSIGHT 서비스 ID 관리 설계안](NSIGHT%20서비스%20ID%20관리%20설계안.docx) | ServiceId·Handler Registry | `tcf-core`, `tcf-om` | [03-transaction.md](../architecture/03-transaction.md) |
| [NSIGHT 사용자 정보관리 설계안](NSIGHT%20사용자%20정보관리%20설계안.docx) | 사용자·권한그룹·세션 | `tcf-om`, `tcf-ui` | [11-login.md](../architecture/11-login.md) |
| [NSIGHT 기능권한 설계안](NSIGHT%20기능권한%20설계안.docx) | CRUD·다운로드 버튼 권한 | `tcf-om`, `tcf-ui` | — |
| [NSIGHT 메뉴관리 설계안](NSIGHT%20메뉴관리%20설계안.docx) | 메뉴 트리·화면 내비게이션 | `tcf-om`, `tcf-ui` | — |
| [NSIGHT 공통코드 관리 설계안](NSIGHT%20공통코드%20관리%20설계안.docx) | 공통코드·채널·캐시 | `tcf-cache`, `tcf-om` | — |
| [NSIGHT TCF Framework 파일관리 설계안](NSIGHT%20TCF%20Framework%20파일관리%20설계안.docx) | UD 업·다운로드 | `tcf-om` (`/ud/files`) | [18-fileupdownload.md](../architecture/18-fileupdownload.md) |
| [NSIGHT 환경설정 조회 설계안](NSIGHT%20환경설정%20조회%20설계안.docx) | Runtime·Profile 조회(읽기 전용) | `tcf-om` | [25-env-profile.md](../architecture/25-env-profile.md) |
| [NSIGHT TCF Framework 운영 대시보드 설계안](NSIGHT%20TCF%20Framework%20운영%20대시보드%20설계안-대쉬보드.docx) | AP/DB/세션/배포·TPS·Timeout | `tcf-om`, `tcf-batch`, `tcf-ui` | [13-batch.md](../architecture/13-batch.md) |
| [NSIGHT TCF Framework 헬스체크 설계안](NSIGHT%20TCF%20Framework%20헬스체크%20설계안.docx) | Liveness/Readiness/Deep/Smoke | `tcf-web`, `tcf-batch` | — |
| [NSIGHT TCF Framework 배포관리 설계안](NSIGHT%20TCF%20Framework%20배포관리%20설계안.docx) | CI/CD·WAR·Rolling·Rollback | `tcf-cicd`, `ztomcat` | [16-deploy.md](../architecture/16-deploy.md) |
| [NSIGHT 배치·스케줄러 설계안](NSIGHT%20배치·스케줄러%20설계안.docx) | Scheduler·Job·Execution | `tcf-batch`, `tcf-om` | [13-batch.md](../architecture/13-batch.md), [15-schedule.md](../architecture/15-schedule.md) |

## 설계안 ↔ OM 관리 화면

| OM 화면 (ztomcat) | serviceId (예) | 설계안 |
|-------------------|----------------|--------|
| `/ui/om/admin/dashboard.html` | `OM.Dashboard.inquiry` | 운영 대시보드 |
| `/ui/om/admin/transaction-log.html` | `OM.TransactionLog.inquiry` | 거래로그 관리 |
| `/ui/om/admin/service-catalog.html` | `OM.ServiceCatalog.*` | 서비스 ID 관리 |
| `/ui/om/admin/transaction-control.html` | `OM.TransactionControl.*` | 거래통제 |
| `/ui/om/admin/timeout-policy.html` | `OM.TimeoutPolicy.*` | Timeout / 타임아웃 관리 |
| `/ui/om/admin/user-auth.html` | `OM.User.*`, `OM.AuthGroup.*`, … | 사용자 정보·기능·메뉴·데이터권한 |
| `/ui/om/admin/common-code.html` | `OM.CommonCode.*` | 공통코드 관리 |
| `/ui/om/admin/error-code.html` | `OM.ErrorCode.*` | 오류코드·메시지 |
| `/ui/om/admin/auth-history.html` | `OM.AuthHistory.inquiry` | 권한이력 |
| `/ui/om/admin/cache.html` | `OM.Cache.*` | Cache 관리 |
| `/ui/om/admin/session.html` | `OM.Session.*` | 세션관리 |
| `/ui/om/admin/batch.html` | `OM.Batch.*` | 배치·스케줄러 |
| `/ui/om/admin/deploy.html` | `OM.Deploy.*` | 배포관리 |
| `/ui/om/admin/file-management.html` | `/ud/files` (REST) | 파일관리 |
| `/ui/om/admin/system-config.html` | `OM.SystemConfig.inquiry` | 환경설정 조회 |

## 핵심 테이블 (설계 공통)

| 테이블 | 역할 |
|--------|------|
| `TCF_TRANSACTION_CONTROL` | Header 7필드 거래 차단 (`BLOCK_YN=Y`) |
| `TCF_SERVICE_TIMEOUT_POLICY` | 서비스별 Online/TX/DB Query Timeout |
| `TCF_TRANSACTION_LOG` | 거래 상태 (PROCESSING → SUCCESS/FAIL/TIMEOUT/UNKNOWN) |
| `OM_SERVICE_CATALOG` | ServiceId 마스터·Handler·감사·Timeout 기본값 |
| `OM_FUNCTION_AUTH` | 권한그룹별 메뉴 CRUD·다운로드 |
| `SPRING_SESSION` / `SPRING_SESSION_ATTRIBUTES` | Spring Session JDBC (공유 SESSIONDB) |

## 읽는 순서 (권장)

1. **NSIGHT TCF Framework 설계안** — 전체 구조·모듈·처리 흐름
2. **거래통제** + **서비스별 Timeout** — STF 전처리 정책
3. **거래로그** + **공통전문조립** + **오류코드·메시지** — ETF 후처리
4. **사용자·기능·메뉴·공통코드** — OM 마스터
5. **운영 대시보드** + **헬스체크** + **배포·배치** — 운영·인프라

Markdown 구현 문서는 [docs/architecture/architecture.md](../architecture/architecture.md)에서 전체 목차를 확인할 수 있습니다.
