# EOS Design–Source GAP (M15)

> Agent Step: **M15**  
> 설계: GATE-U/D/S · 소스: M13  
> 일자: 2026-08-16

---

## 1. 요약

| 영역 | 설계(P0) | 현행소스 | GAP |
|------|----------|----------|-----|
| Package | `nhnis.eos.co.a` | 동일 | 없음 |
| ServiceId | 0110~0190, 0141, 0151, 0165 | `0100S0` only | **대** |
| Handler/Facade/Service/DAO | 도메인별 다수 | `eoscoa0100*` only | **대** |
| DTO | M11 스펙 | riskCd/statusCd 조회만 | **대** |
| Mapper/SQL | 17 테이블 | `TB_EOS_ASSET_EOL` | **대** |
| schema/data | P0 DDL | 구스키마 | **대** |
| Rule/Status/Risk/SoD | DESIGN | 없음 | **대** |
| Audit CHG_HIST | 필수 | 없음 | **대** |
| 오류코드 EOS-E* | 초안 | 공통만 | **중** |
| Test | ST-* | 없음 | **중** |
| UI | UX-003 | 없음 | P1/별도 |
| Config/Build | 재사용 | 就绪 | 소 |

---

## 2. 상세 GAP

### GAP-PKG
패키지 구조는 유지. 클래스 폭발 시 Handler를 `eoscoa01xxHandler` 단위(화면/도메인)로 분할.

### GAP-SVC
| 설계 ServiceId | 소스 | 조치 |
|----------------|------|------|
| 0110~0190, 0141, 0151, 0165 | 미존재 | 신규 |
| 0100S0 | 존재 | deprecate → 0120으로 이관 후 삭제 |

### GAP-DB
| 설계 | 소스 | 조치 |
|------|------|------|
| `ddl/TB_EOS_P0_H2.sql` | `schema.sql` ASSET_EOL | 구현 Sprint1에서 교체 |
| 코드/정책 시드 | 최소/없음 | `data.sql` 시드 |

### GAP-RULE
StatusEngine, RiskCalculator, ActionStateMachine, ExceptionSoD — `…/domain` 또는 `…/rule` 패키지 **신규**.

### GAP-TX
Facade `@Transactional` 패턴을 9000과 동일하게 맞출 것 (구현 시 소스 확인).

---

## 3. 위험

| 위험 | 완화 |
|------|------|
| 한 스프린트에 전 Service | Wave 분할 (M16) |
| ASSET_EOL 잔존 | schema 교체 시 DROP 명시 |
| Open CONF | ADR-001 Default로 진행 |

---

## 4. 다음

**M16** `02-implementation-plan.md` — Wave별 구현 순서.
