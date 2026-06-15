package com.nh.nsight.marketing.pc.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.pc.dao.PcSampleDao;
import com.nh.nsight.marketing.pc.rule.PcSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PcSampleService {
    private final PcSampleRule rule;
    private final PcSampleDao dao;

    public PcSampleService(PcSampleRule rule, PcSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "PC");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);
        return result;
    }
}
