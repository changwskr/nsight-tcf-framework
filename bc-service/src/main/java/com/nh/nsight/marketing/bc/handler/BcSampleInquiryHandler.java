package com.nh.nsight.marketing.bc.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.bc.facade.BcSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BcSampleInquiryHandler implements TransactionHandler {
    private final BcSampleFacade facade;

    public BcSampleInquiryHandler(BcSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "BC.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
