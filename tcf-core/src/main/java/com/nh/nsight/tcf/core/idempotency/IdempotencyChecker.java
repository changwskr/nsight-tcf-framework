package com.nh.nsight.tcf.core.idempotency;

import com.nh.nsight.tcf.core.message.StandardHeader;

public interface IdempotencyChecker {
    void checkAndMarkProcessing(StandardHeader header);
    void markSuccess(StandardHeader header);
    void markFail(StandardHeader header);
}
