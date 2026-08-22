# NSIGHT WEB / Apache Architecture

> 상태: **G30 Physical Baseline / Current Working**

## 1. 역할

NSIGHT WEB 계층의 표준 Runtime은 Apache HTTP Server이다.

```text
GSLB → L4 → WEB Server → Apache Instance → Tomcat Connector
```

WEB Server와 Apache Instance는 분리해서 관리한다.

## 2. 기본 구조

```text
WEB Server / VM
└─ Apache Instance
   ├─ Listen 9000 → Tomcat JVM :19000
   ├─ Listen 9001 → Tomcat JVM :19001
   ├─ Listen 9010 → Tomcat JVM :19010
   └─ Listen 9011 → Tomcat JVM :19011
```

하나의 Apache Instance는 복수 포트를 Listen할 수 있다.

## 3. Routing 원칙

운영 HA에서는 WEB01/WEB02가 WAS01/WAS02의 동일 Application Peer JVM으로 Cross Routing할 수 있어야 한다.

```text
WEB01 ─┬→ WAS01/JVM-A
       └→ WAS02/JVM-A
WEB02 ─┬→ WAS01/JVM-A
       └→ WAS02/JVM-A
```

고정 1:1 WEB→WAS 종속은 표준으로 보지 않는다.

## 4. 관리항목

| 영역 | 항목 |
|---|---|
| Server | WEB Hostname / Environment / Center |
| Instance | Apache Instance ID / OS Account |
| Listener | Listen Port / VirtualHost |
| Routing | Target WAS / Target JVM / Connector Port |
| Network | L4 Pool / VIP / Health Check |
| Security | TLS/Certificate/Headers |
| Performance | KeepAlive / Connection / Timeout |
| Logging | Access/Error Log |
| HA/DR | Peer WEB / DR WEB |

## 5. 현재 확인된 포트 패턴

| Service Port | Connector Port | 상태 |
|---:|---:|---|
| 9000 | 19000 | Confirmed design pattern |
| 9001 | 19001 | Confirmed design pattern |
| 9010 | 19010 | Development pattern |
| 9011 | 19011 | Development pattern |

실제 운영별 `httpd.conf`/VirtualHost/ProxyPass 값은 전수증적이 아직 부족하다.

## 6. Architecture Rule

- Apache Service Port와 Tomcat Connector Port를 구분한다.
- 하나의 Apache에서 Multi-Listen 가능하다.
- HA Application은 복수 WAS JVM으로 Routing 가능해야 한다.
- 실제 Proxy Target은 Config Evidence 없이 추정하지 않는다.
- L4/Apache/Tomcat Timeout 계층은 G60/G70에서 정합성 검증한다.

## 7. OPEN

1. 운영 전체 Apache Instance Inventory
2. `httpd.conf` / include 파일 Canonical Path
3. VirtualHost/ProxyPass/Worker 전수 Mapping
4. SSL/TLS 종단 위치
5. L4 Health Check와 Apache Health URI
6. KeepAlive/Connection/Read Timeout 실제값
7. DR Apache Routing 전환절차
