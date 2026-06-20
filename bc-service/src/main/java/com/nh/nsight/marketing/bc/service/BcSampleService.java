package com.nh.nsight.marketing.bc.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.bc.dao.BcSampleDao;
import com.nh.nsight.marketing.bc.rule.BcSampleRule;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BcSampleService {
    private final BcSampleRule rule;
    private final BcSampleDao dao;

    public BcSampleService(BcSampleRule rule, BcSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        System.out.println("\n ==============================================[BcSampleService.inquiry] start");
        System.out.println(" ==============================================[BcSampleService.inquiry] serviceId="
                + context.getHeader().getServiceId());
        System.out.println(" ==============================================[BcSampleService.inquiry] guid="
                + context.getHeader().getGuid());
        System.out.println(" ==============================================[BcSampleService.inquiry] body=" + body);

        rule.validateInquiry(body);
        Map<String, Object> data = dao.selectSample(body);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "BC");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("data", data);

        System.out.println(" ==============================================[BcSampleService.inquiry] data=" + data);
        System.out.println(" ==============================================[BcSampleService.inquiry] end");
        return result;
    }
}
