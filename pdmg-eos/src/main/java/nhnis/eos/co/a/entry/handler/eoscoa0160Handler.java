package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0160Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0160Handler implements TransactionHandler {
    private final eoscoa0160Facade facade;
    public eoscoa0160Handler(eoscoa0160Facade facade) { this.facade = facade; }
    @Override
    public Collection<String> serviceIds() {
        return List.of("eoscoa0160S0", "eoscoa0160C0", "eoscoa0160U0", "eoscoa0160U1", "eoscoa0160U2", "eoscoa0165U0");
    }
    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        return switch (context.getServiceId()) {
            case "eoscoa0160S0" -> facade.eoscoa0160S0(dtoBody);
            case "eoscoa0160C0" -> facade.eoscoa0160C0(dtoBody);
            case "eoscoa0160U0" -> facade.eoscoa0160U0(dtoBody);
            case "eoscoa0160U1" -> facade.eoscoa0160U1(dtoBody);
            case "eoscoa0160U2" -> facade.eoscoa0160U2(dtoBody);
            case "eoscoa0165U0" -> facade.eoscoa0165U0(dtoBody);
            default -> throw new ServiceHandlerNotFound(context.getServiceId());
        };
    }
}
