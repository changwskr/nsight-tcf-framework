package nhnis.fw.tcf.timeout;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ActiveJdbcStatementRegistryTest {

    private final ActiveJdbcStatementRegistry registry = new ActiveJdbcStatementRegistry();

    @AfterEach
    void tearDown() {
        registry.unbind();
    }

    @Test
    void cancelAllInvokesStatementCancelForBoundKey() throws Exception {
        registry.bind("ev-1");
        AtomicInteger cancelCount = new AtomicInteger();
        Statement statement = proxyStatement(cancelCount, false, null);
        registry.register(statement);

        assertThat(registry.activeCount("ev-1")).isEqualTo(1);
        assertThat(registry.cancelAll("ev-1")).isEqualTo(1);
        assertThat(cancelCount.get()).isEqualTo(1);
    }

    @Test
    void cancelAllSkipsClosedStatements() throws Exception {
        registry.bind("ev-2");
        AtomicInteger cancelCount = new AtomicInteger();
        Statement statement = proxyStatement(cancelCount, true, null);
        registry.register(statement);

        assertThat(registry.cancelAll("ev-2")).isZero();
        assertThat(cancelCount.get()).isZero();
    }

    @Test
    void cancelAllIgnoresCancelFailures() throws Exception {
        registry.bind("ev-3");
        AtomicInteger cancelCount = new AtomicInteger();
        Statement statement = proxyStatement(cancelCount, false, new SQLException("driver-cancel-fail"));
        registry.register(statement);

        assertThat(registry.cancelAll("ev-3")).isZero();
        assertThat(cancelCount.get()).isEqualTo(1);
    }

    @Test
    void registerWithoutBindIsIgnored() {
        AtomicInteger cancelCount = new AtomicInteger();
        Statement statement = proxyStatement(cancelCount, false, null);
        registry.register(statement);

        assertThat(registry.cancelAll("orphan")).isZero();
        assertThat(cancelCount.get()).isZero();
    }

    @Test
    void unbindClearsActiveStatements() {
        registry.bind("ev-4");
        AtomicInteger cancelCount = new AtomicInteger();
        registry.register(proxyStatement(cancelCount, false, null));
        assertThat(registry.activeCount("ev-4")).isEqualTo(1);

        registry.unbind();
        assertThat(registry.activeCount("ev-4")).isZero();
        assertThat(registry.cancelAll("ev-4")).isZero();
    }

    private static Statement proxyStatement(AtomicInteger cancelCount, boolean closed, SQLException onCancel) {
        return (Statement) Proxy.newProxyInstance(
                Statement.class.getClassLoader(),
                new Class<?>[] {Statement.class},
                (proxy, method, args) -> {
                    String name = method.getName();
                    if ("cancel".equals(name)) {
                        cancelCount.incrementAndGet();
                        if (onCancel != null) {
                            throw onCancel;
                        }
                        return null;
                    }
                    if ("isClosed".equals(name)) {
                        return closed;
                    }
                    if ("equals".equals(name)) {
                        return proxy == args[0];
                    }
                    if ("hashCode".equals(name)) {
                        return System.identityHashCode(proxy);
                    }
                    if ("toString".equals(name)) {
                        return "ProxyStatement";
                    }
                    Class<?> returnType = method.getReturnType();
                    if (returnType == boolean.class) {
                        return false;
                    }
                    if (returnType == int.class) {
                        return 0;
                    }
                    if (returnType == long.class) {
                        return 0L;
                    }
                    return null;
                });
    }
}
