# EOS-NAVIGATION — 화면 정보구조 (M04)

> 모드: `SCREEN_DESIGN`  
> 근거: `AGENT.md` §16, `분석및요건정의.md` §23, `EOS-RULES.md`  
> 원칙: Excel Sheet ≠ 화면 1:1 복제. **자원원장 중심** 네비게이션.

---

## 1. 최상위 메뉴

```text
EOS
├─ 01 Dashboard              EOS-010   P0
├─ 02 자원
│  ├─ 통합조회               EOS-020   P0
│  ├─ 자원 등록·상세         EOS-030   P0
│  └─ Product Lifecycle      EOS-040   P0
├─ 03 위험·조치
│  ├─ 위험도 평가            EOS-050   P0
│  ├─ 조치계획               EOS-060   P0
│  └─ 완료검증               EOS-065   P1  [추가제안: 060에 탭으로도 가능]
├─ 04 예외
│  ├─ 예외 신청              EOS-070   P0
│  ├─ 예외 승인              EOS-080   P0
│  └─ 예외·월간점검          EOS-090   P0
├─ 05 보고
│  ├─ 월간보고               EOS-100   P1
│  └─ 일정 Calendar          EOS-110   P1
├─ 06 점검·수집
│  ├─ 점검결과               EOS-120   P1
│  └─ 자동수집 현황          EOS-130   P2
└─ 07 관리
   ├─ 코드·정책              EOS-140   P0
   └─ 변경·감사이력          EOS-150   P0
```

---

## 2. Excel 시트 ↔ 화면 매핑

| Excel | 주요 화면 | 비고 |
|-------|-----------|------|
| 00_Dashboard | EOS-010 | KPI Drill-down → EOS-020 |
| 01_EOS관리대장 | EOS-020 / 030 | 원장. Lifecycle은 040과 연결 |
| 02_위험도평가 | EOS-050 | 자원 상세에서 진입 가능 |
| 03_조치계획 | EOS-060 | 전환·Rollback 포함 |
| 04_예외승인 | EOS-070 / 080 | 신청·승인 SoD 분리 |
| 05_월간보고 | EOS-100 / 090 | Snapshot + 예외점검 |
| 06_코드기준표 | EOS-140 | 하드코딩 금지 |
| 07_점검명령어 | EOS-120 / 130 | P1/P2 |

---

## 3. 핵심 사용자 Journey

### 3.1 위험 식별 → 조치

```text
Dashboard(Critical)
  → 자원목록(EOS-020)
  → 자원상세(EOS-030)
  → 위험평가(EOS-050)
  → 조치계획(EOS-060)
  → (전환/Rollback) → 완료검증
```

### 3.2 조치불가 → 예외

```text
조치계획(EOS-060) [조치불가]
  → 예외신청(EOS-070)
  → 예외승인(EOS-080)
  → 예외·월간점검(EOS-090)
  → 만료 시 Dashboard·알림
```

### 3.3 Lifecycle 일괄 반영

```text
Product Lifecycle(EOS-040) EOS일자 변경
  → 동일 Version Instance 일괄 상태 재계산
  → Dashboard/목록 반영
```

---

## 4. P0 화면 우선 구현 순서

1. EOS-140 코드·정책 (임계값·등급·상태)  
2. EOS-040 Product Lifecycle  
3. EOS-030 / 020 자원 등록·조회  
4. EOS-050 위험평가  
5. EOS-060 조치계획  
6. EOS-070 / 080 예외  
7. EOS-010 Dashboard  
8. EOS-150 감사이력  

---

## 5. [확인필요]

| ID | 내용 |
|----|------|
| UX-001 | 완료검증을 독립 화면(EOS-065) vs 조치계획 탭 |
| UX-002 | Dashboard `위험 상태` KPI 산정식 (CONF-001) |
| UX-003 | UI 기술: **`pdmg-ui` static** (`#/eos`, `#/eoscoa` → :8082) |

---

## 6. 다음 (M05 → M06 → M07)

화면 상세: [EOS-SCREEN-DESIGN.md](./EOS-SCREEN-DESIGN.md)  
GATE-U: [EOS-GATE-U.md](./EOS-GATE-U.md) — **CONDITIONAL PASS**  
- DONE: EOS-010~090, 140, 150 (P0)  
- NEXT: **M07** 논리 데이터 모델  
