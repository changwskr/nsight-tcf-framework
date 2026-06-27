# tcf-uj — NSIGHT 온라인 거래 테스트 UI (UJ)

`tcf-ui`를 복제한 UJ 전용 UI 모듈입니다. WebTopSuite/Client 없이 브라우저에서 표준 HTTP/JSON 전문을 작성·전송·응답 확인하고, **OM 운영관리 포털**도 제공합니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-uj` |
| 메인 클래스 | `com.nh.nsight.tcf.uj.NsightTcfUjApplication` |
| bootRun 산출물 | `tcf-uj.jar` (포트 **8102**) |
| WAR (bootWar) | `tcf-uj.war` → ztomcat `uj.war` (`/uj`) |

`tcf-uj`는 **Relay 서버** 역할을 합니다.

```text
브라우저 → tcf-uj (/api/relay, /api/gateway, /api/updownload) → 업무 WAS / tcf-gateway / tcf-om
```

## 배포 모드

| 모드 | 설정 | OM Admin URL |
|------|------|--------------|
| **local (bootRun)** | `deployment-mode: bootrun` | http://localhost:8102/om/admin/login.html |
| **dev/prod (Tomcat WAR)** | `deployment-mode: tomcat` (WAR `/uj`) | http://localhost:8080/uj/om/admin/login.html |

## 실행

```bash
gradle :tcf-uj:bootRun
tcf-uj/scripts/run-local.bat
tcf-scripts/run-local.bat uj

# ztomcat
ztomcat/deploy-wars.bat uj
tcf-uj/scripts/deploy.bat   # JAR 로컬 deploy 디렉터리
```

## 스크립트

| 스크립트 | 설명 |
|----------|------|
| `scripts/build.bat` | `tcf-uj.jar` 빌드 / `run` → bootRun |
| `scripts/run-local.bat` | bootRun (8102) |
| `scripts/deploy.bat` | JAR → `tcf-uj/deploy/` |

## tcf-ui와의 차이

| 항목 | tcf-ui | tcf-uj |
|------|--------|--------|
| 포트 | 8099 | 8102 |
| Tomcat context | `/ui` | `/uj` |
| WAR | `ui.war` | `uj.war` |
| 설정 prefix | `nsight.tcf-ui` | `nsight.tcf-uj` |

기능·화면 구성은 `tcf-ui`와 동일합니다.

## OM + Gateway (JWT 미사용)

`tcf-uj`는 OM 운영관리 API를 **tcf-gateway** 경유로 호출합니다. 인증은 **OM 로그인 세션**(JSESSIONID 쿠키)만 사용합니다.

```text
브라우저 → tcf-uj (:8102)
  → OM 로그인 (OM.Auth.login)
  → POST /api/gateway/om/online  (쿠키 전달)
  → tcf-gateway (:8101 또는 /gw)
  → tcf-om (:8097 또는 /om/online)
```

### 로컬 실행 순서

1. **tcf-om** — `gradle :tcf-om:bootRun` (8097)
2. **tcf-gateway** — `gradle :tcf-gateway:bootRun` (8101)
3. **tcf-uj** — `gradle :tcf-uj:bootRun` (8102)
4. OM 로그인: `http://localhost:8102/om/admin/login.html` (`admin01` / `nsight01!`)

> `tcf-jwt`는 필요하지 않습니다. `nsight.tcf-uj.om-gateway-enabled: true`(기본)일 때 모든 OM API가 gateway를 경유합니다.
