# APPENDIX A. ServiceId Registry — Detailed Final
## PDMG Current Service Routing SSOT Reference

### A.1 목적

ServiceId는 단순 URL 코드가 아니라 `Business → Program → Handler → Facade → Service → DAO/Mapper → Runtime Evidence`를 연결하는 핵심 식별자다.

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Handler Registry
 ↓
Handler
 ↓
Facade / Service
 ↓
DAO / Mapper / SqlId
 ↓
GUID / Runtime Evidence
```

### A.2 ServiceId 구조

```text
2 + 2 + 1 + 4 + 1 + 1 = 11 chars
```

Transaction Type:
`S=조회`, `C=등록`, `U=수정`, `D=삭제`, `A=혼합`, `R=출력물/Report`.

### A.3 Current Handler Registry — 13

| ServiceId | Program | Type | 의미 | Seq | Handler | 상태 | 근거 |
|---|---|---|---|---|---|---|---|
| mgcoa5530S0 | mgcoa5530 | S | 조회 | 0 | mgcoa5530Handler | [AS-IS] | Current Handler Registry |
| mgcoa8888S0 | mgcoa8888 | S | 조회 | 0 | mgcoa8888Handler | [AS-IS] | Current Handler Registry |
| mgcoa8888D0 | mgcoa8888 | D | 삭제 | 0 | mgcoa8888Handler | [AS-IS] | Current Handler Registry |
| mgcoa9000S0 | mgcoa9000 | S | 조회 | 0 | mgcoa9000Handler | [AS-IS] | Current Handler Registry |
| mgcoa9000C0 | mgcoa9000 | C | 등록 | 0 | mgcoa9000Handler | [AS-IS] | Current Handler Registry |
| mgcoa9000U0 | mgcoa9000 | U | 수정 | 0 | mgcoa9000Handler | [AS-IS] | Current Handler Registry |
| mgcoa9000D0 | mgcoa9000 | D | 삭제 | 0 | mgcoa9000Handler | [AS-IS] | Current Handler Registry |
| mgcoa9001S0 | mgcoa9001 | S | 조회 | 0 | mgcoa9001Handler | [AS-IS] | Current Handler Registry |
| mgcoa9001C0 | mgcoa9001 | C | 등록 | 0 | mgcoa9001Handler | [AS-IS] | Current Handler Registry |
| mgcoa9001U0 | mgcoa9001 | U | 수정 | 0 | mgcoa9001Handler | [AS-IS] | Current Handler Registry |
| mgcoa9001D0 | mgcoa9001 | D | 삭제 | 0 | mgcoa9001Handler | [AS-IS] | Current Handler Registry |
| mgcoa9100S0 | mgcoa9100 | S | 조회 | 0 | mgcoa9100Handler | [AS-IS] | Current Handler Registry |
| mgcoa9999S0 | mgcoa9999 | S | 조회 | 0 | mgcoa9999Handler | [AS-IS] | Current Handler Registry |

### A.4 Validation Rules

- ServiceId는 Registry 내 Unique 해야 한다.
- Duplicate ServiceId는 startup fail 대상이다.
- Handler의 `serviceIds()` 등록값과 `handle()` branch는 일치해야 한다.
- UI Transaction Catalog와 Backend Registry는 자동 Diff 대상이다.
- Current Handler Source는 13건이며 과거 8건 자료는 Drift/Superseded 후보로 본다.
- TCF OFF에서는 동일 ID가 Dispatcher Registry Key가 아니라 MVC 진입 식별자로 사용될 수 있으므로 두 경로를 구분한다.

### A.5 GAP

- `[GAP]` UI Catalog ↔ Backend 13 ServiceIds 자동정합 미완료.
- `[GAP]` Dispatcher ServiceId ↔ Context/Header/Path mismatch rejection 미확정.
- `[GAP]` ServiceId → SqlId → Table/View 전수 Trace 미완료.
