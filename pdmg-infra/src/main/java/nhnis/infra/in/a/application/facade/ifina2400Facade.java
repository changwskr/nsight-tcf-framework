package nhnis.infra.in.a.application.facade;

import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import nhnis.infra.in.a.application.service.ifina2400Service;
import nhnis.infra.in.a.dto.*;

@Service
public class ifina2400Facade {
    private final ifina2400Service service;
    private final ObjectMapper objectMapper;

    public ifina2400Facade(ifina2400Service service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public ifina2400S0DTOout ifina2400S0(Object dtoBody) throws Exception {
        return service.ifina2400S0(convert(dtoBody, ifina2400S0DTOin.class));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public ifina2400C0DTOout ifina2400C0(Object dtoBody) throws Exception {
        return service.ifina2400C0(convert(dtoBody, ifina2400C0DTOin.class));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public ifina2400U0DTOout ifina2400U0(Object dtoBody) throws Exception {
        return service.ifina2400U0(convert(dtoBody, ifina2400U0DTOin.class));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public ifina2400D0DTOout ifina2400D0(Object dtoBody) throws Exception {
        return service.ifina2400D0(convert(dtoBody, ifina2400D0DTOin.class));
    }

    private <T> T convert(Object dtoBody, Class<T> type) {
        return objectMapper.convertValue(dtoBody == null ? Collections.emptyMap() : dtoBody, type);
    }
}
