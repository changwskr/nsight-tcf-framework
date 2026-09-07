# 별첨 E. Data Architecture

## E.1 PDMG Current Data Access

```text
ServiceId
 ↓
Service / DAO
 ↓
Mapper Namespace
 ↓
SqlId
 ↓
SQL
 ↓
RDW / DB
```

## E.2 NSIGHT Data Role

```text
RDW
= Operational / Near-real-time

ADW
= Analytical / Mart / Heavy Query
```

PDMG Current의 ADW 직접 사용 여부는 Datasource/Mapper Inventory로 확인한다.

## E.3 Data Trace

```text
ServiceId
 ↓
DAO
 ↓
Mapper
 ↓
SqlId
 ↓
Table / View
 ↓
Owner / Lineage / Evidence
```

## E.4 CDC SLA Conflict

```text
Baseline A = 3s
Baseline B = 30s

[CONFLICT]
→ SLA Tier / Measurement Point ADR 필요
```
