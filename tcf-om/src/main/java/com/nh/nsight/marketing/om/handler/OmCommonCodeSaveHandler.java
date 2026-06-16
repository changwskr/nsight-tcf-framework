package com.nh.nsight.marketing.om.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.marketing.om.facade.OmCommonCodeFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OmCommonCodeSaveHandler implements TransactionHandler {
    private final OmCommonCodeFacade facade;

    public OmCommonCodeSaveHandler(OmCommonCodeFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "OM.CommonCode.save";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.save(request.getBody(), context);
    }
}

