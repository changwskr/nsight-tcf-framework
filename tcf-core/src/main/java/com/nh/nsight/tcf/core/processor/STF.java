package com.nh.nsight.tcf.core.processor;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.context.TransactionContextHolder;
import com.nh.nsight.tcf.core.control.TransactionControlService;
import com.nh.nsight.tcf.core.idempotency.IdempotencyChecker;
import com.nh.nsight.tcf.core.logging.TransactionLogService;
import com.nh.nsight.tcf.core.message.StandardHeader;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.security.AuthorizationValidator;
import com.nh.nsight.tcf.core.security.SessionValidator;
import com.nh.nsight.tcf.core.support.TcfConsoleLog;
import com.nh.nsight.tcf.core.timeout.TimeoutPolicyService;
import com.nh.nsight.tcf.core.validation.StandardHeaderValidator;
import com.nh.nsight.tcf.util.GuidGenerator;
import java.util.Map;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class STF {
    private final StandardHeaderValidator headerValidator;
    private final SessionValidator sessionValidator;
    private final AuthorizationValidator authorizationValidator;
    private final IdempotencyChecker idempotencyChecker;
    private final TransactionControlService transactionControlService;
    private final TimeoutPolicyService timeoutPolicyService;
    private final TransactionLogService transactionLogService;

    public STF(StandardHeaderValidator headerValidator,
            SessionValidator sessionValidator,
            AuthorizationValidator authorizationValidator,
            IdempotencyChecker idempotencyChecker,
            TransactionControlService transactionControlService,
            TimeoutPolicyService timeoutPolicyService,
            TransactionLogService transactionLogService) {
        this.headerValidator = headerValidator;
        this.sessionValidator = sessionValidator;
        this.authorizationValidator = authorizationValidator;
        this.idempotencyChecker = idempotencyChecker;
        this.transactionControlService = transactionControlService;
        this.timeoutPolicyService = timeoutPolicyService;
        this.transactionLogService = transactionLogService;
    }

    public TransactionContext preProcess(StandardRequest<Map<String, Object>> request, StandardHeader clientHeader) {
        TcfConsoleLog.println("=====================================================[STF.preProcess] start");
        StandardHeader header = request.getHeader();
        if (clientHeader == null) {
            clientHeader = StandardHeader.copyOf(header);
        }
        TcfConsoleLog.println(
                " =====================================================[STF.preProcess] headerValidator.validate");
        headerValidator.validate(request);
        if (!StringUtils.hasText(header.getGuid())) {
            header.setGuid(GuidGenerator.newGuid());
        }
        if (!StringUtils.hasText(header.getTraceId())) {
            header.setTraceId(GuidGenerator.newTraceId());
        }
        clientHeader.applyGeneratedCorrelationIdsFrom(header);
        TcfConsoleLog.println(
                " =====================================================[STF.preProcess] guid/traceId assigned");
        TransactionContext context = new TransactionContext(header, clientHeader);
        TransactionContextHolder.set(context);
        putMdc(header);
        TcfConsoleLog.println(
                " =====================================================[STF.preProcess] sessionValidator.validate");
        sessionValidator.validate(header);
        TcfConsoleLog.println(
                " =====================================================[STF.preProcess] authorizationValidator.validate");
        authorizationValidator.validate(header);
        TcfConsoleLog.println(
                " =====================================================[STF.preProcess] transactionControlService.check");
        transactionControlService.check(header);
        TcfConsoleLog.println(
                " =====================================================[STF.preProcess] timeoutPolicyService.resolveAndApply");
        timeoutPolicyService.resolveAndApply(header, context);
        TcfConsoleLog.println(
                " =====================================================[STF.preProcess] idempotencyChecker.checkAndMarkProcessing");
        idempotencyChecker.checkAndMarkProcessing(header);
        TcfConsoleLog.println(
                " =====================================================[STF.preProcess] transactionLogService.start");
        transactionLogService.start(context);
        TcfConsoleLog.println(" =====================================================[STF.preProcess] end");
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
