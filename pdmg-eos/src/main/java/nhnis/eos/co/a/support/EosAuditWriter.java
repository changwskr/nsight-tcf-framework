package nhnis.eos.co.a.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import nhnis.eos.co.a.persistence.dao.eoscoaAuditDAO;

@Component
public class EosAuditWriter {

    private final eoscoaAuditDAO dao;
    private final EosIdGenerator ids;

    public EosAuditWriter(eoscoaAuditDAO dao, EosIdGenerator ids) {
        this.dao = dao;
        this.ids = ids;
    }

    public void write(String serviceId, String entityType, String entityId, String actionCd,
                      String beforeJson, String afterJson, String resultCd, String reason) {
        Map<String, Object> p = new HashMap<>();
        p.put("histId", ids.next("AUD"));
        p.put("traceId", ids.next("TRC"));
        p.put("userId", "LOCAL");
        p.put("orgCd", "");
        p.put("serviceId", serviceId);
        p.put("entityType", entityType);
        p.put("entityId", entityId);
        p.put("actionCd", actionCd);
        p.put("beforeJson", beforeJson);
        p.put("afterJson", afterJson);
        p.put("resultCd", resultCd == null ? "OK" : resultCd);
        p.put("reasonTxt", reason);
        p.put("eventDtm", EosDtm.now());
        dao.insert(p);
    }
}
