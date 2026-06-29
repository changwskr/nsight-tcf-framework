package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.marketing.om.entry.facade.OmServiceCatalogFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmServiceCatalogSaveHandler implements TransactionHandler {
    private final OmServiceCatalogFacade facade;

    public OmServiceCatalogSaveHandler(OmServiceCatalogFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.ServiceCatalog.save";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.save(request.getBody(), context);
    }
}
