# tcf-uj — NSIGHT 온라인 거래 테스트 UI (UJ)

WebTopSuite/Client 없이 브라우저에서 TCF 표준 HTTP/JSON 전문을 작성·전송·응답 확인하는 **테스트 UI**이며, **OM 운영관리 포털**·**JWT Admin**·**파일 업·다운로드** 화면도 제공합니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-uj` |
| 메인 클래스 | `com.nh.nsight.tcf.uj.NsightTcfUjApplication` |
| bootRun | `tcf-uj.jar` (포트 **8102**) |
| WAR (bootWar) | `tcf-uj.war` → ztomcat `uj.war` (`/uj`) |
| 설정 prefix | `nsight.tcf-uj` |

## 역할

`tcf-uj`는 **Relay 서버 + 정적 UI**입니다. 브라우저는 tcf-uj에만 요청하고, 온라인 거래는 **항상 tcf-gateway**를 경유합니다.

```text
브라우저
  → tcf-uj (:8102 또는 /uj)
      POST /api/relay/{code}/online        (Cookie: JSESSIONID — 세션 모드)
      POST /api/gateway/om/online          (Authorization: Bearer — JWT 모드)
  → tcf-gateway (:8100 또는 /gw)
      POST /{code}/online
  → downstream WAS  (예: sv-service :8086/sv/online)
```

> **인증:** JWT **발급**은 `tcf-jwt`(:8110), **검증**은 `tcf-gateway`가 담당합니다(`nsight.gateway.auth.jwt.enabled`). Bearer 없으면 OM 로그인 후 발급된 **JSESSIONID** 쿠키를 relay 시 그대로 전달합니다.

### gateway 미경유 (예외)

파일 업·다운로드(`/api/updownload/*`)는 **tcf-om에 직접** 연결합니다. (gateway에 UD 프록시 없음)

```text
브라우저 → tcf-uj  /api/updownload/*  → tcf-om (:8097 또는 /om)
```

## 화면 구성

| 경로 | 설명 |
|------|------|
| `/` | 업무코드 허브 (모듈 목록) |
| `/{code}/index.html` | 단일 서비스 온라인 테스트 |
| `/{code}/index-multi.html` | 다중 서비스 온라인 테스트 |
| `/om/admin/*` | OM 운영관리 포털 (로그인·대시보드·거래통제 등) |
| `/jwt/admin/*` | JWT 토큰·정책·로그인 이력 관리 |
| `/ud/updownload.html` | 공통 파일 업·다운로드 |

Tomcat `/uj` 배포 시 `uj-context.js`가 `/uj` context 접두사를 API·정적 리소스에 자동 부여합니다.

## Relay API

`TcfApiController` — `@RequestMapping("/api")` (`entry/web`)

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `GET` | `/business-modules` | 등록 업무 목록 |
| `GET` | `/business-modules/{code}` | 업무 상세 |
| `GET` | `/business-modules/{code}/target-url` | gateway relay 대상 URL |
| `POST` | `/relay/{code}/online` | **온라인 거래 relay** (gateway 경유) |
| `GET` | `/multi/business-modules` | 다중 거래 카탈로그 |
| `POST` | `/multi/relay/{code}/online` | 다중 거래 relay |
| `GET` | `/config` | 배포 모드·gateway URL 설정 |
| `POST` | `/gateway/om/online` | OM relay — **JWT Bearer** (`Authorization` 헤더 전달) |

Query 파라미터: `deploymentMode`, `bootrunHost`, `tomcatGatewayUrl` — gateway downstream URL 결정에 사용됩니다.

### Updownload API

`UpdownloadApiController` — `@RequestMapping("/api/updownload")` (`entry/web`)

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `GET` | `/base-url` | tcf-om updownload 베이스 URL |
| `POST` | `/upload` | 파일 업로드 |
| `GET` | `/files` | 파일 목록 |
| `GET` | `/files/{fileId}` | 파일 상세 |
| `GET` | `/files/{fileId}/download` | 파일 다운로드 |
| `PUT` | `/files/{fileId}` | 메타 수정 |
| `DELETE` | `/files/{fileId}` | 파일 삭제 |

## 서비스 레이어

| 클래스 | 패키지 | 설명 |
|--------|--------|------|
| `TransactionRelayService` | `client` | 모든 `/api/relay` 요청을 `GatewayRelayService`에 위임 |
| `GatewayRelayService` | `client` | tcf-gateway URL 조립·HTTP POST·Set-Cookie 전달 |
| `UpdownloadRelayService` | `client` | tcf-om updownload API 직접 relay |
| `BusinessModuleCatalog` | `application/service` | `BusinessModuleDefinitions` 기반 업무 목록 |
| `BusinessTransactionCatalog` | `application/service` | sample-requests JSON 기반 다중 거래 정의 |

### Gateway relay URL

| 배포 모드 | relay 대상 |
|-----------|------------|
| **bootrun** | `http://127.0.0.1:8100/{code}/online` |
| **tomcat** | `http://localhost:8080/gw/{code}/online` |

## 등록 업무 (BusinessModuleDefinitions)

| 코드 | 이름 | 그룹 | bootRun 포트 |
|------|------|------|-------------|
| CC | Common | 공통 | 8081 |
| IC | Integration Customer | 고객 | 8082 |
| PC | Private Customer | 고객 | 8083 |
| BC | Business Customer | 고객 | 8084 |
| MS | Mini Single View | 고객 | 8085 |
| SV | Single View | 마케팅 | 8086 |
| PD | Product | 마케팅 | 8087 |
| CM | Campaign | 마케팅 | 8088 |
| EB | EBM | 마케팅 | 8089 |
| EP | Event Processing | 실시간 | 8090 |
| BP | Behavior Processing | 실시간 | 8091 |
| BD | Behavior Data | 데이터 | 8092 |
| SS | Sales Support | 지원 | 8093 |
| CS | Common Service | 지원 | 8094 |
| CT | Contents | 지원 | 8095 |
| MG | Message | 지원 | 8096 |
| OM | Operation Management | 운영 | 8097 |
| UD | Common UpDownload | 공통 | 8097 |
| JWT | JWT Auth | 인증 | 8110 |

> gateway에 등록된 업무(EB, EP, IC, MG, MS, OM, PC, PD, SS, SV, JWT)만 relay가 성공합니다. CC·BC 등 미등록 코드는 [tcf-gateway](../tcf-gateway/README.md) 카탈로그 추가가 필요합니다.

## 배포 모드

| 모드 | profile / 설정 | UI URL 예 |
|------|------------------|-----------|
| **bootrun** | `application-local.yml`, `deployment-mode: bootrun` | http://localhost:8102/ |
| **tomcat** | `application-dev.yml`, `deployment-mode: tomcat` | http://localhost:8080/uj/ |

| 화면 | bootRun | Tomcat |
|------|---------|--------|
| 홈 | http://localhost:8102/ | http://localhost:8080/uj/ |
| OM Admin 로그인 | http://localhost:8102/om/admin/login.html | http://localhost:8080/uj/om/admin/login.html |
| SV 테스트 | http://localhost:8102/sv/index.html | http://localhost:8080/uj/sv/index.html |

## 로컬 실행 순서

온라인 거래·OM Admin·JWT Admin을 사용할 때:

1. **대상 업무 WAS** — 예: `gradle :sv-service:bootRun` (8086)
2. **tcf-om** — `gradle :tcf-om:bootRun` (8097, 세션·OM Admin)
3. **tcf-gateway** — `gradle :tcf-gateway:bootRun` (8100)
4. **tcf-uj** — `gradle :tcf-uj:bootRun` (8102)
5. OM 로그인: http://localhost:8102/om/admin/login.html (`admin01` / `nsight01!`)

JWT Admin만 테스트할 때는 **tcf-jwt** (8110)도 기동합니다.

OM Admin JWT Gateway 모드(`callViaGateway`) 사용 시 **tcf-gateway**(:8100, `local` JWT enabled)도 필요합니다. SSO 로그인은 쿠키 relay만 사용합니다 (`om-admin.js`).

## 실행

```bash
# Gradle
gradle :tcf-uj:bootRun

# 스크립트 (모듈 scripts/)
tcf-uj\scripts\build.bat
tcf-uj\scripts\build.bat run
tcf-uj\scripts\run-local.bat
tcf-uj\scripts\deploy.bat

# 루트 통합 스크립트
tcf-scripts\run-local.bat uj
```

## Tomcat 배포

```bash
gradle :tcf-uj:bootWar
tcf-uj\scripts\deploy-war.bat
# 또는 ztomcat/deploy-wars.bat uj
```

## 설정

`application-local.yml`

```yaml
server:
  port: 8102

nsight:
  tcf-uj:
    deployment-mode: bootrun
    tomcat-gateway-url: http://localhost:8080
    bootrun-host: http://127.0.0.1
    om-gateway-enabled: true   # OM relay gateway 경유 (기본)
```

## tcf-ui와의 차이

| 항목 | tcf-ui | tcf-uj |
|------|--------|--------|
| 포트 | 8099 | 8102 |
| Tomcat context | `/ui` | `/uj` |
| WAR | `ui.war` | `uj.war` |
| 설정 prefix | `nsight.tcf-ui` | `nsight.tcf-uj` |
| gateway relay | tcf-ui `GatewayRelayService` | 동일 패턴, UJ 전용 모듈 |

화면·API 구조는 `tcf-ui`와 동일하며, UJ는 gateway 경유 relay가 기본 동작입니다.

## 패키지 구조

`tcf-ui`와 동일한 6계층입니다 (`com.nh.nsight.tcf.uj`).

```text
com.nh.nsight.tcf.uj
├── NsightTcfUjApplication
├── application/service/     BusinessModuleCatalog, BusinessTransactionCatalog
├── client/                  TransactionRelayService, GatewayRelayService, UpdownloadRelayService
├── config/                  TcfUjProperties, TcfUjConfiguration
├── entry/web/               TcfApiController, UpdownloadApiController, …
└── support/                 BusinessModuleDefinitions, RelayResult
```

## 관련 모듈

| 모듈 | README |
|------|--------|
| tcf-gateway (API Gateway) | [tcf-gateway/README.md](../tcf-gateway/README.md) |
| tcf-jwt (JWT 발급) | [tcf-jwt/README.md](../tcf-jwt/README.md) |
| TCF 처리 순서 | [docs/TCF_FRAMEWORK_GUIDE.md](../docs/TCF_FRAMEWORK_GUIDE.md) |
