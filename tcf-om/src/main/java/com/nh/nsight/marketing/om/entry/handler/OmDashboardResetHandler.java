package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.marketing.om.entry.facade.OmDashboardFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmDashboardResetHandler implements TransactionHandler {
    private final OmDashboardFacade facade;

    public OmDashboardResetHandler(OmDashboardFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Dashboard.reset";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.reset(request.getBody(), context);
    }
}
