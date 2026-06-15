package com.nh.nsight.marketing.cc.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.cc.dao.CcSampleDao;
import com.nh.nsight.marketing.cc.rule.CcSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CcSampleService {
    private final CcSampleRule rule;
    private final CcSampleDao dao;

    public CcSampleService(CcSampleRule rule, CcSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "CC");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
