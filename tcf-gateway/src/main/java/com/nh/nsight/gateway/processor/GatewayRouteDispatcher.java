package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.support.GatewayProxyTrace;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Component
public class GatewayRouteDispatcher {
    private static final String PHASE = "GatewayRouteDispatcher.dispatch";

    private final RestClient restClient;

    public GatewayRouteDispatcher() {
        this.restClient = RestClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .build();
    }

    public GatewayForwardResponse dispatch(RouteContext context, String cookieHeader) {
        GatewayProxyTrace.start(PHASE);
        try {
            GatewayProxyTrace.log(PHASE, "targetUrl=" + context.targetUrl());
            GatewayProxyTrace.log(PHASE, "restClient.post");
            return restClient.post()
                    .uri(URI.create(context.targetUrl()))
                    .headers(headers -> {
                        if (StringUtils.hasText(cookieHeader)) {
                            headers.set(HttpHeaders.COOKIE, cookieHeader);
                        }
                    })
                    .body(context.enrichedBody())
                    .exchange((request, response) -> {
                        String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
                        List<String> setCookies = response.getHeaders().getOrEmpty(HttpHeaders.SET_COOKIE);
                        return new GatewayForwardResponse(
                                response.getStatusCode().value(),
                                System.currentTimeMillis() - context.startedAtMillis(),
                                responseBody == null ? "" : responseBody,
                                setCookies
                        );
                    });
        } catch (RuntimeException e) {
            GatewayProxyTrace.log(PHASE, "error=" + e.getClass().getSimpleName() + " message=" + e.getMessage());
            throw e;
        } finally {
            GatewayProxyTrace.end(PHASE);
        }
    }
}
