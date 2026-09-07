# 별첨 J. DevOps / Artifact / Deployment

## J.1 Current Source/Build

```text
Java 21
Spring Boot 3.5.14
Gradle Multi-project

pdmg-service → pdmg-fw
pdmg-jwt     → pdmg-fw
```

## J.2 Target Promotion

```text
Source Commit
 ↓
Build
 ↓
ArtifactHash
 ↓
DEV
 ↓
TEST
 ↓
PROD
 ↓
DR

Build Once / Promote Same Binary
```

## J.3 Deployment Identity

```text
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
JVM / Host
 ↓
ServiceId / GUID
```

CI/CD Orchestrator는 Decision Register에서 OPEN 항목으로 유지한다.
