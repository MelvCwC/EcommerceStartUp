package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.entity.Cart;
import com.onilicious.EcommerceStartUp.entity.CartItem;
import com.onilicious.EcommerceStartUp.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /*
     * Get cart by user
     */
    @GetMapping("/{userId")
    public ResponseEntity<Cart> getCartByUser(@PathVariable Long userId) {
        Cart cart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(cart);
    }

    /*
     * Add item to cart
     */
    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addItemTocart(@PathVariable Long userId, @RequestBody CartItem item) {
        Cart cart = cartService.addItemToCart(userId, item);
        return ResponseEntity.ok(cart);
    }

    /*
     * Update quantity of cart item
     */
    @PutMapping("/{userId}/update/{itemId}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long userId, @PathVariable Long itemId, @RequestParam int quantity) {
        Cart updatedCart = cartService.updateCartItem(userId, itemId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    /*
     * Remove a specific item from the cart
     */
    @DeleteMapping("/{userId}/remove/{itemId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        Cart updatedCart = cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(updatedCart);
    }

    /*
     * Clear all items in cart
     */
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
