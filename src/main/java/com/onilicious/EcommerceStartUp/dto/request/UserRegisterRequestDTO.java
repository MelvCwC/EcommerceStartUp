package com.onilicious.EcommerceStartUp.dto.request;

import lombok.Getter;
import lombok.Setter;

/*
 * What the client is allowed to send when registering
 */
@Getter
@Setter
public class UserRegisterRequestDTO {
    private String username;
    private String password;
    private String email;
}
