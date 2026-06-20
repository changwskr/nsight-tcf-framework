package com.nh.nsight.tcf.core.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TcfConsoleLog {
    private static final Logger LOG = LoggerFactory.getLogger("tcf.console");

    private TcfConsoleLog() {
    }

    public static void println(String message) {
        LOG.info("{}", message);
    }
}
