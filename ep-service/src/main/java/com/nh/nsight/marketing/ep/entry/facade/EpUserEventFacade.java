package com.nh.nsight.marketing.ep.entry.facade;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.ep.application.service.EpUserEventService;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpUserEventFacade {
    private final EpUserEventService service;

    public EpUserEventFacade(EpUserEventService service) {
        this.service = service;
    }

    @Transactional(timeout = 5)
    public Map<String, Object> receive(Map<String, Object> body, TransactionContext context) {
        return service.receive(body, context);
    }

    @Transactional(readOnly = true, timeout = 5)
    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        return service.inquiry(body, context);
    }
}
