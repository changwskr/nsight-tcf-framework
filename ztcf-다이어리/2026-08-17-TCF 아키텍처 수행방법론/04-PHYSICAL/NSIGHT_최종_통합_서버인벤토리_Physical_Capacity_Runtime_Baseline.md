# NSIGHT 최종 통합 서버 인벤토리 · Physical Architecture · Capacity Baseline

> 농협 상호금융 NSIGHT 정보계  
> 기준일: 2026-08-18  
> 상태: **Current Working Baseline / 일부 항목 검증 필요**

---

## 0. 문서 목적

본 문서는 지금까지 제공된 서버 목록, 운영/DR 구성, WEB/WAS 구성, 용량산정 화면, 성능 파라미터, 미들웨어 점검 결과를 하나로 합쳐 다음을 단일 Markdown 기준정보로 관리하기 위한 통합본이다.

- 서버 71대 상세 인벤토리
- 시스템/역할/Hostname 매핑
- CPU 원산정값과 최종 조정값
- Memory / OS Disk / 추가 Disk
- tpmC 및 용량산정 기준
- WEB/WAS/AP/DB/ETL/CDC/Appliance 역할
- Apache/Tomcat 실행 구조
- JVM/Tomcat/Hikari/Spring/MyBatis 성능 기준
- HA/DR 구조
- BI포털 용량산정 검증
- N-1 장애 용량 검토
- 현재 미확정/재검증 항목
- 향후 Application/WAR 및 Runtime Evidence 매핑 항목

본 문서는 `서버 1대 = SERVER_MASTER 1행`을 원칙으로 한다. 파일시스템은 Hostname 중복을 피하기 위해 별도 `FILESYSTEM` 구조로 관리하는 것을 원칙으로 한다.

---

# 1. Baseline 상태 구분

| 상태 | 의미 |
|---|---|
| **CONFIRMED** | 현재 자료에서 직접 확인된 값 |
| **WORKING** | 현재 작업 기준으로 사용하되 성능시험/원본 확인 후 확정할 값 |
| **UNKNOWN** | 현재 자료만으로 확정할 수 없는 값 |
| **DELETE** | 삭제 대상으로 표시된 서버 |
| **REVIEW** | 삭제/존치 또는 값 재검토가 필요한 서버 |

---

# 2. 전체 서버 현황

## 2.1 시스템 그룹별

| 시스템 그룹 | 서버 수 |
|---|---:|
| 마케팅플랫폼 | 15 |
| 신BI포털시스템 | 16 |
| 데이터거버넌스 | 4 |
| IT 서비스 및 업무지원 | 28 |
| 데이터플랫폼 시스템 | 8 |
| **합계** | **71** |

## 2.2 역할별

| 역할 | 서버 수 |
|---|---:|
| WEB | 20 |
| WAS | 28 |
| AP | 13 |
| DB | 10 |
| **합계** | **71** |

## 2.3 Lifecycle 관점

- 신규/운영대상 서버가 대다수이다.
- OLAP WEB/WAS 2대 + OLAP AP 2대 = **4대 삭제 대상**
- 데이터흐름 WAS #02 = **삭제검토**
- ETL 2대, CDC 중계 DB 2대 = **단독**
- RDW 2대, ADW 6대 = **Oracle Appliance**

---

# 3. NSIGHT Physical Architecture Big Picture

```text
사용자 / WebTopSuite / Client
            │
            ▼
          GSLB
            │
            ▼
           L4
            │
            ▼
       Apache WEB
            │
            ▼
   Tomcat JVM Instance
            │
            ▼
  업무 Application / TCF
            │
            ├──────────────┐
            ▼              ▼
          RDW             ADW
     운영/조회계        분석/대용량
            │              │
            ├──── CDC ─────┤
            │              │
            ▼              ▼
       실시간/배치       BI/분석
```

Physical Architecture의 핵심 원칙은 다음과 같다.

1. WEB, WAS, AP, DB의 책임을 분리한다.
2. RDW와 ADW를 분리하여 온라인/운영 부하와 분석 부하를 격리한다.
3. 실시간 처리와 배치/ETL을 동일 Runtime 자원에 혼재시키지 않는다.
4. WAS Server(VM)와 Tomcat JVM Instance를 구분한다.
5. 장애단위는 가능한 작게 유지하며 Scale-Out을 우선 검토한다.
6. 요구 Core와 실제 할당 Core를 같은 값으로 취급하지 않는다.

---

# 4. 서버별 최종 상세 인벤토리

> `CPU` = ISP/원 산정 Core  
> `수정CPU` = 현재 구축/조정 기준 Core  
> `-` = 미기재/비적용/확인필요  
> Appliance Disk는 원본에서 서버군 공통값으로 표시된 값을 행별로 보존한 것이므로 노드별 실제 물리용량 여부를 별도 확인해야 한다.

| No | 시스템 그룹 | Hostname | 서버명 | 역할 | CPU | 수정CPU | MEM GB | OS Disk GB | 추가 Disk GB | 용량기준 | tpmC | DR | 상태 |
|---:|---|---|---|:---:|---:|---:|---:|---:|---:|---|---:|:---:|---|
| 1 | 마케팅플랫폼 | `sbmpcolowb01` | 마케팅플랫폼 WEB #01 | WEB | 11 | 12 | 48 | 250 | 100 | ISP | 1,130,017 | O | 신규 |
| 2 | 마케팅플랫폼 | `sbmpcolowb02` | 마케팅플랫폼 WEB #02 | WEB | 11 | 12 | 48 | 250 | 100 | ISP | 1,130,017 | O | 신규 |
| 3 | 마케팅플랫폼 | `sbmpcolows01` | 마케팅플랫폼 WAS #01 | WAS | 35 | 32 | 256 | 250 | 110 | ISP | 3,849,561 | O | 신규 |
| 4 | 마케팅플랫폼 | `sbmpcolows02` | 마케팅플랫폼 WAS #02 | WAS | 35 | 32 | 256 | 250 | 110 | ISP | 3,849,561 | O | 신규 |
| 5 | 마케팅플랫폼 | `sbmpmslowb01` | 미니싱글뷰 WEB #01 | WEB | 7 | 8 | 32 | 250 | 100 | ISP | 751,802 | O | 신규 |
| 6 | 마케팅플랫폼 | `sbmpmslowb02` | 미니싱글뷰 WEB #02 | WEB | 7 | 8 | 32 | 250 | 100 | ISP | 751,802 | O | 신규 |
| 7 | 마케팅플랫폼 | `sbmpmslows01` | 미니싱글뷰 WAS #01 | WAS | 42 | 32 | 256 | 250 | 110 | ISP | 4,603,836 | O | 신규 |
| 8 | 마케팅플랫폼 | `sbmpmslows02` | 미니싱글뷰 WAS #02 | WAS | 42 | 32 | 256 | 250 | 110 | ISP | 4,603,836 | O | 신규 |
| 9 | 마케팅플랫폼 | `sbmppeloap01` | 실시간 처리 AP #01 | AP | 32 | 32 | 64 | 250 | 200 | 솔루션 권고 | - | - | 신규 |
| 10 | 마케팅플랫폼 | `sbmppeloap02` | 실시간 처리 AP #02 | AP | 32 | 32 | 64 | 250 | 200 | 솔루션 권고 | - | - | 신규 |
| 11 | 마케팅플랫폼 | `sbmppbloap01` | 행동정보 처리 AP #01 | AP | 32 | 32 | 64 | 250 | 200 | 솔루션 권고 | - | - | 신규 |
| 12 | 마케팅플랫폼 | `sbmppbloap02` | 행동정보 처리 AP #02 | AP | 32 | 32 | 64 | 250 | 200 | 솔루션 권고 | - | - | 신규 |
| 13 | 마케팅플랫폼 | `sbmpcdloap01` | 고객 행동 데이터 AP #01 | AP | 32 | 32 | 64 | 250 | 1,024 | 솔루션 권고 | - | - | 신규 |
| 14 | 마케팅플랫폼 | `sbmpcdloap02` | 고객 행동 데이터 AP #02 | AP | 32 | 32 | 64 | 250 | 1,024 | 솔루션 권고 | - | - | 신규 |
| 15 | 마케팅플랫폼 | `sbmpcdloap03` | 고객 행동 데이터 AP #03 | AP | 32 | 32 | 64 | 250 | 1,024 | 솔루션 권고 | - | - | 신규 |
| 16 | 신BI포털시스템 | `sbbiptlowb01` | BI 포털 WEB #01 | WEB | 4 | 4 | 16 | 250 | 100 | ISP | 362,070 | - | 신규 |
| 17 | 신BI포털시스템 | `sbbiptlowb02` | BI 포털 WEB #02 | WEB | 4 | 4 | 16 | 250 | 100 | ISP | 362,070 | - | 신규 |
| 18 | 신BI포털시스템 | `sbbiptlows01` | BI 포털 WAS #01 | WAS | 7 | 8 | 64 | 250 | 110 | ISP | 706,045 | - | 신규 |
| 19 | 신BI포털시스템 | `sbbiptlows02` | BI 포털 WAS #02 | WAS | 7 | 8 | 64 | 250 | 110 | ISP | 706,045 | - | 신규 |
| 20 | 신BI포털시스템 | `sbbicrlowb01` | 신용 실적 WEB #01 | WEB | 2 | 2 | 8 | 250 | 100 | ISP | 143,730 | - | 신규 |
| 21 | 신BI포털시스템 | `sbbicrlowb02` | 신용 실적 WEB #02 | WEB | 2 | 2 | 8 | 250 | 100 | ISP | 143,730 | - | 신규 |
| 22 | 신BI포털시스템 | `sbbicrlows01` | 신용 실적 WAS #01 | WAS | 6 | 6 | 48 | 250 | 110 | ISP | 587,235 | - | 신규 |
| 23 | 신BI포털시스템 | `sbbicrlows02` | 신용 실적 WAS #02 | WAS | 6 | 6 | 48 | 250 | 110 | ISP | 587,235 | - | 신규 |
| 24 | 신BI포털시스템 | `sbbioalows01` | OLAP WEB/WAS #01 | WAS | 9 | - | 0 | 250 | 170 | ISP | 892,140 | - | **삭제** |
| 25 | 신BI포털시스템 | `sbbioalows02` | OLAP WEB/WAS #02 | WAS | 9 | - | 0 | 250 | 170 | ISP | 892,140 | - | **삭제** |
| 26 | 신BI포털시스템 | `sbbioalap01` | OLAP AP #01 | AP | 27 | - | 0 | 250 | - | ISP | 2,932,029 | - | **삭제** |
| 27 | 신BI포털시스템 | `sbbioalap02` | OLAP AP #02 | AP | 27 | - | 0 | 250 | - | ISP | 2,932,029 | - | **삭제** |
| 28 | 신BI포털시스템 | `sbbisblowb01` | Self-BI WEB #01 | WEB | 4 | 4 | 16 | 250 | 100 | 솔루션 권고 | - | - | 신규 |
| 29 | 신BI포털시스템 | `sbbisblowb02` | Self-BI WEB #02 | WEB | 4 | 4 | 16 | 250 | 100 | 솔루션 권고 | - | - | 신규 |
| 30 | 신BI포털시스템 | `sbbislows01` | Self-BI WAS #01 | WAS | 16 | 16 | 256 | 250 | 1,024 | 솔루션 권고 | - | - | 신규 |
| 31 | 신BI포털시스템 | `sbbislows02` | Self-BI WAS #02 | WAS | 16 | 16 | 256 | 250 | 1,024 | 솔루션 권고 | - | - | 신규 |
| 32 | 데이터거버넌스 | `sbkdgdlows01` | 비즈메타/데이터품질 WAS #01 | WAS | 16 | 16 | 64 | 250 | 1,024 | 솔루션 권고 | - | - | 신규 |
| 33 | 데이터거버넌스 | `sbkdgdlows02` | 비즈메타/데이터품질 WAS #02 | WAS | 16 | 16 | 64 | 250 | 1,024 | 솔루션 권고 | - | - | 신규 |
| 34 | 데이터거버넌스 | `sbdgddlows01` | 데이터흐름 WAS #01 | WAS | 16 | 16 | 256 | 250 | 1,536 | 솔루션 권고 | - | - | 신규 |
| 35 | 데이터거버넌스 | `sbdgddlows02` | 데이터흐름 WAS #02 | WAS | 16 | 16 | 256 | 250 | - | - | - | - | **삭제검토** |
| 36 | IT 서비스 및 업무지원 | `sbimxmlowb01` | 단말관리 WEB #01 | WEB | - | 4 | 16 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 37 | IT 서비스 및 업무지원 | `sbimxmlowb02` | 단말관리 WEB #02 | WEB | - | 4 | 16 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 38 | IT 서비스 및 업무지원 | `sbimxmlowb03` | 단말관리 WEB #03 | WEB | - | 4 | 16 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 39 | IT 서비스 및 업무지원 | `sbimxmlowb04` | 단말관리 WEB #04 | WEB | - | 4 | 16 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 40 | IT 서비스 및 업무지원 | `sbimxmlows01` | 단말관리 WAS #01 | WAS | - | 8 | 64 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 41 | IT 서비스 및 업무지원 | `sbimxmlows02` | 단말관리 WAS #02 | WAS | - | 8 | 64 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 42 | IT 서비스 및 업무지원 | `sbimxmlows03` | 단말관리 WAS #03 | WAS | - | 8 | 64 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 43 | IT 서비스 및 업무지원 | `sbimxmlows04` | 단말관리 WAS #04 | WAS | - | 8 | 64 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 44 | IT 서비스 및 업무지원 | `sbimxdlowb01` | 단말배포 WEB #01 | WEB | - | 4 | 8 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 45 | IT 서비스 및 업무지원 | `sbimxdlowb02` | 단말배포 WEB #02 | WEB | - | 4 | 8 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 46 | IT 서비스 및 업무지원 | `sbimxdlowb03` | 단말배포 WEB #03 | WEB | - | 4 | 8 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 47 | IT 서비스 및 업무지원 | `sbimxdlowb04` | 단말배포 WEB #04 | WEB | - | 4 | 8 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 48 | IT 서비스 및 업무지원 | `sbimxdlowb05` | 단말배포 WEB #05 | WEB | - | 4 | 8 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 49 | IT 서비스 및 업무지원 | `sbimxdlowb06` | 단말배포 WEB #06 | WEB | - | 4 | 8 | 250 | 100 | 솔루션 권고 | - | O | 신규 |
| 50 | IT 서비스 및 업무지원 | `sbimxdlows01` | 단말배포 WAS #01 | WAS | - | 4 | 8 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 51 | IT 서비스 및 업무지원 | `sbimxdlows02` | 단말배포 WAS #02 | WAS | - | 4 | 8 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 52 | IT 서비스 및 업무지원 | `sbimxdlows03` | 단말배포 WAS #03 | WAS | - | 4 | 8 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 53 | IT 서비스 및 업무지원 | `sbimxdlows04` | 단말배포 WAS #04 | WAS | - | 4 | 8 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 54 | IT 서비스 및 업무지원 | `sbimxdlows05` | 단말배포 WAS #05 | WAS | - | 4 | 8 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 55 | IT 서비스 및 업무지원 | `sbimxdlows06` | 단말배포 WAS #06 | WAS | - | 4 | 8 | 250 | 110 | 솔루션 권고 | - | O | 신규 |
| 56 | IT 서비스 및 업무지원 | `sbimxloap01` | 배치 AP #01 | AP | 16 | 16 | 256 | 250 | - | 은행 기준적용 | - | - | 신규 |
| 57 | IT 서비스 및 업무지원 | `sbimcdloap01` | UNO Dashboard #01 | AP | 4 | 4 | 8 | 250 | 10 | 고객 요청사항 | - | - | 신규 |
| 58 | IT 서비스 및 업무지원 | `sbimrdlows01` | 솔루션(RD) WAS #01 | WAS | 0 | 0 | 0 | 250 | 110 | 솔루션 권고 | - | - | 신규 |
| 59 | IT 서비스 및 업무지원 | `sbimrdlows02` | 솔루션(RD) WAS #02 | WAS | 0 | 0 | 0 | 250 | 110 | 솔루션 권고 | - | - | 신규 |
| 60 | IT 서비스 및 업무지원 | `sbimdtlot01` | ETL #01 | AP | 64 | 64 | 512 | - | - | ISP | 6,318,317 | - | 단독 |
| 61 | IT 서비스 및 업무지원 | `sbimdtlot02` | ETL #02 | AP | 64 | 64 | 512 | - | - | ISP | 6,318,317 | - | 단독 |
| 62 | IT 서비스 및 업무지원 | `sbimcdicdb01` | CDC 중계 DB #01 | DB | 8 | 8 | 384 | 800 | - | 은행 기준적용 | - | - | 단독 |
| 63 | IT 서비스 및 업무지원 | `sbimcdicdb02` | CDC 중계 DB #02 | DB | 8 | 8 | 384 | 800 | - | 은행 기준적용 | - | - | 단독 |
| 64 | 데이터플랫폼 시스템 | `sbrdcoxodb01` | RDW 어플라이언스 #01 | DB | 96 | 96 | 1,024 | - | 249,856 | ISP | 24,387,550 | O | Appliance |
| 65 | 데이터플랫폼 시스템 | `sbrdcoxodb02` | RDW 어플라이언스 #02 | DB | 96 | 96 | 1,024 | - | 249,856 | ISP | 24,387,550 | O | Appliance |
| 66 | 데이터플랫폼 시스템 | `sbadcoxodb01` | ADW 어플라이언스 #01 | DB | 384 | 384 | 3,072 | - | 1,073,152 | ISP | 47,818,722 | - | Appliance |
| 67 | 데이터플랫폼 시스템 | `sbadcoxodb02` | ADW 어플라이언스 #02 | DB | 384 | 384 | 3,072 | - | 1,073,152 | ISP | 47,818,722 | - | Appliance |
| 68 | 데이터플랫폼 시스템 | `sbadcoxodb03` | ADW 어플라이언스 #03 | DB | 384 | 384 | 3,072 | - | 1,073,152 | ISP | 47,818,722 | - | Appliance |
| 69 | 데이터플랫폼 시스템 | `sbadcoxodb04` | ADW 어플라이언스 #04 | DB | 384 | 384 | 3,072 | - | 1,073,152 | ISP | 47,818,722 | - | Appliance |
| 70 | 데이터플랫폼 시스템 | `sbadcoxodb05` | ADW 어플라이언스 #05 | DB | 384 | 384 | 3,072 | - | 1,073,152 | ISP | 47,818,722 | - | Appliance |
| 71 | 데이터플랫폼 시스템 | `sbadcoxodb06` | ADW 어플라이언스 #06 | DB | 384 | 384 | 3,072 | - | 1,073,152 | ISP | 47,818,722 | - | Appliance |

---

# 5. 용량산정 모델

## 5.1 기본 원칙

`tpmC`와 `TPS`는 동일한 값이 아니다.

```text
업무량 / TPS
      │
      ▼
업무복잡도 및 운영 보정
      │
      ▼
요구 tpmC
      │
      ▼
tpmC / Core
      │
      ▼
Required Core
      │
      ▼
HA/N+1/표준 VM 단위 반영
      │
      ▼
Allocated Core
```

따라서 반드시 아래 값을 분리한다.

| 컬럼 | 의미 |
|---|---|
| `tpmC_Baseline` | 기준 tpmC |
| `tpmC_Calculated` | 보정 후 요구 tpmC |
| `tpmC_Per_Core` | Core당 처리 기준 |
| `Required_Core` | 요구 Core |
| `Server_Count` | 서버 대수 |
| `Allocated_Core` | 실제 할당 Core |
| `CPU_Headroom` | 정상 여유 Core |
| `N1_Remaining_Core` | 1대 장애 후 잔여 Core |
| `N1_Gap` | N-1 상태의 요구 Core 대비 차이 |

---

# 6. BI포털 용량산정 상세

최신 확대 화면에서 BI포털 WEB/WAS의 계산 구조가 명확히 확인되었다.

## 6.1 BI포털 WEB

| 단계 | 항목 | 보정치 | 산정값 |
|---:|---|---:|---:|
| 1 | 전체 사용자 | - | 93,000 |
| 2 | 동시 사용자 | 10% | 9,300 |
| 3 | 사용자당 Operation | 3 | 27,900 |
| 4 | Interface 부하 | 1.1 | 30,690 |
| 5 | Peak Time 부하 | 1.1 | 33,759 |
| 6 | Network 부하 | 1.1 | 37,135 |
| 7 | 시스템 여유율 | 1.3 | 48,276 |
| 8 | 향후 증가율 | 1.5 | 72,414 |
| 9 | tpmC 변환 | ×10 | **724,140** |
| 10 | 최종 tpmC | ×1 | **724,140** |
| 11 | Core 기준 | 110,000 tpmC/Core | - |
| 12 | 필요 Core | `724,140 / 110,000` | **7 Core** |

실제 인벤토리:

```text
BI WEB #01 = 4 Core
BI WEB #02 = 4 Core
-------------------
Allocated = 8 Core
Required  = 7 Core
Headroom  = +1 Core
```

## 6.2 BI포털 WAS

| 단계 | 항목 | 보정치 | 산정값 |
|---:|---|---:|---:|
| 1 | 전체 사용자 | - | 93,000 |
| 2 | 동시 사용자 | 10% | 9,300 |
| 3 | 사용자당 Operation | 3 | 27,900 |
| 4 | Interface 부하 | 1.1 | 30,690 |
| 5 | Peak Time 부하 | 1.1 | 33,759 |
| 6 | Network 부하 | 1.1 | 37,135 |
| 7 | Cluster 보정 | 1.3 | 48,276 |
| 8 | 시스템 여유율 | 1.3 | 62,759 |
| 9 | 시스템 관리 보정 | 1.5 | 94,139 |
| 10 | 향후 증가율 | 1.5 | 141,209 |
| 11 | tpmC 변환 | ×10 | **1,412,090** |
| 12 | 최종 tpmC | ×1 | **1,412,090** |
| 13 | Core 기준 | 110,000 tpmC/Core | - |
| 14 | 필요 Core | `1,412,090 / 110,000` | **13 Core** |

실제 인벤토리:

```text
BI WAS #01 = 8 Core
BI WAS #02 = 8 Core
--------------------
Allocated = 16 Core
Required  = 13 Core
Headroom  = +3 Core
```

## 6.3 정상/N-1 검토

| Capacity Group | 필요 Core | 정상 할당 | 정상 GAP | 1대 장애 후 | N-1 GAP |
|---|---:|---:|---:|---:|---:|
| BI Portal WEB | 7 | 8 | **+1** | 4 | **-3** |
| BI Portal WAS | 13 | 16 | **+3** | 8 | **-5** |

판정:

- 정상상태: **충족**
- N-1 상태에서 전체 설계 Peak를 그대로 요구할 경우: **부족**
- 단, 용량산정에 이미 Peak/Cluster/향후증가 보정이 포함돼 있으므로 실제 증설 결정은 N-1 성능시험/운영정책과 함께 판단해야 한다.

---

# 7. 병컴·인터넷뱅킹 tpmC 산정 Evidence

확대 화면에서 다음 계산이 확인된다.

```text
목표 TPS 618
 × 60                   = 37,080
 × 1.2                  = 44,496
 × 1.3                  = 57,845
 ÷ 0.6                  = 96,408
 × 1.5                  = 144,612
 × 1.0                  = 144,612
 × 1.6                  = 231,379
 × 2.0                  = 462,758
 × 1.1                  = 509,034
 × 1.8                  = 916,262
 × 1.1                  = 1,007,888
 × 2.0                  = 2,015,776
 × 1.3                  = 2,620,508
```

따라서:

> **618 TPS → 보정 후 2,620,508 tpmC**

이며 TPS와 tpmC는 단순 1:1 환산값이 아니다.

---

# 8. Current Performance Working Baseline

## 8.1 사용자 / 세션 / SLA

| 영역 | 항목 | Working Baseline |
|---|---|---:|
| 사용자 | 전체 사용자 | 36,000 |
| 세션 | 설계 세션 | ≥43,200 |
| 세션 | Idle Timeout | 90분 |
| 세션 | Absolute Timeout | 12시간 |
| Session Object | 권장 | ≤2KB |
| Session Object | 최대 | ≤5KB |
| SLA | 일반 Peak | 600 TPS |
| SLA | 설계 Peak | **1,200 TPS** |
| SLA | Stress | **1,800 TPS** |
| SLA | 응답시간 | **p95 ≤3초** |

60분 세션 문서도 존재하므로 90분은 현재 Working Baseline으로 관리하고 운영정책 확정 시 Baseline 승격이 필요하다.

## 8.2 VM Capacity

| 항목 | 값 |
|---|---:|
| 표준 Working VM | 16 vCPU / 64GB |
| TPMC 기반 산정 Capacity | **855 TPS/VM** |
| 운영 80% Capacity | **684 TPS/VM** |
| 기존 보수 기준 | 16Core ≈ 500 TPS/VM |
| 상태 | 855 TPS는 성능시험 전 Working Baseline |

과거의 `8C≈250`, `16C≈500`, `32C≈1,000 TPS` 기준은 보수 Capacity Reference로 유지할 수 있으나 최신 855 TPS/16C 모델과 섞어 단일 확정값으로 사용하면 안 된다.

---

# 9. JVM Baseline

| 항목 | 일반 AP 초기값 | SingleView | 상태 |
|---|---:|---:|---|
| Heap | 24GB | 28GB | Working |
| 화면상 후보 | 28GB | 28GB | 일반 AP는 시험 후 결정 |
| `-Xss` | 512k | 512k | Working |
| GC | G1GC | G1GC | Working |
| MaxGCPauseMillis | 200ms | 200ms | 목표 |
| MaxMetaspaceSize | 2GB | 2GB | Working |
| Code Cache | 256MB | 256MB | Working |
| Heap Usage | ≤70% | ≤70% | 운영 임계치 |
| CPU | 평균 ≤70% | 평균 ≤70% | 운영 임계치 |

**28GB를 모든 JVM에 일괄 적용하지 않는다.** 64GB VM의 Native Memory, Thread Stack, APM/보안 Agent, OS Page Cache, Heap Dump 여유까지 포함해 Memory Budget을 검증한다.

---

# 10. Tomcat Baseline

| 파라미터 | 기준 |
|---|---:|
| maxThreads | **800 초기** |
| 시험범위 | **800~1,000** |
| minSpareThreads | 200 |
| acceptCount | 800 |
| maxConnections | 20,000 |
| KeepAlive | 5초 |
| maxKeepAliveRequests | 100 |
| Busy Thread 정상 | <70% |
| Busy Thread 주의 | 70~85% |
| Busy Thread 위험 | >85% |

`1,411`, `1,552`와 같은 계산치는 실제 최종 설정값으로 사용하지 않고 **산정 참고값**으로 관리한다.

---

# 11. HikariCP Baseline

| 파라미터 | 일반 AP | SingleView |
|---|---:|---:|
| maximumPoolSize | **150** | **180** |
| minimumIdle | 30 | 40 |
| connectionTimeout | 3초 | 3초 |
| validationTimeout | 1초 검토 | 1초 검토 |
| idleTimeout | 10분 | 10분 |
| maxLifetime | ≤30분 | ≤30분 |
| keepaliveTime | 5분 | 5분 |
| autoCommit | false | false |
| 정상 Pool 사용률 | <70% | <70% |
| 주의 | 70~80% | 70~80% |

기존 보수 기준은 일반 `80~100`, SingleView `100~120`이다. 현재 `150/180`은 DB Connection Hold Time 기반 Working Value이며 **전체 JVM × DataSource × Pool 수를 Oracle Session 총량과 반드시 대조**해야 한다.

Pool 기본식:

```text
DB Pool
≈ TPS
 × DB Connection Hold Time
 × DB 사용 거래 비율
 × Safety Factor
```

---

# 12. Timeout Chain

온라인 거래는 다음 순서를 유지한다.

```text
DB Query Timeout
      <
Spring Transaction Timeout
      <
WEB Request Timeout
      <
Client / Upper Timeout
```

| 영역 | 권장 기준 |
|---|---|
| MyBatis 공통코드 | 1~2초 |
| MyBatis 일반조회 | 2~3초 |
| MyBatis SingleView | 3초 |
| Spring TX 공통 | 3초 |
| Spring TX 일반 | 4초 |
| Spring TX SingleView | 5초 |
| 변경성 거래 | 5~10초 검토 |
| 10초 초과 | 온라인 동기 처리 금지, 비동기/배치 전환 |
| WEB Request | 6~8초 |

L4 Idle Timeout은 Transaction Timeout Chain과 별도의 네트워크 연결정책으로 관리한다.

---

# 13. WEB/WAS Middleware Architecture

## 13.1 확정 정의

**Container 1개 = 독립 Tomcat JVM Instance 1개**

```text
WAS Server / VM
│
├─ Tomcat JVM Instance #1
│   ├─ 독립 Java Process
│   ├─ CATALINA_BASE #1
│   ├─ Connector Port #1
│   ├─ JVM Heap #1
│   └─ Application #1
│
└─ Tomcat JVM Instance #2
    ├─ 독립 Java Process
    ├─ CATALINA_BASE #2
    ├─ Connector Port #2
    ├─ JVM Heap #2
    └─ Application #2
```

즉, `WAS Server`와 `Tomcat JVM Instance`는 같은 개념이 아니다.

## 13.2 Apache

하나의 Apache 인스턴스는 여러 포트를 동시에 Listen할 수 있다.

```text
Apache Instance
 ├─ Listen 9000 → Tomcat :19000
 ├─ Listen 9001 → Tomcat :19001
 ├─ Listen 9010 → Tomcat :19010
 └─ Listen 9011 → Tomcat :19011
```

포트별 VirtualHost/Proxy 구성이 가능하며, 서비스별 Tomcat JVM과 연결한다.

## 13.3 대표 운영 패턴

```text
GSLB / L4
   │
   ├──────── WEB #01 ────────┐
   │                          │
   └──────── WEB #02 ────────┤
                              ▼
                         Apache
                              │
                    9000 / 9001 ...
                              │
                 ┌────────────┴────────────┐
                 ▼                         ▼
          WAS Server #01              WAS Server #02
                 │                         │
          Tomcat JVM(s)               Tomcat JVM(s)
                 │                         │
                 └──────────┬──────────────┘
                            ▼
                          RDW
```

---

# 14. 시스템별 WEB/WAS Runtime Pattern

현재 구성자료에서 확인된 대표 패턴:

| 시스템 | WEB | WAS | WAS당 Tomcat JVM | 주요 포트 | 비고 |
|---|---:|---:|---:|---|---|
| 마케팅플랫폼 운영 | 2 | 2 | 2 | 9000/9001 → 19000/19001 | 공통/화면 분리 |
| 미니싱글뷰 운영 | 2 | 2 | 2 | 9000/9001 → 19000/19001 | 공통/화면 분리 |
| BI포털 운영 | 2 | 2 | 1 | 9000 → 19000 계열 | 이중화 |
| 신용실적 운영 | 2 | 2 | 2 | 9000/9001 → 19000/19001 | 실적/화면 분리 |
| OLAP 운영 | 2 | 2 | 1 | 9000 | 삭제 대상 별도 관리 |
| 단말관리 운영 | 복수 | 복수 | 1 | 9000 | DR 구성 확인 |
| 단말배포 운영 | 복수 | 복수 | 1 | 9000 | #01~#06 계열 |
| 보고서디자이너 | 2 | 2 | 1 | 9000 | 일부 WEB/WAS 동일 서버 패턴 존재 |

Application/WAR의 정확한 명칭과 각 JVM별 배포목록은 아직 전체 71대에 대해 확정되지 않았으므로 별도 Application Deployment Inventory가 필요하다.

---

# 15. HA / DR Architecture

## 15.1 기본 원칙

```text
운영센터
 #01 / #02
      │
      │ 서비스 이중화
      ▼
Load Balancer

DR센터
 #51 / #52
```

현재 구성자료에서 마케팅플랫폼, 미니싱글뷰 등은 운영 `#01/#02`와 DR `#51/#52` 패턴이 확인되었다.

대표 DR Hostname 패턴:

```text
운영
sbmpcolowb01 / 02
sbmpcolows01 / 02

DR
sbmpcolowb51 / 52
sbmpcolows51 / 52
```

미니싱글뷰 역시 동일하게 `#51/#52` DR 패턴을 가진다.

## 15.2 세션 HA

센터 내부 Session HA Working Baseline:

- DeltaManager
- `<distributable/>`
- `jvmRoute`
- NTP 동기화
- 세션 객체 권장 ≤2KB
- 최대 ≤5KB
- 대형 고객조회/거래목록 객체 Session 저장 금지

센터 간 세션복제는 별도 DR 정책으로 관리하고, 단순히 센터 내부 DeltaManager를 DR까지 확장하지 않는다.

---

# 16. Architecture Zone 매핑

| Zone | 서버군 | 책임 |
|---|---|---|
| Presentation | WEB | HTTP 진입, Reverse Proxy |
| Application | WAS | 온라인 업무 처리 |
| Real-time Processing | 실시간/행동정보 AP | 이벤트/행동 처리 |
| Customer Data Processing | 고객행동 데이터 AP | 고객행동 데이터 처리 |
| BI/Analytics | BI/Self-BI | 조회·분석 |
| Governance | 데이터품질/흐름 | 메타·품질·Lineage |
| Batch | Batch/ETL | 대량 처리 |
| Integration | CDC 중계 | 데이터 변경 연계 |
| Operational Tool | UNO/RD | 운영/출력 |
| Operational DW | RDW | 운영/조회계 데이터 |
| Analytical DW | ADW | 분석 데이터 |

---

# 17. 최소사양과 실제사양 분리

기존 서버 최소사양 화면의 기준은 다음과 같다.

| 역할 | 최소 CPU | 최소 MEM | 최소 Disk |
|---|---:|---:|---:|
| WEB | 2 vCPU | 16GB | 60GB |
| WAS | 4 vCPU | 16GB | 70GB |

이는 **최소 구축 Baseline**이며 위 71대의 실제 할당 사양 또는 ISP 용량산정 결과와 동일한 의미가 아니다.

최종 인벤토리에는 다음 3개를 분리한다.

```text
Minimum Spec
     ≠
Capacity Required Spec
     ≠
Allocated / Actual Spec
```

---

# 18. 서버명/Hostname 관리 원칙

서버명은 법인·업무·서버플랫폼·환경·용도·순번의 코드체계로 관리한다.

상호금융 법인코드:

```text
sb
```

운영 서버의 예:

```text
sb + mpco + l + o + wb + 01
```

Hostname은 시스템/업무/환경/역할/순번을 추적할 수 있도록 관리하며, 사진 판독본과 원본 서버명 표준 간 차이가 있으면 **원본 Excel/CMDB를 최종 Source of Truth**로 한다.

---

# 19. SERVER_MASTER 권장 스키마

```text
SERVER_MASTER
├─ environment
├─ center
├─ manufacturer
├─ os
├─ server_type
├─ lifecycle
├─ system_group
├─ application_group
├─ business_code
├─ hostname
├─ server_name
├─ role
├─ cpu_original_core
├─ cpu_allocated_core
├─ memory_gb
├─ os_disk_gb
├─ data_disk_gb
├─ capacity_basis
├─ tpmc
├─ required_core
├─ allocated_core
├─ ha_group
├─ dr_target
├─ middleware
├─ application_mapping_status
└─ evidence_status
```

---

# 20. FILESYSTEM 권장 스키마

서버 1대에 Mount Point가 여러 개 존재하므로 SERVER_MASTER에 반복 행으로 넣지 않는다.

```text
FILESYSTEM
├─ hostname
├─ disk
├─ disk_capacity_gb
├─ volume_group
├─ logical_volume
├─ mount_point
├─ mount_capacity_gb
├─ used_gb
├─ use_percent
├─ owner_user
├─ owner_group
└─ purpose
```

---

# 21. APPLICATION_DEPLOYMENT 권장 스키마

아직 71대 전체에 대해 미완료된 핵심 매핑이다.

```text
APPLICATION_DEPLOYMENT
├─ hostname
├─ tomcat_instance_id
├─ catalina_base
├─ connector_port
├─ application_group
├─ business_code
├─ war_name
├─ context_path
├─ service_id_scope
├─ jvm_heap
├─ max_threads
├─ hikari_pool
├─ target_db
├─ ha_group
└─ dr_pair
```

최종 추적 목표:

```text
System
 ↓
Business
 ↓
Application / WAR
 ↓
Tomcat JVM
 ↓
WAS Hostname
 ↓
CPU / MEM
 ↓
Thread / Heap
 ↓
Hikari
 ↓
RDW / ADW
 ↓
TPS / tpmC
 ↓
HA / DR
```

---

# 22. 현재 주요 GAP / 확인필요

## P0

1. **Application/WAR ↔ Tomcat JVM ↔ Hostname 전체 매핑**
2. **RDW/ADW 96C/384C, Memory, 대용량 Disk가 노드별인지 서버군 전체인지 원본 확인**
3. **RD WAS #01/#02 CPU/MEM 0 표기의 의미 확인**
4. **삭제 OLAP 4대가 최종 Physical Architecture에서 완전히 제외되는지 확정**
5. **데이터흐름 WAS #02 삭제검토 최종 결정**

## P1

6. Hikari 150/180 적용 시 Oracle 전체 Session 총량 검증
7. 일반 JVM Heap 24GB vs 28GB 최종 성능시험 확정
8. Tomcat maxThreads 800~1,000 최종 부하시험 확정
9. 16Core 855 TPS Working Baseline 성능시험 검증
10. 세션 90분 vs 기존 60분 기준 운영정책 확정
11. 모든 WEB/WAS의 HA Pair와 DR Pair 완전 매핑
12. FILESYSTEM의 VG/LV/Mount/사용률 입력

## P2

13. 제조사/OS 버전/Kernel/JDK/Tomcat/Apache 버전
14. IP/VIP/SCAN/DataGuard 상세정보 통합
15. APM/보안 Agent/로그 경로
16. 실제 JVM PID/포트/계정
17. Runtime Evidence 기반 CPU/GC/Thread/Pool p95 검증

---

# 23. Architecture Gate

현재 상태의 Gate는 다음과 같이 판단한다.

| Gate | 상태 | 이유 |
|---|---|---|
| Server Identity | PASS | 71대 식별/분류 |
| CPU/MEM/Disk | CONDITIONAL PASS | 대부분 매핑, 일부 0/-/Appliance 해석 필요 |
| tpmC | CONDITIONAL PASS | ISP 서버군 중심으로 매핑 |
| Capacity Mapping | CONDITIONAL PASS | BI포털은 상세 검증, 전 서버군 확대 필요 |
| Middleware | CONDITIONAL PASS | 구조 확정, 전체 서버별 배치목록 미완료 |
| Application/WAR | HOLD | 전체 71대 매핑 미완료 |
| HA/DR | CONDITIONAL PASS | 일부 시스템 명확, 전체 Pair 미완료 |
| Runtime | HOLD | 성능시험/실운영 Evidence 필요 |
| **Physical Architecture Baseline** | **CONDITIONAL PASS** | 서버/자원 기준은 상당히 완성, Application/Runtime 연결 필요 |

---

# 24. 최종 결론

현재 자료를 기준으로 NSIGHT 서버 아키텍처는 단순한 71대 자산목록 수준을 넘어 다음까지 연결된 상태이다.

```text
71대 Server Inventory
       ↓
CPU / MEM / Disk
       ↓
ISP tpmC / Required Core
       ↓
Allocated Core
       ↓
WEB / WAS / AP / DB Role
       ↓
Apache / Tomcat JVM 구조
       ↓
JVM / Thread / Hikari / Timeout
       ↓
HA / DR
       ↓
Capacity GAP
```

현재 가장 중요한 남은 연결고리는:

```text
Application / WAR
       ↓
Tomcat JVM Instance
       ↓
Hostname
       ↓
Runtime Evidence
```

이다.

이 연결이 완료되면 서버를 하나 선택했을 때 다음을 한 번에 추적할 수 있다.

> **“이 서버는 어느 시스템/업무를 수행하고, 어떤 Application/WAR가 어느 Tomcat JVM에서 실행되며, CPU/MEM/Disk가 얼마이고, 몇 tpmC/TPS를 수용하며, Thread/Heap/Hikari가 얼마이고, 어느 DB를 사용하며, 장애 시 어느 서버/센터가 대체하는가?”**

이 상태를 NSIGHT **Physical Architecture + Capacity + Runtime Inventory Baseline**의 최종 목표로 한다.

---

# 25. Source Basis

본 통합본은 다음 자료군을 기준으로 작성했다.

- NSIGHT 서버 최종 상세 인벤토리 CPU/MEM/DISK/tpmC
- 운영시스템 구성 체계
- DR 시스템 구성 체계
- WEB/WAS 미들웨어 구성 및 점검자료
- 서버 최소사양 자료
- ISP WEB/WAS/DB 용량산정 화면
- BI포털 WEB/WAS 확대 용량산정 화면
- TPMC/TPS 기반 용량산정 보고서
- NSIGHT 성능 파라미터 비교/환경구성 자료
- JVM/Tomcat/Hikari/Spring/MyBatis 설정 자료
- NSIGHT 아키텍처 전략 및 Physical Architecture 방향
- 현재 대화에서 제공된 최신 확대 이미지

---

**문서 상태:** Current Working Baseline  
**다음 승격 조건:** Application/WAR 매핑 + HA/DR 전수검증 + Runtime 성능시험 Evidence + Appliance 원본사양 확인
