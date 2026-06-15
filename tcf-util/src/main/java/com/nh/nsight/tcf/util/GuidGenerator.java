package com.nh.nsight.tcf.util;

import java.util.UUID;

public final class GuidGenerator {
    private GuidGenerator() {}

    public static String newGuid() {
        return UUID.randomUUID().toString();
    }

    public static String newTraceId() {
        return "trc-" + UUID.randomUUID();
    }
}
