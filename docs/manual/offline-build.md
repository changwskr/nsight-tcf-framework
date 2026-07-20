# 오프라인(무인터넷) 빌드·이관

사내망처럼 Gradle이 외부와 통신할 수 없을 때, 의존성·플러그인·Gradle 배포본을
프로젝트 안에 넣어 빌드하는 방법입니다.

스크립트 위치: `tcf-scripts/offlinebuild/`

## 구성 요소

| 경로 | 역할 |
|------|------|
| `offline-repo/` | Maven 레이아웃 로컬 저장소 (jar/pom/module) |
| `gradle/wrapper/gradle-*-bin.zip` | Gradle 배포본 (인터넷 없이 Wrapper 기동) |
| `gradlew` / `gradlew.bat` | Wrapper |
| `gradle.properties` → `nsight.dependency.mode` | `offline` / `hybrid` / `online` |

## 인터넷 PC에서 (이관 전 1회)

```bat
tcf-scripts\offlinebuild\prepare-offline-bundle.bat
```

또는:

```powershell
.\tcf-scripts\offlinebuild\prepare-offline-bundle.ps1
```

수행 내용:

1. Gradle Wrapper 생성
2. `gradle-8.10.1-bin.zip` 다운로드 → `gradle/wrapper/`
3. `distributionUrl`을 로컬 zip으로 고정
4. 전체 `build -x test`로 캐시 워밍
5. `populateOfflineRepo` → `offline-repo/` 채움
6. `nsight.dependency.mode=offline` 설정
7. `--offline` 빌드로 검증

완료 후 **프로젝트 폴더 전체**를 USB/공유로 사내에 복사합니다.
(`offline-repo/`, `gradle/wrapper/*.zip` 포함)

## 사내(무인터넷) PC에서

```bat
tcf-scripts\offlinebuild\build-offline-run.bat
```

또는:

```bat
.\gradlew.bat build -x test --offline
```

JDK 21이 설치되어 있어야 합니다. (Gradle/의존성은 번들에 포함)

## 모드 전환

`gradle.properties`:

```properties
# 사내 무인터넷
nsight.dependency.mode=offline

# 집/개발 PC (offline-repo 우선, 없으면 Central)
nsight.dependency.mode=hybrid

# 항상 원격만
nsight.dependency.mode=online
```

## 수동 태스크

```bat
.\gradlew.bat populateOfflineRepo
.\gradlew.bat verifyOfflineRepo
```
