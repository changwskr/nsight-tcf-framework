package nhnis.fw.tcf.core.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.fw.commons.runtime.MgActiveTransactionRegistry;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.dispatch.TransactionDispatcher;
import nhnis.fw.tcf.etf.etf;
import nhnis.fw.tcf.stf.stf;
import nhnis.fw.tcf.timeout.OnlineTimeoutExecutor;

/**
 * TCF Core Facade.
 *
 * <p>tcf-core 의 {@code TCF.process} 역할을 PDMG 에 맞게 단순화한다.
 * 시스템 선후처리(Filter / ServicePreventionInterceptor / ResponseBodyAdvice)는
 * 그대로 유지하고, 여기서는 STF → Handler → ETF 연결을 담당한다.
 *
 * <p>{@link OnlineTimeoutExecutor} 가 활성이면 Dispatcher 이하를 Worker + TX 로 감싼다.
 *
 * <pre>
 * Filter → 시스템선처리 → Controller → TcfFacade
 *   → stf.preProcess(거래통제) → OnlineTimeoutExecutor
 *   → Handler → 업무Facade → …
 *   → etf.postProcess(성공/예외 무관 finally, 타임아웃 인터벌 점검)
 * </pre>
 */
@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class TcfFacade {

    private static final Logger log = LoggerFactory.getLogger(TcfFacade.class);

    private final TransactionDispatcher dispatcher;
    private final OnlineTimeoutExecutor onlineTimeoutExecutor;
    private final stf stf;
    private final etf etf;
    private final MgActiveTransactionRegistry activeTransactionRegistry;

    public TcfFacade(
            TransactionDispatcher dispatcher,
            OnlineTimeoutExecutor onlineTimeoutExecutor,
            stf stf,
            etf etf,
            MgActiveTransactionRegistry activeTransactionRegistry) {
        log.info("========================= [TcfFacade.<init>] 시작");
        try {
            this.dispatcher = dispatcher;
            this.onlineTimeoutExecutor = onlineTimeoutExecutor;
            this.stf = stf;
            this.etf = etf;
            this.activeTransactionRegistry = activeTransactionRegistry;
        } finally {
            log.info("========================= [TcfFacade.<init>] 종료");
        }
    }

    /**
     * @param serviceId 거래 식별자 (예: mgcoa5530S0)
     * @param dtoBody   요청 전문 {@code dto} 노드
     * @return 업무 응답 DTO
     */
    public Object process(String serviceId, Object dtoBody) throws Exception {
        log.info("========================= [TcfFacade.process] 시작");
        TransactionContext context = TransactionContext.fromCurrent(serviceId);
        Exception primary = null;
        activeTransactionRegistry.begin(context);
        try {
            log.debug("[TcfFacade] process start serviceId={}", serviceId);
            stf.preProcess(context);
            Object result = onlineTimeoutExecutor.execute(
                    () -> dispatcher.dispatch(serviceId, dtoBody, context));
            log.debug("[TcfFacade] process end serviceId={} elapsedMs={}",
                    serviceId, context.elapsedMsSinceStart());
            return result;
        } catch (Exception e) {
            primary = e;
            throw e;
        } finally {
            activeTransactionRegistry.end(context);
            // ETF는 성공/실패와 무관하게 항상 수행 (종료 후처리)
            try {
                etf.postProcess(context);
            } catch (RuntimeException etfEx) {
                if (primary != null) {
                    primary.addSuppressed(etfEx);
                } else {
                    throw etfEx;
                }
            } finally {
                log.info("========================= [TcfFacade.process] 종료");
            }
        }
    }
}
