# Exception Handling (1/2)

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_56_31 GMT+9.zip` |
| 원본 이미지 | `39.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **오류처리 아키텍처** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **75.0%** |
| 원본 이미지 키 | `85631/39.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
ExceptionHandling(1/2) |
프레임워크에서 제 공하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생
ㅇ
Service 에서 Exception 객 제 를 전달 받음
@ |
Z ： E | Common 영역 Error Process 호출 77 | catch! Cihrowablel®)(
| &@ XFrameOll 전달할 메세지 Setting | | 0 6282906662
~ 트 | 비- = |__|] | = erserviceName: 에러 서비스 명
= = 652 {* errMethodName: 에러 메서드 명
| | ErrorProcess(Co; | * erFileName:에러 파일 명
——— =, * pgmLineNo: 프로그램 라인 번호
@ 표 준 에러 전문 생성 Ta —=
@./
그 xFrameOi] 전달할 메세지 생성 '
| | |
1. ServiceOllA| Exception 발생시 NhBaseException 객체를 생성하여 장애종류코드, 에러 서비스 명 등 값을 셋팅
2. ControllerOl|M= 506「4160에서 전 달 받은Exception 객체를 <0ㅁ10107 영 역의Error Process 메서드의 인자 로 전달
3. HSS Exception 객체를 기반 으로 표준 에러 전문 생성
4. Ul(xFrame)Ol]전달할 오류 메세지 생성
5. Error Process 로 부터 전달받은 오류 메 세 지 를 xFrameOll 전달
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | - | ExceptionHandling(1/2) \|<br>프레임워크에서 제 공하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생 | - |
| 상중단 | ㅇ | Service 에서 Exception 객 제 를 전달 받음<br>@ \| | - |
| 중단 | - | Z ： E \| Common 영역 Error Process 호출 77 \| catch! Cihrowablel®)(<br>\| &@ XFrameOll 전달할 메세지 Setting \| \| 0 6282906662<br>~ 트 \| 비- = \|__\|] \| = erserviceName: 에러 서비스 명<br>= = 652 {* errMethodName: 에러 메서드 명<br>\| \| ErrorProcess(Co; \| * erFileName:에러 파일 명<br>——— =, * pgmLineNo: 프로그램 라인 번호<br>@ 표 준 에러 전문 생성 Ta —= | - |
| 중하단 | - | @./<br>그 xFrameOi] 전달할 메세지 생성 '<br>\| \| \|<br>1. ServiceOllA\| Exception 발생시 NhBaseException 객체를 생성하여 장애종류코드, 에러 서비스 명 등 값을 셋팅<br>2. ControllerOl\|M= 506「4160에서 전 달 받은Exception 객체를 <0ㅁ10107 영 역의Error Process 메서드의 인자 로 전달 | - |
| 하단 | 3. HSS Exception 객체를 기반 으로 표준 에러 전문 생성<br>4. Ul(xFrame)Ol]전달할 오류 메세지 생성<br>5. Error Process 로 부터 전달받은 오류 메 세 지 를 xFrameOll 전달 | - | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `xFrame` | 중단/중앙 | \| &@ XFrameOll 전달할 메세지 Setting \| \| 0 6282906662 |
| `xFrame` | 중하단/중앙 | 그 xFrameOi] 전달할 메세지 생성 ' |
| `xFrame` | 하단/좌측 | 4. Ul(xFrame)Ol]전달할 오류 메세지 생성 |
| `프레임워크` | 상단/중앙 | 프레임워크에서 제 공하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생 |
| `File` | 중단/중앙 | \| \| ErrorProcess(Co; \| * erFileName:에러 파일 명 |
| `파일` | 중단/중앙 | \| \| ErrorProcess(Co; \| * erFileName:에러 파일 명 |
| `Exception` | 상단/중앙 | ExceptionHandling(1/2) \| |
| `Exception` | 상단/중앙 | 프레임워크에서 제 공하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생 |
| `Exception` | 상중단/중앙 | Service 에서 Exception 객 제 를 전달 받음 |
| `Controller` | 중하단/중앙 | 2. ControllerOl\|M= 506「4160에서 전 달 받은Exception 객체를 <0ㅁ10107 영 역의Error Process 메서드의 인자 로 전달 |
| `전문` | 상단/중앙 | 프레임워크에서 제 공하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생 |
| `전문` | 중단/중앙 | @ 표 준 에러 전문 생성 Ta —= |
| `전문` | 하단/좌측 | 3. HSS Exception 객체를 기반 으로 표준 에러 전문 생성 |

## 3. 이미지에서 확인되는 핵심 내용

- Service에서 Exception 발생 시 NHBaseException을 생성하고 Controller의 Common Error Process로 전달하는 흐름이 보인다.
- UI F/W로 오류 메시지를 반환하는 단계가 번호로 표시된다.

## 4. 아키텍처 분석

- 업무 서비스는 예외를 던지고 공통 오류처리기가 오류 메시지/응답 형식을 만드는 중앙집중형 예외처리 구조다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `xFrame`, `프레임워크`, `File`, `파일`, `Exception`, `Controller`, `전문`
- 이 장표는 **오류처리 아키텍처** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `40%` — @./
  - `54%` — ~ 트 | 비- = |__|] | = erserviceName: 에러 서비스 명

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `오류처리 아키텍처` |
| Source Key | `85631/39.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

