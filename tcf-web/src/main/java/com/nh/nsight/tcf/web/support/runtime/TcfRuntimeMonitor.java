package com.nh.nsight.tcf.web.support.runtime;

import com.nh.nsight.tcf.core.support.runtime.ActiveTransactionRegistry;
import com.nh.nsight.tcf.core.support.runtime.SlowSqlTracker;
import com.nh.nsight.tcf.core.support.runtime.SlowTransactionTracker;
import com.nh.nsight.tcf.core.support.runtime.model.ActiveTransactionInfo;
import com.nh.nsight.tcf.core.support.runtime.model.SlowSqlInfo;
import com.nh.nsight.tcf.core.support.runtime.model.SlowTransactionInfo;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TcfRuntimeMonitor {
    private final ActiveTransactionRegistry registry;
    private final SlowTransactionTracker slowTransactionTracker;
    private final SlowSqlTracker slowSqlTracker;
    private final DataSource dataSource;
    private final String applicationName;
    private final int serverPort;
    private final String businessCode;
    private final String version;
    private final long gcWindowStartMillis = System.currentTimeMillis();
    private long lastGcCount;
    private long lastGcTimeMs;

    public TcfRuntimeMonitor(
            ActiveTransactionRegistry registry,
            SlowTransactionTracker slowTransactionTracker,
            SlowSqlTracker slowSqlTracker,
            DataSource dataSource,
            @Value("${spring.application.name:nsight-tcf}") String applicationName,
            @Value("${server.port:8080}") int serverPort,
            @Value("${nsight.tcf.runtime.business-code:}") String businessCode,
            org.springframework.beans.factory.ObjectProvider<BuildProperties> buildPropertiesProvider) {
        this.registry = registry;
        this.slowTransactionTracker = slowTransactionTracker;
        this.slowSqlTracker = slowSqlTracker;
        this.dataSource = dataSource;
        this.applicationName = applicationName;
        this.serverPort = serverPort;
        this.businessCode = resolveBusinessCode(applicationName, businessCode);
        this.version = buildPropertiesProvider.getIfAvailable() != null
                ? buildPropertiesProvider.getIfAvailable().getVersion()
                : "dev";
        resetGcBaseline();
    }

    public Map<String, Object> createStatusSnapshot() {
        Map<String, Object> snapshot = new LinkedHashMap<>();
        snapshot.put("instance", buildInstance());
        snapshot.put("summary", buildSummary());
        snapshot.put("thread", collectThreadStatus());
        snapshot.put("jvm", collectJvmStatus());
        snapshot.put("dbPools", collectDbPoolStatus());
        snapshot.put("activeTransactions", toActiveTransactionRows(registry.snapshot(), 50));
        snapshot.put("slowTransactions", toSlowTransactionRows(slowTransactionTracker.snapshot(20)));
        snapshot.put("slowSql", toSlowSqlRows(slowSqlTracker.snapshot(20)));
        return snapshot;
    }

    public List<Map<String, Object>> getActiveTransactions() {
        return toActiveTransactionRows(registry.snapshot(), 200);
    }

    public List<Map<String, Object>> getSlowTransactions(int limit) {
        return toSlowTransactionRows(slowTransactionTracker.snapshot(limit));
    }

    public List<Map<String, Object>> getSlowSqlList(int limit) {
        return toSlowSqlRows(slowSqlTracker.snapshot(limit));
    }

    public List<Map<String, Object>> getThreadDetails() {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (ActiveTransactionInfo info : registry.snapshot()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("threadName", info.threadName());
            row.put("businessCode", info.businessCode());
            row.put("serviceId", info.serviceId());
            row.put("elapsedMs", info.elapsedMillis());
            row.put("currentStep", info.currentStep() == null ? null : info.currentStep().name());
            row.put("sqlId", info.currentSqlId());
            row.put("externalSystemCode", info.externalSystemCode());
            rows.add(row);
        }
        return rows;
    }

    private Map<String, Object> buildInstance() {
        Map<String, Object> instance = new LinkedHashMap<>();
        instance.put("hostName", resolveHostName());
        instance.put("port", serverPort);
        instance.put("applicationName", applicationName);
        instance.put("businessCode", businessCode);
        instance.put("version", version);
        return instance;
    }

    private Map<String, Object> buildSummary() {
        Map<String, Object> thread = collectThreadStatus();
        Map<String, Object> jvm = collectJvmStatus();
        List<Map<String, Object>> pools = collectDbPoolStatus();
        double busyRatio = toDouble(thread.get("busyRatio"));
        double heapRatio = toDouble(jvm.get("heapRatio"));
        double processCpu = toDouble(jvm.get("processCpuRatio"));
        boolean deadlock = Boolean.TRUE.equals(thread.get("deadlock"));
        int pending = sumPending(pools);
        int activeTx = registry.count();
        long oneMinuteAgo = System.currentTimeMillis() - 60_000L;
        int slowTx = slowTransactionTracker.countRecent(oneMinuteAgo);
        int slowSql = slowSqlTracker.countRecent(oneMinuteAgo);

        String causeCode = "NORMAL";
        String message = "현재 주요 병목이 없습니다.";
        String status = "NORMAL";

        if (deadlock) {
            causeCode = "THREAD_DEADLOCK";
            message = "Thread Deadlock가 발견되었습니다.";
            status = "CRITICAL";
        } else if (isPoolExhausted(pools)) {
            causeCode = "DB_POOL_EXHAUSTED";
            message = "DB Connection 대기가 발생하고 있습니다.";
            status = "CRITICAL";
        } else if (heapRatio >= 80 && toLong(jvm.get("gcTimeLastMinuteMs")) >= 3000) {
            causeCode = "GC_PRESSURE";
            message = "GC 증가로 JVM 응답이 지연되고 있습니다.";
            status = "WARN";
        } else if (processCpu >= 90 && pending == 0) {
            causeCode = "CPU_OVERLOAD";
            message = "JVM CPU가 과부하 상태입니다.";
            status = "WARN";
        } else if (busyRatio >= 85 || slowTx >= 5) {
            causeCode = "THREAD_SATURATION";
            message = "Tomcat Thread가 포화 상태입니다.";
            status = "WARN";
        } else if (slowSql >= 3) {
            causeCode = "SLOW_SQL";
            message = "특정 Mapper SQL이 장시간 실행 중입니다.";
            status = "WARN";
        } else if (countExternalWait() >= 3) {
            causeCode = "EXTERNAL_WAIT";
            message = "외부 시스템 응답을 기다리고 있습니다.";
            status = "WARN";
        }

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("status", status);
        summary.put("primaryCauseCode", causeCode);
        summary.put("message", message);
        summary.put("activeTransactionCount", activeTx);
        summary.put("slowTransactionCount", slowTx);
        summary.put("slowSqlCount", slowSql);
        return summary;
    }

    public Map<String, Object> collectThreadStatus() {
        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        int live = threads.getThreadCount();
        int activeTx = registry.count();
        int max = Math.max(live + 50, 200);
        int busy = Math.max(activeTx, Math.min(live, live - threads.getDaemonThreadCount()));
        double busyRatio = max > 0 ? busy * 100.0 / max : 0;
        long[] deadlocked = threads.findDeadlockedThreads();

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("max", max);
        row.put("current", live);
        row.put("busy", busy);
        row.put("busyRatio", round1(busyRatio));
        row.put("activeTransactionCount", activeTx);
        row.put("slowTransactionCount", slowTransactionTracker.countRecent(System.currentTimeMillis() - 60_000L));
        row.put("blocked", 0);
        row.put("deadlock", deadlocked != null && deadlocked.length > 0);
        return row;
    }

    public Map<String, Object> collectJvmStatus() {
        MemoryUsage heap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        double heapRatio = heap.getMax() > 0 ? heap.getUsed() * 100.0 / heap.getMax() : 0;
        long gcTimeLastMinute = measureGcTimeLastMinute();

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("processCpuRatio", round1(resolveProcessCpuUsagePct()));
        row.put("heapUsedMb", round1(heap.getUsed() / 1024.0 / 1024.0));
        row.put("heapMaxMb", round1(heap.getMax() / 1024.0 / 1024.0));
        row.put("heapRatio", round1(heapRatio));
        row.put("oldGenRatio", round1(resolveOldGenRatio()));
        row.put("metaspaceMb", round1(resolveMetaspaceMb()));
        row.put("gcCountLastMinute", measureGcCountLastMinute());
        row.put("gcTimeLastMinuteMs", gcTimeLastMinute);
        row.put("liveThreadCount", ManagementFactory.getThreadMXBean().getThreadCount());
        row.put("deadlock", Boolean.TRUE.equals(collectThreadStatus().get("deadlock")));
        row.put("uptimeMs", ManagementFactory.getRuntimeMXBean().getUptime());
        return row;
    }

    public List<Map<String, Object>> collectDbPoolStatus() {
        List<Map<String, Object>> rows = new ArrayList<>();
        if (dataSource instanceof HikariDataSource hikari) {
            HikariPoolMXBean pool = hikari.getHikariPoolMXBean();
            int max = hikari.getMaximumPoolSize();
            int active = pool == null ? 0 : pool.getActiveConnections();
            int idle = pool == null ? 0 : pool.getIdleConnections();
            int pending = pool == null ? 0 : pool.getThreadsAwaitingConnection();
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("poolName", hikari.getPoolName());
            row.put("maximum", max);
            row.put("active", active);
            row.put("idle", idle);
            row.put("pending", pending);
            row.put("usageRatio", max > 0 ? round1(active * 100.0 / max) : 0);
            rows.add(row);
        }
        return rows;
    }

    private List<Map<String, Object>> toActiveTransactionRows(List<ActiveTransactionInfo> source, int limit) {
        List<Map<String, Object>> rows = new ArrayList<>();
        int index = 0;
        for (ActiveTransactionInfo info : source) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("serviceId", info.serviceId());
            row.put("businessCode", info.businessCode());
            row.put("guid", maskGuid(info.guid()));
            row.put("elapsedMs", info.elapsedMillis());
            row.put("currentStep", info.currentStep() == null ? null : info.currentStep().name());
            row.put("sqlId", info.currentSqlId());
            row.put("externalSystemCode", info.externalSystemCode());
            rows.add(row);
            index++;
            if (index >= limit) {
                break;
            }
        }
        return rows;
    }

    private List<Map<String, Object>> toSlowTransactionRows(List<SlowTransactionInfo> source) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (SlowTransactionInfo info : source) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("serviceId", info.serviceId());
            row.put("businessCode", info.businessCode());
            row.put("guid", maskGuid(info.guid()));
            row.put("elapsedMs", info.elapsedMillis());
            row.put("lastStep", info.lastStep() == null ? null : info.lastStep().name());
            row.put("sqlId", info.lastSqlId());
            rows.add(row);
        }
        return rows;
    }

    private List<Map<String, Object>> toSlowSqlRows(List<SlowSqlInfo> source) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (SlowSqlInfo info : source) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("mapperId", info.mapperId());
            row.put("sqlId", info.sqlId());
            row.put("serviceId", info.serviceId());
            row.put("elapsedMs", info.elapsedMillis());
            row.put("success", info.success());
            rows.add(row);
        }
        return rows;
    }

    private boolean isPoolExhausted(List<Map<String, Object>> pools) {
        for (Map<String, Object> pool : pools) {
            int maximum = (int) toLong(pool.get("maximum"));
            int active = (int) toLong(pool.get("active"));
            int idle = (int) toLong(pool.get("idle"));
            int pending = (int) toLong(pool.get("pending"));
            if (maximum > 0 && active >= maximum && idle == 0 && pending > 0) {
                return true;
            }
        }
        return false;
    }

    private int countExternalWait() {
        int count = 0;
        for (ActiveTransactionInfo info : registry.snapshot()) {
            if (info.currentStep() != null && "WAIT_EXTERNAL".equals(info.currentStep().name())) {
                count++;
            }
        }
        return count;
    }

    private int sumPending(List<Map<String, Object>> pools) {
        int pending = 0;
        for (Map<String, Object> pool : pools) {
            pending += (int) toLong(pool.get("pending"));
        }
        return pending;
    }

    private long measureGcTimeLastMinute() {
        long total = 0;
        for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
            total += bean.getCollectionTime();
        }
        long delta = Math.max(0, total - lastGcTimeMs);
        resetGcBaseline();
        return delta;
    }

    private long measureGcCountLastMinute() {
        long total = 0;
        for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
            total += bean.getCollectionCount();
        }
        long delta = Math.max(0, total - lastGcCount);
        return delta;
    }

    private void resetGcBaseline() {
        long gcCount = 0;
        long gcTime = 0;
        for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
            gcCount += bean.getCollectionCount();
            gcTime += bean.getCollectionTime();
        }
        lastGcCount = gcCount;
        lastGcTimeMs = gcTime;
        // baseline timestamp kept for future trend use
        // no-op read of gcWindowStartMillis to avoid unused warning
        if (gcWindowStartMillis < 0) {
            return;
        }
    }

    private double resolveOldGenRatio() {
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            String name = pool.getName();
            if (name != null && name.contains("Old")) {
                MemoryUsage usage = pool.getUsage();
                if (usage != null && usage.getMax() > 0) {
                    return usage.getUsed() * 100.0 / usage.getMax();
                }
            }
        }
        return 0;
    }

    private double resolveMetaspaceMb() {
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            if ("Metaspace".equals(pool.getName())) {
                MemoryUsage usage = pool.getUsage();
                if (usage != null) {
                    return usage.getUsed() / 1024.0 / 1024.0;
                }
            }
        }
        return 0;
    }

    private double resolveProcessCpuUsagePct() {
        OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();
        if (bean instanceof com.sun.management.OperatingSystemMXBean sunBean) {
            double load = sunBean.getProcessCpuLoad();
            if (load >= 0) {
                return load * 100.0;
            }
        }
        double systemLoad = bean.getSystemLoadAverage();
        return systemLoad >= 0 ? Math.min(systemLoad * 10, 100) : 0;
    }

    private String resolveHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "localhost";
        }
    }

    private static String resolveBusinessCode(String applicationName, String configured) {
        if (StringUtils.hasText(configured)) {
            return configured.trim().toUpperCase();
        }
        if (!StringUtils.hasText(applicationName)) {
            return "TCF";
        }
        String normalized = applicationName.toLowerCase();
        if (normalized.contains("-om")) {
            return "OM";
        }
        if (normalized.contains("-sv") || normalized.contains("sv-service")) {
            return "SV";
        }
        if (normalized.contains("-ic") || normalized.contains("ic-service")) {
            return "IC";
        }
        if (normalized.contains("-mg") || normalized.contains("mg-service")) {
            return "MG";
        }
        if (normalized.contains("-batch")) {
            return "BATCH";
        }
        if (normalized.contains("-gateway")) {
            return "GATEWAY";
        }
        return applicationName.length() >= 2 ? applicationName.substring(0, 2).toUpperCase() : "TCF";
    }

    private static String maskGuid(String guid) {
        if (guid == null || guid.length() < 8) {
            return "masked";
        }
        return guid.substring(0, 4) + "****" + guid.substring(guid.length() - 4);
    }

    private static double round1(double value) {
        return Math.round(value * 10) / 10.0;
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
