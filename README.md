# NSIGHT TCF Framework

NSIGHT HTTP/JSON 표준 전문 처리 구조를 **TCF(Transaction Control Framework)** 중심으로 구성한 멀티 모듈 Gradle 프로젝트입니다.

## 모듈 구조

```text
nsight-tcf-framework
├─ tcf-util              공통 유틸 (Spring 없음)
├─ tcf-core              TCF 엔진 (STF/TCF/ETF, Dispatcher)
├─ tcf-web               HTTP 레이어 (/online, TcfGateway, 거래로그 DB)
├─ tcf-cache             EhCache / Spring Cache 공통 모듈
├─ tcf-om                운영관리 + 파일 업·다운로드 (OM/UD, bootRun 8097)
├─ tcf-ui                거래 테스트 UI · OM 관리 포털 Relay (8099)
├─ tcf-scripts           빌드·실행·배포 스크립트
├─ cc-service … mg-service   16개 업무 WAR
└─ om-service            레거시 OM WAR (tcf-om 권장)
```

각 모듈 상세는 해당 디렉터리의 `README.md`를 참고하세요.

## TCF 처리 흐름

```text
Client / tcf-ui / REST API
   ↓
OnlineTransactionController / TcfGateway
   ↓
TCF.process()
   ├─ STF.preProcess()           Header 검증, GUID, 세션·권한, 멱등성
   ├─ TransactionDispatcher      serviceId → TransactionHandler
   └─ ETF.success/fail/error     표준 응답, 감사·메트릭
```

## 의존 방향

```text
tcf-util → tcf-core → tcf-web → tcf-cache(선택) → 업무 서비스 / tcf-om
```

## 빠른 시작

```bash
# SV 업무 단독 실행
gradle :sv-service:bootRun

# OM 운영관리 (TCF 마이그레이션본)
gradle :tcf-om:bootRun

# 테스트 UI · OM 관리 포털
gradle :tcf-ui:bootRun

# 17개 WAR 일괄 빌드 (om-service 포함)
gradle buildBusinessWars

# 스크립트 (Windows, 프로젝트 루트)
tcf-scripts\run-local.bat sv
tcf-scripts\run-local.bat tcf-om
tcf-scripts\run-local.bat ui
```

## 포트 요약

| 포트 | 모듈 | 비고 |
|------|------|------|
| 8081–8096 | cc ~ mg (*-service) | 업무별 bootRun |
| 8097 | **tcf-om** / om-service | 동시 기동 불가, UD API tcf-om 내장 |
| 8099 | tcf-ui | Relay · OM admin UI |

## OM 관리 포털 (tcf-ui + tcf-om)

1. `gradle :tcf-om:bootRun` (8097)
2. `gradle :tcf-ui:bootRun` (8099)
3. http://localhost:8099/om/admin/login.html (`admin01` / `nsight01!`)

주요 화면: 대시보드, 거래로그, ServiceId, 사용자/권한, 공통코드, 오류코드, Cache, 세션, 파일 관리 등

## 샘플 호출

```bash
curl -X POST http://localhost:8086/sv/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/sv-sample-inquiry.json
```

## 요구 사항

- Java 21
- Gradle 8.x
