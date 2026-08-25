# 물리기술아키텍처 — 하드웨어 목록 — DR·개발·선도환경 분석

> 원본 범위: `3. 물리 기술 아키텍처 > 3.3 하드웨어 목록`  
> 원본 장표: DR환경, 개발환경, 선도환경 구성내역  
> 분석 원칙: 이미지에 명시된 Hostname·서버명·역할·CPU·MEM·Disk를 우선 기록한다. Appliance의 병합 셀은 장비 전체 합계일 수 있어 개별 노드 사양으로 중복 합산하지 않는다.

---

## 1. 핵심 결론

- **DR환경**은 안성센터에 마케팅·단말·마스터솔루션·RDW를 배치한 선택적 복구 구성이다.
- **개발환경**은 의왕센터에서 전 업무 개발·검증과 DevOps·ETL·SQL품질을 지원하는 29개 행의 통합 구성이다.
- **선도환경**은 의왕센터에서 마케팅·단말·DevOps 패턴을 먼저 검증하는 12개 행의 최소 구성이다.
- 세 환경 모두 업무 서버는 `x86 / Linux / IaaS`, 데이터플랫폼은 Oracle Appliance를 사용한다.
- DR RDW는 Quarter Rack, 개발·선도 RDW는 Eighth Rack으로 규모가 다르다.

```text
운영(전체 서비스)
   ├─ DR: 핵심 서비스 복구
   ├─ 개발: 전체 기능 개발·통합검증
   └─ 선도: 선행 기술·업무 패턴 검증
```

---

## 2. 환경별 규모 요약

| 환경 | 센터 | IaaS/일반 행 | Appliance 행 | 합계 |
|---|---|---:|---:|---:|
| DR | 안성 | 29 | 2 | 31 |
| 개발 | 의왕 | 27 | 2 | 29 |
| 선도 | 의왕 | 10 | 2 | 12 |
| **전체** | - | **66** | **6** | **72** |

---

# Ⅰ. DR환경

## 3. DR 마케팅플랫폼 목록

공통: `DR / 안성 / x86 / Linux / IaaS`

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk |
|---|---|---:|---:|---:|---:|---:|
| sbmpcolowb51 | 마케팅플랫폼 WEB #51 | WEB | 8 | 32 | 250 | 100 |
| sbmpcolowb52 | 마케팅플랫폼 WEB #52 | WEB | 8 | 32 | 250 | 100 |
| sbmpcolows51 | 마케팅플랫폼 WAS #51 | WAS | 28 | 224 | 250 | 110 |
| sbmpcolows52 | 마케팅플랫폼 WAS #52 | WAS | 28 | 224 | 250 | 110 |
| sbmpmslowb51 | 미니싱글뷰 WEB #51 | WEB | 6 | 20 | 250 | 100 |
| sbmpmslowb52 | 미니싱글뷰 WEB #52 | WEB | 6 | 20 | 250 | 100 |
| sbmpmslows51 | 미니싱글뷰 WAS #51 | WAS | 32 | 264 | 250 | 110 |
| sbmpmslows52 | 미니싱글뷰 WAS #52 | WAS | 32 | 264 | 250 | 110 |

DR 마케팅 WAS는 운영과 완전히 동일한 sizing이 아니다. 마케팅플랫폼 WAS는 28 Core·224 GB, 미니싱글뷰 WAS는 32 Core·264 GB로 업무별 복구 부하를 다르게 산정했다.

## 4. DR 단말관리·배포 목록

공통: `DR / 안성 / x86 / Linux / IaaS`

### 4.1 단말관리

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk |
|---|---|---:|---:|---:|---:|---:|
| sbimxmlowb51 | 단말관리 WEB #51 | WEB | 4 | 16 | 250 | 100 |
| sbimxmlowb52 | 단말관리 WEB #52 | WEB | 4 | 16 | 250 | 100 |
| sbimxmlowb53 | 단말관리 WEB #53 | WEB | 4 | 16 | 250 | 100 |
| sbimxmlowb54 | 단말관리 WEB #54 | WEB | 4 | 16 | 250 | 100 |
| sbimxmlows51 | 단말관리 WAS #51 | WAS | 8 | 64 | 250 | 110 |
| sbimxmlows52 | 단말관리 WAS #52 | WAS | 8 | 64 | 250 | 110 |
| sbimxmlows53 | 단말관리 WAS #53 | WAS | 8 | 64 | 250 | 110 |
| sbimxmlows54 | 단말관리 WAS #54 | WAS | 8 | 64 | 250 | 110 |

### 4.2 단말배포

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk |
|---|---|---:|---:|---:|---:|---:|
| sbimxdlowb51 | 단말배포 WEB #51 | WEB | 4 | 8 | 250 | 100 |
| sbimxdlowb52 | 단말배포 WEB #52 | WEB | 4 | 8 | 250 | 100 |
| sbimxdlowb53 | 단말배포 WEB #53 | WEB | 4 | 8 | 250 | 100 |
| sbimxdlowb54 | 단말배포 WEB #54 | WEB | 4 | 8 | 250 | 100 |
| sbimxdlowb55 | 단말배포 WEB #55 | WEB | 4 | 8 | 250 | 100 |
| sbimxdlowb56 | 단말배포 WEB #56 | WEB | 4 | 8 | 250 | 100 |
| sbimxdlows51 | 단말배포 WAS #51 | WAS | 4 | 8 | 250 | 110 |
| sbimxdlows52 | 단말배포 WAS #52 | WAS | 4 | 8 | 250 | 110 |
| sbimxdlows53 | 단말배포 WAS #53 | WAS | 4 | 8 | 250 | 110 |
| sbimxdlows54 | 단말배포 WAS #54 | WAS | 4 | 8 | 250 | 110 |
| sbimxdlows55 | 단말배포 WAS #55 | WAS | 4 | 8 | 250 | 110 |
| sbimxdlows56 | 단말배포 WAS #56 | WAS | 4 | 8 | 250 | 110 |

## 5. DR 마스터솔루션·RDW 목록

| 환경 | 제조/OS/Type | Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---|---|---:|---:|---:|---:|---:|---|
| DR | x86/Linux/IaaS | sbimfwloap51 | 마스터솔루션 #51 | AP | - | - | 250 | - | CPU/MEM 미기재 |
| DR | Oracle/Linux/Appliance | sbrdcoxodb51 | RDW Appliance #51 | DB | 96* | 1,024* | - | 249,856* | Quarter Rack |
| DR | Oracle/Linux/Appliance | sbrdcoxodb52 | RDW Appliance #52 | DB | 공유/합계 | 공유/합계 | - | 공유/합계 | Quarter Rack |

`*`는 2개 RDW 행에 병합된 값으로 Quarter Rack 전체 합계일 가능성이 높다.

```text
DR RDW Quarter Rack
CPU 96 / MEM 1,024 GB / 추가 Disk 249,856 GB
```

---

# Ⅱ. 개발환경

## 6. 개발 마케팅플랫폼 목록

공통: `개발 / 의왕 / x86 / Linux / IaaS`

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---:|---:|---:|---:|---:|---|
| sbmpcoltwb01 | 마케팅플랫폼 WEB #01 | WEB | 8 | 32 | 250 | 100 | 미니싱글뷰 통합구성 |
| sbmpcoltws01 | 마케팅플랫폼 WAS #01 | WAS | 32 | 256 | 250 | 110 | 미니싱글뷰 통합구성 |
| sbmpepltap01 | 실시간 처리 AP #01 | AP | 16 | 32 | 250 | 1,024 | |
| sbmpbpltap01 | 행동정보 처리 AP #01 | AP | 16 | 32 | 250 | 1,024 | |
| sbmpbdltap01 | 고객 행동 데이터 AP #01 | AP | 16 | 32 | 250 | 1,024 | |

## 7. 개발 BI·신용실적·Self-BI 목록

공통: `개발 / 의왕 / x86 / Linux / IaaS`

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---:|---:|---:|---:|---:|---|
| sbbiptltwb01 | BI 포탈 WEB #01 | WEB | 2 | 8 | 250 | 110 | |
| sbbiptltws01 | BI 포탈 WAS #01 | WAS | 4 | 32 | 250 | 110 | |
| sbbicrltwb01 | 신용 실적 WEB #01 | WEB | 2 | 8 | 250 | 170 | |
| sbbicrltws01 | 신용 실적 WAS #01 | WAS | 4 | 32 | 250 | 170 | |
| sbbisbltwb01 | Self-BI WEB #01 | WEB | 2 | 8 | 250 | 100 | |
| sbbisbltws01 | Self-BI WAS #01 | WAS | 8 | 64 | 250 | 200 | AUD |
| sbbisbltap01 | Self-BI AP #01 | AP | 8 | 64 | 250 | 200 | TRINITY |

## 8. 개발 데이터거버넌스 목록

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk |
|---|---|---:|---:|---:|---:|---:|
| sbdgdqltws01 | 비즈메타/데이터품질 WAS #01 | WAS | 4 | 32 | 250 | 200 |
| sbdgdlltws01 | 데이터흐름 WAS #01 | WAS | 4 | 32 | 250 | 300 |

## 9. 개발 IT서비스·업무지원 목록

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---:|---:|---:|---:|---:|---|
| sbimxmltwb01 | 단말관리 WEB #01 | WEB | 4 | 8 | 250 | 100 | |
| sbimxmltws01 | 단말관리 WAS #01 | WAS | 4 | 8 | 250 | 110 | |
| sbimxdltwb01 | 단말배포 WEB #01 | WEB | 4 | 8 | 250 | 100 | |
| sbimxdltws01 | 단말배포 WAS #01 | WAS | 4 | 8 | 250 | 110 | |
| sbimxmltap01 | 단말개발도구 AP #01 | AP | 4 | 8 | 250 | 100 | |
| sbimbjltap01 | 배치 AP #01 | AP | 6 | 96 | 250 | - | |
| sbimrdltws01 | 출력물(RD) WAS #01 | WAS | 0* | 0* | 250 | 110 | 장표 CPU/MEM `0 GB` 표기 확인 필요 |
| sbimdtltl01 | ETL #01 | AP | 16 | 64 | 250 | 1,000 | |
| sbimsmltap01 | 소스관리 AP #01 | AP | 4 | 32 | 250 | 200 | 배포관리와 통합 구성 |
| sbimfwltap01 | 마스터솔루션 #01 | AP | 8 | 64 | 250 | 100 | 미들웨어 요청 |
| - | 라이브러리 #01 | - | - | - | - | - | AS-IS Nexus 사용 |
| sbimcdltap01 | UNO Dashboard #01 | AP | 4 | 8 | 250 | 10 | OGG 모니터링 |
| sbimsiltap01 | SQL 품질 | AP | 16 | 32 | 250 | 100 | |

`출력물(RD) WAS`의 CPU/MEM는 원본이 `0`, `0 GB`로 보인다. 실제 무할당인지 표기 오류인지 VM 명세로 반드시 확인해야 한다.

## 10. 개발 RDW Appliance 목록

| 제조/OS/Type | Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---|---:|---:|---:|---:|---:|---|
| Oracle/Linux/Appliance | sbrdcoxtdb01 | RDW Appliance #01 | DB | 48* | 768* | - | 124,928* | Eighth Rack |
| Oracle/Linux/Appliance | sbrdcoxtdb02 | RDW Appliance #02 | DB | 공유/합계 | 공유/합계 | - | 공유/합계 | Eighth Rack |

```text
개발 RDW Eighth Rack
CPU 48 / MEM 768 GB / 추가 Disk 124,928 GB
```

---

# Ⅲ. 선도환경

## 11. 선도 IaaS 하드웨어 목록

공통: `선도 / 의왕 / x86 / Linux / IaaS`

| Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---:|---:|---:|---:|---:|---|
| sbmpcoltwb01 | 마케팅플랫폼 WEB #01 | WEB | 8 | 32 | 250 | 100 | 미니싱글뷰 통합구성 |
| sbmpcoltws01 | 마케팅플랫폼 WAS #01 | WAS | 32 | 256 | 250 | 110 | 미니싱글뷰 통합구성 |
| sbimxmltwb01 | 단말관리 WEB #01 | WEB | 4 | 8 | 250 | 100 | |
| sbimxmltws01 | 단말관리 WAS #01 | WAS | 4 | 8 | 250 | 110 | |
| sbimxdltwb01 | 단말배포 WEB #01 | WEB | 4 | 8 | 250 | 100 | |
| sbimxdltws01 | 단말배포 WAS #01 | WAS | 4 | 8 | 250 | 110 | |
| sbimxmltap01 | 단말개발도구 AP #01 | AP | 4 | 8 | 250 | 100 | |
| sbimsmltap01 | 소스관리 AP #01 | AP | 4 | 32 | 250 | 200 | 배포관리와 통합 구성 |
| sbimfwltap01 | 마스터솔루션 #01 | AP | 8 | 64 | 250 | 100 | 미들웨어 요청 |
| - | 라이브러리 #01 | - | - | - | - | - | AS-IS Nexus 사용 |

선도환경의 Hostname과 사양은 개발환경의 대응 노드와 동일하게 보인다. 동일 VM을 공유하는지, 별도 논리환경만 분리하는지는 CMDB·IP·VM UUID로 확인해야 한다.

## 12. 선도 RDW Appliance 목록

| 제조/OS/Type | Hostname | 서버명 | 역할 | CPU | MEM | OS Disk | 추가 Disk | 비고 |
|---|---|---|---:|---:|---:|---:|---:|---|
| Oracle/Linux/Appliance | sbrdcoxtdb01 | RDW Appliance #01 | DB | 48* | 768* | - | 124,928* | Eighth Rack |
| Oracle/Linux/Appliance | sbrdcoxtdb02 | RDW Appliance #02 | DB | 공유/합계 | 공유/합계 | - | 공유/합계 | Eighth Rack |

개발환경과 동일 Hostname·Rack 사양이므로 선도와 개발이 동일 RDW를 공유하는 구조일 가능성이 높다.

---

## 13. 환경별 하드웨어 세로 요약

```text
DR환경 — 31행
  마케팅 8
  단말관리·배포 20
  마스터솔루션 1
  RDW Appliance 2

개발환경 — 29행
  마케팅 5
  BI·실적 7
  데이터거버넌스 2
  IT서비스·업무지원 13
  RDW Appliance 2

선도환경 — 12행
  마케팅 2
  단말·DevOps 8
  RDW Appliance 2
```

---

## 14. 환경 간 사양 비교

| 구성 | DR | 개발 | 선도 | 분석 |
|---|---|---|---|---|
| 마케팅 WEB | 2대, 8C/32G | 1대, 8C/32G | 1대, 8C/32G | DR 이중화 |
| 마케팅 WAS | 2대, 28C/224G | 1대, 32C/256G | 1대, 32C/256G | DR 개별 노드 축소 |
| 미니싱글뷰 | WEB/WAS 각 2대 | 마케팅에 통합 | 마케팅에 통합 | 환경별 배치 차이 |
| 단말관리 | WEB/WAS 각 4대 | 각 1대 | 각 1대 | DR 서비스 지속성 우선 |
| 단말배포 | WEB/WAS 각 6대 | 각 1대 | 각 1대 | DR 대량 배포 처리 유지 |
| RDW | Quarter Rack | Eighth Rack | Eighth Rack | DR가 개발보다 큰 용량 |
| BI·거버넌스 | 미표시 | 구성 | 미표시 | DR·선도 범위 제한 |

---

## 15. 주요 아키텍처 위험

| 위험 | 영향 | 대응 방향 |
|---|---|---|
| 개발·선도 Hostname 중복 | 자산·배포 대상 혼선 | VM UUID, IP, tenant/project로 공유 여부 확정 |
| Appliance 병합값 중복 합산 | 용량 산정 오류 | Rack 전체와 DB 노드별 자원 분리 |
| 라이브러리 자원 미기재 | Nexus 장애·용량 계획 누락 | AS-IS Nexus 자산과 백업 명세 연결 |
| 출력물 서버 0 사양 | 구축 또는 CMDB 오류 | 실제 할당 vCPU·MEM 확인 |
| DR 선택적 구성 | 일부 업무 재해복구 불가 | 업무별 RTO/RPO와 DR 포함 범위 대조 |
| 개발·선도 공용 RDW | 시험 간 데이터·성능 간섭 | schema·계정·resource manager 격리 |
| 단일 개발 노드 | 장애·부하 시험 한계 | 복구 자동화 및 운영 전 별도 HA 시험 |

---

## 16. 검증 체크리스트

- [ ] 72개 표 행을 CMDB·가상화 관리시스템과 대조했는가?
- [ ] 개발과 선도의 동일 Hostname이 실제 공유 자원인지 확인했는가?
- [ ] RDW Quarter/Eighth Rack 수치가 장비 합계임을 확인했는가?
- [ ] 출력물 WAS의 CPU·MEM `0` 표기를 정정했는가?
- [ ] 라이브러리 AS-IS Nexus의 Hostname·사양·백업을 연결했는가?
- [ ] DR 마케팅 WAS의 sizing 근거와 목표 동시사용자를 검증했는가?
- [ ] DR에서 제외된 BI·거버넌스·배치의 복구대책을 확인했는가?
- [ ] 개발·선도 RDW 계정·schema·데이터를 논리적으로 격리했는가?
- [ ] 모든 추가 Disk의 스토리지 유형과 백업 정책이 정의되었는가?
- [ ] Hostname OCR 결과를 원본 Excel 또는 자산대장으로 재검증했는가?

---

## 17. 최종 평가

DR·개발·선도 하드웨어는 동일한 x86 Linux IaaS 표준을 공유하면서 환경 목적에 따라 노드 수와 데이터플랫폼 규모를 달리한다. DR은 핵심 서비스의 다중 노드와 Quarter Rack RDW로 복구 능력을 확보하고, 개발은 전체 기능을 단일 노드 위주로 제공하며, 선도는 마케팅·단말·DevOps 패턴만 최소 구성한다.

핵심 확인사항은 **개발·선도 자원 공유 여부, Appliance 병합 사양 해석, 미기재 자원 보완, DR 범위와 RTO/RPO의 정합성**이다.
