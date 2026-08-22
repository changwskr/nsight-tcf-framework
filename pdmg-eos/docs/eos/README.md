# docs / eos 산출물 README

`AGENT.md` §59 디렉터리. **업무 내용의 1차 근거**는 상위 `analysys/`, **설계 작성 방식의 근거**는 상위 `design/` 마스터프롬프트이다.  
`eos/`는 그 둘(+ `EOS-RULES`/`AGENT`)을 따라 Gate·설계서·구현로그를 쌓는 실행 산출물 루트다.

| 원천 | 역할 |
|------|------|
| `docs/analysys/` | Excel·요건정의·종합정리 → 도메인/화면ID/P0 범위 |
| `docs/design/` | 화면·DB·서비스 설계서 **작성 마스터프롬프트** → `eos/02~04` 작성 가이드 |
| `docs/implementation/` | 구현 마스터프롬프트 → `eos/07-implementation` |
| `docs/AGENT.md` + `EOS-RULES.md` | 파이프라인·불변 규칙 |

| 폴더 | 용도 |
|------|------|
| `00-source` | 원본 요약·Inventory |
| `01-requirements` | 요건 인덱스·GATE-R |
| `02-screen` | 네비게이션·화면설계·GATE-U |
| `03-data` | 논리/물리 DB·DDL·GATE-D |
| `04-service` | 서비스 매트릭스·DTO·GATE-S |
| `05-adr` | Architecture Decision |
| `06-test` | 테스트 계획·결과 |
| `07-implementation` | 소스분석·GAP·구현계획·검증 |

세션 상태: [00-AGENT-STATUS.md](./00-AGENT-STATUS.md)

## 진행 관리 에이전트

| 항목 | 위치 |
|------|------|
| Skill | 저장소 `.cursor/skills/eos-progress-manager/` |
| Rule | `.cursor/rules/eos-development.mdc` (`pdmg-eos/**`) |
| Module AGENTS | `pdmg-eos/AGENTS.md` |

「진행해」/EOS 상태 질문 시 위 스킬이 `00-AGENT-STATUS.md`를 읽고 다음 Wave만 진행·갱신한다.
