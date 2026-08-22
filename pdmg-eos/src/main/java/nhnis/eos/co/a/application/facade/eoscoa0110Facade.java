package nhnis.eos.co.a.application.facade;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.application.service.eoscoa0110Service;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0110Facade {
    private final eoscoa0110Service service;
    public eoscoa0110Facade(eoscoa0110Service service) { this.service = service; }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0110S0(Object dtoBody) {
        return service.eoscoa0110S0(EosResults.asMap(dtoBody));
    }
}
