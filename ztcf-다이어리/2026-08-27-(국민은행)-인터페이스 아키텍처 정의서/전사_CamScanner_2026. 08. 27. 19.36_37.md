# 대외 EAI 유형별 처리 원칙 — 주요 기능

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_37.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

대외 EAI의 주요 기능은 Adapter, 전문처리, 라우팅 등의 기능과 업무기능/공통기능/부가기능 컴포넌트로 구성.

| # | 항목 | 세부 | 기능 설명 |
|---|------|------|-----------|
| 1 | Adapter | Standard / Various / Custom | 표준 시스템 연계. Socket, FTP, WebService. 은행 특화 시 Custom Adapter 프레임워크 |
| 2 | 전문 처리 | Async Logging, Parsing, Formatting, Mapping | Async 거래 로깅. Parser로 key-value 추출. 포맷 변환. 내부 엔진용 데이터셋 매핑 |
| 3 | 라우팅 | Protocol Conversion, Content-based Routing, Patterns, Exception | Provider–Consumer 프로토콜 변환. 콘텐츠 기반 동적/정적 라우팅. Sync Req/Res, Async One-to-One 등. 예외 처리 |
| 4 | 업무기능 | 채번, 결번, 재처리, Timeout | Inbound GUID 채번. 파일전송 블록 재요청. Outbound Timeout 시 업무시스템에 취소 전문 |
| 5 | 공통기능 | Tracking, Code 변환, Timer, Simulator | GUID 기준 거래당 최대 4건 로그. 문자셋 변환. Outbound 수신 Timeout 점검. In/Outbound 시뮬레이터 |
| 6 | 부가기능 | 시스템 보안, 암호화 연동, 통신 보안 | 다단계 사용자/관리자 권한. 송수신 데이터 내 고객정보 암복호화. 시스템–서버 통신 보안 |

- 원문 페이지: 29
