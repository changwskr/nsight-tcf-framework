package com.nh.nsight.marketing.bc.facade;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.marketing.bc.service.BcSampleService;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BcSampleFacade {
    private final BcSampleService service;

    public BcSampleFacade(BcSampleService service) {
        this.service = service;
    }

    @Transactional(readOnly = true, timeout = 5)
    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        System.out.println("\n ==============================================[BcSampleFacade.inquiry] start");
        System.out.println(" ==============================================[BcSampleFacade.inquiry] serviceId="
                + context.getHeader().getServiceId());
        System.out.println(" ==============================================[BcSampleFacade.inquiry] guid="
                + context.getHeader().getGuid());
        System.out.println(" ==============================================[BcSampleFacade.inquiry] body=" + body);

        Map<String, Object> result = service.inquiry(body, context);

        System.out.println(" ==============================================[BcSampleFacade.inquiry] resultKeys="
                + result.keySet());
        System.out.println(" ==============================================[BcSampleFacade.inquiry] end");
        return result;
    }
}
