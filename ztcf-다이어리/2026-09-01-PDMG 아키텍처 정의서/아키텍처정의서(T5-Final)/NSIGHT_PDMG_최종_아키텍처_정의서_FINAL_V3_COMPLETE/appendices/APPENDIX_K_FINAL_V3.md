# 별첨 K. Observability / Runtime Evidence

## K.1 Current Foundation

```text
GUID
ServiceId
MDC
ImageLog
Timeout / Overload / Error Signal
```

## K.2 Target Observability

```text
Metrics
+
Structured Logs
+
Distributed Trace
      ↓
ServiceId / GUID
      ↓
DeploymentId / Host / JVM
      ↓
Architecture Rule Evidence
```

## K.3 Logging vs Evidence

```text
Logging
= 사건 기록

Runtime Evidence
= Architecture Rule 준수 증명
```

ImageLog는 운영/감사에 유용하지만 민감정보/DDL/Storage Governance를 별도로 점검해야 한다.
