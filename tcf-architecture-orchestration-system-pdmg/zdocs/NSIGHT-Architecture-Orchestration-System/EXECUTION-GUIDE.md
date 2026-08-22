# PDMG Architecture Orchestration 완전 실행체계

## 1. 실행체계의 목적

이 구조는 `pdmg-ui`, `pdmg-fw`, `pdmg-service`, `pdmg-jwt` 네 프로젝트를 **Golden Reference의 원재료**로 사용하되, Source가 존재한다는 이유만으로 표준으로 자동 승격하지 않는다.

```text
L0 RAW SOURCE
   ↓ scan / evidence
L1 VERIFIED BASELINE
   ↓ rule / test / runtime / approval
L2 REFERENCE ARCHITECTURE
   ↓ conformance
TARGET PROJECT
   ↓
Drift / GAP / ADR / Approval / Release
```

## 2. 실행 엔진

`execution/pdmg_orchestrator.py`는 외부 Python 라이브러리를 요구하지 않는다.

| 명령 | 역할 |
|---|---|
| `init` | Workspace/Release 디렉터리 초기화 |
| `scan-reference` | PDMG 4개 프로젝트 Source/Config/Java/ServiceId/Mapper/Trace 스캔 |
| `create-target-run` | 대상 검증 Run 생성 |
| `scan-target` | Target Source를 PDMG Reference와 비교 |
| `run-check` | 실제 Build/Test/Security/Architecture 검사 실행 증적 |
| `artifact` | WAR/JAR 등 Artifact SHA-256 고정 |
| `record-deployment` | Artifact가 어느 환경에 배포됐는지 기록 |
| `import-runtime` | 실제 Runtime Evidence 복사 및 SHA-256 Manifest 생성 |
| `register-gap` / `resolve` | GAP/Drift 등록 및 해결상태 관리 |
| `create-adr` | PENDING 상태의 ADR Draft 생성 |
| `require-approval` | 필수 Approval과 승인 Role/Artifact Hash 등록 |
| `approve` | Artifact Hash에 바인딩된 Human Approval 생성 |
| `evaluate` | Evaluator 기반 Gate 판정 |
| `release` | Final Gate PASS일 때만 새 Baseline 발급 |
| `impact` | 변경파일 기반 ServiceId 영향 분석 |
| `status` | Run 내부 Artifact 조회 |
| `validate` | Orchestration package 자체 점검 |

## 3. Source Scanner

기본 제외 디렉터리:

```text
build/
bin/
.gradle/
target/
logs/
generated/
history/
duplicate/
node_modules/
```

Scanner는 다음을 추출한다.

```text
Git branch / commit (없으면 UNKNOWN)
Java Toolchain
Spring Boot version
Gradle wrapper version
Java package / class / import / annotation
ServiceId / Handler ownership
Mapper namespace / SQL ID
SQL Table 후보
Component import relation
ServiceId static trace
```

`STATIC_IMPORT_GRAPH`는 Runtime 호출을 확정하는 증거가 아니다. G50 Runtime Evidence와 비교하기 위한 정적 Source Evidence다.

## 4. Reference Rule 승격 원칙

자동 추출 규칙은 기본 상태가 `CANDIDATE`다.

```text
RAW Source Fact
  ↓
Candidate Rule
  ↓
PDMG 내부 상호검증
  ↓
Build/Test/Security/Architecture Test
  ↓
Runtime Evidence
  ↓
Drift/GAP 해소
  ↓
Human Approval
  ↓
RHG90
  ↓
REFERENCE
```

## 5. Gate Engine

Gate는 사람이 `PASS` 문자열을 넣어 통과시키는 구조가 아니다.

```text
Evaluator
   ↓
Measured Value
   ↓
Threshold
   ↓
Result
   ↓
Gate Decision
```

지원 Evaluator:

- `file_exists`
- `json_field_equals`
- `json_fields_equal`
- `runtime_present`
- `artifact_hash_present`
- `critical_open_zero`
- `approval_valid`
- `approval_register_valid`
- `count_at_least`

최종 `RHG90/HG90`에서는 다음이 Hard Blocker다.

- Document/Model/Source Mapping 부재
- Build/Test/Security/Architecture 검사 실패 또는 미실행
- Artifact Hash 부재
- Deployment Evidence 부재
- Tested Artifact와 Deployed Artifact Hash 불일치
- Runtime Evidence 부재
- Critical Drift OPEN
- Critical GAP OPEN
- Hash-valid Human Approval 부재

## 6. Human Approval 무결성

Approval은 다음을 묶는다.

```text
Approval ID
Artifact Path
Artifact SHA-256
Approver
Role
Decision
ApprovedAt
Comment
Expiration
```

Approval 후 Artifact가 변경되면 Gate의 `approval_valid` Evaluator가 실패한다.

## 7. Release 불변성

`release`는 Final Gate가 `PASS`일 때만 새 디렉터리를 만든다.

```text
10-RELEASES/
├─ REFERENCE/PDMG-REF-*/
└─ TARGET/PDMG-TGT-*/
```

Release에는 Run의 Evidence Package와 파일별 SHA-256을 저장한다. 이전 Baseline ID는 `previousBaseline`으로 연결하며 과거 Evidence Package를 수정하지 않는다.

## 8. Runtime Evidence 경계

이 Harness는 Runtime이 없을 때 Runtime을 가정하지 않는다.

- Runtime 파일 미입력 → `runtimeEvidencePresent=false/파일없음`
- G50/RG50 → HOLD
- HG90/RHG90 → HOLD
- Static Source 분석과 Drift Candidate 분석은 계속 가능

## 9. 운영 권장 순서

```text
REFERENCE_BOOTSTRAP
→ RG00/RG10/RG20/RG30
→ Build/Test/Security/Architecture Test
→ RG40
→ Deploy + Runtime
→ RG50
→ Drift/GAP
→ RG60/RG70
→ Human Approval
→ RG80
→ RHG90
→ Reference Release

Target Mission
→ G00~G30
→ Build/Test
→ G40
→ Deploy/Runtime
→ G50
→ Drift/GAP/ADR
→ G60/G70
→ Approval
→ G80
→ HG90
→ Target Baseline Release
```
