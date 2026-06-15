package com.nh.nsight.common.etc.service;

import com.nh.nsight.tcf.core.message.StandardHeader;
import com.nh.nsight.common.etc.model.TransactionIoRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionIoService {
    private static final Logger log = LoggerFactory.getLogger(TransactionIoService.class);

    public void record(StandardHeader header, String ioType, String maskedPayload) {
        if (header == null) {
            return;
        }
        TransactionIoRecord record = new TransactionIoRecord();
        record.setGuid(header.getGuid());
        record.setTraceId(header.getTraceId());
        record.setServiceId(header.getServiceId());
        record.setTransactionCode(header.getTransactionCode());
        record.setIoType(ioType);
        record.setMaskedPayload(maskedPayload);
        log.debug("ET_IO_RECORD guid={} serviceId={} ioType={}", record.getGuid(), record.getServiceId(), record.getIoType());
    }
}
