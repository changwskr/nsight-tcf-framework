package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.marketing.om.entry.facade.OmSessionFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmSessionInquiryHandler implements TransactionHandler {
    private final OmSessionFacade facade;

    public OmSessionInquiryHandler(OmSessionFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Session.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
