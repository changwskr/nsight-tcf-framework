# NSIGHT TCF Framework Source Index

## 1. TCF 공통 모듈

| 영역 | 위치 |
|---|---|
| Util | `tcf-util/src/main/java/com/nh/nsight/tcf/util` |
| 표준 전문 | `tcf-core/src/main/java/com/nh/nsight/tcf/core/message` |
| 거래 Context | `tcf-core/src/main/java/com/nh/nsight/tcf/core/context` |
| 오류/예외 | `tcf-core/src/main/java/com/nh/nsight/tcf/core/error` |
| TCF | `tcf-web/src/main/java/com/nh/nsight/tcf/web/processor/TCF.java` |
| STF | `tcf-web/src/main/java/com/nh/nsight/tcf/web/processor/STF.java` |
| ETF | `tcf-web/src/main/java/com/nh/nsight/tcf/web/processor/ETF.java` |
| Dispatcher | `tcf-web/src/main/java/com/nh/nsight/tcf/web/dispatch/TransactionDispatcher.java` |
| Handler Interface | `tcf-web/src/main/java/com/nh/nsight/tcf/web/transaction/TransactionHandler.java` |
| Controller | `tcf-web/src/main/java/com/nh/nsight/tcf/web/controller/OnlineTransactionController.java` |

## 2. 업무 샘플

| 업무 | 대표 Handler |
|---|---|
| SV | `sv-service/src/main/java/com/nh/nsight/marketing/sv/handler/SvSampleInquiryHandler.java` |
| CM | `cm-service/src/main/java/com/nh/nsight/marketing/cm/handler/CmSampleInquiryHandler.java` |
| MG | `mg-service/src/main/java/com/nh/nsight/marketing/mg/handler/MgSampleInquiryHandler.java` |
| OM | `om-service/src/main/java/com/nh/nsight/marketing/om/handler/OmSampleInquiryHandler.java` |

## 3. 실행 흐름

```text
OnlineTransactionController → TCF → STF → TransactionDispatcher → TransactionHandler → ETF
```
