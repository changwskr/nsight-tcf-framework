package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.marketing.om.entry.facade.OmFunctionAuthFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmFunctionAuthUpdateHandler implements TransactionHandler {
    private final OmFunctionAuthFacade facade;

    public OmFunctionAuthUpdateHandler(OmFunctionAuthFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.FunctionAuth.update";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.update(request.getBody(), context);
    }
}
