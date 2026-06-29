package com.nh.nsight.marketing.eb.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.eb.entry.facade.EbUserFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EbUserInquiryHandler implements TransactionHandler {
    private final EbUserFacade facade;

    public EbUserInquiryHandler(EbUserFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "EB.User.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
