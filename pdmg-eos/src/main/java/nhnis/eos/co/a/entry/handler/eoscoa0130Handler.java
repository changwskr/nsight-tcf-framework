package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0130Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0130Handler implements TransactionHandler {

    private static final String S0 = "eoscoa0130S0";
    private static final String C0 = "eoscoa0130C0";
    private static final String U0 = "eoscoa0130U0";
    private static final String D0 = "eoscoa0130D0";

    private final eoscoa0130Facade facade;

    public eoscoa0130Handler(eoscoa0130Facade facade) {
        this.facade = facade;
    }

    @Override
    public Collection<String> serviceIds() {
        return List.of(S0, C0, U0, D0);
    }

    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        return switch (context.getServiceId()) {
            case S0 -> facade.eoscoa0130S0(dtoBody);
            case C0 -> facade.eoscoa0130C0(dtoBody);
            case U0 -> facade.eoscoa0130U0(dtoBody);
            case D0 -> facade.eoscoa0130D0(dtoBody);
            default -> throw new ServiceHandlerNotFound("eoscoa0130Handler unsupported: " + context.getServiceId());
        };
    }
}
