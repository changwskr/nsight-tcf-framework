# 대내 EAI 유형별 처리 원칙 — AP-AP·File-File·DB-DB

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_39.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

AP-AP 거래는 서비스들이 전문 기반으로 실시간 처리하며, DB-DB 거래와 File-File 거래는 배치(일괄) 처리함.

| # | 유형 | 처리원칙 |
|---|------|----------|
| 1 | Application–Application | 온라인 거래 시 업무 서비스 간 전문 실시간 송수신. Sync, Async, Async-Response. 비기능(성능) 중심 EAI에서 주로 사용 |
| 2 | File–File | 송수신 구간 파일 기반 데이터 전송. 대용량 파일전송 솔루션 기반 |
| 3 | DB–DB | 송신 테이블을 수신 테이블로 전송. 동일 구조 데이터 동기화. CDC(실시간)·ETL(대량배치)과 요건 차별화 필요. EAI는 Biz 테이블이 아닌 인터페이스 전용 테이블 사용 |
| 4 | DB–AP / AP–DB | DB→AP: 테이블 데이터를 전문으로 Application에 전달. AP→DB: 전문 Body를 DB layout에 담아 CUD |

- 원문 페이지: 31
