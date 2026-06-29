package com.nh.nsight.marketing.eb.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.eb.persistence.dao.EbEventDao;
import com.nh.nsight.marketing.eb.application.rule.EbEventRule;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EbEventService {
    private final EbEventRule rule;
    private final EbEventDao eventDao;

    public EbEventService(EbEventRule rule, EbEventDao eventDao) {
        this.rule = rule;
        this.eventDao = eventDao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> criteria = buildSearchCriteria(body);
        List<Map<String, Object>> rows = eventDao.searchEvents(criteria);
        int totalCount = eventDao.countEvents(criteria);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "EB");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("rows", rows);
        result.put("totalCount", totalCount);
        result.put("pageNo", criteria.get("pageNo"));
        result.put("pageSize", criteria.get("pageSize"));
        result.put("statusSummary", summarizeStatus(eventDao.countEventsByStatus()));
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

    private Map<String, Object> buildSearchCriteria(Map<String, Object> body) {
        Map<String, Object> safeBody = body != null ? body : Map.of();
        int pageNo = parseInt(safeBody.get("pageNo"), 1);
        int pageSize = parseInt(safeBody.get("pageSize"), 20);
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = 20;
        }

        Map<String, Object> criteria = new HashMap<>();
        criteria.put("pageNo", pageNo);
        criteria.put("pageSize", pageSize);
        criteria.put("offset", (pageNo - 1) * pageSize);
        putTrimmed(criteria, "eventId", safeBody.get("eventId"));
        putTrimmed(criteria, "userId", safeBody.get("userId"));
        putTrimmed(criteria, "eventType", safeBody.get("eventType"));
        putTrimmed(criteria, "eventStatus", safeBody.get("eventStatus"));
        return criteria;
    }

    private void putTrimmed(Map<String, Object> target, String key, Object value) {
        if (value == null) {
            return;
        }
        String text = String.valueOf(value).trim();
        if (!text.isEmpty()) {
            target.put(key, text);
        }
    }
}
