# NSIGHT TCF Framework - tcf-core / tcf-web / tcf-util 재구성본

본 프로젝트는 NSIGHT HTTP/JSON 표준 전문 처리 구조를 **TCF 중심 프레임워크**로 구성한 전체 소스입니다.

## 1. 핵심 변경 사항

| 기존 모듈 | 변경 모듈 | 설명 |
|---|---|---|
| `common-core` | `tcf-core` | 표준 전문, 거래 Context, 공통 예외 |
| `common-web` | `tcf-web` | TCF/STF/ETF, Dispatcher, Controller, Filter |
| 신규 | `tcf-util` | `tcf-core`, `tcf-web`에서 사용하는 공통 Util |

## 2. 전체 모듈 구조

```text
nsight-tcf-framework-tcfmodules
├─ tcf-util
├─ tcf-core
├─ tcf-web
├─ common-etc
├─ common-updownload
├─ tcf-ui
├─ cc-service
├─ ic-service
├─ pc-service
├─ bc-service
├─ ms-service
├─ sv-service
├─ pd-service
├─ cm-service
├─ eb-service
├─ ep-service
├─ bp-service
├─ bd-service
├─ ss-service
├─ cs-service
├─ ct-service
├─ mg-service
├─ om-service
├─ deploy/apache
├─ docs
└─ scripts
```

## 3. TCF 처리 흐름

```text
Client / WebTopSuite
   ↓
OnlineTransactionController
   ↓
TCF.process()
   ├─ STF.preProcess()
   │   ├─ Header 검증
   │   ├─ GUID / TraceId 생성
   │   ├─ Session / Authorization 검증
   │   ├─ Idempotency 확인
   │   └─ 거래 시작 로그
   ├─ TransactionDispatcher.dispatch()
   │   └─ serviceId 기준 TransactionHandler 선택
   └─ ETF.success() / ETF.businessFail() / ETF.systemError()
       ├─ 표준 응답 조립
       ├─ 거래 종료 로그
       ├─ 감사 로그
       └─ 메트릭 기록
```

## 4. 먼저 볼 소스

| 순서 | 파일 |
|---:|---|
| 1 | `tcf-core/src/main/java/com/nh/nsight/tcf/core/message/StandardRequest.java` |
| 2 | `tcf-core/src/main/java/com/nh/nsight/tcf/core/message/StandardResponse.java` |
| 3 | `tcf-web/src/main/java/com/nh/nsight/tcf/web/processor/TCF.java` |
| 4 | `tcf-web/src/main/java/com/nh/nsight/tcf/web/processor/STF.java` |
| 5 | `tcf-web/src/main/java/com/nh/nsight/tcf/web/processor/ETF.java` |
| 6 | `tcf-web/src/main/java/com/nh/nsight/tcf/web/dispatch/TransactionDispatcher.java` |
| 7 | `sv-service/src/main/java/com/nh/nsight/marketing/sv/handler/SvSampleInquiryHandler.java` |

## 5. 빌드

```bash
gradle clean buildBusinessWars
```

## 6. SV 업무 단독 실행

```bash
gradle :sv-service:bootRun
```

## 7. 샘플 호출

```bash
curl -X POST http://localhost:8086/sv/online \
  -H 'Content-Type: application/json' \
  -d @docs/sample-requests/sv-sample-inquiry.json
```

## 8. 의존성 기준

```text
tcf-util
   ↑
tcf-core
   ↑
tcf-web
   ↑
17개 업무 서비스
```

`tcf-util`은 Spring 의존성이 없는 순수 유틸리티 모듈로 유지합니다.
