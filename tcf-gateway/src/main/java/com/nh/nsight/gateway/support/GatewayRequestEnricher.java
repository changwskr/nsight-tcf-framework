package com.nh.nsight.gateway.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GatewayRequestEnricher {
    private final ObjectMapper objectMapper;

    public GatewayRequestEnricher(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String enrich(String requestBody, Jwt jwt) {
        if (!StringUtils.hasText(requestBody) || jwt == null) {
            return requestBody;
        }
        try {
            Map<String, Object> request = objectMapper.readValue(requestBody, new TypeReference<>() {
            });
            @SuppressWarnings("unchecked")
            Map<String, Object> header = (Map<String, Object>) request.computeIfAbsent("header", key -> new LinkedHashMap<>());
            putIfPresent(header, "userId", claim(jwt, "userId", jwt.getSubject()));
            putIfPresent(header, "branchId", claim(jwt, "branchId", null));
            putIfPresent(header, "channelId", claim(jwt, "channelId", "WEBTOP"));
            return objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            return requestBody;
        }
    }

    private void putIfPresent(Map<String, Object> header, String key, String value) {
        if (StringUtils.hasText(value)) {
            header.put(key, value);
        }
    }

    private String claim(Jwt jwt, String name, String fallback) {
        Object value = jwt.getClaims().get(name);
        if (value != null && StringUtils.hasText(String.valueOf(value))) {
            return String.valueOf(value);
        }
        return fallback;
    }
}
