package com.nh.nsight.tcf.core.control;

import java.util.Optional;

public interface TransactionControlRepository {

    boolean isGlobalUnblockActive();

    Optional<TransactionControlRule> findRule(TransactionControlHeader header);
}
