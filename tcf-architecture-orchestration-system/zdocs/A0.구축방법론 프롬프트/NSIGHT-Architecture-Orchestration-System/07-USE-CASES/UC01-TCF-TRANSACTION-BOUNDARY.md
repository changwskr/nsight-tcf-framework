# UC01 — TCF Transaction Boundary 검증

## Mission
TCF Timeout ON 거래에서 실제 DB Transaction Owner/Boundary를 Document, Source, Test, Runtime 기준으로 확정한다.

## Team
Baseline → Document + Source → Model → Test → Runtime → Drift → GAP/ADR → Gate

## 핵심 비교
- 문서의 Transaction Boundary 주장
- `OnlineTransactionTimeoutExecutor`
- Transaction 관련 AOP/AttributeSource/TransactionTemplate 사용 여부
- Business Facade `@Transactional`
- 실제 Runtime Thread/TX Stack

## 성공
AS-IS, TO-BE, GAP가 분리되고 Runtime Evidence 또는 명시적 G50 HOLD가 존재한다.
