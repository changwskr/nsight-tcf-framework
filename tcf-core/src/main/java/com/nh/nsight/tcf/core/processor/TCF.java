package com.nh.nsight.tcf.core.processor;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.context.TransactionContextHolder;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.message.StandardResponse;
import com.nh.nsight.tcf.core.dispatch.TransactionDispatcher;
import java.util.Map;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class TCF {
    private final STF stf;
    private final TransactionDispatcher dispatcher;
    private final ETF etf;

    public TCF(STF stf, TransactionDispatcher dispatcher, ETF etf) {
        this.stf = stf;
        this.dispatcher = dispatcher;
        this.etf = etf;
    }

    public StandardResponse<Object> process(StandardRequest<Map<String, Object>> request) {
        TransactionContext context = null;
        System.out.println("\n ======================================================================[TCF.process] start");
        try {
            System.out.println(" ======================================================================[TCF.process] stf.preProcess");
            context = stf.preProcess(request);
            System.out.println(" ======================================================================[TCF.process] dispatcher.dispatch");
            Object body = dispatcher.dispatch(request, context);
            System.out.println(" ======================================================================[TCF.process] etf.success");
            StandardResponse<Object> response = etf.success(request, body, context);
            System.out.println(" ======================================================================[TCF.process] end (success)");
            return response;
        } catch (BusinessException e) {
            System.out.println(" ======================================================================[TCF.process] etf.businessFail");
            StandardResponse<Object> response = etf.businessFail(request, e, context);
            System.out.println(" ======================================================================[TCF.process] end (businessFail)");
            return response;
        } catch (Exception e) {
            System.out.println(" ======================================================================[TCF.process] etf.systemError");
            StandardResponse<Object> response = etf.systemError(request, e, context);
            System.out.println(" ======================================================================[TCF.process] end (systemError)");
            return response;
        } finally {
            System.out.println(" ======================================================================[TCF.process] cleanup");
            TransactionContextHolder.clear();
            MDC.clear();
        }
    }
}
