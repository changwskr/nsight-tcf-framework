package com.nh.nsight.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nsight.gateway")
public class GatewayProperties {
    private Auth auth = new Auth();
    private Routing routing = new Routing();

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Routing getRouting() {
        return routing;
    }

    public void setRouting(Routing routing) {
        this.routing = routing;
    }

    public static class Auth {
        /** true 시 JSESSIONID 쿠키(로그인) 필수 */
        private boolean loginRequired = true;

        public boolean isLoginRequired() {
            return loginRequired;
        }

        public void setLoginRequired(boolean loginRequired) {
            this.loginRequired = loginRequired;
        }
    }

    public static class Routing {
        /** 요청 query 미지정 시 downstream 라우팅 모드 */
        private DeploymentMode deploymentMode = DeploymentMode.bootrun;
        /** tomcat 모드 downstream 베이스 URL (예: http://localhost:8080) */
        private String tomcatBaseUrl = "http://localhost:8080";
        /** bootrun 모드 downstream 호스트 (예: http://127.0.0.1) */
        private String bootrunHost = "http://127.0.0.1";
        /** true 시 query 파라미터 무시, profile routing 설정만 사용 (dev) */
        private boolean ignoreRequestParams = false;

        public DeploymentMode getDeploymentMode() {
            return deploymentMode;
        }

        public void setDeploymentMode(DeploymentMode deploymentMode) {
            this.deploymentMode = deploymentMode;
        }

        public String getTomcatBaseUrl() {
            return tomcatBaseUrl;
        }

        public void setTomcatBaseUrl(String tomcatBaseUrl) {
            this.tomcatBaseUrl = tomcatBaseUrl;
        }

        public String getBootrunHost() {
            return bootrunHost;
        }

        public void setBootrunHost(String bootrunHost) {
            this.bootrunHost = bootrunHost;
        }

        public boolean isIgnoreRequestParams() {
            return ignoreRequestParams;
        }

        public void setIgnoreRequestParams(boolean ignoreRequestParams) {
            this.ignoreRequestParams = ignoreRequestParams;
        }
    }

    public enum DeploymentMode {
        bootrun, tomcat
    }
}
