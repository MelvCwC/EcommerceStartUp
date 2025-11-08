package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.dto.OrderRequestDTO;
import com.onilicious.EcommerceStartUp.entity.Order;
import com.onilicious.EcommerceStartUp.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /*
     * Get specific order by orderId
     */
    @GetMapping("/{orderId")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    /*
     * Get order by userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrder(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }

    /*
     * Create order
     */
    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO request) {
        Order createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /*
     * Update order
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    /*
     * Delete Order
     */
    @DeleteMapping("/{orderId")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
