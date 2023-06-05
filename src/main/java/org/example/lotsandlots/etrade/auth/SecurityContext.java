package org.example.lotsandlots.etrade.auth;

public class SecurityContext {

    private OAuthConfig oAuthConfig;

    private boolean initialized;
    private OAuthToken token;

    public OAuthConfig getOAuthConfig() {
        return oAuthConfig;
    }
    public void setOAuthConfig(OAuthConfig oAuthConfig) {
        this.oAuthConfig = oAuthConfig;
    }

    public boolean isInitialized() {
        return initialized;
    }
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public OAuthToken getToken() {
        return token;
    }
    public void setToken(OAuthToken token) {
        this.token = token;
    }
}
