package com.nh.nsight.marketing.om.application.service;

import com.nh.nsight.marketing.om.application.rule.OmOperationRule;
import com.nh.nsight.marketing.om.support.OmBodySupport;
import com.nh.nsight.tcf.core.support.context.TransactionContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
                ? collector.collectActiveTransactions() : List.of();
        List<Map<String, Object>> slowTransactions = includeDetails
                ? collector.collectSlowTransactions(50) : List.of();
        List<Map<String, Object>> slowSql = includeDetails
                ? collector.collectSlowSql(50) : List.of();
        List<Map<String, Object>> threads = includeDetails
                ? collector.collectThreads() : List.of();
        List<Map<String, Object>> serviceCpuUsage = includeDetails
                ? collector.extractServiceCpuUsage(collected, 30) : List.of();

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
        result.put("serviceCpuUsage", serviceCpuUsage);
        result.put("threadAnalysis", buildThreadAnalysis(collected, analysis));
        result.put("jvmAnalysis", buildJvmAnalysis(collected, analysis, serviceCpuUsage));
        result.put("dbPoolAnalysis", buildDbPoolAnalysis(collected, analysis, activeTransactions));
        result.put("sqlAnalysis", buildSqlAnalysis(collected, analysis, slowSql, activeTransactions));
        result.put("dominanceAnalysis", buildDominanceAnalysis(
                collected, analysis, activeTransactions, serviceCpuUsage));
        result.put("transactionDetailAnalysis", buildTransactionDetailAnalysis(
                slowTransactions, activeTransactions, slowSql, threads));
        result.put("causeAnalysis", buildCauseAnalysis(collected, analysis, activeTransactions));
        result.put("statusCardsAnalysis", buildStatusCardsAnalysis(collected, analysis));
        result.put("activeTransactionListAnalysis", buildActiveTransactionListAnalysis(activeTransactions));
        result.put("businessOccupancyAnalysis", buildBusinessOccupancyAnalysis(collected, analysis));
        result.put("incidentFlowAnalysis", buildIncidentFlowAnalysis(
                collected, analysis, activeTransactions, slowSql, serviceCpuUsage));
        return result;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildThreadAnalysis(Map<String, Object> collected, Map<String, Object> analysis) {
        Map<String, Object> threadAnalysis = new LinkedHashMap<>();
        List<Map<String, Object>> warRows = new ArrayList<>();
        Map<String, Object> sharedPool = null;
        boolean anySaturation = false;

        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                Map<String, Object> target = (Map<String, Object>) targetRow;
                String businessCode = stringValue(target.get("businessCode"));
                boolean reachable = !Boolean.FALSE.equals(target.get("reachable"));
                Map<String, Object> status = castMap(target.get("status"));
                Map<String, Object> threadMetrics = castMap(status.get("thread"));
                Map<String, Object> summary = castMap(status.get("summary"));

                if (sharedPool == null && reachable && !threadMetrics.isEmpty()) {
                    sharedPool = extractSharedPool(threadMetrics);
                }

                double busyRatio = toDouble(threadMetrics.get("busyRatio"));
                int slowTx = (int) toLong(threadMetrics.get("slowTransactionCount"));
                boolean saturated = busyRatio >= 85 && slowTx >= 5;
                anySaturation = anySaturation || saturated;

                Map<String, Object> warRow = new LinkedHashMap<>();
                warRow.put("businessCode", businessCode);
                warRow.put("reachable", reachable);
                warRow.put("activeTransactionCount", threadMetrics.getOrDefault("activeTransactionCount", 0));
                warRow.put("slowTransactionCount", slowTx);
                warRow.put("busyRatio", busyRatio);
                warRow.put("threadSaturation", saturated);
                warRow.put("causeCode", summary.get("primaryCauseCode"));
                warRow.put("status", summary.get("status"));
                warRows.add(warRow);
            }
        }

        Map<String, Object> cards = castMap(analysis.get("cards"));
        Map<String, Object> saturation = new LinkedHashMap<>();
        boolean primarySaturation = "THREAD_SATURATION".equals(stringValue(analysis.get("primaryCauseCode")));
        saturation.put("detected", anySaturation || primarySaturation);
        saturation.put("primaryCauseCode", primarySaturation ? "THREAD_SATURATION" : (anySaturation ? "THREAD_SATURATION" : "NORMAL"));
        saturation.put("message", primarySaturation
                ? stringValue(analysis.get("primaryMessage"))
                : (anySaturation
                ? "Tomcat Thread 부족 또는 장기 거래 점유 상태입니다."
                : "Thread 포화 징후 없음"));
        saturation.put("criteria", "busyRatio >= 85 AND slowTransactionCount >= 5 (최근 1분)");
        saturation.put("note", "maxThreads 부족 단정 전 DB Pool·JVM 상태를 함께 확인하세요.");

        threadAnalysis.put("sharedTomcatPool", sharedPool == null ? Map.of() : sharedPool);
        threadAnalysis.put("warMetrics", warRows);
        threadAnalysis.put("saturation", saturation);
        threadAnalysis.put("aggregateBusyRatio", cards.get("threadBusyRatio"));
        threadAnalysis.put("aggregateSlowTransactionCount", cards.get("slowTransactionCount"));
        return threadAnalysis;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildJvmAnalysis(
            Map<String, Object> collected,
            Map<String, Object> analysis,
            List<Map<String, Object>> serviceCpuUsage) {
        Map<String, Object> jvmAnalysis = new LinkedHashMap<>();
        Map<String, Object> sharedJvm = null;

        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                Map<String, Object> target = (Map<String, Object>) targetRow;
                boolean reachable = !Boolean.FALSE.equals(target.get("reachable"));
                Map<String, Object> status = castMap(target.get("status"));
                Map<String, Object> jvm = castMap(status.get("jvm"));
                if (sharedJvm == null && reachable && !jvm.isEmpty()) {
                    sharedJvm = new LinkedHashMap<>(jvm);
                    sharedJvm.put("scope", "tomcat-shared");
                }
            }
        }
        if (sharedJvm == null) {
            sharedJvm = Map.of();
        }

        Map<String, Object> cards = castMap(analysis.get("cards"));
        int dbPending = (int) toLong(cards.get("dbPending"));
        double processCpu = toDouble(sharedJvm.get("processCpuRatio"));
        double heapRatio = toDouble(sharedJvm.get("heapRatio"));
        long gcTimeMs = toLong(sharedJvm.get("gcTimeLastMinuteMs"));

        boolean cpuOverload = processCpu >= 90 && dbPending == 0;
        boolean gcPressure = heapRatio >= 80 && gcTimeMs >= 3000;
        boolean primaryCpu = "CPU_OVERLOAD".equals(stringValue(analysis.get("primaryCauseCode")));
        boolean primaryGc = "GC_PRESSURE".equals(stringValue(analysis.get("primaryCauseCode")));

        Map<String, Object> cpu = new LinkedHashMap<>();
        cpu.put("detected", cpuOverload || primaryCpu);
        cpu.put("primaryCauseCode", primaryCpu ? "CPU_OVERLOAD" : (cpuOverload ? "CPU_OVERLOAD" : "NORMAL"));
        cpu.put("message", primaryCpu
                ? stringValue(analysis.get("primaryMessage"))
                : (cpuOverload
                ? "JVM CPU가 과부하 상태입니다. 업무 연산 또는 과도한 Thread 실행 가능"
                : "JVM CPU 정상 범위"));
        cpu.put("criteria", "processCpuRatio >= 90 AND dbPending = 0");
        cpu.put("processCpuRatio", processCpu);
        cpu.put("dbPending", dbPending);
        cpu.put("note", "동일 JVM에서 WAR별 Process CPU는 분리할 수 없습니다. ServiceId Thread CPU로 영향 업무를 추정하세요.");

        Map<String, Object> gc = new LinkedHashMap<>();
        gc.put("detected", gcPressure || primaryGc);
        gc.put("primaryCauseCode", primaryGc ? "GC_PRESSURE" : (gcPressure ? "GC_PRESSURE" : "NORMAL"));
        gc.put("message", primaryGc
                ? stringValue(analysis.get("primaryMessage"))
                : (gcPressure
                ? "Heap 압박으로 거래 지연 가능"
                : "GC 압박 징후 없음"));
        gc.put("criteria", "heapRatio >= 80 AND gcTimeLastMinuteMs >= 3000");
        gc.put("heapRatio", heapRatio);
        gc.put("gcTimeLastMinuteMs", gcTimeMs);
        gc.put("screenMessage", String.format(
                "JVM Heap 사용률 %s%%%n최근 1분 GC 소요시간 %s초%n모든 WAR의 응답 지연 가능성이 있습니다.",
                format1(heapRatio),
                format1(gcTimeMs / 1000.0)));

        jvmAnalysis.put("sharedJvm", sharedJvm);
        jvmAnalysis.put("cpuOverload", cpu);
        jvmAnalysis.put("gcPressure", gc);
        jvmAnalysis.put("serviceCpuUsage", serviceCpuUsage);
        jvmAnalysis.put("aggregateDbPending", dbPending);
        return jvmAnalysis;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildDbPoolAnalysis(
            Map<String, Object> collected,
            Map<String, Object> analysis,
            List<Map<String, Object>> activeTransactions) {
        Map<String, Object> dbPoolAnalysis = new LinkedHashMap<>();
        List<Map<String, Object>> warPools = new ArrayList<>();
        boolean anyExhausted = false;
        int aggregatePending = 0;
        String exhaustionScreenMessage = null;

        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                Map<String, Object> target = (Map<String, Object>) targetRow;
                String businessCode = stringValue(target.get("businessCode"));
                boolean reachable = !Boolean.FALSE.equals(target.get("reachable"));
                Map<String, Object> status = castMap(target.get("status"));

                for (Map<String, Object> pool : castList(status.get("dbPools"))) {
                    int maximum = (int) toLong(pool.get("maximum"));
                    int active = (int) toLong(pool.get("active"));
                    int idle = (int) toLong(pool.get("idle"));
                    int pending = (int) toLong(pool.get("pending"));
                    boolean exhausted = maximum > 0 && active >= maximum && idle == 0 && pending > 0;
                    anyExhausted = anyExhausted || exhausted;
                    aggregatePending += pending;

                    Map<String, Object> warRow = new LinkedHashMap<>(pool);
                    warRow.put("businessCode", businessCode);
                    warRow.put("reachable", reachable);
                    warRow.put("exhausted", exhausted);
                    warPools.add(warRow);

                    if (exhausted && exhaustionScreenMessage == null) {
                        exhaustionScreenMessage = buildExhaustionScreenMessage(businessCode, pool, pending);
                    }
                }
            }
        }

        boolean primaryExhausted = "DB_POOL_EXHAUSTED".equals(stringValue(analysis.get("primaryCauseCode")));

        Map<String, Object> exhaustion = new LinkedHashMap<>();
        exhaustion.put("detected", anyExhausted || primaryExhausted);
        exhaustion.put("primaryCauseCode", primaryExhausted || anyExhausted ? "DB_POOL_EXHAUSTED" : "NORMAL");
        exhaustion.put("message", primaryExhausted
                ? stringValue(analysis.get("primaryMessage"))
                : (anyExhausted
                ? "DB Connection을 얻지 못해 거래가 대기 중입니다."
                : "DB Pool 포화 징후 없음"));
        exhaustion.put("criteria", "active = maximum AND idle = 0 AND pending > 0");
        exhaustion.put("screenMessage", exhaustionScreenMessage != null
                ? exhaustionScreenMessage
                : "DB Pool 포화 기준 미충족");
        exhaustion.put("aggregatePending", aggregatePending);

        List<Map<String, Object>> dbWaitRows = new ArrayList<>();
        List<Map<String, Object>> sqlWaitRows = new ArrayList<>();
        for (Map<String, Object> tx : activeTransactions) {
            String step = stringValue(tx.get("currentStep"));
            if ("WAIT_DB_CONNECTION".equals(step)) {
                dbWaitRows.add(tx);
            } else if ("EXECUTING_SQL".equals(step)) {
                sqlWaitRows.add(tx);
            }
        }

        dbPoolAnalysis.put("pools", warPools);
        dbPoolAnalysis.put("exhaustion", exhaustion);
        dbPoolAnalysis.put("dbWaitTransactions", dbWaitRows);
        dbPoolAnalysis.put("executingSqlTransactions", sqlWaitRows);
        return dbPoolAnalysis;
    }

    private Map<String, Object> buildSqlAnalysis(
            Map<String, Object> collected,
            Map<String, Object> analysis,
            List<Map<String, Object>> slowSql,
            List<Map<String, Object>> activeTransactions) {
        Map<String, Object> sqlAnalysis = new LinkedHashMap<>();
        List<Map<String, Object>> statusRows = new ArrayList<>();
        int runningCount = 0;
        long configuredThresholdMs = 1000L;

        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                @SuppressWarnings("unchecked")
                Map<String, Object> target = (Map<String, Object>) targetRow;
                Map<String, Object> status = castMap(target.get("status"));
                Map<String, Object> runtimeConfig = castMap(status.get("runtimeConfig"));
                if (runtimeConfig.containsKey("slowSqlThresholdMs")) {
                    configuredThresholdMs = toLong(runtimeConfig.get("slowSqlThresholdMs"));
                    break;
                }
            }
        }

        for (Map<String, Object> tx : activeTransactions) {
            if (!"EXECUTING_SQL".equals(stringValue(tx.get("currentStep")))) {
                continue;
            }
            String mapperSql = stringValue(tx.get("mapperSql"));
            if (mapperSql == null || mapperSql.isBlank() || "-".equals(mapperSql)) {
                mapperSql = formatMapperSqlDisplay(stringValue(tx.get("sqlId")), null);
            }
            if (mapperSql == null || mapperSql.isBlank() || "-".equals(mapperSql)) {
                continue;
            }
            runningCount++;
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("businessCode", tx.get("businessCode"));
            row.put("serviceId", tx.get("serviceId"));
            row.put("mapperSql", mapperSql);
            row.put("status", "RUNNING");
            row.put("elapsedMs", tx.get("elapsedMs"));
            statusRows.add(row);
        }

        for (Map<String, Object> sql : slowSql) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("businessCode", sql.get("businessCode"));
            row.put("serviceId", sql.get("serviceId"));
            row.put("mapperSql", sql.containsKey("mapperSql")
                    ? sql.get("mapperSql")
                    : formatMapperSqlDisplay(stringValue(sql.get("mapperId")), stringValue(sql.get("sqlId"))));
            row.put("status", Boolean.FALSE.equals(sql.get("success")) ? "FAILED" : "COMPLETED");
            row.put("elapsedMs", sql.get("elapsedMs"));
            row.put("affectedRows", sql.get("affectedRows"));
            statusRows.add(row);
        }

        statusRows.sort((a, b) -> {
            int rankA = statusRank(stringValue(a.get("status")));
            int rankB = statusRank(stringValue(b.get("status")));
            if (rankA != rankB) {
                return Integer.compare(rankA, rankB);
            }
            return Long.compare(toLong(b.get("elapsedMs")), toLong(a.get("elapsedMs")));
        });

        Map<String, Object> cards = castMap(analysis.get("cards"));
        int aggregateSlowSql = (int) toLong(cards.get("slowSqlCount"));
        boolean primarySlow = "SLOW_SQL".equals(stringValue(analysis.get("primaryCauseCode")));
        boolean slowDetected = primarySlow || aggregateSlowSql >= 3;

        Map<String, Object> slowSqlAlert = new LinkedHashMap<>();
        slowSqlAlert.put("detected", slowDetected);
        slowSqlAlert.put("primaryCauseCode", primarySlow ? "SLOW_SQL" : (aggregateSlowSql >= 3 ? "SLOW_SQL" : "NORMAL"));
        slowSqlAlert.put("message", primarySlow
                ? stringValue(analysis.get("primaryMessage"))
                : (aggregateSlowSql >= 3
                ? "특정 Mapper SQL이 장시간 실행 중입니다."
                : "Slow SQL 징후 없음"));
        slowSqlAlert.put("criteria", String.format(
                "최근 1분 Slow SQL >= 3건 (현재 %d건) · threshold %dms",
                aggregateSlowSql,
                configuredThresholdMs));
        slowSqlAlert.put("aggregateSlowSqlCount", aggregateSlowSql);

        List<Map<String, Object>> thresholds = List.of(
                thresholdRow("단건 조회", 1000, configuredThresholdMs),
                thresholdRow("목록 조회", 2000, configuredThresholdMs),
                thresholdRow("등록·변경", 2000, configuredThresholdMs),
                thresholdRow("대량 처리", null, configuredThresholdMs));

        sqlAnalysis.put("thresholds", thresholds);
        sqlAnalysis.put("configuredThresholdMs", configuredThresholdMs);
        sqlAnalysis.put("thresholdNote", "1차: runtime-slow-sql-threshold-ms 공통 적용 · 2차: Service Catalog별 분리 예정");
        sqlAnalysis.put("slowSqlAlert", slowSqlAlert);
        sqlAnalysis.put("statusRows", statusRows);
        sqlAnalysis.put("runningSqlCount", runningCount);
        sqlAnalysis.put("completedSlowSql", slowSql);
        return sqlAnalysis;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildDominanceAnalysis(
            Map<String, Object> collected,
            Map<String, Object> analysis,
            List<Map<String, Object>> activeTransactions,
            List<Map<String, Object>> serviceCpuUsage) {
        Map<String, Object> dominanceAnalysis = new LinkedHashMap<>();
        Map<String, Map<String, Object>> businessIndex = new LinkedHashMap<>();
        Map<String, Integer> externalWaitByBusiness = new HashMap<>();
        Map<String, ServiceElapsedStats> serviceStats = new HashMap<>();
        Map<String, Double> cpuShareByBusiness = new HashMap<>();

        Map<String, Object> cards = castMap(analysis.get("cards"));
        double aggregateBusyRatio = toDouble(cards.get("threadBusyRatio"));
        int totalActive = 0;

        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                Map<String, Object> target = (Map<String, Object>) targetRow;
                String businessCode = stringValue(target.get("businessCode"));
                Map<String, Object> status = castMap(target.get("status"));
                Map<String, Object> summary = castMap(status.get("summary"));
                Map<String, Object> thread = castMap(status.get("thread"));

                int activeCount = (int) toLong(summary.get("activeTransactionCount"));
                totalActive += activeCount;

                Map<String, Object> metrics = businessIndex.computeIfAbsent(businessCode, code -> {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("businessCode", code);
                    return row;
                });
                metrics.put("activeTransactions", activeCount);
                metrics.put("slowTransactionCount", toLong(summary.get("slowTransactionCount")));
                metrics.put("slowSqlCount", toLong(summary.get("slowSqlCount")));
                metrics.put("sharedThreadBusyRatio", thread.get("busyRatio"));

                int dbActive = 0;
                int dbPending = 0;
                for (Map<String, Object> pool : castList(status.get("dbPools"))) {
                    dbActive += (int) toLong(pool.get("active"));
                    dbPending += (int) toLong(pool.get("pending"));
                }
                metrics.put("dbActive", dbActive);
                metrics.put("dbPending", dbPending);
            }
        }

        for (Map<String, Object> tx : activeTransactions) {
            String businessCode = stringValue(tx.get("businessCode"));
            if (businessCode != null && "WAIT_EXTERNAL".equals(stringValue(tx.get("currentStep")))) {
                externalWaitByBusiness.merge(businessCode, 1, Integer::sum);
            }
            String serviceId = stringValue(tx.get("serviceId"));
            if (serviceId == null) {
                continue;
            }
            ServiceElapsedStats stats = serviceStats.computeIfAbsent(serviceId, key -> new ServiceElapsedStats());
            stats.businessCode = businessCode;
            stats.count++;
            long elapsed = toLong(tx.get("elapsedMs"));
            stats.sumElapsed += elapsed;
            stats.maxElapsed = Math.max(stats.maxElapsed, elapsed);
        }

        for (Map<String, Object> cpu : serviceCpuUsage) {
            String businessCode = stringValue(cpu.get("businessCode"));
            if (businessCode != null) {
                cpuShareByBusiness.merge(businessCode, toDouble(cpu.get("cpuSharePct")), Double::sum);
            }
        }

        List<Map<String, Object>> businessMetrics = new ArrayList<>();
        for (Map<String, Object> metrics : businessIndex.values()) {
            String businessCode = stringValue(metrics.get("businessCode"));
            int activeCount = (int) toLong(metrics.get("activeTransactions"));
            double ownershipPct = totalActive > 0 ? round1(activeCount * 100.0 / totalActive) : 0;
            metrics.put("ownershipPct", ownershipPct);
            metrics.put("busyThreadOccupancy", activeCount);
            metrics.put("externalWaitCount", externalWaitByBusiness.getOrDefault(businessCode, 0));
            metrics.put("timeoutCount", null);
            metrics.put("threadCpuSharePct", round1(cpuShareByBusiness.getOrDefault(businessCode, 0.0)));

            ServiceElapsedStats businessElapsed = aggregateElapsedForBusiness(businessCode, serviceStats);
            metrics.put("avgElapsedMs", businessElapsed.count > 0
                    ? Math.round(businessElapsed.sumElapsed / businessElapsed.count) : 0);
            metrics.put("maxElapsedMs", businessElapsed.maxElapsed);
            businessMetrics.add(metrics);
        }
        businessMetrics.sort(Comparator.comparingDouble(
                (Map<String, Object> row) -> toDouble(row.get("ownershipPct"))).reversed());

        List<Map<String, Object>> serviceOwnership = new ArrayList<>();
        for (Map.Entry<String, ServiceElapsedStats> entry : serviceStats.entrySet()) {
            ServiceElapsedStats stats = entry.getValue();
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("serviceId", entry.getKey());
            row.put("businessCode", stats.businessCode);
            row.put("activeCount", stats.count);
            row.put("ownershipPct", totalActive > 0 ? round1(stats.count * 100.0 / totalActive) : 0);
            row.put("avgElapsedMs", stats.count > 0 ? Math.round(stats.sumElapsed / stats.count) : 0);
            row.put("maxElapsedMs", stats.maxElapsed);
            serviceOwnership.add(row);
        }
        serviceOwnership.sort(Comparator.comparingDouble(
                (Map<String, Object> row) -> toDouble(row.get("ownershipPct"))).reversed());

        String dominantBusiness = stringValue(analysis.get("dominantBusinessCode"));
        double dominantBusinessPct = toDouble(cards.get("dominantBusinessOwnershipPct"));
        boolean businessCondition = dominantBusinessPct >= 60 && aggregateBusyRatio >= 80;
        String primaryCause = stringValue(analysis.get("primaryCauseCode"));
        boolean primaryBusiness = "BUSINESS_RESOURCE_DOMINANCE".equals(primaryCause)
                || "BUSINESS_DOMINANCE".equals(primaryCause);

        Map<String, Object> businessDominance = new LinkedHashMap<>();
        businessDominance.put("detected", businessCondition || primaryBusiness);
        businessDominance.put("primaryCauseCode", primaryBusiness || businessCondition
                ? "BUSINESS_RESOURCE_DOMINANCE" : "NORMAL");
        businessDominance.put("message", primaryBusiness
                ? stringValue(analysis.get("primaryMessage"))
                : (businessCondition
                ? String.format(
                        "%s 업무가 현재 전체 실행 거래의 %s%%를 점유하고 있습니다.",
                        dominantBusiness,
                        format1(dominantBusinessPct))
                : "업무 자원 독점 징후 없음"));
        businessDominance.put("criteria", "ownershipPct >= 60 AND threadBusyRatio >= 80");
        businessDominance.put("dominantBusinessCode", dominantBusiness);
        businessDominance.put("ownershipPct", dominantBusinessPct);
        businessDominance.put("threadBusyRatio", aggregateBusyRatio);
        businessDominance.put("totalActiveTransactions", totalActive);
        int dominantBusinessActive = 0;
        if (dominantBusiness != null) {
            Map<String, Object> dominantMetrics = businessIndex.get(dominantBusiness);
            if (dominantMetrics != null) {
                dominantBusinessActive = (int) toLong(dominantMetrics.get("activeTransactions"));
            }
        }
        businessDominance.put("screenMessage", businessCondition || primaryBusiness
                ? String.format(
                        "전체 실행 거래: %d건%n %s 실행 거래: %d건%n %s Thread 점유율: %s%%%nTomcat Busy Thread Ratio: %s%%",
                        totalActive,
                        dominantBusiness,
                        dominantBusinessActive,
                        dominantBusiness,
                        format1(dominantBusinessPct),
                        format1(aggregateBusyRatio))
                : "업무 독점 기준 미충족");

        String dominantService = stringValue(analysis.get("dominantServiceId"));
        double dominantServicePct = 0;
        int dominantServiceCount = 0;
        for (Map<String, Object> row : serviceOwnership) {
            if (dominantService != null && dominantService.equals(stringValue(row.get("serviceId")))) {
                dominantServicePct = toDouble(row.get("ownershipPct"));
                dominantServiceCount = (int) toLong(row.get("activeCount"));
                break;
            }
        }
        boolean serviceCondition = dominantServicePct >= 40;
        boolean primaryService = "SERVICE_DOMINANCE".equals(primaryCause);

        Map<String, Object> serviceDominance = new LinkedHashMap<>();
        serviceDominance.put("detected", serviceCondition || primaryService);
        serviceDominance.put("primaryCauseCode", primaryService || serviceCondition ? "SERVICE_DOMINANCE" : "NORMAL");
        serviceDominance.put("message", primaryService
                ? stringValue(analysis.get("primaryMessage"))
                : (serviceCondition
                ? String.format(
                        "%s 거래가 현재 Tomcat 처리량의 %s%%를 점유하고 있습니다.",
                        dominantService,
                        format1(dominantServicePct))
                : "ServiceId 독점 징후 없음"));
        serviceDominance.put("criteria", "serviceId activeCount / totalActive >= 40%");
        serviceDominance.put("dominantServiceId", dominantService);
        serviceDominance.put("ownershipPct", dominantServicePct);
        serviceDominance.put("activeCount", dominantServiceCount);
        serviceDominance.put("totalActiveTransactions", totalActive);
        serviceDominance.put("screenMessage", serviceCondition || primaryService
                ? String.format(
                        "%s%n 실행 중: %d건%n 전체 거래: %d건%n 점유율: %s%%",
                        dominantService,
                        dominantServiceCount,
                        totalActive,
                        format1(dominantServicePct))
                : "ServiceId 독점 기준 미충족");

        dominanceAnalysis.put("businessMetrics", businessMetrics);
        dominanceAnalysis.put("serviceOwnership", serviceOwnership);
        dominanceAnalysis.put("businessDominance", businessDominance);
        dominanceAnalysis.put("serviceDominance", serviceDominance);
        dominanceAnalysis.put("aggregateBusyRatio", aggregateBusyRatio);
        dominanceAnalysis.put("totalActiveTransactions", totalActive);
        dominanceAnalysis.put("scopeNote",
                "동일 JVM — WAR별 Heap 분리 불가 · Active Transaction·Thread·DB·SQL 지표로 독점 추정");
        return dominanceAnalysis;
    }

    private Map<String, Object> buildTransactionDetailAnalysis(
            List<Map<String, Object>> slowTransactions,
            List<Map<String, Object>> activeTransactions,
            List<Map<String, Object>> slowSql,
            List<Map<String, Object>> threads) {
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("slowTransactions", detailSection(
                "12.2",
                "GET /internal/runtime/slow-transactions?limit=50",
                "최근 Slow 거래",
                slowTransactions));
        detail.put("activeTransactions", detailSection(
                "12.3",
                "GET /internal/runtime/active-transactions",
                "현재 실행 중 거래",
                activeTransactions));
        detail.put("slowSql", detailSection(
                "12.4",
                "GET /internal/runtime/slow-sql?limit=50",
                "최근 Slow SQL",
                slowSql));
        detail.put("threads", detailSection(
                "12.5",
                "GET /internal/runtime/threads",
                "TCF 거래 연계 Thread",
                threads));
        detail.put("scopeNote",
                "Thread Dump 전체가 아닙니다. ActiveTransactionRegistry에 등록된 TCF 거래 Thread만 반환합니다.");
        return detail;
    }

    private static Map<String, Object> detailSection(
            String section, String api, String title, List<Map<String, Object>> rows) {
        Map<String, Object> block = new LinkedHashMap<>();
        block.put("section", section);
        block.put("api", api);
        block.put("title", title);
        block.put("count", rows.size());
        block.put("rows", rows);
        return block;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildCauseAnalysis(
            Map<String, Object> collected,
            Map<String, Object> analysis,
            List<Map<String, Object>> activeTransactions) {
        Map<String, Object> causeAnalysis = new LinkedHashMap<>();
        Map<String, Object> cards = castMap(analysis.get("cards"));
        String primaryCauseCode = stringValue(analysis.get("primaryCauseCode"));
        String normalizedPrimary = normalizeCauseCode(primaryCauseCode);

        boolean anyDeadlock = false;
        boolean anyDbPoolExhausted = false;
        double maxHeapRatio = 0;
        long maxGcTimeMs = 0;
        double maxCpu = toDouble(cards.get("jvmCpuRatio"));
        double maxBusy = toDouble(cards.get("threadBusyRatio"));
        int slowTx = (int) toLong(cards.get("slowTransactionCount"));
        int slowSql = (int) toLong(cards.get("slowSqlCount"));
        int dbPending = (int) toLong(cards.get("dbPending"));
        int externalWait = 0;

        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                Map<String, Object> status = castMap(((Map<String, Object>) targetRow).get("status"));
                Map<String, Object> thread = castMap(status.get("thread"));
                Map<String, Object> jvm = castMap(status.get("jvm"));
                if (Boolean.TRUE.equals(thread.get("deadlock"))) {
                    anyDeadlock = true;
                }
                maxHeapRatio = Math.max(maxHeapRatio, toDouble(jvm.get("heapRatio")));
                maxGcTimeMs = Math.max(maxGcTimeMs, toLong(jvm.get("gcTimeLastMinuteMs")));
                for (Map<String, Object> pool : castList(status.get("dbPools"))) {
                    int maximum = (int) toLong(pool.get("maximum"));
                    int active = (int) toLong(pool.get("active"));
                    int idle = (int) toLong(pool.get("idle"));
                    int pending = (int) toLong(pool.get("pending"));
                    if (maximum > 0 && active >= maximum && idle == 0 && pending > 0) {
                        anyDbPoolExhausted = true;
                    }
                }
            }
        }

        for (Map<String, Object> tx : activeTransactions) {
            if ("WAIT_EXTERNAL".equals(stringValue(tx.get("currentStep")))) {
                externalWait++;
            }
        }

        double businessOwnership = toDouble(cards.get("dominantBusinessOwnershipPct"));
        String dominantBusiness = stringValue(cards.get("dominantBusinessCode"));
        double serviceOwnership = 0;
        String dominantService = stringValue(analysis.get("dominantServiceId"));
        if (dominantService != null) {
            int totalActive = 0;
            int serviceActive = 0;
            for (Map<String, Object> tx : activeTransactions) {
                totalActive++;
                if (dominantService.equals(stringValue(tx.get("serviceId")))) {
                    serviceActive++;
                }
            }
            if (totalActive > 0) {
                serviceOwnership = round1(serviceActive * 100.0 / totalActive);
            }
        }

        boolean gcPressure = maxHeapRatio >= 80 && maxGcTimeMs >= 3000;
        boolean cpuOverload = maxCpu >= 90;
        boolean threadSaturation = maxBusy >= 85;
        boolean slowSqlDetected = slowSql >= 3;
        boolean externalWaitDetected = externalWait >= 3;
        boolean businessDominance = businessOwnership >= 60;
        boolean serviceDominance = serviceOwnership >= 40;
        boolean unknownFallback = "UNKNOWN".equals(primaryCauseCode);
        boolean normal = "NORMAL".equals(primaryCauseCode);

        List<Map<String, Object>> priorities = List.of(
                priorityRow(1, "Deadlock"),
                priorityRow(2, "DB Pool 고갈"),
                priorityRow(3, "GC 압박"),
                priorityRow(4, "CPU 과부하"),
                priorityRow(5, "Tomcat Thread 포화"),
                priorityRow(6, "Slow SQL"),
                priorityRow(7, "외부 연계 지연"),
                priorityRow(8, "특정 업무·ServiceId 독점"),
                priorityRow(9, "원인 불명"));

        List<Map<String, Object>> causeTable = new ArrayList<>();
        causeTable.add(causeRow(
                1, "Deadlock 존재", "THREAD_DEADLOCK",
                "Thread Deadlock이 발견되었습니다.",
                anyDeadlock, normalizedPrimary, anyDeadlock ? "thread.deadlock=true" : "-"));
        causeTable.add(causeRow(
                2, "Active=Max, Idle=0, Pending>0", "DB_POOL_EXHAUSTED",
                "DB Connection 대기가 발생했습니다.",
                anyDbPoolExhausted, normalizedPrimary,
                anyDbPoolExhausted ? String.format("Pending %d", dbPending) : "-"));
        causeTable.add(causeRow(
                3, "Heap≥80%, GC 시간 증가", "GC_PRESSURE",
                "GC 증가로 JVM 응답이 지연되고 있습니다.",
                gcPressure, normalizedPrimary,
                gcPressure ? String.format("Heap %s%%, GC %sms", format1(maxHeapRatio), maxGcTimeMs) : "-"));
        causeTable.add(causeRow(
                4, "CPU≥90%", "CPU_OVERLOAD",
                "JVM CPU가 과부하 상태입니다.",
                cpuOverload, normalizedPrimary,
                cpuOverload ? String.format("CPU %s%%, DB Pending %d", format1(maxCpu), dbPending) : "-"));
        causeTable.add(causeRow(
                5, "Busy≥85%", "THREAD_SATURATION",
                "Tomcat Thread가 포화 상태입니다.",
                threadSaturation, normalizedPrimary,
                threadSaturation ? String.format("Busy %s%%, Slow TX %d", format1(maxBusy), slowTx) : "-"));
        causeTable.add(causeRow(
                6, "Slow SQL 다수", "SLOW_SQL",
                "특정 Mapper SQL이 장시간 실행 중입니다.",
                slowSqlDetected, normalizedPrimary,
                slowSqlDetected ? String.format("Slow SQL %d건 (1분)", slowSql) : "-"));
        causeTable.add(causeRow(
                7, "WAIT_EXTERNAL 다수", "EXTERNAL_WAIT",
                "외부 시스템 응답을 기다리고 있습니다.",
                externalWaitDetected, normalizedPrimary,
                externalWaitDetected ? String.format("WAIT_EXTERNAL %d건", externalWait) : "-"));
        causeTable.add(causeRow(
                8, "업무 점유율≥60%", "BUSINESS_DOMINANCE",
                "특정 업무가 실행 자원을 과다 점유 중입니다.",
                businessDominance, normalizedPrimary,
                businessDominance ? String.format("%s %s%%", dominantBusiness, format1(businessOwnership)) : "-"));
        causeTable.add(causeRow(
                8, "ServiceId 점유율≥40%", "SERVICE_DOMINANCE",
                "특정 ServiceId가 자원을 과다 점유 중입니다.",
                serviceDominance, normalizedPrimary,
                serviceDominance ? String.format("%s %s%%", dominantService, format1(serviceOwnership)) : "-"));
        causeTable.add(causeRow(
                9, "조건 불충분", "UNKNOWN",
                "추가 로그와 DB 상태 확인이 필요합니다.",
                unknownFallback, normalizedPrimary,
                unknownFallback ? "경계 징후 존재, 명확한 원인 미확정" : "-"));

        if (normal) {
            Map<String, Object> normalRow = new LinkedHashMap<>();
            normalRow.put("priority", 10);
            normalRow.put("condition", "이상 없음");
            normalRow.put("causeCode", "NORMAL");
            normalRow.put("message", "현재 주요 병목이 없습니다.");
            normalRow.put("detected", true);
            normalRow.put("primary", true);
            normalRow.put("evidence", "-");
            causeTable.add(normalRow);
        }

        causeAnalysis.put("priorities", priorities);
        causeAnalysis.put("causeTable", causeTable);
        causeAnalysis.put("primaryCauseCode", primaryCauseCode);
        causeAnalysis.put("primaryMessage", analysis.get("primaryMessage"));
        causeAnalysis.put("overallStatus", analysis.get("overallStatus"));
        causeAnalysis.put("warFindings", analysis.get("findings"));
        causeAnalysis.put("note",
                "WAR 1~7번은 각 WAR summary 중 최우선 Cause 채택 · 8번 독점은 1~7 미해당 시 tcf-om 적용");
        return causeAnalysis;
    }

    private static Map<String, Object> priorityRow(int rank, String label) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("rank", rank);
        row.put("label", label);
        return row;
    }

    private static Map<String, Object> causeRow(
            int priority,
            String condition,
            String causeCode,
            String message,
            boolean detected,
            String primaryCauseCode,
            String evidence) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("priority", priority);
        row.put("condition", condition);
        row.put("causeCode", causeCode);
        row.put("message", message);
        row.put("detected", detected);
        row.put("primary", causeCode.equals(primaryCauseCode)
                || ("BUSINESS_DOMINANCE".equals(causeCode) && "BUSINESS_RESOURCE_DOMINANCE".equals(primaryCauseCode)));
        row.put("evidence", evidence);
        return row;
    }

    private static String normalizeCauseCode(String causeCode) {
        if ("BUSINESS_RESOURCE_DOMINANCE".equals(causeCode)) {
            return "BUSINESS_DOMINANCE";
        }
        return causeCode;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildStatusCardsAnalysis(
            Map<String, Object> collected,
            Map<String, Object> analysis) {
        Map<String, Object> statusCards = new LinkedHashMap<>();
        Map<String, Object> cards = castMap(analysis.get("cards"));

        int threadBusy = 0;
        int threadMax = 0;
        double threadBusyRatio = toDouble(cards.get("threadBusyRatio"));
        double jvmCpu = toDouble(cards.get("jvmCpuRatio"));
        double heapRatio = toDouble(cards.get("heapRatio"));
        long gcTimeMs = 0;

        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                Map<String, Object> status = castMap(((Map<String, Object>) targetRow).get("status"));
                Map<String, Object> thread = castMap(status.get("thread"));
                Map<String, Object> jvm = castMap(status.get("jvm"));
                if (threadMax == 0 && toLong(thread.get("max")) > 0) {
                    threadMax = (int) toLong(thread.get("max"));
                    threadBusy = (int) toLong(thread.get("busy"));
                }
                gcTimeMs = Math.max(gcTimeMs, toLong(jvm.get("gcTimeLastMinuteMs")));
            }
        }

        int dbActive = (int) toLong(cards.get("dbActive"));
        int dbMaximum = (int) toLong(cards.get("dbMaximum"));
        int dbPending = (int) toLong(cards.get("dbPending"));
        int slowTx = (int) toLong(cards.get("slowTransactionCount"));
        int slowSql = (int) toLong(cards.get("slowSqlCount"));
        String dominantBusiness = stringValue(cards.get("dominantBusinessCode"));
        double dominantPct = toDouble(cards.get("dominantBusinessOwnershipPct"));

        boolean gcPressure = heapRatio >= 80 && gcTimeMs >= 3000;
        boolean dbExhausted = dbMaximum > 0 && dbActive >= dbMaximum && dbPending > 0;

        String threadDisplay = threadMax > 0
                ? String.format("%d / %d, %s%%", threadBusy, threadMax, format1(threadBusyRatio))
                : String.format("- / -, %s%%", format1(threadBusyRatio));
        String gcDisplay = gcPressure
                ? String.format("압박 (%s초)", format1(gcTimeMs / 1000.0))
                : "정상";
        String dbDisplay = String.format("%d / %d, Pending %d", dbActive, dbMaximum, dbPending);
        String businessDisplay = dominantBusiness != null && !dominantBusiness.isBlank()
                ? String.format("%s %s%% 점유", dominantBusiness, format1(dominantPct))
                : "-";

        List<Map<String, Object>> cardList = new ArrayList<>();
        cardList.add(statusCard("thread", "Thread", threadDisplay,
                threadBusyRatio >= 85 ? "warn" : "normal"));
        cardList.add(statusCard("jvmCpu", "JVM CPU", format1(jvmCpu) + "%",
                jvmCpu >= 90 ? "warn" : "normal"));
        cardList.add(statusCard("heap", "Heap", format1(heapRatio) + "%",
                heapRatio >= 80 ? "warn" : "normal"));
        cardList.add(statusCard("gc", "GC", gcDisplay, gcPressure ? "warn" : "normal"));
        cardList.add(statusCard("dbPool", "DB Pool", dbDisplay,
                dbExhausted ? "critical" : (dbPending > 0 ? "warn" : "normal")));
        cardList.add(statusCard("slowTransaction", "Slow 거래", slowTx + "건",
                slowTx >= 5 ? "warn" : (slowTx > 0 ? "info" : "normal")));
        cardList.add(statusCard("slowSql", "Slow SQL", slowSql + "건",
                slowSql >= 3 ? "warn" : (slowSql > 0 ? "info" : "normal")));
        cardList.add(statusCard("dominantBusiness", "주요 업무", businessDisplay,
                dominantPct >= 60 ? "warn" : "normal"));

        statusCards.put("cards", cardList);
        statusCards.put("overallStatus", analysis.get("overallStatus"));
        statusCards.put("primaryCauseCode", analysis.get("primaryCauseCode"));
        statusCards.put("primaryMessage", analysis.get("primaryMessage"));
        statusCards.put("scopeNote",
                "Tomcat 공유 Thread·JVM · DB Pool·Slow 지표는 전 WAR 중 max·합산 기준");
        return statusCards;
    }

    private Map<String, Object> buildActiveTransactionListAnalysis(
            List<Map<String, Object>> activeTransactions) {
        Map<String, Object> listAnalysis = new LinkedHashMap<>();
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Map<String, Object> tx : activeTransactions) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("businessCode", tx.get("businessCode"));
            row.put("serviceId", tx.get("serviceId"));
            row.put("elapsedMs", tx.get("elapsedMs"));
            row.put("elapsedDisplay", formatElapsedDisplay(toLong(tx.get("elapsedMs"))));
            row.put("currentStep", tx.get("currentStep"));
            row.put("stepLabel", formatStepLabel(stringValue(tx.get("currentStep"))));
            row.put("sqlOrExternal", formatSqlOrExternal(tx));
            rows.add(row);
        }
        listAnalysis.put("section", "15.4");
        listAnalysis.put("title", "실행 중 거래 목록");
        listAnalysis.put("api", "GET /internal/runtime/active-transactions");
        listAnalysis.put("count", rows.size());
        listAnalysis.put("rows", rows);
        return listAnalysis;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildBusinessOccupancyAnalysis(
            Map<String, Object> collected,
            Map<String, Object> analysis) {
        Map<String, Object> occupancy = new LinkedHashMap<>();
        Map<String, Map<String, Object>> byBusiness = new LinkedHashMap<>();
        int totalActive = 0;

        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                Map<String, Object> target = (Map<String, Object>) targetRow;
                String businessCode = stringValue(target.get("businessCode"));
                if (businessCode == null || businessCode.isBlank()) {
                    continue;
                }
                Map<String, Object> status = castMap(target.get("status"));
                Map<String, Object> summary = castMap(status.get("summary"));

                int activeCount = (int) toLong(summary.get("activeTransactionCount"));
                int slowTx = (int) toLong(summary.get("slowTransactionCount"));
                int slowSql = (int) toLong(summary.get("slowSqlCount"));
                totalActive += activeCount;

                int dbActive = 0;
                int dbMaximum = 0;
                for (Map<String, Object> pool : castList(status.get("dbPools"))) {
                    dbActive += (int) toLong(pool.get("active"));
                    dbMaximum = Math.max(dbMaximum, (int) toLong(pool.get("maximum")));
                }

                Map<String, Object> row = byBusiness.computeIfAbsent(businessCode, code -> {
                    Map<String, Object> created = new LinkedHashMap<>();
                    created.put("businessCode", code);
                    return created;
                });
                row.put("activeTransactions", activeCount);
                row.put("dbActive", dbActive);
                row.put("dbMaximum", dbMaximum);
                row.put("dbDisplay", dbMaximum > 0 ? dbActive + "/" + dbMaximum : String.valueOf(dbActive));
                row.put("slowTransactionCount", slowTx);
                row.put("slowSqlCount", slowSql);
                row.put("slowCount", slowTx + slowSql);
            }
        }

        List<Map<String, Object>> rows = new ArrayList<>();
        for (Map<String, Object> row : byBusiness.values()) {
            int activeCount = (int) toLong(row.get("activeTransactions"));
            double ownershipPct = totalActive > 0 ? round1(activeCount * 100.0 / totalActive) : 0;
            row.put("ownershipPct", ownershipPct);
            rows.add(row);
        }
        rows.sort(Comparator.comparingDouble(
                (Map<String, Object> row) -> toDouble(row.get("ownershipPct"))).reversed());

        occupancy.put("section", "15.5");
        occupancy.put("title", "업무 점유 현황");
        occupancy.put("totalActiveTransactions", totalActive);
        occupancy.put("count", rows.size());
        occupancy.put("rows", rows);
        occupancy.put("scopeNote", "실행 거래·점유율은 ActiveTransactionRegistry · Slow는 WAR별 최근 1분 집계");
        return occupancy;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> buildIncidentFlowAnalysis(
            Map<String, Object> collected,
            Map<String, Object> analysis,
            List<Map<String, Object>> activeTransactions,
            List<Map<String, Object>> slowSql,
            List<Map<String, Object>> serviceCpuUsage) {
        Map<String, Object> flowAnalysis = new LinkedHashMap<>();
        Map<String, Object> cards = castMap(analysis.get("cards"));
        String primaryCause = stringValue(analysis.get("primaryCauseCode"));

        int waitDbCount = 0;
        int executingSqlCount = 0;
        int waitExternalCount = 0;
        long maxExecutingElapsed = 0;
        Map<String, Integer> sqlHits = new HashMap<>();
        Map<String, Integer> externalHits = new HashMap<>();

        for (Map<String, Object> tx : activeTransactions) {
            String step = stringValue(tx.get("currentStep"));
            if ("WAIT_DB_CONNECTION".equals(step)) {
                waitDbCount++;
            } else if ("EXECUTING_SQL".equals(step)) {
                executingSqlCount++;
                maxExecutingElapsed = Math.max(maxExecutingElapsed, toLong(tx.get("elapsedMs")));
                String sqlKey = shortenSqlLabel(stringValue(tx.get("mapperSql")), stringValue(tx.get("sqlId")));
                if (sqlKey != null && !"-".equals(sqlKey)) {
                    sqlHits.merge(sqlKey, 1, Integer::sum);
                }
            } else if ("WAIT_EXTERNAL".equals(step)) {
                waitExternalCount++;
                String external = stringValue(tx.get("externalSystemCode"));
                if (external != null && !external.isBlank()) {
                    externalHits.merge(external, 1, Integer::sum);
                }
            }
        }
        for (Map<String, Object> sql : slowSql) {
            String sqlKey = shortenSqlLabel(stringValue(sql.get("mapperSql")), stringValue(sql.get("sqlId")));
            if (sqlKey != null && !"-".equals(sqlKey)) {
                sqlHits.merge(sqlKey, 1, Integer::sum);
            }
        }

        int topSqlRepeat = sqlHits.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        String topSqlId = sqlHits.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(stringValue(analysis.get("dominantSqlId")));
        int topExternalRepeat = externalHits.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        String topExternalCode = externalHits.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("-");

        int dbPending = (int) toLong(cards.get("dbPending"));
        int dbActive = (int) toLong(cards.get("dbActive"));
        int dbMaximum = (int) toLong(cards.get("dbMaximum"));
        double busyRatio = toDouble(cards.get("threadBusyRatio"));
        double cpuRatio = toDouble(cards.get("jvmCpuRatio"));
        double heapRatio = toDouble(cards.get("heapRatio"));
        int slowSqlCount = (int) toLong(cards.get("slowSqlCount"));

        boolean poolExhausted = dbMaximum > 0 && dbActive >= dbMaximum && dbPending > 0;
        long maxGcTimeMs = 0;
        Object targetRows = collected.get("targets");
        if (targetRows instanceof List<?> list) {
            for (Object item : list) {
                if (!(item instanceof Map<?, ?> targetRow)) {
                    continue;
                }
                Map<String, Object> status = castMap(((Map<String, Object>) targetRow).get("status"));
                Map<String, Object> jvm = castMap(status.get("jvm"));
                maxGcTimeMs = Math.max(maxGcTimeMs, toLong(jvm.get("gcTimeLastMinuteMs")));
            }
        }
        boolean gcNormal = !(heapRatio >= 80 && maxGcTimeMs >= 3000);
        boolean dbPoolNormal = !poolExhausted && dbPending == 0;
        boolean cpuNormal = cpuRatio < 90;

        String topServiceCpu = "-";
        double topServiceCpuPct = 0;
        for (Map<String, Object> cpu : serviceCpuUsage) {
            double share = toDouble(cpu.get("cpuSharePct"));
            if (share > topServiceCpuPct) {
                topServiceCpuPct = share;
                topServiceCpu = stringValue(cpu.get("serviceId"));
            }
        }

        List<Map<String, Object>> flows = new ArrayList<>();

        List<Map<String, Object>> dbPoolSteps = List.of(
                flowStep(1, "Connection 부족", poolExhausted,
                        poolExhausted ? String.format("DB %d/%d", dbActive, dbMaximum) : "Pool 여유"),
                flowStep(2, "WAIT_DB_CONNECTION 증가", waitDbCount > 0,
                        waitDbCount + "건"),
                flowStep(3, "DB Pending 증가", dbPending > 0,
                        "Pending " + dbPending),
                flowStep(4, "Tomcat Busy Thread 증가", busyRatio >= 70,
                        "Busy " + format1(busyRatio) + "%"),
                flowStep(5, "tcf-om 판정: DB_POOL_EXHAUSTED",
                        "DB_POOL_EXHAUSTED".equals(primaryCause),
                        stringValue(analysis.get("primaryMessage"))));
        flows.add(flowScenario(
                "dbPool", "19.1", "DB Pool 장애", "DB_POOL_EXHAUSTED",
                poolExhausted || waitDbCount > 0 || dbPending > 0 || "DB_POOL_EXHAUSTED".equals(primaryCause),
                "DB_POOL_EXHAUSTED".equals(primaryCause),
                dbPoolSteps,
                List.of(
                        flowLink("DB Pool 분석", "/om/admin/runtime-dbpool-analysis.html"),
                        flowLink("실행 중 거래", "/om/admin/runtime-active-transactions.html"),
                        flowLink("자동 원인판정", "/om/admin/runtime-cause-analysis.html"))));

        boolean connectionNormal = !poolExhausted && dbPending == 0;
        List<Map<String, Object>> slowSqlSteps = List.of(
                flowStep(1, "Connection 정상 획득", connectionNormal,
                        connectionNormal ? "Pending 0" : "Pool 대기 존재"),
                flowStep(2, "EXECUTING_SQL 장시간 지속", executingSqlCount > 0 && maxExecutingElapsed >= 3000,
                        executingSqlCount + "건 · 최대 " + formatElapsedDisplay(maxExecutingElapsed)),
                flowStep(3, "동일 Mapper ID 반복", topSqlRepeat >= 2,
                        topSqlId + " × " + topSqlRepeat),
                flowStep(4, "Pool Active 증가", dbMaximum > 0 && dbActive * 100 / dbMaximum >= 70,
                        dbActive + "/" + dbMaximum),
                flowStep(5, "tcf-om 판정: SLOW_SQL",
                        "SLOW_SQL".equals(primaryCause),
                        stringValue(analysis.get("primaryMessage"))));
        flows.add(flowScenario(
                "slowSql", "19.2", "Slow SQL", "SLOW_SQL",
                slowSqlCount >= 3 || executingSqlCount > 0 || "SLOW_SQL".equals(primaryCause),
                "SLOW_SQL".equals(primaryCause),
                slowSqlSteps,
                List.of(
                        flowLink("SQL 분석", "/om/admin/runtime-sql-analysis.html"),
                        flowLink("DB Pool 분석", "/om/admin/runtime-dbpool-analysis.html"),
                        flowLink("거래·Thread 상세", "/om/admin/runtime-transaction-detail.html"),
                        flowLink("자동 원인판정", "/om/admin/runtime-cause-analysis.html"))));

        List<Map<String, Object>> externalSteps = List.of(
                flowStep(1, "DB Pool · CPU · GC 정상", dbPoolNormal && cpuNormal && gcNormal,
                        String.format("Pending %d · CPU %s%% · GC %s", dbPending, format1(cpuRatio), gcNormal ? "정상" : "압박")),
                flowStep(2, "WAIT_EXTERNAL 거래 증가", waitExternalCount >= 1,
                        waitExternalCount + "건"),
                flowStep(3, "특정 외부 시스템 코드 집중", topExternalRepeat >= 2 || waitExternalCount >= 3,
                        topExternalCode + (topExternalRepeat > 0 ? " × " + topExternalRepeat : "")),
                flowStep(4, "tcf-om 판정: EXTERNAL_WAIT",
                        "EXTERNAL_WAIT".equals(primaryCause),
                        stringValue(analysis.get("primaryMessage"))));
        flows.add(flowScenario(
                "externalWait", "19.3", "외부 시스템 지연", "EXTERNAL_WAIT",
                waitExternalCount > 0 || "EXTERNAL_WAIT".equals(primaryCause),
                "EXTERNAL_WAIT".equals(primaryCause),
                externalSteps,
                List.of(
                        flowLink("실행 중 거래", "/om/admin/runtime-active-transactions.html"),
                        flowLink("거래·Thread 상세", "/om/admin/runtime-transaction-detail.html"),
                        flowLink("자동 원인판정", "/om/admin/runtime-cause-analysis.html"))));

        List<Map<String, Object>> cpuSteps = List.of(
                flowStep(1, "DB Pending 없음 · 외부 대기 없음", dbPending == 0 && waitExternalCount == 0,
                        "Pending " + dbPending + " · EXTERNAL " + waitExternalCount),
                flowStep(2, "CPU 90% 이상", cpuRatio >= 90,
                        format1(cpuRatio) + "%"),
                flowStep(3, "특정 ServiceId Thread CPU 집중", topServiceCpuPct >= 25,
                        topServiceCpu + " · " + format1(topServiceCpuPct) + "%"),
                flowStep(4, "tcf-om 판정: CPU_OVERLOAD",
                        "CPU_OVERLOAD".equals(primaryCause),
                        stringValue(analysis.get("primaryMessage"))));
        flows.add(flowScenario(
                "cpuOverload", "19.4", "CPU 과부하", "CPU_OVERLOAD",
                cpuRatio >= 90 || "CPU_OVERLOAD".equals(primaryCause),
                "CPU_OVERLOAD".equals(primaryCause),
                cpuSteps,
                List.of(
                        flowLink("JVM 분석", "/om/admin/runtime-jvm-analysis.html"),
                        flowLink("Thread 분석", "/om/admin/runtime-thread-analysis.html"),
                        flowLink("자원 독점 분석", "/om/admin/runtime-dominance-analysis.html"),
                        flowLink("자동 원인판정", "/om/admin/runtime-cause-analysis.html"))));

        flowAnalysis.put("section", "19");
        flowAnalysis.put("title", "장애 흐름");
        flowAnalysis.put("primaryCauseCode", primaryCause);
        flowAnalysis.put("flows", flows);
        flowAnalysis.put("scopeNote", "각 단계는 현재 수집 지표로 활성(징후) 여부를 표시 · PRIMARY는 tcf-om 최종 판정");
        return flowAnalysis;
    }

    private static Map<String, Object> flowScenario(
            String id,
            String section,
            String title,
            String causeCode,
            boolean active,
            boolean primary,
            List<Map<String, Object>> steps,
            List<Map<String, Object>> links) {
        Map<String, Object> flow = new LinkedHashMap<>();
        flow.put("id", id);
        flow.put("section", section);
        flow.put("title", title);
        flow.put("causeCode", causeCode);
        flow.put("active", active);
        flow.put("primary", primary);
        flow.put("steps", steps);
        flow.put("links", links);
        return flow;
    }

    private static Map<String, Object> flowStep(int order, String label, boolean active, String detail) {
        Map<String, Object> step = new LinkedHashMap<>();
        step.put("order", order);
        step.put("label", label);
        step.put("active", active);
        step.put("detail", detail != null ? detail : "-");
        return step;
    }

    private static Map<String, Object> flowLink(String label, String href) {
        Map<String, Object> link = new LinkedHashMap<>();
        link.put("label", label);
        link.put("href", href);
        return link;
    }

    private static String formatStepLabel(String step) {
        if (step == null || step.isBlank()) {
            return "-";
        }
        return switch (step) {
            case "EXECUTING_SQL" -> "SQL 실행";
            case "WAIT_DB_CONNECTION" -> "DB 대기";
            case "WAIT_EXTERNAL" -> "외부 대기";
            case "SERVICE" -> "Service 처리";
            case "FACADE" -> "Facade 처리";
            case "HANDLER" -> "Handler 처리";
            case "RULE" -> "Rule 검증";
            case "STF" -> "STF 처리";
            case "WAIT_HANDLER" -> "Handler 대기";
            default -> step;
        };
    }

    private static String formatSqlOrExternal(Map<String, Object> tx) {
        String step = stringValue(tx.get("currentStep"));
        if ("WAIT_EXTERNAL".equals(step)) {
            String external = stringValue(tx.get("externalSystemCode"));
            return external != null && !external.isBlank() ? external : "-";
        }
        if ("WAIT_DB_CONNECTION".equals(step)) {
            return "-";
        }
        return shortenSqlLabel(stringValue(tx.get("mapperSql")), stringValue(tx.get("sqlId")));
    }

    private static String shortenSqlLabel(String mapperSql, String sqlId) {
        String candidate = mapperSql;
        if (candidate == null || candidate.isBlank() || "-".equals(candidate)) {
            candidate = sqlId;
        }
        if (candidate == null || candidate.isBlank()) {
            return "-";
        }
        int lastDot = candidate.lastIndexOf('.');
        return lastDot >= 0 ? candidate.substring(lastDot + 1) : candidate;
    }

    private static String formatElapsedDisplay(long elapsedMs) {
        if (elapsedMs >= 1000) {
            return format1(elapsedMs / 1000.0) + "초";
        }
        return elapsedMs + "ms";
    }

    private static Map<String, Object> statusCard(String id, String label, String display, String level) {
        Map<String, Object> card = new LinkedHashMap<>();
        card.put("id", id);
        card.put("label", label);
        card.put("display", display);
        card.put("level", level);
        return card;
    }

    private static ServiceElapsedStats aggregateElapsedForBusiness(
            String businessCode, Map<String, ServiceElapsedStats> serviceStats) {
        ServiceElapsedStats total = new ServiceElapsedStats();
        for (ServiceElapsedStats stats : serviceStats.values()) {
            if (businessCode != null && businessCode.equals(stats.businessCode)) {
                total.count += stats.count;
                total.sumElapsed += stats.sumElapsed;
                total.maxElapsed = Math.max(total.maxElapsed, stats.maxElapsed);
            }
        }
        return total;
    }

    private static double round1(double value) {
        return Math.round(value * 10) / 10.0;
    }

    private static final class ServiceElapsedStats {
        private String businessCode;
        private int count;
        private long sumElapsed;
        private long maxElapsed;
    }

    private static Map<String, Object> thresholdRow(String type, Integer designMs, long configuredMs) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("type", type);
        row.put("designThresholdMs", designMs);
        row.put("appliedThresholdMs", configuredMs);
        row.put("applied", designMs == null ? "ServiceId별 별도" : (designMs == configuredMs ? "동일" : "공통값"));
        return row;
    }

    private static int statusRank(String status) {
        if ("RUNNING".equals(status)) {
            return 0;
        }
        if ("FAILED".equals(status)) {
            return 1;
        }
        return 2;
    }

    private static String formatMapperSqlDisplay(String mapperId, String sqlId) {
        if (mapperId != null && !mapperId.isBlank()) {
            int lastDot = mapperId.lastIndexOf('.');
            if (lastDot > 0) {
                int secondLast = mapperId.lastIndexOf('.', lastDot - 1);
                if (secondLast >= 0) {
                    return mapperId.substring(secondLast + 1);
                }
            }
            return mapperId;
        }
        return sqlId != null && !sqlId.isBlank() ? sqlId : "-";
    }

    private static String buildExhaustionScreenMessage(
            String businessCode, Map<String, Object> pool, int pending) {
        String poolName = stringValue(pool.get("poolName"));
        int maximum = (int) toLong(pool.get("maximum"));
        int active = (int) toLong(pool.get("active"));
        int idle = (int) toLong(pool.get("idle"));
        return String.format(
                "%s %s%n  Max: %d / Active: %d / Idle: %d / Pending: %d%n현재 %s 거래 %d건이 DB Connection을 기다리고 있습니다.",
                businessCode,
                poolName != null ? poolName : "HikariPool",
                maximum,
                active,
                idle,
                pending,
                businessCode,
                pending);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> castList(Object value) {
        List<Map<String, Object>> rows = new ArrayList<>();
        if (!(value instanceof List<?> list)) {
            return rows;
        }
        for (Object item : list) {
            if (item instanceof Map<?, ?> map) {
                rows.add((Map<String, Object>) map);
            }
        }
        return rows;
    }

    private static String format1(double value) {
        return String.valueOf(Math.round(value * 10) / 10.0);
    }

    private Map<String, Object> extractSharedPool(Map<String, Object> thread) {
        Map<String, Object> pool = new LinkedHashMap<>();
        pool.put("max", thread.get("max"));
        pool.put("current", thread.get("current"));
        pool.put("busy", thread.get("busy"));
        pool.put("busyRatio", thread.get("busyRatio"));
        pool.put("blocked", thread.get("blocked"));
        pool.put("deadlock", thread.get("deadlock"));
        pool.put("maxSource", thread.get("maxSource"));
        pool.put("poolName", thread.get("poolName"));
        pool.put("scope", "tomcat-shared");
        return pool;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        if (value instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        return new LinkedHashMap<>();
    }

    private static String stringValue(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private static double toDouble(Object value) {
        if (value == null) {
            return 0;
        }
        return Double.parseDouble(String.valueOf(value));
    }

    private static long toLong(Object value) {
        if (value == null) {
            return 0;
        }
        return Long.parseLong(String.valueOf(value));
    }
}
