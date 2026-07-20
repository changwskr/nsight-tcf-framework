package com.nh.nsight.tcf.batch.support;

import com.nh.nsight.tcf.batch.service.ApStatusCollectService;
import com.nh.nsight.tcf.batch.service.DbStatusCollectService;
import com.nh.nsight.tcf.batch.service.DeployStatusCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Order(50)
@ConditionalOnProperty(name = "nsight.batch.startup-collect.enabled", havingValue = "true", matchIfMissing = true)
public class DashboardCollectStartupRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(DashboardCollectStartupRunner.class);

    private final ApStatusCollectService apStatusCollectService;
    private final DbStatusCollectService dbStatusCollectService;
    private final DeployStatusCollectService deployStatusCollectService;
    private final TaskScheduler taskScheduler;
    private final long initialDelayMs;

    public DashboardCollectStartupRunner(ApStatusCollectService apStatusCollectService,
                                         DbStatusCollectService dbStatusCollectService,
                                         DeployStatusCollectService deployStatusCollectService,
                                         TaskScheduler taskScheduler,
                                         @Value("${nsight.batch.startup-collect.initial-delay-ms:0}") long initialDelayMs) {
        this.apStatusCollectService = apStatusCollectService;
        this.dbStatusCollectService = dbStatusCollectService;
        this.deployStatusCollectService = deployStatusCollectService;
        this.taskScheduler = taskScheduler;
        this.initialDelayMs = initialDelayMs;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (initialDelayMs > 0) {
            log.info("Scheduling initial dashboard collect in {} ms (non-blocking — other WARs can deploy)",
                    initialDelayMs);
            taskScheduler.schedule(this::runInitialCollect, Instant.now().plusMillis(initialDelayMs));
            return;
        }
        runInitialCollect();
    }

    private void runInitialCollect() {
        log.info("Initial dashboard status collect on tcf-batch startup");
        apStatusCollectService.collectAndPersist();
        dbStatusCollectService.collectAndPersist();
        deployStatusCollectService.collectAndPersist();
        log.info("Initial dashboard status collect finished");
    }
}
