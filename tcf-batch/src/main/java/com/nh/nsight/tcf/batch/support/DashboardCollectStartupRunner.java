package com.nh.nsight.tcf.batch.support;

import com.nh.nsight.tcf.batch.service.ApStatusCollectService;
import com.nh.nsight.tcf.batch.service.DbStatusCollectService;
import com.nh.nsight.tcf.batch.service.DeployStatusCollectService;
import com.nh.nsight.tcf.batch.service.SessionStatusCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(50)
@ConditionalOnProperty(name = "nsight.batch.startup-collect.enabled", havingValue = "true", matchIfMissing = true)
public class DashboardCollectStartupRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(DashboardCollectStartupRunner.class);

    private final ApStatusCollectService apStatusCollectService;
    private final DbStatusCollectService dbStatusCollectService;
    private final SessionStatusCollectService sessionStatusCollectService;
    private final DeployStatusCollectService deployStatusCollectService;

    public DashboardCollectStartupRunner(ApStatusCollectService apStatusCollectService,
                                         DbStatusCollectService dbStatusCollectService,
                                         SessionStatusCollectService sessionStatusCollectService,
                                         DeployStatusCollectService deployStatusCollectService) {
        this.apStatusCollectService = apStatusCollectService;
        this.dbStatusCollectService = dbStatusCollectService;
        this.sessionStatusCollectService = sessionStatusCollectService;
        this.deployStatusCollectService = deployStatusCollectService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Initial dashboard status collect on tcf-batch startup");
        apStatusCollectService.collectAndPersist();
        dbStatusCollectService.collectAndPersist();
        sessionStatusCollectService.collectAndPersist();
        deployStatusCollectService.collectAndPersist();
        log.info("Initial dashboard status collect finished");
    }
}
