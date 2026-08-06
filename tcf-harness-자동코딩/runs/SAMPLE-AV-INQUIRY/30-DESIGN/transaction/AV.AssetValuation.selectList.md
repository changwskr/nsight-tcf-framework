# AV.AssetValuation.selectList 거래설계

## 개요
- ServiceId: `AV.AssetValuation.selectList`
- 거래코드: (OM 등록 시 동일 ServiceId 사용)
- 처리유형: INQUIRY (readOnly)
- 업무코드: AV
- 도메인: AssetValuation
- 채널: API (화면 UI 1차 제외, GAP-AV-0003 RESOLVED)
- 요구사항: REQ-AV-0001, REQ-AV-0002, REQ-AV-0003, REQ-AV-0004

## 요청/응답

### Request body
| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| pageNo | Integer | Y | 1부터 |
| pageSize | Integer | Y | 기본 20, 최대 100(Rule) |
| evalDate | String | Y | 평가일자 `yyyyMMdd` (ASM-AV-0001) |
| productCode | String | N | 상품코드 |

### Response body
| 필드 | 타입 | 설명 |
| --- | --- | --- |
| pageNo | Integer | |
| pageSize | Integer | |
| totalCount | Integer | |
| items | Array | 목록 |
| items[].valuationId | String | 평가 ID |
| items[].evalDate | String | 평가일자 |
| items[].productCode | String | 상품코드 |
| items[].valuationAmt | Number | 평가금액 |
| items[].currencyCode | String | 통화 |
| items[].useYn | String | 사용여부 |

## 프로그램 계층

| 계층 | 클래스 | 책임 |
| --- | --- | --- |
| Handler | `AvAssetValuationHandler` | ServiceId 라우팅 |
| Facade | `AvAssetValuationFacade` | `@Transactional(readOnly=true)` |
| Service | `AvAssetValuationService` | 유스케이스 조합 |
| Rule | `AvAssetValuationRule` | 입력검증·Criteria 조립 |
| DAO | `AvAssetValuationDao` | Mapper 호출 |
| Mapper | `AvAssetValuationMapper` | SQL |

패키지 루트: `com.nh.nsight.marketing.av`  
참고 패턴: `AV.CustomerContact.selectList` / `AvCustomerContact*`

## SQL / DB (초안 — DA 승인 필요)

- Mapper namespace: `com.nh.nsight.marketing.av.persistence.mapper.AvAssetValuationMapper`
- statementId: `searchValuations`, `countValuations`
- 제안 테이블: `AV_ASSET_VALUATION` (GAP-AV-0001, 소유권 UNCONFIRMED)
- 페이징: `OFFSET #{offset} ROWS FETCH NEXT #{pageSize} ROWS ONLY`

## OM 초안
- Service Catalog: `AV.AssetValuation.selectList`
- Timeout: 5s (Facade와 동일)
- 거래통제: 조회 전용, 인증된 API 호출

## Trace
- REQ-AV-0001 → AV.AssetValuation.selectList → AvAssetValuationHandler → Mapper → AV_ASSET_VALUATION
- REQ-AV-0002 → pageNo/pageSize/totalCount
- REQ-AV-0004 → StandardRequest 경로 + readOnly 트랜잭션
