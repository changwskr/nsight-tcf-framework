# 98BC. Wave 3D Operator Checklist

## 공통

- [ ] Run ID / Environment / Build / Config Version 고정
- [ ] Hostname / JVM / ServiceId / GUID 수집 가능
- [ ] Production-like 승인 환경
- [ ] DB/APM/OM/로그 접근 승인
- [ ] Rollback/Recovery 절차 확보
- [ ] 장애/배포 Run은 Change Ticket + Approval Token 확보
- [ ] 결과 Bundle SHA-256 및 Timestamp 기록

## 순서 의존성

- [ ] RUN-P1200 완료 후 RUN-S1800
- [ ] RUN-S1800/HIKARI/SLOWSQL 결과 검토 후 RUN-N1
- [ ] Session ADR 후 RUN-SESSION
- [ ] DR Pair + RTO/RPO 승인 후 RUN-CF
- [ ] N-1/Capacity 확인 후 RUN-ROLLING
- [ ] Canonical JWT Key Provider/KMS-HSM 후 RUN-JWT-ROTATE

## 종료

- [ ] Machine Gate 결과 저장
- [ ] Human/ADR 승인 첨부
- [ ] Runtime Evidence Registry 연결
- [ ] Drift/GAP/ADR Register 갱신
