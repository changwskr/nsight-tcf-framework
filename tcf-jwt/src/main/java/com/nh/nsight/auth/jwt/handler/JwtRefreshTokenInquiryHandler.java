package com.nh.nsight.auth.jwt.handler;

import com.nh.nsight.auth.jwt.facade.JwtAdminFacade;
import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.core.message.StandardRequest;
import com.nh.nsight.tcf.core.transaction.TransactionHandler;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtRefreshTokenInquiryHandler implements TransactionHandler {
    private final JwtAdminFacade facade;

    public JwtRefreshTokenInquiryHandler(JwtAdminFacade facade) {
        this.facade = facade;
    }

    @Override
    public String serviceId() {
        return "JWT.RefreshToken.inquiry";
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiryRefreshTokens(request.getBody(), context);
    }
}
