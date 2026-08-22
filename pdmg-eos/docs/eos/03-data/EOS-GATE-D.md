# EOS GATE-D — Data Design Gate

> Agent Step: **M09**  
> 근거: `AGENT.md` M09 / GATE-D, `EOS-RULES.md` RULE-090~095  
> 산출: `EOS-LOGICAL-ERD.md`, `EOS-PHYSICAL-ERD.md`, `ddl/TB_EOS_P0_H2.sql`  
> 판정일: 2026-08-16

---

## 1. 판정 요약

| 항목 | 결과 |
|------|------|
| **GATE-D** | **CONDITIONAL PASS** |
| 근거 | P0 Entity 분리·PK/UK/FK/Index·이력·감사·화면 Traceability 충족 |
| 잔여 | 채번 ADR, LFC 현재행 부분 UK(앱 보장), SoD DB 미강제, 런타임 schema 미적용 |
| 다음 | **M10** 서비스 설계 (`eos/04-service/`) |

런타임 `src/.../schema.sql` 교체 및 프로토타입 `TB_EOS_ASSET_EOL` 제거는 **구현 단계**에서 수행.

---

## 2. 체크리스트

| # | 기준 | 결과 | 비고 |
|---|------|------|------|
| D-01 | PK 정의 | ✅ | 전 테이블 |
| D-02 | Business Key / UK | ✅ | PRODUCT, VER, MONTHLY_CHK 등 |
| D-03 | FK | ✅ | Product→Ver→Rsc→종속 |
| D-04 | NOT NULL | ✅ | 핵심 업무 컬럼 |
| D-05 | CHECK | ✅ | RISK SCORE 1~5 |
| D-06 | Index | ✅ | Dashboard·목록·감사 |
| D-07 | History | ✅ | LFC, RISK, ACTION_ST, EXC_APPR |
| D-08 | Audit | ✅ | TB_EOS_CHG_HIST |
| D-09 | 보관정책 | ✅ | 물리삭제 금지 원칙 문서화 |
| D-10 | 화면-Column Traceability | ✅ | LOGICAL §5 + PHYSICAL §2 |
| D-11 | 단일테이블 금지 (RULE-091) | ✅ | ASSET_EOL 대체 설계 |
| D-12 | 코드 자유입력 금지 | ✅ | CODE_GRP/CODE |
| D-13 | 날짜 타입 원칙 | ⚠ | 물리 VARCHAR(8/17) + Oracle 승격 주석 |
| D-14 | P1 수집/Snapshot DDL | ⏭ | stub만 |
| D-15 | 런타임 schema 적용 | ⏭ | 구현 게이트 |

---

## 3. Open 이월

| ID | 내용 | 해소 |
|----|------|------|
| CONF-003 | ID 채번 확정 | ADR / 서비스설계 |
| CONF-004 | EOL ∈ EOS_STATUS | 코드시드 + ADR |
| DM-001 | INSTALLATION P0 제외 | 유지 |
| DM-002 | CHG_HIST 단일 테이블 | 유지(통합) |
| DM-003 | 캐시 컬럼 동기화 | 서비스 트랜잭션 |
| LFC-UK | 현재 LFC 1건 | 앱 Validation |
| SOD-DB | 신청≠승인 | 서비스 only |

---

## 4. 서명

| Role | 결과 |
|------|------|
| Agent | **CONDITIONAL PASS** |
| Human | (검토 후 PASS / HOLD) |
