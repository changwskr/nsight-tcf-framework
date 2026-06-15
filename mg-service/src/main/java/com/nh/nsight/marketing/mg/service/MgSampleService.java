package com.nh.nsight.marketing.mg.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.mg.dao.MgSampleDao;
import com.nh.nsight.marketing.mg.rule.MgSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MgSampleService {
    private final MgSampleRule rule;
    private final MgSampleDao dao;

    public MgSampleService(MgSampleRule rule, MgSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "MG");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
