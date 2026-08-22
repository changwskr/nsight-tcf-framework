# Wave 5 — 실제 증적 요청 목록

## A. Human Approval

우선 입력 대상은 `98BJ-HUMAN-DECISION-CHECKLIST.md`의 16개 P0 ADR이다. 승인 시 `98BQ-evidence-inbox/adr-approvals/`에 ADR별 JSON을 둔다.

## B. 1차 Runtime

| 순서 | Run | 필수 핵심 증적 |
|---:|---|---|
| 1 | RUN-TIMEOUT | DB before/after, TX log, Hikari metric, worker/context metric |
| 2 | RUN-P600 | JTL/summary, JVM/Tomcat/Hikari/DB metric, run log |
| 3 | RUN-P1200 | JTL/summary, JVM/Tomcat/Hikari/DB metric, run log |

## C. Production Config

Host별로 실제 운영 위치에서 수집한다.

```text
WEB  : httpd.conf, conf.d/*, balancer/vhost
WAS  : server.xml, setenv.sh, CATALINA_BASE, webapps/manifest
APP  : application-prod.yml/properties
NET  : L4/GSLB routing evidence
DB   : datasource target/session ceiling evidence
```

## D. 후속 Runtime

`RUN-S1800`, `RUN-HIKARI`, `RUN-SLOWSQL`, `RUN-N1`, `RUN-SESSION`, `RUN-CF`, `RUN-TRACE`, `RUN-ROLLING`, `RUN-JWT-ROTATE`.

## E. Closure Evidence

각 P0를 닫을 때 `item_id/status/approver/decision_date/evidence_ref`를 포함한 Closure Record를 `98BQ-evidence-inbox/closure/`에 둔다.
