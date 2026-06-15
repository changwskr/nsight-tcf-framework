package com.nh.nsight.tcf.core.validation;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import com.nh.nsight.tcf.core.message.StandardHeader;
import com.nh.nsight.tcf.core.message.StandardRequest;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StandardHeaderValidator {
    public void validate(StandardRequest<Map<String, Object>> request) {
        System.out.println("\n ======================================================================[StandardHeaderValidator.validate] start");
        if (request == null || request.getHeader() == null) {
            System.out.println(" ======================================================================[StandardHeaderValidator.validate] end (header missing)");
            throw new BusinessException(ErrorCode.INVALID_HEADER, "표준 Header가 없습니다.");
        }
        StandardHeader header = request.getHeader();
        System.out.println(" ======================================================================[StandardHeaderValidator.validate] header.normalize");
        header.normalize();
        System.out.println(" ======================================================================[StandardHeaderValidator.validate] required serviceId");
        required(header.getServiceId(), "serviceId");
        System.out.println(" ======================================================================[StandardHeaderValidator.validate] required businessCode");
        required(header.getBusinessCode(), "businessCode");
        System.out.println(" ======================================================================[StandardHeaderValidator.validate] required transactionCode");
        required(header.getTransactionCode(), "transactionCode");
        System.out.println(" ======================================================================[StandardHeaderValidator.validate] required processingType");
        required(header.getProcessingType(), "processingType");
        System.out.println(" ======================================================================[StandardHeaderValidator.validate] required channelId");
        required(header.getChannelId(), "channelId");
        System.out.println(" ======================================================================[StandardHeaderValidator.validate] end");
    }

    private void required(String value, String name) {
        if (!StringUtils.hasText(value)) {
            System.out.println(" ======================================================================[StandardHeaderValidator.required] fail field=" + name);
            throw new BusinessException(ErrorCode.INVALID_HEADER, "필수 Header 누락: " + name);
        }
    }
}
