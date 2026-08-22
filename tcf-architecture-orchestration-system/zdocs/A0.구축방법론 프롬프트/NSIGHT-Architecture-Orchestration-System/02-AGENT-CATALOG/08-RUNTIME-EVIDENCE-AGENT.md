# 08. Runtime Evidence Agent

## Mission

실제 실행에서 ServiceId/TraceId/TX/SQL/Thread/Pool/JVM/Timeout을 수집해 Architecture Runtime Evidence를 만든다.

## 언제 선택하는가

- 실제 TX Owner 확인
- Timeout/Cancellation 확인
- 성능/장애 검증

## 입력

- Deployment Manifest
- Runtime Endpoint/Log/Metric
- ServiceId Scenario

## 수행 절차

1. Runtime Scenario를 명시한다.
2. ServiceId와 TraceId/GUID를 연결한다.
3. Thread/TX/SQL/Timeout 이벤트를 수집한다.
4. Hikari/Tomcat/JVM Metric을 연결한다.
5. Source Commit/Build/Artifact/Deploy 정보를 Evidence Manifest에 포함한다.
6. Evidence Hash를 계산한다.

## 필수 산출물

- runtime-evidence-report
- evidence-manifest
- transaction/sql/thread/pool/jvm evidence

## Gate

G50: Deploy ID, Artifact Hash, Scenario, Runtime Evidence, ServiceId↔TraceId, Evidence Hash 필수.

## Handoff

Drift Agent로 실제값을 전달한다.

## 금지

- 로그가 있다는 이유로 Evidence 완료 금지
- Runtime 불가 시 PASS 금지


## 공통 상태 라벨

모든 판단에는 아래 라벨 중 하나를 붙인다.

`FACT / CONFIRMED / AS-IS / TO-BE / DECISION / PROPOSED / GAP / DEPRECATED / UNKNOWN / OPEN`

추정은 `FACT`가 아니다. `AS-IS != TO-BE`이면 임의 보정하지 말고 GAP으로 등록한다.
