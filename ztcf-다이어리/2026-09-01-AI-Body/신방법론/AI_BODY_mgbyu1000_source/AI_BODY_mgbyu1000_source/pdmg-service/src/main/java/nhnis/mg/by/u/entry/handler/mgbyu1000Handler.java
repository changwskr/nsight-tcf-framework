package nhnis.mg.by.u.entry.handler;

import nhnis.fw.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;
import nhnis.mg.by.u.application.facade.mgbyu1000Facade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * BY 사용자 Profile Program TCF Handler.
 *
 * <p>지원 Service ID: mgbyu1000S0, mgbyu1000C0, mgbyu1000U0.</p>
 */
@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class mgbyu1000Handler implements TransactionHandler {

    private static final String S0 = "mgbyu1000S0";
    private static final String C0 = "mgbyu1000C0";
    private static final String U0 = "mgbyu1000U0";

    private final mgbyu1000Facade facade;

    public mgbyu1000Handler(mgbyu1000Facade facade) {
        this.facade = facade;
    }

    @Override
    public Collection<String> serviceIds() {
        return List.of(S0, C0, U0);
    }

    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        return switch (context.getServiceId()) {
            case S0 -> facade.mgbyu1000S0(dtoBody);
            case C0 -> facade.mgbyu1000C0(dtoBody);
            case U0 -> facade.mgbyu1000U0(dtoBody);
            default -> throw new ServiceHandlerNotFound(
                    "mgbyu1000Handler 미지원 serviceId: " + context.getServiceId()
            );
        };
    }
}
