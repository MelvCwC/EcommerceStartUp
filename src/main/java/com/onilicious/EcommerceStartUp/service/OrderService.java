package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.entity.Order;
import com.onilicious.EcommerceStartUp.entity.OrderItem;
import com.onilicious.EcommerceStartUp.entity.Product;
import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.repository.OrderItemRepository;
import com.onilicious.EcommerceStartUp.repository.OrderRepository;
import com.onilicious.EcommerceStartUp.repository.ProductRepository;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public Order createOrder(Long userId, List<OrderItem> items) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");
        order = orderRepo.save(order);

        for(OrderItem item : items) {
            Product product = productRepo.findById(item.getProduct().getId()).orElseThrow(() -> new RuntimeException("Product not found"));
            item.setOrder(order);
            item.setProduct(product);
            orderItemRepo.save(item);
        }

        order.setItems(items);
        return order;
    }

    /*
     * Get order details by ID
     */
    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /*
     * Update order status
     */
    public Order updateOrderStatus(Long orderId, String status) {
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
