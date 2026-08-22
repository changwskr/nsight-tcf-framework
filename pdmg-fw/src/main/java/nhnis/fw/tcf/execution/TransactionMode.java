package nhnis.fw.tcf.execution;

/**
 * ServiceId별 온라인 Transaction 경계 정책.
 */
public enum TransactionMode {

    NONE,
    RDW_READ_ONLY,
    RDW_READ_WRITE,
    ADW_READ_ONLY;

    public boolean requiresTransaction() {
        return this != NONE;
    }

    public String defaultManagerBean() {
        return switch (this) {
            case NONE -> null;
            case RDW_READ_ONLY, RDW_READ_WRITE -> "rdwTransactionManager";
            case ADW_READ_ONLY -> "adwTransactionManager";
        };
    }

    public boolean readOnlyDefault() {
        return this == RDW_READ_ONLY || this == ADW_READ_ONLY;
    }
}
