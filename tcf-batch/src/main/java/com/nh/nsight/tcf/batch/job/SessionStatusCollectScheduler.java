package com.nh.nsight.tcf.batch.job;

import com.nh.nsight.tcf.batch.config.SessionStatusBatchProperties;
import com.nh.nsight.tcf.batch.model.SessionStatusCollectResult;
import com.nh.nsight.tcf.batch.service.SessionStatusCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SessionStatusCollectScheduler {
    private static final Logger log = LoggerFactory.getLogger(SessionStatusCollectScheduler.class);

    private final SessionStatusBatchProperties properties;
    private final SessionStatusCollectService collectService;

    public SessionStatusCollectScheduler(SessionStatusBatchProperties properties,
                                         SessionStatusCollectService collectService) {
        this.properties = properties;
        this.collectService = collectService;
    }

    @Scheduled(cron = "${nsight.batch.session-status.cron:45 */5 * * * *}")
    public void runScheduled() {
        log.info("Scheduled session status collect started jobId={}", properties.getJobId());
        SessionStatusCollectResult result = collectService.collectAndPersist();
        log.info("Scheduled session status collect finished jobId={} status={} message={}",
                result.jobId(), result.runStatus(), result.message());
    }
}
