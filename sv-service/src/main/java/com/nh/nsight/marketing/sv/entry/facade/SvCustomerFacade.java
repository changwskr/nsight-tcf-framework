package com.nh.nsight.marketing.sv.entry.facade;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.sv.application.service.SvCustomerService;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SvCustomerFacade {
    private final SvCustomerService service;

    public SvCustomerFacade(SvCustomerService service) {
        this.service = service;
    }

    @Transactional(readOnly = true, timeout = 3)
    public Map<String, Object> selectCustomerSummary(Map<String, Object> body, TransactionContext context) {
        return service.selectCustomerSummary(body, context);
    }
}
