# NSIGHT 물리 기술 아키텍처 구성내역

## 0. 문서 개요

본 문서는 사용자가 제공한 **물리 기술 아키텍처 장표(페이지 78~87)**를 기준으로,
NSIGHT의 **운영 / DR / 개발 / 선도 환경**, 주요 **서버·솔루션·소프트웨어 구성**,
그리고 **RDW / ADW 데이터베이스 아키텍처**를 하나의 구성내역으로 통합 정리한 문서이다.

> 작성 원칙  
> - 장표에서 확인되는 값을 우선 기록한다.  
> - 장표에 없는 값은 임의로 보완하지 않는다.  
> - 판독이 불명확하거나 장표 간 값이 달라 보이는 항목은 `확인필요`로 표시한다.  
> - 운영 HA와 센터 간 DR은 서로 다른 가용성 계층으로 구분한다.

---

# 1. 전체 물리 기술 아키텍처

```text
                         NSIGHT 물리 기술 아키텍처
┌─────────────────────────────────────────────────────────────────────┐
│                         환경 구성                                   │
│                                                                     │
│      운영                 DR                개발             선도   │
│       │                   │                  │                │     │
│       ├─ x86/Linux/IaaS   ├─ x86/Linux      ├─ x86/Linux     │     │
│       ├─ IBM/UNIX         └─ RDW Exadata    ├─ 개발도구      │     │
│       └─ Oracle Exadata                      └─ RDW            │     │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────┐
│                      Runtime / Middleware / Solution                 │
│                                                                     │
│ Red Hat / AIX / Oracle Linux                                        │
│ JDK / Apache / Tomcat / NH Framework                                │
│ WebTopSuite / Kafka / GoldenGate / DataStage                        │
│ BI Matrix / DO Miner / Meta Miner / Data Hawk                       │
│ GitLab / GitLab Runner / RD / OpenPOP / SQL Canvas                  │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────┐
│                         DATA PLATFORM                               │
│                                                                     │
│                  RDW                       ADW                       │
│          Exadata RAC 이중화             Exadata RAC 이중화           │
│                                                                     │
│       실시간/준실시간 영역             분석/BI/거버넌스 영역          │
└─────────────────────────────────────────────────────────────────────┘
```

---

# 2. 환경별 구성 요약

| 환경 | 센터/위치 | 주요 플랫폼 | 주요 시스템 | 특징 |
|---|---|---|---|---|
| 운영 | 의왕 | x86/Linux/IaaS, IBM/UNIX, Oracle Exadata | 마케팅플랫폼, 데이터거버넌스, IT서비스/업무지원, RDW, ADW, 이행 | 전체 서비스 운영환경 |
| DR | 안성 | x86/Linux/IaaS, Oracle Exadata | 마케팅플랫폼, 미니싱글뷰, 단말관리/배포, 마스터솔루션, RDW | 핵심 서비스 재해복구 |
| 개발 | 의왕 | x86/Linux/IaaS, Oracle Exadata | 마케팅, 신BI포털, 데이터거버넌스, IT지원, 소스/배포, RDW | 개발·통합·검증 |
| 선도 | 의왕 | x86/Linux/IaaS, Oracle Exadata | 마케팅 공통, 단말, 개발도구, 소스관리, 마스터솔루션, RDW | 최소 선도개발/검증 환경 |

---

# 3. 운영 환경 구성내역

## 3.1 운영 환경 기본 기준

| 항목 | 값 |
|---|---|
| 운영구분 | 운영 |
| 센터 | 의왕 |
| x86 OS | Linux |
| x86 서버 Type | IaaS |
| 물리서버 | IBM UNIX 단독 구성 일부 존재 |
| 데이터플랫폼 | Oracle Exadata Appliance |
| 주요 역할 | WEB / WAS / AP / DB |

## 3.2 데이터거버넌스 및 IT 서비스/업무지원

페이지 78~80에서 확인되는 주요 구성은 다음과 같다.

| 시스템 그룹 | Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---|---|---:|---:|---:|---:|---|
| 데이터거버넌스 | `sbdgdqlows01` | 비즈메타/데이터품질 WAS #01 | WAS | 12 | 64GB | 250GB | 1,024GB | 장표 판독 기준 |
| 데이터거버넌스 | `sbdgdqlows02` | 비즈메타/데이터품질 WAS #02 | WAS | 16 | 64GB | 250GB | 1,024GB | 장표 판독 기준 |
| 데이터거버넌스 | `sbdgdfllows01` | 데이터흐름 WAS #01 | WAS | 16 | 256GB | 250GB | 1,536GB | 장표 판독 기준 |
| IT서비스 및 업무지원 | `sbimxmlowb01` | 단말관리 WEB #01 | WEB | 4 | 16GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxmlowb02` | 단말관리 WEB #02 | WEB | 4 | 16GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxmlowb03` | 단말관리 WEB #03 | WEB | 4 | 16GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxmlowb04` | 단말관리 WEB #04 | WEB | 4 | 16GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxmlows01` | 단말관리 WAS #01 | WAS | 8 | 64GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxmlows02` | 단말관리 WAS #02 | WAS | 8 | 64GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxmlows03` | 단말관리 WAS #03 | WAS | 8 | 64GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxmlows04` | 단말관리 WAS #04 | WAS | 8 | 64GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxdlowb01` | 단말배포 WEB #01 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxdlowb02` | 단말배포 WEB #02 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxdlowb03` | 단말배포 WEB #03 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxdlowb04` | 단말배포 WEB #04 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxdlowb05` | 단말배포 WEB #05 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxdlowb06` | 단말배포 WEB #06 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스 및 업무지원 | `sbimxdlows01` | 단말배포 WAS #01 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxdlows02` | 단말배포 WAS #02 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxdlows03` | 단말배포 WAS #03 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxdlows04` | 단말배포 WAS #04 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxdlows05` | 단말배포 WAS #05 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimxdlows06` | 단말배포 WAS #06 | WAS | 4 | 8GB | 250GB | 110GB |  |

## 3.3 운영 AP / ETL / CDC / DB

| 시스템 그룹 | Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---|---|---:|---:|---:|---:|---|
| IT서비스 및 업무지원 | `sbimbjloap01` | 배치 AP #01 | AP | 16 | 256GB | 250GB | - |  |
| IT서비스 및 업무지원 | `sbimcdloap01` | UNO Dashboard #01 | AP | 4 | 8GB | 250GB | 10GB | OGG 모니터링 |
| IT서비스 및 업무지원 | `sbimrdlows01` | 출력물(RD) WAS #01 | WAS | 확인필요 | 확인필요 | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimrdlows02` | 출력물(RD) WAS #02 | WAS | 확인필요 | 확인필요 | 250GB | 110GB |  |
| IT서비스 및 업무지원 | `sbimfwloap01` | 마스터솔루션 #01 | AP | 확인필요 | 확인필요 | 확인필요 | 확인필요 |  |
| IT서비스 및 업무지원 | `sbimdtlotl01` | ETL #01 | AP | 64 | 512GB | - | - | 물리/단독 |
| IT서비스 및 업무지원 | `sbimdtlotl02` | ETL #02 | AP | 64 | 512GB | - | - | 물리/단독 |
| IT서비스 및 업무지원 | `sbimcdioldb01` | CDC 중계 DB #01 | DB | 8 | 384GB | 800GB | - | IBM UNIX |
| IT서비스 및 업무지원 | `sbimcdioldb02` | CDC 중계 DB #02 | DB | 8 | 384GB | 800GB | - | IBM UNIX |

## 3.4 RDW / ADW Appliance

| 데이터 영역 | Hostname | 서버명 | 역할 | CPU | MEM | 추가 Disk | Rack |
|---|---|---|---|---:|---:|---:|---|
| RDW | `sbrdcoxxdb01` | RDW 어플라이언스 #01 | DB | 96 | 1,024GB | 249,856GB | Quarter Rack |
| RDW | `sbrdcoxxdb02` | RDW 어플라이언스 #02 | DB | 공통구성 | 공통구성 | 공통구성 | Quarter Rack |
| ADW | `sbadcoxxdb01` | ADW 어플라이언스 #01 | DB | 384* | 3,072GB* | 1,073,152GB* | Full Rack |
| ADW | `sbadcoxxdb02` | ADW 어플라이언스 #02 | DB | 공통 | 공통 | 공통 | Full Rack |
| ADW | `sbadcoxxdb03` | ADW 어플라이언스 #03 | DB | 공통 | 공통 | 공통 | Full Rack |
| ADW | `sbadcoxxdb04` | ADW 어플라이언스 #04 | DB | 공통 | 공통 | 공통 | Full Rack |
| ADW | `sbadcoxxdb05` | ADW 어플라이언스 #05 | DB | 공통 | 공통 | 공통 | Full Rack |
| ADW | `sbadcoxxdb06` | ADW 어플라이언스 #06 | DB | 공통 | 공통 | 공통 | Full Rack |

`*` ADW 값은 장표에서 여러 노드를 묶은 병합셀 형태로 표시되어 있어
**노드별 값인지 Rack 전체 값인지 원본 기준 확인이 필요하다.**

## 3.5 이행용 임시장비

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk |
|---|---|---|---:|---:|---:|---:|
| `sbimsiloap01` | [임시] 데이터 이행 변환 AP #01 | AP | 16 | 256GB | 250GB | 640GB |
| `sbimsiloap02` | [임시] 데이터 이행 변환 AP #02 | AP | 16 | 256GB | 250GB | 690GB |
| `sbimsiloap03` | [임시] 데이터 이행 변환 AP #03 | AP | 16 | 256GB | 250GB | 640GB |
| `sbimsiloap04` | [임시] 데이터 이행 변환 AP #04 | AP | 16 | 256GB | 250GB | 640GB |
| `sbimsiloap05` | [임시] 데이터 이행 변환 AP #05 | AP | 16 | 256GB | 250GB | 640GB |
| `sbimsiloap06` | [임시] 데이터 이행 변환 AP #06 | AP | 16 | 256GB | 250GB | 640GB |
| `sbimsiloap07` | [임시] 데이터 이행 AP #01 | AP | 16 | 256GB | 250GB | 400GB |
| `sbimsiloap08` | [임시] SQL 품질 AP #01 | AP | 16 | 32GB | 250GB | 90GB |
| 확인필요 | [임시] 데이터 이행 추출 DB #01 | DB | 16 | 128GB | - | - |

---

# 4. DR 환경 구성내역

## 4.1 DR 기본 구조

```text
운영 #01 / #02
      │
      │ 재해 발생 시 서비스 전환
      ▼
DR #51 / #52

단말관리/단말배포
      └─ #51 ~ #56

RDW
      ├─ RDW Appliance #51
      └─ RDW Appliance #52
```

## 4.2 마케팅플랫폼 DR

| Hostname | 서버명 | 역할 | CPU 수정 | MEM | OS Disk | 추가 Disk |
|---|---|---|---:|---:|---:|---:|
| `sbmpcolowb51` | 마케팅플랫폼 WEB #51 | WEB | 8 | 32GB | 250GB | 100GB |
| `sbmpcolowb52` | 마케팅플랫폼 WEB #52 | WEB | 8 | 32GB | 250GB | 100GB |
| `sbmpcolows51` | 마케팅플랫폼 WAS #51 | WAS | 28 | 224GB | 250GB | 110GB |
| `sbmpcolows52` | 마케팅플랫폼 WAS #52 | WAS | 28 | 224GB | 250GB | 110GB |
| `sbmpmslowb51` | 미니싱글뷰 WEB #51 | WEB | 6 | 20GB | 250GB | 100GB |
| `sbmpmslowb52` | 미니싱글뷰 WEB #52 | WEB | 6 | 20GB | 250GB | 100GB |
| `sbmpmslows51` | 미니싱글뷰 WAS #51 | WAS | 32 | 264GB | 250GB | 110GB |
| `sbmpmslows52` | 미니싱글뷰 WAS #52 | WAS | 32 | 264GB | 250GB | 110GB |

> `28 Core / 224GB`, `32 Core / 264GB`는 장표에 보이는 값을 그대로 기록하였다.  
> 비정형 규격이므로 실제 최종 할당사양인지 원본에서 재확인해야 한다.

## 4.3 단말관리 DR

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk |
|---|---|---|---:|---:|---:|---:|
| `sbimxmlowb51` | 단말관리 WEB #51 | WEB | 4 | 16GB | 250GB | 100GB |
| `sbimxmlowb52` | 단말관리 WEB #52 | WEB | 4 | 16GB | 250GB | 100GB |
| `sbimxmlowb53` | 단말관리 WEB #53 | WEB | 4 | 16GB | 250GB | 100GB |
| `sbimxmlowb54` | 단말관리 WEB #54 | WEB | 4 | 16GB | 250GB | 100GB |
| `sbimxmlows51` | 단말관리 WAS #51 | WAS | 8 | 64GB | 250GB | 110GB |
| `sbimxmlows52` | 단말관리 WAS #52 | WAS | 8 | 64GB | 250GB | 110GB |
| `sbimxmlows53` | 단말관리 WAS #53 | WAS | 8 | 64GB | 250GB | 110GB |
| `sbimxmlows54` | 단말관리 WAS #54 | WAS | 8 | 64GB | 250GB | 110GB |

## 4.4 단말배포 DR

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk |
|---|---|---|---:|---:|---:|---:|
| `sbimxdlowb51` | 단말배포 WEB #51 | WEB | 4 | 8GB | 250GB | 100GB |
| `sbimxdlowb52` | 단말배포 WEB #52 | WEB | 4 | 8GB | 250GB | 100GB |
| `sbimxdlowb53` | 단말배포 WEB #53 | WEB | 4 | 8GB | 250GB | 100GB |
| `sbimxdlowb54` | 단말배포 WEB #54 | WEB | 4 | 8GB | 250GB | 100GB |
| `sbimxdlowb55` | 단말배포 WEB #55 | WEB | 4 | 8GB | 250GB | 100GB |
| `sbimxdlowb56` | 단말배포 WEB #56 | WEB | 4 | 8GB | 250GB | 100GB |
| `sbimxdlows51` | 단말배포 WAS #51 | WAS | 4 | 8GB | 250GB | 110GB |
| `sbimxdlows52` | 단말배포 WAS #52 | WAS | 4 | 8GB | 250GB | 110GB |
| `sbimxdlows53` | 단말배포 WAS #53 | WAS | 4 | 8GB | 250GB | 110GB |
| `sbimxdlows54` | 단말배포 WAS #54 | WAS | 4 | 8GB | 250GB | 110GB |
| `sbimxdlows55` | 단말배포 WAS #55 | WAS | 4 | 8GB | 250GB | 110GB |
| `sbimxdlows56` | 단말배포 WAS #56 | WAS | 4 | 8GB | 250GB | 110GB |

## 4.5 기타 DR 및 RDW

| Hostname | 서버명 | 역할 | CPU | MEM | 추가 Disk | 비고 |
|---|---|---|---:|---:|---:|---|
| `sbimfwloap51` | 마스터솔루션 #51 | AP | 확인필요 | 확인필요 | 확인필요 |  |
| `sbrdcoxxdb51` | RDW 어플라이언스 #51 | DB | 96* | 1,024GB* | 249,856GB* | DR RDW |
| `sbrdcoxxdb52` | RDW 어플라이언스 #52 | DB | 공통 | 공통 | 공통 | DR RDW |

`*` 병합셀 기준 값으로 노드/시스템 단위 여부 확인 필요.

---

# 5. 개발 환경 구성내역

## 5.1 개발환경 성격

개발환경은 단순 WEB/WAS 개발서버만 존재하는 구조가 아니라 다음을 함께 포함한다.

```text
개발
│
├─ 마케팅플랫폼
│   ├─ WEB / WAS
│   ├─ 실시간 처리 AP
│   ├─ 행동정보 처리 AP
│   └─ 고객 행동 데이터 AP
│
├─ 신BI포털시스템
│   ├─ BI포털
│   ├─ 신용실적
│   └─ Self-BI
│
├─ 데이터거버넌스
│
├─ IT서비스/업무지원
│   ├─ 단말
│   ├─ 배치
│   ├─ ETL
│   ├─ 소스관리
│   ├─ 마스터솔루션
│   ├─ UNO Dashboard
│   └─ SQL 품질
│
└─ RDW Appliance
```

## 5.2 주요 개발 서버

| 시스템 그룹 | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---|---:|---:|---:|---:|---|
| 마케팅플랫폼 | 마케팅플랫폼 WEB #01 | WEB | 8 | 32GB | 250GB | 100GB |  |
| 마케팅플랫폼 | 마케팅플랫폼 WAS #01 | WAS | 32 | 256GB | 250GB | 110GB | 미니싱글뷰 통합 구성 |
| 마케팅플랫폼 | 실시간 처리 AP #01 | AP | 16 | 32GB | 250GB | 1,024GB |  |
| 마케팅플랫폼 | 행동정보 처리 AP #01 | AP | 16 | 32GB | 250GB | 1,024GB |  |
| 마케팅플랫폼 | 고객 행동 데이터 AP #01 | AP | 16 | 32GB | 250GB | 1,024GB |  |
| 신BI포털시스템 | BI 포털 WEB #01 | WEB | 2 | 8GB | 250GB | 110GB |  |
| 신BI포털시스템 | BI 포털 WAS #01 | WAS | 4 | 32GB | 250GB | 110GB |  |
| 신BI포털시스템 | 신용 실적 WEB #01 | WEB | 2 | 8GB | 250GB | 170GB |  |
| 신BI포털시스템 | 신용 실적 WAS #01 | WAS | 4 | 32GB | 250GB | 170GB |  |
| 신BI포털시스템 | Self-BI WEB #01 | WEB | 2 | 8GB | 250GB | 100GB |  |
| 신BI포털시스템 | Self-BI WAS #01 | WAS | 8 | 64GB | 250GB | 200GB | AUD |
| 신BI포털시스템 | Self-BI AP #01 | AP | 8 | 64GB | 250GB | 200GB | TRINITY |
| 데이터거버넌스 | 비즈메타/데이터품질 WAS #01 | WAS | 4 | 32GB | 250GB | 200GB |  |
| 데이터거버넌스 | 데이터흐름 WAS #01 | WAS | 4 | 32GB | 250GB | 300GB |  |
| IT서비스/업무지원 | 단말관리 WEB #01 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스/업무지원 | 단말관리 WAS #01 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스/업무지원 | 단말배포 WEB #01 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스/업무지원 | 단말배포 WAS #01 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스/업무지원 | 단말개발도구 AP #01 | AP | 4 | 8GB | 250GB | 100GB |  |
| IT서비스/업무지원 | 배치 AP #01 | AP | 6 | 96GB | 250GB | - |  |
| IT서비스/업무지원 | 출력물(RD) WAS #01 | WAS | 확인필요 | 확인필요 | 250GB | 110GB |  |
| IT서비스/업무지원 | ETL #01 | AP | 16 | 64GB | 250GB | 1,000GB |  |
| IT서비스/업무지원 | 소스관리 AP #01 | AP | 4 | 32GB | 250GB | 200GB | 배포관리와 통합 구성 |
| IT서비스/업무지원 | 마스터솔루션 #01 | AP | 8 | 64GB | 250GB | 100GB | 미들웨어 요청 |
| IT서비스/업무지원 | 라이브러리 #01 | - | - | - | - | - | AS-IS Nexus 사용 |
| IT서비스/업무지원 | UNO Dashboard #01 | AP | 4 | 8GB | 250GB | 10GB | OGG 모니터링 |
| IT서비스/업무지원 | SQL 품질 | AP | 16 | 32GB | 250GB | 100GB |  |
| 데이터플랫폼 | RDW 어플라이언스 #01/#02 | DB | 48* | 768GB* | - | 124,928GB* | Eighth Rack |

---

# 6. 선도 환경 구성내역

선도환경은 전체 개발환경의 복제가 아니라, 핵심 기능을 먼저 검증하기 위한 최소 구성이다.

| 시스템 그룹 | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---|---:|---:|---:|---:|---|
| 마케팅플랫폼 | 마케팅플랫폼 WEB #01 | WEB | 8 | 32GB | 250GB | 100GB |  |
| 마케팅플랫폼 | 마케팅플랫폼 WAS #01 | WAS | 32 | 256GB | 250GB | 110GB | 미니싱글뷰 통합구성 |
| IT서비스/업무지원 | 단말관리 WEB #01 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스/업무지원 | 단말관리 WAS #01 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스/업무지원 | 단말배포 WEB #01 | WEB | 4 | 8GB | 250GB | 100GB |  |
| IT서비스/업무지원 | 단말배포 WAS #01 | WAS | 4 | 8GB | 250GB | 110GB |  |
| IT서비스/업무지원 | 단말개발도구 AP #01 | AP | 4 | 8GB | 250GB | 100GB |  |
| IT서비스/업무지원 | 소스관리 AP #01 | AP | 4 | 32GB | 250GB | 200GB | 배포관리와 통합 구성 |
| IT서비스/업무지원 | 마스터솔루션 #01 | AP | 8 | 64GB | 250GB | 100GB | 미들웨어 요청 |
| IT서비스/업무지원 | 라이브러리 #01 | - | - | - | - | - | AS-IS Nexus 사용 |
| 데이터플랫폼 | RDW 어플라이언스 #01 | DB | 48* | 768GB* | - | 124,928GB* | Eighth Rack |
| 데이터플랫폼 | RDW 어플라이언스 #02 | DB | 공통 | 공통 | - | 공통 | Eighth Rack |

---

# 7. 소프트웨어 구성내역

페이지 84의 S/W 목록을 아키텍처 계층 기준으로 정리한다.

| 구분 | 제품명 | 버전 | 설치/적용 영역 | 비고 |
|---|---|---|---|---|
| O/S | Red Hat | 9.4 | IaaS 서버 | 구성점검 제외 표기 |
| O/S | AIX | 7.x | 물리 서버 |  |
| O/S | Oracle Linux | 미표기 | Appliance |  |
| JAVA | JDK | 21 | IaaS | 구성점검 제외 표기 |
| WEB | Apache | 미표기 | WEB |  |
| WAS | Tomcat | 미표기 | WAS |  |
| F/W | NH F/W | 미표기 | WAS/AP |  |
| DBMS | Oracle | Oracle 21Ai 표기 | Appliance |  |
| 단말UI | WebTopSuite | 미표기 | 단말 |  |
| 데이터품질 | DO Miner | 미표기 | Data Governance |  |
| 비즈메타 | Meta Miner | 미표기 | Data Governance |  |
| 데이터흐름 | Data Hawk | 미표기 | Data Governance |  |
| CDC | Oracle GoldenGate | 미표기 | 물리 서버 |  |
| BI 포털 | BI Matrix | 미표기 | BI |  |
| 마케팅플랫폼 | Kafka | 4.x | 실시간 처리 |  |
| ETL | DataStage | 미표기 | 개발 IaaS / 운영 물리 |  |
| 소스관리 | GitLab | 미표기 | 개발/선도 |  |
| 배포관리 | GitLab Runner | 미표기 | 개발/선도 |  |
| 보고서 | RD | 미표기 | 출력물 |  |
| SQL품질 | OpenPOP | 미표기 | 개발 |  |
| 이행 | SQL Canvas | 미표기 | 이행 |  |

> 장표 제목에는 `S/W목록(17종)`으로 기재되어 있으나,
> 표의 기능 항목은 17개보다 많아 보이므로 제품 기준 17종인지 기능분류 기준인지 확인이 필요하다.

---

# 8. DB 아키텍처 구성내역

## 8.1 DB 기본 원칙

장표의 핵심 정의:

> **RDW, ADW는 EXADATA RAC 각 1식 이중화 구성**

따라서 DB 아키텍처는 다음처럼 해석한다.

```text
[계정계 / 코어뱅킹 / 연계뱅킹 / BCV / 연계업무 /
 비대면채널 / 인터넷뱅킹 / 스마트뱅킹]
                │
                ▼
             CDC 중계
                │
                ▼
┌────────────────────────────────────┐
│                RDW                 │
│                                    │
│ 데이터플랫폼 RDW                   │
│ - 공통                             │
│ - 실시간 SoR                       │
│ - 준실시간 요약집계                │
│ - 준실시간 고객마트                │
│ - 피드백                           │
│                                    │
│ 마케팅플랫폼                       │
│ - 공통 / 개인고객 / 통합고객       │
│ - 기업고객 / 미니싱글뷰             │
│ - 상담판매 / 통합상품 / 캠페인      │
│ - EBM / 실시간처리                  │
│ - 행동정보처리 / 고객행동데이터     │
│ - 영업지원 / CS / 컨텐츠 / 메시지   │
└──────────────────┬─────────────────┘
                   │
                   ▼
┌────────────────────────────────────┐
│                ADW                 │
│                                    │
│ 데이터플랫폼 ADW                   │
│ - 공통                             │
│ - 분석 SoR                         │
│ - 분석통합요약집계                 │
│ - 분석단위업무마트                 │
│ - 분석보고마트                     │
│ - 피드백 / 분석지원                │
│                                    │
│ BI 포탈                            │
│ - BI포탈 / 신용실적 / OLAP         │
│ - Self BI / 신포털 UI/UX           │
│                                    │
│ 데이터거버넌스                     │
│ - 공통 / 비즈메타 / 데이터품질     │
│ - 데이터흐름                       │
│                                    │
│ IT서비스 및 업무지원               │
└────────────────────────────────────┘
```

## 8.2 RDW / ADW 역할 분리

| 구분 | RDW | ADW |
|---|---|---|
| 목적 | 실시간/준실시간 업무·조회 데이터 | 분석/집계/BI 데이터 |
| 처리성격 | Online / Near Real-time | Analytical |
| 주요 유입 | CDC 중심 | RDW/ETL/배치 계열 |
| 데이터 | 실시간 SoR, 고객, 마케팅 | 분석 SoR, 요약집계, 업무마트, 보고마트 |
| 주요 사용자 | 마케팅플랫폼, Mini SingleView | BI Portal, OLAP, Self-BI |
| 데이터거버넌스 | 제한적 | 주요 영역 |
| 물리구성 | Exadata RAC | Exadata RAC |
| DR | DR 장표에 RDW 확인 | 해당 장표에서 ADW DR 미표시 |

---

# 9. 아키텍처 구성관계

```text
사용자 / 단말
     │
     ▼
WEB
Apache
     │
     ▼
WAS
Tomcat + JDK + NH Framework
     │
     ├─────────────┐
     │             │
     ▼             ▼
업무 AP         실시간 AP
     │             │
     └──────┬──────┘
            ▼
           RDW
      Exadata RAC
            │
            ├─ 실시간/준실시간
            ├─ 마케팅/SingleView
            └─ Feedback
            │
            ▼
           ADW
      Exadata RAC
            │
            ├─ BI
            ├─ OLAP
            ├─ Self-BI
            ├─ 분석마트
            └─ 데이터거버넌스
```

운영관리 계열:

```text
GitLab
   │
   ▼
GitLab Runner
   │
   ▼
배포
   │
   ▼
WEB / WAS / AP

GoldenGate ── CDC
DataStage   ── ETL
RD          ── 출력물
OpenPOP     ── SQL 품질
SQL Canvas  ── 데이터 이행
UNO Dashboard ─ OGG 모니터링
```

---

# 10. 확인 필요 / GAP

| ID | 항목 | 현재 상태 | 조치 |
|---|---|---|---|
| GAP-HW-01 | 운영 환경 2/3 장표 간 일부 자원값 차이 가능 | 확인필요 | 원본 Excel 기준 비교 |
| GAP-HW-02 | DR WAS 28C/224GB, 32C/264GB | 확인필요 | 실제 할당사양 또는 오타 확인 |
| GAP-HW-03 | Appliance 병합셀 CPU/MEM/Disk | 확인필요 | 노드별/시스템 전체값 분리 |
| GAP-DR-01 | DR 장표에 ADW가 표시되지 않음 | 확인필요 | ADW DR 범위 확인 |
| GAP-DR-02 | HA(RAC/다중노드)와 센터 DR 관계 | 미정 | HA/DR 설계서에서 분리 정의 |
| GAP-SW-01 | S/W 17종 표기와 표의 기능 항목 수 차이 | 확인필요 | 제품 Count 기준 확정 |
| GAP-SW-02 | Apache/Tomcat/NH F/W 등 버전 공란 | 미완료 | Version Baseline 작성 |
| GAP-SW-03 | 설치대상 서버와 S/W 매핑 | 부분 | 서버-S/W Matrix 작성 |
| GAP-DB-01 | RDW → ADW 데이터 이동 상세방식 | 부분 | ETL/CDC/Batch 흐름 구분 |
| GAP-DB-02 | RDW/ADW RAC 노드별 서비스/SCAN/VIP | 미표기 | DB Physical 상세 설계 연계 |
| GAP-RUNTIME-01 | WEB/WAS별 JVM/Port/Container 매핑 | 별도자료 존재 | Middleware Baseline과 통합 |
| GAP-OPS-01 | 제품별 운영 모니터링 및 책임자 | 미표기 | 운영 아키텍처에서 보강 |

---

# 11. 최종 구성 Baseline

현재 장표를 기준으로 NSIGHT의 물리 기술 구성은 다음 5개 축으로 관리하는 것이 적절하다.

| 축 | 기준 |
|---|---|
| Environment | 운영 / DR / 개발 / 선도 |
| Compute | WEB / WAS / AP / DB / Appliance |
| Runtime | OS / JDK / Apache / Tomcat / NH Framework |
| Platform Solution | Kafka / OGG / DataStage / BI / DG / GitLab 등 |
| Data | RDW / ADW / CDC / ETL / 이행 |

최종 추적구조는 다음과 같이 관리한다.

```text
Environment
   ↓
System Group
   ↓
Server / Hostname
   ↓
Role
   ↓
CPU / MEM / Disk
   ↓
OS / Middleware / Solution
   ↓
Application / Runtime
   ↓
RDW / ADW / Integration
   ↓
HA / DR
   ↓
Monitoring / Operation
```

---

# 12. 결론

페이지 78~87 자료를 통합하면 NSIGHT의 물리 기술 아키텍처는 다음과 같이 정리된다.

1. **운영·DR·개발·선도 환경이 명확히 분리**되어 있다.
2. 온라인 서비스는 **WEB → WAS/AP → RDW/ADW** 구조로 구성된다.
3. RDW와 ADW는 각각 **Exadata RAC 이중화** 구조로 설계되어 있다.
4. RDW는 **실시간/준실시간 및 마케팅 업무 중심**, ADW는 **분석/BI/거버넌스 중심**이다.
5. 단말, CDC, ETL, 배치, 소스·배포, SQL 품질, 데이터 이행을 별도 기술서비스로 구성한다.
6. DR은 운영 전체 복제가 아니라 **핵심 시스템 중심 구성**으로 보이므로 시스템별 DR 범위를 별도 관리해야 한다.
7. S/W Version, Appliance 병합 자원값, ADW DR 범위 등은 Baseline 확정 전 추가 확인이 필요하다.

본 문서는 현 시점에서 **NSIGHT Physical Technology Architecture 구성내역 Working Baseline**으로 활용한다.
