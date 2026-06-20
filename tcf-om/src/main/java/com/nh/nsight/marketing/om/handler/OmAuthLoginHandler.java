package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmAuthFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmAuthLoginHandler implements TransactionHandler {
    private final OmAuthFacade facade;

    public OmAuthLoginHandler(OmAuthFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Auth.login";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.login(request.getBody(), context);
    }
}
