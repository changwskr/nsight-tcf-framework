package nhnis.mg.co.a.application.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.fw.tcf.timeout.ActiveJdbcStatementRegistry;
import nhnis.mg.co.a.persistence.dao.mgcoa5530DAO;
import nhnis.mg.co.a.dto.mgcoa5530S0DTOSub0;
import nhnis.mg.co.a.dto.mgcoa5530S0DTOin;
import nhnis.mg.co.a.dto.mgcoa5530S0DTOout;

/**
 * 마케팅희망고객 조회 Service.
 *
 * @since 2026.08.07
 */
@Service
public class mgcoa5530Service {

    private static final Logger log = LoggerFactory.getLogger(mgcoa5530Service.class);

    @Autowired
    private mgcoa5530DAO mgcoa5530DAO;

    @Autowired
    @Qualifier("rdwDataSource")
    private DataSource rdwDataSource;

    @Autowired
    private ObjectProvider<ActiveJdbcStatementRegistry> statementRegistryProvider;

    /**
     * 타임아웃 E2E용 인위 지연(ms). 기본 0(정상 경로).
     * 예: --nhnis.demo.mgcoa5530-sleep-ms=12000 + overrides.mgcoa5530S0=10000 → FW_TIMEOUT
     */
    @Value("${nhnis.demo.mgcoa5530-sleep-ms:0}")
    private long demoSleepMs;

    /**
     * Statement.cancel() E2E용 JDBC 블로킹. 기본 0.
     * 값이 &gt;0 이면 H2 {@code SYSTEM_RANGE} 장시간 SQL 을 실행해 Registry.cancelAll → cancel 을 재현한다.
     * (값 자체는 “켜짐” 플래그에 가깝고, 대략적인 부하 스케일에도 사용)
     * 예: --nhnis.demo.mgcoa5530-jdbc-hold-ms=15000 + overrides.mgcoa5530S0=10000
     */
    @Value("${nhnis.demo.mgcoa5530-jdbc-hold-ms:0}")
    private long demoJdbcHoldMs;

    /**
     * 마케팅희망고객 목록 조회 (페이징).
     */
    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public mgcoa5530S0DTOout mgcoa5530S0(mgcoa5530S0DTOin input) throws Exception {
        log.info("▶▶▶▶▶▶▶▶ mgcoa5530S0 Service Start!");

        if (demoSleepMs > 0) {
            try {
                log.info("mgcoa5530S0 demo sleep start ms={}", demoSleepMs);
                Thread.sleep(demoSleepMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("mgcoa5530S0 Service sleep interrupted — abort without DAO", e);
                throw e;
            }
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("mgcoa5530S0 aborted: interrupt after demo sleep");
            }
        }

        demoJdbcHoldIfConfigured();

        Map<String, Object> param = new HashMap<>();
        if (input != null) {
            putIfHasText(param, "trtBrc", input.getTrtBrc());
            putIfHasText(param, "basDt", input.getBasDt());
        }

        int pageNo = input == null || input.getPageNo() == null || input.getPageNo() <= 0
                ? 1
                : input.getPageNo();
        int pageSize = input == null || input.getPageSize() == null || input.getPageSize() <= 0
                ? 20
                : input.getPageSize();
        if (pageSize > 100) {
            pageSize = 100;
        }
        int offset = (pageNo - 1) * pageSize;
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        param.put("offset", offset);

        int totalCount = mgcoa5530DAO.mgcoa5530S0_S0_count(param);
        List<Map<String, Object>> rows = mgcoa5530DAO.mgcoa5530S0_S0(param);

        mgcoa5530S0DTOout output = new mgcoa5530S0DTOout();
        if (rows != null) {
            for (Map<String, Object> row : rows) {
                mgcoa5530S0DTOSub0 sub = new mgcoa5530S0DTOSub0();
                sub.setL5101(asString(row, "L5101", "l5101"));
                sub.setL5102(asString(row, "L5102", "l5102"));
                sub.setL5103(asString(row, "L5103", "l5103"));
                sub.setL5104(asString(row, "L5104", "l5104"));
                output.addmgcoa5530S0DTOSub0(sub);
            }
        }
        output.setSize(output.sizemgcoa5530S0DTOSub0());
        output.setPageNo(pageNo);
        output.setPageSize(pageSize);
        output.setTotalCount(totalCount);
        output.setTotalPages(pageSize <= 0 ? 0 : (int) ((totalCount + pageSize - 1L) / pageSize));

        log.info("▶▶▶▶▶▶▶▶ mgcoa5530S0 Service End! - Total: " + totalCount);
        return output;
    }

    private void putIfHasText(Map<String, Object> param, String key, String value) {
        if (value != null && !value.isBlank()) {
            param.put(key, value.trim());
        }
    }

    private String asString(Map<String, Object> row, String upperKey, String camelKey) {
        if (row == null) {
            return null;
        }
        Object value = row.get(upperKey);
        if (value == null) {
            value = row.get(camelKey);
        }
        if (value == null) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (entry.getKey() != null
                        && (entry.getKey().equalsIgnoreCase(upperKey)
                                || entry.getKey().equalsIgnoreCase(camelKey))
                        && entry.getValue() != null) {
                    value = entry.getValue();
                    break;
                }
            }
        }
        return value == null ? null : String.valueOf(value);
    }

    /**
     * H2 장시간 SQL + Registry 등록으로 {@link Statement#cancel()} 라이브 경로를 재현한다.
     * (Java ALIAS sleep 은 H2 cancel 만으로 깨지지 않는 경우가 많아 SYSTEM_RANGE 사용)
     */
    private void demoJdbcHoldIfConfigured() throws SQLException, InterruptedException {
        if (demoJdbcHoldMs <= 0) {
            return;
        }
        ActiveJdbcStatementRegistry registry = statementRegistryProvider.getIfAvailable();
        if (registry == null) {
            log.warn("mgcoa5530S0 demo JDBC hold skipped: ActiveJdbcStatementRegistry unavailable");
            return;
        }
        // hold-ms 를 대략적인 부하 스케일로 사용 (최소 2천만 row)
        long rangeEnd = Math.max(20_000_000L, demoJdbcHoldMs * 2_000L);
        String sql = "SELECT SUM(X) FROM SYSTEM_RANGE(1, " + rangeEnd + ")";
        Connection connection = DataSourceUtils.getConnection(rdwDataSource);
        Statement statement = connection.createStatement();
        registry.register(statement);
        try {
            log.info("mgcoa5530S0 demo JDBC hold start rangeEnd={} (cancelable SQL)", rangeEnd);
            statement.executeQuery(sql);
            log.info("mgcoa5530S0 demo JDBC hold finished normally");
        } catch (SQLException ex) {
            if (Thread.currentThread().isInterrupted()) {
                log.warn("mgcoa5530S0 demo JDBC hold cancelled (interrupt) — abort without DAO: {}",
                        ex.getMessage());
                throw new InterruptedException("mgcoa5530S0 aborted: JDBC cancel/interrupt");
            }
            log.warn("mgcoa5530S0 demo JDBC hold cancelled/failed — abort without DAO: {}",
                    ex.getMessage());
            throw ex;
        } finally {
            registry.unregister(statement);
            try {
                statement.close();
            } catch (SQLException ignored) {
                // ignore
            }
            DataSourceUtils.releaseConnection(connection, rdwDataSource);
        }
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("mgcoa5530S0 aborted: interrupt after demo JDBC hold");
        }
    }
}
