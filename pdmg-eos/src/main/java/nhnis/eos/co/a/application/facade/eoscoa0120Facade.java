package nhnis.eos.co.a.application.facade;

import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import nhnis.eos.co.a.application.service.eoscoa0120Service;
import nhnis.eos.co.a.dto.eoscoa0120S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0120S0DTOout;

@Service
public class eoscoa0120Facade {

    private final eoscoa0120Service service;
    private final ObjectMapper objectMapper;

    public eoscoa0120Facade(eoscoa0120Service service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public eoscoa0120S0DTOout eoscoa0120S0(Object dtoBody) {
        Object source = dtoBody == null ? Collections.emptyMap() : dtoBody;
        eoscoa0120S0DTOin input = objectMapper.convertValue(source, eoscoa0120S0DTOin.class);
        return service.eoscoa0120S0(input);
    }
}
