package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.marketing.om.entry.facade.OmTimeoutPolicyFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmTimeoutPolicyUpdateHandler implements TransactionHandler {
    private final OmTimeoutPolicyFacade facade;

    public OmTimeoutPolicyUpdateHandler(OmTimeoutPolicyFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.TimeoutPolicy.update";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.update(request.getBody(), context);
    }
}
