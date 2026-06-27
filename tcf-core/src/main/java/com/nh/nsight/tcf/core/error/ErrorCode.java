package com.nh.nsight.tcf.core.error;

public final class ErrorCode {
    private ErrorCode() {}

    public static final String INVALID_HEADER = "E-COM-VALID-0001";
    public static final String SERVICE_NOT_FOUND = "E-COM-DISP-0001";
    public static final String SESSION_INVALID = "E-COM-AUTH-0001";
    public static final String AUTHORIZATION_DENIED = "E-COM-AUTH-0002";
    public static final String DUPLICATE_REQUEST = "E-COM-IDEMP-0001";
    public static final String TXCTRL_HDR_SERVICE_ID = "E-TCF-HDR-001";
    public static final String TXCTRL_HDR_TRANSACTION_CODE = "E-TCF-HDR-002";
    public static final String TXCTRL_HDR_BUSINESS_CODE = "E-TCF-HDR-003";
    public static final String TXCTRL_HDR_SERVICE_NAME = "E-TCF-HDR-004";
    public static final String TXCTRL_HDR_USER = "E-TCF-HDR-005";
    public static final String TXCTRL_HDR_CHANNEL_ID = "E-TCF-HDR-006";
    public static final String TXCTRL_HDR_BRANCH = "E-TCF-HDR-007";
    public static final String TXCTRL_NOT_ALLOWED = "E-TCF-CTL-001";
    public static final String TXCTRL_DUPLICATE = "E-TCF-CTL-002";
    public static final String TXCTRL_UNAVAILABLE = "E-TCF-CTL-003";
    public static final String BUSINESS_ERROR = "E-COM-BIZ-0001";
    public static final String SYSTEM_ERROR = "E-COM-SYS-0001";
}
