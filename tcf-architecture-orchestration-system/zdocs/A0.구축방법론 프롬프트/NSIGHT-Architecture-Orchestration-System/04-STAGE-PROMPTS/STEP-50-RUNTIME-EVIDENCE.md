# STEP 50 — Runtime Evidence Prompt

## 목표
실제 Runtime이 Architecture 약속과 일치하는지 증명한다.

## Chain
Baseline→ModelVersion→Commit→Build→ArtifactHash→Deploy→ServiceId→TraceId→Evidence.

## 수집
TX/SQL/Thread/Hikari/JVM/Timeout/Security/Audit.

## 원칙
Runtime 불가 시 `HOLD`, Observability만으로 PASS 금지.

## 결과
Evidence Manifest, Runtime Report, G50.
