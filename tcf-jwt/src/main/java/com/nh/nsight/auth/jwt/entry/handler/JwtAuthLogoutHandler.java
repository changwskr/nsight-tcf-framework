package com.nh.nsight.auth.jwt.entry.handler;

import com.nh.nsight.auth.jwt.entry.facade.JwtAuthFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthLogoutHandler implements TransactionHandler {
    private final JwtAuthFacade facade;

    public JwtAuthLogoutHandler(JwtAuthFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "JWT.Auth.logout";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.logout(request.getBody(), context);
    }
}
