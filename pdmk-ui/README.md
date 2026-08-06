# PDMK UI (`pdmk-ui`)

`pdmk-service` 전문 테스트용 로컬 UI입니다. (`pdmp-ui`를 PDMK 환경에 맞게 재구성)

## 실행

```powershell
# 1) pdmk-service (8080)
cd ..\pdmk-service
.\RUN.bat

# 2) pdmk-ui (8090) — pdmk-service를 HTTP로 중계
cd ..\pdmk-ui
.\RUN.bat
```

Cursor Run/Debug: F5 → **PdmkUiApplication** (초록 CodeLens/`jdt.ls-java-project` 사용 금지).  
클래스패스 갱신: `.\gradlew.bat writeIdeLaunch`

브라우저: http://localhost:8090

## 요청 형식

pdmk-fw `RequestBody` resolver 기준:

```json
{ "dto": { "basDt": "20260801", "pageNo": 1, "pageSize": 20 } }
```

## 등록 거래

| 프로그램 | API |
|---|---|
| `mkpca5530` | `POST /api/mk/co/a/5530/list` |
| `mkpca9999` | `POST /api/mk/co/a/9999/list`, `/detail` |
| `mkpca8888` | `POST /api/mk/co/a/8888/list`, `/detail` |
