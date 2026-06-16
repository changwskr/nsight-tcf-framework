package com.nh.nsight.marketing.om.facade;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.om.service.OmMenuService;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OmMenuFacade {
    private final OmMenuService service;

    public OmMenuFacade(OmMenuService service) {
        this.service = service;
    }

    @Transactional(readOnly = true, timeout = 5)
    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        return service.inquiry(body, context);
    }
}


