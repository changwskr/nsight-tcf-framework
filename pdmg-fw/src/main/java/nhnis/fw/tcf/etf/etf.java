package nhnis.fw.tcf.etf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.fw.commons.log.PdmgTxFlowLog;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.execution.ExecutionDeadline;
import nhnis.fw.tcf.execution.ExecutionDeadlineContext;
import nhnis.fw.tcf.execution.ExecutionDeadlineGuard;
import nhnis.fw.tcf.timeout.OnlineTimeoutProperties;

/**
 * PDMG ETF (End-of-Transaction Framework).
 *
 * <p>TCF ON 경로에서 Handler 종료 후 공통 후처리를 수행한다.
 * {@link ExecutionDeadline} 이 바인딩되어 있으면 stf·Worker 와 동일 deadline 을 점검한다.
 *
 * <pre>
 * … → Handler → etf.postProcess(checkTimeoutInterval) → …
 * </pre>
 */
@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class etf {

    private static final Logger log = LoggerFactory.getLogger(etf.class);

    private final OnlineTimeoutProperties timeoutProperties;

    public etf(OnlineTimeoutProperties timeoutProperties) {
        this.timeoutProperties = timeoutProperties;
    }

    /**
     * 거래 종료 공통 후처리.
     *
     * @param context 온라인 거래 컨텍스트
     */
    public void postProcess(TransactionContext context) {
        PdmgTxFlowLog.enter(log, etf.class, "postProcess");
        try {
            postProcessInternal(context);
        } finally {
            PdmgTxFlowLog.leave(log, etf.class, "postProcess");
        }
    }

    private void postProcessInternal(TransactionContext context) {
        log.info("========================= [etf.postProcess] 시작");
        try {
            checkTimeoutInterval(context);
        } finally {
            log.info("========================= [etf.postProcess] 종료");
        }
    }

    /**
     * Service Deadline 초과 여부를 검사한다.
     *
     * @param context 거래 컨텍스트 (serviceId·guid 포함)
     */
    public void checkTimeoutInterval(TransactionContext context) {
        if (context == null) {
            return;
        }
        if (timeoutProperties == null || !timeoutProperties.isEnabled()) {
            log.debug("[etf] timeout interval check skipped (disabled)");
            return;
        }

        ExecutionDeadline deadline = ExecutionDeadlineContext.current();
        if (deadline != null) {
            String serviceId = context.getServiceId();
            long timeoutMs = timeoutProperties.resolveMilliseconds(serviceId);
            long elapsedMs = deadline.elapsedMillis();
            String guid = context.getGuid();
            log.info("[etf] timeout interval check serviceId={} timeoutMs={} elapsedMs={} guid={} source=ExecutionDeadline",
                    serviceId, timeoutMs, elapsedMs, guid);
            ExecutionDeadlineGuard.throwIfExpired(context, timeoutProperties);
            return;
        }

        log.debug("[etf] timeout interval check skipped (no ExecutionDeadline bound)");
    }
}
