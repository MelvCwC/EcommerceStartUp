package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.entity.Order;
import com.onilicious.EcommerceStartUp.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkot")
public class CheckoutServiceController {
    private final CheckoutService checkOutService;

    public CheckoutServiceController(CheckoutService checkOutService) {
        this.checkOutService = checkOutService;
    }

    /*
     * Checkout using @RequestParam for a simple userId
     */
    @PostMapping
    public ResponseEntity<Order> checkOut(@RequestParam Long userId) {
        Order order = checkOutService.checkout(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    /*
     * Checkout using @PathVariable
     */
    @PostMapping("/{userId}")
    public ResponseEntity<Order> checkOutByPath(@PathVariable Long userId) {
        Order order = checkOutService.checkout(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
