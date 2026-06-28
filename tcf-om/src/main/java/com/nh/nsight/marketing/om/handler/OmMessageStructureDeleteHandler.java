package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmMessageStructureFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmMessageStructureDeleteHandler implements TransactionHandler {
    private final OmMessageStructureFacade facade;

    public OmMessageStructureDeleteHandler(OmMessageStructureFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.MessageStructure.delete";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.delete(request.getBody(), context);
    }
}
