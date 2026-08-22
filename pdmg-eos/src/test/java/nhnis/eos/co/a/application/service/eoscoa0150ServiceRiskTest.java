package nhnis.eos.co.a.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nhnis.eos.co.a.domain.EosRiskCalculator;
import nhnis.eos.co.a.persistence.dao.eoscoa0150DAO;
import nhnis.eos.co.a.support.EosAuditWriter;
import nhnis.eos.co.a.support.EosIdGenerator;

@ExtendWith(MockitoExtension.class)
class eoscoa0150ServiceRiskTest {

    @Mock
    private eoscoa0150DAO dao;
    @Mock
    private EosIdGenerator ids;
    @Mock
    private EosAuditWriter audit;

    private final EosRiskCalculator calc = new EosRiskCalculator();

    private eoscoa0150Service service() {
        return new eoscoa0150Service(dao, calc, ids, audit);
    }

    @Test
    void eoscoa0150C0_computesTotalAndGradeOnServer() {
        when(dao.existsResource(any())).thenReturn(1);
        when(ids.next("RSK")).thenReturn("RSK20260816000001");
        when(dao.selectPolicyVal(any())).thenAnswer(inv -> {
            Map<?, ?> m = inv.getArgument(0);
            Object key = m.get("policyKey");
            if ("CRITICAL_MIN".equals(key)) return "32";
            if ("HIGH_MIN".equals(key)) return "26";
            if ("MEDIUM_MIN".equals(key)) return "20";
            return null;
        });

        Map<String, Object> in = new HashMap<>();
        in.put("resourceId", "RSC20260816000001");
        in.put("scoreBiz", 4);
        in.put("scoreEnv", 4);
        in.put("scoreExp", 3);
        in.put("scoreSec", 4);
        in.put("scoreImp", 4);
        in.put("scoreAlt", 3);
        in.put("scoreEos", 5);

        Map<String, Object> out = service().eoscoa0150C0(in);

        assertThat(out.get("RSLT_CD")).isEqualTo("0000");
        assertThat(out.get("totalScore")).isEqualTo(27);
        assertThat(out.get("riskGradeCd")).isEqualTo("HIGH");
        assertThat(out.get("followUpActionYn")).isEqualTo("Y");
    }

    @Test
    void eoscoa0150C0_rejectsInvalidScore() {
        when(dao.existsResource(any())).thenReturn(1);

        Map<String, Object> in = new HashMap<>();
        in.put("resourceId", "RSC1");
        in.put("scoreBiz", 9);
        in.put("scoreEnv", 1);
        in.put("scoreExp", 1);
        in.put("scoreSec", 1);
        in.put("scoreImp", 1);
        in.put("scoreAlt", 1);
        in.put("scoreEos", 1);

        Map<String, Object> out = service().eoscoa0150C0(in);

        assertThat(out.get("RSLT_CD")).isEqualTo("EOS-E0001");
    }
}
