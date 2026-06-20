package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.marketing.om.facade.OmUserFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmUserSaveHandler implements TransactionHandler {
    private final OmUserFacade facade;

    public OmUserSaveHandler(OmUserFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.User.save";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.save(request.getBody(), context);
    }
}
