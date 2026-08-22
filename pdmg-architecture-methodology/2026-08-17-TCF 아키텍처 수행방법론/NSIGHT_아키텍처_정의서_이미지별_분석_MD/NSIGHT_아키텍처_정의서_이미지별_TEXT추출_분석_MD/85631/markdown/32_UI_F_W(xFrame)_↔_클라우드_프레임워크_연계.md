# UI F/W(xFrame) ↔ 클라우드 프레임워크 연계

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_56_31 GMT+9.zip` |
| 원본 이미지 | `32.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **온라인 프레임워크** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **54.7%** |
| 원본 이미지 키 | `85631/32.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
rr |
단말 Application Fram |
단 pplication Framework 연계
—-) 크라으 ㄷㄷ 프 레이 으 1 a
UIF/W(xFrame) (-) 클라우드 프레임워크(105 Controller) 연계
| Sarees (| || exe http7===/ins/iServiceld) @ = SE ay
“version”:“VERSION. 1” Z @ 서비스 10 인식) ‘AIIES 4 Sa ae Tal
02000 10000 |000 | |? Cee |
： [20065] 180 입력선문 민잘, 셋팅 시스템 추 저리660060086 처리) | |!
: past) ih iain raid WAL ied) Ws Map=> DIO Mapping
datasets: (| | 1659 튜 B T ‘ay SLE column = 010 생성 |
“id”: 5, BSA 0 _- 6) rer) |= = 0
*w info_arr: (J | (Gy serie Corteiot ap HH (Gy asus Present ||| is |
기 개 이 — 4 t Sa Hs Map)
“column. info arr”: (..] 은 eS (Mane Dateseee |. | (0006 통신, 641 연계 필 요시 |
“column 00070 arr’: […] | y 40 aySs=
는 을 asieiciaraoy | = EeRet | | mipas 21 ㅣ
Pe Swe Se
SS = a — St = = 1. xframe0IA| Nhins Controller= xDataset
so 4d]: pring Controller ue
| WEE 고 2, 서비스 1D 인식 시 URL 페턴으로 NHINS
hitp://-~~/nb/(Serviceld) al | Controller S&F
—— et아 고가 Ase
| 1 | 서비스 16 인식 | 를! | 3. ELEMElocDataset ~ Map)
= $$ ¢________.- Sc 장
| | | 시스템 Ae| Exception 처리) | A, 변환한Map Service 000100에 저장
So {J 5. 서비스 호출 시 Input DTOE Dummy zoe
|PriPr1090혀치011 기아) | | 전문 UnMarshal(Json > DTO) | y 전당
| beset Bel | | 전문 UnMarshal(DTO -> 1500) ㅣ 6, Service Contextol 있는 \4007를 가져롬
시스템 선치리(기레재어) |- 1! 7, 400에있는 각 컬덤과010를Mapping
the Rett
} 10500 9, Map -> 900105이으로 변환
He = SDaaseTS TE
더 -140-%
놈 열 증 왕인 · 56" 「
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | 단 pplication Framework 연계<br>UIF/W(xFrame) (-) 클라우드 프레임워크(105 Controller) 연계 | rr \|<br>단말 Application Fram \|<br>—-) 크라으 ㄷㄷ 프 레이 으 1 a | - |
| 상중단 | - | \| Sarees (\| \|\| exe http7===/ins/iServiceld) @ = SE ay<br>“version”:“VERSION. 1” Z @ 서비스 10 인식) ‘AIIES 4 Sa ae Tal | - |
| 중단 | - | 02000 10000 \|000 \| \|? Cee \|<br>： [20065] 180 입력선문 민잘, 셋팅 시스템 추 저리660060086 처리) \| \|!<br>: past) ih iain raid WAL ied) Ws Map=> DIO Mapping<br>datasets: (\| \| 1659 튜 B T ‘ay SLE column = 010 생성 \|<br>“id”: 5, BSA 0 _- 6) rer) \|= = 0<br>*w info_arr: (J \| (Gy serie Corteiot ap HH (Gy asus Present \|\|\| is \|<br>기 개 이 — 4 t Sa Hs Map)<br>“column. info arr”: (..] 은 eS (Mane Dateseee \|. \| (0006 통신, 641 연계 필 요시 \|<br>“column 00070 arr’: […] \| y 40 aySs=<br>는 을 asieiciaraoy \| = EeRet \| \| mipas 21 ㅣ<br>Pe Swe Se | - |
| 중하단 | - | SS = a — St = = 1. xframe0IA\| Nhins Controller= xDataset<br>so 4d]: pring Controller ue<br>\| WEE 고 2, 서비스 1D 인식 시 URL 페턴으로 NHINS<br>hitp://-~~/nb/(Serviceld) al \| Controller S&F<br>—— et아 고가 Ase<br>\| 1 \| 서비스 16 인식 \| 를! \| 3. ELEMElocDataset ~ Map)<br>= $$ ¢________.- Sc 장<br>\| \| \| 시스템 Ae\| Exception 처리) \| A, 변환한Map Service 000100에 저장<br>So {J 5. 서비스 호출 시 Input DTOE Dummy zoe<br>\|PriPr1090혀치011 기아) \| \| 전문 UnMarshal(Json > DTO) \| y 전당<br>\| beset Bel \| \| 전문 UnMarshal(DTO -> 1500) ㅣ 6, Service Contextol 있는 \4007를 가져롬 | - |
| 하단 | - | 시스템 선치리(기레재어) \|- 1! 7, 400에있는 각 컬덤과010를Mapping<br>the Rett<br>} 10500 9, Map -> 900105이으로 변환<br>He = SDaaseTS TE<br>더 -140-%<br>놈 열 증 왕인 · 56" 「 | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `단말` | 상단/중앙 | 단말 Application Fram \| |
| `xFrame` | 상단/좌측 | UIF/W(xFrame) (-) 클라우드 프레임워크(105 Controller) 연계 |
| `xFrame` | 중하단/중앙 | SS = a — St = = 1. xframe0IA\| Nhins Controller= xDataset |
| `AP` | 상단/중앙 | 단말 Application Fram \| |
| `AP` | 중단/중앙 | : past) ih iain raid WAL ied) Ws Map=> DIO Mapping |
| `AP` | 중단/중앙 | *w info_arr: (J \| (Gy serie Corteiot ap HH (Gy asus Present \|\|\| is \| |
| `Framework` | 상단/좌측 | 단 pplication Framework 연계 |
| `프레임워크` | 상단/좌측 | UIF/W(xFrame) (-) 클라우드 프레임워크(105 Controller) 연계 |
| `Exception` | 중하단/중앙 | \| \| \| 시스템 Ae\| Exception 처리) \| A, 변환한Map Service 000100에 저장 |
| `Controller` | 상단/좌측 | UIF/W(xFrame) (-) 클라우드 프레임워크(105 Controller) 연계 |
| `Controller` | 중하단/중앙 | SS = a — St = = 1. xframe0IA\| Nhins Controller= xDataset |
| `Controller` | 중하단/중앙 | so 4d]: pring Controller ue |
| `Application` | 상단/중앙 | 단말 Application Fram \| |
| `전문` | 중하단/중앙 | \|PriPr1090혀치011 기아) \| \| 전문 UnMarshal(Json > DTO) \| y 전당 |
| `전문` | 중하단/중앙 | \| beset Bel \| \| 전문 UnMarshal(DTO -> 1500) ㅣ 6, Service Contextol 있는 \4007를 가져롬 |

## 3. 이미지에서 확인되는 핵심 내용

- UI F/W(xFrame)에서 업무 Controller로 요청을 전달하는 흐름이 번호로 표시된다.
- NHins Controller 계층과 Service/Biz Logic, DTO/Mapping, 시스템 선후처리/AOP가 함께 표현된다.

## 4. 아키텍처 분석

- UI 기술과 서버 프레임워크 사이의 표준 호출 경계를 Controller/Service 계약으로 고정해 화면과 업무로직의 결합을 낮춘다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `단말`, `xFrame`, `AP`, `Framework`, `프레임워크`, `Exception`, `Controller`, `Application`, `전문`
- 이 장표는 **온라인 프레임워크** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `26%` — —— et아 고가 Ase
  - `28%` — *w info_arr: (J | (Gy serie Corteiot ap HH (Gy asus Present ||| is |
  - `28%` — : past) ih iain raid WAL ied) Ws Map=> DIO Mapping
  - `28%` — 02000 10000 |000 | |? Cee |
  - `31%` — Pe Swe Se
  - `32%` — SS = a — St = = 1. xframe0IA| Nhins Controller= xDataset
  - `35%` — = $$ ¢________.- Sc 장
  - `38%` — | Sarees (| || exe http7===/ins/iServiceld) @ = SE ay
  - `38%` — He = SDaaseTS TE
  - `40%` — 는 을 asieiciaraoy | = EeRet | | mipas 21 ㅣ

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `온라인 프레임워크` |
| Source Key | `85631/32.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

