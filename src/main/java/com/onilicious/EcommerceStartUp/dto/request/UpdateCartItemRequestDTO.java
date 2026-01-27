package com.onilicious.EcommerceStartUp.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequestDTO {

    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
}
