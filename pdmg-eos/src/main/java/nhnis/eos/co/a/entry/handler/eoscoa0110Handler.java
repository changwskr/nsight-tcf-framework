package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0110Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0110Handler implements TransactionHandler {
    private final eoscoa0110Facade facade;
    public eoscoa0110Handler(eoscoa0110Facade facade) { this.facade = facade; }
    @Override public Collection<String> serviceIds() { return List.of("eoscoa0110S0"); }
    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        if ("eoscoa0110S0".equals(context.getServiceId())) return facade.eoscoa0110S0(dtoBody);
        throw new ServiceHandlerNotFound(context.getServiceId());
    }
}
