package com.nh.nsight.tcf.batch.web;

import com.nh.nsight.tcf.batch.model.DeployStatusCollectResult;
import com.nh.nsight.tcf.batch.service.DeployStatusCollectService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch/jobs/deploy-status")
public class DeployStatusBatchController {
    private final DeployStatusCollectService collectService;

    public DeployStatusBatchController(DeployStatusCollectService collectService) {
        this.collectService = collectService;
    }

    @GetMapping
    public Map<String, Object> info() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("jobId", "BAT-BATCH-004");
        body.put("description", "OM 대시보드 배포 현황 수집 (Actuator/HTTP → OM_DEPLOY_STATUS)");
        body.put("runEndpoint", "POST /batch/jobs/deploy-status/run");
        return body;
    }

    @PostMapping("/run")
    public Map<String, Object> run() {
        DeployStatusCollectResult result = collectService.collectAndPersist();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("jobId", result.jobId());
        body.put("runTime", result.runTime());
        body.put("runStatus", result.runStatus());
        body.put("durationMs", result.durationMs());
        body.put("targetCount", result.targetCount());
        body.put("successCount", result.successCount());
        body.put("failCount", result.failCount());
        body.put("message", result.message());
        body.put("snapshots", result.snapshots());
        return body;
    }
}
