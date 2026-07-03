package com.nh.nsight.marketing.sv.client;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.eai.client.TcfServiceClient;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * IC(Integration Customer) 서비스 연동 Adapter (SV → IC 방향 데모).
 *
 * <p>SV 는 IC 의 Java 코드를 직접 참조하지 않고, 표준 전문(serviceId) 호출로만 IC 를 조회한다.
 */
@Component
public class IcIntegrationClient {

    private static final String TARGET_BUSINESS_CODE = "IC";
    private static final String SVC_SAMPLE_INQUIRY = "IC.Sample.inquiry";
    private static final String TX_SAMPLE_INQUIRY = "IC-INQ-0001";

    private final TcfServiceClient tcfServiceClient;

    public IcIntegrationClient(TcfServiceClient tcfServiceClient) {
        this.tcfServiceClient = tcfServiceClient;
    }

    /** IC 샘플 조회 (한 줄 호출 편의 메서드 사용). */
    public Map<String, Object> inquirySample(String sampleKey, TransactionContext callerContext) {
        return tcfServiceClient.callForBody(
                TARGET_BUSINESS_CODE,
                SVC_SAMPLE_INQUIRY,
                TX_SAMPLE_INQUIRY,
                Map.of("sampleKey", sampleKey),
                callerContext);
    }
}
