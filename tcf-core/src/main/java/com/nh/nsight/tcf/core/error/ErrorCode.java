package com.nh.nsight.tcf.core.error;

public final class ErrorCode {
    private ErrorCode() {}

    public static final String INVALID_HEADER = "E-COM-VALID-0001";
    public static final String SERVICE_NOT_FOUND = "E-COM-DISP-0001";
    public static final String SESSION_INVALID = "E-COM-AUTH-0001";
    public static final String AUTHORIZATION_DENIED = "E-COM-AUTH-0002";
    public static final String DUPLICATE_REQUEST = "E-COM-IDEMP-0001";
    public static final String BUSINESS_ERROR = "E-COM-BIZ-0001";
    public static final String SYSTEM_ERROR = "E-COM-SYS-0001";
}
