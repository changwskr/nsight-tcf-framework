package com.nh.nsight.auth.jwt.handler;

import com.nh.nsight.auth.jwt.facade.JwtAdminFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenRevokeHandler implements TransactionHandler {
    private final JwtAdminFacade facade;

    public JwtTokenRevokeHandler(JwtAdminFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "JWT.Token.revoke";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.revokeTokenByJti(request.getBody(), context);
    }
}
