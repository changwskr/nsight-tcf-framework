package com.nh.nsight.tcf.batch.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class BatchDatabaseMigration implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(BatchDatabaseMigration.class);

    private final JdbcTemplate jdbcTemplate;

    public BatchDatabaseMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        ensureSessionStatusTable();
        ensureDeployStatusTable();
        removeEmptySessionStatus();
        removeEmptyDeployStatus();
        removeEmptyDbStatus();
    }

    private void removeEmptyDbStatus() {
        jdbcTemplate.update("""
                DELETE FROM OM_DB_STATUS
                 WHERE DB_ID IN ('RDW', 'ADW', 'SESSIONDB')
                    OR (
                        HEALTH_STATUS IN ('FAIL', 'DOWN')
                        AND COALESCE(POOL_USAGE_PCT, 0) = 0
                    )
                """);
    }

    private void removeEmptyDeployStatus() {
        jdbcTemplate.update("""
                DELETE FROM OM_DEPLOY_STATUS
                 WHERE HEALTH_STATUS IN ('FAIL', 'DOWN')
                    OR BUSINESS_CODE = 'ET'
                """);
    }

    private void removeEmptySessionStatus() {
        jdbcTemplate.update("""
                DELETE FROM OM_SESSION_STATUS
                 WHERE HEALTH_STATUS IN ('FAIL', 'DOWN')
                   AND COALESCE(ACTIVE_COUNT, 0) = 0
                   AND COALESCE(EXPIRED_COUNT, 0) = 0
                   AND COALESCE(TOTAL_COUNT, 0) = 0
                   AND COALESCE(UNIQUE_USER_COUNT, 0) = 0
                """);
    }

    private void ensureDeployStatusTable() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS OM_DEPLOY_STATUS (
                    BUSINESS_CODE VARCHAR(10) NOT NULL,
                    WAR_NAME VARCHAR(50),
                    WAR_VERSION VARCHAR(50),
                    DEPLOYED_AT VARCHAR(40),
                    HEALTH_STATUS VARCHAR(20),
                    PRIMARY KEY (BUSINESS_CODE)
                )
                """);
        log.info("OM_DEPLOY_STATUS table ensured");
    }

    private void ensureSessionStatusTable() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS OM_SESSION_STATUS (
                    SCOPE_ID VARCHAR(50) NOT NULL,
                    SCOPE_NAME VARCHAR(100),
                    ACTIVE_COUNT INT,
                    EXPIRED_COUNT INT,
                    TOTAL_COUNT INT,
                    UNIQUE_USER_COUNT INT,
                    HEALTH_STATUS VARCHAR(20),
                    CHECKED_AT VARCHAR(40),
                    PRIMARY KEY (SCOPE_ID)
                )
                """);
        log.info("OM_SESSION_STATUS table ensured");
    }
}
