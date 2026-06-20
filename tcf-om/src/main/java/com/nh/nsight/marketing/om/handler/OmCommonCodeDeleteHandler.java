package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.facade.OmCommonCodeFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmCommonCodeDeleteHandler implements TransactionHandler {
    private final OmCommonCodeFacade facade;

    public OmCommonCodeDeleteHandler(OmCommonCodeFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.CommonCode.delete";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.delete(request.getBody(), context);
    }
}
