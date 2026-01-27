package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.dto.request.AddToCartRequestDTO;
import com.onilicious.EcommerceStartUp.entity.Cart;
import com.onilicious.EcommerceStartUp.entity.CartItem;
import com.onilicious.EcommerceStartUp.entity.Product;
import com.onilicious.EcommerceStartUp.entity.User;
import com.onilicious.EcommerceStartUp.exception.BadRequestException;
import com.onilicious.EcommerceStartUp.exception.ConflictException;
import com.onilicious.EcommerceStartUp.exception.ResourceNotFoundException;
import com.onilicious.EcommerceStartUp.repository.CartRepository;
import com.onilicious.EcommerceStartUp.repository.CartitemRepository;
import com.onilicious.EcommerceStartUp.repository.ProductRepository;
import com.onilicious.EcommerceStartUp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
//Manager shopping cart
public class CartService {

    private final CartRepository cartRepo;

    private final CartitemRepository cartItemRepo;

    private final ProductRepository productRepo;

    private final UserRepository userRepo;

    //Constructor inject dependencies
    public CartService(CartRepository cartRepo, CartitemRepository cartItemRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    /*
     * Get or create a cart for a given user
     */
    public Cart getOrCreateCart(Long userId) {
        return cartRepo.findByUserId(userId).orElseGet(() -> {
            User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepo.save(cart);
        });
    }

    /*
     * Add a product to the user cart
     */
    public Cart addItemToCart(Long userId, AddToCartRequestDTO request) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        CartItem existingItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), product.getId());

        if(existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemRepo.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItemRepo.save(cartItem);
        }

        return cart;
    }

    /*
     * Update quantity for a specific cart item
     */
    public Cart updateCartItem(Long userId, Long itemId, int quantity) {
        Cart cart = getOrCreateCart(userId);

        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if(!cartItem.getCart().getId().equals(cart.getId())) {
            throw new ConflictException("Cart item does not belong to user's cart");
        }

        if(quantity <= 0) {
            cartItemRepo.delete(cartItem);
            throw new BadRequestException("Quantity must be greater than 0");
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepo.save(cartItem);
        }

        return cart;
    }

    /*
     * Remove a product to the user cart
     */
    public Cart removeItemFromCart(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), productId);

        if(cartItem != null) {
            cartItemRepo.delete(cartItem);
        }
        return cart;
    }

    /*
     * Empty user cart
     */
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepo.deleteByCartId(cart.getId());
    }

}
