package com.nh.nsight.tcf.core.config;

import com.nh.nsight.tcf.core.logging.TcfTransactionLogConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nsight.tcf")
public class TcfProperties {
    private boolean sessionValidationEnabled = false;
    private boolean authorizationValidationEnabled = false;
    private boolean idempotencyEnabled = true;
    private boolean auditEnabled = true;
    private boolean transactionLogEnabled = true;
    private boolean transactionLogSchemaAutoInit = true;
    private String transactionLogTableName = TcfTransactionLogConstants.TABLE_NAME;
    private final TransactionLogDataSource transactionLogDatasource = new TransactionLogDataSource();

    public boolean isSessionValidationEnabled() { return sessionValidationEnabled; }
    public void setSessionValidationEnabled(boolean sessionValidationEnabled) { this.sessionValidationEnabled = sessionValidationEnabled; }
    public boolean isAuthorizationValidationEnabled() { return authorizationValidationEnabled; }
    public void setAuthorizationValidationEnabled(boolean authorizationValidationEnabled) { this.authorizationValidationEnabled = authorizationValidationEnabled; }
    public boolean isIdempotencyEnabled() { return idempotencyEnabled; }
    public void setIdempotencyEnabled(boolean idempotencyEnabled) { this.idempotencyEnabled = idempotencyEnabled; }
    public boolean isAuditEnabled() { return auditEnabled; }
    public void setAuditEnabled(boolean auditEnabled) { this.auditEnabled = auditEnabled; }
    public boolean isTransactionLogEnabled() { return transactionLogEnabled; }
    public void setTransactionLogEnabled(boolean transactionLogEnabled) { this.transactionLogEnabled = transactionLogEnabled; }
    public boolean isTransactionLogSchemaAutoInit() { return transactionLogSchemaAutoInit; }
    public void setTransactionLogSchemaAutoInit(boolean transactionLogSchemaAutoInit) { this.transactionLogSchemaAutoInit = transactionLogSchemaAutoInit; }
    public String getTransactionLogTableName() { return transactionLogTableName; }
    public void setTransactionLogTableName(String transactionLogTableName) { this.transactionLogTableName = transactionLogTableName; }
    public TransactionLogDataSource getTransactionLogDatasource() { return transactionLogDatasource; }

    public static class TransactionLogDataSource {
        private boolean separate = true;
        private String url = TcfTransactionLogConstants.DEFAULT_DATASOURCE_URL;
        private String username = "sa";
        private String password = "";
        private String driverClassName = "org.h2.Driver";

        public boolean isSeparate() { return separate; }
        public void setSeparate(boolean separate) { this.separate = separate; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getDriverClassName() { return driverClassName; }
        public void setDriverClassName(String driverClassName) { this.driverClassName = driverClassName; }
    }
}
