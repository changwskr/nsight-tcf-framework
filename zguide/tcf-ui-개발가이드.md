# tcf-ui 개발자 가이드

> **역할:** 온라인 거래 테스트 UI + **OM 운영관리 포털** Relay  
> **포트:** **8099** · Context **`/ui`** (ztomcat)

---

## 1. 이 모듈이 하는 일

WebTopSuite 없이 브라우저에서 TCF JSON 전문을 작성·전송·응답 확인.

```
브라우저 → tcf-ui (/api/relay, /api/updownload) → 업무 WAS / tcf-om (직접)
```

> Gateway **미경유**. 업무 WAS에 **직접** Relay합니다.  
> Gateway 경유 UI는 [tcf-uj-개발가이드.md](./tcf-uj-개발가이드.md).

---

## 2. 5분 빠른 시작

```bash
gradle :sv-service:bootRun   # 8086
gradle :tcf-om:bootRun       # 8097
gradle :tcf-ui:bootRun       # 8099

# 거래 테스트
http://localhost:8099/sv/index.html

# OM Admin (admin01 / nsight01!)
http://localhost:8099/om/admin/login.html
```

---

## 3. 실행

| | |
|---|---|
| bootRun | `gradle :tcf-ui:bootRun` |
| 스크립트 | `tcf-scripts/run-local.bat ui` |
| ztomcat | `http://localhost:8080/ui/` |

**메인:** `com.nh.nsight.tcf.ui.NsightTcfUiApplication`

---

## 4. 주요 API

| API | 설명 |
|-----|------|
| `GET /api/business-modules` | 업무 목록 |
| `POST /api/relay/{code}/online` | 온라인 Relay (**직접** WAS) |
| `POST /api/updownload/upload` | 파일 업로드 (tcf-om) |
| `GET /api/config` | 배포 모드 |

---

## 5. 배포 모드

| 모드 | 설정 | OM Admin URL |
|------|------|--------------|
| bootrun | `deployment-mode: bootrun` | `:8099/om/admin/...` |
| tomcat | `deployment-mode: tomcat` | `:8080/ui/om/admin/...` |

공통 JS: `static/_shared/ui-context.js`, `om-admin.js`

---

## 6. 화면

| URL | 설명 |
|-----|------|
| `/index.html` | 업무 허브 |
| `/{code}/index.html` | 단일 거래 |
| `/sv/sample-list.html` | SV 페이징 샘플 |
| `/om/admin/*` | OM 운영 포털 전체 |
| `/ud/updownload.html` | UD 파일 |

---

## 7. 샘플 JSON

`tcf-ui/src/main/resources/sample-requests/` — 업무별 inquiry JSON

```bash
tcf-scripts/curl-sample.bat sv
```

---

## 8. 패키지 구조

```
com.nh.nsight.tcf.ui
├── client/              TransactionRelayService, UpdownloadRelayService
├── application/service/ BusinessModuleCatalog
├── entry/web/           TcfApiController, UpdownloadApiController
└── support/             BusinessModuleDefinitions
```

---

## 9. tcf-uj와 비교

| | tcf-ui | tcf-uj |
|---|--------|--------|
| 포트 | 8099 | 8102 |
| Relay | WAS **직접** | **Gateway 경유** |
| JWT Admin | ❌ | ✅ |

---

## 10. 참고

| | |
|---|---|
| [tcf-ui/README.md](../tcf-ui/README.md) | |
| [tcf-uj-개발가이드.md](./tcf-uj-개발가이드.md) | Gateway UI |
| [tcf-scripts-개발가이드.md](./tcf-scripts-개발가이드.md) | 로컬 스크립트 |
