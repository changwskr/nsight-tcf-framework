package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.om.facade.OmDeployFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmDeployHealthCheckHandler implements TransactionHandler {
    private final OmDeployFacade facade;

    public OmDeployHealthCheckHandler(OmDeployFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Deploy.healthCheck";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.healthCheck(request.getBody(), context);
    }
}
