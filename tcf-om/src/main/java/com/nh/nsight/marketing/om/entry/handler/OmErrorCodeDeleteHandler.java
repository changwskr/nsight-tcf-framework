package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.entry.facade.OmErrorCodeFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmErrorCodeDeleteHandler implements TransactionHandler {
    private final OmErrorCodeFacade facade;

    public OmErrorCodeDeleteHandler(OmErrorCodeFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.ErrorCode.delete";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.delete(request.getBody(), context);
    }
}
