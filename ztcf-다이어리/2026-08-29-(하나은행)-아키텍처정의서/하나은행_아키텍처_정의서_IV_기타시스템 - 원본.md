# 하나은행 아키텍처 정의서 — IV. 기타 시스템 아키텍처

> 프로젝트 ONE · 1단계 **[마케팅플랫폼 및 데이터허브]**  
> 선행: [`I`](./하나은행_아키텍처_정의서_I_개요.md) · [`II`](./하나은행_아키텍처_정의서_II_시스템구성.md) · [`III`](./하나은행_아키텍처_정의서_III_마케팅플랫폼_데이터허브.md)  
> 작성 기준: 원문 목차 · Top-down TEXT 그림 프롬프트  
> 문서 성격: **초안** · 작성일: 2026-08-29

**기밀:** 하나은행 자산. 대외 반출 시 각별한 주의를 요망한다.

---

## 읽는 법

I~III와 동일하다. 목차 IV.1~4는 **`[업무담당파트 자료 참조]`**, IV.5는 **AS-IS 담당자 문의**. 담당 자료가 없는 기능·권한·SSO 흐름을 창작하지 않는다.

`ztcf-다이어리/2026-08-22-아키텍처이미지 원본 본석`의 BI포탈 분석은 **NSIGHT 장표**(NH Cloud, MSTR, BI-Matrix, eCAMS 등)이다. 하나은행 DataEye/JEUS/SAP BO 장표와 **섞어 FACT로 쓰지 않는다.** 비교가 필요할 때만 `[TO-BE/PROPOSED]`·비교로 표시한다.

---

## 본 장 입력자료

| 구분 | 경로 | 역할 |
|------|------|------|
| 목차 | IV.1~5, 업무담당·AS-IS 문의 | 범위·협업 |
| 물리 TA 장표 2 | BI포털·비즈메타 운영 | DataEye, JEUS, Oracle, L4, HA, Storage |
| 물리 TA 장표 3 | 현행 OLAP | WebSphere, SAP BO, IQ, Oracle |
| 물리 TA 장표 6 | 정보계 TO-BE | BI포털·분석도구·가상화 소비 |
| 물리 TA 장표 7·8 | 데이터흐름관리 | Q-Track, DAMS, 스펙, A-S |
| II.2~II.4 | APP-BIP/BIZ/SBI/DFM, 노드 | 교차 |
| 솔루션 매핑 | R34 OLAP & Self BI | 솔루션 행 존재 |
| 08-22 BI 분석 | NSIGHT | 비교만. 하나 원문 아님 |

업무담당 파트 정의서, Self BI 제품 선정서, SSO/EAM AS-IS 설계서는 **미입수**.

---

# IV. 장 연결

## 1. 핵심 결론

IV는 1단계 **코어(마케팅·허브) 옆의 정보계 소비·거버넌스·인증 예외**다.

```text
Enterprise Users / Data Producers
        │
        ├─ IV.1 BI포탈     조회·포털
        ├─ IV.2 BIZ메타    비즈니스 메타
        ├─ IV.3 Self BI    현업 자율 분석
        ├─ IV.4 데이터흐름관리  계보(메타), 적재 파이프 아님
        └─ IV.5 비표준단말 SSO/EAM
                │
                ▼
 Shared Data (RTW/ADW/BSA…) / Auth / Integration
                │
                ▼
 Core: 마케팅플랫폼 · 데이터허브 (I~III)
```

`[FACT]` 장표 2 제목이 **BI포털, 비즈메타**를 한 운영 구성도에 올린다. 논리 분리는 그 장표만으로 확정 불가 `[ANALYSIS]`.

`[FACT]` 흐름관리는 ETCL 적재가 아니라 **데이터 변화 흐름을 분석하는 솔루션**이다. 활용 칸에 DAMS · BI Portal · Biz Meta · Q-Track이 있다.

## 2. 목적 / 범위 / 전제

- **목적:** 각 기타 시스템을 같은 Top-down으로 두고 허브와의 위치를 고정한다.
- **범위:** IV.1~5. 마케팅 온라인은 III.
- **전제:** 담당 자료 없으면 기능 목록을 일반론으로 채우지 않는다.

## 3. 상위 Text Architecture

```text
[사용자]
 BI 사용자 · 메타 사용자 · 현업 분석가 · 거버넌스
 정보단말 · (비표준단말)
        │
        ▼
┌──── IV 기타 시스템 (정보계 소비·거버넌스) ────┐
│  BI포탈 (DataEye Portal, JEUS, A-A)           │
│  BIZ메타 (동일 스택 장표, 논리 TBD)            │
│  Self BI (목차·TRM 행, 제품 TBD)               │
│  데이터흐름관리 (Q-Track A-S)                  │
│  비표준단말 SSO/EAM (AS-IS 문의)               │
└──────────────┬───────────────┬────────────────┘
               │ JDBC/가상화    │ 계보 메타
               ▼               ▼
     ③ 저장소 (II.1)     ④ Data Interface 메타
     현행 OLAP (SAP BO) 는 소비층 As-Is, 이관 TBD
```

**그림 해설**

1. **시작점:** 분석·거버넌스 사용자 또는 원천 변화(계보는 메타).
2. **처리순서:** 포탈/SelfBI/OLAP은 저장을 읽고, 흐름관리는 이동 수단의 메타를 모아 포탈·메타에 제공한다.
3. **책임:** 업무담당 IV.1~4, AS-IS 담당 IV.5, DA는 소스, TA는 노드.
4. **종료점:** 보고서/검색/계보 화면. 원장 갱신은 이 장 밖.

## 4. 시스템별 Context → Runtime → Data 위치

| 시스템 | II.1 영역 | 앱 ID (임시) | Runtime 힌트 | Data |
|--------|-----------|--------------|--------------|------|
| BI포탈 | ② | APP-BIP | DataEye, JEUS, L4 | Portal Oracle + 허브 JDBC |
| BIZ메타 | ② | APP-BIZ | 장표상 포탈과 공유 가능 | 동 또는 TBD |
| Self BI | ② | APP-SBI | TBD | 허브 `[TBD]` |
| 흐름관리 | ②④ 메타 | APP-DFM | Q-Track, WebtoB, JEUS 8.5 | 자체 Oracle 19C |
| SSO/EAM | ① 예외 | — | TBD | — |
| 현행 OLAP | ② As-Is | — | WebSphere, SAP BO | IQ + Oracle BSA |

통일 서술 순서 (각 절): Context → 기능/서비스 → Application/Runtime → Data/I-F → Security/Operations.

---

# IV.1 BI포탈

**원문 작성 방향:** `[업무담당파트 자료 참조]`

## 1. 핵심 결론

`[FACT]` 운영 구성도 명칭: **BI포털**. 제품: **DataEye Portal(WEB)**, **JEUS**, **JDK**, **Linux**. 진입 **L4**. AP **Portal_AP1/AP2 Active-Active**. DB **Portal_DB1 Oracle Master / DB2 Standby**, 복제.

`[FACT]` 공유 Storage 2TB→**5TB 증설 요구**. 사유는 HA가 아니라 설치파일·매뉴얼·**데이터셋 누적 공유**(파일 최저 20~50GB 이상).

`[FACT]` 장표 6 소비: 계정/정보단말, **BI 포털 · 분석도구**, 데이터 가상화 JDBC.

`[GAP]` 업무담당 기능 목록, 리포트 카탈로그, SSO 연동 상세, 조회 SQL 소유권 없음.

NSIGHT 08-22의 신용실적·MSTR·NH Cloud는 **이 절 FACT가 아니다.**

## 2. 목적 / 범위 / 전제

포탈 사용자·조회 흐름·데이터소스·운영 HA. 비즈메타 논리 분리는 IV.2.

## 3. 상위 Text Architecture

```text
User (BI 사용자 · 정보포탈)
        │
        ▼
     [ L4 ]
        │
        ├──────────────┐
        ▼              ▼
 Portal_AP1 (A)   Portal_AP2 (A)
 DataEye Portal    DataEye Portal
 JEUS · JDK · Linux
        │     공유 Storage 2T→5T
        │     (설치파일·매뉴얼·데이터셋)
        └────────┬─────┘
                 ▼ JDBC/내부
        Portal_DB1 Active Oracle ──복제── DB2 Standby
                 │
                 ▼ (허브 조회 — 경로 TBD, 장표6 JDBC/가상화)
        RTW / ADW / BSA / 마트 / (As-Is) OLAP Target
```

**그림 해설:** 시작=사용자, 순서=L4→AP A-A→포탈DB 및/또는 허브, 책임=업무담당+TA, 종료=화면/리포트.

## 4. Context · 기능 · Runtime · Data · 보안운영

### Context / 사용자

장표·I.5: 정보포탈, BI 서비스 디자인 도구, 중앙 관리 콘솔은 **OLAP 장표** 사용자 칸에 있다. 포탈과 OLAP의 UI 경계는 `[TBD]`.

### 기능/서비스

업무담당 자료 없음. 장표에서 읽히는 것: WEB 포털, 데이터셋 공유(스토리지). 대시보드 메뉴 트리 창작 금지.

### Runtime `[FACT]`

| 항목 | 값 |
|------|-----|
| WEB/앱 | DataEye Portal |
| WAS | JEUS (버전 포탈 장표 미기재. 흐름관리는 8.5) |
| OS | Linux (버전 미기재) |
| HA AP | Active-Active |
| HA DB | Active-Standby + 복제 |
| 스펙 AP | 16C / 64GB / 공유스토리지 |
| 스펙 DB | 16C / 64GB / HDD 4TB |

### Data / I-F

- 포탈 자체 DB: Oracle Linux.
- 분석 소스: 장표 6 JDBC·가상화·허브. 포탈이 RDW만 쓰는지 ADW도 쓰는지는 **미기재**.
- 흐름관리 활용 칸에 **BI Portal** `[FACT]` → 계보 UI 연계 후보.

### Security / SSO

포탈 장표에 SSO 박스 없음. IV.5와 연결은 TBD. 마스킹은 III.7.

## 5. 구성요소 및 책임 표

| 구성 | 책임 | 태그 |
|------|------|------|
| DataEye Portal | WEB 포털 | 업무담당 |
| L4 | 부하분산 | TA |
| Portal DB | 포탈 메타/콘텐츠 `[ANALYSIS]` 용도 미기재 | DA/담당 |
| 공유 Storage | 데이터셋·매뉴얼 | 추진단 증설 FACT |
| 허브 Exa | 실적재 | DA, 조회 경로 TBD |

## 6. End-to-End

```text
[정상] 사용자 → L4 → AP1 또는 AP2 → (포탈DB 및/또는 JDBC 허브) → 화면
[AP 장애] A-A 남은 AP
[DB 장애] Standby 승격 [절차 TBD]
[스토리지] 데이터셋 증가 → 5TB 요구 (가용 이슈와 별개)
```

## 7. 설계 규칙

1. 포탈 AP를 A-S로 바꾸어 쓰지 않는다 (장표는 A-A).
2. 5TB를 HA 용량으로 설명하지 않는다.
3. NSIGHT 4영역(신용실적/Self-BI/OLAP)을 하나 포탈 내부 확정으로 넣지 않는다.

## 8. 제약 / 예외 / 장애 / 보안 / 운영

JEUS 버전 불일치 가능(포탈 vs 흐름 8.5). DR 없음. 데이터셋 공유는 보안·반출 통제 TBD.

## 9. Traceability

장표 2·6, 목차 IV.1, II.4.3 스펙, APP-BIP.

## 10. 확정 / 협의 / GAP / ADR

| ID | 유형 | 내용 |
|----|------|------|
| COL-IV.1-01 | 업무담당 | 기능, 권한, 리포트, 허브 조회 매트릭스 |
| COL-IV.1-02 | `[TA협의필요]` | JEUS 버전, DR |
| GAP-IV.1-01 | GAP | 담당 자료, SSO, 쿼리 소스 |
| ADR-BIP-01 | ADR 후보 | 포탈 조회 = 가상화 only vs 직접 JDBC |
| GAP-II.3.2-03 | 이어짐 | BIP vs BIZ 스택 |

## 11. 검증

- [ ] DataEye·JEUS·A-A/A-S가 장표와 같은가
- [ ] MSTR/NH Cloud를 FACT로 안 썼는가
- [ ] 5TB 사유가 데이터셋인가

## 12. 최종 평가

운영 **물리 골격**은 장표로 닫힌다. 업무 서비스 정의는 담당 자료 전 GAP이다.

---

# IV.2 BIZ메타

**원문 작성 방향:** `[업무담당파트 자료 참조]`

## 1. 핵심 결론

`[FACT]` 장표 2 제목에 **비즈메타**가 BI포털과 **함께** 있다. 별도 AP/DB 박스는 그 장표에 없다.

`[FACT]` 흐름관리 활용: **Biz Meta**. 수집 원천에 **IT 메타 DAMS**, 빅데이터 메타.

`[GAP]` 메타 유형(용어/모델/코드), 승인 워크플로, API, 포탈과 프로세스 분리 — 담당 자료 없음. 추정하지 않음.

## 2. 목적 / 범위 / 전제

메타 생성→저장→활용. 흐름관리(IV.4)는 이동 **계보**, 비즈메타는 **비즈니스 의미** 후보 — 역할분담은 자료 없이 단정 금지, 장표 이름만 병기.

## 3. 상위 Text Architecture

```text
Metadata Producer
  IT메타 DAMS · (업무 등록 TBD) · 빅데이터 메타
        │
        ▼
 Collection / Registration  [워크플로 TBD]
        │
        ▼
 Meta Repository
  후보 A: Portal Oracle 공유  [장표2 ANALYSIS]
  후보 B: 별도 DB             [자료 없음]
        │
        ▼
 Search / API / 포탈 화면  [TBD]
        │
        ▼
 Consumer: BI Portal · 데이터흐름(Q-Track) · DA/앱
```

**그림 해설:** 시작=생산자, 순서=수집→저장→검색, 책임=업무담당, 종료=소비자. 저장소 후보는 미결.

## 4. 관리대상 · 책임 · 변경

유형·승인·변경관리 원문 없음 → 표는 템플릿.

| 메타 유형 | Owner | 승인 | 연계 | 상태 |
|-----------|-------|------|------|------|
| `[TBD]` | | | BI / 흐름관리 / DAMS | 공란 |

DAMS는 원천이자 활용에 **IDAMS**로도 표기 `[FACT]` 장표7.

## 5. 구성요소 및 책임

| 구성 | FACT | 책임 |
|------|------|------|
| 비즈메타 | 장표 제목·활용 칸 | 업무담당 |
| DataEye 스택 | 포탈과 그림 공유 | 분리 TBD |
| DAMS | 메타 원천/활용 | DA |

## 6. End-to-End

```text
모델/용어 변경 → (등록 TBD) → Repository → 포탈·Q-Track 표시
실데이터 이동 ≠ 이 흐름 (IV.4 vs III.4)
```

## 7. 설계 규칙

비즈메타를 ETCL로 대체하지 않는다. 용어 표준 범위를 없으면 쓰지 않는다.

## 8. 제약 / 예외 / 장애 / 보안 / 운영

포탈 A-A를 메타 전용 HA로 단정하지 않음. 메타에 개인정보 포함 여부 TBD · III.7.

## 9. Traceability

장표 2·7, 목차 IV.2, APP-BIZ.

## 10. 확정 / 협의 / GAP / ADR

| ID | 유형 | 내용 |
|----|------|------|
| COL-IV.2-01 | 업무담당 | 메타 범위, 승인, API |
| GAP-IV.2-01 | GAP | 논리 분리, Repository |
| ADR-BIZ-01 | ADR 후보 | 포탈 DB 공유 vs 독립 |
| ADR-META-01 | ADR 후보 | 비즈메타 vs Q-Track vs DAMS 책임 |

## 11. 검증

- [ ] 메타 유형을 산업표준으로 채워 넣지 않았는가
- [ ] 장표에 없는 별도 서버를 확정하지 않았는가

## 12. 최종 평가

**이름과 연계 칸**만 FACT다. 메타 거버넌스 본문은 담당 게이트다.

---

# IV.3 Self BI

**원문 작성 방향:** `[업무담당파트 자료 참조]`

## 1. 핵심 결론

`[FACT]` 목차에 **Self BI**가 기타 시스템으로 있다. 솔루션 매핑 행 **OLAP & Self BI** (R34).

`[FACT]` 장표 6: **사용자분석환경**, **분석도구**. S10 사용자분석AP는 사진 오독 가능.

제품명(BI-Matrix 등)은 NSIGHT 장표 → 하나 확정 아님.

BI포탈과의 역할 중복은 담당 자료 없어 **비교표만 이슈로 제시**.

## 2. 목적 / 범위 / 전제

현업 탐색·분석. 대용량조회·다운로드·샌드박스는 자료 있을 때만 — 없음.

## 3. 상위 Text Architecture

```text
Analyst / User
        │
        ▼
 Self BI  [제품·WAS TBD]
        │
        ▼
 Analysis / Semantic Layer  [TBD]
        │
        ▼
 Data Access (JDBC / 가상화 TBD)
        │
        ▼
 Governed sources: ADW 마트 · 가상화 · (직접 허브는 P-06 검토)
```

**그림 해설:** 시작=분석가, 순서=도구→의미층→접근→소스, 책임=업무담당, 종료=시각화/추출. 중간 계층은 공란.

## 4. BI포탈과 경계 (이슈)

| 항목 | BI포탈 `[FACT]` | Self BI |
|------|-----------------|---------|
| 목차 | IV.1 | IV.3 별도 절 |
| 런타임 | DataEye+JEUS | 미기재 |
| 사용자 | BI/정보포탈 | 현업 `[ANALYSIS]` 목차 의도 |
| 부하 | 포탈 A-A | 격리 필요 여부는 NSIGHT 해석, 하나 미기재 |
| 데이터 | 허브·포탈DB | TBD |

## 5. 구성요소 및 책임

APP-SBI, LN-ANL-AP 후보, R34. Owner=업무담당.

## 6. End-to-End

```text
로그인 TBD → 탐색 → 쿼리 → 소스 → 결과
추출/다운로드 · 권한 · 쿼리 Timeout = 전부 TBD
```

OLAP(장표3)은 **현행 다차원 소비**이지 Self BI 제품과 동일하다고 단정하지 않음.

## 7. 설계 규칙

Self BI를 DataEye의 다른 이름이라고 쓰지 않는다 (자료 없음). 1.7 대용량조회와 도구를 자동 동일시하지 않음.

## 8. 제약 / 예외 / 장애 / 보안 / 운영

ADW 부하, 다운로드 개인정보 III.7. 자원 격리는 제안.

## 9. Traceability

목차 IV.3, 장표 6 분석도구, R34, TBD-I.5-02 S10.

## 10. 확정 / 협의 / GAP / ADR

| ID | 유형 | 내용 |
|----|------|------|
| COL-IV.3-01 | 업무담당 | 제품, 권한, 소스, 포탈 경계 |
| GAP-IV.3-01 | GAP | 기능·런타임 전무 |
| ADR-SBI-01 | ADR 후보 | Self BI = 별도 제품 vs 포탈 모듈 vs 현행 OLAP 승계 |
| ADR-OLAP-01 | ADR 후보 | SAP BO To-Be (유지/교체/SelfBI 통합) |

## 11. 검증

- [ ] BI-Matrix/MSTR를 하나 제품으로 안 썼는가
- [ ] 샌드박스를 지어내지 않았는가

## 12. 최종 평가

절은 목차로 **존재**하나, 아키텍처 본문은 담당 자료 없이는 위치만 표시하는 것이 맞다.

---

# IV.4 데이터흐름관리

**원문 작성 방향:** `[업무담당파트 자료 참조]`

## 1. 핵심 결론

`[FACT]` 정의 문구: 데이터를 생성하거나 추출·변환·적재를 통해 DB에 저장·가공하는 **일련의 데이터 변화 흐름을 분석하는 솔루션 영역**.

`[FACT]` 상세는 **데이터 아키텍처 영역 참조**.

`[FACT]` 수집 원천: IT 메타 DAMS, **Data Interface (ETCL, CDC, EAI, …)**, 형상관리(소스코드), 빅데이터 메타.

`[FACT]` 솔루션: AP#1 Active, AP#2 Standby, 공용 Storage, Oracle 19C #1/#2 Active-Standby. **Active-Active 미지원**이므로 AP도 A-S.

`[FACT]` 활용: DAMS(IDAMS) · BI Portal · Biz Meta · Q-Track.

`[FACT]` 스택: Q-Track 3.1, WebtoB, JEUS 8.5, JDK 11, RHEL 9(AP), Oracle 19C RHEL 8.6(DB). 개발/테스트는 단독·공유 NAS 없음.

실데이터 파이프(III.4 ETCL)와 **계층이 다르다** `[ANALYSIS]`.

## 2. 목적 / 범위 / 전제

계보·영향 분석 아키텍처. Lineage 등 영문 기능명은 장표에 없으면 확정하지 않음 — 「변화 흐름 분석」만 FACT.

## 3. 상위 Text Architecture

```text
Source Data (실이동은 III.4)
        │
        ▼
 Movement: ETCL / CDC / EAI / 형상 / 빅데이터메타 / DAMS
        │ 흐름 **정보** 수집
        ▼
┌── Flow Metadata / Management ──┐
│ Q-Track AP A-S + Oracle 19C A-S │
│ NAS SDS 2~4T (운영)              │
└──────────────┬─────────────────┘
               ▼
 Target 활용: DAMS · BI Portal · Biz Meta · Q-Track UI
               │
               ▼
 Impact / Trace Consumer (사용자)
```

**그림 해설:** 시작=원천·인터페이스·소스, 순서=메타 수집→Q-Track→활용 UI, 책임=업무담당+DA, 종료=계보 조회. 적재 성공이 이 절의 종료조건이 아님.

## 4. 실흐름 vs 관리정보

```text
[실데이터] 원천 → CDC/ETCL → RTW/ADW     ← III.4
[관리정보] 같은 수단의 메타 → Q-Track      ← IV.4
[의미메타] 용어/모델 → 비즈메타            ← IV.2  (분담 ADR-META-01)
```

BIZ메타와의 분담: 둘 다 활용 칸에 있음 `[FACT]`. 어느 것이 glossary인지는 미기재.

## 5. 구성요소 및 책임 · 스펙

운영 AP 24C/256G/2T ×2, DB 8C/512G/2T ×2. 개발·테스트 AP 16C/256G/2T, DB 8C/256G/1T. Host/IP 미확정.

## 6. End-to-End

```text
ETCL 잡 변경 → Data Interface 메타 수집 → Q-Track 적재 → BI/BizMeta/DAMS에서 흐름 조회
형상 소스 변경 → 프로그램 계보 후보 [장표 원천에 형상 있음, 화면 기능 TBD]
장애: AP A-S 페일오버. 제품 A-A 없음.
```

## 7. 설계 규칙

Q-Track을 ETL 엔진으로 부르지 않는다. 흐름관리 DB를 ADW와 동일시하지 않는다.

## 8. 제약 / 예외 / 장애 / 보안 / 운영

A-S만. 개발에 NAS 없음. DR 장표 없음. 메타에 쿼리/개인정보 포함 TBD.

## 9. Traceability

장표 7·8, 목차 IV.4, II.4.3, APP-DFM.

## 10. 확정 / 협의 / GAP / ADR

| ID | 유형 | 내용 |
|----|------|------|
| COL-IV.4-01 | 업무담당·DA | DA영역 상세, 화면 기능, DAMS 연동 |
| GAP-IV.4-01 | GAP | Host, DR, lineage 기능명 |
| ADR-META-01 | 이어짐 | vs 비즈메타 |

## 11. 검증

- [ ] A-A로 쓰지 않았는가
- [ ] Q-Track을 TeraStream과 혼동하지 않았는가
- [ ] 스펙이 장표 8과 같은가

## 12. 최종 평가

IV 가운데 **물리·제품·HA가 가장 닫힌 절**이다. 업무 화면·DA 상세는 참조 지시만 있다.

---

# IV.5 비표준단말 SSO/EAM 연계 방안

**원문 작성 방향:** AS-IS 담당자 문의 필요

## 1. 핵심 결론

`[FACT]` 할 일: **비표준단말**에 대한 **SSO/EAM 연계 방안** 정의. **AS-IS 담당자 문의 필요**.

표준 정보단말·계정단말 Runtime은 I.5/III.1.1 (Neoworks 직접 / EIC·MCA). 비표준의 범위·제품·AS-IS 흐름은 **없음**.

JWT 등 다이어리 일반 자료는 하나 AS-IS가 아님.

## 2. 목적 / 범위 / 전제

비표준 단말 ↔ 은행 인증·권한. 담당 인터뷰 전 TO-BE를 단정하지 않음.

## 3. 상위 Text Architecture

```text
[표준] 정보단말 ──► Neoworks
      계정단말 ──EIC/MCA──► Neoworks

[비표준 단말] ──?──► SSO ──?──► EAM ──?──► 업무앱
                 │
                 └─ AS-IS 경로 [문의]
                 └─ TO-BE 경로 [문의 후]

IV.1 포탈 SSO 박스는 하나 장표에 없음 → 포탈도 문의 대상일 수 있음 [TBD]
```

**그림 해설:** 시작=비표준 사용자, 순서=미확정 인증 체인, 책임=AS-IS 담당·보안, 종료=인가된 앱. 지금은 물음표가 정답.

## 4. AS-IS / TO-BE (칸)

| 항목 | AS-IS | TO-BE |
|------|-------|-------|
| 비표준 단말 목록 | 문의 | |
| SSO 제품 | 문의 | |
| EAM 제품 | 문의 | |
| 프로토콜 | 문의 | 창작 금지 |
| 표준단말과 차이 | 문의 | |
| 예외 승인 | P-10 제안 | ADR |

## 5. 구성요소 및 책임

| 구성 | 책임 |
|------|------|
| 비표준 단말 | 미식별 |
| SSO / EAM | 문의 |
| 표준 단말 경로 | III.1.1 유지 (P-01) |

매핑 R08 IM, R07 Control Minder — **이 절 제품 확정에 쓰지 않음** (교점 미판독).

## 6. End-to-End

문의 전 시퀀스 확정 금지. 실패 시 로컬 계정 우회는 보안 ADR.

## 7. 설계 규칙

표준 정보단말 경로를 SSO 없음을 이유로 바꾸지 않는다. 비표준을 표준으로 재정의하지 않음.

## 8. 제약 / 예외 / 장애 / 보안 / 운영

보안팀 III.7과 교차. 장애 시 인증 단일점 TBD.

## 9. Traceability

목차 IV.5, I.5 점선 액터, P-09.

## 10. 확정 / 협의 / GAP / ADR

| ID | 유형 | 내용 |
|----|------|------|
| COL-IV.5-01 | AS-IS 담당 | 단말 목록, SSO/EAM AS-IS |
| COL-IV.5-02 | `[은행보안팀 협업필요]` | TO-BE 허용 |
| GAP-IV.5-01 | GAP | 전 구간 |
| ADR-SSO-01 | ADR 후보 | 비표준 잔존 vs 표준단말 흡수 |

## 11. 검증

- [ ] SSO 제품을 인벤토리에서 집어 확정하지 않았는가
- [ ] 표준 Runtime을 덮어쓰지 않았는가

## 12. 최종 평가

이 절의 성실한 산출은 **질문 목록**이다. 방안 본문은 문의 기록 뒤에 쓴다.

---

# IV. 장 단위 롤업

## 확정사항

1. BI포탈 운영: DataEye, JEUS, L4, AP A-A, DB A-S, Storage 증설 사유=데이터셋.
2. 비즈메타는 포탈 장표·흐름관리 활용 칸에 존재. 스택 공유는 미결.
3. Self BI는 목차·TRM 행으로 존재. 제품 미정.
4. 흐름관리: Q-Track 3.1, A-S, 원천 4종, 활용 4종, 실적재와 분리.
5. 현행 OLAP: WebSphere + SAP BO + IQ/Oracle (As-Is 소비).
6. 비표준 SSO/EAM은 문의 전 공란.

## 협의필요

업무담당 IV.1~4, AS-IS 담당 IV.5, DA(조회 매트릭스·DAMS), TA(JEUS/DR), 보안(SSO TO-BE).

## GAP / ADR

| ID | 내용 |
|----|------|
| GAP-IV.1~3 | 담당 기능 자료 |
| GAP-IV.5-01 | SSO AS-IS |
| ADR-BIP-01 | 포탈 조회 경로 |
| ADR-BIZ-01 | 메타 DB 독립 |
| ADR-META-01 | 메타 vs 계보 책임 |
| ADR-SBI-01 · ADR-OLAP-01 | Self BI / BO 목표 |
| ADR-SSO-01 | 비표준 단말 |

## I~IV 초안 상태

| 장 | 파일 | 상태 |
|----|------|------|
| I | `…_I_개요.md` | 초안 |
| II | `…_II_시스템구성.md` | 초안 |
| III | `…_III_마케팅플랫폼_데이터허브.md` | 초안 |
| IV | 본 파일 | 초안 |

목차 본편은 여기까지다. 이후는 기작성·담당·2사업·FW 원문 입수 후 GAP를 닫거나, 장별 리뷰다.
