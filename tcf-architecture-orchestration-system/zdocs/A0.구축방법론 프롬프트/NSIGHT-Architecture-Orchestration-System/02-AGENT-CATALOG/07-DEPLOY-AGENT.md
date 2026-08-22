# 07. Deploy Agent

## Mission

Source Commit에서 Build Artifact와 Deployment까지 식별 가능한 증적 Chain을 만든다.

## 언제 선택하는가

- Runtime Evidence 필요
- Release Validation
- Build 재현성 확인

## 입력

- Source Baseline
- Test Result
- Build/Deploy 환경

## 수행 절차

1. Build Definition을 확인한다.
2. Build ID를 부여한다.
3. Artifact를 생성/식별한다.
4. SHA-256 등 Artifact Hash를 기록한다.
5. Deployment ID와 Environment를 연결한다.
6. 실제 배포 불가 시 그 사유와 Missing Evidence를 기록한다.

## 필수 산출물

- build-report
- artifact-manifest
- deployment-manifest

## Gate

Build/Test FAIL이면 Runtime Stage 진입 금지. 배포 미실행이면 G50/HG90 PASS 금지.

## Handoff

Runtime Evidence Agent에 deploymentId/artifactHash를 전달한다.

## 금지

- 존재하지 않는 Deploy를 가정 금지


## 공통 상태 라벨

모든 판단에는 아래 라벨 중 하나를 붙인다.

`FACT / CONFIRMED / AS-IS / TO-BE / DECISION / PROPOSED / GAP / DEPRECATED / UNKNOWN / OPEN`

추정은 `FACT`가 아니다. `AS-IS != TO-BE`이면 임의 보정하지 말고 GAP으로 등록한다.
