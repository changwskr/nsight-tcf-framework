package com.nh.nsight.marketing.om.entry.handler;

import com.nh.nsight.marketing.om.entry.facade.OmUserFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmUserDetailHandler implements TransactionHandler {
    private final OmUserFacade facade;

    public OmUserDetailHandler(OmUserFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.User.detail";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.detail(request.getBody(), context);
    }
}
