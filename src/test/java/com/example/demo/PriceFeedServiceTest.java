package com.example.demo;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PriceFeedServiceTest {

    private static final double COMMISSION_RATE = 0.001;

    @Test
    public void testOnMessage() {
        PriceFeedService service = new PriceFeedService();
        String message = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:01:002";

        service.onMessage(message);

        Price priceEurUsd = service.getPrice("EUR/USD");
        Price priceEurJpy = service.getPrice("EUR/JPY");

        // Test EUR/USD
        assertEquals(106, priceEurUsd.getId());
        assertEquals("EUR/USD", priceEurUsd.getInstrumentName());
        assertEquals(1.1000 * (1 - COMMISSION_RATE), priceEurUsd.getBid());
        assertEquals(1.2000 * (1 + COMMISSION_RATE), priceEurUsd.getAsk());

        // Test EUR/JPY
        assertEquals(107, priceEurJpy.getId());
        assertEquals("EUR/JPY", priceEurJpy.getInstrumentName());
        assertEquals(119.60 * (1 - COMMISSION_RATE), priceEurJpy.getBid());
        assertEquals(119.90 * (1 + COMMISSION_RATE), priceEurJpy.getAsk());
    }

    @Test
    public void testOnMessageWithOlderId() {
        PriceFeedService service = new PriceFeedService();
        String message = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n105, EUR/USD, 1.2000,1.3000,01-06-2020 12:01:01:002";

        service.onMessage(message);

        Price price = service.getPrice("EUR/USD");

        assertEquals(106, price.getId());  // id 106 should still be present, because 105 is older

    }

    @Test
    public void testGetPriceNotFound() {
        PriceFeedService service = new PriceFeedService();

        Price price = service.getPrice("EUR/USD");

        assertNull(price);
    }
}

