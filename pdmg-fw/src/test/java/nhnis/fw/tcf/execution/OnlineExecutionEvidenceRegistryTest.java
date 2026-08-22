package nhnis.fw.tcf.execution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nhnis.fw.commons.context.ServiceContext;
import nhnis.fw.commons.context.ServiceContextHolder;
import nhnis.fw.tcf.core.context.TransactionContext;

class OnlineExecutionEvidenceRegistryTest {

    private static final String GUID = "test-guid-001";

    private OnlineExecutionEvidenceRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new OnlineExecutionEvidenceRegistry();
        ServiceContextHolder.setInstance(new ServiceContext(null, GUID, null, null, null, null, null));
    }

    @AfterEach
    void tearDown() {
        ServiceContextHolder.removeInstance();
    }

    @Test
    void keepsWorkerActiveAfterClientTimeoutUntilWorkerTerminates() {
        TransactionContext context = TransactionContext.fromCurrent("mgcoa5530S0");
        registry.begin(context, 200L);

        registry.markQueued(GUID);
        registry.markWorkerStarted(GUID, 42L);
        registry.markTxStarted(GUID, TransactionMode.RDW_READ_WRITE, 5);
        registry.markCancelRequested(GUID);
        registry.markClientTimeout(context, 210L);
        registry.finishClient(context);

        assertThat(registry.activeCount()).isEqualTo(1);
        assertThat(registry.workerOverrunCount()).isEqualTo(1);
        assertThat(registry.snapshotActive(10)).singleElement()
                .satisfies(row -> {
                    assertThat(row.get("clientState")).isEqualTo(ClientExecutionState.TIMEOUT_RESPONSE_SENT.name());
                    assertThat(row.get("workerState")).isEqualTo(WorkerExecutionState.CANCEL_REQUESTED.name());
                    assertThat(row.get("workerOverrun")).isEqualTo(true);
                });

        registry.markWorkerRolledBack(GUID);
        registry.markWorkerTerminated(GUID);

        assertThat(registry.activeCount()).isZero();
        assertThat(registry.snapshotRecent(5)).isNotEmpty();
    }

    @Test
    void tracksWorkerStatesWithFallbackKeyWhenGuidMissing() {
        ServiceContextHolder.setInstance(new ServiceContext(null, null, null, null, null, null, null));
        TransactionContext context = TransactionContext.fromCurrent("mgcoa5530S0");
        registry.begin(context, 200L);
        String evidenceKey = ExecutionEvidenceKey.keyOf(context);

        registry.markQueued(evidenceKey);
        registry.markWorkerStarted(evidenceKey, 42L);
        registry.markCancelRequested(evidenceKey);
        registry.markClientTimeout(context, 210L);
        registry.finishClient(context);

        assertThat(evidenceKey).isNotBlank();
        assertThat(registry.snapshotActive(10)).singleElement()
                .satisfies(row -> {
                    assertThat(row.get("workerState")).isEqualTo(WorkerExecutionState.CANCEL_REQUESTED.name());
                    assertThat(row.get("workerThreadId")).isEqualTo(42L);
                });
    }

    @Test
    void archivesAfterSuccessfulClientAndWorkerCompletion() {
        ServiceContextHolder.setInstance(new ServiceContext(null, "svc-guid", null, null, null, null, null));
        TransactionContext context = TransactionContext.fromCurrent("mgcoa8888S0");
        registry.begin(context, 5000L);
        registry.markWorkerStarted("svc-guid", 99L);
        registry.markWorkerCommitted("svc-guid");
        registry.markWorkerTerminated("svc-guid");
        registry.markClientSuccess(context);
        registry.finishClient(context);

        assertThat(registry.activeCount()).isZero();
        assertThat(registry.snapshotRecent(5)).singleElement()
                .satisfies(row -> {
                    assertThat(row.get("clientState")).isEqualTo(ClientExecutionState.SUCCESS_RESPONSE_SENT.name());
                    assertThat(row.get("workerState")).isEqualTo(WorkerExecutionState.TERMINATED.name());
                });
    }
}
