package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.facade.OmBatchFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmBatchInquiryHandler implements TransactionHandler {
    private final OmBatchFacade facade;

    public OmBatchInquiryHandler(OmBatchFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Batch.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}

