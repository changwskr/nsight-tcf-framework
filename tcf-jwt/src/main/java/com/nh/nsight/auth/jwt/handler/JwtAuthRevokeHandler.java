package com.nh.nsight.auth.jwt.handler;

import com.nh.nsight.auth.jwt.facade.JwtAuthFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthRevokeHandler implements TransactionHandler {
    private final JwtAuthFacade facade;

    public JwtAuthRevokeHandler(JwtAuthFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "JWT.Auth.revoke";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.revoke(request.getBody(), context);
    }
}
