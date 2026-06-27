package com.nh.nsight.gateway.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class GatewayLoginSessionSupport {
    public static final String OM_AUTH_LOGIN = "OM.Auth.login";
    public static final String SESSION_USER = "GW_OM_SESSION_USER";

    private final ObjectMapper objectMapper;

    public GatewayLoginSessionSupport(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Optional<GatewayOmSessionUser> tryStoreOmLogin(String requestBody, String responseBody) {
        if (!StringUtils.hasText(requestBody) || !StringUtils.hasText(responseBody)) {
            return Optional.empty();
        }
        try {
            Map<String, Object> request = objectMapper.readValue(requestBody, new TypeReference<>() {
            });
            if (!OM_AUTH_LOGIN.equals(extractServiceId(request))) {
                return Optional.empty();
            }
            Map<String, Object> response = objectMapper.readValue(responseBody, new TypeReference<>() {
            });
            if (!isSuccess(response)) {
                return Optional.empty();
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> body = (Map<String, Object>) response.get("body");
            if (body == null || !Boolean.TRUE.equals(body.get("loggedIn"))) {
                return Optional.empty();
            }
            return Optional.of(storeSession(body));
        } catch (Exception e) {
            GatewayProxyTrace.log("GatewayLoginSessionSupport.tryStoreOmLogin",
                    "skip reason=" + e.getClass().getSimpleName() + " message=" + e.getMessage());
            return Optional.empty();
        }
    }

    private GatewayOmSessionUser storeSession(Map<String, Object> body) {
        String phase = "GatewayLoginSessionSupport.storeSession";
        GatewayProxyTrace.start(phase);
        HttpServletRequest request = currentRequest()
                .orElseThrow(() -> new IllegalStateException("HTTP 요청 컨텍스트가 없습니다."));
        HttpSession session = request.getSession(true);
        GatewayOmSessionUser user = new GatewayOmSessionUser(
                stringValue(body, "userId"),
                stringValue(body, "userName"),
                stringValue(body, "branchId"),
                stringValue(body, "authGroupId"),
                stringValue(body, "authGroupName"),
                stringValue(body, "sessionId"));
        session.setAttribute(SESSION_USER, user);
        GatewayProxyTrace.log(phase, "sessionAttribute=" + SESSION_USER);
        GatewayProxyTrace.log(phase, "gatewaySessionId=" + session.getId());
        GatewayProxyTrace.log(phase, "userId=" + user.userId());
        GatewayProxyTrace.log(phase, "userName=" + user.userName());
        GatewayProxyTrace.log(phase, "branchId=" + user.branchId());
        GatewayProxyTrace.log(phase, "authGroupId=" + user.authGroupId());
        GatewayProxyTrace.log(phase, "authGroupName=" + user.authGroupName());
        GatewayProxyTrace.log(phase, "omSessionId=" + user.omSessionId());
        GatewayProxyTrace.log(phase, "loggedIn=true");
        GatewayProxyTrace.end(phase);
        return user;
    }

    public Optional<String> currentGatewaySessionId() {
        return currentRequest()
                .map(request -> request.getSession(false))
                .map(HttpSession::getId);
    }

    public void printLoginSession(String phase, GatewayOmSessionUser user) {
        currentGatewaySessionId().ifPresent(sessionId -> GatewayProxyTrace.log(phase, "gatewaySessionId=" + sessionId));
        GatewayProxyTrace.log(phase, "loginSession userId=" + user.userId()
                + " userName=" + user.userName()
                + " branchId=" + user.branchId()
                + " authGroupId=" + user.authGroupId()
                + " authGroupName=" + user.authGroupName()
                + " omSessionId=" + user.omSessionId()
                + " loggedIn=true");
    }

    public Optional<GatewayOmSessionUser> currentUser() {
        return currentRequest()
                .map(request -> request.getSession(false))
                .map(session -> session.getAttribute(SESSION_USER))
                .filter(GatewayOmSessionUser.class::isInstance)
                .map(GatewayOmSessionUser.class::cast);
    }

    public String extractServiceId(String requestBody) {
        if (!StringUtils.hasText(requestBody)) {
            return null;
        }
        try {
            Map<String, Object> request = objectMapper.readValue(requestBody, new TypeReference<>() {
            });
            return extractServiceId(request);
        } catch (Exception e) {
            return null;
        }
    }

    private String extractServiceId(Map<String, Object> request) {
        Object header = request.get("header");
        if (!(header instanceof Map<?, ?> headerMap)) {
            return null;
        }
        Object serviceId = headerMap.get("serviceId");
        return serviceId == null ? null : String.valueOf(serviceId);
    }

    private boolean isSuccess(Map<String, Object> response) {
        Object result = response.get("result");
        if (!(result instanceof Map<?, ?> resultMap)) {
            return false;
        }
        Object resultCode = resultMap.get("resultCode");
        return "S0000".equals(String.valueOf(resultCode));
    }

    private String stringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value == null ? null : String.valueOf(value);
    }

    private Optional<HttpServletRequest> currentRequest() {
        if (!(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs)) {
            return Optional.empty();
        }
        return Optional.ofNullable(attrs.getRequest());
    }

    public record GatewayOmSessionUser(
            String userId,
            String userName,
            String branchId,
            String authGroupId,
            String authGroupName,
            String omSessionId) implements Serializable {
        public Map<String, Object> toSessionMap() {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userId", userId);
            map.put("userName", userName);
            map.put("branchId", branchId);
            map.put("authGroupId", authGroupId);
            map.put("authGroupName", authGroupName);
            map.put("omSessionId", omSessionId);
            map.put("loggedIn", true);
            return map;
        }
    }
}
