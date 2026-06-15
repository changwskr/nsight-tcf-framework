package com.nh.nsight.marketing.ss.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.ss.dao.SsSampleDao;
import com.nh.nsight.marketing.ss.rule.SsSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SsSampleService {
    private final SsSampleRule rule;
    private final SsSampleDao dao;

    public SsSampleService(SsSampleRule rule, SsSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "SS");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
