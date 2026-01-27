package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.dto.request.OrderItemRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.OrderRequestDTO;
import com.onilicious.EcommerceStartUp.entity.*;
import com.onilicious.EcommerceStartUp.exception.BadRequestException;
import com.onilicious.EcommerceStartUp.exception.ConflictException;
import com.onilicious.EcommerceStartUp.exception.ResourceNotFoundException;
import com.onilicious.EcommerceStartUp.repository.OrderItemRepository;
import com.onilicious.EcommerceStartUp.repository.OrderRepository;
import com.onilicious.EcommerceStartUp.repository.ProductRepository;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public Order createOrder(OrderRequestDTO request) {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        order = orderRepo.save(order);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        if(request.getItems() != null) {
            for(OrderItemRequestDTO itemReq : request.getItems()) {
                Product product = productRepo.findById(itemReq.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProductId(product.getId());
                item.setProductName(product.getName());
                item.setPriceAtPurchase(product.getPrice());
                if(itemReq.getQuantity() != null) {
                    item.setQuantity(itemReq.getQuantity());
                    BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
                    total = total.add(itemTotal);
                }

                orderItemRepo.save(item);
                orderItems.add(item);
            }
        }
        order.setItems(orderItems);
        order.setTotal(total);
        return orderRepo.save(order);
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
        return orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    /*
     * Update order status
     */
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        if(status == null) {
            throw new BadRequestException("Order Status cannot be null");
        }

        Order order = getOrder(orderId);

        if(order.getStatus() == OrderStatus.CANCELLED) {
            throw new ConflictException("Cannot update a cancelled order");
        }

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
