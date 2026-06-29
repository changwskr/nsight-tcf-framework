package com.nh.nsight.marketing.ic.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.ic.persistence.dao.IcSampleDao;
import com.nh.nsight.marketing.ic.application.rule.IcSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class IcSampleService {
    private final IcSampleRule rule;
    private final IcSampleDao dao;

    public IcSampleService(IcSampleRule rule, IcSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "IC");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
