package com.nh.nsight.marketing.eb.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.eb.entry.facade.EbBatchFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EbBatchInquiryHandler implements TransactionHandler {
    private final EbBatchFacade facade;

    public EbBatchInquiryHandler(EbBatchFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "EB.Batch.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
