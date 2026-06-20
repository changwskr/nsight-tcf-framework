package com.nh.nsight.tcf.batch.job;

import com.nh.nsight.tcf.batch.config.DeployStatusBatchProperties;
import com.nh.nsight.tcf.batch.model.DeployStatusCollectResult;
import com.nh.nsight.tcf.batch.service.DeployStatusCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeployStatusCollectScheduler {
    private static final Logger log = LoggerFactory.getLogger(DeployStatusCollectScheduler.class);

    private final DeployStatusBatchProperties properties;
    private final DeployStatusCollectService collectService;

    public DeployStatusCollectScheduler(DeployStatusBatchProperties properties,
                                        DeployStatusCollectService collectService) {
        this.properties = properties;
        this.collectService = collectService;
    }

    @Scheduled(cron = "${nsight.batch.deploy-status.cron:55 */5 * * * *}")
    public void runScheduled() {
        log.info("Scheduled deploy status collect started jobId={}", properties.getJobId());
        DeployStatusCollectResult result = collectService.collectAndPersist();
        log.info("Scheduled deploy status collect finished jobId={} status={} message={}",
                result.jobId(), result.runStatus(), result.message());
    }
}
