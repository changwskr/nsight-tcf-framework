# EOS Source Analysis (M13)

> Agent Step: **M13**  
> 대상: `pdmg-eos` + reference `pdmg-service` / `pdmg-fw`  
> 일자: 2026-08-16

---

## 1. Build / Module

| 항목 | 내용 |
|------|------|
| Module | `pdmg-eos` (`settings.gradle` root) |
| FW | `include ':pdmg-fw'` → `../pdmg-fw` |
| Java | 21 · Spring Boot 3.5.14 · WAR |
| Port | **8082** |
| DB local | H2 mem `pdmg_eos` MODE=Oracle · log4jdbc |
| TCF | `nhnis.fw.tcf.enabled=true` |
| Timeout | 5000ms (FW) |
| TxControl | enabled |

패턴은 `pdmg-service`(MG)와 동일: Handler(`TransactionHandler`) → Facade → Service → DAO → MyBatis `rdw.eos.co.a/*-ORA.xml`.

---

## 2. Package Inventory (현행)

```text
nhnis.eos.PdmgEosApplication
nhnis.eos.co.a
  ├─ config/          Security, RdwDataSource, Utf8, MybatisLog
  ├─ entry/handler/   eoscoa0100Handler
  ├─ entry/aspect/    BizPrePostAspect
  ├─ application/controller|facade|service/  eoscoa0100*
  ├─ persistence/dao/ eoscoa0100DAO
  └─ dto/             eoscoa0100S0DTOin/out
resources/
  ├─ application.yml
  ├─ exceptionCode.yml
  ├─ rdw.eos.co.a/eoscoa0100-ORA.xml
  └─ db/h2/schema.sql · data.sql
```

**Test:** `src/test` 거의 없음 (Boot test 의존성만).

---

## 3. 현행 동작 (프로토타입)

| ServiceId | 역할 | 테이블 |
|-----------|------|--------|
| `eoscoa0100S0` | 자산 EOL 목록 조회 | `TB_EOS_ASSET_EOL` |

부가: `TB_MG_TX_CONTROL` (FW 거래제어).

---

## 4. Reference 후보 (`pdmg-service`)

| Service | 패턴 | EOS 적합성 |
|---------|------|------------|
| **mgcoa9000** S0/C0/U0/D0 | CRUD + Handler 스위치 | 자원·코드 CRUD 템플릿 **1순위** |
| mgcoa9001 | 유사 CRUD | 보조 |
| mgcoa5530 S0 | 조회·서브 DTO | 목록/집계 참고 |
| mgcoa8888 / 9999 | 특수 | 낮음 |
| mgcoa9100 | 조회 | Dashboard 단순 집계 참고 |

승인 전용 MG 샘플은 약함 → EOS 예외/조치 전이는 **설계(DESIGN §6–7) + 9000 CRUD 골격**으로 신규 Rule 클래스 추가.

---

## 5. 계층 책임 (준수)

| Layer | 책임 | EOS 구현 시 |
|-------|------|-------------|
| Handler | serviceIds 라우팅 | 도메인별 Handler 또는 통합 |
| Facade | Use Case 진입·TX 경계 | `@Transactional` 위치 MG와 동일 확인 |
| Service | Validation·Rule·오케스트레이션 | StatusEngine, RiskCalc, SoD |
| DAO/Mapper | SQL only | 테이블당 또는 UseCase당 XML |
| DTO | DataObject + FieldProperty | M11 스펙 |

---

## 6. Config 재사용

이미 존재: `RdwDataSourceConfig`, `SecurityConfig`, `Utf8WebConfig`, `BizPrePostAspect`.  
P0 구현 시 **새 Config 최소화**, Mapper namespace `rdw.eos.co.a` 유지.

---

## 7. 결론

- 빌드·기동 골격은 구현 가능 상태.
- 업무 코드는 **0100 프로토타입 1건**뿐 → 설계 P0 전체를 **9000 패턴으로 증설**.
- 다음: **M14** Reference 확정 → **M15** GAP → **M16** 구현계획.
