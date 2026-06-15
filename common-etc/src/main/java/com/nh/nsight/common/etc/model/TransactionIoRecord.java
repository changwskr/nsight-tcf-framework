package com.nh.nsight.common.etc.model;

import java.time.OffsetDateTime;

public class TransactionIoRecord {
    private String guid;
    private String traceId;
    private String serviceId;
    private String transactionCode;
    private String ioType;
    private String maskedPayload;
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }
    public String getTraceId() { return traceId; }
    public void setTraceId(String traceId) { this.traceId = traceId; }
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    public String getIoType() { return ioType; }
    public void setIoType(String ioType) { this.ioType = ioType; }
    public String getMaskedPayload() { return maskedPayload; }
    public void setMaskedPayload(String maskedPayload) { this.maskedPayload = maskedPayload; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
