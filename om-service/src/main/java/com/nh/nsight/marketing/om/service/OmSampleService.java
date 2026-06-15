package com.nh.nsight.marketing.om.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.om.dao.OmSampleDao;
import com.nh.nsight.marketing.om.rule.OmSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OmSampleService {
    private final OmSampleRule rule;
    private final OmSampleDao dao;

    public OmSampleService(OmSampleRule rule, OmSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "OM");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
