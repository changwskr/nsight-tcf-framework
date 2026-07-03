package com.nh.nsight.marketing.sv.entry.handler;

import com.nh.nsight.marketing.sv.entry.facade.SvIntegrationFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * SV.Integration.icSample — SV 가 IC 샘플을 표준 전문으로 연동 조회하는 데모 거래.
 */
@Component
public class SvIcSampleHandler implements TransactionHandler {

    private final SvIntegrationFacade facade;

    public SvIcSampleHandler(SvIntegrationFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "SV.Integration.icSample";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiryIcSample(request.getBody(), context);
    }
}
