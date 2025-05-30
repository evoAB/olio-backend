package com.olio.Services.Transformers;

import com.olio.Dto.Request.UserRequest;
import com.olio.Dto.Response.UserResponse;
import com.olio.Model.Model.User;

public class UserTransformer {
    public static User toEntity(UserRequest request, String encodedPassword){
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role("USER")
                .build();
    }
    
    public static UserResponse toDto(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
