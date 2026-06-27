package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmTimeoutPolicyFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmTimeoutPolicyDeleteHandler implements TransactionHandler {
    private final OmTimeoutPolicyFacade facade;

    public OmTimeoutPolicyDeleteHandler(OmTimeoutPolicyFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.TimeoutPolicy.delete";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.delete(request.getBody(), context);
    }
}
