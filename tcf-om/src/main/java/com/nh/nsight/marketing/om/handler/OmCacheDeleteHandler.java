package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.facade.OmCacheFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmCacheDeleteHandler implements TransactionHandler {
    private final OmCacheFacade facade;

    public OmCacheDeleteHandler(OmCacheFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.Cache.delete";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.delete(request.getBody(), context);
    }
}

