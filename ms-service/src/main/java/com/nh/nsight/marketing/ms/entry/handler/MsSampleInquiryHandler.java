package com.nh.nsight.marketing.ms.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.ms.entry.facade.MsSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MsSampleInquiryHandler implements TransactionHandler {
    private final MsSampleFacade facade;

    public MsSampleInquiryHandler(MsSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "MS.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
