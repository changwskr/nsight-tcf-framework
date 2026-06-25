package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmAuthGroupFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmAuthGroupUpdateHandler implements TransactionHandler {
    private final OmAuthGroupFacade facade;

    public OmAuthGroupUpdateHandler(OmAuthGroupFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.AuthGroup.update";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.update(request.getBody(), context);
    }
}
