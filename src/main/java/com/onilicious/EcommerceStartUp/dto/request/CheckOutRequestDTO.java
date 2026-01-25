package com.onilicious.EcommerceStartUp.dto.request;

import jakarta.validation.constraints.NotNull;

public class CheckOutRequestDTO {
    @NotNull
    private Long userId;

    public CheckOutRequestDTO() {}

    public CheckOutRequestDTO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
