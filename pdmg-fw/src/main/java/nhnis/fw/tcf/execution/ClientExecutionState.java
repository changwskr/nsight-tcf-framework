package nhnis.fw.tcf.execution;

/**
 * HTTP 클라이언트 관점의 거래 종료 상태.
 */
public enum ClientExecutionState {
    CLIENT_WAITING,
    SUCCESS_RESPONSE_SENT,
    TIMEOUT_RESPONSE_SENT,
    ERROR_RESPONSE_SENT
}
