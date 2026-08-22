package nhnis.fw.tcf.execution;

import java.util.concurrent.TimeUnit;

/**
 * 단일 온라인 거래의 절대 deadline.
 *
 * <p>Queue 대기·Worker 실행·Transaction·SQL이 동일한 시간 예산을 공유한다.
 */
public final class ExecutionDeadline {

    private final long startedAtNanos;
    private final long deadlineNanos;

    private ExecutionDeadline(long startedAtNanos, long deadlineNanos) {
        this.startedAtNanos = startedAtNanos;
        this.deadlineNanos = deadlineNanos;
    }

    public static ExecutionDeadline start(long timeoutMs) {
        long now = System.nanoTime();
        return new ExecutionDeadline(now, now + TimeUnit.MILLISECONDS.toNanos(timeoutMs));
    }

    public long remainingMillis() {
        long remaining = deadlineNanos - System.nanoTime();
        return Math.max(0L, TimeUnit.NANOSECONDS.toMillis(remaining));
    }

    public boolean expired() {
        return System.nanoTime() >= deadlineNanos;
    }

    public long elapsedMillis() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedAtNanos);
    }
}
