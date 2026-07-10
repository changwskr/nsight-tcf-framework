package com.nh.nsight.tcf.core.support.runtime;

import com.nh.nsight.tcf.core.support.runtime.model.ActiveTransactionInfo;
import com.nh.nsight.tcf.core.support.runtime.model.RuntimeTransactionStep;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ActiveTransactionRegistry {
    private final Map<String, ActiveTransactionInfo> activeByGuid = new ConcurrentHashMap<>();

    public void register(ActiveTransactionInfo info) {
        if (info == null || info.guid() == null || info.guid().isBlank()) {
            return;
        }
        activeByGuid.put(info.guid(), info);
    }

    public void remove(String guid) {
        if (guid == null || guid.isBlank()) {
            return;
        }
        activeByGuid.remove(guid);
    }

    public void updateStep(String guid, RuntimeTransactionStep step) {
        if (guid == null || step == null) {
            return;
        }
        activeByGuid.computeIfPresent(guid, (key, current) -> current.withStep(step));
    }

    public void updateSqlId(String guid, String sqlId) {
        if (guid == null || sqlId == null || sqlId.isBlank()) {
            return;
        }
        activeByGuid.computeIfPresent(guid, (key, current) -> current.withSqlId(sqlId)
                .withStep(RuntimeTransactionStep.EXECUTING_SQL));
    }

    public void markDbWait(String guid) {
        if (guid == null) {
            return;
        }
        long now = System.currentTimeMillis();
        activeByGuid.computeIfPresent(guid, (key, current) -> current.withDbWaitStart(now));
    }

    public void markExternalWait(String guid, String externalSystemCode) {
        if (guid == null) {
            return;
        }
        activeByGuid.computeIfPresent(guid, (key, current) -> current.withExternalSystem(externalSystemCode));
    }

    public List<ActiveTransactionInfo> snapshot() {
        return activeByGuid.values().stream()
                .sorted(Comparator.comparingLong(ActiveTransactionInfo::elapsedMillis).reversed())
                .toList();
    }

    public int count() {
        return activeByGuid.size();
    }

    public int countByBusinessCode(String businessCode) {
        if (businessCode == null) {
            return 0;
        }
        int count = 0;
        for (ActiveTransactionInfo info : activeByGuid.values()) {
            if (businessCode.equalsIgnoreCase(info.businessCode())) {
                count++;
            }
        }
        return count;
    }

    public int countByServiceId(String serviceId) {
        if (serviceId == null) {
            return 0;
        }
        int count = 0;
        for (ActiveTransactionInfo info : activeByGuid.values()) {
            if (serviceId.equals(info.serviceId())) {
                count++;
            }
        }
        return count;
    }

    public List<ActiveTransactionInfo> snapshotByBusinessCode(String businessCode) {
        List<ActiveTransactionInfo> rows = new ArrayList<>();
        for (ActiveTransactionInfo info : activeByGuid.values()) {
            if (businessCode == null || businessCode.equalsIgnoreCase(info.businessCode())) {
                rows.add(info);
            }
        }
        rows.sort(Comparator.comparingLong(ActiveTransactionInfo::elapsedMillis).reversed());
        return rows;
    }
}
