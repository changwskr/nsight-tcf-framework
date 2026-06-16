package com.nh.nsight.common.updownload.handler;

import com.nh.nsight.common.updownload.facade.UpdownloadFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UpdownloadListHandler implements TransactionHandler {
    private final UpdownloadFacade facade;

    public UpdownloadListHandler(UpdownloadFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "UD.File.list";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.list(request.getBody(), context);
    }
}
