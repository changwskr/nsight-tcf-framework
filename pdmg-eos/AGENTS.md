# pdmg-eos Agent

EOS 자원관리 모듈 작업 시:

1. 스킬 **eos-progress-manager** 를 적용한다 (`진행해`, 상태 확인, Wave/Gate 진행).
2. 개발 기준은 **`docs/eos/`** 이다. `docs/analysys/`, `docs/design/` 은 상위 원천이며 eos 산출물을 뒤엎지 않는다.
3. 세션 상태 SSOT: [`docs/eos/00-AGENT-STATUS.md`](docs/eos/00-AGENT-STATUS.md)
4. 불변 규칙: [`docs/EOS-RULES.md`](docs/EOS-RULES.md), [`docs/AGENT.md`](docs/AGENT.md)
5. 구현 Reference: `pdmg-service` 의 `mgcoa9000*` 패턴

상세 체크리스트: 저장소 루트 `.cursor/skills/eos-progress-manager/checklist.md`
