# BI포털 기술 컴포넌트 (4/4) — Self-BI

## 0. 원본 이미지 및 분석 기준

| 항목 | 내용 |
|---|---|
| 원본 ZIP | `비디오 프레임 추출기 2026-08-19 8_57_37 GMT+9.zip` |
| 원본 이미지 | `13.png` |
| 원본 해상도 | `1920 × 1080` |
| 분석 분류 | **기술 컴포넌트** |
| 판정 상태 | **분석완료** |
| OCR 평균 신뢰도 | **85.0%** |
| 원본 이미지 키 | `85737/13.png` |

> **분석 원칙**: 이미지에서 실제로 보이는 텍스트와 배치만 근거로 전사한다. OCR이 불명확한 문자열은 임의로 일반 지식으로 보정하지 않으며, 그림/구성도는 **TEXT 표**로 재구성한다.

## 1. 이미지 전체 텍스트 추출

```text
BI포털 기술 컴포넌트 (4/4)
Ke
Business Ser
Self-BlApplicationService
Web Service
eCAMSAge
JDBC Driver
JDBC Driver
APM Agent
Java Framework
Java Framework Engine
eCAMSAgent
WebApplicatior
Web Server
WebApplication Server
EE크로리
JVM)
서버 백신
계정 관리
서버 백신
서버 백신
계정 관리
개인정보
서버 보안
개인정보
서버 보안
서버 보안
백업 관리
0
인프라 통합관제
백업 관리
백업 관리
인프라 통합관제
서버 모니터링
서버운영관리
서버 모니터링
서버운영관리
서버 모니터링
|
05
ey
를 를
er
id
```

## 2. 그림/구성도 → TEXT 표 변환

### 2.1 화면 배치 기반 TEXT 표

| 화면 위치 | 좌측 | 중앙 | 우측 |
|---|---|---|---|
| 상단 | BI포털 기술 컴포넌트 (4/4) | - | - |
| 상중단 | - | Ke | - |
| 중단 | Self-BlApplicationService<br>JDBC Driver<br>APM Agent<br>Java Framework Engine | Web Service<br>eCAMSAgent | Business Ser<br>eCAMSAge<br>JDBC Driver<br>Java Framework |
| 중하단 | WebApplication Server<br>EE크로리<br>서버 백신<br>계정 관리<br>개인정보<br>서버 보안 | Web Server<br>JVM)<br>서버 백신<br>서버 보안 | WebApplicatior<br>서버 백신<br>계정 관리<br>개인정보<br>서버 보안 |
| 하단 | 백업 관리<br>인프라 통합관제<br>서버운영관리<br>서버 모니터링<br>\| | 백업 관리<br>서버 모니터링<br>05<br>ey<br>를 를<br>er<br>id | 백업 관리<br>0<br>인프라 통합관제<br>서버 모니터링<br>서버운영관리 |

### 2.2 아키텍처 구성요소 TEXT 표

| 구성요소/키워드 | 이미지상 위치 | 이미지에서 읽힌 문맥 |
|---|---|---|
| `WEB` | 중단/중앙 | Web Service |
| `WEB` | 중하단/우측 | WebApplicatior |
| `WEB` | 중하단/중앙 | Web Server |
| `AP` | 중단/좌측 | Self-BlApplicationService |
| `AP` | 중단/좌측 | APM Agent |
| `AP` | 중하단/우측 | WebApplicatior |
| `DB` | 중단/우측 | JDBC Driver |
| `Framework` | 중단/우측 | Java Framework |
| `Framework` | 중단/좌측 | Java Framework Engine |
| `BI포털` | 상단/좌측 | BI포털 기술 컴포넌트 (4/4) |
| `Application` | 중단/좌측 | Self-BlApplicationService |
| `Application` | 중하단/좌측 | WebApplication Server |
| `서버` | 중하단/우측 | 서버 백신 |
| `서버` | 중하단/우측 | 서버 보안 |
| `서버` | 하단/우측 | 서버 모니터링 |
| `모니터링` | 하단/우측 | 서버 모니터링 |
| `백업` | 하단/우측 | 백업 관리 |

## 3. 이미지에서 확인되는 핵심 내용

- Self-BI AP, Self-BI WEB, Self-BI WAS의 3계층이 표시된다.
- AP/WAS는 Java/JDBC/APM 기반, WEB는 Web Server 기반 공통 스택을 사용한다.

## 4. 아키텍처 분석

- Self-BI를 독립 AP까지 분리하여 사용자 주도 분석 워크로드를 일반 BI 서비스와 격리하는 구성이다.

### 4.1 이미지 기반 구조적 해석

- 이미지에서 식별되는 주요 구성요소/키워드: `WEB`, `AP`, `DB`, `Framework`, `BI포털`, `Application`, `서버`, `모니터링`, `백업`
- 이 장표는 **기술 컴포넌트** 영역의 기준/구성/흐름을 설명하는 증적 이미지로 분류된다.
- 텍스트/표 중심 장표로 판단되며, 원문 항목을 그대로 추출한 뒤 구조적 의미를 분석하였다.

## 5. 설계·운영상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 6. 판독 및 검증 필요사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

- 다음 줄은 OCR 신뢰도가 낮아 원본 이미지 확대 확인이 필요하다:
  - `22%` — 를 를
  - `23%` — EE크로리

## 7. Architecture Evidence

| 항목 | 값 |
|---|---|
| Evidence Type | `IMAGE_FRAME_TEXT_EXTRACTED` |
| Domain | `기술 컴포넌트` |
| Source Key | `85737/13.png` |
| Status | `분석완료` |
| Text Reconstruction | `OCR + 좌표 기반 TEXT 표` |

