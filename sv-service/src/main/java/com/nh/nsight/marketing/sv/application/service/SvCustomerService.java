package com.nh.nsight.marketing.sv.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.sv.application.rule.SvCustomerRule;
import com.nh.nsight.marketing.sv.persistence.dao.SvCustomerDao;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SvCustomerService {
    private final SvCustomerRule rule;
    private final SvCustomerDao dao;

    public SvCustomerService(SvCustomerRule rule, SvCustomerDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> selectCustomerSummary(Map<String, Object> body, TransactionContext context) {
        Map<String, Object> criteria = rule.buildSummaryCriteria(body);

        Map<String, Object> customer = dao.selectCustomerSummary(criteria);
        rule.validateSummaryResult(customer);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "SV");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.putAll(customer);
        return result;
    }
}
