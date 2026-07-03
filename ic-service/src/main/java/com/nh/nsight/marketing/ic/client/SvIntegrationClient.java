package com.nh.nsight.marketing.ic.client;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.eai.client.TcfServiceClient;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * SV(Single View) 서비스 연동 Adapter.
 *
 * <p>IC 업무는 SV 의 Java Service 를 직접 참조하지 않고, 이 Adapter 를 통해
 * 표준 전문(serviceId) 호출로만 SV 를 조회한다. 실제 HTTP 호출은 {@link TcfServiceClient} 가 담당.
 */
@Component
public class SvIntegrationClient {

    private static final String TARGET_BUSINESS_CODE = "SV";
    private static final String SVC_CUSTOMER_SUMMARY = "SV.Customer.selectSummary";
    private static final String TX_CUSTOMER_SUMMARY = "SV-INQ-0002";

    private final TcfServiceClient tcfServiceClient;

    public SvIntegrationClient(TcfServiceClient tcfServiceClient) {
        this.tcfServiceClient = tcfServiceClient;
    }

    /**
     * SV 고객 요약 조회.
     *
     * @return SV 응답 body (customerGrade/totalBalance/lastTransactionDate 등)
     */
    public Map<String, Object> selectCustomerSummary(String customerNo, TransactionContext callerContext) {
        // 대상별 Adapter 없이도 가능한 한 줄 호출 형태 (편의 메서드)
        return tcfServiceClient.callForBody(
                TARGET_BUSINESS_CODE,
                SVC_CUSTOMER_SUMMARY,
                TX_CUSTOMER_SUMMARY,
                Map.of("customerNo", customerNo),
                callerContext);
    }
}
