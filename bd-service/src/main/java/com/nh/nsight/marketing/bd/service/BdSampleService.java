package com.nh.nsight.marketing.bd.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.bd.dao.BdSampleDao;
import com.nh.nsight.marketing.bd.rule.BdSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BdSampleService {
    private final BdSampleRule rule;
    private final BdSampleDao dao;

    public BdSampleService(BdSampleRule rule, BdSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "BD");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
