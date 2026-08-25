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
import nhnis.fw.tcf.execution.OnlineExecutionEvidenceRegistry;
import nhnis.fw.tcf.execution.OnlineTransactionPolicyProperties;
import nhnis.fw.tcf.execution.PropertiesTransactionPolicyResolver;
import nhnis.fw.tcf.execution.TransactionManagerRegistry;
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
 * 온라인 트랜잭션 타임아웃 핵심 시나리오.
 *
 * <pre>
 * S1 정상 트랜잭션 — 예산 내 완료, commit, Worker interrupt 없음
 * S2 타임아웃     — OnlineTimeoutException, Statement.cancel, rollback
 * S3 Worker interrupt — future.cancel(true) 후 sleep InterruptedException + interrupt flag
 * </pre>
 */
@DisplayName("온라인 타임아웃 시나리오")
class OnlineTimeoutScenarioTest {

    private ThreadPoolTaskExecutor taskExecutor;
    private OnlineTimeoutProperties properties;
    private RecordingTransactionManager transactionManager;
    private TransactionPolicyResolver policyResolver;
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

        OnlineTransactionPolicyProperties policyProperties = new OnlineTransactionPolicyProperties();
        policyResolver = new PropertiesTransactionPolicyResolver(policyProperties);

        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("scenario-online-");
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
            Thread t = new Thread(r, "scenario-jdbc-cancel");
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
        ThreadContext.put("serviceId", "scenarioS0");
        ThreadContext.put("guid", "guid-scenario");
    }

    @AfterEach
    void tearDown() {
        ThreadContext.clearAll();
        taskExecutor.shutdown();
        deadlineCancelScheduler.shutdownNow();
    }

    @Nested
    @DisplayName("S1 트랜잭션 정상")
    class NormalTransaction {

        @Test
        @DisplayName("예산 내 완료 시 결과 반환 + TX commit + Worker는 interrupt 되지 않음")
        void completesWithCommitAndNoWorkerInterrupt() throws Exception {
            AtomicBoolean workerInterruptedDuringWork = new AtomicBoolean(false);
            AtomicReference<String> workerThreadName = new AtomicReference<>();

            String result = executor.execute(() -> {
                workerThreadName.set(Thread.currentThread().getName());
                workerInterruptedDuringWork.set(Thread.currentThread().isInterrupted());
                Thread.sleep(40);
                workerInterruptedDuringWork.set(
                        workerInterruptedDuringWork.get() || Thread.currentThread().isInterrupted());
                return "ok";
            });

            assertThat(result).isEqualTo("ok");
            assertThat(workerThreadName.get()).startsWith("scenario-online-");
            assertThat(workerInterruptedDuringWork.get()).isFalse();
            assertThat(transactionManager.beginCount.get()).isEqualTo(1);
            assertThat(transactionManager.commitCount.get()).isEqualTo(1);
            assertThat(transactionManager.rollbackCount.get()).isZero();
            assertThat(transactionManager.lastTimeoutSeconds.get()).isGreaterThanOrEqualTo(1);
        }

        @Test
        @DisplayName("서비스 override 예산(긴 제한)으로 정상 완료")
        void completesUnderServiceOverrideBudget() throws Exception {
            properties.setMilliseconds(80);
            properties.setOverrides(Map.of("scenarioSlowS0", 400L));
            ThreadContext.put("serviceId", "scenarioSlowS0");

            String result = executor.execute(() -> {
                Thread.sleep(150);
                return "override-ok";
            });

            assertThat(result).isEqualTo("override-ok");
            assertThat(transactionManager.commitCount.get()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("S2 트랜잭션 타임아웃")
    class TransactionTimeout {

        @Test
        @DisplayName("예산 초과 시 OnlineTimeoutException + TX rollback")
        void throwsTimeoutAndRollsBack() throws Exception {
            assertThatThrownBy(() -> executor.execute(() -> {
                Thread.sleep(800);
                return "late";
            })).isInstanceOf(OnlineTimeoutException.class)
                    .satisfies(ex -> {
                        OnlineTimeoutException timeout = (OnlineTimeoutException) ex;
                        assertThat(timeout.getTimeoutMs()).isEqualTo(300L);
                        assertThat(timeout.getServiceId()).isEqualTo("scenarioS0");
                        assertThat(timeout.getElapsedMs()).isGreaterThanOrEqualTo(250L);
                    });

            awaitQuiet();
            assertThat(transactionManager.beginCount.get()).isEqualTo(1);
            assertThat(transactionManager.rollbackCount.get()
                    + (transactionManager.rollbackOnlyObserved.get() ? 1 : 0))
                    .isGreaterThanOrEqualTo(1);
            assertThat(transactionManager.commitCount.get()).isZero();
        }

        @Test
        @DisplayName("타임아웃 시 등록된 JDBC Statement.cancel 호출")
        void cancelsJdbcStatementOnTimeout() throws Exception {
            AtomicInteger cancelCount = new AtomicInteger();
            CountDownLatch registered = new CountDownLatch(1);
            CountDownLatch cancelled = new CountDownLatch(1);
            Statement statement = proxyStatement(cancelCount, cancelled);

            assertThatThrownBy(() -> executor.execute(() -> {
                statementRegistry.register(statement);
                registered.countDown();
                long until = System.currentTimeMillis() + 2_000L;
                while (System.currentTimeMillis() < until) {
                    if (cancelCount.get() > 0 || Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    Thread.sleep(10);
                }
                return "late";
            })).isInstanceOf(OnlineTimeoutException.class);

            assertThat(registered.await(2, TimeUnit.SECONDS)).isTrue();
            assertThat(cancelled.await(2, TimeUnit.SECONDS)).isTrue();
            assertThat(cancelCount.get()).isGreaterThanOrEqualTo(1);
        }

        @Test
        @DisplayName("remaining < 1s 이면 QueryTimeout 초단위 보완 대상")
        void subSecondRemainingNeedsJdbcCancelComplement() {
            assertThat(StatementTimeoutResolver.needsJdbcCancelComplement(300)).isTrue();
            assertThat(StatementTimeoutResolver.toConservativeTimeoutSeconds(300)).isEqualTo(1);
            assertThat(StatementTimeoutResolver.needsJdbcCancelComplement(1500)).isFalse();
        }
    }

    @Nested
    @DisplayName("S3 Worker 스레드 interrupt")
    class WorkerInterrupt {

        @Test
        @DisplayName("타임아웃 시 Worker sleep 이 InterruptedException 으로 깨지고 interrupt flag 유지")
        void interruptBreaksSleepAndKeepsFlag() throws Exception {
            CountDownLatch enteredSleep = new CountDownLatch(1);
            AtomicBoolean sawInterruptedException = new AtomicBoolean(false);
            AtomicBoolean interruptFlagAfterCatch = new AtomicBoolean(false);
            AtomicReference<String> workerName = new AtomicReference<>();

            Thread caller = new Thread(() -> {
                try {
                    executor.execute(() -> {
                        workerName.set(Thread.currentThread().getName());
                        enteredSleep.countDown();
                        try {
                            Thread.sleep(5_000L);
                        } catch (InterruptedException e) {
                            sawInterruptedException.set(true);
                            // 업무 코드와 동일하게 flag 복원
                            Thread.currentThread().interrupt();
                            interruptFlagAfterCatch.set(Thread.currentThread().isInterrupted());
                        }
                        return "should-not-commit";
                    });
                } catch (OnlineTimeoutException expected) {
                    // client path
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, "scenario-caller");

            caller.start();
            assertThat(enteredSleep.await(2, TimeUnit.SECONDS)).isTrue();
            caller.join(3_000L);
            awaitQuiet();

            assertThat(workerName.get()).startsWith("scenario-online-");
            assertThat(sawInterruptedException.get())
                    .as("future.cancel(true) 가 Worker sleep 을 interrupt 해야 함")
                    .isTrue();
            assertThat(interruptFlagAfterCatch.get())
                    .as("InterruptedException 처리 후 interrupt flag 가 유지되어야 함")
                    .isTrue();
            assertThat(transactionManager.commitCount.get()).isZero();
        }

        @Test
        @DisplayName("interrupt 후 action 이 값을 반환해도 deadline/interrupt 체크로 commit 되지 않음")
        void interruptedWorkerResultIsNotCommitted() throws Exception {
            CountDownLatch started = new CountDownLatch(1);

            assertThatThrownBy(() -> executor.execute(() -> {
                started.countDown();
                try {
                    Thread.sleep(5_000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // interrupt 후에도 값을 반환하려는 잘못된 업무 코드 시뮬레이션
                return "leaked-success";
            })).isInstanceOf(OnlineTimeoutException.class);

            assertThat(started.await(2, TimeUnit.SECONDS)).isTrue();
            awaitQuiet();
            assertThat(transactionManager.commitCount.get()).isZero();
            assertThat(transactionManager.rollbackCount.get()
                    + (transactionManager.rollbackOnlyObserved.get() ? 1 : 0))
                    .isGreaterThanOrEqualTo(1);
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
                        return "ScenarioProxyStatement";
                    }
                    Class<?> returnType = method.getReturnType();
                    if (returnType == boolean.class) {
                        return false;
                    }
                    if (returnType == int.class) {
                        return 0;
                    }
                    if (returnType == long.class) {
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
        private final AtomicInteger lastTimeoutSeconds = new AtomicInteger(-1);

        @Override
        public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
            beginCount.incrementAndGet();
            if (definition != null) {
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
        public void commit(TransactionStatus status) throws TransactionException {
            if (status != null && status.isRollbackOnly()) {
                rollbackOnlyObserved.set(true);
                rollbackCount.incrementAndGet();
                return;
            }
            commitCount.incrementAndGet();
        }

        @Override
        public void rollback(TransactionStatus status) throws TransactionException {
            rollbackCount.incrementAndGet();
        }
    }
}
