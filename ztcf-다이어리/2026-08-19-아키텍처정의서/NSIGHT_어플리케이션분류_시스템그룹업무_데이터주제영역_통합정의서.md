# NSIGHT 어플리케이션 분류체계 · 시스템 그룹 업무 구분 · 데이터 주제영역 통합 정의서

## 1. 문서 목적

본 문서는 이미지에서 추출한 다음 3개 영역의 정보를 하나의 기준 문서로 통합한 것이다.

1. **어플리케이션 분류 체계**
2. **시스템 그룹 업무 구분**
3. **데이터 주제영역 구성 정의**

원본 이미지에서 확인되는 용어, 코드, 영문명, 서버명, 용도, 비고를 유지하며, 이미지에서 확정되지 않은 세부 업무 구분은 임의로 보완하지 않는다.

> 중복 촬영 프레임인 `21`, `26`, `30`은 동일 내용 중복이므로 통합본에서는 1회만 반영하였다.

---

# 2. 통합 구조

## 2.1 대분류 체계

| 대분류 | 코드 | 영문명 | 주요 역할 |
|---|---|---|---|
| 상호금융_마케팅플랫폼 | `MP` | Marketing Platform | 고객·상품·상담·캠페인·이벤트·마케팅 지원 |
| 상호금융_데이터플랫폼_RDW | `RD` | Real-time Data Warehouse | 실시간/준실시간 데이터 저장·요약·마트·피드백 |
| 상호금융_데이터플랫폼_ADW | `AD` | Analytical Data Warehouse | 분석 SoR·통합요약·업무마트·보고서마트·분석지원 |
| 상호금융_BI포탈 | `BI` | Business Intelligence | BI Portal·신용실적·OLAP·Self BI·UI/UX |
| 상호금융_데이터거버넌스 | `DG` | Data Governance | 비즈메타·데이터품질·데이터흐름 |
| 상호금융_IT서비스 및 업무지원 | `IM` | Information Management | 아키텍처·공통·배포·Framework·형상·단말·Batch·CDC·ETL·Report |

## 2.2 관리 계층

```text
대분류(어플리케이션 그룹)
    ↓
업무구분(어플리케이션)
    ↓
세부 업무 구분(기능)
    ↓
시스템 그룹
    ↓
대상 서버
    ↓
업무구분 코드
    ↓
서버명 / 용어 / 솔루션
    ↓
데이터 주제영역
```

---

# 3. 어플리케이션 분류 체계

# 어플리케이션 분류 체계 (1/5)

## 마케팅플랫폼

| 대구분 한글명 | 대구분 코드 | 대구분 영문명 | 업무구분 한글명 | 업무구분 코드 | 업무구분 영문명 | 세부 업무 구분 |
|---|---|---|---|---|---|---|
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 공통 | CO | Common | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 통합고객 | IC | Integration Customer | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 개인고객 | PC | Private Customer | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 기업고객 | BC | Business Customer | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 미니 싱글뷰 | MS | Mini SingleView | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 상담판매 | SA | Sale | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 통합상품 | PD | Product | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 캠페인 | CM | Campaign | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | EBM | EB | EBM | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 실시간 처리 | EP | Event Processing | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 행동정보 처리 | BP | Behavior Information Processing | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 고객 행동 데이터 | BD | Customer Behavior Data | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 영업지원 | SS | Sales Support | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | CS | CS | Customer Service | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 컨텐츠 | CT | Contents | 1 byte |
| 상호금융_마케팅플랫폼 | MP | Marketing Platform | 메시지 | MG | Message | 1 byte |

> 세부 업무 구분(기능): **업무개발팀 분석, 설계단계 시 반영**

---

# 어플리케이션 분류 체계 (2/5)

## 데이터플랫폼

### 상호금융_데이터플랫폼_RDW

| 대구분 코드 | 대구분 영문명 | 업무구분 한글명 | 업무구분 코드 | 업무구분 영문명 | 세부 업무 구분 |
|---|---|---|---|---|---|
| RD | Real-time Data Warehouse | 공통 | CO | Common | 1 byte |
| RD | Real-time Data Warehouse | 실시간SoR | SR | Source of record | 1 byte |
| RD | Real-time Data Warehouse | 준실시간요약집계 | ZD | Zipped Data | 1 byte |
| RD | Real-time Data Warehouse | 준실시간보고서마트 | RM | Report Data Mart | 1 byte |
| RD | Real-time Data Warehouse | 피드백 | FA | Feedback area | 1 byte |

### 상호금융_데이터플랫폼_ADW

| 대구분 코드 | 대구분 영문명 | 업무구분 한글명 | 업무구분 코드 | 업무구분 영문명 | 세부 업무 구분 |
|---|---|---|---|---|---|
| AD | Analytical Data Warehouse | 공통 | CO | Common | 1 byte |
| AD | Analytical Data Warehouse | 분석SoR | SR | Source of record | 1 byte |
| AD | Analytical Data Warehouse | 분석통합요약집계 | ZD | Zipped data area | 1 byte |
| AD | Analytical Data Warehouse | 분석단위업무마트 | UM | Unit-business mart | 1 byte |
| AD | Analytical Data Warehouse | 분석보고서마트 | RM | Report data mart | 1 byte |
| AD | Analytical Data Warehouse | 피드백 | FA | Feedback area | 1 byte |
| AD | Analytical Data Warehouse | 분석지원 | DA | DW Analysis Assistance | 1 byte |

> 세부 업무 구분(기능): **업무개발팀 분석, 설계단계 시 반영**

---

# 어플리케이션 분류 체계 (3/5)

## BI 포탈

| 대구분 한글명 | 대구분 코드 | 대구분 영문명 | 업무구분 한글명 | 업무구분 코드 | 업무구분 영문명 | 세부 업무 구분 |
|---|---|---|---|---|---|---|
| 상호금융_BI포탈 (사용자분석) | BI | Business Intelligence | BI포탈 | PT | Portal | 1 byte |
| 상호금융_BI포탈 (사용자분석) | BI | Business Intelligence | 신용실적 | CR | Credit result | 1 byte |
| 상호금융_BI포탈 (사용자분석) | BI | Business Intelligence | OLAP | OA | Online analysis process | 1 byte |
| 상호금융_BI포탈 (사용자분석) | BI | Business Intelligence | Self BI | SB | Self business Intelligence | 1 byte |
| 상호금융_BI포탈 (사용자분석) | BI | Business Intelligence | 신BI포탈UIUX | UI | UI/UX | 1 byte |

> 세부 업무 구분(기능): **업무개발팀 분석, 설계 단계 시 반영**

---

# 어플리케이션 분류 체계 (4/5)

## 데이터거버넌스

| 대구분 한글명 | 대구분 코드 | 대구분 영문명 | 업무구분 한글명 | 업무구분 코드 | 업무구분 영문명 | 세부 업무 구분 |
|---|---|---|---|---|---|---|
| 상호금융_데이터거버넌스 | DG | Data Governance | 공통 | CO | Common | 1 byte |
| 상호금융_데이터거버넌스 | DG | Data Governance | 비즈메타 | BM | Biz-Meta system | 1 byte |
| 상호금융_데이터거버넌스 | DG | Data Governance | 데이터품질 | DQ | Data Quality | 1 byte |
| 상호금융_데이터거버넌스 | DG | Data Governance | 데이터흐름 | DL | Data Lineage | 1 byte |

> 세부 업무 구분(기능): **업무개발팀 분석, 설계단계 시 반영**

---

# 어플리케이션 분류 체계 (5/5)

## IT서비스 및 인프라 지원

| 대구분 한글명 | 대구분 코드 | 대구분 영문명 | 업무구분 한글명 | 업무구분 코드 | 업무구분 영문명 | 세부 업무 구분 |
|---|---|---|---|---|---|---|
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 아키텍처 관리 | AM | Architecture Management | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 시스템 공통 | SC | System Common | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 배포 | DP | Deployment | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 프레임워크 | FW | Framework | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 라이브러리 | LB | Library | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 소스 코드 버전 관리 | SM | Source Code Version Management | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 정보단말 관리 | XM | UI/UX Management | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 정보단말 배포 | XD | UI/UX Deployment | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 배치작업 처리 | BJ | Batch Job Processing | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 실시간 중계 | CD | CDC Gateway | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 데이터 치환 적재 | DT | Data Transform Load | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 보고서 디자이너 | RD | Report Designer | 1 byte |
| 상호금융_IT서비스 및 업무지원 | IM | Information Management | 거래 공통 메모리 | IG | In Memory Data Grid | 1 byte |

> 세부 업무 구분(기능): **업무개발팀 분석, 설계단계 시 반영**

---

# 4. 시스템 그룹 업무 구분

# 시스템 그룹 업무 구분 (1/6)

## 마케팅플랫폼 시스템 그룹

- 대구분: `상호금융_마케팅플랫폼`
- 코드: `MP`
- 영문: `Marketing Platform`

| 대상서버 | 업무구분 코드 | 서버명 | 용어 | 비고 |
|---|---|---|---|---|
| 마케팅플랫폼 WEB 서버 | CO, IC, PC, BC, SA, PD, CM, EB, SS, CS, CT, MG | sbmpco | co = Common | Apache |
| 마케팅플랫폼 WAS 서버 | CO, IC, PC, BC, SA, PD, CM, EB, SS, CS, CT, MG | sbmpco | co = Common | Tomcat, NH Cloud Framework |
| 미니싱글뷰 WEB 서버 | MS | sbmpms | ms = 미니 싱글뷰 어플리케이션 코드 | Apache |
| 미니싱글뷰 WAS 서버 | MS | sbmpms | ms = 미니 싱글뷰 어플리케이션 코드 | Tomcat, NH Cloud Framework |
| 실시간 처리 서버 | EP | sbmpep | ep = 실시간 처리 어플리케이션 코드 | 솔루션 (고객행동) |
| 행동정보 처리 서버 | BP | sbmpbp | bp = 행동정보 처리 어플리케이션 코드 | 솔루션 (고객행동) |
| 고객 행동 데이터 서버 | BD | sbmpbd | bd = 고객 행동 데이터 어플리케이션 코드 | 솔루션 (Kafka 기반 솔루션) |

---

# 시스템 그룹 업무 구분 (2/6)

## 데이터플랫폼 시스템 그룹

| 대구분 | 코드 | 영문 | 대상서버 | 업무구분 코드 | 서버명 | 용어 | 비고 |
|---|---|---|---|---|---|---|---|
| 상호금융_데이터플랫폼_RDW | RD | Real-time Data Warehouse | RDW 서버 (어플라이언스) | SR, ZD, RM, FA, CO | sbrdco | co = Common | Real-time Data Warehouse |
| 상호금융_데이터플랫폼_ADW | AD | Analytical Data Warehouse | ADW 서버 (어플라이언스) | SR, ZD, UM, FA, CO, RM, DA | sbadco | co = Common | Analytical Data Warehouse |

---

# 시스템 그룹 업무 구분 (3/6)

## BI 포탈 시스템 그룹

- 대구분: `상호금융_BI포탈 (사용자분석)`
- 코드: `BI`
- 영문: `Business Intelligence`

| 대상서버 | 업무구분 코드 | 서버명 | 용어 | 비고 |
|---|---|---|---|---|
| OLAP AP 서버 | OA | sbbioa | oa = OLAP 어플리케이션 코드 | MSTR |
| OLAP WEB/WAS 서버 | OA | sbbioa | oa = OLAP 어플리케이션 코드 | 솔루션 제공 (담당자 투입시 확인 필요) |
| BI 포탈 WEB 서버 | PT | sbbipt | pt = BI Portal 어플리케이션 코드 | 솔루션 (Data Eye) |
| BI 포탈 WAS 서버 | PT | sbbipt | pt = BI Portal 어플리케이션 코드 | 솔루션 (Data Eye) |
| Self BI AP 서버 | SB | sbbisb | sb = Self-BI 어플리케이션 코드 | 솔루션 (BI-Matrix) |
| Self BI WEB 서버 | SB | sbbisb | sb = Self-BI 어플리케이션 코드 | 솔루션 (BI-Matrix) |
| Self BI WAS 서버 | SB | sbbisb | sb = Self-BI 어플리케이션 코드 | 솔루션 (BI-Matrix) |
| 신용 실적 WEB 서버 | CR, UI | sbbicr | cr = 신용실적 어플리케이션 코드 | Apache |
| 신용 실적 WAS 서버 | CR, UI | sbbicr | cr = 신용실적 어플리케이션 코드 | NH Cloud Framework |

---

# 시스템 그룹 업무 구분 (4/6)

## 데이터거버넌스 시스템 그룹

- 대구분: `상호금융_데이터거버넌스`
- 코드: `DG`
- 영문: `Data Governance`

| 대상서버 | 업무구분 코드 | 서버명 | 용어 | 비고 |
|---|---|---|---|---|
| 비즈메타/데이터품질관리 WAS 서버 | CO, DQ, BM | sbdgdq | dq = 데이터 품질 어플리케이션 코드 | GT-One 비즈메타 / GT-One DQ Miner (데이터품질) |
| 데이터흐름관리 WAS 서버 | DF | sbdfdl | df = 데이터 흐름 어플리케이션 코드 | GT-One 데이터 HAWK |

---

# 시스템 그룹 업무 구분 (5/6)

## IT서비스 및 인프라 지원 시스템 그룹

- 대구분: `상호금융_IT서비스 및 업무지원`
- 코드: `IM`
- 영문: `Information Management`

| 대상서버/기능 | 업무구분 코드 | 서버명 | 용어 | 비고 |
|---|---|---|---|---|
| NH Cloud Framework 마스터 관리 | - | - | - | 테크시스템부 미들웨어팀 |
| 라이브러리 | - | - | - | AS-IS Nexus 사용 |
| 소스 코드 버전 관리 | SM | sbimsm | sm = 소스관리 어플리케이션 코드 | GitLab |
| 배포 AP 서버 | DP | sbimdp | dp = 배포 어플리케이션 코드 | GitLab Runner |
| 단말관리 WAS 서버 | XM | sbimum | um = 단말관리 어플리케이션 코드 | 솔루션 (WebTopSuite 5.0) |
| 단말배포 WAS 서버 | XD | sbimud | ud = 단말배포 어플리케이션 코드 | 솔루션 (WebTopSuite 5.0) |
| 배치 AP 서버 | BJ | sbimbj | bj = 배치작업 처리 어플리케이션 코드 | NH Cloud Framework Batch |
| CDC 중계 AP 서버 | CD | sbimcd | cd = CDC 어플리케이션 코드 | OGG |
| ETL 서버 | DT | sbimdt | dt = ETL 어플리케이션 코드 | DataStage |
| 출력물(RD) WAS 서버 | RD | sbimrd | rd = 출력물 보고서 어플리케이션 코드 | Report Designer |

---

# 시스템 그룹 업무 구분 (6/6)

## 인프라 임시 시스템 그룹

- 대구분: `상호금융_IT서비스 및 업무지원`
- 코드: `IM`
- 영문: `Information Management`
- 시스템 그룹: `이행용 임시장비`

| 대상서버 | 서버명 | 용어 | 비고 |
|---|---|---|---|
| [임시] 데이터 이행용 (변환) AP #01 | sbimsi | imsi = 이행 임시 | - |
| [임시] 데이터 이행용 (변환) AP #02 | sbimsi | imsi = 이행 임시 | - |
| [임시] 데이터 이행용 (변환) AP #03 | sbimsi | imsi = 이행 임시 | - |
| [임시] 데이터 이행용 (변환) AP #04 | sbimsi | imsi = 이행 임시 | - |
| [임시] 데이터 이행용 (변환) AP #05 | sbimsi | imsi = 이행 임시 | - |
| [임시] 데이터 이행용 (변환) AP #06 | sbimsi | imsi = 이행 임시 | - |
| [임시] 데이터 이행용 (추출) AP #07 | sbimsi | imsi = 이행 임시 | 데이터 추출용 |
| [임시] 데이터 이행용 (이행) AP #08 | sbimsi | imsi = 이행 임시 | 데이터 이행용 |
| [임시] 데이터 이행용 (SQL 품질) AP #09 | sbimsi | imsi = 이행 임시 | SQL 품질 점검용 |

---

# 5. 데이터 주제영역 구성 정의

# 데이터 주제영역 구성 정의 (1/2)

## 차세대 정보계 데이터 주제영역

| 주제영역 | 세부 영역 |
|---|---|
| 데이터플랫폼 RDW | 공통, 실시간SoR, 준실시간요약집계, 준실시간보고서마트, 피드백 |
| 데이터플랫폼 ADW | 공통, 분석SoR, 분석통합요약집계, 분석단위업무마트, 분석보고서마트, 피드백, 분석지원 |
| BI 포탈 | BI포탈, 신용실적, OLAP, Self BI, 신포털UIUX |
| 마케팅플랫폼 | 공통, 통합고객, 개인고객, 기업고객, 미니싱글뷰, 상담판매, 통합상품, 캠페인, EBM, 실시간처리, 행동정보처리, 고객행동데이터, 영업지원, CS, 컨텐츠, 메시지 |
| 데이터거버넌스 | 비즈메타, 데이터품질, 데이터흐름 |
| IT서비스 및 업무지원 | 아키텍처 관리, 시스템 공통, 배포, 프레임워크, 라이브러리, 소스코드 버전관리, 정보단말 관리, 정보단말 배포, 배치작업 처리, 실시간 중계, 데이터 치환 적재, 보고서 디자이너 |

## TEXT 구조

```text
차세대 정보계
├─ 데이터플랫폼 RDW
│  ├─ 공통
│  ├─ 실시간SoR
│  ├─ 준실시간요약집계
│  ├─ 준실시간보고서마트
│  └─ 피드백
├─ 데이터플랫폼 ADW
│  ├─ 공통
│  ├─ 분석SoR
│  ├─ 분석통합요약집계
│  ├─ 분석단위업무마트
│  ├─ 분석보고서마트
│  ├─ 피드백
│  └─ 분석지원
├─ BI 포탈
│  ├─ BI포탈
│  ├─ 신용실적
│  ├─ OLAP
│  ├─ Self BI
│  └─ 신포털UIUX
├─ 마케팅플랫폼
│  ├─ 공통 / 통합고객 / 개인고객 / 기업고객
│  ├─ 미니싱글뷰 / 상담판매 / 통합상품 / 캠페인
│  ├─ EBM / 실시간처리 / 행동정보처리 / 고객행동데이터
│  └─ 영업지원 / CS / 컨텐츠 / 메시지
├─ 데이터거버넌스
│  ├─ 비즈메타
│  ├─ 데이터품질
│  └─ 데이터흐름
└─ IT서비스 및 업무지원
   ├─ 아키텍처 관리 / 시스템 공통 / 배포
   ├─ 프레임워크 / 라이브러리 / 소스코드 버전관리
   ├─ 정보단말 관리 / 정보단말 배포 / 배치작업 처리
   └─ 실시간 중계 / 데이터 치환 적재 / 보고서 디자이너
```

---

# 데이터 주제영역 구성 정의 (2/2)

## 차세대 정보계

| 주제영역 한글명 | 영문 | 주제영역 정의 |
|---|---|---|
| 데이터플랫폼 RDW | Real-time Data Warehouse | 공통, 실시간SoR, 준실시간요약집계, 준실시간보고서마트, 피드백을 관리하는 영역 |
| 데이터플랫폼 ADW | Analytical Data Warehouse | 공통, 분석SoR, 분석통합요약집계, 분석단위업무마트, 분석보고서마트, 피드백, 분석지원을 관리하는 영역 |
| BI포탈 | Business Intelligence | BI포탈, 신용실적, OLAP, Self BI, 신포털UIUX를 관리하는 영역 |
| 마케팅플랫폼 | Marketing Platform | 공통, 통합고객, 개인고객, 기업고객, 미니싱글뷰, 상담판매, 통합상품, 캠페인, EBM, 실시간처리, 행동정보처리, 고객행동데이터, 영업지원, CS, 컨텐츠, 메시지를 관리하는 영역 |
| 데이터 거버넌스 | Data Governance | 비즈메타, 데이터품질, 데이터흐름을 관리하는 영역 |
| IT서비스 및 업무지원 | Information Management | 아키텍처 관리, 시스템 공통, 배포, 프레임워크, 라이브러리, 소스코드 버전관리, 정보단말 관리, 정보단말 배포, 배치작업 처리, 실시간 중계, 데이터 치환 적재, 보고서 디자이너를 관리하는 영역 |

---

# 6. 어플리케이션 ↔ 시스템 그룹 ↔ 데이터 주제영역 통합 매핑

| 어플리케이션 그룹 | 코드 | 대표 업무구분 | 주요 시스템 그룹/서버 | 데이터 주제영역 |
|---|---|---|---|---|
| 마케팅플랫폼 | MP | CO, IC, PC, BC, MS, SA, PD, CM, EB, EP, BP, BD, SS, CS, CT, MG | 마케팅플랫폼 WEB/WAS, 미니싱글뷰 WEB/WAS, 실시간처리, 행동정보처리, 고객행동데이터 | 공통, 통합고객, 개인고객, 기업고객, 미니싱글뷰, 상담판매, 통합상품, 캠페인, EBM, 실시간처리, 행동정보처리, 고객행동데이터, 영업지원, CS, 컨텐츠, 메시지 |
| 데이터플랫폼 RDW | RD | CO, SR, ZD, RM, FA | RDW 서버(어플라이언스) | 공통, 실시간SoR, 준실시간요약집계, 준실시간보고서마트, 피드백 |
| 데이터플랫폼 ADW | AD | CO, SR, ZD, UM, RM, FA, DA | ADW 서버(어플라이언스) | 공통, 분석SoR, 분석통합요약집계, 분석단위업무마트, 분석보고서마트, 피드백, 분석지원 |
| BI포탈 | BI | PT, CR, OA, SB, UI | OLAP AP/WEB/WAS, BI포탈 WEB/WAS, Self BI AP/WEB/WAS, 신용실적 WEB/WAS | BI포탈, 신용실적, OLAP, Self BI, 신포털UIUX |
| 데이터거버넌스 | DG | CO, BM, DQ, DL | 비즈메타/데이터품질관리 WAS, 데이터흐름관리 WAS | 비즈메타, 데이터품질, 데이터흐름 |
| IT서비스 및 업무지원 | IM | AM, SC, DP, FW, LB, SM, XM, XD, BJ, CD, DT, RD, IG | Framework 관리, 라이브러리, 소스코드 버전관리, 배포 AP, 단말관리/배포 WAS, 배치 AP, CDC 중계 AP, ETL, Report Designer | 아키텍처 관리, 시스템 공통, 배포, 프레임워크, 라이브러리, 소스코드 버전관리, 정보단말 관리/배포, 배치작업 처리, 실시간 중계, 데이터 치환 적재, 보고서 디자이너 |
| 인프라 임시 시스템 | IM | 이미지상 별도 업무코드 미표기 | 데이터 이행용 변환/추출/이행/SQL품질 AP | 이미지상 별도 주제영역 정의 없음 |

---

# 7. 코드 기준 요약

## 7.1 마케팅플랫폼

`CO, IC, PC, BC, MS, SA, PD, CM, EB, EP, BP, BD, SS, CS, CT, MG`

## 7.2 데이터플랫폼 RDW

`CO, SR, ZD, RM, FA`

## 7.3 데이터플랫폼 ADW

`CO, SR, ZD, UM, RM, FA, DA`

## 7.4 BI포탈

`PT, CR, OA, SB, UI`

## 7.5 데이터거버넌스

`CO, BM, DQ, DL`

## 7.6 IT서비스 및 업무지원

`AM, SC, DP, FW, LB, SM, XM, XD, BJ, CD, DT, RD, IG`

---

# 8. 원본 이미지 대응

| 영역 | 기준 이미지 |
|---|---|
| 어플리케이션 분류 체계 | `18.png ~ 23.png` |
| 시스템 그룹 업무 구분 | `24.png ~ 31.png` |
| 데이터 주제영역 구성 정의 | `32.png, 33.png` |

## 중복 프레임

- `20.png` / `21.png`: BI포탈 어플리케이션 분류 체계 동일 내용
- `25.png` / `26.png`: 데이터플랫폼 시스템 그룹 업무 구분 동일 내용
- `29.png` / `30.png`: IT서비스 및 인프라지원 시스템 그룹 업무 구분 동일 내용

---

# 9. 주의사항

- `세부 업무 구분(기능)`은 원본 이미지에서 **“업무개발팀 분석, 설계단계 시 반영”**으로 되어 있으므로 임의로 확정하지 않는다.
- 서버명, 용어, 솔루션명은 이미지에서 판독한 값을 그대로 유지한다.
- 이미지 간 용어/코드가 상충하는 경우 본 통합본에서 임의로 수정하거나 재정의하지 않는다.
- 인프라 임시 시스템의 데이터 주제영역은 원본 이미지에서 별도 정의되지 않았다.
