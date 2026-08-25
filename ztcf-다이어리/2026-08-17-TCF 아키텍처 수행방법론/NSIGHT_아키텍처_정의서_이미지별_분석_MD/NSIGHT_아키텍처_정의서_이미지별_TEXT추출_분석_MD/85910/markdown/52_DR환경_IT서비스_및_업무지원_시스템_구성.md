# DR환경 IT서비스 및 업무지원 시스템 구성

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_59_10 GMT+9.zip` |
| 원본 이미지 | `52.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **환경별 Physical Architecture** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **68.2%** |
| 원본 이미지 키 | `85910/52.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
2.24
DR2tZ |1서비스 및
지원 시스템 구성
지원 시스템은 서비스 제공2006에 배치됨
11서비스 및
널
채
서비스제
ㅣ
^~
(3
[!서비스 및 업무지원 시스템
==
=
iiControl-M
TINA Gloud FWK 1
oe
|I/MasterSolution!
550
60075
|
(ebabetSoltis
oS eI
aes
단말관리 WEB
단말관리 WAS
단말배포WEB:
단말배포 WAS
대외채
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | DR2tZ \|1서비스 및 | 지원 시스템 구성 | 2.24 |
| 상중단 | 11서비스 및<br>채<br>ㅣ | 지원 시스템은 서비스 제공2006에 배치됨<br>널<br>^~<br>(3 | 서비스제<br>[!서비스 및 업무지원 시스템 |
| 중단 | - | =<br>iiControl-M<br>550<br>oS eI<br>단말관리 WEB<br>단말관리 WAS | ==<br>TINA Gloud FWK 1<br>oe<br>\|I/MasterSolution!<br>60075<br>\|<br>(ebabetSoltis<br>aes<br>단말배포WEB:<br>단말배포 WAS |
| 중하단 | - | - | - |
| 하단 | 대외채 | - | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `단말` | 중단/중앙 | 단말관리 WEB |
| `단말` | 중단/중앙 | 단말관리 WAS |
| `단말` | 중단/우측 | 단말배포WEB: |
| `WEB` | 중단/중앙 | 단말관리 WEB |
| `WEB` | 중단/우측 | 단말배포WEB: |
| `WAS` | 중단/중앙 | 단말관리 WAS |
| `WAS` | 중단/우측 | 단말배포 WAS |
| `Control-M` | 중단/중앙 | iiControl-M |
| `배치` | 상중단/중앙 | 지원 시스템은 서비스 제공2006에 배치됨 |
| `Master` | 중단/우측 | \|I/MasterSolution! |
| `Solution` | 중단/우측 | \|I/MasterSolution! |

## 3. 이미지에서 확인되는 핵심 내용

- IT서비스 및 업무지원 영역에 SSO, Control-M, NH Cloud Framework, eCAMS, 단말관리 WEB/WAS 등 공통 지원 시스템이 표시된다.

## 4. 아키텍처 분석

- 업무 복구에 필요한 인증·운영·단말지원 기능을 DR 필수 공통 서비스로 포함하는 구조다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `단말`, `WEB`, `WAS`, `Control-M`, `배치`, `Master`, `Solution`
- 이 장표는 **환경별 Physical Architecture** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `8%` — aes
  - `16%` — (ebabetSoltis
  - `33%` — oS eI
  - `49%` — |I/MasterSolution!
  - `50%` — TINA Gloud FWK 1
  - `54%` — iiControl-M

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `환경별 Physical Architecture` |
| Source Key | `85910/52.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

