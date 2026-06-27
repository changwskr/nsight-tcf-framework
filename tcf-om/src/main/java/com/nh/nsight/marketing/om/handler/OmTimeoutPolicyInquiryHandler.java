package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmTimeoutPolicyFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmTimeoutPolicyInquiryHandler implements TransactionHandler {
    private final OmTimeoutPolicyFacade facade;

    public OmTimeoutPolicyInquiryHandler(OmTimeoutPolicyFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.TimeoutPolicy.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
