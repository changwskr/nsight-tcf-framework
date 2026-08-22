# PDMG Architecture Orchestration — Quick Start

이 패키지는 문서 템플릿만이 아니라 `execution/pdmg_orchestrator.py`를 중심으로 실제 Run을 생성하고 Evidence/Gate/Baseline을 관리하는 실행체계다.

## 1. 요구환경

- Python 3.11 이상
- Reference Source Repository 안에 다음 4개 디렉터리
  - `pdmg-ui`
  - `pdmg-fw`
  - `pdmg-service`
  - `pdmg-jwt`
- 실제 Build/Test를 실행하려면 해당 프로젝트가 요구하는 JDK/Gradle/DB/외부환경이 별도 필요하다.

## 2. 초기화

Linux/macOS:

```bash
./execution/bin/pdmg-orchestrator init
```

Windows:

```bat
execution\bin\pdmg-orchestrator.bat init
```

## 3. PDMG Reference Run 생성

```bash
./execution/bin/pdmg-orchestrator scan-reference \
  --repo /path/to/nsight-tcf-framework \
  --mission "PDMG 4개 기준 프로젝트 Reference Baseline 구축"
```

출력된 `REF-RUN-*`을 이후 `<RUN_ID>`로 사용한다.

자동 생성되는 주요 증적:

```text
03-WORKSPACE/RUNS/<RUN_ID>/
├─ 00-SOURCE/
│  ├─ source-baseline.json
│  ├─ source-inventory.json
│  ├─ config-inventory.json
│  ├─ serviceid-index.json
│  ├─ mapper-sql-index.json
│  ├─ component-relations.json
│  └─ traceability.json
├─ 10-DOCUMENT/CURRENT-ARCHITECTURE.md
├─ 20-MODEL/reference-baseline-draft.json
├─ 30-CONFORMANCE/reference-rules.json
├─ 40-TEST/conformance-result.json
├─ 60-DRIFT/DRIFT-REGISTER.json
└─ 70-GAP-ADR/GAP-REGISTER.json
```

## 4. 초기 Gate 확인

```bash
./execution/bin/pdmg-orchestrator evaluate --run <RUN_ID> --gate RG00
./execution/bin/pdmg-orchestrator evaluate --run <RUN_ID> --gate RG20
./execution/bin/pdmg-orchestrator evaluate --run <RUN_ID> --gate RG30
```

## 5. 실제 Build/Test/Security/Architecture Test Evidence

실제 명령을 실행하고 Exit Code와 출력을 Evidence로 저장한다.

```bash
./execution/bin/pdmg-orchestrator run-check \
  --run <RUN_ID> --type build --cwd /path/to/pdmg-service \
  -- ./gradlew clean build

./execution/bin/pdmg-orchestrator run-check \
  --run <RUN_ID> --type test --cwd /path/to/pdmg-service \
  -- ./gradlew test
```

내장 Static Architecture/Security 검사기는 바로 실행할 수 있다.

```bash
./execution/bin/pdmg-orchestrator static-check --run <RUN_ID> --type architecture
./execution/bin/pdmg-orchestrator static-check --run <RUN_ID> --type security
```

조직 고유의 추가 검사기가 있다면 `run-check --type security|architecture`로 외부 명령도 Evidence로 기록할 수 있다.

명령이 실행되지 않거나 Exit Code가 0이 아니면 Gate는 PASS되지 않는다.

## 6. Artifact Hash 고정

```bash
./execution/bin/pdmg-orchestrator artifact \
  --run <RUN_ID> \
  --file /path/to/build/libs/pdmg-service.war \
  --build-id BUILD-20260817-001
```

## 7. Deployment 기록

```bash
./execution/bin/pdmg-orchestrator record-deployment \
  --run <RUN_ID> \
  --deployment-id DEPLOY-SIT-001 \
  --environment SIT \
  --status DEPLOYED
```

Artifact Hash를 생략하면 Run의 `artifact-manifest.json` 값을 사용한다.

## 8. Runtime Evidence 입력

실제 로그/Trace/Runtime JSON 파일을 지정한다.

```bash
./execution/bin/pdmg-orchestrator import-runtime \
  --run <RUN_ID> \
  --files /evidence/runtime.json /evidence/tx.log \
  --service-id mgcoa8888S0 \
  --trace-id TRACE-001 \
  --deployment-id DEPLOY-SIT-001
```

Runtime 환경이 없으면 이 명령을 실행한 것으로 꾸미지 않는다. `RG50/HG90`은 HOLD 상태가 정상이다.

## 9. Human Approval

먼저 필수 승인 대상을 등록하고 그 다음 실제 승인을 생성한다.

```bash
./execution/bin/pdmg-orchestrator require-approval \
  --run <RUN_ID> \
  --artifact 03-WORKSPACE/RUNS/<RUN_ID>/20-MODEL/reference-baseline-draft.json \
  --approval-id APR-PDMG-REF-001 \
  --role ArchitectureBoard \
  --reason "PDMG Reference Baseline 승격"

./execution/bin/pdmg-orchestrator approve \
  --run <RUN_ID> \
  --artifact 03-WORKSPACE/RUNS/<RUN_ID>/20-MODEL/reference-baseline-draft.json \
  --approval-id APR-PDMG-REF-001 \
  --approver "홍길동" \
  --role ArchitectureBoard \
  --decision APPROVED \
  --comment "Reference baseline 승인"
```

`REQUIRED-APPROVALS.json`의 모든 필수 Approval이 유효해야 G80/RG80 및 최종 Gate가 통과한다. 승인 뒤 Artifact가 바뀌거나 승인 Role이 요청 Role과 다르면 승인은 무효다.

## 10. 최종 Reference Gate와 Release

```bash
./execution/bin/pdmg-orchestrator evaluate --run <RUN_ID> --gate RHG90
./execution/bin/pdmg-orchestrator release --run <RUN_ID>
```

`RHG90=PASS`가 아니면 `release` 명령은 실패한다.

## 11. Target 프로젝트 검증

```bash
./execution/bin/pdmg-orchestrator create-target-run \
  --repo /path/to/target-repo \
  --reference PDMG-REF-20260817-120000 \
  --projects sv-service \
  --mission "sv-service PDMG Reference Conformance 검증"
```

출력된 `TGT-RUN-*`에 대해:

```bash
./execution/bin/pdmg-orchestrator scan-target --run <TGT_RUN_ID>
./execution/bin/pdmg-orchestrator evaluate --run <TGT_RUN_ID> --gate G30
```

이후 Build/Test/Deploy/Runtime/Approval을 동일하게 수집한 뒤 `HG90`과 `release`를 실행한다.

## 12. Continuous Architecture 영향분석

```bash
./execution/bin/pdmg-orchestrator impact \
  --run <RUN_ID> \
  --changed pdmg-service/src/main/java/.../mgcoa8888Handler.java
```

Traceability 인덱스를 이용해 영향 ServiceId와 `INCREMENTAL/BROAD` 재검증 전략을 생성한다.
