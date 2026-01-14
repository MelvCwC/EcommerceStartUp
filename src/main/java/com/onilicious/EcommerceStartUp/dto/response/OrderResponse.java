package com.onilicious.EcommerceStartUp.dto.response;

import com.onilicious.EcommerceStartUp.entity.Order;
import com.onilicious.EcommerceStartUp.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public OrderResponse() {};

    public OrderResponse(Long id, BigDecimal total, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static OrderResponse from(Order o) {
        return new OrderResponse(
                o.getId(),
                o.getTotal(),
                o.getStatus(),
                o.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
