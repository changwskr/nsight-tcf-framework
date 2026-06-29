package com.nh.nsight.marketing.ic.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.ic.entry.facade.IcSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class IcSampleInquiryHandler implements TransactionHandler {
    private final IcSampleFacade facade;

    public IcSampleInquiryHandler(IcSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "IC.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
