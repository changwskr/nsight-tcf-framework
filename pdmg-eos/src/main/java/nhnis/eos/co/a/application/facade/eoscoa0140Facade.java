package nhnis.eos.co.a.application.facade;

import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import nhnis.eos.co.a.application.service.eoscoa0140Service;
import nhnis.eos.co.a.dto.eoscoa0140S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0140S0DTOout;
import nhnis.eos.co.a.dto.eoscoa0140U0DTOin;
import nhnis.eos.co.a.dto.eoscoa0140U0DTOout;

@Service
public class eoscoa0140Facade {

    private final eoscoa0140Service service;
    private final ObjectMapper objectMapper;

    public eoscoa0140Facade(eoscoa0140Service service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public eoscoa0140S0DTOout eoscoa0140S0(Object dtoBody) {
        return service.eoscoa0140S0(convert(dtoBody, eoscoa0140S0DTOin.class));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0140U0DTOout eoscoa0140U0(Object dtoBody) {
        return service.eoscoa0140U0(convert(dtoBody, eoscoa0140U0DTOin.class));
    }

    /** U1 = U0 with recalcInstanceYn=Y (default) */
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0140U0DTOout eoscoa0140U1(Object dtoBody) {
        eoscoa0140U0DTOin in = convert(dtoBody, eoscoa0140U0DTOin.class);
        if (in.getRecalcInstanceYn() == null || in.getRecalcInstanceYn().isBlank()) {
            in.setRecalcInstanceYn("Y");
        }
        return service.eoscoa0140U0(in);
    }

    private <T> T convert(Object dtoBody, Class<T> type) {
        return objectMapper.convertValue(dtoBody == null ? Collections.emptyMap() : dtoBody, type);
    }
}
