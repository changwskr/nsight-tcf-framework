package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmFunctionAuthFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmFunctionAuthSaveHandler implements TransactionHandler {
    private final OmFunctionAuthFacade facade;

    public OmFunctionAuthSaveHandler(OmFunctionAuthFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.FunctionAuth.save";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.save(request.getBody(), context);
    }
}
