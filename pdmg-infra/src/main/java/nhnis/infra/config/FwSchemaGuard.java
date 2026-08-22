package nhnis.infra.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * FW 필수 테이블 보장.
 *
 * <p>IDE 가 stale {@code build/resources} 로 sql.init 하면 {@code TB_MG_TX_CONTROL}
 * 이 빠져 {@code FW0411} 이 난다. CREATE IF NOT EXISTS 로 보강한다.
 */
@Component
@Order(0)
public class FwSchemaGuard implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(FwSchemaGuard.class);

    private final JdbcTemplate jdbcTemplate;

    public FwSchemaGuard(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void run(ApplicationArguments args) {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS TB_MG_TX_CONTROL (
                    SERVICE_ID       VARCHAR(100)  NOT NULL,
                    TRANSACTION_CODE VARCHAR(50)   NOT NULL,
                    BUSINESS_CODE    VARCHAR(10)   NOT NULL,
                    SERVICE_NAME     VARCHAR(200)  NOT NULL,
                    USER_ID          VARCHAR(50)   NOT NULL,
                    CHANNEL_ID       VARCHAR(30)   NOT NULL,
                    BRANCH_ID        VARCHAR(30)   NOT NULL,
                    CONTROL_TYPE     VARCHAR(20)   NOT NULL,
                    BLOCK_YN         VARCHAR(1)    NOT NULL,
                    CHANGE_REASON    VARCHAR(500),
                    REG_USER_ID      VARCHAR(50),
                    REG_DTM          VARCHAR(17),
                    CHG_USER_ID      VARCHAR(50),
                    CHG_DTM          VARCHAR(17),
                    CONSTRAINT TB_MG_TX_CONTROL_PK PRIMARY KEY (
                        SERVICE_ID, TRANSACTION_CODE, BUSINESS_CODE, SERVICE_NAME,
                        USER_ID, CHANNEL_ID, BRANCH_ID
                    )
                )
                """);
        jdbcTemplate.execute(
                "CREATE INDEX IF NOT EXISTS IDX_MG_TX_CTRL_TYPE ON TB_MG_TX_CONTROL (CONTROL_TYPE, BLOCK_YN)");
        log.info("[FwSchemaGuard] TB_MG_TX_CONTROL ready");
    }
}
