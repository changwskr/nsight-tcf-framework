package com.nh.nsight.tcf.core.logging;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionLogService {
    private static final Logger log = LoggerFactory.getLogger("transaction.log");

    public void start(TransactionContext context) {
        StandardHeader h = context.getHeader();
        log.info("TX_START guid={} traceId={} serviceId={} txCode={} userId={} branchId={} channelId={}",
                h.getGuid(), h.getTraceId(), h.getServiceId(), h.getTransactionCode(), h.getUserId(), h.getBranchId(), h.getChannelId());
    }

    public void end(TransactionContext context, String resultCode, String errorCode) {
        StandardHeader h = context.getHeader();
        log.info("TX_END guid={} traceId={} serviceId={} resultCode={} errorCode={} elapsedMs={}",
                h.getGuid(), h.getTraceId(), h.getServiceId(), resultCode, errorCode, context.elapsedMillis());
    }
}
