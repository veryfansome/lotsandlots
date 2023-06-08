package org.example.lotsandlots.etrade.auth;

import org.example.lotsandlots.etrade.model.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class AuthorizationRestTemplate extends RestTemplate {

    public AuthorizationRestTemplate(ClientHttpRequestFactory factory) {
        super(factory);
    }

    public ResponseEntity<AuthorizationResponse> execute(Message message) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Authorization", message.getOauthHeader());

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(message.getUrl())
                .build();
        return super.exchange(
                uriComponents.toString(),
                HttpMethod.valueOf(message.getHttpMethod()),
                new HttpEntity<>(headers),
                AuthorizationResponse.class
        );
    }
}
