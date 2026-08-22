package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0141Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0141Handler implements TransactionHandler {
    private final eoscoa0141Facade facade;
    public eoscoa0141Handler(eoscoa0141Facade facade) { this.facade = facade; }
    @Override
    public Collection<String> serviceIds() {
        return List.of("eoscoa0141S0", "eoscoa0141C0", "eoscoa0141U0", "eoscoa0151S0");
    }
    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        return switch (context.getServiceId()) {
            case "eoscoa0141S0" -> facade.eoscoa0141S0(dtoBody);
            case "eoscoa0141C0" -> facade.eoscoa0141C0(dtoBody);
            case "eoscoa0141U0" -> facade.eoscoa0141U0(dtoBody);
            case "eoscoa0151S0" -> facade.eoscoa0151S0(dtoBody);
            default -> throw new ServiceHandlerNotFound(context.getServiceId());
        };
    }
}
