package com.olio.Dto.Request;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
}
