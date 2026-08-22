package nhnis.fw.tcf.timeout;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;

/**
 * 온라인 거래 공통 타임아웃 설정.
 *
 * <pre>
 * nhnis.fw.timeout.enabled=true
 * nhnis.fw.timeout.milliseconds=5000
 * nhnis.fw.timeout.pool-size=20
 * nhnis.fw.timeout.queue-capacity=100
 * nhnis.fw.timeout.min-start-budget-ms=1000
 * nhnis.fw.timeout.overrides.mgcoa5530S0=10000
 * </pre>
 */
@ConfigurationProperties(prefix = "nhnis.fw.timeout")
public class OnlineTimeoutProperties {

    private boolean enabled = false;

    private long milliseconds = 5000L;

    private int poolSize = 20;

    private int queueCapacity = 100;

    /** Worker 시작 시 TX를 열기 위한 최소 남은 시간(ms). */
    private long minStartBudgetMs = 1000L;

    /** serviceId → timeout(ms). 미등록 시 {@link #milliseconds} 사용. */
    private Map<String, Long> overrides = new LinkedHashMap<>();

    @PostConstruct
    void validate() {
        if (milliseconds < 1) {
            throw new IllegalStateException("nhnis.fw.timeout.milliseconds must be >= 1");
        }
        if (poolSize < 1) {
            throw new IllegalStateException("nhnis.fw.timeout.pool-size must be >= 1");
        }
        if (queueCapacity < 0) {
            throw new IllegalStateException("nhnis.fw.timeout.queue-capacity must be >= 0");
        }
        if (minStartBudgetMs < 1) {
            throw new IllegalStateException("nhnis.fw.timeout.min-start-budget-ms must be >= 1");
        }
        if (overrides != null) {
            for (Map.Entry<String, Long> entry : overrides.entrySet()) {
                if (!StringUtils.hasText(entry.getKey())) {
                    throw new IllegalStateException("nhnis.fw.timeout.overrides key must not be blank");
                }
                Long value = entry.getValue();
                if (value == null || value < 1L) {
                    throw new IllegalStateException(
                            "nhnis.fw.timeout.overrides." + entry.getKey() + " must be >= 1");
                }
            }
        }
    }

    /**
     * serviceId 별 override가 있으면 그 값, 없으면 기본 {@link #milliseconds}.
     */
    public long resolveMilliseconds(String serviceId) {
        if (StringUtils.hasText(serviceId) && overrides != null) {
            Long override = overrides.get(serviceId.trim());
            if (override != null && override >= 1L) {
                return override;
            }
        }
        return milliseconds;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public long getMinStartBudgetMs() {
        return minStartBudgetMs;
    }

    public void setMinStartBudgetMs(long minStartBudgetMs) {
        this.minStartBudgetMs = minStartBudgetMs;
    }

    public Map<String, Long> getOverrides() {
        return overrides == null ? Collections.emptyMap() : overrides;
    }

    public void setOverrides(Map<String, Long> overrides) {
        this.overrides = overrides == null ? new LinkedHashMap<>() : new LinkedHashMap<>(overrides);
    }
}
