# 별첨 H. Capacity / Performance Architecture

## H.1 Capacity Chain

```text
Tomcat Request Thread
 ↓
PDMG Worker Pool
 ↓
Worker Queue
 ↓
Hikari Connection Pool
 ↓
DB Session
 ↓
CPU / IO / Lock
```

## H.2 Current PDMG Worker Snapshot

```text
timeout = 5000ms
worker pool = 20
queue = 100

[AS-IS SNAPSHOT]
```

이 값은 Tomcat maxThreads나 Hikari maxPoolSize가 아니다.

## H.3 Candidate VM Alternatives

```text
Candidate A  32C / 256G × 4
Candidate B  16C / 128G × 8
Candidate C  16C / 128G × 4 × 2 business groups + DR

[PROPOSED / CANDIDATE]
```

Final Capacity는 Load Model, p95, CPU, GC, Hikari Pending, DB Wait, N+1 Residual Capacity Evidence로 확정한다.
