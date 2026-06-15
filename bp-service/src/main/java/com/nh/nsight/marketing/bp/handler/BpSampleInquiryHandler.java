package com.nh.nsight.marketing.bp.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.bp.facade.BpSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BpSampleInquiryHandler implements TransactionHandler {
    private final BpSampleFacade facade;

    public BpSampleInquiryHandler(BpSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "BP.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
