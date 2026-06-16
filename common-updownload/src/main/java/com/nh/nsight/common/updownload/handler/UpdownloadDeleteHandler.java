package com.nh.nsight.common.updownload.handler;

import com.nh.nsight.common.updownload.facade.UpdownloadFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UpdownloadDeleteHandler implements TransactionHandler {
    private final UpdownloadFacade facade;

    public UpdownloadDeleteHandler(UpdownloadFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "UD.File.delete";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.delete(request.getBody(), context);
    }
}
