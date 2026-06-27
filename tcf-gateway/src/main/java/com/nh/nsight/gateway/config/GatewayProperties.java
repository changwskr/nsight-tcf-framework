package com.nh.nsight.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nsight.gateway")
public class GatewayProperties {
    private Auth auth = new Auth();

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
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
}
