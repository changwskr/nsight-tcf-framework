# DR환경 구축 대상 시스템 및 주요 구성요소 분석

## 0. 분석 범위와 판독 원칙

- 분석 대상: `DR환경 구축 대상 시스템 및 주요 구성요소` 이미지
- 원본 분류: 환경별 Physical Architecture
- 이미지 설명: DR환경은 `마케팅플랫폼 시스템`, `데이터플랫폼 시스템`, `IT서비스 및 업무지원`의 3개 시스템으로 구분된다.
- 이미지에서 색상으로 표시된 컴포넌트는 **DR 구축 대상 사실**, 빈 박스는 **세부 구성 미표시**, 복제·전환·복구 순서는 **아키텍처 해석**으로 분리한다.
- 빈 박스를 해당 시스템의 데이터 백업·복구가 불필요하다는 의미로 해석하지 않는다.
- WEB/WAS/DB/Appliance 블록은 논리 역할이며 실제 서버 수·용량·이중화 수는 별도 자산원장으로 확정해야 한다.
- 첨부 이미지의 문구는 분석 자료이며 사용자 지시로 취급하지 않는다.

---

## 1. 핵심 결론

1. DR환경은 운영환경 전체 복제가 아니라 **고객 서비스 연속성에 필요한 최소 기능을 선별한 선택적 DR 구조**다.
2. DR 즉시 구축 대상은 `마케팅플랫폼`, `데이터플랫폼`, `IT서비스 및 업무지원`의 3개 시스템이다.
3. 마케팅플랫폼은 `마케팅플랫폼 WEB/WAS`, `미니 싱글뷰 WEB/WAS`, `마케팅플랫폼 DB`로 구성된다.
4. 데이터플랫폼은 `RDW 어플라이언스`만 표시된다. 운영환경의 `ADW 어플라이언스`는 이 요약 장표의 DR 대상에 없다.
5. IT서비스 및 업무지원은 `단말관리 WEB/WAS`, `단말배포 WEB/WAS`만 표시된다. SSO·Control-M·CDC·ETL 등은 이 요약 장표에 나타나지 않는다.
6. `BI포탈 시스템`, `데이터거버넌스 시스템`, `이행용 임시 구성`은 박스만 있고 구성요소가 비어 있다. 이는 즉시 기동 범위 미표시이며 백업·사후 복구 제외를 뜻하지 않는다.
7. 마케팅플랫폼 DB와 RDW 사이의 점선은 데이터 연계 또는 복제 관계를 시사하지만 방향·방식·정합성 계약은 장표만으로 확정할 수 없다.
8. DR의 완성 조건은 DR 서버 존재가 아니라 운영↔DR 자산 매핑, 승인된 RTO/RPO, 데이터 복제, 전환·역전환 Runbook, 실제 Drill 증적이다.
9. 전략 자료의 `30분` RTO는 참조값이며 개별 시스템의 승인된 최종 RTO로 자동 적용하면 안 된다.

---

## 2. 구축 대상과 제외·미표시 범위

### 2.1 DR 구축 대상 3개 시스템

| 우선순위 | 시스템 | 이미지 구성요소 | DR 목적 |
|---:|---|---|---|
| 1 | 마케팅플랫폼 시스템 | 마케팅플랫폼 WEB, 마케팅플랫폼 WAS, 미니 싱글뷰 WEB, 미니 싱글뷰 WAS, 마케팅플랫폼 DB | 고객·내부 사용자 핵심 서비스 제공 |
| 2 | 데이터플랫폼 시스템 | RDW 어플라이언스 | 운영 조회·마케팅 서비스 데이터 제공 |
| 3 | IT서비스 및 업무지원 시스템 | 단말관리 WEB, 단말관리 WAS, 단말배포 WEB, 단말배포 WAS | 정보계 단말 운영·배포 지속 |

### 2.2 박스는 있으나 세부 구성이 없는 시스템

| 시스템 | 장표 상태 | 안전한 해석 | 추가 결정 |
|---|---|---|---|
| BI포탈 시스템 | 빈 박스 | DR 즉시 기동 구성 미표시 | 백업·후순위 복구·대체조회 |
| 데이터거버넌스 시스템 | 빈 박스 | DR 즉시 기동 구성 미표시 | 메타·품질·Lineage DB 백업과 복구 |
| 이행용 임시 구성 | 점선 빈 박스 | DR 상시 구성 대상이 아닌 임시자원 | 이행 종료 후 폐기·잔존 데이터 파기 |

### 2.3 운영환경 대비 축소 항목

| 운영 구성 | DR 요약 장표 | 의미 |
|---|---|---|
| 마케팅 실시간/행동 처리 AP | 미표시 | WEB/WAS 내부 포함 여부 또는 후순위 여부 미확인 |
| ADW 어플라이언스 | 미표시 | 분석계 즉시 복구 우선순위에서 제외된 것으로 해석 가능 |
| BI포탈·신용실적·Self-BI·OLAP | 미표시 | BI 서비스 후순위 또는 대체 절차 필요 |
| 데이터거버넌스 WAS/DB | 미표시 | 메타·품질 서비스 후순위이나 데이터 보호 필요 |
| SSO·Control-M·FWK Master·eCAMS | 미표시 | DR 마케팅/단말이 실제 기동하려면 의존성 보완 필요 |
| 배치 AP·CDC·ETL·RD·UNO | 미표시 | 데이터 동기화·배치·출력·관측의 DR 전략 별도 필요 |

---

## 3. 전체 DR 아키텍처 텍스트 그림

### 3.1 원본 장표 재현

```text
┌─ 1. 마케팅플랫폼 시스템 ─────────┐  ┌─ 2. 데이터플랫폼 시스템 ───────┐
│ [마케팅플랫폼 WEB] [마케팅플랫폼 WAS] │  │ [RDW 어플라이언스]               │
│ [미니 싱글뷰 WEB]  [미니 싱글뷰 WAS]  │  │                                  │
│                                      │  │                                  │
│                     [마케팅플랫폼 DB] ├··┼······························· │
└──────────────────────────────────────┘  └──────────────────────────────────┘

┌─ BI포탈 시스템 ───────────────────┐  ┌─ 데이터거버넌스 시스템 ─────────┐
│ 세부 구성요소 미표시               │  │ 세부 구성요소 미표시              │
└────────────────────────────────────┘  └───────────────────────────────────┘

┌─ 3. IT서비스 및 업무지원 시스템 ──┐  ┌┄ 이행용 임시 구성 ┄┄┄┄┄┄┄┄┄┄┄┐
│ [단말관리 WEB] [단말배포 WEB]       │  ┊ 세부 구성요소 미표시              ┊
│ [단말관리 WAS] [단말배포 WAS]       │  ┊                                  ┊
└────────────────────────────────────┘  └┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┘
```

### 3.2 운영센터와 DR센터의 최소 대응

```text
┌──────────────── 운영센터 ────────────────┐
│ Marketing WEB/WAS + Mini SingleView      │
│ Marketing DB                             │
│ RDW Appliance                            │
│ Terminal Mgmt/Deploy WEB/WAS             │
└──────────────┬────────────────────────────┘
               │
               ├─ AP Artifact/설정 동기화 ───────────────┐
               ├─ Marketing DB 복제 ────────────────────┤
               ├─ RDW 데이터 복제 ──────────────────────┤
               └─ 단말 정책/배포 상태 복제 ─────────────┤
                                                        ▼
┌──────────────── DR센터 ───────────────────┐
│ Marketing WEB/WAS + Mini SingleView      │
│ Marketing DB                             │
│ RDW Appliance                            │
│ Terminal Mgmt/Deploy WEB/WAS             │
└───────────────────────────────────────────┘

필수 보완: DNS/GSLB/L4, SSL, SSO, Route, Secret, 배포 Artifact, 관측·알람
```

### 3.3 DR 복구 의존성 순서

```text
[0. 재해 선언·변경 동결]
             │
             ▼
[1. Network/DNS/GSLB/L4/Firewall/SSL]
             │
             ▼
[2. DB 정합성 확인]
    ├─ Marketing DB promote
    ├─ RDW promote/Read-Write 역할 확정
    └─ Split-Brain 방지 / Primary fencing
             │
             ▼
[3. 공통 의존성]
    SSO · Secret · Route · Framework 설정 · 관측
             │
             ▼
[4. Application 기동]
    Marketing WAS → WEB → Mini SingleView WAS/WEB
             │
             ▼
[5. 단말관리/배포 기동]
             │
             ▼
[6. Health/Deep/Smoke/업무 검증]
             │
             ▼
[7. Traffic 전환·강화 모니터링·복구 선언]
```

### 3.4 데이터 복제와 정합성 경계

```text
운영 Marketing DB ──DB Replication──> DR Marketing DB
       │                                    │
       │ 업무/고객 데이터                  │ DR 승격 시 Writer
       └───────────────┐                    │
                       ▼                    ▼
운영 RDW ───────Data Replication─────> DR RDW

전환 직전 확인:
  Last Applied Position / Replication Lag / Archive Gap / Row·Sum 대사

전환 시 통제:
  운영 Writer 차단 → 마지막 Log 적용 → DR Promote → Application 연결 변경

금지:
  운영 DB와 DR DB의 동시 Writer 활성화(합의된 다중 Writer 설계가 없는 경우)
```

### 3.5 Failover 상태 전이

```text
NORMAL
  │ 센터 장애 탐지
  ▼
INCIDENT_DECLARED
  │ 운영총괄 승인·변경 동결
  ▼
SOURCE_FENCED
  │ 복제 최종 위치·데이터 손실량 확인
  ▼
DR_DATA_PROMOTED
  │ 서비스 의존성·AP 기동
  ▼
DR_SMOKE_VALIDATED
  │ Traffic 전환
  ▼
DR_ACTIVE

각 전이: 승인자, 시작/종료시각, 증적, 실패 시 되돌림 조건 기록
```

### 3.6 Failback 흐름

```text
[원 운영센터 복구]
       │ 인프라·DB·AP 검증
       ▼
[DR → 원 운영센터 역방향 데이터 동기화]
       │ Lag=0 또는 승인된 손실 범위
       ▼
[원 운영센터 Read-only Smoke]
       │
       ▼
[변경 동결·DR Writer 차단]
       │ 최종 Delta 적용
       ▼
[원 운영센터 Promote + Traffic 원복]
       │
       ▼
[DR Standby 재구성·복제 검증·사후 RCA]
```

### 3.7 후순위 시스템 복구 계층

```text
Tier 1 — 즉시 고객/단말 서비스
  Marketing WEB/WAS, Mini SingleView, Marketing DB, RDW,
  Terminal Mgmt/Deploy

Tier 2 — 서비스 안정화 후
  SSO/운영도구 보완, CDC/ETL/Batch, 출력, 관측

Tier 3 — 분석·통제 서비스
  BI Portal/Self-BI/OLAP, Data Governance

Tier 4 — 복구하지 않거나 재구성
  Migration Temporary Resources

※ Tier는 분석 권고이며 공식 RTO/RPO 등급은 업무·운영·데이터 오너 승인 필요.
```

---

## 4. DR 대상 시스템별 상세 분석

### 4.1 마케팅플랫폼 WEB/WAS

| 계층 | DR 책임 | 반드시 동기화할 항목 | 주요 위험 |
|---|---|---|---|
| WEB | 정적·Reverse Proxy·SSL·Routing | Artifact, 인증서, 설정 | DNS/L4 미전환, 인증서 만료 |
| WAS | 업무 API·세션·DB 연결 | WAR, profile, Secret, Route | 설정 Drift, 세션 손실 |
| 미니 싱글뷰 WEB/WAS | 핵심 고객정보 조회 | Application·DB View/Query 계약 | RDW 신선도·권한 |

- AP는 무상태 또는 세션 외부화를 통해 DR 기동 가능해야 한다.
- 운영과 DR Artifact checksum, Git Tag, 설정 버전을 일치시킨다.
- DB 접속정보만 바꾸는 수동 절차보다 환경별 Secret/Config와 자동화된 검증이 안전하다.
- DR 용량이 평시 운영량 전부인지 축소 용량인지, 최대 허용 부하와 기능 제한을 명시한다.

### 4.2 마케팅플랫폼 DB

- 업무 데이터의 일관성과 Writer 단일성을 최우선한다.
- 복제 방식, 동기/비동기, Log 보존, 암호화, Promote 권한을 명시한다.
- DR 전환 시 운영 DB를 fencing하지 않으면 Split-Brain 위험이 있다.
- RPO 측정은 마지막 성공 백업 시각이 아니라 마지막 적용 Transaction 위치와 실제 대사로 수행한다.
- Failback에는 DR 운영 중 생성된 데이터를 원 센터에 되돌리는 계획이 필요하다.

### 4.3 RDW 어플라이언스

- DR 장표의 유일한 데이터플랫폼 구성요소로, 핵심 조회·마케팅 서비스의 데이터 기반이다.
- Appliance 자체 HA와 센터 간 DR을 구분한다.
- Schema·Table·Partition·통계·권한·DB Link·Character Set까지 복제 범위에 포함한다.
- 미니 싱글뷰가 요구하는 데이터 신선도와 DR 전환 시점의 Lag 허용치를 연결해야 한다.
- 운영환경의 ADW가 미표시이므로 분석·대량 BI 기능 제한과 사용자 공지가 필요하다.

### 4.4 단말관리 WEB/WAS

- 단말 ID, 조직, 인증서, 정책, 설치 버전, 접속 상태, 변경 이력을 복구해야 한다.
- 단말관리 DB는 이미지에 없지만 서비스 기동을 위해 필요한 상태 저장소이므로 DR 자산원장에서 확인해야 한다.
- 센터 전환 후 단말 Agent가 새 Endpoint를 찾는 방식(DNS, GSLB, 재접속)을 검증한다.
- 인증서·Token·시간 동기화가 어긋나면 대량 접속 실패가 발생할 수 있다.

### 4.5 단말배포 WEB/WAS

- Package Artifact, 서명·해시, 대상 Snapshot, 배포 상태, Rollback Package를 복구한다.
- DR 전환 중 신규 대규모 배포는 원칙적으로 동결하고 서비스 안정화 후 재개한다.
- 운영센터에서 진행 중이던 배포의 상태를 승계할지 중단·재등록할지 정한다.
- DR 용량이 제한적이면 단계별 Rate Limit으로 단말 재접속 폭주를 제어한다.

---

## 5. 빈 영역의 처리 원칙

### 5.1 BI포탈

- 즉시 기동 대상이 아니어도 BI Template, Dataset, 권한, Dashboard 정의, 사용자 설정을 백업한다.
- DR 기간에는 RDW 기반 핵심 조회를 마케팅/미니 싱글뷰로 대체할 수 있는지 결정한다.
- ADW 미복구 시 Self-BI·OLAP·대량 분석 기능 제한을 공지한다.

### 5.2 데이터거버넌스

- 비즈메타·품질규칙·Lineage·승인·감사이력을 백업한다.
- 서비스가 후순위라도 메타 변경을 동결하고 복구 후 운영 중 변경분과 충돌하지 않도록 한다.
- DR 기간 데이터 변경과 신규 ETL/Report의 Lineage 공백을 사후 보정한다.

### 5.3 이행용 임시 구성

- 상시 DR 복제 대상이 아니라면 이행 종료 조건과 자원 폐기일을 명시한다.
- 진행 중인 이행 기간에 재해가 발생할 가능성이 있다면 Checkpoint, 원본 보존, 재개 Runbook을 별도로 둔다.
- 임시 데이터와 계정이 DR 환경에 영구 잔존하지 않도록 파기 증적을 남긴다.

---

## 6. RTO·RPO와 서비스 등급

### 6.1 용어

| 지표 | 질문 | 측정 기준 |
|---|---|---|
| RTO | 재해 선언 후 언제 서비스가 복구되는가? | 선언 시각→업무 복구 승인 시각 |
| RPO | 어느 시점까지 데이터 손실을 허용하는가? | 운영 마지막 Commit→DR 마지막 적용 Commit |
| MTPD | 업무가 견딜 수 있는 최대 중단은 얼마인가? | 업무 영향 분석 |
| WRT | 기술 복구 후 업무 정상화에 얼마나 더 필요한가? | 대사·사용자 검증·재처리 |

### 6.2 시스템별 승인 항목

| 시스템 | RTO 승인 주체 | RPO 승인 주체 | 주요 측정 증적 |
|---|---|---|---|
| 마케팅 WEB/WAS | 업무·운영 오너 | 해당 없음/세션정책 | Traffic·Smoke 시각 |
| 마케팅 DB | 업무·운영 오너 | 데이터 오너 | Replication position·대사 |
| RDW | 업무·데이터 오너 | 데이터 오너 | Apply position·신선도 |
| 단말관리/배포 | 운영·단말 오너 | 운영 데이터 오너 | 접속·정책·배포 상태 대사 |
| BI/거버넌스 후순위 | 각 서비스 오너 | 데이터 오너 | 백업 복원·기능 검증 |

`30분 RTO`는 전략 수준 참조다. 서비스 등급별 승인값과 실제 Drill 실측값이 있어야 PASS로 판정한다.

---

## 7. DR 전환 Runbook

### 7.1 Failover

```text
1. 장애 탐지와 Center Failure 판정
2. Incident Commander 지정, 재해 선언, 변경·배포·배치 동결
3. 운영센터 Writer와 Traffic fencing
4. Marketing DB/RDW 복제 위치·손실량·정합성 확인
5. DR DB/RDW Promote
6. Network·DNS/GSLB/L4·Firewall·SSL·Secret 점검
7. 공통 의존성 → WAS → WEB → 단말관리/배포 순서 기동
8. Liveness → Readiness → Deep Check → 대표 거래 Smoke
9. 업무 담당자 검증·승인
10. Traffic 전환, 오류율·지연·데이터 신선도 강화 관측
11. RTO/RPO 실측, 복구 선언, 사용자 공지
```

### 7.2 Failback

```text
1. 원 운영센터 복구와 독립 기술 검증
2. DR→운영 역동기화, Schema·Config·Artifact Drift 제거
3. 원 운영센터 Read-only Smoke
4. 전환 Window 승인, 변경 동결
5. DR Writer fencing, 최종 Delta 적용·대사
6. 원 운영센터 Promote와 Traffic 원복
7. 업무 검증·강화 관측
8. DR Standby 재구성, 정방향 복제 재개
9. RCA·Runbook·RTO/RPO 실측 갱신
```

### 7.3 중단·되돌림 조건

- 복제 Gap 또는 데이터 손실량이 승인된 RPO를 초과
- DB Split-Brain 가능성을 제거하지 못함
- 핵심 Smoke 거래 실패 또는 데이터 대사 불일치
- SSO·Route·Secret 등 공통 의존성 불완전
- 오류율·지연이 DR 허용 임계치 초과
- 단말 재접속·정책 배포가 통제되지 않음

---

## 8. 보안·관측·운영 통제

### 8.1 보안

- DR 계정·Secret·인증서·암호화키의 동기화와 만료를 관리한다.
- 평시 DR 접근은 차단·최소화하고 전환 시 승인된 역할만 활성화한다.
- 방화벽 규칙과 DB 권한이 운영보다 과도하게 열리지 않도록 대조한다.
- 전환·Promote·DNS 변경·긴급계정 사용을 감사로그로 남긴다.

### 8.2 관측 지표

| 단계 | 지표 | 경보/판정 |
|---|---|---|
| 평시 | Replication Lag, Archive Gap, DR Health | RPO 위험 사전 경보 |
| 전환 | 단계별 소요시간, 실패, 승인 대기 | RTO Budget 초과 |
| 데이터 | 마지막 Commit/Apply, 건수·합계·해시 | 정합성 불일치 |
| AP | Health, p95/p99, 5xx, Pool, Thread | 서비스 불안정 |
| 단말 | 재접속률, 인증실패, 정책 불일치 | 접속 폭주·시간 오류 |
| 전환 후 | Error Budget, 데이터 신선도, 미처리 Queue | 복구 완료 판정 |

### 8.3 Configuration Drift

- 운영과 DR의 Artifact, Git Tag, 환경 설정 Key 목록, Route, DB Schema, 인증서, 방화벽을 정기 비교한다.
- 비밀값 자체가 아니라 Secret Version/Checksum을 비교한다.
- Drift가 존재하면 DR Drill 전에 해결하며, 승인 없는 수동 수정은 금지한다.

---

## 9. 현재 TCF 저장소와의 대응

| 관점 | 저장소 근거 | 판정 |
|---|---|---|
| DR 원칙 | `04-PHYSICAL/HA-DR.md` | AP 확장, DB 정합성 우선, L4/GSLB 전환 정합 |
| TCF 복구 | `zdocs-1/architecture/45-disaster-recovery.md` | WAR·설정·DB·Gateway·세션 통합 복구 정합 |
| 서비스 등급 | `ADR-DR-002` | RTO/RPO는 서비스별 승인 필요 |
| Center Drill | `RUN-CF` Runbook | Failover/Failback 실측 증적 필요 |
| Zone 기준 | 운영/개발/DR Zone 분석 | DR은 3개 핵심 시스템 선택 구성 |

### 9.1 저장소가 명시하는 열린 항목

- 승인된 시스템별 RTO/RPO가 부족하다.
- 운영↔DR 전수 자산 매핑과 정확한 Target이 필요하다.
- GSLB/L4/DB Failover와 Failback의 운영 증적이 필요하다.
- 세션 Failover/재로그인 정책은 별도 승인 대상이다.
- DR 노드 존재와 DR 용량·RTO/RPO 충족은 별개로 판정해야 한다.

---

## 10. 검증 시나리오

1. 운영↔DR의 WEB/WAS/DB/RDW/단말 서버가 자산원장에서 1:1 또는 N:1로 매핑되는지 확인한다.
2. 운영과 DR의 Artifact checksum·Git Tag·설정 버전을 대조한다.
3. Marketing DB와 RDW 복제 Lag가 승인된 RPO 안에 유지되는지 확인한다.
4. 운영 Writer fencing 없이 DR Promote가 불가능한지 확인한다.
5. 전체 센터 장애 시 DNS/GSLB/L4 전환이 승인 절차대로 수행되는지 확인한다.
6. DR에서 SSO·Route·Secret·인증서가 정상 동작하는지 확인한다.
7. Marketing WAS→WEB→Mini SingleView 기동 순서와 Health를 검증한다.
8. 대표 마케팅 조회·변경 거래의 결과가 운영 기준과 일치하는지 확인한다.
9. RDW 데이터 신선도가 승인된 시점과 일치하는지 확인한다.
10. ADW 미구축 상태에서 제한되는 BI·분석 기능과 사용자 공지가 준비되었는지 확인한다.
11. 단말 Agent가 DR Endpoint로 재접속하고 인증되는지 확인한다.
12. 단말 정책·버전·대상군·배포 상태가 운영과 대사되는지 확인한다.
13. DR 전환 중 대규모 단말배포가 동결되는지 확인한다.
14. BI포탈·데이터거버넌스의 데이터 백업이 실제 복원 가능한지 확인한다.
15. DR 서비스 용량에서 설계 Peak 또는 합의된 축소 Peak를 처리하는지 부하 시험한다.
16. Failover RTO가 재해 선언부터 업무 승인까지 실측되는지 확인한다.
17. RPO가 마지막 Commit과 DR 마지막 Apply 위치로 실측되는지 확인한다.
18. Failback 시 DR 기간 변경분이 원 운영센터에 무유실 반영되는지 확인한다.
19. Failback 후 DR이 다시 Standby로 구성되고 복제가 정상화되는지 확인한다.
20. Drill 결과·실패·승인·RCA가 Runbook과 Architecture Baseline에 반영되는지 확인한다.

---

## 11. 사실·해석·미확인 분리

### 11.1 이미지에서 확인된 사실

- DR환경은 마케팅플랫폼, 데이터플랫폼, IT서비스 및 업무지원의 3개 시스템으로 구분된다.
- 마케팅플랫폼 WEB/WAS, 미니 싱글뷰 WEB/WAS, 마케팅플랫폼 DB가 표시된다.
- 데이터플랫폼에는 RDW 어플라이언스가 표시된다.
- IT서비스 및 업무지원에는 단말관리 WEB/WAS, 단말배포 WEB/WAS가 표시된다.
- BI포탈, 데이터거버넌스, 이행용 임시 구성은 세부 컴포넌트가 표시되지 않았다.
- 마케팅플랫폼 DB와 RDW 사이에 점선 관계가 표시된다.

### 11.2 저장소 증적으로 보완된 사실

- DR은 운영 전체가 아닌 선택적 3개 시스템 구성으로 정리되어 있다.
- AP Level HA와 DB 정합성 우선의 현실적 DR 방향이 제시되어 있다.
- RTO/RPO, 전수 자산 매핑, Center Failover/Failback 증적은 승인·검증이 필요한 열린 항목이다.
- 전략 수준 30분 RTO를 개별 시스템 승인값으로 자동 승격하지 않는 ADR이 있다.

### 11.3 아키텍처 해석

- RDW는 마케팅·미니 싱글뷰 핵심 조회의 DR 데이터 기반이다.
- 빈 박스의 시스템은 즉시 기동 우선순위에서 제외되었지만 백업·사후 복구가 필요하다.
- 데이터 복구 후 공통 의존성, WAS, WEB, 단말 순으로 기동하는 것이 안전하다.
- Failback은 Failover와 별개의 역방향 동기화·전환 절차다.

### 11.4 미확인 사항

- 서버 Hostname/IP/Port, 운영↔DR Pair, CPU·Memory·Storage 용량
- Marketing DB·RDW의 복제 제품·방식·Topology
- 승인된 시스템별 RTO/RPO와 DR 용량률
- SSO·DNS/GSLB/L4·Firewall·Secret·관측 시스템의 DR 구성
- 세션 유지 또는 재로그인 정책
- 점선 DB→RDW 관계의 실제 방향과 계약

---

## 12. 최종 평가

이 DR 장표는 고객 서비스와 정보계 단말 운영에 필요한 최소 구성만 선별한다. 마케팅 WEB/WAS·미니 싱글뷰·마케팅 DB, RDW, 단말관리·배포가 즉시 구축 대상으로 보이며 BI포탈·데이터거버넌스·이행 임시자원은 후순위 또는 미구성 영역이다.

그러나 박스 배치만으로는 DR이 완성되지 않는다. 데이터 복제와 Writer fencing, 공통 의존성, 운영↔DR Exact Mapping, 용량, 서비스별 RTO/RPO, 단계별 Failover/Failback, 실제 업무 Smoke와 Drill 증적을 함께 확정해야 한다. 특히 빈 영역도 데이터 보호·후순위 복구 정책이 있어야 전체 업무 연속성이 닫힌다.

---

## 13. 관련 근거 문서

- [차세대 정보계 시스템 Zone 구성 기준 분석](./차세대_정보계_시스템_ZONE_구성_기준_분석.md)
- [운영환경 구축 대상 시스템 및 주요 구성요소 분석](./운영환경_구축_대상_시스템_및_주요_구성요소_분석.md)
- [운영환경 마케팅플랫폼 시스템 구성 분석](./운영환경_마케팅플랫폼_시스템_구성_분석.md)
- [운영환경 데이터플랫폼 시스템 구성 분석](./운영환경_데이터플랫폼_시스템_구성_분석.md)
- [운영환경 IT서비스 및 업무지원 시스템 구성 분석](./운영환경_IT서비스_및_업무지원_시스템_구성_분석.md)
- [HA/DR Architecture](<../../2026-08-17-TCF 아키텍처 수행방법론/04-PHYSICAL/HA-DR.md>)
- [TCF 장애복구·DR 설계](../../../zdocs-1/architecture/45-disaster-recovery.md)
- [ADR-DR-002 — Service RTO/RPO Classes](<../../2026-08-17-TCF 아키텍처 수행방법론/00-산출물정리/98BH-adr-sheets/ADR-DR-002.md>)
- [Center Failover/Failback Runbook](<../../2026-08-17-TCF 아키텍처 수행방법론/00-산출물정리/98AW-RUN-CF-RUNBOOK.md>)
- [원본 이미지 분석 증적 85910/49](<../../../pdmg-architecture-methodology/2026-08-17-TCF 아키텍처 수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85910/49_DR환경_구축_대상_시스템_및_주요_구성요소.md>)
