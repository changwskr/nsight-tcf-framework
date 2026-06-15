package com.nh.nsight.marketing.ep.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.ep.facade.EpSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EpSampleInquiryHandler implements TransactionHandler {
    private final EpSampleFacade facade;

    public EpSampleInquiryHandler(EpSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "EP.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
