package com.nh.nsight.marketing.av.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListRequest;
import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListResponse;
import com.nh.nsight.marketing.av.application.rule.AvAssetValuationRule;
import com.nh.nsight.marketing.av.persistence.dao.AvAssetValuationDao;
import com.nh.nsight.marketing.av.persistence.dto.assetvaluation.AssetValuationRow;
import com.nh.nsight.tcf.core.support.context.TransactionContext;
import com.nh.nsight.tcf.core.support.message.StandardHeader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** REQ-AV-001 Service path: masking + paging meta (TC-AV-001/012). */
@ExtendWith(MockitoExtension.class)
class AvAssetValuationServiceTest {

    @Mock
    private AvAssetValuationDao dao;

    private AvAssetValuationService service;

    @BeforeEach
    void setUp() {
        service = new AvAssetValuationService(new AvAssetValuationRule(), dao);
    }

    @Test
    void selectList_returnsMaskedItemsAndTotalCount() {
        AssetValuationRow row = new AssetValuationRow();
        row.setAssetId("AST-000001");
        row.setCustomerNo("1234567890");
        row.setAssetTypeCode("REAL_ESTATE");
        row.setAssetTypeName("부동산");
        row.setValuationAmount(new BigDecimal("350000000"));
        row.setValuationDate("20260805");
        row.setValuationStatusCode("COMPLETED");
        row.setValuationStatusName("평가완료");

        when(dao.searchValuations(any())).thenReturn(List.of(row));
        when(dao.countValuations(any())).thenReturn(1);

        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(
                        Map.of("baseDate", "20260805", "pageNo", 1, "pageSize", 20));
        AssetValuationSelectListResponse response = service.selectList(request, context("BR001"));
        Map<String, Object> body = response.toMap();

        assertEquals(1, body.get("totalCount"));
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("items");
        assertEquals(1, items.size());
        assertEquals("1234******", items.get(0).get("customerNo"));
        assertEquals("AST-000001", items.get(0).get("assetId"));
        assertTrue(((List<?>) body.get("maskedColumns")).contains("customerNo"));
        verify(dao).searchValuations(any());
        verify(dao).countValuations(any());
    }

    @Test
    void selectList_emptyResult_returnsEmptyItems() {
        when(dao.searchValuations(any())).thenReturn(List.of());
        when(dao.countValuations(any())).thenReturn(0);

        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(
                        Map.of("baseDate", "20260805", "pageNo", 1, "pageSize", 20));
        Map<String, Object> body = service.selectList(request, context("BR001")).toMap();

        assertEquals(0, body.get("totalCount"));
        assertEquals(List.of(), body.get("items"));
    }

    private static TransactionContext context(String branchId) {
        StandardHeader header = new StandardHeader();
        header.setServiceId("AV.AssetValuation.selectList");
        header.setGuid("GUID-SVC-TEST");
        header.setBranchId(branchId);
        return new TransactionContext(header);
    }
}
