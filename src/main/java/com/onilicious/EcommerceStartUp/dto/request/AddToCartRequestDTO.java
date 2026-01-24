package com.onilicious.EcommerceStartUp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequestDTO {
    private Long productId;
    private Integer quantity;
}
