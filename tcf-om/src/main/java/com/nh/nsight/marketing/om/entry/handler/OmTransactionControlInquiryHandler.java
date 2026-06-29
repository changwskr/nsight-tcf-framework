package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.om.entry.facade.OmTransactionControlFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmTransactionControlInquiryHandler implements TransactionHandler {
    private final OmTransactionControlFacade facade;

    public OmTransactionControlInquiryHandler(OmTransactionControlFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.TransactionControl.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
