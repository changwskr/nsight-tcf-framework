package nhnis.fw.tcf.timeout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import nhnis.fw.tcf.execution.ExecutionDeadline;
import nhnis.fw.tcf.execution.ExecutionDeadlineContext;
import nhnis.fw.tcf.execution.OnlineExecutionEvidenceRegistry;
import nhnis.fw.tcf.execution.OnlineTransactionPolicyProperties;
import nhnis.fw.tcf.execution.PropertiesTransactionPolicyResolver;
import nhnis.fw.tcf.execution.TransactionManagerRegistry;
import nhnis.fw.tcf.execution.TransactionMode;
import nhnis.fw.tcf.execution.TransactionPolicy;
import nhnis.fw.mybatis.StatementTimeoutResolver;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;

/**
 * pdmg-service local profile 계약 검증:
 * <pre>
 * nhnis.fw.timeout.milliseconds=5000
 * nhnis.fw.timeout.overrides.mgcoa5530S0=10000
 * nhnis.fw.transaction.services.mgcoa5530S0.mode=RDW_READ_ONLY
 * </pre>
 */
class Mgcoa5530S0TimeoutContractTest {

    private static final String SERVICE_ID = "mgcoa5530S0";

    private OnlineTimeoutProperties timeoutProperties;
    private OnlineTransactionPolicyProperties transactionPolicyProperties;
    private PropertiesTransactionPolicyResolver policyResolver;
    private ThreadPoolTaskExecutor taskExecutor;
    private RecordingTransactionManager transactionManager;
    private ScheduledExecutorService deadlineCancelScheduler;
    private DefaultOnlineTimeoutExecutor executor;

    @BeforeEach
    void setUp() {
        timeoutProperties = new OnlineTimeoutProperties();
        timeoutProperties.setEnabled(true);
        timeoutProperties.setMilliseconds(5000);
        timeoutProperties.setOverrides(Map.of(SERVICE_ID, 10000L));
        timeoutProperties.setMinStartBudgetMs(1000);
        timeoutProperties.setSqlSafetyTimeoutSeconds(10);
        timeoutProperties.setPoolSize(4);
        timeoutProperties.setQueueCapacity(10);
        timeoutProperties.validate();

        transactionPolicyProperties = new OnlineTransactionPolicyProperties();
        transactionPolicyProperties.setDefaultManager("rdwTransactionManager");
        transactionPolicyProperties.setDefaultMode(TransactionMode.RDW_READ_WRITE);
        OnlineTransactionPolicyProperties.ServicePolicy servicePolicy =
                new OnlineTransactionPolicyProperties.ServicePolicy();
        servicePolicy.setMode(TransactionMode.RDW_READ_ONLY);
        transactionPolicyProperties.getServices().put(SERVICE_ID, servicePolicy);
        policyResolver = new PropertiesTransactionPolicyResolver(transactionPolicyProperties);

        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("mgcoa5530-contract-");
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(2);
        taskExecutor.setQueueCapacity(4);
        taskExecutor.initialize();

        transactionManager = new RecordingTransactionManager();
        deadlineCancelScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "mgcoa5530-jdbc-cancel");
            t.setDaemon(true);
            return t;
        });
        executor = new DefaultOnlineTimeoutExecutor(
                timeoutProperties,
                taskExecutor,
                policyResolver,
                new TransactionManagerRegistry(Map.of("rdwTransactionManager", transactionManager)),
                new OnlineExecutionEvidenceRegistry(),
                new ActiveJdbcStatementRegistry(),
                deadlineCancelScheduler);
    }

    @AfterEach
    void tearDown() {
        ExecutionDeadlineContext.clear();
        ThreadContext.clearAll();
        taskExecutor.shutdown();
        if (deadlineCancelScheduler != null) {
            deadlineCancelScheduler.shutdownNow();
        }
    }

    @Test
    void timeoutOverrideIsTenSecondsForMgcoa5530S0() {
        assertThat(timeoutProperties.resolveMilliseconds(SERVICE_ID)).isEqualTo(10000L);
        assertThat(timeoutProperties.resolveMilliseconds("mgcoa8888S0")).isEqualTo(5000L);
    }

    @Test
    void transactionPolicyIsRdwReadOnly() {
        TransactionPolicy policy = policyResolver.resolve(SERVICE_ID);
        assertThat(policy.mode()).isEqualTo(TransactionMode.RDW_READ_ONLY);
        assertThat(policy.readOnly()).isTrue();
        assertThat(policy.requiresTransaction()).isTrue();
        assertThat(policy.transactionManagerBean()).isEqualTo("rdwTransactionManager");
    }

    @Test
    void executorUsesOverrideBudgetAndReadOnlyTx() throws Exception {
        ThreadContext.put("serviceId", SERVICE_ID);
        ThreadContext.put("guid", "guid-mgcoa5530-contract");
        ExecutionDeadlineContext.bind(ExecutionDeadline.start(10000));

        String result = executor.execute(() -> {
            Thread.sleep(200);
            return "ok-5530";
        });

        assertThat(result).isEqualTo("ok-5530");
        assertThat(transactionManager.beginCount.get()).isEqualTo(1);
        assertThat(transactionManager.commitCount.get()).isEqualTo(1);
        assertThat(transactionManager.rollbackCount.get()).isZero();
        assertThat(transactionManager.lastReadOnly.get()).isTrue();
        assertThat(transactionManager.lastTimeoutSeconds.get()).isBetween(9, 10);
    }

    @Test
    void defaultServiceStillTimesOutAtFiveSecondsBudget() {
        ThreadContext.put("serviceId", "mgcoa8888S0");
        ThreadContext.put("guid", "guid-default");
        ExecutionDeadlineContext.bind(ExecutionDeadline.start(200));

        assertThatThrownBy(() -> executor.execute(() -> {
            Thread.sleep(400);
            return "late";
        })).isInstanceOf(OnlineTimeoutException.class)
                .satisfies(ex -> {
                    OnlineTimeoutException timeout = (OnlineTimeoutException) ex;
                    assertThat(timeout.getTimeoutMs()).isEqualTo(5000L);
                    assertThat(timeout.getServiceId()).isEqualTo("mgcoa8888S0");
                });
    }

    @Test
    void statementTimeoutFollowsRemainingDeadline() {
        MappedStatement ms = mock(MappedStatement.class);
        when(ms.getTimeout()).thenReturn(null);
        ExecutionDeadline deadline = ExecutionDeadline.start(3500);
        ExecutionDeadlineContext.bind(deadline);
        int remainingSec = StatementTimeoutResolver.toConservativeTimeoutSeconds(deadline.remainingMillis());
        assertThat(StatementTimeoutResolver.resolve(ms, 10)).isEqualTo(Math.min(10, remainingSec));
        assertThat(remainingSec).isBetween(1, 3);
    }

    private static final class RecordingTransactionManager implements PlatformTransactionManager {
        private final AtomicInteger beginCount = new AtomicInteger();
        private final AtomicInteger commitCount = new AtomicInteger();
        private final AtomicInteger rollbackCount = new AtomicInteger();
        private final AtomicReference<Boolean> lastReadOnly = new AtomicReference<>();
        private final AtomicInteger lastTimeoutSeconds = new AtomicInteger(-1);

        @Override
        public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
            beginCount.incrementAndGet();
            if (definition != null) {
                lastReadOnly.set(definition.isReadOnly());
                lastTimeoutSeconds.set(definition.getTimeout());
            }
            return new SimpleTransactionStatus();
        }

        @Override
        public void commit(TransactionStatus status) {
            commitCount.incrementAndGet();
        }

        @Override
        public void rollback(TransactionStatus status) {
            rollbackCount.incrementAndGet();
        }
    }
}
