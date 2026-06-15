package com.nh.nsight.marketing.pd.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.pd.dao.PdSampleDao;
import com.nh.nsight.marketing.pd.rule.PdSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PdSampleService {
    private final PdSampleRule rule;
    private final PdSampleDao dao;

    public PdSampleService(PdSampleRule rule, PdSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "PD");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
