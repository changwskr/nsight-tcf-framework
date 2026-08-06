# NSIGHT 자동 하네스 상세설계 패키지 파일 인덱스

- 총 파일 수: **39개** (인덱스·체크섬 포함)
- 기준일: **2026-08-05**
- 문자 인코딩: **UTF-8**

| 경로 | 설명 | 크기(Byte) | SHA-256 |
|---|---|---:|---|
| `README.md` | 패키지 사용안내와 공통 기술·통제 원칙 | 1,488 | `e3ea9763d10b1f5e…` |
| `api/openapi.yaml` | OpenAPI 3.1 기계판독 API 계약 | 12,319 | `88d3b1b52389dbce…` |
| `api/rest-api-spec.md` | Control Plane·Agent Internal API의 행위·보안·오류 기준 | 4,375 | `f3236395f922c86d…` |
| `architecture/module-package-structure.md` | Gradle 모듈, Java 패키지, 의존성 및 책임 경계 상세설계 | 4,850 | `bb0477c0ce84b41e…` |
| `database/db-table-definition.md` | 상태·메타모델·추적성·Gate·감사 테이블 논리 정의 | 1,606 | `9a4c6401577f4162…` |
| `database/oracle/01_harness_core_tables.sql` | Oracle 19c+ 물리 DDL·인덱스·기준정보 | 11,821 | `3edfbfe9b944afb5…` |
| `database/oracle/02_harness_trace_governance_tables.sql` | Oracle 19c+ 물리 DDL·인덱스·기준정보 | 15,604 | `dcdbb2b414d7b587…` |
| `database/oracle/03_harness_indexes.sql` | Oracle 19c+ 물리 DDL·인덱스·기준정보 | 2,350 | `c36bb13cbe9e927d…` |
| `database/oracle/04_harness_reference_data.sql` | Oracle 19c+ 물리 DDL·인덱스·기준정보 | 2,660 | `d17ed96be6fa67b4…` |
| `docs/superpowers/plans/2026-08-05-harness-platform-contracts.md` | TDD 기반 구현계획 | 13,702 | `b512661336bfea81…` |
| `gate-rules/hg00-input.yaml` | HG 단계별 Gate as Code 규칙 | 1,800 | `0c45e28078479d11…` |
| `gate-rules/hg10-baseline.yaml` | HG 단계별 Gate as Code 규칙 | 1,714 | `8d1488bf6d23bd17…` |
| `gate-rules/hg20-analysis.yaml` | HG 단계별 Gate as Code 규칙 | 2,326 | `69cecbeb2c076eec…` |
| `gate-rules/hg30-design.yaml` | HG 단계별 Gate as Code 규칙 | 3,116 | `441cadeb1f4ea41d…` |
| `gate-rules/hg40-implementation.yaml` | HG 단계별 Gate as Code 규칙 | 1,942 | `bdccfbfb451d8ad1…` |
| `gate-rules/hg50-build.yaml` | HG 단계별 Gate as Code 규칙 | 1,433 | `9de1e277cafba7d4…` |
| `gate-rules/hg60-test.yaml` | HG 단계별 Gate as Code 규칙 | 1,874 | `8a56abe0b9674c27…` |
| `gate-rules/hg70-security-quality.yaml` | HG 단계별 Gate as Code 규칙 | 2,591 | `f64ef66ed644a8af…` |
| `gate-rules/hg80-trace-drift.yaml` | HG 단계별 Gate as Code 규칙 | 2,682 | `3f99ef1a3626d697…` |
| `gate-rules/hg90-final.yaml` | HG 단계별 Gate as Code 규칙 | 2,473 | `ab66e1811f6894f8…` |
| `schemas/artifact-manifest.schema.json` | JSON Schema Draft 2020-12 계약 | 1,406 | `911031013d0e059f…` |
| `schemas/common-definitions.schema.json` | JSON Schema Draft 2020-12 계약 | 3,206 | `7a8e819f4e6888fd…` |
| `schemas/gate-result.schema.json` | JSON Schema Draft 2020-12 계약 | 2,189 | `5dd72634069f7e89…` |
| `schemas/gate-rule-set.schema.json` | JSON Schema Draft 2020-12 계약 | 4,151 | `405ec103ff3e3e0b…` |
| `schemas/requirement-register.schema.json` | JSON Schema Draft 2020-12 계약 | 2,191 | `1ef850baf72209bc…` |
| `schemas/run-create-request.schema.json` | JSON Schema Draft 2020-12 계약 | 1,159 | `9315fc9eb9cbb6e2…` |
| `schemas/task-input-envelope.schema.json` | JSON Schema Draft 2020-12 계약 | 3,800 | `fadd8572b5ebf99f…` |
| `schemas/task-output-envelope.schema.json` | JSON Schema Draft 2020-12 계약 | 2,588 | `dbd835c63317a131…` |
| `schemas/workflow-definition.schema.json` | JSON Schema Draft 2020-12 계약 | 4,836 | `18eb745a1c4d8dfe…` |
| `tools/validate_package.py` | JSON/YAML/Workflow/Gate/OpenAPI/DDL 교차 검증 도구 | 13,679 | `961a5e7aa0e4c961…` |
| `validation_report.txt` | 전체 패키지 자동검증 결과 | 252 | `34e3a18d47dff438…` |
| `workflows/wf-crud-v1.yaml` | 버전 관리되는 실행 Workflow 정의 | 4,906 | `210c9281b2df9a90…` |
| `workflows/wf-new-business-module-v1.yaml` | 버전 관리되는 실행 Workflow 정의 | 4,540 | `a39acf9134723e18…` |
| `workflows/wf-online-inquiry-v1.yaml` | 버전 관리되는 실행 Workflow 정의 | 11,892 | `2d1b308d4d7748cb…` |
| `workspace/templates/input-manifest.json` | 입력 Artifact Manifest JSON 예시 | 689 | `1ed53ca37e6a6dca…` |
| `workspace/templates/run.yaml` | HarnessRun 선언 템플릿 | 658 | `71ea9430caf49120…` |
| `workspace/workspace-layout.md` | Run별 격리 디렉터리, 권한, Promotion 및 복구 기준 | 4,757 | `1e0ff90cbd96d65d…` |
