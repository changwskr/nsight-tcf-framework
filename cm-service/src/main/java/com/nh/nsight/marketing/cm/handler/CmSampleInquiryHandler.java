package com.nh.nsight.marketing.cm.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.cm.facade.CmSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CmSampleInquiryHandler implements TransactionHandler {
    private final CmSampleFacade facade;

    public CmSampleInquiryHandler(CmSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "CM.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
