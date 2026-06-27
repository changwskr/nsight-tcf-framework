# tcf-gateway — 업무 라우팅 API Gateway

JWT Access Token 검증(선택) 후 `tcf-om`으로 TCF 온라인 전문을 프록시합니다.  
**기본(`jwt.enabled: false`)**: JWT 없이 OM `JSESSIONID` 쿠키만 전달·라우팅합니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-gateway` |
| bootRun 포트 | **8101** |
| WAR | `gw.war` → ztomcat `/gw` |

## 흐름

```text
브라우저 → tcf-uj (/api/gateway/om/online, 쿠키 전달)
        → tcf-gateway (라우팅)
        → tcf-om (/om/online)
```

## 라우팅 구조

업무별 개별 RouteService는 두지 않습니다. **`BusinessRouteService` 하나**가 `GatewayBusinessModules` 카탈로그(포트·경로)를 보고 모든 업무를 라우팅합니다.

```text
XxxProxyController  →  AbstractBusinessProxyController  →  BusinessRouteService  →  downstream
     /om/online              (공통 proxyOnline)              forwardOnline("OM", ...)
     /sv/online                                                forwardOnline("SV", ...)
```

| 업무 | bootRun 대상 |
|------|-------------|
| OM | `8097/om/online` |
| IC | `8082/ic/online` |
| SV | `8086/sv/online` |
| … | `GatewayBusinessModules` 참조 |

## 엔드포인트

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `POST` | `/om/online` | OM 온라인 거래 프록시 (기본: 쿠키 전달만, JWT 불필요) |
| `POST` | `/eb/online` | EB 업무 프록시 → `eb-service` (8089) |
| `POST` | `/ep/online` | EP 업무 프록시 → `ep-service` (8090) |
| `POST` | `/ic/online` | IC 업무 프록시 → `ic-service` (8082) |
| `POST` | `/mg/online` | MG 업무 프록시 → `mg-service` (8096) |
| `POST` | `/ms/online` | MS 업무 프록시 → `ms-service` (8085) |
| `POST` | `/pc/online` | PC 업무 프록시 → `pc-service` (8083) |
| `POST` | `/pd/online` | PD 업무 프록시 → `pd-service` (8087) |
| `POST` | `/ss/online` | SS 업무 프록시 → `ss-service` (8093) |
| `POST` | `/sv/online` | SV 업무 프록시 → `sv-service` (8086) |
| `GET` | `/actuator/health` | 헬스체크 (인증 없음) |

## 사전 조건

1. `tcf-om` 기동 (8097 또는 Tomcat `/om`)
2. (JWT 모드 시만) `tcf-jwt` 기동 + `nsight.gateway.jwt.enabled: true`

## 실행

```bash
gradle :tcf-gateway:bootRun
tcf-gateway/scripts/run-local.bat
```

## Tomcat

```bash
tcf-gateway/scripts/deploy.bat
# POST http://localhost:8080/gw/om/online
# (JWT 모드 시) Authorization: Bearer <accessToken>
```

## 설정

`application-local.yml`

```yaml
nsight.gateway.jwt.jwk-set-uri: http://127.0.0.1:8100/.well-known/jwks.json
```
