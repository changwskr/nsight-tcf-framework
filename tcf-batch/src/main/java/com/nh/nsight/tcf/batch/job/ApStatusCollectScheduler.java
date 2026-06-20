package com.nh.nsight.tcf.batch.job;

import com.nh.nsight.tcf.batch.config.ApStatusBatchProperties;
import com.nh.nsight.tcf.batch.model.ApStatusCollectResult;
import com.nh.nsight.tcf.batch.service.ApStatusCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApStatusCollectScheduler {
    private static final Logger log = LoggerFactory.getLogger(ApStatusCollectScheduler.class);

    private final ApStatusBatchProperties properties;
    private final ApStatusCollectService collectService;

    public ApStatusCollectScheduler(ApStatusBatchProperties properties, ApStatusCollectService collectService) {
        this.properties = properties;
        this.collectService = collectService;
    }

    @Scheduled(cron = "${nsight.batch.ap-status.cron:0 */5 * * * *}")
    public void runScheduled() {
        log.info("Scheduled AP status collect started jobId={}", properties.getJobId());
        ApStatusCollectResult result = collectService.collectAndPersist();
        log.info("Scheduled AP status collect finished jobId={} status={} message={}",
                result.jobId(), result.runStatus(), result.message());
    }
}
