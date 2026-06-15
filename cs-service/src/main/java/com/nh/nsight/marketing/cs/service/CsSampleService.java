package com.nh.nsight.marketing.cs.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.cs.dao.CsSampleDao;
import com.nh.nsight.marketing.cs.rule.CsSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CsSampleService {
    private final CsSampleRule rule;
    private final CsSampleDao dao;

    public CsSampleService(CsSampleRule rule, CsSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "CS");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
