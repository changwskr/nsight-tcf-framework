package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.entry.facade.OmAuthGroupFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmAuthGroupInquiryHandler implements TransactionHandler {
    private final OmAuthGroupFacade facade;

    public OmAuthGroupInquiryHandler(OmAuthGroupFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.AuthGroup.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}

