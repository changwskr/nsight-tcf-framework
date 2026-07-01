package com.nh.nsight.marketing.sv.application.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.sv.application.rule.SvSampleRule;
import com.nh.nsight.marketing.sv.persistence.dao.SvSampleDao;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SvSampleService {
    private final SvSampleRule rule;
    private final SvSampleDao dao;

    public SvSampleService(SvSampleRule rule, SvSampleDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateInquiry(body);
        Map<String, Object> criteria = rule.buildSearchCriteria(body);
        List<Map<String, Object>> list = dao.searchSamples(criteria);
        int totalCount = dao.countSamples(criteria);

        int pageNo = (int) criteria.get("pageNo");
        int pageSize = (int) criteria.get("pageSize");
        int totalPage = totalCount == 0 ? 0 : (totalCount + pageSize - 1) / pageSize;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "SV");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("list", list);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        result.put("totalCount", totalCount);
        result.put("totalPage", totalPage);
        return result;
    }
}
