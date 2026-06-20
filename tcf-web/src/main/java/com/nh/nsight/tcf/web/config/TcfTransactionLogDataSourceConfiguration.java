package com.nh.nsight.tcf.web.config;

import com.nh.nsight.tcf.core.config.TcfProperties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "nsight.tcf", name = "transaction-log-enabled", havingValue = "true", matchIfMissing = true)
public class TcfTransactionLogDataSourceConfiguration {

    @Configuration
    @ConditionalOnProperty(prefix = "nsight.tcf.transaction-log-datasource", name = "separate", havingValue = "true", matchIfMissing = true)
    static class SeparateTransactionLogDataSourceConfiguration {

        private static final Logger log = LoggerFactory.getLogger(SeparateTransactionLogDataSourceConfiguration.class);

        @Bean(name = "transactionLogDataSource")
        public DataSource transactionLogDataSource(TcfProperties properties, Environment environment) {
            TcfProperties.TransactionLogDataSource cfg = properties.getTransactionLogDatasource();
            String url = environment.resolvePlaceholders(cfg.getUrl());
            log.info("Transaction log datasource url={}", url);
            return DataSourceBuilder.create()
                    .driverClassName(cfg.getDriverClassName())
                    .url(url)
                    .username(cfg.getUsername())
                    .password(cfg.getPassword())
                    .build();
        }

        @Bean(name = "transactionLogJdbcTemplate")
        public JdbcTemplate transactionLogJdbcTemplate(
                @Qualifier("transactionLogDataSource") DataSource transactionLogDataSource) {
            return new JdbcTemplate(transactionLogDataSource);
        }
    }
}
