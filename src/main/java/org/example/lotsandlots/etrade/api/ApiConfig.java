package org.example.lotsandlots.etrade.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("EtradeApiConfig")
@PropertySource("classpath:etrade.properties")
public class ApiConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ApiConfig.class);

    @Value("#{EtradeAuthConfig.accountIdKey}")
    private String accountIdKey;

    @Value("${api.baseUrl}/v1/accounts/list")
    private String accountListUrl;

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.baseUrl}/v1/accounts/#{EtradeAuthConfig.accountIdKey}/orders/cancel")
    private String ordersCancelUrl;

    @Value("${api.baseUrl}/v1/accounts/#{EtradeAuthConfig.accountIdKey}/orders/place")
    private String ordersPlaceUrl;

    @Value("${api.baseUrl}/v1/accounts/#{EtradeAuthConfig.accountIdKey}/orders/preview")
    private String ordersPreviewUrl;

    @Value("count=100")
    private String ordersQueryString;

    @Value("${api.baseUrl}/v1/accounts/#{EtradeAuthConfig.accountIdKey}/orders")
    private String ordersUrl;

    @Value("count=100&lotsRequired=true&totalsRequired=true")
    private String portfolioQueryString;

    @Value("${api.baseUrl}/v1/accounts/#{EtradeAuthConfig.accountIdKey}/portfolio")
    private String portfolioUrl;

    @Value("${api.baseUrl}/v1/market/quote/")
    private String quoteUrl;

    public ApiConfig() {
    }

    public String getAccountIdKey() {
        return accountIdKey;
    }
    public void setAccountIdKey(String accountIdKey) {
        this.accountIdKey = accountIdKey;
    }

    public String getAccountListUrl() {
        return accountListUrl;
    }
    public void setAccountListUrl(String accountListUrl) {
        this.accountListUrl = accountListUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getOrdersCancelUrl() {
        return ordersCancelUrl;
    }
    public void setOrdersCancelUrl(String ordersCancelUrl) {
        this.ordersCancelUrl = ordersCancelUrl;
    }

    public String getOrdersPlaceUrl() {
        return ordersPlaceUrl;
    }
    public void setOrdersPlaceUrl(String ordersPlaceUrl) {
        this.ordersPlaceUrl = ordersPlaceUrl;
    }

    public String getOrdersPreviewUrl() {
        return ordersPreviewUrl;
    }
    public void setOrdersPreviewUrl(String ordersPreviewUrl) {
        this.ordersPreviewUrl = ordersPreviewUrl;
    }

    public String getOrdersQueryString() {
        return ordersQueryString;
    }
    public void setOrdersQueryString(String ordersQueryString) {
        this.ordersQueryString = ordersQueryString;
    }

    public String getOrdersUrl() {
        return ordersUrl;
    }
    public void setOrdersUrl(String ordersUrl) {
        this.ordersUrl = ordersUrl;
    }

    public String getPortfolioQueryString() {
        return portfolioQueryString;
    }
    public void setPortfolioQueryString(String portfolioQueryString) {
        this.portfolioQueryString = portfolioQueryString;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }
    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public String getQuoteUrl() {
        return quoteUrl;
    }
    public void setQuoteUrl(String quoteUrl) {
        this.quoteUrl = quoteUrl;
    }

    public void info() {
        LOG.info("accountListUrl={} ordersCancelUrl={} ordersPlaceUrl={} ordersPreviewUrl={} ordersQueryString={} "
                        + "ordersUrl={} portfolioQueryString={} portfolioUrl={} quoteUrl={}",
                accountListUrl, ordersCancelUrl, ordersPlaceUrl, ordersPreviewUrl, ordersQueryString,
                ordersUrl, portfolioQueryString, portfolioUrl, quoteUrl);
    }
}
