package nhnis.infra.in.a.entry.handler;

import java.util.Collection;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import nhnis.fw.commons.exception.ServiceHandlerNotFound;
import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.core.handler.TransactionHandler;
import nhnis.infra.in.a.application.facade.ifina2400Facade;

@Component
@ConditionalOnProperty(name = "nhnis.fw.tcf.enabled", havingValue = "true")
public class ifina2400Handler implements TransactionHandler {
    private final ifina2400Facade facade;

    public ifina2400Handler(ifina2400Facade facade) {
        this.facade = facade;
    }

    @Override
    public Collection<String> serviceIds() {
        return List.of("ifina2400S0", "ifina2400C0", "ifina2400U0", "ifina2400D0");
    }

    @Override
    public Object handle(Object dtoBody, TransactionContext context) throws Exception {
        return switch (context.getServiceId()) {
            case "ifina2400S0" -> facade.ifina2400S0(dtoBody);
            case "ifina2400C0" -> facade.ifina2400C0(dtoBody);
            case "ifina2400U0" -> facade.ifina2400U0(dtoBody);
            case "ifina2400D0" -> facade.ifina2400D0(dtoBody);
            default -> throw new ServiceHandlerNotFound("ifina2400 미지원: " + context.getServiceId());
        };
    }
}
