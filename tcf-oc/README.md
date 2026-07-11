# tcf-oc — 용량 산정 (CAP-010~050)

`eb-service` 패키지 구조를 따른 **tcf-oc** (Operation Capacity) 업무 모듈입니다.

## 계층 (eb-service 대응)

| eb-service | tcf-oc | 예시 |
|------------|--------|------|
| entry/handler | entry/handler | `OcHelloHandler` (TCF Online) |
| entry/facade | entry/facade | `OcHelloFacade` |
| — | entry/controller | `ACMSC71001`, `ACMSC72001` (REST) |
| application/service | application/service | `ASMSC71001`, `ASMSC72001`, `DCCapacity` |
| application/rule | application/rule | `CapacityCDtoConverter`, `OcHelloRule` |
| application/dto | application/dto/capacity | `CapacityCalculationCDTO`, … |
| persistence | persistence | `OcHelloDao`, `OcHelloMapper` |
| support | support | `CapacityCalcStep`, `VmProfile`, `OcCapacityBizException` |

## URL

| 구분 | URL |
|------|-----|
| 화면 (tcf-ui) | `/oc/plan.html` |
| API | `GET /api/oc/capacity/defaults` |
| API | `POST /api/oc/capacity/calculate` |
| API | `POST /api/oc/capacity/calculate-step` |
| API | `POST /api/oc/capacity/was-thread/calculate` |
| TCF Online | `POST /oc/online` — `OC.Hello.inquiry` |

## 실행

```bash
gradle :tcf-oc:bootRun
```

포트 **8094** · WAR `oc.war` · 업무코드 **OC**
