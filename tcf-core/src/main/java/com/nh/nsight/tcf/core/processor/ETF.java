package com.nh.nsight.tcf.core.processor;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import com.nh.nsight.tcf.core.idempotency.IdempotencyChecker;
import com.nh.nsight.tcf.core.logging.AuditLogService;
import com.nh.nsight.tcf.core.logging.TransactionLogService;
import com.nh.nsight.tcf.core.message.StandardHeader;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.message.StandardResponse;
import com.nh.nsight.tcf.core.metrics.TransactionMetricService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.nh.nsight.tcf.core.support.TcfConsoleLog;

@Component
public class ETF {
    private static final Logger log = LoggerFactory.getLogger(ETF.class);
    private final TransactionLogService transactionLogService;
    private final AuditLogService auditLogService;
    private final TransactionMetricService metricService;
    private final IdempotencyChecker idempotencyChecker;

    public ETF(TransactionLogService transactionLogService,
            AuditLogService auditLogService,
            TransactionMetricService metricService,
            IdempotencyChecker idempotencyChecker) {
        this.transactionLogService = transactionLogService;
        this.auditLogService = auditLogService;
        this.metricService = metricService;
        this.idempotencyChecker = idempotencyChecker;
    }

    public StandardResponse<Object> success(StandardRequest<Map<String, Object>> request, Object body,
            TransactionContext context, StandardHeader clientHeader) {
        TcfConsoleLog.println("==============================[ETF.success] start");
        StandardHeader header = responseHeaderOf(request, context, clientHeader);
        if (context != null) {
            StandardHeader processingHeader = context.getHeader();
            TcfConsoleLog.println(" ==============================[ETF.success] idempotencyChecker.markSuccess");
            idempotencyChecker.markSuccess(processingHeader);
            TcfConsoleLog.println(" ==============================[ETF.success] transactionLogService.end");
            transactionLogService.end(context, "S0000", null);
            TcfConsoleLog.println(" ==============================[ETF.success] auditLogService.audit");
            auditLogService.audit(context, "S0000");
            TcfConsoleLog.println(" ==============================[ETF.success] metricService.record");
            metricService.record(context, "S0000");
        }
        TcfConsoleLog.println(" ==============================[ETF.success] end");
        return StandardResponse.success(header, body);
    }

    public StandardResponse<Object> businessFail(StandardRequest<Map<String, Object>> request, BusinessException e,
            TransactionContext context, StandardHeader clientHeader) {
        TcfConsoleLog.println("\n ==============================[ETF.businessFail] start");
        StandardHeader header = responseHeaderOf(request, context, clientHeader);
        if (context != null) {
            StandardHeader processingHeader = context.getHeader();
            TcfConsoleLog.println(" ==============================[ETF.businessFail] idempotencyChecker.markFail");
            idempotencyChecker.markFail(processingHeader);
            TcfConsoleLog.println(" ==============================[ETF.businessFail] transactionLogService.end");
            transactionLogService.end(context, "E0001", e.getErrorCode());
            TcfConsoleLog.println(" ==============================[ETF.businessFail] auditLogService.audit");
            auditLogService.audit(context, "E0001");
            TcfConsoleLog.println(" ==============================[ETF.businessFail] metricService.record");
            metricService.record(context, "E0001");
        }
        TcfConsoleLog.println(" ==============================[ETF.businessFail] end");
        return StandardResponse.fail(header, e.getErrorCode(), e.getMessage(), null);
    }

    public StandardResponse<Object> systemError(StandardRequest<Map<String, Object>> request, Exception e,
            TransactionContext context, StandardHeader clientHeader) {
        TcfConsoleLog.println("\n ==============================[ETF.systemError] start");
        StandardHeader header = responseHeaderOf(request, context, clientHeader);
        log.error("TCF system error. serviceId={}", header == null ? null : header.getServiceId(), e);
        if (context != null) {
            StandardHeader processingHeader = context.getHeader();
            TcfConsoleLog.println(" ==============================[ETF.systemError] idempotencyChecker.markFail");
            idempotencyChecker.markFail(processingHeader);
            TcfConsoleLog.println(" ==============================[ETF.systemError] transactionLogService.end");
            transactionLogService.end(context, "E0001", ErrorCode.SYSTEM_ERROR);
            TcfConsoleLog.println(" ==============================[ETF.systemError] auditLogService.audit");
            auditLogService.audit(context, "E0001");
            TcfConsoleLog.println(" ==============================[ETF.systemError] metricService.record");
            metricService.record(context, "E0001");
        }
        TcfConsoleLog.println(" ==============================[ETF.systemError] end");
        return StandardResponse.fail(header, ErrorCode.SYSTEM_ERROR, "시스템 오류가 발생했습니다.", e.getClass().getSimpleName());
    }

    private StandardHeader responseHeaderOf(StandardRequest<Map<String, Object>> request,
            TransactionContext context,
            StandardHeader clientHeader) {
        if (context != null) {
            return context.getClientHeader();
        }
        if (clientHeader != null) {
            return clientHeader;
        }
        return request == null ? null : StandardHeader.copyOf(request.getHeader());
    }
}
