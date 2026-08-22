# 50-RUNTIME-EVIDENCE

## 목적

Deployment/Runtime Evidence

## 하위 영역

```text
deploy/
transactions/
sql/
thread/
pool/
jvm/
timeout/
audit/
manifests/
```

## 운영 규칙

Build/Deploy/Trace Chain 필수.

모든 Artifact는 `runId`, `systemScope`, `sourceBaselineId` 또는 관련 식별자를 포함해야 한다.
