package nhnis.fw.tcf.execution;

import org.springframework.util.StringUtils;

/**
 * {@link OnlineTransactionPolicyProperties} 기반 Transaction Policy resolver.
 */
public class PropertiesTransactionPolicyResolver implements TransactionPolicyResolver {

    private final OnlineTransactionPolicyProperties properties;

    public PropertiesTransactionPolicyResolver(OnlineTransactionPolicyProperties properties) {
        this.properties = properties;
    }

    @Override
    public TransactionPolicy resolve(String serviceId) {
        OnlineTransactionPolicyProperties.ServicePolicy override = null;
        if (StringUtils.hasText(serviceId) && properties.getServices() != null) {
            override = properties.getServices().get(serviceId.trim());
        }

        TransactionMode mode = override != null && override.getMode() != null
                ? override.getMode()
                : properties.getDefaultMode();

        if (mode == TransactionMode.NONE) {
            return new TransactionPolicy(TransactionMode.NONE, null, false);
        }

        String manager = resolveManager(mode, override);
        boolean readOnly = resolveReadOnly(mode, override);
        return new TransactionPolicy(mode, manager, readOnly);
    }

    private String resolveManager(TransactionMode mode,
            OnlineTransactionPolicyProperties.ServicePolicy override) {
        if (override != null && StringUtils.hasText(override.getManager())) {
            return override.getManager().trim();
        }
        String modeDefault = mode.defaultManagerBean();
        if (StringUtils.hasText(modeDefault)) {
            return modeDefault;
        }
        return properties.getDefaultManager();
    }

    private boolean resolveReadOnly(TransactionMode mode,
            OnlineTransactionPolicyProperties.ServicePolicy override) {
        if (override != null && override.getReadOnly() != null) {
            return override.getReadOnly();
        }
        return mode.readOnlyDefault();
    }
}
