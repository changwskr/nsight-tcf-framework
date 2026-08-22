# File Upload 연계

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_56_31 GMT+9.zip` |
| 원본 이미지 | `33.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **온라인 프레임워크** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **69.3%** |
| 원본 이미지 키 | `85631/33.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
FileUpload |
Ul F/W(xFrame) (-) 클라우드 ei]!9) (Nhins Controller) 연 계 간 File Upload 2aS 수행 하는
Controller
http://===/upload/(serviceld) | | BBE벌 =|
[a | © 서비스 1ㅁ 인식 | —>| 파일 및 전문 처리 Map
| M121 Upload 오싱 00 (list, Body)
jie nas ㅣ HTTP Request a A —_
: (Multipart, Body) i sae Ti ile
@ | |______] T= aie ILL
파일 및 데이터 전달 | | Map ㅣ 1| File 2 j | i —_—
— (list, Body) | 오호 DataBa:
7 은은마다EE |
@ Return | | | 서비스 오를 = ㅣ Body || >
Upload 성공, 실패이부 [J = i
1, 비 에 서 파일 Upload 요 정 합니다
2 NhFileControllerOlA| 서비스 ID 인식, 시스템 선처리 를 수 행합니다.
(서비스 1 ㅁ 는 186://-~~/401080/{56010010) 형대로 요청 합니다)
3 요정 받은 파일은HTTP Request Multipart 담 겨 있 으며 Ｌ16(형태로 변 환 하여 요정 8660>값과 같이 Map 에 담 습니다
4 Service 명역에서는 NhFileControllers= 부터 전 달받은 MapOlAl 파 일과Bodys+ 7 MYLICH.
5. 605에PUT 하거나 Body 부에 대한 DB 작 업을 수행합니다.
5 Service 염역에 대한 수행이 완료 되면 NhFileControllerOlAl 시스템 후처리 를 수행합니다.
| = =
- -141- 체가
= aps nd 7 - = 0 oK
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | - | FileUpload \|<br>Ul F/W(xFrame) (-) 클라우드 ei]!9) (Nhins Controller) 연 계 간 File Upload 2aS 수행 하는 | - |
| 상중단 | Controller | http://===/upload/(serviceld) \| \| BBE벌 =\|<br>[a \| © 서비스 1ㅁ 인식 \| —>\| 파일 및 전문 처리 Map | - |
| 중단 | - | \| M121 Upload 오싱 00 (list, Body)<br>jie nas ㅣ HTTP Request a A —_<br>: (Multipart, Body) i sae Ti ile<br>@ \| \|______] T= aie ILL<br>파일 및 데이터 전달 \| \| Map ㅣ 1\| File 2 j \| i —_—<br>— (list, Body) \| 오호 DataBa:<br>7 은은마다EE \| | - |
| 중하단 | 1, 비 에 서 파일 Upload 요 정 합니다<br>2 NhFileControllerOlA\| 서비스 ID 인식, 시스템 선처리 를 수 행합니다.<br>(서비스 1 ㅁ 는 186://-~~/401080/{56010010) 형대로 요청 합니다) | @ Return \| \| \| 서비스 오를 = ㅣ Body \|\| ><br>Upload 성공, 실패이부 [J = i<br>3 요정 받은 파일은HTTP Request Multipart 담 겨 있 으며 Ｌ16(형태로 변 환 하여 요정 8660>값과 같이 Map 에 담 습니다 | - |
| 하단 | 4 Service 명역에서는 NhFileControllers= 부터 전 달받은 MapOlAl 파 일과Bodys+ 7 MYLICH.<br>5. 605에PUT 하거나 Body 부에 대한 DB 작 업을 수행합니다.<br>5 Service 염역에 대한 수행이 완료 되면 NhFileControllerOlAl 시스템 후처리 를 수행합니다.<br>\| = = | - -141- 체가<br>= aps nd 7 - = 0 oK | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `xFrame` | 상단/중앙 | Ul F/W(xFrame) (-) 클라우드 ei]!9) (Nhins Controller) 연 계 간 File Upload 2aS 수행 하는 |
| `AP` | 상중단/중앙 | [a \| © 서비스 1ㅁ 인식 \| —>\| 파일 및 전문 처리 Map |
| `AP` | 중단/중앙 | 파일 및 데이터 전달 \| \| Map ㅣ 1\| File 2 j \| i —_— |
| `AP` | 중하단/중앙 | 3 요정 받은 파일은HTTP Request Multipart 담 겨 있 으며 Ｌ16(형태로 변 환 하여 요정 8660>값과 같이 Map 에 담 습니다 |
| `DB` | 하단/좌측 | 5. 605에PUT 하거나 Body 부에 대한 DB 작 업을 수행합니다. |
| `File` | 상단/중앙 | FileUpload \| |
| `File` | 상단/중앙 | Ul F/W(xFrame) (-) 클라우드 ei]!9) (Nhins Controller) 연 계 간 File Upload 2aS 수행 하는 |
| `File` | 중단/중앙 | 파일 및 데이터 전달 \| \| Map ㅣ 1\| File 2 j \| i —_— |
| `파일` | 상중단/중앙 | [a \| © 서비스 1ㅁ 인식 \| —>\| 파일 및 전문 처리 Map |
| `파일` | 중단/중앙 | 파일 및 데이터 전달 \| \| Map ㅣ 1\| File 2 j \| i —_— |
| `파일` | 중하단/좌측 | 1, 비 에 서 파일 Upload 요 정 합니다 |
| `Controller` | 상단/중앙 | Ul F/W(xFrame) (-) 클라우드 ei]!9) (Nhins Controller) 연 계 간 File Upload 2aS 수행 하는 |
| `Controller` | 상중단/좌측 | Controller |
| `Controller` | 중하단/좌측 | 2 NhFileControllerOlA\| 서비스 ID 인식, 시스템 선처리 를 수 행합니다. |
| `전문` | 상중단/중앙 | [a \| © 서비스 1ㅁ 인식 \| —>\| 파일 및 전문 처리 Map |

## 3. 이미지에서 확인되는 핵심 내용

- UI F/W에서 File Upload 요청을 NHFileController(Upload)로 전달하고 Service를 통해 Object Storage/Database로 저장하는 흐름이 보인다.
- Multipart/Body, Map 변환, File 목록 처리 단계가 표현된다.

## 4. 아키텍처 분석

- 파일 업로드를 개별 업무 Controller가 아닌 공통 File Controller/Service로 표준화해 보안·용량·저장소 연계를 공통화한다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `xFrame`, `AP`, `DB`, `File`, `파일`, `Controller`, `전문`
- 이 장표는 **온라인 프레임워크** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `31%` — 7 은은마다EE |
  - `41%` — = aps nd 7 - = 0 oK
  - `43%` — @ | |______] T= aie ILL
  - `45%` — - -141- 체가
  - `47%` — jie nas ㅣ HTTP Request a A —_
  - `54%` — : (Multipart, Body) i sae Ti ile

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `온라인 프레임워크` |
| Source Key | `85631/33.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

