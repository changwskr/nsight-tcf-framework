package com.nh.nsight.marketing.bc.handler;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import com.nh.nsight.marketing.bc.facade.BcSampleFacade;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BcSampleInquiryHandler implements TransactionHandler {
    private final BcSampleFacade facade;

    public BcSampleInquiryHandler(BcSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "BC.Sample.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        System.out.println("\n ==============================================[BcSampleInquiryHandler.doHandle] start");
        System.out.println(" ==============================================[BcSampleInquiryHandler.doHandle] serviceId="
                + context.getHeader().getServiceId());
        System.out.println(" ==============================================[BcSampleInquiryHandler.doHandle] guid="
                + context.getHeader().getGuid());
        System.out.println(" ==============================================[BcSampleInquiryHandler.doHandle] traceId="
                + context.getHeader().getTraceId());
        System.out.println(" ==============================================[BcSampleInquiryHandler.doHandle] userId="
                + context.getHeader().getUserId());
        System.out.println(" ==============================================[BcSampleInquiryHandler.doHandle] body="
                + request.getBody());

        Object result = facade.inquiry(request.getBody(), context);

        System.out.println(" ==============================================[BcSampleInquiryHandler.doHandle] end");
        return result;
    }
}
