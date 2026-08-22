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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nhnis.eos.co.a.domain.EosActionStateMachine;
import nhnis.eos.co.a.persistence.dao.eoscoa0160DAO;
import nhnis.eos.co.a.support.EosAuditWriter;
import nhnis.eos.co.a.support.EosIdGenerator;

@ExtendWith(MockitoExtension.class)
class eoscoa0160ServiceTransitionTest {

    @Mock
    private eoscoa0160DAO dao;
    @Mock
    private EosIdGenerator ids;
    @Mock
    private EosAuditWriter audit;

    private final EosActionStateMachine sm = new EosActionStateMachine();

    private eoscoa0160Service service() {
        return new eoscoa0160Service(dao, sm, ids, audit);
    }

    @Test
    void eoscoa0160U1_rejectsInvalidTransition() {
        Map<String, Object> cur = new HashMap<>();
        cur.put("STATUS_CD", "NOT_STARTED");
        when(dao.selectOne(any())).thenReturn(cur);

        Map<String, Object> in = new HashMap<>();
        in.put("actionId", "ACT1");
        in.put("toStatusCd", "DONE");

        Map<String, Object> out = service().eoscoa0160U1(in);

        assertThat(out.get("RSLT_CD")).isEqualTo("EOS-E0003");
        verify(dao, never()).updateStatus(any());
    }

    @Test
    void eoscoa0160U1_allowsValidTransition() {
        Map<String, Object> cur = new HashMap<>();
        cur.put("STATUS_CD", "PLANNED");
        when(dao.selectOne(any())).thenReturn(cur);
        when(dao.updateStatus(any())).thenReturn(1);
        when(ids.next("ASH")).thenReturn("ASH1");

        Map<String, Object> in = new HashMap<>();
        in.put("actionId", "ACT1");
        in.put("toStatusCd", "IN_PROGRESS");

        Map<String, Object> out = service().eoscoa0160U1(in);

        assertThat(out.get("RSLT_CD")).isEqualTo("0000");
        assertThat(out.get("statusCd")).isEqualTo("IN_PROGRESS");
        verify(dao).updateStatus(any());
        verify(dao).insertHist(any());
    }

    @Test
    void eoscoa0165U0_rejectsVerifierSameAsOwner() {
        Map<String, Object> cur = new HashMap<>();
        cur.put("STATUS_CD", "TESTING");
        cur.put("OWNER_USER_ID", "OWNER1");
        cur.put("REG_USER_ID", "REG1");
        when(dao.selectOne(any())).thenReturn(cur);

        Map<String, Object> in = new HashMap<>();
        in.put("actionId", "ACT1");
        in.put("approveYn", "Y");
        in.put("verifyUserId", "OWNER1");

        Map<String, Object> out = service().eoscoa0165U0(in);

        assertThat(out.get("RSLT_CD")).isEqualTo("EOS-E0002");
        verify(dao, never()).updateStatus(any());
    }

    @Test
    void eoscoa0165U0_completesWhenVerifierDifferent() {
        Map<String, Object> cur = new HashMap<>();
        cur.put("STATUS_CD", "TESTING");
        cur.put("OWNER_USER_ID", "OWNER1");
        cur.put("REG_USER_ID", "REG1");
        when(dao.selectOne(any())).thenReturn(cur);
        when(dao.updateStatus(any())).thenReturn(1);
        when(ids.next("ASH")).thenReturn("ASH1");

        Map<String, Object> in = new HashMap<>();
        in.put("actionId", "ACT1");
        in.put("approveYn", "Y");
        in.put("verifyUserId", "APPROVER1");
        in.put("actualEndYmd", "20260816");

        Map<String, Object> out = service().eoscoa0165U0(in);

        assertThat(out.get("RSLT_CD")).isEqualTo("0000");
        assertThat(out.get("statusCd")).isEqualTo("DONE");
        verify(dao).updateStatus(any());
    }
}
