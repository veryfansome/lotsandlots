package org.example.lotsandlots.etrade.rest;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;

@Component("ClientHttpRequestFactoryService")
public class ClientHttpRequestFactoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ClientHttpRequestFactoryService.class);

    private final ClientHttpRequestFactory factory;

    @Value("${rest.connectTimeoutMillis:3000}")
    private int connectTimeoutMillis;

    @Value("${rest.connectionRequestTimeoutMillis:3000}")
    private int connectionRequestTimeoutMillis;

    @Value("${rest.readTimeoutMillis:9000}")
    private int readTimeoutMillis;

    @Value("${rest.socketTimeoutMillis:3000}")
    private int socketTimeoutMillis;

    public ClientHttpRequestFactoryService() throws GeneralSecurityException {
        SSLContext sslContext = SSLContexts
                .custom()
                .loadTrustMaterial(null, (x509Certificates, s) -> true)
                .build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(connectTimeoutMillis))
                .setSocketTimeout(Timeout.ofMilliseconds(socketTimeoutMillis))
                .build();
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Timeout.ofMilliseconds(readTimeoutMillis))
                .build();
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                .setDefaultSocketConfig(socketConfig)
                .setMaxConnTotal(100) // Setting this too low may cause more connection request timeouts
                .setSSLSocketFactory(csf)
                .build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(connectionRequestTimeoutMillis))
                .setRedirectsEnabled(true)
                .build();
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                //.setRedirectStrategy(new LaxRedirectStrategy()) // From 4.5.x httpclient code
                .build();
        factory = new HttpComponentsClientHttpRequestFactory(client);
    }

    public ClientHttpRequestFactory get() {
        return factory;
    }

    public void info() {
        LOG.info("connectTimeoutMillis={} connectionRequestTimeoutMillis={} readTimeoutMillis={} "
                        + "socketTimeoutMillis={}",
                connectTimeoutMillis, connectionRequestTimeoutMillis, readTimeoutMillis,
                socketTimeoutMillis);
    }
}
