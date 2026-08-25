package nhnis.fw.tcf.timeout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import nhnis.fw.mybatis.StatementTimeoutResolver;
import nhnis.fw.tcf.execution.ExecutionDeadline;
import nhnis.fw.tcf.execution.ExecutionDeadlineContext;
import nhnis.fw.tcf.execution.OnlineExecutionEvidenceRegistry;
import nhnis.fw.tcf.execution.OnlineTransactionPolicyProperties;
import nhnis.fw.tcf.execution.PropertiesTransactionPolicyResolver;
import nhnis.fw.tcf.execution.TransactionManagerRegistry;
import nhnis.fw.tcf.execution.TransactionMode;
import nhnis.fw.tcf.execution.TransactionPolicy;
import nhnis.fw.tcf.execution.TransactionPolicyResolver;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;

/**
 * 타임아웃 개선 전체 작업에 대한 최종 회귀 점검.
 *
 * <pre>
 * TC-01 정상 트랜잭션 (commit, no interrupt)
 * TC-02 서비스 override 예산
 * TC-03 타임아웃 예외 + rollback
 * TC-04 JDBC Statement.cancel
 * TC-05 sub-second QueryTimeout 보완 판정
 * TC-06 Worker interrupt + flag
 * TC-07 interrupt 후 성공값 반환해도 commit 금지
 * TC-08 mgcoa5530S0 계약 (10s / RDW_READ_ONLY)
 * TC-09 TX timeout 초단위 반영
 * </pre>
 */
@DisplayName("온라인 타임아웃 최종 회귀")
class OnlineTimeoutFinalRegressionTest {

    private static final String SVC = "finalRegS0";
    private static final String GUID = "guid-final-reg";

    private ThreadPoolTaskExecutor taskExecutor;
    private OnlineTimeoutProperties properties;
    private OnlineTransactionPolicyProperties policyProperties;
    private TransactionPolicyResolver policyResolver;
    private RecordingTransactionManager transactionManager;
    private TransactionManagerRegistry transactionManagerRegistry;
    private OnlineExecutionEvidenceRegistry evidenceRegistry;
    private ActiveJdbcStatementRegistry statementRegistry;
    private ScheduledExecutorService deadlineCancelScheduler;
    private DefaultOnlineTimeoutExecutor executor;

    @BeforeEach
    void setUp() {
        properties = new OnlineTimeoutProperties();
        properties.setEnabled(true);
        properties.setMilliseconds(300);
        properties.setMinStartBudgetMs(30);
        properties.setPoolSize(4);
        properties.setQueueCapacity(4);
        properties.setSqlSafetyTimeoutSeconds(10);
        properties.setOverrides(Map.of("mgcoa5530S0", 10000L));

        policyProperties = new OnlineTransactionPolicyProperties();
        policyProperties.setDefaultManager("rdwTransactionManager");
        policyProperties.setDefaultMode(TransactionMode.RDW_READ_WRITE);
        OnlineTransactionPolicyProperties.ServicePolicy s5530 =
                new OnlineTransactionPolicyProperties.ServicePolicy();
        s5530.setMode(TransactionMode.RDW_READ_ONLY);
        policyProperties.getServices().put("mgcoa5530S0", s5530);
        policyResolver = new PropertiesTransactionPolicyResolver(policyProperties);

        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("final-reg-");
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(4);
        taskExecutor.initialize();

        transactionManager = new RecordingTransactionManager();
        transactionManagerRegistry = new TransactionManagerRegistry(
                Map.of("rdwTransactionManager", transactionManager));
        evidenceRegistry = new OnlineExecutionEvidenceRegistry();
        statementRegistry = new ActiveJdbcStatementRegistry();
        deadlineCancelScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "final-reg-jdbc-cancel");
            t.setDaemon(true);
            return t;
        });
        executor = new DefaultOnlineTimeoutExecutor(
                properties,
                taskExecutor,
                policyResolver,
                transactionManagerRegistry,
                evidenceRegistry,
                statementRegistry,
                deadlineCancelScheduler);

        ThreadContext.put("serviceId", SVC);
        ThreadContext.put("guid", GUID);
    }

    @AfterEach
    void tearDown() {
        ExecutionDeadlineContext.clear();
        ThreadContext.clearAll();
        taskExecutor.shutdown();
        deadlineCancelScheduler.shutdownNow();
    }

    @Nested
    @DisplayName("TC-01/02 정상 트랜잭션")
    class NormalPath {

        @Test
        @DisplayName("TC-01 예산 내 완료 → commit, Worker interrupt 없음")
        void tc01_normalCommit() throws Exception {
            AtomicBoolean interrupted = new AtomicBoolean(false);
            String result = executor.execute(() -> {
                interrupted.set(Thread.currentThread().isInterrupted());
                Thread.sleep(40);
                interrupted.set(interrupted.get() || Thread.currentThread().isInterrupted());
                return "ok";
            });
            assertThat(result).isEqualTo("ok");
            assertThat(interrupted.get()).isFalse();
            assertThat(transactionManager.commitCount.get()).isEqualTo(1);
            assertThat(transactionManager.rollbackCount.get()).isZero();
        }

        @Test
        @DisplayName("TC-02 serviceId override 예산으로 정상 완료")
        void tc02_overrideBudgetSuccess() throws Exception {
            properties.setMilliseconds(80);
            properties.setOverrides(Map.of("slowS0", 450L));
            ThreadContext.put("serviceId", "slowS0");
            String result = executor.execute(() -> {
                Thread.sleep(160);
                return "override-ok";
            });
            assertThat(result).isEqualTo("override-ok");
            assertThat(transactionManager.commitCount.get()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("TC-03/04/05 타임아웃 + JDBC cancel")
    class TimeoutPath {

        @Test
        @DisplayName("TC-03 예산 초과 → OnlineTimeoutException + rollback")
        void tc03_timeoutRollback() throws Exception {
            assertThatThrownBy(() -> executor.execute(() -> {
                Thread.sleep(900);
                return "late";
            })).isInstanceOf(OnlineTimeoutException.class)
                    .satisfies(ex -> {
                        OnlineTimeoutException t = (OnlineTimeoutException) ex;
                        assertThat(t.getTimeoutMs()).isEqualTo(300L);
                        assertThat(t.getServiceId()).isEqualTo(SVC);
                    });
            awaitQuiet();
            assertThat(transactionManager.commitCount.get()).isZero();
            assertThat(transactionManager.rollbackCount.get()
                    + (transactionManager.rollbackOnlyObserved.get() ? 1 : 0))
                    .isGreaterThanOrEqualTo(1);
        }

        @Test
        @DisplayName("TC-04 타임아웃 시 Statement.cancel 호출")
        void tc04_statementCancel() throws Exception {
            AtomicInteger cancelCount = new AtomicInteger();
            CountDownLatch cancelled = new CountDownLatch(1);
            Statement st = proxyStatement(cancelCount, cancelled);

            assertThatThrownBy(() -> executor.execute(() -> {
                statementRegistry.register(st);
                long until = System.currentTimeMillis() + 2_000L;
                while (System.currentTimeMillis() < until) {
                    if (cancelCount.get() > 0 || Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    Thread.sleep(8);
                }
                return "late";
            })).isInstanceOf(OnlineTimeoutException.class);

            assertThat(cancelled.await(2, TimeUnit.SECONDS)).isTrue();
            assertThat(cancelCount.get()).isGreaterThanOrEqualTo(1);
        }

        @Test
        @DisplayName("TC-05 remaining < 1s → QueryTimeout 초단위 보완 필요")
        void tc05_subSecondComplementFlag() {
            assertThat(StatementTimeoutResolver.needsJdbcCancelComplement(300)).isTrue();
            assertThat(StatementTimeoutResolver.toConservativeTimeoutSeconds(300)).isEqualTo(1);
            assertThat(StatementTimeoutResolver.needsJdbcCancelComplement(1000)).isFalse();
        }
    }

    @Nested
    @DisplayName("TC-06/07 Worker interrupt")
    class InterruptPath {

        @Test
        @DisplayName("TC-06 future.cancel(true) → sleep InterruptedException + flag 유지")
        void tc06_workerInterrupt() throws Exception {
            CountDownLatch inSleep = new CountDownLatch(1);
            AtomicBoolean sawIe = new AtomicBoolean(false);
            AtomicBoolean flagAfter = new AtomicBoolean(false);

            Thread caller = new Thread(() -> {
                try {
                    executor.execute(() -> {
                        inSleep.countDown();
                        try {
                            Thread.sleep(5_000L);
                        } catch (InterruptedException e) {
                            sawIe.set(true);
                            Thread.currentThread().interrupt();
                            flagAfter.set(Thread.currentThread().isInterrupted());
                        }
                        return "x";
                    });
                } catch (OnlineTimeoutException ignored) {
                    // client timeout
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            caller.start();
            assertThat(inSleep.await(2, TimeUnit.SECONDS)).isTrue();
            caller.join(3_000L);
            awaitQuiet();

            assertThat(sawIe.get()).isTrue();
            assertThat(flagAfter.get()).isTrue();
            assertThat(transactionManager.commitCount.get()).isZero();
        }

        @Test
        @DisplayName("TC-07 interrupt 후 성공 반환 시도해도 commit 금지")
        void tc07_noCommitAfterInterruptReturn() throws Exception {
            assertThatThrownBy(() -> executor.execute(() -> {
                try {
                    Thread.sleep(5_000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "leaked";
            })).isInstanceOf(OnlineTimeoutException.class);
            awaitQuiet();
            assertThat(transactionManager.commitCount.get()).isZero();
        }
    }

    @Nested
    @DisplayName("TC-08/09 계약·정책")
    class ContractPath {

        @Test
        @DisplayName("TC-08 mgcoa5530S0 override=10s, mode=RDW_READ_ONLY")
        void tc08_mgcoa5530Contract() throws Exception {
            assertThat(properties.resolveMilliseconds("mgcoa5530S0")).isEqualTo(10000L);
            TransactionPolicy policy = policyResolver.resolve("mgcoa5530S0");
            assertThat(policy.mode()).isEqualTo(TransactionMode.RDW_READ_ONLY);
            assertThat(policy.readOnly()).isTrue();

            ThreadContext.put("serviceId", "mgcoa5530S0");
            ThreadContext.put("guid", "guid-5530-final");
            ExecutionDeadlineContext.bind(ExecutionDeadline.start(10000));
            String result = executor.execute(() -> {
                Thread.sleep(50);
                return "5530-ok";
            });
            assertThat(result).isEqualTo("5530-ok");
            assertThat(transactionManager.lastReadOnly.get()).isTrue();
            assertThat(transactionManager.lastTimeoutSeconds.get()).isBetween(9, 10);
        }

        @Test
        @DisplayName("TC-09 TX timeout 초는 remaining 예산에서 산출")
        void tc09_txTimeoutFromRemaining() throws Exception {
            properties.setMilliseconds(5000);
            properties.setMinStartBudgetMs(100);
            ThreadContext.put("serviceId", "txBudgetS0");
            executor.execute(() -> "ok");
            assertThat(transactionManager.lastTimeoutSeconds.get()).isBetween(4, 5);
        }
    }

    private void awaitQuiet() throws InterruptedException {
        Thread.sleep(250);
    }

    private static Statement proxyStatement(AtomicInteger cancelCount, CountDownLatch cancelled) {
        return (Statement) Proxy.newProxyInstance(
                Statement.class.getClassLoader(),
                new Class<?>[] {Statement.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("cancel".equals(name)) {
                        cancelCount.incrementAndGet();
                        if (cancelled != null) {
                            cancelled.countDown();
                        }
                        return null;
                    }
                    if ("isClosed".equals(name)) {
                        return false;
                    }
                    if ("equals".equals(name)) {
                        return proxy == args[0];
                    }
                    if ("hashCode".equals(name)) {
                        return System.identityHashCode(proxy);
                    }
                    if ("toString".equals(name)) {
                        return "FinalRegProxyStatement";
                    }
                    Class<?> rt = method.getReturnType();
                    if (rt == boolean.class) {
                        return false;
                    }
                    if (rt == int.class) {
                        return 0;
                    }
                    if (rt == long.class) {
                        return 0L;
                    }
                    return null;
                });
    }

    private static final class RecordingTransactionManager implements PlatformTransactionManager {
        private final AtomicInteger beginCount = new AtomicInteger();
        private final AtomicInteger commitCount = new AtomicInteger();
        private final AtomicInteger rollbackCount = new AtomicInteger();
        private final AtomicBoolean rollbackOnlyObserved = new AtomicBoolean();
        private final AtomicReference<Boolean> lastReadOnly = new AtomicReference<>();
        private final AtomicInteger lastTimeoutSeconds = new AtomicInteger(-1);

        @Override
        public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
            beginCount.incrementAndGet();
            if (definition != null) {
                lastReadOnly.set(definition.isReadOnly());
                lastTimeoutSeconds.set(definition.getTimeout());
            }
            return new SimpleTransactionStatus() {
                @Override
                public void setRollbackOnly() {
                    rollbackOnlyObserved.set(true);
                    super.setRollbackOnly();
                }
            };
        }

        @Override
        public void commit(TransactionStatus status) {
            if (status != null && status.isRollbackOnly()) {
                rollbackOnlyObserved.set(true);
                rollbackCount.incrementAndGet();
                return;
            }
            commitCount.incrementAndGet();
        }

        @Override
        public void rollback(TransactionStatus status) {
            rollbackCount.incrementAndGet();
        }
    }
}
