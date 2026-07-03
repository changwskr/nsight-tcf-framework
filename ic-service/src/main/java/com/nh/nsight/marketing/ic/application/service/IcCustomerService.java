package com.nh.nsight.marketing.ic.application.service;

import com.nh.nsight.marketing.ic.application.rule.IcCustomerRule;
import com.nh.nsight.marketing.ic.client.SvIntegrationClient;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.eai.exception.IntegrationBusinessException;
import com.nh.nsight.tcf.eai.exception.IntegrationException;
import com.nh.nsight.tcf.eai.exception.IntegrationTimeoutException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * IC 고객 상세 조회 서비스.
 *
 * <p>IC 자체 고객 기준정보 + SV 고객요약(연동 조회)을 조립한다.
 * SV 연동은 {@link SvIntegrationClient} 를 통해서만 수행하며, 연동 기술 오류는
 * 표준 업무 예외로 변환한다(트랜잭션은 서비스 간 분산하지 않음 — 조회성 연동).
 */
@Service
public class IcCustomerService {

    private final IcCustomerRule rule;
    private final SvIntegrationClient svIntegrationClient;

    public IcCustomerService(IcCustomerRule rule, SvIntegrationClient svIntegrationClient) {
        this.rule = rule;
        this.svIntegrationClient = svIntegrationClient;
    }

    public Map<String, Object> inquiryCustomerDetail(Map<String, Object> body, TransactionContext context) {
        String customerNo = rule.validateInquiry(body);

        // 1) IC 자체 고객 기준정보 (샘플: 데모용 최소 정보)
        Map<String, Object> icInfo = new LinkedHashMap<>();
        icInfo.put("customerNo", customerNo);
        icInfo.put("customerName", "IC-" + customerNo);

        // 2) SV 고객 요약 연동 조회
        Map<String, Object> svSummary = callSvSummary(customerNo, context);

        // 3) IC + SV 결과 조립
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "IC");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("customerNo", customerNo);
        result.put("customerName", icInfo.get("customerName"));
        result.put("customerGrade", svSummary.get("customerGrade"));
        result.put("totalBalance", svSummary.get("totalBalance"));
        result.put("lastTransactionDate", svSummary.get("lastTransactionDate"));
        result.put("svSummary", svSummary);
        return result;
    }

    private Map<String, Object> callSvSummary(String customerNo, TransactionContext context) {
        try {
            return svIntegrationClient.selectCustomerSummary(customerNo, context);
        } catch (IntegrationTimeoutException e) {
            throw new BusinessException(e.getErrorCode(), "SV 고객요약 조회 응답 지연", e);
        } catch (IntegrationBusinessException e) {
            // 대상 업무 오류코드를 그대로 전파
            throw new BusinessException(e.getTargetResultCode(),
                    "SV 고객요약 조회 업무 오류: " + e.getMessage(), e);
        } catch (IntegrationException e) {
            throw new BusinessException(e.getErrorCode(), "SV 고객요약 조회 연동 오류", e);
        }
    }
}
