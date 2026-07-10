# tcf-h2

로컬에서 `nsight_om` H2 TCP 서버를 별도 프로세스로 실행하기 위한 래퍼 모듈입니다.

실제 H2 기동은 루트의 `ztomcat/h2-txlog.ps1`를 재사용합니다.

## 사용법

PowerShell:

```powershell
.\tcf-h2\scripts\run-h2.ps1 start
.\tcf-h2\scripts\run-h2.ps1 status
.\tcf-h2\scripts\run-h2.ps1 stop
```

Batch:

```bat
tcf-h2\scripts\run-h2.bat start
tcf-h2\scripts\run-h2.bat status
tcf-h2\scripts\run-h2.bat stop
```

## 기본 포트

- `127.0.0.1:9092`

## 참고

- 데이터 경로 기본값: `data/nsight-txlog`
- 데이터소스 URL 예시:

```text
jdbc:h2:tcp://127.0.0.1:9092/./nsight_om;MODE=Oracle;DATABASE_TO_UPPER=false
```
