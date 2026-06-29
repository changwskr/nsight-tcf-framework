package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.entry.facade.OmFunctionAuthFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmFunctionAuthInquiryHandler implements TransactionHandler {
    private final OmFunctionAuthFacade facade;

    public OmFunctionAuthInquiryHandler(OmFunctionAuthFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.FunctionAuth.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}

