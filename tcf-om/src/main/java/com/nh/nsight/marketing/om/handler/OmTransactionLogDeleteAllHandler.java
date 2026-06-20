package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmTransactionLogFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmTransactionLogDeleteAllHandler implements TransactionHandler {
    private final OmTransactionLogFacade facade;

    public OmTransactionLogDeleteAllHandler(OmTransactionLogFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.TransactionLog.deleteAll";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.deleteAll(request.getBody(), context);
    }
}
