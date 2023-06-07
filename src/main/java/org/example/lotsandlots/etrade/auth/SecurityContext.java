package org.example.lotsandlots.etrade.auth;

public class SecurityContext {

    private AuthConfig authConfig;

    private boolean initialized;
    private OAuthToken token;

    public AuthConfig getAuthConfig() {
        return authConfig;
    }
    public void setOAuthConfig(AuthConfig authConfig) {
        this.authConfig = authConfig;
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
