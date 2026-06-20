package com.nh.nsight.tcf.batch.job;

import com.nh.nsight.tcf.batch.config.DbStatusBatchProperties;
import com.nh.nsight.tcf.batch.model.DbStatusCollectResult;
import com.nh.nsight.tcf.batch.service.DbStatusCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DbStatusCollectScheduler {
    private static final Logger log = LoggerFactory.getLogger(DbStatusCollectScheduler.class);

    private final DbStatusBatchProperties properties;
    private final DbStatusCollectService collectService;

    public DbStatusCollectScheduler(DbStatusBatchProperties properties, DbStatusCollectService collectService) {
        this.properties = properties;
        this.collectService = collectService;
    }

    @Scheduled(cron = "${nsight.batch.db-status.cron:30 */5 * * * *}")
    public void runScheduled() {
        log.info("Scheduled DB status collect started jobId={}", properties.getJobId());
        DbStatusCollectResult result = collectService.collectAndPersist();
        log.info("Scheduled DB status collect finished jobId={} status={} message={}",
                result.jobId(), result.runStatus(), result.message());
    }
}
