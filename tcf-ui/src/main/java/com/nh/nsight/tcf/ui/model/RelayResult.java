package com.nh.nsight.tcf.ui.model;

public record RelayResult(
        String businessCode,
        String targetUrl,
        int httpStatus,
        long elapsedMs,
        String responseBody
) {
}
