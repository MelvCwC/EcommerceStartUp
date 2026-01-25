package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.entity.*;
import com.onilicious.EcommerceStartUp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CheckoutService {

    private final CartRepository cartRepo;
    private final CartitemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final UserRepository userRepo;

    public CheckoutService(CartRepository cartRepo, CartitemRepository cartItemRepo, OrderRepository orderRepo, OrderItemRepository orderItemRepo, UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.userRepo = userRepo;
    }

    /*
     * Handle the entire checkout flow
     *  - Validate user & cart
     *  - Create Order
     *  - Transfer item from cart -> order
     *  - Clear cart
     */
    public Order checkout(Long userId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepo.findByCartId(cart.getId());
        if(cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepo.save(order);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProduct().getId());
            orderItem.setProductName(cartItem.getProduct().getName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());

            BigDecimal itemTotal = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);

            orderItemRepo.save(orderItem);
        }

        order.setTotal(total);
        orderRepo.save(order);

        cartItemRepo.deleteByCartId(cart.getId());

        return order;
    }
}
