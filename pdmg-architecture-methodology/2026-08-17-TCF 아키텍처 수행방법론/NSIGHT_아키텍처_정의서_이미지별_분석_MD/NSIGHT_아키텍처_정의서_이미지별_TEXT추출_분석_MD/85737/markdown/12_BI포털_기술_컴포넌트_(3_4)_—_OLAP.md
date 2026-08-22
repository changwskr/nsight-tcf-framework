# BI포털 기술 컴포넌트 (3/4) — OLAP

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_57_37 GMT+9.zip` |
| 원본 이미지 | `12.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **기술 컴포넌트** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **78.8%** |
| 원본 이미지 키 | `85737/12.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
BI포털 기술 컴포넌트 (3/4)
BusimessSe
OLAPApplication Service
Web Service
CAMSAgi
JDBC Driver
ODBC 00060
Java Framewor!
eCAMS Agent
JavaFramework Engine
WebApplicatior
Web Application Server
Web Server
Me
a
JVM
서베백신
서버 백신
계정 관리
서버 백신
계정 관리
서버 보안
개인정보
서버 보안
개인정보
서버보안
백업 관리
인프라 통합관제
백업 관리
앤프라 통합관제
백업관리
서버 모니터링
서뻐운영관리
서버운명관리
서뼈DUES)
서버 모니터링
05
=
=
_ 클끌쓰- 그
SS|
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | BI포털 기술 컴포넌트 (3/4) | - | - |
| 상중단 | - | - | - |
| 중단 | OLAPApplication Service<br>ODBC 00060<br>JavaFramework Engine | Web Service<br>eCAMS Agent | BusimessSe<br>CAMSAgi<br>JDBC Driver<br>Java Framewor! |
| 중하단 | Web Application Server<br>a<br>서베백신<br>계정 관리<br>개인정보<br>서버보안 | Web Server<br>JVM<br>서버 백신<br>서버 보안 | WebApplicatior<br>Me<br>서버 백신<br>계정 관리<br>서버 보안<br>개인정보 |
| 하단 | 앤프라 통합관제<br>백업관리<br>서뻐운영관리<br>서뼈DUES)<br>_ 클끌쓰- 그<br>SS\| | 백업 관리<br>서버 모니터링<br>05<br>=<br>= | 백업 관리<br>인프라 통합관제<br>서버 모니터링<br>서버운명관리 |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `WEB` | 중단/중앙 | Web Service |
| `WEB` | 중하단/우측 | WebApplicatior |
| `WEB` | 중하단/좌측 | Web Application Server |
| `AP` | 중단/좌측 | OLAPApplication Service |
| `AP` | 중하단/우측 | WebApplicatior |
| `AP` | 중하단/좌측 | Web Application Server |
| `DB` | 중단/우측 | JDBC Driver |
| `DB` | 중단/좌측 | ODBC 00060 |
| `Framework` | 중단/좌측 | JavaFramework Engine |
| `OLAP` | 중단/좌측 | OLAPApplication Service |
| `BI포털` | 상단/좌측 | BI포털 기술 컴포넌트 (3/4) |
| `Application` | 중단/좌측 | OLAPApplication Service |
| `Application` | 중하단/좌측 | Web Application Server |
| `서버` | 중하단/우측 | 서버 백신 |
| `서버` | 중하단/우측 | 서버 보안 |
| `서버` | 중하단/좌측 | 서버보안 |
| `모니터링` | 하단/우측 | 서버 모니터링 |
| `백업` | 하단/우측 | 백업 관리 |
| `백업` | 하단/좌측 | 백업관리 |

## 3. 이미지에서 확인되는 핵심 내용

- OLAP AP, OLAP WEB, OLAP WAS가 별도 컬럼으로 구성된다.
- OLAP AP에는 OLAP Application Service, JDBC Driver, Java Framework Engine, Web Application Server/JVM 등이 보인다.

## 4. 아키텍처 분석

- OLAP 엔진/서비스와 사용자 WEB/WAS를 분리하여 분석처리 부하와 화면 부하를 격리한다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `WEB`, `AP`, `DB`, `Framework`, `OLAP`, `BI포털`, `Application`, `서버`, `모니터링`, `백업`
- 이 장표는 **기술 컴포넌트** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 텍스트/표 중심 장표로 판단되며, 원문 항목을 그대로 추출한 뒤 구조적 의미를 분석하였다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `40%` — SS|
  - `52%` — _ 클끌쓰- 그
  - `53%` — CAMSAgi

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `기술 컴포넌트` |
| Source Key | `85737/12.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

