package nhnis.fw.tcf.execution;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import nhnis.fw.tcf.core.context.TransactionContext;

/**
 * Client 응답 종료와 Worker 실행 종료를 분리 추적하는 Runtime Evidence 레지스트리.
 */
@Component
public class OnlineExecutionEvidenceRegistry {

    private static final int RECENT_MAX = 200;

    private final ConcurrentHashMap<String, EvidenceEntry> active = new ConcurrentHashMap<>();
    private final ConcurrentLinkedDeque<Map<String, Object>> recent = new ConcurrentLinkedDeque<>();

    public void begin(TransactionContext context, long configuredTimeoutMs) {
        if (context == null || !StringUtils.hasText(context.getServiceId())) {
            return;
        }
        String key = ExecutionEvidenceKey.assign(context);
        if (key == null) {
            return;
        }
        active.put(key, new EvidenceEntry(
                context.getServiceId().trim(),
                nhnis.fw.commons.runtime.MgActiveTransactionRegistry.resolveBusinessCode(context.getServiceId()),
                context.getGuid(),
                configuredTimeoutMs,
                System.currentTimeMillis(),
                ClientExecutionState.CLIENT_WAITING,
                WorkerExecutionState.SUBMITTED));
    }

    public void markQueued(String evidenceKey) {
        updateWorker(evidenceKey, WorkerExecutionState.QUEUED, entry -> {
            entry.queuedAtMs = System.currentTimeMillis();
        });
    }

    public void markWorkerStarted(String evidenceKey, long workerThreadId) {
        updateWorker(evidenceKey, WorkerExecutionState.WORKER_STARTED, entry -> {
            entry.workerThreadId = workerThreadId;
            entry.workerStartedAtMs = System.currentTimeMillis();
        });
    }

    public void markTxStarted(String evidenceKey, TransactionMode txMode, int txTimeoutSeconds) {
        updateWorker(evidenceKey, WorkerExecutionState.TX_STARTED, entry -> {
            entry.txMode = txMode == null ? null : txMode.name();
            entry.txTimeoutSeconds = txTimeoutSeconds;
            entry.txStartedAtMs = System.currentTimeMillis();
        });
    }

    public void markCancelRequested(String evidenceKey) {
        updateWorker(evidenceKey, WorkerExecutionState.CANCEL_REQUESTED, entry -> {
            entry.cancelRequested = true;
            entry.cancelRequestedAtMs = System.currentTimeMillis();
        });
    }

    public void markClientSuccess(TransactionContext context) {
        markClient(context, ClientExecutionState.SUCCESS_RESPONSE_SENT);
    }

    public void markClientTimeout(TransactionContext context, long elapsedMs) {
        markClient(context, ClientExecutionState.TIMEOUT_RESPONSE_SENT, entry -> entry.clientElapsedMs = elapsedMs);
    }

    public void markClientError(TransactionContext context) {
        markClient(context, ClientExecutionState.ERROR_RESPONSE_SENT);
    }

    public void finishClient(TransactionContext context) {
        if (context == null) {
            return;
        }
        String key = ExecutionEvidenceKey.keyOf(context);
        EvidenceEntry entry = active.get(key);
        if (entry == null) {
            return;
        }
        entry.clientEndedAtMs = System.currentTimeMillis();
        if (entry.workerState == WorkerExecutionState.TERMINATED) {
            archive(key, entry);
        }
    }

    public void markWorkerCommitted(String evidenceKey) {
        updateWorker(evidenceKey, WorkerExecutionState.COMMITTED, entry -> entry.workerCommittedAtMs = System.currentTimeMillis());
    }

    public void markWorkerRolledBack(String evidenceKey) {
        updateWorker(evidenceKey, WorkerExecutionState.ROLLED_BACK, entry -> entry.workerRolledBackAtMs = System.currentTimeMillis());
    }

    public void markWorkerTerminated(String evidenceKey) {
        if (!StringUtils.hasText(evidenceKey)) {
            return;
        }
        String key = evidenceKey.trim();
        EvidenceEntry entry = active.get(key);
        if (entry == null) {
            return;
        }
        entry.workerState = WorkerExecutionState.TERMINATED;
        entry.workerTerminatedAtMs = System.currentTimeMillis();
        if (entry.clientState != ClientExecutionState.CLIENT_WAITING) {
            archive(key, entry);
        }
    }

    public int activeCount() {
        return active.size();
    }

    public int workerOverrunCount() {
        int count = 0;
        for (EvidenceEntry entry : active.values()) {
            if (entry.clientState == ClientExecutionState.TIMEOUT_RESPONSE_SENT
                    && entry.workerState != WorkerExecutionState.TERMINATED) {
                count++;
            }
        }
        return count;
    }

    public List<Map<String, Object>> snapshotActive(int limit) {
        int max = Math.max(1, limit);
        List<Map<String, Object>> rows = new ArrayList<>();
        long now = System.currentTimeMillis();
        for (EvidenceEntry entry : active.values()) {
            if (rows.size() >= max) {
                break;
            }
            rows.add(toMap(entry, now));
        }
        return rows;
    }

    public List<Map<String, Object>> snapshotRecent(int limit) {
        int max = Math.max(1, limit);
        List<Map<String, Object>> rows = new ArrayList<>(max);
        for (Map<String, Object> row : recent) {
            rows.add(row);
            if (rows.size() >= max) {
                break;
            }
        }
        return rows;
    }

    private void markClient(TransactionContext context, ClientExecutionState clientState) {
        markClient(context, clientState, entry -> {
        });
    }

    private void markClient(TransactionContext context, ClientExecutionState clientState,
            java.util.function.Consumer<EvidenceEntry> customizer) {
        if (context == null) {
            return;
        }
        String key = ExecutionEvidenceKey.keyOf(context);
        EvidenceEntry entry = active.get(key);
        if (entry == null) {
            return;
        }
        entry.clientState = clientState;
        customizer.accept(entry);
    }

    private void updateWorker(String evidenceKey, WorkerExecutionState workerState,
            java.util.function.Consumer<EvidenceEntry> customizer) {
        if (!StringUtils.hasText(evidenceKey)) {
            return;
        }
        EvidenceEntry entry = active.get(evidenceKey.trim());
        if (entry == null) {
            return;
        }
        entry.workerState = workerState;
        customizer.accept(entry);
    }

    private void archive(String key, EvidenceEntry entry) {
        active.remove(key, entry);
        recent.addFirst(toMap(entry, System.currentTimeMillis()));
        while (recent.size() > RECENT_MAX) {
            recent.removeLast();
        }
    }

    private static Map<String, Object> toMap(EvidenceEntry entry, long now) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("businessCode", entry.businessCode);
        row.put("serviceId", entry.serviceId);
        row.put("guid", entry.guid);
        row.put("configuredTimeoutMs", entry.configuredTimeoutMs);
        row.put("clientState", entry.clientState.name());
        row.put("workerState", entry.workerState.name());
        row.put("cancelRequested", entry.cancelRequested);
        row.put("elapsedMs", Math.max(0L, now - entry.startedAtMs));
        row.put("clientElapsedMs", entry.clientElapsedMs);
        row.put("workerThreadId", entry.workerThreadId);
        row.put("txMode", entry.txMode);
        row.put("txTimeoutSeconds", entry.txTimeoutSeconds);
        row.put("queuedAtMs", entry.queuedAtMs);
        row.put("workerStartedAtMs", entry.workerStartedAtMs);
        row.put("txStartedAtMs", entry.txStartedAtMs);
        row.put("cancelRequestedAtMs", entry.cancelRequestedAtMs);
        row.put("clientEndedAtMs", entry.clientEndedAtMs);
        row.put("workerTerminatedAtMs", entry.workerTerminatedAtMs);
        row.put("workerOverrun", entry.clientState == ClientExecutionState.TIMEOUT_RESPONSE_SENT
                && entry.workerState != WorkerExecutionState.TERMINATED);
        row.put("currentStep", entry.workerState.name());
        return row;
    }

    static final class EvidenceEntry {
        final String serviceId;
        final String businessCode;
        final String guid;
        final long configuredTimeoutMs;
        final long startedAtMs;
        ClientExecutionState clientState;
        WorkerExecutionState workerState;
        boolean cancelRequested;
        long clientElapsedMs = -1;
        long workerThreadId = -1;
        String txMode;
        int txTimeoutSeconds = -1;
        long queuedAtMs = -1;
        long workerStartedAtMs = -1;
        long txStartedAtMs = -1;
        long cancelRequestedAtMs = -1;
        long clientEndedAtMs = -1;
        long workerCommittedAtMs = -1;
        long workerRolledBackAtMs = -1;
        long workerTerminatedAtMs = -1;

        EvidenceEntry(String serviceId, String businessCode, String guid, long configuredTimeoutMs,
                long startedAtMs, ClientExecutionState clientState, WorkerExecutionState workerState) {
            this.serviceId = serviceId;
            this.businessCode = businessCode;
            this.guid = guid;
            this.configuredTimeoutMs = configuredTimeoutMs;
            this.startedAtMs = startedAtMs;
            this.clientState = clientState;
            this.workerState = workerState;
        }
    }
}
