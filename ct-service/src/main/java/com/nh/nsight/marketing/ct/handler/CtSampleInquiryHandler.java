package com.nh.nsight.marketing.ct.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.ct.facade.CtSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CtSampleInquiryHandler implements TransactionHandler {
    private final CtSampleFacade facade;

    public CtSampleInquiryHandler(CtSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "CT.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
