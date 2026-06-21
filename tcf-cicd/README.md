# tcf-cicd — NSIGHT TCF 환경 설정 (SoT)

`local` / `dev` / `prod` 프로파일별 **Spring yml·Tomcat setenv·Apache** 를 이 디렉터리에서 관리합니다.

| 프로파일 | 용도 | 주요 경로 |
|----------|------|-----------|
| `local/` | bootRun (개발자 PC) | `local/spring/`, `local/ztomcat/` |
| `dev/` | ztomcat 통합 검증 | `dev/spring/`, `dev/ztomcat/` |
| `prod/` | 운영 Tomcat + Apache | `prod/spring/`, `prod/ztomcat/`, `prod/apache/` |

상세: [docs/architecture/25-env-profile.md](../docs/architecture/25-env-profile.md)

---

## 디렉터리 구조

```text
tcf-cicd/
├── manifest.yaml           # 모듈·프로파일 매핑
├── local/ dev/ prod/
│   ├── spring/{module}/application-{profile}.yml
│   ├── ztomcat/setenv.*
│   └── env/.env.example
├── prod/apache/            # Apache reverse proxy
└── scripts/
    ├── pull-from-framework.ps1   # framework → tcf-cicd (bootstrap)
    ├── sync-to-framework.ps1     # tcf-cicd → framework (빌드 전)
    └── apply-tomcat-config.sh    # prod → Tomcat conf/nsight/ (runtime)
```

---

## 일반 워크플로

### 1. 최초 bootstrap (framework → tcf-cicd)

```powershell
cd tcf-cicd/scripts
.\pull-from-framework.ps1 -Profile all
```

### 2. 설정 수정 후 framework 반영 (빌드 전)

```powershell
# dev ztomcat 배포 전
.\sync-to-framework.ps1 -Profile dev

# prod 샘플·apache 반영
.\sync-to-framework.ps1 -Profile prod
```

```powershell
# dry-run
.\sync-to-framework.ps1 -Profile dev -DryRun
```

### 3. ztomcat / Gradle 빌드

**local 전체 빌드 (권장):**

```powershell
cd tcf-cicd/local/script
.\build-all.ps1              # sync local + gradle build (전 모듈)
.\build-all.ps1 -Target wars # sync + buildZtomcatWars (19 WAR)
```

**local Tomcat 배포 (19 WAR):**

```powershell
cd tcf-cicd/local/script
.\deploy-wars.ps1            # sync dev + buildZtomcatWars + webapps
.\deploy-wars.ps1 sv om      # 선택 배포
.\deploy-wars.ps1 -Restart   # 배포 후 Tomcat 기동
```

```bash
tcf-cicd/local/script/deploy-wars.sh
tcf-cicd/local/script/deploy-wars.sh sv om batch --restart
```

**local Tomcat 기동/중지:**

```powershell
cd tcf-cicd/local/ztomcat
.\start.ps1              # sync dev + apply-config + batch/ui deploy + start
.\start.ps1 -DeployAll   # 19 WAR 전체 배포 후 기동
.\stop.ps1
.\deploy-restart.ps1     # stop + 19 WAR deploy + start + health verify
.\deploy-restart.ps1 sv om
```

```bash
tcf-cicd/local/ztomcat/start.sh
tcf-cicd/local/ztomcat/stop.sh
tcf-cicd/local/ztomcat/deploy-restart.sh
```

```bash
tcf-cicd/local/script/build-all.sh
tcf-cicd/local/script/build-all.sh wars
```

```bash
# dev
cd ../..
ztomcat/start.sh

# WAR 빌드 (직접 Gradle)
gradle buildZtomcatWars
```

### 4. prod runtime mount (WAR 재빌드 없이 yml만 갱신)

Tomcat `setenv`에 추가:

```sh
CATALINA_OPTS="${CATALINA_OPTS} -Dspring.config.additional-location=file:${CATALINA_BASE}/conf/nsight/"
```

배포:

```bash
export CATALINA_BASE=/path/to/tomcat
tcf-cicd/scripts/apply-tomcat-config.sh prod
# Tomcat 재기동 또는 context reload
```

---

## 관리 대상 모듈

**플랫폼:** `tcf-web`, `tcf-cache`, `tcf-core`, `tcf-om`, `tcf-batch`, `tcf-ui`  
**업무:** `cc-service` … `mg-service`, `om-service` (17)

`application.yml`(공통)은 **framework 모듈**에 유지합니다.  
`tcf-cicd`는 **`application-{local,dev,prod}.yml`** 만 관리합니다.

---

## CI 연동 예시

```yaml
# dev pipeline
- run: pwsh tcf-cicd/scripts/sync-to-framework.ps1 -Profile dev
- run: gradle buildZtomcatWars
- run: ztomcat/deploy-wars.sh all

# prod pipeline (runtime config)
- run: tcf-cicd/scripts/apply-tomcat-config.sh prod
- scp: tcf-cicd/prod/ztomcat/setenv.sh -> server tomcat/bin/
```

---

## 주의

- **비밀번호·키는 Git에 넣지 않음** — `prod/env/.env.example` 참고, `${NSIGHT_*}` placeholder 유지
- `local`은 개발 편의를 위해 framework와 **양방향 sync** 가능 (bootRun 즉시 반영)
- `prod` Spring yml 변경은 **apply-tomcat-config** 또는 sync 후 WAR 재배포 중 선택
