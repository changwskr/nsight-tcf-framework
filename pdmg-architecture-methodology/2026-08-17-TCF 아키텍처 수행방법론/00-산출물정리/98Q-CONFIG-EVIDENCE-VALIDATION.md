# 98Q. Config Evidence Validation Result

## 1. Validator Test

`pytest` 기반 Test-First로 Parser/Validator를 구현했다.

- Apache parser
- Tomcat server.xml parser
- setenv parser
- Spring YAML parser + secret redaction
- placeholder resolution
- Evidence Manifest acceptance/rejection
- ZIP config ingestion
- Hash-bound Production Evidence Bundle
- CLI scan-dir

최종 Test: **11 passed**.

## 2. Current Source Scan

| Check | Result |
|---|---:|
| 대상 Config | 122 |
| Parse PASS | 122 |
| Parse ERROR | 0 |
| Production Accepted | 0 |
| Reference/Candidate | 122 |
| Unique Hash | 102 |
| Exact Duplicate Group | 16 |

## 3. 왜 0개가 Production Accepted인가

현재 Config는 Source ZIP의 Config Repository/Manual에 존재한다. Branch/Commit은 확인 가능하지만 `Environment + Hostname + Capture Timestamp + 실제 Host Source Path`가 결합된 Runtime Evidence가 아니다.

따라서 다음과 같은 정보만 있는 Config는 거부한다.

```text
application-prod.yml
server.xml
httpd.conf
```

반드시 다음처럼 Host에 바인딩한다.

```text
PROD
+ sbmpcolows01
+ /app/tomcat/JVM01/conf/server.xml
+ SHA-256
+ 2026-08-19T...
```

## 4. Gate Effect

Wave 2C는 Evidence 수집 자동화를 준비했지만 운영증적 자체를 대신하지 않는다.

```text
G80 = HOLD
HG90 = HOLD
```
