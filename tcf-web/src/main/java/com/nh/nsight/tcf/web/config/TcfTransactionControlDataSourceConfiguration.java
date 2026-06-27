package com.nh.nsight.tcf.web.config;

import com.nh.nsight.tcf.core.config.TcfProperties;
import com.nh.nsight.tcf.core.logging.H2DevDataSourceUrls;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

/**
 * 거래통제 규칙({@code TCF_TRANSACTION_CONTROL})은 OM과 동일한 nsight_om DB를 참조해야 한다.
 * DataSource는 Spring 빈으로 등록하지 않는다 — 다중 DataSource 시 PlatformTransactionManager 자동구성이 깨지는 것을 방지.
 */
@Configuration
@ConditionalOnProperty(prefix = "nsight.tcf", name = "transaction-control-enabled", havingValue = "true", matchIfMissing = true)
public class TcfTransactionControlDataSourceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TcfTransactionControlDataSourceConfiguration.class);

    @Bean(name = "transactionControlJdbcTemplate")
    public JdbcTemplate transactionControlJdbcTemplate(TcfProperties properties, Environment environment) {
        String url = resolveUrl(properties, environment);
        log.info("Transaction control datasource url={}", url);
        TcfProperties.TransactionLogDataSource cfg = properties.getTransactionLogDatasource();
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(cfg.getDriverClassName())
                .url(url)
                .username(cfg.getUsername())
                .password(cfg.getPassword())
                .build();
        return new JdbcTemplate(dataSource);
    }

    static String resolveUrl(TcfProperties properties, Environment environment) {
        String explicit = environment.getProperty("nsight.tcf.transaction-control-datasource.url");
        if (StringUtils.hasText(explicit)) {
            return H2DevDataSourceUrls.resolveNsightOmUrl(environment, explicit.trim());
        }
        String txLog = environment.getProperty("nsight.tcf.transaction-log-datasource.url");
        if (StringUtils.hasText(txLog)) {
            return H2DevDataSourceUrls.resolveNsightOmUrl(environment, txLog.trim());
        }
        if (!properties.getTransactionLogDatasource().isSeparate()) {
            String primary = environment.getProperty("spring.datasource.url");
            if (StringUtils.hasText(primary)) {
                return H2DevDataSourceUrls.resolveNsightOmUrl(environment, primary.trim());
            }
        }
        return H2DevDataSourceUrls.resolveNsightOmUrl(environment, null);
    }
}
