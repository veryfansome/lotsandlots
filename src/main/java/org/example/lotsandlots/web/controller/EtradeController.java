package org.example.lotsandlots.web.controller;

import org.example.lotsandlots.etrade.rest.ClientHttpRequestFactoryService;
import org.example.lotsandlots.etrade.rest.RestTemplateFactory;
import org.example.lotsandlots.etrade.auth.AuthConfig;
import org.example.lotsandlots.etrade.auth.AuthorizationResponse;
import org.example.lotsandlots.etrade.auth.AuthorizationRestTemplate;
import org.example.lotsandlots.etrade.auth.EtradeOAuthClient;
import org.example.lotsandlots.etrade.auth.OAuthToken;
import org.example.lotsandlots.etrade.auth.SecurityContext;
import org.example.lotsandlots.etrade.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/etrade")
public class EtradeController implements EtradeOAuthClient {

    private static final Logger LOG = LoggerFactory.getLogger(EtradeController.class);

    ClientHttpRequestFactoryService requestFactory;
    RestTemplateFactory templateFactory;

    @GetMapping("/auth")
    public RedirectView getAuth() {
        AuthConfig authConfig = templateFactory.getAuthConfig();
        SecurityContext securityContext = new SecurityContext(authConfig);
        Message tokenMessage = new Message();
        tokenMessage.setHttpMethod("GET");
        tokenMessage.setRequiresOauth(true);
        tokenMessage.setUrl(authConfig.getRequestTokenUrl());
        LOG.debug("Initialized {}", tokenMessage);
        try {
            OAuthToken requestToken = getOauthToken(securityContext, tokenMessage, OAuthToken.TokenType.REQUEST);
            String url = String.format(
                    "%s?key=%s&token=%s",
                    authConfig.getAuthorizeUrl(),
                    authConfig.getConsumerKey(),
                    requestToken.getOauthToken());
            return new RedirectView(url);
        } catch (Exception e) {
            LOG.error("Failed to redirect", e);
            return null;
        }
    }

    @GetMapping("/auth/{verifier}")
    public void getAuthWithVerifier(@PathVariable String verifier) {
        LOG.info("woot {}", verifier);
    }

    @GetMapping("/controller/info")
    public void getControllerInfo() {
        requestFactory.info();
        templateFactory.info();
    }

    private OAuthToken getOauthToken(
            SecurityContext securityContext,
            Message tokenMessage,
            OAuthToken.TokenType tokenType) throws UnsupportedEncodingException, GeneralSecurityException {
        setOAuthHeader(securityContext, tokenMessage);

        ResponseEntity<AuthorizationResponse> tokenMessageResponse =
                new AuthorizationRestTemplate(requestFactory.get()).execute(tokenMessage);
        MultiValueMap<String, String> tokenMessageResponseBody = tokenMessageResponse.getBody();
        assert tokenMessageResponseBody != null;
        OAuthToken token = new OAuthToken(
                tokenMessageResponseBody.getFirst("oauth_token"),
                tokenMessageResponseBody.getFirst("oauth_token_secret"),
                tokenType);
        securityContext.setToken(token);
        return token;
    }

    @Autowired
    public void setRequestFactory(ClientHttpRequestFactoryService requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Autowired
    public void setTemplateFactory(RestTemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
    }
}
