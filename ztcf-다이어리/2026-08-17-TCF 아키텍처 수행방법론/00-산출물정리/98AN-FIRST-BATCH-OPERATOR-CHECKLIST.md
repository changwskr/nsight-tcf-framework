# Wave3C First Batch Operator Checklist

## A. 공통

- [ ] Change/Test Ticket
- [ ] Environment Owner
- [ ] Application Owner
- [ ] DBA
- [ ] Test Operator
- [ ] Start/Stop Time
- [ ] Git Commit
- [ ] Artifact Version
- [ ] Config Version
- [ ] Hostname
- [ ] Tomcat JVM Instance
- [ ] DB Target
- [ ] ServiceId
- [ ] Request Body Version
- [ ] GUID/RunId Trace 확인

## B. RUN-TIMEOUT

- [ ] Test-only ServiceId
- [ ] DB < TX < Online < Client
- [ ] DB Before
- [ ] Pool Before
- [ ] Thread/Context Before
- [ ] Client Timeout Probe
- [ ] 2× Online Timeout 대기
- [ ] DB After
- [ ] TX Log
- [ ] Pool After
- [ ] Worker Returned
- [ ] Context Leak 0
- [ ] Machine Evaluate
- [ ] Human Approval

## C. RUN-P600

- [ ] RUN-TIMEOUT Safety Issue 없음
- [ ] Load Generator 준비
- [ ] Metrics 수집 준비
- [ ] 600 TPS 실행
- [ ] JTL 저장
- [ ] Summary 변환
- [ ] p95 ≤ 3초
- [ ] Error/Timeout Review
- [ ] Resource Review
- [ ] Human Approval

## D. RUN-P1200

- [ ] P600 승인
- [ ] P600/P1200 Artifact/Config 비교
- [ ] 1,200 TPS 실행
- [ ] JTL 저장
- [ ] Summary 변환
- [ ] p95 ≤ 3초
- [ ] P600 대비 Regression 검토
- [ ] Error/Timeout Review
- [ ] Resource Review
- [ ] Human Approval
