# RD(Report Designer) 연계

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_56_31 GMT+9.zip` |
| 원본 이미지 | `35.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **온라인 프레임워크** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **77.3%** |
| 원본 이미지 키 | `85631/35.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
레포트(00) 연계
니 [700(자(6006) 에서 RD (Report Desinger) 연계 시 DB 저리를 수행할때 사용하는 Controller
| 3 http://===/rd/(serviceld} —
| | @ 서비스 ID 인식 |
| | ㅣ | 서비스 호출 Ｌ DataBasi
Map 전당 |
T ㅣ String
Retum: String —— =
1 미 에 서 RD Controller 를 호줄하며 Map 을 전탈 합니다. i
2 NhRDControllerOAM| 서비스 Ip 인식, 시스템 선 처리 를 수행 합니다,
(AEA ID httpi//~~~/rd/(Serviceld) SES QAP LICH)
3. Service SHA| Service] 전달한 인자값 은 Map 형태로 전달합니다,
4. 00000101에서 전 탈 된 Map& 사용해 ㅁ 8작 입을 수 행합니다
5. Service 영역이 대한 수행이 완 료되면 NhRDControllerHAl 시스템 후저리를 수행합니다,
7 결과 Return 값은50109 타입을 반 환 합니다.
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | 레포트(00) 연계 | 니 [700(자(6006) 에서 RD (Report Desinger) 연계 시 DB 저리를 수행할때 사용하는 Controller | - |
| 상중단 | \| 3 http://===/rd/(serviceld} —<br>\| \| @ 서비스 ID 인식 \| | - | - |
| 중단 | Map 전당 \| | \| \| ㅣ \| 서비스 호출 Ｌ DataBasi<br>T ㅣ String | - |
| 중하단 | Retum: String —— =<br>1 미 에 서 RD Controller 를 호줄하며 Map 을 전탈 합니다. i<br>2 NhRDControllerOAM\| 서비스 Ip 인식, 시스템 선 처리 를 수행 합니다,<br>(AEA ID httpi//~~~/rd/(Serviceld) SES QAP LICH)<br>3. Service SHA\| Service] 전달한 인자값 은 Map 형태로 전달합니다, | - | - |
| 하단 | 4. 00000101에서 전 탈 된 Map& 사용해 ㅁ 8작 입을 수 행합니다<br>5. Service 영역이 대한 수행이 완 료되면 NhRDControllerHAl 시스템 후저리를 수행합니다,<br>7 결과 Return 값은50109 타입을 반 환 합니다. | - | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `AP` | 중단/좌측 | Map 전당 \| |
| `AP` | 중하단/좌측 | 1 미 에 서 RD Controller 를 호줄하며 Map 을 전탈 합니다. i |
| `AP` | 중하단/좌측 | (AEA ID httpi//~~~/rd/(Serviceld) SES QAP LICH) |
| `DB` | 상단/중앙 | 니 [700(자(6006) 에서 RD (Report Desinger) 연계 시 DB 저리를 수행할때 사용하는 Controller |
| `Controller` | 상단/중앙 | 니 [700(자(6006) 에서 RD (Report Desinger) 연계 시 DB 저리를 수행할때 사용하는 Controller |
| `Controller` | 중하단/좌측 | 1 미 에 서 RD Controller 를 호줄하며 Map 을 전탈 합니다. i |
| `Controller` | 중하단/좌측 | 2 NhRDControllerOAM\| 서비스 Ip 인식, 시스템 선 처리 를 수행 합니다, |

## 3. 이미지에서 확인되는 핵심 내용

- UI F/W에서 RD(Report Designer) 연계 시 DB 처리를 수행하는 공통 Controller 흐름이 보인다.
- Controller의 Map 변환 후 Service/Database로 연결된다.

## 4. 아키텍처 분석

- 리포트 전용 솔루션 호출도 공통 Controller/Service 경계를 통해 업무 프레임워크와 일관되게 통합한다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `AP`, `DB`, `Controller`
- 이 장표는 **온라인 프레임워크** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `49%` — (AEA ID httpi//~~~/rd/(Serviceld) SES QAP LICH)

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `온라인 프레임워크` |
| Source Key | `85631/35.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

