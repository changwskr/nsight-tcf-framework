package com.nh.nsight.tcf.batch.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.nh.nsight.tcf.batch.config.SessionStatusBatchProperties.SessionTargetProperties;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SessionMetricsClient {
    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;

    public SessionMetricsClient(JdbcTemplate jdbcTemplate, RestTemplate batchRestTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = batchRestTemplate;
    }

    public SessionCounts collectSpringSessionCounts(long now) {
        try {
            Map<String, Object> row = jdbcTemplate.queryForMap("""
                    SELECT COUNT(*) AS "totalCount",
                           COALESCE(SUM(CASE WHEN EXPIRY_TIME > ? THEN 1 ELSE 0 END), 0) AS "activeCount",
                           COALESCE(SUM(CASE WHEN EXPIRY_TIME <= ? THEN 1 ELSE 0 END), 0) AS "expiredCount",
                           COALESCE(COUNT(DISTINCT NULLIF(PRINCIPAL_NAME, '')), 0) AS "uniqueUserCount"
                      FROM SPRING_SESSION
                    """, now, now);
            return new SessionCounts(
                    toInt(row.get("activeCount")),
                    toInt(row.get("expiredCount")),
                    toInt(row.get("totalCount")),
                    toInt(row.get("uniqueUserCount")),
                    true
            );
        } catch (Exception e) {
            return SessionCounts.unreachable();
        }
    }

    public SessionCounts collectActuatorSessionCounts(SessionTargetProperties target) {
        try {
            String baseUrl = normalizeBaseUrl(target.getBaseUrl());
            JsonNode health = restTemplate.getForObject(baseUrl + "/actuator/health", JsonNode.class);
            if (health == null || !"UP".equalsIgnoreCase(health.path("status").asText("DOWN"))) {
                return SessionCounts.unreachable();
            }
            double active = metricValue(baseUrl, target.getMetricName());
            int activeCount = (int) Math.round(active);
            return new SessionCounts(activeCount, 0, activeCount, 0, true);
        } catch (Exception e) {
            return SessionCounts.unreachable();
        }
    }

    public String resolveHealthStatus(SessionCounts counts, int warnActiveThreshold) {
        if (!counts.reachable()) {
            return "FAIL";
        }
        if (counts.activeCount() >= warnActiveThreshold) {
            return "WARN";
        }
        if (counts.expiredCount() > counts.activeCount() && counts.expiredCount() > 10) {
            return "WARN";
        }
        return "NORMAL";
    }

    private double metricValue(String baseUrl, String metricName) {
        JsonNode body = restTemplate.getForObject(baseUrl + "/actuator/metrics/" + metricName, JsonNode.class);
        if (body == null) {
            return 0;
        }
        JsonNode measurements = body.path("measurements");
        if (!measurements.isArray() || measurements.isEmpty()) {
            return 0;
        }
        return measurements.get(0).path("value").asDouble(0);
    }

    private int toInt(Object value) {
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(value));
    }

    private String normalizeBaseUrl(String baseUrl) {
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    public record SessionCounts(int activeCount, int expiredCount, int totalCount, int uniqueUserCount,
                                boolean reachable) {
        public static SessionCounts unreachable() {
            return new SessionCounts(0, 0, 0, 0, false);
        }
    }
}
