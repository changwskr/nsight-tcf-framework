package com.nh.nsight.marketing.cm.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.cm.dao.CmSampleDao;
import com.nh.nsight.marketing.cm.rule.CmSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CmSampleService {
    private final CmSampleRule rule;
    private final CmSampleDao dao;

    public CmSampleService(CmSampleRule rule, CmSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "CM");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
