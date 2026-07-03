package com.nh.nsight.marketing.sv.application.service;

import com.nh.nsight.marketing.sv.client.IcIntegrationClient;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.eai.exception.IntegrationBusinessException;
import com.nh.nsight.tcf.eai.exception.IntegrationException;
import com.nh.nsight.tcf.eai.exception.IntegrationTimeoutException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * SV → IC 연동 데모 서비스.
 *
 * <p>tcf-eai 가 특정 방향/업무에 묶이지 않음을 보이기 위한 역방향(SV→IC) 예시.
 * IC 샘플 조회 결과를 SV 응답에 합성한다. 연동 기술 오류는 표준 업무 예외로 변환한다.
 */
@Service
public class SvIntegrationDemoService {

    private final IcIntegrationClient icIntegrationClient;

    public SvIntegrationDemoService(IcIntegrationClient icIntegrationClient) {
        this.icIntegrationClient = icIntegrationClient;
    }

    public Map<String, Object> inquiryIcSample(Map<String, Object> body, TransactionContext context) {
        String sampleKey = resolveSampleKey(body);
        Map<String, Object> icSample = callIcSample(sampleKey, context);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "SV");
        result.put("serviceId", context.getHeader().getServiceId());
        result.put("guid", context.getHeader().getGuid());
        result.put("sampleKey", sampleKey);
        result.put("icSample", icSample);
        return result;
    }

    private String resolveSampleKey(Map<String, Object> body) {
        if (body == null) {
            return "SV-CALL";
        }
        Object raw = body.get("sampleKey");
        String value = raw == null ? "" : String.valueOf(raw).trim();
        return value.isEmpty() ? "SV-CALL" : value;
    }

    private Map<String, Object> callIcSample(String sampleKey, TransactionContext context) {
        try {
            return icIntegrationClient.inquirySample(sampleKey, context);
        } catch (IntegrationTimeoutException e) {
            throw new BusinessException(e.getErrorCode(), "IC 샘플 조회 응답 지연", e);
        } catch (IntegrationBusinessException e) {
            throw new BusinessException(e.getTargetResultCode(),
                    "IC 샘플 조회 업무 오류: " + e.getMessage(), e);
        } catch (IntegrationException e) {
            throw new BusinessException(e.getErrorCode(), "IC 샘플 조회 연동 오류", e);
        }
    }
}
