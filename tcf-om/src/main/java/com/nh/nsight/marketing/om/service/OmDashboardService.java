package com.nh.nsight.marketing.om.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.util.DateTimeUtil;
import com.nh.nsight.marketing.om.dao.OmOperationDao;
import com.nh.nsight.marketing.om.rule.OmOperationRule;
import com.nh.nsight.marketing.om.support.OmBodySupport;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OmDashboardService {
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
    private static final int RECENT_WINDOW_MINUTES = 15;
    private static final int RECENT_TOP_LIMIT = 10;

    private final OmOperationRule rule;
    private final OmOperationDao dao;
    private final String batchServiceUrl;

    public OmDashboardService(OmOperationRule rule, OmOperationDao dao,
                              @Value("${nsight.om.batch-service-url:http://127.0.0.1:8098/batch}") String batchServiceUrl) {
        this.rule = rule;
        this.dao = dao;
        this.batchServiceUrl = batchServiceUrl;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateOperation(context);
        String baseDate = OmBodySupport.stringValue(body, "baseDate");
        if (baseDate == null) {
            baseDate = DateTimeUtil.todayKst();
            if (baseDate.length() == 8) {
                baseDate = baseDate.substring(0, 4) + "-" + baseDate.substring(4, 6) + "-" + baseDate.substring(6, 8);
            }
        }

        Map<String, Object> txSummary = dao.selectTxSummary(baseDate);
        long total = toLong(txSummary.get("totalCount"));
        long errors = toLong(txSummary.get("errorCount"));
        double errorRate = total == 0 ? 0.0 : (errors * 100.0 / total);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "OM");
        result.put("screen", "운영 대시보드");
        result.put("baseDate", baseDate);
        result.put("overallStatus", errors > 0 ? "WARN" : "NORMAL");
        result.put("errorRatePct", Math.round(errorRate * 10) / 10.0);
        result.put("timeoutCount", txSummary.get("timeoutCount"));
        result.put("avgElapsedMs", txSummary.get("avgElapsedMs"));
        List<Map<String, Object>> apStatus = dao.selectApStatus();
        List<Map<String, Object>> dbStatus = dao.selectDbStatus();
        List<Map<String, Object>> deployStatus = dao.selectDeployStatus();
        List<Map<String, Object>> sessionStatus = dao.selectSessionStatus();
        String batchLastCollectedAt = resolveLatestCollectedAt(apStatus, dbStatus, sessionStatus, deployStatus);
        result.put("apStatus", apStatus);
        result.put("dbStatus", dbStatus);
        result.put("deployStatus", deployStatus);
        result.put("sessionStatus", sessionStatus);
        result.put("batchServiceUrl", batchServiceUrl);
        result.put("batchLastCollectedAt", batchLastCollectedAt);
        result.put("batchDataStale", isBatchDataStale(batchLastCollectedAt));
        int activeFromBatch = dao.sumSessionStatusActiveCount();
        result.put("activeSessionCount", sessionStatus.isEmpty()
                ? dao.selectActiveSessionCount() : activeFromBatch);
        result.put("expiredSessionCount", dao.sumSessionStatusExpiredCount());
        result.put("uniqueUserCount", dao.sumSessionStatusUniqueUsers());
        String toTime = DateTimeUtil.nowKst();
        String fromTime = OffsetDateTime.now(KST).minusMinutes(RECENT_WINDOW_MINUTES).toString();
        result.put("recentWindowMinutes", RECENT_WINDOW_MINUTES);
        result.put("errorTop", dao.selectErrorTop(fromTime, toTime, RECENT_TOP_LIMIT));
        result.put("slowTransactionFromTime", fromTime);
        result.put("slowTransactionToTime", toTime);
        result.put("slowTransactionTop", dao.selectSlowTransactionsTop(fromTime, toTime, RECENT_TOP_LIMIT));
        result.put("transactionSummary", txSummary);
        return result;
    }

    private long toLong(Object value) {
        if (value == null) {
            return 0L;
        }
        return Long.parseLong(String.valueOf(value));
    }

    private String resolveLatestCollectedAt(List<Map<String, Object>> apStatus,
                                          List<Map<String, Object>> dbStatus,
                                          List<Map<String, Object>> sessionStatus,
                                          List<Map<String, Object>> deployStatus) {
        OffsetDateTime latest = null;
        latest = maxCheckedAt(latest, apStatus, "checkedAt");
        latest = maxCheckedAt(latest, dbStatus, "checkedAt");
        latest = maxCheckedAt(latest, sessionStatus, "checkedAt");
        latest = maxCheckedAt(latest, deployStatus, "deployedAt");
        return latest == null ? null : latest.toString();
    }

    private OffsetDateTime maxCheckedAt(OffsetDateTime latest, List<Map<String, Object>> rows, String field) {
        for (Map<String, Object> row : rows) {
            Object raw = row.get(field);
            if (raw == null) {
                continue;
            }
            String value = String.valueOf(raw);
            if (value.isBlank() || "-".equals(value)) {
                continue;
            }
            try {
                OffsetDateTime parsed = OffsetDateTime.parse(value);
                if (latest == null || parsed.isAfter(latest)) {
                    latest = parsed;
                }
            } catch (Exception ignored) {
                // skip unparsable timestamps
            }
        }
        return latest;
    }

    private boolean isBatchDataStale(String batchLastCollectedAt) {
        if (batchLastCollectedAt == null || batchLastCollectedAt.isBlank()) {
            return true;
        }
        try {
            OffsetDateTime collectedAt = OffsetDateTime.parse(batchLastCollectedAt);
            return collectedAt.isBefore(OffsetDateTime.now(KST).minusMinutes(6));
        } catch (Exception e) {
            return true;
        }
    }
}


