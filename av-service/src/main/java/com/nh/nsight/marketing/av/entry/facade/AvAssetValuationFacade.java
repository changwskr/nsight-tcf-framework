package com.nh.nsight.marketing.av.entry.facade;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListRequest;
import com.nh.nsight.marketing.av.application.service.AvAssetValuationService;
import com.nh.nsight.tcf.core.support.context.TransactionContext;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** REQ-AV-001 Facade — Read Only, timeout 3s. */
@Service
public class AvAssetValuationFacade {
    private final AvAssetValuationService service;

    public AvAssetValuationFacade(AvAssetValuationService service) {
        this.service = service;
    }

    @Transactional(readOnly = true, timeout = 3)
    public Map<String, Object> selectList(Map<String, Object> body, TransactionContext context) {
        AssetValuationSelectListRequest request = AssetValuationSelectListRequest.fromMap(body);
        return service.selectList(request, context).toMap();
    }
}
