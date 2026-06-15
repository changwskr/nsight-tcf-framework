package com.nh.nsight.tcf.core.dispatch;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TransactionDispatcher {
    private static final Logger log = LoggerFactory.getLogger(TransactionDispatcher.class);
    private final Map<String, TransactionHandler> handlerMap = new ConcurrentHashMap<>();

    public TransactionDispatcher(List<TransactionHandler> handlers) {
        System.out.println("\n ======================================================================[TransactionDispatcher.<init>] start");
        for (TransactionHandler handler : handlers) {
            TransactionHandler previous = handlerMap.put(handler.serviceId(), handler);
            if (previous != null) {
                throw new IllegalStateException("Duplicate serviceId detected: " + handler.serviceId());
            }
            System.out.println(" ======================================================================[TransactionDispatcher.<init>] register serviceId=" + handler.serviceId());
            log.info("Registered NSIGHT handler. serviceId={}", handler.serviceId());
        }
        System.out.println(" ======================================================================[TransactionDispatcher.<init>] end (count=" + handlerMap.size() + ")");
    }

    public Object dispatch(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        System.out.println("\n ======================================================================[TransactionDispatcher.dispatch] start");
        String serviceId = request.getHeader() == null ? null : request.getHeader().getServiceId();
        System.out.println(" ======================================================================[TransactionDispatcher.dispatch] resolve serviceId=" + serviceId);
        if (!StringUtils.hasText(serviceId)) {
            System.out.println(" ======================================================================[TransactionDispatcher.dispatch] end (invalid serviceId)");
            throw new BusinessException(ErrorCode.INVALID_HEADER, "serviceId가 없습니다.");
        }
        TransactionHandler handler = handlerMap.get(serviceId);
        if (handler == null) {
            System.out.println(" ======================================================================[TransactionDispatcher.dispatch] end (handler not found)");
            throw new BusinessException(ErrorCode.SERVICE_NOT_FOUND, "등록되지 않은 serviceId입니다: " + serviceId);
        }
        System.out.println(" ======================================================================[TransactionDispatcher.dispatch] handler.handle serviceId=" + serviceId);
        Object result = handler.handle(request, context);
        System.out.println(" ======================================================================[TransactionDispatcher.dispatch] end");
        return result;
    }

    public Map<String, TransactionHandler> handlers() {
        return Map.copyOf(handlerMap);
    }
}
