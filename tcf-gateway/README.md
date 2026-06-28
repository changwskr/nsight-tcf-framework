# tcf-gateway — 업무 라우팅 API Gateway

TCF 온라인 전문(`POST /{업무코드}/online`)을 단일 진입점에서 수신하고, **세션(JSESSIONID) 확인** 후 downstream 업무 WAS로 프록시합니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-gateway` |
| 메인 클래스 | `com.nh.nsight.gateway.NsightGatewayApplication` |
| bootRun 포트 | **8100** |
| WAR | `gw.war` → ztomcat `/gw` |

## 역할

`tcf-uj`(또는 기타 클라이언트)가 업무별 WAS에 직접 붙지 않고 gateway를 경유하도록 합니다.

```text
브라우저
  → tcf-uj (:8102)  POST /api/relay/{code}/online  (Cookie: JSESSIONID)
  → tcf-gateway (:8100 또는 /gw)  POST /{code}/online
  → downstream WAS  POST /{code}/online  (예: sv-service :8086/sv/online)
```

> **인증 방식:** JWT 검증은 gateway에서 수행하지 않습니다. `GSF` 단계에서 `JSESSIONID` 쿠키 존재 여부로 로그인 상태를 확인합니다. (`nsight.gateway.auth.login-required`, 기본 `true`)

## 처리 흐름 (TCF 패턴)

```text
XxxProxyController
  → BusinessRouteService (파사드)
    → GRF.forwardOnline
      → GSF.preProcess
          - TCF_GATEWAY_ROUTE 조회 (ENV_CODE + BUSINESS_CODE) — **유일한 라우팅 기준**
          - 미등록 시 404 (GatewayRouteNotFoundException)
          - GatewaySessionValidator (JSESSIONID 확인)
          - GatewayRequestEnricher (JWT Principal 있을 때 header 보강)
      → GatewayRouteDispatcher.dispatch  (RestClient POST + Cookie 전달)
      → GEF.success / authFail / httpError / connectionError
```

업무별 개별 RouteService는 두지 않습니다. **`BusinessRouteService` 하나**가 `TCF_GATEWAY_ROUTE` 기준으로 모든 업무를 라우팅합니다.

## 패키지 구조

| 패키지 | 주요 클래스 | 설명 |
|--------|-------------|------|
| `web` | `*ProxyController`, `AbstractBusinessProxyController` | 업무별 `POST /{code}/online` 엔드포인트 |
| `service` | `BusinessRouteService`, `RouteResult` | GRF 진입 파사드 |
| `processor` | `GRF`, `GSF`, `GEF`, `GatewayRouteDispatcher` | 라우팅·전처리·응답 조립 |
| `catalog` | `GatewayBusinessModules`, `GatewayRouteCatalog` | 업무코드 카탈로그·관리화면 코드성 데이터 |
| `security` | `GatewaySessionValidator`, `GatewayAuthException` | JSESSIONID 로그인 확인 |
| `config` | `GatewayProperties`, `GatewaySecurityConfiguration` | gateway 설정·Spring Security |
| `route` | `GatewayRouteDao`, `GatewayRouteResolver`, `GatewayRouteAdminService` | TCF_GATEWAY_ROUTE 조회·캐시·관리 |
| `route/web` | `GatewayRouteAdminController` | 라우팅 테이블 REST API |

## 등록 업무 (GatewayBusinessModules)

| 업무코드 | bootRun 포트 | online 경로 | downstream |
|----------|-------------|-------------|------------|
| CC | 8081 | `/cc/online` | cc-service |
| BC | 8084 | `/bc/online` | bc-service |
| EB | 8089 | `/eb/online` | eb-service |
| EP | 8090 | `/ep/online` | ep-service |
| IC | 8082 | `/ic/online` | ic-service |
| MG | 8096 | `/mg/online` | mg-service |
| MS | 8085 | `/ms/online` | ms-service |
| OM | 8097 | `/om/online` | tcf-om |
| PC | 8083 | `/pc/online` | pc-service |
| PD | 8087 | `/pd/online` | pd-service |
| SS | 8093 | `/ss/online` | ss-service |
| SV | 8086 | `/sv/online` | sv-service |
| JWT | 8100 | `/online` | tcf-jwt |

| `support` | `GatewayRequestEnricher`, `GatewayProxyTrace` | 전문 보강·STF 스타일 trace 로그 |

## 라우팅 테이블 (TCF_GATEWAY_ROUTE)

환경(`ENV_CODE`: LOCAL / DEV / PRD)과 업무코드로 Target URL을 DB에서 조회합니다.

```text
TARGET_URL = TARGET_BASE_URL + CONTEXT_PATH + ONLINE_PATH
```

| 실행 모드 | 업무코드 | 예시 Target URL |
|-----------|----------|-----------------|
| LOCAL | SV | `http://127.0.0.1:8086/sv/online` |
| DEV | SV | `http://dev-msa-b-service:9090/sv/online` |
| PRD | SV | `http://msa-b-service:9090/sv/online` |

- 설계 문서: [docs/ROUTING_TABLE.md](docs/ROUTING_TABLE.md)
- Oracle DDL: [sql/oracle/TCF_GATEWAY_ROUTE.sql](sql/oracle/TCF_GATEWAY_ROUTE.sql)
- Oracle 시드: [sql/oracle/TCF_GATEWAY_ROUTE_DATA.sql](sql/oracle/TCF_GATEWAY_ROUTE_DATA.sql)
- 관리 화면: `http://localhost:8100/admin/routes.html`
- REST API: `GET/POST/PUT/DELETE /api/admin/routes`

## 엔드포인트

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `POST` | `/cc/online`, `/bc/online` | CC·BC 온라인 거래 프록시 |
| `POST` | `/{code}/online` | 업무별 온라인 거래 프록시 (`code`: om, sv, cc, bc, jwt …) |
| `GET` | `/actuator/health` | 헬스체크 (인증 없음) |
| `GET` | `/admin/routes.html` | 라우팅 테이블 관리 화면 |
| `GET/POST/PUT/DELETE` | `/api/admin/routes` | 라우팅 테이블 REST API |

### Query 파라미터 (하위 호환)

`tcf-uj` relay가 gateway 호출 시 `deploymentMode`, `bootrunHost`, `tomcatGatewayUrl`을 전달할 수 있으나,
**라우팅은 TCF_GATEWAY_ROUTE만 사용**하며 query 파라미터는 Target URL 결정에 사용하지 않습니다.

## tcf-uj 연동

`tcf-uj`의 `TransactionRelayService`는 **모든** `/api/relay/{code}/online` 요청을 gateway로 위임합니다.

```text
tcf-uj GatewayRelayService
  → bootRun: http://127.0.0.1:8100/{code}/online
  → Tomcat:  http://localhost:8080/gw/{code}/online
```

OM Admin·온라인 테스트·JWT Admin 화면 모두 동일 relay 경로를 사용합니다.

> **미경유:** `/api/updownload/*`는 gateway를 거치지 않고 tcf-om(8097)에 직접 연결합니다.

## 사전 조건 (로컬 bootRun)

1. 대상 업무 WAS 기동 (예: `gradle :sv-service:bootRun`)
2. OM Admin·세션 사용 시 **tcf-om** 기동 (8097) + OM 로그인
3. **tcf-gateway** 기동 (8100)
4. **tcf-uj** 기동 (8102)

```bash
# 예: SV 온라인 테스트
gradle :sv-service:bootRun    # 8086
gradle :tcf-om:bootRun        # 8097 (세션 발급용)
gradle :tcf-gateway:bootRun   # 8100
gradle :tcf-uj:bootRun        # 8102
```

## 실행

```bash
# Gradle
gradle :tcf-gateway:bootRun

# 스크립트 (모듈 scripts/)
tcf-gateway\scripts\build.bat
tcf-gateway\scripts\build.bat run
tcf-gateway\scripts\run-local.bat
tcf-gateway\scripts\deploy.bat
```

## Tomcat 배포

```bash
tcf-gateway\scripts\deploy.bat
# 또는 ztomcat/deploy-wars.bat gw

# 호출 예
POST http://localhost:8080/gw/sv/online
Cookie: JSESSIONID=...
Content-Type: application/json
```

## 설정

`application.yml`

```yaml
nsight:
  gateway:
    env-code: LOCAL          # LOCAL | DEV | PRD
    route-table:
      cache-enabled: false   # dev/prod: true
      cache-ttl-seconds: 30
    auth:
      login-required: true   # false 시 JSESSIONID 확인 생략
```

`application-local.yml` — bootRun 포트

```yaml
server:
  port: 8100
```

## 직접 호출 예 (curl)

```bash
curl -X POST http://localhost:8100/sv/online \
  -H "Content-Type: application/json" \
  -H "Cookie: JSESSIONID=<세션값>" \
  -d @sv-service/src/main/resources/sample-requests/sv-sample-inquiry.json
```

## 관련 모듈

| 모듈 | README |
|------|--------|
| tcf-uj (Relay UI) | [tcf-uj/README.md](../tcf-uj/README.md) |
| tcf-jwt (JWT 발급) | [tcf-jwt/README.md](../tcf-jwt/README.md) |
| TCF 처리 순서 | [docs/TCF_FRAMEWORK_GUIDE.md](../docs/TCF_FRAMEWORK_GUIDE.md) |
