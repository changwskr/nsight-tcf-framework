# 별첨 C. Physical / Infrastructure Architecture

## C.1 Working Physical Path

```text
GSLB
 ↓
L4
 ↓
Apache
 ↓
Tomcat / JVM
 ↓
WAR
 ↓
Hikari / MyBatis / JDBC
 ↓
RDW / DB
```

## C.2 Physical Layer

```text
Center
 ↓
Physical Host
 ↓
VM
 ↓
OS
 ↓
JVM / Process
 ↓
WAR / Artifact
 ↓
Port / Datasource / Storage
 ↓
Monitoring / Backup / Evidence
```

## C.3 Current Mapping Status

| Logical Node | Physical Projection | 상태 |
|---|---|---|
| UI Delivery | WEB/Application placement | OPEN |
| Authentication | pdmg-jwt Runtime placement | OPEN |
| Application Runtime | Tomcat/JVM/WAR projection | PARTIAL |
| Data Service | RDW/DB | PARTIAL |
| Integration | Interface Runtime | OPEN |
| Operations | pdmg-om/OM | UNKNOWN |

정확한 Hostname, Port, Tomcat Version, Server Count는 실제 Inventory 없이 확정하지 않는다.
