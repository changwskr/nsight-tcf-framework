# 은행내 Technical Architecture 주요 변화 방향성

> 원본: `원본/CamScanner 2026. 08. 27. 19.36_3.pdf`  
> 문서: KB국민은행 · Enterprise Interface Architecture 정의서 · 원문 전사(FACT)  
> 비고: 원문 장표 페이지 4. 동일 장표가 `_4`~`_7` PDF에도 중복 스캔됨.

## 본문

**우측 태그:** 주요 변화 요인 분석

다양한 시스템 간 인터페이스 지원의 강화, 신규 Biz. 및 신기술 기반 환경 변화에 신속하고 유연한 인프라 지원, Biz. 관점 통합관제와 테스트 관리 등 IT품질과 생산성 개선을 추진함

### As-Is 기술 아키텍처

**Biz. 영향도 관리**
- 비즈니스 서비스 관점의 영향도 관리 제약
- 서비스 장애 예측, 선제적 감지 한계
- 변경 전후 테스트 제약으로 인한 장애 가능성 상존

**시스템 간 I/F**
- 이기종 간의 파일, 데이터 연계 편의성, 호환성 부족
- 실시간 연계 요건 충족 제한적
- 신기술 변화 대응 필요

**IT인프라 유연성**
- 업무 별/프로젝트 별 Dedicated 자원 구매, 할당
- IT자원의 신속, 유연한 회수, 재분배 제약

### TA Transformation 방향성

**Biz. 통합 관제**
- 비즈니스 서비스 실시간 통합 관제
- Biz. 서비스 영향도 / Real-time IT 현황

**Biz. 지원 및 연계**
- AI, Big Data 분석, Open API, 실시간 마케팅 등 Biz. 지원 역량 강화
- 전행 통합 I/F 효율성, API 관리 유연성
- 구성요소: API · MCI · API관리 · EAI · WAS-Java · F/W
- 채널 및 대내외 시스템 연계 (파트너사, 그룹사, SNS 등)

**인프라 유연성**
- E2E 검증용 Staging 인프라 및 테스트 자동화 확대
- 개발 → Staging → 운영 → DR
- Cloud IT: 클라우드 인프라 적용 (X86/Linux, SDN)
- Traditional IT: 가상화 활용 확대 (Unix)

### To-Be 기술 아키텍처

**1. Time-To-Market**
- 상용 F/W 기반 실시간 온라인 처리 확대
- 대내외 시스템, 채널 간 유연한 연계 (Web, SOAP, https 등)
- 표준 전문, API 통합 관리 및 재활용 확대 (중복개발 배제)
- 전행 파일 I/F 통합관리
- Biz. 변화에 신속, 유연한 가상화, 클라우드 기반 인프라 제공 확대

**2. 대고객 서비스 품질 강화**
- IT-Biz. 영향도 관리 강화 및 Biz. 관점 지표의 선제적 모니터링
- 잦은 Biz. 변화 대응을 위한 IT 테스트 품질강화, 자동화

## TEXT 구성도

```text
[As-Is]                    [TA Transformation]                         [To-Be]
Biz.영향도 관리 제약  ──►  Biz.통합관제 (서비스영향도 / Real-time IT)  ──►  Time-To-Market
시스템 간 I/F 제약    ──►  MCI / API관리 / EAI + WAS-Java / F/W      ──►  표준전문·API·파일I/F
IT인프라 유연성 제약  ──►  개발→Staging→운영→DR / Cloud+Unix가상화   ──►  대고객 서비스 품질 강화
```

- 원문 페이지: 4
