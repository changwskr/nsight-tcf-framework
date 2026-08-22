package nhnis.eos.co.a.entry.handler;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.eos.co.a.application.facade.eoscoa0100Facade;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;

/**
 * @deprecated use eoscoa0120S0 — legacy bridge to TB_EOS_RESOURCE
 */
@Deprecated
@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class eoscoa0100Handler implements TransactionHandler {

    private static final String S0 = "eoscoa0100S0";

    private final eoscoa0100Facade facade;

    public eoscoa0100Handler(eoscoa0100Facade facade) {
        this.facade = facade;
    }

    @Override
    public Collection<String> serviceIds() {
        return List.of(S0);
    }

    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        if (S0.equals(context.getServiceId())) {
            return facade.eoscoa0100S0(dtoBody);
        }
        throw new ServiceHandlerNotFound("eoscoa0100Handler unsupported serviceId: " + context.getServiceId());
    }
}
