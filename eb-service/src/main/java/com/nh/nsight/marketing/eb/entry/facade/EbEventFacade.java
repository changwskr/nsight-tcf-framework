package com.nh.nsight.marketing.eb.entry.facade;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.eb.application.service.EbEventService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class EbEventFacade {
    private final EbEventService service;

    public EbEventFacade(EbEventService service) {
        this.service = service;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        return service.inquiry(body, context);
    }
}
