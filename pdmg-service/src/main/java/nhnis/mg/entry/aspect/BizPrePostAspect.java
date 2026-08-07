package nhnis.mg.entry.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import nhnis.fw.commons.log.PdmkTxLog;

/**
 * PDMG 업무 공통 선/후처리 Aspect.
 *
 * <p>패키지·클래스명을 운영 로그와 동일하게 {@code nhnis.mg.entry.aspect.BizPrePostAspect} 로 둔다.
 * {@code log.info} 는 이 클래스에서 직접 호출해 {@code %C.%M} 위치가 맞도록 한다.
 */
@Aspect
@Component
@Order(100)
@ConditionalOnProperty(name = "nhnis.fw.commons.legacy-web.enabled", havingValue = "true", matchIfMissing = true)
public class BizPrePostAspect {

    private static final Logger log = LoggerFactory.getLogger(BizPrePostAspect.class);

    @Pointcut("execution(* nhnis.mg.entry.controller..*(..))")
    public void mgCoControllers() {
        // pointcut
    }

    @Before("mgCoControllers()")
    public void before(JoinPoint joinPoint) {
        log.info(PdmkTxLog.bizPreProcess());
        logArgumentsBefore(joinPoint.getArgs());
    }

    @After("mgCoControllers()")
    public void after(JoinPoint joinPoint) {
        log.info(PdmkTxLog.bizPostProcess());
        logArgumentsAfter(joinPoint.getArgs());
    }

    private void logArgumentsBefore(Object[] args) {
        if (args == null || args.length == 0) {
            log.info(PdmkTxLog.bizArgumentBefore(null));
            return;
        }
        for (Object arg : args) {
            log.info(PdmkTxLog.bizArgumentBefore(extractBrc(arg)));
        }
    }

    private void logArgumentsAfter(Object[] args) {
        if (args == null || args.length == 0) {
            log.info(PdmkTxLog.bizArgumentAfter(null));
            return;
        }
        for (Object arg : args) {
            log.info(PdmkTxLog.bizArgumentAfter(extractBrc(arg)));
        }
    }

    private Object extractBrc(Object arg) {
        if (arg == null) {
            return null;
        }
        for (String name : new String[] {"getTrtBrc", "getBrc", "getBRC", "getL5104"}) {
            try {
                Method method = arg.getClass().getMethod(name);
                return method.invoke(arg);
            } catch (ReflectiveOperationException ignored) {
                // try next
            }
        }
        return null;
    }
}
