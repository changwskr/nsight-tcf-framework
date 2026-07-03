package com.nh.nsight.marketing.ic.entry.handler;

import com.nh.nsight.marketing.ic.entry.facade.IcCustomerFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * IC.Customer.inquiry — IC 고객 상세 조회 (SV 고객요약 연동 포함).
 */
@Component
public class IcCustomerInquiryHandler implements TransactionHandler {

    private final IcCustomerFacade facade;

    public IcCustomerInquiryHandler(IcCustomerFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "IC.Customer.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiryCustomerDetail(request.getBody(), context);
    }
}
