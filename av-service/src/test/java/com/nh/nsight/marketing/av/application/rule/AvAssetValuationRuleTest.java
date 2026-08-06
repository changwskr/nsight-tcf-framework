package com.nh.nsight.marketing.av.application.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSearchCriteria;
import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListRequest;
import com.nh.nsight.tcf.core.support.context.TransactionContext;
import com.nh.nsight.tcf.core.support.error.BusinessException;
import com.nh.nsight.tcf.core.support.message.StandardHeader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** REQ-AV-001 / TC-AV-* unit coverage. */
class AvAssetValuationRuleTest {

    private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final AvAssetValuationRule rule = new AvAssetValuationRule();

    @Test
    void validateSelectList_requiresBaseDate() {
        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(Map.of("pageNo", 1, "pageSize", 20));
        BusinessException ex =
                assertThrows(
                        BusinessException.class,
                        () -> rule.validateSelectList(request, context("BR001")));
        assertTrue(ex.getMessage().contains("AV001"));
    }

    @Test
    void validateSelectList_rejectsBadDateFormat() {
        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(
                        Map.of("baseDate", "2026-08-05", "pageNo", 1, "pageSize", 20));
        BusinessException ex =
                assertThrows(
                        BusinessException.class,
                        () -> rule.validateSelectList(request, context("BR001")));
        assertTrue(ex.getMessage().contains("AV002"));
    }

    @Test
    void validateSelectList_rejectsFutureBaseDate() {
        String future = LocalDate.now().plusDays(1).format(YMD);
        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(
                        Map.of("baseDate", future, "pageNo", 1, "pageSize", 20));
        BusinessException ex =
                assertThrows(
                        BusinessException.class,
                        () -> rule.validateSelectList(request, context("BR001")));
        assertTrue(ex.getMessage().contains("AV003"));
    }

    @Test
    void validateSelectList_rejectsPageSizeOverMax() {
        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(
                        Map.of("baseDate", "20260805", "pageNo", 1, "pageSize", 101));
        BusinessException ex =
                assertThrows(
                        BusinessException.class,
                        () -> rule.validateSelectList(request, context("BR001")));
        assertTrue(ex.getMessage().contains("AV005"));
    }

    @Test
    void validateSelectList_rejectsUnknownAssetType() {
        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(
                        Map.of(
                                "baseDate",
                                "20260805",
                                "pageNo",
                                1,
                                "pageSize",
                                20,
                                "assetTypeCode",
                                "UNKNOWN"));
        BusinessException ex =
                assertThrows(
                        BusinessException.class,
                        () -> rule.validateSelectList(request, context("BR001")));
        assertTrue(ex.getMessage().contains("AV006"));
    }

    @Test
    void validateSelectList_requiresBranchId() {
        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(
                        Map.of("baseDate", "20260805", "pageNo", 1, "pageSize", 20));
        BusinessException ex =
                assertThrows(
                        BusinessException.class,
                        () -> rule.validateSelectList(request, context(null)));
        assertTrue(ex.getMessage().contains("AV008"));
    }

    @Test
    void buildSearchCriteria_setsBranchAndOffset() {
        AssetValuationSelectListRequest request =
                AssetValuationSelectListRequest.fromMap(
                        Map.of(
                                "baseDate",
                                "20260805",
                                "pageNo",
                                2,
                                "pageSize",
                                10,
                                "customerNo",
                                "1234567890",
                                "assetTypeCode",
                                "REAL_ESTATE",
                                "valuationStatusCode",
                                "COMPLETED"));
        TransactionContext ctx = context("BR001");
        rule.validateSelectList(request, ctx);
        AssetValuationSearchCriteria criteria = rule.buildSearchCriteria(request, ctx);
        assertEquals(2, criteria.getPageNo());
        assertEquals(10, criteria.getPageSize());
        assertEquals(10, criteria.getOffset());
        assertEquals("20260805", criteria.getBaseDate());
        assertEquals("BR001", criteria.getBranchId());
        assertEquals("1234567890", criteria.getCustomerNo());
        assertEquals("REAL_ESTATE", criteria.getAssetTypeCode());
        assertEquals("COMPLETED", criteria.getValuationStatusCode());
    }

    @Test
    void maskCustomerNo_masksAfterPrefix() {
        assertEquals("1234******", rule.maskCustomerNo("1234567890"));
    }

    private static TransactionContext context(String branchId) {
        StandardHeader header = new StandardHeader();
        header.setServiceId("AV.AssetValuation.selectList");
        header.setGuid("GUID-TEST");
        header.setBranchId(branchId);
        return new TransactionContext(header);
    }
}
