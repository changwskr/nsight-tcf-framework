# APPENDIX E. Physical Inventory

필수 Registry Fields:

| Layer | 필수 항목 |
|---|---|
| Center | Center ID / Role |
| Host | Hostname / IP / HW |
| VM | VM ID / CPU / Memory |
| OS | OS / Version |
| WEB | Apache Instance / Port |
| WAS | Tomcat/JVM / Connector |
| Artifact | WAR / Version / Hash |
| Network | L4 VIP / Firewall / Route |
| DB | Datasource / Service / Node |
| Storage | Mount / Capacity / Backup |
| Deployment | deploymentId / Time / Owner |

현재 Host/JVM/WAR 전수 Mapping은 `[GAP]`.
