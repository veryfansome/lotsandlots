package org.example.lotsandlots.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PortfolioDataFetch {

    private static final Logger LOG = LoggerFactory.getLogger(PortfolioDataFetch.class);

    @Scheduled(fixedRate = 10000) // Millis
    public void fetchOrderData() {
        LOG.info("Fetching portfolio data");
    }
}
