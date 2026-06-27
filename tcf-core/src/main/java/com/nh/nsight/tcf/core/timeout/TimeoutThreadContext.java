package com.nh.nsight.tcf.core.timeout;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.context.TransactionContextHolder;
import java.util.Map;
import java.util.function.Supplier;
import org.slf4j.MDC;

/** 온라인 timeout 워커 스레드로 Timeout·Transaction·MDC 컨텍스트를 전파한다. */
public final class TimeoutThreadContext {

    private TimeoutThreadContext() {}

    public static <T> T callWithPropagatedContext(Supplier<T> action) {
        TimeoutPolicy policy = TimeoutContextHolder.get();
        TransactionContext transactionContext = TransactionContextHolder.get();
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        try {
            if (policy != null) {
                TimeoutContextHolder.set(policy);
            }
            if (transactionContext != null) {
                TransactionContextHolder.set(transactionContext);
            }
            if (mdc != null) {
                MDC.setContextMap(mdc);
            }
            return action.get();
        } finally {
            TimeoutContextHolder.clear();
            TransactionContextHolder.clear();
            MDC.clear();
        }
    }
}
