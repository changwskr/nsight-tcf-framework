# 농협 상호금융 NSIGHT 통합 아키텍처 · 시스템구성 · 서버인벤토리 전체 논의 정리본

> 작성 기준: 2026-08-18까지 본 프로젝트에서 논의·분석한 이미지, 서버 인벤토리, 운영/DR/개발/선도개발 구성, 애플리케이션 코드, 서버명 규칙, WEB/WAS, 성능/용량, 보안/JWT, 데이터 이행, 요구사항, HW/SW 일정 및 PDMG/TCF 아키텍처 분석 결과를 통합 정리한다.  
> 원칙: 확인된 자료는 **FACT/WORKING BASELINE**, 추가 검증이 필요한 항목은 **OPEN/GAP**으로 구분하며 임의로 값을 채우지 않는다.

---

## 0. 문서 사용 목적

이 문서는 단순 서버목록이 아니라 다음을 한 체계로 연결하는 **NSIGHT 통합 Working Baseline**이다.

```text
Architecture Strategy
        ↓
Application / Business Classification
        ↓
Application Code / Server Naming
        ↓
Environment
선도개발 → 개발 → 운영 → DR
        ↓
Server / Middleware / Runtime
        ↓
CPU / MEM / Disk / tpmC / TPS
        ↓
JVM / Tomcat / Hikari / DB
        ↓
HA / DR / Migration / Operation
        ↓
Requirement / Evidence / Gap / Gate
```

최종적으로는 아래 질문에 답할 수 있어야 한다.

1. 어떤 애플리케이션과 업무가 존재하는가?
2. 각 업무는 어느 서버에서 실행되는가?
3. 서버명만 보고 법인·업무·OS·환경·역할·센터를 식별할 수 있는가?
4. 운영/DR/개발/선도개발 서버가 어떻게 대응되는가?
5. WEB/WAS/AP/DB가 어떤 미들웨어와 Runtime으로 구성되는가?
6. 각 서버의 CPU/MEM/Disk/tpmC/TPS는 적정한가?
7. 장애/DR 전환 시 서비스를 계속 처리할 수 있는가?
8. 문서·설정·소스·Runtime Evidence가 서로 추적 가능한가?

---

# 1. 프로젝트 아키텍처 방향

## 1.1 비전

NSIGHT의 지향점은 단순 DW 고도화가 아니라 **배치 중심 정보계를 실시간 병행형 데이터·마케팅 플랫폼으로 전환**하는 것이다.

핵심 방향:

- 데이터가 저장되는 시스템에서 **데이터가 흐르고 반응하는 시스템**으로 전환
- 배치와 실시간 처리 병행
- RDW와 ADW의 역할·부하 분리
- CDC/Kafka 기반 Near Real-time 데이터 흐름
- 마케팅/Single View/BI/거버넌스/운영지원 책임 분리
- 표준 인터페이스와 통제 가능한 Runtime
- 장애 격리와 Scale-Out
- SLA 기반 관측/운영

## 1.2 6단계 아키텍처 방법론

```text
Vision
  ↓
Big Picture
  ↓
Logical Architecture
  ↓
Physical Architecture
  ↓
Mechanism
  ↓
Runtime Validation
```

| 단계 | 핵심 질문 |
|---|---|
| Vision | 왜 구축하며 NFR/SLA는 무엇인가 |
| Big Picture | 어떤 영역과 책임/경계가 존재하는가 |
| Logical | 도메인·데이터·Integration 정책은 무엇인가 |
| Physical | 실제 서버/DB/네트워크/격리는 어떻게 배치되는가 |
| Mechanism | HTTP/JSON, TCF, CDC, Kafka, Batch 등이 어떻게 실행되는가 |
| Runtime | 실제 요청/장애/부하 상황에서도 설계가 성립하는가 |

## 1.3 Big Picture 핵심 영역

```text
Channel
  ↓
Interface / Standard Message
  ↓
Data Platform
  ├─ RDW
  └─ ADW
  ↓
Marketing / Analytics
  ├─ MP
  ├─ BL
  └─ DG
  ↓
Information Management / Operation
```

핵심 통제정책:

- HTTP/JSON 표준 통신
- 비표준 직접 연결 통제
- GUID/ServiceId 기반 추적
- P2P/DB Link 직접연결 지양 또는 통제
- RDW/ADW 물리·책임 분리
- 온라인 AP/배치/ETL/Event/IMDG 기능 분리
- 기능별 Fault Isolation

---

# 2. 애플리케이션 대그룹 체계

현재 운영/개발/코드정의 자료에서 확인되는 상위 애플리케이션 그룹은 다음과 같다.

| 코드 | 한글 영역 | 영문명 | 핵심 역할 |
|---|---|---|---|
| **MP** | 상호금융 마케팅플랫폼 | Marketing Platform | 마케팅, 고객, 상품, 캠페인, 실시간·행동정보 처리 |
| **RD** | 데이터플랫폼 RDW | Real-time Data Warehouse | 실시간/준실시간 데이터 |
| **AD** | 데이터플랫폼 ADW | Analytical Data Warehouse | 분석 데이터 |
| **BL** | BI포탈 | Business Analysis Layer | BI Portal, OLAP, Self-BI, 신용실적 |
| **DG** | 데이터거버넌스 | Data Governance | 메타, 품질, 데이터 흐름 |
| **IM** | 정보계관리/IT서비스 및 업무지원 | Information Management | Framework, 소스/배포, 단말, 배치, CDC, ETL, RD, IMDG |

전체 계층:

```text
NSIGHT
├─ MP Marketing Platform
├─ RD Real-time Data Warehouse
├─ AD Analytical Data Warehouse
├─ BL Business Analysis Layer
├─ DG Data Governance
└─ IM Information Management
```

---

# 3. 애플리케이션 업무 코드 체계

## 3.1 MP — Marketing Platform

| 업무 | 코드 | 영문 | 최종 4자리 코드 |
|---|---|---|---|
| 공통 | CO | Common | MPCO |
| 통합고객 | IC | Integration Customer | MPIC |
| 개인고객 | PC | Private Customer | MPPC |
| 기업고객 | BC | Business Customer | MPBC |
| 미니싱글뷰 | MS | Mini SingleView | MPMS |
| 상품판매 | SA | Sale | MPSA |
| 통합상품 | PD | Product | MPPD |
| 캠페인 | CM | Campaign | MPCM |
| EBM | EB | EBM | MPEB |
| 실시간 처리 | EP | Event Processing | MPEP |
| 행동정보 처리 | BP | Behavior Information Processing | MPBP |
| 고객행동 데이터 | BD | Customer Behavior Data | MPBD |
| 영업지원 | SS | Sales Support | MPSS |
| 고객서비스 | CS | Customer Service | MPCS |
| 컨텐츠 | CT | Contents | MPCT |
| 메시지 | MG | Message | MPMG |

## 3.2 RD — Real-time Data Warehouse

| 업무 | 코드 | 영문 | 최종코드 |
|---|---|---|---|
| 공통 | CO | Common | RDCO |
| 실시간 SoR | SR | Source of Record | RDSR |
| 준실시간 요약집계 | ZD | Zipped Data | RDZD |
| 준실시간 보고서마트 | RM | Report Data Mart | RDRM |
| 피드백 | FA | Feedback Area | RDFA |

## 3.3 AD — Analytical Data Warehouse

| 업무 | 코드 | 영문 | 최종코드 |
|---|---|---|---|
| 공통 | CO | Common | ADCO |
| 분석 SoR | SR | Source of Record | ADSR |
| 분석 통합요약집계 | ZD | Zipped Data Area | ADZD |
| 분석 단위업무마트 | UM | Unit-business Mart | ADUM |
| 분석 보고서마트 | RM | Report Data Mart | ADRM |
| 피드백 | FA | Feedback Area | ADFA |
| 분석지원 | DA | DW Analysis Assistance | ADDA |

## 3.4 BL — Business Analysis Layer

| 업무 | 코드 | 영문 | 최종코드 |
|---|---|---|---|
| BI포탈 | PT | Portal | BLPT |
| 신용실적 | CR | Credit Result | BLCR |
| OLAP | OA | Online Analysis Process | BLOA |
| Self BI | SB | Self Business Intelligence | BLSB |
| 신BI포탈 UI/UX | UI | UI/UX | BLUI |

## 3.5 DG — Data Governance

| 업무 | 코드 | 영문 | 최종코드 |
|---|---|---|---|
| 공통 | CO | Common | DGCO |
| 비즈메타 | BM | Biz-Meta System | DGBM |
| 데이터품질 | DQ | Data Quality | DGDQ |
| 데이터흐름 | DF | Data Flow | DGDF |

## 3.6 IM — Information Management

| 업무 | 코드 | 영문 | 최종코드 |
|---|---|---|---|
| 아키텍처관리 | AM | Architecture Management | IMAM |
| 시스템공통 | SC | System Common | IMSC |
| 배포 | DP | Deployment | IMDP |
| 프레임워크 | FW | Framework | IMFW |
| 라이브러리 | LB | Library | IMLB |
| 소스코드 버전관리 | SM | Source Code Version Management | IMSM |
| 정보단말 관리 | XM | UI/UX Management | IMXM |
| 정보단말 배포 | XD | UI/UX Deployment | IMXD |
| 배치작업 처리 | BJ | Batch Job Processing | IMBJ |
| 실시간 중계 | CD | CDC Gateway | IMCD |
| 데이터 치환 적재 | DT | Data Transform Load | IMDT |
| 보고서 디자이너 | RD | Report Designer | IMRD |
| 거래 공통 메모리 | IG | In Memory Data Grid | IMIG |

---

# 4. 서버명(Hostname) 표준

## 4.1 12자리 구조

```text
①② ③④⑤⑥ ⑦ ⑧ ⑨⑩ ⑪⑫
│    │     │  │   │    │
법인  업무   서버 운영  용도  순번
```

공식:

```text
[법인 2]
+ [업무 4]
+ [서버플랫폼 1]
+ [환경 1]
+ [용도 2]
+ [순번 2]
= 12자리
```

## 4.2 법인 코드

| 법인 | 코드 |
|---|---|
| 중앙회(상호금융 제외) | nh |
| 농협 | nb |
| **상호금융** | **sb** |
| 금융지주 | fg |
| 경제지주 | ag |

## 4.3 서버 플랫폼 코드

| 플랫폼 | 코드 |
|---|---|
| Unix-IBM | i |
| Unix-HP | h |
| Unix-Oracle | o |
| Linux(EMC/HP) | l |
| Windows(EMC/HP) | w |
| Exadata(Oracle) | x |

## 4.4 환경 코드

원 서버명 기준표에서 확인된 값:

| 환경 | 코드 |
|---|---|
| 운영 | o |
| 검증 | v |
| 개발 | t |

※ 선도개발 서버명도 `t`를 사용하는 사례가 확인되므로 **개발/선도개발의 논리 환경은 별도 인벤토리 컬럼으로 구분**하고 Hostname의 환경코드만으로 두 환경을 단정하지 않는다.

## 4.5 용도 코드

| 역할 | 코드 |
|---|---|
| AP | ap |
| DB | db |
| WEB | wb |
| WAS | ws |
| Backup | bk |
| Batch | bt |
| ETL | tl |

## 4.6 순번과 센터

| 범위 | 센터/의미 |
|---|---|
| 01~49 | 주센터 |
| 51~99 | DR센터 |

대표 예:

```text
sbmpcolowb01
= sb | mpco | l | o | wb | 01

상호금융
+ Marketing Platform/Common
+ Linux
+ 운영
+ WEB
+ 주센터 #01
```

DR:

```text
sbmpcolowb51
= 동일 논리 서버의 DR #51
```

대표 매핑:

```text
sbmpcolowb01 ↔ sbmpcolowb51
sbmpcolowb02 ↔ sbmpcolowb52

sbmpcolows01 ↔ sbmpcolows51
sbmpcolows02 ↔ sbmpcolows52

sbrdcoxodb01 ↔ sbrdcoxodb51
sbrdcoxodb02 ↔ sbrdcoxodb52
```

---

# 5. 환경별 시스템 구성 체계

## 5.1 운영 시스템

운영계는 다음 영역을 포함한다.

| 코드 | 주요 운영 구성 |
|---|---|
| MP | 마케팅 WEB/WAS, Mini SingleView WEB/WAS, 실시간 AP, 행동정보 AP, 고객행동데이터 AP |
| RD | RDW Appliance |
| AD | ADW Appliance |
| DG | 데이터품질/비정형, 데이터흐름 |
| BL | OLAP, BI Portal, Self-BI, 신용실적 |
| IM | Framework, 단말관리/배포, Batch, CDC, ETL, 보고서, IMDG |
| IM 임시 | 데이터 이행용 임시장비 |

운영 기본 흐름:

```text
Client
  ↓
WEB / Apache
  ↓
WAS / Tomcat
  ↓
Application
  ↓
RDW / ADW / Integration
```

MP 운영 주요 구성:

- 마케팅플랫폼 WEB #01/#02
- 마케팅플랫폼 WAS #01/#02
- 미니싱글뷰 WEB #01/#02
- 미니싱글뷰 WAS #01/#02
- 실시간 처리 AP #01/#02
- 행동정보 처리 AP #01/#02
- 고객행동 데이터 AP #01~#03

RD:

- RDW Appliance #01/#02

AD:

- ADW Appliance #01~#06

DG:

- 데이터품질 계열 WAS
- 데이터흐름 계열 WAS

BL:

- OLAP
- BI Portal
- Self-BI
- 신용실적

IM:

- NH Cloud Framework 관리
- 소스/배포/라이브러리
- 단말관리/단말배포
- Batch
- CDC/OGG
- ETL
- 출력물/보고서
- IMDG

## 5.2 DR 시스템

DR 화면에서 확인된 주요 구성:

### MP
- 마케팅 WEB #51/#52
- 마케팅 WAS #51/#52
- 미니싱글뷰 WEB #51/#52
- 미니싱글뷰 WAS #51/#52

### RD
- RDW Appliance #51/#52

### IM
- NH Cloud Framework 관리
- 단말관리 WAS #51
- 단말배포 WAS #51
- 단말관리 DB
- RDW Exadata 활용
- IMDG AP #51 / Redis 표기

DR 특성:

- 운영 전체를 동일 수량으로 복제한 구조는 아님
- MP/RDW 핵심 온라인은 2↔2 대응이 명확
- 일부 IM은 운영 2대 대비 DR 1대로 축소
- DR 대상/비대상/공유자원/축소구성 정책을 Master에 별도 관리해야 함

## 5.3 개발 시스템

개발 구성표에서 확인되는 영역:

- MP Marketing Platform
- RD Real-time Data Warehouse
- AD Analytical Data Warehouse
- DG Data Governance
- BL Business Analysis Layer
- IM Information Management
- 이행용 임시장비

개발 환경은 운영보다 다양한 구축/솔루션 검증 요소를 포함한다.

대표 구성:

- MP WEB/WAS
- 관리자/캠페인/고객행동 계열 AP
- RDW/ADW
- 데이터거버넌스
- OLAP/BI Portal/Self-BI/신용실적
- GitLab/GitRunner
- Framework 관리
- WebTopSuite
- Batch/ETL/RD
- 데이터 이행용 AP

## 5.4 선도개발 시스템

선도개발은 전체 시스템 복제가 아니라 **Reference Architecture/기술 검증용 최소 환경**으로 분석된다.

| 영역 | 구성 |
|---|---|
| MP | WEB #01, WAS #01 |
| RD | 개발환경 RDW Appliance 활용 |
| IM | GitLab, GitRunner, Framework 관리, Nexus, 단말관리/배포 |

확인된 Hostname 예:

| 대상 | Hostname | 비고 |
|---|---|---|
| MP WEB #01 | `sbmpcoltwb01` | Apache |
| MP WAS #01 | `sbmpcoltws01` | Tomcat, NH Cloud Framework |
| 소스관리 AP #01 | `sbimsmltap01` | GitLab |
| 배포관리 AP #01 | `sbimdpltap01` | GitRunner |
| 단말관리 WAS #01 | `sbimxmltws01` | WebTopSuite |
| 단말배포 WAS #01 | `sbimxdltws01` | WebTopSuite |

선도개발 의미:

```text
Architecture / Framework Design
        ↓
선도개발 Reference 검증
        ↓
Architecture Baseline
        ↓
본 개발
        ↓
시험/검증
        ↓
운영
        ↓
DR
```

---

# 6. 전체 서버 인벤토리 관리 모델

최종 기준은 **서버 1대 = Master Inventory 1행**이다.

## 6.1 필수 관리 컬럼

| 영역 | 필수 항목 |
|---|---|
| 조직 | 법인명, 법인코드 |
| Application | 대그룹, 코드, 영문명 |
| 업무 | 업무명, 업무코드, Application Code |
| 시스템 | 시스템그룹, 세부시스템 |
| 식별 | 대상서버명, Hostname |
| 환경 | 선도개발/개발/검증/운영/DR/임시 |
| 센터 | 주센터/DR센터/센터명 |
| 플랫폼 | 제조사, x86/Unix/Exadata, OS |
| 역할 | WEB/WAS/AP/DB/Batch/ETL/CDC |
| Compute | CPU Core, 수정 Core, Memory |
| Storage | OS Disk, Data Disk, Log Disk, Mount |
| Capacity | tpmC, Core당 TPS, 서버 TPS, Peak TPS |
| Middleware | Apache, Tomcat, WebTopSuite 등 |
| Framework | NH Cloud Framework/TCF |
| JVM | Xms/Xmx, GC, Xss, Metaspace |
| WAS | maxThreads, minSpareThreads, acceptCount, Port |
| DB | Hikari Pool, Session, DB 대상 |
| Network | IP, VIP, L4, SCAN, DataGuard, Port |
| HA | HA Group, Active/Standby, 장애 잔여용량 |
| DR | DR 대상, 대응 Hostname, RTO/RPO |
| Lifecycle | 신규/유지/삭제/삭제검토/임시 |
| Evidence | 출처, 확인일, 상태, 비고 |

## 6.2 71대 Physical Server Baseline

현재 사진 기반 상세 판독 결과는 기존 70대 초안에서 **71대**로 보정되었다.

대표 자원값:

| 서버군 | CPU | 수정CPU | MEM | OS Disk | 추가 Disk | tpmC |
|---|---:|---:|---:|---:|---:|---:|
| 마케팅 WEB #01/#02 | 11 | 12 | 48GB | 250GB | 100GB | 1,130,017 |
| 마케팅 WAS #01/#02 | 35 | 32 | 256GB | 250GB | 110GB | 3,849,561 |
| Mini SingleView WEB #01/#02 | 7 | 8 | 32GB | 250GB | 100GB | 751,802 |
| Mini SingleView WAS #01/#02 | 42 | 32 | 256GB | 250GB | 110GB | 4,603,836 |
| BI Portal WEB #01/#02 | 4 | 4 | 16GB | 250GB | 100GB | 362,070 |
| BI Portal WAS #01/#02 | 7 | 8 | 64GB | 250GB | 110GB | 706,045 |
| 신용실적 WEB #01/#02 | 2 | 2 | 8GB | 250GB | 100GB | 143,730 |
| 신용실적 WAS #01/#02 | 6 | 6 | 48GB | 250GB | 110GB | 587,235 |
| ETL #01/#02 | 64 | 64 | 512GB | 확인필요 | 확인필요 | 6,318,317 |
| CDC 중계 DB #01/#02 | 8 | 8 | 384GB | 800GB | - | 은행기준 |
| RDW Appliance #01/#02 | 96 | 96 | 1,024GB | - | 249,856GB | 24,387,550 |
| ADW Appliance #01~#06 | 384 | 384 | 3,072GB | - | 1,073,152GB | 47,818,722 |

주의:

- Appliance 자원값이 **개별 노드 값인지 서버군 전체값인지 원본 Excel로 재검증 필요**
- tpmC는 서버군 산정값과 개별 서버값을 혼동하지 않아야 함
- tpmC와 TPS는 동일 지표가 아님
- Capacity Requirement와 실제 Inventory Resource를 별도 컬럼으로 관리

## 6.3 인벤토리 핵심 관계

```text
Application
  ↓
Business
  ↓
Application Code
  ↓
Server
  ↓
Hostname
  ↓
CPU / MEM / Disk
  ↓
Middleware
  ↓
JVM / Thread / Pool
  ↓
DB
  ↓
HA / DR
```

---

# 7. WEB/WAS 미들웨어 아키텍처

## 7.1 기본 원칙

- WEB: Apache HTTP Server
- WAS: Tomcat
- Framework: NH Cloud Framework / TCF 연계
- Apache 1 Instance에서 복수 Listen Port 사용 가능
- 포트별 VirtualHost/Proxy를 통해 서로 다른 Tomcat JVM으로 라우팅 가능

예:

```text
Apache
├─ :9000 → Tomcat JVM #1 :19000
├─ :9001 → Tomcat JVM #2 :19001
├─ :9010 → Tomcat JVM #3 :19010
└─ :9011 → Tomcat JVM #4 :19011
```

## 7.2 Container 정의

현재 논의에서 중요한 정의:

> 구성도에서 `Container 1개 = 독립 Tomcat JVM Instance 1개`

즉:

```text
WAS Server / VM
├─ Tomcat JVM #1
│  ├─ CATALINA_BASE #1
│  ├─ Connector Port
│  ├─ Heap/GC/Thread
│  └─ Application
├─ Tomcat JVM #2
└─ ...
```

따라서 `WAS Server`와 `Tomcat JVM Instance`를 같은 것으로 보면 안 된다.

## 7.3 시스템별 패턴

- 마케팅플랫폼 운영: WEB 2 + WAS 2, WAS당 복수 Tomcat JVM
- Mini SingleView 운영: WEB 2 + WAS 2, WAS당 복수 JVM
- BI Portal: WEB/WAS 이중화
- 신용실적: WEB/WAS + 다중 JVM 패턴
- OLAP: WEB/WAS/AP 역할 분리
- 단말관리/배포: WebTopSuite 기반
- 보고서디자이너: 일부 구성은 WEB/WAS 동일 서버

---

# 8. WEB/WAS 기본사양과 실제 용량

최소사양 화면 기준:

| 역할 | 최소 CPU | 최소 MEM | Disk |
|---|---:|---:|---:|
| WEB | 2 vCPU | 16GB | 60GB |
| WAS | 4 vCPU | 16GB | 70GB |

주의:

> 최소사양 ≠ 운영 용량산정 사양

운영에서는 실제 TPS/Thread/Pool/Heap에 따라 8C, 16C, 32C 등 별도 산정한다.

---

# 9. 성능·용량 아키텍처

## 9.1 Capacity Chain

```text
전체 사용자
  ↓
로그인 세션
  ↓
동시 요청자
  ↓
목표 TPS
  ↓
AP/VM 수량
  ↓
WAS Thread
  ↓
DB Connection Pool
  ↓
DB Session
  ↓
장애 시 잔여 처리량
```

## 9.2 사용자/세션 기준

현재 논의된 대표 기준:

- 지점: 6,000
- 지점당 사용자: 6
- 전체 사용자: 36,000
- 세션 설계: 36,000
- 여유율 20~30%: 약 43,000~47,000
- p95 목표 응답시간: 3초
- 세션 Timeout 자료에는 60분/90분 버전이 공존
- 최신 성능 Working Baseline에서는 90분 사용

세션 수와 동시 요청자는 반드시 분리한다.

```text
Session = 로그인 유지 규모
Concurrent Request = TPS/Thread/DB Pool 산정 규모
```

## 9.3 TPS 시나리오

대표 시나리오:

| 동시요청률 | 동시요청자 | 3초 기준 TPS |
|---:|---:|---:|
| 3% | 1,080 | 360 |
| 5% | 1,800 | 600 |
| 10% | 3,600 | 1,200 |
| 15% | 5,400 | 1,800 |

## 9.4 Working Performance Baseline

현재 최신 작업자료 기준:

| 영역 | 항목 | Working Baseline |
|---|---|---:|
| 사용자 | 전체 사용자 | 36,000 |
| 세션 | 설계 Session | ≥43,200 |
| 세션 | Idle Timeout | 90분 |
| SLA | 일반 Peak | 600 TPS |
| SLA | 설계 Peak | 1,200 TPS |
| SLA | Stress | 1,800 TPS |
| SLA | 응답시간 | p95 ≤ 3초 |
| VM | CPU | 16 vCPU |
| VM | Memory | 64GB |
| VM | Working Capacity | 약 855 TPS |
| VM | 80% 운영 Capacity | 약 684 TPS |
| JVM | Heap | 약 24GB Working |
| JVM | Heap Usage | ≤70% |
| JVM | GC Pause | ≤200ms |
| OS | CPU | 평균 ≤70% |
| Tomcat | maxThreads | 초기 800 |
| Tomcat | 시험범위 | 800~1,000 |
| Tomcat | minSpareThreads | 200 |
| Tomcat | acceptCount | 800 |
| Hikari | 일반 Pool | 120~150 |
| DB | Query Timeout | 2~3초 |
| Spring | Transaction Timeout | 4~5초 |
| WEB | Request Timeout | 6~8초 |
| HA | 센터 구성 | 2 VM + 2 VM |

### 주의: 역사적 기준과 최신 Working 기준

기존 보수 산정 문서:

- 8C ≈ 250 TPS
- 16C ≈ 500 TPS
- 32C ≈ 1,000 TPS

최근 성능 작업:

- 16C/64GB ≈ 855 TPS Working Capacity

따라서 **500 TPS와 855 TPS를 하나의 확정값으로 혼합하면 안 된다.**
최종 Capacity Baseline은 성능시험으로 확정한다.

## 9.5 DB Pool 공식

```text
DB Pool
= TPS
× DB Connection Hold Time
× DB Usage Ratio
× Safety Factor
```

실무적으로는:

```text
Final Pool
= max(
    운영 최소값,
    min(
        WAS Thread 기반 상한,
        TPS 기반 산정값
    )
)
```

원칙:

- Pool이 너무 작으면 Hikari 대기 증가
- Pool이 너무 크면 DB Session 폭증
- Thread와 Pool의 정합성 검증 필수

## 9.6 Timeout Chain

권장 원칙:

```text
DB Query Timeout
<
Transaction Timeout
<
WEB/Integration Timeout
<
Client Timeout
```

예:

```text
DB Query       2~3 sec
Transaction    4~5 sec
WEB Request    6~8 sec
```

---

# 10. JVM 메모리 원칙

16C/128GB와 같은 대용량 VM에서도 전체 Memory를 Heap으로 사용하지 않는다.

예시 원칙:

- 일반 AP Heap 24~32GB
- SingleView AP 32~40GB 범위 검토
- 나머지는 OS, Native, Thread Stack, Agent, Buffer, Dump/장애분석 여유

핵심:

> 메모리 증설 자체가 TPS 증가를 의미하지 않는다.

CPU Core와 처리시간이 1차 처리량을 결정하며 Memory는 안정성/GC/캐시/대용량 조립 여유를 제공한다.

---

# 11. HA / DR

## 11.1 운영 이중화

대표 온라인 영역:

```text
L4/GSLB
  ↓
WEB #01 / #02
  ↓
WAS #01 / #02
  ↓
RDW/DB
```

## 11.2 DR 번호규칙

```text
운영 #01 → DR #51
운영 #02 → DR #52
```

## 11.3 DR 설계 시 관리 항목

- DR 대상 여부
- 1:1 / 축소 / 공유 구성
- Active/Standby 또는 Active-Active 여부
- RTO
- RPO
- 데이터 동기화
- Fail-Over
- Fail-Back
- 장애 시 잔여 Capacity

현재 DR 구성표에는 RTO/RPO가 직접 명시되어 있지 않으므로 **OPEN**이다.

---

# 12. TCF / Application Runtime 아키텍처

PDMG Reference 분석에서 확인된 기본 거래 흐름은 다음과 같다.

```text
Client
  ↓
Filter
  ↓
Interceptor
  ↓
OnlineTransactionController
  ↓
TcfFacade
  ↓
STF
  ↓
Timeout Executor
  ↓
Transaction
  ↓
ServiceId Dispatcher
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper / SQL
  ↓
DB
  ↓
ETF / Response / Error
```

## 12.1 책임 분리

| 계층 | 책임 |
|---|---|
| Filter/Interceptor | HTTP, 인증, Header, Context, Log |
| TCF | 거래 실행 생명주기 |
| STF | 거래 선처리/통제 |
| Timeout | 실행시간 제한 |
| Dispatcher | ServiceId → Handler |
| Handler | Use Case 진입 |
| Facade | 거래/Use Case 경계 |
| Service | 실제 업무 절차 |
| Rule | 업무 판단/정책 |
| DAO | DB 접근 경계 |
| Mapper | SQL 실행 |
| ETF | 거래 후처리 |

## 12.2 ServiceId 중심 추적성

```text
ServiceId
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
  ↓
SQL
  ↓
Table
```

Application/업무/패키지/클래스/DTO/SQL/로그까지 동일 분류축을 사용하는 방향이 핵심이다.

---

# 13. 표준전문

표준전문은 단순 JSON Body가 아니라 거래 추적/통제용 Envelope이다.

PDMG Reference 구조:

```text
Request
├─ hdr_nhnis
│  └─ sys_comm
└─ dto
```

오류 응답:

```text
Response
├─ hdr_nhnis
└─ result
```

공통 Header에는 GUID, ServiceId, 시스템, 화면, 점코드, 사용자, 단말IP 등 거래 추적정보가 포함된다.

원칙:

- Header → Framework/Context 책임
- DTO → 업무 Application 책임
- 업무 Service까지 전체 전문을 직접 전달하지 않음
- GUID + ServiceId로 End-to-End 추적

---

# 14. JWT / SSO 인증 구조

논의된 인증 방향:

```text
사용자
  ↓
SSO / IdP
  ↓
JWT 발급 서버
  ├─ KMS Private Key
  ├─ Access Token
  └─ Refresh Token
  ↓
UI
  ↓
Authorization: Bearer JWT
  ↓
Backend / Gateway
  ↓
Public Key / JWKS 검증
  ↓
업무 서비스
```

핵심 정책:

- RS256 계열 비대칭키
- Private Key는 발급 영역에서 보호
- Public Key/JWKS는 검증 영역에 배포
- Access/Refresh 분리
- KMS 활용
- 만료/Refresh/Revoke 처리
- Gateway 미경유 호출 방어 필요
- SSO 세션과 JWT 역할을 명확히 분리

---

# 15. Logging / Observability

## 15.1 거래 추적키

```text
GUID + ServiceId
```

## 15.2 Logging 구간

- System Logging
- TCF/Transaction Logging
- Business Logging
- Error Logging
- ImageLog(PRE/POST/EXCEPTION)
- Runtime Evidence

## 15.3 ImageLog

ImageLog는 일반 로그가 아니라 요청/응답/오류 시점의 핵심 전문과 실행 문맥을 거래 증적으로 남기는 구조이다.

운영 목표:

```text
Request
  ↓
GUID/ServiceId
  ↓
Framework
  ↓
Business
  ↓
DB
  ↓
Response/Error
  ↓
하나의 거래로 검색 가능
```

---

# 16. 데이터 이행 아키텍처

전체 방향:

```text
AS-IS
  ↓
추출
  ↓
STAGE
  ↓
정제 / 변환 / 검증
  ↓
TARGET
  ↓
ADW / ETL / 최종 시스템
  ↓
사전이행
  ↓
본이행
```

주요 구성:

- AS-IS DW/ODS/OLAP/실적 등
- 이행서버
- SQL 기반 이행
- STAGE
- Oracle/Exadata
- DataStage/ETL
- 재수행/오류 격리/검증

원칙:

- AS-IS 운영 DB 직접 훼손 금지
- STAGE 경계 확보
- 추출→정제→검증→적재 단계화
- 개발/통합/영업점/사전이행/본이행 환경별 발전

---

# 17. HW/SW 도입 일정 관점

일정은 환경별로 관리한다.

```text
계획
  ↓
SR 요청
  ↓
HW/SW 도입
  ↓
환경 구축
  ↓
솔루션 설치
  ↓
구성점검
```

환경:

- 개발
- 운영
- DR

대표 품목:

- IaaS x86
- Exadata
- Disk
- Cloud Management
- WebTopSuite
- GT-One 계열
- Oracle/DB
- GoldenGate/CDC
- ETL
- Middleware

인벤토리에 `도입차수 / 설치목표 / 설치상태 / 검수상태` 컬럼을 추가할 수 있다.

---

# 18. 요구사항 체계

현재 분석된 요구사항은 `RT-IA-0001 ~ RT-IA-0021` 범위이다.

주요 요구사항 축:

- 현행 운영환경 제시
- 운영 아키텍처 도식화
- Fail-Over/Fail-Back
- 개발환경 구축
- 개발 아키텍처
- 개발표준
- 확장성
- 성능/용량
- 데이터
- 운영/관리

권장 Traceability:

```text
Requirement
  ↓
Architecture Decision
  ↓
Logical/Physical Design
  ↓
Configuration
  ↓
Test
  ↓
Runtime Evidence
```

---

# 19. Architecture as Closed Loop

NSIGHT가 지향하는 최종 관리 방식:

```text
Architecture as Document
        ↓
Architecture as Model
        ↓
Architecture as Code
        ↓
Architecture as Test
        ↓
Architecture as Runtime Evidence
        ↓
Drift
        ↓
Gap / ADR
        ↓
New Baseline
```

아키텍처는 문서를 작성하는 것으로 끝나지 않고 실제 소스/설정/Runtime으로 검증한다.

---

# 20. Architecture Rule 핵심

## 20.1 구조 규칙

- ServiceId Unique
- ServiceId → Handler 매핑
- Handler → DAO 직접호출 금지
- Controller → DAO 직접호출 금지
- Facade → Service
- Service → DAO
- DAO ↔ Mapper Namespace 정합
- 업무코드 ↔ Package ↔ ServiceId 정합

## 20.2 Framework 규칙

- TCF 거래 생명주기 준수
- STF/ETF 책임분리
- Timeout/Transaction 경계 명확화
- Dispatcher는 ServiceId 기반

## 20.3 Security 규칙

- Private Key 보호
- Public/JWKS 기반 검증
- JWT 만료/Refresh/Revoke
- 인증/인가 경계 명확화

## 20.4 Domain 규칙

- 타 도메인 DAO/Mapper 직접호출 금지
- 도메인 간 공개 ServiceId/API 사용
- 데이터 변경은 소유 도메인 책임
- 순환 동기호출 지양/금지

---

# 21. 현재 미확정 GAP / OPEN

다음은 최종 Baseline 확정을 위해 추가 확인이 필요하다.

| ID | 항목 | 상태 |
|---|---|---|
| GAP-01 | 71대 전체 CPU/MEM/Disk 원본 Excel 셀 단위 재검증 | OPEN |
| GAP-02 | RDW/ADW Appliance 값이 노드별인지 전체인지 확인 | OPEN |
| GAP-03 | 일부 Hostname 미기재/중복 확인 | OPEN |
| GAP-04 | Application/WAR → WAS/JVM 매핑 완성 | OPEN |
| GAP-05 | JVM Instance별 Port/Heap/Thread 매핑 | OPEN |
| GAP-06 | Hikari Pool/DB Session 서버별 매핑 | OPEN |
| GAP-07 | IP/VIP/L4/SCAN/DataGuard 전체 연결 | OPEN |
| GAP-08 | DR 대상/비대상/축소/공유 정책 확정 | OPEN |
| GAP-09 | RTO/RPO 확정 | OPEN |
| GAP-10 | 500 TPS 보수기준 vs 855 TPS Working 기준 성능시험 확정 | OPEN |
| GAP-11 | 세션 60분/90분 문서 충돌 최종 기준 확정 | OPEN |
| GAP-12 | 삭제/삭제검토 서버 최종 Lifecycle 승인 | OPEN |
| GAP-13 | 개발/선도개발의 Hostname `t` 환경코드 세부 구분 | OPEN |
| GAP-14 | 선도개발 → 개발 → 운영 → DR 논리 서버 매핑표 완성 | OPEN |
| GAP-15 | tpmC 서버군 요구량과 개별 Inventory 값 분리 검증 | OPEN |

---

# 22. 최종 Master Inventory 권장 컬럼

```text
No
Environment
Center
Corporation
CorporationCode
ApplicationGroup
ApplicationGroupCode
Business
BusinessCode
ApplicationCode
SystemGroup
System
TargetServer
Hostname
ServerPlatform
OS
ServerType
Role
Sequence
Lifecycle
CPUCore
AdjustedCPUCore
MemoryGB
OSDiskGB
DataDiskGB
LogDiskGB
TPMC
CoreTPS
ServerTPS
PeakTPS
Middleware
Framework
TomcatInstance
ConnectorPort
JVMXms
JVMXmx
GC
MaxThreads
MinSpareThreads
AcceptCount
HikariPool
DBTarget
DBSession
IP
VIP
L4
SCAN
DataGuardIP
HAGroup
DRTarget
DRHostname
RTO
RPO
Evidence
VerificationStatus
Remark
```

---

# 23. 환경간 Mapping Master

최종적으로는 다음 형태의 환경 추적표를 별도로 가진다.

| 논리 서비스 | 선도개발 | 개발 | 운영 | DR |
|---|---|---|---|---|
| MP Common WEB | `sbmpcoltwb01` | DEV Host | `sbmpcolowb01/02` | `sbmpcolowb51/52` |
| MP Common WAS | `sbmpcoltws01` | DEV Host | `sbmpcolows01/02` | `sbmpcolows51/52` |
| RDW | 개발 RDW 활용 | DEV RDW | `sbrdcoxodb01/02` | `sbrdcoxodb51/52` |
| IM Source | `sbimsmltap01` | DEV | 운영 관리환경 | 필요 시 DR |
| IM Deployment | `sbimdpltap01` | DEV | 운영 관리환경 | 필요 시 DR |
| IM Terminal | `sbimxmltws01` | DEV | `sbimxmlows01/02` | `sbimxmlows51...` |

※ 개발 Hostname 전체는 원본 개발 구성표에서 최종 재검증 필요.

---

# 24. 최종 아키텍처 관점

현재까지의 자료를 하나로 합치면 NSIGHT의 구조는 다음과 같다.

```text
                         [사용자/채널]
                              │
                              ▼
                     GSLB / L4 / Apache
                              │
                              ▼
                     Tomcat JVM Instances
                              │
                              ▼
                    Application / ServiceId
                              │
                 ┌────────────┴────────────┐
                 ▼                         ▼
             TCF Runtime              Business Layer
      Filter/Interceptor/STF       Handler/Facade/Service
       Timeout/Transaction          Rule/DAO/Mapper
                 │                         │
                 └────────────┬────────────┘
                              ▼
                       RDW / Integration
                         │          │
                         │          ├─ CDC
                         │          ├─ Kafka
                         │          ├─ ETL
                         │          └─ External API
                         ▼
                  RDW / ADW / Data
                              │
                              ▼
                      BI / Governance
                              │
                              ▼
                   Operation / Evidence
```

Physical/Runtime 측면:

```text
Application
   ↓
WAS Server / VM
   ↓
Tomcat JVM
   ↓
Port / Application
   ↓
CPU / MEM / Heap / Thread
   ↓
Hikari / DB Session
   ↓
RDW / ADW
   ↓
HA / DR
```

---

# 25. 현재 결론

현재까지의 논의를 기준으로 다음은 상당히 명확해졌다.

### 확정 또는 Working Baseline으로 사용할 수 있는 영역

- NSIGHT 6단계 아키텍처 방법론
- MP/RD/AD/BL/DG/IM 대그룹
- 애플리케이션 4자리 코드 구조
- 서버명 12자리 구조
- 운영 #01~49 / DR #51~99 번호 정책
- 운영/DR/개발/선도개발 환경의 역할 차이
- Apache WEB + Tomcat WAS 기본 구조
- Container = 독립 Tomcat JVM Instance 정의
- ServiceId 중심 TCF/Application 구조
- 표준전문/Header/DTO 분리
- JWT/SSO/KMS/JWKS 방향
- 사용자→TPS→Thread→Pool Capacity Chain
- 71대 Physical Inventory의 1차 기준
- CPU/MEM/Disk/tpmC 대표 매핑
- Data Migration의 AS-IS→STAGE→TARGET 구조

### 최종 확정을 위해 남은 핵심

- 원본 Excel 기준 71대 자원 재검증
- WAR/Application/JVM/Port 실제 매핑
- 서버별 Hikari/DB Session
- 네트워크/VIP/Port
- DR RTO/RPO
- 최신 Capacity 숫자 성능시험 검증
- 선도개발/개발/운영/DR 전체 환경 매핑
- Architecture Gate를 통한 최종 Baseline 승인

---

# 26. 권장 최종 산출물 구조

```text
NSIGHT-ARCHITECTURE/
│
├─ 01-STRATEGY/
│  ├─ ARCHITECTURE-VISION.md
│  ├─ NFR-SLA.md
│  └─ BIG-PICTURE.md
│
├─ 02-APPLICATION/
│  ├─ APPLICATION-CODE-MASTER.md
│  ├─ BUSINESS-CLASSIFICATION.md
│  ├─ SERVICEID-MASTER.md
│  └─ STANDARD-MESSAGE.md
│
├─ 03-PHYSICAL/
│  ├─ SERVER-MASTER-INVENTORY.md
│  ├─ ENVIRONMENT-MAPPING.md
│  ├─ NETWORK-MAPPING.md
│  └─ HA-DR.md
│
├─ 04-MIDDLEWARE/
│  ├─ WEB-APACHE.md
│  ├─ WAS-TOMCAT.md
│  └─ JVM-RUNTIME.md
│
├─ 05-CAPACITY/
│  ├─ TPMC-TPS.md
│  ├─ THREAD-POOL.md
│  └─ PERFORMANCE-BASELINE.md
│
├─ 06-DATA/
│  ├─ RDW-ADW.md
│  ├─ CDC-KAFKA-ETL.md
│  └─ DATA-MIGRATION.md
│
├─ 07-SECURITY/
│  └─ SSO-JWT.md
│
├─ 08-OPERATION/
│  ├─ LOGGING-OBSERVABILITY.md
│  ├─ OPERATION-CONTROL.md
│  └─ HW-SW-INTRODUCTION.md
│
└─ 09-GOVERNANCE/
   ├─ REQUIREMENT-TRACEABILITY.md
   ├─ GAP-REGISTER.md
   ├─ ADR.md
   └─ ARCHITECTURE-GATE.md
```

---

## 부록 A. 핵심 용어

| 용어 | 의미 |
|---|---|
| Application Group | MP/RD/AD/BL/DG/IM 상위 영역 |
| Application Code | 대그룹 2자리 + 업무 2자리 |
| Hostname | 12자리 서버명 표준 |
| WAS Server | Tomcat이 실행되는 VM/서버 |
| Tomcat JVM Instance | 독립 Java Process / Container |
| ServiceId | 온라인 거래 논리 주소 |
| TCF | 거래 실행 Framework Runtime |
| STF/ETF | 거래 선/후처리 |
| tpmC | 벤치마크 기반 용량 지표 |
| TPS | 실제 초당 처리량 |
| Hikari | DB Connection Pool |
| RDW | Real-time Data Warehouse |
| ADW | Analytical Data Warehouse |
| DR | 재해복구 환경 |

---

## 부록 B. 문서 상태 표기

앞으로 모든 자료는 다음 상태를 사용한다.

- **FACT**: 자료/소스/설정에서 직접 확인
- **WORKING BASELINE**: 현재 설계·작업 기준
- **DECISION**: 승인된 아키텍처 결정
- **PROPOSAL**: 권고안
- **OPEN**: 추가 확인 필요
- **GAP**: 기준 대비 불일치
- **DEPRECATED**: 더 이상 기준으로 사용하지 않음

---

# 끝

본 문서는 2026-08-18까지 NSIGHT 관련 논의의 통합 정리본이며, 향후 원본 Excel/설정/소스/Runtime Evidence가 확보될 때 **FACT와 WORKING BASELINE을 분리 갱신**하는 것을 원칙으로 한다.
