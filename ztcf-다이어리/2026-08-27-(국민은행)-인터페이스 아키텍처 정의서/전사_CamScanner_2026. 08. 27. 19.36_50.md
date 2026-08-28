# 전행 인터페이스 아키텍처 개념도

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_50.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

**위치:** V. 시스템 구성도 → 2. 인터페이스 아키텍처 개념도

MCI의 옴니채널 연계 서비스에 의한 채널 인터페이스, EAI에 의한 대내외 인터페이스, 그룹 내외 파트너사와의 Open API 인터페이스 거래 유형으로 분류.

| # | 유형 | 설명 |
|---|------|------|
| 1 | 채널 인터페이스 | 채널–상품처리계. MCI가 직원·고객·거래코드별 채널 요건 처리 |
| 2 | 옴니채널 인터페이스 | 채널–내부시스템 간 MCI 옴니채널 서비스. MCI 전문·Service Composition |
| 3 | 대내외 인터페이스 (Online/Batch/Deferred) | 상품처리–단위업무, 단위업무 간(DB·File I/F 포함). 상품처리–대외기관은 EAI |
| 4 | Open API 인터페이스 (TBD) | Open API 포털·계열사 Open API 거래. Open API Gateway 위치·구현 방식 TBD |

범례: MCI=채널–내부 계정성 거래 / EAI 실선=내부 시스템·파일 / EAI 점선=대외기관

- 원문 페이지: 42
