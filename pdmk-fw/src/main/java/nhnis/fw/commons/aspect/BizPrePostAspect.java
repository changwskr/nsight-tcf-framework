package nhnis.fw.commons.aspect;

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
 * PDMK 업무 공통 선/후처리 Aspect.
 *
 * <p>운영 로그 카테고리는 {@code nhnis.mk.co.common.BizPrePostAspect} 로 남겨
 * BIZ 어펜더·운영 로그와 동일하게 보이게 한다.
 */
@Aspect
@Component
@Order(100)
@ConditionalOnProperty(name = "nhnis.fw.commons.legacy-web.enabled", havingValue = "true", matchIfMissing = true)
public class BizPrePostAspect {

    /** 운영 로그 클래스명과 동일한 로거 카테고리 */
    private static final Logger log = LoggerFactory.getLogger("nhnis.mk.co.common.BizPrePostAspect");

    @Pointcut("execution(* nhnis..co..controller..*(..))")
    public void coControllers() {
        // pointcut
    }

    @Before("coControllers()")
    public void before(JoinPoint joinPoint) {
        PdmkTxLog.bizPreProcess(log);
        logArgumentsBefore(joinPoint.getArgs());
    }

    @After("coControllers()")
    public void after(JoinPoint joinPoint) {
        PdmkTxLog.bizPostProcess(log);
        logArgumentsAfter(joinPoint.getArgs());
    }

    private void logArgumentsBefore(Object[] args) {
        if (args == null || args.length == 0) {
            PdmkTxLog.bizArgumentBefore(log, null);
            return;
        }
        for (Object arg : args) {
            PdmkTxLog.bizArgumentBefore(log, extractBrc(arg));
        }
    }

    private void logArgumentsAfter(Object[] args) {
        if (args == null || args.length == 0) {
            PdmkTxLog.bizArgumentAfter(log, null);
            return;
        }
        for (Object arg : args) {
            PdmkTxLog.bizArgumentAfter(log, extractBrc(arg));
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
