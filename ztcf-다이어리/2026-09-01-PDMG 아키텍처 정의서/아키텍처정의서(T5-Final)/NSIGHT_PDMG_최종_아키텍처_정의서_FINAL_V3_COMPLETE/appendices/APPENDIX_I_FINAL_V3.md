# 별첨 I. HA / DR Architecture

## I.1 Local HA vs DR

```text
Local HA
= Node / Process Failure

DR
= Center / Site Disaster
```

## I.2 DR Recovery Chain

```text
Detect
 ↓
Isolate
 ↓
Traffic Reroute
 ↓
Recover Artifact / Config / Key
 ↓
Recover Data
 ↓
Interface Validation
 ↓
Business Validation
 ↓
Failback
```

## I.3 Principle

```text
Application Active-Active
        ≠
DB Write Active-Active
```

DB 정합성은 별도 Architecture Decision이다.

RTO/RPO 정확한 값은 Current Evidence에서 미확정이며 Business Criticality와 Drill Evidence로 확정한다.
