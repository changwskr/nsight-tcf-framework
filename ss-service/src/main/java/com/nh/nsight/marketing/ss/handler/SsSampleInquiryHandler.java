package com.nh.nsight.marketing.ss.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.ss.facade.SsSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SsSampleInquiryHandler implements TransactionHandler {
    private final SsSampleFacade facade;

    public SsSampleInquiryHandler(SsSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "SS.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
