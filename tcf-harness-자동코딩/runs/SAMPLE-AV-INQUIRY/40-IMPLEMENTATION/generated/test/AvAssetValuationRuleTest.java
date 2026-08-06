package com.nh.nsight.marketing.av.application.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSearchCriteria;
import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListRequest;
import com.nh.nsight.tcf.core.support.error.BusinessException;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AvAssetValuationRuleTest {

    private final AvAssetValuationRule rule = new AvAssetValuationRule();

    @Test
    void validateSelectList_requiresEvalDate() {
        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(Map.of("pageNo", 1, "pageSize", 20));
        assertThrows(BusinessException.class, () -> rule.validateSelectList(request));
    }

    @Test
    void buildSearchCriteria_setsOffset() {
        AssetValuationSelectListRequest request = AssetValuationSelectListRequest.fromMap(
                Map.of("pageNo", 2, "pageSize", 10, "evalDate", "20260805", "productCode", "P001"));
        rule.validateSelectList(request);
        AssetValuationSearchCriteria criteria = rule.buildSearchCriteria(request);
        assertEquals(2, criteria.getPageNo());
        assertEquals(10, criteria.getPageSize());
        assertEquals(10, criteria.getOffset());
        assertEquals("20260805", criteria.getEvalDate());
        assertEquals("P001", criteria.getProductCode());
    }
}
