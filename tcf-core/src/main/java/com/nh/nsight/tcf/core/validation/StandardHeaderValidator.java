package com.nh.nsight.tcf.core.validation;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import com.nh.nsight.tcf.core.message.StandardHeader;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.support.TcfConsoleLog;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StandardHeaderValidator {
    public void validate(StandardRequest<Map<String, Object>> request) {
        TcfConsoleLog.boundary("HeaderValidator", "validate", "START");
        if (request == null || request.getHeader() == null) {
            TcfConsoleLog.boundary("HeaderValidator", "validate", "END", "header missing");
            throw new BusinessException(ErrorCode.INVALID_HEADER, "표준 Header가 없습니다.");
        }
        StandardHeader header = request.getHeader();
        TcfConsoleLog.step("HeaderValidator", "validate", "header.normalize");
        header.normalize();
        TcfConsoleLog.step("HeaderValidator", "validate", "required serviceId");
        required(header.getServiceId(), "serviceId");
        TcfConsoleLog.step("HeaderValidator", "validate", "required businessCode");
        required(header.getBusinessCode(), "businessCode");
        TcfConsoleLog.step("HeaderValidator", "validate", "required transactionCode");
        required(header.getTransactionCode(), "transactionCode");
        TcfConsoleLog.step("HeaderValidator", "validate", "required processingType");
        required(header.getProcessingType(), "processingType");
        TcfConsoleLog.step("HeaderValidator", "validate", "required channelId");
        required(header.getChannelId(), "channelId");
        TcfConsoleLog.boundary("HeaderValidator", "validate", "END");
    }

    private void required(String value, String name) {
        if (!StringUtils.hasText(value)) {
            TcfConsoleLog.step("HeaderValidator", "validate", "FAIL", "field=" + name);
            throw new BusinessException(ErrorCode.INVALID_HEADER, "필수 Header 누락: " + name);
        }
    }
}
