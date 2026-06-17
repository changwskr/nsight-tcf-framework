package com.nh.nsight.tcf.web.config;

import com.nh.nsight.tcf.core.config.TcfProperties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "nsight.tcf", name = "transaction-log-enabled", havingValue = "true", matchIfMissing = true)
public class TcfTransactionLogDataSourceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TcfTransactionLogDataSourceConfiguration.class);

    @Bean(name = "transactionLogDataSource")
    public DataSource transactionLogDataSource(TcfProperties properties) {
        TcfProperties.TransactionLogDataSource cfg = properties.getTransactionLogDatasource();
        log.info("Transaction log datasource url={}", cfg.getUrl());
        return DataSourceBuilder.create()
                .driverClassName(cfg.getDriverClassName())
                .url(cfg.getUrl())
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
