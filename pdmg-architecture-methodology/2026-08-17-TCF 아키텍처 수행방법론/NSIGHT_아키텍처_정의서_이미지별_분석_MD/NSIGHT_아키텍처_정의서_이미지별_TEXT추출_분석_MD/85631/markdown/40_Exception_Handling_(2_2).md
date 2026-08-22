# Exception Handling (2/2)

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_56_31 GMT+9.zip` |
| 원본 이미지 | `40.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **오류처리 아키텍처** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **59.4%** |
| 원본 이미지 키 | `85631/40.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
|
ㅣ
Exception11231701100(2/2)
프 레이 으 ru. eri A
프테럽 워 크에서 제 공 하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생
=
다 - {6
| 년) Service 크어 래 오류 메시지
| If (returntap = null) (= —
|) 하 면멀 10001 |
YF ate (tacepetoe oy; Traninfo lerror2Vapi/msa1/ina/MIgiG009S0} |
| StackTraceflement(] stc = e.getStackTrace(); 오 류 메 시 지 코드 100 ㅣ
exception ineteresericeNuae(see{0) .getclasstiane()); ae |
\axtaption!settrrrilenane(stc(0).getrilcNane())> 표 준 예 러 코 드 넘버 i |
|| exception. setPgetinetio(stc[o].getLineNumber()); ee 아하 |
Wenroorascention; 로 3 |
106 - = 표 준에 러 메 시 지내용:|여 러 가 발 생 하 연 슬 니다
|_6004010 | 이 로
| 잘 애 증 유 코 드 cM
Output * (Map<String, Object>) service.service(input); ee (MiBI0009S0Servicejava
"igervice call
7 예러 서비스 멀 onnhbankIns.service mi bLMIBI0O09SOService
servicelransactionanager, getinstance(),commit(serviceTx): | 에 러 메 서 드 멍
| 번대(Throwable t) (| mee as
iS \[Servicetransactiontianager.getinstance(),rollback(serviceTx); | |프 로 그 램라인번트 24
|고responsestatus = HttpStatus. INTERNAL SERVER ERROR; LN 5
pakJaodeTsataKterToute(3?roseSVTenKFIOESERR.COOE, 100"); 거더 |
lerroriisg = systesProcessor.inserrorProcess(t); 13) |다|
By rode)addAttcibute(xtromesViewXPRAIESERR-OETATL,erroriisn): | | ——
1. ServiceOll} Exception 발생시 NhBaseException 객체를 생성하여 장애종류코드, 에러 서비스 명 등 값을 셋팅
2. 600하이 라 에 서는56004166에서 전 달 받은 Exception 객 체 를 Common SA Error Process 메서드의 인 자로 전달
3. 전 달 받 은 Exception 객체를 기 반 으로 표준 에 러 전문 생성
4. Error Process 로 부터 전 달받은 오류 메 세 지 를 xFrameOll 전달
5. 비에 전달된 거래 오류 메세지
삐 - 148- De
‘suse SK Aas
~ oe, =
ei
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | \|<br>Exception11231701100(2/2)<br>프 레이 으 ru. eri A | 프테럽 워 크에서 제 공 하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생 | ㅣ |
| 상중단 | =<br>다 - {6<br>\| 년) Service 크어 래 오류 메시지 | \| If (returntap = null) (= —<br>\|) 하 면멀 10001 \| | - |
| 중단 | \|_6004010 \| 이 로<br>"igervice call | YF ate (tacepetoe oy; Traninfo lerror2Vapi/msa1/ina/MIgiG009S0} \|<br>\| StackTraceflement(] stc = e.getStackTrace(); 오 류 메 시 지 코드 100 ㅣ<br>exception ineteresericeNuae(see{0) .getclasstiane()); ae \|<br>\axtaption!settrrrilenane(stc(0).getrilcNane())> 표 준 예 러 코 드 넘버 i \|<br>\|\| exception. setPgetinetio(stc[o].getLineNumber()); ee 아하 \|<br>Wenroorascention; 로 3 \|<br>106 - = 표 준에 러 메 시 지내용:\|여 러 가 발 생 하 연 슬 니다<br>\| 잘 애 증 유 코 드 cM | - |
| 중하단 | servicelransactionanager, getinstance(),commit(serviceTx): \| 에 러 메 서 드 멍<br>pakJaodeTsataKterToute(3?roseSVTenKFIOESERR.COOE, 100"); 거더 \| | Output * (Map<String, Object>) service.service(input); ee (MiBI0009S0Servicejava<br>7 예러 서비스 멀 onnhbankIns.service mi bLMIBI0O09SOService<br>\| 번대(Throwable t) (\| mee as<br>iS \[Servicetransactiontianager.getinstance(),rollback(serviceTx); \| \|프 로 그 램라인번트 24<br>\|고responsestatus = HttpStatus. INTERNAL SERVER ERROR; LN 5<br>lerroriisg = systesProcessor.inserrorProcess(t); 13) \|다\|<br>By rode)addAttcibute(xtromesViewXPRAIESERR-OETATL,erroriisn): \| \| ——<br>1. ServiceOll} Exception 발생시 NhBaseException 객체를 생성하여 장애종류코드, 에러 서비스 명 등 값을 셋팅<br>2. 600하이 라 에 서는56004166에서 전 달 받은 Exception 객 체 를 Common SA Error Process 메서드의 인 자로 전달 | - |
| 하단 | 3. 전 달 받 은 Exception 객체를 기 반 으로 표준 에 러 전문 생성<br>4. Error Process 로 부터 전 달받은 오류 메 세 지 를 xFrameOll 전달<br>5. 비에 전달된 거래 오류 메세지<br>~ oe, =<br>ei | 삐 - 148- De<br>‘suse SK Aas | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `xFrame` | 하단/좌측 | 4. Error Process 로 부터 전 달받은 오류 메 세 지 를 xFrameOll 전달 |
| `AP` | 상중단/중앙 | \| If (returntap = null) (= — |
| `AP` | 중단/중앙 | YF ate (tacepetoe oy; Traninfo lerror2Vapi/msa1/ina/MIgiG009S0} \| |
| `AP` | 중단/중앙 | \axtaption!settrrrilenane(stc(0).getrilcNane())> 표 준 예 러 코 드 넘버 i \| |
| `ETL` | 중단/중앙 | \|\| exception. setPgetinetio(stc[o].getLineNumber()); ee 아하 \| |
| `SSO` | 중하단/중앙 | lerroriisg = systesProcessor.inserrorProcess(t); 13) \|다\| |
| `Exception` | 상단/좌측 | Exception11231701100(2/2) |
| `Exception` | 상단/중앙 | 프테럽 워 크에서 제 공 하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생 |
| `Exception` | 중단/중앙 | exception ineteresericeNuae(see{0) .getclasstiane()); ae \| |
| `거래` | 하단/좌측 | 5. 비에 전달된 거래 오류 메세지 |
| `전문` | 상단/중앙 | 프테럽 워 크에서 제 공 하는 NhBaseException 에 의하여 Exception 발생시 메시지 저리 및 에러전문 생 |
| `전문` | 하단/좌측 | 3. 전 달 받 은 Exception 객체를 기 반 으로 표준 에 러 전문 생성 |

## 3. 이미지에서 확인되는 핵심 내용

- Service/Controller 코드 예시와 실제 오류 메시지 화면이 함께 제시된다.
- NHBaseException 생성/전달 및 Common Error Process 호출 예가 강조된다.

## 4. 아키텍처 분석

- 오류처리 표준이 개념뿐 아니라 구현 코드와 사용자 메시지까지 연결되어 있음을 보여주는 증적 장표다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `xFrame`, `AP`, `ETL`, `SSO`, `Exception`, `거래`, `전문`
- 이 장표는 **오류처리 아키텍처** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 텍스트/표 중심 장표로 판단되며, 원문 항목을 그대로 추출한 뒤 구조적 의미를 분석하였다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `29%` — exception ineteresericeNuae(see{0) .getclasstiane()); ae |
  - `32%` — pakJaodeTsataKterToute(3?roseSVTenKFIOESERR.COOE, 100"); 거더 |
  - `32%` — | 번대(Throwable t) (| mee as
  - `34%` — By rode)addAttcibute(xtromesViewXPRAIESERR-OETATL,erroriisn): | | ——
  - `38%` — "igervice call
  - `40%` — ~ oe, =
  - `41%` — YF ate (tacepetoe oy; Traninfo lerror2Vapi/msa1/ina/MIgiG009S0} |
  - `42%` — || exception. setPgetinetio(stc[o].getLineNumber()); ee 아하 |
  - `44%` — ‘suse SK Aas
  - `46%` — 다 - {6

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `오류처리 아키텍처` |
| Source Key | `85631/40.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

