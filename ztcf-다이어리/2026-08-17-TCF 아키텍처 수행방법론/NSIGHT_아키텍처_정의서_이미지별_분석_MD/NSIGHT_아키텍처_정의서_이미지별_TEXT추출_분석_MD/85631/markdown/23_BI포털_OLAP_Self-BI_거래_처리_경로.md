# BI포털/OLAP/Self-BI 거래 처리 경로

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_56_31 GMT+9.zip` |
| 원본 이미지 | `23.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **거래/라우팅 표준** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **66.3%** |
| 원본 이미지 키 | `85631/23.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
업그 = ㅜ 션 7 re x 리 경로
무 솔루션 거래 처리 2
BI 포탈
사용자 (정보 단말)
Client
ㅣ 미포탈패키지비| 이 40패키지 Ul 51-81패키지 Ul
http!//pt.prod.nhct/패키지 URL) http://oa,prod.njact/{l]1] URL} http'//sb.prod.njact/{tl?|] URL)
\f vy
내 부 망 L4 내 부 망 La 내 부 망 14
ㅣ ㅣ = ㅣ
+ + + + + +
비 포탈 비 포 탈 OLAP OLAP Self-BI Self=B!
WEB #1 WEB #2 WEB #1 WEB #2 WEB #1 WEB #2
Sal Se a
% — X— — J =
비포탈 미포 탈 OLAP OLAP Self-Bl Self-BI
WAS #1 WAS #2 WAS #1) WAS #2 WAS #1 WAS #2
= 132 - Dr
Osacas = 00개
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | 업그 = ㅜ 션 7 re x 리 경로<br>무 솔루션 거래 처리 2<br>사용자 (정보 단말) | BI 포탈 | - |
| 상중단 | Client | ㅣ 미포탈패키지비\| 이 40패키지 Ul 51-81패키지 Ul<br>http!//pt.prod.nhct/패키지 URL) http://oa,prod.njact/{l]1] URL} http'//sb.prod.njact/{tl?\|] URL)<br>\f vy<br>내 부 망 L4 내 부 망 La 내 부 망 14 | - |
| 중단 | - | ㅣ ㅣ = ㅣ<br>+ + + + + +<br>비 포탈 비 포 탈 OLAP OLAP Self-BI Self=B!<br>WEB #1 WEB #2 WEB #1 WEB #2 WEB #1 WEB #2<br>Sal Se a<br>% — X— — J = | - |
| 중하단 | - | 비포탈 미포 탈 OLAP OLAP Self-Bl Self-BI<br>WAS #1 WAS #2 WAS #1) WAS #2 WAS #1 WAS #2 | - |
| 하단 | - | = 132 - Dr<br>Osacas = 00개 | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `사용자` | 상단/좌측 | 사용자 (정보 단말) |
| `단말` | 상단/좌측 | 사용자 (정보 단말) |
| `WEB` | 중단/중앙 | WEB #1 WEB #2 WEB #1 WEB #2 WEB #1 WEB #2 |
| `WAS` | 중하단/중앙 | WAS #1 WAS #2 WAS #1) WAS #2 WAS #1 WAS #2 |
| `AP` | 중단/중앙 | 비 포탈 비 포 탈 OLAP OLAP Self-BI Self=B! |
| `AP` | 중하단/중앙 | 비포탈 미포 탈 OLAP OLAP Self-Bl Self-BI |
| `OLAP` | 중단/중앙 | 비 포탈 비 포 탈 OLAP OLAP Self-BI Self=B! |
| `OLAP` | 중하단/중앙 | 비포탈 미포 탈 OLAP OLAP Self-Bl Self-BI |
| `Self-BI` | 중단/중앙 | 비 포탈 비 포 탈 OLAP OLAP Self-BI Self=B! |
| `Self-BI` | 중하단/중앙 | 비포탈 미포 탈 OLAP OLAP Self-Bl Self-BI |
| `SELF-BI` | 중단/중앙 | 비 포탈 비 포 탈 OLAP OLAP Self-BI Self=B! |
| `SELF-BI` | 중하단/중앙 | 비포탈 미포 탈 OLAP OLAP Self-Bl Self-BI |
| `거래` | 상단/좌측 | 무 솔루션 거래 처리 2 |

## 3. 이미지에서 확인되는 핵심 내용

- BI포털, OLAP, Self-BI UI가 각각 WEB #1/#2 및 WAS #1/#2로 이중화 배치된 구조가 나란히 표시된다.

## 4. 아키텍처 분석

- BI 기능별 독립 도메인/WEB/WAS 풀을 구성해 분석 워크로드와 장애영향을 분리한다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `사용자`, `단말`, `WEB`, `WAS`, `AP`, `OLAP`, `Self-BI`, `SELF-BI`, `거래`
- 이 장표는 **거래/라우팅 표준** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `18%` — \f vy
  - `20%` — Sal Se a
  - `43%` — http!//pt.prod.nhct/패키지 URL) http://oa,prod.njact/{l]1] URL} http'//sb.prod.njact/{tl?|] URL)
  - `43%` — Osacas = 00개
  - `47%` — BI 포탈

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `거래/라우팅 표준` |
| Source Key | `85631/23.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

