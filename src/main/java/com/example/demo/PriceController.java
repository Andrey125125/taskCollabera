package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceFeedService priceFeedService;

    public PriceController(PriceFeedService priceFeedService) {
        this.priceFeedService = priceFeedService;
    }

    @GetMapping("/{instrumentName}")
    public ResponseEntity<Price> getPrice(@PathVariable String instrumentName) {
        Price price = priceFeedService.getPrice(instrumentName);
        if (price != null) {
            return ResponseEntity.ok(price);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}