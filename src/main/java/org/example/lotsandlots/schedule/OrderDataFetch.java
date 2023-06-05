package org.example.lotsandlots.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderDataFetch {

    private static final Logger LOG = LoggerFactory.getLogger(OrderDataFetch.class);

    @Scheduled(fixedRate = 10000) // Millis
    public void fetchOrderData() {
        LOG.info("Fetching orders data");
    }
}
