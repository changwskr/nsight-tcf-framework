package nhnis.eos.co.a.application.facade;

import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import nhnis.eos.co.a.application.service.eoscoa0130Service;
import nhnis.eos.co.a.dto.eoscoa0130C0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130C0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130D0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130D0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130S0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130U0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130U0DTOout;

@Service
public class eoscoa0130Facade {

    private final eoscoa0130Service service;
    private final ObjectMapper objectMapper;

    public eoscoa0130Facade(eoscoa0130Service service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public eoscoa0130S0DTOout eoscoa0130S0(Object dtoBody) {
        return service.eoscoa0130S0(convert(dtoBody, eoscoa0130S0DTOin.class));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0130C0DTOout eoscoa0130C0(Object dtoBody) {
        return service.eoscoa0130C0(convert(dtoBody, eoscoa0130C0DTOin.class));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0130U0DTOout eoscoa0130U0(Object dtoBody) {
        return service.eoscoa0130U0(convert(dtoBody, eoscoa0130U0DTOin.class));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0130D0DTOout eoscoa0130D0(Object dtoBody) {
        return service.eoscoa0130D0(convert(dtoBody, eoscoa0130D0DTOin.class));
    }

    private <T> T convert(Object dtoBody, Class<T> type) {
        Object source = dtoBody == null ? Collections.emptyMap() : dtoBody;
        return objectMapper.convertValue(source, type);
    }
}
