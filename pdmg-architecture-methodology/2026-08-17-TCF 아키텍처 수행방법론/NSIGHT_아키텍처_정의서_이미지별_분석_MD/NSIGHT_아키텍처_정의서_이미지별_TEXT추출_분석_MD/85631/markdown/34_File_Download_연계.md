# File Download 연계

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_56_31 GMT+9.zip` |
| 원본 이미지 | `34.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **온라인 프레임워크** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **68.2%** |
| 원본 이미지 키 | `85631/34.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
!
File Download
UlF/W(xFrame) (-) 클라우드 Hej/2!9/5 (Nhins Controller) 연 계 간 File Download 24'S 수행하
yt ie) 은 더 제스
는 Controller
hae Aa | | — KS == =i Dbject Storage —
「 | | IO 서비스 ㅁ 인식 | _|@ Map. |
Hittp//===/download/(serviceld) = = [=>
| | M8 Download 오성 | |; i | 서비스 호출 | [_(ownlaoad할 파일명)
= | | | 시스템 선처리 주형 | | |
Downlosd파일 |"| eine RES) [Mixsien |
Map 형태 전달
Oo 시스템 후 저리 Lowe toa
LC ——: (OutputStream)
1. 비 메서 파일 Download 요청합니다.
2 NhFileControllerOlA| 서비스 ip 인식, 시스템 선처리 를 수 행합니다.
(서비스 ID hittp//~~~/download/(Serviceld) 형태로 요 청합니다)
3 Service 오 출시 561160에 전달한 인 자값 은 Download 할 파 일 명 을 Map 형태로 전달 합 니다.
4. ControllerOlA| 전 달 된 MapOllAl Download 할 파일명을 AYLIct.
5, Object 5401296에서File 가져옵니다.
6. Service 영역에 대한 수행이 완 료되면 NhFileControllerOlAl 시스템 후처리를 수행합니다.
미에 결과Beton voidOU Http Responses보 내 게 됩 니디
Sasa mae SK Soci |
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | !<br>File Download | UlF/W(xFrame) (-) 클라우드 Hej/2!9/5 (Nhins Controller) 연 계 간 File Download 24'S 수행하<br>yt ie) 은 더 제스 | - |
| 상중단 | 는 Controller | hae Aa \| \| — KS == =i Dbject Storage —<br>「 \| \| IO 서비스 ㅁ 인식 \| _\|@ Map. \|<br>Hittp//===/download/(serviceld) = = [=> | - |
| 중단 | Map 형태 전달 | \| \| M8 Download 오성 \| \|; i \| 서비스 호출 \| [_(ownlaoad할 파일명)<br>= \| \| \| 시스템 선처리 주형 \| \| \|<br>Downlosd파일 \|"\| eine RES) [Mixsien \|<br>Oo 시스템 후 저리 Lowe toa<br>LC ——: (OutputStream) | - |
| 중하단 | 1. 비 메서 파일 Download 요청합니다.<br>2 NhFileControllerOlA\| 서비스 ip 인식, 시스템 선처리 를 수 행합니다.<br>(서비스 ID hittp//~~~/download/(Serviceld) 형태로 요 청합니다)<br>3 Service 오 출시 561160에 전달한 인 자값 은 Download 할 파 일 명 을 Map 형태로 전달 합 니다. | - | - |
| 하단 | 4. ControllerOlA\| 전 달 된 MapOllAl Download 할 파일명을 AYLIct.<br>5, Object 5401296에서File 가져옵니다.<br>6. Service 영역에 대한 수행이 완 료되면 NhFileControllerOlAl 시스템 후처리를 수행합니다.<br>미에 결과Beton voidOU Http Responses보 내 게 됩 니디 | Sasa mae SK Soci \| | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `xFrame` | 상단/중앙 | UlF/W(xFrame) (-) 클라우드 Hej/2!9/5 (Nhins Controller) 연 계 간 File Download 24'S 수행하 |
| `AP` | 상중단/중앙 | 「 \| \| IO 서비스 ㅁ 인식 \| _\|@ Map. \| |
| `AP` | 중단/좌측 | Map 형태 전달 |
| `AP` | 중하단/좌측 | 3 Service 오 출시 561160에 전달한 인 자값 은 Download 할 파 일 명 을 Map 형태로 전달 합 니다. |
| `DB` | 상중단/중앙 | hae Aa \| \| — KS == =i Dbject Storage — |
| `File` | 상단/좌측 | File Download |
| `File` | 상단/중앙 | UlF/W(xFrame) (-) 클라우드 Hej/2!9/5 (Nhins Controller) 연 계 간 File Download 24'S 수행하 |
| `File` | 중하단/좌측 | 2 NhFileControllerOlA\| 서비스 ip 인식, 시스템 선처리 를 수 행합니다. |
| `파일` | 중단/중앙 | \| \| M8 Download 오성 \| \|; i \| 서비스 호출 \| [_(ownlaoad할 파일명) |
| `파일` | 중단/중앙 | Downlosd파일 \|"\| eine RES) [Mixsien \| |
| `파일` | 중하단/좌측 | 1. 비 메서 파일 Download 요청합니다. |
| `Controller` | 상단/중앙 | UlF/W(xFrame) (-) 클라우드 Hej/2!9/5 (Nhins Controller) 연 계 간 File Download 24'S 수행하 |
| `Controller` | 상중단/좌측 | 는 Controller |
| `Controller` | 중하단/좌측 | 2 NhFileControllerOlA\| 서비스 ip 인식, 시스템 선처리 를 수 행합니다. |

## 3. 이미지에서 확인되는 핵심 내용

- UI F/W의 File Download 요청이 NHFileController(Download) → Service → Object Storage로 이어진다.
- Download Map, InputStream, File GET 등의 처리 요소가 보인다.

## 4. 아키텍처 분석

- 다운로드 역시 공통 Controller에서 권한·파일식별·스트리밍을 통제하는 표준 구조다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `xFrame`, `AP`, `DB`, `File`, `파일`, `Controller`
- 이 장표는 **온라인 프레임워크** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `31%` — yt ie) 은 더 제스
  - `41%` — LC ——: (OutputStream)
  - `41%` — Sasa mae SK Soci |
  - `48%` — Downlosd파일 |"| eine RES) [Mixsien |
  - `49%` — 미에 결과Beton voidOU Http Responses보 내 게 됩 니디
  - `49%` — hae Aa | | — KS == =i Dbject Storage —
  - `50%` — Oo 시스템 후 저리 Lowe toa

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `온라인 프레임워크` |
| Source Key | `85631/34.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

