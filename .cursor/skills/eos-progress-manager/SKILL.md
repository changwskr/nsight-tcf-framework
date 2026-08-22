---
name: eos-progress-manager
description: >-
  Manages pdmg-eos delivery progress against docs/eos gates and implementation
  waves. Use when the user says 진행해, 자동관리, 계속 진행, EOS 진행, EOS 상태,
  Wave, GATE, next step, or asks to continue/resume EOS work in pdmg-eos.
  Auto-manage mode completes the current Wave (and may continue) in one turn.
---

# EOS Progress Manager

진행 관리 전담. 구현 세부보다 **기준 준수·다음 할 일·상태 갱신**을 책임진다.

## 1. Single source of truth

| 역할 | 경로 |
|------|------|
| 세션/파이프라인 상태 | `pdmg-eos/docs/eos/00-AGENT-STATUS.md` |
| 구현 Wave 계획 | `pdmg-eos/docs/eos/07-implementation/02-implementation-plan.md` |
| 개발 직접 기준 | `pdmg-eos/docs/eos/` (02-screen · 03-data · 04-service) |
| 상위 원천(재해석 금지) | `pdmg-eos/docs/analysys/`, `design/` — 이미 eos에 반영됨 |
| 불변 규칙 | `pdmg-eos/docs/EOS-RULES.md`, `AGENT.md` |
| Open 결정 | `pdmg-eos/docs/eos/05-adr/ADR-001-open-decisions.md` |

**코드/설계 충돌 시:** `docs/eos/` Gate 산출물을 따른다. analysys/design을 다시 풀어 설계를 뒤집지 않는다.

## 2. Turn protocol (매 턴)

사용자가 「진행해」/EOS Continuations 이면:

1. **Read** `00-AGENT-STATUS.md` (Pipeline + 현재 모드)
2. **Decide** 다음 목표
3. **Announce** 한국어 한 줄: `다음: W1 — eoscoa0130*` 형식
4. **Execute**
5. **Update** `00-AGENT-STATUS.md`
6. **Report** 완료 / NEXT / Open

### 자동관리 모드

트리거: `자동관리`, `계속 진행`, `자동으로 진행`, `eos 에이전트가 자동`

이 모드에서는 **현재 Wave를 막힐 때까지** 같은 턴에서 연속 구현한다.

- W1이면 `0120` → `0130*` → `0140*` 순으로 가능한 한 완료
- Wave 경계에서 STATUS 갱신 후, 같은 턴에 다음 Wave를 시작해도 됨 (사용자가 자동관리를 명시한 경우)
- compile 실패 시 그 지점에서 멈추고 보고
- 커밋은 하지 않음 (사용자 요청 시에만)

일반 「진행해」만이면 Wave 안 **주요 Service 묶음 1개**(예: 0130 CRUD 전체)까지.  
「자동관리」면 Wave 완료를 목표로 연속 진행.

## 3. Mode map

| 모드 | 의미 | 다음 행동 |
|------|------|-----------|
| `SCREEN_DESIGN` 등 설계 | Gate 전 | 해당 Gate 문서 완성 |
| `IMPLEMENTATION` | W0~W6 | 계획 Wave 순서 |
| Blocked | CONF/Human | ADR Default로 갈지 질문 1회 후 진행 |

설계 Gate(R/U/D/S)는 이미 CONDITIONAL PASS면 **재개설하지 말고** 구현 Wave로 간다.

## 4. Wave checklist

상세: [checklist.md](checklist.md)

```text
W0 schema/seed     → W1 0120/0130/0140 → W2 0110/0150
→ W3 0160/0165 → W4 0170/0180/0190 → W5 0141/0151 → W6 cleanup/test
```

Wave 종료 조건: compile 성공 + 해당 ServiceId 스모크 가능 + STATUS에 DONE 기록.

## 5. Hard rules (요약)

- 서버가 계산: 상태·위험점수·등급·승인자·감사자
- PRODUCT / VERSION / RESOURCE 분리 (단일 ASSET 테이블로 회귀 금지)
- 예외 SoD, 조치 상태전이 서비스화
- UI(`pdmg-ui`)는 UX-003 확정 전 비범위 가능
- 커밋은 사용자 요청 시에만

## 6. User replies (한국어)

- 상태 질문 → STATUS Pipeline 블록만 요약
- 「진행해」 → protocol 실행
- 범위 밖(P1 수집/월간보고 UI 등) → 비범위 한 줄 + 현재 Wave로 복귀

## 7. Do not

- analysys를 다시 분석하느라 구현을 멈춤
- Gate CONDITIONAL PASS를 FAIL로 되돌림 (새 결함 발견 시에만 HOLD 제안)
- STATUS 미갱신으로 세션 종료
