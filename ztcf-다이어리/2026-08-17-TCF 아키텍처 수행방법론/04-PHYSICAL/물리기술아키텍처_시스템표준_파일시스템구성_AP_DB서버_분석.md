# 물리기술아키텍처 — 시스템 표준 — 파일 시스템 구성 — AP·DB 서버 분석

## 1. 핵심 결론

장표는 AP·DB 서버의 디렉터리를 업무 관점에서 크게 다음 세 영역으로 분류한다.

1. **Program Area** — 프로그램 및 스키마
2. **Application Log Area** — 프로그램 실행 로그
3. **Data Area** — 프로그램 입·출력 파일

기본 경로는 각각 `/pgm`, `/aplog`, `/userdir`이며, HA 적용 시스템에서는 경로 뒤에 호스트명을 붙인 `/pgm_hostname`, `/aplog_hostname`, `/userdir_hostname` 형식을 사용한다. 이는 다중 노드가 동일 스토리지나 상위 네임스페이스를 사용할 때 노드별 파일의 충돌과 혼재를 방지하려는 규칙으로 해석된다.

이와 별도로 DB 엔진과 공통 운영 도구는 제품·기능별 고정 설치 경로를 사용한다.

```text
AP·DB 서버 파일 시스템
├─ 업무 애플리케이션 영역
│  ├─ /pgm                 프로그램·Schema
│  ├─ /aplog               애플리케이션 로그
│  └─ /userdir             입·출력 데이터
│
├─ DB 소프트웨어 영역
│  └─ /nhod                Oracle Database Engine
│
└─ 공통 운영 에이전트 영역
   ├─ /usr/local/TOS       서버보안(Secuve TOS)
   ├─ /sepa                서버운영 관리
   ├─ /seba                배치작업 관리
   ├─ /SMS                 SMS(HP OpenView) Agent
   └─ /SMT/perfmon         통합관제(perfmon) Agent
```

## 2. 장표 원문 전사

| 구분 | 용도 | 기본 디렉터리 | HA 적용 시스템 디렉터리 |
|---|---|---|---|
| Program Area | 프로그램 및 Schema | `/pgm` | `/pgm_hostname` |
| Application Log Area | 프로그램 실행 시 발생되는 로그 | `/aplog` | `/aplog_hostname` |
| Data Area | 프로그램 실행 시 입·출력되는 파일 | `/userdir` | `/userdir_hostname` |
| Oracle S/W | 오라클 데이터베이스 엔진 | `/nhod` | N/A |
| 서버보안 | 서버보안(Secuve TOS) 설치 디렉터리 | `/usr/local/TOS` | N/A |
| 서버운영 관리 | 서버운영 관리 설치 디렉터리 | `/sepa` | N/A |
| 배치작업 관리 | 배치작업 관리 설치 디렉터리 | `/seba` | N/A |
| SMS | SMS(HP OpenView) Agent 설치 디렉터리 | `/SMS` | N/A |
| 통합관제 | 통합관제(perfmon) Agent 설치 디렉터리 | `/SMT/perfmon` | N/A |

> `hostname`은 실제 서버 호스트명으로 치환하는 자리표시자다. 예를 들어 호스트명이 `sbmpcolows01`이라면 `/pgm_sbmpcolows01`과 같은 방식으로 생성한다. 실제 호스트명과 경로 조합은 배포 표준에서 검증해야 한다.

## 3. 업무 3영역 분리 메커니즘

### 3.1 Program Area — `/pgm`

프로그램 실행 파일, 라이브러리, 설정 템플릿 및 장표에서 언급한 Schema 관련 산출물을 배치하는 영역이다.

- 배포 단위와 버전별 디렉터리를 분리한다.
- 프로그램 바이너리는 운영 중 임의 변경하지 못하도록 쓰기 권한을 제한한다.
- 현재 버전과 직전 정상 버전을 보존해 롤백 시간을 줄인다.
- 설정·비밀정보·로그·입출력 데이터를 프로그램 파일과 혼합하지 않는다.
- DB Schema가 DDL 스크립트를 의미한다면 실행 이력과 승인 버전을 소스/배포 관리 시스템에서 추적한다.

권장 내부 구조 예시는 다음과 같다.

```text
/pgm[_hostname]/<service>
├─ releases/
│  ├─ <version-A>/
│  └─ <version-B>/
├─ current -> releases/<active-version>
├─ config/                 # 환경별 설정, 권한 분리
├─ scripts/                # 기동·중지·운영 스크립트
└─ schema/                 # 승인된 DDL·배포 스크립트(해당 시)
```

### 3.2 Application Log Area — `/aplog`

프로그램 실행 시 발생하는 애플리케이션 로그를 저장한다.

- 로그 폭증이 프로그램 실행이나 OS 루트 파일시스템 장애로 전파되지 않도록 독립 파일시스템을 권장한다.
- 서비스·인스턴스·로그 유형별 경로를 표준화한다.
- `logrotate` 또는 제품 자체 회전 정책 중 책임 주체를 하나로 정한다.
- 중앙 로그 플랫폼으로 전송하고 로컬 보존 기간은 최소화한다.
- 개인정보·인증정보·SQL 바인드 값 등 민감정보의 기록을 통제한다.
- 사용량뿐 아니라 inode, 증가율, 열린 삭제 파일을 감시한다.

```text
/aplog[_hostname]/<service>
├─ application/
├─ access/
├─ error/
├─ audit/
└─ gc/                     # JVM 사용 시
```

### 3.3 Data Area — `/userdir`

프로그램 실행 과정에서 입력·출력되는 파일을 저장한다.

- 입력 대기, 처리 중, 완료, 오류, 출력 및 보관 상태를 경로로 분리한다.
- 중복 처리 방지를 위해 파일 이동·상태 전환을 원자적으로 수행한다.
- 보존 기간, 재처리 기준, 파일명 규칙과 암호화 정책을 정의한다.
- 외부 파일 수신 경로에는 확장자·크기·악성코드·권한 검사를 적용한다.
- 대용량 배치 파일은 프로그램·로그와 다른 증설·백업 정책을 적용한다.

```text
/userdir[_hostname]/<service>
├─ inbound/
│  ├─ ready/
│  ├─ processing/
│  ├─ done/
│  └─ error/
├─ outbound/
├─ archive/
└─ work/
```

## 4. 기본 시스템과 HA 시스템의 차이

### 4.1 기본 디렉터리

```text
단일 또는 비공유 구성
AP/DB Node
├─ /pgm
├─ /aplog
└─ /userdir
```

경로가 단순해 운영 편의성이 높지만, 여러 노드가 같은 공유 파일시스템을 마운트하면 동일 경로에 파일이 섞일 수 있다.

### 4.2 HA 적용 디렉터리

```text
공유 상위 스토리지 또는 공통 네임스페이스
├─ /pgm_hostA       ├─ /pgm_hostB
├─ /aplog_hostA     ├─ /aplog_hostB
└─ /userdir_hostA   └─ /userdir_hostB
```

노드별 접미부의 주요 목적은 다음과 같다.

- 다중 노드의 로그·임시·처리 파일 충돌 방지
- 장애 분석 시 생성 노드 식별
- 노드별 용량과 파일 소유권 관리
- Active–Active 구성에서 동시 쓰기 영역 격리

그러나 이 규칙만으로 HA가 보장되지는 않는다. Active–Standby 서비스가 장애전환 후 기존 노드의 프로그램이나 데이터를 사용해야 한다면 다음 중 하나를 명시해야 한다.

1. 서비스가 활성 노드와 무관한 **서비스 공통 경로**를 사용한다.
2. 노드별 실제 경로를 가리키는 **고정 심볼릭 링크 또는 bind mount**를 전환한다.
3. 클러스터 리소스 관리자가 파일시스템 마운트와 링크를 함께 전환한다.
4. 공유 불필요 데이터는 노드 로컬로 유지하고 필요한 상태만 외부 저장소에 둔다.

### 4.3 권장 경로 추상화

```text
애플리케이션이 사용하는 고정 경로
  /pgm/<service>       /aplog/<service>       /userdir/<service>
          │                    │                       │
          └──── 클러스터/마운트/링크 계층에서 현재 활성 자원으로 매핑 ────┘

실제 노드별 저장 위치
  /pgm_hostA/...       /aplog_hostA/...       /userdir_hostA/...
```

애플리케이션 코드에 실제 호스트명을 하드코딩하지 않는 것이 중요하다. 호스트 기반 경로 전환은 배포 또는 클러스터 관리 계층이 담당해야 한다.

## 5. AP 서버와 DB 서버 적용 범위

| 영역 | AP 서버 | DB 서버 | 비고 |
|---|---:|---:|---|
| `/pgm` 계열 | 필수 | 관리 스크립트·Schema 배포 시 사용 | 프로그램 영역 |
| `/aplog` 계열 | 필수 | DB 외부 관리 프로그램 로그 시 사용 | Oracle 진단 로그 위치와 혼동 금지 |
| `/userdir` 계열 | 입출력 파일 사용 시 필수 | 적재·추출·백업 연계 파일 사용 시 적용 | 업무 데이터 영역 |
| `/nhod` | 일반적으로 불필요 | Oracle DB 서버 적용 | DB 엔진 설치 영역 |
| `/usr/local/TOS` | 공통 적용 가능 | 공통 적용 가능 | 보안 에이전트 |
| `/sepa` | 공통 적용 가능 | 공통 적용 가능 | 운영 관리 도구 |
| `/seba` | 배치 실행/관리 시 적용 | DB 배치 관리 시 적용 가능 | 배치 에이전트 |
| `/SMS` | 공통 적용 가능 | 공통 적용 가능 | SMS Agent |
| `/SMT/perfmon` | 공통 적용 가능 | 공통 적용 가능 | 통합관제 Agent |

DB 서버의 실제 Oracle 데이터파일, 제어파일, Redo/Archive Log, FRA 및 백업 영역은 `/nhod`와 분리된 전용 스토리지 또는 ASM Disk Group을 사용할 수 있다. 장표는 DB 엔진 **설치 디렉터리**만 제시하므로 데이터베이스 저장 구조까지 `/nhod` 하나로 해석해서는 안 된다.

## 6. Oracle S/W 영역 — `/nhod`

장표에서 `/nhod`는 오라클 데이터베이스 엔진 설치 디렉터리다.

- Oracle Base, Oracle Home 및 인벤토리의 실제 하위 경로를 표준화한다.
- DB 바이너리와 데이터파일을 분리한다.
- Grid Infrastructure와 Database Home의 소유 계정·그룹을 구분한다.
- 패치 전후 Home 또는 이미지 기반 롤백 전략을 마련한다.
- Cluster/RAC 환경의 각 노드에 동일한 바이너리 버전과 패치 수준을 유지한다.
- `/nhod`가 로컬인지 공유인지 명확히 하며, 일반적으로 실행 Home은 노드별 로컬 구성이 운영 독립성에 유리하다.

```text
/nhod
├─ app/<owner>/product/<version>/dbhome_<n>
├─ grid/<version>                    # 적용 시
└─ inventory                         # 실제 표준에 따라 별도 가능
```

## 7. 공통 운영 도구 영역

### 7.1 `/usr/local/TOS` — 서버보안

- 장표상 Secuve TOS 설치 경로다.
- 보안 에이전트의 실행 파일, 정책, 로그 경로를 구분한다.
- 서비스 계정·커널 모듈·정책 업데이트 권한을 제한한다.
- 제품 버전과 실제 설치 경로가 표준에서 변경되면 CMDB와 자동화 템플릿을 함께 갱신한다.

### 7.2 `/sepa` — 서버운영 관리

- 서버 운영 관리 도구의 설치 영역이다.
- 원격 명령, 배포, 계정 관리 등 고권한 기능이 있다면 실행 이력과 접근 권한을 감사한다.
- 에이전트 로그가 설치 파일시스템을 고갈시키지 않도록 별도 로그 경로를 검토한다.

### 7.3 `/seba` — 배치작업 관리

- 배치 스케줄러 또는 에이전트 설치 영역이다.
- 배치 정의·실행 로그·표준 출력·임시 파일을 설치 바이너리와 분리한다.
- HA 환경에서 동일 Job이 중복 실행되지 않도록 스케줄러의 활성 노드 제어와 연계한다.

### 7.4 `/SMS` — HP OpenView Agent

- 장표상 SMS(HP OpenView) Agent 설치 영역이다.
- 경로가 대문자이므로 Linux의 대소문자 구분을 자동화와 운영 절차에 반영한다.
- 현재 사용 제품과 명칭이 변경되었는지 운영 자산 목록으로 검증해야 한다.

### 7.5 `/SMT/perfmon` — 통합관제

- 통합관제 perfmon Agent 설치 영역이다.
- `/SMS`와 마찬가지로 대소문자를 정확히 유지한다.
- 수집 로그·큐·덤프의 증가량을 감시하고 중앙 관제 장애 시 로컬 버퍼 상한을 둔다.

## 8. 권장 파일시스템 및 LVM 구성

세 업무 영역은 장애 영향과 증가 특성이 다르므로 별도 LV 또는 독립 마운트를 권장한다.

```text
업무 VG 예시: vg_app
├─ lv_pgm[_host]      → /pgm[_hostname]
├─ lv_aplog[_host]    → /aplog[_hostname]
└─ lv_userdir[_host]  → /userdir[_hostname]

DB SW VG 예시: vg_dbsoft
└─ lv_nhod            → /nhod

공통 에이전트
├─ OS 영역 하위       → /usr/local/TOS
└─ 별도 또는 공통 LV  → /sepa, /seba, /SMS, /SMT/perfmon
```

| 영역 | 증가 특성 | 권장 운영 |
|---|---|---|
| Program | 배포 시 계단식 증가 | 릴리스 보존 개수와 롤백 공간 관리 |
| Application Log | 지속·돌발 증가 | 별도 LV, 회전·중앙전송·조기 경보 |
| Data | 업무량에 따라 대용량 증가 | 별도 LV, 보존·아카이브·재처리 정책 |
| Oracle S/W | 패치·Home 추가 시 증가 | 데이터 영역과 분리, 패치 여유 확보 |
| Agent | 버전·로그 증가 | 설치와 로그 분리, 제품별 상한 관리 |

## 9. 용량 산정

```text
/pgm 요구량
  = 릴리스 1개 크기 × 보존 릴리스 수
  + 공통 라이브러리
  + 압축해제/패치 작업 공간
  + 안전 여유

/aplog 요구량
  = 일 로그량 × 로컬 보존 일수
  + 장애 시 중앙 전송 중단 버퍼
  + 피크 배율
  + 안전 여유

/userdir 요구량
  = 일 입력량 + 일 출력량 + 작업 중 임시량
  × 보존/재처리 기간
  + 최대 단일 파일 및 동시 처리 여유
```

평균치만 사용하지 말고 월말·연말·대량 배치 및 장애 시 재처리 피크를 반영한다. 파일 수가 많은 영역은 inode 용량도 함께 산정한다.

## 10. 권한과 보안

| 경로 | 권장 소유 원칙 | 보안 통제 |
|---|---|---|
| `/pgm*` | 배포 계정 소유, 실행 계정 읽기·실행 | 운영 중 쓰기 제한, 무결성 검증 |
| `/aplog*` | 실행 계정 쓰기, 운영/관제 읽기 | 민감정보 마스킹, 삭제 권한 제한 |
| `/userdir*` | 업무 서비스 계정·그룹 | 입력 검증, 암호화, 쿼터, 반출 통제 |
| `/nhod` | Oracle/Grid 전용 계정·그룹 | 일반 계정 접근 금지, 패치 승인 |
| 공통 Agent 경로 | root 또는 제품 전용 계정 | 바이너리 변조 감시, 최소 권한 |

- `777` 권한을 사용하지 않고 서비스별 umask를 정의한다.
- AP 실행 계정과 배포 계정, DB 엔진 계정, 모니터링 계정을 분리한다.
- 실행 불필요 데이터 영역에는 `nodev`, `nosuid`, `noexec`를 검토한다.
- SELinux 컨텍스트와 서비스 정책을 표준 배포에 포함한다.
- HA 공유 스토리지에서는 모든 노드의 UID/GID를 일치시킨다.

## 11. HA 장애전환 시나리오

### 11.1 Active 노드 장애

```text
Active Node 장애 감지
  → 클러스터가 서비스·VIP 정지 확인
  → 공유 파일시스템 또는 대상 LV를 Standby에 마운트
  → 고정 서비스 경로를 대상 실경로에 매핑
  → 파일 잠금·미완료 입력·PID 정리
  → DB/외부 연결 확인
  → 애플리케이션 기동
  → 로그·입출력 중복 여부 검증
```

### 11.2 주요 검증 항목

- `/pgm_hostname`이 노드 로컬인지 공유인지
- 장애전환 후 어느 노드의 Program Area를 사용하는지
- `/aplog_hostname` 로그 연속성과 중앙 수집 태그
- `/userdir_hostname` 미완료 파일의 소유권과 재처리 정책
- 동일 배치·입력 파일의 이중 처리 차단
- 공유 파일시스템의 fencing 및 split-brain 방지
- Oracle RAC/HA 구성에서 `/nhod`의 노드별 패치 일치 여부

## 12. 백업·복구 기준

| 경로 | 백업 중요도 | 복구 방법 |
|---|---|---|
| `/pgm*` | 중간 | 아티팩트 재배포 + 설정·Schema 스크립트 복구 |
| `/aplog*` | 정책별 | 중앙 로그를 기준으로 복구, 감사 로그는 별도 보존 |
| `/userdir*` | 높음 | 업무 RPO/RTO에 따른 증분·전체 백업과 복구 시험 |
| `/nhod` | 중간 | Oracle Home 이미지/패치 기준 재구성, 설정 별도 백업 |
| Agent 경로 | 낮음~중간 | 표준 설치 자동화로 재구성, 제품 설정 백업 |

DB 데이터파일과 Archive/Redo/FRA의 백업은 `/nhod` 백업과 별도의 데이터베이스 백업 정책으로 관리한다.

## 13. 모니터링 기준

- 파일시스템별 용량·inode 사용률과 예상 고갈 시점
- `/aplog*` 시간당 증가량, 로그 회전 및 중앙 전송 지연
- `/userdir*` ready/processing/error 파일 수와 체류 시간
- `/pgm*` 승인되지 않은 파일 변경과 배포 버전 불일치
- HA 노드별 경로 존재 여부, 마운트 소스, 소유권과 UID/GID
- `/nhod` Oracle Home 버전·패치 수준의 노드 간 차이
- `/seba` 배치 중복 실행, `/SMS`·`/SMT/perfmon` Agent 상태와 로컬 큐
- 삭제됐지만 프로세스가 계속 점유하는 대용량 로그 파일
- read-only 재마운트, I/O 오류 및 LVM/VG 여유 공간

## 14. 프로비저닝 자동화 모델

```yaml
application_filesystems:
  program:
    base_path: /pgm
    ha_path_pattern: /pgm_{hostname}
    purpose: program_and_schema
  log:
    base_path: /aplog
    ha_path_pattern: /aplog_{hostname}
    purpose: application_log
  data:
    base_path: /userdir
    ha_path_pattern: /userdir_{hostname}
    purpose: application_io

common_software:
  oracle: /nhod
  server_security: /usr/local/TOS
  server_operations: /sepa
  batch_management: /seba
  sms_agent: /SMS
  monitoring_agent: /SMT/perfmon
```

자동화 시 호스트명은 CMDB의 확정 값을 사용하며, 경로 생성 후 소유권·권한·마운트·SELinux·백업·모니터링 정책을 함께 적용한다.

## 15. 주요 위험과 대응

| 위험 | 영향 | 대응 |
|---|---|---|
| `_hostname` 의미 불명확 | 공유/로컬 경로 오구성 | 스토리지 소스와 HA 전환 소유권 명시 |
| 애플리케이션의 물리 경로 하드코딩 | 장애전환·서버 교체 실패 | 고정 논리 경로와 링크/마운트 추상화 |
| 로그와 프로그램 동일 파일시스템 | 로그 폭증으로 실행 장애 | `/aplog*` 독립 LV 및 회전 |
| 입출력 상태 혼합 | 중복·누락 처리 | ready/processing/done/error 상태 분리 |
| `/nhod`에 DB 데이터 저장 | 엔진 패치와 데이터 장애 도메인 혼합 | DB 데이터·Redo·FRA 전용 영역 분리 |
| 대문자 경로 혼동 | 자동화·운영 스크립트 실패 | `/SMS`, `/SMT/perfmon` 대소문자 검증 |
| HA 공유 영역 UID/GID 불일치 | 장애전환 후 접근 거부 | 노드 간 계정·그룹 ID 표준화 |
| Agent 로그 무제한 증가 | 공통 운영 영역 고갈 | 제품별 로그 분리·상한·감시 |

## 16. 검증 체크리스트

- [ ] `/pgm`, `/aplog`, `/userdir`의 용도와 소유 조직이 구분되는가?
- [ ] HA 대상 경로가 실제 호스트명으로 정확히 생성되는가?
- [ ] HA 경로의 공유/로컬 스토리지 여부가 문서화되어 있는가?
- [ ] 애플리케이션이 물리 호스트 경로를 하드코딩하지 않는가?
- [ ] Program·Log·Data가 독립 용량·백업·보안 정책을 갖는가?
- [ ] `/aplog*` 로그 회전과 중앙 전송 실패 버퍼가 설정되어 있는가?
- [ ] `/userdir*` 입력 파일의 상태 전이와 중복 처리 방지가 구현되어 있는가?
- [ ] `/nhod`와 Oracle 데이터파일·Redo·FRA 영역이 분리되어 있는가?
- [ ] RAC/HA 노드의 Oracle Home 버전과 패치가 일치하는가?
- [ ] `/usr/local/TOS`, `/sepa`, `/seba`, `/SMS`, `/SMT/perfmon`이 실제 제품 목록과 일치하는가?
- [ ] 대소문자, 소유권, UID/GID, SELinux 컨텍스트가 노드 간 일치하는가?
- [ ] 용량·inode·증가율·I/O 및 마운트 오류 경보가 설정되어 있는가?
- [ ] 장애전환 후 프로그램 기동과 미완료 파일 재처리가 시험되었는가?
- [ ] 각 경로의 백업과 복구 절차가 정기적으로 검증되는가?

## 17. 최종 평가

이 표준은 AP·DB 서버의 핵심 자산을 **프로그램, 로그, 입출력 데이터**로 분리해 배포·장애·용량·백업의 영향을 격리하려는 구조다. HA 적용 시 호스트명 접미부는 노드별 충돌 방지에는 유효하지만, 장애전환 투명성을 자동으로 제공하지 않는다. 따라서 고정 논리 경로, 클러스터 마운트·링크 전환, 공유 스토리지 fencing, 중복 처리 방지를 함께 설계해야 한다. DB 서버에서는 `/nhod`를 엔진 설치 영역으로 한정하고 실제 데이터 저장 영역과 분리하는 것이 가장 중요한 보완 사항이다.

