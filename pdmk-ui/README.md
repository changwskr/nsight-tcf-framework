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

브라우저: http://localhost:8090

## 전문 형식

요청/응답 모두 `hdr_nhnis` + `dto` 구조입니다.

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "std_gbl_id": "c3d65cb1a54a43838688b76afe82521e",
      "tr_trm_ipadr": "127.0.0.1",
      "tr_brc": "10001",
      "optr_eno": "E0000001",
      "ttl_ug_ync": 0
    }
  },
  "dto": {
    "basDt": "20260801",
    "pageNo": 1,
    "pageSize": 20
  }
}
```

응답 예: `dto.records` / `dto.totalCount` 와 함께 `hdr_nhnis.sys_comm.std_gbl_id` 가 돌아옵니다.

## 등록 거래

| 프로그램 | API |
|---|---|
| `mkpca5530` | `POST /api/mk/co/a/5530/list` |
| `mkpca9999` | `POST /api/mk/co/a/9999/list`, `/detail` |
| `mkpca8888` | `POST /api/mk/co/a/8888/list`, `/detail` |
