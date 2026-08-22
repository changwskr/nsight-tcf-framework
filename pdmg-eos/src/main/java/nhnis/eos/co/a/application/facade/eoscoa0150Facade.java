package nhnis.eos.co.a.application.facade;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.application.service.eoscoa0150Service;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0150Facade {
    private final eoscoa0150Service service;
    public eoscoa0150Facade(eoscoa0150Service service) { this.service = service; }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0150S0(Object body) { return service.eoscoa0150S0(EosResults.asMap(body)); }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0150C0(Object body) { return service.eoscoa0150C0(EosResults.asMap(body)); }
}
