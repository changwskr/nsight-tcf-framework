package nhnis.fw.tcf.timeout;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import jakarta.annotation.PreDestroy;
import nhnis.fw.tcf.execution.OnlineTransactionPolicyProperties;
import nhnis.fw.tcf.execution.PropertiesTransactionPolicyResolver;
import nhnis.fw.tcf.execution.TransactionManagerRegistry;
import nhnis.fw.tcf.execution.TransactionPolicyResolver;

/**
 * 온라인 타임아웃 Executor 구성.
 *
 * <p>{@code nhnis.fw.timeout.enabled=true} 일 때 Worker Pool + TransactionTemplate 경로를 활성한다.
 * TransactionManager는 {@link TransactionPolicyResolver} + {@link TransactionManagerRegistry} 로 해석한다.
 */
@Configuration
@EnableConfigurationProperties({OnlineTimeoutProperties.class, OnlineTransactionPolicyProperties.class})
public class OnlineTimeoutConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OnlineTimeoutConfiguration.class);

    private ThreadPoolTaskExecutor onlineTaskExecutor;

    @Bean
    public TransactionPolicyResolver transactionPolicyResolver(OnlineTransactionPolicyProperties properties) {
        return new PropertiesTransactionPolicyResolver(properties);
    }

    @Bean
    @ConditionalOnProperty(name = "nhnis.fw.timeout.enabled", havingValue = "false", matchIfMissing = true)
    public OnlineTimeoutExecutor syncOnlineTimeoutExecutor() {
        log.info("[ONLINE-TIMEOUT] disabled — SyncOnlineTimeoutExecutor");
        return new SyncOnlineTimeoutExecutor();
    }

    @Bean(name = "pdmgOnlineTimeoutTaskExecutor")
    @ConditionalOnProperty(name = "nhnis.fw.timeout.enabled", havingValue = "true")
    public ThreadPoolTaskExecutor pdmgOnlineTimeoutTaskExecutor(OnlineTimeoutProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("pdmg-online-");
        executor.setCorePoolSize(properties.getPoolSize());
        executor.setMaxPoolSize(properties.getPoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        this.onlineTaskExecutor = executor;
        log.info("[ONLINE-TIMEOUT] pool ready poolSize={} queueCapacity={} timeoutMs={}",
                properties.getPoolSize(), properties.getQueueCapacity(), properties.getMilliseconds());
        return executor;
    }

    @Bean
    @ConditionalOnProperty(name = "nhnis.fw.timeout.enabled", havingValue = "true")
    public OnlineTimeoutExecutor defaultOnlineTimeoutExecutor(
            OnlineTimeoutProperties properties,
            @org.springframework.beans.factory.annotation.Qualifier("pdmgOnlineTimeoutTaskExecutor")
            ThreadPoolTaskExecutor taskExecutor,
            TransactionPolicyResolver policyResolver,
            TransactionManagerRegistry transactionManagerRegistry,
            nhnis.fw.tcf.execution.OnlineExecutionEvidenceRegistry evidenceRegistry) {
        return new DefaultOnlineTimeoutExecutor(
                properties, taskExecutor, policyResolver, transactionManagerRegistry, evidenceRegistry);
    }

    @PreDestroy
    void shutdown() {
        if (onlineTaskExecutor != null) {
            onlineTaskExecutor.shutdown();
        }
    }
}
