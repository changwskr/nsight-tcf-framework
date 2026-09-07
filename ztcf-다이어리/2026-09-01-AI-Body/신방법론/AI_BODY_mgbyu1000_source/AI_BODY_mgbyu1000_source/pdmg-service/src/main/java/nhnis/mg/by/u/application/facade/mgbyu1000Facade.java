package nhnis.mg.by.u.application.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import nhnis.mg.by.u.application.service.mgbyu1000Service;
import nhnis.mg.by.u.dto.mgbyu1000C0DTOin;
import nhnis.mg.by.u.dto.mgbyu1000C0DTOout;
import nhnis.mg.by.u.dto.mgbyu1000S0DTOin;
import nhnis.mg.by.u.dto.mgbyu1000S0DTOout;
import nhnis.mg.by.u.dto.mgbyu1000U0DTOin;
import nhnis.mg.by.u.dto.mgbyu1000U0DTOout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * mgbyu1000 Profile DTO 변환 및 Transaction 경계.
 */
@Service
public class mgbyu1000Facade {

    private final ObjectMapper objectMapper;
    private final mgbyu1000Service service;

    public mgbyu1000Facade(ObjectMapper objectMapper, mgbyu1000Service service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @Transactional(
            transactionManager = "rdwTransactionManager",
            readOnly = true
    )
    public mgbyu1000S0DTOout mgbyu1000S0(Object dtoBody) throws Exception {
        mgbyu1000S0DTOin input =
                objectMapper.convertValue(source(dtoBody), mgbyu1000S0DTOin.class);
        return service.mgbyu1000S0(input);
    }

    @Transactional(
            transactionManager = "rdwTransactionManager",
            rollbackFor = Exception.class
    )
    public mgbyu1000C0DTOout mgbyu1000C0(Object dtoBody) throws Exception {
        mgbyu1000C0DTOin input =
                objectMapper.convertValue(source(dtoBody), mgbyu1000C0DTOin.class);
        return service.mgbyu1000C0(input);
    }

    @Transactional(
            transactionManager = "rdwTransactionManager",
            rollbackFor = Exception.class
    )
    public mgbyu1000U0DTOout mgbyu1000U0(Object dtoBody) throws Exception {
        mgbyu1000U0DTOin input =
                objectMapper.convertValue(source(dtoBody), mgbyu1000U0DTOin.class);
        return service.mgbyu1000U0(input);
    }

    private Object source(Object dtoBody) {
        return dtoBody == null ? new HashMap<String, Object>() : dtoBody;
    }
}
