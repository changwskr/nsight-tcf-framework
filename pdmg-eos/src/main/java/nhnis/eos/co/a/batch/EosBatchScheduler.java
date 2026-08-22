package nhnis.eos.co.a.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "eos.batch", name = "enabled", havingValue = "true")
public class EosBatchScheduler {

    private static final Logger log = LoggerFactory.getLogger(EosBatchScheduler.class);

    private final EosBatchJobs jobs;
    private final EosBatchProperties properties;

    public EosBatchScheduler(EosBatchJobs jobs, EosBatchProperties properties) {
        this.jobs = jobs;
        this.properties = properties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        if (!properties.isRunOnStartup()) {
            return;
        }
        log.info("eos.batch run-on-startup begin");
        jobs.runAll();
        log.info("eos.batch run-on-startup end");
    }

    @Scheduled(cron = "${eos.batch.status-recalc-cron:0 0 2 * * *}")
    public void statusRecalc() {
        jobs.statusRecalc();
    }

    @Scheduled(cron = "${eos.batch.exception-expire-cron:0 15 2 * * *}")
    public void exceptionExpire() {
        jobs.exceptionExpire();
    }

    @Scheduled(cron = "${eos.batch.action-overdue-cron:0 30 2 * * *}")
    public void actionOverdue() {
        jobs.actionOverdue();
    }

    @Scheduled(cron = "${eos.batch.monthly-check-miss-cron:0 0 3 1 * *}")
    public void monthlyCheckMiss() {
        jobs.monthlyCheckMiss();
    }
}
