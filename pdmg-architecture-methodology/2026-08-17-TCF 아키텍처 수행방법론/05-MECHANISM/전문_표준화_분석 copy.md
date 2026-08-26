# 전문 표준화 — 이미지 분석

> **원본 이미지**: [`../24-전문_표준화.jpg`](../24-전문_표준화.jpg)  
> **문서 출처**: `8. 아키텍처 표준화 > 8.2 전문 표준화 정의` (농협중앙회 차세대 정보계)  
> **분석 일자**: 2026-08-22  
> **분석 원칙**: 이미지에서 **직접 확인 가능한 사실**과 **구조 해석**을 분리한다.  
> **관련 Evidence**: [`85631/24_표준전문_처리_정의.md`](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/NSIGHT_아키텍처_정의서_이미지별_TEXT추출_분석_MD/85631/markdown/24_표준전문_처리_정의.md) (OCR·TEXT 추출)

---

## 1. 한 줄 요약

차세대 정보계는 **표준전문**을 기반으로 온라인 거래를 처리하며, **채널·채널통합·업무 AP·대내통합·연계 업무** 전 구간에 동일 원칙을 적용한다. **업무 솔루션 Package UI → BI포탈** 경로만 **비표준(json/http)** 예외이고, **MCA(FLAT)·MCI·API G/W(CruzAPIM)·GSE(FLAT)** 가 연계 게이트웨이 역할을 한다.

---

## 2. 이미지 식별 정보

| 항목 | 내용 |
|---|---|
| 제목 | 전문 표준화 |
| 상위 장 | 8. 아키텍처 표준화 — **8.2 전문 표준화 정의** |
| 좌측 | **표준전문 적용 범위** — 5구간 흐름도 |
| 우측 | **표준화 내용** — 연계별 규칙·예시 |
| 핵심 원칙 | 차세대 정보계 업무 시스템은 **표준전문** 기반 온라인 거래 · 연계 시스템 간 **동일 적용** · **업무 솔루션 Package UI 예외(개별 처리)** |
| Evidence Key | `85631/24.png` |

---

## 3. 표준전문 적용 범위 TEXT 표그림

원본 이미지의 **좌(5구간 흐름도)·우(표준화 내용)** 배치를 TEXT 표·ASCII로 변환한 것이다.

### 3.1 5구간 아키텍처

| 구간 | 명칭 | 주요 구성요소 |
|---|---|---|
| **1** | 채널 | **대내**: 통합업무시스템, 정보단말, Package UI · **대고객**: 비대면 채널 |
| **2** | 채널 통합 | **영업점 MCA**, **MCI** |
| **3** | 업무 시스템 (차세대 정보계) | **마케팅플랫폼** 온라인 AP · **BI포탈** 온라인 AP |
| **4** | 대내 통합 | **API G/W (CruzAPIM)**, **GSE** |
| **5** | 연계 업무 | 계정계 · 정보계 · e-Banking · Card · 은행(법인) 등 |

### 3.2 거래 경로 · 프로토콜 표

| No | 출발 | 경유 | 도착 | 포맷 | 표준/비표준 |
|---:|---|---|---|---|---|
| **P1** | 통합업무시스템 | **영업점 MCA** | 마케팅플랫폼 AP | MCA 구간 **FLAT** → AP **json/http** | **표준전문** |
| **P2** | 정보단말 | — | 마케팅플랫폼 AP | **json/http** | **표준전문** |
| **P3** | 비대면 채널 | **MCI** | 마케팅플랫폼 / BI포탈 AP | **json/http** | **표준전문** |
| **P4** | 차세대 정보계 AP | **API G/W (CruzAPIM)** | 대내 업무 AP (계정계·정보계 등) | **json/http** | **표준전문** (IN/OUT-bound) |
| **P5** | 차세대 정보계 AP | **GSE** | 은행(법인) | **FLAT** | **표준전문** |
| **P6** | Package UI | — | BI포탈 | **json/http** | **비표준** (개별 처리) |

### 3.3 좌(흐름도) · 우(표준화 내용) 배치 표

| 구역 | 표준전문 적용 범위 (좌) | 표준화 내용 (우) |
|---|---|---|
| 상단 | 차세대 정보계 = 표준전문 기반 온라인 · Package UI 예외 | 차세대 정보계는 **표준전문을 적용** (업무 솔루션 Package UI 제외) |
| 대내·정보단말 | 정보단말 → 마케팅플랫폼 AP (json/http) | **정보단말 거래** — 모두 표준전문 기반 |
| MCA | 통합업무시스템 → **영업점 MCA** → 차세대 정보계 AP | MCA 연계: 통합업무 → MCA → 차세대 AP · 예: **마케팅플랫폼 싱글뷰** |
| MCI | 비대면 채널 → **MCI** → 차세대 정보계 AP | MCI 연계: 비대면 → MCI → 차세대 AP · 예: **영업점 방문 예약**, **실시간 이용 내역 조회** |
| API G/W | 차세대 AP ↔ **CruzAPIM** ↔ 대내 업무 AP | 차세대 AP ↔ 대내 업무 AP **IN-bound / OUT-bound** · APIG/W 단독 또는 **API G/W ↔ EAI** 경유 |
| GSE | 차세대 AP ↔ **GSE** ↔ 은행(법인) | GSE 연계: 은행(법인) **클릭폰** 업무 요청 |
| Package | Package UI → BI포탈 (json/http) | **비표준** — 업무 솔루션 Package UI는 **개별 처리** |

### 3.4 ASCII Big Picture

```text
┌─────────────────────── 1. 채널 ───────────────────────┐
│  [대내] 통합업무시스템 │ 정보단말 │ Package UI          │
│  [대고객] 비대면 채널                                   │
└────────────┬───────────────────────┬────────────────────┘
             │ FLAT                  │ json/http (비표준)
             ▼                       ▼
┌──────── 2. 채널 통합 ────────┐   (Package→BI 직결)
│  영업점 MCA    │    MCI      │
└───────┬────────┴──────┬──────┘
        │ json/http     │ json/http
        ▼               ▼
┌──────────── 3. 업무 시스템 (차세대 정보계) ─────────────┐
│  마케팅플랫폼 온라인 AP  │  BI포탈 온라인 AP           │
│         표준전문 (json/http) 기반 처리                 │
└────────────┬────────────────────────┬───────────────────┘
             │ json/http             │ FLAT
             ▼                       ▼
┌──────── 4. 대내 통합 ─────────────────────────────────┐
│  API G/W (CruzAPIM)              │  GSE               │
└────────────┬─────────────────────┴──────────┬─────────┘
             │                                │
             ▼                                ▼
┌──────── 5. 연계 업무 ─────────────────────────────────┐
│  계정계 │ 정보계 │ e-Banking │ Card │ 은행(법인)      │
└───────────────────────────────────────────────────────┘
```

### 3.5 End-to-End Flow

| Flow ID | 흐름 | 경로 | 비고 |
|---|---|---|---|
| **F01** | 영업점 통합업무 | 통합업무 → MCA(FLAT) → MP AP(json/http) | 싱글뷰 등 |
| **F02** | 정보단말 | 정보단말 → MP AP(json/http) | 전 거래 표준전문 |
| **F03** | 대고객 비대면 | 비대면 → MCI → MP/BI AP(json/http) | 방문예약·이용내역 등 |
| **F04** | 대내 연계 OUT | 차세대 AP → CruzAPIM → 대내 AP | 단독 또는 EAI 경유 |
| **F05** | 대내 연계 IN | 대내 AP → CruzAPIM → 차세대 AP | IN-bound |
| **F06** | 법인 클릭폰 | 차세대 AP ↔ GSE(FLAT) ↔ 은행(법인) | |
| **F07** | Package 예외 | Package UI → BI포탈(json/http) | **비표준** · 개별 처리 |

---

## 4. 표준화 내용 상세

### 4.1 적용 원칙

| 항목 | 내용 |
|---|---|
| 기본 | 차세대 정보계 업무 시스템 = **표준전문** 기반 온라인 거래 |
| 범위 | 각 **연계 시스템 간 거래**에 **동일하게** 적용 |
| 예외 | **업무 솔루션 Package UI** — **개별 처리** (표준전문 미적용) |

### 4.2 연계 게이트웨이별 규칙

| 게이트웨이 | 방향 | 포맷 | 역할·예시 |
|---|---|---|---|
| **정보단말** | 단말 → MP AP | json/http | 모든 정보단말 거래 = 표준전문 |
| **영업점 MCA** | 통합업무 → MCA → 차세대 AP | FLAT → json/http | 마케팅플랫폼 **싱글뷰** 거래 |
| **MCI** | 비대면 → MCI → 차세대 AP | json/http | **영업점 방문 예약**, **실시간 이용 내역 조회** |
| **API G/W (CruzAPIM)** | 차세대 AP ↔ 대내 업무 AP | json/http | **IN-bound / OUT-bound** · 단독 또는 **EAI 경유** |
| **GSE** | 차세대 AP ↔ 은행(법인) | FLAT | **클릭폰** 업무 요청 |

### 4.3 표준 vs 비표준 구분

| 구분 | 경로 | 처리 방식 |
|---|---|---|
| **표준전문** | P1~P5 (F01~F06) | 공통 Header/Envelope · ServiceId · GUID · 업무 DTO 분리 |
| **비표준** | P6 Package UI → BI (F07) | json/http이나 **Package UI 전용** 개별 규격 · 표준전문 미강제 |

---

## 5. 아키텍처 관점 핵심 정리

| 관점 | 확인되는 구조 |
|---|---|
| Message Standard | 차세대 정보계 온라인 = **표준전문** 공통 계약 |
| Channel Layer | 대내(통합업무·정보단말·Package) · 대고객(비대면) |
| Integration Hub | MCA · MCI · CruzAPIM · GSE — **채널·대내·법인** 경계 |
| Wire Format | **json/http** (정보계 내부·MCI·CruzAPIM) · **FLAT** (MCA·GSE·레거시) |
| Exception Policy | **Package UI → BI** 만 비표준 · 나머지 전 구간 표준 |
| Linked Systems | 계정계 · 정보계 · e-Banking · Card · 은행(법인) |

---

## 6. PDMG/TCF · Protocol 매핑

장표의 **표준전문**은 PDMG/TCF에서 **HTTP/JSON Envelope(`hdr_nhnis` + `dto`)** 로 구현된다.

| 장표 개념 | PDMG/TCF 대응 | 비고 |
|---|---|---|
| 표준전문 | `hdr_nhnis.sys_comm` + `dto` | [PROTOCOL-STANDARD](../../2026-08-17-TCF%20아키텍처%20수행방법론/05-MECHANISM/PROTOCOL-STANDARD.md) |
| 공통 Header | GUID(`std_gbl_id`), ServiceId(`rms_svc_c`), 채널·점·단말·사용자 | Filter → `ServiceContext` |
| 업무 Data | `dto` (Business DTO) | Controller/Handler 이후 전달 |
| json/http 경로 | DefaultFilter → `OnlineTransactionController` → `TcfFacade` | P2·P3·P4 |
| ServiceId 라우팅 | URL `{서비스코드}` → Dispatcher → Handler | [도메인_정의_거래_처리_경로_분석](./도메인_정의_거래_처리_경로_분석.md) |
| FLAT 경로 (MCA/GSE) | Adapter/변환 계층 (장표상) | FLAT ↔ json 변환 — **구현 세부는 별도 연계 규격** |
| Package UI 예외 | BI포탈 Package URL (`pt`/`sb`/`oa`) | F07 · 표준전문 미강제 |

```text
[표준전문 → TCF 실행선 — json/http 경로 (P2~P4)]

Client / MCI / CruzAPIM
  │  HTTP POST json/http
  │  { hdr_nhnis: { sys_comm: { std_gbl_id, rms_svc_c, ... } }, dto: { ... } }
  ▼
DefaultFilter  →  ServiceContext (공통 Header)
  ▼
OnlineTransactionController  (ServiceId)
  ▼
TcfFacade.process(serviceId)
  → stf.preProcess() → Handler → Facade → Service → dto
  → etf.postProcess()
  ▼
표준 응답 전문 (Header + dto)
```

> **Gap**: MCA/GSE **FLAT ↔ json** 변환기·스키마 Mapping은 본 장표에 **미기재** — 연계 인터페이스 정의서·계정계 Mapping 문서와 대조 필요.

---

## 7. 인접 장표와의 관계

| 장표 | 관계 |
|---|---|
| [도메인_정의_거래_처리_경로_분석](./도메인_정의_거래_처리_경로_분석.md) (18~23) | **어디로**(URL/도메인) vs 본 장표 **무엇으로**(표준전문/포맷) |
| [거래_처리_구조_분석](./거래_처리_구조_분석.md) (15) | AP **내부** 8단계 vs 본 장표 **AP 간** 메시지 표준 |
| [전체_시스템_아키텍처_구조_정의_분석](./전체_시스템_아키텍처_구조_정의_분석.md) (34) | MCA·MCI·CruzAPIM·GSE 상위 배치 |
| 85631/25 GUID 관리 | 표준전문 **추적성** (std_gbl_id) |
| 85631/26 캐릭터셋 | 표준전문 **데이터 표준** |

---

## 8. 판독 제한·검증 필요 항목

| 항목 | 상태 |
|---|---|
| FLAT 전문 스키마·길이·필드 | 장표는 **FLAT** 명시만 — 세부 스키마 미기재 |
| MCA vs MCI 역할 중복 범위 | 대내(MCA) vs 대고객(MCI) — 본 장표 기준 분리 |
| CruzAPIM ↔ EAI 경유 조건 | "단독 또는 EAI 경유" — **선택 기준** 미기재 |
| Package UI "개별 처리" 규격 | json/http이나 **표준전문 Header 미적용** 여부 — BI Package 연계 규격 확인 |
| OCR `저리`/`UE`/`1464` | **처리** / **UI** / **MCA** 로 해석 (85631 Evidence) |

---

## 9. 교차 참조

| 문서 | 역할 |
|---|---|
| [85631/24_표준전문_처리_정의.md](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/NSIGHT_아키텍처_정의서_이미지별_TEXT추출_분석_MD/85631/markdown/24_표준전문_처리_정의.md) | OCR·Evidence SSOT |
| [PROTOCOL-STANDARD.md](../../2026-08-17-TCF%20아키텍처%20수행방법론/05-MECHANISM/PROTOCOL-STANDARD.md) | HTTP/JSON·Header·ServiceId 규격 |
| [표준전문 + Context 아키텍처 구조.md](../../2026-08-17-알아야%20되는%20아키텍처/표준전문%20+%20Context%20아키텍처%20구조.md) | hdr_nhnis · ServiceContext · TCF 실행선 |
| [INTEGRATION-STANDARD.md](../../2026-08-17-TCF%20아키텍처%20수행방법론/05-MECHANISM/INTEGRATION-STANDARD.md) | CruzAPIM·연계 통제 |
| [도메인_정의_거래_처리_경로_분석.md](./도메인_정의_거래_처리_경로_분석.md) | URL·ServiceID·WAR 라우팅 |

---

*본 문서는 2026-08-22 제공 원본 이미지(85631/24.png 동일 장표)를 기준으로 작성되었다. FLAT 연계·Package 예외 규격은 연계 정의서를 우선한다.*
