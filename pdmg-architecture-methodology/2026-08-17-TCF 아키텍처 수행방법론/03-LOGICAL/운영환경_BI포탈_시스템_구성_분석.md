# 운영환경 BI포탈 시스템 구성 분석

## 0. 분석 범위와 판독 원칙

- 분석 대상: `운영환경 BI포탈 시스템 구성` 이미지
- 원본 분류: 환경별 Physical Architecture
- 이미지 설명: BI포탈은 `서비스 제공 Zone`에 배치되며 채널·채널통합·대내통합 Zone은 업무 연계 대상을 표시한다.
- 이미지의 구성요소는 **사실**, 화살표가 없는 호출·데이터 방향은 **해석**, 서버·제품·계정·테이블은 **미확인**으로 분리한다.
- WEB/WAS/AP/DB는 논리 역할이며 각 블록이 단일 서버를 의미하지 않는다.

---

## 1. 핵심 결론

1. BI포탈 시스템은 `BI포탈`, `신용실적`, `Self-BI`, `OLAP`의 4개 기능 영역을 WEB·WAS·AP 실행단위로 분리한 분석 플랫폼이다.
2. BI포탈/신용실적은 제공·업무 접점, Self-BI는 사용자 자율 분석, OLAP은 다차원·대량 분석으로 책임이 다르다.
3. `BI포탈(대시보드-경영) DB(RDW)`와 `마케팅플랫폼 DB(ADW)`가 별도 표시되며, RDW는 신선도가 중요한 대시보드/경영 조회, ADW는 분석·집계·대량 Query에 우선 사용하는 구조로 해석한다.
4. Self-BI와 OLAP은 부하 폭증 가능성이 높으므로 BI포탈/신용실적과 자원그룹·커넥션풀·쿼리 큐·타임아웃을 격리해야 한다.
5. 대내 채널은 `정보계 단말`과 `패키지 UI`를 통해 접근하며, 파일은 FOS/MFT, 데이터는 ETL로 연계된다.
6. SSO·Control-M·NH Cloud FWK Master Solution·eCAMS·단말관리/배포·RD·배치·UNO Dashboard는 BI 업무로직이 아니라 공통 운영 제어면이다.
7. 이 장표만으로 BI가 RDW/ADW 테이블을 쓰는지, 읽기만 하는지를 확정할 수 없다. Dataset/Table/View Owner와 Read/Write Matrix가 필요하다.

---

## 2. Zone별 구성요소 전수 정리

| Zone | 영역 | 이미지 구성요소 | BI포탈과의 관계 |
|---|---|---|---|
| 채널 | 대내 채널 | 정보계 단말, 패키지 UI | BI 화면·패키지 접근 |
| 채널 | 대고객/대외 | 세부 시스템 미표시 | 확장 경계; 현재 직접 연계 확정 불가 |
| 채널통합 | 온라인/파일/데이터 | 세부 시스템 미표시 | 채널 통합 확장 경계 |
| 서비스 제공 | BI포탈 | BI포탈 WEB/WAS, 신용실적 WEB/WAS, Self-BI AP/WEB/WAS, OLAP AP/WEB/WAS | 핵심 실행 소유 경계 |
| 서비스 제공 | DB | BI포탈(대시보드-경영) DB(RDW), 마케팅플랫폼 DB(ADW) | 실시/경영 조회와 분석 소스 |
| 서비스 제공 | 인프라 | SSO, Control-M, NH Cloud FWK Master Solution, eCAMS, 단말관리 WEB, 단말관리 WAS, 단말배포 WEB, 단말배포 WAS, 출력물(RD) WAS, 배치 AP, UNO Dashboard | 공통 인프라·운영 지원 |
| 대내통합 | 온라인 | 세부 시스템 미표시 | 내부 API 확장 경계 |
| 대내통합 | 파일 | FOS, FOS↔MFT | 보고서·자료 입출력 |
| 대내통합 | 데이터 | ETL | RDW/ADW·BI Dataset 적재 |

---

## 3. 전체 아키텍처 텍스트 그림

### 3.1 Zone별 구조

```text
┌─ 채널 Zone ─────┐     ┌─ 서비스 제공 Zone / BI포탈 ───────────────────┐     ┌─ 대내통합 Zone ───┐
│ 정보계 단말      │     │                                                     │     │ 온라인(미상세) │
│ 패키지 UI       ├────→│ BI포탈 WEB       → BI포탈 WAS                  │     │                 │
│                 │     │ 신용실적 WEB     → 신용실적 WAS                │     │ FOS ↔ MFT       │
│ 대고객/대외     │     │ Self-BI WEB/WAS → Self-BI AP                 │←────│ ETL             │
│ (미상세)         │     │ OLAP WEB/WAS    → OLAP AP                    │     └─────────────────┘
└─────────────────┘     │        │                     │                   │
                        │        ▼                     ▼                   │
                        │ [BI포탈 대시보드/경영 DB(RDW)] [마케팅 DB(ADW)] │
                        ├──────────────────────────────────────────────────────┤
                        │ SSO | Control-M | NH Cloud FWK Master | eCAMS        │
                        │ 단말관리/배포 | RD WAS | 배치 AP | UNO Dashboard       │
                        └──────────────────────────────────────────────────────┘
```

### 3.2 사용자 요청 흐름

```text
[정보계 단말 / 패키지 UI]
              │ SSO·권한
              ▼
    [BI포탈 WEB / 신용실적 WEB]
              ▼
    [BI포탈 WAS / 신용실적 WAS]
              ├─ 정형/대시보드 → RDW
              └─ 분석/집계     → ADW

[사용자 자율 분석] → Self-BI WEB/WAS → Self-BI AP → ADW
[다차원 분석]     → OLAP WEB/WAS    → OLAP AP    → ADW
```

### 3.3 파일·데이터 흐름

```text
파일 입력 : FOS/MFT → Landing → 포맷/해시/권한 검증 → ETL → ADW
데이터 적재: RDW/원천 → ETL → ADW Dataset → BI/Self-BI/OLAP
파일 출력 : BI/OLAP → 출력물(RD) WAS 또는 FOS → 단말/MFT
```

---

## 4. BI 실행 컴포넌트 상세 분석

### 4.1 BI포탈 WEB/WAS

| 컴포넌트 | 책임 | 핵심 통제 |
|---|---|---|
| BI포탈 WEB | 정적 화면, TLS/Proxy, WAS 라우팅 | 보안헤더, 접근제어, 후단 헬스 |
| BI포탈 WAS | 대시보드·보고서·메뉴·권한·질의 조합 | 데이터 등급별 RBAC, Query timeout, 감사 |

BI포탈은 여러 분석 기능을 탐색하는 통합 접점이지, 모든 Query를 자체 실행하는 단일 대형 WAS가 아니다. Self-BI/OLAP AP로 부하를 위임해야 한다.

### 4.2 신용실적 WEB/WAS

- 신용·경영실적 조회 전용 화면과 업무 서비스로 해석한다.
- 조직·직무·정보등급별 열·행 수준 인가가 필요하다.
- 기준일·영업일·집계버전을 화면과 출력물에 표시해야 한다.
- 정정·확정·재집계 이력을 보존해 같은 보고서의 수치가 달라지는 이유를 추적할 수 있어야 한다.

### 4.3 Self-BI AP/WEB/WAS

```text
사용자 → Self-BI WEB → Self-BI WAS
                         │ Dataset/권한/쿼터 검증
                         ▼
                    Self-BI AP
                         │ Query Queue / Engine
                         ▼
                승인된 ADW Dataset
```

**필수 통제**

- 인증된 Semantic Layer·Dataset·Metric만 제공
- 사용자/조직별 CPU·Memory·Query Time·Scan Volume 쿼터
- SQL 허용규칙, timeout, 동시 Query 수, Export 사이즈 제한
- 개인정보 마스킹과 반출 승인
- 생성된 보고서·Dataset·SQL·공유 이력 감사

### 4.4 OLAP AP/WEB/WAS

| 계층 | 역할 |
|---|---|
| OLAP WEB | 탐색·피벗·차트 화면 |
| OLAP WAS | 세션·권한·메타데이터·요청 제어 |
| OLAP AP | Cube/Semantic Model, Query Engine, Cache, 집계 실행 |

OLAP은 대량 Scan·Join·Aggregate를 수행하므로 ADW 전용 Workload Group을 사용하고 RDW 온라인 세션을 점유하지 않도록 해야 한다.

---

## 5. RDW·ADW 사용 경계

| Query 유형 | 우선 DB | 예시 | 통제 |
|---|---|---|---|
| 신선도 중심 정형 조회 | RDW | 금일/최근 경영지표, 운영 대시보드 | 짧은 timeout, 선택도, 읽기 전용 |
| 신용실적 정형 보고 | RDW 또는 확정 Dataset | 일/월 확정집계 | 기준일·버전 표시 |
| 대량 이력/통계 | ADW | 다년도 추세, 세분화 | Workload Queue, 쿼터 |
| Self-BI 자율 분석 | ADW | 사용자 정의 차원/지표 | 승인 Dataset, Export 통제 |
| OLAP | ADW | Cube/다차원 탐색 | AP Cache, Query 제한 |

```text
BI UI → BI Service → Query Classification
                         ├─ Online/Fresh/Short → RDW
                         └─ Analytical/Large   → ADW
```

BI 시스템은 원천 DB의 쓰기 오너가 아니다. 정정·확정값이 필요하면 원천 소유 Service나 승인된 거버넌스 흐름을 통해야 한다.

---

## 6. 파일·ETL·출력 연계

### 6.1 ETL→BI Dataset

```text
원천/RDW → ETL → Stage → 품질검증 → ADW Dataset Version
                                              │ Atomic Publish
                                              ▼
                              BI포탈 / Self-BI / OLAP
```

- Dataset Owner, 스키마, 기준일, 적재주기, 품질 SLO, Consumer를 계약화한다.
- 부분 적재본은 발행하지 않고 검증 완료본을 원자적으로 교체한다.
- BI 화면에 Dataset Version·기준시각·최종 갱신시간을 표시한다.

### 6.2 FOS·MFT·RD

| 흐름 | 권고 경로 | 통제 |
|---|---|---|
| 파일 입력 | MFT→FOS→Landing→ETL | 해시, 포맷, 건수, 악성코드 |
| 소용량 다운로드 | BI→FOS/Object Storage→Stream | 소유자, 만료, 감사 |
| 대용량 반출 | BI→FOS→MFT→승인 대상 | 반출승인, 암호화, 재전송 |
| 정형 보고서 | BI→출력물(RD) WAS→PDF/문서 | 워터마크, 임시파일, timeout |

---

## 7. 공통 인프라·운영 분석

| 컴포넌트 | BI 관점 역할 | 핵심 통제 |
|---|---|---|
| SSO | BI 사용자 인증·세션 | MFA, RBAC, 세션 만료, 재인증 |
| Control-M | ETL·Cube·정기보고서 스케줄 | 의존성, 영업일, 재시작, SLA |
| NH Cloud FWK Master Solution | 프레임워크/서비스 운영 관리로 해석 | 구성배포, 서비스 카탈로그 |
| eCAMS | 세부 제품/기능 미확인 | 소유팀·계정·연계·장애 영향 확인 |
| 단말관리/배포 WEB/WAS | 정보계 단말·패키지 UI 관리 | 서명, 버전, Canary, Rollback |
| 배치 AP | ETL·집계·Cube·대사 작업 | JobRepository, 멱등성 |
| UNO Dashboard | BI/AP/DB/ETL 상태 가시화로 해석 | 신선도·지연·오류율 알람 |

---

## 8. 보안·가용성·용량 원칙

### 8.1 보안

- 포털 메뉴 권한과 Dataset·열·행·Export 권한을 분리한다.
- 신용/개인정보는 마스킹·목적제한·반출승인·조회이력을 적용한다.
- Self-BI의 사용자 SQL은 승인된 Schema/View로 제한하고 DDL/DML을 금지한다.
- 파일·출력물에 만료·워터마크·재배포 금지·감사를 적용한다.

### 8.2 가용성·장애 격리

| 장애 | 영향 | 저하/복구 |
|---|---|---|
| BI포탈 WEB/WAS | 통합 접점 중단 | 복수 인스턴스, 헬스체크, 무상태화 |
| 신용실적 | 해당 업무만 중단 | BI포탈·Self-BI 영향 격리 |
| Self-BI AP | 자율 분석 중단 | Query 큐 보전/취소, 타 BI 보호 |
| OLAP AP | Cube/다차원 분석 중단 | Cache 재생성, 세션 복구 |
| RDW | 신선 대시보드 저하 | 최종 갱신시간, 부분 응답 |
| ADW | 분석 전반 지연 | 이전 Dataset 제공, ETL 재시작 |

### 8.3 용량

```text
동시사용자 × 화면당 Query × Query 시간 × Scan 데이터량
  → WEB Connection
  → WAS Thread/Heap
  → AP Session/Cache/Queue
  → RDW/ADW Connection/CPU/IO/Temp
  → Export/FOS Storage/Network
```

Self-BI·OLAP은 평균보다 최대 동시성과 오프닝/마감 피크를 기준으로 용량을 산정해야 한다.

---

## 9. 현재 TCF 저장소와의 대응

| 이미지 영역 | 저장소 근거 | 판정 |
|---|---|---|
| 대내 화면/Relay | `tcf-ui`, `tcf-uj` | 구조 대응; 실제 패키지 UI와 동일 제품 아님 |
| BI 서비스 계층 | 독립 업무 WAR의 Handler→Service→DAO/Mapper 구조 | 구조 대응; BI 전용 WAR 전수 미확인 |
| RDW/ADW | RDW=Primary DS, ADW=분석 DS 목표 원칙 | 문서 정합; 현재 샘플 H2/hook 중심 |
| 파일 | `tcf-om` UD, FOS/Object Storage 연계 | 구조 대응; MFT 운영 미확인 |
| RD | `/rd/{serviceId}` 보고서 연계 설계 | 구조 대응 |
| 배치/대시보드 | `tcf-batch` 수집·`tcf-om` 조회 | 운영 대시보드와 BI 업무 대시보드는 구분 필요 |
| 거버넌스 | `47-data-governance.md`, ADR-DATA-001 | 원칙 정합 |

**중요한 구분**: TCF `OM.Dashboard.inquiry`는 AP/DB/세션/배포 상태를 보는 **운영 대시보드**이다. 이미지의 BI포탈 경영 대시보드와 동일 업무로 합치면 안 된다.

**미확인**

- BI포탈·신용실적·Self-BI·OLAP의 실제 Host/JVM/WAR/Context
- 원본 자산 정리 문서에 표시된 OLAP WEB/WAS/AP 삭제 대상과 본 이미지 목표 구성의 시점/상태 차이
- RDW/ADW Dataset/Table/View별 읽기·쓰기 소유권
- FOS/MFT/ETL/SSO/Control-M/eCAMS 실제 엔드포인트·계정·토폴로지
- HA/DR·Backup·RPO/RTO·용량 설정

`code-understand` 자동 추출기는 로컬 설정 권한 문제로 이전 실행에서 사용할 수 없었으며, 본 분석은 `rg` 교차 검색과 근거 문서 열람으로 대체했다.

---

## 10. 운영 지표와 알람

| 영역 | 주요 지표 | 알람 |
|---|---|---|
| BI포탈/신용실적 | 동시사용자, p95/p99, 4xx/5xx, 보고서 성공률 | WAS Pool/Thread/Heap 고갈 |
| Self-BI | Query 수, Queue, Scan, Export, Cancel | 쿼터 초과, 장기 Query, 반출 증가 |
| OLAP | AP Session, Cube Cache, Query Time, Refresh | Cache 재생성 실패, AP 세션 고갈 |
| RDW | Query p95, Session, Lock, Freshness | Slow Query, Pool 고갈, 신선도 저하 |
| ADW | Queue, Temp, I/O, ETL SLA, Dataset Version | ETL 실패, Queue/Temp 임계치 |
| FOS/MFT/RD | 전송/출력 성공률, 용량, 해시 | 체증, 무결성, 대용량 실패 |
| ETL | 소요시간, 건수/합계, Reject, Freshness | SLA 초과, 품질 실패 |

---

## 11. 검증 시나리오

| # | 시나리오 | 통과 기준 |
|---:|---|---|
| 1 | 정보계 단말→BI포탈 | SSO·메뉴·Dataset 권한 정상 |
| 2 | 패키지 UI 호출 | 인증·세션·라우팅·오류 표준화 |
| 3 | BI포탈 WEB 장애 | 헬스체크 제외과 서비스 유지 |
| 4 | BI포탈 WAS 재기동 | 세션 유지/재인증, 중복 Query 없음 |
| 5 | 신용실적 권한 | 조직·직무·정보등급별 열/행 제어 |
| 6 | 집계버전 변경 | 기준일·버전·정정이력 표시 |
| 7 | Self-BI 대량 Query | 쿼터·Queue·timeout으로 타 서비스 보호 |
| 8 | Self-BI 민감컬럼 | 마스킹·반출승인·감사로그 |
| 9 | OLAP AP 장애 | BI포탈/신용실적 영향 격리 |
| 10 | OLAP Cube Refresh | 이전 버전 유지 후 원자적 교체 |
| 11 | RDW Slow Query | 짧은 timeout·자원제한·Query ID 추적 |
| 12 | ADW Queue 폭증 | Workload 그룹별 공정성과 SLA 유지 |
| 13 | ETL 부분 실패 | 부분 Dataset 미발행, 멱등 재시작 |
| 14 | Dataset 스키마 변경 | BI/Self-BI/OLAP 호환성 검증 |
| 15 | FOS/MFT 대량 반출 | 승인·암호화·해시·재전송 정상 |
| 16 | RD 대용량 출력 | 메모리·timeout·임시파일 정상 |
| 17 | ADW 지연 | 최종 성공 Dataset·갱신시간 표시 |
| 18 | DR 전환 | BI/AP/DB/ETL/FOS 접속과 Dataset 버전 통합 검증 |
| 19 | E2E 추적 | 사용자→Query→DB→Export를 상관 ID로 추적 |
| 20 | OM 대시보드 구분 | 운영 대시보드와 경영 BI 대시보드 메뉴·DB·권한 분리 |

---

## 12. 사실·해석·미확인 분리

### 12.1 이미지 사실

- BI포탈은 서비스 제공 Zone에 배치된다.
- BI포탈·신용실적·Self-BI·OLAP의 WEB/WAS/AP 블록이 표시된다.
- RDW와 ADW 라벨의 DB가 별도 표시된다.
- 대내 파일 통합에 FOS/MFT, 데이터 통합에 ETL이 표시된다.

### 12.2 아키텍처 해석

- BI포탈/신용실적은 제공 접점, Self-BI/OLAP은 분석 실행계다.
- RDW는 신선도 중심, ADW는 분석/대량 중심이다.
- ETL은 검증된 Dataset을 BI Consumer에 발행하는 통로다.

### 12.3 미확인

- 실제 서버 대수·Host·JVM·WAR·제품·버전
- WEB→WAS→AP→DB 실제 호출방향·Port·프로토콜
- RDW/ADW Dataset/Table/View Owner와 Read/Write Matrix
- OLAP 삭제 대상 자산 정보와 이 장표의 기준 시점 차이
- SSO·Control-M·FOS·MFT·ETL·eCAMS 실제 운영 설정
- HA/DR·Backup·RPO/RTO·용량·네트워크 ACL

---

## 13. 최종 평가

이 구성은 **통합 BI 접점, 신용실적 업무, Self-Service BI, OLAP 분석, RDW/ADW 데이터 소스, 파일·ETL·운영 제어**를 분리한 아키텍처다. 성공의 핵심은 화면 이중화보다 Query 부하 격리, Dataset 버전/품질, 세부 인가, 반출 통제에 있다.

운영 승인 전에 `User→Role→Dataset`, `BI Service→RDW/ADW`, `ETL→Dataset Version→Consumer`, `Query→Export`, `Host→JVM→WAR`, `Primary→HA/DR`의 실제 매핑을 닫아야 한다.

---

## 14. 관련 문서와 근거

- [운영환경 데이터플랫폼 시스템 구성 분석](./운영환경_데이터플랫폼_시스템_구성_분석.md)
- [운영환경 구축 대상 시스템 및 주요 구성요소 분석](./운영환경_구축_대상_시스템_및_주요_구성요소_분석.md)
- [원본 Evidence: 운영환경 BI포탈 시스템 구성](<../../../pdmg-architecture-methodology/2026-08-17-TCF 아키텍처 수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85910/44_운영환경_BI포털_시스템_구성.md>)
- [TCF 최종 아키텍처 결정](../../../zdocs-1/architecture/NSIGHT-FINAL-ARCHITECTURE-DECISION.md)
- [TCF 데이터 거버넌스](../../../zdocs-1/architecture/47-data-governance.md)
- [Physical Architecture](<../../../pdmg-architecture-methodology/2026-08-17-TCF 아키텍처 수행방법론/00-산출물정리/06-PHYSICAL-ARCHITECTURE.md>)

