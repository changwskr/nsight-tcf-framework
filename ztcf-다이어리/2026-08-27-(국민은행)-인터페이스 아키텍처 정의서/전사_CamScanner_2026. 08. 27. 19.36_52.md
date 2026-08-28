# To-Be TA H/W 구성도 — DR센터 (Staging)

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_52.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)

## 본문

**위치:** V. 시스템 구성도 → 3. H/W 구성도

범례: 신규구축(회색) / 재구축(흰 테두리) / Staging 구축 비대상(사선)

| 영역 | 구성 (노드) |
|------|-------------|
| MCI | 단말AP 4, 인터넷AP 4, 자동화기기AP 4, 콜센터AP 2, DB 2, 관리운영AP 2 |
| 전행표준전문관리 | AP 1, DB 1 |
| 마케팅 플랫폼 | AP 2, DB 2 |
| 고객통지 | 메시지AP 2, 대량메일AP 2, Push AP 2, 보안메일AP 2, SMS/Mail DB 2 |
| 금융복합 | AP 4, DB 4, 통합로그DB 2 |
| 상품처리 | AP 4, DB 4, 배치AP 2, 배치DB 2 |
| 과거거래내역조회 DB | 2 |
| 고객정보분리보관 DB | 2 |
| 여신배치 | AP 2 |
| 프로세스 자동화 | AP 2, DB 2 |
| 데이터 Hub | 빅데이터 분석 / 실시간분석 Appliance / 통합분석 ADW |
| BI Portal | AP 2 |
| 대내 EAI | DMZ AP 2, 내부AP 4, 대외AP 4, 유통망AP 4, 일괄전송AP 4, 내부DB 2, 대외DB 2, 유통망DB 2, 일괄전송DB 1 |
| IT 내부관리 | 모니터링·ITSM·자동화·백업 등 1~2노드 |

- 원문 페이지: 44
