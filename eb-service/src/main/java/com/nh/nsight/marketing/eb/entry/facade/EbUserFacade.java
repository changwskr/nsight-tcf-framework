package com.nh.nsight.marketing.eb.entry.facade;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.eb.application.service.EbUserService;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EbUserFacade {
    private final EbUserService service;

    public EbUserFacade(EbUserService service) {
        this.service = service;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        return service.inquiry(body, context);
    }

    @Transactional(timeout = 5)
    public Map<String, Object> create(Map<String, Object> body, TransactionContext context) {
        return service.create(body, context);
    }
}
