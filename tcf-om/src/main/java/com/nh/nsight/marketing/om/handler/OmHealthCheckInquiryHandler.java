package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.facade.OmHealthCheckFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmHealthCheckInquiryHandler implements TransactionHandler {
    private final OmHealthCheckFacade facade;

    public OmHealthCheckInquiryHandler(OmHealthCheckFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.HealthCheck.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}

