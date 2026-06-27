package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmAuthHistoryFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmAuthHistoryDeleteAllHandler implements TransactionHandler {
    private final OmAuthHistoryFacade facade;

    public OmAuthHistoryDeleteAllHandler(OmAuthHistoryFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.AuthHistory.deleteAll";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.deleteAll(request.getBody(), context);
    }
}
