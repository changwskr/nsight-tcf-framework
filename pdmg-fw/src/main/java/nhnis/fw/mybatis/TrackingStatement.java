package nhnis.fw.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nhnis.fw.tcf.timeout.ActiveJdbcStatementRegistry;

/**
 * MyBatis가 사용하는 {@link Statement} 래퍼.
 *
 * <p>실제 JDBC Statement 를 레지스트리에 등록하고, {@code close()} 시 unregister 한다.
 * 타임아웃 스레드의 {@link Statement#cancel()} 은 원본 Statement 에 걸린다.
 *
 * <p>원본이 {@link PreparedStatement}/{@link CallableStatement} 이면 동일 인터페이스로
 * 프록시하여 MyBatis 캐스팅이 깨지지 않게 한다.
 */
final class TrackingStatement {

    private TrackingStatement() {
    }

    static Statement wrap(Statement statement, ActiveJdbcStatementRegistry registry) {
        if (statement == null || registry == null) {
            return statement;
        }
        registry.register(statement);
        InvocationHandler handler = new TrackingHandler(statement, registry);
        return (Statement) Proxy.newProxyInstance(
                statement.getClass().getClassLoader(),
                interfacesFor(statement),
                handler);
    }

    private static Class<?>[] interfacesFor(Statement statement) {
        List<Class<?>> interfaces = new ArrayList<>(3);
        interfaces.add(Statement.class);
        if (statement instanceof CallableStatement) {
            interfaces.add(CallableStatement.class);
            interfaces.add(PreparedStatement.class);
        } else if (statement instanceof PreparedStatement) {
            interfaces.add(PreparedStatement.class);
        }
        return interfaces.toArray(Class<?>[]::new);
    }

    private static final class TrackingHandler implements InvocationHandler {
        private final Statement delegate;
        private final ActiveJdbcStatementRegistry registry;

        private TrackingHandler(Statement delegate, ActiveJdbcStatementRegistry registry) {
            this.delegate = delegate;
            this.registry = registry;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String name = method.getName();
            if ("close".equals(name)) {
                try {
                    return method.invoke(delegate, args);
                } finally {
                    registry.unregister(delegate);
                }
            }
            if ("equals".equals(name)) {
                Object other = args[0];
                if (other == proxy) {
                    return true;
                }
                return delegate.equals(other);
            }
            if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            }
            if ("unwrap".equals(name) && args != null && args.length == 1 && args[0] instanceof Class<?> type) {
                if (type.isInstance(delegate)) {
                    return type.cast(delegate);
                }
                if (type.isInstance(proxy)) {
                    return type.cast(proxy);
                }
                return method.invoke(delegate, args);
            }
            if ("isWrapperFor".equals(name) && args != null && args.length == 1 && args[0] instanceof Class<?> type) {
                return type.isInstance(delegate) || type.isInstance(proxy) || (Boolean) method.invoke(delegate, args);
            }
            try {
                return method.invoke(delegate, args);
            } catch (java.lang.reflect.InvocationTargetException ex) {
                throw ex.getCause() != null ? ex.getCause() : ex;
            }
        }
    }
}
