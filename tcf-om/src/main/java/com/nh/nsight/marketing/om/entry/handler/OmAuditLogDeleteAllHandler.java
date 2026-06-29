package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.marketing.om.entry.facade.OmAuditLogFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmAuditLogDeleteAllHandler implements TransactionHandler {
    private final OmAuditLogFacade facade;

    public OmAuditLogDeleteAllHandler(OmAuditLogFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.AuditLog.deleteAll";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.deleteAll(request.getBody(), context);
    }
}
