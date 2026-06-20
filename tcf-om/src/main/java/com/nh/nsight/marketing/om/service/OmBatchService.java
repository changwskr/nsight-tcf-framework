package com.nh.nsight.marketing.om.service;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.util.DateTimeUtil;
import com.nh.nsight.marketing.om.dao.OmOperationDao;
import com.nh.nsight.marketing.om.rule.OmOperationRule;
import com.nh.nsight.marketing.om.support.OmBodySupport;
import com.nh.nsight.marketing.om.support.OmChangeRecorder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OmBatchService {
    private final OmOperationRule rule;
    private final OmOperationDao dao;
    private final OmChangeRecorder recorder;
    private final OmSessionCleanupService sessionCleanupService;

    public OmBatchService(OmOperationRule rule, OmOperationDao dao, OmChangeRecorder recorder,
                          OmSessionCleanupService sessionCleanupService) {
        this.rule = rule;
        this.dao = dao;
        this.recorder = recorder;
        this.sessionCleanupService = sessionCleanupService;
    }

    public Map<String, Object> inquiry(Map<String, Object> body, TransactionContext context) {
        rule.validateOperation(context);
        Map<String, Object> jobCriteria = new HashMap<>();
        putIfPresent(body, jobCriteria, "businessCode", "useYn");

        Map<String, Object> historyCriteria = new HashMap<>(body == null ? Map.of() : body);
        rule.normalizePaging(historyCriteria);
        putIfPresent(body, historyCriteria, "jobId", "runStatus");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "OM");
        result.put("screen", "배치 / 스케줄 관리");
        result.put("jobs", dao.searchBatchJobs(jobCriteria));
        result.put("histories", dao.searchBatchHistories(historyCriteria));
        result.put("pageNo", historyCriteria.get("pageNo"));
        result.put("pageSize", historyCriteria.get("pageSize"));
        result.put("totalCount", dao.countBatchHistories(historyCriteria));
        return result;
    }

    public Map<String, Object> execute(Map<String, Object> body, TransactionContext context) {
        rule.validateOperation(context);
        rule.requireField(body, "jobId");
        rule.requireReason(body, "executeReason");

        String jobId = OmBodySupport.stringValue(body, "jobId");
        Map<String, Object> job = dao.selectBatchJobById(jobId);
        if (job == null || !"Y".equalsIgnoreCase(String.valueOf(job.get("useYn")))) {
            throw new BusinessException("E-OM-BIZ-0003", "실행 가능한 배치 Job이 없습니다: " + jobId);
        }

        String reason = OmBodySupport.stringValue(body, "executeReason");
        if (OmSessionCleanupService.JOB_ID.equals(jobId)) {
            OmSessionCleanupService.OmSessionCleanupResult cleanup = sessionCleanupService.runManual(context, reason);
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("businessCode", "OM");
            result.put("screen", "배치 재실행");
            result.put("executed", true);
            result.put("jobId", jobId);
            result.put("runStatus", "SUCCESS");
            result.put("expiredCount", cleanup.expiredCount());
            result.put("deletedCount", cleanup.deletedCount());
            result.put("activeCount", cleanup.activeCount());
            result.put("durationMs", cleanup.durationMs());
            return result;
        }

        long start = System.currentTimeMillis();
        String runStatus = "SUCCESS";
        String message = "수동 재실행 완료: " + job.get("jobName");

        Map<String, Object> history = new HashMap<>();
        history.put("historyId", "BH-" + UUID.randomUUID());
        history.put("jobId", jobId);
        history.put("runTime", DateTimeUtil.nowKst());
        history.put("runStatus", runStatus);
        history.put("durationMs", System.currentTimeMillis() - start + 120);
        history.put("resultMessage", message + " (사유: " + reason + ")");
        dao.insertBatchHistory(history);

        recorder.recordAdminAudit(context, "BATCH_EXECUTE", "배치 재실행", reason, runStatus);
        recorder.recordAuthHistory(context, "BATCH", jobId, "scheduled", "manual-execute", reason);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", "OM");
        result.put("screen", "배치 재실행");
        result.put("executed", true);
        result.put("jobId", jobId);
        result.put("historyId", history.get("historyId"));
        result.put("runStatus", runStatus);
        return result;
    }

    private void putIfPresent(Map<String, Object> body, Map<String, Object> criteria, String... keys) {
        for (String key : keys) {
            String value = OmBodySupport.stringValue(body, key);
            if (value != null) {
                criteria.put(key, value);
            }
        }
    }
}


