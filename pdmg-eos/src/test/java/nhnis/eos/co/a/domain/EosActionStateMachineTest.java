package nhnis.eos.co.a.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EosActionStateMachineTest {

    private final EosActionStateMachine sm = new EosActionStateMachine();

    @Test
    void allowsPlannedPath() {
        assertThat(sm.canTransit("NOT_STARTED", "PLANNED")).isTrue();
        assertThat(sm.canTransit("PLANNED", "IN_PROGRESS")).isTrue();
        assertThat(sm.canTransit("IN_PROGRESS", "TESTING")).isTrue();
    }

    @Test
    void rejectsDirectDoneViaU1() {
        assertThat(sm.canTransit("TESTING", "DONE")).isFalse();
        assertThat(sm.canTransit("IN_PROGRESS", "DONE")).isFalse();
        assertThat(sm.canTransit("PLANNED", "DONE")).isFalse();
    }

    @Test
    void sameStatusIsNoOp() {
        assertThat(sm.canTransit("HOLD", "HOLD")).isTrue();
    }

    @Test
    void completeRequestOnlyFromTesting() {
        assertThat(sm.canRequestComplete("TESTING")).isTrue();
        assertThat(sm.canRequestComplete("IN_PROGRESS")).isFalse();
        assertThat(sm.canRequestComplete("DONE")).isFalse();
    }

    @Test
    void rejectsUnknownFrom() {
        assertThat(sm.canTransit("UNKNOWN", "PLANNED")).isFalse();
        assertThat(sm.canTransit(null, "PLANNED")).isFalse();
    }
}
