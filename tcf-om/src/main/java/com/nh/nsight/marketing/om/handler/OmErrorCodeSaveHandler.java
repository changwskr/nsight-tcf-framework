package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.facade.OmErrorCodeFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmErrorCodeSaveHandler implements TransactionHandler {
    private final OmErrorCodeFacade facade;

    public OmErrorCodeSaveHandler(OmErrorCodeFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.ErrorCode.save";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.save(request.getBody(), context);
    }
}

