package com.nh.nsight.marketing.om.facade;

import com.nh.nsight.marketing.om.service.OmSampleService;
import com.nh.nsight.tcf.core.context.TransactionContext;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OmSampleFacade {
    private final OmSampleService service;

    public OmSampleFacade(OmSampleService service) {
        this.service = service;
    }

    @Transactional(readOnly = true, timeout = 5)
    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        return service.inquiry(body, context);
    }
}


