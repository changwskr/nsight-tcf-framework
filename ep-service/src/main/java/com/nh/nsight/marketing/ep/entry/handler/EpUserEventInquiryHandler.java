package com.nh.nsight.marketing.ep.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.ep.entry.facade.EpUserEventFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EpUserEventInquiryHandler implements TransactionHandler {
    private final EpUserEventFacade facade;

    public EpUserEventInquiryHandler(EpUserEventFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "EP.UserEvent.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
