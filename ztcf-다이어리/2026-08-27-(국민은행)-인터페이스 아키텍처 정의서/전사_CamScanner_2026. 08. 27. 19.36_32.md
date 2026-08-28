# 대내 채널 통합(MCI) 유형별 처리 원칙 — Service Composition

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_32.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

MCI는 Service Composition 기능을 기반으로 순차거래, 조건순차거래, 병렬거래 등을 지원함.

### Service Composition을 통한 전문 통합
채널 통합 UI → MCI(입력전문 A,B,C → 전문변환 → 전문관리 A~F) → 상품서비스(코어뱅킹 A,B,C / CRM D,E,F)

- **순차 거래:** 시작 → 마이포인트 조회 → 한도 조회 → 종료
- **조건 순차거래:** 시작 → 고객정보 조회 → 정상? → (Y) 실적 조회 → 종료
- **병렬 거래:** 시작 → 분기 → 결제금액 조회 / 한도 조회 → 조합 → 종료

### MCI 주요 기능 설명
- 다양한 채널의 거래 생성 시 Back-End 업무 서비스의 변화 없는 구조
- Gateway를 넘어 채널–업무시스템 간 통합서비스 제공자 역할 강화
- 채널 서비스 연계 필수 기능·정보 관리로 Back-End 불필요 호출 최소화
- 전문 매핑/전환 툴 및 Flow Designer 필요
- Visualization Flow Design으로 프로세스 모델/변경/적용을 실시간 수행
- 응답속도가 중요한 거래는 Service Composition 성능 영향도 고려 필요

- 원문 페이지: 24
