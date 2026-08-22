# NSIGHT Current Source Map

현재 업로드된 NSIGHT 저장소 구조를 Orchestration 관점에서 분류한다.

## NSIGHT TCF 플랫폼

```text
tcf-core
tcf-web
tcf-util
tcf-jwt
tcf-gateway
tcf-om
tcf-cache
tcf-eai
tcf-batch
tcf-cicd
tcf-ontology-service
tcf-harness*
```

## 주요 업무 서비스

```text
sv-service
ic-service
pc-service
mg-service
ms-service
pd-service
eb-service
ep-service
ss-service
av-service
ln-service
```

## 별도 System Scope

```text
pdmg-*
pdmk-*
pdmp-*
```

이들은 NSIGHT_TCF와 관련이 있지만 동일 AS-IS 구현으로 취급하지 않는다.

## Reference/Knowledge

```text
zarchitecture
zdocs-1
zdocs-2
zguide
zman
ztcfbook*
ztomcat
```

## 기본 제외

```text
**/build/**
**/bin/**
**/.gradle/**
**/target/**
**/logs/**
생성 Help 복제
과거 Diary/History
```

## 중요 현재 GAP Seed

1. Root `settings.gradle`, `build.gradle`, `gradlew` Baseline 부재/불명확
2. 22/24 Module 문서 차이
3. OM Handler 문서 24 vs Source 25
4. NSIGHT_TCF TransactionTemplate vs 정책기반 `@Transactional` 관계 재확정 필요
5. Timeout Worker unbounded cached thread pool
6. 전체 Payload `System.out` logging
7. Critical module test 부족
8. Session/Authz 설정과 Gateway bypass 방어 일관성
9. Runtime timeout 응답과 DB/EAI 실제 cancellation 보장
10. Runtime Evidence 없이 Final Gate가 통과 가능한 기존 Harness 구조
