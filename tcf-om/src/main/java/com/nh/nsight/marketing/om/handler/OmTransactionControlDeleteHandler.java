package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.om.facade.OmTransactionControlFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmTransactionControlDeleteHandler implements TransactionHandler {
    private final OmTransactionControlFacade facade;

    public OmTransactionControlDeleteHandler(OmTransactionControlFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.TransactionControl.delete";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.delete(request.getBody(), context);
    }
}
