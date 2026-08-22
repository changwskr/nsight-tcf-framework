# NSIGHT Source / Evidence Inventory — G00

## 1. 목적

`2026-08-17-NSIGHT` 전체 자료를 Current Architecture Baseline으로 사용하기 전에 Source Scope, Canonical Source, 생성물/중복, 버전 충돌을 분리한다.

## 2. Source Intake 결과

### 2.1 `nsight-tcf-framework (2).zip` 실제 Snapshot

- ZIP Entry: 35,584
- 최상위 Gradle `build.gradle` 보유 모듈: 42개
- ZIP 루트에 전체 모듈을 묶는 `settings.gradle` / `build.gradle`은 확인되지 않음
- 다수 모듈이 자체 `settings.gradle`을 가지는 독립 Build Root 형태
- Root Repository의 Branch/Commit을 식별할 수 있는 `.git`은 확인되지 않음
- `.git` 정보는 `znsight-config-info` 하위 별도 환경설정 Repository에 한정되어 확인됨

따라서 `22개 모듈`, `24개 모듈` 같은 과거 숫자를 전체 ZIP의 물리 모듈 수로 사용하지 않는다. 모듈 수는 반드시 Scope를 붙여 관리한다.

### 2.2 Current Source Scope

| Scope | Current Snapshot | 판정 |
|---|---|---|
| PDMG | `pdmg-ui`, `pdmg-jwt`, `pdmg-fw`, `pdmg-service`, `pdmg-eos`, `pdmg-infra` | AS-IS Source |
| PDMG OM | 독립 `pdmg-om` 없음 | GAP/EXPECTED |
| PDMK | `pdmk-ui`, `pdmk-fw`, `pdmk-service`, `pdmk-om` | AS-IS Source |
| PDMP | `pdmp-ui`, `pdmp-fw`, `pdmp-service` | AS-IS Source |
| TCF Core | `tcf-core`, `tcf-web`, `tcf-util`, `tcf-cache`, `tcf-eai`, `tcf-jwt`, `tcf-gateway` 등 | TCF Source |
| TCF OM | `tcf-om` | TCF Source |
| Business Service | `av/eb/ep/ic/ln/mg/ms/om/pc/pd/ss/sv-service` | 별도 Scope |
| Architecture Automation | `tcf-harness`, `tcf-ontology-service`, AI Methodology 등 | Tooling Scope |
| Generated/Docs | `build/`, `bin/`, `z*` 문서/책/다이어리 | Current Code SoT 제외 |

### 2.3 Build / Java Baseline

실제 Build 파일 점검 결과:

- PDMG/PDMK/PDMP 계열 주요 Build Root는 Java Toolchain 21을 명시
- `pdmg-fw`, `pdmg-service`, `pdmg-ui` 등은 Spring Boot 3.5.14 계열
- `pdmg-service`는 WAR 배포를 사용하며 `bootWar=false`, `war=true`
- `pdmg-service/settings.gradle`은 sibling `pdmg-fw`를 include
- Generic `tcf-*` 모듈의 `build.gradle`은 `project(':tcf-core')` 등의 multi-project 의존성을 사용하지만, 해당 전체 Root `settings.gradle`은 ZIP 최상위에 없음

따라서:

```text
PDMG Java 21 = CURRENT SOURCE FACT
Java 17 문서 = LEGACY/COMPATIBILITY 후보
TCF 전체 Toolchain = ROOT BUILD 부재로 UNKNOWN
```

### 2.4 OM Drift

`tcf-om/src/main/java/.../entry/handler` 실제 Source에는 `*Handler.java`가 25개 존재한다.

`OmMessageStructureHandler`가 포함되어 있으므로, 24개로 기술한 문서는 Current Source와 Drift가 있다.

```text
AS-IS CODE = 25 Handler
문서 24 Handler = STALE / DRIFT
```

### 2.5 Configuration Canonical Rule

동일 설정이 다음 위치에 중복되어 있다.

```text
src/main/resources/
build/resources/main/
bin/main/
```

Current Config Source of Truth는 원칙적으로:

```text
src/main/resources/**
```

로 두고 `build/`, `bin/`은 Generated/Compiled Copy로 제외한다.

## 3. Canonical Evidence 우선순위

1. `src/main/java`, `src/main/resources`, 실제 `build.gradle/settings.gradle`
2. Current Architecture/Strategy Baseline
3. 승인 Decision/ADR
4. Current Server/Performance Working Baseline
5. Requirement/Interview/Plan
6. Guide/Book
7. Diary/Build Help/Generated Copy

## 4. G00 미해결 조건

| ID | 미해결 조건 | 상태 |
|---|---|---|
| G00-C01 | Root Repository Branch/Commit | UNKNOWN |
| G00-C02 | TCF 전체 Multi-project Root Build | MISSING/UNKNOWN |
| G00-C03 | 운영 Apache `httpd.conf` 실제 원본 | 추가 Evidence 필요 |
| G00-C04 | 운영 Tomcat `server.xml/setenv.sh` 실제 원본 | 추가 Evidence 필요 |
| G00-C05 | Session 60m/90m 최종 승인값 | Working Conflict |
| G00-C06 | 16Core 500/855 TPS 최종 승인값 | Load Test 전 Working Conflict |

## 5. G00 판정

**CONDITIONAL PASS**

Source Scope와 Canonical Rule은 확정 가능하다. Branch/Commit과 일부 운영 Config는 UNKNOWN으로 명시하고 다음 Gate로 진행한다. 성능/세션 충돌은 Source Intake를 막는 항목이 아니라 G60/ADR에서 확정할 항목으로 이관한다.
