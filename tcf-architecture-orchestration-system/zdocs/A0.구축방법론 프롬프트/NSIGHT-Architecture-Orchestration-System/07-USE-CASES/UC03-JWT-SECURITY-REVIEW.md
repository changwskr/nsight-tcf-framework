# UC03 — JWT Security Review

## Mission
Token Issuer, Private Key, JWKS/Public Key, Gateway/WAR 검증, Refresh/Revoke, bypass 방어를 검증한다.

## Team
Document → Source → Code Rule → Test → Runtime(가능 시) → Drift → GAP/ADR

## Critical Rules
- Private Key issuer 밖 존재 금지
- JWT URL 전달 금지
- issuer/audience/exp 검증
- Gateway bypass 시 WAR 검증
- revoke/denylist 실제 적용 확인
