# EOS Reference Selection (M14)

> Agent Step: **M14**  
> 일자: 2026-08-16

---

## 1. 선정

| 우선 | Reference | 적용 EOS 영역 |
|------|-----------|---------------|
| **1** | `pdmg-service` **mgcoa9000** (S0/C0/U0/D0) | Resource, Code/Policy, Action/Exception의 **CRUD·Handler·Facade·DAO·DTO** 골격 |
| **2** | `mgcoa5530S0` | Dashboard·목록의 **집계/서브리스트 DTO** |
| **3** | `eoscoa0100` (자체) | EOS 패키지·Mapper 경로·기동 검증용 (업무 SQL은 폐기) |

---

## 2. 선정 이유

- CRUD + Paging 가능 구조가 이미 Handler switch로 정착.
- EOS와 동일하게 `pdmg-fw` TCF Online TX.
- 승인·상태전이는 MG에 완전한 복제본이 없으므로 **복사 금지·규칙 신규** (`EOS-RULES` + SERVICE-DESIGN).

---

## 3. 복사하지 않을 것

| 금지 | 이유 |
|------|------|
| MG 테이블/SQL 그대로 | 도메인 다름 |
| ASSET_EOL 확장으로 P0 구현 | RULE-091 위반 |
| Client 점수·상태 신뢰 | RULE-031 등 |

---

## 4. 구현 시 템플릿 체크리스트 (9000 기준)

```text
[ ] Handler serviceIds + switch
[ ] Facade method per ServiceId
[ ] Service validation
[ ] DAO interface + *-ORA.xml
[ ] DTOin/out FieldProperty
[ ] exceptionCode 추가
[ ] (필요) TxControl 시드
```
