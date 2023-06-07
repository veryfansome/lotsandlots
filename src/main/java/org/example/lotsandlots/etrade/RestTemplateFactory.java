package org.example.lotsandlots.etrade;

import org.example.lotsandlots.etrade.api.ApiConfig;
import org.example.lotsandlots.etrade.auth.AuthConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("EtradeRestTemplateFactory")
public class RestTemplateFactory {

    private static final Logger LOG = LoggerFactory.getLogger(RestTemplateFactory.class);

    @Value("${rest.connectTimeoutMillis:3000}")
    private int connectTimeoutMillis;

    @Value("${rest.connectionRequestTimeoutMillis:3000}")
    private int connectionRequestTimeoutMillis;

    @Value("${rest.readTimeoutMillis:9000}")
    private int readTimeoutMillis;

    @Value("${rest.socketTimeoutMillis:3000}")
    private int socketTimeoutMillis;

    private ApiConfig apiConfig;
    private AuthConfig authConfig;

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

    public void info() {
        LOG.info("connectTimeoutMillis={} connectionRequestTimeoutMillis={} readTimeoutMillis={} "
                        + "socketTimeoutMillis={}",
                connectTimeoutMillis, connectionRequestTimeoutMillis, readTimeoutMillis,
                socketTimeoutMillis);
        apiConfig.info();
        authConfig.info();
    }
}
