package nhnis.fw.tcf.execution;

/**
 * ServiceId에 적용할 Physical Transaction 정책.
 */
public record TransactionPolicy(
        TransactionMode mode,
        String transactionManagerBean,
        boolean readOnly) {

    public boolean requiresTransaction() {
        return mode != null && mode.requiresTransaction();
    }
}
