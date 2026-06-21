# Generate application-local/dev/prod.yml for all business WAR modules
$Root = Split-Path -Parent $PSScriptRoot

$Services = @(
    @('cc-service', 8081, 'nsight-cc-service', 'cc', 'nsight_cc'),
    @('ic-service', 8082, 'nsight-ic-service', 'ic', 'nsight_ic'),
    @('pc-service', 8083, 'nsight-pc-service', 'pc', 'nsight_pc'),
    @('bc-service', 8084, 'nsight-bc-service', 'bc', 'nsight_bc'),
    @('ms-service', 8085, 'nsight-ms-service', 'ms', 'nsight_ms'),
    @('sv-service', 8086, 'nsight-sv-service', 'sv', 'nsight_sv'),
    @('pd-service', 8087, 'nsight-pd-service', 'pd', 'nsight_pd'),
    @('cm-service', 8088, 'nsight-cm-service', 'cm', 'nsight_cm'),
    @('eb-service', 8089, 'nsight-eb-service', 'eb', 'nsight_eb'),
    @('ep-service', 8090, 'nsight-ep-service', 'ep', 'nsight_ep'),
    @('bp-service', 8091, 'nsight-bp-service', 'bp', 'nsight_bp'),
    @('bd-service', 8092, 'nsight-bd-service', 'bd', 'nsight_bd'),
    @('ss-service', 8093, 'nsight-ss-service', 'ss', 'nsight_ss'),
    @('cs-service', 8094, 'nsight-cs-service', 'cs', 'nsight_cs'),
    @('ct-service', 8095, 'nsight-ct-service', 'ct', 'nsight_ct'),
    @('mg-service', 8096, 'nsight-mg-service', 'mg', 'nsight_mg'),
    @('om-service', 8097, 'nsight-om-service', 'om', 'nsight_om')
)

$CommonYml = @'
server:
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
'@

$Txlog = @'
    transaction-log-schema-auto-init: true
    transaction-log-datasource:
      url: jdbc:h2:file:${nsight.txlog.path:./data/nsight-txlog}/nsight_om;MODE=Oracle;AUTO_SERVER=TRUE;DATABASE_TO_UPPER=false
      username: sa
      password:
      driver-class-name: org.h2.Driver
'@

$TxlogProd = @'
    transaction-log-schema-auto-init: false
    transaction-log-datasource:
      url: ${NSIGHT_TXLOG_JDBC_URL:jdbc:h2:file:${nsight.txlog.path:./data/nsight-txlog}/nsight_om;MODE=Oracle;AUTO_SERVER=TRUE;DATABASE_TO_UPPER=false}
      username: ${NSIGHT_TXLOG_DB_USER:sa}
      password: ${NSIGHT_TXLOG_DB_PASSWORD:}
      driver-class-name: ${NSIGHT_TXLOG_DB_DRIVER:org.h2.Driver}
'@

foreach ($s in $Services) {
    $mod, $port, $appName, $code, $db = $s
    $res = Join-Path $Root "$mod\src\main\resources"
    New-Item -ItemType Directory -Force -Path $res | Out-Null
    $utf8 = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText((Join-Path $res 'application.yml'), $CommonYml, $utf8)

    $local = @"
# local - bootRun (port $port)
server:
  port: $port

spring:
  application:
    name: $appName
  datasource:
    url: jdbc:h2:mem:$db;MODE=Oracle;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false
    hikari:
      pool-name: nsight-$code-hikari-local
  h2:
    console:
      enabled: true

nsight:
  tcf:
$Txlog
"@
    $dev = @"
# dev - ztomcat WAR (/$code)
spring:
  application:
    name: $appName
  datasource:
    url: jdbc:h2:mem:$db;MODE=Oracle;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false
    hikari:
      pool-name: nsight-$code-hikari-dev
  h2:
    console:
      enabled: true

nsight:
  tcf:
$Txlog
"@
    $codeU = $code.ToUpper()
    $prod = @"
# prod - production Tomcat WAR (/$code)
spring:
  application:
    name: $appName
  datasource:
    url: `${NSIGHT_${codeU}_DB_URL:jdbc:oracle:thin:@//localhost:1521/$db}
    username: `${NSIGHT_${codeU}_DB_USER:}
    password: `${NSIGHT_${codeU}_DB_PASSWORD:}
    driver-class-name: `${NSIGHT_${codeU}_DB_DRIVER:oracle.jdbc.OracleDriver}
    hikari:
      pool-name: nsight-$code-hikari-prod
  h2:
    console:
      enabled: false

nsight:
  tcf:
$TxlogProd
"@

    [System.IO.File]::WriteAllText((Join-Path $res 'application-local.yml'), $local, $utf8)
    [System.IO.File]::WriteAllText((Join-Path $res 'application-dev.yml'), $dev, $utf8)
    [System.IO.File]::WriteAllText((Join-Path $res 'application-prod.yml'), $prod, $utf8)
    Write-Host "OK $mod"
}
Write-Host "Done $($Services.Count)"
