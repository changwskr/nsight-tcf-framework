package com.nh.nsight.marketing.sv.entry.facade;

import com.nh.nsight.marketing.sv.application.service.SvIntegrationDemoService;
import com.nh.nsight.tcf.core.context.TransactionContext;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SV → IC 연동 데모 유스케이스 조립(트랜잭션 경계).
 *
 * <p>조회성 연동이므로 SV DB 트랜잭션과 IC DB 트랜잭션을 하나로 묶지 않는다.
 */
@Service
public class SvIntegrationFacade {

    private final SvIntegrationDemoService service;

    public SvIntegrationFacade(SvIntegrationDemoService service) {
        this.service = service;
    }

    @Transactional(readOnly = true, timeout = 5)
    public Map<String, Object> inquiryIcSample(Map<String, Object> body, TransactionContext context) {
        return service.inquiryIcSample(body, context);
    }
}
