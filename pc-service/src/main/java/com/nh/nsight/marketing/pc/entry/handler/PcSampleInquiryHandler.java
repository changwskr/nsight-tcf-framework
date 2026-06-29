package com.nh.nsight.marketing.pc.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.pc.entry.facade.PcSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PcSampleInquiryHandler implements TransactionHandler {
    private final PcSampleFacade facade;

    public PcSampleInquiryHandler(PcSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "PC.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
