package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0120Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0120Handler implements TransactionHandler {

    private static final String S0 = "eoscoa0120S0";

    private final eoscoa0120Facade facade;

    public eoscoa0120Handler(eoscoa0120Facade facade) {
        this.facade = facade;
    }

    @Override
    public Collection<String> serviceIds() {
        return List.of(S0);
    }

    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        if (S0.equals(context.getServiceId())) {
            return facade.eoscoa0120S0(dtoBody);
        }
        throw new ServiceHandlerNotFound("eoscoa0120Handler unsupported serviceId: " + context.getServiceId());
    }
}
