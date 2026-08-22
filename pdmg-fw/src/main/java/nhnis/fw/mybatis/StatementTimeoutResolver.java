package nhnis.fw.mybatis;

import org.apache.ibatis.mapping.MappedStatement;

import nhnis.fw.tcf.execution.ExecutionDeadline;
import nhnis.fw.tcf.execution.ExecutionDeadlineContext;

/**
 * MyBatis/JDBC Statement timeout(초) 계산.
 *
 * <p>{@code min(mapperTimeout or safetyCeiling, deadline remaining)} 를 적용한다.
 */
public final class StatementTimeoutResolver {

    private StatementTimeoutResolver() {
    }

    public static int resolve(MappedStatement mappedStatement, int sqlSafetyTimeoutSeconds) {
        ExecutionDeadline deadline = ExecutionDeadlineContext.current();
        int configured = resolveConfiguredTimeout(mappedStatement, sqlSafetyTimeoutSeconds);
        if (deadline == null) {
            return configured;
        }
        int remainingSeconds = toConservativeTimeoutSeconds(deadline.remainingMillis());
        return Math.min(configured, remainingSeconds);
    }

    static int resolveConfiguredTimeout(MappedStatement mappedStatement, int sqlSafetyTimeoutSeconds) {
        Integer mapperTimeout = mappedStatement.getTimeout();
        if (mapperTimeout != null && mapperTimeout > 0) {
            return mapperTimeout;
        }
        return Math.max(1, sqlSafetyTimeoutSeconds);
    }

    public static int toConservativeTimeoutSeconds(long remainingMs) {
        return Math.max(1, (int) (remainingMs / 1000L));
    }
}
