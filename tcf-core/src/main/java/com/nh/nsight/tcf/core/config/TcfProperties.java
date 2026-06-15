package com.nh.nsight.tcf.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nsight.tcf")
public class TcfProperties {
    private boolean sessionValidationEnabled = false;
    private boolean authorizationValidationEnabled = false;
    private boolean idempotencyEnabled = true;
    private boolean auditEnabled = true;

    public boolean isSessionValidationEnabled() { return sessionValidationEnabled; }
    public void setSessionValidationEnabled(boolean sessionValidationEnabled) { this.sessionValidationEnabled = sessionValidationEnabled; }
    public boolean isAuthorizationValidationEnabled() { return authorizationValidationEnabled; }
    public void setAuthorizationValidationEnabled(boolean authorizationValidationEnabled) { this.authorizationValidationEnabled = authorizationValidationEnabled; }
    public boolean isIdempotencyEnabled() { return idempotencyEnabled; }
    public void setIdempotencyEnabled(boolean idempotencyEnabled) { this.idempotencyEnabled = idempotencyEnabled; }
    public boolean isAuditEnabled() { return auditEnabled; }
    public void setAuditEnabled(boolean auditEnabled) { this.auditEnabled = auditEnabled; }
}
