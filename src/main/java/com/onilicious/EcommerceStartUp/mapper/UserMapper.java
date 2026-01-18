package com.onilicious.EcommerceStartUp.mapper;

import com.onilicious.EcommerceStartUp.dto.request.UserRegisterRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.UserResponseDTO;
import com.onilicious.EcommerceStartUp.entity.User;

/*
 * Mapper converts entities and DTOs without containing business logi
 * We dont map inside the service as we want to keep the service focused on business rules and controller focused on API contracts
 *
 */

public class UserMapper {

    public static User toEntity(UserRegisterRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static UserResponseDTO toResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
