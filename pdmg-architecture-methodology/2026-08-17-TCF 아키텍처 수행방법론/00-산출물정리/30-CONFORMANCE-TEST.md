# NSIGHT Conformance Test — G80

## 1. 판정 원칙

`PASS`는 현재 확보된 Evidence Scope에서만 유효하다. `STATIC PASS`를 Runtime PASS로 확대 해석하지 않는다.

## 2. G80 Conformance Result

| Test ID | Rule/Test | Evidence Type | Result | Evidence |
|---|---|---|---|---|
| CT-R1-001 | ServiceId Unique per Module Runtime Scope | STATIC | PASS | 121 mappings / duplicate module 0 |
| CT-R1-003 | Handler direct DAO import prohibition | STATIC | PASS | direct DAO import 0 |
| CT-R1-003B | Handler direct Mapper import prohibition | STATIC | PASS | direct Mapper import 0 |
| CT-DOMAIN-001 | Cross-domain Handler import | STATIC | PASS | candidate 0 |
| CT-R2-005 | Facade Transaction Boundary adoption | STATIC | PASS_WITH_EXCEPTION | Facade 50 중 @Transactional 50; Service TX 4: eb-service:EbUserService, ep-service:EpUserEventService, tcf-oc:CapNewApprovalService, tcf-oc:CapNewWizardService |
| CT-R2-008 | Dispatcher duplicate fail-fast | SOURCE | PASS | TransactionDispatcher가 put 이전값 존재 시 IllegalStateException 발생 |
| CT-R3-001 | JWT Signing Key external SoT | SOURCE | FAIL | JwtKeyConfiguration이 RSAKeyGenerator(2048)로 process-local key 생성; KMS/HSM 참조 없음 |
| CT-R3-002 | JWT kid rotation | SOURCE | FAIL | 고정 KEY_ID 사용; version/rotation evidence 없음 |
| CT-R4-001 | GUID+ServiceId E2E Runtime Trace | RUNTIME | NOT_RUN | Runtime trace bundle 없음 |
| CT-R4-003 | Timeout cancel/rollback/connection return | RUNTIME | NOT_RUN | Fault injection evidence 없음 |
| CT-R4-004 | Peak/Stress runtime metrics | RUNTIME | NOT_RUN | 600/1200/1800 TPS 원본 결과 없음 |
| CT-R4-008 | N-1/Center Failover | RUNTIME | NOT_RUN | Failover/Failback evidence 없음 |
| CT-INFRA-002 | 71 Server→JVM→WAR mapping | EVIDENCE | OPEN | 전수 runtime/deployment mapping 미완료 |
| CT-INFRA-003 | Apache→Tomcat actual routing | CONFIG | OPEN | httpd.conf/server.xml 실설정 원본 연결 미완료 |
| CT-GOV-002 | Architecture Model schema validation | MODEL | PARTIAL | Draft model 380 nodes / 380 edges; full schema gate 미완료 |
| CT-GOV-003 | Runtime Evidence required | GATE | PASS_POLICY | Runtime 미확보 항목에 PASS를 부여하지 않도록 G80 HOLD 적용 |
| CT-OBS-LOG | TCF console output policy | STATIC | FAIL_CANDIDATE | tcf-core System.out 포함 파일 5: TransactionDispatcher.java, ETF.java, STF.java, TCF.java, AuthenticationContextValidator.java |

## 3. G80 정적 스캔 기준

- Source ZIP: `nsight-tcf-framework (2).zip`
- `src/main/java/com/nh/nsight/**` 중심, generated/examples/ref 제외
- Runtime/Build/Deployment Test는 실제 실행 증적이 제공되지 않아 `NOT_RUN/OPEN` 처리

## 4. Mandatory Runtime Runs

| Run | 목적 | G80 상태 |
|---|---|---|
| RUN-P600 | 600 TPS General Peak | NOT_RUN |
| RUN-P1200 | 1,200 TPS Design Peak | NOT_RUN |
| RUN-S1800 | 1,800 TPS Stress | NOT_RUN |
| RUN-N1 | AP N-1 | NOT_RUN |
| RUN-CF | Center Failure/Failback | NOT_RUN |
| RUN-TIMEOUT | Timeout cancel/rollback/connection return | NOT_RUN |
| RUN-SESSION | Session Failover | NOT_RUN |
| RUN-ROLLING | Rolling deploy residual capacity | NOT_RUN |
| RUN-TRACE | GUID+ServiceId E2E Trace | NOT_RUN |

## 5. 결론

정적 구조 Conformance는 일부 강하게 확인되었지만 Security Target와 Runtime Evidence가 닫히지 않았다. 따라서 G80 전체는 `HOLD`가 적절하다.
