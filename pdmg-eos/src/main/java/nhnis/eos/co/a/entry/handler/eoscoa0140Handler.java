package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0140Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0140Handler implements TransactionHandler {

    private static final String S0 = "eoscoa0140S0";
    private static final String U0 = "eoscoa0140U0";
    private static final String U1 = "eoscoa0140U1";

    private final eoscoa0140Facade facade;

    public eoscoa0140Handler(eoscoa0140Facade facade) {
        this.facade = facade;
    }

    @Override
    public Collection<String> serviceIds() {
        return List.of(S0, U0, U1);
    }

    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        return switch (context.getServiceId()) {
            case S0 -> facade.eoscoa0140S0(dtoBody);
            case U0 -> facade.eoscoa0140U0(dtoBody);
            case U1 -> facade.eoscoa0140U1(dtoBody);
            default -> throw new ServiceHandlerNotFound("eoscoa0140Handler unsupported: " + context.getServiceId());
        };
    }
}
