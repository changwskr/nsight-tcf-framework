package nhnis.eos.co.a.batch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.domain.EosStatusEngine;
import nhnis.eos.co.a.persistence.dao.eoscoaBatchDAO;
import nhnis.eos.co.a.support.EosDtm;
import nhnis.eos.co.a.support.EosIdGenerator;
import nhnis.eos.co.a.support.EosResults;

/**
 * P0 Batch — MATRIX §3 / RULE-200·201.
 */
@Component
public class EosBatchJobs {

    private static final Logger log = LoggerFactory.getLogger(EosBatchJobs.class);
    private static final DateTimeFormatter YMD = DateTimeFormatter.BASIC_ISO_DATE;

    private final eoscoaBatchDAO dao;
    private final EosStatusEngine statusEngine;
    private final EosIdGenerator ids;

    public EosBatchJobs(eoscoaBatchDAO dao, EosStatusEngine statusEngine, EosIdGenerator ids) {
        this.dao = dao;
        this.statusEngine = statusEngine;
        this.ids = ids;
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> statusRecalc() {
        return run("eos.batch.statusRecalc", () -> {
            int approaching = policyInt("APPROACHING_DAYS", 365);
            int due = policyInt("DUE_DAYS", 90);
            String dtm = EosDtm.now();
            List<Map<String, Object>> rows = dao.listActiveResourcesWithLfc();
            int process = 0;
            int success = 0;
            int fail = 0;
            if (rows != null) {
                for (Map<String, Object> row : rows) {
                    process++;
                    try {
                        String resourceId = EosResults.str(row, "RESOURCE_ID");
                        String old = EosResults.str(row, "EOS_STATUS_CD");
                        String eosYmd = EosResults.str(row, "EOS_YMD");
                        String eolYmd = EosResults.str(row, "EOL_YMD");
                        String neu = statusEngine.resolveStatus(eosYmd, eolYmd, approaching, due);
                        if (neu != null && !neu.equals(old)) {
                            Map<String, Object> upd = new HashMap<>();
                            upd.put("resourceId", resourceId);
                            upd.put("eosStatusCd", neu);
                            upd.put("chgUserId", "BATCH");
                            upd.put("chgDtm", dtm);
                            dao.updateResourceStatus(upd);
                        }
                        success++;
                    } catch (Exception e) {
                        fail++;
                        log.warn("statusRecalc row fail: {}", e.getMessage());
                    }
                }
            }
            return result(process, success, fail, "recalc done");
        });
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> exceptionExpire() {
        return run("eos.batch.exceptionExpire", () -> {
            String asOf = LocalDate.now().format(YMD);
            String dtm = EosDtm.now();
            List<Map<String, Object>> rows = dao.listExpiredExceptions(Map.of("asOfYmd", asOf));
            int process = 0;
            int success = 0;
            int fail = 0;
            if (rows != null) {
                for (Map<String, Object> row : rows) {
                    process++;
                    try {
                        String excReqId = EosResults.str(row, "EXC_REQ_ID");
                        String resourceId = EosResults.str(row, "RESOURCE_ID");
                        Map<String, Object> exp = new HashMap<>();
                        exp.put("excReqId", excReqId);
                        exp.put("chgUserId", "BATCH");
                        exp.put("chgDtm", dtm);
                        dao.expireException(exp);
                        Map<String, Object> rsc = new HashMap<>();
                        rsc.put("resourceId", resourceId);
                        rsc.put("chgUserId", "BATCH");
                        rsc.put("chgDtm", dtm);
                        dao.clearResourceExceptionYn(rsc);
                        success++;
                    } catch (Exception e) {
                        fail++;
                        log.warn("exceptionExpire row fail: {}", e.getMessage());
                    }
                }
            }
            return result(process, success, fail, "expire asOf=" + asOf);
        });
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> actionOverdue() {
        return run("eos.batch.actionOverdue", () -> {
            String asOf = LocalDate.now().format(YMD);
            int cnt = dao.countActionOverdue(Map.of("asOfYmd", asOf));
            log.info("eos.batch.actionOverdue count={}", cnt);
            return result(cnt, cnt, 0, "overdue count (notify P1)");
        });
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> monthlyCheckMiss() {
        return run("eos.batch.monthlyCheckMiss", () -> {
            String ym = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
            int cnt = dao.countMonthlyCheckMiss(Map.of("checkYm", ym));
            log.info("eos.batch.monthlyCheckMiss ym={} count={}", ym, cnt);
            return result(cnt, cnt, 0, "miss count ym=" + ym);
        });
    }

    /** 스케줄/기동 시 4 Job 순차 실행. */
    public Map<String, Object> runAll() {
        Map<String, Object> out = new HashMap<>();
        out.put("statusRecalc", statusRecalc());
        out.put("exceptionExpire", exceptionExpire());
        out.put("actionOverdue", actionOverdue());
        out.put("monthlyCheckMiss", monthlyCheckMiss());
        return out;
    }

    private Map<String, Object> run(String jobId, JobBody body) {
        String runId = ids.next("BAT");
        String start = EosDtm.now();
        try {
            dao.insertBatchRun(Map.of(
                    "runId", runId,
                    "jobId", jobId,
                    "startDtm", start));
        } catch (Exception e) {
            log.warn("batch run insert skip: {}", e.getMessage());
        }
        Map<String, Object> stats;
        String status = "SUCCESS";
        try {
            stats = body.execute();
            if (EosResults.intVal(stats, "failCnt", 0) > 0) {
                status = "PARTIAL";
            }
        } catch (Exception e) {
            status = "FAIL";
            stats = result(0, 0, 1, e.getMessage());
            log.error("{} failed: {}", jobId, e.getMessage());
        }
        try {
            Map<String, Object> fin = new HashMap<>(stats);
            fin.put("runId", runId);
            fin.put("endDtm", EosDtm.now());
            fin.put("statusCd", status);
            dao.finishBatchRun(fin);
        } catch (Exception e) {
            log.warn("batch run finish skip: {}", e.getMessage());
        }
        stats.put("runId", runId);
        stats.put("jobId", jobId);
        stats.put("statusCd", status);
        log.info("{} process={} success={} fail={} status={}",
                jobId, stats.get("processCnt"), stats.get("successCnt"), stats.get("failCnt"), status);
        return stats;
    }

    private int policyInt(String key, int def) {
        String v = dao.selectPolicyVal(Map.of("policyTypeCd", "EOS_THRESHOLD", "policyKey", key));
        if (v == null || v.isBlank()) {
            return def;
        }
        try {
            return Integer.parseInt(v.trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static Map<String, Object> result(int process, int success, int fail, String msg) {
        Map<String, Object> m = new HashMap<>();
        m.put("processCnt", process);
        m.put("successCnt", success);
        m.put("failCnt", fail);
        m.put("msgTxt", msg);
        return m;
    }

    @FunctionalInterface
    private interface JobBody {
        Map<String, Object> execute();
    }
}
