package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmBatchFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmBatchDeleteAllHandler implements TransactionHandler {
    private final OmBatchFacade facade;

    public OmBatchDeleteAllHandler(OmBatchFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Batch.deleteAll";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.deleteAllHistories(request.getBody(), context);
    }
}
