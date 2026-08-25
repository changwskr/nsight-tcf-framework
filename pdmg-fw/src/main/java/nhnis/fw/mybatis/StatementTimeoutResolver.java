package nhnis.fw.mybatis;

import org.apache.ibatis.mapping.MappedStatement;

import nhnis.fw.tcf.execution.ExecutionDeadline;
import nhnis.fw.tcf.execution.ExecutionDeadlineContext;

/**
 * MyBatis/JDBC Statement timeout(초) 계산.
 *
 * <p>{@code min(mapperTimeout or safetyCeiling, deadline remaining)} 를 적용한다.
 *
 * <p>JDBC {@code setQueryTimeout}/{@code TransactionTemplate} timeout 은 초 단위라
 * remaining &lt; 1s 이면 최소 1초로 올려 예산보다 길어질 수 있다.
 * 그 구간은 {@link nhnis.fw.tcf.timeout.DefaultOnlineTimeoutExecutor} 가
 * deadline 시각에 {@link java.sql.Statement#cancel()} 을 걸어 보완한다.
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

    /**
     * JDBC/Spring TX 초 단위 timeout. 최소 1초.
     *
     * <p>remainingMs=999 → 1초(예산 초과 가능). 이 경우
     * {@link #needsJdbcCancelComplement(long)} 가 true 이다.
     */
    public static int toConservativeTimeoutSeconds(long remainingMs) {
        return Math.max(1, (int) (remainingMs / 1000L));
    }

    /**
     * 초 단위 timeout 이 remaining 예산보다 길면 Statement.cancel 로 보완해야 한다.
     */
    public static boolean needsJdbcCancelComplement(long remainingMs) {
        if (remainingMs <= 0L) {
            return true;
        }
        long jdbcFloorMs = toConservativeTimeoutSeconds(remainingMs) * 1000L;
        return jdbcFloorMs > remainingMs;
    }
}
