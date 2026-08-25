# BI포털 기술 컴포넌트 (3/4) — OLAP

## 0. 원본 정보

- 원본 ZIP: `비디오 프레임 추출기 2026-08-19 8_57_37 GMT+9.zip`
- 원본 이미지: `12.png`
- 분석 분류: **기술 컴포넌트**
- 판정 상태: **분석완료**
- 분석 원칙: 화면에서 식별 가능한 내용만 근거로 정리하며, 작은 글씨나 불명확한 수치는 추정하지 않는다.

## 1. 이미지에서 확인되는 핵심 내용

- OLAP AP, OLAP WEB, OLAP WAS가 별도 컬럼으로 구성된다.
- OLAP AP에는 OLAP Application Service, JDBC Driver, Java Framework Engine, Web Application Server/JVM 등이 보인다.

## 2. 아키텍처 해석

- OLAP 엔진/서비스와 사용자 WEB/WAS를 분리하여 분석처리 부하와 화면 부하를 격리한다.

## 3. 설계상 시사점

- 이 장표는 상위/하위 아키텍처 항목과 함께 읽어야 하며, 단독으로 확정 기준을 만들기보다 해당 영역의 근거 장표로 관리하는 것이 적절하다.

## 4. 확인 필요 사항

- 화면 해상도로 판독이 어려운 세부 수치·URL·Hostname·버전은 원본 문서 또는 원본 이미지 확대본과 대조한다.

## 5. 아키텍처 증적 분류

- Evidence Type: `IMAGE_FRAME`
- Domain: `기술 컴포넌트`
- Source Key: `85737/12.png`
- Status: `분석완료`
