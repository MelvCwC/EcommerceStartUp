package com.onilicious.EcommerceStartUp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    @NotNull
    private Long userId;

    @NotEmpty
    private List<OrderItemRequestDTO> items;
}
