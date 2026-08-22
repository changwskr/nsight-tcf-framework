# AP/DB 서버 애플리케이션 파일시스템 구성

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_57_37 GMT+9.zip` |
| 원본 이미지 | `52.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **시스템 표준** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **77.3%** |
| 원본 이미지 키 | `85737/52.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
일 시스템 구성
Ao
AP, DB 서버의 디렉토리는 크게 3가지로 분류한다.
구분
용도
기본 디렉토리
11 적용시스템 디
ProgramArea
프로그램 및 Schema
/pam
/pgm_hostname
ApplicationLogArea
프로그램 실행 시 발생되는 로그
Japlog
/aplog_hostname
프로그램실행 Ao 출력되는파일
/feeraie
aa
Data Area
/userdir_hostname
OracleS/W
오라클 데이터베이스 엔진
/nhod
서버보'
안
서 버 보 안(56046TOS) 설치 디렉토리
/usr/local/TOS
fea
서
버운영관리
서버운영 관리 설치 디렉토리
|
배치작업 관리 설치 디렉토리
/seba
작업관리
SMS(HPOvenview) Agent설치 디렉토
/SMS
15
통합관제(60000)Agent설지 디렉토리
/7914170000000
|:
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | 일 시스템 구성<br>Ao | - | - |
| 상중단 | AP, DB 서버의 디렉토리는 크게 3가지로 분류한다.<br>구분<br>ProgramArea | 용도<br>프로그램 및 Schema | 기본 디렉토리<br>11 적용시스템 디<br>/pgm_hostname |
| 중단 | ApplicationLogArea<br>Data Area<br>OracleS/W | /pam<br>프로그램 실행 시 발생되는 로그<br>Japlog<br>프로그램실행 Ao 출력되는파일<br>/feeraie<br>aa<br>오라클 데이터베이스 엔진<br>/nhod | /aplog_hostname<br>/userdir_hostname |
| 중하단 | 서버보'<br>안<br>서<br>버운영관리<br>\|<br>작업관리 | 서 버 보 안(56046TOS) 설치 디렉토리<br>/usr/local/TOS<br>fea<br>서버운영 관리 설치 디렉토리<br>배치작업 관리 설치 디렉토리<br>/seba<br>SMS(HPOvenview) Agent설치 디렉토 | - |
| 하단 | 15<br>\|: | /SMS<br>통합관제(60000)Agent설지 디렉토리<br>/7914170000000 | - |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `AP` | 상중단/좌측 | AP, DB 서버의 디렉토리는 크게 3가지로 분류한다. |
| `AP` | 중단/좌측 | ApplicationLogArea |
| `AP` | 중단/중앙 | Japlog |
| `DB` | 상중단/좌측 | AP, DB 서버의 디렉토리는 크게 3가지로 분류한다. |
| `Oracle` | 중단/좌측 | OracleS/W |
| `배치` | 중하단/중앙 | 배치작업 관리 설치 디렉토리 |
| `파일` | 중단/중앙 | 프로그램실행 Ao 출력되는파일 |
| `Application` | 중단/좌측 | ApplicationLogArea |
| `서버` | 상중단/좌측 | AP, DB 서버의 디렉토리는 크게 3가지로 분류한다. |
| `서버` | 중하단/좌측 | 서버보' |
| `서버` | 중하단/중앙 | 서버운영 관리 설치 디렉토리 |

## 3. 이미지에서 확인되는 핵심 내용

- Program Area, Application Log Area, Data Area, Oracle S/W, 서비스/배치/솔루션 디렉터리 등으로 크게 분류된다.
- 가이드 디렉터리와 HA 시스템 디렉터리를 별도 열로 제시한다.
- `/pgm`, `/aplog`, `/userdata` 등 경로가 일부 보인다.

## 4. 아키텍처 분석

- 프로그램·로그·데이터·솔루션 영역을 분리하여 배포, 용량관리, 백업, 장애복구를 독립적으로 수행하려는 파일시스템 표준이다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `AP`, `DB`, `Oracle`, `배치`, `파일`, `Application`, `서버`
- 이 장표는 **시스템 표준** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 장표의 상자·선·배치 관계는 아래 `그림 → TEXT 표`에서 위치 기반으로 재구성했으며, 연결 방향이 불명확한 경우 임의로 보완하지 않았다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `38%` — fea
  - `40%` — /feeraie
  - `42%` — /7914170000000
  - `49%` — /usr/local/TOS
  - `53%` — /pam

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `시스템 표준` |
| Source Key | `85737/52.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

