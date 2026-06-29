package com.nh.nsight.marketing.eb.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.eb.persistence.dao.EbSampleDao;
import com.nh.nsight.marketing.eb.application.rule.EbSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EbSampleService {
    private final EbSampleRule rule;
    private final EbSampleDao dao;

    public EbSampleService(EbSampleRule rule, EbSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "EB");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
