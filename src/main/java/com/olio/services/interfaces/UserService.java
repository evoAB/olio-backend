package com.olio.services.interfaces;

import com.olio.dto.Request.LoginRequest;
import com.olio.dto.Request.UserRequest;
import com.olio.dto.Response.LoginResponse;
import com.olio.dto.Response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(UserRequest request);
    LoginResponse login(LoginRequest request);
    List<UserResponse> getAllUser();
    void deleteUser(Long id);
    void requestToBecomeSeller(String userName);
    List<String> getUsers();
    List<String> getSellers();
    List<String> getPendingSellers();
    List<String> getAdmins();
}
