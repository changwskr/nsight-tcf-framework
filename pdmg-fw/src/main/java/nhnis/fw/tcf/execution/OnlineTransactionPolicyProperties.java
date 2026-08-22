package nhnis.fw.tcf.execution;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;

/**
 * ServiceId별 Transaction Policy YAML 바인딩.
 *
 * <pre>
 * nhnis:
 *   fw:
 *     transaction:
 *       default-manager: rdwTransactionManager
 *       default-mode: RDW_READ_WRITE
 *       services:
 *         mgcoa5530S0:
 *           mode: RDW_READ_ONLY
 *         mgcoa9000S0:
 *           mode: NONE
 * </pre>
 */
@ConfigurationProperties(prefix = "nhnis.fw.transaction")
public class OnlineTransactionPolicyProperties {

    private String defaultManager = "rdwTransactionManager";

    private TransactionMode defaultMode = TransactionMode.RDW_READ_WRITE;

    private Map<String, ServicePolicy> services = new LinkedHashMap<>();

    @PostConstruct
    void validate() {
        if (!StringUtils.hasText(defaultManager)) {
            throw new IllegalStateException("nhnis.fw.transaction.default-manager must not be blank");
        }
        if (defaultMode == null) {
            throw new IllegalStateException("nhnis.fw.transaction.default-mode must not be null");
        }
        if (services != null) {
            for (Map.Entry<String, ServicePolicy> entry : services.entrySet()) {
                if (!StringUtils.hasText(entry.getKey())) {
                    throw new IllegalStateException("nhnis.fw.transaction.services key must not be blank");
                }
                ServicePolicy policy = entry.getValue();
                if (policy != null && policy.getMode() == TransactionMode.NONE
                        && StringUtils.hasText(policy.getManager())) {
                    throw new IllegalStateException(
                            "nhnis.fw.transaction.services." + entry.getKey()
                                    + " mode=NONE must not specify manager");
                }
            }
        }
    }

    public String getDefaultManager() {
        return defaultManager;
    }

    public void setDefaultManager(String defaultManager) {
        this.defaultManager = defaultManager;
    }

    public TransactionMode getDefaultMode() {
        return defaultMode;
    }

    public void setDefaultMode(TransactionMode defaultMode) {
        this.defaultMode = defaultMode;
    }

    public Map<String, ServicePolicy> getServices() {
        return services == null ? Collections.emptyMap() : services;
    }

    public void setServices(Map<String, ServicePolicy> services) {
        this.services = services == null ? new LinkedHashMap<>() : new LinkedHashMap<>(services);
    }

    public static final class ServicePolicy {

        private TransactionMode mode;

        private String manager;

        private Boolean readOnly;

        public TransactionMode getMode() {
            return mode;
        }

        public void setMode(TransactionMode mode) {
            this.mode = mode;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
        }

        public Boolean getReadOnly() {
            return readOnly;
        }

        public void setReadOnly(Boolean readOnly) {
            this.readOnly = readOnly;
        }
    }
}
