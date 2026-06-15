package com.nh.nsight.tcf.ui.service;

import com.nh.nsight.tcf.ui.config.TcfUiProperties;
import com.nh.nsight.tcf.ui.model.BusinessModuleInfo;
import com.nh.nsight.tcf.ui.service.TransactionRelayService.RelayOptions;
import java.net.URI;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class EtcRelayService {
    private static final String BUSINESS_CODE = "ET";

    private final TcfUiProperties properties;
    private final BusinessModuleCatalog catalog;
    private final RestClient restClient;

    public EtcRelayService(TcfUiProperties properties, BusinessModuleCatalog catalog) {
        this.properties = properties;
        this.catalog = catalog;
        this.restClient = RestClient.builder().build();
    }

    public String resolveBaseUrl(RelayOptions options) {
        BusinessModuleInfo module = catalog.findByCode(BUSINESS_CODE);
        if (resolveMode(options) == TcfUiProperties.DeploymentMode.tomcat) {
            return trimTrailingSlash(resolveTomcatGateway(options)) + module.contextPath();
        }
        return trimTrailingSlash(resolveBootrunHost(options)) + ":" + module.localPort();
    }

    public String relayDeleteAllLogs(RelayOptions options) {
        String targetUrl = resolveBaseUrl(options) + "/et/transaction-io/logs/delete";
        try {
            return restClient.post()
                    .uri(URI.create(targetUrl))
                    .retrieve()
                    .body(String.class);
        } catch (RestClientResponseException e) {
            return e.getResponseBodyAsString();
        } catch (Exception e) {
            return connectionErrorJson(targetUrl, e);
        }
    }

    private String connectionErrorJson(String targetUrl, Exception e) {
        String message = e.getMessage() == null ? "대상 서비스에 연결할 수 없습니다." : e.getMessage();
        return """
                {
                  "body": {
                    "error": "%s",
                    "targetUrl": "%s",
                    "hint": "common-etc 모듈이 포함된 업무 WAS를 기동했는지 확인하세요."
                  }
                }
                """.formatted(escapeJson(message), escapeJson(targetUrl));
    }

    private TcfUiProperties.DeploymentMode resolveMode(RelayOptions options) {
        if (options != null && StringUtils.hasText(options.deploymentMode())) {
            return TcfUiProperties.DeploymentMode.valueOf(options.deploymentMode());
        }
        return properties.getDeploymentMode();
    }

    private String resolveTomcatGateway(RelayOptions options) {
        if (options != null && StringUtils.hasText(options.tomcatGatewayUrl())) {
            return options.tomcatGatewayUrl();
        }
        return properties.getTomcatGatewayUrl();
    }

    private String resolveBootrunHost(RelayOptions options) {
        if (options != null && StringUtils.hasText(options.bootrunHost())) {
            return options.bootrunHost();
        }
        return properties.getBootrunHost();
    }

    private String trimTrailingSlash(String value) {
        if (!StringUtils.hasText(value)) {
            return "http://127.0.0.1";
        }
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
