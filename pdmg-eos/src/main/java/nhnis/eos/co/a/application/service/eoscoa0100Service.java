package nhnis.eos.co.a.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.dto.eoscoa0100S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0100S0DTOout;
import nhnis.eos.co.a.persistence.dao.eoscoa0100DAO;

@Service
public class eoscoa0100Service {

    private final eoscoa0100DAO dao;

    public eoscoa0100Service(eoscoa0100DAO dao) {
        this.dao = dao;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public eoscoa0100S0DTOout eoscoa0100S0(eoscoa0100S0DTOin input) {
        Map<String, Object> param = new HashMap<>();
        if (input != null) {
            if (input.getRiskCd() != null && !input.getRiskCd().isBlank()) {
                param.put("riskCd", input.getRiskCd());
            }
            if (input.getStatusCd() != null && !input.getStatusCd().isBlank()) {
                param.put("statusCd", input.getStatusCd());
            }
        }
        List<Map<String, Object>> rows = dao.eoscoa0100S0_S0(param);
        eoscoa0100S0DTOout out = new eoscoa0100S0DTOout();
        out.setItems(rows);
        out.setSize(rows == null ? 0 : rows.size());
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }
}
