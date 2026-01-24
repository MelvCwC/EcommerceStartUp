package com.onilicious.EcommerceStartUp.mapper;

import com.onilicious.EcommerceStartUp.dto.response.CartItemResponseDTO;
import com.onilicious.EcommerceStartUp.dto.response.CartResponseDTO;
import com.onilicious.EcommerceStartUp.entity.Cart;
import com.onilicious.EcommerceStartUp.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class CartMapper {

    public static CartResponseDTO toResponse(Cart cart) {
        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(cart.getId());
        response.setUserId(cart.getUser().getId());

        List<CartItemResponseDTO> itemsDTO = cart.getItems().stream().map(CartMapper::toItemResponse).toList();
        response.setItems(itemsDTO);

        BigDecimal total = itemsDTO.stream().map(CartItemResponseDTO::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalAmount(total);

        return response;
    }

    public static CartItemResponseDTO toItemResponse(CartItem item) {
        CartItemResponseDTO response = new CartItemResponseDTO();
        response.setItemId(item.getId());
        response.setProductId(item.getProduct().getId());
        response.setProductName(item.getProduct().getName());
        response.setPrice(item.getProduct().getPrice());
        response.setQuantity(item.getQuantity());

        response.setSubtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

        return response;
    }
}
