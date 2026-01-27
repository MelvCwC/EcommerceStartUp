package com.onilicious.EcommerceStartUp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckOutRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    public CheckOutRequestDTO() {}

    public CheckOutRequestDTO(Long userId) {
        this.userId = userId;
    }
}
