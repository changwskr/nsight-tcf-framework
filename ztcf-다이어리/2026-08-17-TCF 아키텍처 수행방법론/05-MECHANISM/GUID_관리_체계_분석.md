# GUID 관리 체계 — 이미지 분석

> **원본 이미지**: [`../25-GUID_관리_체계.jpg`](../25-GUID_관리_체계.jpg)  
> **문서 출처**: `8. 아키텍처 표준화 > 8.3 GUID 관리 체계 정의` (농협중앙회 차세대 정보계)  
> **분석 일자**: 2026-08-22  
> **분석 원칙**: 이미지에서 **직접 확인 가능한 사실**과 **구조 해석**을 분리한다.  
> **관련 Evidence**: [`85631/25_GUID_관리_체계_정의.md`](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/NSIGHT_아키텍처_정의서_이미지별_TEXT추출_분석_MD/85631/markdown/25_GUID_관리_체계_정의.md) (OCR·TEXT 추출)

---

## 1. 한 줄 요약

표준전문 내 **GUID**를 노드 간 **Correlation Key**로 사용해 **End-to-End 거래 추적**을 구현한다. **채널(1) → 채널통합(2) → 차세대 정보계(3) → 연계(4) → 복귀(5~7)** 구간마다 GUID **진행번호를 +1** 증가시키고, 각 시스템 로그의 **원거래글로벌ID**로 동일 거래를 조회한다. **Package UI**는 전문 표준화(24번)와 동일하게 예외.

---

## 2. 이미지 식별 정보

| 항목 | 내용 |
|---|---|
| 제목 | GUID 관리 체계 정의 |
| 상위 장 | 8. 아키텍처 표준화 — **8.3 GUID 관리 체계 정의** |
| 좌측 | **GUID 적용 범위** — 5구간 U자형 왕복 흐름도 (진행번호 1~7) |
| 우측 | **표준화 내용** — GUID 생성·증분·로그 규칙 5항 |
| 핵심 원칙 | 표준전문 기반 온라인 처리 + **GUID 노드 간 진행번호 증가** → E2E 추적 |
| Evidence Key | `85631/25.png` |

---

## 3. GUID 적용 범위 TEXT 표그림

원본 이미지의 **좌(왕복 흐름도)·우(표준화 내용)** 배치를 TEXT 표·ASCII로 변환한 것이다. 5구간 구조는 [전문_표준화_분석](./전문_표준화_분석.md)과 동일하다.

### 3.1 5구간 · GUID 진행번호 (왕복)

| Step | 구간 | 노드 | GUID 진행번호 | 방향 |
|---:|---|---|---:|---|
| **1** | 채널 | 정보단말 · Package UI · 비대면 등 | **1** | 요청 출발 |
| **2** | 채널 통합 | MCA · MCI | **2** | → |
| **3** | 업무 시스템 | 차세대 정보계 AP (마케팅플랫폼 · BI포탈) | **3** | → |
| **4** | 연계 업무 | 계정계 · 정보계 · e-Banking · Card · 은행(법인) | **4** | → (최원) |
| **5** | 업무 시스템 | 차세대 정보계 AP | **5** | ← 복귀 |
| **6** | 채널 통합 | MCA · MCI | **6** | ← |
| **7** | 채널 | 최종 응답 | **7** | ← 완료 |

> **해석**: 진행번호는 GUID 내부(또는 GUID 연동 필드)의 **노드 통과 순번**이다. 시스템 노드를 경유할 때마다 **+1** 증가 후 다음 노드로 전달한다.

### 3.2 5구간 구성요소 (24번 장표와 동일)

| 구간 | 명칭 | 주요 구성요소 |
|---|---|---|
| **1** | 채널 | 대내(통합업무·정보단말·Package UI) · 대고객(비대면) |
| **2** | 채널 통합 | 영업점 MCA · MCI |
| **3** | 업무 시스템 | 마케팅플랫폼 · BI포탈 온라인 AP |
| **4** | 대내 통합 | API G/W (CruzAPIM) · GSE |
| **5** | 연계 업무 | 계정계 · 정보계 · e-Banking · Card · 은행(법인) |

### 3.3 좌(흐름도) · 우(표준화 내용) 배치 표

| 구역 | GUID 적용 범위 (좌) | 표준화 내용 (우) |
|---|---|---|
| 상단 | 표준전문 기반 온라인 + GUID 노드·진행번호 → **E2E 추적** | 차세대 정보계는 표준전문 내 **GUID**를 노드 간 식별자로 사용 (Package UI 제외) |
| 채널 | Step 1 (진행번호 1) · Step 7 (7) | **채널 시스템**이 최초 거래 시작 시 표준전문 GUID 항목을 **규칙에 따라 설정** |
| 단말·FW | 정보단말 · 온라인 AP | **WebTopSuit**(정보단말) · **NH Cloud Framework**(업무 FW)가 GUID 생성 규칙에 따라 처리 구현 |
| 노드 경유 | Step 2~6 (2~6) | 시스템 **노드 경유 시** 표준전문 GUID 내 **진행상황(진행번호) +1** 후 다음 노드 전달 |
| 로그 | 전 구간 | 각 시스템 로그 **원거래글로벌ID** 항목 = 거래 **고유 식별자** → 조회·추적 |
| 예외 | Package UI → BI | 업무 솔루션 Package UI — 전문 표준화(24번)와 동일 **개별 처리** |

### 3.4 ASCII Big Picture (E2E 왕복)

```text
                    ┌── Step 1: 채널 (진행번호 1) ──┐
                    │  정보단말 / Package / 비대면   │
                    └──────────────┬────────────────┘
                                   ▼
                    ┌── Step 2: 채널통합 (2) ───────┐
                    │  MCA  │  MCI                  │
                    └──────────────┬────────────────┘
                                   ▼
                    ┌── Step 3: 차세대 정보계 (3) ───┐
                    │  MP AP  │  BI AP               │
                    │  (NH Cloud Framework)          │
                    └──────────────┬────────────────┘
                                   ▼
              ┌── Step 4: 연계 (4) ─────────────────┐
              │  CruzAPIM → 계정계·정보계·Card …     │
              │  GSE → 은행(법인)                    │
              └──────────────┬──────────────────────┘
                             │  (응답 복귀)
                             ▼
                    Step 5: 정보계 (5) → Step 6: 통합 (6) → Step 7: 채널 (7)

  [GUID 규칙]  노드 통과마다 진행번호 +1  │  로그 = 원거래글로벌ID 로 E2E 조회
```

### 3.5 End-to-End Flow

| Flow ID | 흐름 | GUID 동작 |
|---|---|---|
| **F01** | 정보단말 → MP AP | 채널(1) → … → AP(3) · 진행번호 순차 증가 |
| **F02** | 통합업무 → MCA → MP | MCA(2) 경유 시 +1 |
| **F03** | 비대면 → MCI → MP/BI | MCI(2) 경유 |
| **F04** | MP AP → CruzAPIM → 계정계 | OUT(3→4) · IN 응답(4→5) |
| **F05** | MP AP ↔ GSE ↔ 법인 | FLAT 구간도 GUID 유지·증분 |
| **F06** | 왕복 완료 | Step 7까지 진행번호 증가 후 채널 응답 |
| **F07** | Package UI → BI | **GUID 표준 미적용** (예외) |

---

## 4. 표준화 내용 (5대 규칙)

| No | 규칙 | 내용 |
|---:|---|---|
| **R1** | 노드 식별 | 차세대 정보계는 표준전문 내 **GUID**를 **시스템 노드 간 식별자**로 사용 (Package UI 제외) |
| **R2** | 최초 생성 | **채널 시스템**이 최초 거래 시작 시 표준전문 GUID 항목을 **규칙에 따라 설정** |
| **R3** | 구현 주체 | **WebTopSuit**(정보단말) · **NH Cloud Framework**(업무)가 GUID **생성 규칙**에 따라 처리 구현 |
| **R4** | 진행번호 증분 | 시스템 **노드 경유 시** GUID 내 **진행상황(진행번호) +1** → 다음 노드 전달 |
| **R5** | 로그 추적 | 각 시스템 로그 **원거래글로벌ID** = 거래 **고유 식별자** → **조회·추적** |

---

## 5. 아키텍처 관점 핵심 정리

| 관점 | 확인되는 구조 |
|---|---|
| Correlation | GUID = **End-to-End Correlation Key** (전문·로그·연계 호출 연결) |
| Hop Counter | 노드 통과마다 **진행번호 +1** (왕복 7단계 모델) |
| Origin ID | **원거래글로벌ID** = 로그 검색·추적 키 |
| Creation Point | **채널**이 최초 GUID 설정 · **WebTopSuit / NH Cloud FW** 구현 |
| Scope | [전문_표준화](./전문_표준화_분석.md)와 **동일 5구간** · Package UI 예외 |
| Trace Model | 요청·응답 **왕복** 전 구간 단일 거래로 추적 |

---

## 6. PDMG/TCF · sys_comm 매핑

장표 GUID 개념과 PDMG `hdr_nhnis.sys_comm` 필드의 **대응 관계**다.

| 장표 개념 | sys_comm 필드 | PDMG/TCF 구현 | 비고 |
|---|---|---|---|
| GUID (거래 식별) | `std_gbl_id` | `ServiceContext.guid` = MDC `guid` = ImageLog 키 | Interceptor **강제 채번** 가능 |
| 원거래글로벌ID | `orgtr_gbl_id` | 연계·원거래 추적 (선택) | R5 로그 조회 키 후보 |
| 거래 GUID (부가) | `trz_gbl_id` | 선택 필드 | 장표 미명시 |
| 진행번호 / 진행상황 | *(장표: GUID 내부)* | **별도 필드·자동 +1 미구현** | **Gap** — 아래 §8 |
| GUID 생성 (R2·R3) | — | `ServicePreventionInterceptor.ensureGuid()` | 클라이언트 미전달 시 UUID 채번 |
| 로그 추적 (R5) | `std_gbl_id` | `TB_FW_IMAGE_LOG.GUID` · Request/Response ImageLog | PRE/POST/Exception |

```text
[GUID → TCF 추적선 — json/http AP 구간 (Step 3)]

Client / WebTopSuit
  │  hdr_nhnis.sys_comm.std_gbl_id  (채널 최초 설정, R2)
  ▼
DefaultFilter  →  ServiceContext.guid
  ▼
ServicePreventionInterceptor.ensureGuid()
  │  (없으면 UUID 채번 · Header·MDC 동기화)
  ▼
TcfFacade / Handler / Service  …  업무 처리
  ▼
ImageLog (PRE/POST)  →  TB_FW_IMAGE_LOG  keyed by GUID
  ▼
응답 hdr_nhnis.sys_comm.std_gbl_id  (동일 GUID 유지)
```

**3값 일치 원칙** (PDMG Baseline):

```text
ServiceContext.guid  =  hdr_nhnis.sys_comm.std_gbl_id  =  MDC["guid"]
```

> **Gap (R4)**: 장표의 **노드 경유 시 진행번호 +1** 은 MCA/MCI/CruzAPIM/GSE **연계 어댑터** 책임으로 해석되나, `pdmg-fw` Core에는 **자동 hop increment API가 없음**. GUID 문자열 내 embedded 진행번호 vs 별도 필드 — **스키마 정의서(85631/25 외)** 와 대조 필요.

---

## 7. 인접 장표와의 관계

| 장표 | 관계 |
|---|---|
| [전문_표준화_분석](./전문_표준화_분석.md) (24) | **표준전문** Envelope 위에 GUID가 실림 · 동일 5구간·Package 예외 |
| [거래_처리_구조_분석](./거래_처리_구조_분석.md) (15) | AP **내부** ① 입력로그·⑧ 출력로그 ↔ GUID ImageLog |
| [전체_시스템_아키텍처_구조_정의_분석](./전체_시스템_아키텍처_구조_정의_분석.md) (34) | MCA·MCI·CruzAPIM·GSE = GUID hop **노드** |
| 85631/26 캐릭터셋 | 표준전문 **데이터 표준** (GUID 인코딩과 별도) |
| [PROTOCOL-STANDARD](../../2026-08-17-TCF%20아키텍처%20수행방법론/05-MECHANISM/PROTOCOL-STANDARD.md) | Trace ID/GUID **모든 거래 필수** |

---

## 8. 판독 제한·검증 필요 항목

| 항목 | 상태 |
|---|---|
| GUID **문자열 포맷** (진행번호 위치·자릿수) | 장표는 **+1 규칙**만 — 포맷 스키마 미기재 |
| `진행번호` vs `진행상황` vs `std_tgrm_lclc` | 장표=진행상황/진행번호 · `std_tgrm_lclc`=로케일(`KO`) — **별개 필드** |
| MCA/MCI/GSE hop increment **구현 주체** | 연계 Middleware vs AP Framework — **책임 분리 미명시** |
| Package UI 예외 시 GUID | BI Package 경로 GUID 정책 — **별도 규격** |
| `orgtr_gbl_id` vs `std_gbl_id` | 연계 OUT 시 orgtr=원거래? — **Mapping 정의서** 확인 |
| OCR `제례`/`저리`/`SWS` | **체계** / **처리** / **순** 번호 로 해석 |

---

## 9. 교차 참조

| 문서 | 역할 |
|---|---|
| [85631/25_GUID_관리_체계_정의.md](../../../pdmg-architecture-methodology/2026-08-17-TCF%20아키텍처%20수행방법론/NSIGHT_아키텍처_정의서_이미지별_분석_MD/NSIGHT_아키텍처_정의서_이미지별_TEXT추출_분석_MD/85631/markdown/25_GUID_관리_체계_정의.md) | OCR·Evidence SSOT |
| [전문_표준화_분석.md](./전문_표준화_분석.md) | 표준전문 적용 범위 (선행 장표) |
| [표준전문 + Context 아키텍처 구조.md](../../2026-08-17-알아야%20되는%20아키텍처/표준전문%20+%20Context%20아키텍처%20구조.md) | GUID ↔ ServiceContext ↔ MDC ↔ ImageLog |
| [전문 아키텍처 구조.md](../../2026-08-17-알아야%20되는%20아키텍처/전문%20아키텍처%20구조.md) | hdr_nhnis · sys_comm 필드 |
| [PROTOCOL-STANDARD.md](../../2026-08-17-TCF%20아키텍처%20수행방법론/05-MECHANISM/PROTOCOL-STANDARD.md) | Trace ID/GUID Protocol 규칙 |

---

*본 문서는 2026-08-22 제공 원본 이미지(85631/25.png 동일 장표)를 기준으로 작성되었다. GUID 포맷·hop increment 구현은 연계 인터페이스 정의서와 `pdmg-fw` 소스를 우선한다.*
