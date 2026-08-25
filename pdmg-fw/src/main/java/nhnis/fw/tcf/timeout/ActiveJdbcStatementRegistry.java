package nhnis.fw.tcf.timeout;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 온라인 Worker 실행 중 활성 JDBC {@link Statement} 추적.
 *
 * <p>타임아웃 시 요청 Thread에서 {@link #cancelAll(String)} 을 호출해
 * {@link Statement#cancel()} 로 SQL 강제 취소를 시도한다.
 *
 * <p>등록은 MyBatis prepare 시점(원본 Statement), 해제는 Statement.close 또는 Worker unbind.
 * cancel 은 원본 Statement 에 걸리며, JDBC API 특성상 best-effort 이다.
 */
@Component
public class ActiveJdbcStatementRegistry {

    private static final Logger log = LoggerFactory.getLogger(ActiveJdbcStatementRegistry.class);

    private final ConcurrentHashMap<String, Set<Statement>> byEvidenceKey = new ConcurrentHashMap<>();
    private final ThreadLocal<String> boundEvidenceKey = new ThreadLocal<>();

    public void bind(String evidenceKey) {
        if (!StringUtils.hasText(evidenceKey)) {
            return;
        }
        boundEvidenceKey.set(evidenceKey.trim());
    }

    public void unbind() {
        String key = boundEvidenceKey.get();
        boundEvidenceKey.remove();
        if (key != null) {
            clear(key);
        }
    }

    public void register(Statement statement) {
        if (statement == null) {
            return;
        }
        String key = boundEvidenceKey.get();
        if (key == null) {
            return;
        }
        byEvidenceKey.computeIfAbsent(key, ignored -> ConcurrentHashMap.newKeySet()).add(statement);
    }

    public void unregister(Statement statement) {
        if (statement == null) {
            return;
        }
        String key = boundEvidenceKey.get();
        if (key == null) {
            // close 가 Worker 종료 직후 올 수 있으므로 전 키에서 제거 시도
            for (Set<Statement> statements : byEvidenceKey.values()) {
                statements.remove(statement);
            }
            byEvidenceKey.entrySet().removeIf(e -> e.getValue() == null || e.getValue().isEmpty());
            return;
        }
        Set<Statement> statements = byEvidenceKey.get(key);
        if (statements != null) {
            statements.remove(statement);
            if (statements.isEmpty()) {
                byEvidenceKey.remove(key, statements);
            }
        }
    }

    /**
     * @return 실제로 {@link Statement#cancel()} 을 호출한 건수 (이미 closed/실패 제외 가능)
     */
    public int cancelAll(String evidenceKey) {
        if (!StringUtils.hasText(evidenceKey)) {
            return 0;
        }
        String key = evidenceKey.trim();
        Set<Statement> statements = byEvidenceKey.get(key);
        if (statements == null || statements.isEmpty()) {
            return 0;
        }
        // 순회 중 register/unregister 와 겹치지 않도록 스냅샷
        List<Statement> snapshot = new ArrayList<>(statements);
        int cancelled = 0;
        for (Statement statement : snapshot) {
            if (cancelQuietly(statement, key)) {
                cancelled++;
            }
        }
        if (cancelled > 0) {
            log.warn("[ONLINE-TIMEOUT][JDBC-CANCEL] evidenceKey={} candidates={} cancelInvoked={}",
                    key, snapshot.size(), cancelled);
        } else if (!snapshot.isEmpty()) {
            log.warn("[ONLINE-TIMEOUT][JDBC-CANCEL] evidenceKey={} candidates={} cancelInvoked=0",
                    key, snapshot.size());
        }
        return cancelled;
    }

    public void clear(String evidenceKey) {
        if (!StringUtils.hasText(evidenceKey)) {
            return;
        }
        byEvidenceKey.remove(evidenceKey.trim());
    }

    public int activeCount(String evidenceKey) {
        if (!StringUtils.hasText(evidenceKey)) {
            return 0;
        }
        Set<Statement> statements = byEvidenceKey.get(evidenceKey.trim());
        return statements == null ? 0 : statements.size();
    }

    String currentBoundKey() {
        return boundEvidenceKey.get();
    }

    private static boolean cancelQuietly(Statement statement, String evidenceKey) {
        if (statement == null) {
            return false;
        }
        try {
            if (statement.isClosed()) {
                return false;
            }
            statement.cancel();
            return true;
        } catch (SQLException | RuntimeException ex) {
            log.warn("[ONLINE-TIMEOUT][JDBC-CANCEL] failed evidenceKey={} message={}",
                    evidenceKey, ex.getMessage());
            return false;
        }
    }
}
