package nhnis.fw.mybatis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.PreparedStatement;
import java.sql.Statement;

import nhnis.fw.tcf.timeout.ActiveJdbcStatementRegistry;
import nhnis.fw.tcf.timeout.OnlineTimeoutProperties;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MybatisStatementTimeoutInterceptorTest {

    private OnlineTimeoutProperties properties;
    private ActiveJdbcStatementRegistry registry;
    private MybatisStatementTimeoutInterceptor interceptor;

    @BeforeEach
    void setUp() {
        properties = new OnlineTimeoutProperties();
        properties.setEnabled(true);
        properties.setSqlSafetyTimeoutSeconds(10);
        registry = new ActiveJdbcStatementRegistry();
        interceptor = new MybatisStatementTimeoutInterceptor(properties, registry);
        registry.bind("ev-mybatis");
    }

    @AfterEach
    void tearDown() {
        registry.unbind();
    }

    @Test
    void prepareRegistersStatementAndSetsQueryTimeout() throws Throwable {
        PreparedStatement statement = mock(PreparedStatement.class);
        MappedStatement mappedStatement = mock(MappedStatement.class);
        when(mappedStatement.getTimeout()).thenReturn(null);
        when(mappedStatement.getId()).thenReturn("demo.select");

        Invocation invocation = mock(Invocation.class);
        when(invocation.proceed()).thenReturn(statement);
        when(invocation.getTarget()).thenReturn(new HandlerTarget(mappedStatement));

        Object result = interceptor.intercept(invocation);

        assertThat(result).isInstanceOf(PreparedStatement.class);
        assertThat(result).isInstanceOf(Statement.class);
        assertThat(result).isNotSameAs(statement);
        verify(statement).setQueryTimeout(10);
        assertThat(registry.activeCount("ev-mybatis")).isEqualTo(1);

        ((Statement) result).close();
        assertThat(registry.activeCount("ev-mybatis")).isZero();
        verify(statement).close();
    }

    /** MetaObject path: delegate.mappedStatement */
    @SuppressWarnings("unused")
    static final class HandlerTarget {
        private final DelegateInner delegate;

        HandlerTarget(MappedStatement mappedStatement) {
            this.delegate = new DelegateInner(mappedStatement);
        }

        static final class DelegateInner {
            private final MappedStatement mappedStatement;

            DelegateInner(MappedStatement mappedStatement) {
                this.mappedStatement = mappedStatement;
            }
        }
    }
}
