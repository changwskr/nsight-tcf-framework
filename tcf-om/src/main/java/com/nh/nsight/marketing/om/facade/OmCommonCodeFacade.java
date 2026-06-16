package com.nh.nsight.marketing.om.facade;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.om.service.OmCommonCodeService;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OmCommonCodeFacade {
    private final OmCommonCodeService service;

    public OmCommonCodeFacade(OmCommonCodeService service) {
        this.service = service;
    }

    @Transactional(readOnly = true, timeout = 5)
    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        return service.inquiry(body, context);
    }

    @Transactional(timeout = 5)
    public Map<String, Object> save(Map<String, Object> body, TransactionContext context) {
        return service.save(body, context);
    }
}


