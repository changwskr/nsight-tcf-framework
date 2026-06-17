package com.nh.nsight.tcf.web.config;

import com.nh.nsight.tcf.core.config.TcfProperties;
import com.nh.nsight.tcf.core.logging.TransactionLogRepository;
import com.nh.nsight.tcf.web.logging.JdbcTransactionLogRepository;
import com.nh.nsight.tcf.web.logging.TransactionLogSchemaInitializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class TcfTransactionLogConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "nsight.tcf", name = "transaction-log-enabled", havingValue = "true", matchIfMissing = true)
    public TransactionLogRepository jdbcTransactionLogRepository(
            @Qualifier("transactionLogJdbcTemplate") JdbcTemplate transactionLogJdbcTemplate,
            TcfProperties properties) {
        return new JdbcTransactionLogRepository(transactionLogJdbcTemplate, properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "nsight.tcf", name = "transaction-log-schema-auto-init", havingValue = "true", matchIfMissing = true)
    public TransactionLogSchemaInitializer transactionLogSchemaInitializer(
            @Qualifier("transactionLogJdbcTemplate") JdbcTemplate transactionLogJdbcTemplate,
            TcfProperties properties) {
        return new TransactionLogSchemaInitializer(transactionLogJdbcTemplate, properties);
    }
}
