package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.dto.request.CheckOutRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.OrderResponseDTO;
import com.onilicious.EcommerceStartUp.entity.Order;
import com.onilicious.EcommerceStartUp.mapper.OrderMapper;
import com.onilicious.EcommerceStartUp.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutServiceController {
    private final CheckoutService checkOutService;

    public CheckoutServiceController(CheckoutService checkOutService) {
        this.checkOutService = checkOutService;
    }

    /*
     * Checkout using @RequestParam for a simple userId
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> checkOut(@RequestBody CheckOutRequestDTO request) {
        Order order = checkOutService.checkout(request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toResponse(order));
    }

    /*
     * Checkout using @PathVariable
     */
    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDTO> checkOutByPath(@PathVariable Long userId) {
        Order order = checkOutService.checkout(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toResponse(order));
    }
}
