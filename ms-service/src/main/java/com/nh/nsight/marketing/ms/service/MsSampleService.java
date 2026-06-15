package com.nh.nsight.marketing.ms.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.ms.dao.MsSampleDao;
import com.nh.nsight.marketing.ms.rule.MsSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MsSampleService {
    private final MsSampleRule rule;
    private final MsSampleDao dao;

    public MsSampleService(MsSampleRule rule, MsSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "MS");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
