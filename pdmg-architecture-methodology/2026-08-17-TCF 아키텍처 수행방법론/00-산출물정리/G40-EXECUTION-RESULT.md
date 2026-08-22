# G40 Mechanism / Source Conformance 실행 결과

## 1. Gate 판정

**G40 = CONDITIONAL PASS**

실제 Source Snapshot에서 PDMG AS-IS와 NSIGHT TCF TO-BE의 핵심 Runtime Mechanism이 모두 확인되었다. 특히 NSIGHT TCF는 PDMG Reference에서 확인된 ServiceId Dispatcher/Context/Timeout 구조를 계승하면서, STF/ETF의 책임을 인증·권한·멱등성·Timeout Policy·거래로그·Audit·Metric까지 확장하고 Transaction Owner를 Facade 중심으로 분리하는 방향이 확인된다.

## 2. 완료 항목

- PDMG System / TCF / Business 선후처리 Source 확인
- PDMG ServiceId Dispatcher Source 확인
- PDMG Timeout Worker + TransactionTemplate Source 확인
- PDMG TransactionContext/ServiceContext Lifecycle 확인
- NSIGHT TCF `TCF → STF → Timeout → Dispatcher → ETF` Source 확인
- NSIGHT ServiceId Duplicate Fail-Fast 확인
- NSIGHT TimeoutPolicy Repository/Context 적용 확인
- Policy-driven Transaction Timeout 확인
- StandardRequest/Response/Header 구조 확인
- Error Owner 분리 확인
- PDMG vs NSIGHT TCF Conformance Matrix 작성

## 3. Source Scan 주요 수치

| 항목 | 결과 |
|---|---:|
| PDMG Handler | 6 |
| PDMG ServiceId | 13 |
| PDMG ServiceId 중복 | 0 |
| NSIGHT `com.nh.nsight.*` Handler | 69 |
| NSIGHT ServiceId 후보 | 121 |
| NSIGHT Facade `@Transactional` 파일 | 53 |
| NSIGHT Service `@Transactional` 파일 | 4 |

## 4. Critical G40 GAP / Risk

| ID | 내용 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G40-C01 | Transaction Owner: Facade 원칙과 Service TX 4개 예외 정리 | P0 | G40/G60 |
| G40-C02 | PDMG Outer TransactionTemplate 경로의 Late Commit/Connection Hold Runtime Test | P0 | G60 |
| G40-C03 | SQL Query Timeout 전수 적용 검증 | P0 | G60 |
| G40-C04 | Worker RequestAttributes 전파 정책 ADR | P0 | G40/G50 |
| G40-C05 | `om-service` vs `tcf-om` Runtime/Build Scope 확정 | P0 | G00/G40 |
| G40-C06 | PDMG ServiceId ↔ NSIGHT ServiceId Mapping 정책 | P0 | G50/G80 |
| G40-C07 | `hdr_nhnis+dto` ↔ `header+body+result` Contract Mapping | P0 | G50 |
| G40-C08 | HTTP 200 Error Response 정책 | P1 | G50/G70 |
| G40-C09 | TCF/STF/ETF Console Trace 운영정책 | P1 | G70 |
| G40-C10 | Runtime Context/ThreadLocal Leak Test | P0 | G60 |

## 5. 다음 단계

```text
G40 CONDITIONAL PASS
       ↓
G50 Security / Data / Integration
       ↓
SSO/JWT/Key
Data Ownership
RDW/ADW
Domain Integration Contract
Standard Message Mapping
```

다음 단계는 **G50 Security / Data / Integration**이다.
