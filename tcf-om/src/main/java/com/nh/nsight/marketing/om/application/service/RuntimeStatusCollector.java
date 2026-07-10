package com.nh.nsight.marketing.om.application.service;

import com.nh.nsight.marketing.om.client.OmRuntimeRemoteClient;
import com.nh.nsight.marketing.om.config.OmRuntimeDiagnosticsProperties;
import com.nh.nsight.marketing.om.config.OmRuntimeDiagnosticsProperties.Target;
import com.nh.nsight.tcf.util.DateTimeUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RuntimeStatusCollector {
    private final OmRuntimeDiagnosticsProperties properties;
    private final OmRuntimeRemoteClient remoteClient;

    public RuntimeStatusCollector(
            OmRuntimeDiagnosticsProperties properties,
            OmRuntimeRemoteClient remoteClient) {
        this.properties = properties;
        this.remoteClient = remoteClient;
    }

    public Map<String, Object> collectAll() {
        String checkedAt = DateTimeUtil.nowKst();
        List<Map<String, Object>> targets = enabledTargets().parallelStream()
                .map(this::collectOneTarget)
                .toList();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("checkedAt", checkedAt);
        result.put("targets", targets);
        return result;
    }

    public List<Map<String, Object>> extractActiveTransactions(Map<String, Object> collected) {
        return extractFromStatuses(collected, "activeTransactions", 200);
    }

    public List<Map<String, Object>> extractSlowTransactions(Map<String, Object> collected, int limit) {
        return extractFromStatuses(collected, "slowTransactions", limit);
    }

    public List<Map<String, Object>> extractSlowSql(Map<String, Object> collected, int limit) {
        return extractFromStatuses(collected, "slowSql", limit);
    }

    public List<Map<String, Object>> collectThreads() {
        List<Map<String, Object>> rows = enabledTargets().parallelStream()
                .flatMap(target -> remoteClient.fetchThreads(target).stream()
                        .map(thread -> {
                            Map<String, Object> row = new LinkedHashMap<>(thread);
                            row.putIfAbsent("businessCode", target.getBusinessCode());
                            return row;
                        }))
                .sorted((a, b) -> Long.compare(toLong(b.get("elapsedMs")), toLong(a.get("elapsedMs"))))
                .toList();
        return new ArrayList<>(rows);
    }

    private Map<String, Object> collectOneTarget(Target target) {
        Map<String, Object> status = remoteClient.fetchStatus(target);
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("businessCode", target.getBusinessCode());
        row.put("baseUrl", target.getBaseUrl());
        row.put("reachable", status.getOrDefault("reachable", true));
        row.put("status", status);
        return row;
    }

    private List<Target> enabledTargets() {
        return properties.getTargets().stream()
                .filter(Target::isEnabled)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> extractFromStatuses(
            Map<String, Object> collected, String field, int limit) {
        List<Map<String, Object>> rows = new ArrayList<>();
        Object targetRows = collected.get("targets");
        if (!(targetRows instanceof List<?> list)) {
            return rows;
        }
        for (Object item : list) {
            if (!(item instanceof Map<?, ?> targetRow)) {
                continue;
            }
            String businessCode = String.valueOf(((Map<String, Object>) targetRow).get("businessCode"));
            Object statusObj = ((Map<String, Object>) targetRow).get("status");
            if (!(statusObj instanceof Map<?, ?> statusMap)) {
                continue;
            }
            Object values = ((Map<String, Object>) statusMap).get(field);
            if (!(values instanceof List<?> valueList)) {
                continue;
            }
            for (Object value : valueList) {
                if (!(value instanceof Map<?, ?> valueMap)) {
                    continue;
                }
                Map<String, Object> row = new LinkedHashMap<>((Map<String, Object>) valueMap);
                row.putIfAbsent("businessCode", businessCode);
                rows.add(row);
            }
        }
        rows.sort((a, b) -> Long.compare(toLong(b.get("elapsedMs")), toLong(a.get("elapsedMs"))));
        if (rows.size() > limit) {
            return rows.subList(0, limit);
        }
        return rows;
    }

    private static long toLong(Object value) {
        if (value == null) {
            return 0L;
        }
        return Long.parseLong(String.valueOf(value));
    }
}
