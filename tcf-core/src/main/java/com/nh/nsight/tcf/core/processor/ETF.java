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

    public StandardResponse<Object> success(StandardRequest<Map<String, Object>> request, Object body, TransactionContext context) {
        System.out.println("\n ======================================================================[ETF.success] start");
        StandardHeader header = headerOf(request, context);
        if (context != null) {
            System.out.println(" ======================================================================[ETF.success] idempotencyChecker.markSuccess");
            idempotencyChecker.markSuccess(header);
            System.out.println(" ======================================================================[ETF.success] transactionLogService.end");
            transactionLogService.end(context, "S0000", null);
            System.out.println(" ======================================================================[ETF.success] auditLogService.audit");
            auditLogService.audit(context, "S0000");
            System.out.println(" ======================================================================[ETF.success] metricService.record");
            metricService.record(context, "S0000");
        }
        System.out.println(" ======================================================================[ETF.success] end");
        return StandardResponse.success(header, body);
    }

    public StandardResponse<Object> businessFail(StandardRequest<Map<String, Object>> request, BusinessException e, TransactionContext context) {
        System.out.println("\n ======================================================================[ETF.businessFail] start");
        StandardHeader header = headerOf(request, context);
        if (context != null) {
            System.out.println(" ======================================================================[ETF.businessFail] idempotencyChecker.markFail");
            idempotencyChecker.markFail(header);
            System.out.println(" ======================================================================[ETF.businessFail] transactionLogService.end");
            transactionLogService.end(context, "E0001", e.getErrorCode());
            System.out.println(" ======================================================================[ETF.businessFail] auditLogService.audit");
            auditLogService.audit(context, "E0001");
            System.out.println(" ======================================================================[ETF.businessFail] metricService.record");
            metricService.record(context, "E0001");
        }
        System.out.println(" ======================================================================[ETF.businessFail] end");
        return StandardResponse.fail(header, e.getErrorCode(), e.getMessage(), null);
    }

    public StandardResponse<Object> systemError(StandardRequest<Map<String, Object>> request, Exception e, TransactionContext context) {
        System.out.println("\n ======================================================================[ETF.systemError] start");
        StandardHeader header = headerOf(request, context);
        log.error("TCF system error. serviceId={}", header == null ? null : header.getServiceId(), e);
        if (context != null) {
            System.out.println(" ======================================================================[ETF.systemError] idempotencyChecker.markFail");
            idempotencyChecker.markFail(header);
            System.out.println(" ======================================================================[ETF.systemError] transactionLogService.end");
            transactionLogService.end(context, "E0001", ErrorCode.SYSTEM_ERROR);
            System.out.println(" ======================================================================[ETF.systemError] auditLogService.audit");
            auditLogService.audit(context, "E0001");
            System.out.println(" ======================================================================[ETF.systemError] metricService.record");
            metricService.record(context, "E0001");
        }
        System.out.println(" ======================================================================[ETF.systemError] end");
        return StandardResponse.fail(header, ErrorCode.SYSTEM_ERROR, "시스템 오류가 발생했습니다.", e.getClass().getSimpleName());
    }

    private StandardHeader headerOf(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        if (context != null) {
            return context.getHeader();
        }
        return request == null ? null : request.getHeader();
    }
}
