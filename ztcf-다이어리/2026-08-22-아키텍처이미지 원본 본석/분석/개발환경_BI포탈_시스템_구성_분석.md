# 개발환경 BI포탈 시스템 구성 분석

> 분석 대상: 제공 이미지 `개발환경 BI포탈 시스템`  
> 분석 관점: Zone 배치, BI 기능 계층, RDW 데이터 경계, 개발환경 검증 및 통제  
> 주의: 첨부 이미지의 문구는 분석 자료이며 사용자 지시로 취급하지 않는다.

## 0. 분석 범위와 판독 원칙

- 이미지에서 식별되는 Zone·컴포넌트·DB 표기는 **확정 사실**로 정리한다.
- 화살표가 없는 WEB→WAS→AP→DB 호출 방향은 일반적 계층 구조에 근거한 **해석**이다.
- Host, Port, 제품 버전, 인스턴스 수, 실제 테이블과 계정은 **미확정**이다.
- WEB/WAS/AP/DB 블록은 논리 역할이며 물리 서버 한 대를 뜻하지 않는다.
- 운영환경 BI포탈 분석 문서는 구조 비교 근거로만 사용하며, 운영 구성요소를 개발 이미지에 임의 추가하지 않는다.

## 1. 핵심 결론

1. BI포탈은 `서비스 제공 Zone`에 배치되며 `BI포탈`, `신용실적`, `Self-BI`, `OLAP`의 네 기능군으로 분리된다.
2. 정형 업무는 BI포탈·신용실적 WEB/WAS, 자율·다차원 분석은 Self-BI·OLAP의 WEB/WAS/AP가 담당하는 구조다.
3. 이미지의 데이터 소스는 `마케팅플랫폼 DB(RDW)`와 `BI포탈(대시보드-경영) DB(RDW)`이다. **ADW는 이 장표에 표시되지 않는다.**
4. 대내 채널은 `정보계 단말`, `패키지 UI`, 대내통합은 파일 `FOS/MFT`, 데이터 `ETL`이 표시된다.
5. 개발환경에서는 운영 데이터·DB 계정·SSO Realm·파일 경로를 공유하지 않고 합성 또는 비식별 데이터로 권한·쿼리·출력·배치 흐름을 검증해야 한다.
6. Self-BI와 OLAP은 임의 SQL·대량 Scan 가능성이 크므로 정형 BI와 RDW 자원그룹, 연결 Pool, Query Queue, Timeout을 분리해야 한다.
7. 개발 장표에는 `단말개발도구 AP`, `소스관리 AP`가 포함되어 소스→빌드→배포→BI 회귀검증을 연결할 수 있다.

## 2. Zone별 구성요소 전수 정리

| Zone | 영역 | 이미지 구성요소 | 역할 해석 |
|---|---|---|---|
| 채널 | 대내 채널 | 정보계 단말, 패키지 UI | BI 화면 및 패키지 접근점 |
| 채널 | 대고객 채널 | 세부 구성 미표시 | 직접 연계 확정 불가 |
| 채널 | 대외 채널 | 세부 구성 미표시 | 직접 연계 확정 불가 |
| 채널 통합 | 온라인·파일·데이터 통합 | 세부 구성 미표시 | 개발 BI의 채널통합 경계만 표현 |
| 서비스 제공 | BI포탈 | BI포탈 WEB, BI포탈 WAS | 통합 BI 화면·업무 서비스 |
| 서비스 제공 | 신용실적 | 신용실적 WEB, 신용실적 WAS | 신용·실적 정형 조회 |
| 서비스 제공 | Self-BI | Self-BI AP, Self-BI WEB, Self-BI WAS | 사용자 자율 분석 |
| 서비스 제공 | OLAP | OLAP AP, OLAP WEB, OLAP WAS | 다차원 분석·집계 |
| 서비스 제공 | 데이터 | 마케팅플랫폼 DB(RDW), BI포탈(대시보드-경영) DB(RDW) | BI 조회·분석 데이터 소스 |
| 서비스 제공 | 공통 인프라 | SSO, Control-M, NH Cloud FWK Master Solution, eCAMS | 인증·스케줄·표준 프레임워크·관리 |
| 서비스 제공 | 개발·단말 인프라 | 단말관리 WEB, 단말관리 WAS, 단말배포 WEB, 단말배포 WAS | 단말 자산·정책·배포 |
| 서비스 제공 | 개발·실행 인프라 | 단말개발도구 AP, 소스관리 AP, 출력물(RD) WAS, 배치 AP | 개발·형상·보고서·배치 |
| 대내 통합 | 온라인 통합 | 세부 구성 미표시 | 내부 API 연계 미확정 |
| 대내 통합 | 파일 통합 | FOS, FOS, MFT | 파일 송수신·중계 |
| 대내 통합 | 데이터 통합 | ETL | RDW·BI 데이터셋 적재·변환 |

## 3. 텍스트 아키텍처 그림

### 3.1 Zone 전체 구조

```text
┌──────── 채널 Zone ────────┐
│ 정보계 단말 | 패키지 UI   │
│ 대고객/대외 채널: 미상세  │
└───────────┬───────────────┘
            │ 인증·화면 요청
┌───────────▼──────────────────────────────────────────┐
│ 서비스 제공 Zone — 개발환경 BI포탈                  │
│  BI포탈 WEB ─ BI포탈 WAS                            │
│  신용실적 WEB ─ 신용실적 WAS                        │
│  Self-BI WEB ─ Self-BI WAS ─ Self-BI AP             │
│  OLAP WEB ─ OLAP WAS ─ OLAP AP                      │
│          │                    │                      │
│  [마케팅플랫폼 DB(RDW)] [BI포탈 대시보드-경영 DB(RDW)]│
├──────────────────────────────────────────────────────┤
│ SSO | Control-M | NH Cloud FWK | eCAMS              │
│ 단말관리·배포 | 단말개발·소스관리 | RD WAS | 배치 AP │
└───────────┬──────────────────────────────────────────┘
            │
┌───────────▼───────────────┐
│ 대내통합: FOS/MFT | ETL   │
└───────────────────────────┘
```

### 3.2 BI 기능 계층

```text
[정보계 단말 / 패키지 UI]
              │
              ▼
┌────────────────────────────────────┐
│ WEB: 화면·정적자원·TLS·라우팅       │
└────────────────┬───────────────────┘
                 ▼
┌────────────────────────────────────┐
│ WAS: 세션·권한·메뉴·업무 API        │
└──────────┬──────────────────┬──────┘
           │                  │
           ▼                  ▼
  [정형 BI/신용실적]   [Self-BI AP / OLAP AP]
           │                  │
           └────────┬─────────┘
                    ▼
             [개발 RDW 데이터]
```

### 3.3 BI포탈·신용실적 정형 조회

```text
[사용자]
   │ SSO
   ▼
[BI포탈 WEB] ─→ [BI포탈 WAS] ─┬→ [대시보드-경영 RDW]
                              └→ [마케팅플랫폼 RDW]

[사용자]
   │ 역할·조직·정보등급
   ▼
[신용실적 WEB] → [신용실적 WAS] → [승인 View/Dataset]
                                      │
                                      └→ 기준일·집계버전 표시
```

### 3.4 Self-BI 자율 분석

```text
[Self-BI 사용자]
       │
       ▼
[Self-BI WEB] → [Self-BI WAS]
                       │ Dataset·컬럼·Export 권한
                       ▼
                  [Self-BI AP]
                       │ Query Queue / Quota / Timeout
                       ▼
              [승인된 개발 RDW View]
                       │
                       └→ 결과·SQL·반출 감사
```

### 3.5 OLAP 분석

```text
[OLAP WEB]
     │ 피벗·차트·탐색
     ▼
[OLAP WAS]
     │ 세션·권한·메타데이터
     ▼
[OLAP AP]
     │ Cube/Semantic Model/Cache
     ▼
[개발 RDW 분석용 Dataset]

통제: OLAP Queue/Pool ─X─ 정형 BI Pool
```

### 3.6 두 RDW 데이터 경계

```text
                 [Query Classifier]
                 /                \
                ▼                  ▼
┌────────────────────────┐  ┌──────────────────────────┐
│ 마케팅플랫폼 DB(RDW)    │  │ BI포탈 대시보드-경영     │
│                        │  │ DB(RDW)                  │
│ 고객·마케팅 Dataset 후보│  │ 경영·대시보드 Dataset 후보│
└───────────┬────────────┘  └────────────┬─────────────┘
            └──────── 기준일·지표 정의 ───┘

주의: 실제 Table/View와 읽기·쓰기 소유권은 이미지로 확정 불가
```

### 3.7 ETL 데이터셋 발행

```text
[개발 원천/승인 테스트 파일]
             │
             ▼
         [ETL / 배치 AP]
             │ Extract→Validate→Transform→Load
             ▼
       [Stage / Quality Gate]
             │ 건수·합계·Null·중복·기준일 대사
             ▼
 [Versioned RDW View/Dataset Publish]
             │
      ┌──────┼─────────┐
      ▼      ▼         ▼
  BI포탈  Self-BI    OLAP
```

### 3.8 파일·출력 연계

```text
입력: [MFT] → [FOS] → [Landing] → 검증 → [ETL] → [RDW]
                       │
                       ├─ checksum / recordCount
                       └─ 악성코드 / 포맷 / 중복 검사

출력: [BI/신용실적] → [출력물(RD) WAS] → [FOS] → [MFT/승인 사용자]
                          │
                          └─ 마스킹·워터마크·만료·감사
```

### 3.9 개발 CI/CD와 BI 회귀검증

```text
[소스관리 AP]
      │ Commit / Review
      ▼
[Build + Unit + Contract + SQL Lint]
      │
      ▼
[개발 BI WEB/WAS/AP 배포]
      │
      ├→ 화면·권한 회귀
      ├→ Dataset/Metric 계약
      ├→ Query Plan·Timeout
      └→ RD 출력·파일 반출
              │
              ▼
         [승격 승인 증적]
```

### 3.10 개발 데이터 격리

```text
[합성 데이터 / 승인된 운영 추출]
                 │
                 ▼
          [비식별·마스킹]
                 │
                 ▼
       [버전형 개발 Test Dataset]
                 │
        ┌────────┴────────┐
        ▼                 ▼
[마케팅 RDW Dev]   [BI포탈 RDW Dev]
        │                 │
        └──── 테스트 ─────┘
                 │
                 ▼
        [결과 보존 후 초기화]

금지: 운영 DB 계정·운영 URL·운영 개인정보 원문
```

## 4. BI 실행 컴포넌트 상세 분석

### 4.1 BI포탈 WEB/WAS

| 컴포넌트 | 책임 | 개발 검증 포인트 |
|---|---|---|
| BI포탈 WEB | 정적 화면, TLS 종료 또는 Proxy, WAS 라우팅 | 보안 Header, Cache, Health, 잘못된 Route 차단 |
| BI포탈 WAS | 메뉴, Dashboard, 보고서, 권한, Query 조합 | 역할별 권한, Timeout, 오류 표준, 추적 ID |

BI포탈은 모든 분석을 자체 처리하는 단일 실행기가 아니라 Self-BI·OLAP·RDW로 요청을 분류하는 통합 접점이어야 한다.

### 4.2 신용실적 WEB/WAS

- 신용 및 경영실적 정형 조회를 제공하는 업무 경계로 해석한다.
- 조직·직무·정보등급에 따라 Row/Column 권한을 분리한다.
- 화면과 출력물에 기준일, 최종 갱신시각, 집계버전을 표시한다.
- 정정·확정·재집계 이력을 통해 동일 보고서의 수치 변경 원인을 추적한다.

### 4.3 Self-BI AP/WEB/WAS

- WEB은 자율 분석 UI, WAS는 Session·Dataset·공유·Export 제어, AP는 Query 실행·Queue·Cache를 담당한다.
- 사용자 임의 SQL은 승인된 Schema/View에 한정하고 DDL/DML을 차단한다.
- 사용자·조직별 CPU, Memory, Scan Volume, 동시 Query, Export 용량을 제한한다.
- SQL, Dataset, 결과 반출, 공유 이력을 감사한다.

### 4.4 OLAP AP/WEB/WAS

- WEB은 Pivot·Chart, WAS는 Session·권한·Metadata, AP는 Cube·Semantic Model·Cache·집계를 담당한다.
- 대량 Scan과 집계가 정형 BI Pool을 고갈시키지 않도록 전용 Workload Group을 사용한다.
- Cube Refresh 실패 시 이전 성공 버전을 유지하고 검증 완료 후 원자적으로 교체한다.

## 5. RDW 데이터 사용 경계

| 데이터 영역 | 이미지 표기 | 우선 용도 해석 | 필수 확인 |
|---|---|---|---|
| 마케팅 | 마케팅플랫폼 DB(RDW) | 고객·마케팅 관련 BI Dataset | Schema/View Owner, 허용 Query, 갱신주기 |
| 경영·대시보드 | BI포탈(대시보드-경영) DB(RDW) | 경영지표·정형 대시보드 | 지표 정의, 기준일, 집계버전 |

두 DB가 모두 `RDW`로 표시되므로 이 이미지 근거만으로 ADW 분석 경로를 가정해서는 안 된다. Self-BI·OLAP의 대량 분석이 RDW를 사용한다면 다음 통제가 필수다.

- 읽기 전용 계정과 승인 View 사용
- BI포탈, Self-BI, OLAP별 Connection Pool 분리
- Query Queue, Statement Timeout, Scan Limit, Temp/Spill 제한
- 정형 조회와 분석 조회의 Resource Group 분리
- 장기 Query 취소 및 Query ID 기반 추적

## 6. 개발환경 데이터·권한 통제

1. 기본 데이터는 합성 데이터로 하고 현실 분포가 필요한 경우 승인된 비식별 데이터를 사용한다.
2. 고객번호, 계좌, 연락처, 신용정보는 비가역 마스킹 또는 토큰화한다.
3. 개발 DB Service, Schema, 계정, Secret, SSO Realm, FOS 경로를 운영과 분리한다.
4. 보고서, Export, Reject 파일, 로그에도 동일한 마스킹 규칙을 적용한다.
5. Dataset Seed와 Reset Script를 버전 관리하여 테스트를 반복 재현한다.
6. 개발자는 필요 역할만 부여받고 관리자·반출 행위를 감사한다.

## 7. 데이터 계약과 지표 거버넌스

| 계약 영역 | 필수 항목 |
|---|---|
| Dataset | datasetId, owner, schemaVersion, 기준일, 갱신주기 |
| Metric | 명칭, 산식, 단위, 집계 Grain, 포함·제외 조건 |
| 권한 | Role, Row/Column Filter, Masking, Export 가능 여부 |
| 품질 | Null, 중복, 참조 무결성, 건수·합계, 허용 오차 |
| 운영 | SLA, Timeout, 재시도, 이전 성공본, 연락체계 |
| 추적 | correlationId, queryId, batchId, fileId, reportVersion |

BI포탈·신용실적·Self-BI·OLAP이 같은 지표를 사용하면 Metric 정의와 기준시점을 공통 Semantic Layer에서 공유해야 한다.

## 8. 공통 인프라 분석

| 구성요소 | BI 관점 역할 | 개발 검증 포인트 |
|---|---|---|
| SSO | 사용자 인증·Session | 개발 Realm, Role Mapping, 만료·재인증 |
| Control-M | ETL·집계·정기보고서 Schedule | 영업일, 의존성, 재시작, SLA |
| NH Cloud FWK Master Solution | 표준 애플리케이션 실행·관리 기반 | 공통 설정, 배포, 로깅, 예외 |
| eCAMS | 구성·배포·운영 관리 지원 후보 | 개발/운영 Configuration 분리와 감사 |
| 단말관리 WEB/WAS | 단말·조직·정책 관리 | 개발 단말군과 운영 단말군 분리 |
| 단말배포 WEB/WAS | 패키지 배포 | 서명·해시·Canary·Rollback |
| 단말개발도구 AP | 단말·패키지 개발 지원 | SDK/Plugin Version, 호환성 |
| 소스관리 AP | Source·Tag·Review 관리 | Branch 보호, Commit→Artifact 추적 |
| 출력물(RD) WAS | 정형 보고서 생성 | Template, Font, Masking, 임시파일 파기 |
| 배치 AP | ETL·집계·Cube Refresh | 멱등성, Checkpoint, 건수 대사 |

## 9. 장애·재처리·관측성

| 장애 | 영향 | 복구·보호 원칙 |
|---|---|---|
| BI포탈 WEB/WAS 장애 | 통합 접점 중단 | 무상태화, Health 제외, 재인증 |
| Self-BI AP 과부하 | 자율 분석 지연 | Queue·Quota·Cancel, 정형 BI 보호 |
| OLAP AP/Cube 실패 | 다차원 분석 중단 | 이전 Cube 유지, 원자적 재발행 |
| RDW 지연 | Dashboard·분석 지연 | 마지막 성공 Dataset과 갱신시각 표시 |
| ETL 부분 실패 | Dataset 불완전 | 미완성본 비공개, Checkpoint 재시작 |
| FOS/MFT 오류 | 입출력 지연 | Checksum·Sequence 기반 재전송 |
| RD 출력 실패 | 보고서 미생성 | Job ID 재처리, 임시파일 정리 |

**주요 지표**

- WEB/WAS: 동시 사용자, p95/p99, 4xx/5xx, Thread/Pool/Heap.
- Self-BI/OLAP: Queue, Scan Volume, Query Time, Cancel, Cache/Cube Refresh.
- RDW: Session, CPU/IO, Lock, Temp, Long Query, Dataset Freshness.
- ETL: Job/Step 상태, 처리·오류 건수, 기준일, 소요시간.
- FOS/MFT/RD: 전송·출력 성공률, Checksum, 파일 크기, 임시 저장 용량.
- 보안: 권한 거부, 민감정보 조회, Export, 관리자 작업.

## 10. 운영환경과 개발환경 비교

| 항목 | 운영환경 | 이번 개발환경 이미지 |
|---|---|---|
| 핵심 BI 실행계 | BI포탈·신용실적·Self-BI·OLAP | 동일 기능군 표시 |
| DB 표기 | 대시보드-경영 DB(RDW), 마케팅플랫폼 DB(ADW) | 마케팅플랫폼 DB(RDW), 대시보드-경영 DB(RDW) |
| 개발 도구 | 운영 장표에 미표시 | 단말개발도구 AP, 소스관리 AP 표시 |
| Dashboard 도구 | UNO Dashboard 표시 | UNO Dashboard 미표시 |
| 데이터 | 운영 실제 데이터 | 합성·비식별 Test Dataset 권고 |
| 목적 | 고객 서비스·SLA | 기능·계약·회귀·장애 검증 |

DB 유형 차이는 단순 오탈자일 수도 있고 환경별 설계 차이일 수도 있다. 따라서 `운영 ADW ↔ 개발 RDW`의 Schema·SQL·Optimizer·Character Set 호환성을 실제 DB 카탈로그로 확인해야 한다.

## 11. 현재 TCF 저장소와의 연결 및 Gap

- 개발환경 전체 구성 문서는 BI WEB/WAS/AP, RDW/ADW, ETL, 소스관리·단말개발도구를 E2E 검증 대상으로 정의한다.
- 데이터 거버넌스 원칙은 Dataset Owner, Schema, 품질 Rule, Lineage, Consumer를 추적하도록 요구한다.
- CI/CD 원칙상 동일 Artifact를 환경별 재빌드하지 않고 설정·Secret만 분리해야 한다.
- TCF 운영 Dashboard와 경영 BI Dashboard는 목적·DB·권한이 다르므로 동일 기능으로 합치지 않는다.

**확인이 필요한 Gap**

1. 마케팅플랫폼·BI포탈 RDW의 실제 DB Service, Schema, Table/View.
2. Self-BI·OLAP이 RDW만 사용하는지 별도 ADW가 생략된 것인지.
3. 운영 장표의 마케팅 ADW와 개발 장표의 마케팅 RDW 간 환경 정합성.
4. BI포탈/신용실적/Self-BI/OLAP의 Host→JVM→WAR→Context 매핑.
5. ETL→Dataset→Consumer Lineage와 Read/Write Matrix.
6. SSO·Control-M·FOS·MFT·eCAMS의 개발 Endpoint와 계정.
7. 개발환경 통합 모니터링 도구의 실제 배치 여부.

## 12. 검증 시나리오

| 번호 | 시나리오 | 합격 기준 |
|---:|---|---|
| 1 | 정보계 단말→BI포탈 | 개발 SSO·메뉴·Route 정상 |
| 2 | 패키지 UI 접근 | 인증·Session·오류 표준 정상 |
| 3 | BI포탈 WEB 장애 | Health 제외 후 서비스 유지 |
| 4 | BI포탈 WAS 재기동 | 중복 Query 없이 복구 |
| 5 | 신용실적 권한 | 조직·직무별 Row/Column 제어 |
| 6 | 집계버전 변경 | 기준일·Version·정정 이력 표시 |
| 7 | Self-BI 임의 SQL | 승인 View 외 접근 차단 |
| 8 | Self-BI 대량 Query | Quota·Queue·Timeout 적용 |
| 9 | Self-BI Export | 마스킹·용량·승인·감사 정상 |
| 10 | OLAP Cube Refresh | 이전본 유지 후 원자적 교체 |
| 11 | OLAP 과부하 | 정형 BI Pool 영향 없음 |
| 12 | 마케팅 RDW Query | 개발계만 접속하고 권한 최소화 |
| 13 | BI포탈 RDW Query | 기준시점·지표 정의 일치 |
| 14 | ETL 정상 적재 | 입력·성공·출력 건수 대사 |
| 15 | ETL 부분 실패 | 미완성 Dataset 미발행·재시작 |
| 16 | Dataset Schema 변경 | Consumer Contract Test 통과 |
| 17 | FOS/MFT 파일 | Checksum·중복·재전송 정상 |
| 18 | RD 출력 | Template·마스킹·임시파일 정상 |
| 19 | CI/CD 배포 | Commit→Artifact→Runtime 추적 |
| 20 | 데이터 초기화 | 결과 보존 후 동일 Seed 재현 |
| 21 | 운영 연결 차단 | 운영 DB·SSO·FOS 접속 불가 |
| 22 | E2E 관측 | User→Query→DB→Export 추적 가능 |

## 13. 사실·해석·미확정 구분

| 구분 | 내용 |
|---|---|
| 이미지 확정 | 표에 열거한 Zone, BI 실행 컴포넌트, 두 RDW DB, FOS/MFT, ETL, 공통 인프라 |
| 구조 해석 | WEB→WAS→AP→DB, Self-BI/OLAP 자원 분리, ETL Dataset 발행 |
| 저장소 근거 | 개발환경 E2E 검증, 데이터 격리, CI/CD, 데이터 거버넌스 원칙 |
| 미확정 | 실제 호출 방향·Protocol·Host·Port·제품 버전·서버 수·HA·용량·RPO/RTO |

## 14. 최종 평가

개발환경 BI포탈은 운영과 유사한 네 BI 기능군을 유지하면서 소스관리·단말개발 도구를 포함해 변경을 반복 검증하는 구조다. 특히 두 데이터 소스가 모두 RDW로 표시되므로, Self-BI·OLAP의 대량 분석 부하가 정형 BI를 침범하지 않도록 Workload 격리가 핵심이다.

운영 전환의 신뢰성을 확보하려면 `User→Role→Dataset`, `Service→RDW View`, `ETL→Dataset Version→Consumer`, `Query→Export`, `Commit→Artifact→Runtime` 매핑을 실제 자산과 연결해야 한다. 개발환경의 목적은 운영의 단순 축소 복사본이 아니라 권한·데이터·쿼리·배포 결함을 운영 전에 재현하고 차단하는 것이다.

## 15. 관련 문서와 근거

- [개발환경 구축 대상 시스템 및 주요 구성요소](./개발환경_구축_대상_시스템_및_주요_구성요소_분석.md)
- [개발환경 데이터플랫폼 시스템 구성](./개발환경_데이터플랫폼_시스템_구성_분석.md)
- [운영환경 BI포탈 시스템 구성](./운영환경_BI포탈_시스템_구성_분석.md)
- [차세대 정보계 시스템 Zone 구성 기준](./차세대_정보계_시스템_ZONE_구성_기준_분석.md)
- [원본 Evidence: 개발환경 BI포털 시스템 구성](<../../../pdmg-architecture-methodology/2026-08-17-TCF 아키텍처 수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/85910/57_개발환경_BI포털_시스템_구성.md>)
- [TCF 데이터 거버넌스](../../../zdocs-1/architecture/47-data-governance.md)
- [CI/CD Deployment Architecture](<../../2026-08-17-TCF 아키텍처 수행방법론/00-산출물정리/23-CI-CD-DEPLOYMENT.md>)
