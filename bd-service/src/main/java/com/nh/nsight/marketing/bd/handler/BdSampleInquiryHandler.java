package com.nh.nsight.marketing.bd.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.bd.facade.BdSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BdSampleInquiryHandler implements TransactionHandler {
    private final BdSampleFacade facade;

    public BdSampleInquiryHandler(BdSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "BD.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
