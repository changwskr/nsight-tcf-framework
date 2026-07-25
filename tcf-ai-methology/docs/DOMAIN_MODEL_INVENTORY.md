# NSIGHT 업무모델 인벤토리 (코드 분석 기반)

생성일: 2026-07-25 · 총 **41**건

출처: `*-service` / `tcf-om` Handler의 ServiceId + `schema.sql` 컬럼.

| BC | 모듈 | 건수 | 대표 ServiceId |
|----|------|------|----------------|
| EB | eb-service | 6 | `EB.User.inquiry`, `EB.User.create`, `EB.Event.inquiry` |
| EP | ep-service | 3 | `EP.UserEvent.inquiry`, `EP.UserEvent.receive`, `EP.Sample.inquiry` |
| IC | ic-service | 2 | `IC.Customer.inquiry`, `IC.Sample.inquiry` |
| MG | mg-service | 1 | `MG.Sample.inquiry` |
| MS | ms-service | 1 | `MS.Sample.inquiry` |
| OM | tcf-om | 22 | `OM.User.inquiry`, `OM.User.detail`, `OM.User.save` |
| PC | pc-service | 1 | `PC.Sample.inquiry` |
| PD | pd-service | 1 | `PD.Sample.inquiry` |
| SS | ss-service | 1 | `SS.Sample.inquiry` |
| SV | sv-service | 3 | `SV.Customer.selectSummary`, `SV.Sample.inquiry`, `SV.Integration.icSample` |

## 전체 ServiceId

- `SV.Customer.selectSummary` · SELECT_ONE · `SV_CUSTOMER` · SV-CUS-0001
- `SV.Sample.inquiry` · SELECT_LIST · `SV_SAMPLE` · SV-SMP-0001
- `SV.Integration.icSample` · SELECT_ONE · `SV_INTEGRATION_LOG` · SV-INT-0001
- `IC.Customer.inquiry` · SELECT_LIST · `IC_CUSTOMER` · IC-CUS-0001
- `IC.Sample.inquiry` · SELECT_LIST · `IC_SAMPLE` · IC-SMP-0001
- `EB.User.inquiry` · SELECT_LIST · `EB_USER` · EB-USR-0001
- `EB.User.create` · INSERT · `EB_USER` · EB-USR-0002
- `EB.Event.inquiry` · SELECT_LIST · `EB_EVENT` · EB-EVT-0001
- `EB.Batch.inquiry` · SELECT_ONE · `EB_EVENT` · EB-BAT-0001
- `EB.SystemTx.inquiry` · SELECT_LIST · `EB_SYSTEM_TX` · EB-STX-0001
- `EB.Sample.inquiry` · SELECT_LIST · `EB_SAMPLE` · EB-SMP-0001
- `EP.UserEvent.inquiry` · SELECT_LIST · `EP_USER_EVENT` · EP-UEV-0001
- `EP.UserEvent.receive` · INSERT · `EP_USER_EVENT` · EP-UEV-0002
- `EP.Sample.inquiry` · SELECT_LIST · `EP_SAMPLE` · EP-SMP-0001
- `PC.Sample.inquiry` · SELECT_LIST · `PC_SAMPLE` · PC-SMP-0001
- `MS.Sample.inquiry` · SELECT_LIST · `MS_SAMPLE` · MS-SMP-0001
- `PD.Sample.inquiry` · SELECT_LIST · `PD_SAMPLE` · PD-SMP-0001
- `SS.Sample.inquiry` · SELECT_LIST · `SS_SAMPLE` · SS-SMP-0001
- `MG.Sample.inquiry` · SELECT_LIST · `MG_SAMPLE` · MG-SMP-0001
- `OM.User.inquiry` · SELECT_LIST · `OM_USER` · OM-USR-0001
- `OM.User.detail` · SELECT_ONE · `OM_USER` · OM-USR-0002
- `OM.User.save` · INSERT · `OM_USER` · OM-USR-0003
- `OM.User.update` · UPDATE · `OM_USER` · OM-USR-0004
- `OM.User.delete` · DELETE · `OM_USER` · OM-USR-0005
- `OM.Menu.inquiry` · SELECT_LIST · `OM_MENU` · OM-MNU-0001
- `OM.AuthGroup.inquiry` · SELECT_LIST · `OM_AUTH_GROUP` · OM-AGR-0001
- `OM.ServiceCatalog.inquiry` · SELECT_LIST · `OM_SERVICE_CATALOG` · OM-SVC-0001
- `OM.CommonCode.inquiry` · SELECT_LIST · `OM_COMMON_CODE` · OM-COD-0001
- `OM.ErrorCode.inquiry` · SELECT_LIST · `OM_ERROR_CODE` · OM-ERR-0001
- `OM.TransactionLog.inquiry` · SELECT_LIST · `TCF_TX_LOG` · OM-TXL-0001
- `OM.AuditLog.inquiry` · SELECT_LIST · `OM_AUDIT_LOG` · OM-AUD-0001
- `OM.Batch.inquiry` · SELECT_LIST · `OM_BATCH_JOB` · OM-BAT-0001
- `OM.Session.inquiry` · SELECT_LIST · `SPRING_SESSION` · OM-SES-0001
- `OM.Auth.login` · INSERT · `OM_USER` · OM-ATH-0001
- `OM.Auth.session` · SELECT_ONE · `SPRING_SESSION` · OM-ATH-0002
- `OM.Dashboard.inquiry` · SELECT_LIST · `OM_AP_STATUS` · OM-DSH-0001
- `OM.SystemConfig.inquiry` · SELECT_LIST · `OM_SYSTEM_CONFIG` · OM-CFG-0001
- `OM.FunctionAuth.inquiry` · SELECT_LIST · `OM_FUNCTION_AUTH` · OM-FNA-0001
- `OM.HealthCheck.inquiry` · SELECT_LIST · `OM_AP_STATUS` · OM-HLT-0001
- `OM.Cache.inquiry` · SELECT_LIST · `OM_CACHE_STATUS` · OM-CCH-0001
- `OM.Sample.inquiry` · SELECT_LIST · `OM_SAMPLE` · OM-SMP-0001

## 재생성

```bash
node tcf-ai-methology/generate-domain-models.js
```

DB 반영: `POST /api/models/reseed`
