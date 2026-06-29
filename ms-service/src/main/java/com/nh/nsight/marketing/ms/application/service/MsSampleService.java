package com.nh.nsight.marketing.ms.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.ms.application.rule.MsSampleRule;
import com.nh.nsight.marketing.ms.persistence.dao.MsSampleDao;
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
