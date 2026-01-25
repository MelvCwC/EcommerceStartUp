package com.onilicious.EcommerceStartUp.mapper;

import com.onilicious.EcommerceStartUp.dto.response.OrderItemResponseDTO;
import com.onilicious.EcommerceStartUp.dto.response.OrderResponseDTO;
import com.onilicious.EcommerceStartUp.entity.Order;
import com.onilicious.EcommerceStartUp.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO toResponse(Order order) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setUserId(order.getUser().getId());
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus());
        response.setTotal(order.getTotal());
        response.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> items = order.getItems().stream().map(OrderMapper::toItemResponse).toList();

        response.setItems(items);
        return response;
    }

    public static OrderItemResponseDTO toItemResponse(OrderItem item) {
        OrderItemResponseDTO response = new OrderItemResponseDTO();
        response.setItemId(item.getId());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPriceAtPurchase());
        response.setProductId(item.getProductId());
        response.setProductName(item.getProductName());
        response.setSubtotal(item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())));

        return response;
    }
}
