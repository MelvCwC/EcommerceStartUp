package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.dto.request.OrderItemRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.OrderRequestDTO;
import com.onilicious.EcommerceStartUp.entity.*;
import com.onilicious.EcommerceStartUp.repository.OrderItemRepository;
import com.onilicious.EcommerceStartUp.repository.OrderRepository;
import com.onilicious.EcommerceStartUp.repository.ProductRepository;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public OrderService(OrderRepository orderRepo, OrderItemRepository orderItemRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    /*
     * Create order
     */
//    public Order createOrder(Long userId, List<OrderItem> items) {
//        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setCreatedAt(LocalDateTime.now());
//        order.setStatus("PENDING");
//        order = orderRepo.save(order);
//
//        for(OrderItem item : items) {
//            Product product = productRepo.findById(item.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found"));
//            item.setOrder(order);
//            item.setProduct(product);
//            orderItemRepo.save(item);
//        }
//
//        order.setItems(items);
//        return order;
//    }
    public Order createOrder(OrderRequestDTO request) {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order = orderRepo.save(order);

        List<OrderItem> savedItems = new ArrayList<>();
        if(request.getItems() != null) {
            for(OrderItemRequestDTO itemReq : request.getItems()) {
                Product product = productRepo.findById(itemReq.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProductId(product.getId());
                if(itemReq.getQuantity() != null) {
                    item.setQuantity(itemReq.getQuantity());
                }

                orderItemRepo.save(item);
                savedItems.add(item);
            }
        }
        order.setItems(savedItems);
        return order;
    }

    /*
     * Get order details by userId
     */
    @Transactional(readOnly = true)
    public List<Order> getOrderByUserId(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    /*
     * Get order details by orderId
     */
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /*
     * Update order status
     */
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrder(orderId);
        order.setStatus(status);
        return orderRepo.save(order);
    }

    /*
     * Delete an order and its items
     */
    public void deleteOrder(Long orderId) {
        orderRepo.deleteById(orderId);
        orderItemRepo.deleteByOrderId(orderId);
    }

}
