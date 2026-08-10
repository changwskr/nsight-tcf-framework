# PDMG UI (`pdmg-ui`)

`pdmg-service` 전문 테스트 + `tcf-ontology-service` Architecture Design Wizard 통합 셸입니다.
화면 구조·시각 스타일은 `tcf-ontology-service` Workbench(`workbench.css`)를 기준으로 통일했습니다.
- 셸: `_shared/pdmg-workbench.css`
- 전문/관리 화면: `_shared/online.css` (Workbench 라이트 톤)

## 실행

```powershell
# 1) pdmg-service (8080)
cd ..\pdmg-service
.\RUN.bat

# 2) tcf-ontology-service (8098) — Architecture Design
cd ..\tcf-ontology-service
.\RUN.bat

# 3) pdmg-ui (8090)
cd ..\pdmg-ui
.\RUN.bat
```

브라우저: http://localhost:8090

## 라우팅 (Workbench 정렬)

| Hash | 화면 |
|---|---|
| `#/home` | Home · 카드/통계 |
| `#/design` | Architecture Design Wizard (ontology embed) |
| `#/mgcoa5530` … | 전문 테스트 iframe |
| `#/imagelog`, `#/txparam` | 관리 화면 iframe |

레거시 `#view=mgcoa5530` 해시는 `#/mgcoa5530`으로 자동 변환됩니다.

## Architecture Design

- 메뉴 **07 · Architecture Design** → ontology Workbench `#/design?embed=1`
- 설정: `pdmg.ui.ontology-base-url` (기본 `http://localhost:8098`)
- Done 저장 후 Designs 조회: ontology `#/dashboard?view=designs`

## 전문 형식

요청/응답 모두 `hdr_nhnis` + `dto` 구조입니다.

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "std_gbl_id": "c3d65cb1a54a43838688b76afe82521e",
      "rms_svc_c": "mgcoa5530S0",
      "scid": "mgcoa5530",
      "tr_trm_ipadr": "127.0.0.1",
      "tr_brc": "10001",
      "optr_eno": "E0000001",
      "ttl_ug_ync": 0
    }
  },
  "dto": {
    "pageNo": 1,
    "pageSize": 20
  }
}
```

## 등록 거래

| 프로그램 | API |
|---|---|
| **이미지로그 관리** | `/imagelog` (`POST /mgcoa8888S0` 조회 · `POST /mgcoa8888D0` 삭제) |
| `mgcoa5530` | `POST /mgcoa5530S0` (안내항목 목록) |
| `mgcoa9999` | `POST /mgcoa9999S0` (영업팁 실적 목록) |
