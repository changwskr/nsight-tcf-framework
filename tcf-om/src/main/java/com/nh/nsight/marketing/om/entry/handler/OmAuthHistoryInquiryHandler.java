package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.entry.facade.OmAuthHistoryFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmAuthHistoryInquiryHandler implements TransactionHandler {
    private final OmAuthHistoryFacade facade;

    public OmAuthHistoryInquiryHandler(OmAuthHistoryFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.AuthHistory.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}

