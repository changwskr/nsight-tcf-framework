package com.nh.nsight.marketing.sv.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.sv.entry.facade.SvCustomerFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SvCustomerSummaryHandler implements TransactionHandler {
    private final SvCustomerFacade facade;

    public SvCustomerSummaryHandler(SvCustomerFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "SV.Customer.selectSummary";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.selectCustomerSummary(request.getBody(), context);
    }
}
