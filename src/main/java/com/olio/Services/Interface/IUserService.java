package com.olio.Services.Interface;

import com.olio.Dto.Request.LoginRequest;
import com.olio.Dto.Request.UserRequest;
import com.olio.Dto.Response.LoginResponse;
import com.olio.Dto.Response.UserResponse;

import java.util.List;

public interface IUserService {
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
