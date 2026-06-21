#!/usr/bin/env python3
"""Generate application-local/dev/prod.yml for all business WAR modules."""
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]

SERVICES = [
    ("cc-service", 8081, "nsight-cc-service", "cc", "nsight_cc"),
    ("ic-service", 8082, "nsight-ic-service", "ic", "nsight_ic"),
    ("pc-service", 8083, "nsight-pc-service", "pc", "nsight_pc"),
    ("bc-service", 8084, "nsight-bc-service", "bc", "nsight_bc"),
    ("ms-service", 8085, "nsight-ms-service", "ms", "nsight_ms"),
    ("sv-service", 8086, "nsight-sv-service", "sv", "nsight_sv"),
    ("pd-service", 8087, "nsight-pd-service", "pd", "nsight_pd"),
    ("cm-service", 8088, "nsight-cm-service", "cm", "nsight_cm"),
    ("eb-service", 8089, "nsight-eb-service", "eb", "nsight_eb"),
    ("ep-service", 8090, "nsight-ep-service", "ep", "nsight_ep"),
    ("bp-service", 8091, "nsight-bp-service", "bp", "nsight_bp"),
    ("bd-service", 8092, "nsight-bd-service", "bd", "nsight_bd"),
    ("ss-service", 8093, "nsight-ss-service", "ss", "nsight_ss"),
    ("cs-service", 8094, "nsight-cs-service", "cs", "nsight_cs"),
    ("ct-service", 8095, "nsight-ct-service", "ct", "nsight_ct"),
    ("mg-service", 8096, "nsight-mg-service", "mg", "nsight_mg"),
    ("om-service", 8097, "nsight-om-service", "om", "nsight_om"),
]

COMMON_YML = """server:
  servlet:
    context-path: /
    encoding:
      charset: UTF-8
      enabled: true
      force: true
    session:
      timeout: 60m
      tracking-modes: cookie
      cookie:
        name: JSESSIONID
        path: /
        http-only: true
        secure: false
        same-site: Lax

spring:
  profiles:
    default: local
  datasource:
    driver-class-name: org.h2.Driver
    username: sa
    password:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 2
      connection-timeout: 3000
      validation-timeout: 3000
      idle-timeout: 600000
      max-lifetime: 1800000
      keepalive-time: 300000
      auto-commit: false
  session:
    store-type: none
  transaction:
    default-timeout: 5

mybatis:
  mapper-locations:
    - classpath:/mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true
    default-statement-timeout: 3
    jdbc-type-for-null: NULL
    call-setters-on-nulls: true
    default-fetch-size: 500
    cache-enabled: false

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus,threaddump
  endpoint:
    health:
      probes:
        enabled: true

nsight:
  system-id: NSIGHT-MP
  domain: nh.marketing.com
  tcf:
    session-validation-enabled: false
    authorization-validation-enabled: false
    idempotency-enabled: true
    audit-enabled: true
    transaction-log-enabled: true
  timeout:
    online-transaction-seconds: 5
    db-query-seconds: 3
"""

TXLOG = """    transaction-log-schema-auto-init: true
    transaction-log-datasource:
      url: jdbc:h2:file:${nsight.txlog.path:./data/nsight-txlog}/nsight_om;MODE=Oracle;AUTO_SERVER=TRUE;DATABASE_TO_UPPER=false
      username: sa
      password:
      driver-class-name: org.h2.Driver
"""

TXLOG_PROD = """    transaction-log-schema-auto-init: false
    transaction-log-datasource:
      url: ${NSIGHT_TXLOG_JDBC_URL:jdbc:h2:file:${nsight.txlog.path:./data/nsight-txlog}/nsight_om;MODE=Oracle;AUTO_SERVER=TRUE;DATABASE_TO_UPPER=false}
      username: ${NSIGHT_TXLOG_DB_USER:sa}
      password: ${NSIGHT_TXLOG_DB_PASSWORD:}
      driver-class-name: ${NSIGHT_TXLOG_DB_DRIVER:org.h2.Driver}
"""


def main() -> None:
    for mod, port, app_name, code, db in SERVICES:
        res = ROOT / mod / "src" / "main" / "resources"
        res.mkdir(parents=True, exist_ok=True)
        (res / "application.yml").write_text(COMMON_YML, encoding="utf-8", newline="\n")
        code_u = code.upper()
        local = f"""# local — bootRun (port {port})
server:
  port: {port}

spring:
  application:
    name: {app_name}
  datasource:
    url: jdbc:h2:mem:{db};MODE=Oracle;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false
    hikari:
      pool-name: nsight-{code}-hikari-local
  h2:
    console:
      enabled: true

nsight:
  tcf:
{TXLOG}"""
        dev = f"""# dev — ztomcat / 개발 서버 WAR (/{code})
spring:
  application:
    name: {app_name}
  datasource:
    url: jdbc:h2:mem:{db};MODE=Oracle;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false
    hikari:
      pool-name: nsight-{code}-hikari-dev
  h2:
    console:
      enabled: true

nsight:
  tcf:
{TXLOG}"""
        prod = f"""# prod — 운영 Tomcat WAR (/{code})
spring:
  application:
    name: {app_name}
  datasource:
    url: ${{NSIGHT_{code_u}_DB_URL:jdbc:oracle:thin:@//localhost:1521/{db}}}
    username: ${{NSIGHT_{code_u}_DB_USER:}}
    password: ${{NSIGHT_{code_u}_DB_PASSWORD:}}
    driver-class-name: ${{NSIGHT_{code_u}_DB_DRIVER:oracle.jdbc.OracleDriver}}
    hikari:
      pool-name: nsight-{code}-hikari-prod
  h2:
    console:
      enabled: false

nsight:
  tcf:
{TXLOG_PROD}"""
        for name, content in [
            ("application-local.yml", local),
            ("application-dev.yml", dev),
            ("application-prod.yml", prod),
        ]:
            (res / name).write_text(content, encoding="utf-8", newline="\n")
        print("OK", mod)
    print("Done", len(SERVICES))


if __name__ == "__main__":
    main()
