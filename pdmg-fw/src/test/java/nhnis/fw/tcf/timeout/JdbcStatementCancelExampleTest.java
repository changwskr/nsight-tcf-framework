package nhnis.fw.tcf.timeout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
 * {@link Statement#cancel()} 동작 예제 + 검증.
 *
 * <h2>예제 개요</h2>
 * <pre>
 * [예제 A] Raw JDBC — H2 가 실제로 cancel 가능한 장시간 SQL
 *   Worker: SELECT SUM(X) FROM SYSTEM_RANGE(...) 실행 중
 *   다른 스레드: statement.cancel()
 *   → SQLException / 조기 종료 (Thread.interrupt 없이)
 *
 * [예제 B] PDMG OnlineTimeoutExecutor 경로
 *   Worker: Statement 등록 후 장시간 SQL / CALL SLEEP
 *   Client: Future.get timeout
 *   → ActiveJdbcStatementRegistry.cancelAll() → Statement.cancel()
 *   → OnlineTimeoutException
 * </pre>
 *
 * <p>참고: H2 Java ALIAS 의 {@code Thread.sleep} 은 SQL 엔진 cancel 플래그만으로는
 * 깨지지 않는 경우가 많다. 운영 경로에서는 cancel + Worker interrupt 가 함께 동작한다.
 *
 * <p>라이브 서비스 재현:
 * <pre>
 * --jwt.enabled=false
 * --nhnis.demo.mgcoa5530-jdbc-hold-ms=15000
 * (overrides.mgcoa5530S0=10000 이면 FW_TIMEOUT + JDBC-CANCEL 로그)
 * </pre>
 */
@DisplayName("Statement.cancel 예제")
class JdbcStatementCancelExampleTest {

    /** H2 가 cancel 플래그를 검사하는 장시간 집계. */
    private static final String LONG_CANCELABLE_SQL =
            "SELECT SUM(X) FROM SYSTEM_RANGE(1, 50000000)";

    private Connection connection;

    @BeforeEach
    void openH2() throws Exception {
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:stmt_cancel_example;MODE=Oracle;DB_CLOSE_DELAY=-1", "sa", "");
        try (Statement bootstrap = connection.createStatement()) {
            bootstrap.execute(
                    "CREATE ALIAS IF NOT EXISTS SLEEP FOR \"nhnis.fw.tcf.timeout.H2SleepAlias.sleepMs\"");
        }
    }

    @AfterEach
    void closeH2() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Nested
    @DisplayName("예제 A — Raw JDBC Statement.cancel")
    class RawJdbcExample {

        @Test
        @DisplayName("장시간 SQL 실행 중 Statement.cancel() 이 쿼리를 중단한다")
        void cancelStopsLongRunningSql() throws Exception {
            Statement statement = connection.createStatement();
            CountDownLatch started = new CountDownLatch(1);
            AtomicReference<SQLException> sqlError = new AtomicReference<>();
            AtomicBoolean finishedNormally = new AtomicBoolean(false);

            Thread worker = new Thread(() -> {
                try {
                    started.countDown();
                    statement.executeQuery(LONG_CANCELABLE_SQL);
                    finishedNormally.set(true);
                } catch (SQLException e) {
                    sqlError.set(e);
                }
            }, "raw-jdbc-cancel-worker");
            worker.start();

            assertThat(started.await(2, TimeUnit.SECONDS)).isTrue();
            Thread.sleep(80);

            long t0 = System.currentTimeMillis();
            statement.cancel();
            worker.join(8_000L);
            long elapsed = System.currentTimeMillis() - t0;

            assertThat(worker.isAlive()).as("cancel 후 worker 가 종료되어야 함").isFalse();
            assertThat(elapsed).as("전체 SYSTEM_RANGE 를 끝까지 돌리지 않아야 함").isLessThan(8_000L);
            assertThat(finishedNormally.get()).isFalse();
            assertThat((Throwable) sqlError.get())
                    .as("cancel 시 H2 는 SQLException 을 던진다")
                    .isNotNull();

            statement.close();
        }
    }

    @Nested
    @DisplayName("예제 B — OnlineTimeoutExecutor + Registry.cancelAll")
    class OnlineTimeoutExecutorExample {

        private ThreadPoolTaskExecutor taskExecutor;
        private ActiveJdbcStatementRegistry statementRegistry;
        private ScheduledExecutorService deadlineCancelScheduler;
        private DefaultOnlineTimeoutExecutor executor;

        @BeforeEach
        void setUpExecutor() {
            OnlineTimeoutProperties properties = new OnlineTimeoutProperties();
            properties.setEnabled(true);
            properties.setMilliseconds(400);
            properties.setMinStartBudgetMs(50);
            properties.setPoolSize(2);
            properties.setQueueCapacity(2);
            properties.setSqlSafetyTimeoutSeconds(10);

            OnlineTransactionPolicyProperties policyProperties = new OnlineTransactionPolicyProperties();
            TransactionPolicyResolver policyResolver =
                    new PropertiesTransactionPolicyResolver(policyProperties);

            taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setThreadNamePrefix("cancel-example-");
            taskExecutor.setCorePoolSize(2);
            taskExecutor.setMaxPoolSize(2);
            taskExecutor.setQueueCapacity(2);
            taskExecutor.initialize();

            RecordingTransactionManager transactionManager = new RecordingTransactionManager();
            statementRegistry = new ActiveJdbcStatementRegistry();
            deadlineCancelScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "cancel-example-sched");
                t.setDaemon(true);
                return t;
            });

            executor = new DefaultOnlineTimeoutExecutor(
                    properties,
                    taskExecutor,
                    policyResolver,
                    new TransactionManagerRegistry(Map.of("rdwTransactionManager", transactionManager)),
                    new OnlineExecutionEvidenceRegistry(),
                    statementRegistry,
                    deadlineCancelScheduler);

            ThreadContext.put("serviceId", "jdbcCancelExampleS0");
            ThreadContext.put("guid", "guid-jdbc-cancel-example");
        }

        @AfterEach
        void tearDownExecutor() {
            ThreadContext.clearAll();
            taskExecutor.shutdown();
            deadlineCancelScheduler.shutdownNow();
        }

        @Test
        @DisplayName("타임아웃 시 Registry.cancelAll → 실제 Statement.cancel 호출")
        void timeoutPathInvokesRealStatementCancel() throws Exception {
            AtomicInteger cancelCount = new AtomicInteger();
            CountDownLatch enteredSql = new CountDownLatch(1);
            CountDownLatch cancelled = new CountDownLatch(1);

            Connection workerConnection = DriverManager.getConnection(
                    "jdbc:h2:mem:stmt_cancel_example;MODE=Oracle;DB_CLOSE_DELAY=-1", "sa", "");
            Statement realStatement = workerConnection.createStatement();
            Statement countingStatement = wrapCountingCancel(realStatement, cancelCount, cancelled);

            assertThatThrownBy(() -> executor.execute(() -> {
                statementRegistry.register(countingStatement);
                enteredSql.countDown();
                countingStatement.executeQuery(LONG_CANCELABLE_SQL);
                return "should-timeout";
            })).isInstanceOf(OnlineTimeoutException.class);

            assertThat(enteredSql.await(2, TimeUnit.SECONDS)).isTrue();
            assertThat(cancelled.await(3, TimeUnit.SECONDS))
                    .as("Future timeout → cancelAll → Statement.cancel")
                    .isTrue();
            assertThat(cancelCount.get()).isGreaterThanOrEqualTo(1);

            realStatement.close();
            workerConnection.close();
        }

        @Test
        @DisplayName("CALL SLEEP 경로에서도 cancelAll 이 호출된다 (interrupt 와 병행)")
        void timeoutPathInvokesCancelOnSleepAlias() throws Exception {
            AtomicInteger cancelCount = new AtomicInteger();
            CountDownLatch enteredSleep = new CountDownLatch(1);
            CountDownLatch cancelled = new CountDownLatch(1);

            Connection workerConnection = DriverManager.getConnection(
                    "jdbc:h2:mem:stmt_cancel_example;MODE=Oracle;DB_CLOSE_DELAY=-1", "sa", "");
            try (Statement bootstrap = workerConnection.createStatement()) {
                bootstrap.execute(
                        "CREATE ALIAS IF NOT EXISTS SLEEP FOR \"nhnis.fw.tcf.timeout.H2SleepAlias.sleepMs\"");
            }
            Statement realStatement = workerConnection.createStatement();
            Statement countingStatement = wrapCountingCancel(realStatement, cancelCount, cancelled);

            assertThatThrownBy(() -> executor.execute(() -> {
                statementRegistry.register(countingStatement);
                enteredSleep.countDown();
                countingStatement.execute("CALL SLEEP(30000)");
                return "should-timeout";
            })).isInstanceOf(OnlineTimeoutException.class);

            assertThat(enteredSleep.await(2, TimeUnit.SECONDS)).isTrue();
            assertThat(cancelled.await(3, TimeUnit.SECONDS)).isTrue();
            assertThat(cancelCount.get()).isGreaterThanOrEqualTo(1);

            realStatement.close();
            workerConnection.close();
        }
    }

    private static Statement wrapCountingCancel(
            Statement delegate, AtomicInteger cancelCount, CountDownLatch cancelled) {
        return (Statement) java.lang.reflect.Proxy.newProxyInstance(
                Statement.class.getClassLoader(),
                new Class<?>[] {Statement.class},
                (proxy, method, args) -> {
                    if ("cancel".equals(method.getName())) {
                        cancelCount.incrementAndGet();
                        cancelled.countDown();
                        return method.invoke(delegate, args);
                    }
                    if ("equals".equals(method.getName())) {
                        return proxy == args[0];
                    }
                    if ("hashCode".equals(method.getName())) {
                        return System.identityHashCode(proxy);
                    }
                    try {
                        return method.invoke(delegate, args);
                    } catch (java.lang.reflect.InvocationTargetException ex) {
                        throw ex.getCause() != null ? ex.getCause() : ex;
                    }
                });
    }

    private static final class RecordingTransactionManager implements PlatformTransactionManager {
        @Override
        public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
            return new SimpleTransactionStatus();
        }

        @Override
        public void commit(TransactionStatus status) {
        }

        @Override
        public void rollback(TransactionStatus status) {
        }
    }
}
