package nhnis.eos.co.a.application.facade;

import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import nhnis.eos.co.a.application.service.eoscoa0100Service;
import nhnis.eos.co.a.dto.eoscoa0100S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0100S0DTOout;

@Service
public class eoscoa0100Facade {

    private final eoscoa0100Service service;
    private final ObjectMapper objectMapper;

    public eoscoa0100Facade(eoscoa0100Service service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public eoscoa0100S0DTOout eoscoa0100S0(Object dtoBody) {
        Object source = dtoBody == null ? Collections.emptyMap() : dtoBody;
        eoscoa0100S0DTOin input = objectMapper.convertValue(source, eoscoa0100S0DTOin.class);
        return service.eoscoa0100S0(input);
    }
}
