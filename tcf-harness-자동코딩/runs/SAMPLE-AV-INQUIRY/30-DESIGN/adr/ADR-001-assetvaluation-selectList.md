# ADR-001: AssetValuation 목록 조회 ServiceId·패턴

## Status
Proposed (AA 승인 대기)

## Context
BA가 ServiceId `AV.AssetValuation.selectList`를 승인했다.  
av-service에는 Sample·CustomerContact 도메인이 있으며, 페이징 목록은 CustomerContact 패턴이 적합하다.

## Decision
1. ServiceId = `AV.AssetValuation.selectList`
2. 도메인별 Handler 1개 (`AvAssetValuationHandler`)
3. CustomerContact와 동일한 6계층·페이징 SQL 패턴
4. 화면 UI는 1차 범위 제외

## Alternatives
- `AV.AssetValuation.inquiryList` — 프롬프트 샘플 혼재 → selectList로 통일
- Sample Handler에 편입 — 도메인 경계 훼손 → 기각

## Consequences
- OM·테스트·Mapper에 동일 ServiceId 사용
- DA가 테이블 소유권 승인해야 구현 Gate(HG-40) 통과 가능
