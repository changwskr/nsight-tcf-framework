# 대내 채널 통합(MCI) 유형별 처리 원칙 — Built-in Adapter

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_33.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

신규 채널 추가 시 다양한 프로토콜을 지원하는 Built-in Adapter를 제공하고, Hard-coding이 아닌 Parameter 등록 기반으로 신속 적용하여 유연성을 확보함.

채널 프로토콜: TCP/IP, HTTP, SOAP, JMS 등  
MCI Built-in Adapter: TCP/IP, HTTP, SOAP, JMS, RMI/IOP, ebXML 등 / 전문 Format: Flat, XML, Object

### Parameter 설정에 의한 신규 채널 추가 예

| 분류 | 채널정보 Parameter | 설명 |
|------|-------------------|------|
| 채널 기본정보 | 채널 ID | Unique ID |
| | 업무명 | 채널을 사용하는 업무 서비스명 |
| | 입출력 모드 | Bi-directional, Uni-directional 등 |
| | 채널 Layer 구분 | 채널 단계/계층 |
| | 실제 프로그램 명 | 채널 프로그램명 |
| | 채널 설명 | 채널 정보 설명 |
| | 채널 Type | 채널 분류 기준 |
| | Handler | 채널 오류 처리 클래스 |
| | 전문 형태 | 전문 길이, 포맷 등 |
| 채널 기본속성 | Timeout | 최대 대기시간 |
| | Protocol Type | 채널 프로토콜 |
| | 메시지 정보 | 송수신 메시지 Min/Max 길이, Header |
| | 플러그인 정보 | 플러그인 필요 시 |
| | 인코딩 정보 | Encoding |
| 채널 확장속성 | 인스턴스별 정보 | 인스턴스별 Connection·Pool |

1. Built-in Adapter 기반 Plug-in 방식 Adapter 적용
2. 채널 정보 Parameter 설정으로 신규 Adapter 등록·관리 (DB 기반 파라미터 관리)
3. Customer Adapter 개발환경 지원

- 원문 페이지: 25
