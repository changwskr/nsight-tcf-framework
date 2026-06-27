package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.security.GatewayAuthException;
import com.nh.nsight.gateway.service.RouteResult;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class GEF {
    private static final String PHASE = "GEF";

    public RouteResult success(RouteContext context, GatewayForwardResponse response) {
        String phase = PHASE + ".success";
        GatewayProxyTrace.start(phase);
        GatewayProxyTrace.log(phase, "targetUrl=" + context.targetUrl());
        GatewayProxyTrace.log(phase, "httpStatus=" + response.httpStatus() + " elapsedMs=" + response.elapsedMs());
        GatewayProxyTrace.log(phase, "RouteResult");
        RouteResult result = new RouteResult(
                context.targetUrl(),
                response.httpStatus(),
                response.elapsedMs(),
                response.responseBody(),
                response.setCookies()
        );
        GatewayProxyTrace.end(phase);
        return result;
    }

    public RouteResult authFail(String businessCode, RouteContext context, GatewayAuthException error) {
        String phase = PHASE + ".authFail";
        GatewayProxyTrace.start(phase);
        String targetUrl = context == null ? "" : context.targetUrl();
        long elapsedMs = context == null ? 0L : System.currentTimeMillis() - context.startedAtMillis();
        GatewayProxyTrace.log(phase, "businessCode=" + businessCode + " httpStatus=" + error.httpStatus());
        GatewayProxyTrace.log(phase, "message=" + error.getMessage());
        GatewayProxyTrace.log(phase, "RouteResult");
        RouteResult result = new RouteResult(
                targetUrl,
                error.httpStatus(),
                elapsedMs,
                authErrorJson(businessCode, error),
                List.of()
        );
        GatewayProxyTrace.end(phase);
        return result;
    }

    public RouteResult httpError(RouteContext context, RestClientResponseException error) {
        String phase = PHASE + ".httpError";
        GatewayProxyTrace.start(phase);
        GatewayProxyTrace.log(phase, "targetUrl=" + context.targetUrl());
        GatewayProxyTrace.log(phase, "httpStatus=" + error.getStatusCode().value());
        GatewayProxyTrace.log(phase, "RouteResult");
        List<String> setCookies = error.getResponseHeaders() == null
                ? List.of()
                : error.getResponseHeaders().getOrEmpty(org.springframework.http.HttpHeaders.SET_COOKIE);
        RouteResult result = new RouteResult(
                context.targetUrl(),
                error.getStatusCode().value(),
                System.currentTimeMillis() - context.startedAtMillis(),
                error.getResponseBodyAsString(),
                setCookies
        );
        GatewayProxyTrace.end(phase);
        return result;
    }

    public RouteResult connectionError(RouteContext context, Exception error) {
        String phase = PHASE + ".connectionError";
        GatewayProxyTrace.start(phase);
        GatewayProxyTrace.log(phase, "targetUrl=" + context.targetUrl());
        GatewayProxyTrace.log(phase, "error=" + error.getClass().getSimpleName() + " message=" + error.getMessage());
        GatewayProxyTrace.log(phase, "connectionErrorJson");
        RouteResult result = new RouteResult(
                context.targetUrl(),
                502,
                System.currentTimeMillis() - context.startedAtMillis(),
                connectionErrorJson(context, error.getMessage()),
                List.of()
        );
        GatewayProxyTrace.end(phase);
        return result;
    }

    private String authErrorJson(String businessCode, GatewayAuthException error) {
        String safeMessage = error.getMessage() == null ? "" : error.getMessage().replace("\\", "\\\\").replace("\"", "\\\"");
        return """
                {"error":"%s","businessCode":"%s","hint":"gateway 인증에 실패했습니다."}
                """.formatted(safeMessage, businessCode.toUpperCase(Locale.ROOT));
    }

    private String connectionErrorJson(RouteContext context, String message) {
        String safeMessage = message == null ? "" : message.replace("\\", "\\\\").replace("\"", "\\\"");
        String safeUrl = context.targetUrl() == null ? "" : context.targetUrl().replace("\\", "\\\\").replace("\"", "\\\"");
        return """
                {"error":"%s","targetUrl":"%s","hint":"%s(포트 %d 또는 /%s) 기동 상태를 확인하세요."}
                """.formatted(
                safeMessage,
                safeUrl,
                context.module().serviceHint(),
                context.module().bootrunPort(),
                context.module().code().toLowerCase(Locale.ROOT)
        );
    }
}
