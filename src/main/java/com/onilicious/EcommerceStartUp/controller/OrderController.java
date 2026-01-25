package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.dto.request.OrderRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.OrderResponseDTO;
import com.onilicious.EcommerceStartUp.entity.Order;
import com.onilicious.EcommerceStartUp.entity.OrderStatus;
import com.onilicious.EcommerceStartUp.mapper.OrderMapper;
import com.onilicious.EcommerceStartUp.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    /*
     * Get order by userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getUserOrder(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrderByUserId(userId);
        List<OrderResponseDTO> response = orders.stream().map(OrderMapper::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    /*
     * Create order
     */
    @PostMapping("/")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        Order createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toResponse(createdOrder));
    }

    /*
     * Update order
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        Order order = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    /*
     * Delete Order
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
