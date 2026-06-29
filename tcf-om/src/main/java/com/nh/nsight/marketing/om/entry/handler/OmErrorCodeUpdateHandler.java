package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.entry.facade.OmErrorCodeFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmErrorCodeUpdateHandler implements TransactionHandler {
    private final OmErrorCodeFacade facade;

    public OmErrorCodeUpdateHandler(OmErrorCodeFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.ErrorCode.update";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.update(request.getBody(), context);
    }
}
