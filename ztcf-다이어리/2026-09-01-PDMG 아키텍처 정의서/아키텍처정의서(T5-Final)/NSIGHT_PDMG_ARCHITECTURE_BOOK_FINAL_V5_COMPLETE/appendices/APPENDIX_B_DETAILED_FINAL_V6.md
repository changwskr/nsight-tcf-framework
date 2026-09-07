# APPENDIX B. Application Code Registry — Detailed Final
## NSIGHT Application Classification SSOT

### B.1 6 Application Groups

| Group | 한글명 | 영문명 |
|---|---|---|
| MP | 마케팅플랫폼 | Marketing Platform |
| RD | 데이터플랫폼 RDW | Real-time Data Warehouse |
| AD | 데이터플랫폼 ADW | Analytical Data Warehouse |
| BI | BI 포탈 | Business Intelligence |
| DG | 데이터거버넌스 | Data Governance |
| IM | IT서비스 및 업무지원 | Information Management |

### B.2 Application Code Master — 50

| Group | Group Name | App | Canonical Key | 한글 | 영문 | 상태 | 근거 |
|---|---|---|---|---|---|---|---|
| MP | 마케팅플랫폼 | CO | MP-CO | 공통 | Common | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | IC | MP-IC | 통합고객 | Integration Customer | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | PC | MP-PC | 개인고객 | Private Customer | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | BC | MP-BC | 기업고객 | Business Customer | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | MS | MP-MS | 미니 싱글뷰 | Mini SingleView | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | SA | MP-SA | 상담판매 | Sale | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | PD | MP-PD | 통합상품 | Product | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | CM | MP-CM | 캠페인 | Campaign | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | EB | MP-EB | EBM | EBM | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | EP | MP-EP | 실시간 처리 | Event Processing | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | BP | MP-BP | 행동정보 처리 | Behavior Information Processing | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | BD | MP-BD | 고객 행동 데이터 | Customer Behavior Data | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | SS | MP-SS | 영업지원 | Sales Support | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | CS | MP-CS | CS | Customer Service | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | CT | MP-CT | 컨텐츠 | Contents | [BASELINE] | Application Code Definition |
| MP | 마케팅플랫폼 | MG | MP-MG | 메시지 | Message | [BASELINE] | Application Code Definition |
| RD | 데이터플랫폼 RDW | CO | RD-CO | 공통 | Common | [BASELINE] | Application Code Definition |
| RD | 데이터플랫폼 RDW | SR | RD-SR | 실시간 SoR | Source of Record | [BASELINE] | Application Code Definition |
| RD | 데이터플랫폼 RDW | ZD | RD-ZD | 준실시간요약집계 | Zipped Data | [BASELINE] | Application Code Definition |
| RD | 데이터플랫폼 RDW | RM | RD-RM | 준실시간보고서마트 | Report Data Mart | [BASELINE] | Application Code Definition |
| RD | 데이터플랫폼 RDW | FA | RD-FA | 피드백 | Feedback Area | [BASELINE] | Application Code Definition |
| AD | 데이터플랫폼 ADW | CO | AD-CO | 공통 | Common | [BASELINE] | Application Code Definition |
| AD | 데이터플랫폼 ADW | SR | AD-SR | 분석 SoR | Source of Record | [BASELINE] | Application Code Definition |
| AD | 데이터플랫폼 ADW | ZD | AD-ZD | 분석통합요약집계 | Zipped Data Area | [BASELINE] | Application Code Definition |
| AD | 데이터플랫폼 ADW | UM | AD-UM | 분석단위업무마트 | Unit-business Mart | [BASELINE] | Application Code Definition |
| AD | 데이터플랫폼 ADW | RM | AD-RM | 분석보고서마트 | Report Data Mart | [BASELINE] | Application Code Definition |
| AD | 데이터플랫폼 ADW | FA | AD-FA | 피드백 | Feedback Area | [BASELINE] | Application Code Definition |
| AD | 데이터플랫폼 ADW | DA | AD-DA | 분석지원 | DW Analysis Assistance | [BASELINE] | Application Code Definition |
| BI | BI 포탈 | PT | BI-PT | BI 포탈 | Portal | [BASELINE] | Application Code Definition |
| BI | BI 포탈 | CR | BI-CR | 신용실적 | Credit Result | [BASELINE] | Application Code Definition |
| BI | BI 포탈 | OA | BI-OA | OLAP | Online Analysis Process | [BASELINE] | Application Code Definition |
| BI | BI 포탈 | SB | BI-SB | Self BI | Self Business Intelligence | [BASELINE] | Application Code Definition |
| BI | BI 포탈 | UI | BI-UI | 신 BI 포털 UI/UX | UI/UX | [BASELINE] | Application Code Definition |
| DG | 데이터거버넌스 | CO | DG-CO | 공통 | Common | [BASELINE] | Application Code Definition |
| DG | 데이터거버넌스 | BM | DG-BM | 비즈메타 | Biz-Meta System | [BASELINE] | Application Code Definition |
| DG | 데이터거버넌스 | DQ | DG-DQ | 데이터품질 | Data Quality | [BASELINE] | Application Code Definition |
| DG | 데이터거버넌스 | DL | DG-DL | 데이터흐름 | Data Lineage | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | AM | IM-AM | 아키텍처 관리 | Architecture Management | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | SC | IM-SC | 시스템 공통 | System Common | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | DP | IM-DP | 배포 | Deployment | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | FW | IM-FW | 프레임워크 | Framework | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | LB | IM-LB | 라이브러리 | Library | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | SM | IM-SM | 소스 코드 버전 관리 | Source Code Version Management | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | XM | IM-XM | 정보단말 관리 | UI/UX Management | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | XD | IM-XD | 정보단말 배포 | UI/UX Deployment | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | BJ | IM-BJ | 배치작업 처리 | Batch Job Processing | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | CD | IM-CD | 실시간 중계 | CDC Gateway | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | DT | IM-DT | 데이터 치환 적재 | Data Transform Load | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | RD | IM-RD | 보고서 디자이너 | Report Designer | [BASELINE] | Application Code Definition |
| IM | IT서비스 및 업무지원 | IG | IM-IG | 거래 공통 메모리 | In Memory Data Grid | [BASELINE] | Application Code Definition |

### B.3 Canonical Identification

```text
Canonical Application Key
= Group + "-" + Application

RD-SR ≠ AD-SR
```

같은 L2 코드라도 Group이 다르면 다른 Application이다.

### B.4 PDMG Mapping Rule

```text
NSIGHT Target
MP
 ↓
Mapping Registry / ADR
 ↓
PDMG Current
mg
```

`MP = mg`를 자동 가정하지 않는다.

### B.5 Current Conflict / GAP

- MP 일부 구형자료에 PC/BC/EB 누락 이력.
- AD 과거코드와 현재 Baseline 충돌 이력.
- BI Portal `PO` vs `PT` 충돌 이력.
- PDMG `mg` ↔ NSIGHT `MP` Mapping Registry 필요.
