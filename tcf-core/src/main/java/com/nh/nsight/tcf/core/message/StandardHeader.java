package com.nh.nsight.tcf.core.message;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class StandardHeader implements Serializable {
    private String systemId;
    private String businessCode;
    private String serviceId;
    private String transactionCode;
    private String processingType;
    private String guid;
    private String traceId;
    private String channelId;
    private String userId;
    private String branchId;
    private String centerId;
    private String requestTime;
    private String clientIp;
    private String idempotencyKey;

    public void normalize() {
        if (systemId == null || systemId.isBlank()) {
            systemId = "NSIGHT-MP";
        }
        if (requestTime == null || requestTime.isBlank()) {
            requestTime = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (businessCode != null) {
            businessCode = businessCode.trim().toUpperCase();
        }
        if (processingType != null) {
            processingType = processingType.trim().toUpperCase();
        }
    }

    public String safeServiceId() {
        return Objects.toString(serviceId, "UNKNOWN");
    }

    public String getSystemId() { return systemId; }
    public void setSystemId(String systemId) { this.systemId = systemId; }
    public String getBusinessCode() { return businessCode; }
    public void setBusinessCode(String businessCode) { this.businessCode = businessCode; }
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    public String getProcessingType() { return processingType; }
    public void setProcessingType(String processingType) { this.processingType = processingType; }
    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }
    public String getTraceId() { return traceId; }
    public void setTraceId(String traceId) { this.traceId = traceId; }
    public String getChannelId() { return channelId; }
    public void setChannelId(String channelId) { this.channelId = channelId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }
    public String getCenterId() { return centerId; }
    public void setCenterId(String centerId) { this.centerId = centerId; }
    public String getRequestTime() { return requestTime; }
    public void setRequestTime(String requestTime) { this.requestTime = requestTime; }
    public String getClientIp() { return clientIp; }
    public void setClientIp(String clientIp) { this.clientIp = clientIp; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
}
