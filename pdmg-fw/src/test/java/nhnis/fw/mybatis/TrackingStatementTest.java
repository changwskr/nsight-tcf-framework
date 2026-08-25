package nhnis.fw.mybatis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;

import nhnis.fw.tcf.timeout.ActiveJdbcStatementRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * TrackingStatement 래퍼 — MyBatis PreparedStatement 캐스팅 회귀 방지.
 */
@DisplayName("TrackingStatement 래퍼")
class TrackingStatementTest {

    private ActiveJdbcStatementRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new ActiveJdbcStatementRegistry();
        registry.bind("ev-track");
    }

    @AfterEach
    void tearDown() {
        registry.unbind();
    }

    @Test
    @DisplayName("PreparedStatement 원본은 PreparedStatement 로 캐스팅 가능해야 한다")
    void wrapsPreparedStatementPreservingType() throws Exception {
        PreparedStatement raw = mock(PreparedStatement.class);
        when(raw.isClosed()).thenReturn(false);

        Statement wrapped = TrackingStatement.wrap(raw, registry);

        assertThat(wrapped).isInstanceOf(PreparedStatement.class);
        assertThat(wrapped).isInstanceOf(Statement.class);
        assertThat(registry.activeCount("ev-track")).isEqualTo(1);

        ((PreparedStatement) wrapped).cancel();
        verify(raw).cancel();

        wrapped.close();
        verify(raw).close();
        assertThat(registry.activeCount("ev-track")).isZero();
    }

    @Test
    @DisplayName("CallableStatement 원본은 CallableStatement/PreparedStatement 캐스팅이 가능해야 한다")
    void wrapsCallableStatementPreservingType() throws Exception {
        CallableStatement raw = mock(CallableStatement.class);
        Statement wrapped = TrackingStatement.wrap(raw, registry);

        assertThat(wrapped).isInstanceOf(CallableStatement.class);
        assertThat(wrapped).isInstanceOf(PreparedStatement.class);
        assertThat(registry.activeCount("ev-track")).isEqualTo(1);
    }

    @Test
    @DisplayName("일반 Statement 도 등록·close 해제된다")
    void wrapsPlainStatement() throws Exception {
        Statement raw = mock(Statement.class);
        Statement wrapped = TrackingStatement.wrap(raw, registry);

        assertThat(wrapped).isInstanceOf(Statement.class);
        assertThat(wrapped).isNotInstanceOf(PreparedStatement.class);
        assertThat(registry.activeCount("ev-track")).isEqualTo(1);
        wrapped.close();
        assertThat(registry.activeCount("ev-track")).isZero();
    }
}
