package nhnis.fw.commons.runtime;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.execution.OnlineExecutionEvidenceRegistry;
import nhnis.fw.tcf.timeout.OnlineTimeoutProperties;

/**
 * 실행 중 온라인 거래 레지스트리 (런타임 진단용).
 *
 * <p>Client/Worker 상태 분리는 {@link OnlineExecutionEvidenceRegistry} 에 위임한다.
 */
@Component
public class MgActiveTransactionRegistry {

    private final OnlineExecutionEvidenceRegistry evidence;
    private final OnlineTimeoutProperties timeoutProperties;

    public MgActiveTransactionRegistry(
            OnlineExecutionEvidenceRegistry evidence,
            OnlineTimeoutProperties timeoutProperties) {
        this.evidence = evidence;
        this.timeoutProperties = timeoutProperties;
    }

    public void begin(TransactionContext context) {
        if (context == null || !StringUtils.hasText(context.getServiceId())) {
            return;
        }
        long configuredTimeoutMs = timeoutProperties.resolveMilliseconds(context.getServiceId());
        evidence.begin(context, configuredTimeoutMs);
    }

    /**
     * @deprecated Client 종료는 {@link #finishClient(TransactionContext)} 를 사용한다.
     */
    @Deprecated
    public void end(TransactionContext context) {
        finishClient(context);
    }

    public void markClientSuccess(TransactionContext context) {
        evidence.markClientSuccess(context);
    }

    public void markClientTimeout(TransactionContext context, long elapsedMs) {
        evidence.markClientTimeout(context, elapsedMs);
    }

    public void markClientError(TransactionContext context) {
        evidence.markClientError(context);
    }

    public void finishClient(TransactionContext context) {
        evidence.finishClient(context);
    }

    public int count() {
        return evidence.activeCount();
    }

    public int workerOverrunCount() {
        return evidence.workerOverrunCount();
    }

    public List<Map<String, Object>> snapshot(int limit) {
        return evidence.snapshotActive(limit);
    }

    public List<Map<String, Object>> snapshotRecent(int limit) {
        return evidence.snapshotRecent(limit);
    }

    public static String resolveBusinessCode(String serviceId) {
        if (!StringUtils.hasText(serviceId)) {
            return "MG";
        }
        String id = serviceId.trim();
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < id.length() && letters.length() < 2; i++) {
            char c = id.charAt(i);
            if (Character.isLetter(c)) {
                letters.append(Character.toUpperCase(c));
            } else if (letters.length() > 0) {
                break;
            }
        }
        return letters.length() > 0 ? letters.toString() : "MG";
    }
}
