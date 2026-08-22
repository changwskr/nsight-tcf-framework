---
document-status: AS-IS
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# PDMG SOURCE BASELINE


```yaml
baselineId: PDMG-SRC-20260817-SEED
promotionLevel: RAW
branch: UNKNOWN
commit: UNKNOWN
referenceProjects:
  - pdmg-ui
  - pdmg-fw
  - pdmg-service
  - pdmg-jwt
technology:
  java: 21
  springBoot: 3.5.14
  gradleWrapper: 8.10.1
excludedFromSoT:
  - build/**
  - bin/**
  - .gradle/**
  - target/**
  - logs/**
  - generated/**
  - history/**
  - duplicate/**
```

## Source 확인 사항

- `pdmg-ui`: `settings.gradle`의 rootProject `pdmg-ui`, Java 21, Boot 3.5.14, `pdmg-ui.jar`.
- `pdmg-fw`: `java-library`, `bootJar=false`, `jar=true`, TCF/Timeout/Dispatcher 공통 코드.
- `pdmg-service`: `war`, `bootWar=false`, `pdmg-fw` sibling include, 업무 Layer 및 MyBatis.
- `pdmg-jwt`: `war`, `bootWar=false`, `pdmg-fw` sibling include, Nimbus JOSE JWT 기반 RS256/JWKS 구성.

이 Seed는 RAW Source Inventory이며 아직 `PDMG-REF-*` 공식 Reference Baseline이 아니다.
