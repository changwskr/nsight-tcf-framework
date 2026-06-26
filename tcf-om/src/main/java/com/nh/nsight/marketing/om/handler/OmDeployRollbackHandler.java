package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.om.facade.OmDeployFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmDeployRollbackHandler implements TransactionHandler {
    private final OmDeployFacade facade;

    public OmDeployRollbackHandler(OmDeployFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Deploy.rollbackRequest";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.rollbackRequest(request.getBody(), context);
    }
}
