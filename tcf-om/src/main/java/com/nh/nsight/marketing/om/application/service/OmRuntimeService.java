package com.nh.nsight.marketing.om.application.service;

import com.nh.nsight.marketing.om.application.rule.OmOperationRule;
import com.nh.nsight.marketing.om.support.OmBodySupport;
import com.nh.nsight.tcf.core.support.context.TransactionContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class OmRuntimeService {
    private final OmOperationRule rule;
    private final RuntimeStatusCollector collector;
    private final RuntimeCauseAnalyzer analyzer;

    public OmRuntimeService(
            OmOperationRule rule,
            RuntimeStatusCollector collector,
            RuntimeCauseAnalyzer analyzer) {
        this.rule = rule;
        this.collector = collector;
        this.analyzer = analyzer;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateOperation(context);
        boolean includeDetails = "Y".equalsIgnoreCase(OmBodySupport.stringValue(body, "includeDetails"));

        Map<String, Object> collected = collector.collectAll();
        Map<String, Object> analysis = analyzer.analyze(collected);
        List<Map<String, Object>> activeTransactions = includeDetails
                ? collector.extractActiveTransactions(collected) : List.of();
        List<Map<String, Object>> slowTransactions = includeDetails
                ? collector.extractSlowTransactions(collected, 50) : List.of();
        List<Map<String, Object>> slowSql = includeDetails
                ? collector.extractSlowSql(collected, 50) : List.of();
        List<Map<String, Object>> threads = includeDetails
                ? collector.collectThreads() : List.of();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "OM");
        result.put("screen", "런타임 진단");
        result.put("checkedAt", collected.get("checkedAt"));
        result.put("overallStatus", analysis.get("overallStatus"));
        result.put("primaryCauseCode", analysis.get("primaryCauseCode"));
        result.put("primaryMessage", analysis.get("primaryMessage"));
        result.put("dominantBusinessCode", analysis.get("dominantBusinessCode"));
        result.put("dominantServiceId", analysis.get("dominantServiceId"));
        result.put("dominantSqlId", analysis.get("dominantSqlId"));
        result.put("cards", analysis.get("cards"));
        result.put("findings", analysis.get("findings"));
        result.put("businessOwnership", analysis.get("businessOwnership"));
        result.put("targets", collected.get("targets"));
        result.put("activeTransactions", activeTransactions);
        result.put("slowTransactions", slowTransactions);
        result.put("slowSql", slowSql);
        result.put("threads", threads);
        return result;
    }
}
