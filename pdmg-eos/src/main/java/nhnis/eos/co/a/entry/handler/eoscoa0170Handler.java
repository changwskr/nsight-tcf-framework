package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0170Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0170Handler implements TransactionHandler {
    private final eoscoa0170Facade facade;
    public eoscoa0170Handler(eoscoa0170Facade facade) { this.facade = facade; }
    @Override
    public Collection<String> serviceIds() {
        return List.of("eoscoa0170S0", "eoscoa0170C0", "eoscoa0180S0", "eoscoa0180U0", "eoscoa0180U1",
                "eoscoa0190S0", "eoscoa0190C0");
    }
    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        return switch (context.getServiceId()) {
            case "eoscoa0170S0" -> facade.eoscoa0170S0(dtoBody);
            case "eoscoa0170C0" -> facade.eoscoa0170C0(dtoBody);
            case "eoscoa0180S0" -> facade.eoscoa0180S0(dtoBody);
            case "eoscoa0180U0" -> facade.eoscoa0180U0(dtoBody);
            case "eoscoa0180U1" -> facade.eoscoa0180U1(dtoBody);
            case "eoscoa0190S0" -> facade.eoscoa0190S0(dtoBody);
            case "eoscoa0190C0" -> facade.eoscoa0190C0(dtoBody);
            default -> throw new ServiceHandlerNotFound(context.getServiceId());
        };
    }
}
