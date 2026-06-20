# NSIGHT TCF Framework

NSIGHT HTTP/JSON 표준 전문 처리 구조를 **TCF(Transaction Control Framework)** 중심으로 구성한 멀티 모듈 Gradle 프로젝트입니다.

## 모듈 구조

```text
nsight-tcf-framework
├─ tcf-util              공통 유틸 (Spring 없음)
├─ tcf-core              TCF 엔진 (STF/TCF/ETF, Dispatcher)
├─ tcf-web               HTTP 레이어 (/online, TcfGateway)
├─ common-etc            공통 ETC 라이브러리 (ET)
├─ tcf-om                운영관리 + 파일 업·다운로드 (OM/UD, bootRun)
├─ tcf-ui                거래 테스트 UI (Relay, 8099)
├─ tcf-scripts           빌드·실행 스크립트
├─ cc-service … mg-service   17개 업무 WAR
└─ om-service            레거시 OM WAR
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
tcf-util → tcf-core → tcf-web → 업무 서비스 / tcf-om
```

## 빠른 시작

```bash
# SV 업무 단독 실행
gradle :sv-service:bootRun

# 테스트 UI
gradle :tcf-ui:bootRun

# 17개 WAR 일괄 빌드
gradle buildBusinessWars

# 스크립트 사용 (Windows)
tcf-scripts\run-local.bat sv
tcf-scripts\run-local.bat ui
```

## 포트 요약

| 포트 | 모듈 |
|------|------|
| 8081–8096 | cc ~ mg (*-service) |
| 8097 | tcf-om / om-service (동시 기동 불가, UD 파일 API 내장) |
| 8099 | tcf-ui |

## 샘플 호출

```bash
curl -X POST http://localhost:8086/sv/online \
  -H "Content-Type: application/json" \
  -d @docs/sample-requests/sv-sample-inquiry.json
```

## 요구 사항

- Java 21
- Gradle 8.x
