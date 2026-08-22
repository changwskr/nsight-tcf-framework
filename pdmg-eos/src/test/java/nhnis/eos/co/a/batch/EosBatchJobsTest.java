package nhnis.eos.co.a.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nhnis.eos.co.a.domain.EosStatusEngine;
import nhnis.eos.co.a.persistence.dao.eoscoaBatchDAO;
import nhnis.eos.co.a.support.EosIdGenerator;

@ExtendWith(MockitoExtension.class)
class EosBatchJobsTest {

    @Mock
    private eoscoaBatchDAO dao;
    @Mock
    private EosIdGenerator ids;

    private final EosStatusEngine statusEngine = new EosStatusEngine();

    private EosBatchJobs jobs() {
        return new EosBatchJobs(dao, statusEngine, ids);
    }

    @Test
    void statusRecalc_updatesChangedStatuses() {
        when(ids.next("BAT")).thenReturn("BAT1");
        when(dao.selectPolicyVal(any())).thenReturn(null);
        Map<String, Object> row = new HashMap<>();
        row.put("RESOURCE_ID", "RSC1");
        row.put("EOS_STATUS_CD", "NORMAL");
        row.put("EOS_YMD", "20000101");
        row.put("EOL_YMD", null);
        when(dao.listActiveResourcesWithLfc()).thenReturn(List.of(row));

        Map<String, Object> out = jobs().statusRecalc();

        assertThat(out.get("statusCd")).isEqualTo("SUCCESS");
        assertThat(out.get("processCnt")).isEqualTo(1);
        assertThat(out.get("successCnt")).isEqualTo(1);
        ArgumentCaptor<Map<String, Object>> cap = ArgumentCaptor.forClass(Map.class);
        verify(dao).updateResourceStatus(cap.capture());
        assertThat(cap.getValue().get("eosStatusCd")).isEqualTo("OVERDUE");
        assertThat(cap.getValue().get("chgUserId")).isEqualTo("BATCH");
        verify(dao).finishBatchRun(any());
    }

    @Test
    void exceptionExpire_clearsActiveFlag() {
        when(ids.next("BAT")).thenReturn("BAT2");
        Map<String, Object> row = new HashMap<>();
        row.put("EXC_REQ_ID", "EXC1");
        row.put("RESOURCE_ID", "RSC1");
        row.put("END_YMD", "20000101");
        row.put("STATUS_CD", "APPROVED");
        when(dao.listExpiredExceptions(any())).thenReturn(List.of(row));

        Map<String, Object> out = jobs().exceptionExpire();

        assertThat(out.get("processCnt")).isEqualTo(1);
        verify(dao).expireException(any());
        verify(dao).clearResourceExceptionYn(any());
    }
}
