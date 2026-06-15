package com.nh.nsight.tcf.util;

public final class MaskingUtils {
    private MaskingUtils() {}

    public static String maskCustomerId(String value) {
        if (value == null || value.length() <= 4) {
            return "****";
        }
        return value.substring(0, 2) + "****" + value.substring(value.length() - 2);
    }

    public static String maskAccountNo(String value) {
        if (value == null || value.length() <= 6) {
            return "****";
        }
        return value.substring(0, 3) + "****" + value.substring(value.length() - 3);
    }
}
