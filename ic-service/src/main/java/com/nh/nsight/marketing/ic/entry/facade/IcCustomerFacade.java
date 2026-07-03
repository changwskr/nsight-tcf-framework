package com.nh.nsight.marketing.ic.entry.facade;

import com.nh.nsight.marketing.ic.application.service.IcCustomerService;
import com.nh.nsight.tcf.core.context.TransactionContext;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * IC 고객 상세 조회 유스케이스 조립(트랜잭션 경계).
 *
 * <p>SV 연동 조회는 조회성이므로 IC DB 트랜잭션과 SV DB 트랜잭션을 하나로 묶지 않는다.
 */
@Service
public class IcCustomerFacade {

    private final IcCustomerService service;

    public IcCustomerFacade(IcCustomerService service) {
        this.service = service;
    }

    @Transactional(readOnly = true, timeout = 5)
    public Map<String, Object> inquiryCustomerDetail(Map<String, Object> body, TransactionContext context) {
        return service.inquiryCustomerDetail(body, context);
    }
}
