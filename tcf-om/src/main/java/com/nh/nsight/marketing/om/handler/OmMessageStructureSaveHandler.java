package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmMessageStructureFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmMessageStructureSaveHandler implements TransactionHandler {
    private final OmMessageStructureFacade facade;

    public OmMessageStructureSaveHandler(OmMessageStructureFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.MessageStructure.save";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.save(request.getBody(), context);
    }
}
