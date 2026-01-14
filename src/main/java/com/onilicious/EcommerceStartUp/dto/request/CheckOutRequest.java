package com.onilicious.EcommerceStartUp.dto.request;

import jakarta.validation.constraints.NotNull;

public class CheckOutRequest {
    @NotNull
    private Long userId;

    public CheckOutRequest() {}

    public CheckOutRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
