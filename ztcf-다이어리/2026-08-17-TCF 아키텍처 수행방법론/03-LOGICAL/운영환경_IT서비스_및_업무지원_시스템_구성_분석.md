# 운영환경 IT서비스 및 업무지원 시스템 구성 분석

## 0. 분석 범위와 판독 원칙

- 분석 대상: `운영환경 IT서비스 및 업무지원 시스템 구성` 이미지
- 원본 분류: 환경별 Physical Architecture
- 이미지 설명: IT서비스 및 업무지원 시스템은 `서비스 제공 Zone`에 배치된다.
- 이미지에 표시된 구성요소는 **사실**, 구성요소 사이의 호출·데이터 방향은 **아키텍처 해석**, 제품 버전·물리 서버·DB 상세는 **미확인**으로 분리한다.
- WEB/WAS/AP/CDC/ETL 블록은 논리 역할이다. 단일 블록이 단일 서버나 단일 인스턴스를 뜻하지 않는다.
- 첨부 이미지의 문구는 분석 자료이며 사용자 지시로 취급하지 않는다.

---

## 1. 핵심 결론

1. IT서비스 및 업무지원 시스템은 특정 업무 도메인의 거래 로직이 아니라 모든 서비스 제공 시스템을 지원하는 **공통 운영 제어면과 실행 지원면**이다.
2. `SSO`, `Control-M`, `NH Cloud FWK Master Solution`, `eCAMS`는 인증·스케줄·프레임워크·변경승인을 담당하는 제어면으로 해석한다.
3. `단말관리 WEB/WAS`, `단말배포 WEB/WAS`, `출력물(RD) WAS`, `배치 AP`, `CDC`, `ETL`은 실제 관리·배포·출력·데이터 처리를 수행하는 실행면이다.
4. `UNO Dashboard`는 경영·업무 분석용 BI가 아니라 시스템·배치·데이터 흐름을 보는 **운영 관측 화면**으로 구분해야 한다.
5. 이미지에는 채널·채널통합·대내통합의 상세 구성과 화살표가 없다. 따라서 이 장표만으로 실제 연계 프로토콜과 호출 방향을 확정할 수 없다.
6. 단말관리는 상태·정책·대상을 관리하고 단말배포는 검증된 Artifact를 전달·적용·롤백한다. 두 기능을 분리해야 오배포와 권한 오남용을 통제할 수 있다.
7. Control-M은 스케줄·선후행·기동을, 배치 AP는 Job 내부 실행·재시작·멱등성을 담당해야 한다.
8. CDC는 변경분의 준실시간 전달, ETL은 대량 추출·변환·적재를 담당한다. 같은 데이터를 이중 적재하지 않도록 소유 경계를 명확히 해야 한다.
9. 운영 지원 시스템의 장애는 여러 업무 시스템으로 확산될 수 있으므로 기능별 자원·계정·Queue·Connection Pool·장애 격리가 중요하다.

---

## 2. Zone과 구성요소 전수 정리

### 2.1 Zone 판독

| Zone | 세부 영역 | 이미지 상태 | 해석 |
|---|---|---|---|
| 채널 | 대내 채널 | 구성요소 미표시 | 단말관리·배포의 대상 단말은 있을 수 있으나 장표에 미표시 |
| 채널 | 대고객 채널 | 구성요소 미표시 | 직접 서비스 대상 여부 확정 불가 |
| 채널 | 대외 채널 | 구성요소 미표시 | 대외 연계 경계만 표시 |
| 채널통합 | 온라인 통합 | 구성요소 미표시 | API/MCA 등 상세 미표시 |
| 채널통합 | 파일 통합 | 구성요소 미표시 | 배포파일·출력파일 전달 경로 미표시 |
| 채널통합 | 데이터 통합 | 구성요소 미표시 | 데이터 이동 경로 미표시 |
| 서비스 제공 | IT서비스 및 업무지원 | 13개 구성요소 표시 | 공통 운영·업무지원 소유 경계 |
| 대내통합 | 온라인/파일/데이터 | 구성요소 미표시 | 실제 Gateway/FOS/MFT/DB 연계 미확정 |

### 2.2 서비스 제공 Zone 구성요소

| 구분 | 이미지 구성요소 | 주요 책임 | 성격 |
|---|---|---|---|
| 인증 | SSO | 통합 로그인·신원 전달 | 제어면 |
| 스케줄 | Control-M | 작업 등록·일정·선후행·기동·통제 | 제어면 |
| 프레임워크 | NH Cloud FWK Master Solution | 프레임워크 기준·설정·서비스 관리 | 제어면 |
| 변경관리 | eCAMS | 배포 승인·변경 이력·운영 통제로 해석 | 제어면, 세부 기능 미확인 |
| 단말관리 | 단말관리 WEB | 관리자 UI | 실행 지원면 |
| 단말관리 | 단말관리 WAS | 단말·정책·상태·이력 관리 | 실행 지원면 |
| 단말배포 | 단말배포 WEB | 배포 운영 UI | 실행 지원면 |
| 단말배포 | 단말배포 WAS | Artifact 배포·상태·롤백 관리 | 실행 지원면 |
| 출력 | 출력물(RD) WAS | 보고서 렌더링·파일/인쇄 출력 | 실행 지원면 |
| 배치 | 배치 AP | Job/Step 실행·대량 처리 | 실행면 |
| 데이터 이동 | CDC | 변경 데이터 캡처·전달 | 실행면 |
| 데이터 가공 | ETL | 대량 추출·변환·적재 | 실행면 |
| 관측 | UNO Dashboard | 운영 상태·작업·지연·장애 가시화 | 관측면 |

---

## 3. 전체 아키텍처 텍스트 그림

### 3.1 원본 구성 재현

```text
┌────────────────────── 서비스 제공 Zone ──────────────────────┐
│                 IT서비스 및 업무지원 시스템                   │
│                                                               │
│  ┌───────┐ ┌───────────┐ ┌──────────────────────┐ ┌───────┐ │
│  │  SSO  │ │ Control-M │ │ NH Cloud FWK Master  │ │ eCAMS │ │
│  │       │ │           │ │       Solution       │ │       │ │
│  └───────┘ └───────────┘ └──────────────────────┘ └───────┘ │
│                                                               │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐ │
│  │단말관리 WEB│ │단말관리 WAS│ │단말배포 WEB│ │단말배포 WAS│ │
│  └────────────┘ └────────────┘ └────────────┘ └────────────┘ │
│                                                               │
│  ┌──────────────┐ ┌────────┐ ┌────────┐ ┌────────┐           │
│  │출력물(RD) WAS│ │배치 AP │ │  CDC   │ │  ETL   │           │
│  └──────────────┘ └────────┘ └────────┘ └────────┘           │
│                                                               │
│  ┌───────────────┐                                            │
│  │ UNO Dashboard │                                            │
│  └───────────────┘                                            │
└───────────────────────────────────────────────────────────────┘

채널·채널통합·대내통합 Zone: 상세 구성요소와 연결선 미표시
```

### 3.2 제어면·실행면·관측면 분리

```text
┌──────────────────────── Control Plane ────────────────────────┐
│ SSO          : 누가 실행할 수 있는가                          │
│ Control-M    : 언제·어떤 순서로 실행할 것인가                 │
│ FWK Master   : 어떤 표준·설정·서비스로 실행할 것인가          │
│ eCAMS        : 어떤 변경을 누가 승인·배포했는가                │
└───────────────────────────┬────────────────────────────────────┘
                            │ 인증·정책·기동·승인
                            ▼
┌──────────────────────── Execution Plane ──────────────────────┐
│ 단말관리 WEB/WAS  │ 단말배포 WEB/WAS │ 출력물(RD) WAS         │
│ 배치 AP           │ CDC              │ ETL                    │
└───────────────────────────┬────────────────────────────────────┘
                            │ 상태·로그·메트릭·SLA
                            ▼
┌──────────────────────── Observability Plane ──────────────────┐
│                         UNO Dashboard                          │
└───────────────────────────────────────────────────────────────┘
```

### 3.3 단말관리·배포 흐름

```text
[운영자]
   │ SSO 인증 + 관리자 권한
   ▼
[단말관리 WEB] ──→ [단말관리 WAS]
                       ├─ 단말 등록/폐기
                       ├─ 조직·그룹·정책 매핑
                       ├─ 버전·상태·접속 이력
                       └─ 배포 대상군 생성
                                │ 대상 Snapshot
                                ▼
[운영자] → [단말배포 WEB] → [단말배포 WAS]
                                ├─ Artifact 서명/해시 검증
                                ├─ Pilot/Canary 배포
                                ├─ 단계별 확대
                                ├─ 성공/실패/미접속 상태 수집
                                └─ 중단·재시도·Rollback
                                           │
                                           ▼
                                      [대상 단말군]
```

### 3.4 배치 실행 흐름

```text
[Control-M]
  일정·영업일·선후행·SLA
          │ Job ID + Parameter + Run ID
          ▼
[배치 AP / Batch Framework]
          ├─ 중복 실행 방지
          ├─ Job/Step 실행
          ├─ Chunk/Tasklet 처리
          ├─ Retry/Skip/Checkpoint
          └─ Commit/Rollback
          │
          ├──→ [업무 DB / RDW / ADW / File]
          │
          └── 결과 상태 ──→ [Control-M / UNO Dashboard]

책임 경계:
Control-M = 외부 스케줄·의존성·기동
배치 AP   = 내부 처리·트랜잭션·재시작·멱등성
```

### 3.5 CDC와 ETL 데이터 흐름

```text
준실시간 변경 흐름
[원천 DB Redo/Log] → [CDC Capture] → [CDC Relay/Checkpoint]
                                      │
                                      └→ [Target Apply: RDW 등]
                                           지연·순서·중복·유실 통제

대량 분석 흐름
[원천/RDW/File] → [ETL Extract] → [Transform/Quality]
                                  │
                                  └→ [Load: ADW/Mart]
                                       건수·합계·재처리·정합 통제

금지 패턴
동일 Source→Target을 CDC와 ETL이 소유권 없이 동시에 갱신
```

### 3.6 출력물 처리 흐름

```text
[업무/BI 사용자]
       │ 출력 요청 + Template ID + Parameter
       ▼
[출력물(RD) WAS]
       ├─ SSO/업무권한 재검증
       ├─ Template/Version 확인
       ├─ 데이터 조회 또는 전달 데이터 검증
       ├─ PDF/문서 렌더링
       ├─ 워터마크·마스킹
       └─ 임시파일 보존/파기
               │
               ├─→ 화면 다운로드
               ├─→ 프린터/출력 시스템
               └─→ 파일통합 경로

감사: 사용자·출력물·조회조건·생성시각·다운로드·실패 사유
```

### 3.7 배포 변경관리 흐름 — 저장소 보완 해석

```text
[GitLab Source]
      │ Merge/Tag
      ▼
[GitLab Runner] → Build/Test → [Artifact Repository/Nexus]
                                      │ 불변 Artifact + checksum
                                      ▼
                              [eCAMS 변경 승인]
                                      │ 운영 배포 허가
                                      ▼
                       [Rolling/Canary Production Deploy]
                                      │
                         ┌────────────┴────────────┐
                         │ 정상                    │ 실패
                         ▼                         ▼
                    완료 증적                 자동/수동 Rollback

※ GitLab·Runner·Repository는 이미지에 없고 저장소의 배포 ADR 근거다.
```

---

## 4. 컴포넌트별 상세 분석

### 4.1 SSO

- 통합 인증과 사용자·조직·역할 Claim 제공
- 관리자 UI, Control-M, Dashboard, 단말관리·배포 등 관리 접점의 인증 통합
- SSO 인증과 업무 권한은 분리하고 각 시스템에서 최소권한을 재검증
- 긴급 운영계정은 별도 보관·승인·만료·감사 적용
- SSO 장애 시 신규 로그인, 기존 세션, Break-glass 정책 명시

### 4.2 Control-M

| 책임 | 포함 | 제외 |
|---|---|---|
| 스케줄 | 시간·영업일·Calendar | 업무 데이터 변환 로직 |
| 선후행 | Job Dependency·조건 | Step 내부 트랜잭션 |
| 기동 | 배치 AP 실행과 Parameter 전달 | Item retry/skip 구현 |
| 관제 | 상태·SLA·알람·재기동 명령 | 업무 멱등성 보장 |

운영자는 재실행 전에 원 실행의 Commit 범위와 Checkpoint를 확인해야 하며 단순 재기동으로 중복처리를 유발하면 안 된다.

### 4.3 NH Cloud FWK Master Solution

- 프레임워크 표준 설정·Library·Service Catalog·배포 상태 관리로 해석한다.
- 중앙 설정 변경은 다수 서비스에 영향을 줄 수 있으므로 버전, 승인, 영향도, 단계 배포, Rollback을 갖춰야 한다.
- Master 장애가 업무 런타임 즉시 장애로 이어지는지, 업무가 마지막 정상 설정으로 계속 동작하는지 확인해야 한다.
- 저장소의 `tcf-om`과 개념 일부가 대응하지만 제품·기능 동일성은 미확인이다.

### 4.4 eCAMS

- 이미지에는 명칭만 있고 제품의 정확한 역할이 설명되지 않는다.
- 저장소 배포 ADR에서는 운영 변경승인·배포 통제 지점으로 다뤄진다.
- 승인자와 실행자를 분리하고 Ticket/변경번호, Artifact checksum, 대상, 시작·종료, 결과, Rollback 증적을 남겨야 한다.
- 실제로 배포 도구인지 변경관리 시스템인지, 또는 양쪽 기능인지 운영 매뉴얼로 확정해야 한다.

### 4.5 단말관리 WEB/WAS

| 계층 | 책임 | 주요 통제 |
|---|---|---|
| WEB | 관리자 화면·검색·등록·정책 UI | XSS/CSRF, 세션, 권한 |
| WAS | 단말·조직·상태·정책·이력 API | 유일 ID, 낙관 Lock, 감사 |
| 보완 DB | 단말·정책·버전·접속·변경이력 | 이미지 미표시, 백업·암호화 필요 |

단말의 식별키, 인증서, 소유 조직, 마지막 접속, 설치 버전, 정책 준수 상태를 분리 관리해야 한다.

### 4.6 단말배포 WEB/WAS

- WEB은 배포 Package 등록, 대상 선택, 일정, 승인, 진행상태, 중단·Rollback UI다.
- WAS는 Artifact 검증, 대상 Snapshot, 배포 Queue, 상태수집, 재시도, Rollback을 수행한다.
- 대규모 동시 배포는 단계·지역·조직별 Rate Limit과 Maintenance Window를 적용한다.
- 배포 Artifact는 서명·해시·버전·만료·호환성 정보가 있어야 한다.
- 오프라인 단말은 실패가 아닌 `대기/미접속` 상태로 구분한다.

### 4.7 출력물(RD) WAS

- 정형 보고서 Template과 Parameter를 이용해 문서를 렌더링한다.
- 사용자 권한과 조회 데이터 권한을 출력 시점에 다시 검증한다.
- 대용량 출력은 비동기 Job과 만료 URL을 사용하고 WAS Thread를 장시간 점유하지 않게 한다.
- 임시파일, Spool, Cache에 개인정보가 남지 않도록 암호화·파기한다.
- `UNO Dashboard`나 BI포탈과 달리 출력물 생성 Engine이라는 책임을 유지한다.

### 4.8 배치 AP

- Job/Step 처리, 트랜잭션, Checkpoint, 재시작, Retry/Skip, 실행 이력을 담당한다.
- Job Parameter와 Business Date를 실행 식별자에 포함하고 중복 기동을 방지한다.
- 온라인 DB와 자원을 공유할 경우 Resource Group·Pool·실행시간·Query 제한을 둔다.
- 현재 저장소의 `tcf-batch`는 Spring Scheduling 기반의 경량 상태 수집 성격이라는 기존 분석이 있으므로, 원본 목표 Spring Batch 구조와 동일하다고 보면 안 된다.

### 4.9 CDC

- 원천 DB 변경 로그를 읽고 Target에 순서대로 적용하는 준실시간 데이터 이동 계층이다.
- Capture position, Relay checkpoint, Apply position을 분리 관측한다.
- DDL 변경, PK 없는 Table, 대량 Transaction, 장기 장애, Log 보존 만료를 시험해야 한다.
- 저장소에는 CDC SLA가 `3초`와 `30초`로 혼재한다. 공식 Baseline에서 단일 기준을 확정해야 한다.

### 4.10 ETL

- 원천 추출, 정제·변환, 품질 검사, Target 적재, 대사를 담당한다.
- Full/Incremental, Watermark, Partition, Reject, Restart 지점을 명시한다.
- 재처리 시 Delete/Reload, Merge, Append 중 어느 정책인지 Dataset별로 정의한다.
- CDC가 신선도를 담당한다면 ETL은 대량 변환·집계·정합 보정을 담당하도록 중복을 피한다.

### 4.11 UNO Dashboard

- 시스템·배치·CDC·ETL·단말배포 상태를 통합 표시하는 운영 Dashboard로 해석한다.
- TCF 저장소의 OM Dashboard와 개념은 유사하지만 같은 제품이라고 확정할 수 없다.
- 경영지표용 BI Dashboard와 혼동하지 않는다.
- Dashboard 장애가 원 시스템 실행을 중단시키지 않아야 하며, 원본 Metric/Log가 별도 보존되어야 한다.

---

## 5. 시스템 그룹·업무 코드 대응

저장소의 통합 정의 자료는 시스템 그룹을 다음과 같이 분류한다.

| 영역 | 시스템/업무 코드 | 저장소 보완 정보 | 이미지 대응 |
|---|---|---|---|
| IT서비스 및 업무지원 | `IM` | Information Management | 전체 시스템 그룹 |
| 소스관리 | `SM` | GitLab | 이미지 직접 미표시 |
| 배포 | `DP` | GitLab Runner | eCAMS·단말배포와 구분 필요 |
| 단말관리 | `XM` | WebTopSuite 계열 증적 | 단말관리 WEB/WAS |
| 단말배포 | `XD` | WebTopSuite 계열 증적 | 단말배포 WEB/WAS |
| 배치 | `BJ` | NH Cloud Framework Batch | 배치 AP |
| CDC | `CD` | OGG 증적 | CDC |
| ETL | `DT` | DataStage 증적 | ETL |
| 출력물 | `RD` | Report Designer | 출력물(RD) WAS |

제품명은 저장소의 서버 분류 증적이며 첨부 이미지 자체의 제품·버전 확정값은 아니다.

---

## 6. 주요 연계 메커니즘

| Source | Target | 계약 | 핵심 통제 | 판정 |
|---|---|---|---|---|
| 사용자 | 각 관리 WEB | SSO Token/Session | MFA, 역할, 만료 | 해석 |
| Control-M | 배치 AP | Job ID·Parameter·Run ID | 중복방지, 상태반환 | 해석 |
| eCAMS | 배포 실행계 | Change ID·Artifact·Target | 승인, checksum, rollback | 저장소 근거 + 해석 |
| 단말관리 | 단말배포 | 대상 Snapshot·정책·버전 | 배포 중 대상 불변 | 해석 |
| 단말배포 | 단말 | Package·명령·상태 | 서명, 재시도, 속도제한 | 해석 |
| 업무/BI | RD WAS | Template·Parameter | 권한, 마스킹, 만료 | 해석 |
| 원천 DB | CDC | Redo/Change Log | 순서, checkpoint, lag | 저장소 근거 |
| 원천/RDW | ETL | Dataset·Watermark | 대사, 재처리, 품질 | 저장소 근거 |
| 전체 구성 | UNO Dashboard | Metric·Log·Status | 최신성, 권한, 독립성 | 해석 |

### 6.1 상태 모델 권장

```text
요청/작업 공통 상태
REGISTERED → APPROVED → READY → RUNNING
                           ├─→ SUCCEEDED
                           ├─→ FAILED → RETRYING → SUCCEEDED/FAILED
                           ├─→ STOPPING → STOPPED
                           └─→ ROLLING_BACK → ROLLED_BACK/ROLLBACK_FAILED

각 상태 전이는 Actor, Timestamp, Reason, Correlation ID, 이전/신규 값 기록
```

---

## 7. 운영·보안·가용성 원칙

### 7.1 공통 보안

- 관리 화면과 API는 SSO·MFA·역할기반 접근제어를 적용한다.
- 개발자, 승인자, 배포 실행자, 운영자, 감사자 역할을 분리한다.
- 배포·단말·배치·출력·데이터 이동 계정은 개별 Service Account와 최소권한을 사용한다.
- 비밀은 코드·Script·Dashboard에 저장하지 않고 Vault/KMS로 관리한다.
- 관리자 작업, 재실행, 강제종료, Rollback, 출력, 파일 다운로드는 감사 대상으로 둔다.

### 7.2 장애 격리

- SSO 장애가 기존 업무 세션을 즉시 종료시키지 않도록 정책을 정의한다.
- Control-M 장애 중 실행 중인 배치와 배치 AP의 처리 지속 여부를 명시한다.
- Master Solution/eCAMS 장애가 이미 기동한 업무 서비스에 미치는 영향을 최소화한다.
- CDC 정체가 원천 DB Log 보존 공간을 고갈시키지 않도록 Lag·Archive 사용량을 경보한다.
- ETL 폭주가 RDW/ADW 온라인 Query와 충돌하지 않도록 Queue·Pool·Resource Group을 분리한다.
- UNO Dashboard는 관측 실패가 실행 실패로 전파되지 않는 비침투 구조여야 한다.

### 7.3 백업·DR

| 구성 | 반드시 보호할 상태 | DR 검증 |
|---|---|---|
| SSO | 사용자 연계·Client·정책 | 인증·기존 세션 |
| Control-M | Calendar·Job 정의·실행 상태 | 중복 없는 재기동 |
| Master/eCAMS | 설정·승인·배포 이력 | 마지막 정상 버전 복원 |
| 단말관리/배포 | 단말·정책·대상·Package·상태 | Pilot 배포·Rollback |
| 배치 AP | JobRepository·Checkpoint | 실패 Step 재시작 |
| CDC | Capture/Apply Checkpoint | 무유실 재개·대사 |
| ETL | Watermark·Reject·실행이력 | 부분 재처리·대사 |
| RD | Template·Font·임시파일 정책 | 동일 출력 재현 |

---

## 8. 관측 지표와 경보

| 영역 | 핵심 지표 | 대표 경보 |
|---|---|---|
| SSO | 인증 성공률, p95, Token 발급 실패 | 오류율·지연 급증 |
| Control-M | SLA Miss, 대기·실패·장기 실행 Job | 선후행 정체 |
| Master/eCAMS | 배포 성공률, 승인 대기, Drift | 무승인 변경·Rollback 실패 |
| 단말관리 | 등록 수, 미접속, 정책 불일치 | 장기 미접속·인증서 만료 |
| 단말배포 | 성공/실패/대기, 전송률, Rollback | 실패율·오배포 임계치 |
| RD | 생성시간, Queue, 실패, 임시파일 | Queue 적체·용량 임계치 |
| 배치 | 성공률, 실행시간, Retry/Skip | SLA Miss·중복 실행 |
| CDC | Capture/Apply Lag, Trail/Log, 오류 | Lag·Log 보존 한계 |
| ETL | 처리건수, Throughput, Reject, 대사차 | 건수/합계 불일치 |
| UNO | 수집 최신성, 누락 Source | 관측 공백·알람 전달 실패 |

---

## 9. 현재 TCF 저장소와의 대응

| 이미지 영역 | 저장소 대응 | 판정 |
|---|---|---|
| NH Cloud FWK Master Solution | `tcf-om`, 서비스 Catalog·운영 통제 | 개념 정합, 제품 동일성 미확인 |
| 배치 AP | `tcf-batch`, 배치 분석 문서 | 현행 경량 Scheduler와 목표 Spring Batch 차이 존재 |
| UNO Dashboard | OM Admin Dashboard, Batch 상태 화면 | 개념 정합, 제품 동일성 미확인 |
| 출력물(RD) WAS | RD Download 연계 문서 | 역할 정합 |
| CDC/ETL | Data Flow/Transport 정책 | 역할·경계 정합 |
| eCAMS | ADR-DEP-001 배포 승인 경로 | 방향 정합, 실제 Pipeline 증적 부족 |

### 9.1 중요한 불일치와 열린 결정

1. CDC SLA가 `3초`와 `30초`로 혼재한다.
2. GitLab→Runner→Artifact Repository→eCAMS→운영 배포는 권고·ADR 근거가 있으나 실제 Pipeline 증적은 별도 확인이 필요하다.
3. 현행 `tcf-batch`와 목표 Spring Batch/Control-M 구조의 기능 범위가 다르다.
4. UNO Dashboard와 TCF OM Dashboard는 같은 제품으로 확정할 수 없다.
5. 이미지에 소스관리·배포관리 DB, 단말관리 DB, CDC 중계 DB가 직접 표시되지 않는다.

---

## 10. 설계·운영 검증 시나리오

1. SSO 역할별로 각 관리화면과 API 접근이 제한되는지 확인한다.
2. SSO 장애 시 기존 세션·신규 로그인·긴급계정 정책을 시험한다.
3. Control-M이 영업일·선후행·SLA·Parameter를 정확히 전달하는지 확인한다.
4. 동일 Run ID의 배치 중복 기동이 차단되는지 확인한다.
5. 실패 Step부터 재시작해 이미 Commit된 데이터가 중복되지 않는지 확인한다.
6. Master 설정 변경이 승인·버전·단계 배포·Rollback 이력으로 남는지 확인한다.
7. eCAMS 승인 Artifact와 실제 배포 Artifact checksum이 일치하는지 확인한다.
8. 승인자와 배포 실행자가 분리되는지 확인한다.
9. 단말 등록·조직 변경·폐기 이력이 감사로그로 재현되는지 확인한다.
10. 단말 대상군 Snapshot이 배포 중 임의 변경되지 않는지 확인한다.
11. Pilot 실패 시 전체 배포가 중단되고 정상 버전으로 Rollback 되는지 확인한다.
12. 오프라인 단말이 재접속할 때 만료·대체 Package 정책이 적용되는지 확인한다.
13. RD 출력 시 사용자·데이터 권한·마스킹·워터마크가 적용되는지 확인한다.
14. 대용량 출력이 비동기로 처리되고 임시파일이 정책대로 파기되는지 확인한다.
15. CDC 장기 중단 후 Checkpoint부터 무유실·무중복 재개되는지 대사한다.
16. CDC DDL 변경과 PK 없는 Table의 처리정책을 시험한다.
17. ETL 부분 실패 후 실패 Partition만 재처리되고 건수·합계가 일치하는지 확인한다.
18. CDC와 ETL이 같은 Target 데이터를 중복 갱신하지 않는지 확인한다.
19. UNO Dashboard 수집 장애가 원 업무·배치·CDC/ETL 실행에 영향을 주지 않는지 확인한다.
20. DR 전환 후 SSO·단말·배치·CDC·ETL·RD의 상태와 Checkpoint를 순서대로 검증한다.

---

## 11. 사실·해석·미확인 분리

### 11.1 이미지에서 확인된 사실

- IT서비스 및 업무지원 시스템은 서비스 제공 Zone에 배치된다.
- `SSO`, `Control-M`, `NH Cloud FWK Master Solution`, `eCAMS`가 표시된다.
- `단말관리 WEB/WAS`, `단말배포 WEB/WAS`가 표시된다.
- `출력물(RD) WAS`, `배치 AP`, `CDC`, `ETL`, `UNO Dashboard`가 표시된다.
- 채널·채널통합·대내통합 영역에는 세부 구성요소가 표시되지 않았다.

### 11.2 저장소 증적으로 보완된 사실

- 시스템 그룹 코드는 `IM`이다.
- `SM`, `DP`, `XM`, `XD`, `BJ`, `CD`, `DT`, `RD` 업무 분류 자료가 있다.
- CDC는 OGG, ETL은 DataStage, 출력은 Report Designer로 분류한 서버 인벤토리 증적이 있다.
- 배포 ADR은 GitLab→Runner→Artifact Repository→eCAMS→Production 방향을 제시한다.

### 11.3 아키텍처 해석

- 상단 4개 구성은 제어면, 중단 실행 컴포넌트는 실행면, UNO는 관측면이다.
- 단말관리와 배포는 대상 Snapshot을 계약으로 연계한다.
- Control-M과 배치 AP는 외부 스케줄과 내부 실행 책임을 분리한다.
- CDC는 준실시간 변경 전달, ETL은 대량 변환·적재를 담당한다.

### 11.4 미확인 사항

- 물리 서버 수, Hostname, IP/Port, Cluster와 Load Balancer
- 각 제품의 정확한 버전·라이선스·HA 구성
- eCAMS의 실제 기능범위와 연계 API
- 단말관리/배포 DB, CDC 중계 DB, 배치 Repository의 물리 구성
- 공식 CDC SLA와 Dataset별 RPO/RTO
- UNO Dashboard의 수집 Agent·저장소·보존기간

---

## 12. 최종 평가

이 장표는 인증, 작업 스케줄, 프레임워크 관리, 변경관리, 단말 관리·배포, 출력, 배치, CDC, ETL, 운영 관측을 하나의 `IT서비스 및 업무지원` 소유 경계로 묶는다. 각 업무 시스템이 이 기능을 중복 구축하지 않고 공통 서비스를 이용하게 한다는 점이 핵심이다.

다만 공통화는 결합도와 장애 영향 범위를 키울 수 있다. 따라서 제어면·실행면·관측면을 분리하고, 기능별 계정·자원·상태 저장소·재시작·Rollback·감사 계약을 명확히 해야 한다. 특히 단말 오배포, 배치 중복, CDC 유실, ETL 중복 적재, 출력물 개인정보 노출, 중앙 관리도구 장애가 주요 통제 대상이다.

---

## 13. 관련 근거 문서

- [운영환경 구축 대상 시스템 및 주요 구성요소 분석](./운영환경_구축_대상_시스템_및_주요_구성요소_분석.md)
- [배치 처리 프레임워크 구조 및 컴포넌트 분석](./배치_처리_프레임워크_구조_및_컴포넌트_분석.md)
- [운영환경 데이터플랫폼 시스템 구성 분석](./운영환경_데이터플랫폼_시스템_구성_분석.md)
- [운영환경 BI포탈 시스템 구성 분석](./운영환경_BI포탈_시스템_구성_분석.md)
- [Zone 구성 기준 분석](./차세대_정보계_시스템_ZONE_구성_기준_분석.md)
- [Data Flow Policy](<../../2026-08-17-TCF 아키텍처 수행방법론/03-LOGICAL/DATA-FLOW-POLICY.md>)
- [Data Transport Standard](<../../2026-08-17-TCF 아키텍처 수행방법론/05-MECHANISM/DATA-TRANSPORT-STANDARD.md>)
- [배포 ADR-DEP-001](<../../2026-08-17-TCF 아키텍처 수행방법론/00-산출물정리/98BH-adr-sheets/ADR-DEP-001.md>)
- [원본 이미지 분석 증적 85910/46](<../../../pdmg-architecture-methodology/2026-08-17-TCF 아키텍처 수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85910/46_운영환경_IT서비스_및_업무지원_시스템_구성.md>)
- [중복 원본 이미지 분석 증적 85910/47](<../../../pdmg-architecture-methodology/2026-08-17-TCF 아키텍처 수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85910/47_운영환경_IT서비스_및_업무지원_시스템_구성.md>)
- [시스템 그룹·업무코드 통합 정의](<../../../pdmg-architecture-methodology/pdmg-02-architecture-documenting/2026-08-19-아키텍처정의서-최초추출자료/NSIGHT_어플리케이션분류_시스템그룹업무_데이터주제영역_통합정의서.md>)
