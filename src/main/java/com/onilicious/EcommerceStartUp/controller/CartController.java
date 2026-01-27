package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.dto.request.AddToCartRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.UpdateCartItemRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.CartResponseDTO;
import com.onilicious.EcommerceStartUp.entity.Cart;
import com.onilicious.EcommerceStartUp.entity.CartItem;
import com.onilicious.EcommerceStartUp.mapper.CartMapper;
import com.onilicious.EcommerceStartUp.service.CartService;
import jakarta.validation.Valid;
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
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUser(@PathVariable Long userId) {
        Cart cart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(CartMapper.toResponse(cart));
    }

    /*
     * Add item to cart
     */
    @PostMapping("/{userId}/add")
    public ResponseEntity<CartResponseDTO> addItemTocart(@PathVariable Long userId, @Valid @RequestBody AddToCartRequestDTO request) {
        Cart cart = cartService.addItemToCart(userId, request);
        return ResponseEntity.ok(CartMapper.toResponse(cart));
    }

    /*
     * Update quantity of cart item
     */
    @PutMapping("/{userId}/update/{itemId}")
    public ResponseEntity<CartResponseDTO> updateCartItem(@PathVariable Long userId, @PathVariable Long itemId, @Valid @RequestBody UpdateCartItemRequestDTO request) {
        Cart updatedCart = cartService.updateCartItem(userId, itemId, request.getQuantity());
        return ResponseEntity.ok(CartMapper.toResponse(updatedCart));
    }

    /*
     * Remove a specific item from the cart
     */
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<CartResponseDTO> removeItemFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        Cart updatedCart = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(CartMapper.toResponse(updatedCart));
    }

    /*
     * Clear all items in cart
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
