package nhnis.eos.co.a.entry.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import nhnis.fw.commons.context.ServiceContext;
import nhnis.fw.commons.context.ServiceContextHolder;
import nhnis.fw.commons.log.PdmgMessagePrinter;
import nhnis.fw.commons.log.PdmgTxLog;

@Aspect
@Component
@Order(100)
@ConditionalOnProperty(name = "nhnis.fw.commons.legacy-web.enabled", havingValue = "true", matchIfMissing = true)
public class BizPrePostAspect {

    private static final Logger log = LoggerFactory.getLogger(BizPrePostAspect.class);

    @Pointcut("execution(public * nhnis.eos.co.a.application.service..*(..))")
    public void eosBizBoundary() {
        // pointcut
    }

    @Before("eosBizBoundary()")
    public void before(JoinPoint joinPoint) {
        log.info(PdmgTxLog.bizPreStart());
        logBizPreProgress(joinPoint);
        log.info(PdmgTxLog.bizPreEnd());
        log.info(PdmgTxLog.bizProcessStart());
    }

    @AfterReturning(pointcut = "eosBizBoundary()", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        log.info(PdmgTxLog.bizProcessEnd());
        log.info(PdmgTxLog.bizPostStart());
        if (log.isInfoEnabled()) {
            log.info(PdmgTxLog.bizResponseMessage());
            log.info(PdmgMessagePrinter.businessDto(result));
        }
        log.info(PdmgTxLog.bizPostEnd());
    }

    private void logBizPreProgress(JoinPoint joinPoint) {
        ServiceContext ctx = ServiceContextHolder.getInstance();
        String guid = ctx != null ? ctx.getGuid() : null;
        String serviceId = null;
        if (ctx != null && ctx.getHeader() != null && ctx.getHeader().getSys_comm() != null) {
            serviceId = ctx.getHeader().getSys_comm().getRms_svc_c();
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String method = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        log.info(PdmgTxLog.bizPreProgress("GUID: " + guid));
        log.info(PdmgTxLog.bizPreProgress("서비스: " + serviceId));
        log.info(PdmgTxLog.bizPreProgress("호출: " + method));
        log.info(PdmgTxLog.bizPreProgress("BRC: " + extractBrc(joinPoint.getArgs())));
    }

    private Object extractBrc(Object[] args) {
        if (args == null) {
            return null;
        }
        for (Object arg : args) {
            if (arg == null) {
                continue;
            }
            for (String name : new String[] {"getTrtBrc", "getBrc", "getBRC"}) {
                try {
                    Method method = arg.getClass().getMethod(name);
                    return method.invoke(arg);
                } catch (ReflectiveOperationException ignored) {
                    // try next
                }
            }
        }
        return null;
    }
}
