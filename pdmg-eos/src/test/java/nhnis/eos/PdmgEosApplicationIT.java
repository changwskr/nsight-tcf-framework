package nhnis.eos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import nhnis.eos.co.a.batch.EosBatchJobs;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"local", "test"})
class PdmgEosApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EosBatchJobs batchJobs;

    @Test
    void contextLoads() {
        assertThat(batchJobs).isNotNull();
    }

    @Test
    void eoscoa0110S0_dashboardOk() throws Exception {
        String body = postOk("eoscoa0110S0", "{\"dto\":{}}");
        assertThat(body).contains("\"totalCnt\"");
        assertRsltOk(body);
    }

    @Test
    void eoscoa0120S0_listOk() throws Exception {
        String body = postOk("eoscoa0120S0", "{\"dto\":{\"pageNo\":1,\"pageSize\":5}}");
        assertThat(body).contains("\"totalCount\"");
        assertRsltOk(body);
    }

    @Test
    void eoscoa0130S0_detailSeedOk() throws Exception {
        String body = postOk("eoscoa0130S0", "{\"dto\":{\"resourceId\":\"RSC20260816000001\"}}");
        assertThat(body).contains("RSC20260816000001");
        assertRsltOk(body);
    }

    @Test
    void batch_runAll_writesHistory() {
        Map<String, Object> all = batchJobs.runAll();
        assertThat(all).containsKeys("statusRecalc", "exceptionExpire", "actionOverdue", "monthlyCheckMiss");
        @SuppressWarnings("unchecked")
        Map<String, Object> recalc = (Map<String, Object>) all.get("statusRecalc");
        assertThat(recalc.get("statusCd")).isIn("SUCCESS", "PARTIAL");
        assertThat(recalc.get("runId")).isNotNull();
        assertThat(((Number) recalc.get("processCnt")).intValue()).isGreaterThanOrEqualTo(1);
    }

    private String postOk(String serviceId, String requestBody) throws Exception {
        return mockMvc.perform(post("/" + serviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("rms_svc_c", serviceId)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    /** Map → RSLT_CD, DataObject JSON → rslt_CD */
    private static void assertRsltOk(String body) {
        assertThat(body.contains("\"RSLT_CD\":\"0000\"") || body.contains("\"rslt_CD\":\"0000\"")).isTrue();
    }
}
