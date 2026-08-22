package nhnis.eos.co.a.application.facade;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.application.service.eoscoa0170Service;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0170Facade {
    private final eoscoa0170Service service;
    public eoscoa0170Facade(eoscoa0170Service service) { this.service = service; }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0170S0(Object b) { return service.eoscoa0170S0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0170C0(Object b) { return service.eoscoa0170C0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0180S0(Object b) { return service.eoscoa0180S0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0180U0(Object b) { return service.eoscoa0180U0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0180U1(Object b) { return service.eoscoa0180U1(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0190S0(Object b) { return service.eoscoa0190S0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0190C0(Object b) { return service.eoscoa0190C0(EosResults.asMap(b)); }
}
