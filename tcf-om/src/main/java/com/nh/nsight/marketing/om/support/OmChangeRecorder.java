package com.nh.nsight.marketing.om.support;

import com.nh.nsight.tcf.core.context.TransactionContext;
import com.nh.nsight.tcf.util.DateTimeUtil;
import com.nh.nsight.marketing.om.dao.OmOperationDao;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OmChangeRecorder {
    private final OmOperationDao dao;

    public OmChangeRecorder(OmOperationDao dao) {
        this.dao = dao;
    }

    public void recordAdminAudit(TransactionContext context, String functionId, String functionName,
                                 String reason, String resultStatus) {
        Map<String, Object> row = new HashMap<>();
        row.put("auditId", "AUD-" + UUID.randomUUID());
        row.put("auditTime", DateTimeUtil.nowKst());
        row.put("userId", context.getHeader().getUserId());
        row.put("branchId", context.getHeader().getBranchId());
        row.put("customerNo", null);
        row.put("functionId", functionId);
        row.put("functionName", functionName);
        row.put("inquiryReason", reason);
        row.put("resultStatus", resultStatus);
        row.put("clientIp", context.getHeader().getClientIp());
        dao.insertAuditLog(row);
    }
}
