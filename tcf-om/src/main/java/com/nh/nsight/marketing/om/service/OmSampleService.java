package com.nh.nsight.marketing.om.service;

import com.nh.nsight.marketing.om.dao.OmSampleDao;
import com.nh.nsight.marketing.om.rule.OmSampleRule;
import com.nh.nsight.tcf.core.context.TransactionContext;
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
        rule.validateInquiry(body, context);
        Map<String, Object> data = dao.selectSample(body, context);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "OM");
        result.put("businessName", "Operation Management");
        result.put("businessGroup", "운영");
        result.put("description", "운영관리, 관리자 기능, 기준정보/권한/메뉴/배치/감사 조회 업무");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("transactionCode", context.getHeader().getTransactionCode());
        result.put("data", data);
        return result;
    }
}


