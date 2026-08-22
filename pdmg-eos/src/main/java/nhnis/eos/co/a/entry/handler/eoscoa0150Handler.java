package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0150Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0150Handler implements TransactionHandler {
    private final eoscoa0150Facade facade;
    public eoscoa0150Handler(eoscoa0150Facade facade) { this.facade = facade; }
    @Override public Collection<String> serviceIds() { return List.of("eoscoa0150S0", "eoscoa0150C0"); }
    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        return switch (context.getServiceId()) {
            case "eoscoa0150S0" -> facade.eoscoa0150S0(dtoBody);
            case "eoscoa0150C0" -> facade.eoscoa0150C0(dtoBody);
            default -> throw new ServiceHandlerNotFound(context.getServiceId());
        };
    }
}
