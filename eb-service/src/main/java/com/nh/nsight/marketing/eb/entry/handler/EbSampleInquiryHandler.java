package com.nh.nsight.marketing.eb.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.eb.entry.facade.EbSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EbSampleInquiryHandler implements TransactionHandler {
    private final EbSampleFacade facade;

    public EbSampleInquiryHandler(EbSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "EB.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
