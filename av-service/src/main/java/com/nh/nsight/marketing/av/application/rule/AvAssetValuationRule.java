package com.nh.nsight.marketing.av.application.rule;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSearchCriteria;
import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListRequest;
import com.nh.nsight.marketing.av.persistence.dto.assetvaluation.AssetValuationRow;
import com.nh.nsight.tcf.core.support.context.TransactionContext;
import com.nh.nsight.tcf.core.support.error.BusinessException;
import com.nh.nsight.tcf.core.support.error.ErrorCode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

/** REQ-AV-001 / AV-VAL-001~008 / AV-RULE-001~010. */
@Component
public class AvAssetValuationRule {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;
    private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Set<String> ASSET_TYPE_CODES =
            Set.of("REAL_ESTATE", "MOVABLE", "SECURITIES");
    private static final Set<String> VALUATION_STATUS_CODES =
            Set.of("COMPLETED", "IN_PROGRESS", "CANCELLED");

    public void validateSelectList(
            AssetValuationSelectListRequest request, TransactionContext context) {
        AssetValuationSelectListRequest safe =
                request != null ? request : AssetValuationSelectListRequest.fromMap(Map.of());

        if (safe.getBaseDate() == null) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "AV001: 기준일은 필수입니다.");
        }
        if (!safe.getBaseDate().matches("\\d{8}")) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV002: 기준일은 yyyyMMdd 형식이어야 합니다.");
        }
        LocalDate baseDate;
        try {
            baseDate = LocalDate.parse(safe.getBaseDate(), YMD);
        } catch (DateTimeParseException ex) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV002: 기준일은 yyyyMMdd 형식이어야 합니다.");
        }
        if (baseDate.isAfter(LocalDate.now())) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV003: 기준일은 현재 업무일보다 미래일 수 없습니다.");
        }

        int pageNo = safe.getPageNo() != null ? safe.getPageNo() : 1;
        if (pageNo < 1) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "AV004: 페이지 번호는 1 이상이어야 합니다.");
        }
        int pageSize = safe.getPageSize() != null ? safe.getPageSize() : DEFAULT_PAGE_SIZE;
        if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV005: 페이지 크기는 1~100이어야 합니다.");
        }

        if (safe.getAssetTypeCode() != null
                && !ASSET_TYPE_CODES.contains(safe.getAssetTypeCode())) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV006: 정의되지 않은 자산유형 코드입니다.");
        }
        if (safe.getValuationStatusCode() != null
                && !VALUATION_STATUS_CODES.contains(safe.getValuationStatusCode())) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV007: 정의되지 않은 평가상태 코드입니다.");
        }

        String branchId =
                context != null && context.getHeader() != null
                        ? context.getHeader().getBranchId()
                        : null;
        if (branchId == null || branchId.isBlank()) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV008: 인증 문맥에 담당 지점이 존재해야 합니다.");
        }
    }

    public AssetValuationSearchCriteria buildSearchCriteria(
            AssetValuationSelectListRequest request, TransactionContext context) {
        AssetValuationSelectListRequest safe =
                request != null ? request : AssetValuationSelectListRequest.fromMap(Map.of());
        int pageNo = safe.getPageNo() != null ? safe.getPageNo() : 1;
        int pageSize = safe.getPageSize() != null ? safe.getPageSize() : DEFAULT_PAGE_SIZE;
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        AssetValuationSearchCriteria criteria = new AssetValuationSearchCriteria();
        criteria.setPageNo(pageNo);
        criteria.setPageSize(pageSize);
        criteria.setOffset((pageNo - 1) * pageSize);
        criteria.setBaseDate(safe.getBaseDate());
        criteria.setCustomerNo(safe.getCustomerNo());
        criteria.setAssetTypeCode(safe.getAssetTypeCode());
        criteria.setValuationStatusCode(safe.getValuationStatusCode());
        criteria.setBranchId(context.getHeader().getBranchId());
        return criteria;
    }

    /** AV-RULE-006: 고객번호 서버 마스킹. */
    public List<Map<String, Object>> toMaskedRows(List<AssetValuationRow> rows) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (rows == null) {
            return result;
        }
        for (AssetValuationRow row : rows) {
            Map<String, Object> map = row.toMap();
            map.put("customerNo", maskCustomerNo(row.getCustomerNo()));
            result.add(map);
        }
        return result;
    }

    public String maskCustomerNo(String customerNo) {
        if (customerNo == null || customerNo.isBlank()) {
            return customerNo;
        }
        String value = customerNo.trim();
        if (value.length() <= 4) {
            return "****";
        }
        return value.substring(0, 4) + "*".repeat(value.length() - 4);
    }
}
