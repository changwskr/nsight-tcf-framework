package nhnis.fw.mybatis;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import nhnis.fw.tcf.timeout.OnlineTimeoutProperties;

/**
 * JDBC {@link Statement#setQueryTimeout(int)} 를 Service deadline remaining 과 연계한다.
 */
@Component
@ConditionalOnProperty(name = "nhnis.fw.timeout.enabled", havingValue = "true")
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class MybatisStatementTimeoutInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(MybatisStatementTimeoutInterceptor.class);

    private final OnlineTimeoutProperties timeoutProperties;

    public MybatisStatementTimeoutInterceptor(OnlineTimeoutProperties timeoutProperties) {
        this.timeoutProperties = timeoutProperties;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement = (Statement) invocation.proceed();
        if (statement == null) {
            return null;
        }

        MappedStatement mappedStatement = mappedStatement(invocation.getTarget());
        int timeoutSeconds = StatementTimeoutResolver.resolve(
                mappedStatement,
                timeoutProperties.getSqlSafetyTimeoutSeconds());

        if (timeoutSeconds > 0) {
            statement.setQueryTimeout(timeoutSeconds);
            if (log.isDebugEnabled()) {
                log.debug("[MYBATIS-TIMEOUT] sqlId={} queryTimeoutSec={}",
                        mappedStatement.getId(),
                        timeoutSeconds);
            }
        }
        return statement;
    }

    private static MappedStatement mappedStatement(Object target) {
        MetaObject metaObject = SystemMetaObject.forObject(unwrap(target));
        return (MappedStatement) metaObject.getValue("delegate.mappedStatement");
    }

    private static Object unwrap(Object target) {
        if (Plugin.class.isAssignableFrom(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return unwrap(metaObject.getValue("target"));
        }
        return target;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
