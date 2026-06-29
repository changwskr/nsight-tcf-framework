package com.nh.nsight.marketing.ep.entry.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.ep.entry.facade.EpUserEventFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class EpUserEventReceiveHandler implements TransactionHandler {
    private final EpUserEventFacade facade;

    public EpUserEventReceiveHandler(EpUserEventFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "EP.UserEvent.receive";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.receive(request.getBody(), context);
    }
}
