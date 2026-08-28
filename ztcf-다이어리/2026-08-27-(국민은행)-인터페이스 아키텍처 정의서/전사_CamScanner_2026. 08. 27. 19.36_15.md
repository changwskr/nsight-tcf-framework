# 운영 시스템 구성방안

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_15.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)  
> 워터마크(메타): KB S010781 / 장*용 / 2020-09-15 17:12

## 본문

**우측 상단:** III. 환경 별 시스템 구성방안 / 2. 운영시스템

채널-MCI-상품서비스계 구간의 부하분산은 L4, MCI Web 서버, MCI Instance 등에 의해 다각적인 관점에서 부하분산 아키텍처를 구성함

**소제목:** 운영 인프라 환경 구성방안 – 부하분산

**도식 제목:** 부하분산 방안

1. **L4 부하분산:** 채널 - MCI 구간은 L4 Switch 기반으로 거래 부하를 분산 함
2. **MCI Web 서버 부하분산:** MCI Web - MCI WAS 구간은 MCI Web 서버가 거래의 분산처리를 담당 함
3. **MCI 인스턴스 부하분산:** MCI - 처리계 Web 서버 구간의 거래 부하 처리

1) MCI WAS 인스턴스는 구축단계에서 거래량을 고려하여 구성이 변경될 수 있음

## TEXT 구성도

```text
[채널]          [MCI Web]         [I/F]                      [상품서비스 및 마케팅 Hub]
 직원채널                         MCI #1 (Instance #1)
 고객채널  → L4 →  Web 서버 #1 →   OpenAPI Adapter
 대외채널          Web 서버 #2     인뱅 Adapter
                    ②             모바일 Adapter
                 ①                통합단말 Adapter
                                  MCI #2 (Instance #2)
                                   (동일 Adapter 구성)
                                    ③
                                                             상품서비스계
                                                               상품서비스 | 지급결제 | 여신지원
                                                               대행/제휴  | 상품서비스 | 후처리
                                                             상품서비스계 단위업무
                                                               방카슈랑스 | PPR | ...
                                                             마케팅Hub계
                                                               마케팅 플랫폼 | 금융복합 플랫폼 | 지능형 플랫폼
```

- 원문 페이지: 12
