package com.nh.nsight.tcf.core.processor;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.context.TransactionContextHolder;
import com.nh.nsight.tcf.core.idempotency.IdempotencyChecker;
import com.nh.nsight.tcf.core.logging.TransactionLogService;
import com.nh.nsight.tcf.core.message.StandardHeader;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.validation.StandardHeaderValidator;
import com.nh.nsight.tcf.util.GuidGenerator;
import java.util.Map;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class STF {
    private final StandardHeaderValidator headerValidator;
    private final IdempotencyChecker idempotencyChecker;
    private final TransactionLogService transactionLogService;

    public STF(StandardHeaderValidator headerValidator,
               IdempotencyChecker idempotencyChecker,
               TransactionLogService transactionLogService) {
        this.headerValidator = headerValidator;
        this.idempotencyChecker = idempotencyChecker;
        this.transactionLogService = transactionLogService;
    }

    public TransactionContext preProcess(StandardRequest<Map<String, Object>> request) {
        System.out.println("\n ======================================================================[STF.preProcess] start");
        System.out.println(" ======================================================================[STF.preProcess] headerValidator.validate");
        headerValidator.validate(request);
        StandardHeader header = request.getHeader();
        if (!StringUtils.hasText(header.getGuid())) {
            header.setGuid(GuidGenerator.newGuid());
        }
        if (!StringUtils.hasText(header.getTraceId())) {
            header.setTraceId(GuidGenerator.newTraceId());
        }
        System.out.println(" ======================================================================[STF.preProcess] guid/traceId assigned");
        TransactionContext context = new TransactionContext(header);
        TransactionContextHolder.set(context);
        putMdc(header);
        System.out.println(" ======================================================================[STF.preProcess] idempotencyChecker.checkAndMarkProcessing");
        idempotencyChecker.checkAndMarkProcessing(header);
        System.out.println(" ======================================================================[STF.preProcess] transactionLogService.start");
        transactionLogService.start(context);
        System.out.println(" ======================================================================[STF.preProcess] end");
        return context;
    }

    private void putMdc(StandardHeader header) {
        MDC.put("guid", header.getGuid());
        MDC.put("traceId", header.getTraceId());
        MDC.put("serviceId", header.getServiceId());
        MDC.put("userId", header.getUserId());
        MDC.put("branchId", header.getBranchId());
    }
}
