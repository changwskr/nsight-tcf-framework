package nhnis.fw.tcf.execution;

import org.springframework.util.StringUtils;

import nhnis.fw.commons.context.ServiceContext;
import nhnis.fw.tcf.core.context.TransactionContext;

/**
 * Runtime Evidence 레지스트리 키 (GUID 또는 요청 Thread 기준 fallback).
 */
public final class ExecutionEvidenceKey {

    /** {@link ServiceContext#getUserContext()} 에 Worker 로 전파하는 키. */
    public static final String USER_CONTEXT_KEY = "pdmg.evidenceKey";

    private ExecutionEvidenceKey() {
    }

    /**
     * Evidence 키를 계산해 {@link TransactionContext} 및 {@link ServiceContext} 에 고정한다.
     *
     * <p>요청 Thread 의 {@code begin()} 시점에 한 번만 호출해야 fallback 키가 Worker 와 일치한다.
     */
    public static String assign(TransactionContext context) {
        if (context == null || !StringUtils.hasText(context.getServiceId())) {
            return null;
        }
        String key = resolveGuidOrFallback(context);
        context.bindEvidenceKey(key);
        propagateToServiceContext(context, key);
        return key;
    }

    public static String keyOf(TransactionContext context) {
        if (context == null) {
            return null;
        }
        String bound = context.getEvidenceKey();
        if (StringUtils.hasText(bound)) {
            return bound.trim();
        }
        return resolveGuidOrFallback(context);
    }

    public static String fromServiceContext(ServiceContext serviceContext) {
        if (serviceContext == null || serviceContext.getUserContext() == null) {
            return null;
        }
        Object value = serviceContext.getUserContext().get(USER_CONTEXT_KEY);
        if (value == null) {
            return null;
        }
        String key = String.valueOf(value).trim();
        return key.isEmpty() ? null : key;
    }

    private static String resolveGuidOrFallback(TransactionContext context) {
        String guid = context.getGuid();
        if (StringUtils.hasText(guid)) {
            return guid.trim();
        }
        return fallbackKey(context);
    }

    private static String fallbackKey(TransactionContext context) {
        return context.getServiceId().trim() + "@" + Thread.currentThread().threadId() + "@"
                + System.identityHashCode(context);
    }

    private static void propagateToServiceContext(TransactionContext context, String key) {
        ServiceContext serviceContext = context.getServiceContext();
        if (serviceContext != null && key != null) {
            serviceContext.getUserContext().put(USER_CONTEXT_KEY, key);
        }
    }
}
