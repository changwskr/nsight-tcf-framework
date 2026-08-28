# 대외 EAI 유형별 처리 원칙 — 통합 아키텍처

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_36.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

대외 인터페이스 통합 아키텍처는 전문 송수신을 위한 Front/Back-End 어댑터와 전문처리의 핵심인 Interface Flow로 구성.

- **업무기능 컴포넌트:** 전문 채번, Timeout 처리, 결번 처리, 재처리
- **공통기능 컴포넌트:** 전문 변환, Code 변환, 전문 Tracking, Timer
- **부가기능 컴포넌트:** 보안, 시뮬레이터

※ Conceptual Architecture. Vendor 솔루션에 따라 상이할 수 있음.

## TEXT 구성도

```text
상품서비스(여신지원·후처리·지급결제·상품서비스·대행/제휴)
        │ 요청/응답
  [Front-End Adapter] HTTPS, TCP/IP, SFTP, XML
        │
  Interface Flow: Parsing → Formatting → Mapping → Routing
        │ 업무기능 / 공통기능 / 부가기능
  [Back-End Adapter]
        │
 대외기관(금결원·은행연합회·카드사·은행·계열사)
```

- 원문 페이지: 28
