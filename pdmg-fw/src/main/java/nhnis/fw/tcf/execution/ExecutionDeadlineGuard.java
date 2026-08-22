package nhnis.fw.tcf.execution;

import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.timeout.OnlineTimeoutException;
import nhnis.fw.tcf.timeout.OnlineTimeoutProperties;

/**
 * {@link ExecutionDeadline} 기준 공통 timeout 검사.
 *
 * <p>stf / Worker / etf 가 동일 deadline 을 참조할 때 사용한다.
 */
public final class ExecutionDeadlineGuard {

    private ExecutionDeadlineGuard() {
    }

    public static void throwIfExpired(TransactionContext context, OnlineTimeoutProperties properties) {
        if (properties == null || !properties.isEnabled()) {
            return;
        }
        ExecutionDeadline deadline = ExecutionDeadlineContext.current();
        if (deadline == null || !deadline.expired()) {
            return;
        }
        String serviceId = context == null ? null : context.getServiceId();
        String guid = context == null ? null : context.getGuid();
        long timeoutMs = properties.resolveMilliseconds(serviceId);
        throw new OnlineTimeoutException(timeoutMs, deadline.elapsedMillis(), serviceId, guid);
    }
}
