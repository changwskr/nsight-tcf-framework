package nhnis.mg.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 전문 테스트 대상(pdmg-service) 접속 정보.
 *
 * <p>화면에서 대상 URL을 직접 바꿔가며 시험할 수 있고, 여기 값은 초기값으로만 쓰인다.
 */
@ConfigurationProperties("pdmg.ui")
public class PdmgUiProperties {

    /** pdmg-service 기본 주소. */
    private String targetBaseUrl = "http://localhost:8080";

    /** tcf-ontology-service Workbench 기본 주소 (Architecture Design). */
    private String ontologyBaseUrl = "http://localhost:8098";

    /** pdmg-jwt 기본 주소 (브라우저 직접 호출). */
    private String jwtBaseUrl = "http://localhost:8110";

    /** 브라우저 fetch Abort 타임아웃(ms). pdmg-service OnlineTimeout과 별개. */
    private int timeoutMs = 10000;

    public String getTargetBaseUrl() {
        return targetBaseUrl;
    }

    public void setTargetBaseUrl(String targetBaseUrl) {
        this.targetBaseUrl = targetBaseUrl;
    }

    public String getOntologyBaseUrl() {
        return ontologyBaseUrl;
    }

    public void setOntologyBaseUrl(String ontologyBaseUrl) {
        this.ontologyBaseUrl = ontologyBaseUrl;
    }

    public String getJwtBaseUrl() {
        return jwtBaseUrl;
    }

    public void setJwtBaseUrl(String jwtBaseUrl) {
        this.jwtBaseUrl = jwtBaseUrl;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }
}
