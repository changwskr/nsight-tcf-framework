package nhnis.eos.co.a.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nhnis.eos.co.a.persistence.dao.eoscoa0170DAO;
import nhnis.eos.co.a.support.EosAuditWriter;
import nhnis.eos.co.a.support.EosIdGenerator;

@ExtendWith(MockitoExtension.class)
class eoscoa0170ServiceSodTest {

    @Mock
    private eoscoa0170DAO dao;
    @Mock
    private EosIdGenerator ids;
    @Mock
    private EosAuditWriter audit;

    @InjectMocks
    private eoscoa0170Service service;

    @Test
    void eoscoa0180U0_rejectsSameApproverAsRequester() {
        Map<String, Object> req = new HashMap<>();
        req.put("STATUS_CD", "PENDING");
        req.put("REQ_USER_ID", "USER_A");
        req.put("RESOURCE_ID", "RSC1");
        when(dao.selectOne(any())).thenReturn(req);

        Map<String, Object> in = new HashMap<>();
        in.put("excReqId", "EXC1");
        in.put("decisionCd", "APPROVE");
        in.put("approverId", "USER_A");

        Map<String, Object> out = service.eoscoa0180U0(in);

        assertThat(out.get("RSLT_CD")).isEqualTo("EOS-E0002");
        assertThat(String.valueOf(out.get("RSLT_MSG"))).contains("SOD");
        verify(dao, never()).insertAppr(any());
    }

    @Test
    void eoscoa0180U0_approvesWhenDifferentUser() {
        Map<String, Object> req = new HashMap<>();
        req.put("STATUS_CD", "PENDING");
        req.put("REQ_USER_ID", "USER_A");
        req.put("RESOURCE_ID", "RSC1");
        when(dao.selectOne(any())).thenReturn(req);
        when(ids.next("APR")).thenReturn("APR20260816000001");

        Map<String, Object> in = new HashMap<>();
        in.put("excReqId", "EXC1");
        in.put("decisionCd", "APPROVE");
        in.put("approverId", "USER_B");

        Map<String, Object> out = service.eoscoa0180U0(in);

        assertThat(out.get("RSLT_CD")).isEqualTo("0000");
        verify(dao).insertAppr(any());
        verify(dao).updateStatus(any());
        verify(dao).updateResourceExceptionYn(any());
    }
}
