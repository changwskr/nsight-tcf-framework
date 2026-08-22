package nhnis.fw.tcf.execution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ExecutionDeadlineTest {

    @Test
    void remainingMillisDecreasesOverTime() throws Exception {
        ExecutionDeadline deadline = ExecutionDeadline.start(500);
        long first = deadline.remainingMillis();
        Thread.sleep(50);
        long second = deadline.remainingMillis();
        assertThat(second).isLessThan(first);
        assertThat(second).isLessThanOrEqualTo(500L);
    }

    @Test
    void expiredAfterTimeout() throws Exception {
        ExecutionDeadline deadline = ExecutionDeadline.start(80);
        Thread.sleep(120);
        assertThat(deadline.expired()).isTrue();
        assertThat(deadline.remainingMillis()).isZero();
    }
}
