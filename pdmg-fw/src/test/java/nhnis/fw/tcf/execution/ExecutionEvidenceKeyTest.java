package nhnis.fw.tcf.execution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import nhnis.fw.commons.context.ServiceContext;
import nhnis.fw.commons.context.ServiceContextHolder;
import nhnis.fw.tcf.core.context.TransactionContext;

class ExecutionEvidenceKeyTest {

    @AfterEach
    void tearDown() {
        ServiceContextHolder.removeInstance();
    }

    @Test
    void usesGuidWhenPresent() {
        ServiceContextHolder.setInstance(new ServiceContext(null, "GUID-100", null, null, null, null, null));
        TransactionContext context = TransactionContext.fromCurrent("mgcoa5530S0");

        String key = ExecutionEvidenceKey.assign(context);

        assertThat(key).isEqualTo("GUID-100");
        assertThat(context.getEvidenceKey()).isEqualTo("GUID-100");
        assertThat(ExecutionEvidenceKey.fromServiceContext(context.getServiceContext())).isEqualTo("GUID-100");
    }

    @Test
    void assignsStableFallbackWhenGuidMissing() {
        ServiceContext serviceContext = new ServiceContext(null, null, null, null, null, null, null);
        ServiceContextHolder.setInstance(serviceContext);
        TransactionContext context = TransactionContext.fromCurrent("mgcoa5530S0");

        String key = ExecutionEvidenceKey.assign(context);

        assertThat(key).contains("mgcoa5530S0@");
        assertThat(context.getEvidenceKey()).isEqualTo(key);
        assertThat(ExecutionEvidenceKey.fromServiceContext(serviceContext)).isEqualTo(key);
        assertThat(ExecutionEvidenceKey.keyOf(context)).isEqualTo(key);
    }
}
