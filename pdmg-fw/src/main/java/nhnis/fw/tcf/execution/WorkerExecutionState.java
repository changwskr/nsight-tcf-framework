package nhnis.fw.tcf.execution;

/**
 * Worker Thread 관점의 거래 실행 상태.
 */
public enum WorkerExecutionState {
    SUBMITTED,
    QUEUED,
    WORKER_STARTED,
    TX_STARTED,
    CANCEL_REQUESTED,
    COMMITTED,
    ROLLED_BACK,
    TERMINATED
}
