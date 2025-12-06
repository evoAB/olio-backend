package com.olio.services.transformers;

import com.olio.dto.Request.UserRequest;
import com.olio.dto.Response.UserResponse;
import com.olio.model.User;
import com.olio.enums.Role;

public class UserTransformer {
    public static User toEntity(UserRequest request, String encodedPassword){
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(Role.valueOf("USER"))
                .build();
    }
    
    public static UserResponse toDto(User user, String token){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .token(token)
                .build();
    }

    public static UserResponse convertEntityToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }
}
