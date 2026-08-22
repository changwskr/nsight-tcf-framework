package nhnis.fw.tcf.execution;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Spring에 등록된 {@link PlatformTransactionManager} Bean을 이름으로 조회한다.
 */
@Component
public class TransactionManagerRegistry {

    private final Map<String, PlatformTransactionManager> managers;

    public TransactionManagerRegistry(Map<String, PlatformTransactionManager> managers) {
        this.managers = Map.copyOf(managers);
    }

    public PlatformTransactionManager require(String beanName) {
        PlatformTransactionManager manager = managers.get(beanName);
        if (manager == null) {
            throw new IllegalStateException("Unknown PlatformTransactionManager bean: " + beanName
                    + ". Registered: " + managers.keySet());
        }
        return manager;
    }
}
