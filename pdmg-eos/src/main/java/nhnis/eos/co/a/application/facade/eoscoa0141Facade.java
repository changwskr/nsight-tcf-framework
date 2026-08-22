package nhnis.eos.co.a.application.facade;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.application.service.eoscoa0141Service;
import nhnis.eos.co.a.application.service.eoscoa0151Service;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0141Facade {
    private final eoscoa0141Service s0141;
    private final eoscoa0151Service s0151;
    public eoscoa0141Facade(eoscoa0141Service s0141, eoscoa0151Service s0151) {
        this.s0141 = s0141; this.s0151 = s0151;
    }
    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0141S0(Object b) { return s0141.eoscoa0141S0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0141C0(Object b) { return s0141.eoscoa0141C0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0141U0(Object b) { return s0141.eoscoa0141U0(EosResults.asMap(b)); }
    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0151S0(Object b) { return s0151.eoscoa0151S0(EosResults.asMap(b)); }
}
