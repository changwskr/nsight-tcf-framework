package nhnis.fw.tcf.timeout;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.ThreadContext;

import nhnis.fw.commons.context.ServiceContext;
import nhnis.fw.commons.context.ServiceContextHolder;
import nhnis.fw.commons.dto.header.hdr_nhnis;
import nhnis.fw.commons.dto.header.sys_comm;
import nhnis.fw.tcf.execution.ExecutionEvidenceKey;
import org.springframework.util.StringUtils;

/**
 * 요청 Thread → Worker Thread 컨텍스트 스냅샷.
 *
 * <p>Servlet request/response는 보관하지 않는다.
 */
final class OnlineTimeoutWorkerContext {

    private final ServiceContext serviceContext;
    private final Map<String, String> mdc;
    private final String guid;
    private final String evidenceKey;
    private final String serviceId;

    private OnlineTimeoutWorkerContext(ServiceContext serviceContext, Map<String, String> mdc,
            String guid, String evidenceKey, String serviceId) {
        this.serviceContext = serviceContext;
        this.mdc = mdc;
        this.guid = guid;
        this.evidenceKey = evidenceKey;
        this.serviceId = serviceId;
    }

    static OnlineTimeoutWorkerContext capture() {
        ServiceContext ctx = ServiceContextHolder.getInstance();
        Map<String, String> mdcCopy = new HashMap<>();
        Map<String, String> current = ThreadContext.getContext();
        if (current != null) {
            mdcCopy.putAll(current);
        }
        String guid = null;
        String serviceId = null;
        if (ctx != null) {
            guid = ctx.getGuid();
            hdr_nhnis header = ctx.getHeader();
            if (header != null) {
                sys_comm sys = header.getSys_comm();
                if (sys != null) {
                    if (guid == null || guid.isBlank()) {
                        guid = sys.getStd_gbl_id();
                    }
                    serviceId = sys.getRms_svc_c();
                }
            }
        }
        if (serviceId == null || serviceId.isBlank()) {
            serviceId = mdcCopy.get("serviceId");
        }
        if (guid == null || guid.isBlank()) {
            guid = mdcCopy.get("guid");
        }
        // begin()에서 assign 된 키를 우선해 cancel/evidence 와 일치시킨다.
        String evidenceKey = ExecutionEvidenceKey.fromServiceContext(ctx);
        if (!StringUtils.hasText(evidenceKey) && StringUtils.hasText(guid)) {
            evidenceKey = guid.trim();
        }
        return new OnlineTimeoutWorkerContext(ctx, mdcCopy, guid, evidenceKey, serviceId);
    }

    void install() {
        if (serviceContext != null) {
            ServiceContextHolder.setInstance(serviceContext);
        }
        ThreadContext.clearAll();
        if (!mdc.isEmpty()) {
            ThreadContext.putAll(mdc);
        }
    }

    void clear() {
        ServiceContextHolder.removeInstance();
        ThreadContext.clearAll();
    }

    String getGuid() {
        return guid;
    }

    String getEvidenceKey() {
        return evidenceKey;
    }

    String getServiceId() {
        return serviceId;
    }
}
