# Step 3: qa-war

## 읽어야 할 파일

- step0~2 산출물
- `../pdmp-service/script/build.bat`

## 작업

전체 테스트와 WAR 빌드를 실행하고 `phases/1-mpcoa8888-stabilize/qa-report.md`에 명령·exit code·요약을 기록한다. H2 증거와 Oracle 미검증을 구분한다.

## Acceptance Criteria

```bash
cd ../pdmp-service
.\gradlew.bat test
.\gradlew.bat war
```

## 금지사항

- 실패를 숨기기 위해 테스트를 삭제하지 마라
