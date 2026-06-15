package com.nh.nsight.marketing.ct.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.ct.dao.CtSampleDao;
import com.nh.nsight.marketing.ct.rule.CtSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CtSampleService {
    private final CtSampleRule rule;
    private final CtSampleDao dao;

    public CtSampleService(CtSampleRule rule, CtSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "CT");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
