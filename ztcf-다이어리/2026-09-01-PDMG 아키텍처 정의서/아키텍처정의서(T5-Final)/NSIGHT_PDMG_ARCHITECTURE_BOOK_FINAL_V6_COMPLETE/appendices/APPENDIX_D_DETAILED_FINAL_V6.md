# APPENDIX D. Mapper / SqlId / Table Index — Detailed Final

### D.1 Mapper Resource Baseline

Current Mapper load pattern:

```text
classpath*:rdw.*/*.xml
```

Mapper Scan:

```text
nhnis.mg.co.a.persistence.dao
```

| Resource Path | Mapper XML | 상태 | DAO/Namespace | Table/View | 비고 |
|---|---|---|---|---|---|
| rdw.mg.co.a/ | mgcoa5530-ORA.xml | [CONFIRMED] | [OPEN] | [OPEN] | SqlId/Table 전수 Source Scan 필요 |
| rdw.mg.co.a/ | mgcoa8888-ORA.xml | [CONFIRMED] | nhnis.mg.co.a.persistence.dao.mgcoa8888DAO | [OPEN] | Namespace Exact Match 확인 |
| rdw.mg.co.a/ | mgcoa9000-ORA.xml | [CONFIRMED] | nhnis.mg.co.a.persistence.dao.mgcoa9000DAO | [OPEN] | Java DAO↔Mapper 연결 확인 |
| rdw.mg.co.a/ | mgcoa9001-ORA.xml | [CONFIRMED] | nhnis.mg.co.a.persistence.dao.mgcoa9001DAO | [OPEN] | Mapper class stem 확인 |

### D.2 Representative SqlId Naming

| SqlId | Program | Tx | Role | 상태 | Table/View |
|---|---|---|---|---|---|
| mgcoa9000S0_S0 | mgcoa9000 | S0 | Representative | [CONFIRMED NAMING] | Table/View [OPEN] |
| mgcoa9000S0_COUNT | mgcoa9000 | S0 | Count helper | [CONFIRMED NAMING] | Table/View [OPEN] |
| mgcoa9000C0_C0 | mgcoa9000 | C0 | Create | [CONFIRMED NAMING] | Table/View [OPEN] |
| mgcoa9000U0_U0 | mgcoa9000 | U0 | Update | [CONFIRMED NAMING] | Table/View [OPEN] |
| mgcoa9000D0_D0 | mgcoa9000 | D0 | Delete | [CONFIRMED NAMING] | Table/View [OPEN] |
| ..._exists | Generic | helper | Existence helper | [SOURCE MAY EXIST] | Do not constrain to CRUD suffix only |

### D.3 MyBatis Contract

```text
DAO Interface FQCN
      │
      │ EXACT MATCH
      ▼
Mapper XML namespace
      ↓
SqlId
      ↓
SQL
      ↓
Table / View
```

### D.4 Current Limitation

`SqlId → Table/View` 전수 Inventory는 현재 완료되지 않았다. 따라서 확인되지 않은 Table명을 임의로 채우지 않는다.

### D.5 Completion Gate

- Mapper XML 전수 Scan.
- Namespace ↔ DAO FQCN Exact Match.
- SqlId 전수 추출.
- SQL Parser 기반 Table/View 참조 추출.
- ServiceId → DAO → Mapper → SqlId → Table/View Trace 생성.
