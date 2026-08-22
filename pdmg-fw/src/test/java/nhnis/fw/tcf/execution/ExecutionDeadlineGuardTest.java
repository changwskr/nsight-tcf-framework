package nhnis.fw.tcf.execution;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import nhnis.fw.tcf.core.context.TransactionContext;
import nhnis.fw.tcf.timeout.OnlineTimeoutException;
import nhnis.fw.tcf.timeout.OnlineTimeoutProperties;

class ExecutionDeadlineGuardTest {

    @AfterEach
    void tearDown() {
        ExecutionDeadlineContext.clear();
    }

    @Test
    void passesWhenDeadlineHasRemainingBudget() {
        OnlineTimeoutProperties properties = enabledProperties();
        ExecutionDeadlineContext.bind(ExecutionDeadline.start(500));

        ExecutionDeadlineGuard.throwIfExpired(TransactionContext.fromCurrent("mgcoa8888S0"), properties);
    }

    @Test
    void throwsWhenSharedDeadlineExpired() throws Exception {
        OnlineTimeoutProperties properties = enabledProperties();
        ExecutionDeadline deadline = ExecutionDeadline.start(50);
        ExecutionDeadlineContext.bind(deadline);
        Thread.sleep(60);

        assertThatThrownBy(() -> ExecutionDeadlineGuard.throwIfExpired(
                TransactionContext.fromCurrent("mgcoa8888S0"), properties))
                .isInstanceOf(OnlineTimeoutException.class)
                .satisfies(ex -> assertThat(((OnlineTimeoutException) ex).getTimeoutMs()).isEqualTo(200L));
    }

    @Test
    void skipsWhenTimeoutDisabled() throws Exception {
        OnlineTimeoutProperties properties = enabledProperties();
        properties.setEnabled(false);
        ExecutionDeadlineContext.bind(ExecutionDeadline.start(10));
        Thread.sleep(20);

        ExecutionDeadlineGuard.throwIfExpired(TransactionContext.fromCurrent("mgcoa8888S0"), properties);
    }

    private static OnlineTimeoutProperties enabledProperties() {
        OnlineTimeoutProperties properties = new OnlineTimeoutProperties();
        properties.setEnabled(true);
        properties.setMilliseconds(200);
        return properties;
    }
}
