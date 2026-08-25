# 제28장 SOURCE 검증 메모

## 우선 근거
1. `book/source/성능감시.md`
2. `book/chapter/28장.성능 감시와 운영 진단.md`
3. Timeout/Transaction/ImageLog/GUID 이전 장 재분석

## 현재 사실
- 로그 기반 구간 추적 중심.
- TransactionContext nanoTime/elapsed.
- MybatisLogInterceptor sqlId/parameter/elapsed debug + finally cleanup.
- DefaultOnlineTimeoutExecutor timeout/interrupt/overload elapsed.
- GUID/MDC Request↔Worker 연결.
- MgActiveTransactionRegistry single-JVM memory snapshot.
- currentStep=RUNNING 고정.
- MgRuntimeMonitor JVM/Tomcat/Hikari/Active TX snapshot.
- mgcoa9100Service는 단일 JVM 운영정보 조립.
- slowTransactions/slowSql는 빈 목록/0으로 채워지는 미수집 상태.
- Tomcat JMX 실패 시 estimated 가능.
- Hikari unwrap 실패 시 Pool 목록 empty 가능.
- GC last-minute는 정교한 sliding window가 아님.

## 현재로 단정하지 않음
- Actuator/Micrometer/Prometheus.
- distributed tracing/APM.
- Service ID별 장기 p95/p99 current dashboard.
- Slow SQL/Slow TX registry.
- multi-instance aggregate dashboard.

## 핵심 운영판정
- empty != measured zero
- estimated != measured
- timeout response != worker end
- SQL elapsed != connection acquire time
- Service elapsed != physical transaction duration
- GUID = log/trace correlation, not metric label
