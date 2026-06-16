package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.facade.OmBatchFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmBatchExecuteHandler implements TransactionHandler {
    private final OmBatchFacade facade;

    public OmBatchExecuteHandler(OmBatchFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Batch.execute";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.execute(request.getBody(), context);
    }
}

