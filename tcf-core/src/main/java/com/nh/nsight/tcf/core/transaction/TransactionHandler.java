package com.nh.nsight.tcf.core.transaction;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import java.util.Map;

public interface TransactionHandler {
    String serviceId();

    default Object handle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        System.out.println("\n ======================================================================[TransactionHandler.handle] start serviceId=" + serviceId());
        try {
            Object result = doHandle(request, context);
            System.out.println(" ======================================================================[TransactionHandler.handle] end serviceId=" + serviceId());
            return result;
        } catch (RuntimeException e) {
            System.out.println(" ======================================================================[TransactionHandler.handle] end (error) serviceId=" + serviceId());
            throw e;
        }
    }

    Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context);
}
