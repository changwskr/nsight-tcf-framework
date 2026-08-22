# EOS GATE-RUN — Runtime Gate

> 판정일: 2026-08-16

## 판정: **PASS** (로컬)

| 항목 | 결과 |
|------|------|
| Port | 8082 |
| Profile | local |
| DB | H2 mem `pdmg_eos` MODE=Oracle |
| TCF | enabled |
| JWT | disabled |
| Security | permitAll (local) |
| 기동 | ✅ |
| 스모크 | ✅ GATE-T |

## 기동

```bat
cd pdmg-eos
gradlew.bat bootRun
```

## 스모크 스크립트

`docs/eos/06-test/smoke-curl.ps1`
