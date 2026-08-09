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

/**
 * Worker Pool + TransactionTemplate + Future.get(timeout) 기반 온라인 타임아웃 실행기.
 *
 * <p>제한시간은 {@link OnlineTimeoutProperties#resolveMilliseconds(String)} 로
 * serviceId별 override를 반영한다.
 */
public class DefaultOnlineTimeoutExecutor implements OnlineTimeoutExecutor {

    private static final Logger log = LoggerFactory.getLogger(DefaultOnlineTimeoutExecutor.class);

    private final OnlineTimeoutProperties properties;
    private final ThreadPoolTaskExecutor taskExecutor;
    private final TransactionTemplate transactionTemplate;

    public DefaultOnlineTimeoutExecutor(OnlineTimeoutProperties properties,
            ThreadPoolTaskExecutor taskExecutor,
            PlatformTransactionManager transactionManager) {
        this.properties = properties;
        this.taskExecutor = taskExecutor;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    @Override
    public <T> T execute(Callable<T> action) throws Exception {
        OnlineTimeoutWorkerContext workerContext = OnlineTimeoutWorkerContext.capture();
        long timeoutMs = properties.resolveMilliseconds(workerContext.getServiceId());
        long startedAtNanos = System.nanoTime();
        long deadlineNanos = startedAtNanos + TimeUnit.MILLISECONDS.toNanos(timeoutMs);

        Future<T> future;
        try {
            future = taskExecutor.getThreadPoolExecutor().submit(
                    () -> runInWorker(workerContext, timeoutMs, deadlineNanos, action));
        } catch (RejectedExecutionException ex) {
            throw overload(workerContext);
        }

        try {
            T result = future.get(timeoutMs, TimeUnit.MILLISECONDS);
            if (log.isDebugEnabled()) {
                log.debug("[ONLINE-TIMEOUT] completed guid={} serviceId={} timeoutMs={} elapsedMs={}",
                        workerContext.getGuid(),
                        workerContext.getServiceId(),
                        timeoutMs,
                        elapsedMs(startedAtNanos));
            }
            return result;
        } catch (TimeoutException ex) {
            boolean cancelled = future.cancel(true);
            long elapsed = elapsedMs(startedAtNanos);
            log.warn("[ONLINE-TIMEOUT] guid={} serviceId={} timeoutMs={} elapsedMs={} cancelRequested={}",
                    workerContext.getGuid(),
                    workerContext.getServiceId(),
                    timeoutMs,
                    elapsed,
                    cancelled);
            throw new OnlineTimeoutException(
                    timeoutMs,
                    elapsed,
                    workerContext.getServiceId(),
                    workerContext.getGuid());
        } catch (InterruptedException ex) {
            future.cancel(true);
            Thread.currentThread().interrupt();
            throw new OnlineTimeoutException(
                    timeoutMs,
                    elapsedMs(startedAtNanos),
                    workerContext.getServiceId(),
                    workerContext.getGuid());
        } catch (ExecutionException ex) {
            throw unwrap(ex.getCause());
        } catch (TaskRejectedException ex) {
            throw overload(workerContext);
        }
    }

    private <T> T runInWorker(OnlineTimeoutWorkerContext workerContext, long timeoutMs, long deadlineNanos,
            Callable<T> action) {
        workerContext.install();
        try {
            return transactionTemplate.execute(status -> {
                try {
                    T result = action.call();
                    if (isDeadlineExceeded(deadlineNanos) || Thread.currentThread().isInterrupted()) {
                        status.setRollbackOnly();
                        long elapsed = elapsedFromDeadline(deadlineNanos, timeoutMs);
                        if (log.isDebugEnabled()) {
                            log.debug("[ONLINE-TIMEOUT] worker deadline exceeded guid={} serviceId={} timeoutMs={} elapsedMs~={}",
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
                }
            });
        } finally {
            workerContext.clear();
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

    private static boolean isDeadlineExceeded(long deadlineNanos) {
        return System.nanoTime() >= deadlineNanos;
    }

    private static long elapsedMs(long startedAtNanos) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedAtNanos);
    }

    private static long elapsedFromDeadline(long deadlineNanos, long timeoutMs) {
        long over = System.nanoTime() - (deadlineNanos - TimeUnit.MILLISECONDS.toNanos(timeoutMs));
        return Math.max(timeoutMs, TimeUnit.NANOSECONDS.toMillis(over));
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
