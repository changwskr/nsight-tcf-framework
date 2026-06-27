package com.nh.nsight.tcf.core.timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.nh.nsight.tcf.core.config.TcfProperties;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class OnlineTransactionTimeoutExecutorTest {

    private ExecutorService executorService;

    @AfterEach
    void tearDown() {
        TimeoutContextHolder.clear();
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    @Test
    void throwsOnlineTimeoutBusinessException() {
        TcfProperties properties = new TcfProperties();
        properties.setTimeoutPolicyEnabled(true);
        executorService = Executors.newSingleThreadExecutor();
        OnlineTransactionTimeoutExecutor executor =
                new OnlineTransactionTimeoutExecutor(properties, executorService);

        TimeoutPolicy policy = new TimeoutPolicy();
        policy.setOnlineTimeoutSec(1);
        TimeoutContextHolder.set(policy);

        BusinessException ex = assertThrows(BusinessException.class, () -> executor.execute(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
            return "done";
        }));

        assertEquals(ErrorCode.TIMEOUT_ONLINE, ex.getErrorCode());
    }
}
