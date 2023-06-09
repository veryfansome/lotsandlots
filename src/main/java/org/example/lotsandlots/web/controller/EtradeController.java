package org.example.lotsandlots.web.controller;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/etrade")
public class EtradeController implements EtradeOAuthClient {

    private static final Logger LOG = LoggerFactory.getLogger(EtradeController.class);

    private SecurityContext newSecurityContext;
    private Message newTokenMessage;
    private ClientHttpRequestFactoryService requestFactory;
    private RestTemplateFactory templateFactory;

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/auth")
    public RedirectView getAuth(@RequestParam(name = "oauth_verifier", required = false) String oAuthVerifier) {
        AuthConfig authConfig = templateFactory.getAuthConfig();
        if (StringUtils.isBlank(oAuthVerifier)) {
            newSecurityContext  = new SecurityContext(authConfig);
            newTokenMessage = new Message();
            newTokenMessage.setHttpMethod("GET");
            newTokenMessage.setRequiresOauth(true);
            newTokenMessage.setUrl(authConfig.getRequestTokenUrl());
            LOG.debug("Initialized token {}", newTokenMessage);
            try {
                OAuthToken requestToken = getOauthToken(newSecurityContext, newTokenMessage, OAuthToken.TokenType.REQUEST);
                String url = String.format(
                        "%s?key=%s&token=%s",
                        authConfig.getAuthorizeUrl(),
                        authConfig.getConsumerKey(),
                        requestToken.getOauthToken());
                return new RedirectView(url);
            } catch (Exception e) {
                LOG.error("Unable to redirect to {}", authConfig.getRequestTokenUrl(), e);
                return null;
            }
        } else {
            LOG.info("Verifier {}", oAuthVerifier);
            newTokenMessage.setVerifierCode(oAuthVerifier);
            newTokenMessage.setUrl(authConfig.getAccessTokenUrl());
            LOG.debug("Updated token {}", newTokenMessage);
            try {
                getOauthToken(newSecurityContext, newTokenMessage, OAuthToken.TokenType.ACCESS);
                newSecurityContext.setInitialized(true);
                templateFactory.setSecurityContext(newSecurityContext);
                return new RedirectView("http://localhost:" + serverPort + "/api/etrade/controller/info");
            } catch (Exception e) {
                LOG.info("Failed to initialize security context", e);
                return null;
            }
        }
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
