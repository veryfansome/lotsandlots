package org.example.lotsandlots.etrade.auth;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("EtradeAuthConfig")
@PropertySource("classpath:etrade.properties")
public class AuthConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AuthConfig.class);

    @Value("${api.baseUrl}/oauth/access_token")
    private String accessTokenUrl;

    private String accountIdKey;

    @Value("${auth.authorizeUrl}")
    private String authorizeUrl;

    private String consumerKey;

    @Value("${api.baseUrl}/oauth/request_token")
    private String requestTokenUrl;

    private String sharedSecret;

    public AuthConfig() {
        Config config = ConfigFactory.load();
        accountIdKey = config.getString("etrade.accountIdKey");
        consumerKey = config.getString("etrade.auth.consumerKey");
        sharedSecret = config.getString("etrade.auth.consumerSecret");
    }

    public String getAccessTokenHttpMethod() {
        return "GET";
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }
    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getAccountIdKey() {
        return accountIdKey;
    }
    public void setAccountIdKey(String accountIdKey) {
        this.accountIdKey = accountIdKey;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }
    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getRequestTokenHttpMethod() {
        return "GET";
    }

    public String getRequestTokenUrl() {
        return requestTokenUrl;
    }
    public void setRequestTokenUrl(String requestTokenUrl) {
        this.requestTokenUrl = requestTokenUrl;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }
    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    public void info() {
        LOG.trace("accessTokenUrl={} accountIdKey={} authorizeUrl={} consumerKey={} requestTokenUrl={} sharedSecret={}",
                accessTokenUrl, accountIdKey, authorizeUrl, consumerKey, requestTokenUrl, sharedSecret);
    }
}
