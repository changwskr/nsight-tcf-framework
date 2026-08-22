# NSIGHT UI/UX Traceability — G80 Draft

## 1. 상태

UI/UX 자료는 존재하지만 현재 Baseline에는 화면 전수 Catalog와 ServiceId 전수 연결이 포함되어 있지 않아 `PARTIAL`이다.

## 2. Target Trace

```text
Requirement → Menu → Screen ID → Event/Function → Program ID → ServiceId → API/Handler → Data
```

## 3. G80 요구사항

- 화면ID/메뉴/기능/ServiceId Transaction Catalog 생성
- UI 권한과 ServiceId/Data Authorization 연결
- 화면 진행률과 API/DB 구현 진행률 분리/연결
- 화면별 오류/Timeout/권한 Runtime Evidence 연결

확인되지 않은 Screen 전수목록은 임의 생성하지 않는다.
