# 대내 EAI 유형별 처리 원칙 — 대내 EAI 아키텍처

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_40.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

대내 EAI 구조는 전문의 송수신 처리를 담당하는 Adapter와 전문 변환, 라우팅, 로깅, 데이터 처리 등을 수행하는 엔진으로 구성.

Adapter: TCP / DB / Http  
엔진: 전문 변환, 라우팅, 데이터 처리, 전문 로깅  
관리: 서비스 관리, 전문 관리, 트랜잭션 관리, Common Manager, 모니터링 및 관리  
전문 저장 / Message Queue / 전문 추출

상단 연계: 상품서비스계, 상품서비스 단위업무, 마케팅 Hub계  
하단 연계: 데이터 Hub(정보보호·분석), IT내부 관리계, 정보분석계

※ Conceptual Architecture. Vendor 솔루션에 따라 상이할 수 있음.

- 원문 페이지: 32
