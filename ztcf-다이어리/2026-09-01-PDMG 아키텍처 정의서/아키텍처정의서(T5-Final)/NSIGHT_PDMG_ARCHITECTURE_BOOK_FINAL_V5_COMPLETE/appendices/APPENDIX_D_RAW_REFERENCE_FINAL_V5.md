# APPENDIX D. Mapper / SqlId / Table Index

최종 자동 Index는 다음 Chain으로 생성한다.

```text
ServiceId
 ↓
Handler / Controller
 ↓
Facade / Service
 ↓
DAO Method
 ↓
Mapper Namespace
 ↓
SqlId
 ↓
SQL
 ↓
Table / View
```

현재 실제 전수 Table/View 목록은 Source Scanner 산출물로 관리한다.
