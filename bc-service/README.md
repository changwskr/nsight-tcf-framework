# bc-service — Business Customer (고객)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `bc-service` |
| 업무코드 | `BC` |
| 메인 클래스 | `com.nh.nsight.marketing.bc.NsightBcServiceApplication` |
| bootRun 포트 | **8084** |
| WAR | `bc.war` |
| Tomcat context | `/bc` |

## 개요

**기업 고객(BC)** 업무 서비스입니다. TCF 프레임워크(`tcf-util`, `tcf-core`, `tcf-web`)만 의존합니다.

## 의존성

| 모듈 | 필수 |
|------|------|
| `tcf-util` | O |
| `tcf-core` | O |
| `tcf-web` | O |
| `common-etc` | X |

## 빌드·실행·배포

### 모듈 전용 스크립트 (`bc-service/scripts/`)

```bash
# Windows
bc-service\scripts\build.bat          # tcf + bc.war 빌드
bc-service\scripts\build.bat clean
bc-service\scripts\build.bat run       # bootRun
bc-service\scripts\run-local.bat      # bootRun (8084)
bc-service\scripts\deploy.bat         # Tomcat webapps 배포

# Linux / macOS
chmod +x bc-service/scripts/*.sh
bc-service/scripts/build.sh
bc-service/scripts/run-local.sh
bc-service/scripts/deploy.sh
```

### 프로젝트 공통 스크립트 (`tcf-scripts/`)

```bash
tcf-scripts\build.bat bc
tcf-scripts\run-local.bat bc
tcf-scripts\deploy.bat bc
```

### Gradle 직접 호출 (프로젝트 루트)

```bash
gradle :bc-service:bootWar
gradle :bc-service:bootRun
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/bc/online` |

## tcf-ui

- http://localhost:8099/bc/index.html
- http://localhost:8099/bc/index-multi.html
