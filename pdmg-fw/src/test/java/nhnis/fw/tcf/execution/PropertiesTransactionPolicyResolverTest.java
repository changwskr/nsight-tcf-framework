package nhnis.fw.tcf.execution;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

class PropertiesTransactionPolicyResolverTest {

    @Test
    void usesDefaultReadWritePolicy() {
        OnlineTransactionPolicyProperties properties = new OnlineTransactionPolicyProperties();
        PropertiesTransactionPolicyResolver resolver = new PropertiesTransactionPolicyResolver(properties);

        TransactionPolicy policy = resolver.resolve("mgcoa8888S0");
        assertThat(policy.mode()).isEqualTo(TransactionMode.RDW_READ_WRITE);
        assertThat(policy.transactionManagerBean()).isEqualTo("rdwTransactionManager");
        assertThat(policy.readOnly()).isFalse();
    }

    @Test
    void resolvesServiceOverrideReadOnly() {
        OnlineTransactionPolicyProperties properties = new OnlineTransactionPolicyProperties();
        OnlineTransactionPolicyProperties.ServicePolicy servicePolicy =
                new OnlineTransactionPolicyProperties.ServicePolicy();
        servicePolicy.setMode(TransactionMode.RDW_READ_ONLY);
        properties.getServices().put("mgcoa5530S0", servicePolicy);

        PropertiesTransactionPolicyResolver resolver = new PropertiesTransactionPolicyResolver(properties);
        TransactionPolicy policy = resolver.resolve("mgcoa5530S0");

        assertThat(policy.mode()).isEqualTo(TransactionMode.RDW_READ_ONLY);
        assertThat(policy.readOnly()).isTrue();
    }

    @Test
    void resolvesNoneModeWithoutManager() {
        OnlineTransactionPolicyProperties properties = new OnlineTransactionPolicyProperties();
        OnlineTransactionPolicyProperties.ServicePolicy servicePolicy =
                new OnlineTransactionPolicyProperties.ServicePolicy();
        servicePolicy.setMode(TransactionMode.NONE);
        properties.getServices().put("mgcoa9000S0", servicePolicy);

        PropertiesTransactionPolicyResolver resolver = new PropertiesTransactionPolicyResolver(properties);
        TransactionPolicy policy = resolver.resolve("mgcoa9000S0");

        assertThat(policy.requiresTransaction()).isFalse();
        assertThat(policy.transactionManagerBean()).isNull();
    }
}
