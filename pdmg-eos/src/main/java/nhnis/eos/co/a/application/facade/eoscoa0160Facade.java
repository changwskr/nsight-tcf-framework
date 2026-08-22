package nhnis.eos.co.a.application.facade;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.application.service.eoscoa0160Service;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0160Facade {
    private final eoscoa0160Service service;
    public eoscoa0160Facade(eoscoa0160Service service) { this.service = service; }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0160S0(Object b) { return service.eoscoa0160S0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0160C0(Object b) { return service.eoscoa0160C0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0160U0(Object b) { return service.eoscoa0160U0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0160U1(Object b) { return service.eoscoa0160U1(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0160U2(Object b) { return service.eoscoa0160U2(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0165U0(Object b) { return service.eoscoa0165U0(EosResults.asMap(b)); }
}
