package com.nh.nsight.tcf.batch.support;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ScheduledCollectSupport {
    private final BatchCollectWarmupGate warmupGate;

    public ScheduledCollectSupport(BatchCollectWarmupGate warmupGate) {
        this.warmupGate = warmupGate;
    }

    public boolean skipIfWarmingUp(String jobId, Logger log) {
        if (warmupGate.isReady()) {
            return false;
        }
        log.info("Skipping scheduled collect jobId={} — Tomcat WAR warmup ({} ms remaining)",
                jobId, warmupGate.remainingMs());
        return true;
    }
}
