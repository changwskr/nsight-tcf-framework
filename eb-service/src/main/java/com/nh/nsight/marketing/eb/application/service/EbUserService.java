package com.nh.nsight.marketing.eb.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import com.nh.nsight.tcf.util.GuidGenerator;
import com.nh.nsight.marketing.eb.persistence.dao.EbEventDao;
import com.nh.nsight.marketing.eb.persistence.dao.EbUserDao;
import com.nh.nsight.marketing.eb.application.rule.EbUserRule;
import com.nh.nsight.marketing.eb.support.EbEventStatus;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EbUserService {
    private final EbUserRule rule;
    private final EbUserDao userDao;
    private final EbEventDao eventDao;

    public EbUserService(EbUserRule rule, EbUserDao userDao, EbEventDao eventDao) {
        this.rule = rule;
        this.userDao = userDao;
        this.eventDao = eventDao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> criteria = buildSearchCriteria(body);
        List<Map<String, Object>> rows = userDao.searchUsers(criteria);
        int totalCount = userDao.countUsers(criteria);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "EB");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("rows", rows);
        result.put("totalCount", totalCount);
        result.put("pageNo", criteria.get("pageNo"));
        result.put("pageSize", criteria.get("pageSize"));
        return result;
    }

    @Transactional(timeout = 5)
    public Map<String, Object> create(Map<String, Object> body, TransactionContext context) {
        rule.validateCreate(body);
        String userId = String.valueOf(body.get("userId")).trim();
        String userName = String.valueOf(body.get("userName")).trim();
        String branchId = body.get("branchId") != null ? String.valueOf(body.get("branchId")).trim() : null;

        if (userDao.existsByUserId(userId)) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "이미 등록된 사용자입니다: " + userId);
        }

        Map<String, Object> userRow = new HashMap<>();
        userRow.put("userId", userId);
        userRow.put("userName", userName);
        userRow.put("branchId", branchId);
        userDao.insertUser(userRow);

        System.out.println("---------- [EB-EVENT] START Outbox 적재 (EB.User.create / EbUserService.create) userId="
                + userId + " ----------");
        String eventId = GuidGenerator.newGuid();
        Map<String, Object> eventRow = new HashMap<>();
        eventRow.put("eventId", eventId);
        eventRow.put("userId", userId);
        eventRow.put("eventType", EbEventStatus.USER_CREATED);
        eventRow.put("eventStatus", EbEventStatus.READY);
        eventRow.put("retryCount", 0);
        eventDao.insertEvent(eventRow);
        System.out.println("---------- [EB-EVENT] END Outbox 적재 (EbUserService.create) eventId=" + eventId
                + " eventType=" + EbEventStatus.USER_CREATED + " status=" + EbEventStatus.READY + " ----------");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "EB");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("userId", userId);
        result.put("userName", userName);
        result.put("branchId", branchId);
        result.put("eventId", eventId);
        result.put("eventType", EbEventStatus.USER_CREATED);
        result.put("eventStatus", EbEventStatus.READY);
        return result;
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
        putTrimmed(criteria, "userId", safeBody.get("userId"));
        putTrimmed(criteria, "userName", safeBody.get("userName"));
        putTrimmed(criteria, "branchId", safeBody.get("branchId"));
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
