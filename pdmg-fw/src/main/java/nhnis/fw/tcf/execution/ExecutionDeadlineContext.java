package nhnis.fw.tcf.execution;

/**
 * Worker Thread에서 MyBatis/JDBC가 참조하는 현재 거래 deadline.
 */
public final class ExecutionDeadlineContext {

    private static final ThreadLocal<ExecutionDeadline> DEADLINE = new ThreadLocal<>();

    private ExecutionDeadlineContext() {
    }

    public static void bind(ExecutionDeadline deadline) {
        DEADLINE.set(deadline);
    }

    public static ExecutionDeadline current() {
        return DEADLINE.get();
    }

    public static void clear() {
        DEADLINE.remove();
    }
}
