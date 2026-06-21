package com.nh.nsight.tcf.core.transaction;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.support.TcfConsoleLog;
import java.util.Map;

public interface TransactionHandler {
    String serviceId();

    default Object handle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        String id = serviceId();
        TcfConsoleLog.boundary("Handler", id, "START");
        try {
            Object result = doHandle(request, context);
            TcfConsoleLog.boundary("Handler", id, "END");
            return result;
        } catch (RuntimeException e) {
            TcfConsoleLog.boundary("Handler", id, "END", "error");
            throw e;
        }
    }

    Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context);
}
