package nhnis.fw.tcf.timeout;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import nhnis.fw.mybatis.StatementTimeoutResolver;
import nhnis.fw.tcf.execution.ExecutionDeadline;
import nhnis.fw.tcf.execution.ExecutionDeadlineContext;
import nhnis.fw.tcf.execution.OnlineExecutionEvidenceRegistry;
import nhnis.fw.tcf.execution.TransactionManagerRegistry;
import nhnis.fw.tcf.execution.TransactionPolicy;
import nhnis.fw.tcf.execution.TransactionPolicyResolver;

/**
 * Worker Pool + TransactionTemplate + Future.get(timeout) 기반 온라인 타임아웃 실행기.
 *
 * <p>제한시간은 {@link OnlineTimeoutProperties#resolveMilliseconds(String)} 로
 * serviceId별 override를 반영한다.
 *
 * <p>Transaction 경계는 {@link TransactionPolicyResolver} 가 결정하고,
 * {@link TransactionManagerRegistry} 로 Bean 이름을 해석한다.
 */
public class DefaultOnlineTimeoutExecutor implements OnlineTimeoutExecutor {

    private static final Logger log = LoggerFactory.getLogger(DefaultOnlineTimeoutExecutor.class);

    private final OnlineTimeoutProperties properties;
    private final ThreadPoolTaskExecutor taskExecutor;
    private final TransactionPolicyResolver policyResolver;
    private final TransactionManagerRegistry transactionManagerRegistry;
    private final OnlineExecutionEvidenceRegistry evidenceRegistry;

    public DefaultOnlineTimeoutExecutor(OnlineTimeoutProperties properties,
            ThreadPoolTaskExecutor taskExecutor,
            TransactionPolicyResolver policyResolver,
            TransactionManagerRegistry transactionManagerRegistry,
            OnlineExecutionEvidenceRegistry evidenceRegistry) {
        this.properties = properties;
        this.taskExecutor = taskExecutor;
        this.policyResolver = policyResolver;
        this.transactionManagerRegistry = transactionManagerRegistry;
        this.evidenceRegistry = evidenceRegistry;
    }

    @Override
    public <T> T execute(Callable<T> action) throws Exception {
        OnlineTimeoutWorkerContext workerContext = OnlineTimeoutWorkerContext.capture();
        long configuredTimeoutMs = properties.resolveMilliseconds(workerContext.getServiceId());
        ExecutionDeadline boundDeadline = ExecutionDeadlineContext.current();
        if (boundDeadline == null) {
            boundDeadline = ExecutionDeadline.start(configuredTimeoutMs);
        }
        final ExecutionDeadline deadline = boundDeadline;

        Future<T> future;
        try {
            future = taskExecutor.getThreadPoolExecutor().submit(
                    () -> runInWorker(workerContext, configuredTimeoutMs, deadline, action));
        } catch (RejectedExecutionException ex) {
            throw overload(workerContext);
        }
        evidenceRegistry.markQueued(workerContext.getEvidenceKey());

        long waitMs = Math.max(1L, deadline.remainingMillis());
        try {
            T result = future.get(waitMs, TimeUnit.MILLISECONDS);
            if (log.isDebugEnabled()) {
                log.debug("[ONLINE-TIMEOUT] completed guid={} serviceId={} timeoutMs={} elapsedMs={}",
                        workerContext.getGuid(),
                        workerContext.getServiceId(),
                        configuredTimeoutMs,
                        deadline.elapsedMillis());
            }
            return result;
        } catch (TimeoutException ex) {
            boolean cancelled = future.cancel(true);
            long elapsed = deadline.elapsedMillis();
            evidenceRegistry.markCancelRequested(workerContext.getEvidenceKey());
            log.warn("[ONLINE-TIMEOUT] guid={} serviceId={} timeoutMs={} elapsedMs={} cancelRequested={}",
                    workerContext.getGuid(),
                    workerContext.getServiceId(),
                    configuredTimeoutMs,
                    elapsed,
                    cancelled);
            throw new OnlineTimeoutException(
                    configuredTimeoutMs,
                    elapsed,
                    workerContext.getServiceId(),
                    workerContext.getGuid());
        } catch (InterruptedException ex) {
            future.cancel(true);
            Thread.currentThread().interrupt();
            throw new OnlineTimeoutException(
                    configuredTimeoutMs,
                    deadline.elapsedMillis(),
                    workerContext.getServiceId(),
                    workerContext.getGuid());
        } catch (ExecutionException ex) {
            throw unwrap(ex.getCause());
        } catch (TaskRejectedException ex) {
            throw overload(workerContext);
        }
    }

    private <T> T runInWorker(OnlineTimeoutWorkerContext workerContext, long timeoutMs,
            ExecutionDeadline deadline, Callable<T> action) throws Exception {
        String evidenceKey = workerContext.getEvidenceKey();
        evidenceRegistry.markWorkerStarted(evidenceKey, Thread.currentThread().threadId());
        workerContext.install();
        boolean workerCommitted = false;
        try {
            long remainingMs = deadline.remainingMillis();
            if (remainingMs < properties.getMinStartBudgetMs()) {
                long elapsed = deadline.elapsedMillis();
                log.warn("[ONLINE-TIMEOUT] worker start budget insufficient guid={} serviceId={} "
                                + "timeoutMs={} remainingMs={} minStartBudgetMs={} elapsedMs={}",
                        workerContext.getGuid(),
                        workerContext.getServiceId(),
                        timeoutMs,
                        remainingMs,
                        properties.getMinStartBudgetMs(),
                        elapsed);
                throw new OnlineTimeoutException(
                        timeoutMs,
                        elapsed,
                        workerContext.getServiceId(),
                        workerContext.getGuid());
            }

            TransactionPolicy policy = policyResolver.resolve(workerContext.getServiceId());
            if (!policy.requiresTransaction()) {
                if (log.isDebugEnabled()) {
                    log.debug("[ONLINE-TIMEOUT] no-tx policy guid={} serviceId={} mode={}",
                            workerContext.getGuid(),
                            workerContext.getServiceId(),
                            policy.mode());
                }
                T result = runWithoutTransaction(workerContext, timeoutMs, deadline, action);
                workerCommitted = true;
                return result;
            }

            PlatformTransactionManager transactionManager =
                    transactionManagerRegistry.require(policy.transactionManagerBean());
            int transactionTimeoutSeconds = StatementTimeoutResolver.toConservativeTimeoutSeconds(remainingMs);
            evidenceRegistry.markTxStarted(evidenceKey, policy.mode(), transactionTimeoutSeconds);
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            transactionTemplate.setTimeout(transactionTimeoutSeconds);
            transactionTemplate.setReadOnly(policy.readOnly());

            if (log.isDebugEnabled()) {
                log.debug("[ONLINE-TIMEOUT] worker tx policy guid={} serviceId={} manager={} readOnly={} "
                                + "remainingMs={} txTimeoutSec={}",
                        workerContext.getGuid(),
                        workerContext.getServiceId(),
                        policy.transactionManagerBean(),
                        policy.readOnly(),
                        remainingMs,
                        transactionTimeoutSeconds);
            }

            T txResult = transactionTemplate.execute(status -> {
                ExecutionDeadlineContext.bind(deadline);
                try {
                    T result = action.call();
                    if (deadline.expired() || Thread.currentThread().isInterrupted()) {
                        status.setRollbackOnly();
                        long elapsed = deadline.elapsedMillis();
                        if (log.isDebugEnabled()) {
                            log.debug("[ONLINE-TIMEOUT] worker deadline exceeded guid={} serviceId={} timeoutMs={} elapsedMs={}",
                                    workerContext.getGuid(),
                                    workerContext.getServiceId(),
                                    timeoutMs,
                                    elapsed);
                        }
                        throw new OnlineTimeoutException(
                                timeoutMs,
                                elapsed,
                                workerContext.getServiceId(),
                                workerContext.getGuid());
                    }
                    return result;
                } catch (OnlineTimeoutException | OnlineOverloadException ex) {
                    status.setRollbackOnly();
                    throw ex;
                } catch (RuntimeException ex) {
                    status.setRollbackOnly();
                    throw ex;
                } catch (Exception ex) {
                    status.setRollbackOnly();
                    throw new OnlineTimeoutExecutionException(ex);
                } finally {
                    ExecutionDeadlineContext.clear();
                }
            });
            workerCommitted = true;
            return txResult;
        } finally {
            if (evidenceKey != null && !evidenceKey.isBlank()) {
                if (workerCommitted) {
                    evidenceRegistry.markWorkerCommitted(evidenceKey);
                } else {
                    evidenceRegistry.markWorkerRolledBack(evidenceKey);
                }
                evidenceRegistry.markWorkerTerminated(evidenceKey);
            }
            workerContext.clear();
        }
    }

    private <T> T runWithoutTransaction(OnlineTimeoutWorkerContext workerContext, long timeoutMs,
            ExecutionDeadline deadline, Callable<T> action) throws Exception {
        ExecutionDeadlineContext.bind(deadline);
        try {
            T result = action.call();
            if (deadline.expired() || Thread.currentThread().isInterrupted()) {
                throw new OnlineTimeoutException(
                        timeoutMs,
                        deadline.elapsedMillis(),
                        workerContext.getServiceId(),
                        workerContext.getGuid());
            }
            return result;
        } finally {
            ExecutionDeadlineContext.clear();
        }
    }

    private OnlineOverloadException overload(OnlineTimeoutWorkerContext workerContext) {
        ThreadPoolExecutor pool = taskExecutor.getThreadPoolExecutor();
        int active = pool == null ? 0 : pool.getActiveCount();
        int poolSize = properties.getPoolSize();
        int queueSize = pool == null || pool.getQueue() == null ? properties.getQueueCapacity()
                : pool.getQueue().size();
        log.warn("[ONLINE-OVERLOAD] guid={} serviceId={} active={} poolSize={} queueSize={}",
                workerContext.getGuid(),
                workerContext.getServiceId(),
                active,
                poolSize,
                queueSize);
        return new OnlineOverloadException(
                workerContext.getServiceId(),
                workerContext.getGuid(),
                active,
                poolSize,
                queueSize);
    }

    private static Exception unwrap(Throwable cause) throws Exception {
        if (cause instanceof OnlineTimeoutExecutionException wrapped) {
            Throwable inner = wrapped.getCause();
            if (inner instanceof Exception ex) {
                throw ex;
            }
            if (inner instanceof Error err) {
                throw err;
            }
            throw new RuntimeException(inner);
        }
        if (cause instanceof Exception ex) {
            throw ex;
        }
        if (cause instanceof Error err) {
            throw err;
        }
        throw new RuntimeException(cause);
    }

    /** checked Exception을 TransactionTemplate 밖으로 전달하기 위한 래퍼. */
    static final class OnlineTimeoutExecutionException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        OnlineTimeoutExecutionException(Exception cause) {
            super(cause);
        }
    }
}
