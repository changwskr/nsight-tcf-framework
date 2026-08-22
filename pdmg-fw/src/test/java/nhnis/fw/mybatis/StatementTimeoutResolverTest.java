package nhnis.fw.mybatis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.ibatis.mapping.MappedStatement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import nhnis.fw.tcf.execution.ExecutionDeadline;
import nhnis.fw.tcf.execution.ExecutionDeadlineContext;

class StatementTimeoutResolverTest {

    @AfterEach
    void tearDown() {
        ExecutionDeadlineContext.clear();
    }

    @Test
    void usesSafetyCeilingWhenNoDeadline() {
        MappedStatement ms = mappedStatement(null);
        assertThat(StatementTimeoutResolver.resolve(ms, 10)).isEqualTo(10);
    }

    @Test
    void capsByRemainingDeadline() {
        MappedStatement ms = mappedStatement(null);
        ExecutionDeadline deadline = ExecutionDeadline.start(5000);
        ExecutionDeadlineContext.bind(deadline);
        int expected = StatementTimeoutResolver.toConservativeTimeoutSeconds(deadline.remainingMillis());
        assertThat(StatementTimeoutResolver.resolve(ms, 10)).isEqualTo(Math.min(10, expected));
    }

    @Test
    void respectsMapperTimeoutAndCapsByRemaining() {
        MappedStatement ms = mappedStatement(8);
        ExecutionDeadline deadline = ExecutionDeadline.start(3000);
        ExecutionDeadlineContext.bind(deadline);
        int remainingSec = StatementTimeoutResolver.toConservativeTimeoutSeconds(deadline.remainingMillis());
        assertThat(StatementTimeoutResolver.resolve(ms, 10)).isEqualTo(Math.min(8, remainingSec));
    }

    @Test
    void toConservativeTimeoutSecondsUsesFloorDivision() {
        assertThat(StatementTimeoutResolver.toConservativeTimeoutSeconds(5000)).isEqualTo(5);
        assertThat(StatementTimeoutResolver.toConservativeTimeoutSeconds(1500)).isEqualTo(1);
    }

    private static MappedStatement mappedStatement(Integer timeout) {
        MappedStatement ms = mock(MappedStatement.class);
        when(ms.getTimeout()).thenReturn(timeout);
        return ms;
    }
}
