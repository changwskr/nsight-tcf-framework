package com.nh.nsight.marketing.sv.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.sv.entry.facade.SvSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SvSampleInquiryHandler implements TransactionHandler {
    private final SvSampleFacade facade;

    public SvSampleInquiryHandler(SvSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "SV.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
