package com.nh.nsight.marketing.eb.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class EbDatabaseMigration implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(EbDatabaseMigration.class);

    private final JdbcTemplate jdbcTemplate;

    public EbDatabaseMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS EB_USER (
                    USER_ID     VARCHAR(50) PRIMARY KEY,
                    USER_NAME   VARCHAR(100),
                    BRANCH_ID   VARCHAR(20),
                    CREATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """);
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS EB_EVENT (
                    EVENT_ID      VARCHAR(50) PRIMARY KEY,
                    USER_ID       VARCHAR(50) NOT NULL,
                    EVENT_TYPE    VARCHAR(30) NOT NULL,
                    EVENT_STATUS  VARCHAR(20) NOT NULL,
                    RETRY_COUNT   INT DEFAULT 0,
                    CREATED_AT    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    SENT_AT       TIMESTAMP
                )
                """);
        jdbcTemplate.execute("""
                CREATE INDEX IF NOT EXISTS IX_EB_EVENT_STATUS ON EB_EVENT (EVENT_STATUS, CREATED_AT)
                """);
        log.debug("EB schema migration applied.");
    }
}
