# 30-CODE

## 목적

Source/Config/Policy as Code

## 하위 영역

```text
inventory/
source-map/
config-map/
om-catalog/
policy-as-code/
generated/
```

## 운영 규칙

Generated와 actual source를 구분한다.

모든 Artifact는 `runId`, `systemScope`, `sourceBaselineId` 또는 관련 식별자를 포함해야 한다.
