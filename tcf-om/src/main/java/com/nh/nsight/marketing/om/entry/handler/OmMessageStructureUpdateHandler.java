package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.marketing.om.entry.facade.OmMessageStructureFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmMessageStructureUpdateHandler implements TransactionHandler {
    private final OmMessageStructureFacade facade;

    public OmMessageStructureUpdateHandler(OmMessageStructureFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.MessageStructure.update";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.update(request.getBody(), context);
    }
}
