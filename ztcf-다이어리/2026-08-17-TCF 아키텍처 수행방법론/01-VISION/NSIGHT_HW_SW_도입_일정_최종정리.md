# 농협 상호금융 NSIGHT HW·SW 도입 일정 최종 정리

## 1. 문서 개요

### 1.1 목적

본 문서는 농협 상호금융 NSIGHT 정보계의 **HW·SW 도입 일정을 개발·운영·DR 환경별로 통합 정리**하고, 각 도입 품목의 차수·목표 시점·진행상태·주요 용도 및 후속 관리 기준을 명확히 하기 위한 기준 문서이다.

본 문서의 일정은 제공된 「HW SW 도입 일정 정리」 화면 및 정리자료에서 판독 가능한 내용을 기준으로 작성하였다. 원본에서 명확히 확인되지 않는 세부 일자는 임의로 보정하지 않으며, 월 단위 또는 상태 중심으로 관리한다.

---

## 2. 적용 범위

| 구분 | 적용 범위 |
|---|---|
| 환경 | 개발, 운영, DR |
| HW | IaaS(x86), UNIX, Exadata X11M, Disk/Storage, ZFS |
| SW | Cloud 관리 플랫폼, UI, 데이터 서비스, DBMS, DB Middleware, CDC, SMS |
| 데이터 플랫폼 | RDW, ADW |
| 주요 솔루션 | WebTopSuite, Oracle GoldenGate, GTOne 계열, CUNI 3.0 계열 |
| 관리 단계 | 계획수립 → SR요청 → 환경구축 → 솔루션 설치 → 구성점검 → 검수/운영전환 |

---

## 3. 전체 일정 관리 구조

```text
계획수립
   ↓
SR 요청
   ↓
환경 구축
   ↓
솔루션 설치
   ↓
구성 점검
   ↓
검수 / 운영 전환
```

도입 일정은 단순 구매 일정이 아니라 다음 관계를 함께 관리하는 것을 원칙으로 한다.

```text
도입 품목
   ↓
도입 차수
   ↓
환경
   ↓
설치 대상 서버
   ↓
설치 SW / Middleware
   ↓
구성 점검
   ↓
서비스 투입
   ↓
운영 / DR 대응
```

---

# 4. 개발환경 HW·SW 도입 일정

## 4.1 개발환경 요약

개발환경은 초기 IaaS 기반 WEB/WAS/AP/ETL 자원과 데이터 플랫폼을 먼저 확보하고, 이후 WebTopSuite·데이터 서비스·DB 관련 SW·CDC를 구성하는 방식이다.

| 환경 | 구분 | 주요 도입 대상 | 차수 | 도입/설치 목표 | 상태 | 비고 |
|---|---|---|---:|---|---|---|
| 개발 | HW | IaaS(x86) 서버 | 1차 | 2025.12 | 검수완료 | WEB/WAS/배치AP/업무AP |
| 개발 | HW | IaaS(x86) 서버 | 1차 | 2025.12 | 검수완료 | ETL |
| 개발 | HW | 디스크 | 1차 | 2025.12 | 검수완료 | ETL |
| 개발 | HW | Appliance Exadata X11M | 1.5차 | 2026.03 | 설치지원 | RDW #1, RDW #2, ADW 개발환경 추가 구성 |
| 개발 | SW | 클라우드 관리 플랫폼 | 1차 | 2025.12 | 검수완료 | CUNI 3.0 계열 |
| 개발 | SW | 단말 UI | 1.5차 | 2026.03 | 설치지원 | WebTopSuite |
| 개발 | SW | 데이터 분산/서비스 | 1.5차 | 2026.03 | 설치지원 | GTOne 계열 |
| 개발 | SW | 데이터베이스 관련 | 1.5차 | 2026.03 | 설치지원 | GTOne 계열 |
| 개발 | SW | DB 미들웨어 | 1.5차 | 2026.03 | 설치지원 | GTOne 계열 |
| 개발 | SW | CDC | 1.5차 | 2026.03 | 설치지원 | Oracle GoldenGate |

## 4.2 개발환경 일정 흐름

```text
2025.12
  │
  ├─ IaaS WEB/WAS/AP 도입
  ├─ ETL 서버/디스크 도입
  └─ Cloud 관리 플랫폼 도입
        │
        ▼
2026.03
  │
  ├─ Exadata 개발환경
  ├─ WebTopSuite
  ├─ 데이터 서비스/DB 관련 SW
  ├─ DB Middleware
  └─ Oracle GoldenGate
        │
        ▼
개발환경 구성점검
        │
        ▼
통합개발 / 시험
```

---

# 5. 운영환경 HW·SW 도입 일정

## 5.1 운영환경 요약

운영환경은 **IaaS WEB/WAS/AP → RDW → CDC → ADW → 백업/ZFS → SMS → 최종 구성점검**으로 확장되는 구조이다.

| 환경 | 구분 | 주요 도입 대상 | 차수 | 도입/설치 목표 | 상태 | 비고 |
|---|---|---|---:|---|---|---|
| 운영 | HW | IaaS(x86) 서버 | 2차 | 2026.08 | 계약완료 | WEB/WAS/배치AP/업무AP |
| 운영 | HW | IaaS(x86) 서버 | 2차 | 2026.08 | 계약완료 | ETL |
| 운영 | HW | 물리(UNIX) 서버 | 1차 | 2026.05 | 계약완료 | CDC 중계서버 |
| 운영 | HW | Appliance Exadata X11M | 1.5차 | 2026.05 | 계약완료 | RDW #1, RDW #2 |
| 운영 | HW | 디스크 | 2차 | 2026.08 | 설치지원 | 서버/업무용 디스크 |
| 운영 | SW/HW | 디스크/스토리지 | 3차 | 2027.01 | 도입예정 | CDC/ETL 관련 |
| 운영 | HW | Appliance Exadata X11M | 3차 | 2027.01 | 도입예정 | ADW #1~#6, Full Rack |
| 운영 | SW | ZFS/ADW | 3차 | 2027.01 | 도입예정 | ADW 백업 |
| 운영 | SW | ZFS/RDW | 3차 | 2027.01 | 도입예정 | RDW 백업 |
| 운영 | SW | SMS(x86) | 3차 | 2027.01 | 도입예정 | 서버관리시스템 |
| 운영 | SW | Appliance Storage SW(RDW) | 1.5차 | 2026.03 | 설치지원 | Exadata Storage SW |
| 운영 | SW | Appliance Oracle DBMS(RDW) | 1.5차 | 2026.03 | 설치지원 | Oracle DBMS |
| 운영 | SW | 단말 UI | 1.5차 | 2026.03 | 설치지원 | WebTopSuite |
| 운영 | SW | 데이터 분산/서비스 | 1.5차 | 2026.03 | 설치지원 | GTOne 계열 |
| 운영 | SW | 데이터베이스 관련 | 1.5차 | 2026.03 | 설치지원 | GTOne 계열 |
| 운영 | SW | DB 미들웨어 | 1.5차 | 2026.03 | 설치지원 | GTOne 계열 |
| 운영 | SW | CDC | 2차 | 2026.05 | 도입예정 | Oracle GoldenGate |
| 운영 | SW | CDC | 2차 | 2026.05 | 도입예정 | RDW 운영서버 |
| 운영 | SW | SMS(UNIX) | 2차 | 2026.05 | 도입예정 | 서버관리시스템 |
| 운영 | SW | 클라우드 관리 플랫폼 | 2차 | 2026.05 | 도입예정 | CUNI 3.0 운영 |

## 5.2 운영환경 일정 흐름

```text
2026.03
  │
  ├─ RDW Storage SW
  ├─ RDW Oracle DBMS
  ├─ WebTopSuite
  └─ GTOne 계열 SW
        │
        ▼
2026.05
  │
  ├─ RDW Exadata
  ├─ CDC 중계 UNIX
  ├─ Oracle GoldenGate
  ├─ SMS(UNIX)
  └─ CUNI 운영
        │
        ▼
2026.08
  │
  ├─ 운영 IaaS WEB/WAS/AP
  ├─ ETL 서버
  └─ Disk
        │
        ▼
2027.01
  │
  ├─ ADW Full Rack #1~#6
  ├─ ZFS/RDW
  ├─ ZFS/ADW
  ├─ CDC/ETL Storage
  └─ SMS(x86)
        │
        ▼
운영 구성점검
        │
        ▼
운영전환
```

---

# 6. DR환경 HW·SW 도입 일정

## 6.1 DR환경 요약

DR 환경은 운영환경의 핵심 데이터 플랫폼과 서비스 연속성을 확보하기 위한 후행 구축 구조로 관리한다.

| 환경 | 구분 | 주요 도입 대상 | 차수 | 도입/설치 목표 | 상태 | 비고 |
|---|---|---|---:|---|---|---|
| DR | HW | Appliance Exadata X11M | 3차 | 2026.10 | 도입예정 | DR RDW/ADW |
| DR | HW | 디스크 | 2차 | 2026.05 | 도입예정 | 서버 스토리지 |
| DR | HW/SW | ZFS/RDW | 3차 | 2027.01 | 도입예정 | 백업 |
| DR | SW | Appliance Storage SW(RDW) | 1.5차 | 2026.03 | 설치지원 | Exadata Storage SW |
| DR | SW | Appliance Oracle DBMS(RDW) | 1.5차 | 2026.03 | 설치지원 | Oracle DBMS |
| DR | SW | 단말 UI | 1.5차 | 2026.03 | 설치지원 | WebTopSuite |
| DR | SW | 클라우드 관리 플랫폼 | 2차 | 2026.05 | 도입예정 | CUNI 3.0 DR |

## 6.2 DR환경 일정 흐름

```text
2026.03
  │
  ├─ RDW Storage SW
  ├─ Oracle DBMS
  └─ WebTopSuite
        │
        ▼
2026.05
  │
  ├─ DR Disk
  └─ CUNI 3.0 DR
        │
        ▼
2026.10
  │
  └─ DR Exadata RDW/ADW
        │
        ▼
2027.01
  │
  └─ ZFS/RDW 백업
        │
        ▼
DR 구성점검
        │
        ▼
Failover / Failback 검증
```

---

# 7. 환경별 주요 마일스톤

| Milestone | 목표 | 주요 대상 | 완료 판단 |
|---|---|---|---|
| M1 | 개발 인프라 확보 | IaaS, WEB/WAS/AP, ETL | 서버·OS·Network 사용 가능 |
| M2 | 개발 데이터 플랫폼 확보 | Exadata RDW/ADW | DB 기동 및 연결 가능 |
| M3 | 개발 SW 구성 | WebTopSuite, GTOne, CDC | 개발 통합시험 가능 |
| M4 | 운영 기본 인프라 확보 | IaaS, RDW, CDC | 운영계 기본 서비스 구성 가능 |
| M5 | 운영 데이터 플랫폼 완성 | RDW + ADW Full Rack | 데이터 처리경로 구성 완료 |
| M6 | 운영 관리/백업체계 완성 | ZFS, SMS, CDC | 백업·관측·통제 가능 |
| M7 | DR 구축 | DR Exadata, Storage, SW | DR 기동 가능 |
| M8 | 운영/DR 정합성 검증 | 전체 | Failover/Failback 검증 완료 |

---

# 8. Critical Path

현재 자료를 기준으로 가장 중요한 구축 Critical Path는 다음으로 관리한다.

```text
Exadata 도입
   ↓
RDW 구축
   ↓
DBMS / Storage 설치
   ↓
CDC 구축
   ↓
ADW 구축
   ↓
백업 / ZFS 구축
   ↓
운영 구성점검
   ↓
DR 구성점검
   ↓
Failover / Failback 검증
```

이 경로의 어느 한 단계라도 지연되면 후속 데이터 이행, 통합시험, 운영전환 및 DR 시험에 직접 영향을 줄 수 있으므로 별도 Milestone 관리가 필요하다.

---

# 9. Architecture 관점의 도입 품목 매핑

| 아키텍처 영역 | 주요 도입 품목 | 역할 |
|---|---|---|
| WEB | IaaS(x86), WebTopSuite | 사용자 접점 및 WEB 계층 |
| WAS/AP | IaaS(x86), Cloud Framework | 업무 서비스 실행 |
| RDW | Exadata X11M, Oracle DBMS | 온라인/실시간 조회 데이터 |
| ADW | Exadata X11M Full Rack | 분석/배치 데이터 |
| CDC | UNIX 중계서버, Oracle GoldenGate | 원천 → RDW 데이터 변경 전파 |
| ETL | IaaS/Storage | RDW → ADW 정제/적재 |
| Backup | ZFS/RDW, ZFS/ADW | 데이터 백업 |
| 운영관리 | SMS, CUNI | 서버/클라우드 운영관리 |
| DR | DR Exadata, Disk, DBMS | 재해복구 |

---

# 10. 서버 Master Inventory 연계 기준

HW/SW 도입 일정은 서버 인벤토리와 별개로 관리하지 않고 다음 키로 연결한다.

| 관리영역 | 필수 항목 |
|---|---|
| 식별 | 환경, 시스템그룹, Hostname, 서버명 |
| 역할 | WEB/WAS/AP/DB/ETL/CDC/Backup |
| 자원 | CPU, MEM, Disk |
| 도입 | HW/SW 구분, 도입차수, 계약상태 |
| 일정 | 계획일, SR요청일, 환경구축일, 설치일, 구성점검일 |
| SW | 설치 솔루션, 버전, 라이선스 |
| 데이터 | RDW/ADW/CDC/ETL 연결관계 |
| 가용성 | HA Group, 운영 대응서버, DR 대응서버 |
| 검증 | 설치검수, 구성점검, 운영전환, DR 시험 |
| 증적 | 원본자료, 변경이력, 담당조직, 비고 |

권장 통합키는 다음과 같다.

```text
환경
+ 시스템그룹
+ Hostname
+ 서버역할
+ 도입차수
+ 솔루션
```

---

# 11. 상태 관리 기준

| 상태 | 의미 |
|---|---|
| 계획 | 계획수립 단계 |
| SR요청 | 인프라/솔루션 작업요청 단계 |
| 계약완료 | 구매/계약 완료 |
| 환경구축 | 서버/스토리지/네트워크 구축 중 |
| 설치지원 | 솔루션 설치 또는 구성 지원 중 |
| 구성점검 | 설치 이후 환경/연계/설정 검증 |
| 검수완료 | 해당 도입 차수 검수 완료 |
| 도입예정 | 향후 차수 도입 대상 |
| 운영전환 | 운영 서비스 투입 완료 |
| DR검증완료 | Failover/Failback 검증 완료 |

---

# 12. 주요 점검사항

## 12.1 일정 정합성

- 동일 솔루션의 개발/운영/DR 설치 시점 차이를 관리한다.
- HW 도입 전에 해당 SW 설치일이 앞서는 경우 실제 설치대상 또는 라이선스 선반영 여부를 확인한다.
- Exadata 장비 도입일과 Storage SW/Oracle DBMS 설치일의 선후 관계를 검증한다.
- CDC는 원천·중계·RDW 대상 전체 구성 완료 시점을 하나의 서비스 준비완료 기준으로 관리한다.
- ADW Full Rack 도입 이후 ETL/Backup/운영관리 구성 완료 시점을 함께 관리한다.

## 12.2 운영전환 점검

- WEB/WAS/AP 서버 Hostname 및 운영 인벤토리 매핑
- Apache/Tomcat 설치 및 Instance/Port 확인
- JVM/Thread/DB Pool 설정
- RDW/ADW DB 연결
- CDC 동기화
- ETL Job 연계
- Monitoring/SMS 등록
- Backup/ZFS 구성
- 보안·계정·접근통제
- 운영/DR 대응관계

## 12.3 DR 점검

- 운영 ↔ DR 서버 대응관계
- RDW/ADW 복제/복구 방식
- 데이터 정합성
- 운영 중단 판단기준
- Failover 절차
- Failback 절차
- RTO/RPO
- DR 전환 후 WEB/WAS/DB/CDC/ETL 정상 여부

---

# 13. 현재 확인이 필요한 GAP

| GAP ID | 항목 | 현재 상태 | 후속조치 |
|---|---|---|---|
| HW-SW-GAP-01 | 원본 일자 단위 일정 | 월 단위 중심 | 원본 Excel과 대조 |
| HW-SW-GAP-02 | 계획수립/SR요청 상세일 | 일부 미확정 | 담당자 일정표 확인 |
| HW-SW-GAP-03 | 환경구축 상세일 | 일부 미확정 | 인프라 작업계획 연결 |
| HW-SW-GAP-04 | 솔루션 설치 상세일 | 일부 미확정 | 솔루션사 계획 확인 |
| HW-SW-GAP-05 | 구성점검 상세일 | 일부 미확정 | 점검 Checklist 연계 |
| HW-SW-GAP-06 | 각 품목의 Hostname 매핑 | 별도 인벤토리에 존재 | Master Inventory JOIN |
| HW-SW-GAP-07 | 솔루션 Version/License | 미확정 | SW Inventory 추가 |
| HW-SW-GAP-08 | 운영↔DR 1:1 대응 | 별도 자료 존재 | DR Mapping Table 생성 |
| HW-SW-GAP-09 | RTO/RPO | 본 일정표에서 확인되지 않음 | DR 설계서에서 확정 |
| HW-SW-GAP-10 | 검수 기준 | 상태만 존재 | Acceptance Criteria 정의 |

---

# 14. Architecture Gate

HW/SW 도입 완료는 단순히 장비 입고 또는 SW 설치만으로 판단하지 않는다.

```text
HW 도입
  ↓
OS / Network
  ↓
Middleware / DB / Solution
  ↓
Application 연결
  ↓
Data 연결
  ↓
Monitoring / Backup
  ↓
HA / DR
  ↓
성능 / 장애시험
  ↓
운영 증적
```

최종 Gate는 다음 기준을 사용한다.

| Gate | 완료 조건 |
|---|---|
| G1 자원확보 | HW/VM/Storage 확보 |
| G2 환경구축 | OS/Network/계정/보안 구성 |
| G3 SW설치 | Middleware/DB/Solution 설치 |
| G4 구성정합성 | 서버·포트·DB·연계 설정 검증 |
| G5 서비스시험 | Application/Interface 정상 |
| G6 운영준비 | Monitoring/Backup/Runbook 완료 |
| G7 DR검증 | Failover/Failback 검증 |
| G8 최종승인 | 운영 인수 및 증적 완료 |

Gate 판정은 `PASS / CONDITIONAL PASS / HOLD / REJECT`로 관리한다.

---

# 15. 최종 관리표 권장 컬럼

최종 Excel 또는 관리 DB에서는 다음 컬럼을 권장한다.

```text
환경
HW/SW
품목분류
제품/솔루션명
도입차수
수량
대상시스템
대상서버
Hostname
서버역할
CPU
MEM
Disk
계획수립일
SR요청일
환경구축일
솔루션설치일
구성점검일
검수일
운영전환일
현재상태
운영대응서버
DR대응서버
담당조직
담당자
근거자료
비고
```

---

# 16. 최종 결론

NSIGHT HW·SW 도입 일정은 다음 세 축으로 관리하는 것이 최종 방향이다.

```text
① 일정 축
계획수립 → SR → 구축 → 설치 → 점검 → 검수

② 자산 축
HW/SW → 서버 → Hostname → 자원 → 솔루션

③ 아키텍처 축
개발 → 운영 → DR
      +
WEB/WAS/AP → RDW/ADW → CDC/ETL → Backup/운영관리
```

따라서 본 일정표는 향후 **서버 Master Inventory, Middleware Inventory, Solution Inventory, DR Mapping, Architecture Gate**와 연결하여 하나의 Physical Architecture Baseline으로 관리한다.

---

## 17. 출처

- 「HW SW 도입 일정 정리」 원본 화면 및 정리자료
- NSIGHT 서버/운영 시스템 구성 자료
- NSIGHT 아키텍처 연속성 기준 문서

> 주의: 본 문서의 일정/상태는 제공 자료에서 판독 가능한 범위의 Working Baseline이다. 원본 Excel 또는 공식 PMO 일정표가 확보되면 상세 일자와 상태를 대조하여 Baseline을 갱신해야 한다.
