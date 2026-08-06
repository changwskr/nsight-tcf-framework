# Data Design — AV_ASSET_VALUATION (초안)

> GAP-AV-0001: 소유 도메인·공식 스키마는 **DATA_ARCHITECT 승인 전 확정 아님**.  
> 로컬/개발용 H2 `schema.sql` 확장 초안이며 운영 DDL 실행 금지.

## 제안 테이블

| 컬럼 | 타입(안) | 설명 |
| --- | --- | --- |
| VALUATION_ID | VARCHAR | PK |
| EVAL_DATE | VARCHAR(8) | yyyyMMdd |
| PRODUCT_CODE | VARCHAR | 상품코드 |
| VALUATION_AMT | DECIMAL | 평가금액 |
| CURRENCY_CODE | VARCHAR(3) | 통화 |
| USE_YN | CHAR(1) | Y/N |
| REG_DTM | TIMESTAMP | |
| UPD_DTM | TIMESTAMP | |

## 인덱스(안)
- `(EVAL_DATE, USE_YN, PRODUCT_CODE)`

## 소유권
- ownerDomain: AssetValuation (제안)
- ownerOrganization: **TBD — DA 확인**
- status: UNCONFIRMED

## DDL 초안 (개발용)

```sql
CREATE TABLE IF NOT EXISTS AV_ASSET_VALUATION (
    VALUATION_ID   VARCHAR(32) PRIMARY KEY,
    EVAL_DATE      VARCHAR(8) NOT NULL,
    PRODUCT_CODE   VARCHAR(32),
    VALUATION_AMT  DECIMAL(18,2),
    CURRENCY_CODE  VARCHAR(3),
    USE_YN         CHAR(1) DEFAULT 'Y',
    REG_DTM        TIMESTAMP,
    UPD_DTM        TIMESTAMP
);
```
