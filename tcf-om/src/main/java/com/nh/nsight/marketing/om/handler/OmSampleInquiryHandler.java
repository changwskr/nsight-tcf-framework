package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmSampleFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmSampleInquiryHandler implements TransactionHandler {
    private final OmSampleFacade facade;

    public OmSampleInquiryHandler(OmSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}

