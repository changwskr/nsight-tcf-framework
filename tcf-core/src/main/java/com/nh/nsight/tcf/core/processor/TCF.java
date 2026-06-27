package com.nh.nsight.tcf.core.processor;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.context.TransactionContextHolder;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.message.StandardHeader;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.message.StandardResponse;
import com.nh.nsight.tcf.core.dispatch.TransactionDispatcher;
import com.nh.nsight.tcf.core.support.TcfConsoleLog;
import com.nh.nsight.tcf.core.timeout.OnlineTransactionTimeoutExecutor;
import com.nh.nsight.tcf.core.timeout.TimeoutContextHolder;
import com.nh.nsight.tcf.core.timeout.TimeoutExceptionResolver;
import java.util.Map;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class TCF {
    private final STF stf;
    private final TransactionDispatcher dispatcher;
    private final ETF etf;
    private final OnlineTransactionTimeoutExecutor onlineTransactionTimeoutExecutor;

    public TCF(STF stf,
               TransactionDispatcher dispatcher,
               ETF etf,
               OnlineTransactionTimeoutExecutor onlineTransactionTimeoutExecutor) {
        this.stf = stf;
        this.dispatcher = dispatcher;
        this.etf = etf;
        this.onlineTransactionTimeoutExecutor = onlineTransactionTimeoutExecutor;
    }

    public StandardResponse<Object> process(StandardRequest<Map<String, Object>> request) {
        TransactionContext context = null;
        TcfConsoleLog
                .println("\n ====================================================================[TCF.process] start");
        try {
            logClientRequest(request);

            TcfConsoleLog.println(" ======================================[TCF.process] STF START");
            context = stf.preProcess(request);
            TcfConsoleLog.println(" ======================================[TCF.process] STF END");

            TcfConsoleLog.println(" ======================================[TCF.process] DISPATCHER  START");
            TransactionContext dispatchContext = context;
            Object body = onlineTransactionTimeoutExecutor.execute(
                    () -> dispatcher.dispatch(request, dispatchContext));
            TcfConsoleLog.println(" ======================================[TCF.process] DISPATCHER END");

            TcfConsoleLog.println(" ======================================[TCF.process] ETF START");
            StandardResponse<Object> response = etf.success(request, body, context);
            TcfConsoleLog.println(" ======================================[TCF.process] ETF END");

            logClientResponse(response);
            TcfConsoleLog.println(
                    " =============================================================[TCF.process] end (success)");
            return response;
        } catch (BusinessException e) {
            TcfConsoleLog.println(" =====================================[TCF.process] ETF.businessFail START");
            StandardResponse<Object> response = etf.businessFail(request, e, context);
            logClientResponse(response);
            TcfConsoleLog.println(" ====================================[TCF.process] end (businessFail)");
            return response;
        } catch (Exception e) {
            var timeoutError = TimeoutExceptionResolver.toBusinessException(e);
            if (timeoutError.isPresent()) {
                TcfConsoleLog.println(" =====================================[TCF.process] ETF.businessFail (timeout)");
                StandardResponse<Object> response = etf.businessFail(request, timeoutError.get(), context);
                logClientResponse(response);
                TcfConsoleLog.println(" ====================================[TCF.process] end (timeoutFail)");
                return response;
            }
            TcfConsoleLog.println(" ===============================================[TCF.process] etf.systemError");
            StandardResponse<Object> response = etf.systemError(request, e, context);
            logClientResponse(response);
            TcfConsoleLog.println(" ===============================================[TCF.process] end (systemError)");
            return response;
        } finally {
            TcfConsoleLog.println(" ===============================================[TCF.process] cleanup");
            TransactionContextHolder.clear();
            TimeoutContextHolder.clear();
            MDC.clear();
        }
    }

    private void logClientRequest(StandardRequest<Map<String, Object>> request) {
        TcfConsoleLog
                .println(" ======================================[TCF.logClientRequest] client request (incoming)");
        if (request == null) {
            TcfConsoleLog.println("  request=null");
            return;
        }
        StandardHeader header = request.getHeader();
        if (header == null) {
            TcfConsoleLog.println("  [header] null");
        } else {
            TcfConsoleLog.println("  [header]");
            TcfConsoleLog.println("    systemId=" + header.getSystemId());
            TcfConsoleLog.println("    businessCode=" + header.getBusinessCode());
            TcfConsoleLog.println("    serviceId=" + header.getServiceId());
            TcfConsoleLog.println("    transactionCode=" + header.getTransactionCode());
            TcfConsoleLog.println("    processingType=" + header.getProcessingType());
            TcfConsoleLog.println("    guid=" + header.getGuid());
            TcfConsoleLog.println("    traceId=" + header.getTraceId());
            TcfConsoleLog.println("    channelId=" + header.getChannelId());
            TcfConsoleLog.println("    userId=" + header.getUserId());
            TcfConsoleLog.println("    branchId=" + header.getBranchId());
            TcfConsoleLog.println("    centerId=" + header.getCenterId());
            TcfConsoleLog.println("    requestTime=" + header.getRequestTime());
            TcfConsoleLog.println("    clientIp=" + header.getClientIp());
            TcfConsoleLog.println("    idempotencyKey=" + header.getIdempotencyKey());
        }
        TcfConsoleLog.println("  [body]");
        TcfConsoleLog.println(formatPayload(request.getBody()));
    }

    private void logClientResponse(StandardResponse<Object> response) {
        TcfConsoleLog
                .println(" ======================================[TCF.logClientRequest] client response (outgoing)");
        if (response == null) {
            TcfConsoleLog.println("  response=null");
            return;
        }
        StandardHeader header = response.getHeader();
        if (header == null) {
            TcfConsoleLog.println("  [header] null");
        } else {
            TcfConsoleLog.println("  [header]");
            TcfConsoleLog.println("    businessCode=" + header.getBusinessCode());
            TcfConsoleLog.println("    serviceId=" + header.getServiceId());
            TcfConsoleLog.println("    transactionCode=" + header.getTransactionCode());
            TcfConsoleLog.println("    guid=" + header.getGuid());
            TcfConsoleLog.println("    traceId=" + header.getTraceId());
        }
        if (response.getResult() != null) {
            TcfConsoleLog.println("  [result]");
            TcfConsoleLog.println("    resultCode=" + response.getResult().getResultCode());
            TcfConsoleLog.println("    resultMessage=" + response.getResult().getResultMessage());
            TcfConsoleLog.println("    errorCode=" + response.getResult().getErrorCode());
            TcfConsoleLog.println("    errorMessage=" + response.getResult().getErrorMessage());
        }
        TcfConsoleLog.println("  [body]");
        TcfConsoleLog.println(formatPayload(response.getBody()));
    }

    private String formatPayload(Object value) {
        if (value == null) {
            return "    null";
        }
        if (value instanceof Map<?, ?> map) {
            if (map.isEmpty()) {
                return "    {}";
            }
            StringBuilder sb = new StringBuilder("    {");
            boolean first = true;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (!first) {
                    sb.append(',');
                }
                sb.append(System.lineSeparator())
                        .append("      ")
                        .append(entry.getKey())
                        .append('=')
                        .append(entry.getValue());
                first = false;
            }
            sb.append(System.lineSeparator()).append("    }");
            return sb.toString();
        }
        return "    " + value;
    }
}
