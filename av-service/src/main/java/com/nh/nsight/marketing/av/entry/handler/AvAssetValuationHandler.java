package com.nh.nsight.marketing.av.entry.handler;

import com.nh.nsight.marketing.av.entry.facade.AvAssetValuationFacade;
import com.nh.nsight.tcf.core.support.context.TransactionContext;
import com.nh.nsight.tcf.core.support.error.BusinessException;
import com.nh.nsight.tcf.core.support.error.ErrorCode;
import com.nh.nsight.tcf.core.support.message.StandardRequest;
import com.nh.nsight.tcf.core.support.transaction.TransactionHandler;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * AV 자산평가 도메인 핸들러. AV.AssetValuation.* 거래를 처리한다.
 * REQ-AV-001 / PILOT-AV-001
 */
@Component
public class AvAssetValuationHandler implements TransactionHandler {

    private static final String SELECT_LIST = "AV.AssetValuation.selectList";

    private final AvAssetValuationFacade facade;

    public AvAssetValuationHandler(AvAssetValuationFacade facade) {
        this.facade = facade;
    }

    @Override
    public Collection<String> serviceIds() {
        return List.of(SELECT_LIST);
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        String serviceId = context.getHeader().getServiceId();
        return switch (serviceId) {
            case SELECT_LIST -> facade.selectList(request.getBody(), context);
            default -> throw new BusinessException(
                    ErrorCode.SERVICE_NOT_FOUND,
                    "지원하지 않는 ServiceId: " + serviceId);
        };
    }
}
