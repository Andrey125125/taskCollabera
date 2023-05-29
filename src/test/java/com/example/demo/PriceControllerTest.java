package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriceControllerTest {

    @Test
    public void testGetPrice() {
        PriceFeedService mock = mock(PriceFeedService.class);
        PriceController controller = new PriceController(mock);
        Price price = new Price(1, "EUR/USD", 1.1000, 1.2000);

        when(mock.getPrice("EUR/USD")).thenReturn(price);

        ResponseEntity<Price> response = controller.getPrice("EUR/USD");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(price, response.getBody());
    }

    @Test
    public void testGetPriceNotFound() {
        PriceFeedService mockService = mock(PriceFeedService.class);
        PriceController controller = new PriceController(mockService);

        when(mockService.getPrice("EUR/USD")).thenReturn(null);

        ResponseEntity<Price> response = controller.getPrice("EUR/USD");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

