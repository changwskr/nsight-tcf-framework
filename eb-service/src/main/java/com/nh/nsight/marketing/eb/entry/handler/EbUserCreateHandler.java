package com.nh.nsight.marketing.eb.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.eb.entry.facade.EbUserFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EbUserCreateHandler implements TransactionHandler {
    private final EbUserFacade facade;

    public EbUserCreateHandler(EbUserFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "EB.User.create";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.create(request.getBody(), context);
    }
}
