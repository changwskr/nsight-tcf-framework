package com.nh.nsight.marketing.bp.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.bp.dao.BpSampleDao;
import com.nh.nsight.marketing.bp.rule.BpSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BpSampleService {
    private final BpSampleRule rule;
    private final BpSampleDao dao;

    public BpSampleService(BpSampleRule rule, BpSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "BP");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
