package org.example.lotsandlots.etrade.rest;

import org.example.lotsandlots.etrade.api.ApiConfig;
import org.example.lotsandlots.etrade.auth.AuthConfig;
import org.example.lotsandlots.etrade.auth.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("EtradeRestTemplateFactory")
public class RestTemplateFactory {

    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateFactory.class);

    private ApiConfig apiConfig;
    private AuthConfig authConfig;
    private SecurityContext securityContext;

    public RestTemplateFactory() {
    }

    public ApiConfig getApiConfig() {
        return apiConfig;
    }
    @Autowired
    public void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public AuthConfig getAuthConfig() {
        return authConfig;
    }
    @Autowired
    public void setAuthConfig(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }
    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public void info() {
        apiConfig.info();
        authConfig.info();
    }
}
