# APPENDIX G. Configuration Inventory

Known PDMG Current Snapshot:

```yaml
nhnis:
  fw:
    tcf:
      enabled: true
    timeout:
      enabled: true
      milliseconds: 5000
      pool-size: 20
      queue-capacity: 100
    commons:
      legacy-web:
        enabled: true
      filter:
        enabled: true
```

이 값은 AS-IS Snapshot이며 Target SLA가 아니다.
