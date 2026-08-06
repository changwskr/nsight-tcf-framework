package nhnis.mk.co.common;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * PDMK(mpco) 업무 공통 선/후처리 Aspect.
 *
 * <p>운영 로그:
 * <ul>
 *   <li>before → {@code ▶▶▶▶▶▶ mpco 업무 공통 선처리 ▶▶▶▶▶▶}</li>
 *   <li>Argument → {@code Argument: BRC : ...}</li>
 *   <li>after → {@code ◀◀◀◀◀◀ mpco 업무 공통 후처리 ◀◀◀◀◀◀}</li>
 * </ul>
 */
@Aspect
@Component
@Order(100)
public class BizPrePostAspect {

    private static final Logger log = LoggerFactory.getLogger(BizPrePostAspect.class);

    @Pointcut("execution(* nhnis.mk.co..controller..*(..))")
    public void mkCoControllers() {
        // pointcut
    }

    @Before("mkCoControllers()")
    public void before(JoinPoint joinPoint) {
        log.info("▶▶▶▶▶▶ mpco 업무 공통 선처리 ▶▶▶▶▶▶");
        logArguments(joinPoint.getArgs());
    }

    @After("mkCoControllers()")
    public void after(JoinPoint joinPoint) {
        log.info("◀◀◀◀◀◀ mpco 업무 공통 후처리 ◀◀◀◀◀◀");
        logArguments(joinPoint.getArgs());
    }

    private void logArguments(Object[] args) {
        if (args == null || args.length == 0) {
            log.info("Argument: BRC : null");
            return;
        }
        for (Object arg : args) {
            log.info("Argument: BRC : {}", extractBrc(arg));
        }
    }

    private Object extractBrc(Object arg) {
        if (arg == null) {
            return null;
        }
        for (String name : new String[] {"getTrtBrc", "getBrc", "getBRC"}) {
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
