package org.example.lotsandlots.etrade.auth;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OAuthConfig {

    private static final Logger LOG = LoggerFactory.getLogger(OAuthConfig.class);

    private String accessTokenUrl;
    private String authorizeUrl;
    private String consumerKey;
    private String requestTokenUrl;
    private String sharedSecret;

    public OAuthConfig() {
        Config config = ConfigFactory.load();
        setAccessTokenUrl(config.getString("etrade.accessTokenUrl"));
        setAuthorizeUrl(config.getString("etrade.authorizeUrl"));
        setConsumerKey(config.getString("etrade.consumerKey"));
        setRequestTokenUrl(config.getString("etrade.requestTokenUrl"));
        setSharedSecret(config.getString("etrade.consumerSecret"));
        LOG.info("Initialized OAuthConfig");
    }

    public String getConsumerKey() {
        return consumerKey;
    }
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }
    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    public String getRequestTokenUrl() {
        return requestTokenUrl;
    }
    public void setRequestTokenUrl(String requestTokenUrl) {
        this.requestTokenUrl = requestTokenUrl;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }
    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }
    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getRequestTokenHttpMethod() {
        return "GET";
    }

    public String getAccessTokenHttpMethod() {
        return "GET";
    }
}
