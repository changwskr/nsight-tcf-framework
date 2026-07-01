package com.nh.nsight.marketing.ep.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import com.nh.nsight.marketing.ep.persistence.dao.EpUserEventDao;
import com.nh.nsight.marketing.ep.application.rule.EpUserEventRule;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpUserEventService {
    private static final Logger log = LoggerFactory.getLogger(EpUserEventService.class);

    private final EpUserEventRule rule;
    private final EpUserEventDao dao;

    public EpUserEventService(EpUserEventRule rule, EpUserEventDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> criteria = rule.buildSearchCriteria(body);
        List<Map<String, Object>> list = dao.searchReceivedEvents(criteria);
        int totalCount = dao.countReceivedEvents(criteria);
        return buildPagedResult(context, criteria, list, totalCount);
    }

    @Transactional(timeout = 5)
    public Map<String, Object> receive(Map<String, Object> body, TransactionContext context) {
        String eventId = body != null && body.get("eventId") != null ? String.valueOf(body.get("eventId")).trim() : "";
        System.out.println("========== [EP-EVENT] START receive (EP.UserEvent.receive / EpUserEventService.receive) eventId="
                + eventId + " ==========");
        try {
            rule.validateReceive(body);
            eventId = String.valueOf(body.get("eventId")).trim();
            String userId = String.valueOf(body.get("userId")).trim();
            String eventType = String.valueOf(body.get("eventType")).trim();
            System.out.println("[EP-EVENT] body (EpUserEventService.receive) eventId=" + eventId
                    + " userId=" + userId + " eventType=" + eventType);

            if (dao.existsByEventId(eventId)) {
                throw new BusinessException(ErrorCode.BUSINESS_ERROR, "이미 수신한 이벤트입니다: " + eventId);
            }

            log.info("EP user event received eventId={} userId={} eventType={}", eventId, userId, eventType);

            Map<String, Object> row = new HashMap<>();
            row.put("eventId", eventId);
            row.put("userId", userId);
            row.put("eventType", eventType);
            dao.insertReceivedEvent(row);

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("businessCode", "EP");
            result.put("serviceId", context.getHeader().getServiceId());
            result.put("guid", context.getHeader().getGuid());
            result.put("eventId", eventId);
            result.put("userId", userId);
            result.put("eventType", eventType);
            result.put("received", true);
            System.out.println("[EP-EVENT] EP_USER_EVENT 저장 완료 (EpUserEventService.receive) eventId=" + eventId);
            return result;
        } catch (Exception e) {
            System.out.println("[EP-EVENT] ERROR receive (EpUserEventService.receive) eventId=" + eventId
                    + " message=" + e.getMessage());
            throw e;
        } finally {
            System.out.println("========== [EP-EVENT] END receive (EP.UserEvent.receive / EpUserEventService.receive) eventId="
                    + eventId + " ==========");
        }
    }

    private Map<String, Object> buildPagedResult(
            TransactionContext context,
            Map<String, Object> criteria,
            List<Map<String, Object>> list,
            int totalCount) {
        int pageNo = (int) criteria.get("pageNo");
        int pageSize = (int) criteria.get("pageSize");
        int totalPage = totalCount == 0 ? 0 : (totalCount + pageSize - 1) / pageSize;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "EP");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("list", list);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        result.put("totalCount", totalCount);
        result.put("totalPage", totalPage);
        return result;
    }
}
