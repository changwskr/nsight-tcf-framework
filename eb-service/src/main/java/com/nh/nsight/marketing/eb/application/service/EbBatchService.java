package com.nh.nsight.marketing.eb.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.eb.config.EbEventPublishProperties;
import com.nh.nsight.marketing.eb.persistence.dao.EbEventDao;
import com.nh.nsight.marketing.eb.support.EbEventStatus;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EbBatchService {
    private final EbEventPublishProperties properties;
    private final EbEventDao eventDao;

    public EbBatchService(EbEventPublishProperties properties, EbEventDao eventDao) {
        this.properties = properties;
        this.eventDao = eventDao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        Map<String, Object> statusSummary = summarizeStatus(eventDao.countEventsByStatus());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "EB");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("enabled", properties.isEnabled());
        result.put("fixedDelayMs", properties.getFixedDelayMs());
        result.put("batchSize", properties.getBatchSize());
        result.put("epOnlineUrl", properties.getEpOnlineUrl());
        result.put("schedulerClass", "EbEventPublishScheduler");
        result.put("schedulerMethod", "publishUserEvents");
        result.put("readyCount", statusSummary.getOrDefault(EbEventStatus.READY, 0));
        result.put("sentCount", statusSummary.getOrDefault(EbEventStatus.SENT, 0));
        result.put("failCount", statusSummary.getOrDefault(EbEventStatus.FAIL, 0));
        result.put("totalCount", statusSummary.getOrDefault("TOTAL", 0));
        result.put("statusSummary", statusSummary);
        return result;
    }

    private Map<String, Object> summarizeStatus(List<Map<String, Object>> rows) {
        Map<String, Object> summary = new LinkedHashMap<>();
        int total = 0;
        for (Map<String, Object> row : rows) {
            String status = stringValue(row, "eventStatus", "EVENT_STATUS");
            int count = readCount(row);
            summary.put(status, count);
            total += count;
        }
        summary.put("TOTAL", total);
        if (!summary.containsKey(EbEventStatus.READY)) {
            summary.put(EbEventStatus.READY, 0);
        }
        if (!summary.containsKey(EbEventStatus.SENT)) {
            summary.put(EbEventStatus.SENT, 0);
        }
        if (!summary.containsKey(EbEventStatus.FAIL)) {
            summary.put(EbEventStatus.FAIL, 0);
        }
        return summary;
    }

    private String stringValue(Map<String, Object> row, String camelKey, String upperKey) {
        Object value = row.get(camelKey);
        if (value == null) {
            value = row.get(upperKey);
        }
        return value == null ? "" : String.valueOf(value);
    }

    private int readCount(Map<String, Object> row) {
        Object value = row.get("count");
        if (value == null) {
            value = row.get("COUNT");
        }
        return parseInt(value, 0);
    }

    private int parseInt(Object value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
